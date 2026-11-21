package net.sevenstars.middleearth.features.generation.caves;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.MathHelper;
import net.sevenstars.middleearth.features.generation.noise.SimplexNoise;

import static net.sevenstars.middleearth.features.generation.caves.Galleries.WOBBLE;
import static net.sevenstars.middleearth.features.generation.caves.Galleries.WOBBLE_BOUND;
import static net.sevenstars.middleearth.features.generation.caves.Galleries.WOBBLE_SCALE;
import static net.sevenstars.middleearth.features.generation.caves.MistyCaves.mix;
import static net.sevenstars.middleearth.features.generation.caves.MistyCaves.sq;

// from one hall's centre to another's, the floor sloping evenly between theirs; some cross a crevasse on an ice
// bridge, and on steep ones the drop is gathered into a frozen waterfall with a ledge round it
final class Tunnel {
    enum Kind { PLAIN, CREVASSE, WATERFALL }

    private static final float WALL = 0.4f; // share of the height with straight walls, the rest is the arch
    private static final int WATERFALL_MIN_RISE = 10;
    private static final int WATERFALL_CHANCE = 60; // out of 256
    private static final float WATERFALL_WIDEN = 0.8f;
    private static final float WATERFALL_CHAMBER = 9; // the widening reaches this far either side of the drop
    private static final float LEDGE_WIDTH = 2.2f;
    private static final int POOL_REACH = 6;
    private static final int STREAM_REACH = 9;
    private static final float CURTAIN_MAX = 3.2f;
    private static final int CREVASSE_MIN_RUN = 60;
    private static final int CREVASSE_CHANCE = 36; // out of 256
    private static final float BRIDGE_HALF_WIDTH = 1.6f;
    private static final float BRIDGE_MIN_HALF_WIDTH = 1.6f; // narrower pinches to a diagonal nobody can walk
    private static final int BRIDGE_THICKNESS = 2;
    private static final int BRIDGE_ICICLES = 150; // out of 256
    private static final int BRIDGE_APPROACH = 10;
    private static final int ICEFALL_DEPTH = 18; // down the crevasse walls under the bridge
    private static final float STREAM_MEANDER = 1.2f;
    private static final float STREAM_HALF_WIDTH = 1.3f;

    final Kind kind;
    final Hall a;
    final Hall b;
    final float flatA; // the floor stays level this far into the tunnel, and as far from its end
    final float flatB;
    final float dx;
    final float dz;
    final float length;
    final float height;
    final Box box;
    private final GalleryLayout layout;
    private final float bend;
    private final float phase;
    private final float halfWidth;
    private final int hash;
    private final int noiseX;
    private final int noiseZ;
    private final float at; // where the crevasse or the drop is, along the tunnel
    private final float gap; // half the crevasse's width
    private final float reach; // half its length, across the tunnel
    private final int depth;
    private final int drop; // signed along the tunnel
    private final int side; // of the ledge

    Tunnel(GalleryLayout layout, Hall a, Hall b, int h) {
        this.layout = layout;
        this.a = a;
        this.b = b;
        this.flatA = a.radius() * 0.85f;
        this.flatB = b.radius() * 0.85f;
        float ex = b.x - a.x;
        float ez = b.z - a.z;
        this.length = (float) Math.sqrt(ex * ex + ez * ez);
        this.dx = ex / length;
        this.dz = ez / length;
        this.hash = h;
        float r1 = (mix(h, 1, 0) & 0xFF) / 255f;
        float r2 = (mix(h, 2, 0) & 0xFF) / 255f;
        float r3 = (mix(h, 3, 0) & 0xFF) / 255f;
        float r4 = (mix(h, 4, 0) & 0xFF) / 255f;
        this.bend = (r1 - 0.5f) * 0.3f * length;
        this.phase = (float) (Math.PI * 2 * r2);
        this.halfWidth = 2.3f + 1.3f * r3;
        this.height = 4.5f + 2f * r2;
        this.noiseX = layout.misty.offsetX + 7321;
        this.noiseZ = layout.misty.offsetZ + 2203;
        int roll = (h >>> 8) & 0xFF;
        int rise = Math.abs(b.floor - a.floor);
        float run = length - flatA - flatB;
        if (rise >= WATERFALL_MIN_RISE && roll < WATERFALL_CHANCE) this.kind = Kind.WATERFALL;
        else if (run >= CREVASSE_MIN_RUN && roll >= 256 - CREVASSE_CHANCE) this.kind = Kind.CREVASSE;
        else this.kind = Kind.PLAIN;
        this.at = flatA + run * (0.4f + 0.2f * r4);
        this.gap = 1.8f + 1.0f * r1;
        this.reach = 10 + 8 * r2;
        this.depth = 30 + Math.round(20 * r3);
        int fall = Math.min(rise, WATERFALL_MIN_RISE + Math.floorMod(mix(h, 5, 0), 7));
        this.drop = kind == Kind.WATERFALL ? (b.floor > a.floor ? fall : -fall) : 0;
        this.side = r4 < 0.5f ? -1 : 1;
        float out = Math.abs(bend) + 2 + halfWidth * (1 + WOBBLE) * (1 + WATERFALL_WIDEN) + 1 + (kind == Kind.CREVASSE ? reach : 0);
        this.box = new Box((int) Math.floor(Math.min(a.x, b.x) - out), (int) Math.ceil(Math.max(a.x, b.x) + out),
                Math.min(a.floor, b.floor) - (kind == Kind.CREVASSE ? depth + 1 : 0), Math.max(a.floor, b.floor) + (int) Math.ceil(height * (1 + WOBBLE)) + 3,
                (int) Math.floor(Math.min(a.z, b.z) - out), (int) Math.ceil(Math.max(a.z, b.z) + out));
    }

