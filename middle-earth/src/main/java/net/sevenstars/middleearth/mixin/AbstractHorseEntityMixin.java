package net.sevenstars.middleearth.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.item.FoodItemsME;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractHorse.class)
public abstract class AbstractHorseEntityMixin {

    @Shadow
    protected SimpleContainer inventory;

    @WrapOperation(method = "handleEating(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/ItemStack;)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z", ordinal = 3))
    private boolean receiveFoodLettuce(ItemStack instance, Item item, Operation<Boolean> original) {
        return original.call(instance, item) || instance.is(FoodItemsME.LETTUCE);
    }

    @WrapOperation(method = "handleEating(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/ItemStack;)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z", ordinal = 5))
    private boolean receiveFoodHorseFeed(ItemStack instance, Item item, Operation<Boolean> original) {
        return original.call(instance, item) || instance.is(FoodItemsME.SACK_OF_HORSEFEED);
    }

    @Inject(at = @At("HEAD"), method = "dropEquipment()V")
    private void preserveNpcMountInventory(CallbackInfo ci) {
        Entity passenger = ((AbstractHorse) (Object) this).getControllingPassenger();
        if (passenger instanceof NpcEntity) {
            this.inventory.clearContent();
        }
    }
}
