package net.sevenstars.middleearth.recipe;

import com.google.common.collect.Lists;
import net.sevenstars.middleearth.item.dataComponents.CustomDyeableDataComponent;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;

import java.util.ArrayList;

public class CustomArmorDyeRecipe extends SpecialCraftingRecipe {
    public CustomArmorDyeRecipe(CraftingRecipeCategory craftingRecipeCategory) {
        super(craftingRecipeCategory);
    }

    @Override
    public boolean matches(CraftingRecipeInput craftingRecipeInput, World world) {
        ItemStack itemStack = ItemStack.EMPTY;
        ArrayList<ItemStack> list = Lists.newArrayList();

        for (int i = 0; i < craftingRecipeInput.size(); ++i) {
            ItemStack itemStack2 = craftingRecipeInput.getStackInSlot(i);
            if (itemStack2.isEmpty()) continue;
            if (itemStack2.isIn(ModTags.DYEABLE)) {
                if (!itemStack.isEmpty()) {
                    return false;
                }
                itemStack = itemStack2;
                continue;
            }
            if (itemStack2.getItem() instanceof DyeItem) {
                list.add(itemStack2);
                continue;
            }
            return false;
        }
        return !itemStack.isEmpty() && !list.isEmpty();
    }

    @Override
    public ItemStack craft(CraftingRecipeInput craftingRecipeInput, RegistryWrapper.WrapperLookup lookup) {
        ArrayList<DyeItem> list = Lists.newArrayList();
        ItemStack itemStack = ItemStack.EMPTY;

        for (int i = 0; i < craftingRecipeInput.size(); ++i) {
            ItemStack itemStack2 = craftingRecipeInput.getStackInSlot(i);
            if (itemStack2.isEmpty()) continue;
            if (itemStack2.isIn(ModTags.DYEABLE)) {
                if (!itemStack.isEmpty()) {
                    return ItemStack.EMPTY;
                }
                itemStack = itemStack2.copy();
                continue;
            }
            Item item = itemStack2.getItem();
            if (item instanceof DyeItem dyeItem) {
                list.add(dyeItem);
                continue;
            }
            return ItemStack.EMPTY;
        }
        if (itemStack.isEmpty() || list.isEmpty()) {
            return ItemStack.EMPTY;
        }
        return CustomDyeableDataComponent.setColor(itemStack, list);
    }

    public boolean fits(int width, int height) {
        return width * height >= 2;
    }

    public RecipeSerializer<? extends SpecialCraftingRecipe> getSerializer() {
        return ModRecipeSerializer.CUSTOM_ARMOR_DYE;
    }
}
