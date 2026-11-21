package net.sevenstars.middleearth.features.generation.map;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.features.generation.biomes.BiomeKeyRegistryME;
import net.sevenstars.middleearth.features.generation.biomes.surface.MapBasedBiomePool;
import net.sevenstars.middleearth.features.generation.biomes.surface.MapBasedCustomBiome;
import net.sevenstars.middleearth.utils.resources.FileUtils;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

// a few rivers are painted on up the foot of a mountain; baked, the water carves the slope into a sheer wall.
// Their tips are taken back to where the ground is still low, a pixel at a time, and given the land beside them;
// the way they used to go is where the river now runs on under the mountain
public final class RiverHeads {
    private static final int CUTOFF = 40; // base height a river may not climb past
    private static final int MAX_NEIGHBOURS = 3; // a tip, not the bank of a wider river
    private static final int BLOCKS_PER_PIXEL = (int) (MiddleEarthMapConfigs.PIXEL_WEIGHT * Math.pow(2, MiddleEarthMapConfigs.MAP_ITERATION));
    private static final int[][] RING = {{-1, -1}, {0, -1}, {1, -1}, {1, 0}, {1, 1}, {0, 1}, {-1, 1}, {-1, 0}};
    private static final Set<RegistryKey<Biome>> RIVERS = Set.of(BiomeKeyRegistryME.RIVER, BiomeKeyRegistryME.NURN_RIVER,
            BiomeKeyRegistryME.MIRKWOOD_RIVER, BiomeKeyRegistryME.GREAT_RIVER, BiomeKeyRegistryME.RIVER_RUNNING, BiomeKeyRegistryME.MORGUL_RIVER);

    private static List<Head> heads;

    // the way a trimmed river went, in block centres from where it now ends into the mountain
    public record Head(float[] xs, float[] zs, int waterHeight) {
    }

    private RiverHeads() {
    }

    // a copy of the map with the climbing tips repainted; the base height is red, at the map's own scale
    public static BufferedImage trim(BufferedImage map, BufferedImage baseHeight) {
        BufferedImage out = new BufferedImage(map.getWidth(), map.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int z = 0; z < map.getHeight(); z++) {
            for (int x = 0; x < map.getWidth(); x++) out.setRGB(x, z, map.getRGB(x, z));
        }
        int trimmed = erode(map, baseHeight, out).size();
        MiddleEarth.LOGGER.logInfoMsg("River heads: %d river pixels trimmed".formatted(trimmed));
        return out;
    }

    // worked out again from the painted map, so instant-booted worlds know them too
    public static synchronized List<Head> heads() {
        if (heads != null) return heads;
        heads = List.of();
        BufferedImage map = FileUtils.getInstance().getResourceImage(MiddleEarthMapConfigs.INITIAL_IMAGE);
        BufferedImage baseHeight = FileUtils.getInstance().getRunImage(MiddleEarthMapConfigs.BASE_HEIGHT_PATH + MiddleEarthMapConfigs.BASE_HEIGHT_IMAGE_NAME);
        if (map == null || baseHeight == null) return heads;
        int width = map.getWidth();
        IntArrayList removed = erode(map, baseHeight, null);
        // removed from the far tip back, so the last one taken lies next to the river that is left
        Set<Integer> gone = new HashSet<>();
        for (int i = 0; i < removed.size(); i++) gone.add(removed.getInt(i));
        Set<Integer> left = new HashSet<>(gone);
        List<Head> found = new ArrayList<>();
        for (int i = removed.size() - 1; i >= 0; i--) {
            int start = removed.getInt(i);
            if (!left.remove(start)) continue;
            List<Integer> path = new ArrayList<>();
            path.add(start);
            int at = start;
            while (true) {
                int next = -1;
                for (int[] d : RING) {
                    int n = at + d[1] * width + d[0];
                    if (left.contains(n)) {
                        next = n;
                        break;
                    }
                }
                if (next < 0) break;
                left.remove(next);
                path.add(next);
                at = next;
            }
            int end = riverBeside(map, width, start, gone);
            if (end < 0) continue;
            path.addFirst(end);
            float[] xs = new float[path.size()];
            float[] zs = new float[path.size()];
            for (int k = 0; k < path.size(); k++) {
                xs[k] = (path.get(k) % width + 0.5f) * BLOCKS_PER_PIXEL;
                zs[k] = (path.get(k) / width + 0.5f) * BLOCKS_PER_PIXEL;
            }
            found.add(new Head(xs, zs, MapBasedBiomePool.getBiomeByColor(map.getRGB(end % width, end / width)).getWaterHeight()));
        }
        heads = List.copyOf(found);
        return heads;
    }

    // wears the climbing tips away, repainting them in out when given; the pixels taken, in the order taken
    private static IntArrayList erode(BufferedImage map, BufferedImage baseHeight, BufferedImage out) {
        int width = map.getWidth();
        int height = map.getHeight();
        boolean[] river = new boolean[width * height];
        for (int z = 0; z < height; z++) {
            for (int x = 0; x < width; x++) river[z * width + x] = isRiver(map.getRGB(x, z));
        }
        // a tip is worn back while it stands high; the inside of a river through a gorge has river on both sides and stays
        IntArrayList removed = new IntArrayList();
        boolean changed = true;
        while (changed) {
            changed = false;
            for (int z = 1; z < height - 1; z++) {
                for (int x = 1; x < width - 1; x++) {
                    if (!river[z * width + x] || (baseHeight.getRGB(x, z) >> 16 & 0xFF) <= CUTOFF || !tip(river, width, x, z)) continue;
                    river[z * width + x] = false;
                    if (out != null) out.setRGB(x, z, land(out, river, width, x, z, map.getRGB(x, z)));
                    removed.add(z * width + x);
                    changed = true;
                }
            }
        }
        return removed;
    }

    private static int riverBeside(BufferedImage map, int width, int pixel, Set<Integer> gone) {
        for (int[] d : RING) {
            int n = pixel + d[1] * width + d[0];
            if (!gone.contains(n) && isRiver(map.getRGB(n % width, n / width))) return n;
        }
        return -1;
    }

    private static boolean isRiver(int rgb) {
        MapBasedCustomBiome biome = MapBasedBiomePool.getBiomeByColor(rgb);
        return biome != null && RIVERS.contains(biome.getBiomeKey());
    }

    // few river neighbours, all in one arc, so taking it away splits nothing
    private static boolean tip(boolean[] river, int width, int x, int z) {
        int count = 0;
        int arcs = 0;
        for (int i = 0; i < RING.length; i++) {
            boolean here = river[(z + RING[i][1]) * width + x + RING[i][0]];
            int[] before = RING[(i + RING.length - 1) % RING.length];
            boolean previous = river[(z + before[1]) * width + x + before[0]];
            if (here) count++;
            if (here && !previous) arcs++;
        }
        return count <= MAX_NEIGHBOURS && (count == 0 || arcs == 1);
    }

    // the land most of its neighbours are
    private static int land(BufferedImage map, boolean[] river, int width, int x, int z, int fallback) {
        Map<Integer, Integer> votes = new HashMap<>();
        int best = fallback;
        int bestVotes = 0;
        for (int[] d : RING) {
            if (river[(z + d[1]) * width + x + d[0]]) continue;
            int rgb = map.getRGB(x + d[0], z + d[1]);
            int count = votes.merge(rgb, 1, Integer::sum);
            if (count > bestVotes) {
                best = rgb;
                bestVotes = count;
            }
        }
        return best;
    }
}
