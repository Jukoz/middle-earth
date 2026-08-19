package net.sevenstars.middleearth.block.special.saplings;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockState;

public class VariantSaplingBlock extends SaplingBlock {
    private final List<TreeGrower> trees;

    public VariantSaplingBlock(Properties settings, List<TreeGrower> trees) {
        super(trees.getFirst(), settings);
        this.trees = trees;
    }

    @Override
    public void advanceTree(ServerLevel world, BlockPos pos, BlockState state, RandomSource random) {
        if (state.getValue(STAGE) == 0) {
            world.setBlock(pos, (BlockState)state.cycle(STAGE), Block.UPDATE_INVISIBLE);
        } else {
            TreeGrower saplingGenerator = trees.get(random.nextInt(trees.size()));
            saplingGenerator.growTree(world, world.getChunkSource().getGenerator(), pos, state, random);
        }
    }
}
