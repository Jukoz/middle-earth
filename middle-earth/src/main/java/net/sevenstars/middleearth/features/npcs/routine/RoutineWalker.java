package net.sevenstars.middleearth.features.npcs.routine;

import net.minecraft.block.StairsBlock;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.ai.pathing.PathNode;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.Heightmap;
import net.sevenstars.middleearth.block.special.structureManager.features.NestSurvey;
import net.sevenstars.middleearth.features.npcs.NpcEntity;
import net.sevenstars.middleearth.features.npcs.pathing.NpcNavigation;
import net.sevenstars.middleearth.features.npcs.routine.NpcRoutine.Phase;
import net.sevenstars.middleearth.features.npcs.classes.NpcClass;
import net.sevenstars.middleearth.features.npcs.classes.data.RoutineData;
import net.sevenstars.middleearth.utils.SpawnUtil;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static net.sevenstars.middleearth.features.npcs.routine.NpcRoutine.REST;
import static net.sevenstars.middleearth.features.npcs.routine.NpcRoutine.ROUTINE_TIME_LIMIT;
import static net.sevenstars.middleearth.features.npcs.routine.NpcRoutine.isUnderCover;
import static net.sevenstars.middleearth.features.npcs.routine.RoutineReturn.STORE_REACH;

class RoutineWalker {
    static final double ARRIVAL_DISTANCE = 2.5;
    private static final int HOME_DISTANCE = 12;
    private static final float BED_SEARCH = 4;
    private static final double STORE_ARRIVAL_DISTANCE = 1.5;
    private static final double SEAT_ARRIVAL_DISTANCE = 2;

    private static final int DIRECT_DISTANCE = 18;
    // Shape top heights. A fence or wall is above the first, a slab or carpet below the second
    private static final double BLOCK_HEIGHT = 1;
    private static final double LOW_THING_HEIGHT = 0.5;
    private static final double LEG_END_REACH = 2;
    private static final int IDLE_LEGS = 3;
    private static final int WAYPOINT_SPACING = 16;
    private static final double WAYPOINT_REACH = 2.5;
    private static final int FAR_DOOR_REACH = 32;
    // A door this near a place is its way in, this near the npc is the door it's already at
    private static final int DOOR_REACH = 16;
    private static final double AT_DOOR = 2.5;
    private static final int LEG_LENGTH = 14;
    // In degrees
    private static final float[] DETOURS = {0, 25, -25, 50, -50};
    private static final float[] AROUND_LEFT = {90, 60, 120};
    private static final float[] AROUND_RIGHT = {-90, -60, -120};
    private static final int AROUND_LENGTH = 8;
    static final int MAXIMUM_HEIGHT_CHANGE = 8;
    private static final int MAXIMUM_LEGS = 30;
    private static final int MAXIMUM_FAILURES = 3;
    private static final int MINIMUM_PROGRESS = 2;
    // Looks (four seconds' worth) with no progress along the path before the walk is stalled
    private static final int WATCH_THINKS = 8;
    private static final int STEP_OFF_RADIUS = 3;
    private static final int STEP_OFF_DEPTH = 3;

    private final NpcRoutine routine;
    private final NpcEntity npc;

    int legs = 0;
    int failures = 0;
    private @Nullable BlockPos lastLegEnd = null;
    private double bestLegDistance = Double.MAX_VALUE;
    private int idleLegs = 0;
    private boolean lastLegMoved = true;
    private final Set<BlockPos> triedDoors = new HashSet<>();
    private boolean planned = false;
    private @Nullable List<BlockPos> waypoints = null;
    private int waypointIndex = 0;
    private @Nullable Path watchedPath = null;
    private int watchedNode = 0;
    private double watchedDistance = 0;
    private int watchedThinks = 0;
    boolean steppedOff = false;

    RoutineWalker(NpcRoutine routine) {
        this.routine = routine;
        this.npc = routine.npc;
    }

