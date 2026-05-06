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

public class ChestnutCofferBlock extends ChestBlock {

    public ChestnutCofferBlock(Settings settings) {
        super(() -> ModBlockEntities.CHESTNUT_COFFER, settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ChestnutCofferBlockEntity(pos, state);
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
            return Block.createCuboidShape(0.0, 0.0, 2.0, 16.0, 12.0, 13.0);
        } else {
            return Block.createCuboidShape(2.0, 0.0, 0.0, 13.0, 12.0, 16.0);
        }
    }
}
