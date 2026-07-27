package net.sevenstars.middleearth.gui.forge;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class ForgeOutputSlot extends Slot {
    private final Player player;
    private int amount;

    private final boolean isEnabled;

    public ForgeOutputSlot(Player player, Container inventory, int index, int x, int y, boolean isEnabled) {
        super(inventory, index, x, y);
        this.player = player;
        this.isEnabled = isEnabled;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return false;
    }

    @Override
    public ItemStack remove(int amount) {
        if (this.hasItem()) {
            this.amount += Math.min(amount, this.getItem().getCount());
        }
        return super.remove(amount);
    }

    @Override
    public void onTake(Player player, ItemStack stack) {
        this.checkTakeAchievements(stack);
        super.onTake(player, stack);
    }

    @Override
    protected void onQuickCraft(ItemStack stack, int amount) {
        this.amount += amount;
        this.checkTakeAchievements(stack);
    }

    @Override
    public boolean isActive() {
        return isEnabled;
    }

    @Override
    public boolean mayPickup(Player playerEntity) {
        return isEnabled;
    }
}
