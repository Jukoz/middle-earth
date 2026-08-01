package net.sevenstars.middleearth.gui.sack;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;

public class SackScreenHandler extends ScreenHandler {
	private static final int CONTAINER_SIZE = 9;
	private static final int INVENTORY_START = 9;
	private static final int INVENTORY_END = 36;
	private static final int HOTBAR_START = 36;
	private static final int HOTBAR_END = 45;
	private final Inventory inventory;

	public SackScreenHandler(int syncId, PlayerInventory playerInventory) {
		this(syncId, playerInventory, new SimpleInventory(9));
	}

	public SackScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
		super(ScreenHandlerType.GENERIC_3X3, syncId);
		checkSize(inventory, CONTAINER_SIZE);
		this.inventory = inventory;
		inventory.onOpen(playerInventory.player);
		this.add3x3Slots(inventory, 62, 17);
		this.addPlayerSlots(playerInventory, 8, 84);
	}

	protected void add3x3Slots(Inventory inventory, int x, int y) {
		for(int i = 0; i < 3; ++i) {
			for(int j = 0; j < 3; ++j) {
				int k = j + i * 3;
				this.addSlot(new SackSlot(inventory, k, x + j * 18, y + i * 18));
			}
		}
	}

	public boolean canUse(PlayerEntity player) {
		return this.inventory.canPlayerUse(player);
	}

	public ItemStack quickMove(PlayerEntity player, int slot) {
		ItemStack itemStack = ItemStack.EMPTY;
		Slot slot2 = (Slot)this.slots.get(slot);
		if (slot2 != null && slot2.hasStack()) {
			ItemStack itemStack2 = slot2.getStack();
			itemStack = itemStack2.copy();
			if (slot < 9) {
				if (!this.insertItem(itemStack2, INVENTORY_START, HOTBAR_END, true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.insertItem(itemStack2, 0, INVENTORY_START, false)) {
				return ItemStack.EMPTY;
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

	public void onClosed(PlayerEntity player) {
		super.onClosed(player);
		this.inventory.onClose(player);
	}
}
