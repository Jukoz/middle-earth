package net.jukoz.me.entity.orcs.wild;

import java.util.Arrays;
import java.util.Comparator;

public enum WildGoblinVariant {
    LIGHT_BROWN_RED(0),
    PALE_BLUE_YELLOW(1),
    PALE_GREY_ORANGE(2);

    private static final WildGoblinVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(WildGoblinVariant::getId)).toArray(WildGoblinVariant[]::new);

    private final int id;

    WildGoblinVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static WildGoblinVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}

