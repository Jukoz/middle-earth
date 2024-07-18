package net.jukoz.me.utils;

public enum ModSubFactions {
    EREBOR("longbeards", ModFactions.LONGBEARDS);

    private final String name;
    private final ModFactions faction;
    private final boolean minor;

    ModSubFactions(String name, ModFactions faction, boolean minor){
        this.name = name;
        this.faction = faction;
        this.minor = minor;
    }

    ModSubFactions(String name, ModFactions faction){
        this.name = name;
        this.faction = faction;
        this.minor = false;
    }
}
