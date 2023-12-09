package net.jukoz.me.world.chunkgen.map;

import com.google.common.base.Stopwatch;
import net.jukoz.me.world.biomes.MEBiome;
import net.jukoz.me.world.biomes.MEBiomesData;
import org.apache.commons.lang3.time.StopWatch;

import java.awt.image.*;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class ImageUtils {
    public static int BRUSH_SIZE = 24;
    public static float RATIO = 1.0f / (BRUSH_SIZE * BRUSH_SIZE);
    private static byte[] SEED = generateSeed(99999999); //generateSeed(14232899, 0); //generateSeed(99999999);
    private static int SEED_INDEX = 0;

    public static BufferedImage blur(BufferedImage image) {
        float[] blurKernel = new float[BRUSH_SIZE * BRUSH_SIZE];
        Arrays.fill(blurKernel, RATIO);
        Kernel kernel = new Kernel(BRUSH_SIZE, BRUSH_SIZE, blurKernel);
        ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);

        return op.filter(image, null);
    }

    // Other Version, Slightly slower
    public static short[][] convertImageToBiomeIdArray(BufferedImage image) {
        final int width = image.getWidth();
        final int height = image.getHeight();
        short[][] result = new short[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                final int color = image.getRGB(x, y);
                final MEBiome biome = MEBiomesData.getBiomeByColor(color);

                if (biome != null) {
                    final Short id = MEBiomesData.getBiomeIdByBiome(biome);

                    if (id != null) {
                        result[x][y] = id;
                    } else {
                        System.out.println("No biome has this id %d at %d,%d".formatted(id, x, y));
                    }
                } else {
                    System.out.println("MEBiomesData does not contain the color (%d) at %d,%d".formatted(color, x, y));
                }
            }
        }
        return result;
    }

    // Weird code :')
    /*
        public static short[][] convertImageToBiomeIdArray(BufferedImage image) {
        final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        final int width = image.getWidth();
        final int height = image.getHeight();
        final boolean hasAlphaChannel = image.getAlphaRaster() != null;

        short[][] result = new short[height][width];
        final int pixelLength = (hasAlphaChannel) ? 4 : 3;

        for (int pixel = 0, row = 0, col = 0; pixel + pixelLength - 1 < pixels.length; pixel += pixelLength) {
            int argb = 0;
            if (hasAlphaChannel) {
                argb += ((int) pixels[pixel] & 0xff) << 24; // alpha
            } else {
                argb += -16777216; // 255 alpha
            }
            argb += ((int) pixels[pixel + 1] & 0xff); // blue
            argb += (((int) pixels[pixel + 2] & 0xff) << 8); // green
            argb += (((int) pixels[pixel + 3] & 0xff) << 16); // red

            MEBiome biome = MEBiomesData.getBiomeByColor(argb);
            if (biome != null) {
                result[row][col] = MEBiomesData.getBiomeIdByBiome(biome);
            } else {
                System.out.println("Biome datas do not contain the color (%s) which is at %x,%y".formatted(argb, row, col));
            }

            col++;
            if (col == width) {
                col = 0;
                row++;
            }
        }

        return result;
    }
     */
    public static short[][] subdivide(short[][] initial, int amount) {
        Stopwatch stopwatch = Stopwatch.createUnstarted();
        short[][] newResult = initial;
        int valueFilled = 0;
        for(int iteration = 0; iteration < amount; iteration++){
            stopwatch.start();
            final int width = newResult.length;
            final int newWidth = (width * 2) - 1;
            final int height = newResult[0].length;
            final int newHeight = (height * 2) - 1;
            System.out.println("I_%s ::: w_%s -> nw_%s ::: h_%s -> nh_%s".formatted(iteration, width, newWidth, height, newHeight));

            short[][] tempResult = new short[newWidth][newHeight];

            // Copy old values into the new array
            for(int x = 0; x < width; x ++){
                for(int y = 0; y < height; y ++){
                    tempResult[x * 2][y * 2] = newResult[x][y];
                }
            }

            // Create the average values with neighbors
            for(int x = 0; x < newWidth; x ++){
                for(int y = 0; y < newHeight; y ++){
                    boolean xIsUneven = x % 2 == 1;
                    boolean yIsUneven = y % 2 == 1;

                    if(xIsUneven ^ yIsUneven){
                        if(xIsUneven){
                            tempResult[x][y] = (getNextSeed() == 1)
                                    ? tempResult[x + 1][y]
                                    : tempResult[x - 1][y];
                        }
                        if(yIsUneven){
                            tempResult[x][y] = (getNextSeed() == 1)
                                    ? tempResult[x][y + 1]
                                    : tempResult[x][y - 1];

                            // Create the middle coordinate
                            if(x > 1){
                                short value = 0;
                                for(int j = 0; j < 4 * 2; j++){
                                    value += getNextSeed();
                                }

                                if(value < 2){
                                    value = tempResult[x][y];
                                } else if (value < 4){
                                    value = tempResult[x - 1][y + 1];
                                } else if (value < 6){
                                    value = tempResult[x - 2][y];
                                } else {
                                    value = tempResult[x - 1][y - 1];
                                }
                                tempResult[x - 1][y] = value;
                                valueFilled++;
                            }
                        }
                        valueFilled++;
                    }
                }
            }
            newResult = tempResult;
            System.out.println("I_%s took %s".formatted(iteration, stopwatch.elapsed(TimeUnit.MILLISECONDS)));
            stopwatch.reset();

        }
        System.out.println("Filled %s values".formatted(valueFilled));
        return newResult;
    }

    public static byte[] generateSeed(long seed, int initial){
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(seed);

        SEED = buffer.array();
        SEED_INDEX = initial;

        return SEED;
    }

    public static byte[] generateSeed(int bound){
        Random random = new Random();
        long seed = random.nextLong(bound);
        System.out.println("Generated seed is %s with %s as bound".formatted(seed, bound));

        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(seed);

        SEED = buffer.array();
        SEED_INDEX = random.nextInt(SEED.length);

        return SEED;
    }

    public static byte getNextSeed(){
        SEED_INDEX ++;
        if(SEED_INDEX >= SEED.length){
            SEED_INDEX = 0;
        }
        return SEED[SEED_INDEX];
    }


    // Old Version, remove it when everything works with subdivision
    public static int[][] convertTo2D(BufferedImage image) {
        Stopwatch stopwatch = Stopwatch.createStarted();

        final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        final int width = image.getWidth();
        final int height = image.getHeight();
        final boolean hasAlphaChannel = image.getAlphaRaster() != null;

        int[][] result = new int[height][width];
        final int pixelLength = (hasAlphaChannel) ? 4 : 3;

        if (hasAlphaChannel) {
            for (int pixel = 0, row = 0, col = 0; pixel + 3 < pixels.length; pixel += pixelLength) {
                int argb = 0;
                argb += (((int) pixels[pixel] & 0xff) << 24); // alpha
                argb += ((int) pixels[pixel + 1] & 0xff); // blue
                argb += (((int) pixels[pixel + 2] & 0xff) << 8); // green
                argb += (((int) pixels[pixel + 3] & 0xff) << 16); // red
                result[row][col] = argb;
                col++;
                if (col == width) {
                    col = 0;
                    row++;
                }
            }
        } else {
            for (int pixel = 0, row = 0, col = 0; pixel + 2 < pixels.length; pixel += pixelLength) {
                int argb = 0;
                argb += -16777216; // 255 alpha
                argb += ((int) pixels[pixel] & 0xff); // blue
                argb += (((int) pixels[pixel + 1] & 0xff) << 8); // green
                argb += (((int) pixels[pixel + 2] & 0xff) << 16); // red
                result[row][col] = argb;
                col++;
                if (col == width) {
                    col = 0;
                    row++;
                }
            }
        }
        System.out.println("It took %s milliseconds to convert the image to a 2D array (old)".formatted(stopwatch.elapsed(TimeUnit.MILLISECONDS)));
        return result;
    }
}
