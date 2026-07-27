package net.sevenstars.middleearth.block.special;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WoodChairBlock extends SeatBlock implements SimpleWaterloggedBlock {

    public WoodChairBlock(Properties settings) {
        super(settings);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(FACING)){
            default -> Shapes.join(Block.box(2, 0, 4, 14, 9, 16), Block.box(2, 9, 13, 14, 16, 16), BooleanOp.OR);
            case SOUTH -> Shapes.join(Block.box(2, 0, 0, 14, 9, 12), Block.box(2, 9, 0, 14, 16, 3), BooleanOp.OR);
            case EAST -> Shapes.join(Block.box(0, 0, 2, 12, 9, 14), Block.box(0, 9, 2, 3, 16, 14), BooleanOp.OR);
            case WEST -> Shapes.join(Block.box(4, 0, 2, 16, 9, 14), Block.box(13, 9, 2, 16, 16, 14), BooleanOp.OR);
        };
    }
}
