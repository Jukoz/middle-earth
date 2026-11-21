package net.sevenstars.middleearth.features.generation.caves;

import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.sevenstars.middleearth.features.generation.biomes.BiomeKeyRegistryME;
import net.sevenstars.middleearth.features.generation.biomes.surface.MapBasedCustomBiome;
import net.sevenstars.middleearth.features.generation.map.MiddleEarthMapRuntime;
import net.sevenstars.middleearth.features.generation.noise.SimplexNoise;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static net.sevenstars.middleearth.features.generation.caves.GalleryLayout.ground;
import static net.sevenstars.middleearth.features.generation.caves.MistyCaves.mix;
import static net.sevenstars.middleearth.features.generation.caves.MistyCaves.sq;

// Gorgoroth's lava tubes: drained flows from Orodruin a few blocks under the ash, running downhill, branching,
// and open where the roof fell in; a few near the Mountain still carry fire. Laid out once per world seed
public final class LavaTubes {
    private static final float ORODRUIN_X = 68208;
    private static final float ORODRUIN_Z = 54896;
    private static final int REACH = 1500; // no tube goes further from the Mountain
    private static final int BOUNDS = REACH + 40;
    private static final int MAIN_TUBES = 9;
    private static final float STEP = 3;
    private static final float MAX_DROP = 0.45f; // per block along, so each step down is one block
    private static final float MAX_START_SLOPE = 0.25f; // tubes form where the cone eases into the plain
    private static final int MAX_RELIEF = 4; // a skylight only opens in fairly level ground
    private static final int TRIM = 3; // nodes taken off an end that came out into the open
    private static final int MIN_COVER = 5;
    private static final int MAX_COVER = 30; // deeper than this the lava ponded and stopped
    private static final int SKYLIGHT_SPACING = 70;
    private static final int COLLAPSE_SPACING = 12; // where the roof is too thin it falls in all along
    private static final int POND_MARGIN = 2;
    private static final int CLEARANCE = 6; // rock kept between tubes that do not meet
    private static final int OWN_REACH = 20; // nodes either side of a fork, or behind a tube's head, that it may touch
    private static final int GRID = 32;
    private static final int MARGIN = 8; // segments and skylights within this of a chunk are kept for it
    private static final int QUIET = 6; // the noise caves keep this far from a tube
    private static final int SHELL = 5; // rock round a tube that takes its biome, so no spring or ore opens into it
    private static final float WOBBLE = 0.12f;
    private static final float WOBBLE_FINE = 0.05f;
    static final float SWELL = 1 + (WOBBLE + WOBBLE_FINE) * Galleries.NOISE_BOUND; // the most the wobble widens a tube
    private static final float SPRING = 0.4f; // the walls stand straight up to here, then arch over
    private static final float TRENCH = 0.62f; // trench width under the benches, as a share of the tube's
    private static final float MOUTH = 1.6f; // a skylight is this much wider at the top
    private static final long SALT = 0x4f524f445255494eL; // ORODRUIN
    private static final Set<RegistryKey<Biome>> PLAIN = Set.of(BiomeKeyRegistryME.GORGOROTH, BiomeKeyRegistryME.GORGOROTH_ASHEN_WOODS, BiomeKeyRegistryME.GORGOROTH_DELTA);
    // the map paints the Mountain's flanks with the Ered Lithui biomes
    private static final Set<RegistryKey<Biome>> CONE = Set.of(BiomeKeyRegistryME.MOUNT_DOOM, BiomeKeyRegistryME.MOUNT_DOOM_PIT,
            BiomeKeyRegistryME.ERED_LITHUI, BiomeKeyRegistryME.ERED_LITHUI_BASE, BiomeKeyRegistryME.ERED_LITHUI_PEAKS);

    private static volatile LavaTubes current;

    final long seed;
    final int noiseX;
    final int noiseZ;
    private final Long2ObjectOpenHashMap<List<Segment>> segmentGrid = new Long2ObjectOpenHashMap<>();
    private final Long2ObjectOpenHashMap<List<Skylight>> skylightGrid = new Long2ObjectOpenHashMap<>();

