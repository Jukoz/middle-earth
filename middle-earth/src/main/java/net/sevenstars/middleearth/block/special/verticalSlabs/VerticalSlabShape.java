package net.sevenstars.middleearth.block.special.verticalSlabs;

import net.minecraft.util.StringIdentifiable;

public enum VerticalSlabShape implements StringIdentifiable {
    STRAIGHT("straight"),
    INNER_LEFT("inner_left"),
    INNER_RIGHT("inner_right"),
    OUTER_LEFT("outer_left"),
    OUTER_RIGHT("outer_right"),
    ;

    private final String name;

    private VerticalSlabShape(final String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    public String asString() {
        return this.name;
    }
}
