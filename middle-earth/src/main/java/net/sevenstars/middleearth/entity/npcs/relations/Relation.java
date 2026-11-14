package net.sevenstars.middleearth.entity.npcs.relations;

import net.sevenstars.middleearth.resources.datas.common.AffinityLevel;

// How an npc stands with someone, which decides whether it attacks them, leaves them be or comes to their help
public enum Relation {
    // Attacked on sight
    ENEMY,
    // Left alone, until they give the npc a grudge
    NEUTRAL,
    // Another faction on good terms: never attacked, and helped when attacked
    FRIEND,
    // Its own side
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
