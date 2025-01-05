package net.jukoz.me.world.chunkgen.map;

import com.google.common.base.Stopwatch;
import net.jukoz.me.utils.LoggerUtil;
import net.jukoz.me.world.biomes.surface.MapBasedBiomePool;
import net.jukoz.me.world.map.MiddleEarthMapGeneration;
import org.joml.sampling.Convolution;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.List;

public class ImageUtils {
    private static HashMap<Integer, float[]> gaussianBlurKernel = new HashMap<>();
    private static float[] edgeKernel =
                    {-1f, -2f, -1f,
                     -2f,  12f, -2f,
                     -1f, -2f, -1f};
    private static final float GAUSSIAN_SIGMA = 3.81f;

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

    public static BufferedImage[][] subdivide(BufferedImage parent) {
        BufferedImage[][] subidivedImages = new BufferedImage[2][2];
        int width = parent.getWidth();
        int height = parent.getHeight();

        for(int x = 0; x < 2; x++){
            for(int y = 0; y < 2; y++){
                subidivedImages[x][y] = createChildFromParentImage(parent, width, x, y);
            }
        }

        return subidivedImages;
    }


    private static BufferedImage createChildFromParentImage(BufferedImage parent, int regionSize, int xIndex, int yIndex) {
        BufferedImage child = new BufferedImage(regionSize, regionSize, BufferedImage.TYPE_INT_ARGB);
        child = createVoids(child, parent, regionSize/2, xIndex, yIndex);
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


                try {
                    Integer color = getMostOccuringColorFromBiomeList(colorOccurences);
                    result.setRGB(x, y, (color != null ) ? color : colorOccurences.get(0));
                    colorOccurences.clear();
                } catch (Exception exception) {
                    //LoggerUtil.logError("ImageUtils::Can't find color at [%s,%s]".formatted(x,y));
                }
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
                if(x + 1 < result.getWidth())
                    colorOccurences.add(result.getRGB(x + 1, y));
                if(y % 2 == 1)
                    colorOccurences.add(result.getRGB(x, y - 1));
                if(y + 1 < result.getHeight())
                    colorOccurences.add(result.getRGB(x, y + 1));

                try {
                    Integer color = getMostOccuringColorFromBiomeList(colorOccurences);
                    result.setRGB(x, y, (color != null ) ? color : colorOccurences.get(0));
                } catch (Exception exception) {
                    //LoggerUtil.logError("ImageUtils::Can't find color at [%s,%s]".formatted(x,y));
                }
                colorOccurences.clear();
            }
        }
        return result;
    }

    private static Integer getMostOccuringColorFromBiomeList(ArrayList<Integer> list) throws Exception {
        if(list.isEmpty()){
            LoggerUtil.logError("ImageUtils::getMostCommonColor - List was empty!");
            return null;
        }
        Map<Integer, Integer> counts = new HashMap<>();


        int max = 0;
        for(int i = 0; i < list.size(); i++) {
            var value = counts.get(list.get(i));
            var expansionWeight = getExpansionWeight(list.get(i));
            if(value != null)
                expansionWeight += value;

            counts.put(list.get(i), expansionWeight);

            if(expansionWeight > max){
                max = expansionWeight;
            }
        }

        list.clear();

        int finalMax = max;
        counts.forEach((key, value) -> {
            if(value == finalMax)
                list.add(key);
        });

        if(list.size() == 1)
            return list.get(0);
        return list.get(random.nextInt(0, list.size()));
    }

    private static int getExpansionWeight(Integer integer) throws Exception{
        return MapBasedBiomePool.getBiomeByColor(integer).getBiomeData().biomeWeight[(MiddleEarthMapGeneration.CURRENT_ITERATION <= 1) ? 0 : 1];
    }


    /**
     * TODO : Optimise this part, it the longest process in World-Gen
     * about 60s before -> about 40s now
     */
    public static BufferedImage blur(BufferedImage image, int brushSize, boolean crop) {
        int width = image.getWidth();
        int height = image.getHeight();
        int newWidth = width + (2 * brushSize);
        int newHeight = height + (2 * brushSize);

        BufferedImage imageWithBorders = image;
        Graphics2D g2d;
        if(!crop) { // CLAMP_TO_BORDER
            imageWithBorders = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
            g2d = imageWithBorders.createGraphics();

            g2d.setColor(MapBasedBiomePool.DEFAULT_COLOR);
            g2d.fillRect(0, 0, newWidth, newHeight);
            g2d.drawImage(image, brushSize, brushSize, null);
        } else { // CLAMP_TO_EDGE
            imageWithBorders = new BufferedImage(newWidth, newHeight, image.getType());
            g2d = imageWithBorders.createGraphics();

            // Copy image content (center)
            g2d.drawImage(image, brushSize, brushSize, null);

            // extend left & right
            g2d.drawImage(image, 0, brushSize, brushSize, height + brushSize, 0, 0, 1, height, null);
            g2d.drawImage(image, newWidth - brushSize, brushSize, newWidth, height + brushSize, width - 1, 0, width, height, null);

            // extend up & down
            g2d.drawImage(image, brushSize, 0, brushSize + width, brushSize, 0, 0, width, 1, null);
            g2d.drawImage(image, brushSize, newHeight - brushSize, brushSize + width, newHeight, 0, height - 1, width, height, null);

            // extend corners
            g2d.drawImage(image, 0, 0, brushSize, brushSize, 0, 0, 1, 1, null);
            g2d.drawImage(image, newWidth - brushSize, 0, newWidth, brushSize, width - 1, 0, width, 1, null);
            g2d.drawImage(image, 0, newHeight - brushSize, brushSize, newHeight, 0, height - 1, 1, height, null);
            g2d.drawImage(image, newWidth - brushSize, newHeight - brushSize, newWidth, newHeight, width - 1, height - 1, width, height, null);
        }

        float[] blurKernel = new float[brushSize*brushSize];

        if(gaussianBlurKernel.containsKey(brushSize)) {
            blurKernel = gaussianBlurKernel.get(brushSize);
        }
        else {
            Convolution.gaussianKernel(brushSize, brushSize, GAUSSIAN_SIGMA, blurKernel);
            gaussianBlurKernel.put(brushSize, blurKernel);
        }
        Kernel kernel = new Kernel(brushSize, brushSize, blurKernel);
        ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);

        BufferedImage blurredImage = new BufferedImage(width, height, image.getType());
        op.filter(image, blurredImage);

        if(crop) {
            return blurredImage.getSubimage(brushSize, brushSize, width - brushSize*2, height - brushSize*2);
        } else {
            return blurredImage;
        }
    }

    private static final int EDGE_BRUSH_SIZE = 3;
    public static BufferedImage edge(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        Kernel kernel = new Kernel(EDGE_BRUSH_SIZE, EDGE_BRUSH_SIZE, edgeKernel);
        ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);

        BufferedImage edgeImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        edgeImage = op.filter(image, edgeImage);



        return edgeImage;
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
