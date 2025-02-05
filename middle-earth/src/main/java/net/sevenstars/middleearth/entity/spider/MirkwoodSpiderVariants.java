package net.sevenstars.middleearth.entity.spider;

import java.util.Arrays;
import java.util.Comparator;

public enum MirkwoodSpiderVariants {
    BLACK(0),
    BROWN(1),
    DARK_GREEN(2);

    private static final MirkwoodSpiderVariants[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(MirkwoodSpiderVariants::getId)).toArray(MirkwoodSpiderVariants[]::new);

    private final int id;

    MirkwoodSpiderVariants(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static MirkwoodSpiderVariants byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}

