package net.jukoz.me.item.utils;

public enum ModWeaponTypes {

    SWORD("sword", 3, -2.3f, 0.0F, false),
    AXE("axe", 6, -3.1f, 0.0F, false),
    DAGGER("dagger", 2, -0.7f, -1.0F, false),
    SPEAR("spear", 2, -2.5f, 1.0F, false),
    LONGSWORD("longsword", 3, -2.5f, 1.0F, true),
    ;

    public final String name;
    public final float attack;
    public final Float attackSpeed;
    public final Float attackRange;
    public final Boolean twoHanded;

    ModWeaponTypes(String name, float attack, Float attackSpeed, Float attackRange, boolean twoHanded ){
        this.name = name;
        this.attack = attack;
        this.attackSpeed = attackSpeed;
        this.attackRange = attackRange;
        this.twoHanded = twoHanded;
    }
}
