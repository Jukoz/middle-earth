package net.jukoz.me.item.utils;

import net.minecraft.util.StringIdentifiable;

public enum ModRangedWeaponTypes implements StringIdentifiable {

    BOW("bow", false, 512),
    NOBLE_BOW("bow", false, 768),
    LONGBOW("longbow", true, 384),
    NOBLE_LONGBOW("longbow", true, 512),

    CROSSBOW("crossbow", false, 512),
    NOBLE_CROSSBOW("crossbow", false, 768),
    ;

    public final String name;
    public final Boolean twoHanded;
    public final int durability;

    ModRangedWeaponTypes(String name, boolean twoHanded, int durability){
        this.name = name;
        this.twoHanded = twoHanded;
        this.durability = durability;
    }

    @Override
    public String asString() {
        return this.name;
    }
}
