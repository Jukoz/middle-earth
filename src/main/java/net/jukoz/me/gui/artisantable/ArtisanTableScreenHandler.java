package net.jukoz.me.gui.artisantable;

import net.jukoz.me.block.special.artisantable.ArtisanTableEntity;
import net.jukoz.me.gui.ModScreenHandlers;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.StonecutterScreenHandler;
import net.minecraft.screen.slot.Slot;

public class ArtisanTableScreenHandler extends ScreenHandler {

    private final Inventory inventory;
    public final ArtisanTableEntity blockEntity;

    public ArtisanTableScreenHandler(int syncId, PlayerInventory playerInventory, PacketByteBuf buf) {
        this(syncId, playerInventory, playerInventory.player.getWorld().getBlockEntity(buf.readBlockPos()));
    }

    public ArtisanTableScreenHandler(int syncId, PlayerInventory playerInventory, BlockEntity blockEntity) {
        super(ModScreenHandlers.ARTISAN_SCREEN_HANDLER, syncId);
        checkSize((Inventory) blockEntity, 7);
        this.inventory = ((Inventory)blockEntity);
        inventory.onOpen(playerInventory.player);
        this.blockEntity = ((ArtisanTableEntity)blockEntity);

        this.addSlot(new Slot(inventory, 0, 31, 12));
        this.addSlot(new Slot(inventory, 1, 9, 34));
        this.addSlot(new Slot(inventory, 2, 31, 34));
        this.addSlot(new Slot(inventory, 3, 53, 34));
        this.addSlot(new Slot(inventory, 4, 20, 56));
        this.addSlot(new Slot(inventory, 5, 42, 56));
        this.addSlot(new Slot(inventory, 6, 184, 33));

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if(slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if(invSlot < this.inventory.size()) {
                if(!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }
            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }
        return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }


    private void addPlayerInventory(PlayerInventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 36 + j * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 36 + i * 18, 142));
        }
    }
}