    // sideways shift of the centre line at a share f of the way along
    private float centre(float f) {
        float arc = MathHelper.sin((float) Math.PI * f);
        return arc * (bend + 1.5f * MathHelper.sin(f * length * 0.15f + phase));
    }

    // u is across from the centre line, for the waterfall's ledge
    int floorAt(float f, float u) {
        float t = f * length;
        float slope = MathHelper.clamp((t - flatA) / Math.max(1, length - flatA - flatB), 0f, 1f);
        if (drop == 0) return Math.round(a.floor + (b.floor - a.floor) * slope);
        float base = a.floor + (b.floor - a.floor - drop) * slope;
        // the ledge comes down the whole drop at half a block a block, the rest of the floor at once
        float share = onLedge(t, u) ? MathHelper.clamp((t - at + Math.abs(drop)) / (2f * Math.abs(drop)), 0f, 1f) : t >= at ? 1 : 0;
        return Math.round(base + drop * share);
    }

    float pointX(float f) {
        return a.x + dx * f * length - dz * centre(f);
    }

    float pointZ(float f) {
        return a.z + dz * f * length + dx * centre(f);
    }

    // along the tunnel from its start, NaN off either end
    private float along(float px, float pz) {
        float t = (px - a.x) * dx + (pz - a.z) * dz;
        return t < 0 || t > length ? Float.NaN : t;
    }

    private float across(float px, float pz, float t) {
        return (pz - a.z) * dx - (px - a.x) * dz - centre(t / length);
    }

    // its way through a hall
    boolean corridor(float px, float pz, float margin) {
        float t = along(px, pz);
        return !Float.isNaN(t) && Math.abs(across(px, pz, t)) < halfWidth + margin;
    }

    boolean carves(int bx, int y, int bz) {
        if (!box.contains(bx, y, bz)) return false;
        float px = bx + 0.5f - a.x;
        float pz = bz + 0.5f - a.z;
        float t = px * dx + pz * dz;
        if (t < 0 || t > length) return false;
        float f = t / length;
        float u = pz * dx - px * dz - centre(f);
        float wob = Float.NaN;
        if (kind == Kind.CREVASSE && Math.abs(t - at) <= gap * (1 + WOBBLE) && Math.abs(u) <= reach) {
            wob = wobble(bx, y, bz);
            if (crevasse(t, u, y, wob)) return true;
        }
        int dy = y - floorAt(f, u);
        if (dy < 0) return false;
        float tall = height + raise(t);
        float wide = halfWidth * widen(t);
        // the wobble scales both, so beyond their widest the noise cannot help
        if (dy >= tall * (1 + WOBBLE_BOUND) || Math.abs(u) >= wide * (1 + WOBBLE_BOUND)) return false;
        if (Float.isNaN(wob)) wob = wobble(bx, y, bz);
        float h = tall * wob;
        if (dy >= h) return false;
        float w = wide * wob;
        float share = dy / h;
        if (share > WALL) w *= (float) Math.sqrt(1 - sq((share - WALL) / (1 - WALL)));
        return Math.abs(u) < w;
    }

    private float wobble(int bx, int y, int bz) {
        return 1 + WOBBLE * (float) SimplexNoise.noise((bx + noiseX) / WOBBLE_SCALE, y / WOBBLE_SCALE, (bz + noiseZ) / WOBBLE_SCALE);
    }

    // a deep slot across the tunnel, narrowing downward, the bridge left standing over it
    private boolean crevasse(float t, float u, int y, float wob) {
        float along = Math.abs(t - at);
        int floor = floorAt(at / length, 0);
        if (fin(t - at, u, y, floor)) return false;
        float lateral = Math.abs(u) / reach;
        int bottom = floor - Math.round(depth * (1 - 0.6f * lateral * lateral));
        int top = floor + Math.round(height) + 2;
        if (y < bottom || y > top) return false;
        float narrowing = 0.45f + 0.55f * (y - bottom) / (float) (top - bottom);
        return along < gap * wob * narrowing * (1 - 0.5f * lateral * lateral);
    }

