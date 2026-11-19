package net.sevenstars.middleearth.features.generation.caves;

import net.minecraft.util.math.MathHelper;
import net.sevenstars.middleearth.features.generation.noise.SimplexNoise;
import net.sevenstars.middleearth.features.generation.biomes.BiomeGenerationData;
import net.sevenstars.middleearth.features.generation.biomes.caves.CaveType;
import net.sevenstars.middleearth.features.generation.biomes.caves.DeepThemes;
import net.sevenstars.middleearth.features.generation.biomes.caves.DepthBands;
import net.sevenstars.middleearth.features.generation.biomes.surface.MapBasedCustomBiome;
import net.sevenstars.middleearth.features.generation.chunkgen.MiddleEarthChunkGenerator;
import net.sevenstars.middleearth.features.generation.chunkgen.map.MiddleEarthHeightMap;
import net.sevenstars.middleearth.features.generation.map.MiddleEarthMapRuntime;

public final class MistyCaves {
    // chain runs north-south, so rifts are stretched along Z
    private static final float RIFT_X = 16;
    private static final float RIFT_Y = 40;
    private static final float RIFT_Z = 80;
    private static final float RIFT_HALF_WIDTH = 0.16f;
    // shares of the upper band's thickness: the rifts fade in from above its bottom and are fully open a little way into it
    private static final float RIFT_TAPER = 0.15f;
    private static final float RIFT_FULL = 0.1f;
    private static final int RIFT_MIN_THICKNESS = 8;

    private static final int CHAMBER_CLEARANCE = DepthBands.BOTTOM_BAND + 3; // above the deep floor
    private static final int CHAMBER_TOP_FADE = 6;
    private static final int WORM_CELL_XZ = 64;
    private static final int WORM_CELL_Y = 48;
    private static final int WORMS_PER_CELL = 5;
    private static final int WORM_HALF = 45; // half the length of a worm
    private static final double WORM_PITCH = 0.45; // radians, so they run mostly level
    private static final float WORM_MIN_RADIUS = 2.4f; // 5 to 7 blocks across, a little less where a wave is steep
    private static final float WORM_MAX_RADIUS = 3.6f;
    private static final float WORM_WAVE = 9;
    private static final float WORM_WAVELENGTH = 55;

    private static final int MOUTH_MIN_SURFACE_Y = 110;
    private static final float MOUTH_MIN_WEIGHT = 0.95f;
    private static final float MOUTH_GATE = 0.5f;

    // fade distances for the weight, in blocks
    private static final int WEIGHT_INNER_RADIUS = 24;
    private static final int WEIGHT_OUTER_RADIUS = 48;
    private static final int WEIGHT_OUTER_DIAGONAL = 34;

    private static final int SHAFT_CELL = 256; // multiple of 16, so a chunk sits in one cell
    private static final int SHAFT_EDGE = 40; // ravine centres stay this far from their cell border
    private static final int SHAFT_CHANCE = 142; // out of 256, about the density of the round shafts before
    private static final float SHAFT_MIN_WEIGHT = 0.95f;
    private static final int SHAFT_FLOOR_CLEARANCE = 8; // bottom above the deep floor, the taper closes it before that
    private static final int SHAFT_FLOOR_SPREAD = 5;
    private static final float SHAFT_TOP_FROM = 0.9f; // share of the middle band
    private static final int SHAFT_TAPER = 14;
    private static final int SHAFT_MIN_DEPTH = 20;
    private static final int SHAFT_MAX_REACH = 145; // half the length, the meander and the width

    private static final int RAVINE_MIN_LENGTH = 80;
    private static final int RAVINE_MAX_LENGTH = 200;
    private static final float RAVINE_MIN_HALF = 4;
    private static final float RAVINE_MAX_HALF = 9;
    private static final float RAVINE_MIN_MEANDER = 6;
    private static final float RAVINE_MAX_MEANDER = 10;
    private static final float RAVINE_MEANDER_SCALE = 70; // blocks along the length per unit of noise
    private static final float RAVINE_WIDTH_SCALE = 50;
    private static final float RAVINE_WIDTH_VARY = 0.25f;
    private static final float RAVINE_MIN_TAPER = 0.15f; // share of the length that closes each end
    private static final float RAVINE_MAX_TAPER = 0.25f;
    private static final float RAVINE_NARROWING = 0.35f; // share of the width lost at the bottom
    private static final int RAVINE_MIN_DROP = 60;
    private static final int RAVINE_MAX_DROP = 120;
    private static final int RAVINE_MITHRIL_DIP = 12; // lowest end of an ordinary ravine below the top of the mithril band

    // abysses: rare, huge, from the top of the upper band to the deep floor, on a coarser grid of their own
    private static final int ABYSS_CELL = 640; // multiple of 16
    private static final int ABYSS_EDGE = 120;
    private static final int ABYSS_CHANCE = 110; // out of 256, about one ravine in eight in number
    private static final int ABYSS_MAX_REACH = 300; // half the length, the meander and the width
    private static final int ABYSS_MIN_LENGTH = 220;
    private static final int ABYSS_MAX_LENGTH = 420;
    private static final float ABYSS_MIN_HALF = 12;
    private static final float ABYSS_MAX_HALF = 22;
    private static final float ABYSS_MIN_MEANDER = 12;
    private static final float ABYSS_MAX_MEANDER = 20;
    private static final float ABYSS_NARROWING = 0.4f;
    private static final int ABYSS_ROOF_MIN = 50; // blocks of rock above an abyss, with up to 28 more by the abyss: never closer than 30 to the surface
    private static final int ABYSS_ROOF_STEP = 7;
    private static final int ABYSS_TOP_TAPER = 24; // a rounded roof
    private static final int ABYSS_DEEP_CLEARANCE = 3; // above the deep floor, so the bottom meets its water or rock
    private static final int ABYSS_TOP_BOUND = 10000; // the real top is the cap of each column
    private static final int LEDGE_PERIOD = 48;
    private static final int LEDGE_THICKNESS = 4;
    private static final float LEDGE_PINCH = 0.22f; // share of the width a shelf takes


