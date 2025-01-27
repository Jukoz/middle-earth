package net.sevenstars.middleearth.entity.uruks.misties;

import java.util.Arrays;
import java.util.Comparator;

public enum MistyHobgoblinVariant {
    LIGHT_BROWN_RED(0),
    PALE_BLUE_YELLOW(1),
    PALE_GREY_ORANGE(2);

    private static final MistyHobgoblinVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(MistyHobgoblinVariant::getId)).toArray(MistyHobgoblinVariant[]::new);

    private final int id;

    MistyHobgoblinVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static MistyHobgoblinVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}