    // the bridge: a rib of rock sagging a block in the middle, ragged, deepest at the lips; a is along from the middle
    private boolean fin(float a, float u, int y, int floor) {
        if (Math.abs(u) >= finWidth(a)) return false;
        return y < finTop(a, floor) && y >= finBottom(a, u, floor);
    }

    private float finWidth(float a) {
        float lip = Math.min(1, Math.abs(a) / gap);
        float ragged = 0.9f * (float) SimplexNoise.noise(a / 2.0, (hash & 0xFFF) + 0.5);
        return Math.max(BRIDGE_MIN_HALF_WIDTH, BRIDGE_HALF_WIDTH + ragged + 0.8f * lip);
    }

    private int finTop(float a, int floor) {
        return Math.abs(a) < gap * 0.5f ? floor - 1 : floor;
    }

    private int finBottom(float a, float u, int floor) {
        float lip = Math.min(1, Math.abs(a) / gap);
        int edge = Math.abs(u) > finWidth(a) - 1 ? 1 : 0;
        return floor - BRIDGE_THICKNESS - Math.round(5 * lip * lip) + edge;
    }

    // near the drop the tunnel widens, and its low side rises so the whole face shows
    private float near(float t) {
        return drop == 0 ? 0 : 1 - MathHelper.clamp(Math.abs(t - at) / WATERFALL_CHAMBER, 0f, 1f);
    }

    private float widen(float t) {
        return 1 + WATERFALL_WIDEN * near(t);
    }

    float raise(float t) {
        return low(t) ? Math.abs(drop) * near(t) : 0;
    }

    private boolean low(float t) {
        return drop < 0 ? t >= at : drop > 0 && t <= at;
    }

    private boolean onLedge(float t, float u) {
        return side * u > halfWidth * widen(t) - LEDGE_WIDTH;
    }

    // DRESSING

    void dress(BlockBuffer blocks, int startX, int startZ) {
        if (kind == Kind.CREVASSE) bridge(blocks, startX, startZ);
        else if (kind == Kind.WATERFALL) waterfall(blocks, startX, startZ);
        if (a.kind == Hall.Kind.ICEFALL && a.outflow() == this || b.kind == Hall.Kind.ICEFALL && b.outflow() == this) stream(blocks, startX, startZ);
    }

    // ice over the bridge's rock, an iced floor leading onto it, ice down the crevasse walls below, icicles under it
    private void bridge(BlockBuffer blocks, int startX, int startZ) {
        int floor = floorAt(at / length, 0);
        int fromX = Math.max(box.minX(), startX);
        int toX = Math.min(box.maxX(), startX + 15);
        int fromZ = Math.max(box.minZ(), startZ);
        int toZ = Math.min(box.maxZ(), startZ + 15);
        CarveMask air = null;
        for (int bx = fromX; bx <= toX; bx++) {
            for (int bz = fromZ; bz <= toZ; bz++) {
                float t = along(bx + 0.5f, bz + 0.5f);
                if (Float.isNaN(t)) continue;
                float u = across(bx + 0.5f, bz + 0.5f, t);
                float a = t - at;
                float off = Math.abs(a);
                if (off >= gap * 0.8f && off < gap + BRIDGE_APPROACH && Math.abs(u) < halfWidth * (1 + WOBBLE)) {
                    int y = floorAt(t / length, u) - 1;
                    float sheet = (float) SimplexNoise.noise((bx + layout.misty.offsetX) / 4.0, (bz + layout.misty.offsetZ) / 4.0);
                    BlockState below = blocks.get(bx, y, bz);
                    if (sheet > 1.6f * (off - gap) / BRIDGE_APPROACH - 0.8f && !below.isAir() && below.getFluidState().isEmpty()) {
                        blocks.put(bx, y, bz, off < gap + 2 ? Blocks.BLUE_ICE.getDefaultState() : Blocks.PACKED_ICE.getDefaultState());
                    }
                }
                if (off > gap * (1 + WOBBLE) + 1 || Math.abs(u) > reach) continue;
                if (air == null) air = new CarveMask(this::carves, fromX - 1, toX + 1, floor - ICEFALL_DEPTH - 1, floor + 1, fromZ - 1, toZ + 1);
                int bottom = finBottom(a, u, floor);
                for (int y = bottom; y < floor; y++) {
                    if (!fin(a, u, y, floor) || !touches(air, bx, y, bz, true)) continue;
                    float vein = (float) SimplexNoise.noise((bx + layout.misty.offsetX) / 3.0, y / 3.0, (bz + layout.misty.offsetZ) / 3.0);
                    blocks.put(bx, y, bz, vein > 0.2f ? Blocks.BLUE_ICE.getDefaultState() : Blocks.PACKED_ICE.getDefaultState());
                }
                if (Math.abs(u) < finWidth(a)) Icicles.maybeHang(blocks, bx, bottom - 1, bz, mix(bx, bottom, bz ^ hash) & 0xFF, BRIDGE_ICICLES);
                if (Math.abs(u) > finWidth(a) + 3) continue;
                for (int y = floor - ICEFALL_DEPTH; y < floor; y++) {
                    BlockState state = blocks.get(bx, y, bz);
                    if (state.isAir() || !state.getFluidState().isEmpty() || fin(a, u, y, floor) || !touches(air, bx, y, bz, false)) continue;
                    float fade = 1 - (floor - y) / (float) ICEFALL_DEPTH;
                    float flow = (float) SimplexNoise.noise((bx + layout.misty.offsetZ) / 2.0, y / 6.0, (bz + layout.misty.offsetX) / 2.0);
                    if (flow > 1 - 1.8f * fade) blocks.put(bx, y, bz, flow > 0.6f ? Blocks.BLUE_ICE.getDefaultState() : Blocks.PACKED_ICE.getDefaultState());
                }
            }
        }
    }

