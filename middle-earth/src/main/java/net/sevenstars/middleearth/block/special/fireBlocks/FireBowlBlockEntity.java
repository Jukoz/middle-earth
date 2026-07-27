package net.sevenstars.middleearth.block.special.fireBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.sevenstars.middleearth.block.registration.ModBlockEntities;

public class FireBowlBlockEntity extends ToggleableFireBlockEntity {

    public FireBowlBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FIRE_BOWL, pos, state);
    }
}
