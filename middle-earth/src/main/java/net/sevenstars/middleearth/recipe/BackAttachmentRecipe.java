package net.sevenstars.middleearth.recipe;

import net.minecraft.component.DataComponentTypes;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.dataComponents.BackAttachmentDataComponent;
import net.sevenstars.middleearth.item.items.armor.BackAttachmentItem;
import net.sevenstars.middleearth.item.items.armor.CustomChestplateItem;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;


public class BackAttachmentRecipe extends SpecialCraftingRecipe {

    public BackAttachmentRecipe(CraftingRecipeCategory category) {
        super(category);
    }

    @Override
    public boolean matches(CraftingRecipeInput input, World world) {
        ItemStack itemStackChest = ItemStack.EMPTY;
        ItemStack itemStackBackAttachment = ItemStack.EMPTY;

        for(int i = 0; i < input.size(); ++i) {
            ItemStack itemStack2 = input.getStackInSlot(i);
            if (!itemStack2.isEmpty()) {
                if (itemStack2.getItem() instanceof CustomChestplateItem) {
                    if (!itemStackChest.isEmpty()) {
                        return false;
                    }
                    if (itemStack2.get(DataComponentTypesME.BACK_ATTACHMENT_DATA) != null){
                        return false;
                    }
                    itemStackChest = itemStack2;
                } else {
                    if (!(itemStack2.getItem() instanceof BackAttachmentItem)) {
                        return false;
                    }
                    itemStackBackAttachment = itemStack2;
                }
            }
        }
        return !itemStackChest.isEmpty() && !itemStackBackAttachment.isEmpty();
    }

    @Override
    public ItemStack craft(CraftingRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        ItemStack itemStack = ItemStack.EMPTY;
        ItemStack backAttachment = ItemStack.EMPTY;

        for(int i = 0; i < input.size(); ++i) {
            ItemStack itemStack2 = input.getStackInSlot(i);
            if (!itemStack2.isEmpty()) {
                if (itemStack2.getItem() instanceof CustomChestplateItem) {
                    if (!itemStack.isEmpty()) {
                        return ItemStack.EMPTY;
                    }
                    itemStack = itemStack2.copy();
                } else {
                    if (!(itemStack2.getItem() instanceof BackAttachmentItem)) {
                        return ItemStack.EMPTY;
                    }
                    backAttachment = itemStack2;
                }
            }
        }

        if (!itemStack.isEmpty()) {
            int color;
            if (backAttachment.get(DataComponentTypes.DYED_COLOR) != null){
                color = backAttachment.get(DataComponentTypes.DYED_COLOR).rgb();
            } else {
                color = 0;
            }
            return BackAttachmentDataComponent.setBackAttachmentWithColor(itemStack,
                    backAttachment.get(DataComponentTypesME.BACK_ATTACHMENT_DATA).backAttachment(),
                    color);
        } else {
            return ItemStack.EMPTY;
        }
    }

    public boolean fits(int width, int height) {
        return width * height >= 2;
    }

    public RecipeSerializer<? extends SpecialCraftingRecipe> getSerializer() {
        return ModRecipeSerializer.CUSTOM_ARMOR_BACK_ATTACHMENT;
    }
}
