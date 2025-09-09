package net.sevenstars.middleearth.entity.beasts.cave_troll.feature;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.EyesFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.beasts.cave_troll.CaveTrollEntityModel;
import net.sevenstars.middleearth.entity.beasts.cave_troll.CaveTrollEntityRenderState;

public class CaveTrollEyesFeatureRenderer extends EyesFeatureRenderer<CaveTrollEntityRenderState, CaveTrollEntityModel> {
    private static final RenderLayer SKIN = RenderLayer.getEyes(Identifier.of(MiddleEarth.MOD_ID, "textures/entities/trolls/cave/cave_troll_red_eyes.png"));
    public CaveTrollEyesFeatureRenderer(FeatureRendererContext<CaveTrollEntityRenderState, CaveTrollEntityModel> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CaveTrollEntityRenderState state, float limbAngle, float limbDistance) {
        if(state.tameness < 33) {
            VertexConsumer vertexConsumer = vertexConsumers.getBuffer(this.getEyesTexture());
            this.getContextModel().render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV);
        }
    }

    @Override
    public RenderLayer getEyesTexture() {
        return SKIN;
    }
}
