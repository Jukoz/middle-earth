package net.jukoz.me.block.special.shapingAnvil;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.input.RecipeInput;

import java.util.List;

public record SingleStackRecipeInput(ItemStack item) implements RecipeInput {

    @Override
    public ItemStack getStackInSlot(int slot) {
        if (slot >= getSize()) {
            throw new IllegalArgumentException("No item for index " + slot);
        }
        return this.item;
    }

    @Override
    public int getSize() {
        return 1;
    }
}