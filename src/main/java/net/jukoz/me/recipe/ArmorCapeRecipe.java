package net.jukoz.me.recipe;

import net.jukoz.me.item.ModDataComponentTypes;
import net.jukoz.me.item.dataComponents.CapeDataComponent;
import net.jukoz.me.item.items.armor.CapeChestplateItem;
import net.jukoz.me.item.items.armor.CustomChestplateItem;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;


public class ArmorCapeRecipe extends SpecialCraftingRecipe {

    public ArmorCapeRecipe(CraftingRecipeCategory category) {
        super(category);
    }

    @Override
    public boolean matches(CraftingRecipeInput input, World world) {
        ItemStack itemStackChest = ItemStack.EMPTY;
        ItemStack itemStackCape = ItemStack.EMPTY;

        for(int i = 0; i < input.getSize(); ++i) {
            ItemStack itemStack2 = input.getStackInSlot(i);
            if (!itemStack2.isEmpty()) {
                if (itemStack2.getItem() instanceof CustomChestplateItem) {
                    if (!itemStackChest.isEmpty()) {
                        return false;
                    }
                    itemStackChest = itemStack2;
                } else {
                    if (!(itemStack2.getItem() instanceof CapeChestplateItem)) {
                        return false;
                    }
                    itemStackCape = itemStack2;
                }
            }
        }
        return !itemStackChest.isEmpty() && !itemStackCape.isEmpty();
    }

    @Override
    public ItemStack craft(CraftingRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        ItemStack itemStack = ItemStack.EMPTY;
        ItemStack cape = ItemStack.EMPTY;

        for(int i = 0; i < input.getSize(); ++i) {
            ItemStack itemStack2 = input.getStackInSlot(i);
            if (!itemStack2.isEmpty()) {
                if (itemStack2.getItem() instanceof CustomChestplateItem) {
                    if (!itemStack.isEmpty()) {
                        return ItemStack.EMPTY;
                    }
                    itemStack = itemStack2.copy();
                } else {
                    if (!(itemStack2.getItem() instanceof CapeChestplateItem)) {
                        return ItemStack.EMPTY;
                    }
                    cape = itemStack2;
                }
            }
        }

        if (!itemStack.isEmpty()) {
            int color;
            if (cape.get(ModDataComponentTypes.DYE_DATA) != null){
                color = cape.get(ModDataComponentTypes.DYE_DATA).customRgb();
            } else {
                color = 0;
            }
            return CapeDataComponent.setCapeWithColor(itemStack,
                    cape.get(ModDataComponentTypes.CAPE_DATA).cape(),
                    color);
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
