package net.sevenstars.middleearth.block.special;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ScaffoldingBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Fluids;
import net.sevenstars.middleearth.item.DecorativeItemsME;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;

public class ReinforcedScaffoldingBlock extends ScaffoldingBlock {
    public static final int MAX_SUPPORT_DISTANCE = 14;
    private static final int MAX_STORED_DISTANCE = 7;
    public static final IntegerProperty SUPPORT_DISTANCE = IntegerProperty.create("support_distance", 0, MAX_SUPPORT_DISTANCE);
    public static final MapCodec<ScaffoldingBlock> CODEC = simpleCodec(ReinforcedScaffoldingBlock::new);

    public ReinforcedScaffoldingBlock(BlockBehaviour.Properties settings) {
        super(settings);
        this.registerDefaultState(this.stateDefinition.any().setValue(SUPPORT_DISTANCE, 0));
    }

    @Override
    public MapCodec<ScaffoldingBlock> codec() {
        return CODEC;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockPos pos = ctx.getClickedPos();
        Level world = ctx.getLevel();
        int distance = calculateDistance(world, pos, this);
        return this.defaultBlockState()
                .setValue(WATERLOGGED, world.getFluidState(pos).getType() == Fluids.WATER)
                .setValue(DISTANCE, toStoredDistance(distance))
                .setValue(SUPPORT_DISTANCE, distance)
                .setValue(BOTTOM, shouldBeBottom(world, pos, distance, this));
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader world, BlockPos pos, BlockState state) {
        return DecorativeItemsME.REINFORCED_SCAFFOLDING.getDefaultInstance();
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            world.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        }

        if (!world.isClientSide()) {
            world.scheduleTick(pos, this, 1);
        }

        return state;
    }

    @Override
    protected void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        int distance = calculateDistance(world, pos, this);
        BlockState updatedState = state.setValue(DISTANCE, toStoredDistance(distance))
                .setValue(SUPPORT_DISTANCE, distance)
                .setValue(BOTTOM, shouldBeBottom(world, pos, distance, this));
        if (distance == MAX_SUPPORT_DISTANCE) {
            if (state.getValue(SUPPORT_DISTANCE) == MAX_SUPPORT_DISTANCE) {
                FallingBlockEntity.fall(world, pos, updatedState);
            } else {
                world.destroyBlock(pos, true);
            }
        } else if (state != updatedState) {
            world.setBlock(pos, updatedState, 3);
        }
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        return calculateDistance(world, pos, this) < MAX_SUPPORT_DISTANCE;
    }

    @Override
    protected boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
        return context.getItemInHand().getItem() instanceof BlockItem blockItem
                && blockItem.getBlock() == this;
    }

    @Override
    public boolean isScaffolding(BlockState state, LevelReader world, BlockPos pos, LivingEntity entity) {
        return true;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(SUPPORT_DISTANCE);
    }

    public static boolean shouldBeBottom(BlockGetter world, BlockPos pos, int distance, Block scaffoldingBlock) {
        return distance > 0 && !world.getBlockState(pos.below()).is(scaffoldingBlock);
    }

    public static int calculateDistance(BlockGetter world, BlockPos pos, Block scaffoldingBlock) {
        Map<BlockPos, Integer> supportDistances = new HashMap<>();
        ArrayDeque<BlockPos> positionsToCheck = new ArrayDeque<>();
        BlockPos startPos = pos.immutable();

        supportDistances.put(startPos, 0);
        positionsToCheck.add(startPos);

        while (!positionsToCheck.isEmpty()) {
            BlockPos currentPos = positionsToCheck.removeFirst();
            int currentDistance = supportDistances.get(currentPos);
            BlockPos belowPos = currentPos.below();
            BlockState belowState = world.getBlockState(belowPos);

            if (belowState.is(scaffoldingBlock)) {
                addSupportPosition(positionsToCheck, supportDistances, belowPos, currentDistance, true);
            } else if (belowState.isFaceSturdy(world, belowPos, Direction.UP)) {
                return Math.min(currentDistance, MAX_SUPPORT_DISTANCE);
            }

            if (currentDistance >= MAX_SUPPORT_DISTANCE) {
                continue;
            }

            for (Direction direction : Direction.Plane.HORIZONTAL) {
                BlockPos horizontalPos = currentPos.relative(direction);
                if (world.getBlockState(horizontalPos).is(scaffoldingBlock)) {
                    addSupportPosition(positionsToCheck, supportDistances, horizontalPos, currentDistance + 1, false);
                }
            }
        }

        return MAX_SUPPORT_DISTANCE;
    }

    private static void addSupportPosition(ArrayDeque<BlockPos> positionsToCheck, Map<BlockPos, Integer> supportDistances, BlockPos pos, int distance, boolean checkFirst) {
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
