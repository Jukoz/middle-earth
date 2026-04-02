package net.sevenstars.middleearth.entity.beasts.broadhoof;

import net.minecraft.util.function.ValueLists;

import java.util.function.IntFunction;

public enum BroadhoofGoatBeads {
    NONE(0),
    LEATHER(1),
    COAL(2),
    COPPER(3),
    GOLD(4),
    ALMANDINE(5);


    private static final IntFunction<BroadhoofGoatBeads> INDEX_MAPPER = ValueLists.createIndexToValueFunction(
            BroadhoofGoatBeads::getIndex, values(), ValueLists.OutOfBoundsHandling.WRAP
    );
    private final int index;

    private BroadhoofGoatBeads(final int index) {
        this.index = index;
    }

    public int getIndex() {
        return this.index;
    }

    public static BroadhoofGoatBeads byIndex(int index) {
        return (BroadhoofGoatBeads)INDEX_MAPPER.apply(index);
    }
}
