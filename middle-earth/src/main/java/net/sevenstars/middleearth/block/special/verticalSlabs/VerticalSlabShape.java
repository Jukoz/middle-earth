package net.sevenstars.middleearth.block.special.verticalSlabs;

import net.minecraft.util.StringRepresentable;

public enum VerticalSlabShape implements StringRepresentable {
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

    public String getSerializedName() {
        return this.name;
    }
}
