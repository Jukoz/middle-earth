package net.sevenstars.middleearth.entity.beasts.warg;

import java.util.Arrays;
import java.util.Comparator;

public enum WargEyeVariant {
    BLUE(0),
    ORANGE(1);

    private static final WargEyeVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(WargEyeVariant::getId)).toArray(WargEyeVariant[]::new);
    private final int id;

    WargEyeVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static WargEyeVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
