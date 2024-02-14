package net.jukoz.me.entity.humans.gondor;

import java.util.Arrays;
import java.util.Comparator;

public enum GondorHumanVariant {
    LIGHT_BROWN_RED(0),
    PALE_BLUE_YELLOW(1),
    PALE_GREY_ORANGE(2);

    private static final GondorHumanVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(GondorHumanVariant::getId)).toArray(GondorHumanVariant[]::new);

    private final int id;

    GondorHumanVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static GondorHumanVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}

