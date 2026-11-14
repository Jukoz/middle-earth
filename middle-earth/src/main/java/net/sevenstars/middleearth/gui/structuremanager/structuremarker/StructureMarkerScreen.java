package net.sevenstars.middleearth.gui.structuremanager.structuremarker;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.sevenstars.middleearth.MiddleEarth;

@Environment(value = EnvType.CLIENT)
public class StructureMarkerScreen extends HandledScreen<StructureMarkerScreenHandler> {
    private ButtonWidget kindButton;

    public StructureMarkerScreen(StructureMarkerScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        kindButton = ButtonWidget.builder(kindText(), button -> {
            handler.selectNext();
            button.setMessage(kindText());
        }).dimensions(width / 2 - 75, height / 2 - 10, 150, 20).build();
        addDrawableChild(kindButton);
    }

    private Text kindText() {
        return Text.translatable("ui.%s.structure_marker.kind".formatted(MiddleEarth.MOD_ID),
                Text.translatable("structure_marker.%s.%s".formatted(MiddleEarth.MOD_ID, handler.getKind().asString())));
    }

    @Override
    protected void drawBackground(DrawContext context, float deltaTicks, int mouseX, int mouseY) {
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        renderBackground(context, mouseX, mouseY, deltaTicks);
        super.render(context, mouseX, mouseY, deltaTicks);
    }
}
