package net.jukoz.me.entity.dwarves.durin;

import java.util.Arrays;
import java.util.Comparator;

public enum DurinDwarfVariant {
    GINGER(0),
    BlACK(1),
    OLD(2),
    DARK_BLONDE(3),
    BLACK_BALD(4),
    GINGER_NORI(5),
    BLACK_NORI(6),
    DARK_BLONDE_NORI(7);

    private static final DurinDwarfVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(DurinDwarfVariant::getId)).toArray(DurinDwarfVariant[]::new);

    private final int id;

    DurinDwarfVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static DurinDwarfVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}

