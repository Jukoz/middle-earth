package net.sevenstars.middleearth.entity.beasts.broadhoof.features;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.beasts.broadhoof.BroadhoofGoatEntity;
import net.sevenstars.middleearth.entity.beasts.broadhoof.BroadhoofGoatModel;
import net.sevenstars.middleearth.entity.beasts.broadhoof.BroadhoofGoatPattern;

import java.util.Map;

public class BroadhoofGoatPatternFeatureRenderer extends RenderLayer<BroadhoofGoatEntity, BroadhoofGoatModel> {
    private static final String PATH = "textures/entities/broadhoof_goat/patterns/";
    private static final ResourceLocation INVISIBLE_ID = ResourceLocation.withDefaultNamespace("invisible");

    private static final Map<BroadhoofGoatPattern, ResourceLocation> TEXTURES = Maps.newEnumMap(
            Map.ofEntries(
                    Map.entry(BroadhoofGoatPattern.NONE, INVISIBLE_ID),

                    Map.entry(BroadhoofGoatPattern.BLACK_MASK,
                            MiddleEarth.of(PATH + "broadhoof_goat_black_mask.png")),
                    Map.entry(BroadhoofGoatPattern.BLACK_PATCHES,
                            MiddleEarth.of(PATH + "broadhoof_goat_black_patches.png")),
                    Map.entry(BroadhoofGoatPattern.BLACK_SIDE_PATCH,
                            MiddleEarth.of(PATH + "broadhoof_goat_black_side_patch.png")),
                    Map.entry(BroadhoofGoatPattern.BLACK_SPOTS,
                            MiddleEarth.of(PATH + "broadhoof_goat_black_spots.png")),
                    Map.entry(BroadhoofGoatPattern.BLACK_STRIPS,
                            MiddleEarth.of(PATH + "broadhoof_goat_black_strip.png")),

                    Map.entry(BroadhoofGoatPattern.BROWN_MASK,
                            MiddleEarth.of(PATH + "broadhoof_goat_brown_mask.png")),
                    Map.entry(BroadhoofGoatPattern.BROWN_PATCHES,
                            MiddleEarth.of(PATH + "broadhoof_goat_brown_patches.png")),
                    Map.entry(BroadhoofGoatPattern.BROWN_SIDE_PATCH,
                            MiddleEarth.of(PATH + "broadhoof_goat_brown_side_patch.png")),
                    Map.entry(BroadhoofGoatPattern.BROWN_SPOTS,
                            MiddleEarth.of(PATH + "broadhoof_goat_brown_spots.png")),
                    Map.entry(BroadhoofGoatPattern.BROWN_STRIPS,
                            MiddleEarth.of(PATH + "broadhoof_goat_brown_strip.png")),

                    Map.entry(BroadhoofGoatPattern.PALE_MASK,
                            MiddleEarth.of(PATH + "broadhoof_goat_pale_mask.png")),
                    Map.entry(BroadhoofGoatPattern.PALE_PATCHES,
                            MiddleEarth.of(PATH + "broadhoof_goat_pale_patches.png")),
                    Map.entry(BroadhoofGoatPattern.PALE_SIDE_PATCH,
                            MiddleEarth.of(PATH + "broadhoof_goat_pale_side_patch.png")),
                    Map.entry(BroadhoofGoatPattern.PALE_SPOTS,
                            MiddleEarth.of(PATH + "broadhoof_goat_pale_spots.png")),
                    Map.entry(BroadhoofGoatPattern.PALE_STRIPS,
                            MiddleEarth.of(PATH + "broadhoof_goat_pale_strip.png")),

                    Map.entry(BroadhoofGoatPattern.GRAY_BEARD,
                            MiddleEarth.of(PATH + "broadhoof_goat_gray_beard.png"))


            )
    );

    public BroadhoofGoatPatternFeatureRenderer(RenderLayerParent<BroadhoofGoatEntity, BroadhoofGoatModel> featureRendererContext) {
        super(featureRendererContext);
    }

    public void render(
            PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int light, BroadhoofGoatEntity entity,
            float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch
    ) {
        ResourceLocation identifier = TEXTURES.get(entity.getPattern());
        if (identifier != INVISIBLE_ID && !entity.isInvisible()) {
            VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderType.entityTranslucent(identifier));
            this.getParentModel().renderToBuffer(matrixStack, vertexConsumer, light, LivingEntityRenderer.getOverlayCoords(entity, 0.0F));
        }
    }
}
