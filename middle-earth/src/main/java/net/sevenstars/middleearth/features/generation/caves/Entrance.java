package net.sevenstars.middleearth.features.generation.caves;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.chunk.Chunk;
import net.sevenstars.middleearth.block.registration.NatureBlockRegistryME;
import net.sevenstars.middleearth.block.special.DroopingIciclesBlock;
import net.sevenstars.middleearth.features.generation.biomes.surface.MapBasedCustomBiome;
import net.sevenstars.middleearth.features.generation.chunkgen.map.MiddleEarthHeightMap;
import net.sevenstars.middleearth.features.generation.map.MiddleEarthMapRuntime;
import net.sevenstars.middleearth.features.generation.noise.SimplexNoise;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static net.sevenstars.middleearth.features.generation.caves.MistyCaves.mix;
import static net.sevenstars.middleearth.features.generation.chunkgen.MiddleEarthChunkGenerator.DIRT_HEIGHT;

// a cave mouth in a steep Misty hillside: a passage runs level into the slope, then down until it meets a rift
// at most one per cell, at the steepest of a few spots, and only where the passage really reaches a rift
public final class Entrance {
    public enum Kind { HILLSIDE, PORCH, OUTFLOW, ICE }

    static final int CELL = 384;
    private static final int CELL_EDGE = 64;
    private static final int CHANCE = 190; // out of 256, before the slope and the rift have their say
    private static final int CANDIDATES = 24;
    private static final int TRIES = 6;
    private static final int SALT = 7741;
    private static final int SLOPE_STEP = 8;
    private static final float MIN_SLOPE = 0.8f; // rise per block, about 39 degrees
    private static final float PORCH_SLOPE = 1.1f;
    private static final int MIN_SURFACE_Y = 110;
    private static final float MIN_WEIGHT = 0.95f;
    private static final float END_WEIGHT = 0.6f;
    private static final int OUTFLOW_MAX_Y = 210;
    private static final float[] HEADINGS = {0f, 0.45f, -0.45f}; // radians off straight uphill
    private static final int ROOF_COVER = 3; // rock over the passage once it is in the hill
    private static final float CONNECT_MARGIN = 0.03f;

    private static final int OUTSIDE = 4; // the passage starts this far out from the mouth, over the falling slope
    private static final float FLARE = 0.4f;
    private static final float FLARE_DEPTH = 7;
    private static final float WALL = 0.35f; // share of the height with straight walls, the rest is the arch
    private static final float WOBBLE = 0.18f;
    private static final float WOBBLE_SCALE = 5;
    private static final int APRON = 9;
    private static final int ICE_DEPTH = 22;
    private static final int ICICLE_CHANCE = 60; // out of 256, under an ice ceiling
    private static final float GROOVE = 0.75f;
    static final int REACH = 110; // furthest any block of an entrance lies from its mouth, sideways

    private static final Map<Long, Entrance> CACHE = new ConcurrentHashMap<>();
    private static final Entrance NONE = new Entrance();
    private static final int CACHE_LIMIT = 8192;
    private static volatile long cacheSeed = Long.MIN_VALUE;

    final Kind kind;
    final float mx;
    final float mz;
    final int floorY; // first air block at the mouth
    final float dx; // into the hill
    final float dz;
    final float halfWidth;
    final float height;
    final float level; // level stretch before the descent
    final float grade;
    final float length;
    final float wiggle;
    final float freq;
    final float phase;
    final float room; // radius of the chamber at the end
    final float hallWidth; // the porch only
    final float hallHeight;
    final float hallDepth;
    final int hash;
    final int noiseX;
    final int noiseZ;
    final int minX, maxX, minZ, maxZ, minY, maxY;

    private Entrance() {
        kind = null;
        mx = mz = dx = dz = halfWidth = height = level = grade = length = wiggle = freq = phase = room = hallWidth = hallHeight = hallDepth = 0;
        floorY = hash = noiseX = noiseZ = minX = maxX = minZ = maxZ = minY = maxY = 0;
    }