    void travel(ServerWorld world, @Nullable BlockPos target) {
        if(target == null) {
            routine.abandoned("has no home to go back to");
            routine.finish(world, REST);
            return;
        }
        // The usual distance reaches through a wall, so a store behind it would be filled from the street
        boolean storing = routine.phase == Phase.RETURNING && !routine.toBed && routine.carried != null && routine.dropOff() != null;
        boolean sitting = routine.phase == Phase.GOING && routine.meetSeat != null;
        boolean working = routine.phase == Phase.GOING && routine.activity == RoutineData.Activity.WORK;
        // Close to a bed means seeing it, not a step away through the wall
        BlockPos bed = routine.phase == Phase.RETURNING && routine.toBed ? routine.beds.freeBed(world) : null;
        boolean bedding = bed != null;
        BlockPos store = routine.dropOff();
        boolean atStore = !storing || store != null && store.isWithinDistance(npc.getEntityPos(), STORE_REACH) && routine.canSee(world, store);
        // A seat boxed in by other seats and a table is only reachable from a corner, a bit further off
        double arrival = sitting ? SEAT_ARRIVAL_DISTANCE : storing || working || bedding ? STORE_ARRIVAL_DISTANCE : ARRIVAL_DISTANCE;
        if(target.isWithinDistance(mover().getEntityPos(), arrival) && (!bedding || routine.beds.canSeeBed(world, bed)) && (!working || routine.station.workTarget() == null || routine.canSee(world, routine.station.workTarget()))
                && atStore && (!sitting || routine.canSee(world, routine.meetSeat))) {
            routine.arrive(world);
            return;
        }
        // An unfollowable path keeps navigation busy while the npc goes nowhere
        if(isGettingAlong()) {
            watchedThinks = 0;
        } else if(++watchedThinks >= WATCH_THINKS) {
            watchedThinks = 0;
            routine.debug.debugWhy("STALL", world, target, null);
            String heldUp = heldUpAt(world);
            mover().getNavigation().stop();
            failures++;
            if(failures >= MAXIMUM_FAILURES) {
                routine.giveUp(world, "stuck on the way to " + target.toShortString() + heldUp);
                return;
            }
        }
        if(!mover().getNavigation().isIdle() || !RoutineSites.takePath(world))
            return;

        legs++;
        // A stretch that got no closer led nowhere, unless walked to its end: the way to a loft can lead off first
        double distance = target.getSquaredDistance(mover().getEntityPos());
        boolean walkedLeg = lastLegEnd != null && lastLegEnd.isWithinDistance(mover().getEntityPos(), LEG_END_REACH);
        // Nearer than ever before. Stretches back and forth get no nearer, so only a few of those pass
        boolean atWaypoint = waypoints != null && waypoints.get(waypointIndex).isWithinDistance(mover().getEntityPos(), WAYPOINT_REACH);
        boolean improved = legs == 1 || distance < bestLegDistance - MINIMUM_PROGRESS * MINIMUM_PROGRESS || atWaypoint;
        if(improved) {
            bestLegDistance = distance;
            idleLegs = 0;
        }
        boolean stalled = legs > 1 && !improved && lastLegMoved && (!walkedLeg || ++idleLegs > IDLE_LEGS);
        if(stalled && routine.phase == Phase.RETURNING && distance < HOME_DISTANCE * HOME_DISTANCE && (!bedding || routine.beds.isInReach(bed) && routine.beds.canSeeBed(world, bed)) && atStore) {
            routine.arrive(world);
            return;
        }
        // Under cover, a walk that went wrong once plans its whole route. No route means give up.
        // In the open stretches and detours mostly do, but not round a hill, so plan before the last try
        boolean covered = isUnderCover(world, mover().getBlockPos()) || isUnderCover(world, target);
        if(planWay(world, target, covered))
            return;
        Vec3d destination = legs > MAXIMUM_LEGS ? null : waypoints != null ? nextPlannedWaypoint() : nextLeg(world, target);
        // A bed up a stair round the back is further than the usual search looks
        if(bedding)
            mover().getNavigation().setRangeMultiplier(BED_SEARCH);
        boolean moving = destination != null && mover().getNavigation().startMovingTo(destination.x, destination.y, destination.z, bedding || working || storing ? 0 : 1, routine.speed);
        mover().getNavigation().resetRangeMultiplier();
        Path leg = mover().getNavigation().getCurrentPath();
        lastLegEnd = moving && leg != null && leg.reachesTarget() && leg.getEnd() != null ? leg.getEnd().getBlockPos() : null;
        lastLegMoved = moving;
        if(!moving && stepOff(world))
            return;
        if(!moving || stalled) routine.debug.debugWhy(moving ? "NOPROGRESS" : "NOPATH", world, target, destination);
        if(moving && !stalled) {
            failures = 0;
            return;
        }
        failures++;
        if(legs > MAXIMUM_LEGS || failures >= MAXIMUM_FAILURES)
            routine.giveUp(world, (legs > MAXIMUM_LEGS ? "too far, " + legs + " stretches without arriving at " : moving ? "got no nearer to " : "no path to ") + target.toShortString());
    }

