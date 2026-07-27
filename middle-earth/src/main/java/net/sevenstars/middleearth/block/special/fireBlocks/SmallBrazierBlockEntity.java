package net.sevenstars.middleearth.block.special.fireBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.sevenstars.middleearth.block.registration.ModBlockEntities;

public class SmallBrazierBlockEntity extends ToggleableFireBlockEntity {

    public SmallBrazierBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SMALL_BRAZIER, pos, state);
    }
}
