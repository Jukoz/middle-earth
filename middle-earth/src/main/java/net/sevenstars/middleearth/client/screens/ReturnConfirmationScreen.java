package net.sevenstars.middleearth.client.screens;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.render.RenderLayer;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.client.screens.utils.widgets.ModWidget;
import net.sevenstars.middleearth.network.packets.C2S.PacketTeleportToCurrentOverworldSpawn;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.awt.event.KeyEvent;

public class ReturnConfirmationScreen extends Screen {
    private static final Text RETURN_CONFIRMATION_TITLE = Text.translatable("ui.me.return_confirmation.title");
    private static final Identifier BUTTON_WIDGET = Identifier.of(MiddleEarth.MOD_ID,"textures/gui/widget/button_widget.png");
    public ButtonWidget returnToOverworldButton;
    public ButtonWidget closeButton;
    float currentDelay;
    public ReturnConfirmationScreen(float delay) {
        super(RETURN_CONFIRMATION_TITLE);
        currentDelay = delay;
    }

    @Override
    protected void init() {
        ButtonWidget.PressAction returnToOverworldAction = button -> {
            returnToOverworld();
        };
        returnToOverworldButton = ButtonWidget.builder(Text.translatable("ui.me.return_confirmation.continue_character.title"), returnToOverworldAction).build();
        addDrawableChild(returnToOverworldButton);
        if(currentDelay > 0)
            returnToOverworldButton.active = false;
    }

    private void returnToOverworld() {
        ClientPlayNetworking.send(new PacketTeleportToCurrentOverworldSpawn());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        ModWidget.updateMouse(mouseX, mouseY);
        this.renderBackground(context, mouseX, mouseY, delta);
        this.drawContent(context);
    }

    @Override
    public void tick() {
        if(currentDelay > 0){
            currentDelay = Math.max(0, currentDelay - (1f / 20));
            if(currentDelay == 0) {
                returnToOverworldButton.active = true;
            }
        }
        super.tick();
    }

    private void drawContent(DrawContext context) {
        int panelSizeX = 102;
        int panelSizeY = 18;
        int margin = 5;

        // Draw buttons
        int startX = (width / 2) - (panelSizeX / 2);
        int startY = (height / 2) - (panelSizeY / 2);
        if(returnToOverworldButton.active){
            context.drawTexture(RenderLayer::getGuiTextured, BUTTON_WIDGET,
                    startX, startY,0, returnToOverworldButton.isFocused() || isMouseOver(startX, panelSizeX, startY, panelSizeY) ? 19 : 0,
                    panelSizeX, panelSizeY, 256, 256);

            Text continueText = Text.translatable("ui.me.return_confirmation.continue_character.content");
            context.drawText(textRenderer, continueText,
                    startX + (int)((panelSizeX - textRenderer.getWidth(continueText)) / 2f),
                    startY + (int) ((panelSizeY / 2f) - (textRenderer.fontHeight / 2f)) + 1,
                    0, false);

            returnToOverworldButton.setDimensionsAndPosition(panelSizeX, panelSizeY, startX, startY);
            if(ModWidget.getFocusEnabled() && returnToOverworldButton.isFocused()){
                context.drawTexture(RenderLayer::getGuiTextured, BUTTON_WIDGET,
                        startX, startY,103, 0,
                        panelSizeX, panelSizeY, 256, 256);
            }
        } else {
            context.drawTexture(RenderLayer::getGuiTextured, BUTTON_WIDGET,
                    startX, startY,0, 38,
                    panelSizeX, panelSizeY, 256, 256);

            Text delayText = Text.literal(String.valueOf((Math.round(this.currentDelay * 10f) /10f)));
            context.drawText(textRenderer, delayText,
                    startX + (panelSizeX / 2) - (textRenderer.getWidth(delayText) / 2),
                    startY + 5, 0xc4343e, true);
        }
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        // Keybind : Tabulation
        if(keyCode == KeyEvent.VK_CODE_INPUT && !ModWidget.getFocusEnabled()){
            ModWidget.enableFocus(true);
            return true;
        }

        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    private boolean isMouseOver(int startX, int sizeX, int startY, int sizeY) {
        return ModWidget.isMouseOver(sizeX, sizeY, startX, startY);
    }
}
