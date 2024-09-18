package net.jukoz.me.resources.datas;

import net.jukoz.me.MiddleEarth;
import net.minecraft.util.Identifier;

public enum Race {
    DWARF("dwarf"),
    ELF("elf"),
    HUMAN("human"),
    HOBBIT("hobbit"),
    ORC("orc"),
    URUK("uruk");

    private String jsonValue;

    Race(String raceKey) {
        this.jsonValue = raceKey;
    }

    public String getRaceKey() {
        return this.jsonValue;
    }
    public static Race fromString(String text) {
        for (Race race : Race.values()) {
            if (race.jsonValue.equalsIgnoreCase(text)) {
                return race;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }

    public String getLangKey() {
        return Identifier.of(MiddleEarth.MOD_ID, "race." + toString()).toTranslationKey();
    }
}