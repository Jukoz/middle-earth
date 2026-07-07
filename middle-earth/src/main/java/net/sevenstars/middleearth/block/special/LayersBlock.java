package net.sevenstars.middleearth.block.special;

import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;

public class LayersBlock extends SnowBlock implements Waterloggable {
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    public Block fullBlock;

    public LayersBlock(Settings settings, Block fullBlockIn) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)this.stateManager.getDefaultState()).with(LAYERS, 1).with(WATERLOGGED, false));

        fullBlock = fullBlockIn;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos());
        if (blockState.isOf(this)) {
            int i = (Integer)blockState.get(LAYERS);
            if(i == 7){
                return fullBlock.getDefaultState();
            } else {
                if (blockState.get(WATERLOGGED)){
                    return (BlockState)blockState.with(LAYERS, Math.min(8, i + 1)).with(WATERLOGGED, true);
                } else {
                    return (BlockState)blockState.with(LAYERS, Math.min(8, i + 1));
                }
            }
        } else {
            if(blockState.isOf(Blocks.WATER)){
                return super.getPlacementState(ctx).with(WATERLOGGED, true);
            } else {
                return super.getPlacementState(ctx);

            }
        }
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
        if (state.get(WATERLOGGED)) {
            tickView.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        return super.getStateForNeighborUpdate(state, world, tickView, pos, direction, neighborPos, neighborState, random);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LAYERS, WATERLOGGED);
    }
}
