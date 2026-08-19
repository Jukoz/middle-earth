package net.sevenstars.middleearth.block.special;

import net.minecraft.world.level.block.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

public class CustomWaterloggableTallPlantBlock extends DoublePlantBlock implements BonemealableBlock, SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    private final boolean randomBoneMeal;
    public CustomWaterloggableTallPlantBlock(Properties settings, boolean random) {
        super(settings);
        this.randomBoneMeal = random;
        this.registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue(HALF, DoubleBlockHalf.LOWER).setValue(WATERLOGGED, false));
    }

    public void placeAt(LevelAccessor world, BlockPos pos, boolean waterlogged, int flags) {
        world.setBlock(pos, this.defaultBlockState().setValue(HALF, DoubleBlockHalf.LOWER).setValue(WATERLOGGED, waterlogged), flags);
        world.setBlock(pos.above(), this.defaultBlockState().setValue(HALF, DoubleBlockHalf.UPPER).setValue(WATERLOGGED, false), flags);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(HALF) == DoubleBlockHalf.LOWER && state.getValue(WATERLOGGED)) {
            world.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        }

        DoubleBlockHalf doubleBlockHalf = (DoubleBlockHalf)state.getValue(HALF);
        if (direction.getAxis() == Direction.Axis.Y && doubleBlockHalf == DoubleBlockHalf.LOWER == (direction == Direction.UP) && (!neighborState.is(this) || neighborState.getValue(HALF) == doubleBlockHalf)) {
            return Blocks.AIR.defaultBlockState();
        } else {
            return doubleBlockHalf == DoubleBlockHalf.LOWER && direction == Direction.DOWN && !state.canSurvive(world, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, neighborState, world, pos, neighborPos);
        }
    }

    @Override
    protected void neighborChanged(BlockState state, Level world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        if (!world.isClientSide && state.getValue(HALF) == DoubleBlockHalf.UPPER && (
                world.getBlockState(pos.above()).getFluidState().is(Fluids.WATER)
                        || world.getBlockState(pos.north()).getFluidState().is(Fluids.WATER)
                        || world.getBlockState(pos.south()).getFluidState().is(Fluids.WATER)
                        || world.getBlockState(pos.east()).getFluidState().is(Fluids.WATER)
                        || world.getBlockState(pos.west()).getFluidState().is(Fluids.WATER))) {
            this.breakUpperFromWater(world, pos, Blocks.AIR.defaultBlockState());
            return;
        }
        super.neighborChanged(state, world, pos, sourceBlock, sourcePos, notify);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockPos blockPos = ctx.getClickedPos();
        BlockState blockState = ctx.getLevel().getBlockState(ctx.getClickedPos());
        Level world = ctx.getLevel();
        if(blockPos.getY() < world.getMaxBuildHeight() - 1 && world.getBlockState(blockPos.above()).canBeReplaced(ctx)){
            if(blockState.is(Blocks.WATER)){
                return this.defaultBlockState().setValue(WATERLOGGED, true);
            } else{
                return this.defaultBlockState();
            }
        } else {
            return null;
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HALF, WATERLOGGED);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(HALF) == DoubleBlockHalf.LOWER && state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    protected boolean canBeReplaced(BlockState state, Fluid fluid) {
        return state.getValue(HALF) == DoubleBlockHalf.LOWER && super.canBeReplaced(state, fluid);
    }

    @Override
    public boolean canPlaceLiquid(Player filler, BlockGetter world, BlockPos pos, BlockState state, Fluid fluid) {
        if (fluid != Fluids.WATER) {
            return false;
        }

        if (state.getValue(HALF) == DoubleBlockHalf.UPPER) {
            return true;
        }
        return SimpleWaterloggedBlock.super.canPlaceLiquid(filler, world, pos, state, fluid);
    }

    @Override
    public boolean placeLiquid(LevelAccessor world, BlockPos pos, BlockState state, FluidState fluidState) {
        if (!fluidState.is(Fluids.WATER)) {
            return false;
        }

        if (state.getValue(HALF) == DoubleBlockHalf.UPPER) {
            this.breakUpperFromWater(world, pos, Blocks.WATER.defaultBlockState());
            return true;
        }
        return SimpleWaterloggedBlock.super.placeLiquid(world, pos, state, fluidState);
    }

    @Override
    public void setPlacedBy(Level world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        world.setBlock(pos.above(), state.setValue(HALF, DoubleBlockHalf.UPPER).setValue(WATERLOGGED, false), 3);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader world, BlockPos pos, BlockState state) {
        return true;
    }

    public boolean isBonemealSuccess(Level world, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    public void performBonemeal(ServerLevel world, RandomSource random, BlockPos pos, BlockState state) {
        if(this.randomBoneMeal) {
            float val = random.nextFloat();
            if(val > 0.90f){
                popResource(world, pos, new ItemStack(this));
            }
        } else {
            popResource(world, pos, new ItemStack(this));
        }
    }

    private void breakUpperFromWater(LevelAccessor world, BlockPos upperPos, BlockState upperReplacementState) {
        BlockState upperState = world.getBlockState(upperPos);
        if (!upperState.is(this) || upperState.getValue(HALF) != DoubleBlockHalf.UPPER) {
            return;
        }

        if (world instanceof Level actualWorld && !actualWorld.isClientSide) {
            dropResources(upperState, actualWorld, upperPos);
        }

        BlockPos lowerPos = upperPos.below();
        BlockState lowerState = world.getBlockState(lowerPos);
        if (lowerState.is(this) && lowerState.getValue(HALF) == DoubleBlockHalf.LOWER) {
            BlockState lowerReplacementState = lowerState.getValue(WATERLOGGED) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState();
            world.setBlock(lowerPos, lowerReplacementState, 3);
        }

        world.setBlock(upperPos, upperReplacementState, 3);
    }
}
