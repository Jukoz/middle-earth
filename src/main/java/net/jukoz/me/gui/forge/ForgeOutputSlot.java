package net.jukoz.me.gui.forge;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class ForgeOutputSlot extends Slot {
    private final PlayerEntity player;
    private int amount;

    private final boolean isEnabled;

    public ForgeOutputSlot(PlayerEntity player, Inventory inventory, int index, int x, int y, boolean isEnabled) {
        super(inventory, index, x, y);
        this.player = player;
        this.isEnabled = isEnabled;
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return false;
    }

    @Override
    public ItemStack takeStack(int amount) {
        if (this.hasStack()) {
            this.amount += Math.min(amount, this.getStack().getCount());
        }
        return super.takeStack(amount);
    }

    @Override
    public void onTakeItem(PlayerEntity player, ItemStack stack) {
        this.onCrafted(stack);
        super.onTakeItem(player, stack);
    }

    @Override
    protected void onCrafted(ItemStack stack, int amount) {
        this.amount += amount;
        this.onCrafted(stack);
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    @Override
    public boolean canTakeItems(PlayerEntity playerEntity) {
        return isEnabled;
    }
}
