package net.sevenstars.middleearth.block.special.plate;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SingleStackInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.registry.RegistryOps;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.BlockPos;
import net.sevenstars.middleearth.block.ModBlockEntities;

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
        food = view.read("Item", ItemStack.CODEC).orElse(ItemStack.EMPTY);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registries) {
        NbtCompound nbtCompound = new NbtCompound();
        if (!food.isEmpty()) {
            RegistryOps<NbtElement> registryOps = registries.getOps(NbtOps.INSTANCE);
            nbtCompound.put("Item", ItemStack.CODEC, registryOps, food);
        }
        return nbtCompound;
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
