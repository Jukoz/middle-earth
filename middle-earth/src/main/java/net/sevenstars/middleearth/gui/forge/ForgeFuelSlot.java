package net.sevenstars.middleearth.gui.forge;

import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class ForgeFuelSlot extends Slot {
    private final ScreenHandler handler;

    public ForgeFuelSlot(Inventory inventory, ScreenHandler handler, int index, int x, int y) {
        super(inventory, index, x, y);
        this.handler = handler;
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return AbstractFurnaceBlockEntity.canUseAsFuel(stack);
    }

    @Override
    public int getMaxItemCount(ItemStack stack) {
        return super.getMaxItemCount(stack);
    }
}
