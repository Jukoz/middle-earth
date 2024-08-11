package net.jukoz.me.entity.beasts.warg.features;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.beasts.warg.WargEntity;
import net.jukoz.me.entity.beasts.warg.WargModel;
import net.jukoz.me.entity.model.ModEntityModelLayers;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class WargSaddleFeatureRenderer extends FeatureRenderer<WargEntity, WargModel> {
    private final WargSaddleModel model;

    public WargSaddleFeatureRenderer(FeatureRendererContext<WargEntity, WargModel> context, EntityModelLoader loader) {
        super(context);
        this.model = new WargSaddleModel(loader.getModelPart(ModEntityModelLayers.WARG_SADDLE));
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, WargEntity wargEntity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        if (!wargEntity.isSaddled()) {
            return;
        }

        this.model.setAngles(wargEntity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);

        ((WargModel)this.getContextModel()).copyStateTo(this.model);

        Identifier saddleTexture = Identifier.of(MiddleEarth.MOD_ID, "textures/entities/warg/feature/warg_saddle.png");

        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutoutNoCull(saddleTexture));
        this.model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, -1);
    }
}