package net.jukoz.me.entity.swan;

import java.util.Arrays;
import java.util.Comparator;

public enum SwanVariant {
    WHITE(0),
    BLACK(1),
    TRUMPETER(2),
    WHOOPER(3);

    private static final SwanVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(SwanVariant::getId)).toArray(SwanVariant[]::new);

    private final int id;

    SwanVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static SwanVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}