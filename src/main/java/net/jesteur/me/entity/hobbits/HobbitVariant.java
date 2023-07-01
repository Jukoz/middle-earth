package net.jesteur.me.entity.hobbits;

import java.util.Arrays;
import java.util.Comparator;

public enum HobbitVariant {
    /** [Hair Color]_[Dress Color]_[Eye Color](ID) **/
    GINGER_WHITE_BLUE(0),
    DARK_BLONDE_CYAN_BROWN(1),
    GINGER_GREEN_BROWN(2),
    SAM(3),
    FRODO(4);

    private static final HobbitVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(HobbitVariant::getId)).toArray(HobbitVariant[]::new);

    private final int id;

    HobbitVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static HobbitVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}

