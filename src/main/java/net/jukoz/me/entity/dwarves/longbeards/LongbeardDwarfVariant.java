package net.jukoz.me.entity.dwarves.longbeards;

import java.util.Arrays;
import java.util.Comparator;

public enum LongbeardDwarfVariant {
    GINGER(0),
    BlACK(1),
    OLD(2),
    DARK_BLONDE(3),
    BLACK_BALD(4),
    GINGER_NORI(5),
    BLACK_NORI(6),
    DARK_BLONDE_NORI(7);

    private static final LongbeardDwarfVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(LongbeardDwarfVariant::getId)).toArray(LongbeardDwarfVariant[]::new);

    private final int id;

    LongbeardDwarfVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static LongbeardDwarfVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}

