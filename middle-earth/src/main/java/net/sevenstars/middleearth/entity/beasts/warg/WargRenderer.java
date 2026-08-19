package net.sevenstars.middleearth.entity.beasts.warg;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.beasts.warg.features.*;
import net.sevenstars.middleearth.entity.EntityModelLayersME;
import java.util.Map;

public class WargRenderer extends MobRenderer<WargEntity, WargModel> {
    private static final String PATH = "textures/entities/warg/";
    private static final float SIZE = 1f;
    private static final int LIGHT_LEVEL_EMISSIVE_EYES = 8;

    public WargRenderer(EntityRendererProvider.Context context) {
        super(context, new WargModel(context.bakeLayer(EntityModelLayersME.WARG)), 0.8f);
        this.addLayer(new WargEyesFeatureRenderer(this));
        this.addLayer(new WargArmorFeatureRenderer(this, context.getModelSet()));
        this.addLayer(new WargArmorSpineFeatureRenderer(this, context.getModelSet()));
        this.addLayer(new WargArmorSideSkullsFeatureRenderer(this, context.getModelSet()));
        this.addLayer(new WargArmorFrontSkullFeatureRenderer(this, context.getModelSet()));
        this.addLayer(new WargArmorBackSkullFeatureRenderer(this, context.getModelSet()));
        this.addLayer(new WargSaddleFeatureRenderer(this, context.getModelSet()));
    }

    @Override
    public void render(WargEntity entity, float entityYaw, float partialTick, PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int light) {
        if(entity.isBaby()) {
            matrixStack.scale(SIZE/2, SIZE/2, SIZE/2);
        }
        else {
            matrixStack.scale(SIZE, SIZE, SIZE);
        }

        super.render(entity, entityYaw, partialTick, matrixStack, vertexConsumerProvider, light);
    }

    public static final Map<WargVariant, ResourceLocation> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(WargVariant.class), (map) -> {
                map.put(WargVariant.BROWN,
                        ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, PATH + "warg_brown.png"));
                map.put(WargVariant.BLACK,
                        ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, PATH + "warg_black.png"));
                map.put(WargVariant.GRAY,
                        ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, PATH + "warg_gray.png"));
                map.put(WargVariant.LIGHT_GRAY,
                        ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, PATH + "warg_light_gray.png"));
                map.put(WargVariant.SNOW,
                        ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, PATH + "warg_snow.png"));
                map.put(WargVariant.MOTTLED,
                        ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, PATH + "warg_mottled.png"));
                map.put(WargVariant.TAN,
                        ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, PATH + "warg_tan.png"));
            });

    @Override
    public ResourceLocation getTextureLocation(WargEntity entity) {
        return LOCATION_BY_VARIANT.get(entity.getVariant());
    }

    @Override
    protected int getBlockLightLevel(WargEntity entity, BlockPos pos) {
        return !entity.level().isDay() ? 0 : Math.max(super.getBlockLightLevel(entity, pos), LIGHT_LEVEL_EMISSIVE_EYES);
    }
}
