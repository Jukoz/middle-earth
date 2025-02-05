package net.sevenstars.middleearth.entity.orcs.isengard;

import java.util.Arrays;
import java.util.Comparator;

public enum IsengardOrcVariant {
    LIGHT_BROWN_RED(0),
    PALE_BLUE_YELLOW(1),
    PALE_GREY_ORANGE(2);

    private static final IsengardOrcVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(IsengardOrcVariant::getId)).toArray(IsengardOrcVariant[]::new);

    private final int id;

    IsengardOrcVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static IsengardOrcVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}

