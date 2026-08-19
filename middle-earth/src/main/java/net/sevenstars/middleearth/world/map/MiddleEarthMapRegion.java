package net.sevenstars.middleearth.world.map;

import it.unimi.dsi.fastutil.ints.Int2ShortOpenHashMap;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.utils.resources.FileUtils;
import net.sevenstars.middleearth.world.biomes.surface.MapBasedBiomePool;
import net.sevenstars.middleearth.world.biomes.surface.MapBasedCustomBiome;
import org.joml.Vector2i;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class MiddleEarthMapRegion {
    public final static int CALC_REGION_SIZE = MiddleEarthMapConfigs.REGION_SIZE * MiddleEarthMapConfigs.PIXEL_WEIGHT;
    private static final Set<String> REPORTED_MISSING_IMAGES = ConcurrentHashMap.newKeySet();
    public final Vector2i coordinate;
    private final int biomeWidth;
    private final int biomeHeight;
    private final byte[] biomePixels;
    private final MapBasedCustomBiome[] biomePalette;
    private final int heightWidth;
    private final int heightHeight;
    private final byte[] heightPixels;

    public MiddleEarthMapRegion(Vector2i coordinate){
        this.coordinate = coordinate;
        String biomePath = MiddleEarthMapConfigs.BIOME_PATH.formatted(MiddleEarthMapConfigs.MAP_ITERATION) + MiddleEarthMapConfigs.IMAGE_NAME.formatted(coordinate.x,coordinate.y);
        String heightPath = MiddleEarthMapConfigs.HEIGHT_PATH + MiddleEarthMapConfigs.IMAGE_NAME.formatted(coordinate.x, coordinate.y);
        BufferedImage biomeImage = FileUtils.getInstance().getRunImage(biomePath);
        reportMissingImageOnce(biomePath, biomeImage);
        BiomePixels encodedBiomes = encodeBiomes(biomeImage);
        biomeWidth = encodedBiomes.width();
        biomeHeight = encodedBiomes.height();
        biomePixels = encodedBiomes.pixels();
        biomePalette = encodedBiomes.palette();

        BufferedImage heightImage = FileUtils.getInstance().getRunImage(heightPath);
        reportMissingImageOnce(heightPath, heightImage);
        HeightPixels encodedHeights = encodeHeights(heightImage);
        heightWidth = encodedHeights.width();
        heightHeight = encodedHeights.height();
        heightPixels = encodedHeights.pixels();
    }

    private static void reportMissingImageOnce(String path, BufferedImage image) {
        if (image == null && REPORTED_MISSING_IMAGES.add(path)) {
            MiddleEarth.LOGGER.logWarn(
                    "Map region image is unavailable at " + path + "; using safe fallback data."
            );
        }
    }

    public MapBasedCustomBiome getBiome(int imageX, int imageY){
        if (biomePixels != null) {
            int x = clamp(imageX, 0, biomeWidth - 1);
            int y = clamp(imageY, 0, biomeHeight - 1);
            int paletteIndex = Byte.toUnsignedInt(biomePixels[y * biomeWidth + x]);
            if (paletteIndex < biomePalette.length) {
                return biomePalette[paletteIndex];
            }
        }
        return MapBasedBiomePool.defaultBiome;
    }

    public int getHeightRgb(int imageX, int imageY) {
        if (heightPixels != null) {
            int x = clamp(imageX, 0, heightWidth - 1);
            int y = clamp(imageY, 0, heightHeight - 1);
            int offset = (y * heightWidth + x) * 3;
            return 0xFF000000
                    | (Byte.toUnsignedInt(heightPixels[offset]) << 16)
                    | (Byte.toUnsignedInt(heightPixels[offset + 1]) << 8)
                    | Byte.toUnsignedInt(heightPixels[offset + 2]);
        }
        return MiddleEarthMapRuntime.FALLBACK_HEIGHT_RGB;
    }

    public boolean isInRange(Vector2i playerCoord) {
        int middleCoordinateX = CALC_REGION_SIZE * (coordinate.x + 1) - CALC_REGION_SIZE / 2;
        int middleCoordinateZ = CALC_REGION_SIZE * (coordinate.y + 1) - CALC_REGION_SIZE / 2;
        double distance = calculateDistance(playerCoord.x, playerCoord.y, middleCoordinateX, middleCoordinateZ);
        //MiddleEarth.LOGGER.logDebugMsg("IsInRange : [%s,%s] = [%s]".formatted(coordinate.x, coordinate.y, distance));
        return distance < (CALC_REGION_SIZE / 2) + MiddleEarthMapConfigs.BIOME_VALIDATION_DIST_CHECK;
    }

    private double calculateDistance(double x1, double y1, double x2, double y2) {
            return Point2D.distance(x1, y1, x2, y2);
    }

    private static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    private static BiomePixels encodeBiomes(BufferedImage image) {
        if (image == null) {
            return new BiomePixels(0, 0, null, new MapBasedCustomBiome[0]);
        }

        int width = image.getWidth();
        int height = image.getHeight();
        byte[] pixels = new byte[Math.multiplyExact(width, height)];
        List<MapBasedCustomBiome> palette = new ArrayList<>();
        Int2ShortOpenHashMap paletteByColor = new Int2ShortOpenHashMap();
        Map<MapBasedCustomBiome, Short> paletteByBiome = new IdentityHashMap<>();
        paletteByColor.defaultReturnValue((short) -1);
        int[] row = new int[width];

        for (int y = 0; y < height; y++) {
            image.getRGB(0, y, width, 1, row, 0, width);
            int rowOffset = y * width;
            for (int x = 0; x < width; x++) {
                int color = 0xFF000000 | (row[x] & 0x00FFFFFF);
                short paletteIndex = paletteByColor.get(color);
                if (paletteIndex == -1) {
                    MapBasedCustomBiome biome = MapBasedBiomePool.getBiomeByColor(color);
                    Short existingIndex = paletteByBiome.get(biome);
                    if (existingIndex != null) {
                        paletteIndex = existingIndex;
                    } else {
                        if (palette.size() >= 256) {
                            throw new IllegalStateException("Biome map palette exceeds 256 distinct biomes");
                        }
                        paletteIndex = (short) palette.size();
                        paletteByBiome.put(biome, paletteIndex);
                        palette.add(biome);
                    }
                    paletteByColor.put(color, paletteIndex);
                }
                pixels[rowOffset + x] = (byte) paletteIndex;
            }
        }
        image.flush();
        return new BiomePixels(width, height, pixels, palette.toArray(MapBasedCustomBiome[]::new));
    }

    private static HeightPixels encodeHeights(BufferedImage image) {
        if (image == null) {
            return new HeightPixels(0, 0, null);
        }

        int width = image.getWidth();
        int height = image.getHeight();
        byte[] pixels = new byte[Math.multiplyExact(Math.multiplyExact(width, height), 3)];
        int[] row = new int[width];

        for (int y = 0; y < height; y++) {
            image.getRGB(0, y, width, 1, row, 0, width);
            int rowOffset = y * width * 3;
            for (int x = 0; x < width; x++) {
                int color = row[x];
                int offset = rowOffset + x * 3;
                pixels[offset] = (byte) (color >>> 16);
                pixels[offset + 1] = (byte) (color >>> 8);
                pixels[offset + 2] = (byte) color;
            }
        }
        image.flush();
        return new HeightPixels(width, height, pixels);
    }

    private record BiomePixels(
            int width,
            int height,
            byte[] pixels,
            MapBasedCustomBiome[] palette
    ) {
    }

    private record HeightPixels(int width, int height, byte[] pixels) {
    }
}
