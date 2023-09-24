package net.jesteur.me.client.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.jesteur.me.MiddleEarth;
import net.jesteur.me.world.chunkgen.map.MapImageLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.ControlsListWidget;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Text;
import net.minecraft.text.Style;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector2i;

import javax.swing.text.StyleContext;
import java.text.DecimalFormat;
import java.util.Objects;

@Environment(EnvType.CLIENT)
public class MiddleEarthMapScreen extends Screen {
    private static final Identifier WINDOW_TEXTURE = new Identifier(MiddleEarth.MOD_ID,"textures/gui/map_background.png");
    private static final Identifier MAP_UI_TEXTURE = new Identifier(MiddleEarth.MOD_ID,"textures/gui/map_ui.png");
    private static final Identifier MAP_TEXTURE = new Identifier(MiddleEarth.MOD_ID,"textures/map.png");
    private static final Text RETURN_TO_GAME_TEXT = Text.translatable("menu.returnToGame");
    private static final Text MAP_TITLE_TEXT = Text.of("Middle-earth Map");
    public static final int MARGIN = 5;

    public static final int MAP_WIDTH = 1400;
    public static final int MAP_HEIGHT = 1216;

    public static final int WINDOW_WIDTH = Math.round((float)MAP_WIDTH / (10f/3f));
    public static final int WINDOW_HEIGHT = Math.round((float)MAP_HEIGHT / (10f/3f));

    public static final int MAP_WINDOW_WIDTH = WINDOW_WIDTH - (MARGIN * 2);
    public static final int MAP_WINDOW_HEIGHT = WINDOW_HEIGHT - (MARGIN * 2);

    public static final float MIN_ZOOM = (float) MAP_WINDOW_WIDTH / MAP_WIDTH;
    private static final int MAX_ZOOM_LEVEL = 10;
    private static final Vector2i WORLD_SIZE = getWorldSize();

    private static int mapDisplacementX = 0;
    private static int mapDisplacementY = 0;

    private static int zoomLevel = 1;

    private static int zoomButtonIndex;
    private float zoomButtonHover = 0f;
    private static int dezoomButtonIndex;
    private float dezoomButtonHover = 0f;
    private static int centerOnPlayerButgtonIndex;
    private float centerOnPlayerButtonHover = 0f;

    private Vec3d playerCoordinate;

    private Vector2i cursorWorldCoordinate;


    public MiddleEarthMapScreen() {
        super(MAP_TITLE_TEXT);
    }

    @Override
    protected void init() {

        int x = (this.width - WINDOW_WIDTH) / 2;
        int y = (this.height - WINDOW_HEIGHT) / 2;

        int offset = 18 + 3;
        int signsOffsetX = x + WINDOW_WIDTH + 2;
        int signsOffsetY = y + WINDOW_HEIGHT - (offset * 3);


        ButtonWidget centerOnPlayer = ButtonWidget.builder(Text.literal("Center on Player"), button -> {
                    centerOnCoordinates(playerCoordinate.x, playerCoordinate.z);
                })
                .dimensions(signsOffsetX, signsOffsetY, 18, 18).build();

        ButtonWidget zoomButton = ButtonWidget.builder(Text.literal("Zoom"), button -> {
                    zoom(1, false);
                })
                .dimensions(signsOffsetX, signsOffsetY + offset, 18, 18).build();
        ButtonWidget dezoomButton = ButtonWidget.builder(Text.literal("Dezoom"), button -> {
                    zoom(-1, false);
                })
                .dimensions(signsOffsetX, signsOffsetY + (offset * 2), 18, 18).build();

        addDrawableChild(centerOnPlayer);
        addDrawableChild(zoomButton);
        addDrawableChild(dezoomButton);

        centerOnPlayerButgtonIndex = children().indexOf(centerOnPlayer);
        zoomButtonIndex = children().indexOf(zoomButton);
        dezoomButtonIndex = children().indexOf(dezoomButton);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        this.drawWindow(context, mouseX, mouseY);
    }

