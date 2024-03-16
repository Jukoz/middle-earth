package net.jukoz.me.block.special.artisantable;

import net.minecraft.util.StringIdentifiable;

public enum ArtisanTablePart implements StringIdentifiable {
    LEFT("left"),
    RIGHT("right");

    private final String name;

    private ArtisanTablePart(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    public String asString() {
        return this.name;
    }
}
