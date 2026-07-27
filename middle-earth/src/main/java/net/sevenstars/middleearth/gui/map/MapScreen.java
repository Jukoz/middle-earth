package net.sevenstars.middleearth.gui.map;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.config.ModClientConfigs;
import net.sevenstars.middleearth.event.KeyInputHandler;
import net.sevenstars.middleearth.gui.utils.widgets.ModWidget;
import net.sevenstars.middleearth.gui.utils.widgets.backgrounds.BackgroundContainerWidget;
import net.sevenstars.middleearth.gui.utils.widgets.backgrounds.types.BackgroundContainerTypes;
import net.sevenstars.middleearth.gui.utils.widgets.map.FullscreenToggeableMapWidget;
import net.sevenstars.middleearth.utils.ModColors;
import net.sevenstars.middleearth.world.biomes.surface.MapBasedCustomBiome;
import net.sevenstars.middleearth.world.map.MiddleEarthMapConfigs;
import org.joml.Vector2d;
import org.joml.Vector2i;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class MapScreen extends Screen {
    private static final ResourceLocation BACKGROUND_TEXTURE = ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID,"textures/gui/map_background.png");
    private static final ResourceLocation MAP_UI_TEXTURE = ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID,"textures/gui/map_ui.png");

    private static final Component MAP_TITLE_TEXT = Component.translatable("ui." + MiddleEarth.MOD_ID + ".map_screen.map_title_text");
    private static final Vector2i NORMAL_BUTTON_SIZE = new Vector2i(15,15);

    BackgroundContainerWidget backgroundContainerWidget;
    private static final int WIDTH = 208;
    private static final int HEIGHT = 208;
    public static final int MARGIN = 5;

    private static int startX = 0;
    private static int endX = 0;
    private static int startY = 0;
    private static int endY = 0;

    public FullscreenToggeableMapWidget mapWidget;
    public Button fullscreenButton;
    public Button overlayToggleButton;
    public Button zoomInButton;
    public Button zoomOutButton;
    public Button recenterButton;
    private int mouseX, mouseY;
    public MapScreenController controller = null;
    public boolean isFullscreen = false;
    public boolean playerIsInDimension = false;
    public BlockPos playerBlockPos = null;
    public boolean hasTeleportPermission = false;

    public MapScreen() {
        super(MAP_TITLE_TEXT);
        backgroundContainerWidget = new BackgroundContainerWidget(BackgroundContainerTypes.FULLSCREEN_MAP);
    }

    @Override
    protected void init() {
        mapWidget = new FullscreenToggeableMapWidget(WIDTH - (MARGIN * 2), HEIGHT - (MARGIN * 2));

        // Fullscreen toggle button register
        fullscreenButton = Button.builder(Component.translatable("ui." + MiddleEarth.MOD_ID + ".map_screen.button.fullscreen_toggle"), x -> {
            isFullscreen = !isFullscreen;
        }).build();
        fullscreenButton.setSize(NORMAL_BUTTON_SIZE.x,NORMAL_BUTTON_SIZE.y);
        addRenderableWidget(fullscreenButton);

        // Overlay toggle
        overlayToggleButton = Button.builder(Component.translatable("ui." + MiddleEarth.MOD_ID + ".map_screen.button.map_overlay_toggle"), x -> {
            mapWidget.setOverlayState(!mapWidget.isOverlayEnabled());
        }).build();
        overlayToggleButton.active = ModClientConfigs.ENABLE_MAP_OVERLAY;
        overlayToggleButton.setSize(NORMAL_BUTTON_SIZE.x,NORMAL_BUTTON_SIZE.y);
        addRenderableWidget(overlayToggleButton);

        // Recenter on player
        recenterButton = Button.builder(Component.translatable("ui." + MiddleEarth.MOD_ID + ".map_screen.button.recenter_on_player"), x -> {
            Vector2d playerCoords = new Vector2d(playerBlockPos.getX(), playerBlockPos.getZ());
            playerCoords.x /= MiddleEarthMapConfigs.FULL_MAP_SIZE;
            playerCoords.y /= MiddleEarthMapConfigs.FULL_MAP_SIZE;
            mapWidget.instantCenterOnRatio(playerCoords);
        }).build();
        recenterButton.setSize(NORMAL_BUTTON_SIZE.x,NORMAL_BUTTON_SIZE.y);
        addRenderableWidget(recenterButton);

        // Zoom in button register
        zoomInButton = Button.builder(Component.translatable("ui." + MiddleEarth.MOD_ID + ".map_screen.button.zoom_in"), x -> {
            mapWidget.zoomClick();
        }).build();
        zoomInButton.setSize(NORMAL_BUTTON_SIZE.x,NORMAL_BUTTON_SIZE.y);
        addRenderableWidget(zoomInButton);

        // Zoom out button register
        zoomOutButton = Button.builder(Component.translatable("ui." + MiddleEarth.MOD_ID + ".map_screen.button.zoom_out"), x -> {
            mapWidget.dezoomClick();
        }).build();
        zoomOutButton.setSize(NORMAL_BUTTON_SIZE.x,NORMAL_BUTTON_SIZE.y);
        addRenderableWidget(zoomOutButton);

    }

    @Override
    public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {
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

    private void showCursorInformationTooltip(GuiGraphics context, int mouseX, int mouseY) {
        Vector2d mapRatio = mapWidget.getCurrentMapRatio(mouseX, mouseY);
        if(mapRatio != null) {
            List<Component> texts = new ArrayList<>();
            texts.add(Component.translatable("ui." + MiddleEarth.MOD_ID + ".map_screen.tooltip.coordinates_title").withStyle(ChatFormatting.UNDERLINE));
            double x = Math.round((mapRatio.x * MiddleEarthMapConfigs.FULL_MAP_SIZE) * 10) / 10.0;
            double z = Math.round((mapRatio.y * MiddleEarthMapConfigs.FULL_MAP_SIZE) * 10) / 10.0;
            texts.add(Component.translatable("ui." + MiddleEarth.MOD_ID + ".map_screen.tooltip.coordinates_label").withStyle(ChatFormatting.GRAY)
                    .append(Component.translatable("ui." + MiddleEarth.MOD_ID + ".map_screen.tooltip.coordinates_content", x, z).withStyle(ChatFormatting.WHITE)));

            MapBasedCustomBiome biome = mapWidget.getBiomeAt((int) (mapRatio.x * MiddleEarthMapConfigs.REGION_SIZE), (int) (mapRatio.y * MiddleEarthMapConfigs.REGION_SIZE));
            texts.add(Component.translatable("ui." + MiddleEarth.MOD_ID + ".map_screen.tooltip.biome_label").withStyle(ChatFormatting.GRAY)
                    .append(Component.translatable("ui." + MiddleEarth.MOD_ID + ".map_screen.tooltip.biome_content", Component.translatable(biome.getBiome().getBiomeRegistryKey().location().toLanguageKey("biome"))).withStyle(ChatFormatting.WHITE)));
            if(hasTeleportPermission){
                texts.add(Component.translatable("ui." + MiddleEarth.MOD_ID + ".map_screen.tooltip.teleport_keybind", KeyInputHandler.mapTeleportKey.getTranslatedKeyMessage().getString()).withStyle(ChatFormatting.ITALIC).withColor(ModColors.PENDING.color));
            }
            context.renderComponentTooltip(font, texts, mouseX, mouseY);
        }

    }

    private void renderFullscreen(GuiGraphics context) {
        startX = MARGIN;
        endX = context.guiWidth() - MARGIN;
        startY = MARGIN;
        endY = context.guiHeight() - MARGIN;

        // TODO : Draw dynamic background : context.drawTexture(BACKGROUND_TEXTURE, startX, startY, 0, 0,  WIDTH, HEIGHT);
        backgroundContainerWidget.draw(context, 0, 0, context.guiWidth(), context.guiHeight());

        mapWidget.drawFullscreen(context, MARGIN);
        drawFullscreenToggleButton(context);
        drawMapOverlayToggleButton(context);
        drawRecenterButton(context);
        drawZoomButtons(context);
        drawPlayer(context);
    }

    private void renderNormal(GuiGraphics context) {
        int centerX = context.guiWidth() / 2;
        startX = centerX - (WIDTH / 2);
        endX = centerX + (WIDTH / 2);
        startY = (context.guiHeight() / 2) - (HEIGHT / 2);
        endY = startY + HEIGHT;

        context.blit(BACKGROUND_TEXTURE,
                startX, startY, 0, 0,
                WIDTH, HEIGHT, 256, 256);

        mapWidget.drawCentered(context, centerX, startY + MARGIN);
        drawFullscreenToggleButton(context);
        drawMapOverlayToggleButton(context);
        drawRecenterButton(context);
        drawZoomButtons(context);
        drawPlayer(context);
    }

    private void drawPlayer(GuiGraphics context) {
        if(!playerIsInDimension) return;
        Vector2d playerRatio = mapWidget.getMapPointFromWorldCoordinate(new Vector2d(playerBlockPos.getX(), playerBlockPos.getZ()));
        int margin = (isFullscreen) ? 0 : MARGIN;
        double x = Math.max(startX + margin + 4, Math.min(endX - 4 - ((isFullscreen) ? NORMAL_BUTTON_SIZE.x : MARGIN), playerRatio.x));
        double y = Math.max(startY + margin + 4, Math.min(endY - 4 - margin, playerRatio.y));


        // TODO (?) : show the head?
        //PlayerSkinDrawer.draw(context, player.getSkinTextures(), (int)x, (int)y, 4);
        //PlayerSkinDrawer.draw(context, minecraft.getSkinProvider().getSkinTexturesSupplier(new GameProfile(UUID.fromString(this.uuid),this.name)).get(),x,y);

        context.blit(MAP_UI_TEXTURE,
                (int)x- 4, (int) y- 4, 154, 1,
                8, 8, 256, 256);
    }

    private void drawFullscreenToggleButton(GuiGraphics context){
        int fullscreenToggleButtonUvY = ((ModWidget.isMouseOver(fullscreenButton) || fullscreenButton.isFocused()) ? 18 : 1);
        if(!fullscreenButton.active)
            fullscreenToggleButtonUvY = 35;
        if(isFullscreen){
            int x = context.guiWidth() - MARGIN - NORMAL_BUTTON_SIZE.x;
            int y = MARGIN;
            fullscreenButton.setPosition(x, y);
            context.blit(MAP_UI_TEXTURE,
                    x, y, 35, fullscreenToggleButtonUvY,
                    NORMAL_BUTTON_SIZE.x, NORMAL_BUTTON_SIZE.y, 256, 256);

        } else {
            fullscreenButton.setPosition(endX, startY);
            context.blit(MAP_UI_TEXTURE,
                    fullscreenButton.getX(), fullscreenButton.getY(), 35, fullscreenToggleButtonUvY,
                    NORMAL_BUTTON_SIZE.x, NORMAL_BUTTON_SIZE.y, 256, 256);
        }
    }

    private void drawMapOverlayToggleButton(GuiGraphics context){
        int overlayToggleButtonUvY = (ModWidget.isMouseOver(overlayToggleButton) || overlayToggleButton.isFocused()) ? 69 : 52;
        if(!overlayToggleButton.active)
            overlayToggleButtonUvY = 86;

        int x = fullscreenButton.getX();
        int y = fullscreenButton.getY() + NORMAL_BUTTON_SIZE.y;
        overlayToggleButton.setPosition(x, y);

        context.blit(MAP_UI_TEXTURE,
                x, y, 1, overlayToggleButtonUvY,
                NORMAL_BUTTON_SIZE.x, NORMAL_BUTTON_SIZE.y, 256, 256);
    }

    private void drawRecenterButton(GuiGraphics context){
        recenterButton.active = playerIsInDimension;

        int recenterButtonUvY = (ModWidget.isMouseOver(recenterButton) || recenterButton.isFocused()) ? 18 : 1;
        if(!recenterButton.active)
            recenterButtonUvY = 35;

        int x = fullscreenButton.getX();
        int y = zoomInButton.getY() - NORMAL_BUTTON_SIZE.y;
        recenterButton.setPosition(x, y);
        context.blit(MAP_UI_TEXTURE,
                x, y, 52, recenterButtonUvY,
                NORMAL_BUTTON_SIZE.x, NORMAL_BUTTON_SIZE.y, 256, 256);
    }

    private void drawZoomButtons(GuiGraphics context){
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
            int x = context.guiWidth() - MARGIN - NORMAL_BUTTON_SIZE.x;
            int y = context.guiHeight() - MARGIN - NORMAL_BUTTON_SIZE.y;
            zoomOutButton.setPosition(x, y);
            context.blit(MAP_UI_TEXTURE,
                    x, y, zoomOutButtonUvX, zoomOutButtonUvY,
                    NORMAL_BUTTON_SIZE.x, NORMAL_BUTTON_SIZE.y, 256, 256);
            // Zoom in
            y -= NORMAL_BUTTON_SIZE.y;
            zoomInButton.setPosition(x, y);
            context.blit(MAP_UI_TEXTURE,
                    x, y, zoomInButtonUvX, zoomInButtonUvY,
                    NORMAL_BUTTON_SIZE.x, NORMAL_BUTTON_SIZE.y, 256, 256);
        } else {
            // Zoom out
            int y = endY - NORMAL_BUTTON_SIZE.y;
            zoomOutButton.setPosition(endX, y);
            context.blit(MAP_UI_TEXTURE,
                    endX, y, zoomOutButtonUvX, zoomOutButtonUvY,
                    NORMAL_BUTTON_SIZE.x, NORMAL_BUTTON_SIZE.y, 256, 256);
            // Zoom in
            y -= NORMAL_BUTTON_SIZE.y;
            zoomInButton.setPosition(endX, y);
            context.blit(MAP_UI_TEXTURE,
                    endX, y, zoomInButtonUvX, zoomInButtonUvY,
                    NORMAL_BUTTON_SIZE.x, NORMAL_BUTTON_SIZE.y, 256, 256);
        }
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        controller.keyPressed(keyCode, scanCode, modifiers, mouseX, mouseY);
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        controller.mouseClicked(mouseX, mouseY, button);
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
