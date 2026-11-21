package net.sevenstars.middleearth.features.generation.caves;

import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.chunk.Chunk;
import net.sevenstars.middleearth.features.generation.map.RiverHeads;
import net.sevenstars.middleearth.features.generation.noise.SimplexNoise;

import java.util.ArrayList;
import java.util.List;

import static net.sevenstars.middleearth.features.generation.caves.GalleryLayout.ground;
import static net.sevenstars.middleearth.features.generation.caves.MistyCaves.mix;
import static net.sevenstars.middleearth.features.generation.caves.MistyCaves.sq;

// where a river once ran on up a mountain's foot it now goes under it: from a pool where the water stops, a channel
// level with the river winds in along the old course to a dark lake, the river's source. Laid out once per world seed
public final class RiverCaves {
    public static final int NONE = 0;
    public static final int AIR = 1;
    public static final int WATER = 2;

    private static final float STEP = 2;
    private static final int SMOOTHING = 2; // passes of corner cutting over the map's 32-block pixels
    private static final float MEANDER = 4;
    private static final int MOUTH_COVER = 3; // ground over the roof where the cut becomes a cave
    private static final int OPEN_GROUND = 2; // above the water by this, the river's end
    private static final float BACK_REACH = 120; // how far back down the valley the open water is looked for
    private static final float POOL_RADIUS = 6;
    private static final float POOL_BANK = 1.3f; // the bank round the pool steps back this much per block up
    private static final int POOL_BANK_TOP = 10; // the banks are cut back this far above the water, no further
    private static final float POOL_BANK_MAX = (POOL_BANK_TOP - 1) * POOL_BANK;
    private static final float BANK_WIDTH = 2;
    private static final float SHELF = 0.82f; // share of the lake's radius under water; the rest a dry shelf
    private static final int QUIET = 6; // other caves keep this far off, so the water stays in
    private static final int GRID = 32;
    private static final float WOBBLE = 0.1f;
    static final float SWELL = 1 + WOBBLE * Galleries.NOISE_BOUND;
    private static final long SALT = 0x52495645524c4f4eL;

    private static volatile RiverCaves current;
    private static volatile int[][] bounds;

    final long seed;
    final int noiseX;
    final int noiseZ;
    private final List<Piece> pieces = new ArrayList<>();
    private final Long2ObjectOpenHashMap<List<Piece>> grid = new Long2ObjectOpenHashMap<>();

    private RiverCaves(long seed) {
        this.seed = seed;
        this.noiseX = (int) Math.floorMod(seed ^ SALT, 4096L) + 311;
        this.noiseZ = (int) Math.floorMod((seed ^ SALT) >>> 20, 4096L) + 977;
    }

    // a cheap test before anything is laid out, from the heads alone
    public static boolean mayTouchChunk(int startX, int startZ) {
        for (int[] box : bounds()) {
            if (startX + 15 >= box[0] && startX <= box[1] && startZ + 15 >= box[2] && startZ <= box[3]) return true;
        }
        return false;
    }

    private static int[][] bounds() {
        int[][] known = bounds;
        if (known != null) return known;
        List<RiverHeads.Head> heads = RiverHeads.heads();
        int[][] made = new int[heads.size()][];
        for (int i = 0; i < heads.size(); i++) {
            RiverHeads.Head head = heads.get(i);
            float minX = Float.MAX_VALUE, maxX = -Float.MAX_VALUE, minZ = Float.MAX_VALUE, maxZ = -Float.MAX_VALUE;
            for (int k = 0; k < head.xs().length; k++) {
                minX = Math.min(minX, head.xs()[k]);
                maxX = Math.max(maxX, head.xs()[k]);
                minZ = Math.min(minZ, head.zs()[k]);
                maxZ = Math.max(maxZ, head.zs()[k]);
            }
            int reach = 64;
            made[i] = new int[]{(int) minX - reach, (int) maxX + reach, (int) minZ - reach, (int) maxZ + reach};
        }
        bounds = made;
        return made;
    }

    public static RiverCaves of(long seed) {
        RiverCaves known = current;
        if (known != null && known.seed == seed) return known;
        synchronized (RiverCaves.class) {
            known = current;
            if (known == null || known.seed != seed) current = known = layOut(seed);
            return known;
        }
    }

