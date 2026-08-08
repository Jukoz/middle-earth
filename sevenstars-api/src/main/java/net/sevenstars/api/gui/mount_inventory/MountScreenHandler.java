package net.sevenstars.api.gui.mount_inventory;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Identifier;
import net.sevenstars.api.entity.AbstractMountEntity;
import org.jetbrains.annotations.Nullable;

public class MountScreenHandler extends ScreenHandler {
    private static final Identifier EMPTY_SADDLE_SLOT_TEXTURE = Identifier.ofVanilla("container/slot/saddle");
    private static final Identifier EMPTY_LLAMA_ARMOR_SLOT_TEXTURE = Identifier.ofVanilla("container/slot/llama_armor");
    private static final Identifier EMPTY_HORSE_ARMOR_SLOT_TEXTURE = Identifier.ofVanilla("container/slot/horse_armor");
    private final Inventory inventory;
    private final AbstractMountEntity mount;

    public MountScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, AbstractMountEntity mount, int slotColumnCount) {
        super(null, syncId);

        this.inventory = inventory;
        this.mount = mount;

        inventory.onOpen(playerInventory.player);

        this.addPlayerSlots(playerInventory, 8, 84);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot2 = this.slots.get(slot);
        if (slot2 != null && slot2.hasStack()) {
            ItemStack itemStack2 = slot2.getStack();
            itemStack = itemStack2.copy();
            int i = 2 + this.inventory.size();
            if (slot < i) {
                if (!this.insertItem(itemStack2, i, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (this.getSlot(1).canInsert(itemStack2) && !this.getSlot(1).hasStack()) {
                if (!this.insertItem(itemStack2, 1, 2, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (this.getSlot(0).canInsert(itemStack2) && !this.getSlot(0).hasStack()) {
                if (!this.insertItem(itemStack2, 0, 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (this.inventory.size() == 0 || !this.insertItem(itemStack2, 2, i, false)) {
                int j = i + 27;
                int l = j + 9;
                if (slot >= j && slot < l) {
                    if (!this.insertItem(itemStack2, i, j, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (slot >= i && slot < j) {
                    if (!this.insertItem(itemStack2, j, l, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (!this.insertItem(itemStack2, j, j, false)) {
                    return ItemStack.EMPTY;
                }

                return ItemStack.EMPTY;
            }

            if (itemStack2.isEmpty()) {
                slot2.setStack(ItemStack.EMPTY);
            } else {
                slot2.markDirty();
            }
        }

        return itemStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return !this.mount.areInventoriesDifferent(this.inventory)
                && this.inventory.canPlayerUse(player)
                && this.mount.isAlive()
                && player.canInteractWithEntity(this.mount, 4.0);
    }

    @Override
    public void onClosed(PlayerEntity player) {
        super.onClosed(player);
        this.inventory.onClose(player);
    }
}