    private LavaTubes(long seed) {
        this.seed = seed;
        this.noiseX = (int) Math.floorMod(seed ^ SALT, 4096L) + 1201;
        this.noiseZ = (int) Math.floorMod((seed ^ SALT) >>> 20, 4096L) + 619;
    }

    // a cheap test, so the rest of the world never lays out the tubes
    public static boolean mayTouch(int x, int z) {
        return Math.abs(x - ORODRUIN_X) < BOUNDS && Math.abs(z - ORODRUIN_Z) < BOUNDS;
    }

    public static boolean mayTouchChunk(int startX, int startZ) {
        return startX + 15 > ORODRUIN_X - BOUNDS && startX < ORODRUIN_X + BOUNDS && startZ + 15 > ORODRUIN_Z - BOUNDS && startZ < ORODRUIN_Z + BOUNDS;
    }

    public static LavaTubes of(long seed) {
        LavaTubes known = current;
        if (known != null && known.seed == seed) return known;
        // laid out once, not by every worker that reaches Mordor first
        synchronized (LavaTubes.class) {
            known = current;
            if (known == null || known.seed != seed) current = known = layOut(seed);
            return known;
        }
    }

    // the tubes and skylights that reach into a chunk, or null for none
    public Near near(int startX, int startZ) {
        int x0 = startX - MARGIN;
        int z0 = startZ - MARGIN;
        int x1 = startX + 15 + MARGIN;
        int z1 = startZ + 15 + MARGIN;
        List<Segment> segments = new ArrayList<>();
        List<Skylight> lights = new ArrayList<>();
        for (int cx = Math.floorDiv(x0, GRID); cx <= Math.floorDiv(x1, GRID); cx++) {
            for (int cz = Math.floorDiv(z0, GRID); cz <= Math.floorDiv(z1, GRID); cz++) {
                long key = CellCache.key(cx, cz);
                for (Segment s : segmentGrid.getOrDefault(key, List.of())) {
                    if (!segments.contains(s) && s.touches(x0, z0, x1, z1)) segments.add(s);
                }
                for (Skylight l : skylightGrid.getOrDefault(key, List.of())) {
                    if (!lights.contains(l) && l.box.maxX() >= x0 && l.box.minX() <= x1 && l.box.maxZ() >= z0 && l.box.minZ() <= z1) lights.add(l);
                }
            }
        }
        if (segments.isEmpty() && lights.isEmpty()) return null;
        return new Near(this, segments.toArray(new Segment[0]), lights.toArray(new Skylight[0]));
    }

    // the tubes' own biome, in them and the rock round them, not up in the skylights
    public boolean holds(int x, int y, int z) {
        for (Segment s : segmentGrid.getOrDefault(CellCache.key(Math.floorDiv(x, GRID), Math.floorDiv(z, GRID)), List.of())) {
            if (s.holds(x, y, z)) return true;
        }
        return false;
    }

    float wobble(int x, int y, int z) {
        return 1 + WOBBLE * (float) SimplexNoise.noise((x + noiseX) / 12.0, y / 7.0, (z + noiseZ) / 12.0)
                + WOBBLE_FINE * (float) SimplexNoise.noise((x + noiseZ) / 4.0, y / 4.0, (z + noiseX) / 4.0);
    }

    // one chunk's tubes; used by one thread at a time
    public static final class Near {
        final LavaTubes tubes;
        final Segment[] segments;
        final Skylight[] skylights;
        final int minY;
        final int maxY;
        // where segments and skylights overlap, the wobble of a block is worked out once
        private int wobbleX;
        private int wobbleY = Integer.MIN_VALUE;
        private int wobbleZ;
        private float wobble;

