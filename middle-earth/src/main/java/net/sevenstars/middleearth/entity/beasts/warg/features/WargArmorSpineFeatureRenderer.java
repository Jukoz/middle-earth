package net.sevenstars.middleearth.entity.beasts.warg.features;

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
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.EntityModelLayersME;
import net.sevenstars.middleearth.entity.beasts.warg.WargEntity;
import net.sevenstars.middleearth.entity.beasts.warg.WargModel;
import net.sevenstars.middleearth.item.EquipmentItemsME;

public class WargArmorSpineFeatureRenderer extends RenderLayer<WargEntity, WargModel> {
    private final WargArmorBaseAddonsModel model;

    public WargArmorSpineFeatureRenderer(RenderLayerParent<WargEntity, WargModel> context, EntityModelSet loader) {
        super(context);
        this.model = new WargArmorBaseAddonsModel(loader.bakeLayer(EntityModelLayersME.WARG_ARMOR_ADDONS_SPINE));
    }

    @Override
    public void render(PoseStack matrices, MultiBufferSource vertexConsumers, int light, WargEntity entity,
                       float limbAngle, float limbDistance, float tickDelta, float animationProgress,
                       float headYaw, float headPitch) {
        ItemStack itemStack = entity.getBodyArmorItem();
        if(itemStack.is(EquipmentItemsME.WARG_REINFORCED_LEATHER_ARMOR)) {
            VertexConsumer vertexConsumer = ItemRenderer.getArmorFoilBuffer(vertexConsumers, RenderType.armorCutoutNoCull(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "textures/entities/warg/feature/warg_armor_bone_spine_addon.png")), itemStack.hasFoil());

            model.setupAnim(entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);
            model.renderToBuffer(matrices, vertexConsumer, light, OverlayTexture.NO_OVERLAY);
        }
        else if(itemStack.is(EquipmentItemsME.WARG_MORDOR_PLATE_ARMOR) || itemStack.is(EquipmentItemsME.WARG_MORDOR_MAIL_ARMOR)) {
            VertexConsumer vertexConsumer = ItemRenderer.getArmorFoilBuffer(vertexConsumers, RenderType.armorCutoutNoCull(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "textures/entities/warg/feature/warg_armor_mordor_spine_addon.png")), itemStack.hasFoil());

            model.setupAnim(entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);
            model.renderToBuffer(matrices, vertexConsumer, light, OverlayTexture.NO_OVERLAY);
        }
    }
}
