package net.jukoz.me.item.utils;

import net.minecraft.util.StringIdentifiable;

public enum ModRangedWeaponTypes implements StringIdentifiable {

    BOW("bow", false),
    LONGBOW("longbow", true),

    CROSSBOW("crossbow", false),
    ;

    public final String name;
    public final Boolean twoHanded;

    ModRangedWeaponTypes(String name, boolean twoHanded ){
        this.name = name;
        this.twoHanded = twoHanded;
    }

    @Override
    public String asString() {
        return this.name;
    }
}
