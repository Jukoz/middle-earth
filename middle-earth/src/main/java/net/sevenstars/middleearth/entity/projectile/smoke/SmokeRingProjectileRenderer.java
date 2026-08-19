package net.sevenstars.middleearth.entity.projectile.smoke;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.sevenstars.middleearth.MiddleEarth;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

public class SmokeRingProjectileRenderer extends EntityRenderer<SmokeRingProjectileEntity> {
    private final TextureAtlasSprite[] frames;
    private final Quaternionf orientation = new Quaternionf();

    private static final ResourceLocation SPRITES_ATLAS_ID = ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "sprites");
    private static final String SPRITE_PATH_PREFIX = "sprites/smoke_ring/big_smoke_ring_";
    private static final int FRAME_COUNT = 12;
    private static final int FAILED_FIRST_FRAME = 7;
    private static final int FAILED_FRAME_COUNT = 5;
    private static final float SMOKE_RING_SIZE = 1.0f;

    public SmokeRingProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
        frames = loadFrames();
    }

    @Override
    public void render(
            SmokeRingProjectileEntity entity,
            float yaw,
            float tickDelta,
            PoseStack matrices,
            MultiBufferSource vertexConsumers,
            int light) {
        matrices.pushPose();
        matrices.translate(0, 0.2, 0);
        updateOrientationQuaternion(entity, tickDelta);
        matrices.mulPose(this.orientation);

        boolean failed = entity.isFailed();
        int firstFrame = failed ? FAILED_FIRST_FRAME : 0;
        int frameCount = failed ? FAILED_FRAME_COUNT : frames.length;
        float ageInTicks = entity.tickCount + tickDelta;
        int frame = firstFrame + Math.min((int)(ageInTicks / entity.getMaxLifespanTicks() * frameCount), frameCount - 1);
        TextureAtlasSprite sprite = frames[frame];


        Matrix4f matrix = matrices.last().pose();
        VertexConsumer vc = vertexConsumers.getBuffer(RenderType.entityTranslucent(sprite.atlasLocation()));

        float minU = sprite.getU0();
        float maxU = sprite.getU1();
        float minV = sprite.getV0();
        float maxV = sprite.getV1();

        int overlay = net.minecraft.client.renderer.texture.OverlayTexture.NO_OVERLAY;

        drawQuad(vc, matrix, SMOKE_RING_SIZE, minU, maxU, minV, maxV, light, overlay);

        matrices.popPose();
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    private TextureAtlasSprite[] loadFrames() {
        TextureAtlas atlas = (TextureAtlas) Minecraft.getInstance().getTextureManager().getTexture(
                SPRITES_ATLAS_ID);

        TextureAtlasSprite[] sprites = new TextureAtlasSprite[FRAME_COUNT];
        for (int i = 0; i < FRAME_COUNT; i++) {
            ResourceLocation spriteId = ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, SPRITE_PATH_PREFIX + i);
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
        vc.addVertex(matrix, -half, -half, 0).setColor(255, 255, 255, 255).setUv(minU, minV).setOverlay(
                overlay).setLight(light).setNormal(0, 0, 1);
        vc.addVertex(matrix, -half, +half, 0).setColor(255, 255, 255, 255).setUv(minU, maxV).setOverlay(
                overlay).setLight(light).setNormal(0, 0, 1);
        vc.addVertex(matrix, +half, +half, 0).setColor(255, 255, 255, 255).setUv(maxU, maxV).setOverlay(
                overlay).setLight(light).setNormal(0, 0, 1);
        vc.addVertex(matrix, +half, -half, 0).setColor(255, 255, 255, 255).setUv(maxU, minV).setOverlay(
                overlay).setLight(light).setNormal(0, 0, 1);
    }

    private void updateOrientationQuaternion(
            SmokeRingProjectileEntity entity,
            float tickDelta) {
        float yawRad = (float) Math.toRadians(-Mth.lerp(tickDelta,
                entity.yRotO,
                entity.getYRot()));
        float pitchRad = (float) Math.toRadians(Mth.lerp(tickDelta,
                entity.xRotO,
                entity.getXRot()));
        this.orientation.identity().rotateYXZ(yawRad, pitchRad, 0f);
    }

    @Override
    public ResourceLocation getTextureLocation(SmokeRingProjectileEntity entity) {
        return SPRITES_ATLAS_ID;
    }
}