    private static final int STREAM_MIN_THICKNESS = 24; // of the middle band
    private static final int STREAM_LEVEL_THICKNESS = 110; // one more level for every this many blocks of band, up to three
    private static final float STREAM_WINDOW = 0.8f; // share of a level's slot the tunnels use
    private static final int STREAM_WINDOW_MAX = 40;
    private static final int STREAM_EDGE = 8;
    private static final float STREAM_SLOPE = 0.1f; // blocks of drop per block towards +z
    private static final float STREAM_WIDTH_A = 0.09f;
    private static final float STREAM_WIDTH_B = 0.17f;

    private static final int LAKE_CELL = 512; // multiple of 16, so a chunk sits in one cell
    private static final int LAKE_EDGE = 114; // widest basin plus shell, so lakes never meet
    private static final int LAKE_CHANCE = 100; // out of 256
    private static final float LAKE_MIN_WEIGHT = 0.95f;
    private static final float LAKE_RIM_WEIGHT = 0.5f;
    private static final float LAKE_WOBBLE = 0.32f;
    private static final float LAKE_DOME_WOBBLE = 0.25f;
    private static final int LAKE_SHELL = 5;
    private static final int LAKE_ABYSS_GAP = 12; // blocks of rock kept between a lake and an abyss
    private static final int LAKE_WATER_MIN_Y = -95;
    private static final int LAKE_WATER_MAX_Y = -66;
    private static final int LAKE_FLOOR_CLEARANCE = 6; // shell bottom above the deep floor
    private static final int LAKE_TUNNEL_MAX_Y = 45;
    private static final int LAKE_TUNNEL_LENGTH = 130;
    private static final float LAKE_TUNNEL_SLOPE = 0.8f; // steep enough to clear the rising floor at the edge of the deep region
    private static final float LAKE_TUNNEL_RADIUS = 3.2f;
    private static final int LAKE_SURFACE_CLEARANCE = 40;
    private static final int LAKE_REACH = 256; // tunnel included

    public static final int LAKE_NONE = 0;
    public static final int LAKE_AIR = 1;
    public static final int LAKE_WATER = 2;
    public static final int LAKE_STONE = 3;

    public static final int VALUES = 4;
    public static final int RIFT = 0;
    public static final int CHAMBER = 1;
    public static final int SHAFT = 2;
    public static final int STREAM = 3;

    // a ravine: a meandering centreline along x or z, closed at both ends, narrowing with depth
    public static final class Shaft {
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

        private Shaft(DepthBands.Edges edges, int x, int z, int h, boolean alongX, boolean abyss) {
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

        private boolean touches(int startX, int startZ, int size) {
            int pad = Math.round(amplitude + baseHalf * (1 + RAVINE_WIDTH_VARY)) + 2;
            int axis = alongX ? x : z;
            int cross = alongX ? z : x;
            int axisStart = alongX ? startX : startZ;
            int crossStart = alongX ? startZ : startX;
            return axis + len1 >= axisStart && axis - len0 <= axisStart + size - 1 && cross + pad >= crossStart && cross - pad <= crossStart + size - 1;
        }
    }

    public static final class Lake {
        final int x;
        final int z;
        final float rx;
        final float rz;
        final int waterTop; // highest water block
        final int depth;
        final int height;
        final float wobble;
        final int islands;
        final float[] islandX = new float[2];
        final float[] islandZ = new float[2];
        final float[] islandRadius = new float[2];
        final int pillars;
        final float[] pillarX = new float[8];
        final float[] pillarZ = new float[8];
        final float[] pillarRadius = new float[8];
        final float[] pillarPhase = new float[8];
        final float tunnelX;
        final float tunnelY;
        final float tunnelZ;
        final float tunnelDx;
        final float tunnelDz;

        private Lake(MistyCaves caves, int x, int z, int h) {
            this.x = x;
            this.z = z;
            this.rx = 40 + (mix(h, 21, 0) & 0xFF) / 255f * 40;
            this.rz = 40 + (mix(h, 22, 0) & 0xFF) / 255f * 40;
            this.depth = 6 + (mix(h, 24, 0) & 0xFF) % 7;
            this.waterTop = LAKE_WATER_MIN_Y + (mix(h, 23, 0) & 0xFFFF) % (LAKE_WATER_MAX_Y - LAKE_WATER_MIN_Y + 1);
            this.height = 14 + (mix(h, 25, 0) & 0xFF) % 13;
            this.wobble = (mix(h, 26, 0) & 0xFFF);
            float minRadius = Math.min(rx, rz);
            int islandRoll = (mix(h, 27, 0) & 0xFF) % 8;
            this.islands = islandRoll < 3 ? 0 : islandRoll < 6 ? 1 : 2;
            for (int i = 0; i < islands; i++) {
                double angle = (mix(h, 28, i) & 0xFFFF) / 65536.0 * Math.PI * 2;
                float rho = 0.25f + 0.35f * (mix(h, 29, i) & 0xFF) / 255f;
                islandX[i] = x + (float) Math.cos(angle) * rho * rx;
                islandZ[i] = z + (float) Math.sin(angle) * rho * rz;
                islandRadius[i] = 4 + (mix(h, 30, i) & 0xFF) / 255f * 6;
            }
            this.pillars = 3 + (mix(h, 40, 0) & 0xFF) % 6;
            for (int i = 0; i < pillars; i++) {
                double angle = (mix(h, 41, i) & 0xFFFF) / 65536.0 * Math.PI * 2;
                float rho = 0.1f + 0.55f * (mix(h, 42, i) & 0xFF) / 255f;
                pillarX[i] = x + (float) Math.cos(angle) * rho * rx;
                pillarZ[i] = z + (float) Math.sin(angle) * rho * rz;
                pillarRadius[i] = 2 + (mix(h, 43, i) & 0xFF) / 255f * 3;
                pillarPhase[i] = (mix(h, 44, i) & 0xFF) / 255f * 6.28f;
            }
            double angle = (mix(h, 31, 0) & 0xFFFF) / 65536.0 * Math.PI * 2;
            this.tunnelDx = (float) Math.cos(angle);
            this.tunnelDz = (float) Math.sin(angle);
            // starts a little inside the rim, at the water line, so it opens onto the shore
            float rim = 1 / (float) Math.sqrt(sq(tunnelDx / rx) + sq(tunnelDz / rz));
            float start = rim * caves.outline(wobble, x + tunnelDx * rim, z + tunnelDz * rim) - 6;
            this.tunnelX = x + tunnelDx * start;
            this.tunnelZ = z + tunnelDz * start;
            this.tunnelY = waterTop + 3.5f;
        }

