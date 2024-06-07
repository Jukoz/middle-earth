package net.jukoz.me.recipe;

import com.google.common.collect.Lists;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.item.dataComponents.CustomDyeableDataComponent;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.List;

public class CustomArmorDyeRecipe extends SpecialCraftingRecipe {

    public CustomArmorDyeRecipe(CraftingRecipeCategory craftingRecipeCategory) {
        super(craftingRecipeCategory);
    }

    public boolean matches(RecipeInputInventory recipeInputInventory, World world) {
        ItemStack itemStack = ItemStack.EMPTY;
        List<ItemStack> list = Lists.newArrayList();

        for(int i = 0; i < recipeInputInventory.size(); ++i) {
            ItemStack itemStack2 = recipeInputInventory.getStack(i);
            if (!itemStack2.isEmpty()) {
                if (itemStack2.isIn(TagKey.of(RegistryKeys.ITEM, new Identifier(MiddleEarth.MOD_ID, "dyeable")))) {
                    if (!itemStack.isEmpty()) {
                        return false;
                    }

                    itemStack = itemStack2;
                } else {
                    if (!(itemStack2.getItem() instanceof DyeItem)) {
                        return false;
                    }

                    list.add(itemStack2);
                }
            }
        }

        return !itemStack.isEmpty() && !list.isEmpty();
    }

    public ItemStack craft(RecipeInputInventory recipeInputInventory, RegistryWrapper.WrapperLookup wrapperLookup) {
        List<DyeItem> list = Lists.newArrayList();
        ItemStack itemStack = ItemStack.EMPTY;

        for(int i = 0; i < recipeInputInventory.size(); ++i) {
            ItemStack itemStack2 = recipeInputInventory.getStack(i);
            if (!itemStack2.isEmpty()) {
                if (itemStack2.isIn(TagKey.of(RegistryKeys.ITEM, new Identifier(MiddleEarth.MOD_ID, "dyeable")))) {
                    if (!itemStack.isEmpty()) {
                        return ItemStack.EMPTY;
                    }

                    itemStack = itemStack2.copy();
                } else {
                    Item var8 = itemStack2.getItem();
                    if (!(var8 instanceof DyeItem)) {
                        return ItemStack.EMPTY;
                    }

                    DyeItem dyeItem = (DyeItem)var8;
                    list.add(dyeItem);
                }
            }
        }

        if (!itemStack.isEmpty() && !list.isEmpty()) {
            return CustomDyeableDataComponent.setColor(itemStack, list);
        } else {
            return ItemStack.EMPTY;
        }
    }

    public boolean fits(int width, int height) {
        return width * height >= 2;
    }

    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializer.CUSTOM_ARMOR_DYE;
    }
}
