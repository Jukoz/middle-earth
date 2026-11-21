package net.sevenstars.middleearth.features.generation.caves;

import net.minecraft.util.math.MathHelper;
import net.sevenstars.middleearth.features.generation.chunkgen.map.MiddleEarthHeightMap;
import net.sevenstars.middleearth.features.generation.map.MiddleEarthMapRuntime;
import net.sevenstars.middleearth.features.generation.noise.SimplexNoise;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.sevenstars.middleearth.features.generation.caves.MistyCaves.mix;
import static net.sevenstars.middleearth.features.generation.caves.MistyCaves.sq;
import static net.sevenstars.middleearth.features.generation.chunkgen.MiddleEarthChunkGenerator.DIRT_HEIGHT;

// where the galleries' halls and tunnels lie: one hall per grid cell at most, tunnels to the neighbours east,
// south and now and then south-east; the layer sits a set depth under a smoothed copy of the ground
final class GalleryLayout {
    static final int CELL = 112;
    private static final int CELL_EDGE = 26;
    private static final int HALL_CHANCE = 210; // out of 256
    private static final int DIAGONAL_CHANCE = 70; // out of 256
    private static final int SALT = 9127;
    private static final float MIN_WEIGHT = 0.9f;
    private static final float LINK_WEIGHT = 0.6f;
    private static final int DEPTH = 38;
    private static final int DEPTH_SPREAD = 10;
    private static final int MIN_FLOOR = 40;
    private static final int COVER = 8; // rock left over every hall and tunnel
    private static final float MAX_GRADE = 0.45f;
    private static final int COVER_STEP = 6;
    private static final int ZONE_BELOW = 24; // the noise caves come back this far under the layer
    private static final float ZONE_JITTER = 5;
    private static final int ZONE_UNDER_GROUND = 60;
    private static final int LEVEL_STEP = 32;
    private static final int LEVEL_SPAN = 48; // a level point averages 5 by 5 heights this far apart
    private static final int CHIMNEY_MAX_RISE = 70;
    private static final int CHIMNEY_LETHAL_DROP = 30;
    private static final int CACHE_LIMIT = 16384;

    private static volatile GalleryLayout current;

    final MistyCaves misty;
    final MiddleEarthMapRuntime map;
    private final CellCache<Float> levels = new CellCache<>(CACHE_LIMIT);
    private final CellCache<Hall> halls = new CellCache<>(CACHE_LIMIT);
    private final CellCache<Tunnel> planned = new CellCache<>(CACHE_LIMIT);
    private final CellCache<Boolean> walkable = new CellCache<>(CACHE_LIMIT);

    private GalleryLayout(MistyCaves misty, MiddleEarthMapRuntime map) {
        this.misty = misty;
        this.map = map;
    }

    static GalleryLayout of(MistyCaves misty, MiddleEarthMapRuntime map) {
        GalleryLayout layout = current;
        if (layout == null || layout.misty.seed != misty.seed) current = layout = new GalleryLayout(misty, map);
        return layout;
    }

    // planned keeps tunnels that have not been walked, for the walk itself
    Galleries near(int startX, int startZ, boolean planned) {
        int margin = (int) Hall.MAX_RADIUS + 12;
        int minCellX = Math.floorDiv(startX - margin, CELL);
        int maxCellX = Math.floorDiv(startX + 15 + margin, CELL);
        int minCellZ = Math.floorDiv(startZ - margin, CELL);
        int maxCellZ = Math.floorDiv(startZ + 15 + margin, CELL);
        List<Hall> found = null;
        List<Tunnel> ways = null;
        for (int cx = minCellX - 1; cx <= maxCellX; cx++) {
            for (int cz = minCellZ - 1; cz <= maxCellZ; cz++) {
                if (cx >= minCellX && cz >= minCellZ) {
                    Hall hall = hall(cx, cz);
                    if (hall != null && hall.box.touchesChunk(startX, startZ)) {
                        if (found == null) found = new ArrayList<>(2);
                        found.add(hall);
                    }
                }
                for (int link = 0; link < 3; link++) {
                    Tunnel tunnel = planned ? plannedTunnel(cx, cz, link) : tunnel(cx, cz, link);
                    if (tunnel != null && tunnel.box.touchesChunk(startX, startZ)) {
                        if (ways == null) ways = new ArrayList<>(2);
                        ways.add(tunnel);
                    }
                }
            }
        }
        if (found == null && ways == null) return Galleries.EMPTY;
        return new Galleries(found == null ? new Hall[0] : found.toArray(new Hall[0]), ways == null ? new Tunnel[0] : ways.toArray(new Tunnel[0]));
    }