        float ceilingMax() {
            return height * (1 + LAKE_DOME_WOBBLE);
        }

        int lowestY() {
            return waterTop - depth - LAKE_SHELL;
        }

        private boolean touches(int startX, int startZ) {
            int reach = (int) (Math.max(rx, rz) * (1 + LAKE_WOBBLE)) + LAKE_SHELL + 1;
            if (x + reach >= startX && x - reach <= startX + 15 && z + reach >= startZ && z - reach <= startZ + 15) return true;
            float cx = startX + 8 - tunnelX;
            float cz = startZ + 8 - tunnelZ;
            float t = MathHelper.clamp(cx * tunnelDx + cz * tunnelDz, 0, LAKE_TUNNEL_LENGTH);
            float pad = LAKE_TUNNEL_RADIUS + 13; // half a chunk diagonal
            return sq(cx - t * tunnelDx) + sq(cz - t * tunnelDz) <= pad * pad;
        }
    }

    private static float sq(float v) {
        return v * v;
    }

    private static final Lake[] NO_LAKES = new Lake[0];

    private static final Shaft[] NO_SHAFTS = new Shaft[0];

    final int offsetX;
    final int offsetY;
    final int offsetZ;
    final long seed;

    public MistyCaves(long seed) {
        this.seed = seed;
        int hash = Long.hashCode(seed);
        this.offsetX = Math.floorMod(hash, 4096);
        this.offsetY = Math.floorMod(hash * 31 + 7919, 4096);
        this.offsetZ = Math.floorMod(hash * 131 + 104729, 4096);
    }

    // 0 outside the Misty region, 1 further than the outer radius inside it
    public static float weight(MiddleEarthMapRuntime map, int x, int z) {
        int misty = isMisty(map, x, z) ? 1 : 0;
        int inner = WEIGHT_INNER_RADIUS;
        misty += isMisty(map, x + inner, z) ? 1 : 0;
        misty += isMisty(map, x - inner, z) ? 1 : 0;
        misty += isMisty(map, x, z + inner) ? 1 : 0;
        misty += isMisty(map, x, z - inner) ? 1 : 0;
        int outer = WEIGHT_OUTER_RADIUS;
        int diagonal = WEIGHT_OUTER_DIAGONAL;
        misty += isMisty(map, x + outer, z) ? 1 : 0;
        misty += isMisty(map, x - outer, z) ? 1 : 0;
        misty += isMisty(map, x, z + outer) ? 1 : 0;
        misty += isMisty(map, x, z - outer) ? 1 : 0;
        misty += isMisty(map, x + diagonal, z + diagonal) ? 1 : 0;
        misty += isMisty(map, x + diagonal, z - diagonal) ? 1 : 0;
        misty += isMisty(map, x - diagonal, z + diagonal) ? 1 : 0;
        misty += isMisty(map, x - diagonal, z - diagonal) ? 1 : 0;
        // a straight edge gives half the points, so the fade starts there
        return smoothstep(0.5f, 1f, misty / 13f);
    }

    private static boolean isMisty(MiddleEarthMapRuntime map, int x, int z) {
        MapBasedCustomBiome biome = map.getBiome(x, z);
        return biome != null && biome.getBiome() != null && biome.getBiome().getCaveType() == CaveType.MISTIES;
    }

    // clusters of rifts: 0 where there are none
    public float riftCluster(int x, int z) {
        float gate = (float) SimplexNoise.noise((x + offsetX) / 240f, (z + offsetZ) / 400f);
        return smoothstep(-0.1f, 0.35f, gate);
    }

    // open where positive; scale is weight * cluster
    public float riftMargin(int x, int y, int z, float scale, DepthBands.Edges edges) {
        float n = (float) SimplexNoise.noise((x + offsetX) / RIFT_X, (y + offsetY) / RIFT_Y, (z + offsetZ) / RIFT_Z);
        n += 0.25f * (float) SimplexNoise.noise((x + offsetX) / (RIFT_X * 0.5f), (y + offsetY) / (RIFT_Y * 0.5f), (z + offsetZ) / (RIFT_Z * 0.5f));
        n /= 1.25f;
        return RIFT_HALF_WIDTH * scale * riftEnvelope(edges, y) - Math.abs(n);
    }

    // the lowest y a rift can open at in a column, or the cap when it has none
    public static int riftBottom(DepthBands.Edges edges) {
        int thickness = edges.thickness(DepthBands.Band.UPPER);
        if (thickness < RIFT_MIN_THICKNESS) return edges.cap();
        return edges.upperBottom() - (int) (RIFT_TAPER * thickness);
    }

    private static float riftEnvelope(DepthBands.Edges edges, int y) {
        int thickness = edges.thickness(DepthBands.Band.UPPER);
        if (thickness < RIFT_MIN_THICKNESS) return 0;
        return smoothstep(edges.upperBottom() - RIFT_TAPER * thickness, edges.upperBottom() + RIFT_FULL * thickness, y);
    }

