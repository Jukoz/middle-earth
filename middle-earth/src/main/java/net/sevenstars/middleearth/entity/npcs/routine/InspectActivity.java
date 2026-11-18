package net.sevenstars.middleearth.entity.npcs.routine;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.sevenstars.middleearth.block.special.structureManager.marker.MarkerKind;
import net.sevenstars.middleearth.entity.npcs.routine.NpcRoutine.Phase;
import net.sevenstars.middleearth.resources.datas.npc_classes.data.RoutineData;
import net.sevenstars.middleearth.resources.datas.structure_features.StructureFeatureData;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

import static net.sevenstars.middleearth.entity.npcs.routine.NpcRoutine.ROUTINE_TIME_LIMIT;

class InspectActivity extends RoutineActivity {
    private static final int INSPECT_TIME = 20 * 6;
    private static final int INSPECT_TIME_VARIATION = 20 * 6;

    private static final int INSPECT_RING_STOPS = 4;
    private static final int MINIMUM_RING_POINTS = 3;

    private final ArrayDeque<StructureFeatureData.Marker> stops = new ArrayDeque<>();

    InspectActivity(NpcRoutine routine) {
        super(routine);
    }

    @Override
    boolean begin(ServerWorld world, RoutineData.Activity picked, BlockPos home) {
        Random random = npc.getRandom();
        stops.clear();
        List<StructureFeatureData.Marker> posts = new ArrayList<>(routine.site.getMarkers(MarkerKind.GUARD_POST));
        posts.addAll(routine.site.getMarkers(MarkerKind.ENTRANCE));
        if(posts.isEmpty()) {
            List<BlockPos> ring = routine.site.getRing();
            if(ring.size() < MINIMUM_RING_POINTS)
                return routine.cannotStart(picked, "no post marked and no round to walk in this build");
            int step = Math.max(1, ring.size() / INSPECT_RING_STOPS);
            int first = random.nextInt(ring.size());
            for(int i = 0; i < Math.min(INSPECT_RING_STOPS, ring.size()); i++)
                stops.add(new StructureFeatureData.Marker(MarkerKind.PATROL_POINT, ring.get((first + i * step) % ring.size()), null));
        } else {
            BlockPos at = npc.getBlockPos();
            while(!posts.isEmpty()) {
                StructureFeatureData.Marker nearest = posts.getFirst();
                for(StructureFeatureData.Marker post : posts) {
                    if(post.pos().getSquaredDistance(at) < nearest.pos().getSquaredDistance(at))
                        nearest = post;
                }
                posts.remove(nearest);
                stops.add(nearest);
                at = nearest.pos();
            }
        }
        StructureFeatureData.Marker first = stops.poll();
        routine.spot = first.pos();
        routine.facing = first.facing();
        return true;
    }

    @Override
    void arrive(ServerWorld world, Random random, long time) {
        routine.phaseEnd = time + INSPECT_TIME + random.nextInt(INSPECT_TIME_VARIATION);
        routine.lookTarget = routine.facingTarget();
    }

    @Override
    void work(ServerWorld world, Random random, boolean done, long time) {
        if(!done)
            return;
        routine.did("inspect", "looked over the post at " + routine.spot.toShortString());
        if(stops.isEmpty() || !routine.onDuty(world))
            routine.startReturn(world);
        else
            nextStop(time);
    }

    // The skip is logged, the rest of the round still runs
    boolean skipStop(ServerWorld world, String why) {
        if(stops.isEmpty())
            return false;
        routine.abandoned("skipped a post, " + why);
        routine.failed = false;
        nextStop(world.getTime());
        return true;
    }

    private void nextStop(long time) {
        StructureFeatureData.Marker next = stops.poll();
        routine.spot = next.pos();
        routine.facing = next.facing();
        routine.lookTarget = null;
        routine.phase = Phase.GOING;
        routine.walker.resetWalk();
        routine.thinkCooldown = 0;
        routine.deadline = time + ROUTINE_TIME_LIMIT;
    }
}
