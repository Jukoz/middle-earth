package net.sevenstars.middleearth.gui.shapinganvil;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;

public class ShapingAnvilSlot extends Slot {
    private final int MAX_ITEM_COUNT;
    public ShapingAnvilSlot(Container inventory, int index, int x, int y) {
        this(inventory, index, x, y, 1);
    }

    public ShapingAnvilSlot(Container inventory, int index, int x, int y, int itemCount) {
        super(inventory, index, x, y);
        MAX_ITEM_COUNT = itemCount;
    }

    @Override
    public int getMaxStackSize() {
        return MAX_ITEM_COUNT;
    }
}
