package net.jesteur.me.entity.spider;

import java.util.Arrays;
import java.util.Comparator;

public enum SpiderVariant {
    BLACK(0),
    BROWN(1),
    DARK_GREEN(2);

    private static final SpiderVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(SpiderVariant::getId)).toArray(SpiderVariant[]::new);

    private final int id;

    SpiderVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static SpiderVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}

