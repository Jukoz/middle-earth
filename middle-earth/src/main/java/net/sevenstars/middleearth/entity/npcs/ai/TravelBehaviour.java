package net.sevenstars.middleearth.entity.npcs.ai;

import net.minecraft.block.BlockState;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.FuzzyTargeting;
import net.minecraft.entity.ai.NoPenaltyTargeting;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Heightmap;
import net.sevenstars.middleearth.block.special.structureManager.features.NestSurvey;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.entity.npcs.routine.RoutineSites;
import net.sevenstars.middleearth.utils.BlockTagsME;
import net.sevenstars.middleearth.utils.SpawnUtil;
import org.jetbrains.annotations.Nullable;

final class TravelBehaviour extends Behaviour {
    // 1 in this many think ticks
    private static final int LEADER_REST = 10;
    // Also 1 in this many think ticks, which is a couple of seconds between strolls
    private static final int STROLL_PAUSE = 20;
    private static final int STROLL_RETRIES = 3;
    // In path nodes
    private static final int MINIMUM_STROLL = 4;
    private static final int STROLL_RETRY_RANGE = 6;
    private static final int STROLL_RETRY_HEIGHT = 4;
    // Strolls tried from the same block before the npc counts as stuck, about 10 seconds
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
    // In blocks from home, beyond which a stroll by a settled npc is turned back
    private static final int HOME_LEASH = 20;
    private static final int HOME_PULL_RANGE = 10;
    private static final int HOME_PULL_HEIGHT = 7;
    private static final int ARRIVAL_DISTANCE = 24;
    private static final int DESTINATION_CHECK_CHANCE = 10;
    // A bit more than two stretches, far enough ahead to turn before reaching the edge
    private static final int LOOK_AHEAD = 32;
    private static final int TURN_VARIATION = 45;
    private static final float TURN_BACK = 180f;
    private static final float TURN_ASIDE = 90f;

    // The chance of walking is 1 in 120 server ticks, so 1 in this many think ticks
    private static final int WANDER_CHANCE = 60;
    private static final int DESPAWN_COUNTER = 100;
    // In blocks, how far around itself a stroll is looked for
    private static final int STROLL_RADIUS = 10;
    private static final int WATER_STROLL_RADIUS = 15;
    private static final int WANDER_HEIGHT = 7;
    // The chance, per think tick, of a plain nearby target instead of a fuzzy one
    private static final float FAR_PROBABILITY = 0.001F;

    private static final double SLOT_REACHED = 2.5;
    private static final double FOLLOW_GAP = 2.5;
    private static final double SLOT_SPACING = 1.8;
    private static final int SLOTS = 3;
    private static final double CATCH_UP_DISTANCE = 10;
    // Multiples of the normal wander speed
    private static final double FOLLOW_SPEED = 1.15;
    private static final double CATCH_UP_SPEED = 1.4;
    // In think ticks, 10 a second
    private static final int LEADER_MISSING_DELAY = 10 * 30;

    private double targetX;
    private double targetY;
    private double targetZ;
    // Set when the next think tick has to walk, whatever the chance of staying put
    private boolean ignoreChance = false;
    private int failures = 0;
    private int leaderMissingChecks = 0;
    private @Nullable Vec3d leaderPos = null;
    private @Nullable BlockPos lastStrollPos = null;
    private int stuckStrolls = 0;
    private int rescues = 0;
    private @Nullable BlockPos rescuedAt = null;
    private double speedFactor = 1;
    private boolean onStretch = false;

    TravelBehaviour() {
        super(TRAVEL_PRIORITY, MOVE);
    }

