package net.sevenstars.middleearth.features.npcs.ai;

import net.minecraft.block.BlockState;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.NoPenaltyTargeting;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.sevenstars.middleearth.features.npcs.NpcEntity;
import net.sevenstars.middleearth.features.npcs.routine.RoutineSites;
import net.sevenstars.middleearth.utils.BlockTagsME;
import org.jetbrains.annotations.Nullable;

// One way of picking where to walk next, and what to do when the walk can't start
interface TravelMode {
    enum Gate {
        // Not this tick
        SKIP,
        // Subject to the usual wander chance
        CHANCE,
        // Goes ahead whatever the chance says
        FORCED
    }

    record Leg(Vec3d target, double speedFactor, @Nullable Vec3d leaderPos, boolean onStretch) {
        static Leg to(Vec3d target) {
            return new Leg(target, 1, null, false);
        }
    }

    Gate gate(NpcEntity npc);

    @Nullable Leg plan(NpcEntity npc);

    void begin(NpcEntity npc, Leg leg);

    static boolean walk(NpcEntity npc, Leg leg) {
        return npc.getNavigation().startMovingTo(leg.target().x, leg.target().y, leg.target().z, NpcBrain.WALK_SPEED * leg.speedFactor());
    }

    // Wandering about with a vehicle under the npc
    final class Mounted implements TravelMode {
        private final Stroll stroll;

        Mounted(Stroll stroll) {
            this.stroll = stroll;
        }

        @Override
        public Gate gate(NpcEntity npc) {
            return Gate.CHANCE;
        }

        @Override
        public @Nullable Leg plan(NpcEntity npc) {
            Vec3d target = TravelTargets.wander(npc);
            return target == null ? null : Leg.to(target);
        }

        @Override
        public void begin(NpcEntity npc, Leg leg) {
            if(npc.getPartyLeader() == null && npc.getTravelHeading() == null)
                stroll.begin(npc, leg);
            else
                walk(npc, leg);
        }
    }

    // Keeping a slot behind the party leader
    final class Follow implements TravelMode {
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

        private final Journey journey;
        private int leaderMissingChecks = 0;

        Follow(Journey journey) {
            this.journey = journey;
        }

        @Override
        public Gate gate(NpcEntity npc) {
            LivingEntity leader = findLeader(npc);
            if(leader == null)
                return Gate.CHANCE;
            if(npc.getEntityPos().squaredDistanceTo(slotBehind(npc, leader)) < SLOT_REACHED * SLOT_REACHED)
                return Gate.SKIP;
            return Gate.FORCED;
        }

        @Override
        public @Nullable Leg plan(NpcEntity npc) {
            LivingEntity leader = findLeader(npc);
            if(leader == null)
                return journey.plan(npc);
            double speedFactor = npc.squaredDistanceTo(leader) > CATCH_UP_DISTANCE * CATCH_UP_DISTANCE ? CATCH_UP_SPEED : FOLLOW_SPEED;
            return new Leg(slotBehind(npc, leader), speedFactor, leader.getEntityPos(), false);
        }

        @Override
        public void begin(NpcEntity npc, Leg leg) {
            if(leg.leaderPos() == null) {
                journey.begin(npc, leg);
                return;
            }
            if(walk(npc, leg))
                return;
            // The spot behind the leader may be unreachable
            npc.getNavigation().startMovingTo(leg.leaderPos().x, leg.leaderPos().y, leg.leaderPos().z, NpcBrain.WALK_SPEED * leg.speedFactor());
        }

