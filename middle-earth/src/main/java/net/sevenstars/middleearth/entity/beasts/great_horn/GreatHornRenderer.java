package net.sevenstars.middleearth.entity.beasts.great_horn;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.EntityModelLayersME;
import net.sevenstars.middleearth.entity.beasts.great_horn.features.GreatHornArmorFeatureRenderer;
import net.sevenstars.middleearth.entity.beasts.great_horn.features.GreatHornNoseFeatureRenderer;
import net.sevenstars.middleearth.entity.beasts.great_horn.features.GreatHornSaddleFeatureRenderer;

import java.util.Map;

public class GreatHornRenderer extends MobRenderer<GreatHornEntity, GreatHornModel> {
    private static final String PATH = "textures/entities/great_horn/";
    private static final float SIZE = 1f;

    public GreatHornRenderer(EntityRendererProvider.Context context) {
        super(context, new GreatHornModel(context.bakeLayer(EntityModelLayersME.GREAT_HORN)), 0.95f);
        this.addLayer(new GreatHornSaddleFeatureRenderer(this, context.getModelSet()));
        this.addLayer(new GreatHornArmorFeatureRenderer(this, context.getModelSet()));
        this.addLayer(new GreatHornNoseFeatureRenderer(this,  context.getModelSet()));
    }

    @Override
    public void render(GreatHornEntity entity, float entityYaw, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();
        if (entity.isBaby()) {
            poseStack.scale(0.5F, 0.5F, 0.5F);
        }
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
        poseStack.popPose();
    }

    public static final Map<GreatHornVariantDep, ResourceLocation> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(GreatHornVariantDep.class), (map) -> {
                map.put(GreatHornVariantDep.BROWN,
                        ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, PATH + "brown_great_horn.png"));
                map.put(GreatHornVariantDep.TEMPERATE,
                        ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, PATH + "temperate_great_horn.png"));
                map.put(GreatHornVariantDep.COLD,
                        ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, PATH + "cold_great_horn.png"));
                map.put(GreatHornVariantDep.WARM,
                        ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, PATH + "warm_great_horn.png"));
                map.put(GreatHornVariantDep.WHITE,
                        ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, PATH + "white_great_horn.png"));

            });


    @Override
    public ResourceLocation getTextureLocation(GreatHornEntity entity) {
        return GreatHornVariant.texture(entity.getVariant().assetInfo().id());
    }
}
