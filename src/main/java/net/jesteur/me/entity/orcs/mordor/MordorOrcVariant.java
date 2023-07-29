package net.jesteur.me.entity.orcs.mordor;

import java.util.Arrays;
import java.util.Comparator;

public enum MordorOrcVariant {
    LIGHT_BROWN_RED(0),
    PALE_BLUE_YELLOW(1),
    PALE_GREY_ORANGE(2);

    private static final MordorOrcVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(MordorOrcVariant::getId)).toArray(MordorOrcVariant[]::new);

    private final int id;

    MordorOrcVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static MordorOrcVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}

