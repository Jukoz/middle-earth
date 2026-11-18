package net.sevenstars.middleearth.features.npcs.routine;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.sevenstars.middleearth.features.npcs.routine.NpcRoutine.Phase;
import net.sevenstars.middleearth.features.npcs.classes.data.RoutineData;

import java.util.List;

import static net.sevenstars.middleearth.features.npcs.routine.NpcRoutine.ROUTINE_TIME_LIMIT;
import static net.sevenstars.middleearth.features.npcs.routine.RoutineWalker.MAXIMUM_HEIGHT_CHANGE;

class PatrolActivity extends RoutineActivity {
    private static final int WATCH_TIME = 20 * 3;
    private static final int WATCH_TIME_VARIATION = 20 * 4;

    private static final int MINIMUM_ROUTE_POINTS = 2;
    private static final int RIDER_MARGIN = 18;

    int ringIndex = 0;
    private int ringStep = 1;
    private int ringLeft = 0;
    private int laps = 0;

    PatrolActivity(NpcRoutine routine) {
        super(routine);
    }

    @Override
    boolean begin(ServerWorld world, RoutineData.Activity picked, BlockPos home) {
        List<BlockPos> route = routine.site.getPatrolRoute();
        if(route.size() < MINIMUM_ROUTE_POINTS)
            return routine.cannotStart(picked, "no round to walk in this build");
        ringIndex = 0;
        for(int i = 1; i < route.size(); i++) {
            if(route.get(i).getSquaredDistance(npc.getBlockPos()) < route.get(ringIndex).getSquaredDistance(npc.getBlockPos()))
                ringIndex = i;
        }
        laps = 0;
        ringStep = (npc.getUuid().hashCode() & 1) == 0 ? 1 : -1;
        ringLeft = route.size();
        routine.spot = routePoint(world, route.get(ringIndex));
        return true;
    }

    @Override
    void arrive(ServerWorld world, Random random, long time) {
        routine.phaseEnd = time + WATCH_TIME + random.nextInt(WATCH_TIME_VARIATION);
        // A rider's place is further out than the ring
        double awayX = npc.getX() - routine.site.getCenterX();
        double awayZ = npc.getZ() - routine.site.getCenterZ();
        routine.lookTarget = new Vec3d(npc.getX() + awayX, npc.getEyeY(), npc.getZ() + awayZ);
    }

    @Override
    void work(ServerWorld world, Random random, boolean done, long time) {
        if(!done)
            return;
        ringLeft--;
        // The watch walks rounds until the shift ends. With nobody to see it, the round ends
        if(!routine.onDuty(world) || !routine.isWatched(world)) {
            if(routine.onDuty(world))
                routine.abandoned("cut its round short, no player near");
            routine.startReturn(world);
        } else {
            if(ringLeft <= 0) {
                ringLeft = routine.site.getPatrolRoute().size();
                laps++;
            }
            routine.deadline = world.getTime() + ROUTINE_TIME_LIMIT;
            nextWaypoint();
        }
    }

    private BlockPos routePoint(ServerWorld world, BlockPos point) {
        if(!routine.walker.riding() || routine.site.hasMarkedRoute())
            return point;
        double outX = point.getX() - routine.site.getCenterX();
        double outZ = point.getZ() - routine.site.getCenterZ();
        double length = Math.hypot(outX, outZ);
        if(length < 1)
            return point;
        int x = MathHelper.floor(point.getX() + outX / length * RIDER_MARGIN);
        int z = MathHelper.floor(point.getZ() + outZ / length * RIDER_MARGIN);
        if(!world.isChunkLoaded(x >> 4, z >> 4))
            return point;
        BlockPos ground = new BlockPos(x, world.getTopY(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, x, z), z);
        boolean open = Math.abs(ground.getY() - point.getY()) <= MAXIMUM_HEIGHT_CHANGE && world.getFluidState(ground.down()).isEmpty() && !routine.site.isBuilding(x, ground.getY(), z);
        return open ? ground : point;
    }

    boolean canSkipStretch() {
        return ringLeft > 1 && (laps > 0 || ringLeft < routine.site.getPatrolRoute().size());
    }

    void skipStretch() {
        ringLeft--;
        nextWaypoint();
    }

    private void nextWaypoint() {
        List<BlockPos> ring = routine.site.getPatrolRoute();
        int next = ringIndex + ringStep;
        // Turns back at the ends of a round broken by a lake or cliff
        if(routine.site.isPatrolArc() && (next < 0 || next >= ring.size())) {
            ringStep = -ringStep;
            next = ringIndex + ringStep;
        }
        ringIndex = Math.floorMod(next, ring.size());
        routine.spot = npc.getEntityWorld() instanceof ServerWorld world ? routePoint(world, ring.get(ringIndex)) : ring.get(ringIndex);
        routine.lookTarget = null;
        routine.phase = Phase.GOING;
        routine.walker.resetWalk();
        routine.thinkCooldown = 0;
        routine.showProp();
    }
}
