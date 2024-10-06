package net.jukoz.me.gui.forge;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.special.forge.ForgeBlockEntity;
import net.jukoz.me.block.special.forge.MetalTypes;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.List;

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

    private void renderLiquidStorage(DrawContext context, int x, int y) {
        int storedLiquid = (int) (handler.getScaledStoredLiquid() * LIQUID_HEIGHT);
        context.drawTexture(TEXTURE, x + 140, y + 71 - storedLiquid, 177, 114 - storedLiquid, 16, storedLiquid);
    }

    private void renderLiquidStorageTooltip(DrawContext context, int mouseX, int mouseY) {
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        if (mouseX >= x + 140 && mouseX <= x + 155 && mouseY >= y + 12 && mouseY <= y + 71){
            MetalTypes metal = MetalTypes.getValue(handler.getCurrentMetal());
            if(metal != MetalTypes.EMPTY){
                context.drawOrderedTooltip(this.client.textRenderer, Lists.transform(
                        List.of(Text.translatable("tooltip." + MiddleEarth.MOD_ID +".liquid_" + metal.asString().toLowerCase()).withColor(metal.getColor()),
                                Text.literal(handler.getStoredLiquid() / 144  + " ").append(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".ingots_number")),
                                Text.literal(handler.getStoredLiquid() % 144 / 16  + " ").append(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".nuggets_number"))
                        ), Text::asOrderedText), mouseX, mouseY);
            }
        }
    }

    private void renderModeTooltip(DrawContext context, int mouseX, int mouseY) {
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        if (mouseX >= x + 7 && mouseX <= x + 21 && mouseY >= y + 55 && mouseY <= y + 69){
            context.drawOrderedTooltip(this.client.textRenderer, Lists.transform(
                    List.of(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".forge_mode_heating"),
                    Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".forge_mode_heating_switch").formatted(Formatting.GOLD),
                            Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".forge_mode_heating_switch_2").formatted(Formatting.GOLD)
                    ), Text::asOrderedText), mouseX, mouseY);
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX,mouseY,delta);
        super.render(context, mouseX, mouseY, delta);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        drawMouseoverTooltip(context, mouseX, mouseY);
        renderLiquidStorageTooltip(context, mouseX, mouseY);
        renderModeTooltip(context, mouseX, mouseY);
    }
}
