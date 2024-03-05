package net.jukoz.me.gui.artisantable;

import com.mojang.blaze3d.systems.RenderSystem;
import net.jukoz.me.MiddleEarth;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.StonecutterScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.StonecutterScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ArtisanTableScreen extends HandledScreen<ArtisanTableScreenHandler> {
    private static final Identifier TEXTURE = new Identifier(MiddleEarth.MOD_ID, "textures/gui/artisan_table.png");
    protected int backgroundWidth = 232;
    protected int backgroundHeight = 166;

    public ArtisanTableScreen(ArtisanTableScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
        titleY -= 2;
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = this.x;
        int y = this.y;
        context.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
}
