package net.sevenstars.middleearth.recipe;

import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;
import net.sevenstars.middleearth.MiddleEarth;

/**
 * The quality grade of anything crafted, from a hidden quality value (0-100): gems cut at the lapidary table
 * (component {@link net.sevenstars.middleearth.item.DataComponentTypesME#GEM_QUALITY}), forged rings, and later every
 * other craft, each with its own impacts. For cut gems, each grade above Mundane adds to the inscription charges.
 * <p>
 * Legendary is hidden: it is never advertised, and only a finished ring of a flawless 100 reaches it (see
 * {@link #of(int, boolean)}). Everything else stops at Relic, up to 100.
 */
public enum CraftQuality {
    MUNDANE("mundane", 0, 0.0, TextColor.fromFormatting(Formatting.GRAY), false),
    FINE("fine", 40, 0.2, TextColor.fromFormatting(Formatting.GREEN), false),
    EXCEPTIONAL("exceptional", 60, 0.3, TextColor.fromFormatting(Formatting.AQUA), false),
    MASTERWORK("masterwork", 80, 0.4, TextColor.fromFormatting(Formatting.LIGHT_PURPLE), false),
    RELIC("relic", 92, 0.6, TextColor.fromFormatting(Formatting.GOLD), false),
    LEGENDARY("legendary", 100, 0.6, TextColor.fromRgb(0xFF5A1F), true);

    private final String name;
    private final int minValue;
    private final double chargeBonus;
    private final TextColor color;
    private final boolean hidden;

    CraftQuality(String name, int minValue, double chargeBonus, TextColor color, boolean hidden) {
        this.name = name;
        this.minValue = minValue;
        this.chargeBonus = chargeBonus;
        this.color = color;
        this.hidden = hidden;
    }

    /** The grade of a quality value, never a hidden one. */
    public static CraftQuality of(int value) {
        return of(value, false);
    }

    /** The grade of a quality value; hidden grades only when {@code allowHidden} (a finished ring). */
    public static CraftQuality of(int value, boolean allowHidden) {
        CraftQuality quality = MUNDANE;
        for (CraftQuality level : values()) {
            if (value >= level.minValue && (allowHidden || !level.hidden)) {
                quality = level;
            }
        }
        return quality;
    }

    /** The lowest quality value of this level. */
    public int minValue() {
        return minValue;
    }

    /** The highest quality value of this level; hidden grades don't cut a visible one short. */
    public int maxValue() {
        for (int i = ordinal() + 1; i < values().length; i++) {
            if (!values()[i].hidden) {
                return values()[i].minValue - 1;
            }
        }
        return 100;
    }

    /** Charges of a cut gem of this quality: the tier's base charges plus the bonus, rounded down. */
    public int charges(int baseCharges) {
        return (int) Math.floor(baseCharges * (1.0 + chargeBonus));
    }

    public TextColor color() {
        return color;
    }

    public boolean hidden() {
        return hidden;
    }

    public String translationKey() {
        return "craft_quality." + MiddleEarth.MOD_ID + "." + name;
    }
}
