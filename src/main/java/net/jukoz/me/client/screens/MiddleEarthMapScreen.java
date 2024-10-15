package net.jukoz.me.client.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.client.screens.utils.widgets.CycledSelectionWidget;
import net.jukoz.me.client.screens.utils.widgets.ModWidget;
import net.jukoz.me.client.screens.utils.widgets.SearchBarWidget;
import net.jukoz.me.client.screens.utils.widgets.backgrounds.BackgroundContainerWidget;
import net.jukoz.me.client.screens.utils.widgets.backgrounds.types.BackgroundContainerTypes;
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
    private static final Vector2i NORMAL_BUTTON_SIZE = new Vector2i(15,15);

    BackgroundContainerWidget backgroundContainerWidget;
    private FullscreenToggeableMapWidget mapWidget;
    private static final int WIDTH = 208;
    private static final int HEIGHT = 208;
    public static final int MARGIN = 5;
    private boolean isFullscreen = false;

    private int startX = 0;
    private int endX = 0;
    private int startY = 0;
    private int endY = 0;

    private ButtonWidget fullscreenButton;
    private ButtonWidget zoomInButton;
    private ButtonWidget zoomOutButton;

    AbstractClientPlayerEntity player;


    public MiddleEarthMapScreen() {
        super(MAP_TITLE_TEXT);
        backgroundContainerWidget = new BackgroundContainerWidget(BackgroundContainerTypes.FULLSCREEN_MAP);
    }


    @Override
    protected void init() {
        mapWidget = new FullscreenToggeableMapWidget(WIDTH - (MARGIN * 2), HEIGHT - (MARGIN * 2));

        // Fullscreen toggle button register
        fullscreenButton = ButtonWidget.builder(Text.translatable("map_screen.button.fullscreen_toggle"), x -> {
            isFullscreen = !isFullscreen;
        }).build();
        fullscreenButton.setDimensions(NORMAL_BUTTON_SIZE.x,NORMAL_BUTTON_SIZE.y);
        addDrawableChild(fullscreenButton);

        // Zoom in button register
        zoomInButton = ButtonWidget.builder(Text.translatable("map_screen.button.zoom_in"), x -> {
            mapWidget.zoomClick();
        }).build();
        zoomInButton.setDimensions(NORMAL_BUTTON_SIZE.x,NORMAL_BUTTON_SIZE.y);
        addDrawableChild(zoomInButton);

        // Zoom out button register
        zoomOutButton = ButtonWidget.builder(Text.translatable("map_screen.button.zoom_out"), x -> {
            mapWidget.dezoomClick();
        }).build();
        zoomOutButton.setDimensions(NORMAL_BUTTON_SIZE.x,NORMAL_BUTTON_SIZE.y);
        addDrawableChild(zoomOutButton);
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
        startX = MARGIN;
        endX = context.getScaledWindowWidth() - MARGIN;
        startY = MARGIN;
        endY = context.getScaledWindowHeight() - MARGIN;

        // TODO : Draw dynamic background : context.drawTexture(BACKGROUND_TEXTURE, startX, startY, 0, 0,  WIDTH, HEIGHT);
        backgroundContainerWidget.draw(context, 0, 0, context.getScaledWindowWidth(), context.getScaledWindowHeight());

        mapWidget.drawFullscreen(context, MARGIN);
        drawFullscreenToggleButton(context);
        drawZoomButtons(context);
    }

    private void renderNormal(DrawContext context) {
        int centerX = context.getScaledWindowWidth() / 2;
        startX = centerX - (WIDTH / 2);
        endX = centerX + (WIDTH / 2);
        startY = 10;
        endY = HEIGHT + 10;

        context.drawTexture(BACKGROUND_TEXTURE, startX, startY, 0, 0,  WIDTH, HEIGHT);
        mapWidget.drawCentered(context, centerX, startY + MARGIN);
        drawFullscreenToggleButton(context);
        drawZoomButtons(context);
    }

    private void drawFullscreenToggleButton(DrawContext context){
        int fullscreenToggleButtonUvY = (fullscreenButton.isHovered() || fullscreenButton.isFocused()) ? 18 : 1;
        if(!fullscreenButton.active)
            fullscreenToggleButtonUvY = 35;
        if(isFullscreen){
            int x = context.getScaledWindowWidth() - MARGIN - NORMAL_BUTTON_SIZE.x;
            int y = MARGIN;
            fullscreenButton.setPosition(x, y);
            context.drawTexture(MAP_UI_TEXTURE, x, y, 35, fullscreenToggleButtonUvY, NORMAL_BUTTON_SIZE.x, NORMAL_BUTTON_SIZE.y);
        } else {
            fullscreenButton.setPosition(endX, startY);
            context.drawTexture(MAP_UI_TEXTURE, endX, startY, 18, fullscreenToggleButtonUvY, NORMAL_BUTTON_SIZE.x, NORMAL_BUTTON_SIZE.y);
        }
    }

    private void drawZoomButtons(DrawContext context){
        int zoomInButtonUvX = 86;
        int zoomInButtonUvY = (zoomInButton.isHovered() || zoomInButton.isFocused()) ? 18 : 1;
        zoomInButton.active = mapWidget.canZoomIn;
        if(!zoomInButton.active)
            zoomInButtonUvY = 35;
        int zoomOutButtonUvX = 69;
        int zoomOutButtonUvY = (zoomOutButton.isHovered() || zoomOutButton.isFocused()) ? 18 : 1;
        zoomOutButton.active = mapWidget.canZoomOut;
        if(!zoomOutButton.active)
            zoomOutButtonUvY = 35;

        if(isFullscreen){
            // Zoom out
            int x = context.getScaledWindowWidth() - MARGIN - NORMAL_BUTTON_SIZE.x;
            int y = context.getScaledWindowHeight() - MARGIN - NORMAL_BUTTON_SIZE.y;
            zoomOutButton.setPosition(x, y);
            context.drawTexture(MAP_UI_TEXTURE, x, y, zoomOutButtonUvX, zoomOutButtonUvY, NORMAL_BUTTON_SIZE.x, NORMAL_BUTTON_SIZE.y);
            // Zoom in
            y -= NORMAL_BUTTON_SIZE.y;
            zoomInButton.setPosition(x, y);
            context.drawTexture(MAP_UI_TEXTURE, x, y, zoomInButtonUvX, zoomInButtonUvY, NORMAL_BUTTON_SIZE.x, NORMAL_BUTTON_SIZE.y);
        } else {
            // Zoom out
            int y = endY - NORMAL_BUTTON_SIZE.y;
            zoomOutButton.setPosition(endX, y);
            context.drawTexture(MAP_UI_TEXTURE, endX, y, zoomOutButtonUvX, zoomOutButtonUvY, NORMAL_BUTTON_SIZE.x, NORMAL_BUTTON_SIZE.y);
            // Zoom in
            y -= NORMAL_BUTTON_SIZE.y;
            zoomInButton.setPosition(endX, y);
            context.drawTexture(MAP_UI_TEXTURE, endX, y, zoomInButtonUvX, zoomInButtonUvY, NORMAL_BUTTON_SIZE.x, NORMAL_BUTTON_SIZE.y);
        }
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
