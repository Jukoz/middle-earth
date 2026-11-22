package net.sevenstars.middleearth.features.generation.caves;

import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.chunk.Chunk;
import net.sevenstars.middleearth.features.generation.map.RiverHeads;
import net.sevenstars.middleearth.features.generation.noise.SimplexNoise;

import java.util.ArrayList;
import java.util.List;

import static net.sevenstars.middleearth.features.generation.caves.GalleryLayout.ground;
import static net.sevenstars.middleearth.features.generation.caves.LavaTubes.u;
import static net.sevenstars.middleearth.features.generation.caves.MistyCaves.mix;
import static net.sevenstars.middleearth.features.generation.caves.MistyCaves.sq;

// rivers trimmed off a mountain's foot run on under it: a pool, a channel at river level, a dark lake; laid out per seed
public final class RiverCaves {
    public static final int NONE = 0;
    public static final int AIR = 1;
    public static final int WATER = 2;

    private static final float STEP = 2;
    private static final int SMOOTHING = 2; // passes of corner cutting over the map's 32-block pixels
    private static final float MEANDER = 4;
    private static final int MOUTH_COVER = 3; // ground over the roof where the cut becomes a cave
    private static final int OPEN_GROUND = 2; // above the water by this, the river's end
    private static final float BACK_REACH = 120; // how far back the open water is looked for
    private static final float POOL_RADIUS = 6;
    private static final float POOL_BANK = 1.3f; // per block up
    private static final int POOL_BANK_TOP = 10; // no bank is cut higher above the water
    private static final float POOL_BANK_MAX = (POOL_BANK_TOP - 1) * POOL_BANK;
    private static final float BANK_WIDTH = 2;
    private static final float SHELF = 0.82f; // share of the lake's radius under water; the rest is shelf
    private static final int QUIET = 6; // other caves keep this far off so the water stays in
    private static final int GRID = 32;
    private static final float WOBBLE = 0.1f;
    private static final float SWELL = 1 + WOBBLE * Galleries.NOISE_BOUND;
    private static final float ROOF = 4.5f;
    private static final float ROOF_VARY = 1.5f;
    private static final int HEAD_REACH = 64; // past the old course, for the pool and lake
    private static final int MARGIN = 8; // pieces this far outside a chunk can still matter to it
    private static final long SALT = 0x52495645524c4f4eL;

    private static volatile RiverCaves current;
    private static volatile Box[] bounds;

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

    // a cheap test from the heads alone, before anything is laid out
    public static boolean mayTouchChunk(int startX, int startZ) {
        for (Box box : bounds()) {
            if (box.touchesChunk(startX, startZ)) return true;
        }
        return false;
    }

