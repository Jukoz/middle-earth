package net.sevenstars.middleearth.block.special;

import net.minecraft.world.level.block.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LecternBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import java.util.Map;

public class StoneLecternBlock extends LecternBlock {
    private static final VoxelShape BASE_SHAPE;
    private static final Map<Direction, VoxelShape> OUTLINE_SHAPES_BY_DIRECTION;

    public StoneLecternBlock(Properties settings) {
        super(settings);
    }

    @Override
    protected VoxelShape getOcclusionShape(BlockState state, BlockGetter world, BlockPos pos) {
        return BASE_SHAPE;
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return BASE_SHAPE;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return (VoxelShape)OUTLINE_SHAPES_BY_DIRECTION.get(state.getValue(FACING));
    }

    static {
        BASE_SHAPE = Shapes.or(Block.box(0, 0, 0, 16, 2, 16), Block.box(2, 2, 2, 14, 14, 14));
        VoxelShape north = Shapes.or(
                Block.box(0, 10, 1, 16, 14, 5.333333),
                Block.box(0, 12, 5.333333, 16, 16, 9.666667),
                Block.box(0, 14, 9.666667, 16, 18, 14),
                BASE_SHAPE
        );
        VoxelShape south = Shapes.or(
                Block.box(0, 10, 10.666667, 16, 14, 15),
                Block.box(0, 12, 6.333333, 16, 16, 10.666667),
                Block.box(0, 14, 2, 16, 18, 6.333333),
                BASE_SHAPE
        );
        VoxelShape west = Shapes.or(
                Block.box(1, 10, 0, 5.333333, 14, 16),
                Block.box(5.333333, 12, 0, 9.666667, 16, 16),
                Block.box(9.666667, 14, 0, 14, 18, 16),
                BASE_SHAPE
        );
        VoxelShape east = Shapes.or(
                Block.box(10.666667, 10, 0, 15, 14, 16),
                Block.box(6.333333, 12, 0, 10.666667, 16, 16),
                Block.box(2, 14, 0, 6.333333, 18, 16),
                BASE_SHAPE
        );
        OUTLINE_SHAPES_BY_DIRECTION = Map.of(
                Direction.NORTH, north,
                Direction.SOUTH, south,
                Direction.WEST, west,
                Direction.EAST, east
        );
    }
}
