package net.sevenstars.middleearth.recipe.lapidary;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.recipe.RecipeSerializerRegistryME;

/**
 * Glasses or a Dwarven Monocle with a set of focusing lenses, anywhere in a crafting grid: the eyewear comes back
 * with the lenses fitted (see {@link LapidaryEyewear}). The lenses are used up. Eyewear with Refined lenses can
 * take Exquisite ones; nothing lower than what is fitted matches.
 */
public class EyewearLensesRecipe extends SpecialCraftingRecipe {
    public EyewearLensesRecipe(CraftingRecipeCategory category) {
        super(category);
    }

    @Override
    public boolean matches(CraftingRecipeInput input, World world) {
        return !craft(input, null).isEmpty();
    }

    @Override
    public ItemStack craft(CraftingRecipeInput input, RegistryWrapper.WrapperLookup registries) {
        ItemStack eyewear = ItemStack.EMPTY;
        int lenses = 0;
        for (ItemStack stack : input.getStacks()) {
            if (stack.isEmpty()) {
                continue;
            }
            if (LapidaryEyewear.isEyewear(stack) && eyewear.isEmpty()) {
                eyewear = stack;
            } else if (LapidaryEyewear.lensTier(stack.getItem()) > 0 && lenses == 0) {
                lenses = LapidaryEyewear.lensTier(stack.getItem());
            } else {
                return ItemStack.EMPTY;
            }
        }
        if (eyewear.isEmpty() || lenses <= LapidaryEyewear.fitted(eyewear)) {
            return ItemStack.EMPTY;
        }
        ItemStack result = eyewear.copyWithCount(1);
        result.set(DataComponentTypesME.EYEWEAR_LENSES, lenses);
        return result;
    }

    @Override
    public RecipeSerializer<EyewearLensesRecipe> getSerializer() {
        return RecipeSerializerRegistryME.EYEWEAR_LENSES;
    }
}
