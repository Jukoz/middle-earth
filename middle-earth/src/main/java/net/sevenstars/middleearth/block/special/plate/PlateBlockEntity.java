package net.sevenstars.middleearth.block.special.plate;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.SingleStackInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.BlockPos;
import net.sevenstars.middleearth.block.registration.ModBlockEntities;
import org.jetbrains.annotations.Nullable;

public class PlateBlockEntity extends BlockEntity implements SingleStackInventory.SingleStackBlockEntityInventory {
    private ItemStack food = ItemStack.EMPTY;

    public PlateBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.PLATE, pos, state);
    }

    @Override
    protected void writeData(WriteView view) {
        super.writeData(view);
        if (!food.isEmpty()) {
            view.put("Item", ItemStack.CODEC, food);
        }
    }

    @Override
    protected void readData(ReadView view) {
        super.readData(view);
        this.food.copyAndEmpty();
        food = view.read("Item", ItemStack.CODEC).orElse(ItemStack.EMPTY);
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registries) {
        return createNbt(registries);
    }

    @Override
    public BlockEntity asBlockEntity() {
        return this;
    }

    @Override
    public ItemStack getStack() {
        return food;
    }

    @Override
    public void setStack(ItemStack stack) {
        this.food = stack;
        update();
    }

    public void update() {
        markDirty();
        world.updateListeners(pos, getCachedState(), getCachedState(), Block.NOTIFY_ALL);
    }
}
