package net.sevenstars.middleearth.recipe;

import net.sevenstars.middleearth.item.ModDataComponentTypes;
import net.sevenstars.middleearth.item.items.armor.CustomAnimalArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShearsItem;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;


public class MountArmorAddonRemovalRecipe extends SpecialCraftingRecipe {

    public MountArmorAddonRemovalRecipe(CraftingRecipeCategory category) {
        super(category);
    }

    @Override
    public DefaultedList<ItemStack> getRecipeRemainders(CraftingRecipeInput input) {
        DefaultedList<ItemStack> defaultedList = DefaultedList.ofSize(input.size(), ItemStack.EMPTY);

        for(int i = 0; i < defaultedList.size(); ++i) {
            ItemStack itemStack = input.getStackInSlot(i);
            if (!itemStack.getItem().getRecipeRemainder().isEmpty()) {
                defaultedList.set(i, new ItemStack(itemStack.getItem().getRecipeRemainder().getItem()));
            } else if (itemStack.getItem() instanceof ShearsItem) {
                defaultedList.set(i, itemStack.copyWithCount(1));
                break;
            }
        }
        return defaultedList;
    }

    @Override
    public boolean matches(CraftingRecipeInput input, World world) {
        ItemStack itemStackArmor = ItemStack.EMPTY;
        ItemStack itemStackShears = ItemStack.EMPTY;

        for(int i = 0; i < input.size(); ++i) {
            ItemStack itemStack2 = input.getStackInSlot(i);
            if (!itemStack2.isEmpty()) {
                if (itemStack2.getItem() instanceof CustomAnimalArmorItem && itemStack2.get(ModDataComponentTypes.MOUNT_ARMOR_DATA) != null) {
                    if (!itemStackArmor.isEmpty()) {
                        return false;
                    }
                    itemStackArmor = itemStack2;
                } else {
                    if (!itemStack2.isOf(Items.SHEARS)) {
                        return false;
                    }
                    itemStackShears = itemStack2;
                }
            }
        }
        return !itemStackArmor.isEmpty() && !itemStackShears.isEmpty();
    }

    @Override
    public ItemStack craft(CraftingRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        ItemStack itemStack = ItemStack.EMPTY;

        for(int i = 0; i < input.size(); ++i) {
            ItemStack itemStack2 = input.getStackInSlot(i);
            if (!itemStack2.isEmpty()) {
                if (itemStack2.getItem() instanceof CustomAnimalArmorItem && itemStack2.get(ModDataComponentTypes.MOUNT_ARMOR_DATA) != null) {
                    if (!itemStack.isEmpty()) {
                        return ItemStack.EMPTY;
                    }

                    itemStack = itemStack2.copy();
                } else {
                    if (!itemStack2.isOf(Items.SHEARS)) {
                        return ItemStack.EMPTY;
                    }
                }
            }
        }

        if (!itemStack.isEmpty()) {
            itemStack.remove(ModDataComponentTypes.MOUNT_ARMOR_DATA);
            return itemStack;
        } else {
            return ItemStack.EMPTY;
        }
    }

    public boolean fits(int width, int height) {
        return width * height >= 2;
    }

    public RecipeSerializer<? extends SpecialCraftingRecipe> getSerializer() {
        return ModRecipeSerializer.CUSTOM_MOUNT_ARMOR_ADDON_REMOVAL;
    }
}
