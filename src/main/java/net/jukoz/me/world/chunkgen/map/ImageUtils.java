package net.jukoz.me.world.chunkgen.map;

import com.google.common.base.Stopwatch;
import net.jukoz.me.utils.LoggerUtil;
import net.jukoz.me.world.biomes.surface.MEBiome;
import net.jukoz.me.world.biomes.surface.MEBiomesData;
import net.jukoz.me.world.map.MiddleEarthMapGeneration;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.List;
import java.util.logging.Logger;

public class ImageUtils {
    public static int BRUSH_SIZE = 16;
    public static float RATIO = 1.0f / (BRUSH_SIZE * BRUSH_SIZE);

    public static Random random = new Random();

    public static BufferedImage fetchResourceImage(ClassLoader classLoader, String path) throws IOException {
        URL resource = classLoader.getResource(path);
        BufferedImage img = ImageIO.read(resource);
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
        child = createVoids(child, parent, halfRegionSize, xIndex, yIndex);
        child = fillVoidCenters(child);
        child = fillVoidEdges(child);
        return child;
    }

    private static BufferedImage createVoids(BufferedImage result, BufferedImage source, int size, int xIndex, int yIndex){
        for(int x = size * xIndex; x < size * (xIndex+1); x++) {
            for(int y = size * yIndex; y < size * (yIndex+1); y++) {
                result.setRGB((x - (size * xIndex)) * 2, (y - (size * yIndex)) * 2, source.getRGB(x, y));
            }
        }
        return result;
    }

    private static BufferedImage fillVoidCenters(BufferedImage result) {

        ArrayList<Integer> colorOccurences = new ArrayList<>();

        for(int x = 1; x < result.getWidth(); x += 2){
            for(int y = 1; y < result.getHeight(); y += 2){
                colorOccurences.add(result.getRGB(x - 1, y - 1));

                if(x  + 1 < result.getWidth()) {
                    colorOccurences.add(result.getRGB(x + 1, y - 1));
                }
                if(y  + 1 < result.getHeight()) {
                    colorOccurences.add(result.getRGB(x - 1, y + 1));
                }
                if(y  + 1 < result.getHeight() && x  + 1 < result.getWidth()) {
                    colorOccurences.add(result.getRGB(x + 1, y + 1));
                }


                Integer color = getMostOccuringColorFromBiomeList(colorOccurences);
                result.setRGB(x, y, (color != null ) ? color : colorOccurences.get(0));
                colorOccurences.clear();
            }
        }
        return result;

    }

    private static BufferedImage fillVoidEdges(BufferedImage result) {
        ArrayList<Integer> colorOccurences = new ArrayList<>();

        for(int x = 0; x < result.getWidth(); x ++){
            for(int y = 0; y < result.getHeight(); y ++) {
                if(x % 2 == 1 && y % 2 == 1) continue;
                if(x % 2 == 0 && y % 2 == 0) continue;

                if(x % 2 == 1)
                    colorOccurences.add(result.getRGB(x - 1, y));
                if(y % 2 == 1)
                    colorOccurences.add(result.getRGB(x, y - 1));
                if(x + 1 < result.getWidth())
                    colorOccurences.add(result.getRGB(x + 1, y));
                if(y + 1 < result.getHeight())
                    colorOccurences.add(result.getRGB(x, y + 1));

                Integer color = getMostOccuringColorFromBiomeList(colorOccurences);
                result.setRGB(x, y, (color != null ) ? color : colorOccurences.get(0));
                colorOccurences.clear();
            }
        }
        return result;
    }

    private static Integer getMostOccuringColorFromBiomeList(ArrayList<Integer> list){
        if(list.isEmpty()){
            LoggerUtil.getInstance().logError("ImageUtils::getMostCommonColor - List was empty!");
            return null;
        }

        int mostCommonValueIndex = 0;
        int mostCommonValueOccurenceAmount = 0;

        ArrayList<Integer> sameMax = new ArrayList<>();

        for(int i = 0; i < list.size(); i++){
            int count = 0;
            int weight = MEBiomesData.getBiomeByColor(list.get(i)).expansionWeight[(MiddleEarthMapGeneration.CURRENT_ITERATION <= 1) ? 0 : 1];

            for(int j = 0; j < list.size(); j ++){
                if(list.get(i).intValue() == list.get(j).intValue()){
                    count += weight;
                }
            }

            if(count > mostCommonValueOccurenceAmount){
                mostCommonValueOccurenceAmount = count;
                mostCommonValueIndex = i;
            } else if(count == mostCommonValueOccurenceAmount && count == 2){
                sameMax.add(i);
            }
        }

        if(!sameMax.isEmpty())
            mostCommonValueIndex = sameMax.get(random.nextInt(sameMax.size()));

        return list.get(mostCommonValueIndex);
    }


    // Old algorithm
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
        return list.get(random.nextInt(list.size()));
    }
}
