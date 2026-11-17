package net.sevenstars.middleearth.entity.npcs.routine;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.random.Random;
import net.sevenstars.middleearth.block.special.SeatBlock;
import net.sevenstars.middleearth.block.special.structureManager.features.NestSurvey;
import net.sevenstars.middleearth.block.special.structureManager.marker.MarkerKind;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.entity.seat.SeatEntity;
import net.sevenstars.middleearth.resources.datas.npc_classes.data.RoutineData;
import net.sevenstars.middleearth.resources.datas.structure_features.StructureFeatureData;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.sevenstars.middleearth.entity.npcs.routine.NpcRoutine.REST;
import static net.sevenstars.middleearth.entity.npcs.routine.NpcRoutine.REST_VARIATION;
import static net.sevenstars.middleearth.entity.npcs.routine.NpcRoutine.isLoaded;

class TableActivity extends RoutineActivity {
    // Tables within this distance of home are preferred over ones further away
    private static final int TABLE_RANGE = 40;

    private static final int MEETING_TIME = 20 * 20;
    private static final int MEETING_TIME_VARIATION = 20 * 20;
    private static final int COURT_TIME = 20 * 120;
    private static final int COURT_TIME_VARIATION = 20 * 60;
    // How long a household keeps an invitation for a visitor in mind
    private static final int INVITATION_TIME = 20 * 60;
    private static final int TEA_TIME = 20 * 45;
    private static final int TEA_TIME_VARIATION = 20 * 45;

    // How far from the lord's seat marker the seat itself may be
    private static final int LORD_SEAT_REACH = 3;
    private static final int VISIT_SEAT_RANGE = 10;
    private static final int HOST_RANGE = 24;
    private static final int VISIT_TRIES = 4;
    private static final int COMPANION_RANGE = 6;
    private static final int COMPANION_HEIGHT_RANGE = 2;

    // Chances per look while in company, and how long each lasts in ticks
    private static final int SPEAK_ONE_IN = 30;
    private static final int NOD_ONE_IN = 20;
    private static final int SPEAK_TIME = 40;
    private static final int SPEAK_TIME_VARIATION = 40;
    private static final int NOD_TIME = 30;
    private static final int COMPANION_RECHECK_ONE_IN = 40;

    // The seat at its own table that a visitor asked it to take, while it comes home for the visit
    @Nullable BlockPos hostingAt = null;
    // The household the pick found somebody in, which the visit goes to
    @Nullable BlockPos visitHousehold = null;
    @Nullable NpcEntity companion = null;

    TableActivity(NpcRoutine routine) {
        super(routine);
    }

    @Override
    boolean begin(ServerWorld world, RoutineData.Activity picked, BlockPos home) {
        if(picked == RoutineData.Activity.VISIT)
            return beginVisit(world, picked, home);
        if(picked == RoutineData.Activity.TABLE)
            return beginTable(world, picked, home, npc.getRandom());
        beginCourt(world);
        return true;
    }

    private boolean beginVisit(ServerWorld world, RoutineData.Activity picked, BlockPos home) {
        if(hostingAt != null) {
            routine.meetSeat = isLoaded(world, hostingAt) && SeatBlock.isFree(world, hostingAt) ? hostingAt : null;
            BlockPos beside = routine.meetSeat == null ? NestSurvey.standNextTo(world, hostingAt, npc.getBlockPos()) : null;
            routine.spot = routine.meetSeat != null ? routine.standAt(world, routine.meetSeat) : beside != null ? beside : hostingAt;
            routine.did("visit", "comes home to have a visitor over at " + routine.spot.toShortString());
            hostingAt = null;
            return true;
        }
        BlockPos household = visitHousehold != null ? visitHousehold : householdToVisit(world, home);
        visitHousehold = null;
        if(household == null)
            return routine.cannotStart(picked, "no other household to call on");
        // A visit is to somebody, so the household is invited to its own table
        List<NpcEntity> hosts = hostsAt(world, household);
        // To the table if there's room at it, otherwise just inside the door
        routine.meetSeat = routine.site.pickSeatNear(world, household, VISIT_SEAT_RANGE);
        BlockPos hostSeat = routine.meetSeat == null ? null : routine.site.pickSeatNear(world, household, VISIT_SEAT_RANGE, routine.meetSeat);
        for(NpcEntity host : hosts)
            host.invite(hostSeat != null ? hostSeat : household, world.getTime() + INVITATION_TIME);
        if(routine.meetSeat != null) {
            routine.spot = routine.standAt(world, routine.meetSeat);
        } else {
            BlockPos beside = NestSurvey.standNextTo(world, household, npc.getBlockPos());
            routine.spot = beside != null ? beside : household;
        }
        return true;
    }