    public Near near(int startX, int startZ) {
        int x0 = startX - 8, z0 = startZ - 8, x1 = startX + 23, z1 = startZ + 23;
        List<Piece> found = new ArrayList<>();
        for (int cx = Math.floorDiv(x0, GRID); cx <= Math.floorDiv(x1, GRID); cx++) {
            for (int cz = Math.floorDiv(z0, GRID); cz <= Math.floorDiv(z1, GRID); cz++) {
                for (Piece p : grid.getOrDefault(CellCache.key(cx, cz), List.of())) {
                    if (!found.contains(p) && p.maxX >= x0 && p.minX <= x1 && p.maxZ >= z0 && p.minZ <= z1) found.add(p);
                }
            }
        }
        return found.isEmpty() ? null : new Near(this, found.toArray(new Piece[0]));
    }

    float wobble(int x, int y, int z) {
        return 1 + WOBBLE * (float) SimplexNoise.noise((x + noiseX) / 9.0, y / 6.0, (z + noiseZ) / 9.0);
    }

    // one chunk's share; used by one thread at a time
    public static final class Near {
        final RiverCaves caves;
        final Piece[] pieces;
        private int wobbleX;
        private int wobbleY = Integer.MIN_VALUE;
        private int wobbleZ;
        private float wobble;

        Near(RiverCaves caves, Piece[] pieces) {
            this.caves = caves;
            this.pieces = pieces;
        }

        float wobble(int x, int y, int z) {
            if (x != wobbleX || y != wobbleY || z != wobbleZ) {
                wobble = caves.wobble(x, y, z);
                wobbleX = x;
                wobbleY = y;
                wobbleZ = z;
            }
            return wobble;
        }

        public int state(int x, int y, int z) {
            for (Piece p : pieces) {
                int s = p.state(this, x, y, z);
                if (s != NONE) return s;
            }
            return NONE;
        }

        // other caves and the cold of the Misty caves keep off the rock round the water
        public boolean quiet(int x, int y, int z) {
            for (Piece p : pieces) {
                if (p.quiet(x, y, z)) return true;
            }
            return false;
        }

        public boolean nearPool(int x, int z) {
            for (Piece p : pieces) {
                if (p instanceof Pool pool && sq(x + 0.5f - pool.x) + sq(z + 0.5f - pool.z) < sq(pool.radius + POOL_BANK_MAX + 3)) return true;
            }
            return false;
        }

        public void dress(Chunk chunk, BlockBuffer blocks) {
            RiverCaveDressing.dress(this, chunk, blocks);
        }
    }

    // LAYOUT

    private static RiverCaves layOut(long seed) {
        RiverCaves made = new RiverCaves(seed);
        List<RiverHeads.Head> heads = RiverHeads.heads();
        for (int i = 0; i < heads.size(); i++) made.lay(heads.get(i), mix((int) seed, (int) (seed >>> 32), i * 7919 + 13));
        for (Piece p : made.pieces) {
            for (int cx = Math.floorDiv(p.minX, GRID); cx <= Math.floorDiv(p.maxX, GRID); cx++) {
                for (int cz = Math.floorDiv(p.minZ, GRID); cz <= Math.floorDiv(p.maxZ, GRID); cz++) {
                    made.grid.computeIfAbsent(CellCache.key(cx, cz), k -> new ArrayList<>()).add(p);
                }
            }
        }
        return made;
    }

