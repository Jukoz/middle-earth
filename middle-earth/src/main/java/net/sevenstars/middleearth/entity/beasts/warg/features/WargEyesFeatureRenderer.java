package net.sevenstars.middleearth.entity.beasts.warg.features;

import com.google.common.collect.Maps;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.client.RenderUtil;
import net.sevenstars.middleearth.entity.beasts.warg.WargEyeVariant;
import net.sevenstars.middleearth.entity.beasts.warg.WargEntityRenderState;
import net.sevenstars.middleearth.entity.beasts.warg.WargModel;
import java.util.Map;

public class WargEyesFeatureRenderer extends FeatureRenderer<WargEntityRenderState, WargModel> {
    private static final String PATH = "textures/entities/warg/eyes/";
    private static final Identifier EMISSIVE_TEXTURE = Identifier.of(MiddleEarth.MOD_ID, PATH + "warg_eyes_emissive.png");
    private static final Map<WargEyeVariant, Identifier> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(WargEyeVariant.class), (map) -> {
                map.put(WargEyeVariant.BLUE,
                        Identifier.of(MiddleEarth.MOD_ID, PATH + "warg_eyes_blue.png"));
                map.put(WargEyeVariant.ORANGE,
                        Identifier.of(MiddleEarth.MOD_ID, PATH + "warg_eyes_orange.png"));
            });

    public WargEyesFeatureRenderer(FeatureRendererContext<WargEntityRenderState, WargModel> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, WargEntityRenderState state, float limbAngle, float limbDistance) {
        RenderUtil.renderCutoutTexture(this.getContextModel(), matrices, vertexConsumers,
                LOCATION_BY_VARIANT.get(state.eyeVariant), light, OverlayTexture.DEFAULT_UV);

        if(state.haveEmissiveEyes) {
            RenderUtil.renderEmissiveTexture(this.getContextModel(), matrices, vertexConsumers,
                    EMISSIVE_TEXTURE, light, OverlayTexture.DEFAULT_UV);
        }
    }
}
