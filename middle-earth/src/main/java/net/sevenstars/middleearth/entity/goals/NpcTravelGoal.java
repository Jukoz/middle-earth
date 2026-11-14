package net.sevenstars.middleearth.entity.goals;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.NoPenaltyTargeting;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Heightmap;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.entity.npcs.routine.RoutineSites;
import net.sevenstars.middleearth.utils.SpawnUtil;
import org.jetbrains.annotations.Nullable;

public class NpcTravelGoal extends WanderAroundFarGoal {
    // 1 in this many checks, made every other tick
    private static final int LEADER_REST = 10;
    // 1 in this many checks as well, a couple of seconds between two strolls
    private static final int STROLL_PAUSE = 20;
    private static final int STROLL_RETRIES = 3;
    // In path nodes
    private static final int MINIMUM_STROLL = 4;
    private static final int STROLL_RETRY_RANGE = 6;
    // Strolls tried from the same block before the npc is taken to be stuck, about 20 seconds
    private static final int STUCK_STROLLS = 5;
    private static final int MAXIMUM_RESCUE_BACK_OFF = 4;
    private static final int RESCUE_CLEAR_DISTANCE = 4;
    private static final int RESCUE_RADIUS = 3;
    private static final int RESCUE_DEPTH = 3;
    private static final int STRETCH_LENGTH = 12;
    private static final int STRETCH_VARIATION = 2;
    // In degrees
    private static final float[] DETOURS = {0, 20, -20, 40, -40, 60, -60};
    private static final int MAXIMUM_HEIGHT_CHANGE = 8;
    private static final int MAXIMUM_FAILURES = 3;
    private static final int WANDER_RANGE = 20;
    private static final int ARRIVAL_DISTANCE = 24;
    private static final int DESTINATION_CHECK_CHANCE = 10;
    // Two stretches and a bit: far enough to turn before the edge is reached
    private static final int LOOK_AHEAD = 32;
    private static final int TURN_VARIATION = 45;

    private static final double SLOT_REACHED = 2.5;
    private static final double FOLLOW_GAP = 2.5;
    private static final double SLOT_SPACING = 1.8;
    private static final int SLOTS = 3;
    private static final double CATCH_UP_DISTANCE = 10;
    // Times the normal wander speed
    private static final double FOLLOW_SPEED = 1.15;
    private static final double CATCH_UP_SPEED = 1.4;
    // In checks, 10 a second
    private static final int LEADER_MISSING_DELAY = 10 * 30;

    private final NpcEntity npc;
    private int failures = 0;
    private int leaderMissingTicks = 0;
    private @Nullable Vec3d leaderPos = null;
    private @Nullable BlockPos lastStrollPos = null;
    private int stuckStrolls = 0;
    private int rescues = 0;
    private @Nullable BlockPos rescuedAt = null;
    private double speedFactor = 1;
    private boolean onStretch = false;

    public NpcTravelGoal(NpcEntity npc, double speed) {
        super(npc, speed);
        this.npc = npc;
    }

    @Override
    public boolean canStart() {
        if(npc.hasVehicle())
            return super.canStart();

        if(npc.getPartyLeader() != null) {
            LivingEntity leader = findLeader();
            if(leader == null)
                return super.canStart();
            if(npc.getPos().squaredDistanceTo(slotBehind(leader)) < SLOT_REACHED * SLOT_REACHED)
                return false;
            ignoreChanceOnce();
        } else if(npc.getTravelHeading() != null && npc.getRandom().nextInt(LEADER_REST) == 0) {
            ignoreChanceOnce();
        } else if(npc.getTravelHeading() == null && npc.getRandom().nextInt(STROLL_PAUSE) == 0) {
            // People with nothing to do amble about, they do not stand rooted for minutes
            ignoreChanceOnce();
            checkStuck();
        }
        return super.canStart();
    }

