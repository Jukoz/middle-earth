package net.jukoz.me.entity.beasts.broadhoof.features;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.beasts.broadhoof.BroadhoofGoatEntity;
import net.jukoz.me.entity.beasts.broadhoof.BroadhoofGoatModel;
import net.jukoz.me.entity.beasts.warg.WargModel;
import net.jukoz.me.entity.beasts.warg.features.WargArmorModel;
import net.jukoz.me.entity.model.ModEntityModelLayers;
import net.jukoz.me.item.dataComponents.CustomDyeableDataComponent;
import net.jukoz.me.item.items.CustomAnimalArmorItem;
import net.jukoz.me.recipe.ModTags;
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
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class BroadhoofGoatArmorFeatureRenderer extends FeatureRenderer<BroadhoofGoatEntity, BroadhoofGoatModel> {

    private final BroadhoofGoatArmorModel model;

    public BroadhoofGoatArmorFeatureRenderer(FeatureRendererContext<BroadhoofGoatEntity, BroadhoofGoatModel> context, EntityModelLoader loader) {
        super(context);
        this.model = new BroadhoofGoatArmorModel(loader.getModelPart(ModEntityModelLayers.BROADHOOF_GOAT_ARMOR));
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, BroadhoofGoatEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        ItemStack itemStack = entity.getBodyArmor();
        Item item = itemStack.getItem();
        if(item instanceof CustomAnimalArmorItem animalArmorItem) {
            if (animalArmorItem.getArmorType() == CustomAnimalArmorItem.Type.BROADHOOF_GOAT) {
                ((BroadhoofGoatModel)this.getContextModel()).copyStateTo(this.model);

                this.model.setAngles(entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);

                VertexConsumer vertexConsumer;

                if (itemStack.isIn(ModTags.DYEABLE)) {
                    vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutoutNoCull(animalArmorItem.getOverlayTexture()));
                    this.model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, -1);

                    vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutoutNoCull(animalArmorItem.getEntityTexture()));
                    int color = CustomDyeableDataComponent.getColor(itemStack, CustomDyeableDataComponent.DEFAULT_COLOR);
                    this.model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, color);
                }
                else {
                    vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutoutNoCull(animalArmorItem.getEntityTexture()));
                    int color = CustomDyeableDataComponent.getColor(itemStack, CustomDyeableDataComponent.DEFAULT_COLOR);
                    this.model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, -1);
                }
                return;
            }
        }
    }
}
