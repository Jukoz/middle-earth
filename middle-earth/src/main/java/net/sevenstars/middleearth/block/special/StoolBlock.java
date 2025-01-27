package net.sevenstars.middleearth.block.special;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class StoolBlock extends SeatBlock implements Waterloggable {

    public StoolBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch(state.get(FACING)) {
            case WEST, EAST -> Block.createCuboidShape(3, 0, 0, 13, 10, 16);
            case SOUTH, NORTH -> Block.createCuboidShape(0, 0, 3, 16, 10, 13);
            default -> VoxelShapes.cuboid(1,1,1,1,1,1);
        };
    }
}
