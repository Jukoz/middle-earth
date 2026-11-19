package net.sevenstars.middleearth.features.npcs.routine;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.random.Random;
import net.sevenstars.middleearth.block.special.SeatBlock;
import net.sevenstars.middleearth.block.special.structureManager.features.NestSurvey;
import net.sevenstars.middleearth.features.npcs.NpcEntity;
import net.sevenstars.middleearth.entity.seat.SeatEntity;
import net.sevenstars.middleearth.features.npcs.classes.data.RoutineData;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.sevenstars.middleearth.features.npcs.routine.NpcRoutine.REST;
import static net.sevenstars.middleearth.features.npcs.routine.NpcRoutine.REST_VARIATION;
import static net.sevenstars.middleearth.features.npcs.routine.NpcRoutine.isLoaded;

class TableActivity extends RoutineActivity {
    private static final int TABLE_RANGE = 40;

    private static final int MEETING_TIME = 20 * 20;
    private static final int MEETING_TIME_VARIATION = 20 * 20;
    private static final int INVITATION_TIME = 20 * 60;
    private static final int TEA_TIME = 20 * 45;
    private static final int TEA_TIME_VARIATION = 20 * 45;

    private static final int VISIT_SEAT_RANGE = 10;
    private static final int HOST_RANGE = 24;
    private static final int VISIT_TRIES = 4;
    private static final int COMPANION_RANGE = 6;
    private static final int COMPANION_HEIGHT_RANGE = 2;

    // Chance per look, and how long each lasts in ticks
    private static final int SPEAK_ONE_IN = 30;
    private static final int NOD_ONE_IN = 20;
    private static final int SPEAK_TIME = 40;
    private static final int SPEAK_TIME_VARIATION = 40;
    private static final int NOD_TIME = 30;
    private static final int COMPANION_RECHECK_ONE_IN = 40;

    // Seat at its own table a visitor asked it to take
    @Nullable BlockPos hostingAt = null;
    @Nullable NpcEntity companion = null;

    TableActivity(NpcRoutine routine) {
        super(routine);
    }

    @Override
    boolean isSupportedBy(RoutineSite site, RoutineData.Activity kind) {
        return switch (kind) {
            case VISIT -> site.hasHouseholdsToVisit();
            default -> site.hasTables();
        };
    }

    @Override
    boolean isSocial(RoutineData.Activity kind) {
        return true;
    }

    @Override
    boolean isInCompany() {
        return true;
    }

    @Override
    String label(RoutineData.Activity kind) {
        boolean seated = npc.getVehicle() instanceof SeatEntity;
        return switch (kind) {
            case VISIT -> seated ? "visiting, seated" : "visiting";
            default -> seated ? "sitting at a table" : "table";
        };
    }

    @Override
    boolean begin(ServerWorld world, RoutineData.Activity picked, BlockPos home) {
        if(picked == RoutineData.Activity.VISIT)
            return beginVisit(world, picked, home);
        return beginTable(world, picked, home, npc.getRandom());
    }

    private boolean beginVisit(ServerWorld world, RoutineData.Activity picked, BlockPos home) {
        if(hostingAt != null) {
            routine.setMeetSeat(isLoaded(world, hostingAt) && SeatBlock.isFree(world, hostingAt) ? hostingAt : null);
            BlockPos beside = routine.meetSeat() == null ? NestSurvey.standNextTo(world, hostingAt, npc.getBlockPos()) : null;
            routine.setSpot(routine.meetSeat() != null ? routine.standAt(world, routine.meetSeat()) : beside != null ? beside : hostingAt);
            routine.did("visit", "comes home to have a visitor over at " + routine.spot().toShortString());
            hostingAt = null;
            return true;
        }
        BlockPos household = householdToVisit(world, home);
        if(household == null)
            return routine.cannotStart(picked, "no other household to call on");
        List<NpcEntity> hosts = hostsAt(world, household);
        routine.setMeetSeat(routine.site.pickSeatNear(world, household, VISIT_SEAT_RANGE));
        BlockPos hostSeat = routine.meetSeat() == null ? null : routine.site.pickSeatNear(world, household, VISIT_SEAT_RANGE, routine.meetSeat());
        for(NpcEntity host : hosts)
            host.invite(hostSeat != null ? hostSeat : household, world.getTime() + INVITATION_TIME);
        if(routine.meetSeat() != null) {
            routine.setSpot(routine.standAt(world, routine.meetSeat()));
        } else {
            BlockPos beside = NestSurvey.standNextTo(world, household, npc.getBlockPos());
            routine.setSpot(beside != null ? beside : household);
        }
        return true;
    }

