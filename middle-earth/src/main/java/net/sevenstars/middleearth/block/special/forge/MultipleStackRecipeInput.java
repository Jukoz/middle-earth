package net.sevenstars.middleearth.block.special.forge;

import java.util.List;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record MultipleStackRecipeInput(List<ItemStack> items) implements RecipeInput {

    @Override
    public ItemStack getItem(int slot) {
        if (slot >= size()) {
            throw new IllegalArgumentException("No item for index " + slot);
        }
        return this.items.get(slot);
    }

    @Override
    public int size() {
        return items.size();

    }
}