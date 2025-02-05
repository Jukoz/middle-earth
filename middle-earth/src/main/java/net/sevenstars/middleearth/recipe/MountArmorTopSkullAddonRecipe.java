package net.sevenstars.middleearth.recipe;

import net.sevenstars.middleearth.item.ModDataComponentTypes;
import net.sevenstars.middleearth.item.ModEquipmentItems;
import net.sevenstars.middleearth.item.dataComponents.MountArmorAddonComponent;
import net.sevenstars.middleearth.item.items.armor.CustomAnimalArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;

public class MountArmorTopSkullAddonRecipe extends SpecialCraftingRecipe {
    public MountArmorTopSkullAddonRecipe(CraftingRecipeCategory category) {
        super(category);
    }

    @Override
    public boolean matches(CraftingRecipeInput input, World world) {
        ItemStack itemStackArmor = ItemStack.EMPTY;
        ItemStack itemStackStick = ItemStack.EMPTY;
        ItemStack itemStackSkull= ItemStack.EMPTY;

        for(int i = 0; i < input.getSize(); ++i) {
            ItemStack itemStack2 = input.getStackInSlot(i);
            if (!itemStack2.isEmpty()) {
                if (itemStack2.isOf(ModEquipmentItems.WARG_MORDOR_PLATE_ARMOR) || itemStack2.isOf(ModEquipmentItems.WARG_GUNDABAD_PLATE_ARMOR)) {
                    if (!itemStackArmor.isEmpty()) {
                        return false;
                    }
                    itemStackArmor = itemStack2;
                }
                else if (itemStack2.isOf(Items.STICK)) {
                    if (!itemStackStick.isEmpty()) {
                        return false;
                    }
                    itemStackStick = itemStack2;
                }
                else if (itemStack2.isOf(Items.SKELETON_SKULL)) {
                    if (!itemStackSkull.isEmpty()) {
                        return false;
                    }
                    itemStackSkull = itemStack2;
                }
            }
        }
        return !itemStackArmor.isEmpty() && !itemStackStick.isEmpty() && !itemStackSkull.isEmpty();
    }

    @Override
    public ItemStack craft(CraftingRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        ItemStack itemStack = ItemStack.EMPTY;

        for(int i = 0; i < input.getSize(); ++i) {
            ItemStack itemStack2 = input.getStackInSlot(i);
            if (!itemStack2.isEmpty()) {
                if (itemStack2.getItem() instanceof CustomAnimalArmorItem) {
                    if (!itemStack.isEmpty()) {
                        return ItemStack.EMPTY;
                    }
                    itemStack = itemStack2.copy();
                }
            }
        }

        boolean sideArmorAddons = false;
        if(itemStack.get(ModDataComponentTypes.MOUNT_ARMOR_DATA) != null) {
            sideArmorAddons = itemStack.get(ModDataComponentTypes.MOUNT_ARMOR_DATA).sideArmorAddon();
        }

        ItemStack output = itemStack.copyWithCount(1);

        output.set(ModDataComponentTypes.MOUNT_ARMOR_DATA, new MountArmorAddonComponent(true, sideArmorAddons));

        return output;
    }

    @Override
    public boolean fits(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializer.CUSTOM_MOUNT_ARMOR_TOP_SKULL_ADDON;
    }
}