    private Entrance(Kind kind, int x, int surfaceY, int z, float dx, float dz, int h, MistyCaves misty) {
        this.kind = kind;
        this.mx = x + 0.5f;
        this.mz = z + 0.5f;
        this.floorY = surfaceY;
        this.dx = dx;
        this.dz = dz;
        this.hash = h;
        this.noiseX = misty.offsetX + 1733;
        this.noiseZ = misty.offsetZ + 2689;
        float r1 = (mix(h, 1, 0) & 0xFF) / 255f;
        float r2 = (mix(h, 2, 0) & 0xFF) / 255f;
        float r3 = (mix(h, 3, 0) & 0xFF) / 255f;
        // the porch's crack is narrow, so it barely bends or it pinches shut on the diagonal
        this.wiggle = kind == Kind.PORCH ? 1.5f : 2 + 4 * r3;
        this.freq = (float) (Math.PI * 2 / (30 + 25 * r1));
        this.phase = (float) (Math.PI * 2 * r2);
        switch (kind) {
            case PORCH -> {
                hallWidth = 6 + 2.5f * r1;
                hallHeight = 6 + 2 * r2;
                hallDepth = 12 + 5 * r3;
                // the crack at the back is the way on
                halfWidth = 1f;
                height = 4 + r2;
                level = hallDepth * 0.8f;
                grade = 0.6f;
                length = level + 34 + 10 * r1;
                room = 4.5f;
            }
            case OUTFLOW -> {
                hallWidth = hallHeight = hallDepth = 0;
                halfWidth = 1.8f + 0.6f * r1;
                height = 3.5f + r2;
                level = 11;
                grade = 0.35f;
                length = 46 + 12 * r3;
                room = 5;
            }
            case ICE -> {
                hallWidth = hallHeight = hallDepth = 0;
                halfWidth = 2.8f + 1.2f * r1;
                height = 5.5f + 2.5f * r2;
                level = 8;
                grade = 0.45f;
                length = 38 + 16 * r3;
                room = 6;
            }
            default -> {
                hallWidth = hallHeight = hallDepth = 0;
                halfWidth = 2.6f + 1.6f * r1;
                height = 5 + 4 * r2;
                level = 6;
                grade = 0.45f;
                length = 36 + 20 * r3;
                room = 5 + 2 * r1;
            }
        }
        float side = Math.max(Math.max(halfWidth * (1 + FLARE), hallWidth), room) * (1 + WOBBLE) + wiggle + APRON + 2;
        float ex = mx + dx * (length + room);
        float ez = mz + dz * (length + room);
        float sx = mx - dx * (OUTSIDE + APRON);
        float sz = mz - dz * (OUTSIDE + APRON);
        this.minX = (int) Math.floor(Math.min(ex, sx) - side);
        this.maxX = (int) Math.ceil(Math.max(ex, sx) + side);
        this.minZ = (int) Math.floor(Math.min(ez, sz) - side);
        this.maxZ = (int) Math.ceil(Math.max(ez, sz) + side);
        this.minY = floorAt(length) - (int) room - 2;
        this.maxY = floorY + (int) Math.ceil(Math.max(height * (1 + FLARE), hallHeight) * (1 + WOBBLE)) + 2;
    }

    // the entrances whose blocks may reach into a chunk
    public static Entrance[] near(MistyCaves misty, MiddleEarthMapRuntime map, int startX, int startZ) {
        if (cacheSeed != misty.seed) {
            CACHE.clear();
            cacheSeed = misty.seed;
        }
        List<Entrance> found = null;
        int minCellX = Math.floorDiv(startX - REACH, CELL);
        int maxCellX = Math.floorDiv(startX + 15 + REACH, CELL);
        int minCellZ = Math.floorDiv(startZ - REACH, CELL);
        int maxCellZ = Math.floorDiv(startZ + 15 + REACH, CELL);
        for (int cellX = minCellX; cellX <= maxCellX; cellX++) {
            for (int cellZ = minCellZ; cellZ <= maxCellZ; cellZ++) {
                long key = (long) cellX << 32 | (cellZ & 0xFFFFFFFFL);
                Entrance entrance = CACHE.get(key);
                if (entrance == null) {
                    entrance = inCell(misty, map, cellX, cellZ);
                    if (entrance == null) entrance = NONE;
                    if (CACHE.size() >= CACHE_LIMIT) CACHE.clear();
                    CACHE.put(key, entrance);
                }
                if (entrance == NONE || entrance.maxX < startX || entrance.minX > startX + 15 || entrance.maxZ < startZ || entrance.minZ > startZ + 15) continue;
                if (found == null) found = new ArrayList<>(1);
                found.add(entrance);
            }
        }
        return found == null ? NO_ENTRANCES : found.toArray(NO_ENTRANCES);
    }

