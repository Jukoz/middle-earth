package net.sevenstars.middleearth.entity.npcs.ai.task.idle;

import net.sevenstars.middleearth.entity.npcs.NpcEntity;

public class RoutineBehaviour extends IdleBehaviour {
    public RoutineBehaviour() {
        super(5, true, true);
    }

    @Override
    public boolean canStart(NpcEntity npc) {
        return npc.getDayRoutine().canStart();
    }

    @Override
    public boolean shouldContinue(NpcEntity npc) {
        return npc.getDayRoutine().shouldContinue();
    }

    @Override
    public void start(NpcEntity npc) {
        npc.getDayRoutine().start();
    }

    @Override
    public void stop(NpcEntity npc) {
        npc.getDayRoutine().stop();
    }

    @Override
    public void tick(NpcEntity npc) {
        npc.getDayRoutine().tick();
    }
}
