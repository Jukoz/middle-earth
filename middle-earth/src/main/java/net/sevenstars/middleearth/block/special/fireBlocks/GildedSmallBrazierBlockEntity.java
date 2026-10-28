package net.sevenstars.middleearth.block.special.fireBlocks;

import net.sevenstars.middleearth.block.registration.BlockEntityRegistryME;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class GildedSmallBrazierBlockEntity extends ToggleableFireBlockEntity {

    public GildedSmallBrazierBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistryME.GILDED_SMALL_BRAZIER, pos, state);
    }
}