    @Override
    protected @Nullable Vec3d getWanderTarget() {
        leaderPos = null;
        speedFactor = 1;
        onStretch = false;
        if(npc.hasVehicle())
            return super.getWanderTarget();

        if(npc.getPartyLeader() == null && npc.getTravelHeading() == null)
            return nearHome(super.getWanderTarget());

        LivingEntity leader = npc.getPartyLeader() == null ? null : findLeader();
        if(leader != null) {
            leaderPos = leader.getPos();
            speedFactor = npc.squaredDistanceTo(leader) > CATCH_UP_DISTANCE * CATCH_UP_DISTANCE ? CATCH_UP_SPEED : FOLLOW_SPEED;
            return slotBehind(leader);
        }

        Float heading = npc.getTravelHeading();
        if(heading == null)
            return super.getWanderTarget();
        heading = towardsDestination(heading);
        heading = awayFromStillWorld(heading);

        int length = STRETCH_LENGTH + npc.getRandom().nextBetween(-STRETCH_VARIATION, STRETCH_VARIATION);
        for(float detour : DETOURS) {
            Vec3d ahead = npc.getPos().add(direction(heading + detour).multiply(length));
            Vec3d ground = groundAt(ahead.x, ahead.z);
            // The border is where the land changes, and that way is not taken
            if(ground != null && npc.keepsBounds() && !npc.getWorld().getBiome(BlockPos.ofFloored(ground)).equals(npc.getWorld().getBiome(npc.getBlockPos())))
                continue;
            if(ground != null && Math.abs(ground.y - npc.getY()) <= MAXIMUM_HEIGHT_CHANGE) {
                onStretch = true;
                return ground;
            }
        }
        stretchFailed(heading);
        return super.getWanderTarget();
    }

    @Override
    public void start() {
        double moveSpeed = speed * speedFactor;
        boolean moving = npc.getNavigation().startMovingTo(targetX, targetY, targetZ, moveSpeed);
        boolean strolling = npc.getPartyLeader() == null && npc.getTravelHeading() == null;
        // A path that stops at the nearest wall is no stroll
        if(moving && strolling && !isWorthWalking())
            moving = false;
        if(moving) {
            if(onStretch)
                failures = 0;
            return;
        }
        if(onStretch && npc.getTravelHeading() != null)
            stretchFailed(npc.getTravelHeading());
        // The spot behind the leader can be unreachable
        if(leaderPos != null) {
            npc.getNavigation().startMovingTo(leaderPos.x, leaderPos.y, leaderPos.z, moveSpeed);
            return;
        }
        // Indoors most random spots lie behind a wall, so an idle npc tries a few before it gives up on its stroll
        if(strolling) {
            for(int attempt = 0; attempt < STROLL_RETRIES && npc.getWorld() instanceof ServerWorld world && RoutineSites.takePath(world); attempt++) {
                Vec3d other = nearHome(NoPenaltyTargeting.find(npc, STROLL_RETRY_RANGE, 4));
                if(other != null && npc.getNavigation().startMovingTo(other.x, other.y, other.z, moveSpeed) && isWorthWalking())
                    return;
            }
            npc.getNavigation().stop();
        }
    }

    // Settled npcs used to stroll out of their village for good
    private @Nullable Vec3d nearHome(@Nullable Vec3d target) {
        BlockPos home = npc.getRoutineHome();
        if(target == null || home == null || npc.getStructureManagerHostPos() == null || home.isWithinDistance(target, WANDER_RANGE))
            return target;
        return NoPenaltyTargeting.findTo(npc, 10, 7, Vec3d.ofBottomCenter(home), MathHelper.HALF_PI);
    }

    private float towardsDestination(float heading) {
        BlockPos destination = npc.getTravelDestination();
        if(destination == null) {
            // Settlements become known as players come by
            if(!npc.keepsBounds() && npc.getRandom().nextInt(DESTINATION_CHECK_CHANCE) == 0 && npc.getWorld() instanceof ServerWorld world)
                npc.setTravelDestination(RoutineSites.findDestination(world, npc));
            return heading;
        }
        double distanceX = destination.getX() + 0.5 - npc.getX();
        double distanceZ = destination.getZ() + 0.5 - npc.getZ();
        if(distanceX * distanceX + distanceZ * distanceZ < ARRIVAL_DISTANCE * ARRIVAL_DISTANCE) {
            // Journey's end, the party stays around
            npc.setTravelDestination(null);
            npc.setTravelHeading(null);
            return heading;
        }
        // Same as entity yaw: 0 is south, 90 is west
        float towards = (float) (MathHelper.atan2(-distanceX, distanceZ) * MathHelper.DEGREES_PER_RADIAN);
        npc.setTravelHeading(towards);
        return towards;
    }

    // Past the edge of the world that is running nobody sees the journey, and the npc stands frozen where it crossed. One
    // heading that way turns round and gives up where it was going, for a place it can reach
    private float awayFromStillWorld(float heading) {
        if(!(npc.getWorld() instanceof ServerWorld world))
            return heading;
        Vec3d ahead = npc.getPos().add(direction(heading).multiply(LOOK_AHEAD));
        if(world.shouldTickEntityAt(BlockPos.ofFloored(ahead)))
            return heading;
        float turned = heading + 180f + npc.getRandom().nextBetween(-TURN_VARIATION, TURN_VARIATION);
        npc.setTravelDestination(null);
        npc.setTravelHeading(turned);
        failures = 0;
        return turned;
    }

