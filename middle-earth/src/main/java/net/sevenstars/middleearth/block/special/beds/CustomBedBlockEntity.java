package net.sevenstars.middleearth.block.special.beds;

import net.minecraft.block.Block;
import net.sevenstars.middleearth.block.registration.BlockEntityRegistryME;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class CustomBedBlockEntity extends BlockEntity implements BedTagHolder {
    private @Nullable BedTag tag;

    public CustomBedBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistryME.BED, pos, state);
    }

    public CustomBedBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public @Nullable BedTag getBedTag() {
        return tag;
    }

    @Override
    public void setBedTag(@Nullable BedTag tag) {
        this.tag = tag;
        markDirty();
        if(world != null)
            world.updateListeners(pos, getCachedState(), getCachedState(), Block.NOTIFY_ALL);
    }

    @Override
    protected void readData(ReadView view) {
        super.readData(view);
        tag = view.read(TAG_KEY, BedTag.CODEC).orElse(null);
    }

    @Override
    protected void writeData(WriteView view) {
        super.writeData(view);
        view.putNullable(TAG_KEY, BedTag.CODEC, tag);
    }

    @Override
    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registries) {
        return createComponentlessNbt(registries);
    }
}
