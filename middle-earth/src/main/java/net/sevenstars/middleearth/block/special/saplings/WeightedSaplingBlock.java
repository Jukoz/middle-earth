package net.sevenstars.middleearth.block.special.saplings;

import java.util.List;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class WeightedSaplingBlock extends SaplingBlock {
    private final List<WeightedTree> trees;

    public WeightedSaplingBlock(Properties settings, List<WeightedTree> trees) {
        super(trees.getFirst().saplingGenerator, settings);
        this.trees = trees;
    }

    @Override
    public void advanceTree(ServerLevel world, BlockPos pos, BlockState state, RandomSource random) {
        if (state.getValue(STAGE) == 0) {
            world.setBlock(pos, (BlockState)state.cycle(STAGE), Block.UPDATE_INVISIBLE);
        } else {
            int totalWeight = trees.stream().mapToInt(obj -> obj.weight).sum();
            int randomWeight = random.nextInt(totalWeight);
            int accumulatedWeight = 0;
            TreeGrower tree = null;
            for(WeightedTree weightedTree : trees) {
                if(weightedTree.weight + accumulatedWeight > randomWeight) {
                    tree = weightedTree.saplingGenerator;
                    break;
                } else {
                    accumulatedWeight += weightedTree.weight;
                }
            }
            if(tree != null) tree.growTree(world, world.getChunkSource().getGenerator(), pos, state, random);
        }
    }

    public static class WeightedTree {
        public TreeGrower saplingGenerator;
        public int weight;

        public WeightedTree(TreeGrower saplingGenerator, int weight) {
            this.saplingGenerator = saplingGenerator;
            this.weight = weight;
        }
        public WeightedTree(ResourceKey<ConfiguredFeature<?, ?>> saplingGenerator, int weight, String name) {
            this.saplingGenerator = new TreeGrower(name, Optional.empty(), Optional.ofNullable(saplingGenerator),
                    Optional.empty());
            this.weight = weight;
        }
    }
}
