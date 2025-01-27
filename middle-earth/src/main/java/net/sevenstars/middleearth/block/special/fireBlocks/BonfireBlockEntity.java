package net.sevenstars.middleearth.block.special.fireBlocks;

import net.sevenstars.middleearth.block.ModBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class BonfireBlockEntity extends ToggleableFireBlockEntity {

    public BonfireBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BONFIRE, pos, state);
    }


}
