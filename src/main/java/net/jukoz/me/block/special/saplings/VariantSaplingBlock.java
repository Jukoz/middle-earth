package net.jukoz.me.block.special.saplings;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.SaplingGenerator;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;

import java.util.List;

public class VariantSaplingBlock extends SaplingBlock {
    private final List<SaplingGenerator> trees;

    public VariantSaplingBlock(Settings settings, List<SaplingGenerator> trees) {
        super(trees.getFirst(), settings);
        this.trees = trees;
    }

    @Override
    public void generate(ServerWorld world, BlockPos pos, BlockState state, Random random) {
        if (state.get(STAGE) == 0) {
            world.setBlockState(pos, (BlockState)state.cycle(STAGE), Block.NO_REDRAW);
        } else {
            SaplingGenerator saplingGenerator = trees.get(random.nextInt(trees.size()));
            saplingGenerator.generate(world, world.getChunkManager().getChunkGenerator(), pos, state, random);
        }
    }
}
