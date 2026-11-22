package net.sevenstars.middleearth.features.generation.caves;

import net.minecraft.util.math.MathHelper;
import net.minecraft.world.chunk.Chunk;
import net.sevenstars.middleearth.features.generation.chunkgen.map.MiddleEarthHeightMap;
import net.sevenstars.middleearth.features.generation.noise.SimplexNoise;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static net.sevenstars.middleearth.features.generation.caves.MistyCaves.sq;
import static net.sevenstars.middleearth.features.generation.chunkgen.MiddleEarthChunkGenerator.DIRT_HEIGHT;

// Aglarond, the Glittering Caves at the head of Helm's Deep: a chain of halls climbing gently into the Thrihyrne,
// joined by avenues, with dry outer caves by the mouth. The Deeping-stream runs down through them and out of the mouth.
// Laid out once per world seed
public final class Aglarond {
    // the mouth, in the cliff at the head of the Deep, facing back down it
    private static final int MOUTH_X = 46850;
    private static final int MOUTH_Z = 50494;
    private static final int OUTSIDE = 12; // the way in starts this far out over the Deep's floor
    // the halls stay under the massif
    private static final int MIN_X = 46640;
    private static final int MAX_X = 47260;
    private static final int MIN_Z = 50505;
    private static final int MAX_Z = 50860;
    private static final float CENTRE_X = 46960; // the chain leans toward the middle of the massif
    private static final float CENTRE_Z = 50700;
    private static final float LEAN = 0.4f;
    private static final int COVER = 14;
    private static final int MAIN_HALLS = 8;
    private static final int GREAT_HALL = 4; // its place in the chain
    private static final int SIDE_HALLS = 4;
    private static final int TRIES = 80;
    private static final int LAYOUTS = 8;
    private static final float MAX_GRADE = 0.3f;
    private static final float CANYON_GRADE = 0.18f; // steeper avenues are canyons with stepped floors
    private static final int ZONE_MARGIN = 24; // the noise caves are kept this far clear
    static final float WOBBLE = 0.22f;
    static final float WOBBLE_FINE = 0.07f;
    static final float WOBBLE_BOUND = (WOBBLE + WOBBLE_FINE) * Galleries.NOISE_BOUND;
    private static final long SALT = 0x41474c41524f4e44L; // AGLAROND

    private static volatile Aglarond current;

    final long seed;
    final Rock rock;
    final List<Chamber> chambers;
    final List<Avenue> avenues;
    final List<Avenue> stream; // from the great hall down to the mouth
    final Box box;

    private Aglarond(long seed, Rock rock, List<Chamber> chambers, List<Avenue> avenues, List<Avenue> stream) {
        this.seed = seed;
        this.rock = rock;
        this.chambers = chambers;
        this.avenues = avenues;
        this.stream = stream;
        int minX = Integer.MAX_VALUE, maxX = Integer.MIN_VALUE, minY = Integer.MAX_VALUE, maxY = Integer.MIN_VALUE, minZ = Integer.MAX_VALUE, maxZ = Integer.MIN_VALUE;
        for (Chamber c : chambers) {
            minX = Math.min(minX, c.box.minX()); maxX = Math.max(maxX, c.box.maxX());
            minY = Math.min(minY, c.box.minY()); maxY = Math.max(maxY, c.box.maxY());
            minZ = Math.min(minZ, c.box.minZ()); maxZ = Math.max(maxZ, c.box.maxZ());
        }
        for (Avenue a : avenues) {
            minX = Math.min(minX, a.box.minX()); maxX = Math.max(maxX, a.box.maxX());
            minY = Math.min(minY, a.box.minY()); maxY = Math.max(maxY, a.box.maxY());
            minZ = Math.min(minZ, a.box.minZ()); maxZ = Math.max(maxZ, a.box.maxZ());
        }
        this.box = new Box(minX, maxX, minY, maxY, minZ, maxZ);
    }

    public static Aglarond of(long seed) {
        Aglarond known = current;
        if (known == null || known.seed != seed) current = known = layOut(seed);
        return known;
    }

    // the noise caves stay out of the mountain round the caves, from a little under the lowest floor up
    public boolean quiet(int x, int y, int z) {
        return y >= box.minY() - ZONE_MARGIN && x >= box.minX() - ZONE_MARGIN && x <= box.maxX() + ZONE_MARGIN
                && z >= box.minZ() - ZONE_MARGIN && z <= box.maxZ() + ZONE_MARGIN;
    }