    // all gallery air, a chunk's worth looked up at a time
    Walk.Carving carving(boolean planned) {
        Map<Long, Galleries> chunks = new HashMap<>();
        return (x, y, z) -> chunks.computeIfAbsent(CellCache.key(x >> 4, z >> 4), k -> near(x & ~15, z & ~15, planned)).carves(x, y, z);
    }

    List<Hall> hallsAround(int x, int z, int distance) {
        List<Hall> found = new ArrayList<>();
        for (int cx = Math.floorDiv(x - distance, CELL); cx <= Math.floorDiv(x + distance, CELL); cx++) {
            for (int cz = Math.floorDiv(z - distance, CELL); cz <= Math.floorDiv(z + distance, CELL); cz++) {
                Hall hall = hall(cx, cz);
                if (hall != null && sq(hall.x - x) + sq(hall.z - z) <= sq(distance)) found.add(hall);
            }
        }
        return found;
    }

    // the links of a hall's cell: east, south, south-east, and the same three of the cells west, north and north-west into it
    static int[][] links(int cx, int cz) {
        return new int[][]{{cx, cz, 0}, {cx, cz, 1}, {cx, cz, 2}, {cx - 1, cz, 0}, {cx, cz - 1, 1}, {cx - 1, cz - 1, 2}};
    }

    List<Tunnel> tunnelsOf(Hall hall, boolean planned) {
        List<Tunnel> ways = new ArrayList<>(6);
        for (int[] link : links(hall.cellX, hall.cellZ)) {
            Tunnel way = planned ? plannedTunnel(link[0], link[1], link[2]) : tunnel(link[0], link[1], link[2]);
            if (way != null) ways.add(way);
        }
        return ways;
    }

    int layerFloor(int x, int z) {
        return (int) (DIRT_HEIGHT + level(x, z)) - DEPTH;
    }

    // the noise caves are kept out from here up; never closer to the real ground than the old upper band's depth
    int zoneBottom(int x, int z, int surface) {
        float jitter = ZONE_JITTER * (float) SimplexNoise.noise((x + misty.offsetX + 911) / 19.0, (z + misty.offsetZ + 1543) / 19.0);
        return Math.min(layerFloor(x, z) - ZONE_BELOW, surface - ZONE_UNDER_GROUND) + Math.round(jitter);
    }

    Hall hall(int cx, int cz) {
        return halls.get(CellCache.key(cx, cz), key -> makeHall(cx, cz));
    }

    private Hall makeHall(int cx, int cz) {
        int h = mix(cx, cz, Long.hashCode(misty.seed) ^ SALT);
        if ((h & 0xFF) >= HALL_CHANCE) return null;
        int span = CELL - 2 * CELL_EDGE;
        float x = cx * CELL + CELL_EDGE + Math.floorMod(mix(h, 1, 1), span) + 0.5f;
        float z = cz * CELL + CELL_EDGE + Math.floorMod(mix(h, 2, 2), span) + 0.5f;
        if (!MistyCaves.isMisty(map, (int) x, (int) z) || MistyCaves.weight(map, (int) x, (int) z) < MIN_WEIGHT) return null;
        float r1 = (mix(h, 3, 3) & 0xFF) / 255f;
        float r2 = (mix(h, 4, 4) & 0xFF) / 255f;
        float r3 = (mix(h, 5, 5) & 0xFF) / 255f;
        float r4 = (mix(h, 6, 6) & 0xFF) / 255f;
        float rx = 10 + (Hall.MAX_RADIUS - 10) * r1;
        float rz = rx * (0.65f + 0.35f * r2);
        float height = 8 + 5 * r3;
        int floor = layerFloor((int) x, (int) z) + Math.round((r4 - 0.5f) * DEPTH_SPREAD);
        if (floor < MIN_FLOOR) return null;
        int roll = mix(h, 7, 7) & 0xFF;
        Hall.Kind kind = roll < 65 ? Hall.Kind.PLAIN : roll < 95 ? Hall.Kind.ICEFALL : roll < 135 ? Hall.Kind.LAKE
                : roll < 175 ? Hall.Kind.PILLARS : roll < 215 ? Hall.Kind.COLLAPSED : Hall.Kind.CHIMNEY;
        if (kind == Hall.Kind.ICEFALL) height += 6;
        // off the centre, where the tunnels meet
        float chimneyX = x + (r1 - 0.5f) * rx * 0.5f;
        float chimneyZ = z + (r2 - 0.5f) * rz * 0.5f;
        int chimneyTop = (int) ground((int) chimneyX, (int) chimneyZ);
        if (kind == Hall.Kind.CHIMNEY && (chimneyTop - floor - height > CHIMNEY_MAX_RISE || chimneyTop - floor < CHIMNEY_LETHAL_DROP)) kind = Hall.Kind.PLAIN;
        Hall hall = new Hall(this, kind, cx, cz, x, z, floor, rx, rz, (float) (Math.PI * r4), height, h, chimneyX, chimneyZ, chimneyTop);
        float top = floor + height + COVER;
        float reach = Math.max(rx, rz);
        for (int i = 0; i <= 8; i++) {
            float px = i == 8 ? x : x + reach * MathHelper.cos(i * (float) Math.PI / 4);
            float pz = i == 8 ? z : z + reach * MathHelper.sin(i * (float) Math.PI / 4);
            if (ground((int) px, (int) pz) < top) return null;
        }
        return hall;
    }

