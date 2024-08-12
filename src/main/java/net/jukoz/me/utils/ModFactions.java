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
    MISTY_MOUNTAINS_ORCS("misty_mountains_orcs"),
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
