package net.jesteur.me.world.chunkgen.map;

import net.jesteur.me.MiddleEarth;
import net.jesteur.me.world.biomes.MEBiomesData;
import org.apache.commons.lang3.time.StopWatch;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class MapImageLoader {
    private static final int iterations = 3;
    private static int[][] pixels;
    private static Random random = new Random(1379);

    public static void loadImage(ClassLoader classLoader) throws IOException, URISyntaxException {
        URL resource = classLoader.getResource("assets/" + MiddleEarth.MOD_ID + "/textures/map.png");
        //URL exportURL = classLoader.getResource("assets/" + MiddleEarth.MOD_ID + "/textures/output.png");
        //File file = Paths.get(exportURL.toURI()).toFile();
        //String absolutePath = file.getAbsolutePath();

        BufferedImage img;
        try {
            img = ImageIO.read(resource);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            ex.printStackTrace();
            throw ex;
        }
        pixels = ImageUtils.convertTo2D(img);

        for (int i = 0; i < iterations; i++) {
            subDivide(false);
        }

        BufferedImage heightMap = createHeightMap();
        BufferedImage blurredImage = ImageUtils.blur(heightMap);
        //File outputfile = new File(absolutePath);
        //ImageIO.write(blurredImage, "png", outputfile);
        MiddleEarthHeightMap.applyHeightMapImage(blurredImage);
    }

    public static boolean isCoordinateInImage(int x, int y) {
        if(x < 0 || y < 0) return false;
        return (x < pixels[0].length && y < pixels.length);
    }

    public static float getBiomeHeight(int x, int y) {
        if(!isCoordinateInImage(x, y)) return 0;
        float height = 0;
        Color color = new Color(pixels[y][x]);
        try {
            height = MEBiomesData.biomeHeights.get(color).height;
        }
        catch (Exception e) {
            System.out.println("Unknown pixel color (" + color + ") at: " + x + "," + y + " at the Middle Earth map");
            height = MEBiomesData.defaultBiome.height;
        }
        return height;
    }

    public static int getBiomeColor(int x, int y) {
        if(!isCoordinateInImage(x, y)) return 0;
        return pixels[y][x];
    }

    private static void subDivide(boolean takeRandom) {
        int width = (pixels[0].length * 2) - 1;
        int height = (pixels.length * 2) - 1;
        int maxY = pixels.length - 1;
        int maxX = pixels[0].length - 1;

        int[][] newPixels = new int[height][width];
        Arrays.stream(newPixels).forEach(a -> Arrays.fill(a, pixels[0][0]));

        for (int y = 0; y < pixels.length; y++) {
            for (int x = 0; x < pixels[0].length; x++) {
                newPixels[y * 2][x * 2] = pixels[y][x];
            }
        }

        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxX; x++) {
                newPixels[(y * 2) + 1][x * 2] = pickRandom(newPixels[y * 2][x * 2], newPixels[(y * 2) + 2][x * 2]); // Horizontal
                newPixels[y * 2][(x * 2) + 1] = pickRandom(newPixels[y * 2][x * 2], newPixels[y * 2][(x * 2) + 2]); // Vertical
            }
        }
        if(takeRandom) {
            for (int y = 0; y < maxY; y++) {
                for (int x = 0; x < maxX; x++) {
                    int centerAverage = pickRandom(
                            pickRandom(newPixels[(y * 2) + 1][x * 2], newPixels[(y * 2) + 1][(x * 2) + 2]), // Top and down
                            pickRandom(newPixels[y * 2][(x * 2) + 1], newPixels[(y * 2) + 2][(x * 2) + 1])); // Left and right
                    newPixels[(y * 2) + 1][(x * 2) + 1] = centerAverage;
                }
            }
        } else {
            for (int y = 0; y < maxY; y++) {
                for (int x = 0; x < maxX; x++) {
                    int centerAverage = pickPopular(new int[]{
                            newPixels[(y * 2) + 1][x * 2],
                            newPixels[(y * 2) + 1][(x * 2) + 2],
                            newPixels[y * 2][(x * 2) + 1],
                            newPixels[(y * 2) + 2][(x * 2) + 1],

                            newPixels[y * 2][x * 2],
                            newPixels[(y * 2) + 2][(x * 2) + 2],
                            newPixels[y * 2][(x * 2) + 2],
                            newPixels[(y * 2) + 2][x * 2]
                    });
                    newPixels[(y * 2) + 1][(x * 2) + 1] = centerAverage;
                }
            }
        }

        pixels = newPixels;
    }

    private static int pickRandom(int a, int b) {
        return random.nextFloat() < 0.5 ? a : b;
    }

    private static int pickPopular(int[] heights) {
        HashMap<Integer, Integer> popularHeights = new HashMap<>();
        for (int height : heights) {
            popularHeights.merge(height, 1, Integer::sum);
        }

        int popular = heights[0];
        int maxCount = popularHeights.get(popular);
        for (int height : heights) {
            int currentCount = popularHeights.get(height);
            if (currentCount > maxCount) {
                popular = height;
                maxCount = currentCount;
            }
        }

        return popular;
    }

    private static BufferedImage createHeightMap() {
        BufferedImage heightMap = new BufferedImage(pixels[0].length, pixels.length, BufferedImage.TYPE_BYTE_GRAY);
        for (int y = 0; y < pixels.length; y++) {
            for (int x = 0; x < pixels[0].length; x++) {
                int height = (int) ((getBiomeHeight(x, y) - MEBiomesData.MINIMAL_HEIGHT) * 4);
                try {
                    int rgb = new Color(height, height, height).getRGB();
                    heightMap.setRGB(x, y, rgb);
                } catch (Exception e) {
                    System.out.println(e.toString());
                }

            }
        }
        return heightMap;
    }
}
