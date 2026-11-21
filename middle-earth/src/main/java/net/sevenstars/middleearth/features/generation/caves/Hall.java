package net.sevenstars.middleearth.features.generation.caves;

import it.unimi.dsi.fastutil.ints.IntArrayFIFOQueue;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SnowBlock;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.sevenstars.middleearth.block.special.SkeletonBlock;
import net.sevenstars.middleearth.features.generation.noise.SimplexNoise;

import java.util.Arrays;
import java.util.List;

import static net.sevenstars.middleearth.features.generation.caves.Galleries.WOBBLE;
import static net.sevenstars.middleearth.features.generation.caves.Galleries.WOBBLE_BOUND;
import static net.sevenstars.middleearth.features.generation.caves.Galleries.WOBBLE_SCALE;
import static net.sevenstars.middleearth.features.generation.caves.MistyCaves.mix;
import static net.sevenstars.middleearth.features.generation.caves.MistyCaves.smoothstep;
import static net.sevenstars.middleearth.features.generation.caves.MistyCaves.sq;

// a domed hall with a flat floor, and what sets it apart
final class Hall {
    enum Kind { PLAIN, LAKE, PILLARS, COLLAPSED, CHIMNEY, ICEFALL }

    static final float MAX_RADIUS = 17;

    private static final float LAKE_SPREAD = 0.35f; // squared share of the hall the lake covers
    private static final float SHORE_SPREAD = 0.5f;
    private static final int DROWNED_ONE_IN = 5;
    private static final int[] DROWNED_POSES = {2, 3, 4, 5};
    private static final float CHIMNEY_CORE = 0.55f; // share of the radius kept clear, so a fall reaches the floor
    private static final float CHIMNEY_ROUGH = 0.35f;
    private static final float CHIMNEY_ROUGH_SCALE = 2.5f;
    private static final float CHIMNEY_TAPER = 0.3f;
    private static final int SNOW_CAP_GAPS = 26; // out of 256
    private static final float SNOW_PILE_RADIUS = 4.5f;
    private static final int PILLAR_MIN = 9;
    private static final float PILLAR_MARGIN = 0.5f;
    private static final float CORRIDOR_MARGIN = 1.5f; // kept clear beside a tunnel's way through the hall
    private static final float RUBBLE_HEIGHT = 0.75f;
    private static final float FALL_DEPTH = 3; // the icefall's alcove goes this far into the wall
    private static final float POOL_SPREAD = 0.7f;
    private static final float GLAZE = 0.75f;
    private static final int ICICLES = 150; // out of 256
    private static final int[][] NEIGHBOURS = {{1, 0, 0}, {-1, 0, 0}, {0, 1, 0}, {0, -1, 0}, {0, 0, 1}, {0, 0, -1}};

    final Kind kind;
    final float x;
    final float z;
    final int floor;
    final float rx;
    final float rz;
    final float cos;
    final float sin;
    final float height;
    final int hash;
    final int cellX;
    final int cellZ;
    final Box box;
    private final GalleryLayout layout;
    private final int noiseX;
    private final int noiseZ;
    private final int lakeDepth;
    private final float chimneyX;
    private final float chimneyZ;
    private final float chimneyRadius;
    private final int chimneyTop;
    private final float fallHalfWidth;

    // worked out when first needed, from the tunnels
    private volatile List<Tunnel> ways;
    private volatile float[] pillars; // x, z, radius each
    private volatile float[] collapse;
    private volatile float[] fall; // direction, then the wall's distance that way
    private volatile Tunnel outflow;
    private volatile boolean outflowFound;
    private volatile CarveMask loose;

