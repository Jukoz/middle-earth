package net.jukoz.me.block.special;

import net.jukoz.me.world.biomes.MEBiomeKeys;
import net.jukoz.me.world.biomes.ModBiomeSource;
import net.jukoz.me.world.biomes.ModBiomes;
import net.minecraft.block.BlockState;
import net.minecraft.block.Oxidizable;
import net.minecraft.block.WallBlock;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;

public class OxidizableVerticalSlabBlock extends VerticalSlabBlock implements Oxidizable {
    private final OxidationLevel oxidationLevel;

    public OxidizableVerticalSlabBlock(OxidationLevel oxidationLevel, Settings settings) {
        super(settings);
        this.oxidationLevel = oxidationLevel;
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        this.tickDegradation(state, world, pos, random);
    }

    public boolean hasRandomTicks(BlockState state) {
        return Oxidizable.getIncreasedOxidationBlock(state.getBlock()).isPresent();
    }

    public OxidationLevel getDegradationLevel() {
        return this.oxidationLevel;
    }
}
