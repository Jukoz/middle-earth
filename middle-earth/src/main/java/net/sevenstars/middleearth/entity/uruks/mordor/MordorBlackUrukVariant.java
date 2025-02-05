package net.sevenstars.middleearth.entity.uruks.mordor;

import java.util.Arrays;
import java.util.Comparator;

public enum MordorBlackUrukVariant {
    LIGHT_BROWN_RED(0),
    PALE_BLUE_YELLOW(1),
    PALE_GREY_ORANGE(2);

    private static final MordorBlackUrukVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(MordorBlackUrukVariant::getId)).toArray(MordorBlackUrukVariant[]::new);

    private final int id;

    MordorBlackUrukVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static MordorBlackUrukVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}

