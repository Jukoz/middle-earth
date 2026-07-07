package net.sevenstars.middleearth.block.special;

import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

import java.util.Map;

public class StoneLecternBlock extends LecternBlock {
    private static final VoxelShape BASE_SHAPE;
    private static final Map<Direction, VoxelShape> OUTLINE_SHAPES_BY_DIRECTION;

    public StoneLecternBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected VoxelShape getCullingShape(BlockState state) {
        return BASE_SHAPE;
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return BASE_SHAPE;
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return (VoxelShape)OUTLINE_SHAPES_BY_DIRECTION.get(state.get(FACING));
    }

    static {
        BASE_SHAPE = VoxelShapes.union(Block.createColumnShape(16.0, 0.0, 2.0), Block.createColumnShape(12.0, 2.0, 14.0));
        OUTLINE_SHAPES_BY_DIRECTION = VoxelShapes.createHorizontalFacingShapeMap(VoxelShapes.union(Block.createCuboidZShape(16.0, 10.0, 14.0, 1.0, 5.333333), new VoxelShape[]{Block.createCuboidZShape(16.0, 12.0, 16.0, 5.333333, 9.666667), Block.createCuboidZShape(16.0, 14.0, 18.0, 9.666667, 14.0), BASE_SHAPE}));
    }
}
