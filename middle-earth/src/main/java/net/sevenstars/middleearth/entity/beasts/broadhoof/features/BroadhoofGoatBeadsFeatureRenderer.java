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
import net.sevenstars.middleearth.entity.beasts.broadhoof.BroadhoofGoatBeads;
import net.sevenstars.middleearth.entity.beasts.broadhoof.BroadhoofGoatEntity;
import net.sevenstars.middleearth.entity.beasts.broadhoof.BroadhoofGoatModel;
import net.sevenstars.middleearth.entity.beasts.broadhoof.BroadhoofGoatPattern;

import java.util.Map;

public class BroadhoofGoatBeadsFeatureRenderer extends RenderLayer<BroadhoofGoatEntity, BroadhoofGoatModel> {
    private static final String PATH = "textures/entities/broadhoof_goat/beads/";
    private static final ResourceLocation INVISIBLE_ID = ResourceLocation.withDefaultNamespace("invisible");

    private static final Map<BroadhoofGoatBeads, ResourceLocation> TEXTURES = Maps.newEnumMap(
            Map.of(
                    BroadhoofGoatBeads.NONE,
                    INVISIBLE_ID,
                    BroadhoofGoatBeads.LEATHER,
                    MiddleEarth.of(PATH + "broadhoof_goat_leather_beads.png"),
                    BroadhoofGoatBeads.COAL,
                    MiddleEarth.of(PATH + "broadhoof_goat_coal_beads.png"),
                    BroadhoofGoatBeads.COPPER,
                    MiddleEarth.of(PATH + "broadhoof_goat_copper_beads.png"),
                    BroadhoofGoatBeads.GOLD,
                    MiddleEarth.of(PATH + "broadhoof_goat_gold_beads.png"),
                    BroadhoofGoatBeads.ALMANDINE,
                    MiddleEarth.of(PATH + "broadhoof_goat_almandine_beads.png")
            )
    );

    public BroadhoofGoatBeadsFeatureRenderer(RenderLayerParent<BroadhoofGoatEntity, BroadhoofGoatModel> featureRendererContext) {
        super(featureRendererContext);
    }

    public void render(
            PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int light, BroadhoofGoatEntity entity,
            float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch
    ) {
        ResourceLocation identifier = TEXTURES.get(entity.getGoatBeads());
        if (identifier != INVISIBLE_ID && !entity.isInvisible()) {
            VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderType.entityTranslucent(identifier));
            this.getParentModel().renderToBuffer(matrixStack, vertexConsumer, light, LivingEntityRenderer.getOverlayCoords(entity, 0.0F));
        }
    }
}
