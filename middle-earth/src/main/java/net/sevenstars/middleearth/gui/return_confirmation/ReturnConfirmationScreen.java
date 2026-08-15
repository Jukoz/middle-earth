package net.sevenstars.middleearth.gui.return_confirmation;

import net.neoforged.neoforge.network.PacketDistributor;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.CommonColors;
import net.minecraft.world.InteractionHand;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.gui.utils.widgets.ModWidget;
import net.sevenstars.middleearth.network.packets.C2S.PacketTeleportToCurrentOverworldSpawn;
import net.sevenstars.middleearth.network.handlers.OnboardingReturnResult;

import java.awt.event.KeyEvent;

public class ReturnConfirmationScreen extends Screen {
    private static final Component RETURN_CONFIRMATION_TITLE = Component.translatable("ui.%s.return_confirmation.title".formatted(MiddleEarth.MOD_ID));
    private static final ResourceLocation BUTTON_WIDGET = ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID,"textures/gui/widget/button_widget.png");
    public Button returnToOverworldButton;
    public Button closeButton;
    float currentDelay;
    private final InteractionHand interactionHand;
    private boolean requestPending;

    public ReturnConfirmationScreen(float delay, InteractionHand interactionHand) {
        super(RETURN_CONFIRMATION_TITLE);
        currentDelay = delay;
        this.interactionHand = interactionHand;
    }

    @Override
    protected void init() {
        Button.OnPress returnToOverworldAction = button -> {
            returnToOverworld();
        };
        returnToOverworldButton = Button.builder(Component.translatable("ui.%s.return_confirmation.continue_character.title".formatted(MiddleEarth.MOD_ID)), returnToOverworldAction).build();
        addRenderableWidget(returnToOverworldButton);
        if(currentDelay > 0)
            returnToOverworldButton.active = false;
    }

    private void returnToOverworld() {
        if (requestPending) {
            return;
        }
        requestPending = true;
        returnToOverworldButton.active = false;
        PacketDistributor.sendToServer(new PacketTeleportToCurrentOverworldSpawn(interactionHand == InteractionHand.OFF_HAND));
    }

    public void handleResult(OnboardingReturnResult.Status status, int retryAfterMillis) {
        requestPending = false;
        if (status == OnboardingReturnResult.Status.SUCCESS) {
            onClose();
            return;
        }

        showFailure(status.translationSuffix());
        if (status.retryable()) {
            currentDelay = Math.max(0.05F, retryAfterMillis / 1000.0F);
            returnToOverworldButton.active = currentDelay <= 0.0F;
        } else {
            onClose();
        }
    }

    private void showFailure(String suffix) {
        if (minecraft != null && minecraft.player != null) {
            minecraft.player.displayClientMessage(
                    Component.translatable(
                            "ui.%s.return_confirmation.error.%s".formatted(MiddleEarth.MOD_ID, suffix)
                    ),
                    false
            );
        }
    }

    @Override
    public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        ModWidget.updateMouse(mouseX, mouseY);
        this.drawContent(context);
    }

    @Override
    public void tick() {
        if(currentDelay > 0){
            currentDelay = Math.max(0, currentDelay - (1f / 20));
            if(currentDelay == 0 && !requestPending) {
                returnToOverworldButton.active = true;
            }
        }
        super.tick();
    }

    private void drawContent(GuiGraphics context) {
        int panelSizeX = 102;
        int panelSizeY = 18;
        int margin = 5;

        // Draw buttons
        int startX = (width / 2) - (panelSizeX / 2);
        int startY = (height / 2) - (panelSizeY / 2);
        if(returnToOverworldButton.active){
            context.blit(BUTTON_WIDGET,
                    startX, startY,0, returnToOverworldButton.isFocused() || isMouseOver(startX, panelSizeX, startY, panelSizeY) ? 19 : 0,
                    panelSizeX, panelSizeY, 256, 256);

            Component continueText = Component.translatable("ui.%s.return_confirmation.continue_character.content".formatted(MiddleEarth.MOD_ID));
            context.drawString(font, continueText,
                    startX + (int)((panelSizeX - font.width(continueText)) / 2f),
                    startY + (int) ((panelSizeY / 2f) - (font.lineHeight / 2f)) + 1,
                    CommonColors.BLACK, false);

            returnToOverworldButton.setRectangle(panelSizeX, panelSizeY, startX, startY);
            if(ModWidget.getFocusEnabled() && returnToOverworldButton.isFocused()){
                context.blit(BUTTON_WIDGET,
                        startX, startY,103, 0,
                        panelSizeX, panelSizeY, 256, 256);
            }
        } else {
            context.blit(BUTTON_WIDGET,
                    startX, startY,0, 38,
                    panelSizeX, panelSizeY, 256, 256);

            Component delayText = Component.literal(String.valueOf((Math.round(this.currentDelay * 10f) /10f)));
            context.drawString(font, delayText,
                    startX + (panelSizeX / 2) - (font.width(delayText) / 2),
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