    // tall stretches the chambers upwards, for the grotto
    public float chamberMargin(int x, int y, int z, float weight, DepthBands.Edges edges, boolean tall) {
        int top = edges.top(DepthBands.Band.DEEPS);
        float depth = MathHelper.clamp((top - y) / (float) Math.max(1, top - edges.floor()), 0f, 1f);
        float stretch = tall ? 1.9f : 1f;
        float n = 0.7f * (float) SimplexNoise.noise((x + offsetX + 311) / 28f, (y + offsetY) / (18f * stretch), (z + offsetZ + 577) / 28f);
        n += 0.3f * (float) SimplexNoise.noise((x + offsetX + 311) / 14f, (y + offsetY) / (9f * stretch), (z + offsetZ + 577) / 14f);
        // wider and commoner the deeper it gets
        float threshold = (tall ? 0.5f : 0.6f) - 0.25f * depth + (1 - weight) * 0.6f;
        float floorFade = MathHelper.clamp((y - (edges.floor() + CHAMBER_CLEARANCE)) / 4f, 0f, 1f);
        float topFade = MathHelper.clamp((top - y) / (float) CHAMBER_TOP_FADE, 0f, 1f);
        return n - threshold - (1 - floorFade) * 2f - (1 - topFade) * 2f;
    }

    // One worm-bored tunnel of the gnawed deeps: a straight axis with two slow waves across it, a round bore around that
    public static final class Worm {
        final float cx, cy, cz; // middle of the axis
        final float dx, dy, dz; // along it
        final float ux, uy, uz; // the two directions across it
        final float vx, vy, vz;
        final float radius;
        final float a1, f1, p1;
        final float a2, f2, p2;
        final float reach;

        private Worm(float cx, float cy, float cz, int h) {
            this.cx = cx;
            this.cy = cy;
            this.cz = cz;
            double yaw = (mix(h, 1, 0) & 0xFFFF) / 65536.0 * Math.PI * 2;
            double pitch = ((mix(h, 2, 0) & 0xFFFF) / 32768.0 - 1) * WORM_PITCH;
            dx = (float) (Math.cos(pitch) * Math.cos(yaw));
            dy = (float) Math.sin(pitch);
            dz = (float) (Math.cos(pitch) * Math.sin(yaw));
            ux = (float) -Math.sin(yaw);
            uy = 0;
            uz = (float) Math.cos(yaw);
            vx = (float) (-Math.sin(pitch) * Math.cos(yaw));
            vy = (float) Math.cos(pitch);
            vz = (float) (-Math.sin(pitch) * Math.sin(yaw));
            radius = WORM_MIN_RADIUS + (mix(h, 3, 0) & 0xFF) / 255f * (WORM_MAX_RADIUS - WORM_MIN_RADIUS);
            a1 = WORM_WAVE * (0.4f + 0.6f * (mix(h, 4, 0) & 0xFF) / 255f);
            f1 = (float) (Math.PI * 2 / (WORM_WAVELENGTH * (0.7 + 0.6 * (mix(h, 5, 0) & 0xFF) / 255f)));
            p1 = (mix(h, 6, 0) & 0xFF) / 255f * 6.28f;
            a2 = WORM_WAVE * (0.4f + 0.6f * (mix(h, 7, 0) & 0xFF) / 255f);
            f2 = (float) (Math.PI * 2 / (WORM_WAVELENGTH * (0.7 + 0.6 * (mix(h, 8, 0) & 0xFF) / 255f)));
            p2 = (mix(h, 9, 0) & 0xFF) / 255f * 6.28f;
            reach = WORM_HALF + a1 + a2 + radius;
        }
    }

    // worms of the cells around a chunk, for the deeps only
    public Worm[] wormsNear(int startX, int startZ) {
        int minX = Math.floorDiv(startX - WORM_HALF, WORM_CELL_XZ);
        int maxX = Math.floorDiv(startX + 15 + WORM_HALF, WORM_CELL_XZ);
        int minZ = Math.floorDiv(startZ - WORM_HALF, WORM_CELL_XZ);
        int maxZ = Math.floorDiv(startZ + 15 + WORM_HALF, WORM_CELL_XZ);
        int minY = Math.floorDiv(DepthBands.DEEP_FLOOR, WORM_CELL_Y);
        int maxY = Math.floorDiv(DepthBands.MITHRIL_BOTTOM, WORM_CELL_Y);
        Worm[] worms = new Worm[(maxX - minX + 1) * (maxZ - minZ + 1) * (maxY - minY + 1) * WORMS_PER_CELL];
        int count = 0;
        for (int cellX = minX; cellX <= maxX; cellX++) {
            for (int cellY = minY; cellY <= maxY; cellY++) {
                for (int cellZ = minZ; cellZ <= maxZ; cellZ++) {
                    for (int i = 0; i < WORMS_PER_CELL; i++) {
                        int h = mix(cellX + offsetX, cellY + offsetY, cellZ + offsetZ + 7919 * (i + 1));
                        worms[count++] = new Worm(cellX * WORM_CELL_XZ + (mix(h, 10, 0) & 0xFFFF) / 65536f * WORM_CELL_XZ,
                                cellY * WORM_CELL_Y + (mix(h, 11, 0) & 0xFFFF) / 65536f * WORM_CELL_Y,
                                cellZ * WORM_CELL_XZ + (mix(h, 12, 0) & 0xFFFF) / 65536f * WORM_CELL_XZ, h);
                    }
                }
            }
        }
        return worms;
    }

