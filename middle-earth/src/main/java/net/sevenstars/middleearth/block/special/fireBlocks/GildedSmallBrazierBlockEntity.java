package net.sevenstars.middleearth.block.special.fireBlocks;

import net.sevenstars.middleearth.block.ModBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class GildedSmallBrazierBlockEntity extends ToggleableFireBlockEntity {

    public GildedSmallBrazierBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.GILDED_SMALL_BRAZIER, pos, state);
    }
}
