package net.jukoz.me.block.special;

import net.minecraft.block.BarrelBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class ThinBarrelBlock extends BarrelBlock {
    public ThinBarrelBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(FACING)){
            case UP,DOWN -> Block.createCuboidShape(1, 0, 1, 15, 16, 15);
            case NORTH,SOUTH -> Block.createCuboidShape(1, 0, 0, 15, 14, 16);
            case EAST,WEST -> Block.createCuboidShape(0, 0, 1, 16, 14, 15);
        };
    }
}
