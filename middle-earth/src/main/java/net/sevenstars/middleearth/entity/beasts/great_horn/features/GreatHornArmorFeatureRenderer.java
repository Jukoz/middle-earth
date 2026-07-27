package net.sevenstars.middleearth.entity.beasts.great_horn.features;

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
import net.sevenstars.middleearth.entity.beasts.great_horn.GreatHornEntity;
import net.sevenstars.middleearth.entity.beasts.great_horn.GreatHornModel;
import net.sevenstars.middleearth.item.items.armor.MountArmorItem;

public class GreatHornArmorFeatureRenderer extends RenderLayer<GreatHornEntity, GreatHornModel> {
    private static final String TEXTURE_PATH = "textures/entities/great_horn/feature/";
    private final GreatHornArmorModel model;

    public GreatHornArmorFeatureRenderer(
            RenderLayerParent<GreatHornEntity, GreatHornModel> parent, EntityModelSet modelSet) {
        super(parent);
        this.model = new GreatHornArmorModel(modelSet.bakeLayer(EntityModelLayersME.GREAT_HORN_ARMOR));
    }

    @Override
    public void render(PoseStack matrices, MultiBufferSource buffers, int light, GreatHornEntity entity,
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
