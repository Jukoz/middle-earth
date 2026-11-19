package net.sevenstars.middleearth.features.generation.biomes.caves;

import net.sevenstars.middleearth.features.generation.chunkgen.MiddleEarthChunkGenerator;
import net.sevenstars.middleearth.features.generation.chunkgen.map.MiddleEarthHeightMap;
import net.sevenstars.middleearth.features.generation.map.MiddleEarthMapRuntime;
import net.sevenstars.middleearth.features.generation.noise.BlendedNoise;

public final class DepthBands {
    public static final int SHALLOW_FLOOR = -64;
    public static final int DEEP_FLOOR = -128;
    public static final int BOTTOM_BAND = 4; // the depth of the bottom fluid of the wet deeps as well
    public static final int SHALLOW_WATER = 2;

    public static final int UPPER_DEPTH = 16; // below the surface
    public static final int MIDDLE_DEPTH = 60; // below the surface
    public static final int MITHRIL_TOP = 0;
    public static final int MITHRIL_BOTTOM = -40;

    private static final float FLOOR_WOBBLE = 1.5f;
    private static final double FLOOR_WOBBLE_SCALE = 9;
    private static final long FLOOR_WOBBLE_OFFSET = 4177;

    public enum Band {
        UPPER,
        MIDDLE,
        MITHRIL,
        DEEPS
    }

    // Band.values() clones its array on every call
    public static final Band[] BANDS = Band.values();

    // both ends inclusive
    public record Span(Band band, int bottom, int top) {
        public int height() {
            return top - bottom + 1;
        }

        public int at(float fraction) {
            return bottom + Math.round(fraction * (top - bottom));
        }
    }

    private DepthBands() {
    }

    // The band edges of one column. The lower bands claim the rock first, so a low surface squeezes the upper ones out.
    // The tops are exclusive, and the wobble is the stone layers' own.
    public record Edges(int surface, int floor, int cap, int upperBottom, int middleBottom, int mithrilBottom) {
        public static Edges of(int surface, int floor, float blend) {
            int cap = surface - UPPER_DEPTH;
            int shift = (int) blend;
            int middleBottom = MITHRIL_TOP + shift;
            return new Edges(surface, floor, cap, Math.max(surface - MIDDLE_DEPTH + shift, middleBottom), middleBottom, MITHRIL_BOTTOM + shift);
        }

        public int bottom(Band band) {
            return switch (band) {
                case UPPER -> upperBottom;
                case MIDDLE -> middleBottom;
                case MITHRIL -> mithrilBottom;
                case DEEPS -> floor;
            };
        }

        public int top(Band band) {
            return switch (band) {
                case UPPER -> cap;
                case MIDDLE -> Math.min(upperBottom, cap);
                case MITHRIL -> Math.min(middleBottom, cap);
                case DEEPS -> Math.min(mithrilBottom, cap);
            };
        }

        public int thickness(Band band) {
            return Math.max(0, top(band) - bottom(band));
        }

        // 0 at the bottom of the band and 1 at its top, beyond that outside it
        public float fraction(Band band, float y) {
            return (y - bottom(band)) / Math.max(1, top(band) - bottom(band));
        }

        public int at(Band band, float fraction) {
            return bottom(band) + Math.round(fraction * thickness(band));
        }
    }

    public static Span at(int x, int y, int z, int surface, int floor) {
        return at(y, Edges.of(surface, floor, MiddleEarthChunkGenerator.caveBlendNoise(x, z, MiddleEarthHeightMap.getSeed())));
    }

    // null above the cave line, where the surface biome rules
    public static Span at(int y, Edges edges) {
        if(y >= edges.cap()) return null;
        for(Band band : BANDS) {
            if(y >= edges.bottom(band) || band == Band.DEEPS) return new Span(band, edges.bottom(band), edges.top(band) - 1);
        }
        return null;
    }

    // the band of at(y, edges), without the span; null above the cave line
    public static Band bandAt(int y, Edges edges) {
        if(y >= edges.cap()) return null;
        for(Band band : BANDS) {
            if(y >= edges.bottom(band) || band == Band.DEEPS) return band;
        }
        return null;
    }

    public static int deepFloor(MiddleEarthMapRuntime map, int x, int z, long seed) {
        return floorFor(x, z, map.getDepth(x, z), seed);
    }

    // Whole blocks below -64 by the depth of the map. The wobble breaks up the contour lines of a gentle slope and fades out
    // towards both ends, so a column with no depth stays exactly at the old floor.
    public static int floorFor(int x, int z, float depth, long seed) {
        if(depth <= 0) return SHALLOW_FLOOR;
        float share = Math.min(1, depth / (SHALLOW_FLOOR - DEEP_FLOOR));
        double noise = BlendedNoise.noise((x + seed + FLOOR_WOBBLE_OFFSET) / FLOOR_WOBBLE_SCALE, (z + seed + FLOOR_WOBBLE_OFFSET) / FLOOR_WOBBLE_SCALE);
        float floor = SHALLOW_FLOOR - depth + (float) (2 * noise - 1) * FLOOR_WOBBLE * 4 * share * (1 - share);
        return Math.max(DEEP_FLOOR, Math.min(SHALLOW_FLOOR, Math.round(floor)));
    }

    // Floors of one chunk and the ring of columns around it, for the lava sea
    public static final class ChunkFloors {
        private static final int SIZE = 16 + 2;
        private final int[] floors = new int[SIZE * SIZE];

        private ChunkFloors() {
        }

        // null when no column of the chunk is deep
        public static ChunkFloors forChunk(MiddleEarthMapRuntime map, int startX, int startZ, long seed) {
            ChunkFloors result = new ChunkFloors();
            boolean any = false;
            for(int x = -1; x <= 16; x++) {
                for(int z = -1; z <= 16; z++) {
                    int floor = deepFloor(map, startX + x, startZ + z, seed);
                    result.floors[(x + 1) * SIZE + z + 1] = floor;
                    any |= floor < SHALLOW_FLOOR && x >= 0 && x < 16 && z >= 0 && z < 16;
                }
            }
            return any ? result : null;
        }

        // x and z are local to the chunk and may be -1 or 16
        public int floor(int x, int z) {
            return floors[(x + 1) * SIZE + z + 1];
        }

        // the highest floor of the column and the four around it: where one is higher, its water stands higher too
        public int highestFloor(int x, int z) {
            int highest = Math.max(floor(x, z), floor(x - 1, z));
            highest = Math.max(highest, Math.max(floor(x + 1, z), floor(x, z - 1)));
            return Math.max(highest, floor(x, z + 1));
        }

        // true when a column beside this one has the usual floor, and so the old lava sea at -60: nothing of the deeps may touch it
        public boolean touchesShallow(int x, int z) {
            return floor(x - 1, z) >= SHALLOW_FLOOR || floor(x + 1, z) >= SHALLOW_FLOOR || floor(x, z - 1) >= SHALLOW_FLOOR || floor(x, z + 1) >= SHALLOW_FLOOR;
        }
    }
}
