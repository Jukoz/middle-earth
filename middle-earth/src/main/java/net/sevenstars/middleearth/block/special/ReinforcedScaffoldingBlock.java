package net.sevenstars.middleearth.block.special;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ScaffoldingBlock;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;
import net.sevenstars.middleearth.item.DecorativeItemsME;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ReinforcedScaffoldingBlock extends ScaffoldingBlock {
    public static final int MAX_SUPPORT_DISTANCE = 14;
    private static final int MAX_STORED_DISTANCE = 7;
    public static final MapCodec<ScaffoldingBlock> CODEC = createCodec(ReinforcedScaffoldingBlock::new)
            .xmap(block -> block, block -> (ReinforcedScaffoldingBlock) block);

    public ReinforcedScaffoldingBlock(AbstractBlock.Settings settings) {
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
                .with(DISTANCE, toStoredDistance(distance))
                .with(BOTTOM, shouldBeBottom(world, pos, distance, this));
    }

    @Override
    public ItemStack getPickStack(WorldView world, BlockPos pos, BlockState state, boolean includeData) {
        return DecorativeItemsME.REINFORCED_SCAFFOLDING.getDefaultStack();
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
        BlockState updatedState = state.with(DISTANCE, toStoredDistance(distance)).with(BOTTOM, shouldBeBottom(world, pos, distance, this));
        if (distance == MAX_SUPPORT_DISTANCE) {
            if (state.get(DISTANCE) == MAX_STORED_DISTANCE) {
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
        return calculateDistance(world, pos, this) < MAX_SUPPORT_DISTANCE;
    }

    @Override
    protected boolean canReplace(BlockState state, ItemPlacementContext context) {
        return context.getStack().getItem() instanceof BlockItem blockItem
                && blockItem.getBlock() == this;
    }

    public static boolean shouldBeBottom(BlockView world, BlockPos pos, int distance, Block scaffoldingBlock) {
        return distance > 0 && !world.getBlockState(pos.down()).isOf(scaffoldingBlock);
    }

    public static int calculateDistance(BlockView world, BlockPos pos, Block scaffoldingBlock) {
        return calculateDistance(world, pos, scaffoldingBlock, new HashMap<>(), new HashSet<>());
    }

    private static int calculateDistance(BlockView world, BlockPos pos, Block scaffoldingBlock, Map<BlockPos, Integer> cache, Set<BlockPos> visiting) {
        Integer cachedDistance = cache.get(pos);
        if (cachedDistance != null) {
            return cachedDistance;
        }

        if (!visiting.add(pos)) {
            return MAX_SUPPORT_DISTANCE;
        }

        BlockPos.Mutable mutable = pos.mutableCopy().move(Direction.DOWN);
        BlockState belowState = world.getBlockState(mutable);
        int distance = MAX_SUPPORT_DISTANCE;
        if (belowState.isOf(scaffoldingBlock)) {
            distance = calculateDistance(world, mutable.toImmutable(), scaffoldingBlock, cache, visiting);
        } else if (belowState.isSideSolidFullSquare(world, mutable, Direction.UP)) {
            cache.put(pos, 0);
            visiting.remove(pos);
            return 0;
        }

        for (Direction direction : Direction.Type.HORIZONTAL) {
            BlockPos horizontalPos = mutable.set(pos, direction).toImmutable();
            BlockState horizontalState = world.getBlockState(horizontalPos);
            if (!horizontalState.isOf(scaffoldingBlock)) {
                continue;
            }

            distance = Math.min(distance, calculateDistance(world, horizontalPos, scaffoldingBlock, cache, visiting) + 1);
            if (distance == 1) {
                break;
            }
        }

        distance = Math.min(distance, MAX_SUPPORT_DISTANCE);
        cache.put(pos, distance);
        visiting.remove(pos);
        return distance;
    }

    private static int toStoredDistance(int distance) {
        return Math.min(distance, MAX_STORED_DISTANCE);
    }
}
