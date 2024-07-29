package net.jukoz.me.utils;

public enum ModSubFactions {
    NONE("generic", null),

    EREBOR("erebor", ModFactions.LONGBEARDS),

    ;

    private final String name;

    private final ModFactions parent;

    ModSubFactions(String name, ModFactions parent){
        this.name = name;
        this.parent = parent;
    }

    public ModFactions getParent() {
        return parent;
    }

    public String getName() {
        return name;
    }
}
