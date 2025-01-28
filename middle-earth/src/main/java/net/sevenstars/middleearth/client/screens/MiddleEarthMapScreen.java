package net.sevenstars.middleearth.client.screens;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.client.screens.utils.widgets.ModWidget;
import net.sevenstars.middleearth.client.screens.utils.widgets.backgrounds.BackgroundContainerWidget;
import net.sevenstars.middleearth.client.screens.utils.widgets.backgrounds.types.BackgroundContainerTypes;
import net.sevenstars.middleearth.client.screens.utils.widgets.map.FullscreenToggeableMapWidget;
import net.sevenstars.middleearth.config.ModClientConfigs;
import net.sevenstars.middleearth.event.KeyInputHandler;
import net.sevenstars.middleearth.network.packets.C2S.PacketTeleportToDynamicWorldCoordinate;
import net.sevenstars.middleearth.utils.ModColors;
import net.sevenstars.middleearth.world.biomes.surface.MapBasedCustomBiome;
import net.sevenstars.middleearth.world.dimension.ModDimensions;
import net.sevenstars.middleearth.world.map.MiddleEarthMapConfigs;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.joml.Vector2d;
import org.joml.Vector2i;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class MiddleEarthMapScreen extends Screen {
    private static final Identifier BACKGROUND_TEXTURE = Identifier.of(MiddleEarth.MOD_ID,"textures/gui/map_background.png");
    private static final Identifier MAP_UI_TEXTURE = Identifier.of(MiddleEarth.MOD_ID,"textures/gui/map_ui.png");

    private static final Text MAP_TITLE_TEXT = Text.translatable("ui." + MiddleEarth.MOD_ID + ".map_screen.map_title_text");
    private static final Vector2i NORMAL_BUTTON_SIZE = new Vector2i(15,15);

    BackgroundContainerWidget backgroundContainerWidget;
    private FullscreenToggeableMapWidget mapWidget;
    private static final int WIDTH = 208;
    private static final int HEIGHT = 208;
    public static final int MARGIN = 5;
    private static boolean isFullscreen = false;

    private static int startX = 0;
    private static int endX = 0;
    private static int startY = 0;
    private static int endY = 0;

    private ButtonWidget fullscreenButton;
    private ButtonWidget overlayToggleButton;
    private ButtonWidget zoomInButton;
    private ButtonWidget zoomOutButton;
    private ButtonWidget recenterButton;

    private int mouseX, mouseY;
    AbstractClientPlayerEntity player;
    public MiddleEarthMapScreen() {
        super(MAP_TITLE_TEXT);
        backgroundContainerWidget = new BackgroundContainerWidget(BackgroundContainerTypes.FULLSCREEN_MAP);
    }



    @Override
    protected void init() {
        Entity cameraEntity = this.client.getCameraEntity();
        if (cameraEntity instanceof AbstractClientPlayerEntity abstractClientPlayerEntity) {
            this.player = abstractClientPlayerEntity;
        } else {
            MiddleEarth.LOGGER.logError("MiddleEarthMapScreen::Init:Couldn't find player");
        }

        mapWidget = new FullscreenToggeableMapWidget(WIDTH - (MARGIN * 2), HEIGHT - (MARGIN * 2));

        // Fullscreen toggle button register
        fullscreenButton = ButtonWidget.builder(Text.translatable("ui." + MiddleEarth.MOD_ID + ".map_screen.button.fullscreen_toggle"), x -> {
            isFullscreen = !isFullscreen;
        }).build();
        fullscreenButton.setDimensions(NORMAL_BUTTON_SIZE.x,NORMAL_BUTTON_SIZE.y);
        addDrawableChild(fullscreenButton);

        // Overlay toggle
        overlayToggleButton = ButtonWidget.builder(Text.translatable("ui." + MiddleEarth.MOD_ID + ".map_screen.button.map_overlay_toggle"), x -> {
            mapWidget.setOverlayState(!mapWidget.isOverlayEnabled());
        }).build();
        overlayToggleButton.active = ModClientConfigs.ENABLE_MAP_OVERLAY;
        overlayToggleButton.setDimensions(NORMAL_BUTTON_SIZE.x,NORMAL_BUTTON_SIZE.y);
        addDrawableChild(overlayToggleButton);

        // Recenter on player
        recenterButton = ButtonWidget.builder(Text.translatable("ui." + MiddleEarth.MOD_ID + ".map_screen.button.recenter_on_player"), x -> {
            Vector2d playerCoords =  new Vector2d(player.getPos().getX(), player.getPos().getZ());
            playerCoords.x /= MiddleEarthMapConfigs.FULL_MAP_SIZE;
            playerCoords.y /= MiddleEarthMapConfigs.FULL_MAP_SIZE;
            mapWidget.instantCenterOnRatio(playerCoords);
        }).build();
        recenterButton.setDimensions(NORMAL_BUTTON_SIZE.x,NORMAL_BUTTON_SIZE.y);
        addDrawableChild(recenterButton);

        // Zoom in button register
        zoomInButton = ButtonWidget.builder(Text.translatable("ui." + MiddleEarth.MOD_ID + ".map_screen.button.zoom_in"), x -> {
            mapWidget.zoomClick();
        }).build();
        zoomInButton.setDimensions(NORMAL_BUTTON_SIZE.x,NORMAL_BUTTON_SIZE.y);
        addDrawableChild(zoomInButton);

        // Zoom out button register
        zoomOutButton = ButtonWidget.builder(Text.translatable("ui." + MiddleEarth.MOD_ID + ".map_screen.button.zoom_out"), x -> {
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
        ModWidget.updateMouse(mouseX, mouseY);
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        showCursorInformationTooltip(context, mouseX, mouseY);
    }

    private void showCursorInformationTooltip(DrawContext context, int mouseX, int mouseY) {
        if(player != null){
            Vector2d mapRatio = mapWidget.getCurrentMapRatio(mouseX, mouseY);
            if(mapRatio != null) {
                List<Text> texts = new ArrayList<>();
                texts.add(Text.translatable("ui." + MiddleEarth.MOD_ID + ".map_screen.tooltip.coordinates_title").formatted(Formatting.UNDERLINE));
                double x = Math.round((mapRatio.x * MiddleEarthMapConfigs.FULL_MAP_SIZE) * 10) / 10.0;
                double z = Math.round((mapRatio.y * MiddleEarthMapConfigs.FULL_MAP_SIZE) * 10) / 10.0;
                texts.add(Text.translatable("ui." + MiddleEarth.MOD_ID + ".map_screen.tooltip.coordinates_label").formatted(Formatting.GRAY)
                        .append(Text.translatable("ui." + MiddleEarth.MOD_ID + ".map_screen.tooltip.coordinates_content", x, z).formatted(Formatting.WHITE)));

                MapBasedCustomBiome biome = mapWidget.getBiomeAt((int) (mapRatio.x * MiddleEarthMapConfigs.REGION_SIZE), (int) (mapRatio.y * MiddleEarthMapConfigs.REGION_SIZE));
                texts.add(Text.translatable("ui." + MiddleEarth.MOD_ID + ".map_screen.tooltip.biome_label").formatted(Formatting.GRAY)
                        .append(Text.translatable("ui." + MiddleEarth.MOD_ID + ".map_screen.tooltip.biome_content", Text.translatable(biome.getBiome().getBiomeRegistryKey().getValue().toTranslationKey("biome"))).formatted(Formatting.WHITE)));
                if(player.hasPermissionLevel(2)){
                    texts.add(Text.translatable("ui." + MiddleEarth.MOD_ID + ".map_screen.tooltip.teleport_keybind", KeyInputHandler.mapTeleportKey.getBoundKeyLocalizedText().getString()).formatted(Formatting.ITALIC).withColor(ModColors.PENDING.color));
                }
                context.drawTooltip(textRenderer, texts, mouseX, mouseY);
            }
        }
    }

    private void teleportToCursor(double mouseX, double mouseY) {
        if(!player.hasPermissionLevel(2))
            return;
        Vector2d mapRatio = mapWidget.getCurrentMapRatio(mouseX, mouseY);
        if(mapRatio != null){
            double x = mapRatio.x * MiddleEarthMapConfigs.FULL_MAP_SIZE;
            double y = mapRatio.y * MiddleEarthMapConfigs.FULL_MAP_SIZE;

            ClientPlayNetworking.send(new PacketTeleportToDynamicWorldCoordinate(x, y));
            this.close();
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
        drawMapOverlayToggleButton(context);
        drawRecenterButton(context);
        drawZoomButtons(context);
        drawPlayer(context, player);
    }

    private void renderNormal(DrawContext context) {
        int centerX = context.getScaledWindowWidth() / 2;
        startX = centerX - (WIDTH / 2);
        endX = centerX + (WIDTH / 2);
        startY = (context.getScaledWindowHeight() / 2) - (HEIGHT / 2);
        endY = startY + HEIGHT;


        context.drawTexture(BACKGROUND_TEXTURE, startX, startY, 0, 0,  WIDTH, HEIGHT);
        mapWidget.drawCentered(context, centerX, startY + MARGIN);
        drawFullscreenToggleButton(context);
        drawMapOverlayToggleButton(context);
        drawRecenterButton(context);
        drawZoomButtons(context);
        drawPlayer(context, player);
    }

    private void drawPlayer(DrawContext context, AbstractClientPlayerEntity player) {
        if(!ModDimensions.isInMiddleEarth(player.getWorld()))
            return;
        Vector2d playerRatio = mapWidget.getMapPointFromWorldCoordinate(new Vector2d(player.getPos().getX(), player.getPos().getZ()));
        int margin = (isFullscreen) ? 0 : MARGIN;
        double x = Math.max(startX + margin + 4, Math.min(endX - 4 - ((isFullscreen) ? NORMAL_BUTTON_SIZE.x : MARGIN), playerRatio.x));
        double y = Math.max(startY + margin + 4, Math.min(endY - 4 - margin, playerRatio.y));


        // TODO (?) : show the head?
        //PlayerSkinDrawer.draw(context, player.getSkinTextures(), (int)x, (int)y, 4);
        //PlayerSkinDrawer.draw(context, minecraft.getSkinProvider().getSkinTexturesSupplier(new GameProfile(UUID.fromString(this.uuid),this.name)).get(),x,y);

        context.drawTexture(MAP_UI_TEXTURE, (int)x- 4, (int) y- 4, 154, 1, 8, 8);
    }

    private void drawFullscreenToggleButton(DrawContext context){
        int fullscreenToggleButtonUvY = ((ModWidget.isMouseOver(fullscreenButton) || fullscreenButton.isFocused()) ? 18 : 1);
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

    private void drawMapOverlayToggleButton(DrawContext context){
        int overlayToggleButtonUvY = (ModWidget.isMouseOver(overlayToggleButton) || overlayToggleButton.isFocused()) ? 69 : 52;
        if(!overlayToggleButton.active)
            overlayToggleButtonUvY = 86;

        int x = fullscreenButton.getX();
        int y = fullscreenButton.getY() + NORMAL_BUTTON_SIZE.y;
        overlayToggleButton.setPosition(x, y);
        context.drawTexture(MAP_UI_TEXTURE, x, y, 1, overlayToggleButtonUvY, NORMAL_BUTTON_SIZE.x, NORMAL_BUTTON_SIZE.y);
    }

    private void drawRecenterButton(DrawContext context){
        recenterButton.active = (ModDimensions.isInMiddleEarth(player.getWorld()));

        int recenterButtonUvY = (ModWidget.isMouseOver(recenterButton) || recenterButton.isFocused()) ? 18 : 1;
        if(!recenterButton.active)
            recenterButtonUvY = 35;

        int x = fullscreenButton.getX();
        int y = zoomInButton.getY() - NORMAL_BUTTON_SIZE.y;
        recenterButton.setPosition(x, y);
        context.drawTexture(MAP_UI_TEXTURE, x, y, 52, recenterButtonUvY, NORMAL_BUTTON_SIZE.x, NORMAL_BUTTON_SIZE.y);
    }

    private void drawZoomButtons(DrawContext context){
        int zoomInButtonUvX = 86;
        int zoomInButtonUvY = (ModWidget.isMouseOver(zoomInButton) || zoomInButton.isFocused()) ? 18 : 1;
        zoomInButton.active = mapWidget.canZoomIn();
        if(!zoomInButton.active)
            zoomInButtonUvY = 35;
        int zoomOutButtonUvX = 69;
        int zoomOutButtonUvY = (ModWidget.isMouseOver(zoomOutButton)|| zoomOutButton.isFocused()) ? 18 : 1;
        zoomOutButton.active = mapWidget.canZoomOut();
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
        if(KeyInputHandler.mapTeleportKey.matchesKey(keyCode, modifiers)){
            teleportToCursor(mouseX, mouseY);
            return true;
        }
        if(KeyInputHandler.mapFullscreenToggle.matchesKey(keyCode, modifiers)){
            isFullscreen = !isFullscreen;
        }
        if(keyCode == KeyEvent.VK_CODE_INPUT && !ModWidget.getFocusEnabled()){
            ModWidget.enableFocus(true);
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        mapWidget.mouseClicked(mouseX, mouseY, button);
        if(KeyInputHandler.mapTeleportKey.matchesMouse(button)){
            teleportToCursor(mouseX, mouseY);
            return true;
        }
        if(KeyInputHandler.mapFullscreenToggle.matchesMouse(button)){
            isFullscreen = !isFullscreen;
        }
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
