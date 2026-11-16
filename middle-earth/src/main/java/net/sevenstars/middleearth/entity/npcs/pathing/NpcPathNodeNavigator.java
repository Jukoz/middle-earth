package net.sevenstars.middleearth.entity.npcs.pathing;

import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.ai.pathing.PathMinHeap;
import net.minecraft.entity.ai.pathing.PathNode;
import net.minecraft.entity.ai.pathing.PathNodeMaker;
import net.minecraft.entity.ai.pathing.PathNodeNavigator;
import net.minecraft.entity.ai.pathing.TargetPathNode;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.ChunkCache;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

// The game's search with one difference. The game writes a node's walked length before checking whether the newly found
// route to it is better, so a longer route could overwrite a shorter one, and a node near the start could be dropped for
// being past the longest allowed path. Which nodes got dropped depended on the order the search visited them in
public class NpcPathNodeNavigator extends PathNodeNavigator {
    // Same weighting the game uses for the distance still to go against the distance walked
    private static final float HEURISTIC_WEIGHT = 1.5f;
    // Same size as the game's own successor array
    private static final int MAXIMUM_SUCCESSORS = 32;

    private final PathNodeMaker maker;
    private final PathNode[] successors = new PathNode[MAXIMUM_SUCCESSORS];
    private final PathMinHeap heap = new PathMinHeap();
    private int nodes;

    public NpcPathNodeNavigator(PathNodeMaker maker, int nodes) {
        super(maker, nodes);
        this.maker = maker;
        this.nodes = nodes;
    }

    // The game resets this from the follow range every so often, which would undo the larger search
    @Override
    public void setRange(int range) {
        this.nodes = Math.max(this.nodes, range);
    }

    @Override
    public @Nullable Path findPathToAny(ChunkCache world, MobEntity mob, Set<BlockPos> positions, float followRange, int distance, float rangeMultiplier) {
        heap.clear();
        maker.init(world, mob);
        PathNode start = maker.getStart();
        if(start == null)
            return null;
        Map<TargetPathNode, BlockPos> targets = new HashMap<>();
        for(BlockPos pos : positions)
            targets.put(new TargetPathNode(pos.getX(), pos.getY(), pos.getZ()), pos);
        Path path = search(start, targets, followRange, distance, rangeMultiplier);
        maker.clear();
        return path;
    }

    private @Nullable Path search(PathNode start, Map<TargetPathNode, BlockPos> targets, float followRange, int distance, float rangeMultiplier) {
        Set<TargetPathNode> goals = targets.keySet();
        start.penalizedPathLength = 0;
        start.distanceToNearestTarget = distances(start, goals);
        start.heapWeight = start.distanceToNearestTarget;
        heap.clear();
        heap.push(start);
        Set<TargetPathNode> reached = new HashSet<>();
        int visited = 0;
        int most = (int) (nodes * rangeMultiplier);
        while(!heap.isEmpty() && ++visited < most) {
            PathNode node = heap.pop();
            node.visited = true;
            for(TargetPathNode goal : goals) {
                if(node.getManhattanDistance(goal) <= distance) {
                    goal.markReached();
                    reached.add(goal);
                }
            }
            if(!reached.isEmpty())
                break;
            if(node.getDistance(start) >= followRange)
                continue;
            int count = maker.getSuccessors(successors, node);
            for(int i = 0; i < count; i++) {
                PathNode next = successors[i];
                float step = node.getDistance(next);
                float length = node.pathLength + step;
                float weighed = node.penalizedPathLength + step + next.penalty;
                if(length < followRange && (!next.isInHeap() || weighed < next.penalizedPathLength)) {
                    next.pathLength = length;
                    next.previous = node;
                    next.penalizedPathLength = weighed;
                    next.distanceToNearestTarget = distances(next, goals) * HEURISTIC_WEIGHT;
                    if(next.isInHeap()) {
                        heap.setNodeWeight(next, next.penalizedPathLength + next.distanceToNearestTarget);
                    } else {
                        next.heapWeight = next.penalizedPathLength + next.distanceToNearestTarget;
                        heap.push(next);
                    }
                }
            }
        }
        Optional<Path> best = !reached.isEmpty()
                ? reached.stream().map(goal -> createPath(goal.getNearestNode(), targets.get(goal), true)).min(Comparator.comparingInt(Path::getLength))
                : goals.stream().map(goal -> createPath(goal.getNearestNode(), targets.get(goal), false))
                        .min(Comparator.comparingDouble(Path::getManhattanDistanceFromTarget).thenComparingInt(Path::getLength));
        return best.orElse(null);
    }

    private static float distances(PathNode node, Set<TargetPathNode> goals) {
        float nearest = Float.MAX_VALUE;
        for(TargetPathNode goal : goals) {
            float distance = node.getDistance(goal);
            goal.updateNearestNode(distance, node);
            nearest = Math.min(distance, nearest);
        }
        return nearest;
    }

    private static Path createPath(PathNode end, BlockPos target, boolean reaches) {
        List<PathNode> nodes = new ArrayList<>();
        PathNode node = end;
        nodes.add(0, node);
        while(node.previous != null) {
            node = node.previous;
            nodes.add(0, node);
        }
        return new Path(nodes, target, reaches);
    }
}