        Near(LavaTubes tubes, Segment[] segments, Skylight[] skylights) {
            this.tubes = tubes;
            this.segments = segments;
            this.skylights = skylights;
            int lo = Integer.MAX_VALUE;
            int hi = Integer.MIN_VALUE;
            for (Segment s : segments) {
                lo = Math.min(lo, s.minY);
                hi = Math.max(hi, s.maxY);
            }
            for (Skylight l : skylights) {
                lo = Math.min(lo, l.box.minY());
                hi = Math.max(hi, l.box.maxY());
            }
            this.minY = lo;
            this.maxY = hi;
        }

        float wobble(int x, int y, int z) {
            if (x != wobbleX || y != wobbleY || z != wobbleZ) {
                wobble = tubes.wobble(x, y, z);
                wobbleX = x;
                wobbleY = y;
                wobbleZ = z;
            }
            return wobble;
        }

        public boolean carves(int x, int y, int z) {
            if (y < minY || y > maxY) return false;
            for (Segment s : segments) {
                if (s.carves(this, x, y, z)) return true;
            }
            for (Skylight l : skylights) {
                if (l.carves(this, x, y, z)) return true;
            }
            return false;
        }

        // the noise caves are kept out of the rock round a tube, up to the sky
        public boolean quiet(int x, int y, int z) {
            if (y < minY - QUIET) return false;
            for (Segment s : segments) {
                if (s.quiet(x, y, z)) return true;
            }
            return false;
        }

        // the ground round a skylight, where no boulder goes
        public boolean nearSkylight(int x, int z) {
            for (Skylight l : skylights) {
                if (sq(x + 0.5f - l.x) + sq(z + 0.5f - l.z) < sq(l.mouth(3))) return true;
            }
            return false;
        }

        public void dress(Chunk chunk, BlockBuffer blocks) {
            LavaTubeDressing.dress(this, chunk, blocks);
        }
    }

    // LAYOUT

    private static LavaTubes layOut(long seed) {
        LavaTubes made = new LavaTubes(seed);
        Builder builder = new Builder(made, MiddleEarthMapRuntime.getInstance());
        int h0 = mix((int) seed, (int) (seed >>> 32), (int) SALT);
        List<Tube> mains = new ArrayList<>();
        int activeOffset = Math.floorMod(h0, 3);
        for (int i = 0; i < MAIN_TUBES; i++) {
            int h = mix(h0, i, 1);
            float bearing = (float) (Math.PI * 2 * i / MAIN_TUBES) + (u(h, 1) - 0.5f) * 0.5f;
            float[] start = builder.foot(bearing);
            if (start == null) continue;
            boolean active = i % 3 == activeOffset;
            float maxLength = 400 + 700 * u(h, 2);
            float w = 3.2f + 2.2f * u(h, 3);
            float height = 3.8f + 1.7f * u(h, 4);
            Tube tube = builder.walk(null, 0, start[0], start[1], bearing, Float.NaN, w, height, maxLength, active, h);
            if (tube != null) mains.add(tube);
        }
        for (Tube main : mains) {
            int branches = 1 + Math.floorMod(mix(main.hash, 7, 7), 3);
            for (int b = 0; b < branches; b++) {
                int h = mix(main.hash, 20 + b, 2);
                int count = main.nodes.size();
                if (count < 40) break;
                int fork = (int) (count * (0.25f + 0.55f * u(h, 1)));
                Node at = main.nodes.get(fork);
                Node before = main.nodes.get(fork - 1);
                float heading = (float) Math.atan2(at.z - before.z, at.x - before.x);
                float side = (h & 1) == 0 ? 1 : -1;
                heading += side * (0.45f + 0.4f * u(h, 2));
                builder.walk(main, fork, at.x, at.z, heading, at.floor, 2.3f + 1.1f * u(h, 3), 3.3f + 0.9f * u(h, 4), 80 + 220 * u(h, 5), false, h);
            }
        }
        builder.finish();
        return made;
    }

    static float u(int h, int k) {
        return (mix(h, k, k * 31) & 0xFFFF) / 65535f;
    }

    private static final class Builder {
        final LavaTubes made;
        final MiddleEarthMapRuntime map;
        final Map<Long, List<Segment>> building = new HashMap<>();
        final List<Tube> all = new ArrayList<>();

