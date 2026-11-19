package net.sevenstars.middleearth.features.npcs.ai;

import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.sevenstars.middleearth.block.special.structureManager.features.NestSurvey;
import net.sevenstars.middleearth.features.npcs.NpcEntity;
import net.sevenstars.middleearth.features.npcs.routine.RoutineSite;
import net.sevenstars.middleearth.features.npcs.routine.RoutineSites;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.List;

final class WandererBedBehaviour extends Behaviour {
    private static final int MAXIMUM_TRIES = 3;
    // Blocks, beyond the settlement's extent
    private static final int SEARCH_RANGE = 48;
    private static final int SEARCH_INTERVAL = 40;
    private static final int WALK_LIMIT = 20 * 30;
    private static final long DAY_LENGTH = 24000;

    private static final double BED_REACH = 2.5;
    private static final double BED_HEIGHT_REACH = 2;

    private @Nullable BlockPos bed = null;
    private @Nullable RoutineSite site = null;
    private int tries = 0;
    private long triedNight = -1;
    private long nextSearch = 0;
    private long walkDeadline = 0;

    WandererBedBehaviour() {
        super(WANDERER_BED_PRIORITY, MOVE | LOOK);
    }

    @Override
    boolean canStart(NpcEntity npc) {
        return bed != null;
    }

    boolean mayLookForBed(NpcEntity npc) {
        if(!(npc.getEntityWorld() instanceof ServerWorld world) || !world.isNight())
            return false;
        boolean travelling = npc.getPartyLeader() != null || npc.getTravelHeading() != null;
        // Npcs that live somewhere have their own beds, and a raiding party won't sleep where it's raiding
        return travelling && hasTriesLeft(world) && npc.getStructureManagerHostPos() == null && !npc.isEvil() && !npc.isConfined()
                && !npc.isSleeping() && !npc.hasVehicle() && npc.getTarget() == null && world.getTime() >= nextSearch;
    }

    void lookForBed(NpcEntity npc) {
        ServerWorld world = (ServerWorld) npc.getEntityWorld();
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
                return;
            }
        }
        countTry(world);
    }

    @Override
    void start(NpcEntity npc) {
        walkDeadline = npc.getEntityWorld().getTime() + WALK_LIMIT;
        if(!walkToBed(npc))
            giveUp((ServerWorld) npc.getEntityWorld());
    }

    @Override
    boolean shouldContinue(NpcEntity npc) {
        if(bed == null || !npc.getEntityWorld().isNight() || npc.getTarget() != null)
            return false;
        if(npc.isSleeping())
            return npc.getEntityWorld().getBlockState(bed).getBlock() instanceof BedBlock;
        return npc.getEntityWorld().getTime() < walkDeadline;
    }

    @Override
    void tick(NpcEntity npc) {
        if(bed == null || npc.isSleeping() || !(npc.getEntityWorld() instanceof ServerWorld world))
            return;
        double distanceX = bed.getX() + 0.5 - npc.getX();
        double distanceZ = bed.getZ() + 0.5 - npc.getZ();
        if(distanceX * distanceX + distanceZ * distanceZ <= BED_REACH * BED_REACH && Math.abs(bed.getY() - npc.getY()) <= BED_HEIGHT_REACH) {
            BlockState state = world.getBlockState(bed);
            if(!(state.getBlock() instanceof BedBlock) || state.get(BedBlock.OCCUPIED)) {
                giveUp(world);
                return;
            }
            npc.getNavigation().stop();
            npc.lieDown(bed);
        } else if(npc.getNavigation().isIdle() && !walkToBed(npc)) {
            giveUp(world);
        }
    }

    @Override
    void stop(NpcEntity npc) {
        if(npc.isSleeping())
            npc.wakeUp();
        npc.getNavigation().stop();
        if(site != null)
            site.releaseBed(npc.getUuid());
        site = null;
        bed = null;
    }

    private boolean walkToBed(NpcEntity npc) {
        if(bed == null || !(npc.getEntityWorld() instanceof ServerWorld world))
            return false;
        BlockPos stand = NestSurvey.standFor(world, bed);
        return stand != null && npc.getNavigation().startMovingTo(stand.getX() + 0.5, stand.getY(), stand.getZ() + 0.5, NpcBrain.WALK_SPEED);
    }

    private void giveUp(ServerWorld world) {
        countTry(world);
        bed = null;
    }

    private boolean hasTriesLeft(ServerWorld world) {
        return night(world) != triedNight || tries < MAXIMUM_TRIES;
    }

    // Tries start again each night
    private void countTry(ServerWorld world) {
        if(night(world) != triedNight) {
            triedNight = night(world);
            tries = 0;
        }
        tries++;
    }

    private static long night(ServerWorld world) {
        return world.getTimeOfDay() / DAY_LENGTH;
    }
}
