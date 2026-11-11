package net.sevenstars.middleearth.entity.goals;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Heightmap;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.utils.SpawnUtil;
import org.jetbrains.annotations.Nullable;

public class NpcTravelGoal extends WanderAroundFarGoal {
    // 1 in this many checks, made every other tick
    private static final int LEADER_REST = 10;
    private static final int STRETCH_LENGTH = 12;
    private static final int STRETCH_VARIATION = 2;
    // In degrees
    private static final float[] DETOURS = {0, 20, -20, 40, -40, 60, -60};
    private static final int MAXIMUM_HEIGHT_CHANGE = 8;
    private static final int MAXIMUM_FAILURES = 3;

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

        LivingEntity leader = npc.getPartyLeader() == null ? null : findLeader();
        if(leader != null) {
            leaderPos = leader.getPos();
            speedFactor = npc.squaredDistanceTo(leader) > CATCH_UP_DISTANCE * CATCH_UP_DISTANCE ? CATCH_UP_SPEED : FOLLOW_SPEED;
            return slotBehind(leader);
        }

        Float heading = npc.getTravelHeading();
        if(heading == null)
            return super.getWanderTarget();

        int length = STRETCH_LENGTH + npc.getRandom().nextBetween(-STRETCH_VARIATION, STRETCH_VARIATION);
        for(float detour : DETOURS) {
            Vec3d ahead = npc.getPos().add(direction(heading + detour).multiply(length));
            Vec3d ground = groundAt(ahead.x, ahead.z);
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
        if(moving) {
            if(onStretch)
                failures = 0;
            return;
        }
        if(onStretch && npc.getTravelHeading() != null)
            stretchFailed(npc.getTravelHeading());
        // The spot behind the leader can be unreachable
        if(leaderPos != null)
            npc.getNavigation().startMovingTo(leaderPos.x, leaderPos.y, leaderPos.z, moveSpeed);
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
