package net.sevenstars.middleearth.entity.pheasant;

import java.util.Arrays;
import java.util.Comparator;

public enum PheasantVariant {
    MALE(0),
    FEMALE(1);

    private static final PheasantVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(PheasantVariant::getId)).toArray(PheasantVariant[]::new);
    private final int id;

    PheasantVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static PheasantVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