    // Open where positive: the radius of the nearest bore less the distance to it, so the tunnels are round with
    // rounded ends and no thin sheets, and closed under the floor of the deeps and below the top of the band
    public static float wormMargin(Worm[] worms, int x, int y, int z, float weight, DepthBands.Edges edges) {
        float best = -WORM_MAX_RADIUS;
        for (Worm worm : worms) {
            float px = x - worm.cx;
            float py = y - worm.cy;
            float pz = z - worm.cz;
            float away = px * px + py * py + pz * pz;
            if (away > worm.reach * worm.reach) continue;
            float t = MathHelper.clamp(px * worm.dx + py * worm.dy + pz * worm.dz, -WORM_HALF, WORM_HALF);
            float wave1 = worm.a1 * MathHelper.sin(worm.f1 * t + worm.p1);
            float wave2 = worm.a2 * MathHelper.sin(worm.f2 * t + worm.p2);
            float rx = px - t * worm.dx - wave1 * worm.ux - wave2 * worm.vx;
            float ry = py - t * worm.dy - wave1 * worm.uy - wave2 * worm.vy;
            float rz = pz - t * worm.dz - wave1 * worm.uz - wave2 * worm.vz;
            best = Math.max(best, worm.radius - (float) Math.sqrt(rx * rx + ry * ry + rz * rz));
        }
        int top = edges.top(DepthBands.Band.DEEPS);
        float floorFade = MathHelper.clamp((y - (edges.floor() + CHAMBER_CLEARANCE - 2)) / 3f, 0f, 1f);
        float topFade = MathHelper.clamp((top - y) / (float) CHAMBER_TOP_FADE, 0f, 1f);
        return best - (1 - floorFade) * 4f - (1 - topFade) * 4f - (1 - weight) * 4f;
    }

    public static int chamberBottom(DepthBands.Edges edges) {
        return edges.floor() + CHAMBER_CLEARANCE - 4;
    }

    // ravines and abysses whose reach touches the chunk, or an empty array
    public Shaft[] shaftsNear(MiddleEarthMapRuntime map, int startX, int startZ) {
        Shaft[] found = NO_SHAFTS;
        for (boolean abyss : new boolean[]{false, true}) {
            int cell = abyss ? ABYSS_CELL : SHAFT_CELL;
            int reach = abyss ? ABYSS_MAX_REACH : SHAFT_MAX_REACH;
            int minCellX = Math.floorDiv(startX - reach, cell);
            int maxCellX = Math.floorDiv(startX + 15 + reach, cell);
            int minCellZ = Math.floorDiv(startZ - reach, cell);
            int maxCellZ = Math.floorDiv(startZ + 15 + reach, cell);
            for (int cellX = minCellX; cellX <= maxCellX; cellX++) {
                for (int cellZ = minCellZ; cellZ <= maxCellZ; cellZ++) {
                    Shaft shaft = shaftInCell(map, cellX, cellZ, startX, startZ, reach, abyss);
                    if (shaft == null || !shaft.touches(startX, startZ, 16)) continue;
                    Shaft[] grown = java.util.Arrays.copyOf(found, found.length + 1);
                    grown[found.length] = shaft;
                    found = grown;
                }
            }
        }
        return found;
    }

    private Shaft shaftInCell(MiddleEarthMapRuntime map, int cellX, int cellZ, int startX, int startZ, int reach, boolean abyss) {
        int h = abyss ? mix(cellX + offsetZ + 5, cellZ + offsetX + 9, offsetY + 4001) : mix(cellX + offsetX, cellZ + offsetZ, offsetY);
        if ((h & 0xFF) >= (abyss ? ABYSS_CHANCE : SHAFT_CHANCE)) return null;
        int cell = abyss ? ABYSS_CELL : SHAFT_CELL;
        int edge = abyss ? ABYSS_EDGE : SHAFT_EDGE;
        int span = cell - 2 * edge;
        int x = cellX * cell + edge + (mix(h, 1, 0) & 0xFFFF) % span;
        int z = cellZ * cell + edge + (mix(h, 2, 0) & 0xFFFF) % span;
        if (x < startX - reach || x > startX + 15 + reach || z < startZ - reach || z > startZ + 15 + reach) return null;
        if (weight(map, x, z) < SHAFT_MIN_WEIGHT) return null;
        // along the range, so north-south in the Misty Mountains
        boolean alongX = (mix(h, 19, 0) & 1) == 0;
        MapBasedCustomBiome biome = map.getBiome(x, z);
        BiomeGenerationData data = biome == null ? null : biome.getBiomeData();
        if (data != null && data.ridgeAxis == BiomeGenerationData.RidgeAxis.NORTH_SOUTH) alongX = false;
        if (data != null && data.ridgeAxis == BiomeGenerationData.RidgeAxis.EAST_WEST) alongX = true;
        return new Shaft(abyss ? null : edgesAt(map, x, z), x, z, h, alongX, abyss);
    }

    DepthBands.Edges edgesAt(MiddleEarthMapRuntime map, int x, int z) {
        return DepthBands.Edges.of((int) surface(x, z), DepthBands.deepFloor(map, x, z, seed), MiddleEarthChunkGenerator.caveBlendNoise(x, z, seed));
    }

    // the sideways offset of the centreline at this distance along it; zero at the centre
    static float ravineLateral(Shaft shaft, int t) {
        return shaft.amplitude * ((float) SimplexNoise.noise(shaft.wobble + 17, t / RAVINE_MEANDER_SCALE) - shaft.meanderBase);
    }

    // out[index]: distance across from the centreline; out[index + 1]: half width of the ravine here before its depth profile, 0 beyond the ends
    public static void shaftColumn(Shaft shaft, int x, int z, float[] out, int index) {
        int t = shaft.alongX ? x - shaft.x : z - shaft.z;
        int u = shaft.alongX ? z - shaft.z : x - shaft.x;
        if (t < -shaft.len0 || t > shaft.len1) {
            out[index] = 1e6f;
            out[index + 1] = 0;
            return;
        }
        float fromEnd = Math.min(t + shaft.len0, shaft.len1 - t);
        float vary = 1 + RAVINE_WIDTH_VARY * (float) SimplexNoise.noise(shaft.wobble + 53, t / RAVINE_WIDTH_SCALE);
        out[index] = Math.abs(u - ravineLateral(shaft, t));
        out[index + 1] = shaft.baseHalf * vary * smoothstep(0, shaft.taperLen, fromEnd);
    }

    // the wall's ragged width with height, and for an abyss rock shelves that pinch it every so often
    public static float shaftWobble(Shaft shaft, int y) {
        float wobble = 1 + 0.12f * (float) SimplexNoise.noise(shaft.wobble, y / 26f);
        if (shaft.abyss) {
            // a shelf swells out of the wall and fades back into it, so it never hangs from a sharp underside
            int k = Math.floorMod(y + (int) shaft.wobble + LEDGE_THICKNESS, LEDGE_PERIOD) - LEDGE_THICKNESS;
            wobble *= 1 - LEDGE_PINCH * smoothstep(-LEDGE_THICKNESS, 0, k) * (1 - smoothstep(0, LEDGE_THICKNESS, k));
        }
        return wobble;
    }

