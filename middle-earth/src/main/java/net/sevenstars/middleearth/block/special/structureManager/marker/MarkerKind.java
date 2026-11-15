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
    MINE_FACE("mine_face"),
    SPAWN_NPC("spawn_npc"),
    SPAWN_ANIMAL("spawn_animal"),
    CELL_CORNER("cell_corner"),
    // What it faces is not for npcs: a door, gate or hatch stays shut, and a seat, a store, a table or a place of work is
    // left out of what the build is taken to have
    OFF_LIMITS("off_limits");

    public static final Codec<MarkerKind> CODEC = StringIdentifiable.createCodec(MarkerKind::values);
    private final String name;

    MarkerKind(String name) {
        this.name = name;
    }

    @Override
    public String asString() {
        return name;
    }
}
