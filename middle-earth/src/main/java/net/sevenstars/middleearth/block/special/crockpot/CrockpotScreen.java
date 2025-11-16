package net.sevenstars.middleearth.block.special.crockpot;

import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;

public class CrockpotScreen extends HandledScreen<CrockpotScreenHandler> {
    private static final Identifier WATER_TEXTURE = Identifier.ofVanilla("textures/block/water_still.png");
    private static final Identifier TEXTURE = Identifier.of(MiddleEarth.MOD_ID, "textures/gui/crockpot.png");
    private static final Identifier BUBBLES_TEXTURE = Identifier.ofVanilla("container/brewing_stand/bubbles");
    private static final int[] BUBBLE_PROGRESS = new int[]{29, 24, 20, 16, 11, 6, 0};
    private static final int LIQUID_SIZE = 24;

    private static final int PROGRESS_ARROW_SIZE = 24;

    public CrockpotScreen(CrockpotScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
        super.init();
    }

    @Override
    protected void drawBackground(DrawContext context, float deltaTicks, int mouseX, int mouseY) {
        context.drawTexture(RenderPipelines.GUI_TEXTURED, TEXTURE, this.x, this.y, 0, 0, this.backgroundWidth, this.backgroundHeight, 256, 256);
        context.drawTexture(RenderPipelines.GUI_TEXTURED, WATER_TEXTURE, this.x + 120, this.y + 31, 0, 0, 24, 24, 16, 16, 0xFF66AAEE);
    }
}
