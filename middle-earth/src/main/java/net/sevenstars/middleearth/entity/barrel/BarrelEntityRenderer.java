package net.sevenstars.middleearth.entity.barrel;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.EntityModelLayersME;
import org.joml.Quaternionf;

public class BarrelEntityRenderer extends EntityRenderer<BarrelEntity> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "textures/entities/reinforced_barrel/reinforced_barrel.png");
    private final ModelPart modelPart;
    private final ModelPart waterMaskPart;

    public BarrelEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
        modelPart = context.bakeLayer(EntityModelLayersME.REINFORCED_BARREL);
        this.waterMaskPart = context.bakeLayer(EntityModelLayersME.REINFORCED_BARREL_WATER_MASK).getChild("water_patch");
        this.shadowRadius = 0.6F;
    }

    @Override
    public void render(BarrelEntity entity, float yaw, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light) {
        matrices.pushPose();
        matrices.scale(1.35f, 1.35f, 1.35f);
        matrices.mulPose(Axis.YP.rotationDegrees(180.0F - Mth.rotLerp(tickDelta, entity.yRotO, entity.getYRot())));
        matrices.mulPose(Axis.ZP.rotationDegrees(180.0F));

        float f = entity.getHurtTime() - tickDelta;
        if (f > 0.0F) {
            float damage = Math.max(entity.getDamage() - tickDelta, 0.0F);
            matrices.mulPose(Axis.XP.rotationDegrees(Mth.sin(f) * f * damage / 10.0F * entity.getHurtDir()));
        }

        float bubbleAngle = entity.getBubbleAngle(tickDelta);
        if (!entity.isUnderWater() && !Mth.equal(bubbleAngle, 0.0F)) {
            matrices.mulPose((new Quaternionf()).setAngleAxis(bubbleAngle * 0.017453292F, 1.0F, 0.0F, 1.0F));
        }

        matrices.translate(0f, -1.4f, 0f);
        modelPart.render(matrices, vertexConsumers.getBuffer(RenderType.entityCutout(TEXTURE)), light, OverlayTexture.NO_OVERLAY);
        this.renderWaterMask(entity, matrices, vertexConsumers, light);
        matrices.popPose();
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    protected void renderWaterMask(BarrelEntity entity, PoseStack matrices, MultiBufferSource vertexConsumers, int light) {
        if (!entity.isUnderWater()) {
            this.waterMaskPart.render(matrices, vertexConsumers.getBuffer(RenderType.waterMask()), light, OverlayTexture.NO_OVERLAY);
        }
    }

    @Override
    public ResourceLocation getTextureLocation(BarrelEntity entity) {
        return TEXTURE;
    }
}
