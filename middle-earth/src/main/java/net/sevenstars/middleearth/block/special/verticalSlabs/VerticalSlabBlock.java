package net.sevenstars.middleearth.block.special.verticalSlabs;

import net.minecraft.world.level.block.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class VerticalSlabBlock extends Block implements SimpleWaterloggedBlock {
    public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty DOUBLE = BooleanProperty.create("double");
    public static final EnumProperty<VerticalSlabShape> SHAPE = EnumProperty.create("shape", VerticalSlabShape.class);


    public VerticalSlabBlock(Properties settings) {
        super(settings);
        this.registerDefaultState((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false).setValue(DOUBLE, false).setValue(SHAPE, VerticalSlabShape.STRAIGHT)))));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED, DOUBLE, SHAPE);
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState state) {
        return !state.getValue(DOUBLE);
    }

    @Override
    protected boolean isPathfindable(BlockState state, PathComputationType type) {
        return false;
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockPos blockPos = ctx.getClickedPos();
        BlockState blockState = ctx.getLevel().getBlockState(blockPos);
        if (blockState.is(this)) {
            return (blockState.setValue(DOUBLE, true)).setValue(WATERLOGGED, false);
        } else {
            FluidState fluidState = ctx.getLevel().getFluidState(blockPos);
            BlockState blockState2 = (BlockState)((BlockState)((BlockState)this.defaultBlockState().setValue(FACING, ctx.getHorizontalDirection().getOpposite())).setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER));
            return (BlockState)blockState2.setValue(SHAPE, getVerticalSlabShape(blockState2, ctx.getLevel(), blockPos));
        }
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            world.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        }

        return direction.getAxis().isHorizontal() ? (BlockState)state.setValue(SHAPE, getVerticalSlabShape(state, world, pos)) : super.updateShape(state, direction, neighborState, world, pos, neighborPos);

    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
        ItemStack itemStack = context.getItemInHand();
        if (state.getValue(DOUBLE) || !itemStack.is(this.asItem())) {
            return false;
        }
        if (context.replacingClickedOnBlock()) {
            return context.getClickedFace() == state.getValue(FACING);
        }
        return true;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        if(state.getValue(DOUBLE)){
            return Shapes.box(0, 0, 0.0, 1, 1, 1);
        } else {
            if(state.getValue(SHAPE) == VerticalSlabShape.STRAIGHT){
                return switch(state.getValue(BlockStateProperties.HORIZONTAL_FACING)) {
                    case WEST -> Shapes.box(0.5, 0, 0, 1, 1, 1);
                    case EAST -> Shapes.box(0, 0, 0, 0.5, 1, 1);
                    case SOUTH -> Shapes.box(0, 0, 0, 1, 1, 0.5);
                    case NORTH -> Shapes.box(0, 0, 0.5, 1, 1, 1);
                    default -> Shapes.box(1,1,1,1,1,1);
                };
            } else if (state.getValue(SHAPE) == VerticalSlabShape.OUTER_LEFT) {
                return switch (state.getValue(BlockStateProperties.HORIZONTAL_FACING)) {
                    case WEST -> Block.box(8, 0, 0, 16, 16, 8);
                    case EAST -> Block.box(0, 0, 8, 8, 16, 16);
                    case SOUTH -> Block.box(0, 0, 0, 8, 16, 8);
                    case NORTH -> Block.box(8, 0, 8, 16, 16, 16);
                    default -> Shapes.box(1, 1, 1, 1, 1, 1);
                };
            }else if (state.getValue(SHAPE) == VerticalSlabShape.OUTER_RIGHT) {
                return switch (state.getValue(BlockStateProperties.HORIZONTAL_FACING)) {
                    case WEST -> Block.box(8, 0, 8, 16, 16, 16);
                    case EAST -> Block.box(0, 0, 0, 8, 16, 8);
                    case SOUTH -> Block.box(8, 0, 0, 16, 16, 8);
                    case NORTH -> Block.box(0, 0, 8, 8, 16, 16);
                    default -> Shapes.box(1, 1, 1, 1, 1, 1);
                };
            }else if (state.getValue(SHAPE) == VerticalSlabShape.INNER_LEFT) { //good
                return switch (state.getValue(BlockStateProperties.HORIZONTAL_FACING)) {
                    case WEST -> Shapes.join(Block.box(8, 0, 0, 16, 16, 16), Block.box(0, 0, 0, 8, 16, 8), BooleanOp.OR);
                    case EAST -> Shapes.join(Block.box(0, 0, 0, 8, 16, 16), Block.box(8, 0, 8, 16, 16, 16), BooleanOp.OR);
                    case SOUTH -> Shapes.join(Block.box(0, 0, 0, 16, 16, 8), Block.box(0, 0, 8, 8, 16, 16), BooleanOp.OR);
                    case NORTH -> Shapes.join(Block.box(0, 0, 8, 16, 16, 16), Block.box(8, 0, 0, 16, 16, 8), BooleanOp.OR);
                    default -> Shapes.box(1, 1, 1, 1, 1, 1);
                };
            } else {
                return switch (state.getValue(BlockStateProperties.HORIZONTAL_FACING)) { //good
                    case WEST -> Shapes.join(Block.box(0, 0, 8, 16, 16, 16), Block.box(8, 0, 0, 16, 16, 8), BooleanOp.OR);
                    case EAST -> Shapes.join(Block.box(0, 0, 0, 16, 16, 8), Block.box(0, 0, 8, 8, 16, 16), BooleanOp.OR);
                    case SOUTH ->Shapes.join(Block.box(8, 0, 0, 16, 16, 16), Block.box(0, 0, 0, 8, 16, 8), BooleanOp.OR);
                    case NORTH -> Shapes.join(Block.box(0, 0, 0, 8, 16, 16), Block.box(8, 0, 8, 16, 16, 16), BooleanOp.OR);
                    default -> Shapes.box(1, 1, 1, 1, 1, 1);
                };
            }
        }
    }

    protected BlockState mirror(BlockState state, Mirror mirror) {
        Direction direction = (Direction) state.getValue(FACING);
        VerticalSlabShape verticalSlabShape = (VerticalSlabShape) state.getValue(SHAPE);
        switch (mirror) {
            case LEFT_RIGHT:
                if (direction.getAxis() == Direction.Axis.Z) {
                    switch (verticalSlabShape) {
                        case INNER_LEFT -> {
                            return (BlockState) state.rotate(Rotation.CLOCKWISE_180).setValue(SHAPE, VerticalSlabShape.INNER_RIGHT);
                        }
                        case INNER_RIGHT -> {
                            return (BlockState) state.rotate(Rotation.CLOCKWISE_180).setValue(SHAPE, VerticalSlabShape.INNER_LEFT);
                        }
                        case OUTER_LEFT -> {
                            return (BlockState) state.rotate(Rotation.CLOCKWISE_180).setValue(SHAPE, VerticalSlabShape.OUTER_RIGHT);
                        }
                        case OUTER_RIGHT -> {
                            return (BlockState) state.rotate(Rotation.CLOCKWISE_180).setValue(SHAPE, VerticalSlabShape.OUTER_LEFT);
                        }
                        default -> {
                            return state.rotate(Rotation.CLOCKWISE_180);
                        }
                    }
                }
                break;
            case FRONT_BACK:
                if (direction.getAxis() == Direction.Axis.X) {
                    switch (verticalSlabShape) {
                        case INNER_LEFT -> {
                            return (BlockState) state.rotate(Rotation.CLOCKWISE_180).setValue(SHAPE, VerticalSlabShape.INNER_LEFT);
                        }
                        case INNER_RIGHT -> {
                            return (BlockState) state.rotate(Rotation.CLOCKWISE_180).setValue(SHAPE, VerticalSlabShape.INNER_RIGHT);
                        }
                        case OUTER_LEFT -> {
                            return (BlockState) state.rotate(Rotation.CLOCKWISE_180).setValue(SHAPE, VerticalSlabShape.OUTER_RIGHT);
                        }
                        case OUTER_RIGHT -> {
                            return (BlockState) state.rotate(Rotation.CLOCKWISE_180).setValue(SHAPE, VerticalSlabShape.OUTER_LEFT);
                        }
                        case STRAIGHT -> {
                            return state.rotate(Rotation.CLOCKWISE_180);
                        }
                    }
                }
        }
        return super.mirror(state, mirror);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    public static boolean isVerticalSlab(BlockState state) {
        return state.getBlock() instanceof VerticalSlabBlock;
    }

    private static boolean isDifferentOrientation(BlockState state, BlockGetter world, BlockPos pos, Direction dir) {
        BlockState blockState = world.getBlockState(pos.relative(dir));
        return !isVerticalSlab(blockState) || blockState.getValue(FACING) != state.getValue(FACING);
    }

    private static VerticalSlabShape getVerticalSlabShape(BlockState state, BlockGetter world, BlockPos pos) {
        Direction direction = (Direction)state.getValue(FACING);
        BlockState blockState = world.getBlockState(pos.relative(direction));
        if (isVerticalSlab(blockState) ) {
            Direction direction2 = (Direction)blockState.getValue(FACING);
            if (direction2.getAxis() != ((Direction)state.getValue(FACING)).getAxis() && isDifferentOrientation(state, world, pos, direction2.getOpposite())) {
                if (direction2 == direction.getCounterClockWise()) {
                    return VerticalSlabShape.INNER_LEFT;
                }

                return VerticalSlabShape.INNER_RIGHT;
            }
        }

        BlockState blockState2 = world.getBlockState(pos.relative(direction.getOpposite()));
        if (isVerticalSlab(blockState2)) {
            Direction direction3 = (Direction)blockState2.getValue(FACING);
            if (direction3.getAxis() != ((Direction)state.getValue(FACING)).getAxis() && isDifferentOrientation(state, world, pos, direction3)) {
                if (direction3 == direction.getCounterClockWise()) {
                    return VerticalSlabShape.OUTER_LEFT;
                }

                return VerticalSlabShape.OUTER_RIGHT;
            }
        }

        return VerticalSlabShape.STRAIGHT;
    }
}
