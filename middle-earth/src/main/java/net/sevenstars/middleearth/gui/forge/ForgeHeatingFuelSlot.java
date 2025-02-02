package net.sevenstars.middleearth.gui.forge;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class ForgeHeatingFuelSlot extends Slot {
    private final ForgeHeatingScreenHandler handler;

    public ForgeHeatingFuelSlot(Inventory inventory, ForgeHeatingScreenHandler handler, int index, int x, int y) {
        super(inventory, index, x, y);
        this.handler = handler;
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return handler.world.getFuelRegistry().isFuel(stack);
    }

    @Override
    public int getMaxItemCount(ItemStack stack) {
        return super.getMaxItemCount(stack);
    }
}
