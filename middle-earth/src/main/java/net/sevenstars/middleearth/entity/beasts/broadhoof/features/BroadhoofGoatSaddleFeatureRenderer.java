package net.sevenstars.middleearth.entity.beasts.broadhoof.features;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.beasts.broadhoof.BroadhoofGoatEntity;
import net.sevenstars.middleearth.entity.beasts.broadhoof.BroadhoofGoatModel;
import net.sevenstars.middleearth.entity.EntityModelLayersME;

public class BroadhoofGoatSaddleFeatureRenderer extends RenderLayer<BroadhoofGoatEntity, BroadhoofGoatModel> {
    private final BroadhoofGoatSaddleModel model;

    public BroadhoofGoatSaddleFeatureRenderer(RenderLayerParent<BroadhoofGoatEntity, BroadhoofGoatModel> context, EntityModelSet loader) {
        super(context);

        this.model = new BroadhoofGoatSaddleModel(loader.bakeLayer(EntityModelLayersME.BROADHOOF_GOAT_SADDLE));
    }

    @Override
    public void render(PoseStack matrices, MultiBufferSource vertexConsumers, int light, BroadhoofGoatEntity entity,
                       float limbAngle, float limbDistance, float tickDelta, float animationProgress,
                       float headYaw, float headPitch) {
        ItemStack itemStack = entity.getInventory().getItem(AbstractHorse.INV_SLOT_SADDLE);
        if(!itemStack.isEmpty()) {
            VertexConsumer vertexConsumer = ItemRenderer.getArmorFoilBuffer(vertexConsumers, RenderType.armorCutoutNoCull(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "textures/entities/broadhoof_goat/feature/broadhoof_goat_saddle.png")), itemStack.hasFoil());

            model.setupAnim(entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);
            model.renderToBuffer(matrices, vertexConsumer, light, OverlayTexture.NO_OVERLAY);
        }
    }
}
