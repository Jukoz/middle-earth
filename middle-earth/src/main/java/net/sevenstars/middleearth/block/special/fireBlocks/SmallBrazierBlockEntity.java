package net.sevenstars.middleearth.block.special.fireBlocks;

import net.sevenstars.middleearth.block.ModBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class SmallBrazierBlockEntity extends ToggleableFireBlockEntity {

    public SmallBrazierBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SMALL_BRAZIER, pos, state);
    }
}