        Builder(LavaTubes made, MiddleEarthMapRuntime map) {
            this.made = made;
            this.map = map;
        }

        RegistryKey<Biome> biome(float x, float z) {
            MapBasedCustomBiome b = map.getBiome((int) x, (int) z);
            return b == null ? null : b.getBiomeKey();
        }

        // where a bearing leaves the cone and the ground eases off
        float[] foot(float bearing) {
            float c = MathHelper.cos(bearing);
            float s = MathHelper.sin(bearing);
            boolean outside = false;
            for (float r = 40; r < 700; r += 8) {
                float x = ORODRUIN_X + c * r;
                float z = ORODRUIN_Z + s * r;
                RegistryKey<Biome> key = biome(x, z);
                if (CONE.contains(key)) continue;
                if (!PLAIN.contains(key)) return null;
                if (!outside) {
                    outside = true;
                    r += 16;
                    continue;
                }
                float[] g = gradient(x, z);
                if (Math.hypot(g[0], g[1]) < MAX_START_SLOPE) return new float[]{x, z};
            }
            return null;
        }

        // how far the real ground rises and falls round a point
        float relief(float x, float z, float w) {
            float reach = w * 1.6f + 3;
            float low = Float.MAX_VALUE;
            float high = -Float.MAX_VALUE;
            for (int a = -1; a <= 1; a++) {
                for (int b = -1; b <= 1; b++) {
                    float g = ground((int) (x + a * reach), (int) (z + b * reach));
                    low = Math.min(low, g);
                    high = Math.max(high, g);
                }
            }
            return high - low;
        }

        float smooth(float x, float z) {
            float sum = 0;
            for (int a = -1; a <= 1; a++) {
                for (int b = -1; b <= 1; b++) sum += ground((int) x + a * 16, (int) z + b * 16);
            }
            return sum / 9;
        }

        float[] gradient(float x, float z) {
            float d = 12;
            return new float[]{(smooth(x + d, z) - smooth(x - d, z)) / (2 * d), (smooth(x, z + d) - smooth(x, z - d)) / (2 * d)};
        }

