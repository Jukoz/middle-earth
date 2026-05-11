package net.sevenstars.middleearth.block.special.saplings;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.SaplingGenerator;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.feature.ConfiguredFeature;

import java.util.List;
import java.util.Optional;

public class WeightedSaplingBlock extends SaplingBlock {
    private final List<WeightedTree> trees;

    public WeightedSaplingBlock(Settings settings, List<WeightedTree> trees) {
        super(trees.getFirst().saplingGenerator, settings);
        this.trees = trees;
    }

    @Override
    public void generate(ServerWorld world, BlockPos pos, BlockState state, Random random) {
        if (state.get(STAGE) == 0) {
            world.setBlockState(pos, (BlockState)state.cycle(STAGE), Block.NO_REDRAW);
        } else {
            int totalWeight = trees.stream().mapToInt(obj -> obj.weight).sum();
            int randomWeight = random.nextInt(totalWeight);
            int accumulatedWeight = 0;
            SaplingGenerator tree = null;
            for(WeightedTree weightedTree : trees) {
                if(weightedTree.weight + accumulatedWeight > randomWeight) {
                    tree = weightedTree.saplingGenerator;
                    break;
                } else {
                    accumulatedWeight += weightedTree.weight;
                }
            }
            if(tree != null) tree.generate(world, world.getChunkManager().getChunkGenerator(), pos, state, random);
        }
    }

    public static class WeightedTree {
        public SaplingGenerator saplingGenerator;
        public int weight;

        public WeightedTree(SaplingGenerator saplingGenerator, int weight) {
            this.saplingGenerator = saplingGenerator;
            this.weight = weight;
        }
        public WeightedTree(RegistryKey<ConfiguredFeature<?, ?>> saplingGenerator, int weight, String name) {
            this.saplingGenerator = new SaplingGenerator(name, Optional.empty(), Optional.ofNullable(saplingGenerator),
                    Optional.empty());
            this.weight = weight;
        }
    }
}
