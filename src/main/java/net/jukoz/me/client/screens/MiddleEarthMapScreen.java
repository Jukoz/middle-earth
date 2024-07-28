package net.jukoz.me.client.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.jukoz.me.MiddleEarth;
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

import java.util.Optional;

@Environment(EnvType.CLIENT)
public class MiddleEarthMapScreen extends Screen {
    private static final Identifier WINDOW_TEXTURE = Identifier.of(MiddleEarth.MOD_ID,"textures/gui/map_background.png");
    private static final Identifier MAP_UI_TEXTURE = Identifier.of(MiddleEarth.MOD_ID,"textures/gui/map_ui.png");
    private static final Identifier MAP_TEXTURE = Identifier.of(MiddleEarth.MOD_ID,"textures/map.png");
    private static final Text MAP_TITLE_TEXT = Text.translatable("misc." + MiddleEarth.MOD_ID + ".map_title_text");
    public static final int MARGIN = 5;

    public static final int MAP_IMAGE_WIDTH = 3000;
    public static final int MAP_IMAGE_HEIGHT = 3000;
    public int windowWidth, windowHeight;
    public int mapWindowWidth, mapWindowHeight;
    public float minZoom;
    private static final int MAX_ZOOM_LEVEL = 20;
    public static final float [] ZOOM_LEVELS = new float[MAX_ZOOM_LEVEL];
    private static final Vector2i WORLD_SIZE = getWorldSize();

    private static int mapDisplacementX = 0;
    private static int mapDisplacementY = 0;

    private static int zoomLevel = 1;

    private static int zoomButtonIndex;
    private static int dezoomButtonIndex;
    private static int centerOnPlayerButtonIndex;
    private static int debugButtonIndex;

    private Vector2i cursorWorldCoordinate;

    private static boolean debug = false;
    AbstractClientPlayerEntity player;

    private static int pixelWeight;

    public MiddleEarthMapScreen() {
        super(MAP_TITLE_TEXT);
        pixelWeight = MiddleEarthMapConfigs.PIXEL_WEIGHT;
        float zoom = 1;
        for(int i = 0; i < ZOOM_LEVELS.length; i++) {
            ZOOM_LEVELS[i] = zoom;
            zoom *= 1.2f;
        }
    }


    @Override
    protected void init() {
        if(this.client != null) {
            float[]  guiScaleModifiers = { // Index is GUI Scale
                6.25f, // 0
                3f, // 1
                4f, // 2
                5f, // 3
                6.25f, // 4
            };

            int guiScale = Math.max(0, Math.min(guiScaleModifiers.length - 1, this.client.options.getGuiScale().getValue()));
            windowWidth = Math.round((float)MAP_IMAGE_WIDTH / guiScaleModifiers[guiScale] / 2);
            windowHeight = Math.round((float)MAP_IMAGE_HEIGHT / guiScaleModifiers[guiScale] / 2);

            mapWindowWidth =  windowWidth - (MARGIN * 2);
            mapWindowHeight = windowHeight - (MARGIN * 2);
        }

        minZoom = (float) mapWindowWidth / MAP_IMAGE_WIDTH;

        int x = (this.width - windowWidth) / 2;
        int y = (this.height - windowHeight) / 2;

        int offset = 18;
        int buttonMargin = 3;
        int optionsOffsetX = x + windowWidth + 2;
        int optionsOffsetY = y + windowHeight - MARGIN;


        ButtonWidget debugButton = ButtonWidget.builder(Text.literal("Debug"), button -> {
                    debug = !debug;
                })
                .dimensions(optionsOffsetX, y + MARGIN, 18, 18).build();


        ButtonWidget centerOnPlayer = ButtonWidget.builder(Text.literal("Center on Player"), button -> {
                    centerOnPlayer();
                })
                .dimensions(optionsOffsetX, optionsOffsetY - (offset * 3) - (buttonMargin * 2), 18, 18).build();

        ButtonWidget zoomButton = ButtonWidget.builder(Text.literal("Zoom"), button -> {
                    zoom(1, false);
                })
                .dimensions(optionsOffsetX, optionsOffsetY - (offset * 2)  - buttonMargin, 18, 18).build();
        ButtonWidget dezoomButton = ButtonWidget.builder(Text.literal("Dezoom"), button -> {
                    zoom(-1, false);
                })
                .dimensions(optionsOffsetX, optionsOffsetY - offset, 18, 18).build();

        addDrawableChild(debugButton);
        addDrawableChild(centerOnPlayer);
        addDrawableChild(zoomButton);
        addDrawableChild(dezoomButton);

        debugButtonIndex = children().indexOf(debugButton);
        centerOnPlayerButtonIndex = children().indexOf(centerOnPlayer);
        zoomButtonIndex = children().indexOf(zoomButton);
        dezoomButtonIndex = children().indexOf(dezoomButton);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        Entity cameraEntity = this.client.getCameraEntity();
        if(cameraEntity != null) {
            if (cameraEntity instanceof AbstractClientPlayerEntity abstractClientPlayerEntity) {
                this.player = abstractClientPlayerEntity;

                this.renderBackground(context, mouseX, mouseY, delta);
                this.drawWindow(context, mouseX, mouseY);
            } else{
                this.player = null;
            }
        }
    }

