package net.sevenstars.middleearth.light;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

/**
 * A block that shows something when a beam light's beam sweeps over it (a held or placed beam light, as
 * {@link HeldLight.Beam}): called on the client, each tick the beam's cone falls on it, so it can give off a few
 * particles or the like. Nothing is called on the server.
 */
public interface BeamLitBlock {
    void onBeamLit(World world, BlockPos pos, BlockState state, Random random);
}
