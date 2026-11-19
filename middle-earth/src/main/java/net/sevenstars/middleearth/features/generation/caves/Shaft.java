package net.sevenstars.middleearth.features.generation.caves;

import net.sevenstars.middleearth.features.generation.biomes.caves.DepthBands;
import net.sevenstars.middleearth.features.generation.noise.SimplexNoise;

import static net.sevenstars.middleearth.features.generation.caves.MistyCaves.mix;

// a ravine: a meandering centreline along x or z, closed at both ends, narrowing with depth
public final class Shaft {
    static final int SHAFT_CELL = 256; // multiple of 16, so a chunk sits in one cell
    static final int SHAFT_EDGE = 40; // ravine centres stay this far from their cell border
    static final int SHAFT_CHANCE = 142; // out of 256, about the density of the round shafts before
    static final float SHAFT_MIN_WEIGHT = 0.95f;
    static final int SHAFT_FLOOR_CLEARANCE = 8; // bottom above the deep floor, the taper closes it before that
    static final int SHAFT_FLOOR_SPREAD = 5;
    static final float SHAFT_TOP_FROM = 0.9f; // share of the middle band
    static final int SHAFT_TAPER = 14;
    static final int SHAFT_MIN_DEPTH = 20;
    static final int SHAFT_MAX_REACH = 145; // half the length, the meander and the width

    static final int RAVINE_MIN_LENGTH = 80;
    static final int RAVINE_MAX_LENGTH = 200;
    static final float RAVINE_MIN_HALF = 4;
    static final float RAVINE_MAX_HALF = 9;
    static final float RAVINE_MIN_MEANDER = 6;
    static final float RAVINE_MAX_MEANDER = 10;
    static final float RAVINE_MEANDER_SCALE = 70; // blocks along the length per unit of noise
    static final float RAVINE_WIDTH_SCALE = 50;
    static final float RAVINE_WIDTH_VARY = 0.25f;
    static final float RAVINE_MIN_TAPER = 0.15f; // share of the length that closes each end
    static final float RAVINE_MAX_TAPER = 0.25f;
    static final float RAVINE_NARROWING = 0.35f; // share of the width lost at the bottom
    static final int RAVINE_MIN_DROP = 60;
    static final int RAVINE_MAX_DROP = 120;
    static final int RAVINE_MITHRIL_DIP = 12; // lowest end of an ordinary ravine below the top of the mithril band

    // abysses: rare, huge, from the top of the upper band to the deep floor, on a coarser grid of their own
    static final int ABYSS_CELL = 640; // multiple of 16
    static final int ABYSS_EDGE = 120;
    static final int ABYSS_CHANCE = 110; // out of 256, about one ravine in eight in number
    static final int ABYSS_MAX_REACH = 300; // half the length, the meander and the width
    static final int ABYSS_MIN_LENGTH = 220;
    static final int ABYSS_MAX_LENGTH = 420;
    static final float ABYSS_MIN_HALF = 12;
    static final float ABYSS_MAX_HALF = 22;
    static final float ABYSS_MIN_MEANDER = 12;
    static final float ABYSS_MAX_MEANDER = 20;
    static final float ABYSS_NARROWING = 0.4f;
    static final int ABYSS_ROOF_MIN = 50; // blocks of rock above an abyss, with up to 28 more by the abyss: never closer than 30 to the surface
    static final int ABYSS_ROOF_STEP = 7;
    static final int ABYSS_TOP_TAPER = 24; // a rounded roof
    static final int ABYSS_DEEP_CLEARANCE = 3; // above the deep floor, so the bottom meets its water or rock
    static final int ABYSS_TOP_BOUND = 10000; // the real top is the cap of each column
    static final int LEDGE_PERIOD = 48;
    static final int LEDGE_THICKNESS = 4;
    static final float LEDGE_PINCH = 0.22f; // share of the width a shelf takes

    final int x;
    final int z;
    final boolean alongX;
    final int len0; // blocks of centreline before the centre, and after it
    final int len1;
    final float baseHalf;
    final float amplitude;
    final float meanderBase;
    final float taperLen;
    final float narrowing;
    final int bottomY; // ignored by an abyss
    final int topY;
    final boolean abyss;
    final int spread;
    final float wobble;

    Shaft(DepthBands.Edges edges, int x, int z, int h, boolean alongX, boolean abyss) {
        this.x = x;
        this.z = z;
        this.alongX = alongX;
        this.abyss = abyss;
        int length = abyss ? ABYSS_MIN_LENGTH + (mix(h, 3, 0) & 0xFFFF) % (ABYSS_MAX_LENGTH - ABYSS_MIN_LENGTH + 1)
                : RAVINE_MIN_LENGTH + (mix(h, 3, 0) & 0xFFFF) % (RAVINE_MAX_LENGTH - RAVINE_MIN_LENGTH + 1);
        this.len0 = Math.round(length * (0.4f + 0.2f * (mix(h, 13, 0) & 0xFF) / 255f));
        this.len1 = length - len0;
        float share = (mix(h, 14, 0) & 0xFF) / 255f;
        this.baseHalf = abyss ? ABYSS_MIN_HALF + (ABYSS_MAX_HALF - ABYSS_MIN_HALF) * share : RAVINE_MIN_HALF + (RAVINE_MAX_HALF - RAVINE_MIN_HALF) * share;
        float meander = (mix(h, 15, 0) & 0xFF) / 255f;
        this.amplitude = abyss ? ABYSS_MIN_MEANDER + (ABYSS_MAX_MEANDER - ABYSS_MIN_MEANDER) * meander : RAVINE_MIN_MEANDER + (RAVINE_MAX_MEANDER - RAVINE_MIN_MEANDER) * meander;
        this.taperLen = length * (RAVINE_MIN_TAPER + (RAVINE_MAX_TAPER - RAVINE_MIN_TAPER) * (mix(h, 16, 0) & 0xFF) / 255f);
        this.narrowing = abyss ? ABYSS_NARROWING : RAVINE_NARROWING;
        this.wobble = (mix(h, 6, 0) & 0xFFF);
        this.meanderBase = (float) SimplexNoise.noise(wobble + 17, 0);
        this.spread = (mix(h, 4, 0) & 0xFF) % SHAFT_FLOOR_SPREAD;
        if (abyss) {
            this.topY = ABYSS_TOP_BOUND;
            this.bottomY = DepthBands.DEEP_FLOOR;
        } else {
            this.topY = edges.at(DepthBands.Band.MIDDLE, SHAFT_TOP_FROM + (1 - SHAFT_TOP_FROM) * (mix(h, 5, 0) & 0xFF) / 255f);
            int drop = RAVINE_MIN_DROP + (mix(h, 17, 0) & 0xFFFF) % (RAVINE_MAX_DROP - RAVINE_MIN_DROP + 1);
            this.bottomY = Math.max(topY - drop, edges.middleBottom() - RAVINE_MITHRIL_DIP);
        }
    }

    boolean touches(int startX, int startZ, int size) {
        int pad = Math.round(amplitude + baseHalf * (1 + RAVINE_WIDTH_VARY)) + 2;
        int axis = alongX ? x : z;
        int cross = alongX ? z : x;
        int axisStart = alongX ? startX : startZ;
        int crossStart = alongX ? startZ : startX;
        return axis + len1 >= axisStart && axis - len0 <= axisStart + size - 1 && cross + pad >= crossStart && cross - pad <= crossStart + size - 1;
    }
}
