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
import net.sevenstars.middleearth.entity.beasts.warg.WargEntity;
import net.sevenstars.middleearth.entity.beasts.warg.WargModel;
import net.sevenstars.middleearth.entity.EntityModelLayersME;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.dataComponents.MountArmorAddonComponent;

public class WargArmorSideSkullsFeatureRenderer extends RenderLayer<WargEntity, WargModel> {
    private final WargArmorSideAddonsModel model;

    public WargArmorSideSkullsFeatureRenderer(RenderLayerParent<WargEntity, WargModel> context, EntityModelSet loader) {
        super(context);
        this.model = new WargArmorSideAddonsModel(loader.bakeLayer(EntityModelLayersME.WARG_ARMOR_ADDONS_SIDE_SKULL));
    }

    @Override
    public void render(PoseStack matrices, MultiBufferSource vertexConsumers, int light, WargEntity entity,
                       float limbAngle, float limbDistance, float tickDelta, float animationProgress,
                       float headYaw, float headPitch) {
        ItemStack itemStack = entity.getBodyArmorItem();

        MountArmorAddonComponent component = itemStack.get(DataComponentTypesME.MOUNT_ARMOR_DATA);

        if(component != null && component.sideArmorAddon()) {
            VertexConsumer vertexConsumer = ItemRenderer.getArmorFoilBuffer(vertexConsumers, RenderType.armorCutoutNoCull(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "textures/entities/warg/feature/warg_armor_side_skull_addon.png")), itemStack.hasFoil());

            model.setupAnim(entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);
            model.renderToBuffer(matrices, vertexConsumer, light, OverlayTexture.NO_OVERLAY);
        }
    }
}
