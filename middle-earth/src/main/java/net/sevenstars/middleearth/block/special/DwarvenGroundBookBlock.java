package net.sevenstars.middleearth.block.special;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class DwarvenGroundBookBlock extends Block {
    public static final DirectionProperty HORIZONTAL_FACING = HorizontalFacingBlock.FACING;
    public static final BooleanProperty OPEN = BooleanProperty.of("open");

    public DwarvenGroundBookBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(HORIZONTAL_FACING, Direction.NORTH).with(OPEN, false));
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if(!world.isClient && player.getAbilities().allowModifyWorld){
            world.setBlockState(pos, state.cycle(OPEN), 2 | 3);
        }
        return ActionResult.SUCCESS;
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING, OPEN);
    }

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(Properties.HORIZONTAL_FACING, ctx.getHorizontalPlayerFacing().getOpposite()).with(OPEN, false);
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(HORIZONTAL_FACING, rotation.rotate(state.get(HORIZONTAL_FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        Direction direction = state.get(HORIZONTAL_FACING);
        switch (mirror) {
            case LEFT_RIGHT -> {
                if (direction.getAxis() != Direction.Axis.Z) break;
                return state.rotate(BlockRotation.CLOCKWISE_180);
            }
            case FRONT_BACK -> {
                if (direction.getAxis() != Direction.Axis.X) break;
                return state.rotate(BlockRotation.CLOCKWISE_180);
            }
        }
        return super.mirror(state, mirror);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if(state.get(OPEN)){
            return switch(state.get(Properties.HORIZONTAL_FACING)) {
                case WEST, EAST -> Block.createCuboidShape(2, 0, 0, 14, 5, 16);
                case SOUTH, NORTH -> Block.createCuboidShape(0, 0, 2, 16, 5, 14);
                default -> VoxelShapes.cuboid(1,1,1,1,1,1);
            };
        }else {
            return switch(state.get(Properties.HORIZONTAL_FACING)) {
                case WEST, EAST -> Block.createCuboidShape(2, 0, 3, 14, 5, 13);
                case SOUTH, NORTH -> Block.createCuboidShape(3, 0, 2, 13, 5, 14);
                default -> VoxelShapes.cuboid(1,1,1,1,1,1);
            };
        }

    }
}
