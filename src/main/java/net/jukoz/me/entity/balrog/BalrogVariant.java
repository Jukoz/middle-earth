package net.jukoz.me.entity.balrog;

import java.util.Arrays;
import java.util.Comparator;

public enum BalrogVariant {
    BASE(0);

    private static final BalrogVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(BalrogVariant::getId)).toArray(BalrogVariant[]::new);

    private final int id;

    BalrogVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static BalrogVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}