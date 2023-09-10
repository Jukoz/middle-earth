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
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.biome.Biome;
import org.joml.Vector3f;

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

    public static final float MIN_ZOOM = (float) WINDOW_WIDTH / (MAP_WIDTH + (MARGIN * 2));
    public static final float MAX_ZOOM = 3.0f;
    public static final float ZOOMING_POWER = 0.067f;

    private GameProfile gameProfile;
    private float zoomScale = 1f;
    private float mapDisplacementX, mapDisplacementY;


    public MiddleEarthMapScreen(GameProfile gameProfile) {
        super(MAP_TITLE_TEXT);
        this.gameProfile = gameProfile;
    }

    @Override
    protected void init() {
        this.mapDisplacementX = 0f;
        this.mapDisplacementY = 0f;
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
        context.drawTexture(WINDOW_TEXTURE, x, y, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT, WINDOW_WIDTH, WINDOW_HEIGHT);


        context.drawTexture(MAP_TEXTURE, x + MARGIN, y + MARGIN, mapDisplacementX, mapDisplacementY,
                WINDOW_WIDTH - (2 * MARGIN), WINDOW_HEIGHT - (2 * MARGIN), (int) (MAP_WIDTH * zoomScale), (int) (MAP_HEIGHT * zoomScale));

        Entity cameraEntity = this.client.getCameraEntity();
        if(cameraEntity != null) {
            Vec3d pos = Objects.requireNonNull(MinecraftClient.getInstance().getCameraEntity()).getPos();
            if (cameraEntity instanceof AbstractClientPlayerEntity abstractClientPlayerEntity) {
                Vec3d playerCoordinate = new Vec3d(abstractClientPlayerEntity.getPos().x, abstractClientPlayerEntity.getPos().y, abstractClientPlayerEntity.getPos().z);
                Vec2f mapPlayerPos = getCoordinateOnMap((float)playerCoordinate.x, (float)playerCoordinate.z, x, y, abstractClientPlayerEntity);

                context.drawTextWithShadow(textRenderer, Text.literal("World Coordinates : " + (int)playerCoordinate.x + "," + (int)playerCoordinate.y+ "," + (int)playerCoordinate.z), 0, 5, 0xffffff);
                context.drawTextWithShadow(textRenderer, Text.literal("Mouse : " + (int)mouseX + "," + (int)mouseY), 0, 15, 0xffffff);
                context.drawTextWithShadow(textRenderer, Text.literal("Raw Zoom  : " + zoomScale ), 0, 25, 0xffffff);
                context.drawTextWithShadow(textRenderer, Text.literal("Modified Zoom : " + (zoomScale + 1 - MIN_ZOOM)), 0, 35, 0xffffff);

                context.drawTexture(abstractClientPlayerEntity.getSkinTexture(),
                        ((this.width - WINDOW_WIDTH) / 2) + MARGIN + (int)mapPlayerPos.x - 4,
                        ((this.height - WINDOW_HEIGHT) / 2) + MARGIN + (int)mapPlayerPos.y - 4,
                        8, 8, 8, 8, 64, 64);
            }
        }
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (button != 0) {
            return false;
        }
        this.mapDisplacementX -= (float) deltaX;
        this.mapDisplacementY -= (float) deltaY;

        correctMapVision();
        return true;
    }


    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        float zoomAmount = (float) (amount * ZOOMING_POWER);

        this.zoomScale = (float) Math.max(MIN_ZOOM, Math.min(MAX_ZOOM, zoomScale + zoomAmount));

        float zoomAmountX = zoomScale * MAP_WIDTH;
        float zoomAmountY = zoomScale * MAP_HEIGHT;

        this.mapDisplacementX += zoomAmountX; //(int)(mouseX * zoomAmount + (MAX_ZOOM - zoomAmount) * mouseX);
        this.mapDisplacementY += zoomAmountY; //(int)(mouseY * zoomAmount + (MAX_ZOOM - zoomAmount) * mouseY);

        correctMapVision();
        return super.mouseScrolled(mouseX, mouseY, amount);
    }

    private void correctMapVision() {
        this.mapDisplacementX = Math.max(0, mapDisplacementX);
        this.mapDisplacementY = Math.max(0, mapDisplacementY);

        float distanceScale = zoomScale - MIN_ZOOM;
        this.mapDisplacementX = Math.min((float) MAP_WIDTH * distanceScale, mapDisplacementX);
        this.mapDisplacementY = Math.min((float) MAP_HEIGHT * distanceScale, mapDisplacementY);
    }

    private Vec2f getCoordinateOnMap(float posX, float posZ, float centerX, float centerY, AbstractClientPlayerEntity abstractClientPlayerEntity) {
        float worldSizeX = MAP_WIDTH * (float) Math.pow(2 , MapImageLoader.iterations);
        float worldSizeY = MAP_HEIGHT * (float) Math.pow(2 , MapImageLoader.iterations);

        float newZoom = zoomScale + 1 - MIN_ZOOM; // 1 -> max
        float transformedPosX = (float)WINDOW_WIDTH / worldSizeX * posX * newZoom;
        float transformedPosY = (float)WINDOW_HEIGHT / worldSizeY * posZ * newZoom;

        transformedPosX -= mapDisplacementX;
        transformedPosY -= mapDisplacementY;

        //abstractClientPlayerEntity.sendMessage(Text.literal("Zoom : " + newZoom));

        transformedPosX = Math.max(4, Math.min(WINDOW_WIDTH + 4, transformedPosX));
        transformedPosY = Math.max(4, Math.min(WINDOW_HEIGHT + 4, transformedPosY));

        return new Vec2f(transformedPosX, transformedPosY);
    }
}