    private boolean planWay(ServerWorld world, BlockPos target, boolean covered) {
        if(failures > 0 && !planned && (covered || failures >= MAXIMUM_FAILURES - 1)
                && mover().getNavigation() instanceof NpcNavigation navigation) {
            planned = true;
            Path whole = navigation.plan(target);
            if(whole == null) {
                // No path starts up on a railing, but there may be one from the floor beside it
                if(stepOff(world)) {
                    planned = false;
                    return true;
                }
                routine.giveUp(world, "no way at all to " + target.toShortString());
                return true;
            }
            waypoints = new ArrayList<>();
            for(int i = WAYPOINT_SPACING; i < whole.getLength(); i += WAYPOINT_SPACING)
                waypoints.add(whole.getNodePos(i));
            waypoints.add(target);
            failures = 0;
        }
        return false;
    }

    // Further along than at the last look, however little. Water and ladders are slow but still count.
    private boolean isGettingAlong() {
        MobEntity mover = mover();
        Path path = mover.getNavigation().getCurrentPath();
        if(path == null || path.isFinished()) {
            watchedPath = null;
            return true;
        }
        int node = path.getCurrentNodeIndex();
        Vec3d next = path.getNodePosition(mover);
        // Ground only, bobbing in water goes nowhere
        double distance = Math.hypot(next.x - mover.getX(), next.z - mover.getZ());
        boolean further = path != watchedPath || node > watchedNode || distance < watchedDistance;
        watchedPath = path;
        watchedNode = node;
        watchedDistance = distance;
        return further;
    }

    // On the floor: the ground, or a low thing like a slab or carpet. On roof stairs, a ladder top or furniture
    // no path starts, so the npc steps down to the nearest floor once a walk rather than give up every walk
    private boolean stepOff(ServerWorld world) {
        if(steppedOff || riding() || npc.isClimbing() || isOnFloor(world))
            return false;
        steppedOff = true;
        BlockPos floor = NestSurvey.freeFloorNear(world, npc.getBlockPos(), STEP_OFF_RADIUS, STEP_OFF_DEPTH);
        if(floor == null)
            return false;
        routine.did(routine.activity.asString(), "stepped off a spot no path starts from, to " + floor.toShortString());
        npc.refreshPositionAndAngles(floor.getX() + 0.5, floor.getY(), floor.getZ() + 0.5, npc.getYaw(), npc.getPitch());
        return true;
    }

    private boolean isOnFloor(ServerWorld world) {
        BlockPos feet = npc.getBlockPos();
        VoxelShape under = world.getBlockState(feet.down()).getCollisionShape(world, feet.down());
        if(!under.isEmpty() && under.getMax(Direction.Axis.Y) > BLOCK_HEIGHT)
            return false;
        VoxelShape inside = world.getBlockState(feet).getCollisionShape(world, feet);
        return npc.isOnGround() && (inside.isEmpty() || inside.getMax(Direction.Axis.Y) <= LOW_THING_HEIGHT) && !(world.getBlockState(feet).getBlock() instanceof StairsBlock);
    }

    private String heldUpAt(ServerWorld world) {
        Path path = mover().getNavigation().getCurrentPath();
        if(path == null || path.isFinished())
            return "";
        PathNode next = path.getCurrentNode();
        BlockPos at = new BlockPos(next.x, next.y, next.z);
        return ", held up at " + at.toShortString() + " (" + world.getBlockState(at).getBlock().getName().getString() + ")";
    }

    // Nearest door not yet tried. After one has been, farther doors count too: the nearest door to an upstairs
    // room may be inside the house, with the way in round the far side
    private @Nullable BlockPos doorTowards(BlockPos target, MobEntity mover) {
        if(routine.site == null)
            return null;
        int reach = triedDoors.isEmpty() ? DOOR_REACH : FAR_DOOR_REACH;
        BlockPos best = null;
        for(BlockPos door : routine.site.getDoors()) {
            if(triedDoors.contains(door) || !door.isWithinDistance(target, reach) || door.isWithinDistance(mover.getEntityPos(), AT_DOOR))
                continue;
            if(best == null || door.getSquaredDistance(target) < best.getSquaredDistance(target))
                best = door;
        }
        if(best != null)
            triedDoors.add(best);
        return best;
    }