        // a tube from its head down the plain; null if it came to nothing
        Tube walk(Tube parent, int fork, float x, float z, float heading, float floor, float baseW, float baseH, float maxLength, boolean active, int hash) {
            Tube tube = new Tube(parent, active ? 120 + 130 * u(hash, 9) : 0, hash);
            float along = 0;
            float lastLight = parent == null ? -SKYLIGHT_SPACING / 2f : 0;
            float phase = 100 * u(hash, 10);
            boolean exposed = false; // ended where the ground fell away above it
            boolean joined = false;
            while (true) {
                float w = baseW * (1 + 0.22f * noise(along / 40 + phase, 0));
                float h = baseH * (1 + 0.15f * noise(along / 52 + phase, 7));
                float bench = baseW >= 3.5f && h >= 4.5f && noise(along / 60 + phase, 3) > 0.15f ? 1 : 0;
                // the lowest real ground across the tube, and whether a pond lies over it
                float px = -MathHelper.sin(heading);
                float pz = MathHelper.cos(heading);
                float reach = w * SWELL + 2;
                float low = Float.MAX_VALUE;
                boolean pond = false;
                for (int k = -1; k <= 1; k++) {
                    int sx = (int) Math.floor(x + px * reach * k);
                    int sz = (int) Math.floor(z + pz * reach * k);
                    float g = ground(sx, sz);
                    low = Math.min(low, g);
                    MapBasedCustomBiome b = map.getBiome(sx, sz);
                    if (b != null && g <= b.getWaterHeight() + 1.25f + POND_MARGIN) pond = true;
                }
                float roofTop = h * SWELL;
                float cover = MIN_COVER + 1 + 3 * (noise(along / 80 + phase, 5) * 0.5f + 0.5f);
                float target = Math.min(smooth(x, z), low) - cover - roofTop;
                float next = Float.isNaN(floor) ? target : Math.max(Math.min(floor, target), floor - MAX_DROP * STEP);
                float roofCover = low - (next + roofTop);
                if (roofCover > MAX_COVER) break;
                boolean light = false;
                if (roofCover < MIN_COVER) {
                    // a thin roof falls in now and then; under a pond, or open between falls, the tube ends
                    exposed = pond || roofCover < 2;
                    if (pond || roofCover < -2) break;
                    if (roofCover < 3 && along - lastLight >= COLLAPSE_SPACING) {
                        light = true;
                        exposed = false;
                    } else if (roofCover < 2) break;
                } else if (!pond && along - lastLight > SKYLIGHT_SPACING && roofCover < 12 && (mix(hash, (int) along, 3) & 63) < 3) {
                    light = true;
                }
                if (light) light = relief(x, z, w) <= MAX_RELIEF;
                Node node = new Node(x, z, next, w, h, bench, along, pond ? -1 : roofCover);
                Join join = tube.nodes.isEmpty() ? Join.CLEAR : clearance(tube, parent, fork, node);
                if (join == Join.BLOCKED) break;
                tube.add(node);
                floor = next;
                if (join == Join.MEETS) {
                    joined = true;
                    break;
                }
                if (light) {
                    tube.skylights.add(new Skylight(x, z, node, hash ^ (int) along));
                    lastLight = along;
                }
                // the heading: out from the Mountain and down the slope, with a slow meander
                float ox = x - ORODRUIN_X;
                float oz = z - ORODRUIN_Z;
                float out = (float) Math.hypot(ox, oz);
                float[] g = gradient(x, z);
                float slope = (float) Math.hypot(g[0], g[1]);
                float wantX = 0.35f * ox / out - (slope > 0.02f ? g[0] / slope : 0);
                float wantZ = 0.35f * oz / out - (slope > 0.02f ? g[1] / slope : 0);
                float want = (float) Math.atan2(wantZ, wantX);
                heading += MathHelper.clamp(MathHelper.wrapDegrees((want - heading) * MathHelper.DEGREES_PER_RADIAN) * MathHelper.RADIANS_PER_DEGREE, -0.06f, 0.06f)
                        + 0.07f * noise(along / 45 + phase, 11);
                x += MathHelper.cos(heading) * STEP;
                z += MathHelper.sin(heading) * STEP;
                along += STEP;
                if (along > maxLength || Math.hypot(x - ORODRUIN_X, z - ORODRUIN_Z) > REACH || !PLAIN.contains(biome(x, z))) break;
            }
            if (exposed) {
                // back off under a whole roof, so the rounded end never breaks out of a slope
                while (!tube.nodes.isEmpty() && tube.nodes.getLast().cover < MIN_COVER) tube.removeLast();
                for (int k = 0; k < TRIM && !tube.nodes.isEmpty(); k++) tube.removeLast();
                tube.skylights.removeIf(l -> !tube.nodes.contains(l.node));
            }
            if (parent == null && tube.skylights.isEmpty()) {
                // every main tube has a way in: where the roof is thinnest on level ground
                Node best = null;
                for (Node n : tube.nodes) {
                    if (n.along < 30 || n.cover < 3 || n.cover > 14 || best != null && n.cover >= best.cover) continue;
                    if (relief(n.x, n.z, n.w) <= MAX_RELIEF) best = n;
                }
                if (best != null) tube.skylights.add(new Skylight(best.x, best.z, best, hash ^ (int) best.along));
            }
            int least = joined ? 8 : parent == null ? 30 : 15;
            if (tube.nodes.size() < least) return null;
            all.add(tube);
            if (parent != null) parent.branches.add(tube);
            for (Segment s : tube.segments) register(s, building);
            return tube;
        }

        enum Join { CLEAR, MEETS, BLOCKED }

