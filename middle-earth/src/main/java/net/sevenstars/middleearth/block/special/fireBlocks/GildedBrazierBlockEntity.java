package net.sevenstars.middleearth.block.special.fireBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.sevenstars.middleearth.block.registration.ModBlockEntities;

public class GildedBrazierBlockEntity extends ToggleableFireBlockEntity {

    public GildedBrazierBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.GILDED_BIG_BRAZIER, pos, state);
    }
}
