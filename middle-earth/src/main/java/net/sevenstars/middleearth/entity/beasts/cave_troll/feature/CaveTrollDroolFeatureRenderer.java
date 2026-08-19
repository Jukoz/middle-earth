package net.sevenstars.middleearth.entity.beasts.cave_troll.feature;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.beasts.cave_troll.CaveTrollEntityModel;
import net.sevenstars.middleearth.entity.beasts.cave_troll.CaveTrollEntity;

public class CaveTrollDroolFeatureRenderer extends RenderLayer<CaveTrollEntity, CaveTrollEntityModel> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "textures/entities/trolls/cave/cave_troll_green_drooling.png");
    public CaveTrollDroolFeatureRenderer(RenderLayerParent<CaveTrollEntity, CaveTrollEntityModel> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void render(PoseStack matrices, MultiBufferSource vertexConsumers, int light, CaveTrollEntity entity,
                       float limbAngle, float limbDistance, float tickDelta, float animationProgress,
                       float headYaw, float headPitch) {
        if(entity.getTameness() < 50 && entity.isTamed()) {
            VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderType.entityCutoutNoCull(TEXTURE));
            this.getParentModel().renderToBuffer(matrices, vertexConsumer, light, OverlayTexture.NO_OVERLAY);
        }
    }
}
