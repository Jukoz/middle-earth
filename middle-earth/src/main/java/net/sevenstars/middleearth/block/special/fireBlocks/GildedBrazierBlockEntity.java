package net.sevenstars.middleearth.block.special.fireBlocks;

import net.sevenstars.middleearth.block.registration.BlockEntityRegistryME;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class GildedBrazierBlockEntity extends ToggleableFireBlockEntity {

    public GildedBrazierBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistryME.GILDED_BIG_BRAZIER, pos, state);
    }
}
