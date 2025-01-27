package net.sevenstars.middleearth.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.sevenstars.middleearth.item.ModFoodItems;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractHorseEntity.class)
public class AbstractHorseEntityMixin {

    @WrapOperation(method = "receiveFood", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z", ordinal = 3))
    private boolean receiveFoodLettuce(ItemStack instance, Item item, Operation<Boolean> original) {
        return original.call(instance, item) || instance.isOf(ModFoodItems.LETTUCE);
    }

    @WrapOperation(method = "receiveFood", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z", ordinal = 5))
    private boolean receiveFoodHorseFeed(ItemStack instance, Item item, Operation<Boolean> original) {
        return original.call(instance, item) || instance.isOf(ModFoodItems.SACK_OF_HORSEFEED);
    }
}
