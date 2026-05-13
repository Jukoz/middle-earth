package net.sevenstars.middleearth.block.special;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ScaffoldingBlock;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;

public class WoodenScaffoldingBlock extends ScaffoldingBlock {
    public static final MapCodec<ScaffoldingBlock> CODEC = createCodec(WoodenScaffoldingBlock::new)
            .xmap(block -> block, block -> (WoodenScaffoldingBlock) block);

    public WoodenScaffoldingBlock(AbstractBlock.Settings settings) {
        super(settings);
    }

    @Override
    public MapCodec<ScaffoldingBlock> getCodec() {
        return CODEC;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos pos = ctx.getBlockPos();
        World world = ctx.getWorld();
        int distance = calculateDistance(world, pos, this);
        return this.getDefaultState()
                .with(WATERLOGGED, world.getFluidState(pos).getFluid() == Fluids.WATER)
                .with(DISTANCE, distance)
                .with(BOTTOM, shouldBeBottom(world, pos, distance, this));
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
        if (state.get(WATERLOGGED)) {
            tickView.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        if (!world.isClient()) {
            tickView.scheduleBlockTick(pos, this, 1);
        }

        return state;
    }

    @Override
    protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        int distance = calculateDistance(world, pos, this);
        BlockState updatedState = state.with(DISTANCE, distance).with(BOTTOM, shouldBeBottom(world, pos, distance, this));
        if (updatedState.get(DISTANCE) == 7) {
            if (state.get(DISTANCE) == 7) {
                FallingBlockEntity.spawnFromBlock(world, pos, updatedState);
            } else {
                world.breakBlock(pos, true);
            }
        } else if (state != updatedState) {
            world.setBlockState(pos, updatedState, 3);
        }
    }

    @Override
    protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return calculateDistance(world, pos, this) < 7;
    }

    public static boolean shouldBeBottom(BlockView world, BlockPos pos, int distance, Block scaffoldingBlock) {
        return distance > 0 && !world.getBlockState(pos.down()).isOf(scaffoldingBlock);
    }

    public static int calculateDistance(BlockView world, BlockPos pos, Block scaffoldingBlock) {
        BlockPos.Mutable mutable = pos.mutableCopy().move(Direction.DOWN);
        BlockState belowState = world.getBlockState(mutable);
        int distance = 7;
        if (belowState.isOf(scaffoldingBlock)) {
            distance = belowState.get(DISTANCE);
        } else if (belowState.isSideSolidFullSquare(world, mutable, Direction.UP)) {
            return 0;
        }

        for (Direction direction : Direction.Type.HORIZONTAL) {
            BlockState horizontalState = world.getBlockState(mutable.set(pos, direction));
            if (!horizontalState.isOf(scaffoldingBlock)) {
                continue;
            }

            distance = Math.min(distance, horizontalState.get(DISTANCE) + 1);
            if (distance == 1) {
                break;
            }
        }

        return distance;
    }
}
