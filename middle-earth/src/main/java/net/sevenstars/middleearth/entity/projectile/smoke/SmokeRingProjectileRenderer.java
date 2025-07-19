package net.sevenstars.middleearth.entity.projectile.smoke;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.sevenstars.middleearth.MiddleEarth;
import org.joml.Matrix4f;
import net.minecraft.client.render.RenderLayer;
import org.joml.Quaternionf;

/**
 * Client-side renderer for {@link SmokeRingProjectileEntity}.
 * Renders an animated flat quad using sprite frames from a custom atlas.
 */
@Environment(EnvType.CLIENT)
public class SmokeRingProjectileRenderer extends EntityRenderer<SmokeRingProjectileEntity, SmokeRingProjectileRenderState> {
    // Array of frames used for smoke ring animation
    private final Sprite[] frames;

    // Location of the custom atlas and sprite prefix
    private static final Identifier SPRITES_ATLAS_ID = Identifier.of(MiddleEarth.MOD_ID, "sprites");
    private static final String SPRITE_PATH_PREFIX = "sprites/smoke_ring/big_smoke_ring_";
    private static final int FRAME_COUNT = 12;

    public SmokeRingProjectileRenderer(EntityRendererFactory.Context context) {
        super(context);
        frames = loadFrames();
    }

    @Override
    public void render(
            SmokeRingProjectileRenderState state,
            MatrixStack matrices,
            VertexConsumerProvider vertexConsumers,
            int light) {
        matrices.push();
        matrices.translate(0, 0.2, 0);
        matrices.multiply(state.orientationQuat);

        // Select animation frame based on age, scaled to lifespan (no looping)
        int totalFrames = frames.length;
        int frame = Math.min((int) (state.age / state.maxLifespan * totalFrames), totalFrames - 1);
        Sprite sprite = frames[frame];


        Matrix4f matrix = matrices.peek().getPositionMatrix();
        VertexConsumer vc = vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(sprite.getAtlasId()));

        float minU = sprite.getMinU();
        float maxU = sprite.getMaxU();
        float minV = sprite.getMinV();
        float maxV = sprite.getMaxV();

        int overlay = net.minecraft.client.render.OverlayTexture.DEFAULT_UV;

        float size = 1.0f;
        drawQuad(vc, matrix, size, minU, maxU, minV, maxV, light, overlay);

        matrices.pop();
    }

    @Override
    public SmokeRingProjectileRenderState createRenderState() {
        SmokeRingProjectileRenderState state = new SmokeRingProjectileRenderState();
        state.maxLifespan = SmokeRingProjectileEntity.MAX_LIFESPAN_TICKS;

        return state;
    }

    @Override
    public void updateRenderState(
            SmokeRingProjectileEntity entity,
            SmokeRingProjectileRenderState state,
            float tickDelta) {
        super.updateRenderState(entity, state, tickDelta);

        this.updateOrientationQuaternion(entity, state, tickDelta);
    }

    private Sprite[] loadFrames() {
        SpriteAtlasTexture atlas = (SpriteAtlasTexture) MinecraftClient.getInstance().getTextureManager().getTexture(
                SPRITES_ATLAS_ID);

        Sprite[] sprites = new Sprite[FRAME_COUNT];
        for (int i = 0; i < FRAME_COUNT; i++) {
            Identifier spriteId = Identifier.of(MiddleEarth.MOD_ID, SPRITE_PATH_PREFIX + i);
            sprites[i] = atlas.getSprite(spriteId);
        }
        return sprites;
    }

    private void drawQuad(
            VertexConsumer vc,
            Matrix4f matrix,
            float size,
            float minU,
            float maxU,
            float minV,
            float maxV,
            int light,
            int overlay) {
        float half = size / 2f;
        vc.vertex(matrix, -half, -half, 0).color(255, 255, 255, 255).texture(minU, minV).overlay(
                overlay).light(light).normal(0, 0, 1);
        vc.vertex(matrix, -half, +half, 0).color(255, 255, 255, 255).texture(minU, maxV).overlay(
                overlay).light(light).normal(0, 0, 1);
        vc.vertex(matrix, +half, +half, 0).color(255, 255, 255, 255).texture(maxU, maxV).overlay(
                overlay).light(light).normal(0, 0, 1);
        vc.vertex(matrix, +half, -half, 0).color(255, 255, 255, 255).texture(maxU, minV).overlay(
                overlay).light(light).normal(0, 0, 1);
    }

    private void updateOrientationQuaternion(
            SmokeRingProjectileEntity entity,
            SmokeRingProjectileRenderState state,
            float tickDelta) {
        float yawRad = (float) Math.toRadians(-MathHelper.lerp(tickDelta,
                entity.lastYaw,
                entity.getYaw()));
        float pitchRad = (float) Math.toRadians(MathHelper.lerp(tickDelta,
                entity.lastPitch,
                entity.getPitch()));
        state.orientationQuat = new Quaternionf().rotateYXZ(yawRad, pitchRad, 0f);
    }
}