    Hall(GalleryLayout layout, Kind kind, int cellX, int cellZ, float x, float z, int floor, float rx, float rz, float angle, float height, int hash,
         float chimneyX, float chimneyZ, int chimneyTop) {
        this.layout = layout;
        this.kind = kind;
        this.cellX = cellX;
        this.cellZ = cellZ;
        this.x = x;
        this.z = z;
        this.floor = floor;
        this.rx = rx;
        this.rz = rz;
        this.cos = MathHelper.cos(angle);
        this.sin = MathHelper.sin(angle);
        this.height = height;
        this.hash = hash;
        this.noiseX = layout.misty.offsetX + 4409;
        this.noiseZ = layout.misty.offsetZ + 6151;
        this.fallHalfWidth = 2.5f + 2f * (mix(hash, 12, 12) & 0xFF) / 255f;
        this.lakeDepth = 3 + Math.floorMod(mix(hash, 8, 8), 3);
        this.chimneyX = chimneyX;
        this.chimneyZ = chimneyZ;
        this.chimneyRadius = 4.8f + 2.2f * (mix(hash, 9, 9) & 0xFF) / 255f;
        this.chimneyTop = chimneyTop;
        float chimneyReach = kind == Kind.CHIMNEY ? Math.max(Math.abs(chimneyX - x), Math.abs(chimneyZ - z)) + chimneyRadius * (1 + CHIMNEY_ROUGH) + 1 : 0;
        float reach = Math.max(Math.max(rx, rz) * (1 + WOBBLE) + 1 + (kind == Kind.ICEFALL ? FALL_DEPTH + 1 : 0), chimneyReach);
        int minY = kind == Kind.LAKE ? floor - lakeDepth : floor;
        int maxY = kind == Kind.CHIMNEY ? chimneyTop + 1 : floor + (int) Math.ceil(height * (1 + WOBBLE));
        this.box = Box.around(x, z, reach, minY, maxY);
    }

    float radius() {
        return Math.min(rx, rz);
    }

    // the share of the way to the wall, squared, ignoring the wobble
    float spread(float px, float pz) {
        float u = (px - x) * cos + (pz - z) * sin;
        float v = (pz - z) * cos - (px - x) * sin;
        return sq(u / rx) + sq(v / rz);
    }

    boolean holds(float px, float pz) {
        return spread(px, pz) < 0.5f;
    }

    boolean carves(int bx, int y, int bz) {
        if (!box.contains(bx, y, bz)) return false;
        if (kind == Kind.CHIMNEY && (shaft(bx, y, bz) || loose().at(bx, y, bz))) return true;
        return dome(bx, y, bz);
    }

    private boolean dome(int bx, int y, int bz) {
        if (y < floor) return false;
        if (kind == Kind.ICEFALL && alcove(bx, y, bz)) return true;
        float px = bx + 0.5f - x;
        float pz = bz + 0.5f - z;
        float u = px * cos + pz * sin;
        float v = pz * cos - px * sin;
        // the wobble scales the whole dome, so far enough in or out the noise cannot change the answer
        float bare = sq(u / rx) + sq(v / rz) + sq((y - floor) / height);
        if (bare >= sq(1 + WOBBLE_BOUND)) return false;
        if (bare < sq(1 - WOBBLE_BOUND)) return true;
        float wob = 1 + WOBBLE * (float) SimplexNoise.noise((bx + noiseX) / WOBBLE_SCALE, y / WOBBLE_SCALE, (bz + noiseZ) / WOBBLE_SCALE);
        float dy = (y - floor) / (height * wob);
        return sq(u / (rx * wob)) + sq(v / (rz * wob)) + dy * dy < 1;
    }

    // straight and wide, narrowing upward, with rough walls; the core stays clear so a fall reaches the floor
    private boolean shaft(int bx, int y, int bz) {
        if (y < floor + height * 0.5f || y > chimneyTop) return false;
        float rise = (y - floor) / (float) Math.max(1, chimneyTop - floor);
        float px = bx + 0.5f - chimneyX;
        float pz = bz + 0.5f - chimneyZ;
        float wall = chimneyRadius * (1 - CHIMNEY_TAPER * rise);
        float d2 = px * px + pz * pz;
        if (d2 < sq(wall * CHIMNEY_CORE)) return true;
        if (d2 >= sq(wall * (1 + CHIMNEY_ROUGH * Galleries.NOISE_BOUND))) return false;
        // by bearing and height only, so each wall runs solid outward from where it starts
        float bearing = (float) Math.atan2(pz, px);
        float ring = chimneyRadius / CHIMNEY_ROUGH_SCALE;
        float rough = 1 + CHIMNEY_ROUGH * (float) SimplexNoise.noise(MathHelper.cos(bearing) * ring + noiseZ, y / 3.5, MathHelper.sin(bearing) * ring + noiseX);
        float r = Math.max(wall * CHIMNEY_CORE, wall * rough);
        return d2 < r * r;
    }

