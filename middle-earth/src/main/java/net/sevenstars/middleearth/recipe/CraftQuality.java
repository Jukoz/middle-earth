package net.sevenstars.middleearth.recipe;

import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;
import net.sevenstars.middleearth.MiddleEarth;

/** Quality grades for crafted items; the hidden Legendary grade is only for flawless finished rings. */
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

    public static CraftQuality of(int value) {
        return of(value, false);
    }

    public static CraftQuality of(int value, boolean allowHidden) {
        CraftQuality quality = MUNDANE;
        for (CraftQuality level : values()) {
            if (value >= level.minValue && (allowHidden || !level.hidden)) {
                quality = level;
            }
        }
        return quality;
    }

    public int minValue() {
        return minValue;
    }

    public int maxValue() {
        for (int i = ordinal() + 1; i < values().length; i++) {
            if (!values()[i].hidden) {
                return values()[i].minValue - 1;
            }
        }
        return 100;
    }

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