        private Vec3d slotBehind(NpcEntity npc, LivingEntity leader) {
            Float leaderHeading = leader instanceof NpcEntity leaderNpc ? leaderNpc.getTravelHeading() : null;
            if(leaderHeading == null)
                return leader.getEntityPos();
            int slot = Math.floorMod(npc.getUuid().hashCode(), SLOTS);
            Vec3d slotPos = leader.getEntityPos().subtract(TravelTargets.direction(leaderHeading).multiply(FOLLOW_GAP + slot * SLOT_SPACING));
            Vec3d ground = TravelTargets.groundAt(npc, slotPos.x, slotPos.z);
            return ground != null ? ground : leader.getEntityPos();
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

    // Walking along a heading in stretches, towards a destination if there is one
    final class Journey implements TravelMode {
        // 1 in this many think ticks
        private static final int LEADER_REST = 10;
        private static final int STRETCH_LENGTH = 12;
        private static final int STRETCH_VARIATION = 2;
        // In degrees
        private static final float[] DETOURS = {0, 20, -20, 40, -40, 60, -60};
        private static final int MAXIMUM_HEIGHT_CHANGE = 8;
        private static final int MAXIMUM_FAILURES = 3;
        private static final int ARRIVAL_DISTANCE = 24;
        private static final int DESTINATION_CHECK_CHANCE = 10;
        private static final int LOOK_AHEAD = 32;
        private static final int TURN_VARIATION = 45;
        private static final float TURN_BACK = 180f;
        private static final float TURN_ASIDE = 90f;

        private int failures = 0;

        @Override
        public Gate gate(NpcEntity npc) {
            return npc.getTravelHeading() != null && npc.getRandom().nextInt(LEADER_REST) == 0 ? Gate.FORCED : Gate.CHANCE;
        }

        @Override
        public @Nullable Leg plan(NpcEntity npc) {
            Float heading = npc.getTravelHeading();
            if(heading == null)
                return wanderLeg(npc);
            heading = towardsDestination(npc, heading);
            heading = awayFromStillWorld(npc, heading);

            int length = STRETCH_LENGTH + npc.getRandom().nextBetween(-STRETCH_VARIATION, STRETCH_VARIATION);
            for(float detour : DETOURS) {
                Vec3d ahead = npc.getEntityPos().add(TravelTargets.direction(heading + detour).multiply(length));
                Vec3d ground = TravelTargets.groundAt(npc, ahead.x, ahead.z);
                if(ground != null && npc.keepsBounds() && !npc.getEntityWorld().getBiome(BlockPos.ofFloored(ground)).equals(npc.getEntityWorld().getBiome(npc.getBlockPos())))
                    continue;
                if(ground != null && Math.abs(ground.y - npc.getY()) <= MAXIMUM_HEIGHT_CHANGE)
                    return new Leg(ground, 1, null, true);
            }
            stretchFailed(npc, heading);
            return wanderLeg(npc);
        }

        @Override
        public void begin(NpcEntity npc, Leg leg) {
            if(walk(npc, leg)) {
                if(leg.onStretch())
                    failures = 0;
                return;
            }
            if(leg.onStretch() && npc.getTravelHeading() != null)
                stretchFailed(npc, npc.getTravelHeading());
        }

        private @Nullable Leg wanderLeg(NpcEntity npc) {
            Vec3d target = TravelTargets.wander(npc);
            return target == null ? null : Leg.to(target);
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
                npc.setTravelDestination(null);
                npc.setTravelHeading(null);
                return heading;
            }
            // Same as entity yaw: 0 is south, 90 is west
            float towards = (float) (MathHelper.atan2(-distanceX, distanceZ) * MathHelper.DEGREES_PER_RADIAN);
            npc.setTravelHeading(towards);
            return towards;
        }

        // Past the edge of the ticking world an npc freezes, so turn around
        private float awayFromStillWorld(NpcEntity npc, float heading) {
            if(!(npc.getEntityWorld() instanceof ServerWorld world))
                return heading;
            Vec3d ahead = npc.getEntityPos().add(TravelTargets.direction(heading).multiply(LOOK_AHEAD));
            if(world.shouldTickEntityAt(BlockPos.ofFloored(ahead)))
                return heading;
            float turned = heading + TURN_BACK + npc.getRandom().nextBetween(-TURN_VARIATION, TURN_VARIATION);
            npc.setTravelDestination(null);
            npc.setTravelHeading(turned);
            failures = 0;
            return turned;
        }

        private void stretchFailed(NpcEntity npc, float heading) {
            failures++;
            if(failures >= MAXIMUM_FAILURES) {
                failures = 0;
                npc.setTravelHeading(heading + (npc.getRandom().nextBoolean() ? TURN_ASIDE : -TURN_ASIDE));
            }
        }
    }

    // Short walks about the village, kept near home and out of the fields
    final class Stroll implements TravelMode {
        private static final int STROLL_RETRIES = 3;
        // In path nodes
        private static final int MINIMUM_STROLL = 4;
        private static final int STROLL_RETRY_RANGE = 6;
        private static final int STROLL_RETRY_HEIGHT = 4;
        private static final int HOME_LEASH = 20;
        private static final int HOME_PULL_RANGE = 10;
        private static final int HOME_PULL_HEIGHT = 7;

        @Override
        public Gate gate(NpcEntity npc) {
            return Gate.CHANCE;
        }

        @Override
        public @Nullable Leg plan(NpcEntity npc) {
            Vec3d target = nearHome(npc, TravelTargets.wander(npc));
            return target == null ? null : Leg.to(target);
        }

        @Override
        public void begin(NpcEntity npc, Leg leg) {
            boolean moving = walk(npc, leg);
            if(moving && isWorthWalking(npc))
                return;
            for(int attempt = 0; attempt < STROLL_RETRIES && npc.getEntityWorld() instanceof ServerWorld world && RoutineSites.takePath(world); attempt++) {
                Vec3d other = nearHome(npc, NoPenaltyTargeting.find(npc, STROLL_RETRY_RANGE, STROLL_RETRY_HEIGHT));
                if(other != null && npc.getNavigation().startMovingTo(other.x, other.y, other.z, NpcBrain.WALK_SPEED) && isWorthWalking(npc))
                    return;
            }
            npc.getNavigation().stop();
        }

        private @Nullable Vec3d nearHome(NpcEntity npc, @Nullable Vec3d target) {
            BlockPos home = npc.getRoutineHome();
            if(target == null || home == null || npc.getStructureManagerHostPos() == null || home.isWithinDistance(target, HOME_LEASH))
                return inField(npc, target) ? null : target;
            Vec3d back = NoPenaltyTargeting.findTo(npc, HOME_PULL_RANGE, HOME_PULL_HEIGHT, Vec3d.ofBottomCenter(home), MathHelper.HALF_PI);
            return inField(npc, back) ? null : back;
        }

        private boolean inField(NpcEntity npc, @Nullable Vec3d target) {
            if(target == null || npc.isFarming())
                return false;
            BlockPos floor = BlockPos.ofFloored(target).down();
            BlockState ground = npc.getEntityWorld().getBlockState(floor);
            return ground.getBlock() instanceof FarmlandBlock || ground.isIn(BlockTagsME.FARMLANDS);
        }

        private boolean isWorthWalking(NpcEntity npc) {
            Path path = npc.getNavigation().getCurrentPath();
            return path != null && path.reachesTarget() && path.getLength() >= MINIMUM_STROLL;
        }
    }
}