    // rock in the shaft's reach not joined to the rock around it, cut away so nothing hangs in the air
    private CarveMask loose() {
        CarveMask found = loose;
        if (found != null) return found;
        int r = (int) Math.ceil(chimneyRadius * (1 + CHIMNEY_ROUGH)) + 2;
        int x0 = (int) Math.floor(chimneyX) - r;
        int z0 = (int) Math.floor(chimneyZ) - r;
        int y0 = floor + (int) (height * 0.5f) - 1;
        int side = 2 * r + 1;
        int tall = chimneyTop - y0 + 2;
        boolean[] rock = new boolean[side * side * tall];
        boolean[] held = new boolean[rock.length];
        IntArrayFIFOQueue queue = new IntArrayFIFOQueue();
        for (int a = 0; a < side; a++) {
            for (int b = 0; b < side; b++) {
                for (int c = 0; c < tall; c++) {
                    int k = (a * side + b) * tall + c;
                    rock[k] = !shaft(x0 + a, y0 + c, z0 + b) && !dome(x0 + a, y0 + c, z0 + b);
                    boolean edge = a == 0 || b == 0 || a == side - 1 || b == side - 1 || c == 0 || c == tall - 1;
                    if (rock[k] && edge) {
                        held[k] = true;
                        queue.enqueue(k);
                    }
                }
            }
        }
        while (!queue.isEmpty()) {
            int k = queue.dequeueInt();
            int c = k % tall;
            int a = k / tall / side;
            int b = k / tall % side;
            for (int[] step : NEIGHBOURS) {
                int na = a + step[0];
                int nb = b + step[1];
                int nc = c + step[2];
                if (na < 0 || nb < 0 || nc < 0 || na >= side || nb >= side || nc >= tall) continue;
                int nk = (na * side + nb) * tall + nc;
                if (rock[nk] && !held[nk]) {
                    held[nk] = true;
                    queue.enqueue(nk);
                }
            }
        }
        found = new CarveMask((bx, by, bz) -> {
            int a = bx - x0;
            int b = bz - z0;
            int c = by - y0;
            if (a < 0 || b < 0 || c < 0 || a >= side || b >= side || c >= tall) return false;
            int k = (a * side + b) * tall + c;
            return rock[k] && !held[k];
        }, x0, x0 + side - 1, y0, y0 + tall - 1, z0, z0 + side - 1);
        loose = found;
        return found;
    }

    // a tall slot in the wall away from the tunnels, for the icefall to fill
    private boolean alcove(int bx, int y, int bz) {
        if (y > floor + height + 2) return false;
        float[] f = fall();
        float px = bx + 0.5f - x;
        float pz = bz + 0.5f - z;
        float along = px * f[0] + pz * f[1];
        if (along < f[2] * 0.7f || along > f[2] + FALL_DEPTH) return false;
        float across = Math.abs(pz * f[0] - px * f[1]);
        float rough = 1 + 0.25f * (float) SimplexNoise.noise(y / 4.0, along / 3.0 + noiseX);
        return across < fallHalfWidth * rough;
    }

    // from the planned tunnels, since carving cannot wait on the walked ones
    private float[] fall() {
        float[] f = fall;
        if (f != null) return f;
        float[] way = awayFrom(layout.tunnelsOf(this, true), 0);
        float u = way[0] * cos + way[1] * sin;
        float v = way[1] * cos - way[0] * sin;
        f = new float[]{way[0], way[1], 1 / (float) Math.sqrt(sq(u / rx) + sq(v / rz))};
        fall = f;
        return f;
    }