    // the cross-section factor at this height, with the wobble of shaftWobble, in a column with these band edges; 0 where the ravine is closed.
    // An ordinary ravine stays in the middle band and above the mithril; an abyss runs from the top of the middle band, or 50 to 78 under the surface if that is lower, to the deep floor.
    public static float shaftScale(Shaft shaft, int y, DepthBands.Edges edges, float wobble) {
        int floor = edges.floor();
        int bottom = shaft.abyss ? floor + (floor < DepthBands.SHALLOW_FLOOR ? ABYSS_DEEP_CLEARANCE : SHAFT_FLOOR_CLEARANCE) + shaft.spread % 3
                : Math.max(shaft.bottomY, floor + SHAFT_FLOOR_CLEARANCE);
        int ceiling = shaft.abyss ? Math.min(edges.top(DepthBands.Band.MIDDLE), edges.surface() - ABYSS_ROOF_MIN - ABYSS_ROOF_STEP * (shaft.spread % 5))
                : Math.min(shaft.topY, edges.top(DepthBands.Band.MIDDLE));
        if (ceiling - bottom < SHAFT_MIN_DEPTH) return 0;
        int taper = shaft.abyss ? ABYSS_TOP_TAPER : SHAFT_TAPER;
        float envelope = smoothstep(bottom, bottom + SHAFT_TAPER, y) * (1 - smoothstep(ceiling - taper, ceiling, y));
        float depth = MathHelper.clamp((ceiling - y) / (float) (ceiling - bottom), 0f, 1f);
        return envelope * (1 - shaft.narrowing * depth) * wobble;
    }

    // open where positive
    public static float shaftMargin(float scale, float across, float halfWidth, float weight) {
        return halfWidth * scale * weight - across;
    }

    // two thin sheets: a near-vertical one and a layered one that tilts, so the tube winds and slopes
    public float streamMargin(int x, int y, int z, float weight, DepthBands.Edges edges) {
        return streamMargin(x, y, z, weight, edges, streamGate(x, z));
    }

    // depends on the column only
    public float streamGate(int x, int z) {
        return (float) SimplexNoise.noise((x + offsetX + 3301) / 200f, (z + offsetZ + 2711) / 200f);
    }

    public float streamMargin(int x, int y, int z, float weight, DepthBands.Edges edges, float gate) {
        float a = (float) SimplexNoise.noise((x + offsetX + 1201) / 64f, (y + offsetY) / 60f, (z + offsetZ + 733) / 64f);
        float tilted = y + STREAM_SLOPE * z;
        float b = (float) SimplexNoise.noise((x + offsetX + 2203) / 64f, (tilted + offsetY + 97) / 16f, (z + offsetZ + 1511) / 64f);
        float margin = Math.min(1 - Math.abs(a) / STREAM_WIDTH_A, 1 - Math.abs(b) / STREAM_WIDTH_B);
        return margin - (1 - streamEnvelope(edges, y)) * 2f - (1 - smoothstep(-0.25f, 0.2f, gate)) * 2f - (1 - weight) * 2f;
    }

    // one to three levels, each a window around the middle of an equal slot of the middle band
    public static boolean streamBand(DepthBands.Edges edges) {
        return edges.thickness(DepthBands.Band.MIDDLE) >= STREAM_MIN_THICKNESS;
    }

    private static float streamEnvelope(DepthBands.Edges edges, int y) {
        int thickness = edges.thickness(DepthBands.Band.MIDDLE);
        if (thickness < STREAM_MIN_THICKNESS) return 0;
        int levels = Math.min(3, 1 + thickness / STREAM_LEVEL_THICKNESS);
        float slot = thickness / (float) levels;
        float half = Math.min(slot * 0.5f * STREAM_WINDOW, STREAM_WINDOW_MAX);
        float envelope = 0;
        for (int level = 0; level < levels; level++) {
            float centre = edges.middleBottom() + (level + 0.5f) * slot;
            envelope = Math.max(envelope, smoothstep(centre - half - STREAM_EDGE, centre - half, y) * (1 - smoothstep(centre + half, centre + half + STREAM_EDGE, y)));
        }
        return envelope;
    }

    // lakes whose chamber or tunnel touches the chunk, or an empty array
    public Lake[] lakesNear(MiddleEarthMapRuntime map, int startX, int startZ) {
        int minCellX = Math.floorDiv(startX - LAKE_REACH, LAKE_CELL);
        int maxCellX = Math.floorDiv(startX + 15 + LAKE_REACH, LAKE_CELL);
        int minCellZ = Math.floorDiv(startZ - LAKE_REACH, LAKE_CELL);
        int maxCellZ = Math.floorDiv(startZ + 15 + LAKE_REACH, LAKE_CELL);
        Lake[] found = NO_LAKES;
        for (int cellX = minCellX; cellX <= maxCellX; cellX++) {
            for (int cellZ = minCellZ; cellZ <= maxCellZ; cellZ++) {
                int h = mix(cellX + offsetZ, cellZ + offsetX, offsetY + 977);
                if ((h & 0xFF) >= LAKE_CHANCE) continue;
                int span = LAKE_CELL - 2 * LAKE_EDGE;
                int x = cellX * LAKE_CELL + LAKE_EDGE + (mix(h, 1, 0) & 0xFFFF) % span;
                int z = cellZ * LAKE_CELL + LAKE_EDGE + (mix(h, 2, 0) & 0xFFFF) % span;
                Lake lake = new Lake(this, x, z, h);
                if (!lake.touches(startX, startZ)) continue;
                if (!lakeFits(map, lake)) continue;
                Lake[] grown = java.util.Arrays.copyOf(found, found.length + 1);
                grown[found.length] = lake;
                found = grown;
            }
        }
        return found;
    }

