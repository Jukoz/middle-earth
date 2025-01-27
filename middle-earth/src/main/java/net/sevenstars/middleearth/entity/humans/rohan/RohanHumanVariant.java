package net.sevenstars.middleearth.entity.humans.rohan;

import java.util.Arrays;
import java.util.Comparator;

public enum RohanHumanVariant {
    LIGHT_BROWN_RED(0),
    PALE_BLUE_YELLOW(1),
    PALE_GREY_ORANGE(2);

    private static final RohanHumanVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(RohanHumanVariant::getId)).toArray(RohanHumanVariant[]::new);

    private final int id;

    RohanHumanVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static RohanHumanVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}