        // at its own level a tube joins another; well above or below it passes; else it stops short
        Join clearance(Tube tube, Tube parent, int fork, Node node) {
            List<Segment> near = new ArrayList<>();
            int cx = Math.floorDiv((int) node.x, GRID);
            int cz = Math.floorDiv((int) node.z, GRID);
            for (int i = cx - 1; i <= cx + 1; i++) {
                for (int j = cz - 1; j <= cz + 1; j++) {
                    for (Segment s : building.getOrDefault(CellCache.key(i, j), List.of())) {
                        if (s.tube != parent || Math.abs(s.index - fork) >= OWN_REACH) near.add(s);
                    }
                }
            }
            // a tube meandering back on itself counts too, all but the stretch just behind
            near.addAll(tube.segments.subList(0, Math.max(0, tube.nodes.size() - OWN_REACH - 1)));
            for (Segment s : near) {
                float t = s.project(node.x, node.z);
                float d = (float) Math.sqrt(s.lateral2(node.x, node.z, t));
                float ow = MathHelper.lerp(t, s.a.w, s.b.w);
                if (d >= node.w + ow + CLEARANCE) continue;
                float of = MathHelper.lerp(t, s.a.floor, s.b.floor);
                float oh = MathHelper.lerp(t, s.a.h, s.b.h) * SWELL;
                if (Math.abs(node.floor - of) <= 1) {
                    // run on into the other tube and stop there, on its floor
                    node.floor = of;
                    tube.meeting = new Node(s.a.x + s.dx * t, s.a.z + s.dz * t, of, node.w, node.h, 0, node.along + d, node.cover);
                    return Join.MEETS;
                }
                if (node.floor >= of + oh + CLEARANCE / 2f || of >= node.floor + node.h * SWELL + CLEARANCE / 2f) continue;
                return Join.BLOCKED;
            }
            return Join.CLEAR;
        }

        void finish() {
            for (Tube tube : all) {
                if (tube.meeting != null) {
                    tube.add(tube.meeting);
                    register(tube.segments.getLast(), building);
                }
            }
            // a tube nobody can get into is left out, along with what joins only it
            Map<Tube, Integer> group = new HashMap<>();
            for (int i = 0; i < all.size(); i++) group.put(all.get(i), i);
            boolean changed = true;
            while (changed) {
                changed = false;
                for (Tube tube : all) {
                    for (Tube other : linked(tube)) {
                        int a = group.get(tube);
                        int b = group.get(other);
                        if (a != b) {
                            int low = Math.min(a, b);
                            group.put(tube, low);
                            group.put(other, low);
                            changed = true;
                        }
                    }
                }
            }
            Set<Integer> lit = new HashSet<>();
            for (Tube tube : all) {
                if (!tube.skylights.isEmpty()) lit.add(group.get(tube));
            }
            for (Tube tube : all) {
                if (!lit.contains(group.get(tube))) continue;
                for (Segment s : tube.segments) register(s, made.segmentGrid);
                for (Skylight l : tube.skylights) {
                    for (int i = Math.floorDiv(l.box.minX(), GRID); i <= Math.floorDiv(l.box.maxX(), GRID); i++) {
                        for (int j = Math.floorDiv(l.box.minZ(), GRID); j <= Math.floorDiv(l.box.maxZ(), GRID); j++) {
                            made.skylightGrid.computeIfAbsent(CellCache.key(i, j), k -> new ArrayList<>()).add(l);
                        }
                    }
                }
            }
        }

        List<Tube> linked(Tube tube) {
            List<Tube> out = new ArrayList<>(tube.branches);
            if (tube.parent != null) out.add(tube.parent);
            if (tube.meeting != null) {
                for (Tube other : all) {
                    if (other != tube && touches(other, tube.meeting)) out.add(other);
                }
            }
            return out;
        }

        boolean touches(Tube tube, Node point) {
            for (Segment s : tube.segments) {
                if (sq(point.x - s.a.x) + sq(point.z - s.a.z) < 4 || sq(point.x - s.b.x) + sq(point.z - s.b.z) < 4) return true;
                if (s.lateral2(point.x, point.z, s.project(point.x, point.z)) < 1) return true;
            }
            return false;
        }

