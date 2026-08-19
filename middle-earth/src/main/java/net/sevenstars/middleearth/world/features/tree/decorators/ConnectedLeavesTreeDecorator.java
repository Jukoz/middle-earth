package net.sevenstars.middleearth.world.features.tree.decorators;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.sevenstars.middleearth.world.features.tree.ModTreeDecoratorType;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public final class ConnectedLeavesTreeDecorator extends TreeDecorator {
    public static final ConnectedLeavesTreeDecorator INSTANCE =
            new ConnectedLeavesTreeDecorator();
    public static final MapCodec<ConnectedLeavesTreeDecorator> CODEC =
            MapCodec.unit(INSTANCE);
    private static final Direction[] DIRECTIONS = Direction.values();
    private static final int MAX_SUPPORTED_DISTANCE = 6;

    private ConnectedLeavesTreeDecorator() {
    }

    @Override
    protected TreeDecoratorType<?> type() {
        return ModTreeDecoratorType.CONNECTED_LEAVES;
    }

    @Override
    public void place(Context context) {
        if (context.leaves().isEmpty()) {
            return;
        }

        Set<BlockPos> generatedLeaves = new HashSet<>(context.leaves());
        Set<BlockPos> nearbyLeaves = collectNearbyLeaves(
                context, generatedLeaves
        );
        Set<BlockPos> supportedLeaves = findSupportedLeaves(
                context, nearbyLeaves
        );

        Iterator<BlockPos> leaves = context.leaves().iterator();
        while (leaves.hasNext()) {
            BlockPos leaf = leaves.next();
            if (
                    isLeaf(context, leaf)
                            && !supportedLeaves.contains(leaf)
            ) {
                context.setBlock(leaf, Blocks.AIR.defaultBlockState());
                leaves.remove();
            }
        }
    }

    private static Set<BlockPos> collectNearbyLeaves(
            Context context,
            Set<BlockPos> generatedLeaves
    ) {
        Set<BlockPos> nearbyLeaves = new HashSet<>(generatedLeaves.size());
        ArrayDeque<BlockPos> pending = new ArrayDeque<>();
        for (BlockPos leaf : generatedLeaves) {
            if (isLeaf(context, leaf) && nearbyLeaves.add(leaf)) {
                pending.addLast(leaf);
            }
        }

        for (int distance = 0;
             distance < MAX_SUPPORTED_DISTANCE - 1 && !pending.isEmpty();
             distance++) {
            int layerSize = pending.size();
            while (layerSize-- > 0) {
                BlockPos origin = pending.removeFirst();
                for (Direction direction : DIRECTIONS) {
                    BlockPos adjacent = origin.relative(direction);
                    if (
                            isLeaf(context, adjacent)
                                    && nearbyLeaves.add(adjacent)
                    ) {
                        pending.addLast(adjacent);
                    }
                }
            }
        }
        return nearbyLeaves;
    }

    private static Set<BlockPos> findSupportedLeaves(
            Context context,
            Set<BlockPos> nearbyLeaves
    ) {
        Set<BlockPos> supportedLeaves = new HashSet<>(nearbyLeaves.size());
        ArrayDeque<BlockPos> pending = new ArrayDeque<>();
        for (BlockPos leaf : nearbyLeaves) {
            for (Direction direction : DIRECTIONS) {
                if (isLog(context, leaf.relative(direction))) {
                    supportedLeaves.add(leaf);
                    pending.addLast(leaf);
                    break;
                }
            }
        }

        for (int distance = 1;
             distance < MAX_SUPPORTED_DISTANCE && !pending.isEmpty();
             distance++) {
            int layerSize = pending.size();
            while (layerSize-- > 0) {
                BlockPos origin = pending.removeFirst();
                for (Direction direction : DIRECTIONS) {
                    BlockPos adjacent = origin.relative(direction);
                    if (
                            nearbyLeaves.contains(adjacent)
                                    && supportedLeaves.add(adjacent)
                    ) {
                        pending.addLast(adjacent);
                    }
                }
            }
        }
        return supportedLeaves;
    }

    private static boolean isLeaf(Context context, BlockPos pos) {
        return context.level().isStateAtPosition(
                pos,
                state -> LeavesBlock.getOptionalDistanceAt(state).isPresent()
        );
    }

    private static boolean isLog(Context context, BlockPos pos) {
        return context.level().isStateAtPosition(
                pos,
                state -> state.is(BlockTags.LOGS)
        );
    }
}
