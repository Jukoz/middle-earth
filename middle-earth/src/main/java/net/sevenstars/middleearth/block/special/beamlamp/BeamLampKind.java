package net.sevenstars.middleearth.block.special.beamlamp;

import net.minecraft.item.Item;
import net.sevenstars.middleearth.item.ResourceItemsME;

/** The two lapidary beam lamps placed as blocks: range, brightness, colour and lens position. */
public enum BeamLampKind {
    BULLSEYE(24, 12, 0xFFD98A, 4, 3, 4, 6),
    MIRDAIN(36, 15, 0xD6E8FF, 7, 2, 5, 6);

    public static final int GLOW = 8;

    private final int range;
    private final int spotLevel;
    private final int color;

    private final double lensHeight;
    private final double lensOut;
    private final double lensHeightWall;
    private final double lensOutWall;

    BeamLampKind(int range, int spotLevel, int color, double lensHeight, double lensOut, double lensHeightWall, double lensOutWall) {
        this.range = range;
        this.spotLevel = spotLevel;
        this.color = color;
        this.lensHeight = lensHeight;
        this.lensOut = lensOut;
        this.lensHeightWall = lensHeightWall;
        this.lensOutWall = lensOutWall;
    }

    public double lensHeight() {
        return lensHeight;
    }

    public double lensOut() {
        return lensOut;
    }

    public double lensHeightWall() {
        return lensHeightWall;
    }

    public double lensOutWall() {
        return lensOutWall;
    }

    public int range() {
        return range;
    }

    public int spotLevel() {
        return spotLevel;
    }

    public int color() {
        return color;
    }

    public Item item() {
        return this == BULLSEYE ? ResourceItemsME.CITRINE_BULLSEYE_LAMP : ResourceItemsME.MIRDAIN_BEAM_LAMP;
    }
}
