package net.jukoz.me.block.special;

import net.jukoz.me.entity.ModEntities;
import net.jukoz.me.entity.seat.SeatEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class SeatBlock extends Block {
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final BooleanProperty OCCUPIED = Properties.OCCUPIED;

    public SeatBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(FACING, Direction.NORTH).with(WATERLOGGED, false).with(OCCUPIED, false));
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        float yaw = state.get(FACING).getOpposite().asRotation();
        SeatEntity seat = new SeatEntity(ModEntities.SEAT_ENTITY, world);
        seat.setPos(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
        seat.refreshPositionAndAngles(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, yaw, 0);
        seat.setNoGravity(true);
        seat.setSilent(true);
        seat.setInvisible(true);
        seat.setInvulnerable(true);

        if(world.isClient) {
            return ActionResult.CONSUME;
        } else if(player.isSneaking() || player.isSpectator()) {
            return ActionResult.FAIL;
        } else if (player.shouldCancelInteraction()) {
            return ActionResult.SUCCESS;
        } else if (world.getBlockState(pos.up()).isOpaque()){
            player.sendMessage(Text.translatable("alert.me.seat.space_not_empty"), true);
            return ActionResult.SUCCESS;
        }else if (state.get(OCCUPIED)){
            player.sendMessage(Text.translatable("alert.me.seat.occupied"), true);
            return ActionResult.SUCCESS;
        } else {
            if(world.spawnEntity(seat)) {
                player.startRiding(seat, true);
                player.setYaw(yaw);
                player.setHeadYaw(yaw);
                world.setBlockState(pos, state.with(OCCUPIED, true));
                return ActionResult.SUCCESS;
            } else {
                return ActionResult.CONSUME;
            }
        }
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(Properties.HORIZONTAL_FACING, WATERLOGGED, OCCUPIED);
    }

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(Properties.HORIZONTAL_FACING, ctx.getHorizontalPlayerFacing().getOpposite()).with(WATERLOGGED, ctx.getWorld().getFluidState(ctx.getBlockPos()).getFluid() == Fluids.WATER);
    }
    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        Direction direction = state.get(FACING);
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
}
