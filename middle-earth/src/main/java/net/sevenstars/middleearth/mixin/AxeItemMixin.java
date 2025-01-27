package net.sevenstars.middleearth.mixin;

import net.sevenstars.middleearth.item.items.shields.CustomShieldItem;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AxeItem.class)
public class AxeItemMixin {
    @Redirect(
            method = "shouldCancelStripAttempt(Lnet/minecraft/item/ItemUsageContext;)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"
            )
    )
    private static boolean shield_api$shouldCancelStripAttempt(ItemStack instance, Item item) {
        return instance.isOf(Items.SHIELD) || instance.getItem() instanceof CustomShieldItem;
    }
}
