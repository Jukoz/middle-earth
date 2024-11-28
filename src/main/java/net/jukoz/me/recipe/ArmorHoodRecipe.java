package net.jukoz.me.recipe;

import net.jukoz.me.item.ModDataComponentTypes;
import net.jukoz.me.item.dataComponents.HoodDataComponent;
import net.jukoz.me.item.items.armor.CustomHelmetItem;
import net.jukoz.me.item.items.armor.HoodHelmetItem;
import net.jukoz.me.item.utils.armor.hoods.ModHoodStates;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;


public class ArmorHoodRecipe extends SpecialCraftingRecipe {
    public ArmorHoodRecipe(CraftingRecipeCategory category) {
        super(category);
    }

    @Override
    public boolean matches(CraftingRecipeInput input, World world) {
        ItemStack itemStackHelmet = ItemStack.EMPTY;
        ItemStack itemStackHood = ItemStack.EMPTY;

        for(int i = 0; i < input.getSize(); ++i) {
            ItemStack itemStack2 = input.getStackInSlot(i);
            if (!itemStack2.isEmpty()) {
                if (itemStack2.getItem() instanceof CustomHelmetItem) {
                    if (!itemStackHelmet.isEmpty()) {
                        return false;
                    }
                    itemStackHelmet = itemStack2;
                } else {
                    if (!(itemStack2.getItem() instanceof HoodHelmetItem)) {
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
        ItemStack hood = ItemStack.EMPTY;

        for(int i = 0; i < input.getSize(); ++i) {
            ItemStack itemStack2 = input.getStackInSlot(i);
            if (!itemStack2.isEmpty()) {
                if (itemStack2.getItem() instanceof CustomHelmetItem) {
                    if (!itemStack.isEmpty()) {
                        return ItemStack.EMPTY;
                    }

                    itemStack = itemStack2.copy();
                } else {
                    if (!(itemStack2.getItem() instanceof HoodHelmetItem)) {
                        return ItemStack.EMPTY;
                    }
                    hood = itemStack2;
                }
            }
        }

        if (!itemStack.isEmpty()) {
            int color;
            if (hood.get(ModDataComponentTypes.DYE_DATA) != null){
                color = hood.get(ModDataComponentTypes.DYE_DATA).customRgb();
            } else {
                color = 0;
            }
            if (hood.get(ModDataComponentTypes.HOOD_DATA).hood().getConstantState() == ModHoodStates.DOWN){
                return HoodDataComponent.setHoodWithcolor(itemStack, true, hood.get(ModDataComponentTypes.HOOD_DATA).hood(), color);
            } else {
                return HoodDataComponent.setHoodWithcolor(itemStack, false, hood.get(ModDataComponentTypes.HOOD_DATA).hood(), color);
            }
        } else {
            return ItemStack.EMPTY;
        }
    }

    public boolean fits(int width, int height) {
        return width * height >= 2;
    }

    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializer.CUSTOM_ARMOR_HOOD;
    }
}
