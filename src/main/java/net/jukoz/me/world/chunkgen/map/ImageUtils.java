package net.jukoz.me.world.chunkgen.map;

import com.google.common.base.Stopwatch;
import net.jukoz.me.world.biomes.MEBiome;
import net.jukoz.me.world.biomes.MEBiomeKeys;
import net.jukoz.me.world.biomes.MEBiomesData;
import net.minecraft.world.biome.BiomeKeys;
import org.apache.commons.lang3.time.StopWatch;

import java.awt.image.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class ImageUtils {
    public static int BRUSH_SIZE = 24;
    public static float RATIO = 1.0f / (BRUSH_SIZE * BRUSH_SIZE);
    private static byte[] SEED = generateSeed(0);
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
            List<Short> biomeIds = new ArrayList<>();

            for(int x = 0; x < newWidth; x ++){
                for(int y = 0; y < newHeight; y ++){
                    boolean xIsUneven = x % 2 == 1;
                    boolean yIsUneven = y % 2 == 1;

                    if(xIsUneven ^ yIsUneven){
                        if(xIsUneven){
                            biomeIds.add(tempResult[x + 1][y]);
                            biomeIds.add(tempResult[x - 1][y]);
                        }
                        if(yIsUneven){
                            biomeIds.add(tempResult[x][y + 1]);
                            biomeIds.add(tempResult[x][y - 1]);
                        }
                        tempResult[x][y] = getRandomShortFromList(biomeIds);
                        biomeIds.clear();

                        if(yIsUneven && x > 1){
                            // Create the middle coordinate
                            short value = 0;
                            for(int j = 0; j < 4 * 2; j++){
                                value += getNextSeed();
                            }
                            biomeIds.add(tempResult[x][y]);
                            biomeIds.add(tempResult[x - 2][y]);
                            biomeIds.add(tempResult[x - 1][y + 1]);
                            biomeIds.add(tempResult[x - 1][y - 1]);

                            valueFilled++;
                            tempResult[x - 1][y] = getRandomShortFromList(biomeIds);
                            biomeIds.clear();
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

    private static short getRandomShortFromList(List<Short> biomeIds) {
        byte index = 0;
        if(Collections.frequency(biomeIds, 54) >= 2) return 54; // River
        for(byte i = 0; i < biomeIds.size() - 1; i++){
            if(getNextSeed() >= 5){
                index += 1;
            }
        }
        return biomeIds.get(index);
    }

    public static byte[] generateSeed(long seed, int initial){
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(seed);

        SEED = buffer.array();
        System.out.println(Arrays.toString(buffer.array()));
        SEED_INDEX = initial;

        return SEED;
    }

    public static byte[] generateSeed(int bound){

        String piString = "31415926535897932384626433832795028841971693993751058209749445923078164062862089986280348253421170679";

        byte[] piBytes = new byte[piString.length()];
        for (int i = 0; i < piString.length(); i++) {
            piBytes[i] = Byte.parseByte(String.valueOf(piString.charAt(i)));
        }

        SEED = piBytes;
        SEED_INDEX = bound;

        return SEED;
    }

    public static byte getNextSeed(){
        SEED_INDEX ++;
        if(SEED_INDEX >= SEED.length){
            SEED_INDEX = 0;
        }
        return SEED[SEED_INDEX];
    }
}
