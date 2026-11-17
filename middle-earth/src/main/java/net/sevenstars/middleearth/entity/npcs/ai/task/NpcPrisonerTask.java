package net.sevenstars.middleearth.entity.npcs.ai.task;

import net.sevenstars.middleearth.entity.ai.brain.ActivitiesME;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.entity.npcs.ai.NpcBrain;
import net.sevenstars.middleearth.entity.npcs.routine.PrisonCell;

// A prisoner has no routine and nowhere to go. It wanders around its cell
public class NpcPrisonerTask extends NpcTask {
    // In goal ticks, and the game ticks them every other tick
    private static final int PAUSE = 40;
    private static final int PAUSE_VARIATION = 100;

    private int pause = 0;

    public NpcPrisonerTask() {
        super(ActivitiesME.CONFINED);
    }

    @Override
    protected boolean canStart(NpcEntity npc) {
        return npc.isConfined() && !npc.isSleeping();
    }

    @Override
    protected boolean shouldContinue(NpcEntity npc) {
        return canStart(npc);
    }

    @Override
    protected void tick(NpcEntity npc) {
        if(--pause > 0 || !npc.getNavigation().isIdle())
            return;
        pause = PAUSE + npc.getRandom().nextInt(PAUSE_VARIATION);
        PrisonCell cell = npc.getPrisonCell();
        int x = npc.getRandom().nextBetween(cell.minX(), cell.maxX());
        int z = npc.getRandom().nextBetween(cell.minZ(), cell.maxZ());
        npc.getNavigation().startMovingTo(x + 0.5, npc.getY(), z + 0.5, NpcBrain.PRISONER_SPEED);
    }
}
