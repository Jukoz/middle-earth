package net.sevenstars.middleearth.block.special.beds;

import net.sevenstars.middleearth.block.registration.BlockEntityRegistryME;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;

public class CustomBedBlockEntity extends BlockEntity {

    public CustomBedBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistryME.BED, pos, state);
    }

    public CustomBedBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }
}