    static final Entrance[] NO_ENTRANCES = new Entrance[0];

    private static Entrance inCell(MistyCaves misty, MiddleEarthMapRuntime map, int cellX, int cellZ) {
        int h = mix(cellX, cellZ, Long.hashCode(misty.seed) ^ SALT);
        if ((h & 0xFF) >= CHANCE) return null;
        int span = CELL - 2 * CELL_EDGE;
        int[] xs = new int[CANDIDATES];
        int[] zs = new int[CANDIDATES];
        float[] slopes = new float[CANDIDATES];
        float[] gxs = new float[CANDIDATES];
        float[] gzs = new float[CANDIDATES];
        int count = 0;
        for (int i = 0; i < CANDIDATES; i++) {
            int hc = mix(h, i, 17);
            int x = cellX * CELL + CELL_EDGE + Math.floorMod(hc, span);
            int z = cellZ * CELL + CELL_EDGE + Math.floorMod(hc >>> 12, span);
            if (!MistyCaves.isMisty(map, x, z)) continue;
            float gx = (height(x + SLOPE_STEP, z) - height(x - SLOPE_STEP, z)) / (2 * SLOPE_STEP);
            float gz = (height(x, z + SLOPE_STEP) - height(x, z - SLOPE_STEP)) / (2 * SLOPE_STEP);
            float slope = (float) Math.sqrt(gx * gx + gz * gz);
            if (slope < MIN_SLOPE) continue;
            // insertion by slope, steepest first
            int at = count++;
            while (at > 0 && slopes[at - 1] < slope) {
                xs[at] = xs[at - 1];
                zs[at] = zs[at - 1];
                slopes[at] = slopes[at - 1];
                gxs[at] = gxs[at - 1];
                gzs[at] = gzs[at - 1];
                at--;
            }
            xs[at] = x;
            zs[at] = z;
            slopes[at] = slope;
            gxs[at] = gx;
            gzs[at] = gz;
        }
        for (int i = 0; i < Math.min(count, TRIES); i++) {
            int x = xs[i];
            int z = zs[i];
            int surfaceY = (int) (DIRT_HEIGHT + height(x, z));
            if (surfaceY < MIN_SURFACE_Y || MistyCaves.weight(map, x, z) < MIN_WEIGHT) continue;
            Kind kind = kind(map, x, z, surfaceY, slopes[i], h);
            float ux = gxs[i] / slopes[i];
            float uz = gzs[i] / slopes[i];
            for (float turn : HEADINGS) {
                float cos = MathHelper.cos(turn);
                float sin = MathHelper.sin(turn);
                Entrance entrance = new Entrance(kind, x, surfaceY, z, ux * cos - uz * sin, ux * sin + uz * cos, mix(h, i, 31), misty);
                if (entrance.covered() && entrance.inMisty(map) && entrance.connects(misty, map)) return entrance;
            }
        }
        return null;
    }

    private static Kind kind(MiddleEarthMapRuntime map, int x, int z, int surfaceY, float slope, int h) {
        int roll = (h >>> 8) & 0xFF;
        if (snowy(map, x, z)) return roll < 200 ? Kind.ICE : Kind.HILLSIDE;
        if (surfaceY < OUTFLOW_MAX_Y && roll < 80) return Kind.OUTFLOW;
        if (slope >= PORCH_SLOPE && roll >= 80 && roll < 150) return Kind.PORCH;
        return Kind.HILLSIDE;
    }

    // the peaks, whose gentlest ground is snow
    private static boolean snowy(MiddleEarthMapRuntime map, int x, int z) {
        MapBasedCustomBiome biome = map.getBiome(x, z);
        return biome != null && biome.getBiome() != null && biome.getBiome().getSlopeMap().slopeDatas.getFirst().block == Blocks.SNOW_BLOCK;
    }

