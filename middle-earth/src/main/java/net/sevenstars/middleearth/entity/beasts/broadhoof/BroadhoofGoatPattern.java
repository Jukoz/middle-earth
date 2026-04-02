package net.sevenstars.middleearth.entity.beasts.broadhoof;

import java.util.function.IntFunction;
import net.minecraft.util.function.ValueLists;

public enum BroadhoofGoatPattern {
    NONE(0),

    BLACK_MASK(1),
    BLACK_PATCHES(2),
    BLACK_SIDE_PATCH(3),
    BLACK_SPOTS(4),
    BLACK_STRIPS(5),

    BROWN_MASK(6),
    BROWN_PATCHES(7),
    BROWN_SIDE_PATCH(8),
    BROWN_SPOTS(9),
    BROWN_STRIPS(10),

    PALE_MASK(11),
    PALE_PATCHES(12),
    PALE_SIDE_PATCH(13),
    PALE_SPOTS(14),
    PALE_STRIPS(15);

    private static final IntFunction<BroadhoofGoatPattern> INDEX_MAPPER = ValueLists.createIndexToValueFunction(
            BroadhoofGoatPattern::getIndex, values(), ValueLists.OutOfBoundsHandling.WRAP
    );
    private final int index;

    private BroadhoofGoatPattern(final int index) {
        this.index = index;
    }

    public int getIndex() {
        return this.index;
    }

    public static BroadhoofGoatPattern byIndex(int index) {
        return (BroadhoofGoatPattern)INDEX_MAPPER.apply(index);
    }
}
