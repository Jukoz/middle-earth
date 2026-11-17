package net.sevenstars.middleearth.entity.npcs.ai.task.attack;

import net.sevenstars.middleearth.entity.npcs.NpcEntity;

// How an npc fights with the weapon in hand. NpcAttackTask runs one at a time, and all of them tick on every tick
public abstract class AttackBehaviour {
    public abstract boolean canStart(NpcEntity npc);

    public abstract boolean shouldContinue(NpcEntity npc);

    public void start(NpcEntity npc) {
    }

    public void tick(NpcEntity npc) {
    }

    public void stop(NpcEntity npc) {
    }
}
