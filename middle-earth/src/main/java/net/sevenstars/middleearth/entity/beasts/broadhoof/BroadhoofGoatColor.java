package net.sevenstars.middleearth.entity.beasts.broadhoof;

import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.function.ValueLists;

import java.util.Map;
import java.util.Set;
import java.util.function.IntFunction;

public enum BroadhoofGoatColor implements StringIdentifiable {
    WHITE(0, "white"),
    LIGHT_GRAY(1, "light_gray"),
    PALE(2, "pale"),
    RED(3, "red"),
    BROWN(4, "brown"),
    GRAY(5, "gray"),
    BLACK(6, "black");

    private static final Map<BroadhoofGoatColor, Set<BroadhoofGoatPattern>> PATTERN_COMBINATIONS = Maps.newEnumMap(
            Map.of(
                    WHITE,
                    Set.of(BroadhoofGoatPattern.NONE,
                            BroadhoofGoatPattern.BLACK_MASK, BroadhoofGoatPattern.BLACK_PATCHES, BroadhoofGoatPattern.BLACK_SIDE_PATCH, BroadhoofGoatPattern.BLACK_SPOTS, BroadhoofGoatPattern.BLACK_STRIPS,
                            BroadhoofGoatPattern.BROWN_MASK, BroadhoofGoatPattern.BROWN_PATCHES, BroadhoofGoatPattern.BROWN_SIDE_PATCH, BroadhoofGoatPattern.BROWN_SPOTS, BroadhoofGoatPattern.BROWN_STRIPS),
                    LIGHT_GRAY,
                    Set.of(BroadhoofGoatPattern.NONE,
                            BroadhoofGoatPattern.BLACK_MASK, BroadhoofGoatPattern.BLACK_PATCHES, BroadhoofGoatPattern.BLACK_SIDE_PATCH, BroadhoofGoatPattern.BLACK_SPOTS, BroadhoofGoatPattern.BLACK_STRIPS,
                            BroadhoofGoatPattern.BROWN_MASK, BroadhoofGoatPattern.BROWN_PATCHES, BroadhoofGoatPattern.BROWN_SIDE_PATCH, BroadhoofGoatPattern.BROWN_SPOTS, BroadhoofGoatPattern.BROWN_STRIPS),
                    PALE,
                    Set.of(BroadhoofGoatPattern.NONE,
                            BroadhoofGoatPattern.BLACK_MASK, BroadhoofGoatPattern.BLACK_PATCHES, BroadhoofGoatPattern.BLACK_SIDE_PATCH, BroadhoofGoatPattern.BLACK_SPOTS, BroadhoofGoatPattern.BLACK_STRIPS,
                            BroadhoofGoatPattern.BROWN_MASK, BroadhoofGoatPattern.BROWN_PATCHES, BroadhoofGoatPattern.BROWN_SIDE_PATCH, BroadhoofGoatPattern.BROWN_SPOTS, BroadhoofGoatPattern.BROWN_STRIPS),
                    RED,
                    Set.of(BroadhoofGoatPattern.NONE,
                            BroadhoofGoatPattern.BLACK_MASK, BroadhoofGoatPattern.BLACK_PATCHES, BroadhoofGoatPattern.BLACK_SIDE_PATCH, BroadhoofGoatPattern.BLACK_SPOTS, BroadhoofGoatPattern.BLACK_STRIPS,
                            BroadhoofGoatPattern.PALE_MASK, BroadhoofGoatPattern.PALE_PATCHES, BroadhoofGoatPattern.PALE_SIDE_PATCH, BroadhoofGoatPattern.PALE_SPOTS, BroadhoofGoatPattern.PALE_STRIPS),
                    BROWN,
                    Set.of(BroadhoofGoatPattern.NONE,
                            BroadhoofGoatPattern.BLACK_MASK, BroadhoofGoatPattern.BLACK_PATCHES, BroadhoofGoatPattern.BLACK_SIDE_PATCH, BroadhoofGoatPattern.BLACK_SPOTS, BroadhoofGoatPattern.BLACK_STRIPS,
                            BroadhoofGoatPattern.PALE_MASK, BroadhoofGoatPattern.PALE_PATCHES, BroadhoofGoatPattern.PALE_SIDE_PATCH, BroadhoofGoatPattern.PALE_SPOTS, BroadhoofGoatPattern.PALE_STRIPS),
                    GRAY,
                    Set.of(BroadhoofGoatPattern.NONE,
                            BroadhoofGoatPattern.BLACK_MASK, BroadhoofGoatPattern.BLACK_PATCHES, BroadhoofGoatPattern.BLACK_SIDE_PATCH, BroadhoofGoatPattern.BLACK_SPOTS, BroadhoofGoatPattern.BLACK_STRIPS,
                            BroadhoofGoatPattern.BROWN_MASK, BroadhoofGoatPattern.BROWN_PATCHES, BroadhoofGoatPattern.BROWN_SIDE_PATCH, BroadhoofGoatPattern.BROWN_SPOTS, BroadhoofGoatPattern.BROWN_STRIPS,
                            BroadhoofGoatPattern.PALE_MASK, BroadhoofGoatPattern.PALE_PATCHES, BroadhoofGoatPattern.PALE_SIDE_PATCH, BroadhoofGoatPattern.PALE_SPOTS, BroadhoofGoatPattern.PALE_STRIPS),
                    BLACK,
                    Set.of(BroadhoofGoatPattern.NONE,
                            BroadhoofGoatPattern.BROWN_MASK, BroadhoofGoatPattern.BROWN_PATCHES, BroadhoofGoatPattern.BROWN_SIDE_PATCH, BroadhoofGoatPattern.BROWN_SPOTS, BroadhoofGoatPattern.BROWN_STRIPS,
                            BroadhoofGoatPattern.PALE_MASK, BroadhoofGoatPattern.PALE_PATCHES, BroadhoofGoatPattern.PALE_SIDE_PATCH, BroadhoofGoatPattern.PALE_SPOTS, BroadhoofGoatPattern.PALE_STRIPS)
            )
    );

    public boolean isValidCombination(BroadhoofGoatPattern pattern) {
        Set<BroadhoofGoatPattern> validPatterns = PATTERN_COMBINATIONS.get(this);

        return validPatterns.contains(pattern);
    }

    public static final Codec<BroadhoofGoatColor> CODEC = StringIdentifiable.createCodec(BroadhoofGoatColor::values);
    private static final IntFunction<BroadhoofGoatColor> INDEX_MAPPER = ValueLists.createIndexToValueFunction(
            BroadhoofGoatColor::getIndex, values(), ValueLists.OutOfBoundsHandling.WRAP
    );
    public static final PacketCodec<ByteBuf, BroadhoofGoatColor> PACKET_CODEC = PacketCodecs.indexed(INDEX_MAPPER, BroadhoofGoatColor::getIndex);
    private final int index;
    private final String id;

    private BroadhoofGoatColor(final int index, final String id) {
        this.index = index;
        this.id = id;
    }

    public int getIndex() {
        return this.index;
    }

    public static BroadhoofGoatColor byIndex(int index) {
        return (BroadhoofGoatColor)INDEX_MAPPER.apply(index);
    }

    @Override
    public String asString() {
        return this.id;
    }
}