    // the caves' own biome, a little beyond their walls
    public boolean holds(int x, int y, int z) {
        return y >= box.minY() - 6 && y <= box.maxY() + 2 && x >= box.minX() - 4 && x <= box.maxX() + 4 && z >= box.minZ() - 4 && z <= box.maxZ() + 4;
    }

    public boolean touchesChunk(int startX, int startZ) {
        return startX + 15 >= box.minX() - ZONE_MARGIN && startX <= box.maxX() + ZONE_MARGIN
                && startZ + 15 >= box.minZ() - ZONE_MARGIN && startZ <= box.maxZ() + ZONE_MARGIN;
    }

    public boolean carves(int x, int y, int z) {
        if (!box.contains(x, y, z)) return false;
        for (Chamber c : chambers) {
            if (c.carves(x, y, z)) return true;
        }
        for (Avenue a : avenues) {
            if (a.carves(x, y, z)) return true;
        }
        return false;
    }

    public boolean water(int x, int y, int z) {
        if (!box.contains(x, y, z)) return false;
        for (Chamber c : chambers) {
            if (c.water(x, y, z)) return true;
        }
        return false;
    }

    // the noise every wall follows, offset by the seed
    static final class Rock {
        final int noiseX;
        final int noiseZ;

        Rock(long seed) {
            this.noiseX = (int) Math.floorMod(seed ^ SALT, 4096L) + 911;
            this.noiseZ = (int) Math.floorMod((seed ^ SALT) >>> 20, 4096L) + 377;
        }

        float wobble(int x, int y, int z) {
            return 1 + WOBBLE * (float) SimplexNoise.noise((x + noiseX) / 14.0, y / 10.0, (z + noiseZ) / 14.0)
                    + WOBBLE_FINE * (float) SimplexNoise.noise((x + noiseZ) / 5.0, y / 5.0, (z + noiseX) / 5.0);
        }
    }

    public void dress(Chunk chunk, BlockBuffer blocks) {
        AglarondDressing.dress(this, chunk, blocks);
    }

    // LAYOUT

    private static Aglarond layOut(long seed) {
        Aglarond best = null;
        for (int attempt = 0; attempt < LAYOUTS; attempt++) {
            Aglarond candidate = tryLayout(seed, attempt);
            if (candidate.allReachable()) return candidate;
            if (best == null || candidate.chambers.size() > best.chambers.size()) best = candidate;
        }
        return best;
    }

    private static Aglarond tryLayout(long seed, int attempt) {
        Random rng = new Random(seed ^ SALT ^ (attempt * 0x9E3779B97F4A7C15L));
        Rock rock = new Rock(seed);
        List<Chamber> chambers = new ArrayList<>();
        List<Avenue> avenues = new ArrayList<>();
        int floor = (int) ground(MOUTH_X, MOUTH_Z - OUTSIDE) + 1;
        // the outer caves, dry and level, where the Rohirrim keep their stores
        Chamber outer = new Chamber(rock, Chamber.Kind.OUTER, MOUTH_X + 2, MOUTH_Z + 26, floor, 11, 9, 7, 0.3f, rng.nextInt(), 0.1f);
        Chamber store = new Chamber(rock, Chamber.Kind.OUTER, outer.x + rng.nextInt(17) - 8, outer.z + 28, floor, 13, 10, 8, rng.nextFloat() * 3, rng.nextInt(), 0.2f);
        chambers.add(outer);
        chambers.add(store);
        avenues.add(Avenue.mouth(rock, MOUTH_X, MOUTH_Z - OUTSIDE, outer, rng.nextInt()));
        avenues.add(new Avenue(rock, outer, store, Avenue.Kind.TUBE, rng.nextInt()));
        List<Avenue> stream = new ArrayList<>(avenues);

        // the pillared courts, going in and up
        Chamber previous = store;
        float bearing = (float) (Math.PI / 2); // south
        List<Chamber> main = new ArrayList<>();
        boolean greatPlaced = false;
        for (int i = 0; i < MAIN_HALLS; i++) {
            boolean great = !greatPlaced && i >= GREAT_HALL;
            float richness = 0.45f + 0.55f * (i + 1) / MAIN_HALLS;
            Chamber next = place(rock, rng, chambers, previous, bearing, great ? Chamber.Kind.GREAT : Chamber.Kind.HALL, great, richness);
            if (next == null && great) next = place(rock, rng, chambers, previous, bearing, Chamber.Kind.HALL, false, richness);
            else greatPlaced |= great;
            if (next == null) break;
            chambers.add(next);
            main.add(next);
            Avenue way = link(rock, previous, next, rng);
            avenues.add(way);
            if (!greatPlaced || next.kind == Chamber.Kind.GREAT) stream.add(way);
            bearing = (float) Math.atan2(next.z - previous.z, next.x - previous.x);
            previous = next;
        }
        // side halls off the courts, toward the dark recesses
        for (int i = 0; i < SIDE_HALLS && !main.isEmpty(); i++) {
            Chamber from = main.get(rng.nextInt(main.size()));
            Chamber side = place(rock, rng, chambers, from, rng.nextFloat() * (float) Math.PI * 2, Chamber.Kind.SIDE, false, 0.8f);
            if (side == null) continue;
            chambers.add(side);
            avenues.add(link(rock, from, side, rng));
        }
        // one court joined to the one after next, so the ways cross
        if (main.size() >= 3) {
            Chamber a = main.get(rng.nextInt(main.size() - 2));
            Chamber b = main.get(main.indexOf(a) + 2);
            float run = (float) Math.hypot(b.x - a.x, b.z - a.z) - a.radius() - b.radius();
            if (run > 0 && Math.abs(b.floor - a.floor) <= MAX_GRADE * run && run < 90) avenues.add(link(rock, a, b, rng));
        }
        if (!greatPlaced) stream.clear();
        return new Aglarond(seed, rock, chambers, avenues, stream);
    }

