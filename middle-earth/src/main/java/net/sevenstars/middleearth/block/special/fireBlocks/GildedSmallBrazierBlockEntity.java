package net.sevenstars.middleearth.block.special.fireBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.sevenstars.middleearth.block.registration.ModBlockEntities;

public class GildedSmallBrazierBlockEntity extends ToggleableFireBlockEntity {

    public GildedSmallBrazierBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.GILDED_SMALL_BRAZIER, pos, state);
    }
}