    public void drawWindow(DrawContext context, int mouseX, int mouseY) {
        int x = (this.width -  windowWidth) / 2;
        int y = (this.height - windowHeight) / 2;
        RenderSystem.enableBlend();

        drawMaintTextures(context, x, y, mouseX, mouseY);
        Vec2f mapPlayerPos = getCoordinateOnMap((float)player.getBlockPos().getX(), (float)player.getBlockPos().getZ(), 4,4);
        if(ModDimensions.isInMiddleEarth(player.getWorld())){
            context.drawTexture(this.player.getSkinTextures().texture(),
                    x + MARGIN + (int)mapPlayerPos.x - 4,
                    y + MARGIN + (int)mapPlayerPos.y - 4,
                    8, 8, 8, 8, 64, 64);

            boolean outsideBound = cursorIsOutsideOfMapBounds(mouseX, mouseY);
            cursorWorldCoordinate = getWorldCoordinateOfCursor(mouseX, mouseY);

            // Debug panel
            if(debug){
                World world = this.player.getWorld();
                Optional<RegistryKey<Biome>> biomeRegistry = world.getBiome(this.player.getBlockPos()).getKey();
                MutableText text = Text.literal("N/A");
                if(biomeRegistry.isPresent()) {
                    String currentBiomeId = biomeRegistry.get().getValue().toString().replace(':', '.');
                    text = Text.literal("Biome: ");
                    text.append(Text.translatable("biome." + currentBiomeId));

                }
                context.drawTextWithShadow(textRenderer, Text.literal("Player information"), 0, 5, 0xffffff);
                BlockPos playerPos = this.player.getBlockPos();
                context.drawTextWithShadow(textRenderer, Text.literal("Coordinates : " + (int)playerPos.getX() + ", " + (int)playerPos.getY() + ", " + (int)playerPos.getZ()), 5, 15, 0xffffff);
                context.drawTextWithShadow(textRenderer, text, 5, 25, 0xffffff);

                context.drawTextWithShadow(textRenderer, Text.literal("Cursor information"), 0, 45, 0xffffff);
                context.drawTextWithShadow(textRenderer, Text.literal("Coordinates : " + ((outsideBound) ? "N/A" : (int)cursorWorldCoordinate.x + ", "+ (int)cursorWorldCoordinate.y)), 5, 55, 0xffffff);

                /*
                if(!oustideBound && this.player.isCreative()){
                    context.drawTextWithShadow(textRenderer, Text.literal("Right Click to teleport"), mouseX + 10, mouseY, 0xcccccc);
                }
                 */
            }
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if(button == 1){
            if(!cursorIsOutsideOfMapBounds(mouseX, mouseY)){
                if(this.player.isCreative() && debug){
                    getTeleport(getWorldCoordinateOfCursor(mouseX, mouseY));
                    this.close();
                    return true;
                }
            }
        }
        super.mouseClicked(mouseX, mouseY, button);
        return false;
    }

    private void getTeleport(Vector2i coord){
        if(ModDimensions.isInMiddleEarth(this.player.getWorld())){
            String tpString = "/tp %s ~ %s".formatted(coord.x, coord.y);
            new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, tpString);

            /*
                float y = MiddleEarthHeightMap.getHeight(coord.x, coord.y);
                this.player.teleport(coord.x , y, coord.y);
                player.refreshPositionAfterTeleport( coord.x , y, coord.y);
             */
        }
    }

    private float getZoomLevel(){
        return ZOOM_LEVELS[zoomLevel - 1];
    }