    // of 16 bearings turned by offset, the one furthest from every tunnel leaving the hall
    private float[] awayFrom(List<Tunnel> tunnels, float offset) {
        float best = -1;
        float bestAngle = 0;
        for (int i = 0; i < 16; i++) {
            float angle = (float) (Math.PI * 2 * i / 16) + offset;
            float cx = MathHelper.cos(angle);
            float cz = MathHelper.sin(angle);
            float nearest = 2;
            for (Tunnel way : tunnels) {
                float wx = way.a == this ? way.dx : -way.dx;
                float wz = way.a == this ? way.dz : -way.dz;
                nearest = Math.min(nearest, 1 - (cx * wx + cz * wz));
            }
            if (nearest > best) {
                best = nearest;
                bestAngle = angle;
            }
        }
        return new float[]{MathHelper.cos(bestAngle), MathHelper.sin(bestAngle)};
    }

    boolean water(int bx, int y, int bz) {
        if (kind != Kind.LAKE || y >= floor || y < floor - lakeDepth) return false;
        float q = spread(bx + 0.5f, bz + 0.5f);
        if (q >= LAKE_SPREAD) return false;
        return y >= floor - Math.round(lakeDepth * (1 - q / LAKE_SPREAD));
    }

    private List<Tunnel> ways() {
        List<Tunnel> found = ways;
        if (found == null) ways = found = layout.tunnelsOf(this, false);
        return found;
    }

    // where an icefall's water leaves: the tunnel to the lowest hall next door
    Tunnel outflow() {
        if (outflowFound) return outflow;
        Tunnel way = null;
        int lowest = Integer.MAX_VALUE;
        for (Tunnel candidate : ways()) {
            int other = candidate.a == this ? candidate.b.floor : candidate.a.floor;
            if (other < lowest) {
                lowest = other;
                way = candidate;
            }
        }
        outflow = way;
        outflowFound = true;
        return way;
    }

    private boolean clearOfTunnels(float px, float pz, float margin) {
        for (Tunnel way : ways()) {
            if (way.corridor(px, pz, margin)) return false;
        }
        return true;
    }

    // DRESSING: only this chunk's blocks, each choice from a position or a hash, so chunks agree

    void dress(BlockBuffer blocks, int startX, int startZ) {
        switch (kind) {
            case LAKE -> {
                lake(blocks, startX, startZ);
                drowned(blocks, startX, startZ);
            }
            case PILLARS -> pillars(blocks, startX, startZ);
            case COLLAPSED -> rubble(blocks, startX, startZ);
            case ICEFALL -> icefall(blocks, startX, startZ);
            case CHIMNEY -> {
                snowPile(blocks, startX, startZ);
                snowCap(blocks, startX, startZ);
            }
            default -> {
            }
        }
    }

    // clear ice over the water; the basin's edge rounds to no water, so the shore snow starts at the ice
    private void lake(BlockBuffer blocks, int startX, int startZ) {
        for (int bx = Math.max(box.minX(), startX); bx <= Math.min(box.maxX(), startX + 15); bx++) {
            for (int bz = Math.max(box.minZ(), startZ); bz <= Math.min(box.maxZ(), startZ + 15); bz++) {
                if (spread(bx + 0.5f, bz + 0.5f) >= SHORE_SPREAD) continue;
                int y = floor - 1;
                BlockState top = blocks.get(bx, y, bz);
                if (top.isOf(Blocks.WATER)) blocks.put(bx, y, bz, Blocks.ICE.getDefaultState());
                else if (!top.isAir() && carves(bx, floor, bz)) blocks.put(bx, y, bz, Blocks.SNOW_BLOCK.getDefaultState());
            }
        }
    }