    private static float height(int x, int z) {
        return MiddleEarthHeightMap.getHeight(x, z);
    }

    // rock over the whole passage once it is past the mouth
    private boolean covered() {
        // the ground keeps rising behind the mouth, so a thin ridge is not pierced before the roof closes
        for (float s = 2; s < level + 4; s += 2) {
            float rise = Math.min(s * 0.6f, height + ROOF_COVER);
            if (DIRT_HEIGHT + height((int) Math.floor(centreX(s)), (int) Math.floor(centreZ(s))) < floorY + rise) return false;
        }
        float from = kind == Kind.PORCH ? hallDepth * 0.6f : level + 4;
        for (float s = from; s <= length; s += 4) {
            float top = kind == Kind.PORCH && s <= hallDepth ? floorY + hallHeight : floorAt(s) + height;
            int x = (int) Math.floor(centreX(s));
            int z = (int) Math.floor(centreZ(s));
            if (DIRT_HEIGHT + height(x, z) < top + ROOF_COVER) return false;
        }
        return true;
    }

    // so every chunk the passage crosses has the Misty caves to meet it
    private boolean inMisty(MiddleEarthMapRuntime map) {
        return MistyCaves.weight(map, (int) centreX(length * 0.5f), (int) centreZ(length * 0.5f)) >= END_WEIGHT
                && MistyCaves.weight(map, (int) centreX(length), (int) centreZ(length)) >= END_WEIGHT;
    }

    // some of the far half of the passage or its chamber opens onto a rift
    private boolean connects(MistyCaves misty, MiddleEarthMapRuntime map) {
        for (float s = length * 0.5f; s <= length + room; s += 2) {
            float along = Math.min(s, length);
            int x = (int) Math.floor(centreX(along) + dx * (s - along));
            int z = (int) Math.floor(centreZ(along) + dz * (s - along));
            int floor = floorAt(along);
            for (int y = floor; y < floor + height; y += 2) {
                if (misty.riftMarginAt(map, x, y, z) > CONNECT_MARGIN) return true;
            }
        }
        return false;
    }

    private float centre(float s) {
        if (s <= 0) return 0;
        return wiggle * (MathHelper.sin(s * freq + phase) - MathHelper.sin(phase)) * smoothstep(0, 12, s);
    }

    private float centreX(float s) {
        return mx + dx * s - dz * centre(s);
    }

    private float centreZ(float s) {
        return mz + dz * s + dx * centre(s);
    }

    private int floorAt(float s) {
        return floorY - (int) (grade * Math.max(0, s - level));
    }

    boolean inBox(int x, int z) {
        return x >= minX && x <= maxX && z >= minZ && z <= maxZ;
    }

    boolean inBox(int x, int y, int z) {
        return y >= minY && y <= maxY && inBox(x, z);
    }

    // the mouth and the ground in front of it
    public boolean aroundMouth(int x, int z) {
        return inBox(x, z) && along(x, z) < level + hallDepth + 6;
    }

    // air of the entrance
    public boolean carves(int x, int y, int z) {
        if (!inBox(x, y, z)) return false;
        float rx = x + 0.5f - mx;
        float rz = z + 0.5f - mz;
        float s = rx * dx + rz * dz;
        float u = rz * dx - rx * dz;
        if (s < -OUTSIDE || s > length + room) return false;
        float wob = 1 + WOBBLE * (float) SimplexNoise.noise((x + noiseX) / WOBBLE_SCALE, y / WOBBLE_SCALE, (z + noiseZ) / WOBBLE_SCALE);
        if (kind == Kind.PORCH && hall(s, u, y, wob)) return true;
        if (passage(s, u, y, wob)) return true;
        return chamber(x, y, z, wob);
    }

    private boolean hall(float s, float u, int y, float wob) {
        if (s > hallDepth) return false;
        int dy = y - floorY;
        if (dy < 0) return false;
        float q = sq(u / (hallWidth * wob)) + sq(Math.max(0, s) / hallDepth) + sq(dy / (hallHeight * wob));
        return q < 1;
    }

