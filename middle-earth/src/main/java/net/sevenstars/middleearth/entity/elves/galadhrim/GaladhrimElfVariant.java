package net.sevenstars.middleearth.entity.elves.galadhrim;

import java.util.Arrays;
import java.util.Comparator;

public enum GaladhrimElfVariant {
    /** [Hair Color]_[Dress Color]_[Eye Color](ID) **/
    SILVER_TEAL_BLUE(0),
    SILVER_CYAN_BLUE(1),
    SILVER_LIME_BLUE(2),
    BLOND_GREEN_BLUE(3);

    private static final GaladhrimElfVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(GaladhrimElfVariant::getId)).toArray(GaladhrimElfVariant[]::new);

    private final int id;

    GaladhrimElfVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static GaladhrimElfVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}

