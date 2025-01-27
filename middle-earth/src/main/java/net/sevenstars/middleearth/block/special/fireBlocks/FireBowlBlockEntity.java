package net.sevenstars.middleearth.block.special.fireBlocks;

import net.sevenstars.middleearth.block.ModBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class FireBowlBlockEntity extends ToggleableFireBlockEntity {

    public FireBowlBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FIRE_BOWL, pos, state);
    }
}
