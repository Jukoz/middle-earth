package net.sevenstars.middleearth.block.special;

import net.minecraft.block.*;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;

public class CustomWaterloggableTallPlantBlock extends TallPlantBlock implements Fertilizable, Waterloggable {
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    private final boolean randomBoneMeal;
    public CustomWaterloggableTallPlantBlock(Settings settings, boolean random) {
        super(settings);
        this.randomBoneMeal = random;
        this.setDefaultState((BlockState)((BlockState)this.stateManager.getDefaultState()).with(HALF, DoubleBlockHalf.LOWER).with(WATERLOGGED, false));
    }

    public void placeAt(WorldAccess world, BlockPos pos, boolean waterlogged, int flags) {
        world.setBlockState(pos, this.getDefaultState().with(HALF, DoubleBlockHalf.LOWER).with(WATERLOGGED, waterlogged), flags);
        world.setBlockState(pos.up(), this.getDefaultState().with(HALF, DoubleBlockHalf.UPPER).with(WATERLOGGED, false), flags);
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
        if (state.get(HALF) == DoubleBlockHalf.LOWER && state.get(WATERLOGGED)) {
            tickView.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        if (state.get(HALF) == DoubleBlockHalf.UPPER && direction != Direction.DOWN && neighborState.getFluidState().isOf(Fluids.WATER)) {
            if (world instanceof World actualWorld && !actualWorld.isClient) {
                dropStacks(state, actualWorld, pos);
            }
            return Blocks.AIR.getDefaultState();
        }

        DoubleBlockHalf doubleBlockHalf = (DoubleBlockHalf)state.get(HALF);
        if (direction.getAxis() == Direction.Axis.Y && doubleBlockHalf == DoubleBlockHalf.LOWER == (direction == Direction.UP) && (!neighborState.isOf(this) || neighborState.get(HALF) == doubleBlockHalf)) {
            return Blocks.AIR.getDefaultState();
        } else {
            return doubleBlockHalf == DoubleBlockHalf.LOWER && direction == Direction.DOWN && !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, world, tickView, pos, direction, neighborPos, neighborState, random);
        }
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos blockPos = ctx.getBlockPos();
        BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos());
        World world = ctx.getWorld();
        if(blockPos.getY() < world.getTopYInclusive() - 1 && world.getBlockState(blockPos.up()).canReplace(ctx)){
            if(blockState.isOf(Blocks.WATER)){
                return this.getDefaultState().with(WATERLOGGED, true);
            } else{
                return this.getDefaultState();
            }
        } else {
            return null;
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(HALF, WATERLOGGED);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(HALF) == DoubleBlockHalf.LOWER && state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    protected boolean canBucketPlace(BlockState state, Fluid fluid) {
        return state.get(HALF) == DoubleBlockHalf.LOWER && super.canBucketPlace(state, fluid);
    }

    @Override
    public boolean canFillWithFluid(LivingEntity filler, BlockView world, BlockPos pos, BlockState state, Fluid fluid) {
        if (fluid != Fluids.WATER) {
            return false;
        }

        if (state.get(HALF) == DoubleBlockHalf.UPPER) {
            return true;
        }
        return Waterloggable.super.canFillWithFluid(filler, world, pos, state, fluid);
    }

    @Override
    public boolean tryFillWithFluid(WorldAccess world, BlockPos pos, BlockState state, FluidState fluidState) {
        if (!fluidState.isOf(Fluids.WATER)) {
            return false;
        }

        if (state.get(HALF) == DoubleBlockHalf.UPPER) {
            if (world instanceof World actualWorld && !actualWorld.isClient) {
                dropStacks(state, actualWorld, pos);
            }
            BlockPos lowerPos = pos.down();
            BlockState lowerState = world.getBlockState(lowerPos);
            if (lowerState.isOf(this) && lowerState.get(HALF) == DoubleBlockHalf.LOWER) {
                BlockState replacementState = lowerState.get(WATERLOGGED) ? Blocks.WATER.getDefaultState() : Blocks.AIR.getDefaultState();
                world.setBlockState(lowerPos, replacementState, 3);
            }
            return world.setBlockState(pos, Blocks.WATER.getDefaultState(), 3);
        }
        return Waterloggable.super.tryFillWithFluid(world, pos, state, fluidState);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        world.setBlockState(pos.up(), state.with(HALF, DoubleBlockHalf.UPPER).with(WATERLOGGED, false), 3);
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
        return true;
    }

    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        if(this.randomBoneMeal) {
            float val = random.nextFloat();
            if(val > 0.90f){
                dropStack(world, pos, new ItemStack(this));
            }
        } else {
            dropStack(world, pos, new ItemStack(this));
        }
    }
}
