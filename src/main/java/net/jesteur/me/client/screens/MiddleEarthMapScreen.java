package net.jesteur.me.client.screens;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MiddleEarthMapScreen extends Screen {
    private static final Identifier WINDOW_TEXTURE = new Identifier(MiddleEarth.MOD_ID,"textures/gui/map_background.png");
    private static final Identifier MAP_TEXTURE = new Identifier(MiddleEarth.MOD_ID,"textures/map.png");
    private static final Text RETURN_TO_GAME_TEXT = Text.translatable("menu.returnToGame");
    private static final Text MAP_TITLE_TEXT = Text.of("Middle-earth Map");
    public static final int MARGIN = 5;
    public static final int WINDOW_WIDTH = 525; // 1400 / 2.66667
    public static final int WINDOW_HEIGHT = 456; // 1216 / 2.66667
    public static final int MAP_WIDTH = 1400;
    public static final int MAP_HEIGHT = 1216;
    public static final float MAX_ZOOM = 3.0f;
    public static final float MIN_ZOOM = (float) WINDOW_WIDTH / (MAP_WIDTH + (MARGIN * 2));
    public static final float ZOOMING_POWER = 0.067f;

    private GameProfile gameProfile;
    private float zoomScale = 1f;
    private float mapDeltaX = 0;
    private float mapDeltaY = 0;

    public MiddleEarthMapScreen(GameProfile gameProfile) {
        super(MAP_TITLE_TEXT);
        this.gameProfile = gameProfile;
    }

    @Override
    protected void init() {

    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        int i = (this.width - WINDOW_WIDTH) / 2;
        int j = (this.height - WINDOW_HEIGHT) / 2;
        this.renderBackground(context);
        this.drawWindow(context, i, j);
    }

    public void drawWindow(DrawContext context, int x, int y) {
        RenderSystem.enableBlend();
        context.drawTexture(WINDOW_TEXTURE, x, y, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT, WINDOW_WIDTH, WINDOW_HEIGHT);

        context.drawTexture(MAP_TEXTURE, x + MARGIN, y + MARGIN, mapDeltaX, mapDeltaY,
                WINDOW_WIDTH - (2 * MARGIN), WINDOW_HEIGHT - (2 * MARGIN), (int) (MAP_WIDTH * zoomScale), (int) (MAP_HEIGHT * zoomScale));

        Entity cameraEntity = this.client.getCameraEntity();
        if(cameraEntity != null) {
            Vec3d pos = Objects.requireNonNull(MinecraftClient.getInstance().getCameraEntity()).getPos();
            if (cameraEntity instanceof AbstractClientPlayerEntity abstractClientPlayerEntity) {
                Vec3d playerCoordinate = new Vec3d(abstractClientPlayerEntity.getPos().x, abstractClientPlayerEntity.getPos().y, abstractClientPlayerEntity.getPos().z);
                Vec2f mapPlayerPos = getCoordinateOnMap((float)playerCoordinate.x, (float)playerCoordinate.z, x, y, abstractClientPlayerEntity);
                //System.out.println("POS: " + playerPos.x + " . " + playerPos.y);
                context.drawTexture(abstractClientPlayerEntity.getSkinTexture(),
                        ((this.width - WINDOW_WIDTH) / 2) + MARGIN + (int)mapPlayerPos.x,
                        ((this.height - WINDOW_HEIGHT) / 2) + MARGIN + (int)mapPlayerPos.y,
                        8, 8, 8, 8, 64, 64);
            }
        }
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (button != 0) {
            return false;
        }
        this.mapDeltaX -= (float) deltaX;
        this.mapDeltaY -= (float) deltaY;

        correctMapVision();
        return true;
    }


    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        float zoomAmount = (float) (amount * ZOOMING_POWER);
        float distanceDifferenceX = zoomScale * MAP_WIDTH;
        float distanceDifferenceY = zoomScale * MAP_HEIGHT;

        this.zoomScale = (float) Math.max(MIN_ZOOM, Math.min(MAX_ZOOM, zoomScale + zoomAmount));
        distanceDifferenceX -= zoomScale * MAP_WIDTH;
        distanceDifferenceY -= zoomScale * MAP_HEIGHT;

        System.out.println("DIST: " + distanceDifferenceX);
        this.mapDeltaX += distanceDifferenceX; //(int)(mouseX * zoomAmount + (MAX_ZOOM - zoomAmount) * mouseX);
        this.mapDeltaY += distanceDifferenceY; //(int)(mouseY * zoomAmount + (MAX_ZOOM - zoomAmount) * mouseY);

        System.out.println("ZOOM: " + zoomScale);

        correctMapVision();
        return super.mouseScrolled(mouseX, mouseY, amount);
    }

    private void correctMapVision() {
        this.mapDeltaX = Math.max(0, mapDeltaX);
        this.mapDeltaY = Math.max(0, mapDeltaY);

        float distanceScale = zoomScale - MIN_ZOOM;
        this.mapDeltaX = Math.min((float) MAP_WIDTH * distanceScale, mapDeltaX);
        this.mapDeltaY = Math.min((float) MAP_HEIGHT * distanceScale, mapDeltaY);
    }

    private Vec2f getCoordinateOnMap(float posX, float posZ, float centerX, float centerY, AbstractClientPlayerEntity abstractClientPlayerEntity) {
        float worldSizeX = MAP_WIDTH * (float) Math.pow(2 , MapImageLoader.iterations);
        float worldSizeY = MAP_HEIGHT * (float) Math.pow(2 , MapImageLoader.iterations);

        //abstractClientPlayerEntity.sendMessage(Text.literal((long)worldSizeX + ";"+ (long)worldSizeY + " >> " + (int)posX + ";" + (int)posZ));
        float transformedPosX = WINDOW_WIDTH / worldSizeX * posX;
        float transformedPosZ = WINDOW_HEIGHT / worldSizeY * posZ;

        //abstractClientPlayerEntity.sendMessage(Text.literal("Map Coord : " + (int)transformedPosX + "," + (int)transformedPosZ + " >> Coord : " + posX + "," + posZ));
        transformedPosX -= mapDeltaX * zoomScale;
        transformedPosZ -= mapDeltaY * zoomScale;
        abstractClientPlayerEntity.sendMessage(Text.literal("Map Coord : " + (int)transformedPosX + "," + (int)transformedPosZ + " >> offset : " + mapDeltaX * zoomScale + "," + mapDeltaY * zoomScale));


        // posX = Math.max(0, Math.min(WINDOW_WIDTH, posX * WINDOW_WIDTH * zoomScale));
        // posZ = Math.max(0, Math.min(WINDOW_HEIGHT, posZ * WINDOW_HEIGHT * zoomScale));

        return new Vec2f(transformedPosX, transformedPosZ);
    }
}
