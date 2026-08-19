package net.sevenstars.middleearth.entity.beasts.warg.features;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.EntityModelLayersME;
import net.sevenstars.middleearth.entity.beasts.warg.WargEntity;
import net.sevenstars.middleearth.entity.beasts.warg.WargModel;
import net.sevenstars.middleearth.item.items.armor.MountArmorItem;

public class WargArmorFeatureRenderer extends RenderLayer<WargEntity, WargModel> {
    private static final String TEXTURE_PATH = "textures/entity/equipment/horse_body/beast_armor/";
    private final WargArmorModel model;

    public WargArmorFeatureRenderer(RenderLayerParent<WargEntity, WargModel> parent, EntityModelSet modelSet) {
        super(parent);
        this.model = new WargArmorModel(modelSet.bakeLayer(EntityModelLayersME.WARG_ARMOR));
    }

    @Override
    public void render(PoseStack matrices, MultiBufferSource buffers, int light, WargEntity entity,
                       float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks,
                       float netHeadYaw, float headPitch) {
        ItemStack stack = entity.getBodyArmorItem();
        if (!(stack.getItem() instanceof MountArmorItem)) {
            return;
        }

        ResourceLocation itemId = BuiltInRegistries.ITEM.getKey(stack.getItem());
        ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(
                MiddleEarth.MOD_ID, TEXTURE_PATH + itemId.getPath() + ".png");
        VertexConsumer vertices = buffers.getBuffer(RenderType.entityCutoutNoCull(texture));
        int color = stack.has(DataComponents.DYED_COLOR)
                ? DyedItemColor.getOrDefault(stack, DyedItemColor.LEATHER_COLOR)
                : -1;
        this.model.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        this.model.renderToBuffer(matrices, vertices, light, OverlayTexture.NO_OVERLAY, color);

        if (stack.has(DataComponents.DYED_COLOR)) {
            ResourceLocation overlay = ResourceLocation.fromNamespaceAndPath(
                    MiddleEarth.MOD_ID, TEXTURE_PATH + itemId.getPath() + "_overlay.png");
            this.model.renderToBuffer(matrices, buffers.getBuffer(RenderType.entityCutoutNoCull(overlay)),
                    light, OverlayTexture.NO_OVERLAY);
        }
    }
}