    private void lay(RiverHeads.Head head, int hash) {
        int water = head.waterHeight();
        float[][] line = reachWater(smooth(head.xs(), head.zs()), water);
        List<float[]> course = resample(line, hash);
        if (course.size() < 8) return;
        float roofTop = (4.5f + 1.5f) * SWELL;
        // the river ends where its banks first stand clear of the water, and the cut becomes a cave under enough rock
        int mouth = -1;
        for (int i = 0; i < course.size(); i++) {
            float[] p = course.get(i);
            if (ground((int) p[0], (int) p[1]) >= water + roofTop + MOUTH_COVER) {
                mouth = i;
                break;
            }
        }
        if (mouth < 0) return;
        int end = 0;
        for (int i = mouth; i >= 0; i--) {
            float[] p = course.get(i);
            if (ground((int) p[0], (int) p[1]) <= water + OPEN_GROUND) {
                end = i;
                break;
            }
        }
        float[] at = course.get(end);
        pieces.add(new Pool(at[0], at[1], water, POOL_RADIUS + 1.5f * u(hash, 1)));
        float phase = 100 * u(hash, 2);
        Node previous = null;
        for (int i = end; i < course.size(); i++) {
            float[] p = course.get(i);
            float s = p[2];
            float w = 2.4f + 0.8f * noise(s / 31 + phase, 1);
            float h = 4.5f + 1.5f * noise(s / 43 + phase, 2);
            float bankNoise = noise(s / 29 + phase, 3);
            int bank = bankNoise > 0.25f ? 1 : bankNoise < -0.25f ? -1 : 0;
            int depth = 2 + Math.round(1.2f * (noise(s / 23 + phase, 4) * 0.5f + 0.5f));
            Node node = new Node(p[0], p[1], s, w, h, bank, depth);
            if (previous != null) pieces.add(new Segment(previous, node, water));
            previous = node;
        }
        // the lake, a little on past the old tip
        float[] last = course.getLast();
        float[] before = course.get(course.size() - 4);
        float dx = last[0] - before[0];
        float dz = last[1] - before[1];
        float len = (float) Math.sqrt(dx * dx + dz * dz);
        float radius = 10 + 5 * u(hash, 3);
        float cx = last[0] + dx / len * radius * 0.6f;
        float cz = last[1] + dz / len * radius * 0.6f;
        pieces.add(new Lake(cx, cz, water, radius, 9 + 3 * u(hash, 4), 5 + Math.round(3 * u(hash, 5)), hash));
    }

    // the map's river stops short of its last pixel once baked; the course is led back to the nearest open water
    private static float[][] reachWater(float[][] line, int water) {
        float[] x = line[0];
        float[] z = line[1];
        float bestX = 0;
        float bestZ = 0;
        float best = Float.MAX_VALUE;
        for (float dx = -BACK_REACH; dx <= BACK_REACH; dx += 4) {
            for (float dz = -BACK_REACH; dz <= BACK_REACH; dz += 4) {
                float d2 = dx * dx + dz * dz;
                if (d2 >= best || d2 > BACK_REACH * BACK_REACH) continue;
                if (ground((int) (x[0] + dx), (int) (z[0] + dz)) > water + OPEN_GROUND) continue;
                best = d2;
                bestX = dx;
                bestZ = dz;
            }
        }
        if (best == Float.MAX_VALUE || best < 1) return line;
        float len = (float) Math.sqrt(best);
        float[] nx = new float[x.length + 1];
        float[] nz = new float[z.length + 1];
        nx[0] = x[0] + bestX / len * (len + 4);
        nz[0] = z[0] + bestZ / len * (len + 4);
        System.arraycopy(x, 0, nx, 1, x.length);
        System.arraycopy(z, 0, nz, 1, z.length);
        return new float[][]{nx, nz};
    }

    // corner cutting, so the course bends instead of turning at the pixel corners
    private static float[][] smooth(float[] xs, float[] zs) {
        float[] x = xs;
        float[] z = zs;
        for (int pass = 0; pass < SMOOTHING; pass++) {
            float[] nx = new float[(x.length - 1) * 2];
            float[] nz = new float[(z.length - 1) * 2];
            for (int i = 0; i < x.length - 1; i++) {
                nx[2 * i] = 0.75f * x[i] + 0.25f * x[i + 1];
                nz[2 * i] = 0.75f * z[i] + 0.25f * z[i + 1];
                nx[2 * i + 1] = 0.25f * x[i] + 0.75f * x[i + 1];
                nz[2 * i + 1] = 0.25f * z[i] + 0.75f * z[i + 1];
            }
            nx[0] = x[0];
            nz[0] = z[0];
            nx[nx.length - 1] = x[x.length - 1];
            nz[nz.length - 1] = z[z.length - 1];
            x = nx;
            z = nz;
        }
        return new float[][]{x, z};
    }

