package net.jukoz.me.entity.beasts.warg;

import java.util.Arrays;
import java.util.Comparator;

public enum WargVariant {
    BROWN(0),
    WHITE(1),
    GREY(2),
    BLACK(3),
    LIGHT_GREY(4);

    private static final WargVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(WargVariant::getId)).toArray(WargVariant[]::new);
    private final int id;

    WargVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static WargVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
