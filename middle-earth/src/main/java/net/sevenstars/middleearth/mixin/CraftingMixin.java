package net.sevenstars.middleearth.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.sevenstars.middleearth.config.ModServerConfigs;
import net.sevenstars.middleearth.world.dimension.ModDimensions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(CraftingMenu.class)
public class CraftingMixin {

    @ModifyArg(
            method = "slotChangedCraftingGrid(Lnet/minecraft/world/inventory/AbstractContainerMenu;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/inventory/CraftingContainer;Lnet/minecraft/world/inventory/ResultContainer;Lnet/minecraft/world/item/crafting/RecipeHolder;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/inventory/ResultContainer;setItem(ILnet/minecraft/world/item/ItemStack;)V"
            ),
            index = 1
    )
    private static ItemStack filterGoldenFoodResult(ItemStack result, @Local(argsOnly = true) Level level) {
        if (!ModServerConfigs.ENABLE_GOLDEN_FOOD_RECIPES
                && ModDimensions.isInMiddleEarth(level)
                && (result.is(Items.GOLDEN_APPLE) || result.is(Items.GOLDEN_CARROT))) {
            return ItemStack.EMPTY;
        }
        return result;
    }
}