        static void register(Segment s, Map<Long, List<Segment>> grid) {
            for (int i = Math.floorDiv(s.minX, GRID); i <= Math.floorDiv(s.maxX, GRID); i++) {
                for (int j = Math.floorDiv(s.minZ, GRID); j <= Math.floorDiv(s.maxZ, GRID); j++) {
                    grid.computeIfAbsent(CellCache.key(i, j), k -> new ArrayList<>()).add(s);
                }
            }
        }

        static float noise(float t, int salt) {
            return (float) SimplexNoise.noise(t, salt * 17.3);
        }
    }

    // a point on a tube's line, with the tube's size there
    static final class Node {
        final float x;
        final float z;
        float floor; // the lowest air; the floor block is one under
        final float w; // half width
        final float h;
        final float bench; // bench height over the trench floor, 0 for none
        final float along;
        final float cover; // rock over the roof, -1 under a pond

        Node(float x, float z, float floor, float w, float h, float bench, float along, float cover) {
            this.x = x;
            this.z = z;
            this.floor = floor;
            this.w = w;
            this.h = h;
            this.bench = bench;
            this.along = along;
            this.cover = cover;
        }

        int floorY() {
            return Math.round(floor);
        }
    }

    static final class Tube {
        final Tube parent;
        final float hotLength; // 0 for a cold tube
        final int hash;
        final List<Node> nodes = new ArrayList<>();
        final List<Segment> segments = new ArrayList<>();
        final List<Skylight> skylights = new ArrayList<>();
        final List<Tube> branches = new ArrayList<>();
        Node meeting;

        Tube(Tube parent, float hotLength, int hash) {
            this.parent = parent;
            this.hotLength = hotLength;
            this.hash = hash;
        }

        // still carries fire near the Mountain
        boolean active() {
            return hotLength > 0;
        }

        void add(Node node) {
            nodes.add(node);
            if (nodes.size() > 1) segments.add(new Segment(this, nodes.size() - 2, nodes.get(nodes.size() - 2), node));
        }

        void removeLast() {
            nodes.removeLast();
            if (!segments.isEmpty()) segments.removeLast();
        }
    }

    // a stretch between two nodes: a low arch with a flat floor, sometimes with benches
    static final class Segment {
        final Tube tube;
        final int index;
        final Node a;
        final Node b;
        final float dx;
        final float dz;
        final float length2;
        final float length;
        final int minX;
        final int maxX;
        final int minZ;
        final int maxZ;
        final int minY;
        final int maxY;

        Segment(Tube tube, int index, Node a, Node b) {
            this.tube = tube;
            this.index = index;
            this.a = a;
            this.b = b;
            this.dx = b.x - a.x;
            this.dz = b.z - a.z;
            this.length2 = Math.max(1e-4f, dx * dx + dz * dz);
            this.length = (float) Math.sqrt(length2);
            float reach = Math.max(a.w, b.w) * SWELL + QUIET + 1;
            this.minX = (int) Math.floor(Math.min(a.x, b.x) - reach);
            this.maxX = (int) Math.ceil(Math.max(a.x, b.x) + reach);
            this.minZ = (int) Math.floor(Math.min(a.z, b.z) - reach);
            this.maxZ = (int) Math.ceil(Math.max(a.z, b.z) + reach);
            this.minY = Math.min(a.floorY(), b.floorY());
            this.maxY = Math.max(a.floorY(), b.floorY()) + (int) Math.ceil(Math.max(a.h, b.h) * SWELL) + 1;
        }

        boolean touches(int x0, int z0, int x1, int z1) {
            return maxX >= x0 && minX <= x1 && maxZ >= z0 && minZ <= z1;
        }

        float project(float px, float pz) {
            return MathHelper.clamp(((px - a.x) * dx + (pz - a.z) * dz) / length2, 0f, 1f);
        }

        float lateral2(float px, float pz, float t) {
            return sq(px - a.x - dx * t) + sq(pz - a.z - dz * t);
        }

