package net.sevenstars.middleearth.block.special;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WoodBenchBlock extends SeatBlock implements SimpleWaterloggedBlock {

    public WoodBenchBlock(Properties settings) {
        super(settings);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return switch(state.getValue(FACING)) {
            case WEST, EAST -> Block.box(2, 0, 0, 14, 9, 16);
            case SOUTH, NORTH -> Block.box(0, 0, 2, 16, 9, 14);
            default -> Shapes.box(1,1,1,1,1,1);
        };
    }
}
