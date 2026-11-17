package net.sevenstars.middleearth.entity.npcs.ai;

import net.sevenstars.middleearth.entity.npcs.NpcEntity;

// One thing an npc does. It needs some of the npc's controls, and a BehaviourSelector starts it only when each of them is
// free or held by a behaviour with a higher priority number, which it then takes over from
abstract class Behaviour {
    static final int NONE = 0;
    static final int MOVE = 1;
    static final int LOOK = 2;
    static final int TARGET = 4;

    // A lower number takes over from a higher one
    static final int REVENGE_PRIORITY = 1;
    static final int ENEMY_PRIORITY = 3;
    static final int ATTACK_PRIORITY = 4;
    static final int ROUTINE_PRIORITY = 5;
    static final int WANDERER_BED_PRIORITY = 5;
    static final int TRAVEL_PRIORITY = 6;
    static final int LOOK_PRIORITY = 7;
    // On each think tick, for the looks that fill the time
    static final float LOOK_CHANCE = 0.02F;

    private final int priority;
    private final int controls;

    Behaviour(int priority, int controls) {
        this.priority = priority;
        this.controls = controls;
    }

    // For one that nothing else competes with
    Behaviour(int controls) {
        this(0, controls);
    }

    int priority() {
        return priority;
    }

    int controls() {
        return controls;
    }

    // Also ticks on the ticks in between
    boolean runsEveryTick() {
        return false;
    }

    abstract boolean canStart(NpcEntity npc);

    abstract boolean shouldContinue(NpcEntity npc);

    void start(NpcEntity npc) {
    }

    void tick(NpcEntity npc) {
    }

    void stop(NpcEntity npc) {
    }
}
