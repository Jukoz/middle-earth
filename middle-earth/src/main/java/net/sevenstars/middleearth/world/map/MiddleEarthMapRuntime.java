package net.sevenstars.middleearth.world.map;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import net.sevenstars.middleearth.utils.resources.FileUtils;
import net.sevenstars.middleearth.world.biomes.surface.MapBasedBiomePool;
import net.sevenstars.middleearth.world.biomes.surface.MapBasedCustomBiome;
import org.joml.Vector2i;

import java.awt.image.BufferedImage;

public class MiddleEarthMapRuntime {
    private static MiddleEarthMapRuntime singleInstance;
    private static final int MAX_CACHED_REGIONS = 4;
    static final int FALLBACK_HEIGHT_RGB = 0xFF233037;

    private final LoadingCache<Long, MiddleEarthMapRegion> regions;
    private final MiddleEarthMapUtils middleEarthMapUtils;
    private final int edgeWidth;
    private final int edgeHeight;
    private final short[] edgeColorSums;

    public static synchronized MiddleEarthMapRuntime getInstance() {
        if (singleInstance == null) {
            singleInstance = new MiddleEarthMapRuntime();
        }
        return singleInstance;
    }

    public MiddleEarthMapRuntime() {
        regions = CacheBuilder.newBuilder()
                .maximumSize(MAX_CACHED_REGIONS)
                .build(new CacheLoader<>() {
                    @Override
                    public MiddleEarthMapRegion load(Long key) {
                        return loadRegion(key);
                    }
                });
        BufferedImage edgeImage = MiddleEarthMapGeneration.getEdgeHeightImage();
        if (edgeImage == null) {
            String path = MiddleEarthMapConfigs.BASE_HEIGHT_PATH + MiddleEarthMapConfigs.BASE_EDGE_IMAGE_NAME;
            edgeImage = FileUtils.getInstance().getRunImage(path);
            if (edgeImage == null) {
                net.sevenstars.middleearth.MiddleEarth.LOGGER.logWarn(
                        "Map edge image is unavailable at " + path + "; using neutral edge fallback."
                );
            }
        }
        EdgePixels encodedEdge = encodeEdge(edgeImage);
        edgeWidth = encodedEdge.width();
        edgeHeight = encodedEdge.height();
        edgeColorSums = encodedEdge.colorSums();
        middleEarthMapUtils = MiddleEarthMapUtils.getInstance();
    }

    public MapBasedCustomBiome getBiome(int posX, int posZ) {
        if (!middleEarthMapUtils.isWorldCoordinateInBorder(posX, posZ)) {
            return MapBasedBiomePool.defaultBiome;
        }

        int pixelX = Math.floorDiv(posX, MiddleEarthMapConfigs.PIXEL_WEIGHT);
        int pixelZ = Math.floorDiv(posZ, MiddleEarthMapConfigs.PIXEL_WEIGHT);
        MiddleEarthMapRegion region = getRegionToUse(
                Math.floorDiv(pixelX, MiddleEarthMapConfigs.REGION_SIZE),
                Math.floorDiv(pixelZ, MiddleEarthMapConfigs.REGION_SIZE)
        );
        return region.getBiome(
                Math.floorMod(pixelX, MiddleEarthMapConfigs.REGION_SIZE),
                Math.floorMod(pixelZ, MiddleEarthMapConfigs.REGION_SIZE)
        );
    }

    public int getHeightRgb(int posX, int posZ) {
        if (!middleEarthMapUtils.isWorldCoordinateInBorder(posX, posZ)) {
            return FALLBACK_HEIGHT_RGB;
        }

        int pixelX = Math.floorDiv(posX, MiddleEarthMapConfigs.PIXEL_WEIGHT);
        int pixelZ = Math.floorDiv(posZ, MiddleEarthMapConfigs.PIXEL_WEIGHT);
        MiddleEarthMapRegion region = getRegionToUse(
                Math.floorDiv(pixelX, MiddleEarthMapConfigs.REGION_SIZE),
                Math.floorDiv(pixelZ, MiddleEarthMapConfigs.REGION_SIZE)
        );
        return region.getHeightRgb(
                Math.floorMod(pixelX, MiddleEarthMapConfigs.REGION_SIZE),
                Math.floorMod(pixelZ, MiddleEarthMapConfigs.REGION_SIZE)
        );
    }

    public float getEdge(int posX, int posZ) {
        if (!middleEarthMapUtils.isWorldCoordinateInBorder(posX, posZ)) {
            return -0.67f;
        }
        if (edgeColorSums == null) {
            return 1.001f;
        }

        int imageX = clamp((int) ((float) posX / middleEarthMapUtils.ratioX), 0, edgeWidth - 1);
        int imageZ = clamp((int) ((float) posZ / middleEarthMapUtils.ratioZ), 0, edgeHeight - 1);
        float average = Short.toUnsignedInt(edgeColorSums[imageZ * edgeWidth + imageX]) / 3.0f;
        if (average > 0.01f) {
            average *= 12;
        }
        return Math.max(0, 1 - (average / 255));
    }

    private MiddleEarthMapRegion getRegionToUse(int regionX, int regionZ) {
        long key = regionKey(regionX, regionZ);
        return regions.getUnchecked(key);
    }

    private static synchronized MiddleEarthMapRegion loadRegion(long packed) {
        return new MiddleEarthMapRegion(new Vector2i(
                (int) (packed >> 32),
                (int) packed
        ));
    }

    private static long regionKey(int regionX, int regionZ) {
        return ((long) regionX << 32) ^ (regionZ & 0xffffffffL);
    }

    private static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    private static EdgePixels encodeEdge(BufferedImage image) {
        if (image == null) {
            return new EdgePixels(0, 0, null);
        }

        int width = image.getWidth();
        int height = image.getHeight();
        short[] colorSums = new short[Math.multiplyExact(width, height)];
        int[] row = new int[width];
        for (int y = 0; y < height; y++) {
            image.getRGB(0, y, width, 1, row, 0, width);
            int rowOffset = y * width;
            for (int x = 0; x < width; x++) {
                int color = row[x];
                colorSums[rowOffset + x] = (short) (
                        ((color >>> 16) & 0xFF)
                                + ((color >>> 8) & 0xFF)
                                + (color & 0xFF)
                );
            }
        }
        image.flush();
        return new EdgePixels(width, height, colorSums);
    }

    private record EdgePixels(int width, int height, short[] colorSums) {
    }
}
