package net.jukoz.me.gui.artisantable;

import net.jukoz.me.gui.ModScreenHandlers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class ArtisanTableScreenHandler extends ScreenHandler {

    private final Inventory inventory;

    public ArtisanTableScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(7));
    }

    public ArtisanTableScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(ModScreenHandlers.ARTISAN_SCREEN_HANDLER, syncId);
        checkSize(inventory, 7);
        this.inventory = inventory;
        inventory.onOpen(playerInventory.player);

        this.addSlot(new Slot(inventory, 0, 30, 11));
        this.addSlot(new Slot(inventory, 1, 8, 33));
        this.addSlot(new Slot(inventory, 2, 30, 33));
        this.addSlot(new Slot(inventory, 3, 52, 33));
        this.addSlot(new Slot(inventory, 4, 19, 55));
        this.addSlot(new Slot(inventory, 5, 41, 55));
        this.addSlot(new Slot(inventory, 6, 179, 28));

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);

    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        ItemStack stack = ItemStack.EMPTY;
        Slot invSlot = this.slots.get(slot);

        if(invSlot.hasStack()) {
            ItemStack originalStack = invSlot.getStack();
            if(slot < this.inventory.size()) {
                if(!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size() - 1, false)) {
                return ItemStack.EMPTY;
            }
            if (originalStack.isEmpty()) {
                invSlot.setStack(ItemStack.EMPTY);
            } else {
                invSlot.markDirty();
            }
        }
        return stack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }


    private void addPlayerInventory(PlayerInventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }
}
