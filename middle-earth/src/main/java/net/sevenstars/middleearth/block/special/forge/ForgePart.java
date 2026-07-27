package net.sevenstars.middleearth.block.special.forge;

import net.minecraft.util.StringRepresentable;

public enum ForgePart implements StringRepresentable {
    BOTTOM("bottom"),
    TOP("top");

    private final String name;

    private ForgePart(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    public String getSerializedName() {
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
