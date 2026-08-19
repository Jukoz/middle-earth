package net.sevenstars.middleearth.gui.sack;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.sevenstars.middleearth.item.DecorativeItemsME;

public class SackSlot extends Slot {
    public SackSlot(Container inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        if(stack.is(DecorativeItemsME.SACK) || !stack.getItem().canFitInsideContainerItems()) return false;
        return super.mayPlace(stack);
    }
}
