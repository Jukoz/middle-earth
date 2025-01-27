package net.sevenstars.middleearth.block.special.artisantable;

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

    public ArtisanTablePart getOpposite(ArtisanTablePart part){
        if (part == LEFT){
            return RIGHT;
        }else {
            return LEFT;
        }
    }
}