    @Override
    boolean canStart(NpcEntity npc) {
        if(npc.isConfined())
            return false;
        if(npc.hasVehicle())
            return wanderStart(npc);

        if(npc.getPartyLeader() != null) {
            LivingEntity leader = findLeader(npc);
            if(leader == null)
                return wanderStart(npc);
            if(npc.getEntityPos().squaredDistanceTo(slotBehind(npc, leader)) < SLOT_REACHED * SLOT_REACHED)
                return false;
            ignoreChance = true;
        } else if(npc.getTravelHeading() != null && npc.getRandom().nextInt(LEADER_REST) == 0) {
            ignoreChance = true;
        } else if(npc.getTravelHeading() == null && npc.getRandom().nextInt(STROLL_PAUSE) == 0) {
            // Npcs with nothing to do wander around instead of standing in one spot for minutes
            ignoreChance = true;
            checkStuck(npc);
        }
        return wanderStart(npc);
    }

    @Override
    boolean shouldContinue(NpcEntity npc) {
        return !npc.getNavigation().isIdle() && !npc.hasControllingPassenger();
    }

    @Override
    void stop(NpcEntity npc) {
        npc.getNavigation().stop();
    }

    private boolean wanderStart(NpcEntity npc) {
        if(npc.hasControllingPassenger())
            return false;
        if(!ignoreChance) {
            if(npc.getDespawnCounter() >= DESPAWN_COUNTER)
                return false;
            if(npc.getRandom().nextInt(WANDER_CHANCE) != 0)
                return false;
        }
        Vec3d target = findTarget(npc);
        if(target == null)
            return false;
        targetX = target.x;
        targetY = target.y;
        targetZ = target.z;
        ignoreChance = false;
        return true;
    }

    private @Nullable Vec3d wanderTarget(NpcEntity npc) {
        if(npc.isTouchingWater()) {
            Vec3d target = FuzzyTargeting.find(npc, WATER_STROLL_RADIUS, WANDER_HEIGHT);
            return target == null ? NoPenaltyTargeting.find(npc, STROLL_RADIUS, WANDER_HEIGHT) : target;
        }
        if(npc.getRandom().nextFloat() >= FAR_PROBABILITY)
            return FuzzyTargeting.find(npc, STROLL_RADIUS, WANDER_HEIGHT);
        return NoPenaltyTargeting.find(npc, STROLL_RADIUS, WANDER_HEIGHT);
    }

    private @Nullable Vec3d findTarget(NpcEntity npc) {
        leaderPos = null;
        speedFactor = 1;
        onStretch = false;
        if(npc.hasVehicle())
            return wanderTarget(npc);

        if(npc.getPartyLeader() == null && npc.getTravelHeading() == null)
            return nearHome(npc, wanderTarget(npc));

        LivingEntity leader = npc.getPartyLeader() == null ? null : findLeader(npc);
        if(leader != null) {
            leaderPos = leader.getEntityPos();
            speedFactor = npc.squaredDistanceTo(leader) > CATCH_UP_DISTANCE * CATCH_UP_DISTANCE ? CATCH_UP_SPEED : FOLLOW_SPEED;
            return slotBehind(npc, leader);
        }

        Float heading = npc.getTravelHeading();
        if(heading == null)
            return wanderTarget(npc);
        heading = towardsDestination(npc, heading);
        heading = awayFromStillWorld(npc, heading);

        int length = STRETCH_LENGTH + npc.getRandom().nextBetween(-STRETCH_VARIATION, STRETCH_VARIATION);
        for(float detour : DETOURS) {
            Vec3d ahead = npc.getEntityPos().add(direction(heading + detour).multiply(length));
            Vec3d ground = groundAt(npc, ahead.x, ahead.z);
            // An npc that keeps bounds stays in its biome, so it skips a stretch that ends in a different one
            if(ground != null && npc.keepsBounds() && !npc.getEntityWorld().getBiome(BlockPos.ofFloored(ground)).equals(npc.getEntityWorld().getBiome(npc.getBlockPos())))
                continue;
            if(ground != null && Math.abs(ground.y - npc.getY()) <= MAXIMUM_HEIGHT_CHANGE) {
                onStretch = true;
                return ground;
            }
        }
        stretchFailed(npc, heading);
        return wanderTarget(npc);
    }

