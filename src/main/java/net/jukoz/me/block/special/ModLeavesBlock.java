package net.jukoz.me.block.special;

import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;

public class ModLeavesBlock extends LeavesBlock {
    public static final int MAX_DISTANCE = 10;
    public static final IntProperty DISTANCE_1_10 = IntProperty.of("distance", 1, 10);

    final protected boolean castShadow;
    public ModLeavesBlock(Settings settings, boolean castShadow) {
        super(settings);
        this.castShadow = castShadow;
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return shouldDecay(state);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (this.shouldDecay(state)) {
            LeavesBlock.dropStacks(state, world, pos);
            world.removeBlock(pos, false);
        }
    }

    protected boolean shouldDecay(BlockState state) {
        return !state.get(PERSISTENT) && state.get(DISTANCE) == MAX_DISTANCE;
    }

    @Override
    public int getOpacity(BlockState state, BlockView world, BlockPos pos) {
        if(castShadow) return super.getOpacity(state, world, pos);
        return 0;
    }
}