    public void drawWindow(DrawContext context, int mouseX, int mouseY) {
        int x = (this.width - WINDOW_WIDTH) / 2;
        int y = (this.height - WINDOW_HEIGHT) / 2;
        RenderSystem.enableBlend();

        drawMaintTextures(context, x, y, mouseX, mouseY);

        Entity cameraEntity = this.client.getCameraEntity();
        if(cameraEntity != null) {
            Vec3d pos = Objects.requireNonNull(MinecraftClient.getInstance().getCameraEntity()).getPos();
            if (cameraEntity instanceof AbstractClientPlayerEntity abstractClientPlayerEntity) {
                playerCoordinate = new Vec3d(abstractClientPlayerEntity.getPos().x, abstractClientPlayerEntity.getPos().y, abstractClientPlayerEntity.getPos().z);
                Vec2f mapPlayerPos = getCoordinateOnMap((float)playerCoordinate.x, (float)playerCoordinate.z, 4,4);

                // Debug panels
                context.drawTextWithShadow(textRenderer, Text.literal("World Player Coordinates : " + (int)playerCoordinate.x + ", " + (int)playerCoordinate.y+ ", " + (int)playerCoordinate.z), 0, 5, 0xffffff);
                context.drawTextWithShadow(textRenderer, Text.literal("Map Player Coordinates : " + (int)mapPlayerPos.x + ", " + (int)mapPlayerPos.y), 0, 15, 0xffffff);

                //context.drawTextWithShadow(textRenderer, Text.literal("Mouse.coord: " + ((int)mouseX - x) * currentZoom / MIN_ZOOM + "," + ((int)mouseY - y) * currentZoom / MIN_ZOOM), 0, 25, 0xffffff);
                context.drawTextWithShadow(textRenderer, Text.literal("Mouse in Window : " + (int)mouseX + "," + (int)mouseY), 0, 35, 0xffffff);
                context.drawTextWithShadow(textRenderer, Text.literal("Mouse in Map : " + ((int)mouseX - x - MARGIN) + "," +  ((int)mouseY - y - MARGIN)), 0, 45, 0xffffff);

                context.drawTextWithShadow(textRenderer, Text.literal("Zoom Level : " + zoomLevel ), 0, 60, 0xFFBF00);
                cursorWorldCoordinate = getWorldCoordinateOfCursor(mouseX, mouseY);
                context.drawTextWithShadow(textRenderer, Text.literal("Cursor World Coordinates : " + cursorWorldCoordinate.x + ", " + cursorWorldCoordinate.y), 0, 70, 0xFFBF00);

                context.drawTextWithShadow(textRenderer, Text.literal("Map Displacement X : " + mapDisplacementX), 0, 85, 0xffffff);
                context.drawTextWithShadow(textRenderer, Text.literal("Map Displacement Y : " + mapDisplacementY), 0, 95, 0xffffff);

                //context.drawTextWithShadow(textRenderer, Text.literal("Current Map Center : " + (int)centerCoordinate.x + ", " + (int)centerCoordinate.y), 0, 110, 0xffffff);

                context.drawTexture(abstractClientPlayerEntity.getSkinTexture(),
                        x + MARGIN + (int)mapPlayerPos.x - 4,
                        y + MARGIN + (int)mapPlayerPos.y - 4,
                        8, 8, 8, 8, 64, 64);
            }
        }
    }

    private void drawMaintTextures(DrawContext context, int x, int y, double mouseX, double mouseY) {
        // Border
        context.drawTexture(WINDOW_TEXTURE, x, y, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT,
                WINDOW_WIDTH, WINDOW_HEIGHT);
        // Map
        context.drawTexture(MAP_TEXTURE, x + MARGIN, y + MARGIN,
                // UV (x,y)
                mapDisplacementX, mapDisplacementY,
                MAP_WINDOW_WIDTH,
                MAP_WINDOW_HEIGHT,
                (int) (MAP_WIDTH * zoomLevel * MIN_ZOOM),
                (int) (MAP_HEIGHT * zoomLevel * MIN_ZOOM));

        // Map UI

        // Zoom
        int offset = 18 + 3;
        int signsOffsetX = x + WINDOW_WIDTH + 2;
        int signsOffsetY = y + WINDOW_HEIGHT - (offset * 3);

        // Center on player
        int centerOnPlayerTextureOffset = (centerOnPlayerButtonHover > 0) ? 18 : 0;
        centerOnPlayerButtonHover --;
        if(mouseX > signsOffsetX && mouseX < signsOffsetX + 18 && mouseY > signsOffsetY && mouseY < signsOffsetY + 18){
            centerOnPlayerButtonHover = 3;
        }
        if(zoomLevel == 0){
            centerOnPlayerTextureOffset = 36;
        }
        context.drawTexture(MAP_UI_TEXTURE,
                signsOffsetX,
                signsOffsetY,
                centerOnPlayerTextureOffset, 36, 18, 18, 256, 256);


        // Zoom +
        int zoomTextureOffset = (zoomButtonHover > 0) ? 18 : 0;
        zoomButtonHover --;
        if(mouseX > signsOffsetX && mouseX < signsOffsetX + 18 && mouseY > signsOffsetY + offset && mouseY < signsOffsetY + 18 + offset){
            zoomButtonHover = 3;
        }
        if(zoomLevel ==  MAX_ZOOM_LEVEL - 1){
            zoomTextureOffset = 36;
        }
        context.drawTexture(MAP_UI_TEXTURE,
                signsOffsetX,
                signsOffsetY + offset,
                zoomTextureOffset, 0, 18, 18, 256, 256);

        // Zoom -
        int dezoomTextureOffset = (dezoomButtonHover > 0) ? 18 : 0;
        dezoomButtonHover --;
        if(mouseX > signsOffsetX && mouseX < signsOffsetX + 18 && mouseY > signsOffsetY + (offset * 2) && mouseY < signsOffsetY + 18 + (offset * 2)){
            dezoomButtonHover = 3;
        }
        if(zoomLevel == 0){
            dezoomTextureOffset = 36;
        }
        context.drawTexture(MAP_UI_TEXTURE,
                signsOffsetX,
                signsOffsetY + (offset * 2),
                dezoomTextureOffset, 18, 18, 18, 256, 256);

        ((ButtonWidget)children().get(centerOnPlayerButgtonIndex)).active = (zoomLevel > 0);
        ((ButtonWidget)children().get(zoomButtonIndex)).active = (zoomLevel < MAX_ZOOM_LEVEL - 1);
        ((ButtonWidget)children().get(dezoomButtonIndex)).active = (zoomLevel > 0);


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
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        if(!cursorIsOutsideOfMapBounds(mouseX, mouseY)){
            cursorWorldCoordinate = getWorldCoordinateOfCursor(mouseX, mouseY);
            zoom((int)Math.round(amount), true);
        }
        return super.mouseScrolled(mouseX, mouseY, amount);
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

        float modifier = zoomLevel - 1;
        // Maximum (dynamic)
        // When zoomLevelIndex is 0, maximum should be 0,0
        mapDisplacementX = (int)Math.min((MAP_WINDOW_WIDTH) * modifier, mapDisplacementX);
        mapDisplacementY = (int) Math.min((MAP_WINDOW_HEIGHT) * modifier, mapDisplacementY);
    }

