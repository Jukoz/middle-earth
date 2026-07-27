package net.sevenstars.middleearth.block.special.plants;

import com.mojang.serialization.MapCodec;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.BiFunction;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SupportType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public final class BackportedLeafLitterBlock extends BushBlock {
    public static final MapCodec<BackportedLeafLitterBlock> CODEC = simpleCodec(BackportedLeafLitterBlock::new);
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final IntegerProperty AMOUNT = IntegerProperty.create("segment_amount", 1, 4);
    private static final Map<Direction, VoxelShape> SINGLE_SEGMENT = createSingleSegmentShapes();
    private static final BiFunction<Direction, Integer, VoxelShape> SHAPE = Util.memoize(
            (facing, amount) -> {
                VoxelShape result = Shapes.empty();
                Direction segmentDirection = facing;
                for (int segment = 0; segment < amount; segment++) {
                    result = Shapes.or(result, SINGLE_SEGMENT.get(segmentDirection));
                    segmentDirection = segmentDirection.getCounterClockWise();
                }
                return result.optimize();
            });

    public BackportedLeafLitterBlock(BlockBehaviour.Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(AMOUNT, 1));
    }

    private static Map<Direction, VoxelShape> createSingleSegmentShapes() {
        Map<Direction, VoxelShape> shapes = new EnumMap<>(Direction.class);
        shapes.put(Direction.NORTH, Block.box(0.0, 0.0, 0.0, 8.0, 1.0, 8.0));
        shapes.put(Direction.EAST, Block.box(8.0, 0.0, 0.0, 16.0, 1.0, 8.0));
        shapes.put(Direction.SOUTH, Block.box(8.0, 0.0, 8.0, 16.0, 1.0, 16.0));
        shapes.put(Direction.WEST, Block.box(0.0, 0.0, 8.0, 8.0, 1.0, 16.0));
        return shapes;
    }

    @Override
    protected MapCodec<BackportedLeafLitterBlock> codec() {
        return CODEC;
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
        return !context.isSecondaryUseActive()
                        && context.getItemInHand().is(asItem())
                        && state.getValue(AMOUNT) < 4
                || super.canBeReplaced(state, context);
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos floorPos = pos.below();
        return level.getBlockState(floorPos).isFaceSturdy(level, floorPos, Direction.UP, SupportType.FULL);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE.apply(state.getValue(FACING), state.getValue(AMOUNT));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState existing = context.getLevel().getBlockState(context.getClickedPos());
        return existing.is(this)
                ? existing.setValue(AMOUNT, Math.min(4, existing.getValue(AMOUNT) + 1))
                : defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, AMOUNT);
    }
}
