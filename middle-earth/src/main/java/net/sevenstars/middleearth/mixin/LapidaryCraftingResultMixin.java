package net.sevenstars.middleearth.mixin;

import net.sevenstars.middleearth.recipe.lapidary.LapidaryKnowledge;

import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.RecipeUnlocker;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;

/** Lapidary knowledge recipes give nothing to players who have not learned them. */
@Mixin(CraftingResultInventory.class)
public abstract class LapidaryCraftingResultMixin implements RecipeUnlocker {
    @Override
    public boolean shouldCraftRecipe(ServerPlayerEntity player, RecipeEntry<?> recipe) {
        return LapidaryKnowledge.canMake(player, recipe.id()) && RecipeUnlocker.super.shouldCraftRecipe(player, recipe);
    }
}
