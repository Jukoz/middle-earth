package net.jukoz.me.world.chunkgen.map;

import com.google.common.base.Stopwatch;

import javax.imageio.ImageIO;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ImageUtils {
    private static byte[] SEED = generateSeed(50);
    private static int SEED_INDEX = 0;
    public static int BRUSH_SIZE = 16;
    public static float RATIO = 1.0f / (BRUSH_SIZE * BRUSH_SIZE);

    public static BufferedImage fetchResourceImage(ClassLoader classLoader ,String path) throws IOException {
        URL resource = classLoader.getResource(path);
        BufferedImage img = ImageIO.read(resource);
        return img;
    }

    public static BufferedImage fetchRunImage(String path) throws Exception {
        File f = new File(path);
        //System.out.println(f.getAbsolutePath());
        if(!f.exists()) return null;

        BufferedImage img = ImageIO.read(f);
        return img;
    }

    public static void saveImage(BufferedImage bufferedImage, String path, String fileName) throws Exception {
        new File(path).mkdirs();
        File f = new File(path + fileName);
        ImageIO.write(bufferedImage, "png", f);
    }
    public static BufferedImage blur(BufferedImage image) {
        // Create new expended image :
        int width = image.getWidth();
        int height = image.getHeight();
        int newWidth = width + (2 * BRUSH_SIZE);
        int newHeight = height + (2 * BRUSH_SIZE);

        BufferedImage expendedImage = new BufferedImage(newWidth, newHeight, image.getType());
        // Copy image content
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                expendedImage.setRGB(x + BRUSH_SIZE, y + BRUSH_SIZE, image.getRGB(x, y));
            }
        }
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < BRUSH_SIZE; x++) {
                expendedImage.setRGB(x, y + BRUSH_SIZE, image.getRGB(0, y)); // Left edge
                expendedImage.setRGB(width + BRUSH_SIZE + x, y + BRUSH_SIZE, image.getRGB(width - 1, y)); // Right edge
            }
        }

        for (int x = 0; x < width + 2 * BRUSH_SIZE; x++) {
            for (int y = 0; y < BRUSH_SIZE; y++) {
                expendedImage.setRGB(x, y, expendedImage.getRGB(x, BRUSH_SIZE)); // Top edge
                expendedImage.setRGB(x, height + BRUSH_SIZE + y, expendedImage.getRGB(x, height + BRUSH_SIZE - 1)); // Bottom edge
            }
        }

        float[] blurKernel = new float[BRUSH_SIZE * BRUSH_SIZE];
        Arrays.fill(blurKernel, RATIO);
        Kernel kernel = new Kernel(BRUSH_SIZE, BRUSH_SIZE, blurKernel);
        ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);

        expendedImage = op.filter(expendedImage, null);


        return expendedImage.getSubimage(BRUSH_SIZE, BRUSH_SIZE, width, height);
    }


    public static BufferedImage[][] subdivide(BufferedImage parent) {
        BufferedImage[][] subidivedImages = new BufferedImage[2][2];
        int width = parent.getWidth();
        int height = parent.getHeight();

        for(int x = 0; x < 2; x++){
            for(int y = 0; y < 2; y++){
                subidivedImages[x][y] = createChildFromParentImage(
                        new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB), parent, width / 2, x, y
                );
            }
        }

        return subidivedImages;
    }


    private static BufferedImage createChildFromParentImage(BufferedImage child, BufferedImage parent, int halfRegionSize, int xIndex, int yIndex) {
        for(int x = halfRegionSize * xIndex; x < halfRegionSize * (xIndex+1); x++) {
            for(int y = halfRegionSize * yIndex; y < halfRegionSize * (yIndex+1); y++) {
                /* Debug
                    final int color = parent.getRGB(x, y);
                    final Short id = MEBiomesData.getBiomeIdByBiome(MEBiomesData.getBiomeByColor(color));
                    if(id == null){
                        String errMessage = "ImageUtils::Cannot subdivide map image, no biome found for color %s at (%s, %s)".formatted(color, x, y);
                        System.out.println(errMessage);
                        throw new RuntimeException(errMessage);
                    }
                 */
                child.setRGB((x - (halfRegionSize * xIndex)) * 2, (y - (halfRegionSize * yIndex)) * 2, parent.getRGB(x, y));
            }
        }
        return fillImage(child);
    }

    private static BufferedImage fillImage(BufferedImage image) {
        final Stopwatch stopwatch = Stopwatch.createUnstarted();
        final int width = image.getWidth();
        final int height = image.getHeight();

        // Create the average values with neighbors
        List<Integer> biomeColors = new ArrayList<>();

        for(int x = 0; x < width; x ++){
            for(int y = 0; y < height; y ++){
                boolean xIsUneven = x % 2 == 1;
                boolean yIsUneven = y % 2 == 1;

                if(xIsUneven ^ yIsUneven){
                    if(xIsUneven){
                        if(x < width - 1)
                            biomeColors.add(image.getRGB(x + 1,y));
                        biomeColors.add(image.getRGB(x - 1,y));
                    }
                    if(yIsUneven){
                        if(y < height - 1)
                            biomeColors.add(image.getRGB(x,y + 1));
                        biomeColors.add(image.getRGB(x,y - 1));
                    }

                    image.setRGB(x,y, getRandomInteger(biomeColors));

                    biomeColors.clear();

                    if(yIsUneven && x > 1){
                        biomeColors.add(image.getRGB(x,y));
                        biomeColors.add(image.getRGB(x - 2,y));
                        if(y < height - 1)
                            biomeColors.add(image.getRGB(x - 1,y + 1));
                        biomeColors.add(image.getRGB(x - 1,y - 1));

                        image.setRGB(x - 1,y, getRandomInteger(biomeColors));
                        biomeColors.clear();
                    }
                } else if(x == width - 1){ // TODO : Find another solution instead of only taking the one from the left
                    image.setRGB(x,y, image.getRGB(x - 1, y));
                }
            }
        }
        stopwatch.reset();
        return image;
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