    private boolean passage(float s, float u, int y, float wob) {
        float start = kind == Kind.PORCH ? hallDepth * 0.6f : -OUTSIDE;
        if (s < start || s > length) return false;
        int dy = y - floorAt(s);
        if (dy < 0) return false;
        float flare = kind == Kind.PORCH ? 1 : 1 + FLARE * (1 - smoothstep(0, FLARE_DEPTH, s));
        float h = height * flare * wob;
        if (dy >= h) return false;
        float t = dy / h;
        float w = halfWidth * flare * wob;
        if (t > WALL) w *= (float) Math.sqrt(1 - sq((t - WALL) / (1 - WALL)));
        return Math.abs(u - centre(s)) < w;
    }

    private boolean chamber(int x, int y, int z, float wob) {
        float cx = centreX(length);
        float cz = centreZ(length);
        int floor = floorAt(length);
        float r = room * wob;
        if (y < floor - 1) return false;
        float cy = floor + r * 0.5f;
        return sq((x + 0.5f - cx) / r) + sq((y + 0.5f - cy) / (r * 0.7f)) + sq((z + 0.5f - cz) / r) < 1;
    }

    // along the passage and across it, from the mouth
    private float along(int x, int z) {
        return (x + 0.5f - mx) * dx + (z + 0.5f - mz) * dz;
    }

    private float across(int x, int z) {
        return (z + 0.5f - mz) * dx - (x + 0.5f - mx) * dz;
    }

    // scree below the mouth, then the dressing of its rock: ice, a porch floor, a spring
    // only blocks of this chunk are written, and every choice is a hash of its own position, so chunks agree
    public void dress(Chunk chunk, BlockBuffer blocks, int[] surfaceY) {
        int startX = chunk.getPos().getStartX();
        int startZ = chunk.getPos().getStartZ();
        BlockPos.Mutable pos = new BlockPos.Mutable();
        int fromX = Math.max(minX, startX);
        int toX = Math.min(maxX, startX + 15);
        int fromZ = Math.max(minZ, startZ);
        int toZ = Math.min(maxZ, startZ + 15);
        for (int x = fromX; x <= toX; x++) {
            for (int z = fromZ; z <= toZ; z++) {
                apron(blocks, x, z, surfaceY[(x - startX) * 16 + (z - startZ)]);
                for (int y = minY - 1; y <= maxY + 1; y++) {
                    if (carves(x, y, z)) continue;
                    BlockState state = blocks.get(x, y, z);
                    if (state.isAir() || !state.getFluidState().isEmpty()) continue;
                    boolean floor = carves(x, y + 1, z);
                    boolean ceiling = carves(x, y - 1, z);
                    boolean wall = carves(x + 1, y, z) || carves(x - 1, y, z) || carves(x, y, z + 1) || carves(x, y, z - 1);
                    if (!floor && !ceiling && !wall) continue;
                    rock(chunk, blocks, pos, x, y, z, floor, ceiling);
                }
            }
        }
    }

    private void apron(BlockBuffer blocks, int x, int z, int surface) {
        float s = along(x, z);
        if (s > 1 || s < -APRON) return;
        float side = Math.max(halfWidth * (1 + FLARE), hallWidth) + 2;
        float u = Math.abs(across(x, z));
        if (u > side) return;
        if (carves(x, surface, z)) return;
        BlockState top = blocks.get(x, surface, z);
        if (top.isAir() || !top.getFluidState().isEmpty()) return;
        // a fan of patches, not single blocks
        float fan = (1 + Math.min(0, s) / APRON) * (1 - u / side);
        float patch = 0.5f + 0.5f * (float) SimplexNoise.noise((x + noiseX) / 3.5, (z + noiseZ) / 3.5);
        if (0.9f * fan + 0.5f * patch < 0.8f) return;
        boolean second = SimplexNoise.noise((x + noiseZ) / 2.5, (z + noiseX) / 2.5) > 0.2;
        BlockState scree = switch (kind) {
            case ICE -> Blocks.SNOW_BLOCK.getDefaultState();
            case OUTFLOW -> second ? Blocks.MOSSY_COBBLESTONE.getDefaultState() : Blocks.GRAVEL.getDefaultState();
            default -> second ? Blocks.COBBLESTONE.getDefaultState() : Blocks.GRAVEL.getDefaultState();
        };
        blocks.put(x, surface, z, scree);
    }

