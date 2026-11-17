package net.sevenstars.middleearth.mixin;

import net.sevenstars.middleearth.block.special.structureManager.features.NestHost;
import net.sevenstars.middleearth.block.special.structureManager.features.SpawnNestManager;
import net.sevenstars.middleearth.block.special.structureManager.features.StructureManagerService;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BedBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.BlockPos;
import net.sevenstars.middleearth.block.special.beds.BedTag;
import net.sevenstars.middleearth.block.special.beds.BedTagHolder;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

// Vanilla beds keep the tag a builder gave them, like the mod's beds do
@Mixin(BedBlockEntity.class)
public abstract class BedBlockEntityMixin extends BlockEntity implements BedTagHolder, NestHost {
    @Unique
    private @Nullable BedTag middleEarth$tag;
    @Unique
    private @Nullable SpawnNestManager middleEarth$nest;

    public BedBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public @Nullable BedTag getBedTag() {
        return middleEarth$tag;
    }

    @Override
    public void setBedTag(@Nullable BedTag tag) {
        middleEarth$tag = tag;
        BedTagHolder.markChanged(this);
    }

    @Override
    public @Nullable SpawnNestManager getNest() {
        return middleEarth$nest;
    }

    @Override
    public void setNest(@Nullable SpawnNestManager nest) {
        middleEarth$nest = nest;
    }

    @Override
    public void markRemoved() {
        StructureManagerService.unregisterHost(world, pos);
        super.markRemoved();
    }

    @Override
    protected void readData(ReadView view) {
        super.readData(view);
        middleEarth$tag = BedTagHolder.load(view);
        middleEarth$nest = NestHost.load(view);
    }

    @Override
    protected void writeData(WriteView view) {
        super.writeData(view);
        BedTagHolder.save(view, middleEarth$tag);
        NestHost.save(view, middleEarth$nest);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registries) {
        return createComponentlessNbt(registries);
    }
}
