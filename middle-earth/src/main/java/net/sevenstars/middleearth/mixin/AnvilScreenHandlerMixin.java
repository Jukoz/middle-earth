package net.sevenstars.middleearth.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.AnvilScreenHandler;
import net.sevenstars.middleearth.config.ModServerConfigs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AnvilScreenHandler.class)
public class AnvilScreenHandlerMixin {
    @Redirect(method = "updateResult", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/inventory/CraftingResultInventory;setStack(ILnet/minecraft/item/ItemStack;)V"))
    private void interceptAnvilOutput(CraftingResultInventory outputInventory, int slot, ItemStack stack) {
        if (!stack.isEmpty()) {
            var enchants = EnchantmentHelper.getEnchantments(stack);
            for (var entry : enchants.getEnchantmentEntries()) {
                var enchant = entry.getKey();
                if ((enchant.matchesKey(Enchantments.SHARPNESS) && entry.getIntValue() >= ModServerConfigs.SHARPNESS_MAX_LEVEL)
                        || (enchant.matchesKey(Enchantments.POWER) && entry.getIntValue() >= ModServerConfigs.POWER_MAX_LEVEL)) {
                    outputInventory.setStack(slot, ItemStack.EMPTY);
                    return;
                }
            }
        }
        outputInventory.setStack(slot, stack);
    }
}
