package net.sevenstars.middleearth.block.special.beamlamp;

import net.minecraft.item.Item;
import net.sevenstars.middleearth.item.ResourceItemsME;

/**
 * The two focused lamps of the lapidary arts, set down: how far their beams reach, how bright and in what colour, and
 * where their lenses are. Carried, they are held lights like any other (data/middle-earth/middle-earth/held_light).
 */
public enum BeamLampKind {
    /** Masterwork: gold, through citrine. */
    BULLSEYE(24, 12, 0xFFD98A, 4, 3, 4, 6),
    /** Relic: mithril, cool blue-white. */
    MIRDAIN(36, 15, 0xD6E8FF, 7, 2, 5, 6);

    /** The light round a placed lamp itself. */
    public static final int GLOW = 8;

    private final int range;
    private final int spotLevel;
    private final int color;

    /** Where the lens sits on the model, in pixels: height above the block's base and distance out from its centre. */
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

    /** The beam's RGB. */
    public int color() {
        return color;
    }

    public Item item() {
        return this == BULLSEYE ? ResourceItemsME.CITRINE_BULLSEYE_LAMP : ResourceItemsME.MIRDAIN_BEAM_LAMP;
    }
}
