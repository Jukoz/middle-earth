package net.sevenstars.middleearth.entity.orcs.misties;

import java.util.Arrays;
import java.util.Comparator;

public enum MistyGoblinVariant {
    LIGHT_BROWN_RED(0),
    PALE_BLUE_YELLOW(1),
    PALE_GREY_ORANGE(2);

    private static final MistyGoblinVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(MistyGoblinVariant::getId)).toArray(MistyGoblinVariant[]::new);

    private final int id;

    MistyGoblinVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static MistyGoblinVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}

