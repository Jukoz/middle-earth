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
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;
import net.sevenstars.middleearth.item.DecorativeItemsME;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;

public class ReinforcedScaffoldingBlock extends ScaffoldingBlock {
    public static final int MAX_SUPPORT_DISTANCE = 14;
    private static final int MAX_STORED_DISTANCE = 7;
    public static final IntProperty SUPPORT_DISTANCE = IntProperty.of("support_distance", 0, MAX_SUPPORT_DISTANCE);
    public static final MapCodec<ScaffoldingBlock> CODEC = createCodec(ReinforcedScaffoldingBlock::new);

    public ReinforcedScaffoldingBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(SUPPORT_DISTANCE, 0));
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
                .with(SUPPORT_DISTANCE, distance)
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
        BlockState updatedState = state.with(DISTANCE, toStoredDistance(distance))
                .with(SUPPORT_DISTANCE, distance)
                .with(BOTTOM, shouldBeBottom(world, pos, distance, this));
        if (distance == MAX_SUPPORT_DISTANCE) {
            if (state.get(SUPPORT_DISTANCE) == MAX_SUPPORT_DISTANCE) {
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

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(SUPPORT_DISTANCE);
    }

    public static boolean shouldBeBottom(BlockView world, BlockPos pos, int distance, Block scaffoldingBlock) {
        return distance > 0 && !world.getBlockState(pos.down()).isOf(scaffoldingBlock);
    }

    public static int calculateDistance(BlockView world, BlockPos pos, Block scaffoldingBlock) {
        Map<BlockPos, Integer> supportDistances = new HashMap<>();
        ArrayDeque<BlockPos> positionsToCheck = new ArrayDeque<>();
        BlockPos startPos = pos.toImmutable();

        supportDistances.put(startPos, 0);
        positionsToCheck.add(startPos);

        while (!positionsToCheck.isEmpty()) {
            BlockPos currentPos = positionsToCheck.removeFirst();
            int currentDistance = supportDistances.get(currentPos);
            BlockPos belowPos = currentPos.down();
            BlockState belowState = world.getBlockState(belowPos);

            if (belowState.isOf(scaffoldingBlock)) {
                AddSupportPosition(positionsToCheck, supportDistances, belowPos, currentDistance, true);
            } else if (belowState.isSideSolidFullSquare(world, belowPos, Direction.UP)) {
                return Math.min(currentDistance, MAX_SUPPORT_DISTANCE);
            }

            if (currentDistance >= MAX_SUPPORT_DISTANCE) {
                continue;
            }

            for (Direction direction : Direction.Type.HORIZONTAL) {
                BlockPos horizontalPos = currentPos.offset(direction);
                if (world.getBlockState(horizontalPos).isOf(scaffoldingBlock)) {
                    AddSupportPosition(positionsToCheck, supportDistances, horizontalPos, currentDistance + 1, false);
                }
            }
        }

        return MAX_SUPPORT_DISTANCE;
    }

    private static void AddSupportPosition(ArrayDeque<BlockPos> positionsToCheck, Map<BlockPos, Integer> supportDistances, BlockPos pos, int distance, boolean checkFirst) {
        Integer knownDistance = supportDistances.get(pos);
        if (knownDistance != null && knownDistance <= distance) {
            return;
        }

        supportDistances.put(pos, distance);
        if (checkFirst) {
            positionsToCheck.addFirst(pos);
        } else {
            positionsToCheck.addLast(pos);
        }
    }

    private static int toStoredDistance(int distance) {
        return Math.min(distance, MAX_STORED_DISTANCE);
    }
}
