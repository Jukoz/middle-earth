package net.sevenstars.middleearth.gui.structuremanager;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.text.Text;

import java.util.List;

@Environment(value = EnvType.CLIENT)
public class ChoiceListWidget extends AlwaysSelectedEntryListWidget<ChoiceListWidget.ChoiceEntry> {
    private static final int TEXT_COLOR = 0xFFFFFFFF;
    private static final int TEXT_INDENT = 4;
    private static final int SCROLLBAR_WIDTH = 6;
    private static final int SCROLLBAR_ROOM = 10;

    public record Choice(String value, Text label) {}

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
        clearEntries();
        ChoiceEntry selected = null;
        for(Choice choice : choices) {
            ChoiceEntry entry = new ChoiceEntry(choice);
            addEntry(entry);
            if(choice.value().equals(current))
                selected = entry;
        }
        // Something no longer in the list is shown as the first choice
        setSelected(selected == null && !choices.isEmpty() ? getFirst() : selected);
        setScrollY(0);
        if(selected != null)
            centerScrollOn(selected);
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
            context.drawTextWithShadow(textRenderer, choice.label(), x + TEXT_INDENT, y + (entryHeight - textRenderer.fontHeight) / 2, TEXT_COLOR);
        }
    }
}
