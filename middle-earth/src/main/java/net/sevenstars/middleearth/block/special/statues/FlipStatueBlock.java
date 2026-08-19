package net.sevenstars.middleearth.block.special.statues;

import net.minecraft.world.level.block.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.BlockHitResult;

public class FlipStatueBlock extends StatueBlock {
    public static final BooleanProperty FLIP = BooleanProperty.create("flip");

    public FlipStatueBlock(Properties settings) {
        super(settings);
        registerDefaultState(defaultBlockState().setValue(HORIZONTAL_FACING, Direction.NORTH).setValue(HALF, DoubleBlockHalf.LOWER).setValue(WATERLOGGED, false).setValue(FLIP, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FLIP);
        super.createBlockStateDefinition(builder);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        if (!world.isClientSide && player.getAbilities().mayBuild) {
            world.setBlockAndUpdate(pos, state.cycle(FLIP));
            BlockState updatedState = world.getBlockState(pos);
            if(state.getValue(HALF) == DoubleBlockHalf.LOWER) {
                BlockState otherState = world.getBlockState(pos.above());
                world.setBlockAndUpdate(pos.above(), otherState.setValue(FLIP, updatedState.getValue(FLIP)));
            } else {
                BlockState otherState = world.getBlockState(pos.below());
                world.setBlockAndUpdate(pos.below(), otherState.setValue(FLIP, updatedState.getValue(FLIP)));
            }
        }
        return InteractionResult.SUCCESS;
    }
}
