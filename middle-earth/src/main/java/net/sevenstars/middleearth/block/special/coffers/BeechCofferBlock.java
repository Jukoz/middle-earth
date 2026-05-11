package net.sevenstars.middleearth.block.special.coffers;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.sevenstars.middleearth.block.registration.ModBlockEntities;

public class BeechCofferBlock extends ChestBlock {

    public BeechCofferBlock(Settings settings) {
        super(() -> ModBlockEntities.BEECH_COFFER, settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new BeechCofferBlockEntity(pos, state);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction direction = ctx.getHorizontalPlayerFacing();
        FluidState fluidstate = ctx.getWorld().getFluidState(ctx.getBlockPos());
        return this.getDefaultState().with(FACING, direction).with(WATERLOGGED, fluidstate.getFluid() == Fluids.WATER);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if(state.get(FACING) == Direction.NORTH || state.get(FACING) == Direction.SOUTH) {
            return Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 8.0, 10.0);
        } else {
            return Block.createCuboidShape(2.0, 0.0, 2.0, 10.0, 8.0, 14.0);
        }
    }

}
