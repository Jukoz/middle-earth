package net.sevenstars.middleearth.entity.npcs.ai;

import net.sevenstars.middleearth.entity.npcs.NpcEntity;

final class RoutineBehaviour extends Behaviour {
    RoutineBehaviour() {
        super(ROUTINE_PRIORITY, MOVE | LOOK);
    }

    @Override
    boolean canStart(NpcEntity npc) {
        return npc.getDayRoutine().canStart();
    }

    @Override
    boolean shouldContinue(NpcEntity npc) {
        return npc.getDayRoutine().shouldContinue();
    }

    @Override
    void start(NpcEntity npc) {
        npc.getDayRoutine().start();
    }

    @Override
    void stop(NpcEntity npc) {
        npc.getDayRoutine().stop();
    }

    @Override
    void tick(NpcEntity npc) {
        npc.getDayRoutine().tick();
    }
}
