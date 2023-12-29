package net.jukoz.me.world.chunkgen.map;

import com.google.common.base.Stopwatch;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.world.biomes.MEBiome;
import net.jukoz.me.world.biomes.MEBiomesData;
import net.jukoz.me.world.datas.WorldMapDatas;

import javax.imageio.ImageIO;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class ImageUtils {
    private static byte[] SEED = generateSeed(50);
    private static int SEED_INDEX = 0;
    public static short INITIAL_REGION_SIZE_X = 1;
    public static short INITIAL_REGION_SIZE_Y = 1;
    public final static int REGION_SIZE = WorldMapDatas.REGION_SIZE;

    public static int BRUSH_SIZE = 15;
    public static float RATIO = 1.0f / (BRUSH_SIZE * BRUSH_SIZE);

    public static BufferedImage blur(BufferedImage image) {
        float[] blurKernel = new float[BRUSH_SIZE * BRUSH_SIZE];
        Arrays.fill(blurKernel, RATIO);
        Kernel kernel = new Kernel(BRUSH_SIZE, BRUSH_SIZE, blurKernel);
        ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);

        return op.filter(image, null);
    }

    public static void createBiomeMap(BufferedImage intialImage) throws IOException {
        Stopwatch stopwatch = Stopwatch.createUnstarted();
        final int width = intialImage.getWidth();
        final int height = intialImage.getHeight();
        final int halfRegionSize = REGION_SIZE/2;

        if(width % REGION_SIZE != 0 || height % REGION_SIZE != 0){
            String message = "ImageUtils::Cannot subdivide map image, size not dividable by %s, current size is [%s, %s]".formatted(REGION_SIZE, width, height);
            System.out.println(message);
            throw new IOException(message);
        }
        // Creating initial map
        INITIAL_REGION_SIZE_X = (short)(width / REGION_SIZE);
        INITIAL_REGION_SIZE_Y = (short)(height / REGION_SIZE);

        WorldMapDatas.initialize(INITIAL_REGION_SIZE_X, INITIAL_REGION_SIZE_Y);
        BufferedImage newBiomeRegion = new BufferedImage(WorldMapDatas.REGION_SIZE, WorldMapDatas.REGION_SIZE, BufferedImage.TYPE_INT_ARGB);

        WorldMapDatas.saveBiomeRegion(0, 0, 0, intialImage);
        if(MiddleEarth.MAP_ITERATION == 0) return;
        WorldMapDatas.next();
        for(int i = 0; i < WorldMapDatas.AMOUNT_REGION_X; i ++){
            for(int j = 0; j <  WorldMapDatas.AMOUNT_REGION_Y; j ++){
                createChildFromParentRegion(newBiomeRegion, halfRegionSize, intialImage, i, j);
                WorldMapDatas.saveBiomeRegion(i, j, WorldMapDatas.ITERATION, newBiomeRegion);
            }
        }

        // Example of reading a file 'v'
        String stepImage = "ImageUtils::Starting development";
        System.out.println(stepImage);

        // Map iteration 0 is saved for initial map.        while(WorldMapDatas.ITERATION < MiddleEarth.MAP_ITERATION){
        WorldMapDatas.next();
        stopwatch.reset();
        stopwatch.start();
        for(int i = 0; i < WorldMapDatas.AMOUNT_REGION_X/2; i ++){
            for(int j = 0; j < WorldMapDatas.AMOUNT_REGION_Y/2; j ++) {
                String pathOfRegion = WorldMapDatas.BIOME_PATH.formatted(WorldMapDatas.ITERATION - 1) + WorldMapDatas.IMAGE_NAME.formatted(i, j);
                File regionImage = new File(pathOfRegion);
                if(regionImage.exists()){
                    BufferedImage buffImage = ImageIO.read(regionImage);
                    // Iterate to create the subdivision of the current region
                    for(int rI = 0; rI < 2; rI++){
                        for(int rJ = 0; rJ < 2; rJ++){
                            newBiomeRegion = createChildFromParentRegion(newBiomeRegion, halfRegionSize, buffImage, rI, rJ);
                            WorldMapDatas.saveBiomeRegion(i * 2 + rI, j * 2 + rJ, WorldMapDatas.ITERATION, newBiomeRegion);
                        }
                    }
                } else {
                    String message = "ImageUtils::Cannot subdivide map image, region have not been found : %s".formatted(pathOfRegion);
                    System.out.println(message);
                    throw new IOException(message);
                }
            }
        }
        String errMessage = "ImageUtils::Stage of saving regions has been completed for iteration #%s in %s milliseconds".formatted(WorldMapDatas.ITERATION, stopwatch.elapsed(TimeUnit.MILLISECONDS));
        System.out.println(errMessage);

        String msg = "ImageUtils::Creation of the map completed : %s".formatted(WorldMapDatas.toStr());

        System.out.println(msg); // Wtf
    }




    private static BufferedImage fillRegion(BufferedImage newRegion) {
        final Stopwatch stopwatch = Stopwatch.createUnstarted();
        final int width = newRegion.getWidth();
        final int height = newRegion.getHeight();

        // Create the average values with neighbors
        List<Integer> biomeColors = new ArrayList<>();

        for(int x = 0; x < width; x ++){
            for(int y = 0; y < height; y ++){
                boolean xIsUneven = x % 2 == 1;
                boolean yIsUneven = y % 2 == 1;

                if(xIsUneven ^ yIsUneven){
                    if(xIsUneven){
                        if(x < width - 1)
                            biomeColors.add(newRegion.getRGB(x + 1,y));
                        biomeColors.add(newRegion.getRGB(x - 1,y));
                    }
                    if(yIsUneven){
                        if(y < height - 1)
                            biomeColors.add(newRegion.getRGB(x,y + 1));
                        biomeColors.add(newRegion.getRGB(x,y - 1));
                    }

                    newRegion.setRGB(x,y, getRandomInteger(biomeColors));

                    biomeColors.clear();

                    if(yIsUneven && x > 1){
                        biomeColors.add(newRegion.getRGB(x,y));
                        biomeColors.add(newRegion.getRGB(x - 2,y));
                        if(y < height - 1)
                            biomeColors.add(newRegion.getRGB(x - 1,y + 1));
                        biomeColors.add(newRegion.getRGB(x - 1,y - 1));

                        newRegion.setRGB(x - 1,y, getRandomInteger(biomeColors));
                        biomeColors.clear();
                    }
                } else if(x == width - 1){ // TODO : Find another solution instead of only taking the one from the left
                    newRegion.setRGB(x,y, newRegion.getRGB(x - 1, y));
                }
            }
        }
        stopwatch.reset();
        return newRegion;
    }

    private static BufferedImage createChildFromParentRegion(BufferedImage child, int halfRegionSize, BufferedImage parent, int rI, int rJ) {
        for(int x = halfRegionSize * rI; x < halfRegionSize * (rI+1); x++) {
            for(int y = halfRegionSize * rJ; y < halfRegionSize * (rJ+1); y++) {
                final int color = parent.getRGB(x, y);
                final Short id = MEBiomesData.getBiomeIdByBiome(MEBiomesData.getBiomeByColor(color));
                if(id == null){
                    String errMessage = "ImageUtils::Cannot subdivide map image, no biome found for color %s at (%s, %s)".formatted(color, x, y);
                    System.out.println(errMessage);
                    throw new RuntimeException(errMessage);
                }
                child.setRGB((x - halfRegionSize * rI) * 2, (y - halfRegionSize * rJ) * 2, MEBiomesData.getColorByBiomeId(id));
            }
        }
        return fillRegion(child);
    }

    private static Integer getRandomInteger(List<Integer> list) {
        byte index = -1;
        for (int i = 0; i < list.size(); i++) {
            if(getNextSeed() >= 5){
                index += 1;
            }
        }
        if(index == -1){
            index = 0;
        }
        return list.get(index);
    }

    public static byte[] generateSeed(int bound){
        String piString = "31415926535897932384626433832795028841971693993751058209749445923078164062862089986280348253421170679";

        byte[] piBytes = new byte[piString.length()];
        for (int i = 0; i < piString.length(); i++) {
            piBytes[i] = Byte.parseByte(String.valueOf(piString.charAt(i)));
        }

        SEED = piBytes;
        SEED_INDEX = bound % piBytes.length;

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
