package net.jukoz.me.utils;

public enum ModFactions {
    NONE("generic", true),

    LONGBEARDS("longbeards", true),

    MORDOR("mordor", true);

    private final String name;
    private final boolean good;
    private final boolean minor;

    ModFactions(String name, boolean good, boolean minor){
        this.name = name;
        this.good = good;
        this.minor = minor;
    }

    ModFactions(String name, boolean good){
        this.name = name;
        this.good = good;
        this.minor = false;
    }
}
