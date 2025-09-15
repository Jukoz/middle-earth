package net.sevenstars.middleearth.entity.beasts.great_horn;

import java.util.Arrays;
import java.util.Comparator;

public enum GreatHornVariant {
    BROWN(0),
    COLD(1),
    TEMPERATE(2),
    WARM(3),
    WHITE(4);

    private static final GreatHornVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(GreatHornVariant::getId)).toArray(GreatHornVariant[]::new);
    private final int id;

    GreatHornVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static GreatHornVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}