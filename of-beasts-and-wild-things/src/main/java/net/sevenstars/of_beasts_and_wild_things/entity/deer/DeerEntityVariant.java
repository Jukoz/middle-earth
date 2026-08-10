package net.sevenstars.of_beasts_and_wild_things.entity.deer;

import java.util.Arrays;
import java.util.Comparator;

public enum DeerEntityVariant {
    SPOTS(0),
    NO_SPOTS(1);

    private static final DeerEntityVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(DeerEntityVariant::getId)).toArray(DeerEntityVariant[]::new);

    private final int id;

    DeerEntityVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static DeerEntityVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}