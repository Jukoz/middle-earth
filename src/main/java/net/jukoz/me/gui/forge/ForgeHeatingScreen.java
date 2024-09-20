package net.jukoz.me.gui.forge;

import com.mojang.blaze3d.systems.RenderSystem;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.special.forge.ForgeBlockEntity;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ForgeHeatingScreen extends HandledScreen<ForgeHeatingScreenHandler> {
    private static final Identifier TEXTURE = Identifier.of(MiddleEarth.MOD_ID, "textures/gui/forge_heating.png");

    private static final int PROGRESS_FIRE_SIZE = 21;
    private static final int COOKING_FIRE_SIZE = 14;
    private static final int LIQUID_HEIGHT = 60;
    private Boolean heatingMode = null;

    public ForgeHeatingScreen(ForgeHeatingScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
    }

    @Override
    protected void handledScreenTick() {
        super.handledScreenTick();
        if(heatingMode == null)
            heatingMode = handler.heatingMode();
        else if(handler.heatingMode() != heatingMode) {
            heatingMode = handler.heatingMode();
            this.close();
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);

        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        RenderSystem.setShaderTexture(0, TEXTURE);
        context.drawTexture(TEXTURE, this.x, this.y, 0, 0, this.backgroundWidth, this.backgroundHeight);


        renderModeText(context, x, y);
        renderProgressArrow(context, x, y);
        renderLiquidStorage(context, x, y);
    }

    private void renderProgressArrow(DrawContext context, int x, int y) {
        if(handler.isCooking()) {
            int cookingTime = (int) (handler.getScaledCooking() * COOKING_FIRE_SIZE);
            context.drawTexture(TEXTURE, x + 80, y + 50 - cookingTime, 176, COOKING_FIRE_SIZE - cookingTime, COOKING_FIRE_SIZE, cookingTime);
        }
        if(handler.isCrafting()) {
            int progress = (int) (handler.getScaledProgress() * PROGRESS_FIRE_SIZE);
            context.drawTexture(TEXTURE, x + 23, y + 51 - progress, 177, 53 - progress, 13, progress);
        }
    }

    private void renderModeText(DrawContext context, int x, int y) {
        //if(handler.hasBellows()){
        //    context.drawTextWithShadow(this.textRenderer, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".mode").append(" Alloying"), x + 97, y + 73, 0xFF6060);
        //} else {
        //    context.drawTextWithShadow(this.textRenderer, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".mode").append(" Heating"), x + 97, y + 73, 0xFF6060);
        //}
    }

    private void renderLiquidStorage(DrawContext context, int x, int y) {
        int storedLiquid = (int) (handler.getScaledStoredLiquid() * LIQUID_HEIGHT);
        context.drawTexture(TEXTURE, x + 112, y + 70 - storedLiquid, 177, LIQUID_HEIGHT, 16, storedLiquid);
    }

    private void renderLiquidStorageTooltip(DrawContext context, int mouseX, int mouseY) {
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        int x1 = 140;
        int x2 = 155;

        if (mouseX >= x + x1 && mouseX <= x + x2 && mouseY >= y + 12 && mouseY <= y + 71){
            ForgeBlockEntity.MetalTypes metal = ForgeBlockEntity.MetalTypes.getValue(handler.getCurrentMetal());
            if(metal != ForgeBlockEntity.MetalTypes.EMPTY){
                context.drawTooltip(this.client.textRenderer,
                    Text.translatable("tooltip." + MiddleEarth.MOD_ID +".liquid_" + metal.asString().toLowerCase())
                        .append(": ")
                        .append(handler.getStoredLiquid() / 144  + " ")
                        .append(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".ingots_number")
                            .append(" " + handler.getStoredLiquid() % 144 / 16  + " ")
                            .append(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".nuggets_number"))), mouseX, mouseY);
            }
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        if(heatingMode == null) return;
        renderBackground(context, mouseX,mouseY,delta);
        super.render(context, mouseX, mouseY, delta);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        drawMouseoverTooltip(context, mouseX, mouseY);
        renderLiquidStorageTooltip(context, mouseX, mouseY);
    }
}
