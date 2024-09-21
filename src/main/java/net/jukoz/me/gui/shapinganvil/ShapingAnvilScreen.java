package net.jukoz.me.gui.shapinganvil;

import com.mojang.blaze3d.systems.RenderSystem;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.gui.artisantable.ArtisanTableScreenHandler;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ButtonTextures;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ToggleButtonWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ShapingAnvilScreen extends HandledScreen<ShapingAnvilScreenHandler> {
    private static final Identifier TEXTURE = Identifier.of(MiddleEarth.MOD_ID, "textures/gui/shaping_anvil.png");
    private static final int PROGRESS_ARROW_SIZE = 24;

    private static final Identifier LEFT_CYCLE_OUTPUT_BUTTON = Identifier.of(MiddleEarth.MOD_ID, "left_cycle_arrow");
    private static final Identifier LEFT_CYCLE_OUTPUT_BUTTON_FOCUSED = Identifier.of(MiddleEarth.MOD_ID, "left_cycle_arrow_focused");
    private static final ButtonTextures LEFT_CYCLE_OUTPUT_BUTTON_TEXTURES = new ButtonTextures(LEFT_CYCLE_OUTPUT_BUTTON, LEFT_CYCLE_OUTPUT_BUTTON_FOCUSED);

    private static final Identifier RIGHT_CYCLE_OUTPUT_BUTTON = Identifier.of(MiddleEarth.MOD_ID, "right_cycle_arrow");
    private static final Identifier RIGHT_CYCLE_OUTPUT_BUTTON_FOCUSED = Identifier.of(MiddleEarth.MOD_ID, "right_cycle_arrow_focused");
    private static final ButtonTextures RIGHT_CYCLE_OUTPUT_BUTTON_TEXTURES = new ButtonTextures(RIGHT_CYCLE_OUTPUT_BUTTON, RIGHT_CYCLE_OUTPUT_BUTTON_FOCUSED);

    public ToggleButtonWidget leftOutputCycleButton;
    public ToggleButtonWidget rightOutputCycleButton;

    public ShapingAnvilScreen(ShapingAnvilScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;

        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        this.leftOutputCycleButton = new ToggleButtonWidget(x + 50, y + 36, 7 ,11, true);
        this.leftOutputCycleButton.setTextures(LEFT_CYCLE_OUTPUT_BUTTON_TEXTURES);

        this.rightOutputCycleButton = new ToggleButtonWidget(x + 81, y + 36, 7,11, true);
        this.rightOutputCycleButton.setTextures(RIGHT_CYCLE_OUTPUT_BUTTON_TEXTURES);

        addDrawableChild(leftOutputCycleButton);
        addDrawableChild(rightOutputCycleButton);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        context.drawTexture(TEXTURE, this.x, this.y, 0, 0, this.backgroundWidth, this.backgroundHeight);

        renderProgressArrow(context, x, y);
    }

    private void renderProgressArrow(DrawContext context, int x, int y) {
        if(handler.isBonking()) {
            context.drawTexture(TEXTURE, x + 97, y + 34, 176, 14, (int) (handler.getScaledProgress() * PROGRESS_ARROW_SIZE), 17);
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX,mouseY,delta);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
}
