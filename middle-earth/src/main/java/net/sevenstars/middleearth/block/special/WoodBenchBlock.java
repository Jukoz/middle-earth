package net.sevenstars.middleearth.block.special;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class WoodBenchBlock extends SeatBlock implements Waterloggable {

    public WoodBenchBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch(state.get(FACING)) {
            case WEST, EAST -> Block.createCuboidShape(2, 0, 0, 14, 9, 16);
            case SOUTH, NORTH -> Block.createCuboidShape(0, 0, 2, 16, 9, 14);
            default -> VoxelShapes.cuboid(1,1,1,1,1,1);
        };
    }
}
