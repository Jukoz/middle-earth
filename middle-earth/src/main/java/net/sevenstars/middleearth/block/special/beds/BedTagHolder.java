package net.sevenstars.middleearth.block.special.beds;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

// A bed the builder has tagged with who spawns in it. Stored in the bed's block entity, so a structure template saved
// with the bed includes the tag
public interface BedTagHolder {
    String TAG_KEY = "MiddleEarthBedTag";

    @Nullable BedTag getBedTag();

    void setBedTag(@Nullable BedTag tag);

    static @Nullable BedTag load(ReadView view) {
        return view.read(TAG_KEY, BedTag.CODEC).orElse(null);
    }

    static void save(WriteView view, @Nullable BedTag tag) {
        view.putNullable(TAG_KEY, BedTag.CODEC, tag);
    }

    static void markChanged(BlockEntity entity) {
        entity.markDirty();
        World world = entity.getWorld();
        if(world != null)
            world.updateListeners(entity.getPos(), entity.getCachedState(), entity.getCachedState(), Block.NOTIFY_ALL);
    }

    static Optional<BedTag> tagAt(BlockView world, BlockPos pos) {
        BlockEntity entity = world.getBlockEntity(pos);
        return entity instanceof BedTagHolder holder ? Optional.ofNullable(holder.getBedTag()) : Optional.empty();
    }
}
