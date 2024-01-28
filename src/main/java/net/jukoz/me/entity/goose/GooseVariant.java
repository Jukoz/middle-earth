package net.jukoz.me.entity.goose;

import java.util.Arrays;
import java.util.Comparator;

public enum GooseVariant {
    LIGHT_GRAY(0),
    GRAY(1),
    WHITE(2);

    private static final GooseVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(GooseVariant::getId)).toArray(GooseVariant[]::new);

    private final int id;

    GooseVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static GooseVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}