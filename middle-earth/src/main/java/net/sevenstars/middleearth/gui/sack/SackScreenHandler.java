package net.sevenstars.middleearth.gui.sack;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class SackScreenHandler extends AbstractContainerMenu {
	private static final int CONTAINER_SIZE = 9;
	private static final int INVENTORY_START = 9;
	private static final int INVENTORY_END = 36;
	private static final int HOTBAR_START = 36;
	private static final int HOTBAR_END = 45;
	private final Container inventory;

	public SackScreenHandler(int syncId, Inventory playerInventory) {
		this(syncId, playerInventory, new SimpleContainer(9));
	}

	public SackScreenHandler(int syncId, Inventory playerInventory, Container inventory) {
		super(MenuType.GENERIC_3x3, syncId);
		checkContainerSize(inventory, CONTAINER_SIZE);
		this.inventory = inventory;
		inventory.startOpen(playerInventory.player);
		this.add3x3Slots(inventory, 62, 17);
		this.addPlayerInventorySlots(playerInventory, 8, 84);
	}

	protected void add3x3Slots(Container inventory, int x, int y) {
		for(int i = 0; i < 3; ++i) {
			for(int j = 0; j < 3; ++j) {
				int k = j + i * 3;
				this.addSlot(new SackSlot(inventory, k, x + j * 18, y + i * 18));
			}
		}
	}

	protected void addPlayerInventorySlots(Inventory playerInventory, int x, int y) {
		for (int row = 0; row < 3; ++row) {
			for (int column = 0; column < 9; ++column) {
				this.addSlot(new Slot(playerInventory, column + row * 9 + 9, x + column * 18, y + row * 18));
			}
		}

		for (int column = 0; column < 9; ++column) {
			this.addSlot(new Slot(playerInventory, column, x + column * 18, y + 58));
		}
	}

	public boolean stillValid(Player player) {
		return this.inventory.stillValid(player);
	}

	public boolean isContainer(Container container) {
		return this.inventory == container;
	}

	public ItemStack quickMoveStack(Player player, int slot) {
		ItemStack itemStack = ItemStack.EMPTY;
		Slot slot2 = (Slot)this.slots.get(slot);
		if (slot2 != null && slot2.hasItem()) {
			ItemStack itemStack2 = slot2.getItem();
			itemStack = itemStack2.copy();
			if (slot < 9) {
				if (!this.moveItemStackTo(itemStack2, INVENTORY_START, HOTBAR_END, true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.moveItemStackTo(itemStack2, 0, INVENTORY_START, false)) {
				return ItemStack.EMPTY;
			}

			if (itemStack2.isEmpty()) {
				slot2.setByPlayer(ItemStack.EMPTY);
			} else {
				slot2.setChanged();
			}

			if (itemStack2.getCount() == itemStack.getCount()) {
				return ItemStack.EMPTY;
			}

			slot2.onTake(player, itemStack2);
		}

		return itemStack;
	}

	public void removed(Player player) {
		super.removed(player);
		this.inventory.stopOpen(player);
	}
}
