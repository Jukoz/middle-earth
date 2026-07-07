package net.sevenstars.middleearth.block.special.forge;

import net.minecraft.util.StringIdentifiable;

public enum ForgePart implements StringIdentifiable {
    BOTTOM("bottom"),
    TOP("top");

    private final String name;

    private ForgePart(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    public String asString() {
        return this.name;
    }

    public ForgePart getOpposite(ForgePart part){
        if (part == BOTTOM){
            return TOP;
        }else {
            return BOTTOM;
        }
    }
}
