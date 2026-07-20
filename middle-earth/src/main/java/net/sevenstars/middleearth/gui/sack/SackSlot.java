package net.sevenstars.middleearth.gui.sack;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.screen.slot.Slot;
import net.sevenstars.middleearth.item.DecorativeItemsME;

public class SackSlot extends Slot {
    public SackSlot(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        if(stack.isOf(DecorativeItemsME.SACK) || stack.isIn(ItemTags.SHULKER_BOXES)) return false;
        return super.canInsert(stack);
    }
}
