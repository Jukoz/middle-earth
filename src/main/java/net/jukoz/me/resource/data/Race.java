package net.jukoz.me.resource.data;

import net.jukoz.me.MiddleEarth;
import net.minecraft.util.Identifier;

public enum Race {
    Dwarf,
    Elf,
    Human,
    Hobbit,
    Orc;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }

    public String getLangKey() {
        return Identifier.of(MiddleEarth.MOD_ID, "race." + toString()).toTranslationKey();
    }
}
