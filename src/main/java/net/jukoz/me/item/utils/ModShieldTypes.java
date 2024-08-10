package net.jukoz.me.item.utils;

import net.minecraft.util.StringIdentifiable;

public enum ModShieldTypes implements StringIdentifiable {

    LIGHT_SHIELD("light_shield", 250),
    MEDIUM_SHIELD("medium_shield", 336),
    HEAVY_SHIELD("heavy_shield", 500),
    ;

    public final String name;
    public final int durability;

    ModShieldTypes(String name, int durability){
        this.name = name;
        this.durability = durability;
    }

    @Override
    public String asString() {
        return this.name;
    }
}