    // points every STEP along the line, pushed sideways a little so the channel winds; {x, z, along}
    private static List<float[]> resample(float[][] line, int hash) {
        List<float[]> out = new ArrayList<>();
        float phase = 100 * u(hash, 6);
        float[] x = line[0];
        float[] z = line[1];
        float along = 0;
        float carry = 0;
        for (int i = 0; i < x.length - 1; i++) {
            float dx = x[i + 1] - x[i];
            float dz = z[i + 1] - z[i];
            float len = (float) Math.sqrt(dx * dx + dz * dz);
            if (len < 1e-3f) continue;
            for (float d = carry; d < len; d += STEP) {
                float s = along + d;
                float side = MEANDER * noise(s / 37 + phase, 5) * Math.min(1, s / 24);
                out.add(new float[]{x[i] + dx / len * d - dz / len * side, z[i] + dz / len * d + dx / len * side, s});
            }
            carry = (float) (Math.ceil((len - carry) / STEP) * STEP + carry - len);
            along += len;
        }
        return out;
    }

    static float u(int h, int k) {
        return (mix(h, k, k * 31) & 0xFFFF) / 65535f;
    }

    static float noise(float t, int salt) {
        return (float) SimplexNoise.noise(t, salt * 17.3 + 401);
    }

    // PIECES

    abstract static class Piece {
        int minX, maxX, minZ, maxZ;

        abstract int state(Near near, int x, int y, int z);

        abstract boolean quiet(int x, int y, int z);
    }

    record Node(float x, float z, float along, float w, float h, int bank, int depth) {
    }

    // one stretch of the channel: water between rock walls, a ledge along one side now and then, an arch over both
    static final class Segment extends Piece {
        final Node a;
        final Node b;
        final int water;
        final float dx;
        final float dz;
        final float length2;
        final float length;
        final int minY;

        Segment(Node a, Node b, int water) {
            this.a = a;
            this.b = b;
            this.water = water;
            this.dx = b.x() - a.x();
            this.dz = b.z() - a.z();
            this.length2 = Math.max(1e-4f, dx * dx + dz * dz);
            this.length = (float) Math.sqrt(length2);
            float reach = (Math.max(a.w(), b.w()) + BANK_WIDTH + 1) * SWELL + QUIET + 1;
            this.minX = (int) Math.floor(Math.min(a.x(), b.x()) - reach);
            this.maxX = (int) Math.ceil(Math.max(a.x(), b.x()) + reach);
            this.minZ = (int) Math.floor(Math.min(a.z(), b.z()) - reach);
            this.maxZ = (int) Math.ceil(Math.max(a.z(), b.z()) + reach);
            this.minY = water - Math.max(a.depth(), b.depth()) - QUIET;
        }

        float project(float px, float pz) {
            return MathHelper.clamp(((px - a.x()) * dx + (pz - a.z()) * dz) / length2, 0f, 1f);
        }

        @Override
        int state(Near near, int x, int y, int z) {
            if (x < minX || x > maxX || z < minZ || z > maxZ || y < minY || y > water + 9) return NONE;
            float px = x + 0.5f;
            float pz = z + 0.5f;
            float t = project(px, pz);
            float lx = px - a.x() - dx * t;
            float lz = pz - a.z() - dz * t;
            float u = (float) Math.sqrt(lx * lx + lz * lz);
            float side = Math.signum((px - a.x()) * dz - (pz - a.z()) * dx);
            float w = MathHelper.lerp(t, a.w(), b.w());
            int bank = t < 0.5f ? a.bank() : b.bank();
            boolean onBank = bank != 0 && side == bank;
            float half = w + (onBank ? BANK_WIDTH : 0) + 0.5f;
            if (u >= half * SWELL) return NONE;
            int dy = y - water;
            float wob = near.wobble(x, y, z);
            if (dy <= 0) {
                if (u >= w * wob) return NONE;
                float depth = t < 0.5f ? a.depth() : b.depth();
                float here = Math.max(1, depth * (float) Math.sqrt(Math.max(0, 1 - sq(u / (w * wob)))));
                return -dy < here ? WATER : NONE;
            }
            if (u >= half * wob) return NONE;
            if (u >= w * wob && onBank && dy <= 1) return NONE;
            float h = MathHelper.lerp(t, a.h(), b.h());
            float roof = h * (float) Math.sqrt(Math.max(0, 1 - sq(u / (half * wob))));
            return dy < Math.max(2.5f, roof) * wob ? AIR : NONE;
        }

