package net.sevenstars.middleearth.entity.npcs.routine;

import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.sevenstars.middleearth.block.special.structureManager.features.NestSurvey;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.entity.npcs.routine.NpcRoutine.Phase;
import org.jetbrains.annotations.Nullable;

import static net.sevenstars.middleearth.entity.npcs.routine.NpcRoutine.HOLD;
import static net.sevenstars.middleearth.entity.npcs.routine.NpcRoutine.MAXIMUM_STRIKES;
import static net.sevenstars.middleearth.entity.npcs.routine.NpcRoutine.NOTHING_TO_DO_REST;
import static net.sevenstars.middleearth.entity.npcs.routine.NpcRoutine.isLoaded;

class RoutineBed {
    private static final double BED_REACH = 6;
    private static final double BED_HEIGHT_REACH = 5;
    // Just above the top of a bed, so it can be seen across the footboard
    private static final double BED_TOP = 0.6;

    private final NpcRoutine routine;
    private final NpcEntity npc;

    long bedHeldUntil = 0;
    private int bedStrikes = 0;

    RoutineBed(NpcRoutine routine) {
        this.routine = routine;
        this.npc = routine.npc;
    }

    // A bed is two blocks, and the near half is as good as the other. Aims at its middle, or else its top, because a
    // trapdoor left open at the foot of a bed hides the middle from the floor beside it
    boolean canSeeBed(ServerWorld world, BlockPos bed) {
        for(Vec3d aim : new Vec3d[]{Vec3d.ofCenter(bed), Vec3d.ofBottomCenter(bed).add(0, BED_TOP, 0)}) {
            BlockHitResult hit = world.raycast(new RaycastContext(npc.getEyePos(), aim, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, npc));
            if(hit.getType() == HitResult.Type.MISS || hit.getBlockPos().equals(bed)
                    || world.getBlockState(hit.getBlockPos()).getBlock() instanceof BedBlock && hit.getBlockPos().getManhattanDistance(bed) == 1)
                return true;
        }
        return false;
    }

    // Its own bed, unless that's gone or somebody is lying in it
    @Nullable BlockPos freeBed(ServerWorld world) {
        BlockPos bed = npc.getAssignedBed();
        if(bed == null || !isLoaded(world, bed))
            return null;
        BlockState state = world.getBlockState(bed);
        if(!(state.getBlock() instanceof BedBlock) || state.get(BedBlock.OCCUPIED))
            return null;
        // A bed with no floor anywhere beside it, such as one in a tent shut at both ends by its poles, can't be got
        // into, so its owner rests at home. Looked for again in case the bed's chunk wasn't loaded when it was given
        // out
        if(npc.getBedStand() == null) {
            BlockPos stand = NestSurvey.standFor(world, bed);
            if(stand == null) {
                routine.fallsBack("bed", "no floor beside its bed at " + bed.toShortString() + ", rests at home instead");
                return null;
            }
            npc.setHousehold(bed, stand, npc.getStore(), npc.getLinks());
        }
        return bed;
    }

    void goToBed(ServerWorld world) {
        routine.lookTarget = null;
        npc.getNavigation().stop();
        npc.clearRoutineProp();
        BlockPos bed = freeBed(world);
        if(bed != null && isInReach(bed) && canSeeBed(world, bed)) {
            routine.toBed = false;
            npc.lieDown(bed);
            routine.phase = Phase.SLEEPING;
            bedStrikes = 0;
            routine.failed = false;
        } else {
            // A walk to it that was given up is already in the log
            if(!routine.failed)
                routine.abandoned("bed", bed == null ? "its bed is gone or somebody lies in it" : !isInReach(bed) ? "could not get near its bed " + bed.toShortString()
                        : "its bed " + bed.toShortString() + " is behind a wall");
            // Left set until the failure is logged, which records the route to the bed rather than home
            routine.toBed = false;
            bedStrikes = Math.min(bedStrikes + 1, MAXIMUM_STRIKES);
            bedHeldUntil = world.getTime() + (long) HOLD * bedStrikes;
            routine.finish(world, NOTHING_TO_DO_REST);
        }
    }

    // Lofts, ladders and odd corners keep many npcs from walking the last steps to their bed. Inside the house is near
    // enough
    boolean isInReach(BlockPos bed) {
        double distanceX = bed.getX() + 0.5 - npc.getX();
        double distanceZ = bed.getZ() + 0.5 - npc.getZ();
        return distanceX * distanceX + distanceZ * distanceZ <= BED_REACH * BED_REACH && Math.abs(bed.getY() - npc.getY()) <= BED_HEIGHT_REACH;
    }
}
