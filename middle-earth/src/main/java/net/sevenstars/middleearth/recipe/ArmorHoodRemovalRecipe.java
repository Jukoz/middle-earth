package net.sevenstars.middleearth.recipe;

import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.ModDataComponentTypes;
import net.sevenstars.middleearth.item.dataComponents.CustomDyeableDataComponent;
import net.sevenstars.middleearth.item.items.armor.CustomHelmetItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShearsItem;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;


public class ArmorHoodRemovalRecipe extends SpecialCraftingRecipe {

    public ArmorHoodRemovalRecipe(CraftingRecipeCategory category) {
        super(category);
    }


    @Override
    public DefaultedList<ItemStack> getRemainder(CraftingRecipeInput input) {
        DefaultedList<ItemStack> defaultedList = DefaultedList.ofSize(input.getSize(), ItemStack.EMPTY);

        for(int i = 0; i < defaultedList.size(); ++i) {
            ItemStack itemStack = input.getStackInSlot(i);
            if (itemStack.getItem().hasRecipeRemainder()) {
                defaultedList.set(i, new ItemStack(itemStack.getItem().getRecipeRemainder()));
            } else if (itemStack.getItem() instanceof ShearsItem) {
                defaultedList.set(i, itemStack.copyWithCount(1));
            }else if (itemStack.get(ModDataComponentTypes.HOOD_DATA) != null){
                ItemStack hood = new ItemStack(Registries.ITEM.get(Identifier.of(MiddleEarth.MOD_ID, itemStack.get(ModDataComponentTypes.HOOD_DATA).hood().getName())));
                hood.set(ModDataComponentTypes.HOOD_DATA, itemStack.get(ModDataComponentTypes.HOOD_DATA));
                hood.set(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(itemStack.get(ModDataComponentTypes.HOOD_DATA).hoodColor()));
                defaultedList.set(i, hood);
            }
        }

        return defaultedList;
    }

    @Override
    public boolean matches(CraftingRecipeInput input, World world) {
        ItemStack itemStackHelmet = ItemStack.EMPTY;
        ItemStack itemStackHood = ItemStack.EMPTY;

        for(int i = 0; i < input.getSize(); ++i) {
            ItemStack itemStack2 = input.getStackInSlot(i);
            if (!itemStack2.isEmpty()) {
                if (itemStack2.getItem() instanceof CustomHelmetItem && itemStack2.get(ModDataComponentTypes.HOOD_DATA) != null) {
                    if (!itemStackHelmet.isEmpty()) {
                        return false;
                    }
                    itemStackHelmet = itemStack2;
                } else {
                    if (!itemStack2.isOf(Items.SHEARS)) {
                        return false;
                    }
                    itemStackHood = itemStack2;
                }
            }
        }
        return !itemStackHelmet.isEmpty() && !itemStackHood.isEmpty();
    }

    @Override
    public ItemStack craft(CraftingRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        ItemStack itemStack = ItemStack.EMPTY;

        for(int i = 0; i < input.getSize(); ++i) {
            ItemStack itemStack2 = input.getStackInSlot(i);
            if (!itemStack2.isEmpty()) {
                if (itemStack2.getItem() instanceof CustomHelmetItem && itemStack2.get(ModDataComponentTypes.HOOD_DATA) != null) {
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
            itemStack.remove(ModDataComponentTypes.HOOD_DATA);
            return itemStack;
        } else {
            return ItemStack.EMPTY;
        }
    }

    public boolean fits(int width, int height) {
        return width * height >= 2;
    }

    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializer.CUSTOM_ARMOR_HOOD_REMOVAL;
    }
}
