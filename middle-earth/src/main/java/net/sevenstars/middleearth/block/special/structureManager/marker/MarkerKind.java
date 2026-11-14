package net.sevenstars.middleearth.block.special.structureManager.marker;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringIdentifiable;

// What a builder wants known about a place that no block can say
public enum MarkerKind implements StringIdentifiable {
    GUARD_POST("guard_post"),
    ENTRANCE("entrance"),
    LOOKOUT("lookout"),
    LORD_SEAT("lord_seat"),
    PATROL_POINT("patrol_point"),
    MINE_FACE("mine_face");

    public static final Codec<MarkerKind> CODEC = StringIdentifiable.createCodec(MarkerKind::values);
    private final String name;

    MarkerKind(String name) {
        this.name = name;
    }

    public MarkerKind next() {
        return values()[(ordinal() + 1) % values().length];
    }

    @Override
    public String asString() {
        return name;
    }
}