    // Furniture is a trap: an npc that ends up on a stool or a table finds no path off it, and would stand there for good
    private void checkStuck() {
        BlockPos pos = npc.getBlockPos();
        if(!pos.equals(lastStrollPos) || npc.isSleeping()) {
            // Walking well away from where it was freed shows the npc has its feet again
            if(rescuedAt != null && !rescuedAt.isWithinDistance(pos, RESCUE_CLEAR_DISTANCE)) {
                rescuedAt = null;
                rescues = 0;
            }
            lastStrollPos = pos;
            stuckStrolls = 0;
            return;
        }
        // Every one of these checks comes with a try at a stroll, so the same block again and again means none got anywhere.
        // Somebody shut in a small room waits longer each time, or it would hop about its cell
        stuckStrolls++;
        if(stuckStrolls < STUCK_STROLLS << Math.min(rescues, MAXIMUM_RESCUE_BACK_OFF) || !(npc.getWorld() instanceof ServerWorld world))
            return;
        stuckStrolls = 0;
        rescues++;
        rescuedAt = pos;
        npc.getNavigation().stop();
        BlockPos best = null;
        for(BlockPos spot : BlockPos.iterate(pos.add(-RESCUE_RADIUS, -RESCUE_DEPTH, -RESCUE_RADIUS), pos.add(RESCUE_RADIUS, 1, RESCUE_RADIUS))) {
            if(spot.equals(pos) || !world.isChunkLoaded(spot.getX() >> 4, spot.getZ() >> 4))
                continue;
            BlockPos below = spot.down();
            boolean standable = world.getBlockState(spot).getCollisionShape(world, spot).isEmpty()
                    && world.getBlockState(spot.up()).getCollisionShape(world, spot.up()).isEmpty()
                    && world.getFluidState(spot).isEmpty()
                    && world.getBlockState(below).isSideSolidFullSquare(world, below, Direction.UP);
            if(standable && (best == null || spot.getSquaredDistance(pos) < best.getSquaredDistance(pos)))
                best = spot.toImmutable();
        }
        if(best != null)
            npc.refreshPositionAndAngles(best.getX() + 0.5, best.getY(), best.getZ() + 0.5, npc.getYaw(), npc.getPitch());
    }

    private boolean isWorthWalking() {
        Path path = npc.getNavigation().getCurrentPath();
        return path != null && path.reachesTarget() && path.getLength() >= MINIMUM_STROLL;
    }

    private void stretchFailed(float heading) {
        failures++;
        if(failures >= MAXIMUM_FAILURES) {
            failures = 0;
            npc.setTravelHeading(heading + (npc.getRandom().nextBoolean() ? 90f : -90f));
        }
    }

    private Vec3d slotBehind(LivingEntity leader) {
        Float leaderHeading = leader instanceof NpcEntity leaderNpc ? leaderNpc.getTravelHeading() : null;
        if(leaderHeading == null)
            return leader.getPos();
        int slot = Math.floorMod(npc.getUuid().hashCode(), SLOTS);
        Vec3d slotPos = leader.getPos().subtract(direction(leaderHeading).multiply(FOLLOW_GAP + slot * SLOT_SPACING));
        Vec3d ground = groundAt(slotPos.x, slotPos.z);
        return ground != null ? ground : leader.getPos();
    }

    private @Nullable Vec3d groundAt(double x, double z) {
        if(!(npc.getWorld() instanceof ServerWorld world))
            return null;
        int blockX = MathHelper.floor(x);
        int blockZ = MathHelper.floor(z);
        if(!world.isChunkLoaded(blockX >> 4, blockZ >> 4))
            return null;
        BlockPos pos = new BlockPos(blockX, world.getTopY(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, blockX, blockZ), blockZ);
        if(!world.getFluidState(pos).isEmpty() || !world.getFluidState(pos.down()).isEmpty())
            return null;
        if(!SpawnUtil.canSpawn(pos, world, SpawnReason.EVENT))
            return null;
        return Vec3d.ofBottomCenter(pos);
    }

    // Same as entity yaw: 0 is south, 90 is west
    private static Vec3d direction(float heading) {
        float radians = heading * MathHelper.RADIANS_PER_DEGREE;
        return new Vec3d(-MathHelper.sin(radians), 0, MathHelper.cos(radians));
    }

    private @Nullable LivingEntity findLeader() {
        if(npc.getWorld().getEntity(npc.getPartyLeader()) instanceof LivingEntity leader && leader.isAlive()) {
            leaderMissingTicks = 0;
            return leader;
        }
        leaderMissingTicks++;
        if(leaderMissingTicks >= LEADER_MISSING_DELAY)
            npc.setPartyLeader(null);
        return null;
    }
}
