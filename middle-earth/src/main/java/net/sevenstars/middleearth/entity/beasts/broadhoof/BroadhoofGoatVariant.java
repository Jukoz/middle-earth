package net.sevenstars.middleearth.entity.beasts.broadhoof;

import java.util.Arrays;
import java.util.Comparator;

public enum BroadhoofGoatVariant {
    GRAY(0),
    GRAY_BEARD(1),
    GRAY_BEARD_YOUNG(2),
    PATCHED(3),
    RED(4),
    RED_WITH_PATCH(5),
    RED_WITH_SPOTS(6),
    WHITE(7),
    BLACK(8),
    BLACK_MASK(9),
    BROWN(10);

    private static final BroadhoofGoatVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(BroadhoofGoatVariant::getId)).toArray(BroadhoofGoatVariant[]::new);
    private final int id;

    BroadhoofGoatVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static BroadhoofGoatVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}