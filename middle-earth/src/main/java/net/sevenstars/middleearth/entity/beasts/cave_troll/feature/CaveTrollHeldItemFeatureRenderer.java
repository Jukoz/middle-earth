package net.sevenstars.middleearth.entity.beasts.cave_troll.feature;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.math.RotationAxis;
import net.sevenstars.middleearth.entity.beasts.cave_troll.CaveTrollEntityModel;
import net.sevenstars.middleearth.entity.beasts.cave_troll.CaveTrollEntityRenderState;

public class CaveTrollHeldItemFeatureRenderer extends FeatureRenderer<CaveTrollEntityRenderState, CaveTrollEntityModel> {
    public CaveTrollHeldItemFeatureRenderer(FeatureRendererContext context) {
        super(context);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CaveTrollEntityRenderState state, float limbAngle, float limbDistance) {
        this.getContextModel().setArmAngle(matrices);
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-90.0F));
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0F));
        matrices.translate(0.1f, 0, -2.6);
        matrices.scale(1.5f,1.5f,1.5f);

        state.handItemState.render(matrices, vertexConsumers, light, OverlayTexture.DEFAULT_UV);
    }

}
