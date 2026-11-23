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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static net.sevenstars.middleearth.features.generation.caves.Lake.*;
import static net.sevenstars.middleearth.features.generation.caves.Shaft.*;
import static net.sevenstars.middleearth.features.generation.caves.Worm.*;

public final class MistyCaves {
    private static final int CHAMBER_CLEARANCE = DepthBands.BOTTOM_BAND + 3; // above the deep floor
    private static final int CHAMBER_TOP_FADE = 6;

    // fade distances for the weight, in blocks
    private static final int WEIGHT_INNER_RADIUS = 24;
    private static final int WEIGHT_OUTER_RADIUS = 48;
    private static final int WEIGHT_OUTER_DIAGONAL = 34;

    private static final int STREAM_MIN_THICKNESS = 24; // of the middle band
    private static final int STREAM_LEVEL_THICKNESS = 110; // one more level for every this many blocks of band, up to three
    private static final float STREAM_WINDOW = 0.8f; // share of a level's slot the tunnels use
    private static final int STREAM_WINDOW_MAX = 40;
    private static final int STREAM_EDGE = 8;
    private static final float STREAM_SLOPE = 0.1f; // blocks of drop per block towards +z
    private static final float STREAM_WIDTH_A = 0.09f;
    private static final float STREAM_WIDTH_B = 0.17f;

    public static final int LAKE_NONE = 0;
    public static final int LAKE_AIR = 1;
    public static final int LAKE_WATER = 2;
    public static final int LAKE_STONE = 3;

    public static final int VALUES = 3;
    public static final int CHAMBER = 0;
    public static final int SHAFT = 1;
    public static final int STREAM = 2;

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

    static boolean isMisty(MiddleEarthMapRuntime map, int x, int z) {
        MapBasedCustomBiome biome = map.getBiome(x, z);
        return biome != null && biome.getBiome() != null && biome.getBiome().getCaveType() == CaveType.MISTIES;
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

    // open where positive: the radius of the nearest bore less the distance to it, so the tunnels are round with
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
        List<Shaft> found = new ArrayList<>();
        for (int pass = 0; pass < 2; pass++) {
            boolean abyss = pass == 1;
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
                    found.add(shaft);
                }
            }
        }
        return found.isEmpty() ? NO_SHAFTS : found.toArray(NO_SHAFTS);
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

    // the sideways offset of the centreline at this distance along it, zero at the centre
    static float ravineLateral(Shaft shaft, int t) {
        return shaft.amplitude * ((float) SimplexNoise.noise(shaft.wobble + 17, t / RAVINE_MEANDER_SCALE) - shaft.meanderBase);
    }

    // out[index]: distance across the centreline, out[index + 1]: half width before depth profile, 0 beyond the ends
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

    // cross-section factor at this height, with shaftWobble, for a column with these band edges, 0 where closed
    // ravine: middle band above the mithril. Abyss: top of the middle band (or 50-78 under the surface if lower) to the floor
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
        List<Lake> found = new ArrayList<>();
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
                found.add(lake);
            }
        }
        return found.isEmpty() ? NO_LAKES : found.toArray(NO_LAKES);
    }

    private static final int FIT_CACHE_SIZE = 256;
    private static final float[] LAKE_FIT_SHARES = {0, 0.4f, 0.7f, 0.95f};
    private record FitKey(long seed, int x, int z) {
    }

    // guarded by its own lock, since chunks generate in parallel
    private static final Map<FitKey, Boolean> FITS = new LinkedHashMap<>() {
        @Override
        protected boolean removeEldestEntry(Map.Entry<FitKey, Boolean> eldest) {
            return size() > FIT_CACHE_SIZE;
        }
    };

    // the footprint must be Misty, with solid rock between the dome and the surface, the shell clear of the deep floor
    // and the tunnel clear of it all the way up
    private boolean lakeFits(MiddleEarthMapRuntime map, Lake lake) {
        FitKey key = new FitKey(seed, lake.x, lake.z);
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
        return surface(lake.x, lake.z) >= limit && rimFits(map, lake, limit) && basinFits(map, lake) && tunnelFits(map, lake);
    }

    // twelve points around the widest outline
    private boolean rimFits(MiddleEarthMapRuntime map, Lake lake, float limit) {
        for (int i = 0; i < 12; i++) {
            double angle = i * Math.PI / 6;
            int x = lake.x + (int) (Math.cos(angle) * lake.rx * (1 + LAKE_WOBBLE));
            int z = lake.z + (int) (Math.sin(angle) * lake.rz * (1 + LAKE_WOBBLE));
            if (weight(map, x, z) < LAKE_RIM_WEIGHT || surface(x, z) < limit || !wet(x, z)) return false;
        }
        return true;
    }

    // the shell bottom, along eight spokes, stays clear of the deep floor
    private boolean basinFits(MiddleEarthMapRuntime map, Lake lake) {
        for (int i = 0; i < 8; i++) {
            double angle = i * Math.PI / 4;
            for (float share : LAKE_FIT_SHARES) {
                int x = lake.x + (int) (Math.cos(angle) * lake.rx * share);
                int z = lake.z + (int) (Math.sin(angle) * lake.rz * share);
                float inner = share / (1 - LAKE_WOBBLE);
                float bottom = lake.waterTop - (lake.depth + LAKE_SHELL) * (float) Math.sqrt(Math.max(0, 1 - inner * inner));
                if (bottom < DepthBands.deepFloor(map, x, z, seed) + LAKE_FLOOR_CLEARANCE || !wet(x, z)) return false;
            }
        }
        return true;
    }

    private boolean tunnelFits(MiddleEarthMapRuntime map, Lake lake) {
        for (int along = 0; along <= LAKE_TUNNEL_LENGTH; along += LAKE_TUNNEL_STEP) {
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

    // chamber, pillars, islands and the solid shell below and around the water. The tunnel is checked on its own
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
            if (onIsland(lake, x, y, z) || inPillar(lake, x, y, z, height, inner)) return LAKE_STONE;
            return up ? LAKE_AIR : LAKE_WATER;
        }
        if (up) return LAKE_NONE;
        float es = (top + 0.5f - y) / (lake.depth + LAKE_SHELL);
        return h2 / (shellLimit * shellLimit) + es * es < 1 ? LAKE_STONE : LAKE_NONE;
    }

    private static boolean onIsland(Lake lake, int x, int y, int z) {
        for (int i = 0; i < lake.islands; i++) {
            float dx = x - lake.islandX[i];
            float dz = z - lake.islandZ[i];
            float d2 = dx * dx + dz * dz;
            float r = lake.islandRadius[i];
            if (d2 < r * r && y <= lake.waterTop + (d2 < 0.36f * r * r ? 1 : 0)) return true;
        }
        return false;
    }

    private static boolean inPillar(Lake lake, int x, int y, int z, float height, float inner) {
        int top = lake.waterTop;
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
            if (d2 < reach * reach) return true;
        }
        return false;
    }

    // straight tunnel climbing away from the chamber's air, always above the water line
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

    static float sq(float v) {
        return v * v;
    }

    static float smoothstep(float edge0, float edge1, float x) {
        float t = MathHelper.clamp((x - edge0) / (edge1 - edge0), 0f, 1f);
        return t * t * (3 - 2 * t);
    }
}
