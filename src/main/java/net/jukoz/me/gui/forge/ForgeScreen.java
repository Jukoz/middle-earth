package net.jukoz.me.gui.forge;

import com.mojang.blaze3d.systems.RenderSystem;
import net.jukoz.me.MiddleEarth;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ForgeScreen extends HandledScreen<ForgeScreenHandler> {
    private static final Identifier TEXTURE = Identifier.of(MiddleEarth.MOD_ID, "textures/gui/forge.png");
    private static final int PROGRESS_ARROW_SIZE = 24;
    private static final int COOKING_FIRE_SIZE = 14;

    public ForgeScreen(ForgeScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        context.drawTexture(TEXTURE, this.x, this.y, 0, 0, this.backgroundWidth, this.backgroundHeight);

        renderModeText(context, x, y);
        renderProgressArrow(context, x, y);
    }

    private void renderProgressArrow(DrawContext context, int x, int y) {
        if(handler.isCooking()) {
            int cookingTime = (int) (handler.getScaledCooking() * COOKING_FIRE_SIZE);
            context.drawTexture(TEXTURE, x + 53, y + 50 - cookingTime, 176, COOKING_FIRE_SIZE - cookingTime, COOKING_FIRE_SIZE, cookingTime);
        }
        if(handler.isCrafting()) {
            context.drawTexture(TEXTURE, x + 94, y + 34, 176, 14, (int) (handler.getScaledProgress() * PROGRESS_ARROW_SIZE), 17);
        }
    }

    private void renderModeText(DrawContext context, int x, int y) {
        if(handler.hasBellows()){
            context.drawTextWithShadow(this.textRenderer, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".mode").append(" Alloying"), x + 94, y + 65, 0xFF6060);
        } else {
            context.drawTextWithShadow(this.textRenderer, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".mode").append(" Heating"), x + 94, y + 65, 0xFF6060);
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX,mouseY,delta);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
}
