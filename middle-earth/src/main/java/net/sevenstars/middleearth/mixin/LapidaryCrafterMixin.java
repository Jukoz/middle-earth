package net.sevenstars.middleearth.mixin;

import net.sevenstars.middleearth.recipe.lapidary.LapidaryKnowledge;

import java.util.Optional;

import net.minecraft.block.CrafterBlock;
import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/** A crafter has no one who learned the lost arts: it cannot make lapidary knowledge recipes. */
@Mixin(CrafterBlock.class)
public abstract class LapidaryCrafterMixin {
    @Inject(method = "getCraftingRecipe", at = @At("RETURN"), cancellable = true)
    private static void middleearth$noLapidaryRecipes(ServerWorld world, CraftingRecipeInput input,
                                                      CallbackInfoReturnable<Optional<RecipeEntry<CraftingRecipe>>> cir) {
        Optional<RecipeEntry<CraftingRecipe>> recipe = cir.getReturnValue();
        if (recipe.isPresent() && LapidaryKnowledge.isLocked(recipe.get().id())) {
            cir.setReturnValue(Optional.empty());
        }
    }
}
