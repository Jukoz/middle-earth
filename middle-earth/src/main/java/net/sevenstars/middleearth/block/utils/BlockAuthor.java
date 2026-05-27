package net.sevenstars.middleearth.block.utils;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.function.ValueLists;
import java.util.function.IntFunction;

public enum BlockAuthor implements StringIdentifiable {
    BOENNDAL(0, "Boenndal"),
    SCOSHER(1, "Scosher"),
    COFFEE_VIKING(2, "CoffeeViking"),
    NAUTILUS(3, "Nautilus4000")
    ;

    private static final IntFunction<BlockAuthor> BY_ID = ValueLists.createIndexToValueFunction(BlockAuthor::getId, BlockAuthor.values(), ValueLists.OutOfBoundsHandling.ZERO);;
    private final int id;
    private final String authorName;

    public static final Codec<BlockAuthor> CODEC = StringIdentifiable.createBasicCodec(BlockAuthor::values);
    public static final PacketCodec<ByteBuf, BlockAuthor> PACKET_CODEC = PacketCodecs.indexed(BY_ID, BlockAuthor::getId);

    BlockAuthor(int id, String authorName) {
        this.id = id;
        this.authorName = authorName;
    }

    public int getId() {
        return id;
    }

    @Override
    public String asString() {
        return this.name();
    }

    public String getAuthorName() {
        return authorName;
    }
}
