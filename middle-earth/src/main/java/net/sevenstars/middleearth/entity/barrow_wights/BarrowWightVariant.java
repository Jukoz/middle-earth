package net.sevenstars.middleearth.entity.barrow_wights;

import java.util.Arrays;
import java.util.Comparator;

public enum BarrowWightVariant {
    BASIC(0);

    private static final BarrowWightVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(BarrowWightVariant::getId)).toArray(BarrowWightVariant[]::new);

    private final int id;

    BarrowWightVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static BarrowWightVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
