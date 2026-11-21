package net.sevenstars.middleearth.features.npcs.ai;

import net.minecraft.entity.LivingEntity;
import net.sevenstars.middleearth.features.npcs.NpcEntity;

// Drops a party leader who has been gone too long
final class PartyLeaderBehaviour extends Behaviour {
    // In think ticks, 10 a second
    private static final int LEADER_MISSING_DELAY = 10 * 30;

    private int missingChecks = 0;

    PartyLeaderBehaviour() {
        super(NONE);
    }

    @Override
    boolean canStart(NpcEntity npc) {
        return npc.getPartyLeader() != null;
    }

    @Override
    boolean shouldContinue(NpcEntity npc) {
        return false;
    }

    @Override
    void start(NpcEntity npc) {
        if(npc.getEntityWorld().getEntity(npc.getPartyLeader()) instanceof LivingEntity leader && leader.isAlive()) {
            missingChecks = 0;
            return;
        }
        missingChecks++;
        if(missingChecks >= LEADER_MISSING_DELAY) {
            missingChecks = 0;
            npc.setPartyLeader(null);
        }
    }
}
