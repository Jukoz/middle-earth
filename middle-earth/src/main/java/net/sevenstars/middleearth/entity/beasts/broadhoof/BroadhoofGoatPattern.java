package net.sevenstars.middleearth.entity.beasts.broadhoof;

import net.minecraft.util.function.ValueLists;
import net.sevenstars.api.dtos.WeightedPool;

import java.util.*;
import java.util.function.IntFunction;
import java.util.stream.Stream;

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
    PALE_STRIPS(15),

    GRAY_BEARD(16);

    private static final List<WeightedBroadhoofGoatPattern> GENERIC_SET = List.of(
            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.NONE, 5),
            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.GRAY_BEARD)
    );

    private static final List<WeightedBroadhoofGoatPattern> BLACK_SET = List.of(
            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BLACK_MASK),
            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BLACK_PATCHES),
            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BLACK_SIDE_PATCH),
            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BLACK_SPOTS),
            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BLACK_STRIPS)
    );

    private static final List<WeightedBroadhoofGoatPattern> BROWN_SET = List.of(
            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BROWN_MASK),
            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BROWN_PATCHES),
            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BROWN_SIDE_PATCH),
            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BROWN_SPOTS),
            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BROWN_STRIPS)
    );

    private static final List<WeightedBroadhoofGoatPattern> PALE_SET = List.of(
            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.PALE_MASK),
            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.PALE_PATCHES),
            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.PALE_SIDE_PATCH),
            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.PALE_SPOTS),
            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.PALE_STRIPS)
    );

    public static final Map<BroadhoofGoatColor, WeightedPool<WeightedBroadhoofGoatPattern>> PATTERN_COMBINATIONS = Map.of(
            BroadhoofGoatColor.WHITE, new WeightedPool<>(Stream.of(GENERIC_SET, BLACK_SET, BROWN_SET)),
            BroadhoofGoatColor.LIGHT_GRAY, new WeightedPool<>(Stream.of(GENERIC_SET, BLACK_SET, BROWN_SET)),
            BroadhoofGoatColor.PALE, new WeightedPool<>(Stream.of(GENERIC_SET, BLACK_SET, BROWN_SET)),
            BroadhoofGoatColor.RED, new WeightedPool<>(Stream.of(GENERIC_SET, BLACK_SET, PALE_SET)),
            BroadhoofGoatColor.BROWN, new WeightedPool<>(Stream.of(GENERIC_SET, BLACK_SET, PALE_SET)),
            BroadhoofGoatColor.GRAY, new WeightedPool<>(Stream.of(GENERIC_SET, BLACK_SET, BROWN_SET, PALE_SET)),
            BroadhoofGoatColor.BLACK, new WeightedPool<>(Stream.of(GENERIC_SET, BROWN_SET, PALE_SET))
    );

    private static final IntFunction<BroadhoofGoatPattern> INDEX_MAPPER = ValueLists.createIndexToValueFunction(
            BroadhoofGoatPattern::getIndex, values(), ValueLists.OutOfBoundsHandling.WRAP
    );
    private final int index;

    BroadhoofGoatPattern(final int index) {
        this.index = index;
    }

    public int getIndex() {
        return this.index;
    }

    public static BroadhoofGoatPattern byIndex(int index) {
        return INDEX_MAPPER.apply(index);
    }
}