    private static Box[] bounds() {
        Box[] known = bounds;
        if (known != null) return known;
        List<RiverHeads.Head> heads = RiverHeads.heads();
        Box[] made = new Box[heads.size()];
        for (int i = 0; i < heads.size(); i++) {
            float[] xs = heads.get(i).xs();
            float[] zs = heads.get(i).zs();
            float minX = Float.MAX_VALUE, maxX = -Float.MAX_VALUE, minZ = Float.MAX_VALUE, maxZ = -Float.MAX_VALUE;
            for (int k = 0; k < xs.length; k++) {
                minX = Math.min(minX, xs[k]);
                maxX = Math.max(maxX, xs[k]);
                minZ = Math.min(minZ, zs[k]);
                maxZ = Math.max(maxZ, zs[k]);
            }
            made[i] = new Box((int) minX - HEAD_REACH, (int) maxX + HEAD_REACH, 0, 0, (int) minZ - HEAD_REACH, (int) maxZ + HEAD_REACH);
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
        int x0 = startX - MARGIN, z0 = startZ - MARGIN, x1 = startX + 15 + MARGIN, z1 = startZ + 15 + MARGIN;
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

    // one chunk's share; not thread safe, it caches the last column and wobble
    public static final class Near {
        final RiverCaves caves;
        final Piece[] pieces;
        private final int lowY;
        private final int highY;
        private int wobbleX;
        private int wobbleY = Integer.MIN_VALUE;
        private int wobbleZ;
        private float wobble;
        private int columnX;
        private int columnZ;
        private boolean columnSet;
        private final Piece[] inColumn;
        private int inColumnCount;
        private int quietFrom;

        Near(RiverCaves caves, Piece[] pieces) {
            this.caves = caves;
            this.pieces = pieces;
            this.inColumn = new Piece[pieces.length];
            int low = Integer.MAX_VALUE, high = Integer.MIN_VALUE;
            for (Piece p : pieces) {
                low = Math.min(low, p.lowY);
                high = Math.max(high, p.highY);
            }
            this.lowY = low;
            this.highY = high;
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

        // the pieces that can reach this column, in order, and where the quiet starts
        private void column(int x, int z) {
            if (columnSet && x == columnX && z == columnZ) return;
            columnSet = true;
            columnX = x;
            columnZ = z;
            inColumnCount = 0;
            quietFrom = Integer.MAX_VALUE;
            for (Piece p : pieces) {
                if (p.reaches(x, z)) inColumn[inColumnCount++] = p;
                quietFrom = Math.min(quietFrom, p.quietFrom(x, z));
            }
        }

        public int state(int x, int y, int z) {
            if (y < lowY || y > highY) return NONE;
            column(x, z);
            for (int i = 0; i < inColumnCount; i++) {
                int s = inColumn[i].state(this, x, y, z);
                if (s != NONE) return s;
            }
            return NONE;
        }

        // other caves and the Misty cold keep off the rock round the water
        public boolean quiet(int x, int y, int z) {
            column(x, z);
            return y >= quietFrom;
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
        float roofTop = (ROOF + ROOF_VARY) * SWELL;
        // mouth: first point with enough rock over the roof; the pool sits back where the ground opens
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
            float h = ROOF + ROOF_VARY * noise(s / 43 + phase, 2);
            float bankNoise = noise(s / 29 + phase, 3);
            int bank = bankNoise > 0.25f ? 1 : bankNoise < -0.25f ? -1 : 0;
            int depth = 2 + Math.round(1.2f * (noise(s / 23 + phase, 4) * 0.5f + 0.5f));
            Node node = new Node(p[0], p[1], w, h, bank, depth);
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

    // the baked river stops short of its last pixel; lead the course back to open water
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

    private static float noise(float t, int salt) {
        return (float) SimplexNoise.noise(t, salt * 17.3 + 401);
    }

    // PIECES

    abstract static class Piece {
        final int water;
        int minX, maxX, minZ, maxZ;
        int lowY, highY; // state is NONE outside these

        Piece(int water) {
            this.water = water;
        }

        abstract int state(Near near, int x, int y, int z);

        // false only where state is NONE at every height
        boolean reaches(int x, int z) {
            return x >= minX && x <= maxX && z >= minZ && z <= maxZ;
        }

        // the lowest y of this column that is quiet
        int quietFrom(int x, int z) {
            return Integer.MAX_VALUE;
        }
    }

    record Node(float x, float z, float w, float h, int bank, int depth) {
    }

    // a stretch of channel: water between walls, now and then a ledge on one side, an arch over
    static final class Segment extends Piece {
        final Node a;
        final Node b;
        final float dx;
        final float dz;
        final float length2;
        final int minY;

        Segment(Node a, Node b, int water) {
            super(water);
            this.a = a;
            this.b = b;
            this.dx = b.x() - a.x();
            this.dz = b.z() - a.z();
            this.length2 = Math.max(1e-4f, dx * dx + dz * dz);
            float reach = (Math.max(a.w(), b.w()) + BANK_WIDTH + 1) * SWELL + QUIET + 1;
            this.minX = (int) Math.floor(Math.min(a.x(), b.x()) - reach);
            this.maxX = (int) Math.ceil(Math.max(a.x(), b.x()) + reach);
            this.minZ = (int) Math.floor(Math.min(a.z(), b.z()) - reach);
            this.maxZ = (int) Math.ceil(Math.max(a.z(), b.z()) + reach);
            this.minY = water - Math.max(a.depth(), b.depth()) - QUIET;
            this.lowY = minY;
            this.highY = water + 9;
        }

        float project(float px, float pz) {
            return MathHelper.clamp(((px - a.x()) * dx + (pz - a.z()) * dz) / length2, 0f, 1f);
        }

        private boolean onBank(float px, float pz, float t) {
            float side = Math.signum((px - a.x()) * dz - (pz - a.z()) * dx);
            int bank = t < 0.5f ? a.bank() : b.bank();
            return bank != 0 && side == bank;
        }

        private static float half(float w, boolean onBank) {
            return w + (onBank ? BANK_WIDTH : 0) + 0.5f;
        }

        private float across(float px, float pz, float t) {
            float lx = px - a.x() - dx * t;
            float lz = pz - a.z() - dz * t;
            return (float) Math.sqrt(lx * lx + lz * lz);
        }

        @Override
        boolean reaches(int x, int z) {
            if (!super.reaches(x, z)) return false;
            float px = x + 0.5f;
            float pz = z + 0.5f;
            float t = project(px, pz);
            return across(px, pz, t) < half(MathHelper.lerp(t, a.w(), b.w()), onBank(px, pz, t)) * SWELL;
        }

        @Override
        int state(Near near, int x, int y, int z) {
            if (x < minX || x > maxX || z < minZ || z > maxZ || y < minY || y > water + 9) return NONE;
            float px = x + 0.5f;
            float pz = z + 0.5f;
            float t = project(px, pz);
            float u = across(px, pz, t);
            float w = MathHelper.lerp(t, a.w(), b.w());
            boolean onBank = onBank(px, pz, t);
            float half = half(w, onBank);
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
        int quietFrom(int x, int z) {
            if (!super.reaches(x, z)) return Integer.MAX_VALUE;
            float px = x + 0.5f;
            float pz = z + 0.5f;
            float t = project(px, pz);
            float u2 = sq(px - a.x() - dx * t) + sq(pz - a.z() - dz * t);
            float w = MathHelper.lerp(t, a.w(), b.w());
            return u2 < sq((w + BANK_WIDTH + 0.5f) * SWELL + QUIET) ? minY : Integer.MAX_VALUE;
        }
    }

    // the lake: a dome over deep still water, a dry shelf round it
    static final class Lake extends Piece {
        final float x;
        final float z;
        final float radius;
        final float outer; // the outline's widest, wobble and all
        final float dome;
        final int depth;
        final int hash;

        Lake(float x, float z, int water, float radius, float dome, int depth, int hash) {
            super(water);
            this.x = x;
            this.z = z;
            this.radius = radius;
            this.outer = radius * 1.25f * SWELL;
            this.dome = dome;
            this.depth = depth;
            this.hash = hash;
            this.lowY = water - depth - 1;
            this.highY = (int) Math.floor(water + dome * SWELL + 1);
            int reach = (int) Math.ceil(outer) + QUIET + 1;
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

        private float r(int bx, int bz) {
            float px = bx + 0.5f;
            float pz = bz + 0.5f;
            return (float) Math.sqrt(sq(px - x) + sq(pz - z)) / reach(px, pz);
        }

        @Override
        boolean reaches(int bx, int bz) {
            return super.reaches(bx, bz) && r(bx, bz) < SWELL;
        }

        @Override
        int state(Near near, int bx, int y, int bz) {
            if (bx < minX || bx > maxX || bz < minZ || bz > maxZ || y < water - depth - 1 || y > water + dome * SWELL + 1) return NONE;
            float r = r(bx, bz);
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
        int quietFrom(int bx, int bz) {
            if (!super.reaches(bx, bz)) return Integer.MAX_VALUE;
            return sq(bx + 0.5f - x) + sq(bz + 0.5f - z) < sq(outer + QUIET) ? water - depth - QUIET : Integer.MAX_VALUE;
        }
    }

    // where the open river stops: a round pool, its banks stepping back as they rise
    static final class Pool extends Piece {
        final float x;
        final float z;
        final float radius;

        Pool(float x, float z, int water, float radius) {
            super(water);
            this.x = x;
            this.z = z;
            this.radius = radius;
            this.lowY = water - 4;
            this.highY = water + POOL_BANK_TOP;
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
    }
}
