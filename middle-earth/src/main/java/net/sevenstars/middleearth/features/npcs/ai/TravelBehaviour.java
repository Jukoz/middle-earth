package net.sevenstars.middleearth.features.npcs.ai;

import net.sevenstars.middleearth.features.npcs.NpcEntity;
import org.jetbrains.annotations.Nullable;

final class TravelBehaviour extends Behaviour {
    // Vanilla walks 1 in 120 server ticks, so this is 1 in this many think ticks
    private static final int WANDER_CHANCE = 60;
    private static final int DESPAWN_COUNTER = 100;

    private final TravelMode.Journey journey = new TravelMode.Journey();
    private final TravelMode.Stroll stroll = new TravelMode.Stroll();
    private final TravelMode.Follow follow = new TravelMode.Follow(journey);
    private final TravelMode.Mounted mounted = new TravelMode.Mounted(stroll);

    private boolean ignoreChance = false;
    // Found by canStart, walked on start
    private @Nullable TravelMode mode = null;
    private @Nullable TravelMode.Leg leg = null;

    TravelBehaviour() {
        super(TRAVEL_PRIORITY, MOVE);
    }

    static boolean isStrolling(NpcEntity npc) {
        return !npc.isConfined() && !npc.hasVehicle() && npc.getPartyLeader() == null && npc.getTravelHeading() == null;
    }

    // The next stroll goes ahead whatever the wander chance says
    void skipChance() {
        ignoreChance = true;
    }

    @Override
    boolean canStart(NpcEntity npc) {
        if(npc.isConfined())
            return false;
        TravelMode candidate = modeFor(npc);
        boolean forced = ignoreChance;
        switch(candidate.gate(npc)) {
            case SKIP:
                return false;
            case FORCED:
                forced = true;
                break;
            case CHANCE:
                break;
        }
        if(npc.hasControllingPassenger())
            return false;
        if(!forced) {
            if(npc.getDespawnCounter() >= DESPAWN_COUNTER)
                return false;
            if(npc.getRandom().nextInt(WANDER_CHANCE) != 0)
                return false;
        }
        // One forced attempt, even if no target turns up
        ignoreChance = false;
        TravelMode.Leg planned = candidate.plan(npc);
        if(planned == null)
            return false;
        mode = candidate;
        leg = planned;
        return true;
    }

    @Override
    boolean shouldContinue(NpcEntity npc) {
        return !npc.getNavigation().isIdle() && !npc.hasControllingPassenger();
    }

    @Override
    void start(NpcEntity npc) {
        mode.begin(npc, leg);
    }

    @Override
    void stop(NpcEntity npc) {
        npc.getNavigation().stop();
    }

    private TravelMode modeFor(NpcEntity npc) {
        if(npc.hasVehicle())
            return mounted;
        if(npc.getPartyLeader() != null)
            return follow;
        return npc.getTravelHeading() == null ? stroll : journey;
    }
}
