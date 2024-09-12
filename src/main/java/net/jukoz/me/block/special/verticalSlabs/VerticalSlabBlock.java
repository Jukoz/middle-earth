package net.jukoz.me.block.special.verticalSlabs;

import net.minecraft.block.*;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class VerticalSlabBlock extends Block implements Waterloggable {
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final BooleanProperty DOUBLE = BooleanProperty.of("double");
    public static final EnumProperty<VerticalSlabShape> SHAPE = EnumProperty.of("shape", VerticalSlabShape.class);


    public VerticalSlabBlock(Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.stateManager.getDefaultState()).with(FACING, Direction.NORTH).with(WATERLOGGED, false).with(DOUBLE, false).with(SHAPE, VerticalSlabShape.STRAIGHT)))));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED, DOUBLE, SHAPE);
    }

    @Override
    public boolean hasSidedTransparency(BlockState state) {
        return !state.get(DOUBLE);
    }

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos blockPos = ctx.getBlockPos();
        BlockState blockState = ctx.getWorld().getBlockState(blockPos);
        if (blockState.isOf(this)) {
            return (blockState.with(DOUBLE, true)).with(WATERLOGGED, false);
        } else {
            FluidState fluidState = ctx.getWorld().getFluidState(blockPos);
            BlockState blockState2 = (BlockState)((BlockState)((BlockState)this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite())).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER));
            return (BlockState)blockState2.with(SHAPE, getVerticalSlabShape(blockState2, ctx.getWorld(), blockPos));
        }
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        return direction.getAxis().isHorizontal() ? (BlockState)state.with(SHAPE, getVerticalSlabShape(state, world, pos)) : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);

    }

    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        ItemStack itemStack = context.getStack();
        if (state.get(DOUBLE) || !itemStack.isOf(this.asItem())) {
            return false;
        }
        if (context.canReplaceExisting()) {
            return context.getSide() == state.get(FACING);
        }
        return true;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if(state.get(DOUBLE)){
            return VoxelShapes.cuboid(0, 0, 0.0, 1, 1, 1);
        } else {
            if(state.get(SHAPE) == VerticalSlabShape.STRAIGHT){
                return switch(state.get(Properties.HORIZONTAL_FACING)) {
                    case WEST -> VoxelShapes.cuboid(0.5, 0, 0, 1, 1, 1);
                    case EAST -> VoxelShapes.cuboid(0, 0, 0, 0.5, 1, 1);
                    case SOUTH -> VoxelShapes.cuboid(0, 0, 0, 1, 1, 0.5);
                    case NORTH -> VoxelShapes.cuboid(0, 0, 0.5, 1, 1, 1);
                    default -> VoxelShapes.cuboid(1,1,1,1,1,1);
                };
            } else if (state.get(SHAPE) == VerticalSlabShape.OUTER_LEFT) {
                return switch (state.get(Properties.HORIZONTAL_FACING)) {
                    case WEST -> Block.createCuboidShape(8, 0, 0, 16, 16, 8);
                    case EAST -> Block.createCuboidShape(0, 0, 8, 8, 16, 16);
                    case SOUTH -> Block.createCuboidShape(0, 0, 0, 8, 16, 8);
                    case NORTH -> Block.createCuboidShape(8, 0, 8, 16, 16, 16);
                    default -> VoxelShapes.cuboid(1, 1, 1, 1, 1, 1);
                };
            }else if (state.get(SHAPE) == VerticalSlabShape.OUTER_RIGHT) {
                return switch (state.get(Properties.HORIZONTAL_FACING)) {
                    case WEST -> Block.createCuboidShape(8, 0, 8, 16, 16, 16);
                    case EAST -> Block.createCuboidShape(0, 0, 0, 8, 16, 8);
                    case SOUTH -> Block.createCuboidShape(8, 0, 0, 16, 16, 8);
                    case NORTH -> Block.createCuboidShape(0, 0, 8, 8, 16, 16);
                    default -> VoxelShapes.cuboid(1, 1, 1, 1, 1, 1);
                };
            }else if (state.get(SHAPE) == VerticalSlabShape.INNER_LEFT) { //good
                return switch (state.get(Properties.HORIZONTAL_FACING)) {
                    case WEST -> VoxelShapes.combineAndSimplify(Block.createCuboidShape(8, 0, 0, 16, 16, 16), Block.createCuboidShape(0, 0, 0, 8, 16, 8), BooleanBiFunction.OR);
                    case EAST -> VoxelShapes.combineAndSimplify(Block.createCuboidShape(0, 0, 0, 8, 16, 16), Block.createCuboidShape(8, 0, 8, 16, 16, 16), BooleanBiFunction.OR);
                    case SOUTH -> VoxelShapes.combineAndSimplify(Block.createCuboidShape(0, 0, 0, 16, 16, 8), Block.createCuboidShape(0, 0, 8, 8, 16, 16), BooleanBiFunction.OR);
                    case NORTH -> VoxelShapes.combineAndSimplify(Block.createCuboidShape(0, 0, 8, 16, 16, 16), Block.createCuboidShape(8, 0, 0, 16, 16, 8), BooleanBiFunction.OR);
                    default -> VoxelShapes.cuboid(1, 1, 1, 1, 1, 1);
                };
            } else {
                return switch (state.get(Properties.HORIZONTAL_FACING)) { //good
                    case WEST -> VoxelShapes.combineAndSimplify(Block.createCuboidShape(0, 0, 8, 16, 16, 16), Block.createCuboidShape(8, 0, 0, 16, 16, 8), BooleanBiFunction.OR);
                    case EAST -> VoxelShapes.combineAndSimplify(Block.createCuboidShape(0, 0, 0, 16, 16, 8), Block.createCuboidShape(0, 0, 8, 8, 16, 16), BooleanBiFunction.OR);
                    case SOUTH ->VoxelShapes.combineAndSimplify(Block.createCuboidShape(8, 0, 0, 16, 16, 16), Block.createCuboidShape(0, 0, 0, 8, 16, 8), BooleanBiFunction.OR);
                    case NORTH -> VoxelShapes.combineAndSimplify(Block.createCuboidShape(0, 0, 0, 8, 16, 16), Block.createCuboidShape(8, 0, 8, 16, 16, 16), BooleanBiFunction.OR);
                    default -> VoxelShapes.cuboid(1, 1, 1, 1, 1, 1);
                };
            }
        }
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        Direction direction = state.get(FACING);
        switch (mirror) {
            case LEFT_RIGHT -> {
                if (direction.getAxis() != Direction.Axis.Z) break;
                return state.rotate(BlockRotation.CLOCKWISE_180);
            }
            case FRONT_BACK -> {
                if (direction.getAxis() != Direction.Axis.X) break;
                return state.rotate(BlockRotation.CLOCKWISE_180);
            }
        }
        return super.mirror(state, mirror);
    }

    public static boolean isVerticalSlab(BlockState state) {
        return state.getBlock() instanceof VerticalSlabBlock;
    }

    private static boolean isDifferentOrientation(BlockState state, BlockView world, BlockPos pos, Direction dir) {
        BlockState blockState = world.getBlockState(pos.offset(dir));
        return !isVerticalSlab(blockState) || blockState.get(FACING) != state.get(FACING);
    }

    private static VerticalSlabShape getVerticalSlabShape(BlockState state, BlockView world, BlockPos pos) {
        Direction direction = (Direction)state.get(FACING);
        BlockState blockState = world.getBlockState(pos.offset(direction));
        if (isVerticalSlab(blockState) ) {
            Direction direction2 = (Direction)blockState.get(FACING);
            if (direction2.getAxis() != ((Direction)state.get(FACING)).getAxis() && isDifferentOrientation(state, world, pos, direction2.getOpposite())) {
                if (direction2 == direction.rotateYCounterclockwise()) {
                    return VerticalSlabShape.INNER_LEFT;
                }

                return VerticalSlabShape.INNER_RIGHT;
            }
        }

        BlockState blockState2 = world.getBlockState(pos.offset(direction.getOpposite()));
        if (isVerticalSlab(blockState2)) {
            Direction direction3 = (Direction)blockState2.get(FACING);
            if (direction3.getAxis() != ((Direction)state.get(FACING)).getAxis() && isDifferentOrientation(state, world, pos, direction3)) {
                if (direction3 == direction.rotateYCounterclockwise()) {
                    return VerticalSlabShape.OUTER_LEFT;
                }

                return VerticalSlabShape.OUTER_RIGHT;
            }
        }

        return VerticalSlabShape.STRAIGHT;
    }
}
