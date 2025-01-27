package net.sevenstars.middleearth.gui.shapinganvil;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.ModToolItems;
import net.sevenstars.middleearth.network.packets.C2S.AnvilIndexPacket;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ButtonTextures;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ToggleButtonWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;

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

        this.leftOutputCycleButton = new ToggleButtonWidget(x + 69, y + 19, 7 ,11, true);
        this.leftOutputCycleButton.setTextures(LEFT_CYCLE_OUTPUT_BUTTON_TEXTURES);

        this.rightOutputCycleButton = new ToggleButtonWidget(x + 100, y + 19, 7,11, true);
        this.rightOutputCycleButton.setTextures(RIGHT_CYCLE_OUTPUT_BUTTON_TEXTURES);

        addDrawableChild(leftOutputCycleButton);
        addDrawableChild(rightOutputCycleButton);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.leftOutputCycleButton.mouseClicked(mouseX, mouseY, button)) {
            ClientPlayNetworking.send(new AnvilIndexPacket(true, handler.getPos().getX(),handler.getPos().getY(),handler.getPos().getZ()));
        }

        if (this.rightOutputCycleButton.mouseClicked(mouseX, mouseY, button)) {
            ClientPlayNetworking.send(new AnvilIndexPacket(false, handler.getPos().getX(),handler.getPos().getY(),handler.getPos().getZ()));
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        context.drawTexture(TEXTURE, this.x, this.y, 0, 0, this.backgroundWidth, this.backgroundHeight);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX,mouseY,delta);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        context.drawItem(ModToolItems.SMITHING_HAMMER.getDefaultStack(), x + 81, y + 34);

        renderHammerTooltip(context, mouseX, mouseY);

        if (this.handler.getOutput().isEmpty()){
            context.drawTexture(TEXTURE, x + 79, y + 15, 177, 115,18, 18);
        } else {
            context.drawItem(this.handler.getOutput(), x + 80, y + 16);
            renderOutputTooltip(context, mouseX, mouseY);
        }
    }

    private void renderOutputTooltip(DrawContext context, int mouseX, int mouseY) {
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        if (mouseX >= x + 79 && mouseX <= x + 96 && mouseY >= y + 16 && mouseY <= y + 33){
            context.drawTooltip(this.client.textRenderer, handler.getOutput().getItem().getName(), mouseX, mouseY);
        }
    }

    private void renderHammerTooltip(DrawContext context, int mouseX, int mouseY) {
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        if (mouseX >= x + 79 && mouseX <= x + 96 && mouseY >= y + 34 && mouseY <= y + 51){
            context.drawOrderedTooltip(this.client.textRenderer,
                    Lists.transform(List.of(Text.translatable("tooltip." + MiddleEarth.MOD_ID +".anvil_hammer"),
                            Text.translatable("tooltip." + MiddleEarth.MOD_ID +".anvil_hammer_2")),
                            Text::asOrderedText), mouseX, mouseY);
        }
    }
}
