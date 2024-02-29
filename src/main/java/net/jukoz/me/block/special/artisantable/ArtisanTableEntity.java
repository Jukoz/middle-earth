package net.jukoz.me.block.special.artisantable;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.ModBlockEntities;
import net.jukoz.me.gui.artisantable.ArtisanTableScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;

public class ArtisanTableEntity extends BlockEntity implements NamedScreenHandlerFactory, SidedInventory {
    private static final String ID = "artisan_table";
    public static final int OUTPUT_SLOT = 6;

    private final DefaultedList<ItemStack> inventory =
            DefaultedList.ofSize(7, ItemStack.EMPTY);

    public ArtisanTableEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ARTISAN_TABLE, pos, state);
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("screen." + MiddleEarth.MOD_ID + "." + ID);
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new ArtisanTableScreenHandler(syncId, playerInventory, this);
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        int[] slots = new int[inventory.size()];
        for (int i = 0; i < slots.length; i++) {
            slots[i] = i;
        }
        return slots;
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        return true;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return slot != OUTPUT_SLOT;
    }

    @Override
    public int size() {
        return inventory.size();
    }

    @Override
    public boolean isEmpty() {
        for (int i = 0; i < size(); i++) {
            ItemStack itemStack = getStack(i);
            if (!itemStack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getStack(int slot) {
        return inventory.get(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        return Inventories.splitStack(this.inventory, slot, amount);
    }

    @Override
    public ItemStack removeStack(int slot) {
        return Inventories.removeStack(inventory, slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        inventory.set(slot, stack);
        if (stack.getCount() > getMaxCountPerStack()) {
            stack.setCount(getMaxCountPerStack());
        }
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return Inventory.canPlayerUse(this, player);
    }

    @Override
    public void clear() {
        inventory.clear();
    }
}
