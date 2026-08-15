package net.sevenstars.middleearth.block.special.trial;

import net.minecraft.block.*;
import net.minecraft.block.enums.BedPart;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import org.jetbrains.annotations.Nullable;

public class BarrowTrialSpawner extends TrialSpawnerBlock {
    public static final EnumProperty<Direction> FACING = Properties.HORIZONTAL_FACING;
    public static final EnumProperty<BedPart> PART = Properties.BED_PART;

    public BarrowTrialSpawner(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(PART, BedPart.FOOT).with(FACING, Direction.NORTH));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(PART).add(FACING);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction direction = ctx.getHorizontalPlayerFacing();
        BlockPos blockPos = ctx.getBlockPos();
        BlockPos blockPos2 = blockPos.offset(direction);
        World world = ctx.getWorld();
        return world.getBlockState(blockPos2).canReplace(ctx) && world.getWorldBorder().contains(blockPos2) ? this.getDefaultState().with(FACING, direction) : null;
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isClient && player.shouldSkipBlockDrops()) {
            BedPart bedPart = state.get(PART);
            if (bedPart == BedPart.FOOT) {
                BlockPos blockPos = pos.offset(getDirectionTowardsOtherPart(bedPart, state.get(FACING)));
                BlockState blockState = world.getBlockState(blockPos);
                if (blockState.isOf(this) && blockState.get(PART) == BedPart.HEAD) {
                    world.setBlockState(blockPos, Blocks.AIR.getDefaultState(), Block.NOTIFY_ALL | Block.SKIP_DROPS);
                    world.syncWorldEvent(player, WorldEvents.BLOCK_BROKEN, blockPos, Block.getRawIdFromState(blockState));
                }
            }
        }

        return super.onBreak(world, pos, state, player);
    }

    private static Direction getDirectionTowardsOtherPart(BedPart part, Direction direction) {
        return part == BedPart.FOOT ? direction : direction.getOpposite();
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        if (!world.isClient) {
            BlockPos blockPos = pos.offset(state.get(FACING));
            world.setBlockState(blockPos, state.with(PART, BedPart.HEAD), Block.NOTIFY_ALL);
            world.updateNeighbors(pos, Blocks.AIR);
            state.updateNeighbors(world, pos, Block.NOTIFY_ALL);
        }
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if(state.get(FACING) == Direction.NORTH || state.get(FACING) == Direction.SOUTH) {
            return VoxelShapes.union(Block.createCuboidShape(3.0, 12.0, 0.0, 13.0, 14.0, 16.0),
                    Block.createColumnShape(16.0, 0, 12.0));
        } else {
            return VoxelShapes.union(Block.createCuboidShape(0.0, 12.0, 3.0, 16.0, 14.0, 13.0),
                Block.createColumnShape(16.0, 0, 12.0));
        }
    }

    @Override
    protected BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }
}