        // across the line, signed, for the channel's meander
        float side(float px, float pz) {
            return ((px - a.x) * dz - (pz - a.z) * dx) / length;
        }

        int floorY(float t) {
            return Math.round(MathHelper.lerp(t, a.floor, b.floor));
        }

        float width(float t) {
            return MathHelper.lerp(t, a.w, b.w);
        }

        float height(float t) {
            return MathHelper.lerp(t, a.h, b.h);
        }

        float along(float t) {
            return MathHelper.lerp(t, a.along, b.along);
        }

        boolean carves(Near near, int x, int y, int z) {
            if (y < minY || y > maxY || x < minX || x > maxX || z < minZ || z > maxZ) return false;
            float px = x + 0.5f;
            float pz = z + 0.5f;
            float t = project(px, pz);
            float w = width(t);
            float u2 = lateral2(px, pz, t);
            if (u2 >= sq(w * SWELL)) return false;
            int dy = y - floorY(t);
            float h = height(t);
            if (dy < 0 || dy >= h * SWELL) return false;
            float wob = near.wobble(x, y, z);
            float hh = h * wob;
            if (dy >= hh) return false;
            float ww = w * wob;
            float share = dy / hh;
            if (share > SPRING) ww *= (float) Math.sqrt(1 - sq((share - SPRING) / (1 - SPRING)));
            if (dy < MathHelper.lerp(t, a.bench, b.bench)) ww *= TRENCH;
            return u2 < ww * ww;
        }

        boolean quiet(int x, int y, int z) {
            if (x < minX || x > maxX || z < minZ || z > maxZ) return false;
            float px = x + 0.5f;
            float pz = z + 0.5f;
            float t = project(px, pz);
            return y >= floorY(t) - QUIET && lateral2(px, pz, t) < sq(width(t) * SWELL + QUIET);
        }

        boolean holds(int x, int y, int z) {
            if (x < minX || x > maxX || z < minZ || z > maxZ) return false;
            float px = x + 0.5f;
            float pz = z + 0.5f;
            float t = project(px, pz);
            int floor = floorY(t);
            return y >= floor - SHELL && y <= floor + height(t) * SWELL + SHELL && lateral2(px, pz, t) < sq(width(t) * SWELL + SHELL);
        }
    }

    // where the roof has fallen in: a shaft up to the plain, wider at the top, over a cone of rubble
    static final class Skylight {
        final float x;
        final float z;
        final int floor;
        final int roof;
        final int top;
        final float radius;
        final Box box;
        final Node node;

        Skylight(float tx, float tz, Node at, int hash) {
            this.node = at;
            float off = (u(hash, 1) - 0.5f) * at.w * 0.6f;
            float angle = (float) (Math.PI * 2 * u(hash, 2));
            this.x = tx + MathHelper.cos(angle) * off;
            this.z = tz + MathHelper.sin(angle) * off;
            this.radius = Math.min(at.w * 0.9f, 2 + 2.2f * u(hash, 3));
            this.floor = at.floorY();
            this.roof = floor + (int) Math.floor(at.h * 0.7f);
            float reach = radius * MOUTH * SWELL + 1;
            int high = Integer.MIN_VALUE;
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) high = Math.max(high, (int) ground((int) (x + i * reach), (int) (z + j * reach)));
            }
            this.top = high + 3;
            this.box = Box.around(x, z, reach, roof, top);
        }

        // the radius at the top of the shaft, and a little round it
        float mouth(float extra) {
            return radius * MOUTH + extra;
        }

        boolean carves(Near near, int bx, int y, int bz) {
            if (!box.contains(bx, y, bz)) return false;
            float rise = MathHelper.clamp((y - roof) / (float) Math.max(1, top - 3 - roof), 0f, 1f);
            float r = radius * (1 + (MOUTH - 1) * rise) * near.wobble(bx, y, bz);
            return sq(bx + 0.5f - x) + sq(bz + 0.5f - z) < r * r;
        }
    }
}
