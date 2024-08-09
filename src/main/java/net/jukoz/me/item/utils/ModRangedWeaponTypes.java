package net.jukoz.me.item.utils;

import net.minecraft.util.StringIdentifiable;

public enum ModRangedWeaponTypes implements StringIdentifiable {

    BOW("bow", 4, -2.4f, 0.0F, false),
    ;

    public final String name;
    public final float attack;
    public final Float attackSpeed;
    public final Float attackRange;
    public final Boolean twoHanded;

    ModRangedWeaponTypes(String name, float attack, Float attackSpeed, Float attackRange, boolean twoHanded ){
        this.name = name;
        this.attack = attack;
        this.attackSpeed = attackSpeed;
        this.attackRange = attackRange;
        this.twoHanded = twoHanded;
    }

    @Override
    public String asString() {
        return this.name;
    }
}
