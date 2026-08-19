package net.sevenstars.middleearth.item.utils;

import net.minecraft.util.StringRepresentable;

public enum RangedWeaponTypesME implements StringRepresentable {

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

    RangedWeaponTypesME(String name, boolean twoHanded, int durability){
        this.name = name;
        this.twoHanded = twoHanded;
        this.durability = durability;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }
}
