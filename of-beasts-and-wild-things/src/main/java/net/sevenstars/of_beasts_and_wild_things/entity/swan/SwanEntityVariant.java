package net.sevenstars.of_beasts_and_wild_things.entity.swan;

import java.util.Arrays;
import java.util.Comparator;

public enum SwanEntityVariant {
    WHITE(0),
    BLACK(1),
    TRUMPETER(2),
    WHOOPER(3);

    private static final SwanEntityVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(SwanEntityVariant::getId)).toArray(SwanEntityVariant[]::new);

    private final int id;

    SwanEntityVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static SwanEntityVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}