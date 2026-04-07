package net.sevenstars.of_beasts_and_wild_things.entity.pheasant;

import java.util.Arrays;
import java.util.Comparator;

public enum PheasantEntityVariant {
    MALE(0),
    FEMALE(1);

    private static final PheasantEntityVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(PheasantEntityVariant::getId)).toArray(PheasantEntityVariant[]::new);

    private final int id;

    PheasantEntityVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static PheasantEntityVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
