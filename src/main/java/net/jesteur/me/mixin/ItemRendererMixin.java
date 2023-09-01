package net.jesteur.me.mixin;

import net.jesteur.me.MiddleEarth;
import net.jesteur.me.datageneration.VariantsModelProvider;
import net.jesteur.me.datageneration.content.models.SimpleBigItemModel;
import net.minecraft.client.render.item.ItemModels;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {
    @Shadow @Final private ItemModels models;

    @ModifyVariable(method = "renderItem", at = @At("HEAD"), argsOnly = true)
    private BakedModel renderItem(BakedModel bakedModel, ItemStack itemStack, ModelTransformationMode renderMode) {
        if(renderMode == ModelTransformationMode.GUI) {
            if(SimpleBigItemModel.items.contains(itemStack.getItem())) {
                ModelIdentifier modelIdentifier = VariantsModelProvider.getInventoryModelIdentifierVariant(itemStack.getItem()); // new ModelIdentifier(MiddleEarth.MOD_ID, itemStack.getTranslationKey(), "inventory");
                return models.getModelManager().getModel(modelIdentifier);
            }
        }
        return bakedModel;
    }
}