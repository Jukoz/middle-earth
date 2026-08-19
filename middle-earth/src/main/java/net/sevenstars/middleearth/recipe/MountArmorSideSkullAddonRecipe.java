package net.sevenstars.middleearth.recipe;

import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.dataComponents.MountArmorAddonComponent;
import net.sevenstars.middleearth.utils.ItemTagsME;

public class MountArmorSideSkullAddonRecipe extends CustomRecipe {
    public MountArmorSideSkullAddonRecipe(CraftingBookCategory category) {
        super(category);
    }

    @Override
    public boolean matches(CraftingInput input, Level world) {
        ItemStack itemStackArmor = ItemStack.EMPTY;
        ItemStack itemStackString = ItemStack.EMPTY;
        ItemStack itemStackSkull= ItemStack.EMPTY;

        for(int i = 0; i < input.size(); ++i) {
            ItemStack itemStack2 = input.getItem(i);
            if (!itemStack2.isEmpty()) {
                if (itemStack2.is(ItemTagsME.WARG_ARMORS)) {
                    if (!itemStackArmor.isEmpty()) {
                        return false;
                    }
                    MountArmorAddonComponent data = itemStack2.get(DataComponentTypesME.MOUNT_ARMOR_DATA);
                    if (data != null && data.sideArmorAddon()) {
                        return false;
                    }
                    itemStackArmor = itemStack2;
                }
                else if (itemStack2.is(Items.STRING)) {
                    if (!itemStackString.isEmpty()) {
                        return false;
                    }
                    itemStackString = itemStack2;
                }
                else if (itemStack2.is(Items.SKELETON_SKULL)) {
                    if (!itemStackSkull.isEmpty()) {
                        return false;
                    }
                    itemStackSkull = itemStack2;
                } else {
                    return false;
                }
            }
        }
        return !itemStackArmor.isEmpty() && !itemStackString.isEmpty() && !itemStackSkull.isEmpty();
    }

    @Override
    public ItemStack assemble(CraftingInput input, HolderLookup.Provider lookup) {
        ItemStack itemStack = ItemStack.EMPTY;

        for(int i = 0; i < input.size(); ++i) {
            ItemStack itemStack2 = input.getItem(i);
            if (!itemStack2.isEmpty()) {
                if (itemStack2.is(ItemTagsME.WARG_ARMORS)) {
                    if (!itemStack.isEmpty()) {
                        return ItemStack.EMPTY;
                    }
                    itemStack = itemStack2.copy();
                }
            }
        }

        boolean topArmorAddons = false;
        if(itemStack.get(DataComponentTypesME.MOUNT_ARMOR_DATA) != null) {
            topArmorAddons = itemStack.get(DataComponentTypesME.MOUNT_ARMOR_DATA).topArmorAddon();
        }

        ItemStack output = itemStack.copyWithCount(1);

        output.set(DataComponentTypesME.MOUNT_ARMOR_DATA, new MountArmorAddonComponent(topArmorAddons, true));

        return output;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 3;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializer.CUSTOM_MOUNT_ARMOR_SIDE_SKULL_ADDON;
    }
}
