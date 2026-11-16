package net.sevenstars.middleearth.block.special.structureManager;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.sevenstars.middleearth.block.registration.BlockEntityRegistryME;

// Holds nothing and does nothing anymore, since people spawn from beds and markers, and animals from markers. Kept
// for worlds and builds that still contain the block
public class StructureManagerBlockEntity extends BlockEntity {
    public StructureManagerBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistryME.STRUCTURE_MANAGER, pos, state);
    }
}
