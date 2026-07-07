package net.sevenstars.middleearth.gui.forge;

import net.minecraft.inventory.Inventory;
import net.minecraft.screen.slot.Slot;

public class ForgeSlot extends Slot {
    private final int MAX_ITEM_COUNT;
    public ForgeSlot(Inventory inventory, int index, int x, int y) {
        this(inventory, index, x, y, 1);
    }

    public ForgeSlot(Inventory inventory, int index, int x, int y, int itemCount) {
        super(inventory, index, x, y);
        MAX_ITEM_COUNT = itemCount;
    }

    @Override
    public int getMaxItemCount() {
        return MAX_ITEM_COUNT;
    }
}
