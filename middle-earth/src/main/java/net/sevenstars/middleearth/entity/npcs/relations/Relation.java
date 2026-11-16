package net.sevenstars.middleearth.entity.npcs.relations;

import net.sevenstars.middleearth.resources.datas.common.AffinityLevel;

// How an npc sees someone, which decides whether it attacks them, ignores them or comes to their help
public enum Relation {
    // Attacked on sight
    ENEMY,
    // Ignored, until they give the npc a grudge
    NEUTRAL,
    // A faction on good terms: never attacked, and helped when attacked
    FRIEND,
    // The npc's own side
    ALLY;

    public boolean isFriendly() {
        return this == FRIEND || this == ALLY;
    }

    public static Relation of(AffinityLevel affinity) {
        return switch(affinity) {
            case HOSTILE -> ENEMY;
            case NEUTRAL -> NEUTRAL;
            case FRIENDLY -> FRIEND;
            case ALLY -> ALLY;
        };
    }
}
