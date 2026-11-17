package net.sevenstars.middleearth.entity.npcs.routine;

import net.minecraft.block.StairsBlock;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.ai.pathing.PathNode;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.sevenstars.middleearth.block.special.structureManager.features.NestSurvey;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Heightmap;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.entity.npcs.pathing.NpcNavigation;
import net.sevenstars.middleearth.resources.datas.npc_classes.NpcClass;
import net.sevenstars.middleearth.resources.datas.npc_classes.data.RoutineData;
import net.sevenstars.middleearth.utils.SpawnUtil;
import org.jetbrains.annotations.Nullable;
import net.sevenstars.middleearth.entity.npcs.routine.NpcRoutine.Phase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static net.sevenstars.middleearth.entity.npcs.routine.NpcRoutine.REST;
import static net.sevenstars.middleearth.entity.npcs.routine.NpcRoutine.ROUTINE_TIME_LIMIT;
import static net.sevenstars.middleearth.entity.npcs.routine.NpcRoutine.isUnderCover;
import static net.sevenstars.middleearth.entity.npcs.routine.RoutineReturn.STORE_REACH;

// Gets the npc where it is going, a stretch at a time
public class RoutineWalker {
    public static final double ARRIVAL_DISTANCE = 2.5;
    private static final int HOME_DISTANCE = 12;
    // Multiplier on how many nodes a path search may visit when heading to bed
    private static final float BED_SEARCH = 4;
    private static final double STORE_ARRIVAL_DISTANCE = 1.5;
    private static final double SEAT_ARRIVAL_DISTANCE = 2;

    private static final int DIRECT_DISTANCE = 18;
    // Top heights of the shapes below and in the feet's block. A fence or a wall is above the first, a slab or a carpet
    // below the second
    private static final double BLOCK_HEIGHT = 1;
    private static final double LOW_THING_HEIGHT = 0.5;
    // Close enough to the end of a stretch to count it as walked
    private static final double LEG_END_REACH = 2;
    // Stretches walked to their end without getting any nearer, before the walk counts as stuck
    private static final int IDLE_LEGS = 3;
    // Nodes of a planned route between the places walked to one stretch at a time, and how near counts as having
    // reached one
    private static final int WAYPOINT_SPACING = 16;
    private static final double WAYPOINT_REACH = 2.5;
    // Doors within this distance of the place are tried after the nearest one
    private static final int FAR_DOOR_REACH = 32;
    // A door this near a place is taken as its way in, and one this near the npc as the door it's already at
    private static final int DOOR_REACH = 16;
    private static final double AT_DOOR = 2.5;
    private static final int LEG_LENGTH = 14;
    // In degrees
    private static final float[] DETOURS = {0, 25, -25, 50, -50};
    private static final float[] AROUND_LEFT = {90, 60, 120};
    private static final float[] AROUND_RIGHT = {-90, -60, -120};
    private static final int AROUND_LENGTH = 8;
    public static final int MAXIMUM_HEIGHT_CHANGE = 8;
    private static final int MAXIMUM_LEGS = 30;
    private static final int MAXIMUM_FAILURES = 3;
    private static final int MINIMUM_PROGRESS = 2;
    // Consecutive looks at the world (four seconds' worth) without the npc getting any further along its path, before
    // the walk counts as stalled
    private static final int WATCH_THINKS = 8;
    // How far an npc stranded where no path starts looks for a floor to step to
    private static final int STEP_OFF_RADIUS = 3;
    private static final int STEP_OFF_DEPTH = 3;

    private final NpcRoutine engine;
    private final NpcEntity npc;

    public int legs = 0;
    public int failures = 0;
    // Where the path of the last stretch was to end, when it led all the way there
    private @Nullable BlockPos lastLegEnd = null;
    private double bestLegDistance = Double.MAX_VALUE;
    private int idleLegs = 0;
    private boolean lastLegMoved = true;
    // Doors already tried as the way in on this walk
    private final Set<BlockPos> triedDoors = new HashSet<>();
    private boolean planned = false;
    // The whole route to the destination of this walk, once planned
    private @Nullable List<BlockPos> waypoints = null;
    private int waypointIndex = 0;
    private @Nullable Path watchedPath = null;
    private int watchedNode = 0;
    private double watchedDistance = 0;
    private int watchedThinks = 0;
    public boolean steppedOff = false;

