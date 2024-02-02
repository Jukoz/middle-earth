package net.jukoz.me.client.renderer;

import net.jukoz.me.client.model.equipment.RohanScaleHelmetArmorModel;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;

public class RohanHelmetFeatureRenderer extends FeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {
    private final RohanScaleHelmetArmorModel<LivingEntity> helmetModel;

    public RohanHelmetFeatureRenderer(FeatureRendererContext<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> context,
                                      EntityModelLoader loader) {
        super(context);

        helmetModel = new RohanScaleHelmetArmorModel<>(RohanScaleHelmetArmorModel.getTexturedModelData().createModel());
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        this.helmetModel.setAngles(entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);
    }
}
