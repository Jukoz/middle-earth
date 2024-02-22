package net.jukoz.me.entity.uruks.misties;

import java.util.Arrays;
import java.util.Comparator;

public enum MistyUrukVariant {
    LIGHT_BROWN_RED(0),
    PALE_BLUE_YELLOW(1),
    PALE_GREY_ORANGE(2);

    private static final MistyUrukVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(MistyUrukVariant::getId)).toArray(MistyUrukVariant[]::new);

    private final int id;

    MistyUrukVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static MistyUrukVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}

