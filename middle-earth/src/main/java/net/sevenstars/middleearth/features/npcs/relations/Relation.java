package net.sevenstars.middleearth.features.npcs.relations;

import net.sevenstars.middleearth.resources.datas.common.AffinityLevel;

public enum Relation {
    ENEMY,
    // Ignored until they give the npc a grudge
    NEUTRAL,
    // Never attacked, and helped when attacked
    FRIEND,
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
