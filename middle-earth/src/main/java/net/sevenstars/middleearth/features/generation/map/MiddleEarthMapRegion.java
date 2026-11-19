package net.sevenstars.middleearth.features.generation.map;

import net.sevenstars.middleearth.utils.resources.FileUtils;
import net.sevenstars.middleearth.features.generation.biomes.surface.MapBasedBiomePool;
import net.sevenstars.middleearth.features.generation.biomes.surface.MapBasedCustomBiome;
import org.joml.Vector2i;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.image.ComponentSampleModel;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.awt.image.WritableRaster;

public class MiddleEarthMapRegion {
    public final static int CALC_REGION_SIZE = MiddleEarthMapConfigs.REGION_SIZE * MiddleEarthMapConfigs.PIXEL_WEIGHT;
    public final Vector2i coordinate;
    private final BufferedImage biomeImage;
    private final BufferedImage heightImage;
    private final Pixels biomePixels;
    private final Pixels heightPixels;

    public MiddleEarthMapRegion(Vector2i coordinate){
        this.coordinate = coordinate;
        String biomePath = MiddleEarthMapConfigs.BIOME_PATH.formatted(MiddleEarthMapConfigs.MAP_ITERATION) + MiddleEarthMapConfigs.IMAGE_NAME.formatted(coordinate.x,coordinate.y);
        String heightPath = MiddleEarthMapConfigs.HEIGHT_PATH + MiddleEarthMapConfigs.IMAGE_NAME.formatted(coordinate.x, coordinate.y);
        biomeImage = FileUtils.getInstance().getRunImage(biomePath);
        heightImage = FileUtils.getInstance().getRunImage(heightPath);
        biomePixels = biomeImage == null ? null : new Pixels(biomeImage);
        heightPixels = heightImage == null ? null : new Pixels(heightImage);

        //MiddleEarth.LOGGER.sendChat(biomePath);
        //MiddleEarth.LOGGER.sendChat(heightPath);
    }

    public MapBasedCustomBiome getBiome(int imageX, int imageY){
        try {
            if(biomeImage != null){
                return MapBasedBiomePool.getBiomeByColor(biomePixels.rgb(imageX, imageY));
            }
        } catch (Exception exception){
            return MapBasedBiomePool.defaultBiome;
        }
        return MapBasedBiomePool.defaultBiome;
    }

    public int getHeightRgb(int imageX, int imageY) {
        if(heightImage != null){
            return heightPixels.rgb(imageX, imageY);
        }
        return new Color(Math.abs(MapBasedBiomePool.defaultBiome.getHeight()), 1, 0).getRGB();
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

    // BufferedImage.getRGB allocates for every pixel; the common PNG layouts are read straight from their arrays, with
    // exactly the value getRGB gives (opaque for the RGB layouts), and anything else still goes through getRGB
    private static final class Pixels {
        private static final int GENERIC = 0;
        private static final int BGR = 1;
        private static final int ABGR = 2;
        private static final int INT_RGB = 3;
        private static final int INT_ARGB = 4;

        private final BufferedImage image;
        private final int width;
        private final int height;
        private final int kind;
        private final byte[] bytes;
        private final int[] ints;

        Pixels(BufferedImage image) {
            this.image = image;
            this.width = image.getWidth();
            this.height = image.getHeight();
            int kind = GENERIC;
            byte[] bytes = null;
            int[] ints = null;
            WritableRaster raster = image.getRaster();
            int type = image.getType();
            if (raster.getSampleModelTranslateX() == 0 && raster.getSampleModelTranslateY() == 0 && raster.getMinX() == 0 && raster.getMinY() == 0) {
                if ((type == BufferedImage.TYPE_3BYTE_BGR || type == BufferedImage.TYPE_4BYTE_ABGR)
                        && raster.getDataBuffer() instanceof DataBufferByte buffer && raster.getSampleModel() instanceof ComponentSampleModel model) {
                    int stride = type == BufferedImage.TYPE_3BYTE_BGR ? 3 : 4;
                    if (model.getPixelStride() == stride && model.getScanlineStride() == stride * width && buffer.getNumBanks() == 1
                            && buffer.getOffset() == 0 && buffer.getSize() >= (long) stride * width * height) {
                        kind = stride == 3 ? BGR : ABGR;
                        bytes = buffer.getData();
                    }
                } else if ((type == BufferedImage.TYPE_INT_RGB || type == BufferedImage.TYPE_INT_ARGB)
                        && raster.getDataBuffer() instanceof DataBufferInt buffer && buffer.getNumBanks() == 1
                        && buffer.getOffset() == 0 && buffer.getSize() >= width * height
                        && raster.getSampleModel() instanceof java.awt.image.SinglePixelPackedSampleModel model && model.getScanlineStride() == width) {
                    kind = type == BufferedImage.TYPE_INT_RGB ? INT_RGB : INT_ARGB;
                    ints = buffer.getData();
                }
            }
            this.kind = kind;
            this.bytes = bytes;
            this.ints = ints;
        }

        int rgb(int x, int y) {
            if (kind == GENERIC || x < 0 || y < 0 || x >= width || y >= height) return image.getRGB(x, y);
            int index = y * width + x;
            switch (kind) {
                case BGR: {
                    int i = index * 3;
                    return 0xFF000000 | (bytes[i + 2] & 0xFF) << 16 | (bytes[i + 1] & 0xFF) << 8 | (bytes[i] & 0xFF);
                }
                case ABGR: {
                    int i = index * 4;
                    return (bytes[i] & 0xFF) << 24 | (bytes[i + 3] & 0xFF) << 16 | (bytes[i + 2] & 0xFF) << 8 | (bytes[i + 1] & 0xFF);
                }
                case INT_RGB:
                    return (ints[index] & 0xFFFFFF) | 0xFF000000;
                default:
                    return ints[index];
            }
        }
    }
}
