package net.jukoz.me.entity.uruks.mordor;

import java.util.Arrays;
import java.util.Comparator;

public enum MordorUrukVariant {
    LIGHT_BROWN_RED(0),
    PALE_BLUE_YELLOW(1),
    PALE_GREY_ORANGE(2);

    private static final MordorUrukVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(MordorUrukVariant::getId)).toArray(MordorUrukVariant[]::new);

    private final int id;

    MordorUrukVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static MordorUrukVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}

