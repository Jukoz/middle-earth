package net.sevenstars.middleearth.entity.npcs.routine.activity;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.sevenstars.middleearth.resources.datas.npc_classes.data.RoutineData;
import net.sevenstars.middleearth.entity.npcs.routine.NpcRoutine.Phase;
import net.sevenstars.middleearth.entity.npcs.routine.NpcRoutine;

import java.util.List;

import static net.sevenstars.middleearth.entity.npcs.routine.NpcRoutine.ROUTINE_TIME_LIMIT;
import static net.sevenstars.middleearth.entity.npcs.routine.RoutineWalker.MAXIMUM_HEIGHT_CHANGE;

public class PatrolActivity extends RoutineActivity {
    private static final int WATCH_TIME = 20 * 3;
    private static final int WATCH_TIME_VARIATION = 20 * 4;

    private static final int MINIMUM_ROUTE_POINTS = 2;
    // A rider's round is this much further out than the one on foot
    private static final int RIDER_MARGIN = 18;

    public int ringIndex = 0;
    private int ringStep = 1;
    public int ringLeft = 0;
    public int laps = 0;

    public PatrolActivity(NpcRoutine engine) {
        super(engine);
    }

    @Override
    public boolean begin(ServerWorld world, RoutineData.Activity picked, BlockPos home) {
        List<BlockPos> route = engine.site.getPatrolRoute();
        if(route.size() < MINIMUM_ROUTE_POINTS)
            return engine.cannotStart(picked, "no round to walk in this build");
        ringIndex = 0;
        for(int i = 1; i < route.size(); i++) {
            if(route.get(i).getSquaredDistance(npc.getBlockPos()) < route.get(ringIndex).getSquaredDistance(npc.getBlockPos()))
                ringIndex = i;
        }
        laps = 0;
        ringStep = (npc.getUuid().hashCode() & 1) == 0 ? 1 : -1;
        ringLeft = route.size();
        engine.spot = routePoint(world, route.get(ringIndex));
        return true;
    }

    @Override
    public void arrive(ServerWorld world, Random random, long time) {
        engine.phaseEnd = time + WATCH_TIME + random.nextInt(WATCH_TIME_VARIATION);
        // Watches the land outside, with the place for a rider being further out than the ring
        double awayX = npc.getX() - engine.site.getCenterX();
        double awayZ = npc.getZ() - engine.site.getCenterZ();
        engine.lookTarget = new Vec3d(npc.getX() + awayX, npc.getEyeY(), npc.getZ() + awayZ);
    }

    @Override
    public void work(ServerWorld world, Random random, boolean done, long time) {
        if(!done)
            return;
        ringLeft--;
        // The watch walks its rounds until the shift is over, rather than cross the middle of the place to go home after each.
        // With nobody about to see it, the round ends, as every other routine does
        if(!engine.onDuty(world) || !engine.isWatched(world)) {
            if(engine.onDuty(world))
                engine.abandoned("cut its round short, no player near");
            engine.startReturn(world);
        } else {
            if(ringLeft <= 0) {
                ringLeft = engine.site.getPatrolRoute().size();
                laps++;
            }
            engine.deadline = world.getTime() + ROUTINE_TIME_LIMIT;
            nextWaypoint();
        }
    }

    // Riders go round further out, where a horse has room to go
    private BlockPos routePoint(ServerWorld world, BlockPos point) {
        if(!engine.walker.riding() || engine.site.hasMarkedRoute())
            return point;
        double outX = point.getX() - engine.site.getCenterX();
        double outZ = point.getZ() - engine.site.getCenterZ();
        double length = Math.hypot(outX, outZ);
        if(length < 1)
            return point;
        int x = MathHelper.floor(point.getX() + outX / length * RIDER_MARGIN);
        int z = MathHelper.floor(point.getZ() + outZ / length * RIDER_MARGIN);
        if(!world.isChunkLoaded(x >> 4, z >> 4))
            return point;
        BlockPos ground = new BlockPos(x, world.getTopY(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, x, z), z);
        boolean open = Math.abs(ground.getY() - point.getY()) <= MAXIMUM_HEIGHT_CHANGE && world.getFluidState(ground.down()).isEmpty() && !engine.site.isBuilding(x, ground.getY(), z);
        return open ? ground : point;
    }

    public void nextWaypoint() {
        List<BlockPos> ring = engine.site.getPatrolRoute();
        int next = ringIndex + ringStep;
        // Turns back at the ends of a round broken by a lake or a cliff
        if(engine.site.isPatrolArc() && (next < 0 || next >= ring.size())) {
            ringStep = -ringStep;
            next = ringIndex + ringStep;
        }
        ringIndex = Math.floorMod(next, ring.size());
        engine.spot = npc.getEntityWorld() instanceof ServerWorld world ? routePoint(world, ring.get(ringIndex)) : ring.get(ringIndex);
        engine.lookTarget = null;
        engine.phase = Phase.GOING;
        engine.walker.resetWalk();
        engine.thinkCooldown = 0;
        // Dusk and dawn can fall in the middle of a round
        engine.showProp();
    }
}
