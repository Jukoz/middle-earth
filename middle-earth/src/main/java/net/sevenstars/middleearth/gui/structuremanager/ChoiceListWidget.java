package net.sevenstars.middleearth.gui.structuremanager;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import net.sevenstars.middleearth.MiddleEarth;

import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Locale;

@Environment(value = EnvType.CLIENT)
public class ChoiceListWidget extends AlwaysSelectedEntryListWidget<ChoiceListWidget.ChoiceEntry> {
    private static final int TEXT_COLOR = 0xFFFFFFFF;
    private static final int HEADER_COLOR = 0xFFA0A0A0;
    private static final int TEXT_INDENT = 4;
    private static final int SCROLLBAR_WIDTH = 6;
    private static final int SCROLLBAR_ROOM = 10;
    private static final int SEARCH_HEIGHT = 16;
    private static final int SEARCH_GAP = 2;
    private static final int LABEL_GAP = 4;

    private List<Choice> all = List.of();
    private String filter = "";

    // A header stands over the choices below it and cannot be picked
    public record Choice(String value, Text label, boolean header) {
        public Choice(String value, Text label) {
            this(value, label, false);
        }

        public static Choice header(Text label) {
            return new Choice("", label, true);
        }
    }

    public ChoiceListWidget(int width, int height, int x, int y, int rowHeight) {
        super(MinecraftClient.getInstance(), width, height, y, rowHeight);
        setX(x);
    }

    // The game's lists lay their rows out 220 wide around the middle, wider than these narrow side by side ones, and the
    // rows spilled out of the list on both sides with the scroll bar in the next list. Rows fill the list instead
    @Override
    public int getRowWidth() {
        return width - SCROLLBAR_ROOM;
    }

    @Override
    protected int getScrollbarX() {
        return getRight() - SCROLLBAR_WIDTH;
    }

    public void setChoices(List<Choice> choices, String current) {
        all = List.copyOf(choices);
        show(current);
    }

    // What is picked stays in the list whatever is searched for, or saving would pick something else
    public void setFilter(String text) {
        String current = selectedValue();
        filter = text.trim().toLowerCase(Locale.ROOT);
        show(current);
    }

    // A box to type in above the list, which then holds only the choices with that in their name
    public TextFieldWidget newSearchField(TextRenderer textRenderer) {
        Text hint = Text.translatable("ui.%s.choice_list.search".formatted(MiddleEarth.MOD_ID));
        TextFieldWidget field = new TextFieldWidget(textRenderer, getX(), getY() - SEARCH_HEIGHT - SEARCH_GAP, width, SEARCH_HEIGHT, hint);
        field.setPlaceholder(hint);
        field.setChangedListener(this::setFilter);
        return field;
    }

    // Where a label over the list goes, above its search box
    public int labelY(TextRenderer textRenderer) {
        return getY() - SEARCH_HEIGHT - SEARCH_GAP - textRenderer.fontHeight - LABEL_GAP;
    }

    // stables_master is shown as Stables Master
    public static Text readable(String name) {
        StringBuilder text = new StringBuilder();
        for(String word : name.split("_")) {
            if(word.isEmpty())
                continue;
            if(!text.isEmpty())
                text.append(' ');
            text.append(word.substring(0, 1).toUpperCase(Locale.ROOT)).append(word.substring(1));
        }
        return Text.literal(text.toString());
    }

    private void show(String current) {
        clearEntries();
        ChoiceEntry selected = null;
        for(Choice choice : all) {
            // Headers only stand over the whole list, not over what a search left of it
            if(choice.header() ? !filter.isEmpty() : !choice.value().equals(current) && !choice.label().getString().toLowerCase(Locale.ROOT).contains(filter))
                continue;
            ChoiceEntry entry = new ChoiceEntry(choice);
            addEntry(entry);
            if(!choice.header() && choice.value().equals(current))
                selected = entry;
        }
        // Something no longer in the list is shown as the first choice
        if(selected == null) {
            for(ChoiceEntry entry : children()) {
                if(!entry.choice.header()) {
                    selected = entry;
                    break;
                }
            }
        }
        setSelected(selected);
        setScrollY(0);
        if(selected != null)
            centerScrollOn(selected);
    }

    @Override
    public void setSelected(@Nullable ChoiceEntry entry) {
        if(entry == null || !entry.choice.header())
            super.setSelected(entry);
    }

    public String selectedValue() {
        ChoiceEntry entry = getSelectedOrNull();
        return entry == null ? "" : entry.choice.value();
    }

    public class ChoiceEntry extends AlwaysSelectedEntryListWidget.Entry<ChoiceEntry> {
        private final Choice choice;

        ChoiceEntry(Choice choice) {
            this.choice = choice;
        }

        @Override
        public Text getNarration() {
            return choice.label();
        }

        @Override
        public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float deltaTicks) {
            var textRenderer = MinecraftClient.getInstance().textRenderer;
            context.drawTextWithShadow(textRenderer, choice.label(), x + (choice.header() ? 0 : TEXT_INDENT), y + (entryHeight - textRenderer.fontHeight) / 2,
                    choice.header() ? HEADER_COLOR : TEXT_COLOR);
        }
    }
}
