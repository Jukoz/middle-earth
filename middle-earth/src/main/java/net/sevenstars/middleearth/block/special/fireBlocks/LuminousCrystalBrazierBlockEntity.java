package net.sevenstars.middleearth.block.special.fireBlocks;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.sevenstars.middleearth.block.registration.BlockEntityRegistryME;

public class LuminousCrystalBrazierBlockEntity extends ToggleableFireBlockEntity {

    public LuminousCrystalBrazierBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistryME.CRYSTAL_BRAZIER, pos, state);
    }
}
