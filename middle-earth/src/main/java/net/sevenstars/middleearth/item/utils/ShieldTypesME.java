package net.sevenstars.middleearth.item.utils;

import net.minecraft.util.StringRepresentable;

public enum ShieldTypesME implements StringRepresentable {

    LIGHT_SHIELD("light_shield", 250),
    MEDIUM_SHIELD("medium_shield", 336),
    HEAVY_SHIELD("heavy_shield", 500),
    ;

    public final String name;
    public final int durability;

    ShieldTypesME(String name, int durability){
        this.name = name;
        this.durability = durability;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }
}