    // a planned tunnel that can be walked end to end; one crossing another at a different height leaves a drop
    Tunnel tunnel(int cx, int cz, int link) {
        Tunnel tunnel = plannedTunnel(cx, cz, link);
        if (tunnel == null) return null;
        Boolean walks = walkable.get(CellCache.key(cx, cz) * 3 + link, key -> Walk.reaches(carving(true),
                (int) Math.floor(tunnel.a.x), tunnel.a.floor, (int) Math.floor(tunnel.a.z),
                (int) Math.floor(tunnel.b.x), tunnel.b.floor, (int) Math.floor(tunnel.b.z), (int) tunnel.length + 40));
        return walks ? tunnel : null;
    }

    Tunnel plannedTunnel(int cx, int cz, int link) {
        return planned.get(CellCache.key(cx, cz) * 3 + link, key -> makeTunnel(cx, cz, link));
    }

    private Tunnel makeTunnel(int cx, int cz, int link) {
        int h = mix(cx * 3 + link, cz, Long.hashCode(misty.seed) ^ SALT ^ 0x5bd1e995);
        if (link == 2 && (h & 0xFF) >= DIAGONAL_CHANCE) return null;
        Hall a = hall(cx, cz);
        if (a == null) return null;
        Hall b = hall(cx + (link == 1 ? 0 : 1), cz + (link == 0 ? 0 : 1));
        if (b == null) return null;
        Tunnel tunnel = new Tunnel(this, a, b, h);
        if (Math.abs(b.floor - a.floor) > MAX_GRADE * (tunnel.length - tunnel.flatA - tunnel.flatB)) return null;
        for (float t = 0; t <= tunnel.length; t += COVER_STEP) {
            float f = t / tunnel.length;
            int px = (int) Math.floor(tunnel.pointX(f));
            int pz = (int) Math.floor(tunnel.pointZ(f));
            if (ground(px, pz) < tunnel.floorAt(f, 0) + tunnel.height + tunnel.raise(t) + COVER + (tunnel.kind == Tunnel.Kind.CREVASSE ? 2 : 0)) return null;
            if (MistyCaves.weight(map, px, pz) < LINK_WEIGHT) return null;
        }
        return tunnel;
    }

    static float ground(int x, int z) {
        return DIRT_HEIGHT + MiddleEarthHeightMap.getHeight(x, z);
    }

    private float level(int x, int z) {
        int i = Math.floorDiv(x, LEVEL_STEP);
        int j = Math.floorDiv(z, LEVEL_STEP);
        float fx = (float) (x - i * LEVEL_STEP) / LEVEL_STEP;
        float fz = (float) (z - j * LEVEL_STEP) / LEVEL_STEP;
        float a = levelPoint(i, j);
        float b = levelPoint(i + 1, j);
        float c = levelPoint(i, j + 1);
        float d = levelPoint(i + 1, j + 1);
        float top = a + (b - a) * fx;
        float bottom = c + (d - c) * fx;
        return top + (bottom - top) * fz;
    }

    private float levelPoint(int i, int j) {
        return levels.get(CellCache.key(i, j), key -> {
            float sum = 0;
            for (int a = -2; a <= 2; a++) {
                for (int b = -2; b <= 2; b++) sum += MiddleEarthHeightMap.getHeight(i * LEVEL_STEP + a * LEVEL_SPAN, j * LEVEL_STEP + b * LEVEL_SPAN);
            }
            return sum / 25;
        });
    }
}