    private static final int FIT_CACHE_SIZE = 256;
    private static final java.util.Map<java.util.List<Long>, Boolean> FITS = new java.util.LinkedHashMap<>() {
        @Override
        protected boolean removeEldestEntry(java.util.Map.Entry<java.util.List<Long>, Boolean> eldest) {
            return size() > FIT_CACHE_SIZE;
        }
    };

    // the footprint must be Misty, with solid rock between the dome and the surface, the shell clear of the deep floor
    // and the tunnel clear of it all the way up
    private boolean lakeFits(MiddleEarthMapRuntime map, Lake lake) {
        java.util.List<Long> key = java.util.List.of(seed, (long) lake.x, (long) lake.z);
        synchronized (FITS) {
            Boolean known = FITS.get(key);
            if (known != null) return known;
        }
        boolean fits = computeFits(map, lake);
        synchronized (FITS) {
            FITS.put(key, fits);
        }
        return fits;
    }

    // lakes belong to the wet deeps alone, clear of the rock walls between regions
    private boolean wet(int x, int z) {
        return DeepThemes.at(x, z, seed) == DeepThemes.Theme.WET && !DeepThemes.border(x, z, seed);
    }

    // an abyss runs through the basin or close to it
    private boolean abyssBeside(MiddleEarthMapRuntime map, Lake lake) {
        int reach = (int) (Math.max(lake.rx, lake.rz) * (1 + LAKE_WOBBLE)) + LAKE_SHELL + LAKE_ABYSS_GAP;
        int minCellX = Math.floorDiv(lake.x - reach - ABYSS_MAX_REACH, ABYSS_CELL);
        int maxCellX = Math.floorDiv(lake.x + reach + ABYSS_MAX_REACH, ABYSS_CELL);
        int minCellZ = Math.floorDiv(lake.z - reach - ABYSS_MAX_REACH, ABYSS_CELL);
        int maxCellZ = Math.floorDiv(lake.z + reach + ABYSS_MAX_REACH, ABYSS_CELL);
        for (int cellX = minCellX; cellX <= maxCellX; cellX++) {
            for (int cellZ = minCellZ; cellZ <= maxCellZ; cellZ++) {
                Shaft shaft = shaftInCell(map, cellX, cellZ, lake.x - 8, lake.z - 8, reach + ABYSS_MAX_REACH, true);
                if (shaft != null && shaft.touches(lake.x - reach, lake.z - reach, 2 * reach + 1)) return true;
            }
        }
        return false;
    }

    private boolean computeFits(MiddleEarthMapRuntime map, Lake lake) {
        if (weight(map, lake.x, lake.z) < LAKE_MIN_WEIGHT || !wet(lake.x, lake.z)) return false;
        if (abyssBeside(map, lake)) return false;
        float limit = lake.waterTop + lake.ceilingMax() + 1 + LAKE_SURFACE_CLEARANCE;
        if (surface(lake.x, lake.z) < limit) return false;
        for (int i = 0; i < 12; i++) {
            double angle = i * Math.PI / 6;
            int x = lake.x + (int) (Math.cos(angle) * lake.rx * (1 + LAKE_WOBBLE));
            int z = lake.z + (int) (Math.sin(angle) * lake.rz * (1 + LAKE_WOBBLE));
            if (weight(map, x, z) < LAKE_RIM_WEIGHT || surface(x, z) < limit || !wet(x, z)) return false;
        }
        for (int i = 0; i < 8; i++) {
            double angle = i * Math.PI / 4;
            for (float share : new float[]{0, 0.4f, 0.7f, 0.95f}) {
                int x = lake.x + (int) (Math.cos(angle) * lake.rx * share);
                int z = lake.z + (int) (Math.sin(angle) * lake.rz * share);
                float inner = share / (1 - LAKE_WOBBLE);
                float bottom = lake.waterTop - (lake.depth + LAKE_SHELL) * (float) Math.sqrt(Math.max(0, 1 - inner * inner));
                if (bottom < DepthBands.deepFloor(map, x, z, seed) + LAKE_FLOOR_CLEARANCE || !wet(x, z)) return false;
            }
        }
        for (int along = 0; along <= LAKE_TUNNEL_LENGTH; along += 8) {
            int x = (int) (lake.tunnelX + lake.tunnelDx * along);
            int z = (int) (lake.tunnelZ + lake.tunnelDz * along);
            float y = lake.tunnelY + LAKE_TUNNEL_SLOPE * along - LAKE_TUNNEL_RADIUS;
            if (y < DepthBands.deepFloor(map, x, z, seed) + DepthBands.BOTTOM_BAND + 3) return false;
            if (y < DepthBands.MITHRIL_BOTTOM + 5 && !wet(x, z)) return false;
        }
        int endX = (int) (lake.tunnelX + lake.tunnelDx * LAKE_TUNNEL_LENGTH);
        int endZ = (int) (lake.tunnelZ + lake.tunnelDz * LAKE_TUNNEL_LENGTH);
        return surface(endX, endZ) >= lake.tunnelY + LAKE_TUNNEL_SLOPE * LAKE_TUNNEL_LENGTH + 20;
    }

    private static float surface(int x, int z) {
        return MiddleEarthChunkGenerator.DIRT_HEIGHT + MiddleEarthHeightMap.getHeight(x, z);
    }

    // the range of y a lake or its tunnel can touch
    public static int lakeMinY(Lake[] lakes) {
        int min = Integer.MAX_VALUE;
        for (Lake lake : lakes) min = Math.min(min, lake.lowestY());
        return min;
    }

    public static int lakeMaxY() {
        return LAKE_TUNNEL_MAX_Y;
    }

