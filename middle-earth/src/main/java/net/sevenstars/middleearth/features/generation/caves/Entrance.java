package net.sevenstars.middleearth.features.generation.caves;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.chunk.Chunk;
import net.sevenstars.middleearth.features.generation.biomes.surface.MapBasedCustomBiome;
import net.sevenstars.middleearth.features.generation.chunkgen.map.MiddleEarthHeightMap;
import net.sevenstars.middleearth.features.generation.map.MiddleEarthMapRuntime;
import net.sevenstars.middleearth.features.generation.noise.SimplexNoise;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.sevenstars.middleearth.features.generation.caves.MistyCaves.mix;
import static net.sevenstars.middleearth.features.generation.caves.MistyCaves.smoothstep;
import static net.sevenstars.middleearth.features.generation.caves.MistyCaves.sq;
import static net.sevenstars.middleearth.features.generation.chunkgen.MiddleEarthChunkGenerator.DIRT_HEIGHT;

// a cave mouth in a steep Misty hillside, its passage running level into the slope and then down into a gallery hall;
// at most one per cell, at the steepest of a few spots
public final class Entrance {
    public enum Kind { HILLSIDE, PORCH, OUTFLOW, ICE }

    static final int CELL = 384;
    private static final int CELL_EDGE = 64;
    private static final int CHANCE = 190; // out of 256
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
    private static final int AIM_REACH = 110;
    private static final float AIM_CONE = 0.35f; // cosine of the widest turn from straight uphill, about 70 degrees
    private static final int MIN_RUN = 10;
    private static final float MAX_GRADE = 0.6f;
    private static final int ROOF_COVER = 3;

    private static final int OUTSIDE = 4; // the passage starts this far out, over the falling slope
    private static final float FLARE = 0.4f;
    private static final float FLARE_DEPTH = 7;
    private static final float WALL = 0.35f; // share of the height with straight walls, the rest is the arch
    private static final float WOBBLE = 0.18f;
    private static final float WOBBLE_SCALE = 5;
    private static final int APRON = 9;
    private static final int ICE_DEPTH = 22;
    private static final int ICICLE_CHANCE = 60; // out of 256
    private static final float GROOVE = 0.75f;
    private static final int REACH = 110; // furthest any block lies from the mouth, sideways

    private static final CellCache<Entrance> CACHE = new CellCache<>(8192);
    private static final Entrance[] NONE = new Entrance[0];

    final float mx;
    final float mz;
    final int floorY; // first air at the mouth
    final Box box;
    private final Kind kind;
    private final float dx; // into the hill
    private final float dz;
    private final float halfWidth;
    private final float height;
    private final float level; // level stretch before the descent
    private final float grade;
    private final float length;
    private final int bottom; // the hall's floor, where the passage levels out
    private final float wiggle;
    private final float freq;
    private final float phase;
    private final float room; // the chamber at the end
    private final float hallWidth; // the porch only
    private final float hallHeight;
    private final float hallDepth;
    private final float lateral; // nothing carves further than this across the axis
    private final int hash;
    private final int noiseX;
    private final int noiseZ;

    // down to target at the hall's wall, run blocks in, then level to reach
    private Entrance(Kind kind, int x, int surfaceY, int z, float dx, float dz, int h, MistyCaves misty, float run, float reach, int target) {
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
        // the porch's crack is narrow, and bending would pinch it shut on the diagonal
        this.wiggle = kind == Kind.PORCH ? 1.5f : 2 + 4 * r3;
        this.freq = (float) (Math.PI * 2 / (30 + 25 * r1));
        this.phase = (float) (Math.PI * 2 * r2);
        switch (kind) {
            case PORCH -> {
                hallWidth = 6 + 2.5f * r1;
                hallHeight = 6 + 2 * r2;
                hallDepth = 12 + 5 * r3;
                // the crack at the back
                halfWidth = 1f;
                height = 4 + r2;
                level = hallDepth * 0.8f;
                room = 4.5f;
            }
            case OUTFLOW -> {
                hallWidth = hallHeight = hallDepth = 0;
                halfWidth = 1.8f + 0.6f * r1;
                height = 3.5f + r2;
                level = 11;
                room = 5;
            }
            case ICE -> {
                hallWidth = hallHeight = hallDepth = 0;
                halfWidth = 2.8f + 1.2f * r1;
                height = 5.5f + 2.5f * r2;
                level = 8;
                room = 6;
            }
            default -> {
                hallWidth = hallHeight = hallDepth = 0;
                halfWidth = 2.6f + 1.6f * r1;
                height = 5 + 4 * r2;
                level = 6;
                room = 5 + 2 * r1;
            }
        }
        this.length = reach;
        this.bottom = target;
        this.grade = (surfaceY - target) / Math.max(1, run - level);
        float widest = Math.max(Math.max(halfWidth * (1 + FLARE), hallWidth), room);
        // the centre line strays up to twice the wiggle; the chamber sits on it
        this.lateral = 2 * wiggle + widest * (1 + WOBBLE * Galleries.NOISE_BOUND) + 1;
        float side = widest * (1 + WOBBLE) + wiggle + APRON + 2;
        float ex = mx + dx * (length + room);
        float ez = mz + dz * (length + room);
        float sx = mx - dx * (OUTSIDE + APRON);
        float sz = mz - dz * (OUTSIDE + APRON);
        this.box = new Box((int) Math.floor(Math.min(ex, sx) - side), (int) Math.ceil(Math.max(ex, sx) + side),
                floorAt(length) - (int) room - 2, floorY + (int) Math.ceil(Math.max(height * (1 + FLARE), hallHeight) * (1 + WOBBLE)) + 2,
                (int) Math.floor(Math.min(ez, sz) - side), (int) Math.ceil(Math.max(ez, sz) + side));
    }

