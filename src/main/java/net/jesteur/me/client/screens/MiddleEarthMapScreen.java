package net.jesteur.me.client.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import net.jesteur.me.MiddleEarth;
import net.jesteur.me.world.chunkgen.map.MapImageLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;

import java.text.DecimalFormat;
import java.util.Objects;

public class MiddleEarthMapScreen extends Screen {
    private static final Identifier WINDOW_TEXTURE = new Identifier(MiddleEarth.MOD_ID,"textures/gui/map_background.png");
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
    private static final int MAX_ZOOM_INDEX = 13;

    private static int mapDisplacementX = 0;
    private static int mapDisplacementY = 0;

    private static int zoomIndex = 0;
    private static float currentZoom = 1f;
    public MiddleEarthMapScreen() {
        super(MAP_TITLE_TEXT);
    }

    private static float[] zoomModifiers;

    @Override
    protected void init() {
        zoomModifiers = new float[MAX_ZOOM_INDEX];
        float newValue = 0f;
        float power = 1.07f;
        float zoomDecimalModifier = (float)Math.pow(10, 5);
        for(int i = 0; i < MAX_ZOOM_INDEX; i++) {
            newValue += Math.round(Math.pow(power, -i) * zoomDecimalModifier) / zoomDecimalModifier;
            zoomModifiers[i] = newValue;
        }
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

        Entity cameraEntity = this.client.getCameraEntity();
        if(cameraEntity != null) {
            Vec3d pos = Objects.requireNonNull(MinecraftClient.getInstance().getCameraEntity()).getPos();
            if (cameraEntity instanceof AbstractClientPlayerEntity abstractClientPlayerEntity) {
                Vec3d playerCoordinate = new Vec3d(abstractClientPlayerEntity.getPos().x, abstractClientPlayerEntity.getPos().y, abstractClientPlayerEntity.getPos().z);
                Vec2f mapPlayerPos = getCoordinateOnMap((float)playerCoordinate.x, (float)playerCoordinate.z, 4,4);

                // Debug panels
                context.drawTextWithShadow(textRenderer, Text.literal("World Player Coordinates : " + (int)playerCoordinate.x + ", " + (int)playerCoordinate.y+ ", " + (int)playerCoordinate.z), 0, 5, 0xffffff);
                context.drawTextWithShadow(textRenderer, Text.literal("Map Player Coordinates : " + (int)mapPlayerPos.x + ", " + (int)mapPlayerPos.y), 0, 15, 0xffffff);
                context.drawTextWithShadow(textRenderer, Text.literal("Mouse : " + (int)mouseX + "," + (int)mouseY), 0, 25, 0xffffff);
                context.drawTextWithShadow(textRenderer, Text.literal("Zoom  : " + currentZoom ), 0, 35, 0xffffff);
                context.drawTextWithShadow(textRenderer, Text.literal("Zoom Index : " + zoomIndex ), 0, 45, 0xFFBF00);

                context.drawTextWithShadow(textRenderer, Text.literal("Map Displacement X : " + mapDisplacementX), 0, 60, 0xffffff);
                context.drawTextWithShadow(textRenderer, Text.literal("Map Displacement Y : " + mapDisplacementY), 0, 70, 0xffffff);

                context.drawTexture(abstractClientPlayerEntity.getSkinTexture(),
                        x + MARGIN + (int)mapPlayerPos.x - 4,
                        y + MARGIN + (int)mapPlayerPos.y - 4,
                        8, 8, 8, 8, 64, 64);
            }
        }
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (button != 0) {
            return false;
        }
        mapDisplacementX -= (float) deltaX;
        mapDisplacementY -= (float) deltaY;

        correctMapVision();
        return true;
    }


    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        // Check if you're outside the window
        if(mouseX < 275 || mouseX > 685 && mouseY < 75 || mouseY > 430)
            return super.mouseScrolled(mouseX, mouseY, amount);
        int newZoomIndex = (int)Math.min(MAX_ZOOM_INDEX - 1, Math.max(0, (zoomIndex + amount)));
        if(newZoomIndex != zoomIndex) {
            zoomIndex = newZoomIndex;
            currentZoom = zoomModifiers[zoomIndex];
            correctMapVision();
        }
        return super.mouseScrolled(mouseX, mouseY, amount);
    }

    private void correctMapVision() {
        // Minimum (0)
        mapDisplacementX = Math.max(0, mapDisplacementX);
        mapDisplacementY = Math.max(0, mapDisplacementY);

        float modifier = currentZoom - 1;
        // Maximum (dynamic)
        // When zoomIndex is 0, maximum should be 0,0
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
}
