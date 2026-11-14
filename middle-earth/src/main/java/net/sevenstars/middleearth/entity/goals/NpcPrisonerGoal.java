package net.sevenstars.middleearth.entity.goals;

import net.minecraft.entity.ai.goal.Goal;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.entity.npcs.routine.PrisonCell;

import java.util.EnumSet;

// A prisoner has no day to spend and nowhere to go. It ambles about in its cell
public class NpcPrisonerGoal extends Goal {
    // In ticks
    private static final int PAUSE = 40;
    private static final int PAUSE_VARIATION = 100;

    private final NpcEntity npc;
    private final double speed;
    private int pause = 0;

    public NpcPrisonerGoal(NpcEntity npc, double speed) {
        this.npc = npc;
        this.speed = speed;
        this.setControls(EnumSet.of(Control.MOVE));
    }

    @Override
    public boolean canStart() {
        return npc.isConfined() && !npc.isSleeping();
    }

    @Override
    public boolean shouldContinue() {
        return canStart();
    }

    @Override
    public void tick() {
        if(--pause > 0 || !npc.getNavigation().isIdle())
            return;
        pause = PAUSE + npc.getRandom().nextInt(PAUSE_VARIATION);
        PrisonCell cell = npc.getPrisonCell();
        int x = npc.getRandom().nextBetween(cell.minX(), cell.maxX());
        int z = npc.getRandom().nextBetween(cell.minZ(), cell.maxZ());
        npc.getNavigation().startMovingTo(x + 0.5, npc.getY(), z + 0.5, speed);
    }
}
