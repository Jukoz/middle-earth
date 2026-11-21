package net.sevenstars.middleearth.block.special.structureManager;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.sevenstars.middleearth.block.registration.BlockEntityRegistryME;

// Does nothing. Kept so worlds and builds that contain the block still load
public class StructureManagerBlockEntity extends BlockEntity {
    public StructureManagerBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistryME.STRUCTURE_MANAGER, pos, state);
    }
}
