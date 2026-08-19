package net.sevenstars.middleearth.block.special.coffers;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.sevenstars.middleearth.block.registration.ModBlockEntities;

public class BeechCofferBlock extends ChestBlock {

    public BeechCofferBlock(Properties settings) {
        super(settings, () -> ModBlockEntities.BEECH_COFFER);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new BeechCofferBlockEntity(pos, state);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        Direction direction = ctx.getHorizontalDirection();
        FluidState fluidstate = ctx.getLevel().getFluidState(ctx.getClickedPos());
        return this.defaultBlockState().setValue(FACING, direction).setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        if(state.getValue(FACING) == Direction.NORTH) {
            return Block.box(2.0, 0.0, 2.0, 14.0, 8.0, 10.0);
        } else if(state.getValue(FACING) == Direction.SOUTH) {
            return Block.box(2.0, 0.0, 6.0, 14.0, 8.0, 14.0);
        } else if(state.getValue(FACING) == Direction.EAST) {
            return Block.box(6.0, 0.0, 2.0, 14.0, 8.0, 14.0);
        } else {
            return Block.box(2.0, 0.0, 2.0, 10.0, 8.0, 14.0);
        }
    }

}
