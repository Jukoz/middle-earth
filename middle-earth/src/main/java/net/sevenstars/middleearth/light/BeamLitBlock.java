package net.sevenstars.middleearth.light;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

/** A block that reacts on the client while a beam sweeps over it. */
public interface BeamLitBlock {
    void onBeamLit(World world, BlockPos pos, BlockState state, Random random);
}
