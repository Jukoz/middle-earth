package net.sevenstars.middleearth.features.generation.map;

import net.minecraft.registry.RegistryKey;
import net.sevenstars.middleearth.MiddleEarth;
import net.minecraft.world.biome.Biome;
import net.sevenstars.middleearth.features.generation.biomes.BiomeKeyRegistryME;
import net.sevenstars.middleearth.features.generation.biomes.surface.MapBasedBiomePool;
import net.sevenstars.middleearth.features.generation.biomes.surface.MapBasedCustomBiome;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

// a few rivers are painted on up the foot of a mountain; baked, the water carves the slope into a sheer wall.
// Their tips are taken back to where the ground is still low, a pixel at a time, and given the land beside them
public final class RiverHeads {
    private static final int CUTOFF = 40; // base height a river may not climb past
    private static final int MAX_NEIGHBOURS = 3; // a tip, not the bank of a wider river
    private static final int[][] RING = {{-1, -1}, {0, -1}, {1, -1}, {1, 0}, {1, 1}, {0, 1}, {-1, 1}, {-1, 0}};
    private static final Set<RegistryKey<Biome>> RIVERS = Set.of(BiomeKeyRegistryME.RIVER, BiomeKeyRegistryME.NURN_RIVER,
            BiomeKeyRegistryME.MIRKWOOD_RIVER, BiomeKeyRegistryME.GREAT_RIVER, BiomeKeyRegistryME.RIVER_RUNNING, BiomeKeyRegistryME.MORGUL_RIVER);

    private RiverHeads() {
    }

    // a copy of the map with the climbing tips repainted; the base height is red, at the map's own scale
    public static BufferedImage trim(BufferedImage map, BufferedImage baseHeight) {
        int width = map.getWidth();
        int height = map.getHeight();
        BufferedImage out = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        boolean[] river = new boolean[width * height];
        for (int z = 0; z < height; z++) {
            for (int x = 0; x < width; x++) {
                int rgb = map.getRGB(x, z);
                out.setRGB(x, z, rgb);
                river[z * width + x] = isRiver(rgb);
            }
        }
        // a tip is worn back while it stands high; the inside of a river through a gorge has river on both sides and stays
        int trimmed = 0;
        boolean changed = true;
        while (changed) {
            changed = false;
            for (int z = 1; z < height - 1; z++) {
                for (int x = 1; x < width - 1; x++) {
                    if (!river[z * width + x] || (baseHeight.getRGB(x, z) >> 16 & 0xFF) <= CUTOFF || !tip(river, width, x, z)) continue;
                    river[z * width + x] = false;
                    out.setRGB(x, z, land(out, river, width, x, z, map.getRGB(x, z)));
                    changed = true;
                    trimmed++;
                }
            }
        }
        MiddleEarth.LOGGER.logInfoMsg("River heads: %d river pixels trimmed".formatted(trimmed));
        return out;
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
