package net.jesteur.me.entity.trolls.snow;

import java.util.Arrays;
import java.util.Comparator;

public enum SnowTrollVariant {
    SNOW(0),
    ICE(1);

    private static final SnowTrollVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(SnowTrollVariant::getId)).toArray(SnowTrollVariant[]::new);

    private final int id;

    SnowTrollVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static SnowTrollVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}

