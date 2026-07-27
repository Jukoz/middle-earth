package net.sevenstars.middleearth.entity.beasts.great_horn.features;

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
import net.sevenstars.middleearth.entity.EntityModelLayersME;
import net.sevenstars.middleearth.entity.beasts.great_horn.GreatHornEntity;
import net.sevenstars.middleearth.entity.beasts.great_horn.GreatHornModel;

public class GreatHornSaddleFeatureRenderer extends RenderLayer<GreatHornEntity, GreatHornModel> {
    private final GreatHornSaddleModel model;
    private final static String PATH = "textures/entities/great_horn/feature/great_horn_saddle";

    public GreatHornSaddleFeatureRenderer(RenderLayerParent<GreatHornEntity, GreatHornModel> context, EntityModelSet loader) {
        super(context);
        this.model = new GreatHornSaddleModel(loader.bakeLayer(EntityModelLayersME.GREAT_HORN_SADDLE));
    }

    @Override
    public void render(PoseStack matrices, MultiBufferSource vertexConsumers, int light, GreatHornEntity entity,
                       float limbAngle, float limbDistance, float tickDelta, float animationProgress,
                       float headYaw, float headPitch) {
        ItemStack itemStack = entity.getInventory().getItem(AbstractHorse.INV_SLOT_SADDLE);
        boolean blueSaddle = entity.hasBlueSaddle();
        String suffix = "";
        if(blueSaddle) suffix = "_blue";
        if(!itemStack.isEmpty()) {
            VertexConsumer vertexConsumer = ItemRenderer.getArmorFoilBuffer(vertexConsumers,
                    RenderType.armorCutoutNoCull(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, PATH + suffix + ".png")), itemStack.hasFoil());

            model.setupAnim(entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);
            model.renderToBuffer(matrices, vertexConsumer, light, OverlayTexture.NO_OVERLAY);
        }
    }
}
