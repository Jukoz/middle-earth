package net.jukoz.me.entity.orcs.misties;

import java.util.Arrays;
import java.util.Comparator;

public enum MistyOrcVariant {
    LIGHT_BROWN_RED(0),
    PALE_BLUE_YELLOW(1),
    PALE_GREY_ORANGE(2);

    private static final MistyOrcVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(MistyOrcVariant::getId)).toArray(MistyOrcVariant[]::new);

    private final int id;

    MistyOrcVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static MistyOrcVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}

