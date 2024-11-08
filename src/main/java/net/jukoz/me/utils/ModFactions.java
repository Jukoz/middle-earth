package net.jukoz.me.utils;

public enum ModFactions {
    NONE("generic"),

    SHIRE("shire"),
    GONDOR("gondor"),
    ROHAN("rohan"),
    DALE("dale"),
    LOTHLORIEN("lothlorien"),
    LONGBEARDS("longbeards"),

    MORDOR("mordor"),
    MISTY_MOUNTAINS_GOBLINS("misty_mountains_globlins"),
    ISENGARD("isengard"),
    ;

    private final String name;

    ModFactions(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
