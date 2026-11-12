package net.sevenstars.middleearth.recipe.lapidary;

import net.minecraft.util.Formatting;
import net.sevenstars.middleearth.MiddleEarth;

/**
 * How well a gem was cut, from its hidden quality value (0-100, component
 * {@link net.sevenstars.middleearth.item.DataComponentTypesME#GEM_QUALITY}). Each level above Mundane adds to the
 * cut gem's inscription charges.
 */
public enum GemQuality {
    MUNDANE("mundane", 0, 0.0, Formatting.GRAY),
    FINE("fine", 40, 0.2, Formatting.GREEN),
    EXCEPTIONAL("exceptional", 60, 0.3, Formatting.AQUA),
    MASTERWORK("masterwork", 80, 0.4, Formatting.LIGHT_PURPLE),
    RELIC("relic", 92, 0.6, Formatting.GOLD);

    private final String name;
    private final int minValue;
    private final double chargeBonus;
    private final Formatting formatting;

    GemQuality(String name, int minValue, double chargeBonus, Formatting formatting) {
        this.name = name;
        this.minValue = minValue;
        this.chargeBonus = chargeBonus;
        this.formatting = formatting;
    }

    public static GemQuality of(int value) {
        GemQuality quality = MUNDANE;
        for (GemQuality level : values()) {
            if (value >= level.minValue) {
                quality = level;
            }
        }
        return quality;
    }

    /** The lowest quality value of this level. */
    public int minValue() {
        return minValue;
    }

    /** The highest quality value of this level. */
    public int maxValue() {
        return ordinal() + 1 < values().length ? values()[ordinal() + 1].minValue - 1 : 100;
    }

    /** Charges of a cut gem of this quality: the tier's base charges plus the bonus, rounded down. */
    public int charges(int baseCharges) {
        return (int) Math.floor(baseCharges * (1.0 + chargeBonus));
    }

    public Formatting formatting() {
        return formatting;
    }

    public String translationKey() {
        return "gem_quality." + MiddleEarth.MOD_ID + "." + name;
    }
}
