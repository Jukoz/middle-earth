package net.sevenstars.middleearth.entity.wight;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.client.RenderUtil;

public class BarrowWightEyesFeatureRenderer extends FeatureRenderer<BarrowWightRenderState, BarrowWightModel> {
    private static final String PATH = "textures/entities/barrow_wights/";
    private static final Identifier EMISSIVE_TEXTURE = Identifier.of(MiddleEarth.MOD_ID, PATH + "barrow_wight_eyes_emissive.png");

    public BarrowWightEyesFeatureRenderer(FeatureRendererContext<BarrowWightRenderState, BarrowWightModel> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, BarrowWightRenderState state, float limbAngle, float limbDistance) {
        RenderUtil.renderEmissiveTexture(this.getContextModel(), matrices, vertexConsumers,
                EMISSIVE_TEXTURE, light, OverlayTexture.DEFAULT_UV);
    }
}