    // one lake in five keeps two or three who went through the ice, round the middle where it is deep
    private void drowned(BlockBuffer blocks, int startX, int startZ) {
        int h = mix(hash, 13, 13);
        if (Math.floorMod(h, DROWNED_ONE_IN) != 0) return;
        int count = 2 + ((h >>> 4) & 1);
        for (int i = 0; i < count; i++) {
            int g = mix(h, 40 + i, 7);
            float angle = (float) (Math.PI * 2 * (i + ((g & 0xFF) / 255f) * 0.6f) / count);
            float out = 2 + 2.5f * ((g >>> 8) & 0xFF) / 255f;
            int bx = (int) Math.floor(x + MathHelper.cos(angle) * out);
            int bz = (int) Math.floor(z + MathHelper.sin(angle) * out);
            if (bx < startX || bx > startX + 15 || bz < startZ || bz > startZ + 15) continue;
            int y = floor - 2;
            if (!blocks.get(bx, y, bz).isOf(Blocks.WATER)) continue;
            while (y > floor - lakeDepth && blocks.get(bx, y - 1, bz).isOf(Blocks.WATER)) y--;
            Direction facing = Direction.fromHorizontalQuarterTurns((g >>> 16) & 3);
            blocks.put(bx, y, bz, MistyCaveSetPieces.skeleton(DROWNED_POSES[(g >>> 18) & 3], facing).with(SkeletonBlock.WATERLOGGED, true));
        }
    }

    // ice columns, waisted in the middle, floor to roof
    private void pillars(BlockBuffer blocks, int startX, int startZ) {
        float[] found = pillars();
        for (int i = 0; i < found.length; i += 3) {
            float px = found[i];
            float pz = found[i + 1];
            float radius = found[i + 2];
            int reach = (int) Math.ceil(radius) + 1;
            for (int bx = Math.max((int) px - reach, startX); bx <= Math.min((int) px + reach, startX + 15); bx++) {
                for (int bz = Math.max((int) pz - reach, startZ); bz <= Math.min((int) pz + reach, startZ + 15); bz++) {
                    float d = (float) Math.sqrt(sq(bx + 0.5f - px) + sq(bz + 0.5f - pz));
                    for (int y = floor; carves(bx, y, bz); y++) {
                        float share = (y - floor) / height;
                        if (d > radius * (0.7f + 0.6f * Math.abs(share - 0.5f))) continue;
                        float vein = (float) SimplexNoise.noise((bx + layout.misty.offsetX) / 4.0, y / 4.0, (bz + layout.misty.offsetZ) / 4.0);
                        blocks.put(bx, y, bz, vein > 0.35f ? Blocks.BLUE_ICE.getDefaultState() : Blocks.PACKED_ICE.getDefaultState());
                    }
                }
            }
        }
    }

    private float[] pillars() {
        float[] found = pillars;
        if (found != null) return found;
        int count = PILLAR_MIN + Math.floorMod(mix(hash, 10, 10), 5);
        float[] placed = new float[count * 3];
        int n = 0;
        for (int i = 0; i < count; i++) {
            int h = mix(hash, 20 + i, 3);
            float angle = (float) (Math.PI * 2 * (h & 0xFFFF) / 65536f);
            float out = 0.3f + 0.5f * ((h >>> 16) & 0xFF) / 255f;
            float radius = 1.3f + 0.9f * ((h >>> 24) & 0xFF) / 255f;
            float u = MathHelper.cos(angle) * rx * out;
            float v = MathHelper.sin(angle) * rz * out;
            float px = x + u * cos - v * sin;
            float pz = z + u * sin + v * cos;
            if (!clearOfTunnels(px, pz, radius + PILLAR_MARGIN)) continue;
            placed[n++] = px;
            placed[n++] = pz;
            placed[n++] = radius;
        }
        found = Arrays.copyOf(placed, n);
        pillars = found;
        return found;
    }