    public static Entrance[] near(MistyCaves misty, MiddleEarthMapRuntime map, int startX, int startZ) {
        CACHE.forSeed(misty.seed);
        List<Entrance> found = null;
        int minCellX = Math.floorDiv(startX - REACH, CELL);
        int maxCellX = Math.floorDiv(startX + 15 + REACH, CELL);
        int minCellZ = Math.floorDiv(startZ - REACH, CELL);
        int maxCellZ = Math.floorDiv(startZ + 15 + REACH, CELL);
        for (int cellX = minCellX; cellX <= maxCellX; cellX++) {
            for (int cellZ = minCellZ; cellZ <= maxCellZ; cellZ++) {
                int cx = cellX;
                int cz = cellZ;
                Entrance entrance = CACHE.get(CellCache.key(cellX, cellZ), key -> inCell(misty, map, cx, cz));
                if (entrance == null || !entrance.box.touchesChunk(startX, startZ)) continue;
                if (found == null) found = new ArrayList<>(1);
                found.add(entrance);
            }
        }
        return found == null ? NONE : found.toArray(NONE);
    }

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
            // steepest first
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
            // aim into the hill at a hall: ones that lead on first, then the nearest
            GalleryLayout layout = GalleryLayout.of(misty, map);
            List<Hall> halls = layout.hallsAround(x, z, AIM_REACH);
            Map<Hall, Integer> links = new HashMap<>();
            for (Hall hall : halls) links.put(hall, layout.tunnelsOf(hall, false).size());
            halls.sort((a, b) -> links.get(a) > 0 != links.get(b) > 0 ? (links.get(a) > 0 ? -1 : 1)
                    : Float.compare(sq(a.x - x) + sq(a.z - z), sq(b.x - x) + sq(b.z - z)));
            for (Hall hall : halls) {
                float hx = hall.x - x - 0.5f;
                float hz = hall.z - z - 0.5f;
                float distance = (float) Math.sqrt(hx * hx + hz * hz);
                float ax = hx / distance;
                float az = hz / distance;
                if (ax * ux + az * uz < AIM_CONE) continue;
                float run = distance - hall.radius() * 0.9f;
                float reach = distance - hall.radius() * 0.4f;
                Entrance entrance = new Entrance(kind, x, surfaceY, z, ax, az, mix(h, i, 31), misty, run, reach, hall.floor);
                if (run - entrance.level < MIN_RUN || entrance.grade < 0 || entrance.grade > MAX_GRADE) continue;
                if (entrance.covered() && entrance.inMisty(map) && entrance.reaches(hall) && entrance.walks(layout.carving(false))) return entrance;
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

    // rock over the passage past the mouth, and ground rising behind the mouth so a thin ridge is not pierced
    private boolean covered() {
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

    private boolean inMisty(MiddleEarthMapRuntime map) {
        return MistyCaves.weight(map, (int) centreX(length * 0.5f), (int) centreZ(length * 0.5f)) >= END_WEIGHT
                && MistyCaves.weight(map, (int) centreX(length), (int) centreZ(length)) >= END_WEIGHT;
    }

    // into the hall without a drop, say where it crosses a tunnel
    private boolean walks(Walk.Carving galleries) {
        Walk.Carving air = (x, y, z) -> carves(x, y, z) || galleries.at(x, y, z);
        float s = kind == Kind.PORCH ? hallDepth * 0.5f : level + 3;
        int x = (int) Math.floor(centreX(s));
        int z = (int) Math.floor(centreZ(s));
        int y = floorAt(s);
        while (y > box.minY() && air.at(x, y - 1, z)) y--;
        return Walk.reaches(air, x, y, z, (int) Math.floor(centreX(length)), bottom, (int) Math.floor(centreZ(length)), (int) length + 8);
    }

    // ends on the hall's floor, inside it
    private boolean reaches(Hall hall) {
        float s = length - 1;
        int x = (int) Math.floor(centreX(s));
        int z = (int) Math.floor(centreZ(s));
        return Math.abs(floorAt(s) - hall.floor) <= 1 && hall.carves(x, hall.floor + 1, z) && hall.holds(x + 0.5f, z + 0.5f);
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
        return Math.max(bottom, floorY - (int) Math.ceil(grade * Math.max(0, s - level)));
    }

    // the mouth and the ground in front of it
    public boolean aroundMouth(int x, int z) {
        return box.containsColumn(x, z) && along(x, z) < level + hallDepth + 6;
    }

    public boolean carves(int x, int y, int z) {
        if (!box.contains(x, y, z)) return false;
        float rx = x + 0.5f - mx;
        float rz = z + 0.5f - mz;
        float s = rx * dx + rz * dz;
        float u = rz * dx - rx * dz;
        if (s < -OUTSIDE || s > length + room || Math.abs(u) > lateral) return false;
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

    private float along(int x, int z) {
        return (x + 0.5f - mx) * dx + (z + 0.5f - mz) * dz;
    }

    private float across(int x, int z) {
        return (z + 0.5f - mz) * dx - (x + 0.5f - mx) * dz;
    }

    // scree below the mouth, then the rock round the passage: ice, a porch floor, a spring;
    // only this chunk's blocks, each choice from a position or a hash, so chunks agree
    public void dress(Chunk chunk, BlockBuffer blocks, int[] surfaceY) {
        int startX = chunk.getPos().getStartX();
        int startZ = chunk.getPos().getStartZ();
        BlockPos.Mutable pos = new BlockPos.Mutable();
        int fromX = Math.max(box.minX(), startX);
        int toX = Math.min(box.maxX(), startX + 15);
        int fromZ = Math.max(box.minZ(), startZ);
        int toZ = Math.min(box.maxZ(), startZ + 15);
        if (fromX > toX || fromZ > toZ) return;
        CarveMask air = new CarveMask(this::carves, fromX - 1, toX + 1, box.minY() - 2, box.maxY() + 2, fromZ - 1, toZ + 1);
        for (int x = fromX; x <= toX; x++) {
            for (int z = fromZ; z <= toZ; z++) {
                apron(blocks, air, x, z, surfaceY[(x - startX) * 16 + (z - startZ)]);
                for (int y = box.minY() - 1; y <= box.maxY() + 1; y++) {
                    if (air.at(x, y, z)) continue;
                    BlockState state = blocks.get(x, y, z);
                    if (state.isAir() || !state.getFluidState().isEmpty()) continue;
                    boolean floor = air.at(x, y + 1, z);
                    boolean ceiling = air.at(x, y - 1, z);
                    boolean wall = air.at(x + 1, y, z) || air.at(x - 1, y, z) || air.at(x, y, z + 1) || air.at(x, y, z - 1);
                    if (!floor && !ceiling && !wall) continue;
                    rock(chunk, blocks, air, pos, x, y, z, floor, ceiling);
                }
            }
        }
    }

    private void apron(BlockBuffer blocks, CarveMask air, int x, int z, int surface) {
        float s = along(x, z);
        if (s > 1 || s < -APRON) return;
        float side = Math.max(halfWidth * (1 + FLARE), hallWidth) + 2;
        float u = Math.abs(across(x, z));
        if (u > side) return;
        if (air.at(x, surface, z)) return;
        BlockState top = blocks.get(x, surface, z);
        if (top.isAir() || !top.getFluidState().isEmpty()) return;
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

    private void rock(Chunk chunk, BlockBuffer blocks, CarveMask air, BlockPos.Mutable pos, int x, int y, int z, boolean floor, boolean ceiling) {
        float s = along(x, z);
        float patch = (float) SimplexNoise.noise((x + noiseZ) / 2.5, y / 2.5, (z + noiseX) / 2.5);
        switch (kind) {
            case ICE -> {
                if (s > ICE_DEPTH) return;
                // sheets near the mouth, patches further in
                float ice = (float) SimplexNoise.noise((x + noiseX) / 3.0, y / 3.0, (z + noiseZ) / 3.0);
                if (ice < 1.4f * s / ICE_DEPTH - 0.6f) return;
                if (floor) {
                    blocks.put(x, y, z, Blocks.PACKED_ICE.getDefaultState());
                    return;
                }
                blocks.put(x, y, z, ice > 0.45f ? Blocks.BLUE_ICE.getDefaultState() : Blocks.PACKED_ICE.getDefaultState());
                int drip = (mix(hash, z * 13 + y, x) >>> 16) & 0xFF;
                if (ceiling && drip < ICICLE_CHANCE && air.at(x, y - 2, z)) Icicles.hang(blocks, x, y - 1, z, drip < ICICLE_CHANCE / 2 && air.at(x, y - 3, z));
            }
            case PORCH -> {
                if (!floor || s > hallDepth || y != floorY - 1) return;
                if (patch > -0.2f) blocks.put(x, y, z, Blocks.COARSE_DIRT.getDefaultState());
                else if (patch < -0.5f) blocks.put(x, y, z, Blocks.GRAVEL.getDefaultState());
            }
            case OUTFLOW -> {
                if (!floor || s > level) return;
                if (Math.abs(across(x, z) - centre(s)) < GROOVE) {
                    // the spring rises under the roof, where it cannot freeze, and runs out once the chunk loads
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
        for (int up = y; up <= box.maxY() + 4; up++) {
            if (!blocks.get(x, up, z).isAir()) return true;
        }
        return false;
    }

}
