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
    private static final int MAX_ZOOM_INDEX = 10;

    private static int mapDisplacementX = 0;
    private static int mapDisplacementY = 0;

    private static int zoomLevelIndex = 0;
    private static float currentZoom = 1f;

    private static int zoomButtonIndex;
    private float zoomButtonHover = 0f;
    private static int dezoomButtonIndex;
    private float dezoomButtonHover = 0f;
    private static int centerOnPlayerButgtonIndex;
    private float centerOnPlayerButtonHover = 0f;

    private Vec3d playerCoordinate;

    public MiddleEarthMapScreen() {
        super(MAP_TITLE_TEXT);
    }

    private static float[] zoomModifiers;

    @Override
    protected void init() {
        zoomModifiers = new float[MAX_ZOOM_INDEX];
        float newValue = 0f;
        float power = 1.05f;
        float decimalNb = 3;
        for(int i = 0; i < MAX_ZOOM_INDEX; i++) {
            newValue += ((float)Math.round(Math.round(Math.pow(power, -i) * Math.pow(10, decimalNb)))
                    / (float)Math.pow(10, decimalNb));
            zoomModifiers[i] = newValue;
        }

        int x = (this.width - WINDOW_WIDTH) / 2;
        int y = (this.height - WINDOW_HEIGHT) / 2;

        int offset = 18 + 3;
        int signsOffsetX = x + WINDOW_WIDTH + 2;
        int signsOffsetY = y + WINDOW_HEIGHT - (offset * 3);


        ButtonWidget centerOnPlayer = ButtonWidget.builder(Text.literal("Center on Player"), button -> {
                    centerOnPlayer();
                })
                .dimensions(signsOffsetX, signsOffsetY, 18, 18).build();

        ButtonWidget zoomButton = ButtonWidget.builder(Text.literal("Zoom"), button -> {
                    zoom(1);
                })
                .dimensions(signsOffsetX, signsOffsetY + offset, 18, 18).build();
        ButtonWidget dezoomButton = ButtonWidget.builder(Text.literal("Dezoom"), button -> {
                    zoom(-1);
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

                context.drawTextWithShadow(textRenderer, Text.literal("Zoom Level : " + zoomLevelIndex ), 0, 60, 0xFFBF00);
                context.drawTextWithShadow(textRenderer, Text.literal("Zoom Value : " + currentZoom ), 0, 70, 0xffffff);

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
                (int) (MAP_WIDTH * currentZoom * MIN_ZOOM),
                (int) (MAP_HEIGHT * currentZoom * MIN_ZOOM));

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
        if(zoomLevelIndex == 0){
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
        if(zoomLevelIndex ==  MAX_ZOOM_INDEX - 1){
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
        if(zoomLevelIndex == 0){
            dezoomTextureOffset = 36;
        }
        context.drawTexture(MAP_UI_TEXTURE,
                signsOffsetX,
                signsOffsetY + (offset * 2),
                dezoomTextureOffset, 18, 18, 18, 256, 256);

        ((ButtonWidget)children().get(centerOnPlayerButgtonIndex)).active = (zoomLevelIndex > 0);
        ((ButtonWidget)children().get(zoomButtonIndex)).active = (zoomLevelIndex < MAX_ZOOM_INDEX - 1);
        ((ButtonWidget)children().get(dezoomButtonIndex)).active = (zoomLevelIndex > 0);


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
        // Check if you're outside the window
        if(cursorIsOutsideOfMapBounds(mouseX, mouseY))
            return super.mouseScrolled(mouseX, mouseY, amount);
        zoom((int)Math.round(amount));
        return super.mouseScrolled(mouseX, mouseY, amount);
    }

    private void zoom(int amount){
        int newZoomLevelIndex = (int)Math.min(MAX_ZOOM_INDEX - 1, Math.max(0, (zoomLevelIndex + amount)));
        if(newZoomLevelIndex != zoomLevelIndex) {
            zoomLevelIndex = newZoomLevelIndex;
            float previousZoom = currentZoom;
            currentZoom = zoomModifiers[zoomLevelIndex];

            float zoomDifference = (currentZoom - previousZoom) / 2;

            Vec2f center = getCenterOfCurrentMap();

            int newDeltaX = mapDisplacementX + (int)(MAP_WINDOW_WIDTH * zoomDifference);
            int newDeltaY = mapDisplacementY + (int)(MAP_WINDOW_HEIGHT * zoomDifference);

            mapDisplacementX = mapDisplacementX + (int)(((MAP_WINDOW_WIDTH * currentZoom) - (MAP_WINDOW_WIDTH * previousZoom)) / 2);
            mapDisplacementY = mapDisplacementY + (int)(((MAP_WINDOW_HEIGHT * currentZoom) - (MAP_WINDOW_HEIGHT * previousZoom)) / 2);

            System.out.println("\nCenter: " + center.x + ", " + center.y + "\nDelta X:" + newDeltaX + "\nDelta Y:" + newDeltaY);
            // Recenter

            //newMapDisplacementX -= (MAP_WINDOW_WIDTH / 2 - mapDisplacementX) - newMapDisplacementX;
            //newMapDisplacementY -= (MAP_WINDOW_HEIGHT / 2 - mapDisplacementY) - newMapDisplacementY;
            //centerMapTo(2000,2000);

            correctMapVision();
        }
    }

    private void correctMapVision() {
        // Minimum (0)
        mapDisplacementX = Math.max(0, mapDisplacementX);
        mapDisplacementY = Math.max(0, mapDisplacementY);

        float modifier = currentZoom - 1;
        // Maximum (dynamic)
        // When zoomLevelIndex is 0, maximum should be 0,0
        mapDisplacementX = (int)Math.min((MAP_WINDOW_WIDTH) * modifier, mapDisplacementX);
        mapDisplacementY = (int) Math.min((MAP_WINDOW_HEIGHT) * modifier, mapDisplacementY);
    }

    private Vec2f getCoordinateOnMap(float posX, float posZ, int textureOffsetX, int textureOffsetY) {
        float worldSize = (float) Math.pow(2 , MapImageLoader.iterations);
        float worldSizeX = MAP_WIDTH * worldSize;
        float worldSizeY = MAP_HEIGHT * worldSize;

        float transformedPosX = ((posX / worldSizeX) * MAP_WINDOW_WIDTH * currentZoom) - mapDisplacementX;
        float transformedPosY = ((posZ / worldSizeY) * MAP_WINDOW_HEIGHT * currentZoom) - mapDisplacementY;

        transformedPosX = Math.max(textureOffsetX, Math.min(MAP_WINDOW_WIDTH - textureOffsetX, transformedPosX));
        transformedPosY = Math.max(textureOffsetY, Math.min(MAP_WINDOW_HEIGHT - textureOffsetY, transformedPosY));

        return new Vec2f(transformedPosX, transformedPosY);
    }

    private boolean cursorIsOutsideOfMapBounds(double mouseX, double mouseY){
        return !(mouseX >= 275 && mouseX <= 685 && mouseY >= 75 && mouseY <= 430);
    }

    private void centerOnPlayer(){
        float worldSize = (float) Math.pow(2 , MapImageLoader.iterations);

        float worldSizeX = MAP_WIDTH * worldSize;
        float worldSizeY = MAP_HEIGHT * worldSize;

        float transformedPlayerCoordinateX = (((float)playerCoordinate.x / worldSizeX) * MAP_WINDOW_WIDTH * currentZoom);
        float transformedPlayerCoordinateY = (((float)playerCoordinate.z / worldSizeY) * MAP_WINDOW_HEIGHT * currentZoom);

        centerMapTo((int)transformedPlayerCoordinateX, (int)transformedPlayerCoordinateY);
    }

    private Vec2f getCenterOfCurrentMap(){
        float centerX = (mapDisplacementX * MAP_WINDOW_WIDTH * currentZoom);
        float centerY = (mapDisplacementY * MAP_WINDOW_HEIGHT * currentZoom);

        return new Vec2f(centerX, centerY);
    }

    private void centerMapTo(int x, int y){
        mapDisplacementX = x - (MAP_WINDOW_WIDTH / 2);
        mapDisplacementY = y - (MAP_WINDOW_HEIGHT / 2);;

        correctMapVision();
    }
}