    // a fall of rock climbing the wall that faces away from the tunnels
    private void rubble(BlockBuffer blocks, int startX, int startZ) {
        float[] toward = collapse();
        float reach = Math.max(rx, rz);
        for (int bx = Math.max(box.minX(), startX); bx <= Math.min(box.maxX(), startX + 15); bx++) {
            for (int bz = Math.max(box.minZ(), startZ); bz <= Math.min(box.maxZ(), startZ + 15); bz++) {
                float px = bx + 0.5f - x;
                float pz = bz + 0.5f - z;
                float along = (px * toward[0] + pz * toward[1]) / reach;
                float aside = Math.abs(pz * toward[0] - px * toward[1]) / reach;
                float rise = smoothstep(0.15f, 1.05f, along) * (1 - smoothstep(0.45f, 0.85f, aside));
                if (rise <= 0 || !clearOfTunnels(bx + 0.5f, bz + 0.5f, CORRIDOR_MARGIN)) continue;
                float lump = 0.8f + 0.4f * (float) SimplexNoise.noise((bx + layout.misty.offsetZ) / 3.0, (bz + layout.misty.offsetX) / 3.0);
                int top = floor + Math.round(height * RUBBLE_HEIGHT * rise * lump);
                for (int y = floor; y < top && carves(bx, y, bz); y++) {
                    float pick = (float) SimplexNoise.noise((bx + layout.misty.offsetX) / 2.5, y / 2.5, (bz + layout.misty.offsetZ) / 2.5);
                    blocks.put(bx, y, bz, pick > 0.3f ? Blocks.COBBLESTONE.getDefaultState() : pick > -0.2f ? Blocks.STONE.getDefaultState()
                            : pick > -0.5f ? Blocks.TUFF.getDefaultState() : Blocks.GRAVEL.getDefaultState());
                }
            }
        }
    }

    private float[] collapse() {
        float[] found = collapse;
        if (found == null) collapse = found = awayFrom(ways(), (mix(hash, 11, 11) & 0xFF) / 255f * 0.3f);
        return found;
    }

    // the icefall fills its alcove, rippled into columns and spreading into a cone over a ragged pool;
    // the spray froze over the whole hall
    private void icefall(BlockBuffer blocks, int startX, int startZ) {
        float[] f = fall();
        float wall = f[2];
        float footX = x + f[0] * wall * 0.78f;
        float footZ = z + f[1] * wall * 0.78f;
        float spread = radius() * POOL_SPREAD;
        int fromX = Math.max(box.minX(), startX);
        int toX = Math.min(box.maxX(), startX + 15);
        int fromZ = Math.max(box.minZ(), startZ);
        int toZ = Math.min(box.maxZ(), startZ + 15);
        int top = floor + (int) height + 2;
        CarveMask air = new CarveMask(this::carves, fromX - 1, toX + 1, floor - 2, top + 1, fromZ - 1, toZ + 1);
        for (int bx = fromX; bx <= toX; bx++) {
            for (int bz = fromZ; bz <= toZ; bz++) {
                float px = bx + 0.5f - x;
                float pz = bz + 0.5f - z;
                float along = px * f[0] + pz * f[1];
                float across = Math.abs(pz * f[0] - px * f[1]);
                glaze(blocks, air, bx, bz);
                float d = (float) Math.sqrt(sq(bx + 0.5f - footX) + sq(bz + 0.5f - footZ));
                float edge = spread * (1 + 0.35f * (float) SimplexNoise.noise((bx + layout.misty.offsetX) / 4.0, (bz + layout.misty.offsetZ) / 4.0));
                BlockState bed = blocks.get(bx, floor - 1, bz);
                if (d < edge && !bed.isAir() && bed.getFluidState().isEmpty() && air.at(bx, floor, bz)) {
                    blocks.put(bx, floor - 1, bz, Blocks.ICE.getDefaultState());
                }
                if (along < wall * 0.3f || across > fallHalfWidth + 1) continue;
                for (int y = floor; y <= floor + height + 2; y++) {
                    if (!air.at(bx, y, bz)) continue;
                    float columns = (float) SimplexNoise.noise((bx + layout.misty.offsetX) / 1.6, y / 9.0, (bz + layout.misty.offsetZ) / 1.6);
                    float face = wall * 0.78f - 1.2f * columns;
                    float cone = 3.5f * (1 - (y - floor) / 4f);
                    if (along < face - Math.max(0, cone)) continue;
                    blocks.put(bx, y, bz, streak(bx, y, bz));
                }
            }
        }
    }

