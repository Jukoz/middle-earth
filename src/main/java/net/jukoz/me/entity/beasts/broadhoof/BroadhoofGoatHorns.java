package net.jukoz.me.entity.beasts.broadhoof;

import java.util.Arrays;
import java.util.Comparator;

public enum BroadhoofGoatHorns {
    TINY(0),
    NORMAL(1),
    LONG(2),
    CURLY(3),
    SWIRLY(4),
    WIDE(5),
    HUGE(6);

    private static final BroadhoofGoatHorns[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(BroadhoofGoatHorns::getId)).toArray(BroadhoofGoatHorns[]::new);
    private final int id;

    BroadhoofGoatHorns(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static BroadhoofGoatHorns byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
