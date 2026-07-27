package net.sevenstars.middleearth.block.special;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class StoneChairBlock extends SeatBlock implements SimpleWaterloggedBlock {

    public StoneChairBlock(Properties settings) {
        super(settings);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return switch(state.getValue(BlockStateProperties.HORIZONTAL_FACING)) {
            case WEST -> Shapes.join(Block.box(2, 0, 0, 16, 10, 16), Block.box(13, 10, 0, 16, 16, 16), BooleanOp.OR);
            case EAST -> Shapes.join(Block.box(0, 0, 0, 14, 10, 16), Block.box(0, 10, 0, 3, 16, 16), BooleanOp.OR);
            case SOUTH -> Shapes.join(Block.box(0, 0, 0, 16, 10, 14), Block.box(0, 10, 0, 16, 16, 3), BooleanOp.OR);
            case NORTH -> Shapes.join(Block.box(0, 0, 2, 16, 10, 16), Block.box(0, 10, 13, 16, 16, 16), BooleanOp.OR);
            default -> Shapes.box(1,1,1,1,1,1);
        };
    }
}
