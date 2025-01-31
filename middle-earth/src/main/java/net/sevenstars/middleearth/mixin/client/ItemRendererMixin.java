package net.sevenstars.middleearth.mixin.client;

import net.minecraft.item.ModelTransformationMode;
import net.sevenstars.middleearth.datageneration.VariantsModelProvider;
import net.sevenstars.middleearth.datageneration.content.models.HotMetalsModel;
import net.sevenstars.middleearth.datageneration.content.models.SimpleBigItemModel;
import net.sevenstars.middleearth.datageneration.content.models.SimpleSpearModel;
import net.sevenstars.middleearth.item.ModDataComponentTypes;
import net.sevenstars.middleearth.item.items.weapons.artefacts.ArtefactCustomGlowingDaggerWeaponItem;
import net.sevenstars.middleearth.item.items.weapons.artefacts.ArtefactCustomGlowingLongswordWeaponItem;
import net.sevenstars.middleearth.item.items.weapons.ranged.CustomLongbowWeaponItem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {

    @Debug(export = true)
    @ModifyVariable(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V",
            at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private BakedModel renderItem(BakedModel model, ItemStack stack, ModelTransformationMode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if(renderMode == ModelTransformationMode.GUI || renderMode == ModelTransformationMode.GROUND || renderMode == ModelTransformationMode.FIXED) {
            if(SimpleBigItemModel.artefacts.contains(stack.getItem())
                    || SimpleBigItemModel.items.contains(stack.getItem())
                    || SimpleBigItemModel.bigBows.contains(stack.getItem())
                    || SimpleSpearModel.items.contains(stack.getItem())
                    || SimpleBigItemModel.genericItems.contains(stack.getItem())) {
                Identifier identifier = VariantsModelProvider.getInventoryModelIdentifierVariant(stack.getItem());
                if (SimpleBigItemModel.artefactsBroken.contains(stack.getItem()) && stack.getDamage() == stack.getMaxDamage() - 1){
                    identifier = VariantsModelProvider.getInventoryModelBrokenItem(stack.getItem());
                    return MinecraftClient.getInstance().getBakedModelManager().getModel(identifier);
                }

                if (SimpleBigItemModel.artefactsGlowing.contains(stack.getItem())) {
                    if (stack.getItem() instanceof  ArtefactCustomGlowingLongswordWeaponItem item && item.glowing){
                        identifier = VariantsModelProvider.getInventoryModelGlowingItem(item);
                        return MinecraftClient.getInstance().getBakedModelManager().getModel(identifier);
                    } else if (stack.getItem() instanceof  ArtefactCustomGlowingDaggerWeaponItem item && item.glowing){
                        identifier = VariantsModelProvider.getInventoryModelGlowingItem(item);
                        return MinecraftClient.getInstance().getBakedModelManager().getModel(identifier);
                    }
                } else if(SimpleBigItemModel.bigBows.contains(stack.getItem())) {
                    if(stack.getItem() instanceof BowItem bowWeaponItem) {
                        PlayerEntity playerEntity = MinecraftClient.getInstance().player;
                        if(playerEntity.getActiveItem() == stack) {
                            float pull = BowItem.getPullProgress(playerEntity.getItemUseTime());
                            if(stack.getItem() instanceof CustomLongbowWeaponItem) {
                                pull = CustomLongbowWeaponItem.getPullProgressLongbow((int) (playerEntity.getItemUseTime() * 0.92f));
                            }

                            if(pull > 0) {
                                identifier = VariantsModelProvider.getPullLongbowModel(bowWeaponItem, pull);
                                BakedModel bakedModel = MinecraftClient.getInstance().getBakedModelManager().getModel(identifier);
                                return bakedModel;
                            }
                        }
                    }
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

    @Unique
    private static boolean isItemHot(ItemStack stack) {
        return stack.getComponents().contains(ModDataComponentTypes.TEMPERATURE_DATA) && (
                HotMetalsModel.nuggets.contains(stack.getItem()) ||
                HotMetalsModel.ingots.contains(stack.getItem()) ||
                HotMetalsModel.items.contains(stack.getItem())
                );
    }

}