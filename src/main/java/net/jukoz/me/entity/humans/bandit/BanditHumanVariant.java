package net.jukoz.me.entity.humans.bandit;

import java.util.Arrays;
import java.util.Comparator;

public enum BanditHumanVariant {
    LIGHT_BROWN_RED(0),
    PALE_BLUE_YELLOW(1),
    PALE_GREY_ORANGE(2);

    private static final BanditHumanVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(BanditHumanVariant::getId)).toArray(BanditHumanVariant[]::new);

    private final int id;

    BanditHumanVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static BanditHumanVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}

