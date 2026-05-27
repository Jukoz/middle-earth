package net.sevenstars.middleearth.block.special.sack;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.ShulkerBoxScreenHandler;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.registration.ModBlockEntities;
import org.jetbrains.annotations.Nullable;

import java.util.stream.IntStream;

public class SackBlockEntity extends LootableContainerBlockEntity implements SidedInventory {
    private static final int[] AVAILABLE_SLOTS = IntStream.range(0, 9).toArray();
    private DefaultedList<ItemStack> inventory;

    public SackBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SACK, pos, state);
        this.inventory = DefaultedList.ofSize(size(), ItemStack.EMPTY);
        this.setHeldStacks(DefaultedList.ofSize(this.size(), ItemStack.EMPTY));
    }

    @Override
    public int size() {
        return 9;
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new GenericContainerScreenHandler(
                ScreenHandlerType.GENERIC_9X1,
                syncId,
                playerInventory,
                this,
                1
        );
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable(MiddleEarth.of("sack").toTranslationKey("screen"));
    }

    @Override
    protected Text getContainerName() {
        return Text.translatable(MiddleEarth.of("sack").toTranslationKey("screen"));
    }

    protected void readData(ReadView view) {
        super.readData(view);
        this.readInventoryNbt(view);
    }

    protected void writeData(WriteView view) {
        super.writeData(view);
        if (!this.writeLootTable(view)) {
            Inventories.writeData(view, this.inventory, false);
        }

    }

    public void readInventoryNbt(ReadView readView) {
        this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        if (!this.readLootTable(readView)) {
            Inventories.readData(readView, this.inventory);
        }

    }

    protected DefaultedList<ItemStack> getHeldStacks() {
        return this.inventory;
    }

    protected void setHeldStacks(DefaultedList<ItemStack> inventory) {
        this.inventory = inventory;
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public void markDirty() {
        super.markDirty();
        if (world != null) {
            world.updateListeners(pos, getCachedState(), getCachedState(), 3);
        }
    }

    public int[] getAvailableSlots(Direction side) {
        return AVAILABLE_SLOTS;
    }

    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        return !(Block.getBlockFromItem(stack.getItem()) instanceof ShulkerBoxBlock);
    }

    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return true;
    }
}