    // multiplier on the basin's horizontal radius: low frequencies dominate so the outline reads as a cavern
    float outline(float seed, float x, float z) {
        float n = 0.6f * (float) SimplexNoise.noise((x + offsetX) / 70f + seed, (z + offsetZ) / 70f);
        n += 0.3f * (float) SimplexNoise.noise((x + offsetX) / 28f - seed, (z + offsetZ) / 28f);
        n += 0.1f * (float) SimplexNoise.noise((x + offsetX) / 11f + 2 * seed, (z + offsetZ) / 11f);
        return MathHelper.clamp(1 + LAKE_WOBBLE * n, 1 - LAKE_WOBBLE, 1 + LAKE_WOBBLE);
    }

    // out: outline multiplier and dome height multiplier of one column
    public void lakeColumn(Lake lake, int x, int z, float[] out, int index) {
        out[index] = outline(lake.wobble, x, z);
        float dome = (float) SimplexNoise.noise((x + offsetX) / 36f + lake.wobble, (z + offsetZ) / 36f - lake.wobble);
        out[index + 1] = 1 + LAKE_DOME_WOBBLE * MathHelper.clamp(dome, -1, 1);
    }

    // chamber, pillars, islands and the solid shell below and around the water; the tunnel is checked on its own
    public static int lakeState(Lake lake, float wobble, float dome, int x, int y, int z) {
        int top = lake.waterTop;
        float height = lake.height * dome;
        if (y > top + height + 1 || y < top - lake.depth - LAKE_SHELL) return LAKE_NONE;
        float nx = (x - lake.x) / lake.rx;
        float nz = (z - lake.z) / lake.rz;
        float h2 = nx * nx + nz * nz;
        float shellLimit = wobble + LAKE_SHELL / Math.min(lake.rx, lake.rz);
        if (h2 >= shellLimit * shellLimit) return LAKE_NONE;
        boolean up = y > top;
        float e = up ? (y - top - 0.5f) / height : (top + 0.5f - y) / lake.depth;
        float inner = h2 / (wobble * wobble);
        if (inner + e * e < 1) {
            for (int i = 0; i < lake.islands; i++) {
                float dx = x - lake.islandX[i];
                float dz = z - lake.islandZ[i];
                float d2 = dx * dx + dz * dz;
                float r = lake.islandRadius[i];
                if (d2 < r * r && y <= top + (d2 < 0.36f * r * r ? 1 : 0)) return LAKE_STONE;
            }
            for (int i = 0; i < lake.pillars; i++) {
                float dx = x - lake.pillarX[i];
                float dz = z - lake.pillarZ[i];
                float d2 = dx * dx + dz * dz;
                float r = lake.pillarRadius[i];
                if (d2 > 3.7f * r * r) continue;
                // swells slightly with height and flares into the ceiling and the floor
                float ceiling = top + 0.5f + height * (float) Math.sqrt(Math.max(0, 1 - inner));
                float floor = top + 0.5f - lake.depth * (float) Math.sqrt(Math.max(0, 1 - inner));
                float flare = Math.max(MathHelper.clamp((y - ceiling + 8) / 8f, 0, 1), MathHelper.clamp((floor + 5 - y) / 5f, 0, 1));
                float reach = r * (1 + 0.2f * MathHelper.sin(y * 0.17f + lake.pillarPhase[i]) + 0.6f * flare);
                if (d2 < reach * reach) return LAKE_STONE;
            }
            return up ? LAKE_AIR : LAKE_WATER;
        }
        if (up) return LAKE_NONE;
        float es = (top + 0.5f - y) / (lake.depth + LAKE_SHELL);
        return h2 / (shellLimit * shellLimit) + es * es < 1 ? LAKE_STONE : LAKE_NONE;
    }

    // straight tunnel climbing away from the chamber's air; always above the water line
    public static boolean inLakeTunnel(Lake lake, int x, int y, int z) {
        float px = x + 0.5f - lake.tunnelX;
        float py = y + 0.5f - lake.tunnelY;
        float pz = z + 0.5f - lake.tunnelZ;
        float slope = LAKE_TUNNEL_SLOPE;
        float t = (px * lake.tunnelDx + py * slope + pz * lake.tunnelDz) / (1 + slope * slope);
        t = MathHelper.clamp(t, 0, LAKE_TUNNEL_LENGTH);
        float ex = px - t * lake.tunnelDx;
        float ey = py - t * slope;
        float ez = pz - t * lake.tunnelDz;
        return ex * ex + ey * ey + ez * ez < LAKE_TUNNEL_RADIUS * LAKE_TUNNEL_RADIUS;
    }

    // the range of y the shafts of a chunk can touch, as lowest then highest
    public static int[] shaftRange(Shaft[] shafts) {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (Shaft shaft : shafts) {
            min = Math.min(min, shaft.abyss ? DepthBands.DEEP_FLOOR : shaft.bottomY - 4);
            max = Math.max(max, shaft.topY);
        }
        return new int[]{min, max};
    }

    // stateless integer hash
    static int mix(int a, int b, int c) {
        int h = a * 0x9E3779B1 + b * 0x85EBCA6B + c * 0xC2B2AE35;
        h ^= h >>> 16;
        h *= 0x7FEB352D;
        h ^= h >>> 15;
        h *= 0x846CA68B;
        h ^= h >>> 16;
        return h;
    }

    // true where a rift breaks through the surface skin
    public boolean mouthOpen(int x, int z, float weight, int surfaceY) {
        if (weight < MOUTH_MIN_WEIGHT || surfaceY < MOUTH_MIN_SURFACE_Y) return false;
        float gate = (float) SimplexNoise.noise((x + offsetX + 901) / 70f, (z + offsetZ + 433) / 70f);
        if (gate < MOUTH_GATE) return false;
        return riftMargin(x, surfaceY - 5, z, weight * riftCluster(x, z), DepthBands.Edges.of(surfaceY, DepthBands.SHALLOW_FLOOR, MiddleEarthChunkGenerator.caveBlendNoise(x, z, seed))) > 0.02f;
    }

    private static float smoothstep(float edge0, float edge1, float x) {
        float t = MathHelper.clamp((x - edge0) / (edge1 - edge0), 0f, 1f);
        return t * t * (3 - 2 * t);
    }
}
