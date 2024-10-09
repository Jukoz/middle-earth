package net.jukoz.me.block.special.forge;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.input.RecipeInput;

import java.util.List;

public record MultipleStackRecipeInput(List<ItemStack> items) implements RecipeInput {

    @Override
    public ItemStack getStackInSlot(int slot) {
        if (slot >= getSize()) {
            throw new IllegalArgumentException("No item for index " + slot);
        }
        return this.items.get(slot);
    }

    @Override
    public int getSize() {
        return items.size();
    }
}