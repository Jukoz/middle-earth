package net.sevenstars.middleearth.block.special.plants;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.TintedParticleLeavesBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;
import net.sevenstars.middleearth.block.registration.NatureBlockRegistryME;
import net.sevenstars.middleearth.block.registration.WoodBlockSetRegistryME;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CustomLeavesBlock extends TintedParticleLeavesBlock {
    final protected boolean castShadow;

    public CustomLeavesBlock(float f, Settings settings, boolean castShadow) {
        super(f, settings);
        this.castShadow = castShadow;
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        world.setBlockState(pos, updateDistanceFromLogs(state, world, pos), Block.NOTIFY_ALL);
    }

    // Tree generation only links leaves through their six faces, so fronds that touch diagonally start out
    // marked as cut off. Check the 26 neighbours before letting such a leaf decay.
    @Override
    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!state.get(PERSISTENT) && state.get(DISTANCE) == 7) {
            int distance = findDistanceFromLogs(world, pos);
            if (distance < 7) {
                world.setBlockState(pos, state.with(DISTANCE, distance), Block.NOTIFY_ALL);
                return;
            }
        }
        super.randomTick(state, world, pos, random);
    }

    private static int findDistanceFromLogs(ServerWorld world, BlockPos start) {
        Set<BlockPos> visited = new HashSet<>();
        List<BlockPos> frontier = List.of(start);
        visited.add(start);
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (int distance = 1; distance < 7 && !frontier.isEmpty(); distance++) {
            List<BlockPos> next = new ArrayList<>();
            for (BlockPos pos : frontier) {
                for (int x = -1; x <= 1; x++) {
                    for (int y = -1; y <= 1; y++) {
                        for (int z = -1; z <= 1; z++) {
                            mutable.set(pos, x, y, z);
                            if (visited.contains(mutable)) continue;
                            BlockState neighbor = world.getBlockState(mutable);
                            if (neighbor.isIn(BlockTags.LOGS)) return distance;
                            if (neighbor.getBlock() instanceof LeavesBlock) {
                                BlockPos immutable = mutable.toImmutable();
                                visited.add(immutable);
                                next.add(immutable);
                            }
                        }
                    }
                }
            }
            frontier = next;
        }
        return 7;
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
        int i;
        if (state.get(WATERLOGGED)) {
            tickView.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        if ((i = getDistanceFromLog(neighborState) + 1) != 1 || state.get(DISTANCE) != i) {
            tickView.scheduleBlockTick(pos, this, 1);
        }
        return state;
    }

    private static BlockState updateDistanceFromLogs(BlockState state, WorldAccess world, BlockPos pos) {
        int i = 7;
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for(int x = -1; x <= 1; x++) {
            for(int y = -1; y <= 1; y++) {
                for(int z = -1; z <= 1; z++) {
                    if(x == 0 && y == 0 && z == 0) continue;
                    mutable.set(pos, x, y, z);
                    i = Math.min(i, getDistanceFromLog(world.getBlockState(mutable)) + 1);
                    if (i == 1) break;
                }
            }
        }

        return state.with(DISTANCE, i);
    }

    private static int getDistanceFromLog(BlockState state) {
        return LeavesBlock.getOptionalDistanceFromLog(state).orElse(7);
    }

    @Override
    protected int getOpacity(BlockState state) {
        if(castShadow) return super.getOpacity(state);
        return 0;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        BlockState blockState = (this.getDefaultState().with(PERSISTENT, true)).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
        return updateDistanceFromLogs(blockState, ctx.getWorld(), ctx.getBlockPos());
    }

    @Override
    protected ActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (stack.isOf(Items.BONE_MEAL) && state.isOf(WoodBlockSetRegistryME.MALLORN_SET.leaves)) {
            world.setBlockState(pos, NatureBlockRegistryME.FLOWERING_MALLORN_LEAVES.getDefaultState());
            return ActionResult.SUCCESS;
        }
        return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
    }
}