    private void drawMaintTextures(DrawContext context, int x, int y, double mouseX, double mouseY) {
        // Border
        context.drawTexture(WINDOW_TEXTURE, x, y, 0, 0,  windowWidth, windowHeight,
                 windowWidth, windowHeight);
        // Map
        context.drawTexture(MAP_TEXTURE, x + MARGIN, y + MARGIN,
                // UV (x,y)
                mapDisplacementX, mapDisplacementY,
                mapWindowWidth,
                 mapWindowHeight,
                (int) (MAP_IMAGE_WIDTH * getZoomLevel() * minZoom),
                (int) (MAP_IMAGE_HEIGHT * getZoomLevel() * minZoom));

        // Map UI

        // Debug Button
        int debugButtonTextureOffset = children().get(debugButtonIndex).isMouseOver(mouseX, mouseY) ? 18 : 0;
        if(!ModDimensions.isInMiddleEarth(player.getWorld())){
            debugButtonTextureOffset = 36;
        }
        context.drawTexture(MAP_UI_TEXTURE,
                ((ButtonWidget)children().get(debugButtonIndex)).getX(),
                ((ButtonWidget)children().get(debugButtonIndex)).getY(),
                debugButtonTextureOffset, 54, 18, 18, 256, 256);

        // Center on player
        int centerOnPlayerTextureOffset = children().get(centerOnPlayerButtonIndex).isMouseOver(mouseX, mouseY) ? 18 : 0;
        if(!canCenterOnPlayer()){
            centerOnPlayerTextureOffset = 36;
        }
        context.drawTexture(MAP_UI_TEXTURE,
                ((ButtonWidget)children().get(centerOnPlayerButtonIndex)).getX(),
                ((ButtonWidget)children().get(centerOnPlayerButtonIndex)).getY(),
                centerOnPlayerTextureOffset, 36, 18, 18, 256, 256);

        // Zoom +
        int zoomTextureOffset = children().get(zoomButtonIndex).isMouseOver(mouseX, mouseY) ? 18 : 0;
        if(zoomLevel ==  MAX_ZOOM_LEVEL - 1){
            zoomTextureOffset = 36;
        }
        context.drawTexture(MAP_UI_TEXTURE,
                ((ButtonWidget)children().get(zoomButtonIndex)).getX(),
                ((ButtonWidget)children().get(zoomButtonIndex)).getY(),
                zoomTextureOffset, 0, 18, 18, 256, 256);

        // Zoom -
        int dezoomTextureOffset = children().get(dezoomButtonIndex).isMouseOver(mouseX, mouseY) ? 18 : 0;
        if(zoomLevel == 1){
            dezoomTextureOffset = 36;
        }
        context.drawTexture(MAP_UI_TEXTURE,
                ((ButtonWidget)children().get(dezoomButtonIndex)).getX(),
                ((ButtonWidget)children().get(dezoomButtonIndex)).getY(),
                dezoomTextureOffset, 18, 18, 18, 256, 256);

        ((ButtonWidget)children().get(debugButtonIndex)).active = ModDimensions.isInMiddleEarth(player.getWorld());
        ((ButtonWidget)children().get(centerOnPlayerButtonIndex)).active = canCenterOnPlayer();
        ((ButtonWidget)children().get(zoomButtonIndex)).active = (zoomLevel < MAX_ZOOM_LEVEL - 1);
        ((ButtonWidget)children().get(dezoomButtonIndex)).active = (zoomLevel > 1);


        // BREE (TEST)
        /*
        Vec2f breeOnMap = getCoordinateOnMap(4100.0f, 2550.0f, 2,2);
        context.drawTexture(MAP_UI_TEXTURE,
                x + MARGIN + (int)breeOnMap.x,
                y + MARGIN + (int)breeOnMap.y,
                54, 0, 4, 4, 256, 256);
        context.drawText(textRenderer,"BREE",
                x + MARGIN + (int)breeOnMap.x + 5,
                y + MARGIN + (int)breeOnMap.y - 2,
                0x000000,
                false);
         */

    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (button != 0) {
            return false;
        }
        if(cursorIsOutsideOfMapBounds(mouseX, mouseY))
            return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);

        mapDisplacementX -= (float) deltaX;
        mapDisplacementY -= (float) deltaY;

