package net.jesteur.me.entity.trolls.cave;

import java.util.Arrays;
import java.util.Comparator;

public enum CaveTrollVariant {
    GREY_YELLOW(0),
    GREY_BLUE(1),
    GREY_STONE(2);

    private static final CaveTrollVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(CaveTrollVariant::getId)).toArray(CaveTrollVariant[]::new);

    private final int id;

    CaveTrollVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static CaveTrollVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}

