package net.jesteur.me.entity.elves.galadhrim;

import java.util.Arrays;
import java.util.Comparator;

public enum GaladhrimElfVariant {
    /** [Hair Color]_[Dress Color]_[Eye Color](ID) **/
    SILVER_TEAL_BLUE_WOMAN(0),
    SILVER_CYAN_BLUE_WOMAN(1),
    SILVER_LIME_BLUE_WOMAN(2);

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

