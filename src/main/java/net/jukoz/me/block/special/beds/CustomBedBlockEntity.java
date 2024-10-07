package net.jukoz.me.block.special.beds;

import net.jukoz.me.block.ModBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;

public class CustomBedBlockEntity extends BlockEntity {

    public CustomBedBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BED, pos, state);
    }

    public CustomBedBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }
}
