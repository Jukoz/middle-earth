package net.sevenstars.middleearth.entity.beasts.warg.features;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.client.RenderUtil;
import net.sevenstars.middleearth.entity.beasts.warg.WargEyeVariant;
import net.sevenstars.middleearth.entity.beasts.warg.WargEntity;
import net.sevenstars.middleearth.entity.beasts.warg.WargModel;
import java.util.Map;

public class WargEyesFeatureRenderer extends RenderLayer<WargEntity, WargModel> {
    private static final String PATH = "textures/entities/warg/eyes/";
    private static final ResourceLocation EMISSIVE_TEXTURE = ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, PATH + "warg_eyes_emissive.png");
    private static final Map<WargEyeVariant, ResourceLocation> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(WargEyeVariant.class), (map) -> {
                map.put(WargEyeVariant.BLUE,
                        ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, PATH + "warg_eyes_blue.png"));
                map.put(WargEyeVariant.ORANGE,
                        ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, PATH + "warg_eyes_orange.png"));
            });

    public WargEyesFeatureRenderer(RenderLayerParent<WargEntity, WargModel> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void render(PoseStack matrices, MultiBufferSource vertexConsumers, int light, WargEntity entity,
                       float limbAngle, float limbDistance, float tickDelta, float animationProgress,
                       float headYaw, float headPitch) {
        RenderUtil.renderCutoutTexture(this.getParentModel(), matrices, vertexConsumers,
                LOCATION_BY_VARIANT.get(entity.getEyeVariant()), light, OverlayTexture.NO_OVERLAY);

        if(!entity.level().isDay()
                || entity.level().getMaxLocalRawBrightness(entity.blockPosition()) <= 8) {
            RenderUtil.renderEmissiveTexture(this.getParentModel(), matrices, vertexConsumers,
                    EMISSIVE_TEXTURE, light, OverlayTexture.NO_OVERLAY);
        }
    }
}
