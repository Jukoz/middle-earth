package net.jukoz.me.client.screens;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.network.packets.C2S.PacketTeleportToCurrentSpawn;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.awt.event.KeyEvent;

@Environment(EnvType.CLIENT)
public class OnboardingSelectionScreen extends Screen {
    private static final Text ONBOARDING_SELECTION_TITLE = Text.of("onboarding_selection_screen");
    private static final Identifier BUTTON_WIDGET = Identifier.of(MiddleEarth.MOD_ID,"textures/gui/widget/button_widget.png");
    private boolean focusEnabled;
    public ButtonWidget continueAsCharacterButton;
    public ButtonWidget resetCharacterButton;

    private int mouseX;
    private int mouseY;
    private boolean canResetCharacter;
    private ClientPlayerEntity player;

    public OnboardingSelectionScreen(boolean canResetCharacter) {
        super(ONBOARDING_SELECTION_TITLE);
        this.canResetCharacter = canResetCharacter;
        focusEnabled = false;
    }

    @Override
    protected void init() {
        ButtonWidget.PressAction continueAsFaction = button -> {
            teleportPlayerToMiddleEarth();
        };
        continueAsCharacterButton = ButtonWidget.builder(Text.of("continue_character"), continueAsFaction).build();
        addDrawableChild(continueAsCharacterButton);

        if(canResetCharacter){
            ButtonWidget.PressAction resetCharacterAction = button -> {
                MinecraftClient mc = MinecraftClient.getInstance();
                mc.setScreen(new FactionSelectionScreen());
            };
            resetCharacterButton = ButtonWidget.builder(Text.of("reset_character"), resetCharacterAction).build();
            addDrawableChild(resetCharacterButton);
        }
    }

    private void teleportPlayerToMiddleEarth() {
        ClientPlayNetworking.send(new PacketTeleportToCurrentSpawn(false));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        Entity cameraEntity = this.client.getCameraEntity();
        if (cameraEntity != null) {
            if (cameraEntity instanceof ClientPlayerEntity clientPlayerEntity) {
                this.player = clientPlayerEntity;
                this.mouseX = mouseX;
                this.mouseY = mouseY;
                this.renderBackground(context, mouseX, mouseY, delta);
                this.drawContent(context);
            } else {
                this.player = null;
            }
        }
    }

    private void drawContent(DrawContext context) {
        int panelSizeX = 102;
        int panelSizeY = 18;
        int margin = 5;

        // Draw buttons
        int startX = (width / 2) - (panelSizeX / 2);
        int startY = (height / 2) - (panelSizeY / 2);
        context.drawTexture(BUTTON_WIDGET,
                startX,
                startY,
                0, continueAsCharacterButton.isFocused() || isMouseOver(startX, panelSizeX, startY, panelSizeY) ? 19 : 0,
                panelSizeX,
                panelSizeY
        );
        Text continueText = Text.translatable("ui.me.continue_character");
        context.drawText(textRenderer, continueText,
                startX + (int)((panelSizeX - textRenderer.getWidth(continueText)) / 2f),
                startY + (int) ((panelSizeY / 2f) - (textRenderer.fontHeight / 2f)) + 1,
                0, false);

        continueAsCharacterButton.setDimensionsAndPosition(panelSizeX, panelSizeY, startX, startY);
        if(focusEnabled && continueAsCharacterButton.isFocused()){
            context.drawTexture(BUTTON_WIDGET,
                    startX,
                    startY,
                    103, 0,
                    panelSizeX,
                    panelSizeY
            );
        }

        if(canResetCharacter) {

            startY += panelSizeY + margin;

            context.drawTexture(BUTTON_WIDGET,
                    startX,
                    startY,
                    0, resetCharacterButton.isFocused() || isMouseOver(startX, panelSizeX, startY, panelSizeY) ? 19 : 0,
                    panelSizeX,
                    panelSizeY
            );
            Text resetText = Text.translatable("ui.me.reset_character");
            context.drawText(textRenderer, resetText,
                    startX + (int) ((panelSizeX - textRenderer.getWidth(resetText)) / 2f),
                    startY + (int) ((panelSizeY / 2f) - (textRenderer.fontHeight / 2f)) + 1,
                    0, false);
            resetCharacterButton.setDimensionsAndPosition(panelSizeX, panelSizeY, startX, startY);
            if(focusEnabled && resetCharacterButton.isFocused()){
                context.drawTexture(BUTTON_WIDGET,
                        startX,
                        startY,
                        103, 0,
                        panelSizeX,
                        panelSizeY
                );
            }
        }
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        // Keybind : Tabulation
        if(keyCode == KeyEvent.VK_CODE_INPUT && !focusEnabled){
            focusEnabled = true;
            return true;
        }

        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    private boolean isMouseOver(int startX, int sizeX, int startY, int sizeY) {
        return mouseX >= startX && mouseX <= startX + sizeX
                && mouseY >= startY && mouseY <= startY + sizeY;
    }
}
