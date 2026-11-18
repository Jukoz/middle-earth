package net.sevenstars.middleearth.features.generation.map;

import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.sevenstars.middleearth.utils.resources.FileUtils;
import net.sevenstars.middleearth.features.generation.biomes.surface.MapBasedBiomePool;
import net.sevenstars.middleearth.features.generation.biomes.surface.MapBasedCustomBiome;
import net.sevenstars.middleearth.features.generation.biomes.surface.SubBiome;
import net.sevenstars.middleearth.features.generation.biomes.surface.SubBiomes;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;

// Ponds, coasts, caves and Isengard come from height or caves, so they aren't on the map
public class MapBiomeLocator {
    private static short[] pixels;
    private static int width;
    private static int height;
    // null for colours the pool doesn't know
    private static MapBasedCustomBiome[] palette;
    private static Set<RegistryKey<Biome>> findableBiomes;

    private static synchronized void load() {
        if (pixels != null) return;

        BufferedImage image = FileUtils.getInstance().getResourceImage(MiddleEarthMapConfigs.INITIAL_IMAGE);
        width = image.getWidth();
        height = image.getHeight();
        int[] argb = image.getRGB(0, 0, width, height, null, 0, width);

        Map<Integer, MapBasedCustomBiome> biomeByRgb = new HashMap<>();
        for (Map.Entry<Color, MapBasedCustomBiome> entry : MapBasedBiomePool.getMapBiomes().entrySet()) {
            biomeByRgb.put(entry.getKey().getRGB(), entry.getValue());
        }

        Map<Integer, Short> indexByRgb = new HashMap<>();
        List<MapBasedCustomBiome> paletteList = new ArrayList<>();
        short[] indexed = new short[argb.length];
        for (int i = 0; i < argb.length; i++) {
            // map.png has alpha, the pool colours don't
            int rgb = argb[i] | 0xFF000000;
            Short index = indexByRgb.get(rgb);
            if (index == null) {
                index = (short) paletteList.size();
                indexByRgb.put(rgb, index);
                paletteList.add(biomeByRgb.get(rgb));
            }
            indexed[i] = index;
        }
        palette = paletteList.toArray(new MapBasedCustomBiome[0]);

        Set<RegistryKey<Biome>> findable = new LinkedHashSet<>();
        for (MapBasedCustomBiome biome : palette) {
            if (biome == null) continue;
            findable.add(biome.getBiomeKey());
            SubBiome subBiome = SubBiomes.getSubBiome(biome.getBiomeKey());
            if (subBiome != null) {
                for (SubBiome.SubBiomeData data : subBiome.subBiomesData) findable.add(data.biome);
            }
        }
        findableBiomes = findable;
        pixels = indexed;
    }

    public static Set<RegistryKey<Biome>> getFindableBiomes() {
        load();
        return findableBiomes;
    }

    // The map is only a guide: the generated tiles shift borders by up to a pixel, and ponds, coasts
    // and Isengard replace map biomes, so every spot is checked against the real biome source.
    public static Optional<BlockPos> locate(RegistryKey<Biome> target, int originX, int originZ, BiFunction<Integer, Integer, RegistryKey<Biome>> realBiome) {
        load();

        float ratioX = MiddleEarthMapUtils.getInstance().ratioX;
        float ratioZ = MiddleEarthMapUtils.getInstance().ratioZ;

        boolean[] candidate = new boolean[palette.length];
        boolean anyCandidate = false;
        for (int i = 0; i < palette.length; i++) {
            MapBasedCustomBiome biome = palette[i];
            if (biome == null) continue;
            SubBiome subBiome = SubBiomes.getSubBiome(biome.getBiomeKey());
            candidate[i] = biome.getBiomeKey().equals(target) || (subBiome != null && subBiome.containsSubBiome(target));
            anyCandidate |= candidate[i];
        }
        if (!anyCandidate) return Optional.empty();

        int originPixelX = Math.floorDiv(originX, (int) ratioX);
        int originPixelZ = Math.floorDiv(originZ, (int) ratioZ);
        int maxRing = Math.max(
                Math.max(Math.abs(originPixelX), Math.abs(width - 1 - originPixelX)),
                Math.max(Math.abs(originPixelZ), Math.abs(height - 1 - originPixelZ))) + 1;

        long bestDistance = Long.MAX_VALUE;
        BlockPos best = null;
        // rivers and other thin biomes may never fill a whole 3x3 block of cells, so keep a plain match as fallback
        long plainDistance = Long.MAX_VALUE;
        BlockPos plain = null;
        for (int ring = 0; ring <= maxRing; ring++) {
            // nothing on this ring or past it can be closer. A plain match alone still lets the
            // search go on to twice its distance, looking for a spot that passes matchesAround.
            long ringDistance = (long) Math.max(0, ring - 1) * (long) ratioX;
            long limit = best != null ? bestDistance : plain != null ? 4 * plainDistance : Long.MAX_VALUE;
            if (ringDistance * ringDistance > limit) break;

            for (int dx = -ring; dx <= ring; dx++) {
                int step = (dx == -ring || dx == ring) ? 1 : 2 * ring;
                for (int dz = -ring; dz <= ring; dz += step) {
                    int px = originPixelX + dx;
                    int pz = originPixelZ + dz;
                    if (!nearCandidate(candidate, px, pz)) continue;

                    for (int sx = SAMPLE_STEP / 2; sx < ratioX; sx += SAMPLE_STEP) {
                        for (int sz = SAMPLE_STEP / 2; sz < ratioZ; sz += SAMPLE_STEP) {
                            int x = (int) (px * ratioX) + sx;
                            int z = (int) (pz * ratioZ) + sz;
                            long distance = (long) (x - originX) * (x - originX) + (long) (z - originZ) * (z - originZ);
                            if (distance >= bestDistance) continue;
                            if (!target.equals(realBiome.apply(x, z))) continue;
                            if (distance < plainDistance) {
                                plainDistance = distance;
                                plain = new BlockPos(x, 0, z);
                            }
                            if (!matchesAround(target, x, z, realBiome)) continue;

                            bestDistance = distance;
                            best = new BlockPos(x, 0, z);
                        }
                    }
                }
            }
        }
        return Optional.ofNullable(best != null ? best : plain);
    }

    // F3 and /execute blend each lookup with the neighbouring 4-block biome cells, so a spot only
    // reads as the target once its neighbours are the target too. The centre is already checked.
    private static boolean matchesAround(RegistryKey<Biome> target, int x, int z, BiFunction<Integer, Integer, RegistryKey<Biome>> realBiome) {
        for (int dx = -4; dx <= 4; dx += 4) {
            for (int dz = -4; dz <= 4; dz += 4) {
                if ((dx != 0 || dz != 0) && !target.equals(realBiome.apply(x + dx, z + dz))) return false;
            }
        }
        return true;
    }

    // blocks between checks inside a map pixel
    private static final int SAMPLE_STEP = 8;

    // a border can move a pixel either way, so the pixels around a region count too
    private static boolean nearCandidate(boolean[] candidate, int px, int pz) {
        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                int x = px + dx;
                int z = pz + dz;
                if (x < 0 || z < 0 || x >= width || z >= height) continue;
                if (candidate[pixels[z * width + x]]) return true;
            }
        }
        return false;
    }
}
