package net.sevenstars.middleearth.entity.beasts.broadhoof.features;

import com.mojang.blaze3d.vertex.PoseStack;
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
import net.sevenstars.middleearth.entity.beasts.broadhoof.BroadhoofGoatEntity;
import net.sevenstars.middleearth.entity.beasts.broadhoof.BroadhoofGoatModel;
import net.sevenstars.middleearth.item.items.armor.MountArmorItem;

public class BroadhoofGoatArmorFeatureRenderer extends RenderLayer<BroadhoofGoatEntity, BroadhoofGoatModel> {
    private static final String TEXTURE_PATH = "textures/entity/equipment/horse_body/beast_armor/";
    private final BroadhoofGoatArmorModel model;

    public BroadhoofGoatArmorFeatureRenderer(
            RenderLayerParent<BroadhoofGoatEntity, BroadhoofGoatModel> parent, EntityModelSet modelSet) {
        super(parent);
        this.model = new BroadhoofGoatArmorModel(modelSet.bakeLayer(EntityModelLayersME.BROADHOOF_GOAT_ARMOR));
    }

    @Override
    public void render(PoseStack matrices, MultiBufferSource buffers, int light, BroadhoofGoatEntity entity,
                       float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks,
                       float netHeadYaw, float headPitch) {
        ItemStack stack = entity.getBodyArmorItem();
        if (!(stack.getItem() instanceof MountArmorItem)) {
            return;
        }

        String name = BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath();
        ResourceLocation texture = MiddleEarth.of(TEXTURE_PATH + name + ".png");
        int color = stack.has(DataComponents.DYED_COLOR)
                ? DyedItemColor.getOrDefault(stack, DyedItemColor.LEATHER_COLOR)
                : -1;
        this.model.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        this.model.renderToBuffer(matrices, buffers.getBuffer(RenderType.entityCutoutNoCull(texture)),
                light, OverlayTexture.NO_OVERLAY, color);

        if (stack.has(DataComponents.DYED_COLOR)) {
            ResourceLocation overlay = MiddleEarth.of(TEXTURE_PATH + name + "_overlay.png");
            this.model.renderToBuffer(matrices, buffers.getBuffer(RenderType.entityCutoutNoCull(overlay)),
                    light, OverlayTexture.NO_OVERLAY);
        }
    }
}
