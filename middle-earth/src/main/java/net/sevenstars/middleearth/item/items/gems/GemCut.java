package net.sevenstars.middleearth.item.items.gems;

import net.sevenstars.middleearth.MiddleEarth;

/** The 16 cuts in 5 tiers; the tier sets inscription words and charges, the cut a set ring gem's polarity shift. */
public enum GemCut {
    ROUND("round", 1, 10),
    SPHERE("sphere", 1, 0),
    OVAL("oval", 1, -10),
    HEXAGON("hexagon", 2, 10),
    OCTAGON("octagon", 2, 0),
    PENTAGON("pentagon", 2, 0),
    RHOMBUS("rhombus", 2, -10),
    PEAR("pear", 3, 10),
    TAPERED("tapered", 3, 0),
    POINTED_LOZENGE("pointed_lozenge", 3, -10),
    EMERALD("emerald", 4, 10),
    ASSCHER("asscher", 4, 0),
    MARQUIS("marquis", 4, -10),
    BRILLIANT("brilliant", 5, 10),
    RADIANT("radiant", 5, 0),
    BRIOLETTE("briolette", 5, -10);

    private static final int[] CHARGES_BY_TIER = {4, 8, 16, 32, 64};

    private final String name;
    private final int tier;
    private final int polarity;

    GemCut(String name, int tier, int polarity) {
        this.name = name;
        this.tier = tier;
        this.polarity = polarity;
    }

    public String id() {
        return name;
    }

    public int tier() {
        return tier;
    }

    public int polarity() {
        return polarity;
    }

    public int charges() {
        return CHARGES_BY_TIER[tier - 1];
    }

    public String translationKey() {
        return "gem_cut." + MiddleEarth.MOD_ID + "." + name;
    }
}
