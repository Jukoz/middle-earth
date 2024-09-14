package net.jukoz.me.block.special.fireBlocks;

import net.jukoz.me.block.ModBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class GildedSmallBrazierBlockEntity extends ToggleableFireBlockEntity {

    public GildedSmallBrazierBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.GILDED_SMALL_BRAZIER, pos, state);
    }
}
