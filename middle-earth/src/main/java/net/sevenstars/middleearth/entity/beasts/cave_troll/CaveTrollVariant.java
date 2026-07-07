package net.sevenstars.middleearth.entity.beasts.cave_troll;

import java.util.Arrays;
import java.util.Comparator;

public enum CaveTrollVariant {
    GREEN(0),
    YELLOW(1),
    BROWN(2),
    BLUE(3),
    GRAY(4),
    STONE(5),
    GREY_BLUE(6);

    private static final CaveTrollVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(CaveTrollVariant::getId)).toArray(CaveTrollVariant[]::new);
    private final int id;

    CaveTrollVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static CaveTrollVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