        @Override
        boolean quiet(int x, int y, int z) {
            if (x < minX || x > maxX || z < minZ || z > maxZ || y < minY) return false;
            float px = x + 0.5f;
            float pz = z + 0.5f;
            float t = project(px, pz);
            float u2 = sq(px - a.x() - dx * t) + sq(pz - a.z() - dz * t);
            float w = MathHelper.lerp(t, a.w(), b.w());
            return u2 < sq((w + BANK_WIDTH + 0.5f) * SWELL + QUIET);
        }

        float along(float px, float pz) {
            return MathHelper.lerp(project(px, pz), a.along(), b.along());
        }
    }

    // the dark lake the river comes from: a dome over deep still water and a dry shelf round it
    static final class Lake extends Piece {
        final float x;
        final float z;
        final int water;
        final float radius;
        final float dome;
        final int depth;
        final int hash;

        Lake(float x, float z, int water, float radius, float dome, int depth, int hash) {
            this.x = x;
            this.z = z;
            this.water = water;
            this.radius = radius;
            this.dome = dome;
            this.depth = depth;
            this.hash = hash;
            int reach = (int) Math.ceil(radius * 1.25f * SWELL) + QUIET + 1;
            this.minX = (int) x - reach;
            this.maxX = (int) x + reach;
            this.minZ = (int) z - reach;
            this.maxZ = (int) z + reach;
        }

        // the outline wanders with the angle, so the lake is no circle
        float reach(float px, float pz) {
            float angle = (float) Math.atan2(pz - z, px - x);
            return radius * (1 + 0.2f * (float) SimplexNoise.noise(Math.cos(angle) * 1.3 + (hash & 0xFF), Math.sin(angle) * 1.3));
        }

        @Override
        int state(Near near, int bx, int y, int bz) {
            if (bx < minX || bx > maxX || bz < minZ || bz > maxZ || y < water - depth - 1 || y > water + dome * SWELL + 1) return NONE;
            float px = bx + 0.5f;
            float pz = bz + 0.5f;
            float r = (float) Math.sqrt(sq(px - x) + sq(pz - z)) / reach(px, pz);
            float wob = near.wobble(bx, y, bz);
            int dy = y - water;
            if (dy <= 0) {
                if (r >= SHELF * wob) return NONE;
                float here = 1 + (depth - 1) * (1 - sq(r / SHELF));
                return -dy < here ? WATER : NONE;
            }
            if (r >= wob) return NONE;
            if (r >= SHELF * wob && dy <= 1) return NONE;
            return dy < dome * (float) Math.sqrt(1 - r * r) * wob ? AIR : NONE;
        }

        @Override
        boolean quiet(int bx, int y, int bz) {
            if (bx < minX || bx > maxX || bz < minZ || bz > maxZ || y < water - depth - QUIET) return false;
            return sq(bx + 0.5f - x) + sq(bz + 0.5f - z) < sq(radius * 1.25f * SWELL + QUIET);
        }
    }

    // where the open river stops: a round pool, its banks stepping back as they rise
    static final class Pool extends Piece {
        final float x;
        final float z;
        final int water;
        final float radius;

        Pool(float x, float z, int water, float radius) {
            this.x = x;
            this.z = z;
            this.water = water;
            this.radius = radius;
            int reach = (int) Math.ceil(radius + POOL_BANK_MAX) + 1;
            this.minX = (int) x - reach;
            this.maxX = (int) x + reach;
            this.minZ = (int) z - reach;
            this.maxZ = (int) z + reach;
        }

        @Override
        int state(Near near, int bx, int y, int bz) {
            if (bx < minX || bx > maxX || bz < minZ || bz > maxZ || y < water - 4 || y > water + POOL_BANK_TOP) return NONE;
            float d = (float) Math.sqrt(sq(bx + 0.5f - x) + sq(bz + 0.5f - z));
            int dy = y - water;
            if (dy <= 0) {
                if (d >= radius) return NONE;
                return -dy < 1 + 2 * (1 - sq(d / radius)) ? WATER : NONE;
            }
            return d < radius + (dy - 1) * POOL_BANK ? AIR : NONE;
        }

        @Override
        boolean quiet(int bx, int y, int bz) {
            return false;
        }
    }
}