    private Vec3d nextPlannedWaypoint() {
        MobEntity mover = mover();
        while(waypointIndex < waypoints.size() - 1 && waypoints.get(waypointIndex).isWithinDistance(mover.getEntityPos(), WAYPOINT_REACH))
            waypointIndex++;
        return Vec3d.ofBottomCenter(waypoints.get(waypointIndex));
    }

    // Long walks are cut into stretches, a path over the whole way costs too much
    private @Nullable Vec3d nextLeg(ServerWorld world, BlockPos target) {
        MobEntity mover = mover();
        double distanceX = target.getX() + 0.5 - mover.getX();
        double distanceZ = target.getZ() + 0.5 - mover.getZ();
        double distance = Math.sqrt(distanceX * distanceX + distanceZ * distanceZ);
        boolean close = distance <= DIRECT_DISTANCE;
        if(close && failures == 0)
            return Vec3d.ofBottomCenter(target);
        // No path straight to an indoor place, so go by the nearest door. A search from far off gives out on the way round
        BlockPos door = failures > 0 ? doorTowards(target, mover) : null;
        if(door != null)
            return Vec3d.ofBottomCenter(door);
        // Under a roof or underground, search straight for the place and get as near as found
        if(isUnderCover(world, mover.getBlockPos()) || isUnderCover(world, target))
            return Vec3d.ofBottomCenter(target);

        // No path to a close place means a wall, and a search aimed straight at it gives up there.
        // Go around the building, to one side first and the other if that leads nowhere
        float[] detours = !close ? DETOURS : failures % 2 == 1 ? AROUND_LEFT : AROUND_RIGHT;
        int length = close ? AROUND_LENGTH : LEG_LENGTH;
        float heading = (float) (MathHelper.atan2(distanceZ, distanceX) * MathHelper.DEGREES_PER_RADIAN);
        for(float detour : detours) {
            float radians = (heading + detour) * MathHelper.RADIANS_PER_DEGREE;
            int x = MathHelper.floor(mover.getX() + MathHelper.cos(radians) * length);
            int z = MathHelper.floor(mover.getZ() + MathHelper.sin(radians) * length);
            if(!world.isChunkLoaded(x >> 4, z >> 4))
                continue;
            BlockPos ground = new BlockPos(x, world.getTopY(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, x, z), z);
            if(Math.abs(ground.getY() - mover.getY()) > MAXIMUM_HEIGHT_CHANGE || (routine.site != null && routine.site.isBuilding(x, ground.getY(), z)))
                continue;
            if(world.getFluidState(ground.down()).isEmpty() && SpawnUtil.canSpawn(ground, world, SpawnReason.EVENT))
                return Vec3d.ofBottomCenter(ground);
        }
        return Vec3d.ofBottomCenter(target);
    }

    boolean riding() {
        NpcClass npcClass = npc.getNpcClass();
        return npcClass != null && npcClass.hasMount() && npc.getVehicle() instanceof MobEntity mount && mount.getFirstPassenger() == npc;
    }

    MobEntity mover() {
        return riding() && npc.getVehicle() instanceof MobEntity mount ? mount : npc;
    }

    void stopMoving() {
        npc.getNavigation().stop();
        if(npc.getVehicle() instanceof MobEntity mount)
            mount.getNavigation().stop();
    }

    void resetLegs() {
        legs = 0;
        lastLegEnd = null;
        bestLegDistance = Double.MAX_VALUE;
        idleLegs = 0;
        lastLegMoved = true;
        triedDoors.clear();
        planned = false;
        waypoints = null;
        waypointIndex = 0;
        watchedPath = null;
        watchedThinks = 0;
    }

    void resetWalk() {
        resetLegs();
        steppedOff = false;
        failures = 0;
    }

    void newWalk(ServerWorld world) {
        routine.lookTarget = null;
        resetWalk();
        routine.thinkCooldown = 0;
        routine.deadline = world.getTime() + ROUTINE_TIME_LIMIT;
    }
}
