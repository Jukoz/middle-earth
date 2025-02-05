package net.sevenstars.middleearth.entity.uruks.isengard;

import java.util.Arrays;
import java.util.Comparator;

public enum IsengardUrukHaiVariant {
    LIGHT_BROWN_RED(0),
    PALE_BLUE_YELLOW(1),
    PALE_GREY_ORANGE(2);

    private static final IsengardUrukHaiVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(IsengardUrukHaiVariant::getId)).toArray(IsengardUrukHaiVariant[]::new);

    private final int id;

    IsengardUrukHaiVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static IsengardUrukHaiVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}

