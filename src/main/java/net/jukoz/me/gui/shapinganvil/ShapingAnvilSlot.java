package net.jukoz.me.gui.shapinganvil;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class ShapingAnvilSlot extends Slot {
    private final int MAX_ITEM_COUNT;
    public ShapingAnvilSlot(Inventory inventory, int index, int x, int y) {
        this(inventory, index, x, y, 1);
    }

    public ShapingAnvilSlot(Inventory inventory, int index, int x, int y, int itemCount) {
        super(inventory, index, x, y);
        MAX_ITEM_COUNT = itemCount;
    }

    @Override
    public int getMaxItemCount() {
        return MAX_ITEM_COUNT;
    }

    @Override
    public ItemStack takeStack(int amount) {
        markDirty();
        return super.takeStack(amount);
    }

    @Override
    public ItemStack insertStack(ItemStack stack) {
        markDirty();
        return super.insertStack(stack);
    }

    @Override
    protected void onTake(int amount) {
        markDirty();
        super.onTake(amount);
    }
}
