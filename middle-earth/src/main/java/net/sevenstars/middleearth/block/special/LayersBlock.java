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
import net.minecraft.world.WorldAccess;

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
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LAYERS, WATERLOGGED);
    }
}
