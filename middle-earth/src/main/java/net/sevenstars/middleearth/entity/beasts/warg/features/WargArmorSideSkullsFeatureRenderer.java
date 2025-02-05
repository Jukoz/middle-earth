package net.sevenstars.middleearth.entity.beasts.warg.features;

import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.beasts.warg.WargEntity;
import net.sevenstars.middleearth.entity.beasts.warg.WargModel;
import net.sevenstars.middleearth.entity.model.ModEntityModelLayers;
import net.sevenstars.middleearth.item.ModDataComponentTypes;
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

public class WargArmorSideSkullsFeatureRenderer extends FeatureRenderer<WargEntity, WargModel> {
    private final WargArmorSideAddonsModel model;

    public WargArmorSideSkullsFeatureRenderer(FeatureRendererContext<WargEntity, WargModel> context, EntityModelLoader loader) {
        super(context);
        this.model = new WargArmorSideAddonsModel(loader.getModelPart(ModEntityModelLayers.WARG_ARMOR_ADDONS_SIDE_SKULL));
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, WargEntity wargEntity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        ItemStack itemStack = wargEntity.getBodyArmor();
        Item item = itemStack.getItem();

        if(item instanceof CustomAnimalArmorItem animalArmorItem) {
            if (animalArmorItem.getArmorType() == CustomAnimalArmorItem.Type.WARG) {
                if(itemStack.get(ModDataComponentTypes.MOUNT_ARMOR_DATA) != null && itemStack.get(ModDataComponentTypes.MOUNT_ARMOR_DATA).sideArmorAddon()) {
                    ((WargModel) this.getContextModel()).copyStateTo(this.model);

                    this.model.setAngles(wargEntity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);

                    Identifier addonTexture = Identifier.of(MiddleEarth.MOD_ID, "textures/entities/warg/feature/warg_armor_side_skull_addon.png");

                    VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutoutNoCull(addonTexture));
                    this.model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, -1);
                }
            }
        }
    }
}
