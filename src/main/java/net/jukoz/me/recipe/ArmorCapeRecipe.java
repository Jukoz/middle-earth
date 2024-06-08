package net.jukoz.me.recipe;

import net.jukoz.me.item.ModDataComponentTypes;
import net.jukoz.me.item.ModEquipmentItems;
import net.jukoz.me.item.dataComponents.CapeDataComponent;
import net.jukoz.me.item.items.CustomChestplateItem;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;


public class ArmorCapeRecipe extends SpecialCraftingRecipe {

    public ArmorCapeRecipe(CraftingRecipeCategory category) {
        super(category);
    }

    public boolean matches(RecipeInputInventory recipeInputInventory, World world) {
        ItemStack itemStackChest = ItemStack.EMPTY;
        ItemStack itemStackCape = ItemStack.EMPTY;

        for(int i = 0; i < recipeInputInventory.size(); ++i) {
            ItemStack itemStack2 = recipeInputInventory.getStack(i);
            if (!itemStack2.isEmpty()) {
                if (itemStack2.getItem() instanceof CustomChestplateItem && !itemStack2.isOf(ModEquipmentItems.CLOAK)) {
                    if (!itemStackChest.isEmpty()) {
                        return false;
                    }
                    itemStackChest = itemStack2;
                } else {
                    if (!itemStack2.isOf(ModEquipmentItems.CLOAK)) {
                        return false;
                    }
                    itemStackCape = itemStack2;
                }
            }
        }
        return !itemStackChest.isEmpty() && !itemStackCape.isEmpty();
    }

    public ItemStack craft(RecipeInputInventory recipeInputInventory, RegistryWrapper.WrapperLookup wrapperLookup) {
        ItemStack itemStack = ItemStack.EMPTY;

        for(int i = 0; i < recipeInputInventory.size(); ++i) {
            ItemStack itemStack2 = recipeInputInventory.getStack(i);
            if (!itemStack2.isEmpty()) {
                if (itemStack2.getItem() instanceof CustomChestplateItem && !itemStack2.isOf(ModEquipmentItems.CLOAK)) {
                    if (!itemStack.isEmpty()) {
                        return ItemStack.EMPTY;
                    }

                    itemStack = itemStack2.copy();
                } else {
                    if (!itemStack2.isOf(ModEquipmentItems.CLOAK)) {
                        return ItemStack.EMPTY;
                    }
                }
            }
        }

        if (!itemStack.isEmpty()) {
            return CapeDataComponent.setCape(itemStack, true, "base_cape");
        } else {
            return ItemStack.EMPTY;
        }
    }

    public boolean fits(int width, int height) {
        return width * height >= 2;
    }

    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializer.CUSTOM_ARMOR_CAPE;
    }
}
