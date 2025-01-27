package net.sevenstars.middleearth.entity.beasts.warg.features;

import net.sevenstars.middleearth.entity.beasts.warg.WargEntity;
import net.sevenstars.middleearth.entity.beasts.warg.WargModel;
import net.sevenstars.middleearth.entity.model.ModEntityModelLayers;
import net.sevenstars.middleearth.item.dataComponents.CustomDyeableDataComponent;
import net.sevenstars.middleearth.item.items.armor.CustomAnimalArmorItem;
import net.sevenstars.middleearth.recipe.ModTags;
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

public class WargArmorFeatureRenderer extends FeatureRenderer<WargEntity, WargModel> {
    private final WargArmorModel model;

    public WargArmorFeatureRenderer(FeatureRendererContext<WargEntity, WargModel> context, EntityModelLoader loader) {
        super(context);
        this.model = new WargArmorModel(loader.getModelPart(ModEntityModelLayers.WARG_ARMOR));
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, WargEntity wargEntity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        ItemStack itemStack = wargEntity.getBodyArmor();
        Item item = itemStack.getItem();
        if(item instanceof CustomAnimalArmorItem animalArmorItem) {
            if (animalArmorItem.getArmorType() == CustomAnimalArmorItem.Type.WARG) {
                ((WargModel)this.getContextModel()).copyStateTo(this.model);

                this.model.setAngles(wargEntity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);

                VertexConsumer vertexConsumer;

                if (itemStack.isIn(ModTags.DYEABLE)) {
                    if(animalArmorItem.getOverlayTexture() != null) {
                        vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutoutNoCull(animalArmorItem.getOverlayTexture()));
                        this.model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, -1);
                    }

                    vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutoutNoCull(animalArmorItem.getEntityTexture()));
                    int color = CustomDyeableDataComponent.getColor(itemStack, CustomDyeableDataComponent.DEFAULT_COLOR);
                    this.model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, color);
                }
                else {
                    vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutoutNoCull(animalArmorItem.getEntityTexture()));
                    int color = CustomDyeableDataComponent.getColor(itemStack, CustomDyeableDataComponent.DEFAULT_COLOR);
                    this.model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, -1);
                }
            }
        }
    }
}