    private boolean beginTable(ServerWorld world, RoutineData.Activity picked, BlockPos home, Random random) {
        routine.meetSeat = routine.site.pickSeat(world, random, home, TABLE_RANGE);
        if(routine.meetSeat == null)
            return routine.cannotStart(picked, "no free seat at any table");
        routine.spot = routine.standAt(world, routine.meetSeat);
        return true;
    }

    private void beginCourt(ServerWorld world) {
        StructureFeatureData.Marker seatMarker = null;
        for(StructureFeatureData.Marker marker : routine.site.getMarkers(MarkerKind.LORD_SEAT)) {
            if(seatMarker == null || marker.pos().getSquaredDistance(npc.getBlockPos()) < seatMarker.pos().getSquaredDistance(npc.getBlockPos()))
                seatMarker = marker;
        }
        // The marker is beside the seat
        BlockPos seat = null;
        for(BlockPos candidate : routine.site.getSeats()) {
            if(candidate.isWithinDistance(seatMarker.pos(), LORD_SEAT_REACH) && isLoaded(world, candidate) && SeatBlock.isFree(world, candidate)
                    && (seat == null || candidate.getSquaredDistance(seatMarker.pos()) < seat.getSquaredDistance(seatMarker.pos())))
                seat = candidate;
        }
        routine.facing = seatMarker.facing();
        if(seat != null) {
            routine.meetSeat = seat;
            routine.spot = routine.standAt(world, seat);
        } else {
            routine.spot = seatMarker.pos();
        }
    }

    @Override
    void arrive(ServerWorld world, Random random, long time) {
        if(routine.activity == RoutineData.Activity.COURT)
            arriveCourt(world, random, time);
        else
            arriveVisitOrTable(world, random, time);
    }

    private void arriveVisitOrTable(ServerWorld world, Random random, long time) {
        // Somebody quicker may have taken the seat. The table is still the place to be
        boolean seated = routine.sitting.sitDown(world);
        routine.phaseEnd = seated ? time + TEA_TIME + random.nextInt(TEA_TIME_VARIATION) : time + MEETING_TIME + random.nextInt(MEETING_TIME_VARIATION);
        companion = null;
        routine.meetSeat = null;
    }

    private void arriveCourt(ServerWorld world, Random random, long time) {
        boolean seated = routine.sitting.sitDown(world);
        routine.phaseEnd = time + COURT_TIME + random.nextInt(COURT_TIME_VARIATION);
        routine.did("court", seated ? "sat down at the lord's seat" : "stands at the lord's seat");
        companion = null;
        routine.meetSeat = null;
    }

    @Override
    void work(ServerWorld world, Random random, boolean done, long time) {
        if(done) {
            routine.finish(world, REST + random.nextInt(REST_VARIATION));
        } else if(companion == null || !companion.isAlive() || npc.squaredDistanceTo(companion) > COMPANION_RANGE * COMPANION_RANGE) {
            companion = RoutineSites.takeEntitySearch(world) ? findCompanion(world) : null;
            routine.lookTarget = routine.activity == RoutineData.Activity.COURT && !(npc.getVehicle() instanceof SeatEntity) ? routine.facingTarget() : null;
        } else {
            routine.lookTarget = companion.getEyePos();
            converse(random);
        }
    }

    // They take turns. One says something while the other listens and nods now and then. Somebody only passing by,
    // or asleep in the same room, is not spoken to
    private void converse(Random random) {
        NpcRoutine other = companion.getDayRoutine();
        if(!other.isInCompany()) {
            // Somebody who came to sit with it may be nearby by now
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
            // Whoever is spoken to turns to the speaker, so the two look at each other
            other.turnTo(npc);
        }
    }

    void turnTo(NpcEntity speaker) {
        companion = speaker;
    }

    // The nearest of those it's in company with. Failing that, it turns to face anyone else standing around
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

    // Of a few households tried, picks one with somebody in it to call on. Nobody visits an empty house
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
