package net.sevenstars.middleearth.entity.beasts.cave_troll.feature;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.sevenstars.middleearth.entity.beasts.cave_troll.CaveTrollEntityModel;
import net.sevenstars.middleearth.entity.beasts.cave_troll.CaveTrollEntity;

public class CaveTrollHeldItemFeatureRenderer extends RenderLayer<CaveTrollEntity, CaveTrollEntityModel> {
    private final ItemRenderer itemRenderer;

    public CaveTrollHeldItemFeatureRenderer(
            RenderLayerParent<CaveTrollEntity, CaveTrollEntityModel> context, ItemRenderer itemRenderer) {
        super(context);
        this.itemRenderer = itemRenderer;
    }

    @Override
    public void render(PoseStack matrices, MultiBufferSource vertexConsumers, int light, CaveTrollEntity entity,
                       float limbAngle, float limbDistance, float tickDelta, float animationProgress,
                       float headYaw, float headPitch) {
        if (entity.getMainHandItem().isEmpty()) {
            return;
        }
        matrices.pushPose();
        this.getParentModel().setArmAngle(matrices);
        matrices.mulPose(Axis.XP.rotationDegrees(-90.0F));
        matrices.mulPose(Axis.YP.rotationDegrees(180.0F));
        matrices.translate(0.3f, 0, -2.6);
        matrices.scale(1.5f,1.5f,1.5f);

        this.itemRenderer.renderStatic(entity.getMainHandItem(), ItemDisplayContext.THIRD_PERSON_RIGHT_HAND,
                light, OverlayTexture.NO_OVERLAY, matrices, vertexConsumers, entity.level(), entity.getId());
        matrices.popPose();
    }

}
