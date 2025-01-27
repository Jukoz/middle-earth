package net.sevenstars.middleearth.entity.snail;

import java.util.Arrays;
import java.util.Comparator;

public enum SnailVariant {
    GREEN(0),
    PALE_GREEN(1),
    BROWN(2),
    GRAY(3);

    private static final SnailVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(SnailVariant::getId)).toArray(SnailVariant[]::new);

    private final int id;

    SnailVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static SnailVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
