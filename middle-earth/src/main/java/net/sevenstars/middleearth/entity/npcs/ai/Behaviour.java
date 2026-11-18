package net.sevenstars.middleearth.entity.npcs.ai;

import net.sevenstars.middleearth.entity.npcs.NpcEntity;

// Starts only when each control it needs is free, or held by a higher priority number
abstract class Behaviour {
    static final int NONE = 0;
    static final int MOVE = 1;
    static final int LOOK = 2;
    static final int TARGET = 4;

    static final int REVENGE_PRIORITY = 1;
    static final int ENEMY_PRIORITY = 2;
    // Below the enemy search, so anyone awake is dealt with before the beds
    static final int RAID_PRIORITY = 3;
    static final int ATTACK_PRIORITY = 4;
    static final int ROUTINE_PRIORITY = 5;
    static final int WANDERER_BED_PRIORITY = 5;
    static final int TRAVEL_PRIORITY = 6;
    static final int LOOK_PRIORITY = 7;
    static final float LOOK_CHANCE = 0.02F;

    private final int priority;
    private final int controls;

    Behaviour(int priority, int controls) {
        this.priority = priority;
        this.controls = controls;
    }

    Behaviour(int controls) {
        this(0, controls);
    }

    int priority() {
        return priority;
    }

    int controls() {
        return controls;
    }

    // Also runs between think ticks
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
