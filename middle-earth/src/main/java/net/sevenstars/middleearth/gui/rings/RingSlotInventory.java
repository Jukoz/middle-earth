package net.sevenstars.middleearth.gui.rings;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.sevenstars.middleearth.item.items.rings.RingSlot;

/** A one-stack inventory view over the player's worn ring attachment. */
public class RingSlotInventory implements Inventory {
    private final PlayerEntity owner;

    public RingSlotInventory(PlayerEntity owner) {
        this.owner = owner;
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return RingSlot.get(owner).isEmpty();
    }

    @Override
    public ItemStack getStack(int slot) {
        return RingSlot.get(owner);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        ItemStack worn = RingSlot.get(owner).copy();
        ItemStack taken = worn.split(amount);
        RingSlot.set(owner, worn);
        return taken;
    }

    @Override
    public ItemStack removeStack(int slot) {
        ItemStack worn = RingSlot.get(owner);
        RingSlot.set(owner, ItemStack.EMPTY);
        return worn;
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        RingSlot.set(owner, stack);
        if (!stack.isEmpty()) {
            RingSlot.playEquipSound(owner);
        }
    }

    @Override
    public int getMaxCountPerStack() {
        return 1;
    }

    @Override
    public void markDirty() {
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return player == owner;
    }

    @Override
    public void clear() {
        RingSlot.set(owner, ItemStack.EMPTY);
    }
}
