package net.jukoz.me.block.special;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.enums.BlockFace;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Property;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;

import java.util.Objects;

public class ThickLadderBlock extends WallMountedBlock {
    public static final MapCodec<ThickLadderBlock> CODEC = createCodec(ThickLadderBlock::new);

    protected static final VoxelShape EAST_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 3.0, 16.0, 16.0);
    protected static final VoxelShape WEST_SHAPE = Block.createCuboidShape(13.0, 0.0, 0.0, 16.0, 16.0, 16.0);
    protected static final VoxelShape SOUTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 3.0);
    protected static final VoxelShape NORTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 13.0, 16.0, 16.0, 16.0);
    protected static final VoxelShape BOTTOM_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 3.0, 16.0);
    protected static final VoxelShape TOP_SHAPE = Block.createCuboidShape(0.0, 13.0, 0.0, 16.0, 16.0, 16.0);

    @Override
    protected MapCodec<? extends WallMountedBlock> getCodec() {
        return CODEC;
    }

    public ThickLadderBlock(Settings settings) {
        super(settings.nonOpaque());
        this.setDefaultState((BlockState)((BlockState)((BlockState)this.stateManager.getDefaultState()).with(FACING, Direction.NORTH)).with(FACE, BlockFace.WALL));
    }

    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{FACING, FACE});
    }

    protected boolean canPathfindThrough(BlockState state, NavigationType type) {
        return false;
    }

    protected BlockState rotate(BlockState state, BlockRotation rotation) {
        return (BlockState)state.with(FACING, rotation.rotate((Direction)state.get(FACING)));
    }

    protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return true;
    }

    protected BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation((Direction)state.get(FACING)));
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction[] var2 = ctx.getPlacementDirections();
        int var3 = var2.length;


        for (Direction direction : var2) {
            BlockState blockState;
            if (direction.getAxis() == Direction.Axis.Y) {
                if(Objects.requireNonNull(ctx.getPlayer()).isSneaking()){
                    blockState = (BlockState) ((BlockState) this.getDefaultState().with(FACE, BlockFace.WALL)).with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
                } else {
                    blockState = (BlockState) ((BlockState) this.getDefaultState().with(FACE, direction == Direction.UP ? BlockFace.CEILING : BlockFace.FLOOR)).with(FACING, ctx.getHorizontalPlayerFacing());
                }
            } else {
                blockState = (BlockState) ((BlockState) this.getDefaultState().with(FACE, BlockFace.WALL)).with(FACING, direction.getOpposite());
            }

            if (blockState.canPlaceAt(ctx.getWorld(), ctx.getBlockPos())) {
                return blockState;
            }
        }

        return null;
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if(state.get(FACE) == BlockFace.CEILING){
            return TOP_SHAPE;
        } else if (state.get(FACE) == BlockFace.FLOOR){
            return BOTTOM_SHAPE;
        } else {
            return switch ((Direction) state.get(FACING)) {
                default -> NORTH_SHAPE;
                case SOUTH -> SOUTH_SHAPE;
                case WEST -> WEST_SHAPE;
                case EAST -> EAST_SHAPE;
            };
        }
    }
}