    private static boolean touches(CarveMask air, int x, int y, int z, boolean vertical) {
        return air.at(x + 1, y, z) || air.at(x - 1, y, z) || air.at(x, y, z + 1) || air.at(x, y, z - 1)
                || vertical && (air.at(x, y + 1, z) || air.at(x, y - 1, z));
    }

    // a frozen fall down the face of the drop, thicker at its foot, a frozen pool below and a frozen stream
    // running to its lip; the ledge is left clear
    private void waterfall(BlockBuffer blocks, int startX, int startZ) {
        for (int bx = Math.max(box.minX(), startX); bx <= Math.min(box.maxX(), startX + 15); bx++) {
            for (int bz = Math.max(box.minZ(), startZ); bz <= Math.min(box.maxZ(), startZ + 15); bz++) {
                float t = along(bx + 0.5f, bz + 0.5f);
                if (Float.isNaN(t)) continue;
                float u = across(bx + 0.5f, bz + 0.5f, t);
                float off = Math.abs(t - at);
                if (side * u > halfWidth * widen(t) - LEDGE_WIDTH - 0.6f) continue;
                int floor = floorAt(t / length, u);
                if (low(t)) {
                    if (off < POOL_REACH) freeze(blocks, bx, floor - 1, bz);
                    if (off > CURTAIN_MAX) continue;
                    float columns = 0.5f + 0.5f * (float) SimplexNoise.noise((bx + layout.misty.offsetX) / 1.7, (bz + layout.misty.offsetZ) / 1.7);
                    int fall = Math.abs(drop) + Math.round(height);
                    for (int y = floor; carves(bx, y, bz); y++) {
                        float foot = 1 - (y - floor) / (float) fall;
                        if (off > 1.0f + 2.2f * columns * (0.5f + 0.5f * foot)) continue;
                        blocks.put(bx, y, bz, a.streak(bx, y, bz));
                    }
                } else if (off < STREAM_REACH && Math.abs(u) < 1.6f) {
                    freeze(blocks, bx, floor - 1, bz);
                }
            }
        }
    }

    // an icefall's water, frozen in a stream winding down the middle of the floor
    private void stream(BlockBuffer blocks, int startX, int startZ) {
        for (int bx = Math.max(box.minX(), startX); bx <= Math.min(box.maxX(), startX + 15); bx++) {
            for (int bz = Math.max(box.minZ(), startZ); bz <= Math.min(box.maxZ(), startZ + 15); bz++) {
                float t = along(bx + 0.5f, bz + 0.5f);
                if (Float.isNaN(t)) continue;
                float u = across(bx + 0.5f, bz + 0.5f, t);
                float meander = STREAM_MEANDER * (float) SimplexNoise.noise(t / 7.0, hash & 0xFFF);
                if (Math.abs(u - meander) >= STREAM_HALF_WIDTH) continue;
                int y = floorAt(t / length, u) - 1;
                if (carves(bx, y + 1, bz)) freeze(blocks, bx, y, bz);
            }
        }
    }

    private static void freeze(BlockBuffer blocks, int x, int y, int z) {
        BlockState bed = blocks.get(x, y, z);
        if (!bed.isAir() && bed.getFluidState().isEmpty()) blocks.put(x, y, z, Blocks.ICE.getDefaultState());
    }
}