    private Vec2f getCoordinateOnMap(float posX, float posZ, int textureOffsetX, int textureOffsetY) {
        float transformedPosX = ((posX / WORLD_SIZE.x) * MAP_WINDOW_WIDTH * zoomLevel) - mapDisplacementX;
        float transformedPosY = ((posZ / WORLD_SIZE.y) * MAP_WINDOW_HEIGHT * zoomLevel) - mapDisplacementY;

        transformedPosX = Math.max(textureOffsetX, Math.min(MAP_WINDOW_WIDTH - textureOffsetX, transformedPosX));
        transformedPosY = Math.max(textureOffsetY, Math.min(MAP_WINDOW_HEIGHT - textureOffsetY, transformedPosY));

        return new Vec2f(transformedPosX, transformedPosY);
    }

    private boolean cursorIsOutsideOfMapBounds(double mouseX, double mouseY){
        return !(mouseX >= 275 && mouseX <= 685 && mouseY >= 75 && mouseY <= 430);
    }

    private void centerOnCoordinates(double x, double y){
        double transformedCoordinatesX = ((x / WORLD_SIZE.x) * MAP_WINDOW_WIDTH * zoomLevel);
        double transformedCoordinatesY = ((y / WORLD_SIZE.y) * MAP_WINDOW_HEIGHT * zoomLevel);

        centerMapTo((int)transformedCoordinatesX, (int)transformedCoordinatesY);
    }

    private Vector2i getCenterOfCurrentMap(){
        int centerX = (int)((mapDisplacementX + (MAP_WINDOW_WIDTH / 2)) / ((float)zoomLevel * MIN_ZOOM));
        int centerY =  (int)((mapDisplacementY + (MAP_WINDOW_HEIGHT / 2)) / ((float)zoomLevel * MIN_ZOOM));

        return new Vector2i(centerX, centerY);
    }

    private Vector2i getWorldCoordinateOfCursor(double mouseX, double mouseY) {
        mouseX -= (double) (this.width - WINDOW_WIDTH) / 2;
        mouseY -= (double) (this.height - WINDOW_HEIGHT) / 2;

        int centerX = (int)((mapDisplacementX + mouseX) / ((float)zoomLevel * MIN_ZOOM));
        int centerY =  (int)((mapDisplacementY + mouseY) / ((float)zoomLevel * MIN_ZOOM));

        centerX= (int)(WORLD_SIZE.x / MAP_WIDTH * centerX);
        centerY = (int)(WORLD_SIZE.y / MAP_HEIGHT * centerY);

        return new Vector2i(centerX, centerY);
    }


    private Vector2i getCoordinateInCenterOfMap(){
        Vector2i currentCenter = getCenterOfCurrentMap();

        currentCenter.x = (int)(WORLD_SIZE.x / MAP_WIDTH * currentCenter.x);
        currentCenter.y = (int)(WORLD_SIZE.y / MAP_HEIGHT * currentCenter.y);

        return currentCenter;
    }

    private void centerMapTo(int x, int y){
        mapDisplacementX = x - (MAP_WINDOW_WIDTH / 2);
        mapDisplacementY = y - (MAP_WINDOW_HEIGHT / 2);;

        correctMapVision();
    }

    private static Vector2i getWorldSize(){
        float worldSize = (float) Math.pow(2 , MiddleEarth.MAP_ITERATION);

        return new Vector2i((int)(MAP_WIDTH * worldSize), (int)(MAP_HEIGHT * worldSize));
    }
}