    // ice over most of the floor, packed and blue ice up the walls and over the roof, icicles
    private void glaze(BlockBuffer blocks, CarveMask air, int bx, int bz) {
        for (int y = floor - 1; y <= floor + height + 2; y++) {
            if (air.at(bx, y, bz)) continue;
            BlockState state = blocks.get(bx, y, bz);
            if (state.isAir() || !state.getFluidState().isEmpty()) continue;
            boolean onFloor = air.at(bx, y + 1, bz);
            boolean ceiling = air.at(bx, y - 1, bz);
            boolean wall = air.at(bx + 1, y, bz) || air.at(bx - 1, y, bz) || air.at(bx, y, bz + 1) || air.at(bx, y, bz - 1);
            if (!onFloor && !ceiling && !wall) continue;
            float sheet = (float) SimplexNoise.noise((bx + layout.misty.offsetX) / 5.0, y / 5.0, (bz + layout.misty.offsetZ) / 5.0);
            if (sheet < -GLAZE) continue;
            if (onFloor) {
                blocks.put(bx, y, bz, sheet > 0.3f ? Blocks.ICE.getDefaultState() : Blocks.PACKED_ICE.getDefaultState());
                continue;
            }
            blocks.put(bx, y, bz, sheet > 0.4f ? Blocks.BLUE_ICE.getDefaultState() : Blocks.PACKED_ICE.getDefaultState());
            if (ceiling && air.at(bx, y - 2, bz)) Icicles.maybeHang(blocks, bx, y - 1, bz, mix(bx, y, bz ^ hash) & 0xFF, ICICLES);
        }
    }

    BlockState streak(int bx, int y, int bz) {
        float streak = (float) SimplexNoise.noise((bx + layout.misty.offsetZ) / 2.0, y / 8.0, (bz + layout.misty.offsetX) / 2.0);
        return streak > 0.35f ? Blocks.BLUE_ICE.getDefaultState() : streak > -0.35f ? Blocks.PACKED_ICE.getDefaultState() : Blocks.ICE.getDefaultState();
    }

    // powder snow over the chimney's mouth, a few holes left open
    private void snowCap(BlockBuffer blocks, int startX, int startZ) {
        int reach = (int) Math.ceil(chimneyRadius * (1 + CHIMNEY_ROUGH)) + 1;
        for (int bx = Math.max((int) chimneyX - reach, startX); bx <= Math.min((int) chimneyX + reach, startX + 15); bx++) {
            for (int bz = Math.max((int) chimneyZ - reach, startZ); bz <= Math.min((int) chimneyZ + reach, startZ + 15); bz++) {
                if ((mix(bx, bz, hash) & 0xFF) < SNOW_CAP_GAPS) continue;
                int surface = Math.min((int) GalleryLayout.ground(bx, bz), chimneyTop);
                for (int y = surface - 1; y <= surface; y++) {
                    if (shaft(bx, y, bz) || loose().at(bx, y, bz)) blocks.put(bx, y, bz, Blocks.POWDER_SNOW.getDefaultState());
                }
            }
        }
    }

    // a heap of snow under the chimney
    private void snowPile(BlockBuffer blocks, int startX, int startZ) {
        int reach = (int) Math.ceil(SNOW_PILE_RADIUS);
        for (int bx = Math.max((int) chimneyX - reach, startX); bx <= Math.min((int) chimneyX + reach, startX + 15); bx++) {
            for (int bz = Math.max((int) chimneyZ - reach, startZ); bz <= Math.min((int) chimneyZ + reach, startZ + 15); bz++) {
                float d = (float) Math.sqrt(sq(bx + 0.5f - chimneyX) + sq(bz + 0.5f - chimneyZ));
                if (d >= SNOW_PILE_RADIUS) continue;
                float pile = 3.2f * (1 - d / SNOW_PILE_RADIUS);
                int full = (int) pile;
                int y = floor;
                for (; y < floor + full && carves(bx, y, bz); y++) blocks.put(bx, y, bz, Blocks.SNOW_BLOCK.getDefaultState());
                int layers = Math.round((pile - full) * 7);
                if (layers > 0 && carves(bx, y, bz)) blocks.put(bx, y, bz, Blocks.SNOW.getDefaultState().with(SnowBlock.LAYERS, layers));
            }
        }
    }
}
