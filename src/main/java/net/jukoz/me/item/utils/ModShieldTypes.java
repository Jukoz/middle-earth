package net.jukoz.me.item.utils;

import net.minecraft.util.StringIdentifiable;

public enum ModShieldTypes implements StringIdentifiable {

    LIGHT("light", 4),
    MEDIUM("medium", 4),
    HEAVY("heavy", 4),
    ;

    public final String name;
    public final float cooldown;

    ModShieldTypes(String name, float cooldown){
        this.name = name;
        this.cooldown = cooldown;
    }

    @Override
    public String asString() {
        return this.name;
    }
}
