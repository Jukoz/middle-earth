package net.sevenstars.middleearth.mixin;

import net.minecraft.component.ComponentType;
import net.sevenstars.middleearth.item.items.shields.CustomShieldItem;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AxeItem.class)
public class AxeItemMixin {
    @Redirect(
            method = "shouldCancelStripAttempt(Lnet/minecraft/item/ItemUsageContext;)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/item/ItemStack;contains(Lnet/minecraft/component/ComponentType;)Z"
            )
    )
    private static boolean shield_api$shouldCancelStripAttempt(ItemStack instance, ComponentType<?> type) {
        return instance.contains(type) || instance.getItem() instanceof CustomShieldItem;
    }
}
