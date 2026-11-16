package net.sevenstars.middleearth.mixin;

import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.enums.BedPart;
import net.minecraft.world.World;
import net.sevenstars.middleearth.block.special.beds.BedSpawning;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

// Beds don't tick, except the head half, which ticks to handle who spawns in the bed
@Mixin(BedBlock.class)
public abstract class BedBlockMixin implements BlockEntityProvider {
    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient || state.get(BedBlock.PART) != BedPart.HEAD ? null : BedSpawning::tick;
    }
}
