package net.sevenstars.middleearth.block.special.beds;

import net.sevenstars.middleearth.block.special.structureManager.features.NestHost;
import net.sevenstars.middleearth.block.special.structureManager.features.SpawnNestManager;
import net.sevenstars.middleearth.block.special.structureManager.features.StructureManagerService;
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

public class CustomBedBlockEntity extends BlockEntity implements BedTagHolder, NestHost {
    private @Nullable BedTag tag;
    private @Nullable SpawnNestManager nest;

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
        BedTagHolder.markChanged(this);
    }

    @Override
    public @Nullable SpawnNestManager getNest() {
        return nest;
    }

    @Override
    public void setNest(@Nullable SpawnNestManager nest) {
        this.nest = nest;
    }

    @Override
    public void markRemoved() {
        StructureManagerService.unregisterHost(world, pos);
        super.markRemoved();
    }

    @Override
    protected void readData(ReadView view) {
        super.readData(view);
        tag = BedTagHolder.load(view);
        nest = NestHost.load(view);
    }

    @Override
    protected void writeData(WriteView view) {
        super.writeData(view);
        BedTagHolder.save(view, tag);
        NestHost.save(view, nest);
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
