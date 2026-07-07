package net.sevenstars.middleearth.block.special;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class WoodChairBlock extends SeatBlock implements Waterloggable {

    public WoodChairBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(FACING)){
            default -> VoxelShapes.combineAndSimplify(Block.createCuboidShape(2, 0, 4, 14, 9, 16), Block.createCuboidShape(2, 9, 13, 14, 16, 16), BooleanBiFunction.OR);
            case SOUTH -> VoxelShapes.combineAndSimplify(Block.createCuboidShape(2, 0, 0, 14, 9, 12), Block.createCuboidShape(2, 9, 0, 14, 16, 3), BooleanBiFunction.OR);
            case EAST -> VoxelShapes.combineAndSimplify(Block.createCuboidShape(0, 0, 2, 12, 9, 14), Block.createCuboidShape(0, 9, 2, 3, 16, 14), BooleanBiFunction.OR);
            case WEST -> VoxelShapes.combineAndSimplify(Block.createCuboidShape(4, 0, 2, 16, 9, 14), Block.createCuboidShape(13, 9, 2, 16, 16, 14), BooleanBiFunction.OR);
        };
    }
}