    private static Chamber place(Rock rock, Random rng, List<Chamber> placed, Chamber from, float bearing, Chamber.Kind kind, boolean great, float richness) {
        float toCentre = (float) Math.atan2(CENTRE_Z - from.z, CENTRE_X - from.x);
        float lean = kind == Chamber.Kind.SIDE ? 0 : LEAN;
        float ahead = bearing + MathHelper.wrapDegrees((float) Math.toDegrees(toCentre - bearing)) * (float) Math.PI / 180 * lean;
        for (int t = 0; t < TRIES; t++) {
            float turn = ahead + (rng.nextFloat() - 0.5f) * (kind == Chamber.Kind.SIDE ? 2f : 1.6f);
            float rx = great ? 38 + rng.nextFloat() * 6 : kind == Chamber.Kind.SIDE ? 9 + rng.nextFloat() * 7 : 16 + rng.nextFloat() * 14;
            float rz = rx * (great ? 0.65f : 0.6f + rng.nextFloat() * 0.4f);
            float height = great ? 30 : kind == Chamber.Kind.SIDE ? 8 + rng.nextFloat() * 5 : 14 + rng.nextFloat() * 12;
            float gap = from.radius() + Math.min(rx, rz) + 18 + rng.nextFloat() * 22;
            float x = from.x + MathHelper.cos(turn) * gap;
            float z = from.z + MathHelper.sin(turn) * gap;
            float reach = Math.max(rx, rz);
            if (x - reach < MIN_X || x + reach > MAX_X || z - reach < MIN_Z || z + reach > MAX_Z) continue;
            int rise = kind == Chamber.Kind.SIDE ? rng.nextInt(5) - 2 : 3 + rng.nextInt(9);
            int floor = from.floor + rise;
            float run = gap - from.radius() - Math.min(rx, rz);
            if (Math.abs(rise) > MAX_GRADE * run) continue;
            Chamber c = new Chamber(rock, kind, x, z, floor, rx, rz, height, rng.nextFloat() * (float) Math.PI, rng.nextInt(), richness);
            if (!covered(c) || overlaps(c, placed)) continue;
            return c;
        }
        return null;
    }

    private static Avenue link(Rock rock, Chamber a, Chamber b, Random rng) {
        float run = (float) Math.hypot(b.x - a.x, b.z - a.z) - a.radius() - b.radius();
        Avenue.Kind kind = Math.abs(b.floor - a.floor) > CANYON_GRADE * run ? Avenue.Kind.CANYON : Avenue.Kind.TUBE;
        return new Avenue(rock, a, b, kind, rng.nextInt());
    }

