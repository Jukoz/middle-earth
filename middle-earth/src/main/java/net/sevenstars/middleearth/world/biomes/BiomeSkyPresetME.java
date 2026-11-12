package net.sevenstars.middleearth.world.biomes;

/**
 * Sky & fog colours shared by groups of biomes. Every biome takes its sky from one of these,
 * so a region's mood is tuned in one place.
 */
public enum BiomeSkyPresetME {
    /** Open sky of the temperate lowlands */
    CLEAR(0x78A7FF, 0xC0D8FF),
    /** Hills & mountains */
    HIGHLAND(0x7DA2FF, 0xC0D8FF),
    /** Rivers, lakes & seas */
    WATER(0x7BA4FF, 0xC0D8FF),
    /** Harad & Umbar */
    SOUTHERN(0x6EB1FF, 0xC4DFFF),
    /** The Shire & the downs around it */
    GENTLE(0x84ACF5, 0xC0D4F2),
    /** Lindon, the Blue Mountains, Eregion & Rhun */
    LUMINOUS(0x86B0FE, 0xC2DFFF),
    /** Dale */
    DALE(0x7D97FF, 0xB9D2FF),
    /** Frozen waters & Forodwaith */
    FROZEN(0x83A7FF, 0xA1BDE6),
    /** Dunland */
    DUNLAND(0x7290E9, 0xC5D1C6),
    /** Empty lands of Enedwaith & Rhudaur */
    SUBDUED(0x7290E9, 0xA5C1EE),
    /** Shaded vales & the eastern plains */
    SOFT(0x7590E4, 0xBCCDEC),
    /** Emyn Muil & old forests */
    DUSKY(0x6A82C8, 0x9EB8E0),
    /** Angmar, Nurn & the northern wilds */
    COLD_HAZE(0x88A0DD, 0xB3C4DF),
    /** Lands spoiled by Mordor */
    BLIGHTED(0x86A0CA, 0xA8B7C2),
    /** Marshes, troll country & the pools of the wastes under cloud */
    OVERCAST(0x9CA8AB, 0x8D999B),
    /** Caradhras */
    SNOWSTORM(0xB4C0CC, 0xC6D0D8),
    /** Grey Mountains */
    GREY(0xB9B9B9, 0x817770),
    /** Gundabad */
    GUNDABAD(0xACA9B1, 0xA5A5B1),
    /** Dead Marshes */
    DEAD_MARSHES(0xB5B5B3, 0x6C767A),
    /** Lothlorien */
    LORIEN_GOLD(0x78A7FF, 0xD1CA75),
    /** Eaves of Lothlorien */
    LORIEN_EDGE(0x78A7FF, 0xAECA81),
    /** Woodland Realm */
    WOODLAND_REALM(0x86AEFE, 0xBACEC5),
    /** Autumn woods & the Mirkwood peaks */
    WOODLAND_AUTUMN(0x86AEFE, 0x7E8B80),
    /** Eaves of Mirkwood */
    MIRKWOOD_EDGE(0x73A4B0, 0x78827C),
    /** Mirkwood */
    MIRKWOOD(0x6883A6, 0x677468),
    /** Deep Mirkwood & the webbed woods */
    DARK_MIRKWOOD(0x50698C, 0x49584B),
    /** Mirkwood swamps & the shadowed Anduin vales */
    MIRKWOOD_MARSH(0x6A87A0, 0x869CA2),
    /** Dol Guldur */
    DOL_GULDUR(0x383A70, 0x4D4955),
    /** Dagorlad */
    DAGORLAD(0x6E76A0, 0x747581),
    /** Brown Lands & the wastes before Mordor */
    WASTELAND(0x535050, 0x4C4848),
    /** Morgul Vale */
    MORGUL(0x445A52, 0x38513D),
    /** Plains of Mordor */
    ASH(0x292023, 0x1D151B),
    /** Mountains of Mordor & Udun */
    ASH_MOUNTAINS(0x3F3636, 0x2F2727);

    public final int skyColor;
    public final int fogColor;

    BiomeSkyPresetME(int skyColor, int fogColor) {
        this.skyColor = skyColor;
        this.fogColor = fogColor;
    }
}
