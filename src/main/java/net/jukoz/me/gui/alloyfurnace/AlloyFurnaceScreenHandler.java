package net.jukoz.me.gui.alloyfurnace;

import net.jukoz.me.block.special.forge.ForgeBlockEntity;
import net.jukoz.me.gui.ModScreenHandlers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class AlloyFurnaceScreenHandler extends ScreenHandler{
    private final Inventory inventory;
    private final PropertyDelegate propertyDelegate;

    public AlloyFurnaceScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(6), new ArrayPropertyDelegate(3));
    }

    public AlloyFurnaceScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate delegate) {
        super(ModScreenHandlers.ALLOY_SCREEN_HANDLER, syncId);
        checkSize(inventory, 6);
        this.inventory = inventory;
        inventory.onOpen(playerInventory.player);
        this.propertyDelegate = delegate;

        this.addSlot(new AlloyFurnaceFuelSlot(inventory, this, 0, 53, 53));
        this.addSlot(new Slot(inventory, 1, 26, 17));
        this.addSlot(new Slot(inventory, 2, 44, 17));
        this.addSlot(new Slot(inventory, 3, 62, 17));
        this.addSlot(new Slot(inventory, 4, 80, 17));
        this.addSlot(new AlloyFurnaceOutputSlot(playerInventory.player, inventory, 5, 129, 35));

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);

        addProperties(delegate);
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
        return this.inventory.canPlayerUse(player);
    }

    public boolean isCooking() {
        return propertyDelegate.get(1) > 0;
    }

    public float getScaledCooking() {
        int cooking = this.propertyDelegate.get(1);
        int maxCooking = this.propertyDelegate.get(2);
        if(maxCooking == 0) maxCooking = 200;

        return (float) cooking / maxCooking;
    }

    public boolean isCrafting() {
        return propertyDelegate.get(0) > 0;
    }

    public float getScaledProgress() {
        int progress = this.propertyDelegate.get(0);

        return (float) progress / ForgeBlockEntity.MAX_PROGRESS;
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
