package net.sevenstars.middleearth.block.special.sack;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BarrelBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.registration.ModBlockEntities;
import net.sevenstars.middleearth.gui.sack.SackScreenHandler;
import org.jetbrains.annotations.Nullable;

import java.util.stream.IntStream;

public class SackBlockEntity extends RandomizableContainerBlockEntity implements WorldlyContainer {
    private static final int[] AVAILABLE_SLOTS = IntStream.range(0, 9).toArray();
    private NonNullList<ItemStack> inventory;
    private final ContainerOpenersCounter stateManager;

    public SackBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SACK, pos, state);
        this.inventory = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);
        this.setItems(NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY));
        this.stateManager = new ContainerOpenersCounter() {
            protected void onOpen(Level world, BlockPos pos, BlockState state) {
                SackBlockEntity.this.setOpen(state, true);
            }

            protected void onClose(Level world, BlockPos pos, BlockState state) {
                SackBlockEntity.this.setOpen(state, false);
            }

            protected void openerCountChanged(Level world, BlockPos pos, BlockState state, int oldViewerCount, int newViewerCount) {
            }

            protected boolean isOwnContainer(Player player) {
                return player.containerMenu instanceof SackScreenHandler menu
                        && menu.isContainer(SackBlockEntity.this);
            }
        };
    }

    @Override
    public int getContainerSize() {
        return 9;
    }

    @Override
    protected AbstractContainerMenu createMenu(int syncId, Inventory playerInventory) {
        return new SackScreenHandler(syncId, playerInventory, this);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable(MiddleEarth.of("sack").toLanguageKey("screen"));
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable(MiddleEarth.of("sack").toLanguageKey("screen"));
    }

    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.readInventoryNbt(tag, registries);
    }

    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        if (!this.trySaveLootTable(tag)) {
            ContainerHelper.saveAllItems(tag, this.inventory, false, registries);
        }

    }

    public void readInventoryNbt(CompoundTag tag, HolderLookup.Provider registries) {
        this.inventory = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        if (!this.tryLoadLootTable(tag)) {
            ContainerHelper.loadAllItems(tag, this.inventory, registries);
        }
    }

    public void startOpen(Player player) {
        if (!this.remove && !player.isSpectator()) {
            this.stateManager.incrementOpeners(player, this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
    }

    public void stopOpen(Player player) {
        if (!this.remove && !player.isSpectator()) {
            this.stateManager.decrementOpeners(player, this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
    }

    public void tick() {
        if (!this.remove) {
            this.stateManager.recheckOpeners(this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
    }

    protected NonNullList<ItemStack> getItems() {
        return this.inventory;
    }

    protected void setItems(NonNullList<ItemStack> inventory) {
        this.inventory = inventory;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void setChanged() {
        super.setChanged();
    }

    public int[] getSlotsForFace(Direction side) {
        return AVAILABLE_SLOTS;
    }

    public boolean canPlaceItemThroughFace(int slot, ItemStack stack, @Nullable Direction dir) {
        return !(Block.byItem(stack.getItem()) instanceof ShulkerBoxBlock);
    }

    public boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction dir) {
        return true;
    }

    void setOpen(BlockState state, boolean open) {
        this.level.setBlock(this.getBlockPos(), (BlockState)state.setValue(BarrelBlock.OPEN, open), Block.UPDATE_ALL);
    }
}
