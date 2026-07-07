package net.sevenstars.middleearth.block.special;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.state.property.Properties;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class StoneChairBlock extends SeatBlock implements Waterloggable {

    public StoneChairBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch(state.get(Properties.HORIZONTAL_FACING)) {
            case WEST -> VoxelShapes.combineAndSimplify(Block.createCuboidShape(2, 0, 0, 16, 10, 16), Block.createCuboidShape(13, 10, 0, 16, 16, 16), BooleanBiFunction.OR);
            case EAST -> VoxelShapes.combineAndSimplify(Block.createCuboidShape(0, 0, 0, 14, 10, 16), Block.createCuboidShape(0, 10, 0, 3, 16, 16), BooleanBiFunction.OR);
            case SOUTH -> VoxelShapes.combineAndSimplify(Block.createCuboidShape(0, 0, 0, 16, 10, 14), Block.createCuboidShape(0, 10, 0, 16, 16, 3), BooleanBiFunction.OR);
            case NORTH -> VoxelShapes.combineAndSimplify(Block.createCuboidShape(0, 0, 2, 16, 10, 16), Block.createCuboidShape(0, 10, 13, 16, 16, 16), BooleanBiFunction.OR);
            default -> VoxelShapes.cuboid(1,1,1,1,1,1);
        };
    }
}
