package net.sevenstars.middleearth.gui.forge;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class ForgeAlloyingFuelSlot extends Slot {
    private final ForgeAlloyingScreenHandler handler;

    public ForgeAlloyingFuelSlot(Inventory inventory, ForgeAlloyingScreenHandler handler, int index, int x, int y) {
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
