package net.sevenstars.middleearth.item.items.gems;

import net.sevenstars.middleearth.MiddleEarth;

/**
 * Sind's 16 cuts in 5 tiers. Within a tier the cut only changes the look; the tier sets the Inscription Table
 * words and charges.
 */
public enum GemCut {
    ROUND("round", 1),
    SPHERE("sphere", 1),
    OVAL("oval", 1),
    HEXAGON("hexagon", 2),
    OCTAGON("octagon", 2),
    PENTAGON("pentagon", 2),
    RHOMBUS("rhombus", 2),
    PEAR("pear", 3),
    TAPERED("tapered", 3),
    POINTED_LOZENGE("pointed_lozenge", 3),
    EMERALD("emerald", 4),
    ASSCHER("asscher", 4),
    MARQUIS("marquis", 4),
    BRILLIANT("brilliant", 5),
    RADIANT("radiant", 5),
    BRIOLETTE("briolette", 5);

    /** Inscription charges per tier: T1-T5 = 4, 8, 16, 32, 64. */
    private static final int[] CHARGES_BY_TIER = {4, 8, 16, 32, 64};

    private final String name;
    private final int tier;

    GemCut(String name, int tier) {
        this.name = name;
        this.tier = tier;
    }

    public String id() {
        return name;
    }

    public int tier() {
        return tier;
    }

    public int charges() {
        return CHARGES_BY_TIER[tier - 1];
    }

    public String translationKey() {
        return "gem_cut." + MiddleEarth.MOD_ID + "." + name;
    }
}
