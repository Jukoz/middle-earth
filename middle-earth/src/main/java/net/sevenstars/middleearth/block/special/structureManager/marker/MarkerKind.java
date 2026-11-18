package net.sevenstars.middleearth.block.special.structureManager.marker;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.StringIdentifiable;

public enum MarkerKind implements StringIdentifiable {
    GUARD_POST("guard_post"),
    ENTRANCE("entrance"),
    LOOKOUT("lookout"),
    LORD_SEAT("lord_seat"),
    PATROL_POINT("patrol_point"),
    MINE_FACE("mine_face"),
    SPAWN_NPC("spawn_npc"),
    SPAWN_ANIMAL("spawn_animal"),
    CELL_CORNER("cell_corner"),
    // What it faces is off limits to npcs: doors stay shut, seats and stores aren't features
    OFF_LIMITS("off_limits");

    public static final Codec<MarkerKind> CODEC = StringIdentifiable.createCodec(MarkerKind::values);
    public static final PacketCodec<ByteBuf, MarkerKind> PACKET_CODEC = PacketCodecs.VAR_INT.xmap(index -> values()[Math.clamp(index, 0, values().length - 1)], MarkerKind::ordinal);
    private final String name;

    MarkerKind(String name) {
        this.name = name;
    }

    @Override
    public String asString() {
        return name;
    }
}
