package net.sevenstars.middleearth.resources.datas.rings;

import net.minecraft.text.TextColor;
import net.minecraft.util.math.MathHelper;
import net.sevenstars.middleearth.MiddleEarth;

/**
 * The nine bands of a ring's polarity, from Hallowed (+100) to Twisted (-100). Polarity is set by the catalysts
 * used in refining, the theme it is shaped in and the cut of its gem; what each inscription word does depends on
 * the band the ring sits in.
 */
public enum RingPolarity {
    HALLOWED("hallowed", 76, 100, 0xFFF4C2),
    PRESERVING("preserving", 51, 75, 0xE6E9A6),
    STEADFAST("steadfast", 26, 50, 0xBFE3B4),
    UNCLOUDED("unclouded", 6, 25, 0xB4D7E3),
    UNBOUND("unbound", -5, 5, 0xC8C8C8),
    WEARY("weary", -25, -6, 0xB4A6C8),
    ENSNARING("ensnaring", -50, -26, 0x9C7AB4),
    WRAITH_BINDING("wraith_binding", -75, -51, 0x8A4E8A),
    DOOM_WROUGHT("doom_wrought", -100, -76, 0xA0282C);

    public static final int MIN = -100;
    public static final int MAX = 100;

    private final String name;
    private final int min;
    private final int max;
    private final TextColor color;

    RingPolarity(String name, int min, int max, int color) {
        this.name = name;
        this.min = min;
        this.max = max;
        this.color = TextColor.fromRgb(color);
    }

    /** The band a polarity value falls in, clamped to -100..100. */
    public static RingPolarity of(int polarity) {
        int value = MathHelper.clamp(polarity, MIN, MAX);
        for (RingPolarity band : values()) {
            if (value >= band.min && value <= band.max) {
                return band;
            }
        }
        return UNBOUND;
    }

    public String id() {
        return name;
    }

    public int min() {
        return min;
    }

    public int max() {
        return max;
    }

    public TextColor color() {
        return color;
    }

    public String translationKey() {
        return "ring_polarity." + MiddleEarth.MOD_ID + "." + name;
    }
}
