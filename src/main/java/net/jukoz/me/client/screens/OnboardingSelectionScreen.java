package net.jukoz.me.client.screens;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.network.packets.TeleportRequestPacket;
import net.jukoz.me.network.packets.TeleportToMeSpawnRequestPacket;
import net.jukoz.me.resources.StateSaverAndLoader;
import net.jukoz.me.resources.persistent_datas.PlayerData;
import net.jukoz.me.utils.LoggerUtil;
import net.jukoz.me.world.dimension.ModDimensions;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.joml.Vector3i;

public class OnboardingSelectionScreen extends Screen {
    private static final Text ONBOARDING_SELECTION_TITLE = Text.of("onboarding_selection_screen");
    private static final Identifier BUTTON_WIDGET = Identifier.of(MiddleEarth.MOD_ID,"textures/gui/widget/button_widget.png");


    public ButtonWidget continueAsCharacterButton;
    public ButtonWidget resetCharacterButton;

    private int mouseX;
    private int mouseY;
    private boolean canResetCharacter;
    private ClientPlayerEntity player;

    public OnboardingSelectionScreen(boolean canResetCharacter) {
        super(ONBOARDING_SELECTION_TITLE);
        this.canResetCharacter = canResetCharacter;
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
                mc.setScreenAndRender(new FactionSelectionScreen());
            };
            resetCharacterButton = ButtonWidget.builder(Text.of("reset_character"), resetCharacterAction).build();
            addDrawableChild(resetCharacterButton);
        }
    }

    private void teleportPlayerToMiddleEarth() {
        ClientPlayNetworking.send(new TeleportToMeSpawnRequestPacket(true));
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
                0, isMouseOver(startX, panelSizeX, startY, panelSizeY) ? 19 : 0,
                panelSizeX,
                panelSizeY
        );
        Text continueText = Text.translatable("me.continue_character");
        context.drawText(textRenderer, continueText,
                startX + (int)((panelSizeX - textRenderer.getWidth(continueText)) / 2f),
                startY + (int) ((panelSizeY / 2f) - (textRenderer.fontHeight / 2f)) + 1,
                0, false);

        continueAsCharacterButton.setDimensionsAndPosition(panelSizeX, panelSizeY, startX, startY);

        if(canResetCharacter) {

            startY += panelSizeY + margin;

            context.drawTexture(BUTTON_WIDGET,
                    startX,
                    startY,
                    0, isMouseOver(startX, panelSizeX, startY, panelSizeY) ? 19 : 0,
                    panelSizeX,
                    panelSizeY
            );
            Text resetText = Text.translatable("me.reset_character");
            context.drawText(textRenderer, resetText,
                    startX + (int) ((panelSizeX - textRenderer.getWidth(resetText)) / 2f),
                    startY + (int) ((panelSizeY / 2f) - (textRenderer.fontHeight / 2f)) + 1,
                    0, false);
            resetCharacterButton.setDimensionsAndPosition(panelSizeX, panelSizeY, startX, startY);
        }
    }

    private boolean isMouseOver(int startX, int sizeX, int startY, int sizeY) {
        return mouseX >= startX && mouseX <= startX + sizeX
                && mouseY >= startY && mouseY <= startY + sizeY;
    }
}