    private static boolean covered(Chamber c) {
        float top = c.floor + c.height + COVER;
        float reach = Math.max(c.rx, c.rz);
        for (int i = 0; i <= 8; i++) {
            float px = i == 8 ? c.x : c.x + reach * MathHelper.cos(i * (float) Math.PI / 4);
            float pz = i == 8 ? c.z : c.z + reach * MathHelper.sin(i * (float) Math.PI / 4);
            if (ground((int) px, (int) pz) < top) return false;
        }
        return true;
    }

    private static boolean overlaps(Chamber c, List<Chamber> placed) {
        for (Chamber other : placed) {
            if (Math.hypot(c.x - other.x, c.z - other.z) < c.radius() + other.radius() + 10) return true;
        }
        return false;
    }

    static float ground(int x, int z) {
        return DIRT_HEIGHT + MiddleEarthHeightMap.getHeight(x, z);
    }

    // every hall can be walked to from outside the mouth
    private boolean allReachable() {
        Walk.Carving air = this::carves;
        Avenue mouth = avenues.getFirst();
        int sx = (int) Math.floor(mouth.ax);
        int sz = (int) Math.floor(mouth.az);
        for (Chamber c : chambers) {
            if (!Walk.reaches(air, sx, mouth.af, sz, (int) Math.floor(c.x), c.floor, (int) Math.floor(c.z), 400)) return false;
        }
        return true;
    }

    // a domed hall with a level floor. The great hall and some others hold a still lake
    static final class Chamber {
        enum Kind { OUTER, HALL, GREAT, SIDE }

        final Kind kind;
        final float x;
        final float z;
        final int floor;
        final float rx;
        final float rz;
        final float height;
        final float cos;
        final float sin;
        final int hash;
        final float lake; // squared share of the floor under water, 0 for none
        final int lakeDepth;
        final float richness; // how thick the formations grow, from the plain outer caves to the great hall
        final Box box;
        private final Rock rock;

        Chamber(Rock rock, Kind kind, float x, float z, int floor, float rx, float rz, float height, float angle, int hash, float richness) {
            this.rock = rock;
            this.richness = kind == Kind.GREAT ? 1 : richness;
            this.kind = kind;
            this.x = x;
            this.z = z;
            this.floor = floor;
            this.rx = rx;
            this.rz = rz;
            this.height = height;
            this.cos = MathHelper.cos(angle);
            this.sin = MathHelper.sin(angle);
            this.hash = hash;
            this.lake = kind == Kind.GREAT ? 0.55f : kind == Kind.HALL && Math.floorMod(hash, 3) == 0 ? 0.25f : 0;
            this.lakeDepth = kind == Kind.GREAT ? 6 : 3 + Math.floorMod(hash >>> 4, 2);
            float reach = Math.max(rx, rz) * (1 + WOBBLE_BOUND) + 1;
            this.box = Box.around(x, z, reach, floor - (lake > 0 ? lakeDepth : 0), floor + (int) Math.ceil(height * (1 + WOBBLE_BOUND)));
        }

        float radius() {
            return Math.min(rx, rz);
        }

        float spread(float px, float pz) {
            float u = (px - x) * cos + (pz - z) * sin;
            float v = (pz - z) * cos - (px - x) * sin;
            return sq(u / rx) + sq(v / rz);
        }

        boolean carves(int bx, int y, int bz) {
            if (!box.contains(bx, y, bz) || y < floor) return false;
            float bare = spread(bx + 0.5f, bz + 0.5f) + sq((y - floor) / height);
            if (bare >= sq(1 + WOBBLE_BOUND)) return false;
            if (bare < sq(1 - WOBBLE_BOUND)) return true;
            float wob = rock.wobble(bx, y, bz);
            return bare < wob * wob;
        }

        boolean water(int bx, int y, int bz) {
            if (lake == 0 || y >= floor || y < floor - lakeDepth || !box.containsColumn(bx, bz)) return false;
            float shore = lake * (1 + 0.35f * (float) SimplexNoise.noise((bx + rock.noiseX) / 9.0, (bz + rock.noiseZ) / 9.0));
            float q = spread(bx + 0.5f, bz + 0.5f);
            if (q >= shore) return false;
            return y >= floor - Math.round(lakeDepth * (float) Math.sqrt(1 - q / shore));
        }
    }

    // from one hall's centre to another's, or in from outside the mouth: a rounded tube with a level sediment floor,
    // or where it drops faster, a tall narrow canyon whose floor comes down in steps
    static final class Avenue {
        enum Kind { MOUTH, TUBE, CANYON }

