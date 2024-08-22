package net.jukoz.me.entity.humans.dale;

import java.util.Arrays;
import java.util.Comparator;

public enum DaleHumanVariant {
    LIGHT_BROWN_RED(0),
    PALE_BLUE_YELLOW(1),
    PALE_GREY_ORANGE(2);

    private static final DaleHumanVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(DaleHumanVariant::getId)).toArray(DaleHumanVariant[]::new);

    private final int id;

    DaleHumanVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static DaleHumanVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}

