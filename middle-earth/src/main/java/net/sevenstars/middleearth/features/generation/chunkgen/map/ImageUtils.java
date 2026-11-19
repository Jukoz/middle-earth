package net.sevenstars.middleearth.features.generation.chunkgen.map;

import com.google.common.base.Stopwatch;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.features.generation.biomes.surface.MapBasedBiomePool;
import net.sevenstars.middleearth.features.generation.map.MiddleEarthMapGeneration;
import org.joml.sampling.Convolution;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.*;

public class ImageUtils {
    private static HashMap<Integer, float[]> gaussianBlurKernel = new HashMap<>();
    private static float[] edgeKernel =
                    {-1f, -2f, -1f,
                     -2f,  12f, -2f,
                     -1f, -2f, -1f};
    private static final float GAUSSIAN_SIGMA = 3.81f;

    public static Random random = new Random();

    public static BufferedImage fetchResourceImage(String path) throws IOException {
        URL resource = ImageUtils.class.getClassLoader().getResource(path);
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
                    //MiddleEarth.LOGGER.logError("ImageUtils::Can't find color at [%s,%s]".formatted(x,y));
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
                    //MiddleEarth.LOGGER.logError("ImageUtils::Can't find color at [%s,%s]".formatted(x,y));
                }
                colorOccurences.clear();
            }
        }
        return result;
    }

    private static Integer getMostOccuringColorFromBiomeList(ArrayList<Integer> list) throws Exception {
        if(list.isEmpty()){
            MiddleEarth.LOGGER.logError("ImageUtils::getMostCommonColor - List was empty!");
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

    // The depth of the world's bottom from the biomes of the map, as grey levels of 0 to 64 blocks. A max filter first keeps a range's core
    // at its full depth, then a wide gaussian lets the floor slope out under the foothills; the result is averaged down by step.
    public static BufferedImage depth(BufferedImage biomes, int dilation, float sigma, float cutoff, int step) {
        int width = biomes.getWidth();
        int height = biomes.getHeight();
        float[] depth = new float[width * height];
        Map<Integer, Integer> byColor = new HashMap<>();
        for(int z = 0; z < height; z++) {
            for(int x = 0; x < width; x++) {
                int rgb = biomes.getRGB(x, z);
                Integer extra = byColor.get(rgb);
                if(extra == null) {
                    extra = MapBasedBiomePool.getBiomeByColor(rgb).getBiomeData().extraDepth;
                    byColor.put(rgb, extra);
                }
                depth[z * width + x] = extra;
            }
        }

        float[] dilated = depth.clone();
        for(int z = 0; z < height; z++) {
            for(int x = 0; x < width; x++) {
                float value = depth[z * width + x];
                if(value <= 0) continue;
                for(int dz = -dilation; dz <= dilation; dz++) {
                    int nz = z + dz;
                    if(nz < 0 || nz >= height) continue;
                    for(int dx = -dilation; dx <= dilation; dx++) {
                        int nx = x + dx;
                        if(nx < 0 || nx >= width || dx * dx + dz * dz > dilation * dilation) continue;
                        if(dilated[nz * width + nx] < value) dilated[nz * width + nx] = value;
                    }
                }
            }
        }

        int radius = (int) Math.ceil(sigma * 2.5f);
        float[] kernel = new float[radius * 2 + 1];
        float sum = 0;
        for(int i = -radius; i <= radius; i++) {
            kernel[i + radius] = (float) Math.exp(-(i * i) / (2 * sigma * sigma));
            sum += kernel[i + radius];
        }
        for(int i = 0; i < kernel.length; i++) kernel[i] /= sum;
        float[] across = new float[width * height];
        for(int z = 0; z < height; z++) {
            for(int x = 0; x < width; x++) {
                float total = 0;
                for(int i = -radius; i <= radius; i++) total += kernel[i + radius] * dilated[z * width + Math.max(0, Math.min(width - 1, x + i))];
                across[z * width + x] = total;
            }
        }
        float[] blurred = new float[width * height];
        for(int z = 0; z < height; z++) {
            for(int x = 0; x < width; x++) {
                float total = 0;
                for(int i = -radius; i <= radius; i++) total += kernel[i + radius] * across[Math.max(0, Math.min(height - 1, z + i)) * width + x];
                blurred[z * width + x] = Math.max(0, total - cutoff) * MAX_DEPTH / (MAX_DEPTH - cutoff);
            }
        }

        BufferedImage result = new BufferedImage(width / step, height / step, BufferedImage.TYPE_INT_RGB);
        for(int z = 0; z < result.getHeight(); z++) {
            for(int x = 0; x < result.getWidth(); x++) {
                float total = 0;
                for(int dz = 0; dz < step; dz++) {
                    for(int dx = 0; dx < step; dx++) total += blurred[(z * step + dz) * width + x * step + dx];
                }
                int level = Math.round(total / (step * step) / MAX_DEPTH * 255);
                result.setRGB(x, z, (level << 16) | (level << 8) | level);
            }
        }
        return result;
    }

    public static final float MAX_DEPTH = 64;

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