    @Override
    void start(NpcEntity npc) {
        double moveSpeed = NpcBrain.WALK_SPEED * speedFactor;
        boolean moving = npc.getNavigation().startMovingTo(targetX, targetY, targetZ, moveSpeed);
        boolean strolling = npc.getPartyLeader() == null && npc.getTravelHeading() == null;
        // A path that ends at the nearest wall doesn't count as a stroll
        if(moving && strolling && !isWorthWalking(npc))
            moving = false;
        if(moving) {
            if(onStretch)
                failures = 0;
            return;
        }
        if(onStretch && npc.getTravelHeading() != null)
            stretchFailed(npc, npc.getTravelHeading());
        // The spot behind the leader may be unreachable
        if(leaderPos != null) {
            npc.getNavigation().startMovingTo(leaderPos.x, leaderPos.y, leaderPos.z, moveSpeed);
            return;
        }
        // Indoors most random spots are behind a wall, so an idle npc tries a few before giving up on the stroll
        if(strolling) {
            for(int attempt = 0; attempt < STROLL_RETRIES && npc.getEntityWorld() instanceof ServerWorld world && RoutineSites.takePath(world); attempt++) {
                Vec3d other = nearHome(npc, NoPenaltyTargeting.find(npc, STROLL_RETRY_RANGE, STROLL_RETRY_HEIGHT));
                if(other != null && npc.getNavigation().startMovingTo(other.x, other.y, other.z, moveSpeed) && isWorthWalking(npc))
                    return;
            }
            npc.getNavigation().stop();
        }
    }

    // Keeps settled npcs from wandering out of their village for good
    private @Nullable Vec3d nearHome(NpcEntity npc, @Nullable Vec3d target) {
        BlockPos home = npc.getRoutineHome();
        if(target == null || home == null || npc.getStructureManagerHostPos() == null || home.isWithinDistance(target, HOME_LEASH))
            return inField(npc, target) ? null : target;
        Vec3d back = NoPenaltyTargeting.findTo(npc, HOME_PULL_RANGE, HOME_PULL_HEIGHT, Vec3d.ofBottomCenter(home), MathHelper.HALF_PI);
        return inField(npc, back) ? null : back;
    }

    // Npcs don't stroll through crops
    private boolean inField(NpcEntity npc, @Nullable Vec3d target) {
        if(target == null || npc.isFarming())
            return false;
        BlockPos floor = BlockPos.ofFloored(target).down();
        BlockState ground = npc.getEntityWorld().getBlockState(floor);
        return ground.getBlock() instanceof FarmlandBlock || ground.isIn(BlockTagsME.FARMLANDS);
    }

    private float towardsDestination(NpcEntity npc, float heading) {
        BlockPos destination = npc.getTravelDestination();
        if(destination == null) {
            // Settlements only become known as players pass by
            if(!npc.keepsBounds() && npc.getRandom().nextInt(DESTINATION_CHECK_CHANCE) == 0 && npc.getEntityWorld() instanceof ServerWorld world)
                npc.setTravelDestination(RoutineSites.findDestination(world, npc));
            return heading;
        }
        double distanceX = destination.getX() + 0.5 - npc.getX();
        double distanceZ = destination.getZ() + 0.5 - npc.getZ();
        if(distanceX * distanceX + distanceZ * distanceZ < ARRIVAL_DISTANCE * ARRIVAL_DISTANCE) {
            // The journey is over, so the party stays here
            npc.setTravelDestination(null);
            npc.setTravelHeading(null);
            return heading;
        }
        // Same as entity yaw. 0 is south, 90 is west
        float towards = (float) (MathHelper.atan2(-distanceX, distanceZ) * MathHelper.DEGREES_PER_RADIAN);
        npc.setTravelHeading(towards);
        return towards;
    }

