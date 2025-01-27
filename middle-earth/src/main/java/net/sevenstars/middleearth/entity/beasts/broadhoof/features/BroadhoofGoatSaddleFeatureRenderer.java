package net.sevenstars.middleearth.entity.beasts.broadhoof.features;

import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.beasts.broadhoof.BroadhoofGoatEntity;
import net.sevenstars.middleearth.entity.beasts.broadhoof.BroadhoofGoatModel;
import net.sevenstars.middleearth.entity.model.ModEntityModelLayers;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class BroadhoofGoatSaddleFeatureRenderer extends FeatureRenderer<BroadhoofGoatEntity, BroadhoofGoatModel> {
    private final BroadhoofGoatSaddleModel model;

    public BroadhoofGoatSaddleFeatureRenderer(FeatureRendererContext<BroadhoofGoatEntity, BroadhoofGoatModel> context, EntityModelLoader loader) {
        super(context);
        this.model = new BroadhoofGoatSaddleModel(loader.getModelPart(ModEntityModelLayers.BROADHOOF_GOAT_SADDLE));
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, BroadhoofGoatEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        if (!entity.isSaddled()) {
            return;
        }

        this.model.setAngles(entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);

        ((BroadhoofGoatModel)this.getContextModel()).copyStateTo(this.model);

        Identifier saddleTexture = Identifier.of(MiddleEarth.MOD_ID, "textures/entities/broadhoof_goat/feature/broadhoof_goat_saddle.png");

        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutoutNoCull(saddleTexture));
        this.model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, -1);
    }
}