    public RoutineWalker(NpcRoutine engine) {
        this.engine = engine;
        this.npc = engine.npc;
    }

    public void travel(ServerWorld world, @Nullable BlockPos target) {
        if(target == null) {
            engine.abandoned("has no home to go back to");
            engine.finish(world, REST);
            return;
        }
        // The usual distance reaches through a wall, so a store on its far side would be filled from the street
        boolean storing = engine.phase == Phase.RETURNING && !engine.toBed && engine.carried != null && engine.dropOff() != null;
        boolean sitting = engine.phase == Phase.GOING && engine.meetSeat != null;
        boolean working = engine.phase == Phase.GOING && engine.activity == RoutineData.Activity.WORK;
        // Close enough to a bed means able to see it, not a step away through the wall
        BlockPos bed = engine.phase == Phase.RETURNING && engine.toBed ? engine.beds.freeBed(world) : null;
        boolean bedding = bed != null;
        BlockPos store = engine.dropOff();
        boolean atStore = !storing || store != null && store.isWithinDistance(npc.getEntityPos(), STORE_REACH) && engine.canSee(world, store);
        // A seat boxed in by other seats and a table can only be reached from a corner, a little further off
        double arrival = sitting ? SEAT_ARRIVAL_DISTANCE : storing || working || bedding ? STORE_ARRIVAL_DISTANCE : ARRIVAL_DISTANCE;
        if(target.isWithinDistance(mover().getEntityPos(), arrival) && (!bedding || engine.beds.canSeeBed(world, bed)) && (!working || engine.station.workTarget() == null || engine.canSee(world, engine.station.workTarget()))
                && atStore && (!sitting || engine.canSee(world, engine.meetSeat))) {
            engine.arrive(world);
            return;
        }
        // A path that can't be followed keeps the navigation busy without the npc getting anywhere
        if(isGettingAlong()) {
            watchedThinks = 0;
        } else if(++watchedThinks >= WATCH_THINKS) {
            watchedThinks = 0;
            engine.debug.debugWhy("STALL", world, target, null);
            String heldUp = heldUpAt(world);
            mover().getNavigation().stop();
            failures++;
            if(failures >= MAXIMUM_FAILURES) {
                engine.giveUp(world, "stuck on the way to " + target.toShortString() + heldUp);
                return;
            }
        }
        if(!mover().getNavigation().isIdle() || !RoutineSites.takePath(world))
            return;

        legs++;
        // A stretch that ended no closer was a path that led nowhere, unless it was walked to its end, because the way
        // to a loft can lead off from it first
        double distance = target.getSquaredDistance(mover().getEntityPos());
        boolean walkedLeg = lastLegEnd != null && lastLegEnd.isWithinDistance(mover().getEntityPos(), LEG_END_REACH);
        // Nearer than ever before on this walk. Stretches that go back and forth between two places get no nearer, and
        // only a few of those are let pass
        // On a planned route, reaching the next waypoint counts as getting along, though the route may first lead off
        boolean atWaypoint = waypoints != null && waypoints.get(waypointIndex).isWithinDistance(mover().getEntityPos(), WAYPOINT_REACH);
        boolean improved = legs == 1 || distance < bestLegDistance - MINIMUM_PROGRESS * MINIMUM_PROGRESS || atWaypoint;
        if(improved) {
            bestLegDistance = distance;
            idleLegs = 0;
        }
        boolean stalled = legs > 1 && !improved && lastLegMoved && (!walkedLeg || ++idleLegs > IDLE_LEGS);
        if(stalled && engine.phase == Phase.RETURNING && distance < HOME_DISTANCE * HOME_DISTANCE && (!bedding || engine.beds.isInReach(bed) && engine.beds.canSeeBed(world, bed)) && atStore) {
            engine.arrive(world);
            return;
        }
        // A walk under cover that has gone wrong once gets its whole route planned and is walked by stretches. With no
        // route at all, there's no use trying on. Out in the open the stretches and the detours mostly do, but not
        // around the far side of a hill, so the route is planned before the last try
        boolean covered = isUnderCover(world, mover().getBlockPos()) || isUnderCover(world, target);
        if(planWay(world, target, covered))
            return;
        Vec3d destination = legs > MAXIMUM_LEGS ? null : waypoints != null ? nextPlannedWaypoint() : nextLeg(world, target);
        // The path to a bed or a station ends on the floor beside it, not a step off where a table may hide it. A bed
        // up a stair round the back of the house is a long way round, longer than the usual search looks
        if(bedding)
            mover().getNavigation().setRangeMultiplier(BED_SEARCH);
        boolean moving = destination != null && mover().getNavigation().startMovingTo(destination.x, destination.y, destination.z, bedding || working || storing ? 0 : 1, engine.speed);
        mover().getNavigation().resetRangeMultiplier();
        Path leg = mover().getNavigation().getCurrentPath();
        lastLegEnd = moving && leg != null && leg.reachesTarget() && leg.getEnd() != null ? leg.getEnd().getBlockPos() : null;
        lastLegMoved = moving;
        if(!moving && stepOff(world))
            return;
        if(!moving || stalled) engine.debug.debugWhy(moving ? "NOPROGRESS" : "NOPATH", world, target, destination);
        if(moving && !stalled) {
            failures = 0;
            return;
        }
        failures++;
        if(legs > MAXIMUM_LEGS || failures >= MAXIMUM_FAILURES)
            engine.giveUp(world, (legs > MAXIMUM_LEGS ? "too far, " + legs + " stretches without arriving at " : moving ? "got no nearer to " : "no path to ") + target.toShortString());
    }

