package net.sevenstars.middleearth.block.special.fireBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.sevenstars.middleearth.block.registration.ModBlockEntities;

public class BonfireBlockEntity extends ToggleableFireBlockEntity {

    public BonfireBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BONFIRE, pos, state);
    }


}
