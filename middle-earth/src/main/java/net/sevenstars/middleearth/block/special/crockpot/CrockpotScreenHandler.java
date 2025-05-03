package net.sevenstars.middleearth.block.special.crockpot;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;
import net.sevenstars.middleearth.gui.ModScreenHandlers;
import net.sevenstars.middleearth.gui.forge.ForgeOutputSlot;

public class CrockpotScreenHandler extends ScreenHandler {
    final Inventory inventory;
    private final PropertyDelegate propertyDelegate;
    protected BlockPos pos;

    public CrockpotScreenHandler(int syncId, PlayerInventory playerInventory, BlockPos blockPos) {
        this(syncId, playerInventory, new SimpleInventory(5), new ArrayPropertyDelegate(1));
        this.pos = blockPos;
    }

    public CrockpotScreenHandler(int syncId, PlayerInventory playerInventory,
                                 Inventory inventory, PropertyDelegate propertyDelegate) {
        super(ModScreenHandlers.CROCKPOT_SCREEN_HANDLER, syncId);
        this.inventory = inventory;
        this.propertyDelegate = propertyDelegate;
        this.pos = BlockPos.ORIGIN;

        checkSize(inventory, 5);

        this.addSlot(new Slot(inventory, 0, 32, 34));
        this.addSlot(new Slot(inventory, 1, 50, 34));
        this.addSlot(new Slot(inventory, 2, 32, 16));
        this.addSlot(new Slot(inventory, 3, 50, 16));
        this.addSlot(new ForgeOutputSlot(playerInventory.player, inventory, CrockpotBlockEntity.OUTPUT_SLOT, 116, 35, true));

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);
        inventory.onOpen(playerInventory.player);
        addProperties(propertyDelegate);
    }

    public BlockPos getPos() {
        return pos;
    }

    public boolean isCrafting() {
        return propertyDelegate.get(0) > 0;
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot2 = this.slots.get(slot);
        if (slot2.hasStack()) {
            ItemStack itemStack2 = slot2.getStack();
            itemStack = itemStack2.copy();
            if (slot == CrockpotBlockEntity.OUTPUT_SLOT) {
                if (!this.insertItem(itemStack2, 3, 39, true)) {
                    return ItemStack.EMPTY;
                }
                slot2.onQuickTransfer(itemStack2, itemStack);
            }

            if (itemStack2.isEmpty()) {
                slot2.setStack(ItemStack.EMPTY);
            } else {
                slot2.markDirty();
            }

            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot2.onTakeItem(player, itemStack2);
        }

        return itemStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
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
