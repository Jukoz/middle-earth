package net.sevenstars.middleearth.block.special;

import net.sevenstars.middleearth.block.ModDecorativeBlocks;
import net.minecraft.block.*;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
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
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class WoodenBucketBlock extends Block implements Waterloggable {
    private static final DirectionProperty HORIZONTAL_FACING= Properties.HORIZONTAL_FACING;
    public static final BooleanProperty HANGING = Properties.HANGING;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    public WoodenBucketBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(HORIZONTAL_FACING, Direction.NORTH).with(HANGING, false).with(WATERLOGGED, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING, HANGING, WATERLOGGED);
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
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (player.getStackInHand(player.getActiveHand()).isEmpty() && !player.isInCreativeMode()){
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
            player.equipStack(EquipmentSlot.MAINHAND,  new ItemStack(ModDecorativeBlocks.WOODEN_BUCKET.asItem()));
            world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENTITY_CHICKEN_EGG, SoundCategory.BLOCKS, 0.5f, world.random.nextFloat() * 0.1f - 1.0f);
            return ActionResult.PASS;
        } else{
            return super.onUse(state, world, pos, player, hit);
        }
    }

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction[] var3 = ctx.getPlacementDirections();
        int var4 = var3.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            Direction direction = var3[var5];
            if (direction.getAxis() == Direction.Axis.Y) {
                return this.getDefaultState().with(HANGING, direction == Direction.UP).with(HORIZONTAL_FACING, ctx.getHorizontalPlayerFacing()).with(WATERLOGGED, ctx.getWorld().getFluidState(ctx.getBlockPos()).getFluid() == Fluids.WATER);
            }
        }

        return null;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if(state.get(HANGING)){
            return Block.createCuboidShape(4, 0, 4, 12, 16, 12);
        } else {
            return Block.createCuboidShape(4, 0, 4, 12, 10, 12);
        }
    }

    protected Direction attachedDirection(BlockState state) {
        return state.get(HANGING) ? Direction.DOWN : Direction.UP;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        return attachedDirection(state).getOpposite() == direction && !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }
}
