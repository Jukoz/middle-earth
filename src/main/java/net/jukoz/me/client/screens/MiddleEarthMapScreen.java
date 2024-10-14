package net.jukoz.me.client.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.client.screens.utils.widgets.CycledSelectionWidget;
import net.jukoz.me.client.screens.utils.widgets.ModWidget;
import net.jukoz.me.client.screens.utils.widgets.SearchBarWidget;
import net.jukoz.me.client.screens.utils.widgets.map.FullscreenToggeableMapWidget;
import net.jukoz.me.client.screens.utils.widgets.map.MapWidget;
import net.jukoz.me.network.packets.C2S.PacketTeleportToCustomCoordinate;
import net.jukoz.me.network.packets.C2S.PacketTeleportToDynamicCoordinate;
import net.jukoz.me.network.packets.C2S.PacketTeleportToDynamicWorldCoordinate;
import net.jukoz.me.utils.LoggerUtil;
import net.jukoz.me.world.map.MiddleEarthMapConfigs;
import net.jukoz.me.world.dimension.ModDimensions;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec2f;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import org.joml.Vector2i;

import java.awt.event.KeyEvent;
import java.util.Optional;

@Environment(EnvType.CLIENT)
public class MiddleEarthMapScreen extends Screen {
    private static final Identifier BACKGROUND_TEXTURE = Identifier.of(MiddleEarth.MOD_ID,"textures/gui/map_background.png");
    private static final Identifier MAP_UI_TEXTURE = Identifier.of(MiddleEarth.MOD_ID,"textures/gui/map_ui.png");

    private static final Text MAP_TITLE_TEXT = Text.translatable("ui." + MiddleEarth.MOD_ID + ".map_title_text");
    private FullscreenToggeableMapWidget mapWidget;
    private static final int WIDTH = 208;
    private static final int HEIGHT = 208;
    public static final int MARGIN = 5;
    private boolean isFullscreen = false;
    private ButtonWidget fullscreenButton;
    AbstractClientPlayerEntity player;


    public MiddleEarthMapScreen() {
        super(MAP_TITLE_TEXT);
    }


    @Override
    protected void init() {
        mapWidget = new FullscreenToggeableMapWidget(WIDTH - (MARGIN * 2), HEIGHT - (MARGIN * 2));

        fullscreenButton = ButtonWidget.builder(Text.translatable("map_screen.button.fullscreen_toggle"), x -> {
            isFullscreen = !isFullscreen;
        }).build();
        fullscreenButton.setDimensions(15,15);
        addDrawableChild(fullscreenButton);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        if(isFullscreen){
            renderFullscreen(context);
        } else {
            renderNormal(context);
        }
    }

    private void renderFullscreen(DrawContext context) {
        mapWidget.drawFullscreen(context, MARGIN);

        int fullscreenToggleButtonUvY = (fullscreenButton.isFocused() || fullscreenButton.isFocused()) ? 18 : 1;
        if(!fullscreenButton.active)
            fullscreenToggleButtonUvY = 35;
        int x = context.getScaledWindowWidth() - MARGIN  - 15;
        int y = MARGIN;
        fullscreenButton.setPosition(x, y);
        context.drawTexture(MAP_UI_TEXTURE, x, y, 35, fullscreenToggleButtonUvY, 15, 15);
    }

    private void renderNormal(DrawContext context) {
        int centerX = context.getScaledWindowWidth() / 2;
        int startX = centerX - (WIDTH / 2);
        int endX = centerX + (WIDTH / 2);
        int startY = 10;
        int endY = HEIGHT + 10;


        context.drawTexture(BACKGROUND_TEXTURE, startX, startY, 0, 0,  WIDTH, HEIGHT);
        mapWidget.drawCentered(context, centerX, startY + MARGIN);

        int fullscreenToggleButtonUvY = (fullscreenButton.isFocused() || fullscreenButton.isFocused()) ? 18 : 1;
        if(!fullscreenButton.active)
            fullscreenToggleButtonUvY = 35;
        fullscreenButton.setPosition(endX, startY);
        context.drawTexture(MAP_UI_TEXTURE, endX, startY, 18, fullscreenToggleButtonUvY, 15, 15);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if(keyCode == KeyEvent.VK_CODE_INPUT && !ModWidget.getFocusEnabled()){
            ModWidget.enableFocus(true);
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        mapWidget.mouseClicked(mouseX, mouseY, button);
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        mapWidget.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        mapWidget.mouseReleased(mouseX, mouseY, button);
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        mapWidget.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }
}
