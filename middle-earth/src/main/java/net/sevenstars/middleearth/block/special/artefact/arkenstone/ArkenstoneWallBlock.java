package net.sevenstars.middleearth.block.special.artefact.arkenstone;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class ArkenstoneWallBlock extends HorizontalFacingBlock {

    public ArkenstoneWallBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH));
    }

    @Override
    protected MapCodec<? extends HorizontalFacingBlock> getCodec() {
        return ArkenstoneWallBlock.createCodec(ArkenstoneWallBlock::new);
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return (BlockState)((BlockState)this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing()));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(FACING)){
            default -> Block.createCuboidShape(6, 6, 0, 10, 10, 4);
            case SOUTH -> Block.createCuboidShape(6, 6, 12, 10, 10, 16);
            case EAST -> Block.createCuboidShape(12, 6, 6, 16, 10, 10);
            case WEST -> Block.createCuboidShape(0, 6, 6, 4, 10, 10);
        };
    }

}
