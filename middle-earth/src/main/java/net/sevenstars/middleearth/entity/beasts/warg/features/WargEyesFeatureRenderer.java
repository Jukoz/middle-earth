package net.sevenstars.middleearth.entity.beasts.warg.features;

import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.beasts.warg.WargEntity;
import net.sevenstars.middleearth.entity.beasts.warg.WargEntityRenderState;
import net.sevenstars.middleearth.entity.beasts.warg.WargModel;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.feature.EyesFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.util.Identifier;

public class WargEyesFeatureRenderer extends EyesFeatureRenderer<WargEntityRenderState, WargModel> {
    private static final RenderLayer SKIN = RenderLayer.getEyes(Identifier.of(MiddleEarth.MOD_ID, "textures/entities/warg/feature/warg_eyes.png"));
    public WargEyesFeatureRenderer(FeatureRendererContext<WargEntityRenderState, WargModel> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public RenderLayer getEyesTexture() {
        return SKIN;
    }
}
