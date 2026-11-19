package net.sevenstars.middleearth.features.npcs.ai;

import net.sevenstars.middleearth.features.npcs.NpcEntity;

final class DoorShutBehaviour extends Behaviour {
    private final ModDoorBehaviour doors;

    DoorShutBehaviour(ModDoorBehaviour doors) {
        super(NONE);
        this.doors = doors;
    }

    @Override
    boolean canStart(NpcEntity npc) {
        return doors.canShutLeftOpen(npc);
    }

    @Override
    boolean shouldContinue(NpcEntity npc) {
        return false;
    }

    @Override
    void start(NpcEntity npc) {
        doors.shutLeftOpen(npc);
    }
}
