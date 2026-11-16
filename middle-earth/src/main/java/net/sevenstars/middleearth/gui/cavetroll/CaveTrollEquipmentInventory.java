package net.sevenstars.middleearth.gui.cavetroll;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.sevenstars.middleearth.entity.beasts.cave_troll.CaveTrollEntity;

/** The troll's saddle, club and banner (body slot) as an inventory. */
public class CaveTrollEquipmentInventory implements Inventory {
    public static final int SADDLE = 0;
    public static final int WEAPON = 1;
    public static final int BANNER = 2;

    private static final EquipmentSlot[] SLOTS = {EquipmentSlot.SADDLE, EquipmentSlot.MAINHAND, EquipmentSlot.BODY};

    private final CaveTrollEntity troll;

    public CaveTrollEquipmentInventory(CaveTrollEntity troll) {
        this.troll = troll;
    }

    @Override
    public int size() {
        return SLOTS.length;
    }

    @Override
    public boolean isEmpty() {
        return getStack(SADDLE).isEmpty() && getStack(WEAPON).isEmpty() && getStack(BANNER).isEmpty();
    }

    @Override
    public ItemStack getStack(int slot) {
        return troll.getEquippedStack(SLOTS[slot]);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        ItemStack stack = getStack(slot);
        return stack.isEmpty() ? ItemStack.EMPTY : stack.split(amount);
    }

    @Override
    public ItemStack removeStack(int slot) {
        ItemStack stack = getStack(slot).copy();
        troll.equipStack(SLOTS[slot], ItemStack.EMPTY);
        return stack;
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        troll.equipStack(SLOTS[slot], stack);
    }

    @Override
    public void markDirty() {
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return troll.isAlive() && troll.distanceTo(player) < 8.0f;
    }

    @Override
    public void clear() {
        for(EquipmentSlot slot : SLOTS) {
            troll.equipStack(slot, ItemStack.EMPTY);
        }
    }
}
