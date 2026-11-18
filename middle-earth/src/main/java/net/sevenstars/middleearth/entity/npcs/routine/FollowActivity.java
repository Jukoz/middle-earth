package net.sevenstars.middleearth.entity.npcs.routine;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.sevenstars.middleearth.block.special.structureManager.features.NestSurvey;
import net.sevenstars.middleearth.block.special.structureManager.marker.MarkerKind;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.entity.npcs.routine.NpcRoutine.Phase;
import net.sevenstars.middleearth.entity.seat.SeatEntity;
import net.sevenstars.middleearth.registries.content.npcjobs.NpcJobs;
import net.sevenstars.middleearth.resources.datas.npc_classes.data.RoutineData;
import net.sevenstars.middleearth.resources.datas.structure_features.StructureFeatureData;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.UUID;

import static net.sevenstars.middleearth.entity.npcs.routine.NpcRoutine.REST;
import static net.sevenstars.middleearth.entity.npcs.routine.NpcRoutine.ROUTINE_TIME_LIMIT;
import static net.sevenstars.middleearth.entity.npcs.routine.NpcRoutine.isLoaded;
import static net.sevenstars.middleearth.entity.npcs.routine.RoutineWalker.ARRIVAL_DISTANCE;

class FollowActivity extends RoutineActivity {
    private static final int LORD_SEARCH_DELAY = 20 * 5;

    private static final double FOLLOW_NEAR = 3.5;
    private static final double FOLLOW_FAR = 5.5;
    private static final int[] FOLLOW_HEIGHTS = {0, -1, 1};
    // Squared. Closer places count as the same one, with no direction between them
    private static final double NO_HEADING = 0.01;
    private static final int FOLLOW_POST_RANGE = 8;
    // Looks (half a second each) between path corrections
    private static final int FOLLOW_REPATH = 6;
    private static final int LORD_SEARCH_RANGE = 32;
    private static final int LORD_HEIGHT_RANGE = 48;
    private static final int FOLLOW_SPOT_SHIFT = 3;

    private @Nullable UUID lordId = null;
    private long lordSearchAt = 0;
    private int followCheck = 0;

    FollowActivity(NpcRoutine routine) {
        super(routine);
    }

    @Override
    boolean begin(ServerWorld world, RoutineData.Activity picked, BlockPos home) {
        beginFollow(world);
        return true;
    }

    private void beginFollow(ServerWorld world) {
        NpcEntity lord = findLord(world);
        BlockPos stand = lord == null ? null : followSpot(world, lord);
        routine.spot = stand != null ? stand : npc.getBlockPos();
        followCheck = FOLLOW_REPATH;
    }

    @Override
    void arrive(ServerWorld world, Random random, long time) {
        routine.phaseEnd = time;
    }

    @Nullable NpcEntity findLord(ServerWorld world) {
        if(lordId != null && world.getEntity(lordId) instanceof NpcEntity known && known.isAlive())
            return known;
        lordId = null;
        if(world.getTime() < lordSearchAt)
            return null;
        lordSearchAt = world.getTime() + LORD_SEARCH_DELAY;
        int reach = (routine.site == null ? 0 : routine.site.getExtent()) + LORD_SEARCH_RANGE;
        NpcEntity nearest = null;
        for(NpcEntity other : world.getEntitiesByClass(NpcEntity.class, npc.getBoundingBox().expand(reach, LORD_HEIGHT_RANGE, reach),
                other -> other != npc && other.isAlive() && NpcJobs.isLord(other.getJobId()) && Objects.equals(npc.getFactionIdentifier(), other.getFactionIdentifier()))) {
            if(nearest == null || npc.squaredDistanceTo(other) < npc.squaredDistanceTo(nearest))
                nearest = other;
        }
        if(nearest != null)
            lordId = nearest.getUuid();
        return nearest;
    }

    // A post while the lord sits at court, else a few blocks off him. Null if already there
    private @Nullable BlockPos followSpot(ServerWorld world, NpcEntity lord) {
        routine.facing = null;
        if(lord.getVehicle() instanceof SeatEntity && routine.site != null) {
            StructureFeatureData.Marker post = null;
            for(StructureFeatureData.Marker marker : routine.site.getMarkers(MarkerKind.GUARD_POST)) {
                if(marker.pos().isWithinDistance(lord.getBlockPos(), FOLLOW_POST_RANGE)
                        && (post == null || marker.pos().getSquaredDistance(lord.getBlockPos()) < post.pos().getSquaredDistance(lord.getBlockPos())))
                    post = marker;
            }
            if(post != null) {
                routine.facing = post.facing();
                return npc.getBlockPos().isWithinDistance(post.pos(), ARRIVAL_DISTANCE) ? null : post.pos();
            }
        }
        if(npc.squaredDistanceTo(lord) <= FOLLOW_FAR * FOLLOW_FAR)
            return null;
        Vec3d away = npc.getEntityPos().subtract(lord.getEntityPos()).multiply(1, 0, 1);
        away = away.lengthSquared() < NO_HEADING ? new Vec3d(1, 0, 0) : away.normalize();
        BlockPos lordAt = lord.getBlockPos();
        for(int dy : FOLLOW_HEIGHTS) {
            BlockPos candidate = BlockPos.ofFloored(lord.getX() + away.x * FOLLOW_NEAR, lordAt.getY() + dy, lord.getZ() + away.z * FOLLOW_NEAR);
            if(isLoaded(world, candidate) && world.getBlockState(candidate).getCollisionShape(world, candidate).isEmpty()
                    && world.getBlockState(candidate.up()).getCollisionShape(world, candidate.up()).isEmpty() && world.getBlockState(candidate.down()).isSolidBlock(world, candidate.down()))
                return candidate;
        }
        BlockPos beside = NestSurvey.standNextTo(world, lordAt, npc.getBlockPos());
        return beside != null ? beside : lordAt;
    }

    boolean following(ServerWorld world) {
        if(--followCheck > 0)
            return true;
        followCheck = FOLLOW_REPATH;
        NpcEntity lord = findLord(world);
        if(lord == null) {
            routine.abandoned("lost its lord");
            routine.finish(world, REST);
            return false;
        }
        BlockPos stand = followSpot(world, lord);
        if(stand == null) {
            routine.arrive(world);
            return false;
        }
        if(!stand.isWithinDistance(routine.spot, FOLLOW_SPOT_SHIFT))
            routine.walker.resetLegs();
        routine.walker.steppedOff = false;
        routine.spot = stand;
        return true;
    }

    @Override
    void work(ServerWorld world, Random random, boolean done, long time) {
        routine.deadline = time + ROUTINE_TIME_LIMIT;
        if(!routine.onDuty(world) || !routine.isWatched(world)) {
            routine.startReturn(world);
            return;
        }
        NpcEntity lord = findLord(world);
        if(lord == null) {
            routine.abandoned("lost its lord");
            routine.finish(world, REST);
            return;
        }
        BlockPos stand = followSpot(world, lord);
        if(stand != null) {
            routine.spot = stand;
            routine.phase = Phase.GOING;
            routine.walker.resetWalk();
            followCheck = FOLLOW_REPATH;
            return;
        }
        routine.lookTarget = routine.facingTarget();
        if(routine.lookTarget == null) {
            Vec3d away = npc.getEntityPos().subtract(lord.getEntityPos()).multiply(1, 0, 1);
            routine.lookTarget = away.lengthSquared() < NO_HEADING ? null : new Vec3d(npc.getX() + away.x, npc.getEyeY(), npc.getZ() + away.z);
        }
        routine.showProp();
    }
}
