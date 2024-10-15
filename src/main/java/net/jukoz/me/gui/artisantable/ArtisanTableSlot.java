package net.jukoz.me.gui.artisantable;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class ArtisanTableSlot extends Slot {
    private boolean enabled = true;

    public ArtisanTableSlot(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        if(!isEnabled()) return false;
        return super.canInsert(stack);
    }
}
