package net.sevenstars.middleearth.entity.beasts.broadhoof;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.EntityModelLayersME;
import net.sevenstars.middleearth.entity.beasts.broadhoof.features.BroadhoofGoatBeadsFeatureRenderer;
import net.sevenstars.middleearth.entity.beasts.broadhoof.features.BroadhoofGoatArmorFeatureRenderer;
import net.sevenstars.middleearth.entity.beasts.broadhoof.features.BroadhoofGoatPatternFeatureRenderer;
import net.sevenstars.middleearth.entity.beasts.broadhoof.features.BroadhoofGoatSaddleFeatureRenderer;

import java.util.Map;

public class BroadhoofGoatRenderer extends MobRenderer<BroadhoofGoatEntity, BroadhoofGoatModel> {
    private static final String PATH = "textures/entities/broadhoof_goat/";
    private static final float SIZE = 1f;

    public BroadhoofGoatRenderer(EntityRendererProvider.Context context) {
        super(context, new BroadhoofGoatModel(context.bakeLayer(EntityModelLayersME.BROADHOOF_GOAT)), 0.8f);
        this.addLayer(new BroadhoofGoatPatternFeatureRenderer(this));
        this.addLayer(new BroadhoofGoatBeadsFeatureRenderer(this));
        this.addLayer(new BroadhoofGoatArmorFeatureRenderer(this, context.getModelSet()));
        this.addLayer(new BroadhoofGoatSaddleFeatureRenderer(this, context.getModelSet()));
    }

    @Override
    public void render(BroadhoofGoatEntity entity, float entityYaw, float partialTick, PoseStack matrixStack,
                       MultiBufferSource vertexConsumerProvider, int light) {
        if(entity.isBaby()) {
            matrixStack.scale(SIZE/2, SIZE/2, SIZE/2);
        }
        else {
            matrixStack.scale(SIZE, SIZE, SIZE);
        }

        super.render(entity, entityYaw, partialTick, matrixStack, vertexConsumerProvider, light);
    }

    private static final Map<BroadhoofGoatColor, ResourceLocation> TEXTURES = Maps.newEnumMap(
            Map.of(
                    BroadhoofGoatColor.WHITE,
                    MiddleEarth.of(PATH + "broadhoof_goat_white.png"),
                    BroadhoofGoatColor.LIGHT_GRAY,
                    MiddleEarth.of(PATH + "broadhoof_goat_light_gray.png"),
                    BroadhoofGoatColor.PALE,
                    MiddleEarth.of(PATH + "broadhoof_goat_pale.png"),
                    BroadhoofGoatColor.RED,
                    MiddleEarth.of(PATH + "broadhoof_goat_red.png"),
                    BroadhoofGoatColor.BROWN,
                    MiddleEarth.of(PATH + "broadhoof_goat_brown.png"),
                    BroadhoofGoatColor.GRAY,
                    MiddleEarth.of(PATH + "broadhoof_goat_gray.png"),
                    BroadhoofGoatColor.BLACK,
                    MiddleEarth.of(PATH + "broadhoof_goat_black.png")
            )
    );

    @Override
    public ResourceLocation getTextureLocation(BroadhoofGoatEntity entity) {
        return TEXTURES.get(entity.getGoatColor());
    }
}
