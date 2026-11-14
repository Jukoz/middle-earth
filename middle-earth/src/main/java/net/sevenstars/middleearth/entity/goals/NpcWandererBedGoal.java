package net.sevenstars.middleearth.entity.goals;

import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.sevenstars.middleearth.block.special.structureManager.features.NestSurvey;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.entity.npcs.routine.RoutineSite;
import net.sevenstars.middleearth.entity.npcs.routine.RoutineSites;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;

// A traveller near a settlement at night lies down in one of its free beds, the ones nobody is born in, and goes on in the morning
public class NpcWandererBedGoal extends Goal {
    // Blocks from the middle of the settlement's reach
    private static final int SEARCH_RANGE = 48;
    private static final int SEARCH_INTERVAL = 40;
    private static final int MAXIMUM_TRIES = 3;
    private static final int WALK_LIMIT = 20 * 30;
    private static final double BED_REACH = 2.5;
    private static final double BED_HEIGHT_REACH = 2;

    private final NpcEntity npc;
    private final double speed;
    private @Nullable BlockPos bed = null;
    private @Nullable RoutineSite site = null;
    private int tries = 0;
    private long nextSearch = 0;
    private long walkDeadline = 0;

    public NpcWandererBedGoal(NpcEntity npc, double speed) {
        this.npc = npc;
        this.speed = speed;
        this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
    }

    @Override
    public boolean canStart() {
        if(!(npc.getWorld() instanceof ServerWorld world))
            return false;
        if(!world.isNight()) {
            tries = 0;
            return false;
        }
        boolean travelling = npc.getPartyLeader() != null || npc.getTravelHeading() != null;
        // Those who live somewhere have beds of their own, and a raiding party has no mind to sleep in the place it is raiding
        if(!travelling || tries >= MAXIMUM_TRIES || npc.getStructureManagerHostPos() != null || npc.isEvil() || npc.isConfined()
                || npc.isSleeping() || npc.hasVehicle() || npc.getTarget() != null || world.getTime() < nextSearch)
            return false;
        nextSearch = world.getTime() + SEARCH_INTERVAL + npc.getRandom().nextInt(SEARCH_INTERVAL);

        List<RoutineSite> sites = RoutineSites.near(world, npc.getBlockPos(), SEARCH_RANGE);
        sites.sort(Comparator.comparingDouble(found -> npc.squaredDistanceTo(found.getCenterX(), npc.getY(), found.getCenterZ())));
        for(RoutineSite found : sites) {
            if(found.getFaction() != null && npc.isHostileToFaction(found.getFaction()))
                continue;
            BlockPos claimed = found.claimFreeBed(world, npc.getUuid(), npc.getBlockPos());
            if(claimed != null) {
                site = found;
                bed = claimed;
                return true;
            }
        }
        tries++;
        return false;
    }

    @Override
    public void start() {
        walkDeadline = npc.getWorld().getTime() + WALK_LIMIT;
        if(!walkToBed())
            giveUp();
    }

    @Override
    public boolean shouldContinue() {
        if(bed == null || !npc.getWorld().isNight() || npc.getTarget() != null)
            return false;
        if(npc.isSleeping())
            return npc.getWorld().getBlockState(bed).getBlock() instanceof BedBlock;
        return npc.getWorld().getTime() < walkDeadline;
    }

    @Override
    public void tick() {
        if(bed == null || npc.isSleeping() || !(npc.getWorld() instanceof ServerWorld world))
            return;
        double distanceX = bed.getX() + 0.5 - npc.getX();
        double distanceZ = bed.getZ() + 0.5 - npc.getZ();
        if(distanceX * distanceX + distanceZ * distanceZ <= BED_REACH * BED_REACH && Math.abs(bed.getY() - npc.getY()) <= BED_HEIGHT_REACH) {
            BlockState state = world.getBlockState(bed);
            if(!(state.getBlock() instanceof BedBlock) || state.get(BedBlock.OCCUPIED)) {
                giveUp();
                return;
            }
            npc.getNavigation().stop();
            npc.lieDown(bed);
        } else if(npc.getNavigation().isIdle() && !walkToBed()) {
            giveUp();
        }
    }

    @Override
    public void stop() {
        if(npc.isSleeping())
            npc.wakeUp();
        npc.getNavigation().stop();
        if(site != null)
            site.release(npc.getUuid());
        site = null;
        bed = null;
    }

    private boolean walkToBed() {
        if(bed == null || !(npc.getWorld() instanceof ServerWorld world))
            return false;
        BlockPos stand = NestSurvey.standFor(world, bed);
        return stand != null && npc.getNavigation().startMovingTo(stand.getX() + 0.5, stand.getY(), stand.getZ() + 0.5, speed);
    }

    // The goal ends, and the next try is after a pause
    private void giveUp() {
        tries++;
        bed = null;
    }
}
