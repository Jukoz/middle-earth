package net.sevenstars.middleearth.entity.npcs.ai;

import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.entity.npcs.routine.PrisonCell;

final class PrisonerBehaviour extends Behaviour {
    // Multiple of walking speed
    private static final double PRISONER_SPEED = 0.6;
    // In think ticks
    private static final int PAUSE = 40;
    private static final int PAUSE_VARIATION = 100;

    private int pause = 0;

    PrisonerBehaviour() {
        super(MOVE);
    }

    @Override
    boolean canStart(NpcEntity npc) {
        return npc.isConfined() && !npc.isSleeping();
    }

    @Override
    boolean shouldContinue(NpcEntity npc) {
        return canStart(npc);
    }

    @Override
    void tick(NpcEntity npc) {
        if(--pause > 0 || !npc.getNavigation().isIdle())
            return;
        pause = PAUSE + npc.getRandom().nextInt(PAUSE_VARIATION);
        PrisonCell cell = npc.getPrisonCell();
        int x = npc.getRandom().nextBetween(cell.minX(), cell.maxX());
        int z = npc.getRandom().nextBetween(cell.minZ(), cell.maxZ());
        npc.getNavigation().startMovingTo(x + 0.5, npc.getY(), z + 0.5, PRISONER_SPEED);
    }
}
