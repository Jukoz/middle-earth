package net.sevenstars.middleearth.features.generation.caves;

import net.minecraft.util.math.MathHelper;
import net.sevenstars.middleearth.features.generation.noise.SimplexNoise;
import net.sevenstars.middleearth.features.generation.biomes.caves.CaveType;
import net.sevenstars.middleearth.features.generation.biomes.caves.DepthBands;
import net.sevenstars.middleearth.features.generation.biomes.surface.MapBasedCustomBiome;
import net.sevenstars.middleearth.features.generation.chunkgen.MiddleEarthChunkGenerator;
import net.sevenstars.middleearth.features.generation.chunkgen.map.MiddleEarthHeightMap;
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

    private static final int CHAMBER_CLEARANCE = DepthBands.LAVA_THICKNESS + 3; // above the deep floor
    private static final int CHAMBER_TOP_FADE = 6;

    private static final int MOUTH_MIN_SURFACE_Y = 110;
    private static final float MOUTH_MIN_WEIGHT = 0.95f;
    private static final float MOUTH_GATE = 0.5f;

    // fade distances for the weight, in blocks
    private static final int WEIGHT_INNER_RADIUS = 24;
    private static final int WEIGHT_OUTER_RADIUS = 48;
    private static final int WEIGHT_OUTER_DIAGONAL = 34;

    private static final int SHAFT_CELL = 192; // multiple of 16, so a chunk sits in one cell
    private static final int SHAFT_EDGE = 20; // shafts stay this far from their cell border
    private static final int SHAFT_CHANCE = 80; // out of 256
    private static final float SHAFT_MIN_WEIGHT = 0.95f;
    private static final int SHAFT_FLOOR_CLEARANCE = 8; // bottom above the deep floor, the taper closes it before that
    private static final int SHAFT_FLOOR_SPREAD = 5;
    private static final float SHAFT_TOP_FROM = 0.9f; // share of the middle band
    private static final int SHAFT_TAPER = 14;
    private static final int SHAFT_MAX_REACH = 16; // radius + wobble + drift
    private static final int BRIDGE_CHANCE = 102; // out of 256

    private static final int ROAD_MIN_LENGTH = 40;
    private static final int ROAD_MAX_LENGTH = 80;
    private static final int ROAD_FLAT = 22; // straight and level from the shaft centre, past any rim
    private static final int ROAD_RAMP = 10;
    private static final int ROAD_TAPER = 6;
    private static final int ROAD_SIDE = 8; // reach across the axis, meander included
    private static final float ROAD_MEANDER = 3.5f;
    private static final float ROAD_MAX_SLOPE = 0.15f;
    private static final int ROAD_FROM_BOTTOM = 12;
    private static final int ROAD_FROM_TOP = 8;
    private static final int ROAD_SURFACE_MARGIN = 15;
    private static final float ROAD_MIN_DIAMETER = 12;
    private static final int ROAD_TRIES = 8;
    private static final float ROAD_DECK_FROM = 0.5f; // share of the middle band
    private static final float ROAD_DECK_TO = 0.8f;
    private static final int ROAD_SURFACE_STEP = 8;
    private static final int ROAD_APRON = 8; // blocks of solid floor past the shaft rim

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

    public static final class Shaft {
        final int x;
        final int z;
        final float radius;
        final int bottomY;
        final int topY;
        final int middleBottom;
        final int middleTop;
        final float wobble;
        final boolean bridge;

        private Shaft(DepthBands.Edges edges, int x, int z, int h) {
            this.x = x;
            this.z = z;
            this.radius = 5 + (mix(h, 3, 0) & 0xFF) / 255f * 4;
            this.bottomY = edges.floor() + SHAFT_FLOOR_CLEARANCE + (mix(h, 4, 0) & 0xFF) % SHAFT_FLOOR_SPREAD;
            this.topY = edges.at(DepthBands.Band.MIDDLE, SHAFT_TOP_FROM + (1 - SHAFT_TOP_FROM) * (mix(h, 5, 0) & 0xFF) / 255f);
            this.middleBottom = edges.middleBottom();
            this.middleTop = edges.top(DepthBands.Band.MIDDLE);
            this.wobble = (mix(h, 6, 0) & 0xFFF);
            this.bridge = (mix(h, 12, 0) & 0xFF) < BRIDGE_CHANCE;
        }
    }

    public static final class Road {
        final Shaft shaft;
        final boolean alongX;
        final int deckY;
        final int minDeck;
        final int minY;
        final int maxY;
        final int[] length = new int[2]; // negative then positive side
        final float[] slope = new float[2];

        private Road(Shaft shaft, boolean alongX, int deckY, int h) {
            this.shaft = shaft;
            this.alongX = alongX;
            this.deckY = deckY;
            this.minDeck = shaft.middleBottom;
            int low = deckY;
            int high = deckY;
            for (int side = 0; side < 2; side++) {
                length[side] = ROAD_MIN_LENGTH + (mix(h, 21 + side, 0) & 0xFFFF) % (ROAD_MAX_LENGTH - ROAD_MIN_LENGTH + 1);
                slope[side] = ROAD_MAX_SLOPE * ((mix(h, 23 + side, 0) & 0xFFFF) / 32767.5f - 1);
                int end = Math.max(minDeck, deckY + Math.round(slope[side] * (length[side] - ROAD_FLAT)));
                low = Math.min(low, end);
                high = Math.max(high, end);
            }
            this.minY = low - 3;
            this.maxY = high + 4;
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

    private static final Road[] NO_ROADS = new Road[0];

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

    public float chamberMargin(int x, int y, int z, float weight, DepthBands.Edges edges) {
        int top = edges.top(DepthBands.Band.DEEPS);
        float depth = MathHelper.clamp((top - y) / (float) Math.max(1, top - edges.floor()), 0f, 1f);
        float n = 0.7f * (float) SimplexNoise.noise((x + offsetX + 311) / 28f, (y + offsetY) / 18f, (z + offsetZ + 577) / 28f);
        n += 0.3f * (float) SimplexNoise.noise((x + offsetX + 311) / 14f, (y + offsetY) / 9f, (z + offsetZ + 577) / 14f);
        // wider and commoner the deeper it gets
        float threshold = 0.6f - 0.25f * depth + (1 - weight) * 0.6f;
        float floorFade = MathHelper.clamp((y - (edges.floor() + CHAMBER_CLEARANCE)) / 4f, 0f, 1f);
        float topFade = MathHelper.clamp((top - y) / (float) CHAMBER_TOP_FADE, 0f, 1f);
        return n - threshold - (1 - floorFade) * 2f - (1 - topFade) * 2f;
    }

    public static int chamberBottom(DepthBands.Edges edges) {
        return edges.floor() + CHAMBER_CLEARANCE - 4;
    }

    // shafts whose reach touches the chunk, or an empty array
    public Shaft[] shaftsNear(MiddleEarthMapRuntime map, int startX, int startZ) {
        int minCellX = Math.floorDiv(startX - SHAFT_MAX_REACH, SHAFT_CELL);
        int maxCellX = Math.floorDiv(startX + 15 + SHAFT_MAX_REACH, SHAFT_CELL);
        int minCellZ = Math.floorDiv(startZ - SHAFT_MAX_REACH, SHAFT_CELL);
        int maxCellZ = Math.floorDiv(startZ + 15 + SHAFT_MAX_REACH, SHAFT_CELL);
        Shaft[] found = NO_SHAFTS;
        for (int cellX = minCellX; cellX <= maxCellX; cellX++) {
            for (int cellZ = minCellZ; cellZ <= maxCellZ; cellZ++) {
                Shaft shaft = shaftInCell(map, cellX, cellZ, startX, startZ, SHAFT_MAX_REACH);
                if (shaft == null) continue;
                Shaft[] grown = java.util.Arrays.copyOf(found, found.length + 1);
                grown[found.length] = shaft;
                found = grown;
            }
        }
        return found;
    }

    // the shaft whose centre is in this chunk, if any
    public Shaft shaftCenteredIn(MiddleEarthMapRuntime map, int chunkX, int chunkZ) {
        int startX = chunkX << 4;
        int startZ = chunkZ << 4;
        return shaftInCell(map, Math.floorDiv(startX, SHAFT_CELL), Math.floorDiv(startZ, SHAFT_CELL), startX, startZ, 0);
    }

    private Shaft shaftInCell(MiddleEarthMapRuntime map, int cellX, int cellZ, int startX, int startZ, int reach) {
        int h = mix(cellX + offsetX, cellZ + offsetZ, offsetY);
        if ((h & 0xFF) >= SHAFT_CHANCE) return null;
        int span = SHAFT_CELL - 2 * SHAFT_EDGE;
        int x = cellX * SHAFT_CELL + SHAFT_EDGE + (mix(h, 1, 0) & 0xFFFF) % span;
        int z = cellZ * SHAFT_CELL + SHAFT_EDGE + (mix(h, 2, 0) & 0xFFFF) % span;
        if (x < startX - reach || x > startX + 15 + reach || z < startZ - reach || z > startZ + 15 + reach) return null;
        if (weight(map, x, z) < SHAFT_MIN_WEIGHT) return null;
        return new Shaft(edgesAt(map, x, z), x, z, h);
    }

    DepthBands.Edges edgesAt(MiddleEarthMapRuntime map, int x, int z) {
        return DepthBands.Edges.of((int) surface(x, z), DepthBands.deepFloor(map, x, z, seed), MiddleEarthChunkGenerator.caveBlendNoise(x, z, seed));
    }

    // the road of a bridge-rolled shaft, or null when no deck height keeps it under the surface; pure in the shaft
    public Road roadOf(Shaft shaft) {
        if (!shaft.bridge) return null;
        int h = mix(shaft.x, shaft.z, 7311);
        boolean alongX = (mix(h, 20, 0) & 1) == 0;
        float[] profile = new float[3];
        int middle = shaft.middleTop - shaft.middleBottom;
        int lo = Math.max(shaft.bottomY + ROAD_FROM_BOTTOM, shaft.middleBottom + Math.round(ROAD_DECK_FROM * middle));
        int hi = Math.min(shaft.topY - ROAD_FROM_TOP, shaft.middleBottom + Math.round(ROAD_DECK_TO * middle));
        if (hi < lo) return null;
        for (int attempt = 0; attempt < ROAD_TRIES; attempt++) {
            int deck = lo + (mix(h, 30, attempt) & 0xFFFF) % (hi - lo + 1);
            shaftProfile(shaft, deck, profile, 0);
            if (2 * profile[0] < ROAD_MIN_DIAMETER) continue;
            Road road = new Road(shaft, alongX, deck, h);
            if (roadUnderSurface(road)) return road;
        }
        return null;
    }

    private boolean roadUnderSurface(Road road) {
        for (int side = 0; side < 2; side++) {
            int sign = side == 0 ? -1 : 1;
            for (int a = 0; a <= road.length[side]; a += ROAD_SURFACE_STEP) {
                int t = sign * a;
                int u = roadLateral(road, t);
                int x = road.shaft.x + (road.alongX ? t : u);
                int z = road.shaft.z + (road.alongX ? u : t);
                float surface = MiddleEarthChunkGenerator.DIRT_HEIGHT + MiddleEarthHeightMap.getHeight(x, z);
                if (surface - ROAD_SURFACE_MARGIN < roadDeck(road, t) + 4) return false;
            }
        }
        return true;
    }

    // roads whose reach touches the chunk, or an empty array
    public Road[] roadsNear(MiddleEarthMapRuntime map, int startX, int startZ) {
        return roadsNear(map, startX, startZ, 16);
    }

    // roads whose reach touches the square of this size
    public Road[] roadsNear(MiddleEarthMapRuntime map, int startX, int startZ, int size) {
        int reach = ROAD_MAX_LENGTH + 2;
        int minCellX = Math.floorDiv(startX - reach, SHAFT_CELL);
        int maxCellX = Math.floorDiv(startX + size - 1 + reach, SHAFT_CELL);
        int minCellZ = Math.floorDiv(startZ - reach, SHAFT_CELL);
        int maxCellZ = Math.floorDiv(startZ + size - 1 + reach, SHAFT_CELL);
        Road[] found = NO_ROADS;
        for (int cellX = minCellX; cellX <= maxCellX; cellX++) {
            for (int cellZ = minCellZ; cellZ <= maxCellZ; cellZ++) {
                Shaft shaft = shaftInCell(map, cellX, cellZ, startX, startZ, reach + size - 16);
                if (shaft == null || !shaft.bridge) continue;
                Road road = roadOf(shaft);
                if (road == null) continue;
                int axisLow = (road.alongX ? shaft.x : shaft.z) - road.length[0];
                int axisHigh = (road.alongX ? shaft.x : shaft.z) + road.length[1];
                int crossCentre = road.alongX ? shaft.z : shaft.x;
                int axisStart = road.alongX ? startX : startZ;
                int crossStart = road.alongX ? startZ : startX;
                if (axisHigh < axisStart || axisLow > axisStart + size - 1 || crossCentre + ROAD_SIDE < crossStart || crossCentre - ROAD_SIDE > crossStart + size - 1) continue;
                Road[] grown = java.util.Arrays.copyOf(found, found.length + 1);
                grown[found.length] = road;
                found = grown;
            }
        }
        return found;
    }

    // t is the signed distance from the shaft centre along the axis; the meander is across it
    int roadLateral(Road road, int t) {
        int a = Math.abs(t);
        if (a <= ROAD_FLAT) return 0;
        float ramp = smoothstep(ROAD_FLAT, ROAD_FLAT + ROAD_RAMP, a);
        return Math.round(ROAD_MEANDER * ramp * (float) SimplexNoise.noise(road.shaft.wobble + 307 + (t < 0 ? 53 : 0), a / 45f));
    }

    static int roadDeck(Road road, int t) {
        int a = Math.abs(t);
        if (a <= ROAD_FLAT) return road.deckY;
        return Math.max(road.minDeck, road.deckY + Math.round(road.slope[t < 0 ? 0 : 1] * (a - ROAD_FLAT)));
    }

    // solid under and beside the road mouth: the deck row and three rows below it, 5 wide, from just inside the rim outward
    public boolean roadApron(Road road, int x, int y, int z, float weight) {
        if (y > road.deckY || y < road.deckY - 3) return false;
        int dx = x - road.shaft.x;
        int dz = z - road.shaft.z;
        int t = road.alongX ? dx : dz;
        int u = road.alongX ? dz : dx;
        if (Math.abs(t) > ROAD_FLAT || Math.abs(u) > 2) return false;
        float[] profile = new float[3];
        shaftProfile(road.shaft, y, profile, 0);
        float fromCentre = (float) Math.sqrt((x - road.shaft.x - profile[1]) * (x - road.shaft.x - profile[1]) + (z - road.shaft.z - profile[2]) * (z - road.shaft.z - profile[2]));
        float rim = profile[0] * weight;
        return fromCentre >= rim - 2 && fromCentre <= rim + ROAD_APRON;
    }

    // clear rows of a road at this column: deck+1 to deck+4, or an empty range when the column is outside it
    int roadFirstRow(Road road, int x, int z) {
        int dx = x - road.shaft.x;
        int dz = z - road.shaft.z;
        int t = road.alongX ? dx : dz;
        int u = road.alongX ? dz : dx;
        if (Math.abs(t) >= road.length[t < 0 ? 0 : 1] || Math.abs(u) > ROAD_SIDE) return Integer.MIN_VALUE;
        return roadDeck(road, t) + 1;
    }

    // open where the road carves: 3 wide and 3 tall above its deck row, rougher away from the shaft and tapering at the ends
    public boolean roadOpen(Road road, int x, int y, int z) {
        int dx = x - road.shaft.x;
        int dz = z - road.shaft.z;
        int t = road.alongX ? dx : dz;
        int u = road.alongX ? dz : dx;
        int a = Math.abs(t);
        int length = road.length[t < 0 ? 0 : 1];
        if (a >= length || Math.abs(u) > ROAD_SIDE) return false;
        int rel = y - roadDeck(road, t);
        if (rel < 1 || rel > 4) return false;
        int across = Math.abs(u - roadLateral(road, t));
        float open = Math.min(1, (length - a) / (float) ROAD_TAPER);
        if (across > (open < 0.5f ? 0 : 1)) return false;
        int height = 3;
        if (open < 1) {
            height = 1 + (int) (open * 3);
        } else if (a > ROAD_FLAT) {
            float rough = (float) SimplexNoise.noise((x + offsetX) / 4f, (z + offsetZ) / 4f);
            height += rough > 0.35f ? 1 : rough < -0.5f ? -1 : 0;
        }
        return rel <= height;
    }

    // out: radius, centre offset x, centre offset z at this y; radius 0 outside the shaft's span
    public void shaftProfile(Shaft shaft, int y, float[] out, int index) {
        float envelope = smoothstep(shaft.bottomY, shaft.bottomY + SHAFT_TAPER, y)
                * (1 - smoothstep(shaft.topY - SHAFT_TAPER, shaft.topY, y));
        float wobble = (float) SimplexNoise.noise(shaft.wobble, y / 26f);
        out[index] = shaft.radius * envelope * (1 + 0.18f * wobble);
        out[index + 1] = 3f * (float) SimplexNoise.noise(shaft.wobble + 101, y / 40f);
        out[index + 2] = 3f * (float) SimplexNoise.noise(shaft.wobble + 211, y / 40f);
    }

    // open where positive
    public static float shaftMargin(Shaft shaft, float radius, float offsetX, float offsetZ, int x, int y, int z, float weight) {
        float dx = x - shaft.x - offsetX;
        float dz = z - shaft.z - offsetZ;
        return radius * weight - (float) Math.sqrt(dx * dx + dz * dz);
    }

    // two thin sheets: a near-vertical one and a layered one that tilts, so the tube winds and slopes
    public float streamMargin(int x, int y, int z, float weight, DepthBands.Edges edges) {
        float a = (float) SimplexNoise.noise((x + offsetX + 1201) / 64f, (y + offsetY) / 60f, (z + offsetZ + 733) / 64f);
        float tilted = y + STREAM_SLOPE * z;
        float b = (float) SimplexNoise.noise((x + offsetX + 2203) / 64f, (tilted + offsetY + 97) / 16f, (z + offsetZ + 1511) / 64f);
        float gate = (float) SimplexNoise.noise((x + offsetX + 3301) / 200f, (z + offsetZ + 2711) / 200f);
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

    private boolean computeFits(MiddleEarthMapRuntime map, Lake lake) {
        if (weight(map, lake.x, lake.z) < LAKE_MIN_WEIGHT) return false;
        float limit = lake.waterTop + lake.ceilingMax() + 1 + LAKE_SURFACE_CLEARANCE;
        if (surface(lake.x, lake.z) < limit) return false;
        for (int i = 0; i < 12; i++) {
            double angle = i * Math.PI / 6;
            int x = lake.x + (int) (Math.cos(angle) * lake.rx * (1 + LAKE_WOBBLE));
            int z = lake.z + (int) (Math.sin(angle) * lake.rz * (1 + LAKE_WOBBLE));
            if (weight(map, x, z) < LAKE_RIM_WEIGHT || surface(x, z) < limit) return false;
        }
        for (int i = 0; i < 8; i++) {
            double angle = i * Math.PI / 4;
            for (float share : new float[]{0, 0.4f, 0.7f, 0.95f}) {
                int x = lake.x + (int) (Math.cos(angle) * lake.rx * share);
                int z = lake.z + (int) (Math.sin(angle) * lake.rz * share);
                float inner = share / (1 - LAKE_WOBBLE);
                float bottom = lake.waterTop - (lake.depth + LAKE_SHELL) * (float) Math.sqrt(Math.max(0, 1 - inner * inner));
                if (bottom < DepthBands.deepFloor(map, x, z, seed) + LAKE_FLOOR_CLEARANCE) return false;
            }
        }
        for (int along = 0; along <= LAKE_TUNNEL_LENGTH; along += 8) {
            int x = (int) (lake.tunnelX + lake.tunnelDx * along);
            int z = (int) (lake.tunnelZ + lake.tunnelDz * along);
            float y = lake.tunnelY + LAKE_TUNNEL_SLOPE * along - LAKE_TUNNEL_RADIUS;
            if (y < DepthBands.deepFloor(map, x, z, seed) + DepthBands.LAVA_THICKNESS + 3) return false;
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
            min = Math.min(min, shaft.bottomY - 4);
            max = Math.max(max, shaft.topY);
        }
        return new int[]{min, max};
    }

    public static int[] roadRange(Road[] roads) {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (Road road : roads) {
            min = Math.min(min, road.minY);
            max = Math.max(max, road.maxY);
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
