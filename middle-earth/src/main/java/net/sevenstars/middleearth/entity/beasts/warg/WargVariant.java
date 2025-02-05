package net.sevenstars.middleearth.entity.beasts.warg;

import java.util.Arrays;
import java.util.Comparator;

public enum WargVariant {
    BROWN(0),
    BLACK(1),
    GRAY(2),
    LIGHT_GRAY(3),
    GRAY_FACE(4),
    RED_BALD(5),
    TAN(6),
    TAN_GRAY(7);

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
