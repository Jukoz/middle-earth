package net.sevenstars.middleearth.entity.beasts.broadhoof;

import com.google.common.collect.Maps;
import net.minecraft.util.collection.WeightedList;
import net.minecraft.util.function.ValueLists;
import net.sevenstars.api.dtos.WeightedPool;

import java.util.*;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
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
    PALE_STRIPS(15);

    private static final List<WeightedBroadhoofGoatPattern> NONE_SET = List.of(
            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.NONE, 5)
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
            BroadhoofGoatColor.WHITE,
            new WeightedPool<WeightedBroadhoofGoatPattern>(Stream.of(NONE_SET, BLACK_SET, BROWN_SET)
                    .flatMap(List::stream).toList()),

            BroadhoofGoatColor.LIGHT_GRAY,
            new WeightedPool<WeightedBroadhoofGoatPattern>(Stream.of(NONE_SET, BLACK_SET, BROWN_SET)
                    .flatMap(List::stream).toList()),

            BroadhoofGoatColor.PALE,
            new WeightedPool<WeightedBroadhoofGoatPattern>(Stream.of(NONE_SET, BLACK_SET, BROWN_SET)
                    .flatMap(List::stream).toList()),

            BroadhoofGoatColor.RED,
            new WeightedPool<WeightedBroadhoofGoatPattern>(Stream.of(NONE_SET, BLACK_SET, PALE_SET)
                    .flatMap(List::stream).toList()),

            BroadhoofGoatColor.BROWN,
            new WeightedPool<WeightedBroadhoofGoatPattern>(Stream.of(NONE_SET, BLACK_SET, PALE_SET)
                    .flatMap(List::stream).toList()),

            BroadhoofGoatColor.GRAY,
            new WeightedPool<WeightedBroadhoofGoatPattern>(Stream.of(NONE_SET, BLACK_SET, BROWN_SET, PALE_SET)
                    .flatMap(List::stream).toList()),

            BroadhoofGoatColor.BLACK,
            new WeightedPool<WeightedBroadhoofGoatPattern>(Stream.of(NONE_SET, BROWN_SET, PALE_SET)
                    .flatMap(List::stream).toList())

    );

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
