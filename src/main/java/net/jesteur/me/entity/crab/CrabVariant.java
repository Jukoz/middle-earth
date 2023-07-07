package net.jesteur.me.entity.crab;

import java.util.Arrays;
import java.util.Comparator;

public enum CrabVariant {
    ORANGE(0),
    DARK_BROWN(1);

    private static final CrabVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(CrabVariant::getId)).toArray(CrabVariant[]::new);

    private final int id;

    CrabVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static CrabVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}