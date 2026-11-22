package net.sevenstars.middleearth.features.generation.map;

import it.unimi.dsi.fastutil.ints.Int2BooleanOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
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
import java.util.List;
import java.util.Map;
import java.util.Set;

// rivers painted up a mountain's foot bake into sheer walls; their tips are trimmed back to low ground and RiverCaves
// runs the old course under the mountain
public final class RiverHeads {
    private static final int CUTOFF = 40; // base height a river may not climb past
    private static final int MAX_NEIGHBOURS = 3; // a tip, not the bank of a wider river
    private static final int BLOCKS_PER_PIXEL = (int) (MiddleEarthMapConfigs.PIXEL_WEIGHT * Math.pow(2, MiddleEarthMapConfigs.MAP_ITERATION));
    private static final int[][] RING = {{-1, -1}, {0, -1}, {1, -1}, {1, 0}, {1, 1}, {0, 1}, {-1, 1}, {-1, 0}};
    private static final Set<RegistryKey<Biome>> RIVERS = Set.of(BiomeKeyRegistryME.RIVER, BiomeKeyRegistryME.NURN_RIVER,
            BiomeKeyRegistryME.MIRKWOOD_RIVER, BiomeKeyRegistryME.GREAT_RIVER, BiomeKeyRegistryME.RIVER_RUNNING, BiomeKeyRegistryME.MORGUL_RIVER);

    private static List<Head> heads;

    // a trimmed river's old course in block centres, from where it now ends
    public record Head(float[] xs, float[] zs, int waterHeight) {
    }

    private RiverHeads() {
    }

    // the map with high tips repainted; base height is red, at the map's scale
    public static BufferedImage trim(BufferedImage map, BufferedImage baseHeight) {
        int width = map.getWidth();
        int height = map.getHeight();
        BufferedImage out = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        out.setRGB(0, 0, width, height, map.getRGB(0, 0, width, height, null, 0, width), 0, width);
        int trimmed = erode(map, baseHeight, out).size();
        MiddleEarth.LOGGER.logInfoMsg("River heads: %d river pixels trimmed".formatted(trimmed));
        return out;
    }

    // redone from the painted map so instant-booted worlds have them too
    public static synchronized List<Head> heads() {
        if (heads != null) return heads;
        heads = List.of();
        BufferedImage map = FileUtils.getInstance().getResourceImage(MiddleEarthMapConfigs.INITIAL_IMAGE);
        BufferedImage baseHeight = FileUtils.getInstance().getRunImage(MiddleEarthMapConfigs.BASE_HEIGHT_PATH + MiddleEarthMapConfigs.BASE_HEIGHT_IMAGE_NAME);
        if (map == null || baseHeight == null) return heads;
        int width = map.getWidth();
        IntArrayList removed = erode(map, baseHeight, null);
        // taken from the far tip back, so the last one taken touches the river left
        IntOpenHashSet gone = new IntOpenHashSet(removed);
        IntOpenHashSet left = new IntOpenHashSet(removed);
        List<Head> found = new ArrayList<>();
        for (int i = removed.size() - 1; i >= 0; i--) {
            int start = removed.getInt(i);
            if (!left.remove(start)) continue;
            IntArrayList path = new IntArrayList();
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
            path.add(0, end);
            float[] xs = new float[path.size()];
            float[] zs = new float[path.size()];
            for (int k = 0; k < path.size(); k++) {
                xs[k] = (path.getInt(k) % width + 0.5f) * BLOCKS_PER_PIXEL;
                zs[k] = (path.getInt(k) / width + 0.5f) * BLOCKS_PER_PIXEL;
            }
            found.add(new Head(xs, zs, MapBasedBiomePool.getBiomeByColor(map.getRGB(end % width, end / width)).getWaterHeight()));
        }
        heads = List.copyOf(found);
        return heads;
    }

    // wears high tips away, repainting into out if given; the pixels in the order taken
    private static IntArrayList erode(BufferedImage map, BufferedImage baseHeight, BufferedImage out) {
        int width = map.getWidth();
        int height = map.getHeight();
        int[] rgb = map.getRGB(0, 0, width, height, null, 0, width);
        int[] base = baseHeight.getRGB(0, 0, width, height, null, 0, width);
        Int2BooleanOpenHashMap riverColours = new Int2BooleanOpenHashMap();
        boolean[] river = new boolean[width * height];
        for (int i = 0; i < river.length; i++) river[i] = riverColours.computeIfAbsent(rgb[i], RiverHeads::isRiver);
        // only high river pixels can go, so each pass walks those, in scan order
        IntArrayList high = new IntArrayList();
        for (int z = 1; z < height - 1; z++) {
            for (int x = 1; x < width - 1; x++) {
                int i = z * width + x;
                if (river[i] && (base[i] >> 16 & 0xFF) > CUTOFF) high.add(i);
            }
        }
        // high tips wear away; a river through a gorge has river on both sides and stays
        IntArrayList removed = new IntArrayList();
        boolean changed = true;
        while (changed) {
            changed = false;
            for (int k = 0; k < high.size(); k++) {
                int i = high.getInt(k);
                int x = i % width;
                int z = i / width;
                if (!river[i] || !tip(river, width, x, z)) continue;
                river[i] = false;
                if (out != null) out.setRGB(x, z, land(out, river, width, x, z, rgb[i]));
                removed.add(i);
                changed = true;
            }
        }
        return removed;
    }

    private static int riverBeside(BufferedImage map, int width, int pixel, IntOpenHashSet gone) {
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
