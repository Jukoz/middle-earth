package net.sevenstars.middleearth.entity.beasts.warg.features;

import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.beasts.warg.WargEntity;
import net.sevenstars.middleearth.entity.beasts.warg.WargModel;
import net.sevenstars.middleearth.entity.model.ModEntityModelLayers;
import net.sevenstars.middleearth.item.ModEquipmentItems;
import net.sevenstars.middleearth.item.items.armor.CustomAnimalArmorItem;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class WargArmorSpineFeatureRenderer extends FeatureRenderer<WargEntity, WargModel> {
    private final WargArmorBaseAddonsModel model;

    public WargArmorSpineFeatureRenderer(FeatureRendererContext<WargEntity, WargModel> context, EntityModelLoader loader) {
        super(context);
        this.model = new WargArmorBaseAddonsModel(loader.getModelPart(ModEntityModelLayers.WARG_ARMOR_ADDONS_SPINE));
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, WargEntity wargEntity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        ItemStack itemStack = wargEntity.getBodyArmor();
        Item item = itemStack.getItem();

        if(item instanceof CustomAnimalArmorItem animalArmorItem) {
            if (itemStack.isOf(ModEquipmentItems.WARG_REINFORCED_LEATHER_ARMOR)) {
                ((WargModel)this.getContextModel()).copyStateTo(this.model);

                this.model.setAngles(wargEntity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);

                Identifier addonTexture = null;

                if(itemStack.isOf(ModEquipmentItems.WARG_REINFORCED_LEATHER_ARMOR))  { // Bone Spine
                    addonTexture = Identifier.of(MiddleEarth.MOD_ID, "textures/entities/warg/feature/warg_armor_bone_spine_addon.png");
                }

                VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutoutNoCull(addonTexture));
                this.model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, -1);
            }
        }
    }
}