    private void rock(Chunk chunk, BlockBuffer blocks, BlockPos.Mutable pos, int x, int y, int z, boolean floor, boolean ceiling) {
        float s = along(x, z);
        // patches of loose floor rather than single blocks
        float patch = (float) SimplexNoise.noise((x + noiseZ) / 2.5, y / 2.5, (z + noiseX) / 2.5);
        switch (kind) {
            case ICE -> {
                if (s > ICE_DEPTH) return;
                // whole sheets near the mouth, breaking into patches further in
                float ice = (float) SimplexNoise.noise((x + noiseX) / 3.0, y / 3.0, (z + noiseZ) / 3.0);
                if (ice < 1.4f * s / ICE_DEPTH - 0.6f) return;
                if (floor) {
                    blocks.put(x, y, z, Blocks.PACKED_ICE.getDefaultState());
                    return;
                }
                blocks.put(x, y, z, ice > 0.45f ? Blocks.BLUE_ICE.getDefaultState() : Blocks.PACKED_ICE.getDefaultState());
                int drip = (mix(hash, z * 13 + y, x) >>> 16) & 0xFF;
                if (ceiling && drip < ICICLE_CHANCE && carves(x, y - 2, z)) icicles(blocks, x, y - 1, z, drip);
            }
            case PORCH -> {
                if (!floor || s > hallDepth || y != floorY - 1) return;
                if (patch > -0.2f) blocks.put(x, y, z, Blocks.COARSE_DIRT.getDefaultState());
                else if (patch < -0.5f) blocks.put(x, y, z, Blocks.GRAVEL.getDefaultState());
            }
            case OUTFLOW -> {
                if (!floor || s > level) return;
                if (Math.abs(across(x, z) - centre(s)) < GROOVE) {
                    // the spring rises under the roof, where it cannot freeze, and runs out down a groove once the chunk is loaded
                    if (roofed(blocks, x, y + 1, z)) {
                        blocks.put(x, y, z, Blocks.WATER.getDefaultState());
                        chunk.markBlockForPostProcessing(pos.set(x, y, z));
                    } else {
                        blocks.put(x, y, z, Blocks.AIR.getDefaultState());
                    }
                } else if (patch > 0.1f) {
                    blocks.put(x, y, z, Blocks.MOSSY_COBBLESTONE.getDefaultState());
                } else if (patch < -0.4f) {
                    blocks.put(x, y, z, Blocks.GRAVEL.getDefaultState());
                }
            }
            default -> {
                if (!floor) return;
                if (patch > 0.35f) blocks.put(x, y, z, Blocks.GRAVEL.getDefaultState());
                else if (patch < -0.55f) blocks.put(x, y, z, Blocks.COBBLESTONE.getDefaultState());
            }
        }
    }

    private boolean roofed(BlockBuffer blocks, int x, int y, int z) {
        for (int up = y; up <= maxY + 4; up++) {
            if (!blocks.get(x, up, z).isAir()) return true;
        }
        return false;
    }

    private void icicles(BlockBuffer blocks, int x, int y, int z, int pick) {
        if (pick < ICICLE_CHANCE / 2 && carves(x, y - 2, z)) {
            blocks.put(x, y, z, NatureBlockRegistryME.DROOPING_ICICLES.getDefaultState().with(DroopingIciclesBlock.HALF, DoubleBlockHalf.UPPER));
            blocks.put(x, y - 1, z, NatureBlockRegistryME.DROOPING_ICICLES.getDefaultState().with(DroopingIciclesBlock.HALF, DoubleBlockHalf.LOWER));
        } else {
            blocks.put(x, y, z, NatureBlockRegistryME.SHORT_ICICLES.getDefaultState());
        }
    }

    private static float sq(float v) {
        return v * v;
    }

    private static float smoothstep(float edge0, float edge1, float x) {
        float t = MathHelper.clamp((x - edge0) / (edge1 - edge0), 0f, 1f);
        return t * t * (3 - 2 * t);
    }
}
