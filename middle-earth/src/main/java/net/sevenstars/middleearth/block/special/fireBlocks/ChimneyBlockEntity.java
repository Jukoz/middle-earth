package net.sevenstars.middleearth.block.special.fireBlocks;

import net.sevenstars.middleearth.block.ModBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class ChimneyBlockEntity extends ToggleableFireBlockEntity {

    public ChimneyBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CHIMNEY, pos, state);
    }
}