    private boolean beginTable(ServerWorld world, RoutineData.Activity picked, BlockPos home, Random random) {
        routine.setMeetSeat(routine.site.pickSeat(world, random, home, TABLE_RANGE));
        if(routine.meetSeat() == null)
            return routine.cannotStart(picked, "no free seat at any table");
        routine.setSpot(routine.standAt(world, routine.meetSeat()));
        return true;
    }

    @Override
    void arrive(ServerWorld world, Random random, long time) {
        // Somebody quicker may have taken the seat. The table is still the place to be
        boolean seated = routine.sitting.sitDown(world);
        routine.setPhaseEnd(seated ? time + TEA_TIME + random.nextInt(TEA_TIME_VARIATION) : time + MEETING_TIME + random.nextInt(MEETING_TIME_VARIATION));
        companion = null;
        routine.setMeetSeat(null);
    }

    @Override
    void work(ServerWorld world, Random random, boolean done, long time) {
        if(done) {
            routine.finish(world, REST + random.nextInt(REST_VARIATION));
        } else if(companion == null || !companion.isAlive() || npc.squaredDistanceTo(companion) > COMPANION_RANGE * COMPANION_RANGE) {
            companion = RoutineSites.takeEntitySearch(world) ? findCompanion(world) : null;
            routine.setLookTarget(null);
        } else {
            routine.setLookTarget(companion.getEyePos());
            converse(random);
        }
    }

    // They take turns. Passers-by and sleepers aren't spoken to
    private void converse(Random random) {
        NpcRoutine other = companion.getDayRoutine();
        if(!other.isInCompany()) {
            if(random.nextInt(COMPANION_RECHECK_ONE_IN) == 0)
                companion = null;
            return;
        }
        if(npc.getTalk() != NpcEntity.Talk.NONE)
            return;
        if(companion.getTalk().isSpoken()) {
            if(random.nextInt(NOD_ONE_IN) == 0)
                npc.talk(NpcEntity.Talk.NOD, NOD_TIME);
        } else if(random.nextInt(SPEAK_ONE_IN) == 0) {
            npc.talk(NpcEntity.Talk.SPOKEN.get(random.nextInt(NpcEntity.Talk.SPOKEN.size())), SPEAK_TIME + random.nextInt(SPEAK_TIME_VARIATION));
            other.turnTo(npc);
        }
    }

    void turnTo(NpcEntity speaker) {
        companion = speaker;
    }

    private @Nullable NpcEntity findCompanion(ServerWorld world) {
        NpcEntity nearest = null;
        boolean nearestInCompany = false;
        for(NpcEntity other : world.getEntitiesByClass(NpcEntity.class, npc.getBoundingBox().expand(COMPANION_RANGE, COMPANION_HEIGHT_RANGE, COMPANION_RANGE), found -> found != npc && found.isAlive() && npc.getVisibilityCache().canSee(found))) {
            boolean inCompany = other.getDayRoutine().isInCompany();
            if(nearest == null || inCompany && !nearestInCompany || inCompany == nearestInCompany && npc.squaredDistanceTo(other) < npc.squaredDistanceTo(nearest)) {
                nearest = other;
                nearestInCompany = inCompany;
            }
        }
        return nearest;
    }

    // Nobody visits an empty house
    @Nullable BlockPos householdToVisit(ServerWorld world, BlockPos home) {
        for(int attempt = 0; attempt < VISIT_TRIES; attempt++) {
            BlockPos household = routine.site.pickHousehold(world, home, npc.getRandom());
            if(household == null)
                return null;
            if(!hostsAt(world, household).isEmpty())
                return household;
        }
        return null;
    }

    private List<NpcEntity> hostsAt(ServerWorld world, BlockPos household) {
        return world.getEntitiesByClass(NpcEntity.class, new Box(household).expand(HOST_RANGE),
                other -> other != npc && household.equals(other.getRoutineHome()) && !other.isSleeping());
    }
}
