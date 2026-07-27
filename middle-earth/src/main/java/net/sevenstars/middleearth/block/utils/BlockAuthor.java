package net.sevenstars.middleearth.block.utils;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import java.util.function.IntFunction;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;

public enum BlockAuthor implements StringRepresentable {
    BOENNDAL(0, "Boenndal"),
    SCOSHER(1, "Scosher"),
    COFFEE_VIKING(2, "CoffeeViking"),
    NAUTILUS(3, "Nautilus4000"),
    SINDAVAR(4, "Sindavar"),
    ANGMARZKU(5, "Angmarzku")
    ;

    private static final IntFunction<BlockAuthor> BY_ID = ByIdMap.continuous(BlockAuthor::getId, BlockAuthor.values(), ByIdMap.OutOfBoundsStrategy.ZERO);;
    private final int id;
    private final String authorName;

    public static final Codec<BlockAuthor> CODEC = StringRepresentable.fromValues(BlockAuthor::values);
    public static final StreamCodec<ByteBuf, BlockAuthor> PACKET_CODEC = ByteBufCodecs.idMapper(BY_ID, BlockAuthor::getId);

    BlockAuthor(int id, String authorName) {
        this.id = id;
        this.authorName = authorName;
    }

    public int getId() {
        return id;
    }

    @Override
    public String getSerializedName() {
        return this.name();
    }

    public String getAuthorName() {
        return authorName;
    }
}
