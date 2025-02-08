package net.sevenstars.of_beasts_and_wild_things.entity.snail;

import java.util.Arrays;
import java.util.Comparator;

public enum SnailEntityVariant {
    GREEN(0),
    PALE_GREEN(1),
    BROWN(2),
    GRAY(3);

    private static final SnailEntityVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(SnailEntityVariant::getId)).toArray(SnailEntityVariant[]::new);

    private final int id;

    SnailEntityVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static SnailEntityVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
