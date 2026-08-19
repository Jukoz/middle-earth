package net.sevenstars.middleearth.entity.beasts.broadhoof;

import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import java.util.Map;
import java.util.Set;
import java.util.function.IntFunction;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;

public enum BroadhoofGoatColor implements StringRepresentable {
    WHITE(0, "white"),
    LIGHT_GRAY(1, "light_gray"),
    PALE(2, "pale"),
    RED(3, "red"),
    BROWN(4, "brown"),
    GRAY(5, "gray"),
    BLACK(6, "black");

    public static final Codec<BroadhoofGoatColor> CODEC = StringRepresentable.fromEnum(BroadhoofGoatColor::values);
    private static final IntFunction<BroadhoofGoatColor> INDEX_MAPPER = ByIdMap.continuous(
            BroadhoofGoatColor::getIndex, values(), ByIdMap.OutOfBoundsStrategy.WRAP
    );
    public static final StreamCodec<ByteBuf, BroadhoofGoatColor> PACKET_CODEC = ByteBufCodecs.idMapper(INDEX_MAPPER, BroadhoofGoatColor::getIndex);
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
    public String getSerializedName() {
        return this.id;
    }
}