    // Past the edge of the ticking world nobody sees the journey, and the npc freezes where it crossed. An npc heading that
    // way turns around and gives up its destination for one it can reach
    private float awayFromStillWorld(NpcEntity npc, float heading) {
        if(!(npc.getEntityWorld() instanceof ServerWorld world))
            return heading;
        Vec3d ahead = npc.getEntityPos().add(direction(heading).multiply(LOOK_AHEAD));
        if(world.shouldTickEntityAt(BlockPos.ofFloored(ahead)))
            return heading;
        float turned = heading + TURN_BACK + npc.getRandom().nextBetween(-TURN_VARIATION, TURN_VARIATION);
        npc.setTravelDestination(null);
        npc.setTravelHeading(turned);
        failures = 0;
        return turned;
    }

    // Furniture is a trap. An npc that ends up on a stool or a table finds no path off it and would stay there forever
    private void checkStuck(NpcEntity npc) {
        BlockPos pos = npc.getBlockPos();
        if(!pos.equals(lastStrollPos) || npc.isSleeping()) {
            // Once the npc has walked well away from where it was freed, it's no longer stuck
            if(rescuedAt != null && !rescuedAt.isWithinDistance(pos, RESCUE_CLEAR_DISTANCE)) {
                rescuedAt = null;
                rescues = 0;
            }
            lastStrollPos = pos;
            stuckStrolls = 0;
            return;
        }
        // Every think tick comes with a stroll attempt, so the same block over and over means none of them went anywhere.
        // An npc shut in a small room waits longer each time, or it would keep hopping around its cell
        stuckStrolls++;
        if(stuckStrolls < STUCK_STROLLS << Math.min(rescues, MAXIMUM_RESCUE_BACK_OFF) || !(npc.getEntityWorld() instanceof ServerWorld world))
            return;
        stuckStrolls = 0;
        rescues++;
        rescuedAt = pos;
        npc.getNavigation().stop();
        BlockPos best = NestSurvey.freeFloorNear(world, pos, RESCUE_RADIUS, RESCUE_DEPTH);
        if(best != null)
            npc.refreshPositionAndAngles(best.getX() + 0.5, best.getY(), best.getZ() + 0.5, npc.getYaw(), npc.getPitch());
    }

    private boolean isWorthWalking(NpcEntity npc) {
        Path path = npc.getNavigation().getCurrentPath();
        return path != null && path.reachesTarget() && path.getLength() >= MINIMUM_STROLL;
    }

    private void stretchFailed(NpcEntity npc, float heading) {
        failures++;
        if(failures >= MAXIMUM_FAILURES) {
            failures = 0;
            npc.setTravelHeading(heading + (npc.getRandom().nextBoolean() ? TURN_ASIDE : -TURN_ASIDE));
        }
    }

    private Vec3d slotBehind(NpcEntity npc, LivingEntity leader) {
        Float leaderHeading = leader instanceof NpcEntity leaderNpc ? leaderNpc.getTravelHeading() : null;
        if(leaderHeading == null)
            return leader.getEntityPos();
        int slot = Math.floorMod(npc.getUuid().hashCode(), SLOTS);
        Vec3d slotPos = leader.getEntityPos().subtract(direction(leaderHeading).multiply(FOLLOW_GAP + slot * SLOT_SPACING));
        Vec3d ground = groundAt(npc, slotPos.x, slotPos.z);
        return ground != null ? ground : leader.getEntityPos();
    }

    private @Nullable Vec3d groundAt(NpcEntity npc, double x, double z) {
        if(!(npc.getEntityWorld() instanceof ServerWorld world))
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

    // Same as entity yaw. 0 is south, 90 is west
    private static Vec3d direction(float heading) {
        float radians = heading * MathHelper.RADIANS_PER_DEGREE;
        return new Vec3d(-MathHelper.sin(radians), 0, MathHelper.cos(radians));
    }

    private @Nullable LivingEntity findLeader(NpcEntity npc) {
        if(npc.getEntityWorld().getEntity(npc.getPartyLeader()) instanceof LivingEntity leader && leader.isAlive()) {
            leaderMissingChecks = 0;
            return leader;
        }
        leaderMissingChecks++;
        if(leaderMissingChecks >= LEADER_MISSING_DELAY)
            npc.setPartyLeader(null);
        return null;
    }
}
