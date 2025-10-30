package net.sevenstars.middleearth.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.item.FoodItemsME;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractHorseEntity.class)
public abstract class AbstractHorseEntityMixin {

    @Shadow
    @Nullable
    public abstract LivingEntity getControllingPassenger();
    @Shadow
    @Nullable
    protected SimpleInventory items;

    @Shadow
    protected abstract void dropInventory(ServerWorld world);

    @WrapOperation(method = "receiveFood", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z", ordinal = 3))
    private boolean receiveFoodLettuce(ItemStack instance, Item item, Operation<Boolean> original) {
        return original.call(instance, item) || instance.isOf(FoodItemsME.LETTUCE);
    }

    @WrapOperation(method = "receiveFood", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z", ordinal = 5))
    private boolean receiveFoodHorseFeed(ItemStack instance, Item item, Operation<Boolean> original) {
        return original.call(instance, item) || instance.isOf(FoodItemsME.SACK_OF_HORSEFEED);
    }

    @Inject(at = @At("HEAD"), method = "dropInventory", cancellable = true)
    protected void dropInventory(ServerWorld world, CallbackInfo ci) {
        if(getControllingPassenger() != null && getControllingPassenger() instanceof NpcEntity){
            this.items.clear();
            ci.cancel();
        }
    }
}
