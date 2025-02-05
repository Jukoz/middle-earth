package net.sevenstars.middleearth.utils;

public enum ModSubFactions {
    NONE("generic", null),

    EREBOR("erebor", ModFactions.LONGBEARDS),

    MORDOR_BLACK_NUMENOREANS("mordor_black_numenoreans", ModFactions.MORDOR),

    GUNDABAD("gundabad", ModFactions.MISTY_MOUNTAINS_GOBLINS),

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