        private static final float WALL = 0.4f;

        final Kind kind;
        final float ax;
        final float az;
        final int af;
        final int bf;
        final float flatA;
        final float flatB;
        final float dx;
        final float dz;
        final float length;
        final float halfWidth;
        final float height;
        final float bend;
        final float phase;
        final Box box;
        private final Rock rock;

        Avenue(Rock rock, Chamber a, Chamber b, Kind kind, int hash) {
            this(rock, kind, a.x, a.z, a.floor, b.x, b.z, b.floor, a.radius() * 0.8f, b.radius() * 0.8f, hash);
        }

        static Avenue mouth(Rock rock, float x, float z, Chamber into, int hash) {
            return new Avenue(rock, Kind.MOUTH, x, z, into.floor, into.x, into.z, into.floor, 0, into.radius() * 0.8f, hash);
        }

        private Avenue(Rock rock, Kind kind, float ax, float az, int af, float bx, float bz, int bf, float flatA, float flatB, int hash) {
            this.rock = rock;
            this.kind = kind;
            this.ax = ax;
            this.az = az;
            this.af = af;
            this.bf = bf;
            this.flatA = flatA;
            this.flatB = flatB;
            float ex = bx - ax;
            float ez = bz - az;
            this.length = (float) Math.sqrt(ex * ex + ez * ez);
            this.dx = ex / length;
            this.dz = ez / length;
            float r1 = (MistyCaves.mix(hash, 1, 0) & 0xFF) / 255f;
            float r2 = (MistyCaves.mix(hash, 2, 0) & 0xFF) / 255f;
            this.halfWidth = switch (kind) {
                case MOUTH -> 6.5f;
                case CANYON -> 2.4f + 1.0f * r1;
                default -> 4 + 2.5f * r1;
            };
            this.height = switch (kind) {
                case MOUTH -> 10;
                case CANYON -> 12 + 6 * r2;
                default -> 6 + 3 * r2;
            };
            this.bend = kind == Kind.MOUTH ? 0 : (r1 - 0.5f) * 0.25f * length;
            this.phase = (float) (Math.PI * 2 * r2);
            float side = Math.abs(bend) + 2 + halfWidth * (1 + WOBBLE_BOUND) + 1;
            this.box = new Box((int) Math.floor(Math.min(ax, bx) - side), (int) Math.ceil(Math.max(ax, bx) + side),
                    Math.min(af, bf), Math.max(af, bf) + (int) Math.ceil(height * (1 + WOBBLE_BOUND)) + 1,
                    (int) Math.floor(Math.min(az, bz) - side), (int) Math.ceil(Math.max(az, bz) + side));
        }

        float centre(float f) {
            return MathHelper.sin((float) Math.PI * f) * (bend + 1.5f * MathHelper.sin(f * length * 0.12f + phase));
        }

        int floorAt(float f) {
            float t = f * length;
            float slope = MathHelper.clamp((t - flatA) / Math.max(1, length - flatA - flatB), 0f, 1f);
            return Math.round(af + (bf - af) * slope);
        }

        // the mouth is broad and tall where it meets the Deep, and closes in a little toward the outer cave
        private float flare(float f) {
            return kind == Kind.MOUTH ? 1 + 0.25f * (1 - f) : 1;
        }

        boolean carves(int bx, int y, int bz) {
            if (!box.contains(bx, y, bz)) return false;
            float px = bx + 0.5f - ax;
            float pz = bz + 0.5f - az;
            float t = px * dx + pz * dz;
            if (t < 0 || t > length) return false;
            float f = t / length;
            float u = pz * dx - px * dz - centre(f);
            int dy = y - floorAt(f);
            if (dy < 0) return false;
            float flare = flare(f);
            float tall = height * flare;
            float wide = halfWidth * flare;
            if (dy >= tall * (1 + WOBBLE_BOUND) || Math.abs(u) >= wide * (1 + WOBBLE_BOUND)) return false;
            float wob = rock.wobble(bx, y, bz);
            float h = tall * wob;
            if (dy >= h) return false;
            float w = wide * wob;
            float share = dy / h;
            if (share > WALL) w *= (float) Math.sqrt(1 - sq((share - WALL) / (1 - WALL)));
            return Math.abs(u) < w;
        }
    }
}
