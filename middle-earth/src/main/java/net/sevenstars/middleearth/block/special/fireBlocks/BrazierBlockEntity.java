package net.sevenstars.middleearth.block.special.fireBlocks;

import net.sevenstars.middleearth.block.ModBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class BrazierBlockEntity extends ToggleableFireBlockEntity {

    public BrazierBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BIG_BRAZIER, pos, state);
    }
}
