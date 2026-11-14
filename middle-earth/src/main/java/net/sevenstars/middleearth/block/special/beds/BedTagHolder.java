package net.sevenstars.middleearth.block.special.beds;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

// A bed that a builder has said who is born in. Kept in the bed's own block entity, so that a structure template saved with the
// bed has the tag in it
public interface BedTagHolder {
    String TAG_KEY = "MiddleEarthBedTag";

    @Nullable BedTag getBedTag();

    void setBedTag(@Nullable BedTag tag);

    static Optional<BedTag> tagAt(BlockView world, BlockPos pos) {
        BlockEntity entity = world.getBlockEntity(pos);
        return entity instanceof BedTagHolder holder ? Optional.ofNullable(holder.getBedTag()) : Optional.empty();
    }
}