        correctMapVision();
        return true;
    }


    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        if(!cursorIsOutsideOfMapBounds(mouseX, mouseY)){
            Vector2i coord = getWorldCoordinateOfCursor(mouseX, mouseY);
            coord.x /= pixelWeight;
            coord.y /= pixelWeight;

            cursorWorldCoordinate = coord;
            zoom((int)Math.round(horizontalAmount), true);
        }
        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }

    private void zoom(int amount, boolean towardCursor){
        int newZoomLevelIndex = (int)Math.min(MAX_ZOOM_LEVEL - 1, Math.max(1, (zoomLevel + amount)));
        if(newZoomLevelIndex != zoomLevel) {
            Vector2i center = (towardCursor) ? cursorWorldCoordinate : getCoordinateInCenterOfMap();

            zoomLevel = newZoomLevelIndex;

            centerOnCoordinates(center.x, center.y);
        }
    }

    private void correctMapVision() {
        // Minimum (0)
        mapDisplacementX = Math.max(0, mapDisplacementX);
        mapDisplacementY = Math.max(0, mapDisplacementY);

        float modifier = getZoomLevel() - 1;
        // Maximum (dynamic)
        // When zoomLevelIndex is 0, maximum should be 0,0
        mapDisplacementX = (int)Math.min((mapWindowWidth) * modifier, mapDisplacementX);
        mapDisplacementY = (int) Math.min(( mapWindowHeight) * modifier, mapDisplacementY);
    }

    private Vec2f getCoordinateOnMap(float posX, float posZ, int textureOffsetX, int textureOffsetY) {
        float transformedPosX = ((posX / pixelWeight / WORLD_SIZE.x) * mapWindowWidth * getZoomLevel()) - mapDisplacementX;
        float transformedPosY = ((posZ / pixelWeight / WORLD_SIZE.y) *  mapWindowHeight * getZoomLevel()) - mapDisplacementY;

        transformedPosX = Math.max(textureOffsetX, Math.min(mapWindowWidth - textureOffsetX, transformedPosX));
        transformedPosY = Math.max(textureOffsetY, Math.min( mapWindowHeight - textureOffsetY, transformedPosY));

        return new Vec2f(transformedPosX, transformedPosY);
    }

    private boolean cursorIsOutsideOfMapBounds(double mouseX, double mouseY){
        int x = (this.width -  windowWidth) / 2  + MARGIN;
        int y = (this.height - windowHeight) / 2 + MARGIN;

        boolean isInBoundX = (int)mouseX - x > 0 && (int)mouseX - x < mapWindowWidth;
        boolean isInBoundY = (int)mouseY - y > 0 && (int)mouseY - y <  mapWindowHeight;

        return !isInBoundX || !isInBoundY;
    }

    private void centerOnPlayer() {
        if(canCenterOnPlayer()){
            centerOnCoordinates(player.getX()/pixelWeight, player.getZ()/pixelWeight);
        }
    }

    private boolean canCenterOnPlayer(){
        return zoomLevel > 1 && this.player != null && ModDimensions.isInMiddleEarth(player.getWorld());
    }

    private void centerOnCoordinates(double x, double y){
        double transformedCoordinatesX = ((x / WORLD_SIZE.x) * mapWindowWidth * getZoomLevel());
        double transformedCoordinatesY = ((y / WORLD_SIZE.y) *  mapWindowHeight * getZoomLevel());

        centerMapTo((int)transformedCoordinatesX, (int)transformedCoordinatesY);
    }

    private Vector2i getCenterOfCurrentMap(){
        int centerX = (int)((mapDisplacementX + (mapWindowWidth / 2)) / (getZoomLevel() * minZoom) / pixelWeight);
        int centerY =  (int)((mapDisplacementY + ( mapWindowHeight / 2)) / (getZoomLevel() * minZoom) / pixelWeight);

        return new Vector2i(centerX, centerY);
    }

    private Vector2i getWorldCoordinateOfCursor(double mouseX, double mouseY) {
        mouseX -= (double) (this.width -  windowWidth) / 2;
        mouseY -= (double) (this.height - windowHeight) / 2 ;

        int centerX = (int)((mapDisplacementX + mouseX) / (getZoomLevel() * minZoom));
        int centerY =  (int)((mapDisplacementY + mouseY) / (getZoomLevel() * minZoom));

        centerX= (int)(WORLD_SIZE.x / MAP_IMAGE_WIDTH * centerX);
        centerY = (int)(WORLD_SIZE.y / MAP_IMAGE_HEIGHT * centerY);

        return new Vector2i(centerX * pixelWeight, centerY * pixelWeight);
    }


    private Vector2i getCoordinateInCenterOfMap(){
        Vector2i currentCenter = getCenterOfCurrentMap();

        currentCenter.x = (int)(WORLD_SIZE.x / MAP_IMAGE_WIDTH * currentCenter.x);
        currentCenter.y = (int)(WORLD_SIZE.y / MAP_IMAGE_HEIGHT * currentCenter.y);

        return currentCenter;
    }

    private void centerMapTo(int x, int y){
        mapDisplacementX = x - (mapWindowWidth / 2);
        mapDisplacementY = y - ( mapWindowHeight / 2);;

        correctMapVision();
    }

    private static Vector2i getWorldSize(){
        float worldSize = (float) Math.pow(2 , MiddleEarthMapConfigs.MAP_ITERATION);

        return new Vector2i((int)(MAP_IMAGE_WIDTH * worldSize), (int)(MAP_IMAGE_HEIGHT * worldSize));
    }
}
