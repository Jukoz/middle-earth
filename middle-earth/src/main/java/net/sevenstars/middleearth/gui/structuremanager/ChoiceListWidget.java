package net.sevenstars.middleearth.gui.structuremanager;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import net.minecraft.item.ItemStack;
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
    private static final int LABEL_GAP = 3;
    private static final int ICON_SIZE = 16;
    private static final int HOVER = 0x18FFFFFF;
    private static final int SELECTED = 0x38D4A94F;
    private static final int ACCENT_WIDTH = 2;
    private static final int HEADER_LINE = 0xFF333A45;

    private @Nullable TextFieldWidget search;

    private List<Choice> all = List.of();
    private String filter = "";

    public record Choice(String value, Text label, boolean header, @Nullable Text detail, ItemStack icon) {
        public Choice(String value, Text label) {
            this(value, label, false, null, ItemStack.EMPTY);
        }

        public Choice(String value, Text label, @Nullable Text detail, ItemStack icon) {
            this(value, label, false, detail, icon);
        }

        public static Choice header(Text label) {
            return new Choice("", label, true, null, ItemStack.EMPTY);
        }
    }

    public ChoiceListWidget(int width, int height, int x, int y, int rowHeight) {
        super(MinecraftClient.getInstance(), width, height, y, rowHeight);
        setX(x);
    }

    // Vanilla rows are 220 wide and centered, too wide for these lists
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

    // Keep the selection when the search filters it out, so saving doesn't change it
    public void setFilter(String text) {
        String current = selectedValue();
        filter = text.trim().toLowerCase(Locale.ROOT);
        show(current);
        // Warn when nothing matches (the kept selection doesn't count)
        if(search != null)
            search.setEditableColor(!filter.isEmpty() && all.stream().noneMatch(choice -> !choice.header() && matches(choice)) ? EditorPanel.WARNING : EditorPanel.TEXT);
    }

    private boolean matches(Choice choice) {
        return choice.label().getString().toLowerCase(Locale.ROOT).contains(filter);
    }

    @Override
    protected void drawMenuListBackground(DrawContext context) {
        EditorPanel.well(context, getX(), getY(), width, height);
    }

    @Override
    protected void drawHeaderAndFooterSeparators(DrawContext context) {
    }

    @Override
    protected void drawSelectionHighlight(DrawContext context, ChoiceEntry entry, int borderColor) {
        int y = entry.getY();
        context.fill(getX(), y, getScrollbarX(), y + entry.getHeight(), SELECTED);
        context.fill(getX(), y, getX() + ACCENT_WIDTH, y + entry.getHeight(), EditorPanel.ACCENT);
    }

    @Override
    public void renderWidget(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        super.renderWidget(context, mouseX, mouseY, deltaTicks);
        if(children().isEmpty()) {
            var textRenderer = MinecraftClient.getInstance().textRenderer;
            Text none = Text.translatable("ui.%s.choice_list.%s".formatted(MiddleEarth.MOD_ID, filter.isEmpty() ? "empty" : "no_match"));
            context.drawCenteredTextWithShadow(textRenderer, none, getX() + width / 2, getY() + (height - textRenderer.fontHeight) / 2, EditorPanel.MUTED);
        }
    }

    public TextFieldWidget newSearchField(TextRenderer textRenderer) {
        Text hint = Text.translatable("ui.%s.choice_list.search".formatted(MiddleEarth.MOD_ID));
        TextFieldWidget field = new TextFieldWidget(textRenderer, getX(), getY() - SEARCH_HEIGHT - SEARCH_GAP, width, SEARCH_HEIGHT, hint);
        field.setPlaceholder(hint);
        field.setChangedListener(this::setFilter);
        search = field;
        return field;
    }

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
            // Headers only show when the list isn't filtered
            if(choice.header() ? !filter.isEmpty() : !choice.value().equals(current) && !matches(choice))
                continue;
            ChoiceEntry entry = new ChoiceEntry(choice);
            addEntry(entry);
            if(!choice.header() && choice.value().equals(current))
                selected = entry;
        }
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
        public void render(DrawContext context, int mouseX, int mouseY, boolean hovered, float deltaTicks) {
            int x = getContentX();
            int y = getContentY();
            int entryHeight = getContentHeight();
            var textRenderer = MinecraftClient.getInstance().textRenderer;
            int textY = y + (entryHeight - textRenderer.fontHeight) / 2 + 1;
            int right = getScrollbarX() - TEXT_INDENT;
            if(choice.header()) {
                context.drawTextWithShadow(textRenderer, choice.label(), x, textY, HEADER_COLOR);
                int from = x + textRenderer.getWidth(choice.label()) + TEXT_INDENT;
                if(from < right)
                    context.fill(from, y + entryHeight / 2, right, y + entryHeight / 2 + 1, HEADER_LINE);
                return;
            }
            if(hovered && getSelectedOrNull() != this)
                context.fill(getX(), getY(), getScrollbarX(), getY() + getHeight(), HOVER);
            int left = x + TEXT_INDENT;
            if(!choice.icon().isEmpty()) {
                context.drawItem(choice.icon(), left, y + (entryHeight - ICON_SIZE) / 2);
                left += ICON_SIZE + TEXT_INDENT;
            }
            if(choice.detail() != null) {
                int detailWidth = textRenderer.getWidth(choice.detail());
                if(left + textRenderer.getWidth(choice.label()) + TEXT_INDENT + detailWidth <= right) {
                    context.drawTextWithShadow(textRenderer, choice.detail(), right - detailWidth, textY, HEADER_COLOR);
                    right -= detailWidth + TEXT_INDENT;
                }
            }
            String label = choice.label().getString();
            if(textRenderer.getWidth(label) > right - left)
                label = textRenderer.trimToWidth(label, right - left - textRenderer.getWidth("...")) + "...";
            context.drawTextWithShadow(textRenderer, label, left, textY, TEXT_COLOR);
        }
    }
}
