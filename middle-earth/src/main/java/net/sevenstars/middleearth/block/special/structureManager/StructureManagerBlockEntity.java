package net.sevenstars.middleearth.block.special.structureManager;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.sevenstars.middleearth.block.registration.BlockEntityRegistryME;

// Holds nothing and does nothing any more: people are born by their beds and animals by their markers. Kept for the worlds
// and builds that still have the block in them
public class StructureManagerBlockEntity extends BlockEntity {
    public StructureManagerBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistryME.STRUCTURE_MANAGER, pos, state);
    }
}
