package net.jukoz.me.mixin.client;

import net.jukoz.me.datageneration.VariantsModelProvider;
import net.jukoz.me.datageneration.content.models.SimpleBigItemModel;
import net.jukoz.me.datageneration.content.models.SimpleSpearModel;
import net.jukoz.me.item.items.weapons.artefacts.ArtefactCustomGlowingDaggerWeaponItem;
import net.jukoz.me.item.items.weapons.artefacts.ArtefactCustomGlowingLongswordWeaponItem;
import net.jukoz.me.item.items.weapons.artefacts.ArtefactCustomLongswordWeaponItem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.jukoz.me.item.ModDataComponentTypes;
import net.minecraft.client.render.item.ItemModels;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {
    private static final ModelIdentifier TRIDENT = ModelIdentifier.ofInventoryVariant(Identifier.ofVanilla("trident"));

    @Shadow @Final private ItemModels models;

    @Debug(export = true)
    @ModifyVariable(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V",
            at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private BakedModel renderItem(BakedModel model, ItemStack stack, ModelTransformationMode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if(renderMode == ModelTransformationMode.GUI || renderMode == ModelTransformationMode.GROUND || renderMode == ModelTransformationMode.FIXED) {
            if(SimpleBigItemModel.artefacts.contains(stack.getItem())
                    || SimpleBigItemModel.items.contains(stack.getItem())
                    || SimpleSpearModel.items.contains(stack.getItem())) {
                Identifier identifier = VariantsModelProvider.getInventoryModelIdentifierVariant(stack.getItem());
                if (SimpleBigItemModel.artefactsGlowing.contains(stack.getItem())){
                    if (stack.getItem() instanceof  ArtefactCustomGlowingLongswordWeaponItem item && item.glowing){
                        identifier = VariantsModelProvider.getInventoryModelGlowingItem(item);
                    } else if (stack.getItem() instanceof  ArtefactCustomGlowingDaggerWeaponItem item && item.glowing){
                        identifier = VariantsModelProvider.getInventoryModelGlowingItem(item);
                    }
                }
                if (SimpleBigItemModel.artefactsBroken.contains(stack.getItem()) && stack.getDamage() == stack.getMaxDamage() - 1){
                    identifier = VariantsModelProvider.getInventoryModelBrokenItem(stack.getItem());
                }
                return MinecraftClient.getInstance().getBakedModelManager().getModel(identifier);
            }
        }
        if(isItemHot(stack)) {
            Identifier identifier = VariantsModelProvider.getHotModelIdentifierVariant(stack.getItem());
            return MinecraftClient.getInstance().getBakedModelManager().getModel(identifier);
        }
        return model;
    }

    private static boolean isItemHot(ItemStack stack) {
        return stack.getComponents().contains(ModDataComponentTypes.TEMPERATURE_DATA);
    }

}