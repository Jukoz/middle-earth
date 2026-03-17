package net.sevenstars.middleearth.entity.beasts.great_horn;

import java.util.Arrays;
import java.util.Comparator;

public enum GreatHornVariantDep {
    BROWN(0),
    COLD(1),
    TEMPERATE(2),
    WARM(3),
    WHITE(4);

    private static final GreatHornVariantDep[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(GreatHornVariantDep::getId)).toArray(GreatHornVariantDep[]::new);
    private final int id;

    GreatHornVariantDep(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static GreatHornVariantDep byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}