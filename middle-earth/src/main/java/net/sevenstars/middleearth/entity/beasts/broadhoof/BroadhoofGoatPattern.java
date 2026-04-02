package net.sevenstars.middleearth.entity.beasts.broadhoof;

import com.google.common.collect.Maps;
import net.minecraft.util.function.ValueLists;

import java.util.Map;
import java.util.Set;
import java.util.function.IntFunction;

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

    private static Set<WeightedBroadhoofGoatPattern> black_set = Set.of(
            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.NONE),
            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BLACK_MASK),
            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BLACK_PATCHES),
            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BLACK_SIDE_PATCH),
            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BLACK_SPOTS),
            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BLACK_STRIPS)
    );

    private static Set<WeightedBroadhoofGoatPattern> brown_set = Set.of(
            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BROWN_MASK),
            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BROWN_PATCHES),
            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BROWN_SIDE_PATCH),
            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BROWN_SPOTS),
            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BROWN_STRIPS)
    );

    private static Set<WeightedBroadhoofGoatPattern> pale_set = Set.of(
            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.PALE_MASK),
            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.PALE_PATCHES),
            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.PALE_SIDE_PATCH),
            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.PALE_SPOTS),
            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.PALE_STRIPS)
    );

    public static final Map<BroadhoofGoatColor, Set<WeightedBroadhoofGoatPattern>> PATTERN_COMBINATIONS = Maps.newEnumMap(
            Map.of(
                    BroadhoofGoatColor.WHITE,
                    Set.of(
                            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.NONE),
                            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BLACK_MASK),
                            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BLACK_PATCHES),
                            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BLACK_SIDE_PATCH),
                            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BLACK_SPOTS),
                            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BLACK_STRIPS),
                            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BROWN_MASK),
                            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BROWN_PATCHES),
                            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BROWN_SIDE_PATCH),
                            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BROWN_SPOTS),
                            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BROWN_STRIPS)),


                    BroadhoofGoatColor.LIGHT_GRAY,
                    Set.of(
                            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.NONE),
                            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BLACK_MASK),
                            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BLACK_PATCHES),
                            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BLACK_SIDE_PATCH),
                            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BLACK_SPOTS),
                            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BLACK_STRIPS),
                            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BROWN_MASK),
                            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BROWN_PATCHES),
                            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BROWN_SIDE_PATCH),
                            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BROWN_SPOTS),
                            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BROWN_STRIPS)),
                    BroadhoofGoatColor.PALE,
                    Set.of(new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.NONE),
                            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BLACK_MASK),
                            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BLACK_PATCHES),
                            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BLACK_SIDE_PATCH),
                            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BLACK_SPOTS),
                            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BLACK_STRIPS),
                            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BROWN_MASK),
                            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BROWN_PATCHES),
                            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BROWN_SIDE_PATCH),
                            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BROWN_SPOTS),
                            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BROWN_STRIPS)),
                    BroadhoofGoatColor.RED,
                    Set.of(new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.NONE),
                            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BLACK_MASK),
                            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BLACK_PATCHES),
                            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BLACK_SIDE_PATCH),
                            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BLACK_SPOTS),
                            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BLACK_STRIPS),
                            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.PALE_MASK),
                            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.PALE_PATCHES),
                            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.PALE_SIDE_PATCH),
                            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.PALE_SPOTS),
                            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.PALE_STRIPS)),
                    BroadhoofGoatColor.BROWN,
                    Set.of(new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.NONE),
                            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BLACK_MASK),
                            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BLACK_PATCHES),
                            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BLACK_SIDE_PATCH),
                            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BLACK_SPOTS),
                            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BLACK_STRIPS),
                            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.PALE_MASK),
                            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.PALE_PATCHES),
                            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.PALE_SIDE_PATCH),
                            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.PALE_SPOTS),
                            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.PALE_STRIPS)),
                    BroadhoofGoatColor.GRAY,
                    Set.of(new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.NONE),
                            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BLACK_MASK),
                            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BLACK_PATCHES),
                            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BLACK_SIDE_PATCH),
                            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BLACK_SPOTS),
                            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BLACK_STRIPS),
                            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BROWN_MASK),
                            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BROWN_PATCHES),
                            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BROWN_SIDE_PATCH),
                            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BROWN_SPOTS),
                            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BROWN_STRIPS),
                            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.PALE_MASK),
                            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.PALE_PATCHES),
                            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.PALE_SIDE_PATCH),
                            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.PALE_SPOTS),
                            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.PALE_STRIPS)),
                    BroadhoofGoatColor.BLACK,
                    Set.of(new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.NONE),
                            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BROWN_MASK),
                            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BROWN_PATCHES),
                            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BROWN_SIDE_PATCH),
                            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BROWN_SPOTS),
                            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BROWN_STRIPS),
                            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.PALE_MASK),
                            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.PALE_PATCHES),
                            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.PALE_SIDE_PATCH),
                            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.PALE_SPOTS),
                            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.PALE_STRIPS))
            )
    );

    private static final Set<WeightedBroadhoofGoatPattern> BLACK_SET = Set.of(
            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BLACK_MASK),
            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BLACK_PATCHES),
            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BLACK_SIDE_PATCH),
            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BLACK_SPOTS),
            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BLACK_STRIPS)
    );

    private static final Set<WeightedBroadhoofGoatPattern> BROWN_SET = Set.of(
            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BROWN_MASK),
            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BROWN_PATCHES),
            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BROWN_SIDE_PATCH),
            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BROWN_SPOTS),
            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.BROWN_STRIPS)
    );

    private static final Set<WeightedBroadhoofGoatPattern> PALE_SET = Set.of(
            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.PALE_MASK),
            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.PALE_PATCHES),
            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.PALE_SIDE_PATCH),
            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.PALE_SPOTS),
            new WeightedBroadhoofGoatPattern(BroadhoofGoatPattern.PALE_STRIPS)
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
