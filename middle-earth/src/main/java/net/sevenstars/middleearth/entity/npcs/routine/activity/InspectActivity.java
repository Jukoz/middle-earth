package net.sevenstars.middleearth.entity.npcs.routine.activity;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.sevenstars.middleearth.block.special.structureManager.marker.MarkerKind;
import net.sevenstars.middleearth.resources.datas.structure_features.StructureFeatureData;
import net.minecraft.util.math.random.Random;
import net.sevenstars.middleearth.resources.datas.npc_classes.data.RoutineData;
import net.sevenstars.middleearth.entity.npcs.routine.NpcRoutine.Phase;
import net.sevenstars.middleearth.entity.npcs.routine.NpcRoutine;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

import static net.sevenstars.middleearth.entity.npcs.routine.NpcRoutine.ROUTINE_TIME_LIMIT;

public class InspectActivity extends RoutineActivity {
    private static final int INSPECT_TIME = 20 * 6;
    private static final int INSPECT_TIME_VARIATION = 20 * 6;

    private static final int INSPECT_RING_STOPS = 4;
    private static final int MINIMUM_RING_POINTS = 3;

    public final ArrayDeque<StructureFeatureData.Marker> stops = new ArrayDeque<>();

    public InspectActivity(NpcRoutine engine) {
        super(engine);
    }

    @Override
    public boolean begin(ServerWorld world, RoutineData.Activity picked, BlockPos home) {
        Random random = npc.getRandom();
        stops.clear();
        List<StructureFeatureData.Marker> posts = new ArrayList<>(engine.site.getMarkers(MarkerKind.GUARD_POST));
        posts.addAll(engine.site.getMarkers(MarkerKind.ENTRANCE));
        if(posts.isEmpty()) {
            // Nothing marked, so a walk around the place will do
            List<BlockPos> ring = engine.site.getRing();
            if(ring.size() < MINIMUM_RING_POINTS)
                return engine.cannotStart(picked, "no post marked and no round to walk in this build");
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
        engine.spot = first.pos();
        engine.facing = first.facing();
        return true;
    }

    @Override
    public void arrive(ServerWorld world, Random random, long time) {
        engine.phaseEnd = time + INSPECT_TIME + random.nextInt(INSPECT_TIME_VARIATION);
        engine.lookTarget = engine.facingTarget();
    }

    @Override
    public void work(ServerWorld world, Random random, boolean done, long time) {
        if(!done)
            return;
        engine.did("inspect", "looked over the post at " + engine.spot.toShortString());
        if(stops.isEmpty() || !engine.onDuty(world))
            engine.startReturn(world);
        else
            nextStop(time);
    }

    public void nextStop(long time) {
        StructureFeatureData.Marker next = stops.poll();
        engine.spot = next.pos();
        engine.facing = next.facing();
        engine.lookTarget = null;
        engine.phase = Phase.GOING;
        engine.walker.resetWalk();
        engine.thinkCooldown = 0;
        engine.deadline = time + ROUTINE_TIME_LIMIT;
    }
}