    private boolean planWay(ServerWorld world, BlockPos target, boolean covered) {
        if(failures > 0 && !planned && (covered || failures >= MAXIMUM_FAILURES - 1)
                && mover().getNavigation() instanceof NpcNavigation navigation) {
            planned = true;
            Path whole = navigation.plan(target);
            if(whole == null) {
                // Up on a railing no path starts at all, and from the floor beside it there may well be one
                if(stepOff(world)) {
                    planned = false;
                    return true;
                }
                engine.giveUp(world, "no way at all to " + target.toShortString());
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

    // Further along its path than at the last look, by however little: past another node of it, or nearer the next one.
    // Wading through water or climbing a ladder is slow and still counts. Without a path, the stretches handle it
    private boolean isGettingAlong() {
        MobEntity mover = mover();
        Path path = mover.getNavigation().getCurrentPath();
        if(path == null || path.isFinished()) {
            watchedPath = null;
            return true;
        }
        int node = path.getCurrentNodeIndex();
        Vec3d next = path.getNodePosition(mover);
        // Across the ground only, because bobbing in water goes up and down without getting anywhere
        double distance = Math.hypot(next.x - mover.getX(), next.z - mover.getZ());
        boolean further = path != watchedPath || node > watchedNode || distance < watchedDistance;
        watchedPath = path;
        watchedNode = node;
        watchedDistance = distance;
        return further;
    }

    // On the floor means standing on the ground, or on a low thing lying in its block such as a slab or a carpet. On
    // roof stairs at the top of a ladder, or on a piece of furniture, no path starts at all. The npc steps down to the
    // nearest floor once a walk, rather than give up every walk from there
    private boolean stepOff(ServerWorld world) {
        if(steppedOff || riding() || npc.isClimbing() || isOnFloor(world))
            return false;
        steppedOff = true;
        BlockPos floor = NestSurvey.freeFloorNear(world, npc.getBlockPos(), STEP_OFF_RADIUS, STEP_OFF_DEPTH);
        if(floor == null)
            return false;
        engine.did(engine.activity.asString(), "stepped off a spot no path starts from, to " + floor.toShortString());
        npc.refreshPositionAndAngles(floor.getX() + 0.5, floor.getY(), floor.getZ() + 0.5, npc.getYaw(), npc.getPitch());
        return true;
    }

    private boolean isOnFloor(ServerWorld world) {
        BlockPos feet = npc.getBlockPos();
        // The top of a fence or a wall is higher than its block, and no path starts up there
        VoxelShape under = world.getBlockState(feet.down()).getCollisionShape(world, feet.down());
        if(!under.isEmpty() && under.getMax(Direction.Axis.Y) > BLOCK_HEIGHT)
            return false;
        VoxelShape inside = world.getBlockState(feet).getCollisionShape(world, feet);
        return npc.isOnGround() && (inside.isEmpty() || inside.getMax(Direction.Axis.Y) <= LOW_THING_HEIGHT) && !(world.getBlockState(feet).getBlock() instanceof StairsBlock);
    }

    // The block the walk was held up at, found from the path it was following
    private String heldUpAt(ServerWorld world) {
        Path path = mover().getNavigation().getCurrentPath();
        if(path == null || path.isFinished())
            return "";
        PathNode next = path.getCurrentNode();
        BlockPos at = new BlockPos(next.x, next.y, next.z);
        return ", held up at " + at.toShortString() + " (" + world.getBlockState(at).getBlock().getName().getString() + ")";
    }

    // The nearest door to the place that hasn't been tried yet on this walk. Once one has been, doors further off are
    // looked at as well, because the nearest door to a room upstairs may be inside the house, with the way into the
    // house round the far side
    private @Nullable BlockPos doorTowards(BlockPos target, MobEntity mover) {
        if(engine.site == null)
            return null;
        int reach = triedDoors.isEmpty() ? DOOR_REACH : FAR_DOOR_REACH;
        BlockPos best = null;
        for(BlockPos door : engine.site.getDoors()) {
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

    // Long walks are cut into short stretches because a path over the whole way would cost too much
    private @Nullable Vec3d nextLeg(ServerWorld world, BlockPos target) {
        MobEntity mover = mover();
        double distanceX = target.getX() + 0.5 - mover.getX();
        double distanceZ = target.getZ() + 0.5 - mover.getZ();
        double distance = Math.sqrt(distanceX * distanceX + distanceZ * distanceZ);
        boolean close = distance <= DIRECT_DISTANCE;
        if(close && failures == 0)
            return Vec3d.ofBottomCenter(target);
        // No path found straight to a place indoors, so the way in is by the door nearest it, and from there it's a
        // short walk. A search from far off gives out on the way round to the far side of a house
        BlockPos door = failures > 0 ? doorTowards(target, mover) : null;
        if(door != null)
            return Vec3d.ofBottomCenter(door);
        // Under a roof or underground, the stretches over open ground below are no way at all. Each search goes
        // straight for the place, and the walk gets as near as it found
        if(isUnderCover(world, mover.getBlockPos()) || isUnderCover(world, target))
            return Vec3d.ofBottomCenter(target);

        // No path to a place close by means a wall in between, and a search aimed straight at the place gives up at the
        // wall. The way in is around the building, first to one side and to the other if that leads nowhere
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
            if(Math.abs(ground.getY() - mover.getY()) > MAXIMUM_HEIGHT_CHANGE || (engine.site != null && engine.site.isBuilding(x, ground.getY(), z)))
                continue;
            if(world.getFluidState(ground.down()).isEmpty() && SpawnUtil.canSpawn(ground, world, SpawnReason.EVENT))
                return Vec3d.ofBottomCenter(ground);
        }
        // Boxed in, a path straight at the target at least gets closer
        return Vec3d.ofBottomCenter(target);
    }

    // The npc is on its own mount, and the mount's legs do the walking
    public boolean riding() {
        NpcClass npcClass = npc.getNpcClass();
        return npcClass != null && npcClass.hasMount() && npc.getVehicle() instanceof MobEntity mount && mount.getFirstPassenger() == npc;
    }

    // Whatever does the walking. For a rider that's the mount
    public MobEntity mover() {
        return riding() && npc.getVehicle() instanceof MobEntity mount ? mount : npc;
    }

    public void stopMoving() {
        npc.getNavigation().stop();
        if(npc.getVehicle() instanceof MobEntity mount)
            mount.getNavigation().stop();
    }

    public void resetLegs() {
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

    public void resetWalk() {
        resetLegs();
        steppedOff = false;
        failures = 0;
    }

    // Each walk is judged separately: its stretches, the doors tried and the planned route
    public void newWalk(ServerWorld world) {
        engine.lookTarget = null;
        resetWalk();
        engine.thinkCooldown = 0;
        engine.deadline = world.getTime() + ROUTINE_TIME_LIMIT;
    }
}
