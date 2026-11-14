package net.sevenstars.middleearth.mixin;

import net.minecraft.block.Block;
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

// The beds of vanilla keep the tag a builder gave them, as the beds of the mod do
@Mixin(BedBlockEntity.class)
public abstract class BedBlockEntityMixin extends BlockEntity implements BedTagHolder {
    @Unique
    private @Nullable BedTag middleearth$tag;

    public BedBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public @Nullable BedTag getBedTag() {
        return middleearth$tag;
    }

    @Override
    public void setBedTag(@Nullable BedTag tag) {
        middleearth$tag = tag;
        markDirty();
        if(world != null)
            world.updateListeners(pos, getCachedState(), getCachedState(), Block.NOTIFY_ALL);
    }

    @Override
    protected void readData(ReadView view) {
        super.readData(view);
        middleearth$tag = view.read(TAG_KEY, BedTag.CODEC).orElse(null);
    }

    @Override
    protected void writeData(WriteView view) {
        super.writeData(view);
        view.putNullable(TAG_KEY, BedTag.CODEC, middleearth$tag);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registries) {
        return createComponentlessNbt(registries);
    }
}
