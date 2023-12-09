package net.jukoz.me.world.chunkgen.map;

import com.google.common.base.Stopwatch;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.world.biomes.MEBiome;
import net.jukoz.me.world.biomes.MEBiomesData;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MapImageLoader {
    private static short[][] indexes;
    
    private static Random random = new Random(1379);

    public static void loadImage(ClassLoader classLoader) throws IOException, URISyntaxException {
        Stopwatch mainStopwatch = Stopwatch.createStarted();
        Stopwatch stopwatch = Stopwatch.createStarted();

        URL resource = classLoader.getResource("assets/" + MiddleEarth.MOD_ID + "/textures/map.png");

        BufferedImage img;
        try {
            img = ImageIO.read(resource);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            ex.printStackTrace();
            throw ex;
        }
        indexes = ImageUtils.convertImageToBiomeIdArray(img);
        System.out.println("It took %s milliseconds to convert the image to a 2D array (new)".formatted(stopwatch.elapsed(TimeUnit.MILLISECONDS)));
        /*
        stopwatch.reset();
        stopwatch.start();
        // Old
        // MiddleEarth.MAP_ITERATION
        for (int i = 0; i < MiddleEarth.MAP_ITERATION ; i++) {
            subDivide(false);
        }
         */
        //System.out.println("It took %s milliseconds to iterate %s times (old) with %s,%s".formatted(stopwatch.elapsed(TimeUnit.MILLISECONDS), MiddleEarth.MAP_ITERATION, pixels.length, pixels[0].length));

        stopwatch.reset();
        stopwatch.start();
        indexes = ImageUtils.subdivide(indexes, MiddleEarth.MAP_ITERATION);
        System.out.println("It took %s milliseconds to iterate %s times (new)".formatted(stopwatch.elapsed(TimeUnit.MILLISECONDS), MiddleEarth.MAP_ITERATION));

        BufferedImage heightMap = createHeightMap();
        BufferedImage blurredImage = ImageUtils.blur(heightMap);
        //File outputfile = new File(absolutePath);
        //ImageIO.write(blurredImage, "png", outputfile);
        MiddleEarthHeightMap.applyHeightMapImage(blurredImage);
        System.out.println("It took %s milliseconds to load the image".formatted(mainStopwatch.elapsed(TimeUnit.MILLISECONDS)));
    }

    public static boolean isCoordinateInImage(int x, int y) {
        if(x < 0 || y < 0) return false;
        return (x < indexes.length && y < indexes[0].length);
    }

    public static float getBiomeHeight(int x, int y) {
        if(!isCoordinateInImage(x, y)) return 0;
        float height = 0;
        try {
            height = MEBiomesData.getBiomeById(indexes[x][y]).height;
        }
        catch (Exception e) {
            System.out.println("Unknown biome index (" + indexes[x][y] + ") at: " + x + "," + y + " at the Middle Earth map");
            height = MEBiomesData.defaultBiome.height;
        }
        return height;
    }

    public static MEBiome getbiomeByWorldCoordinate(int x, int y) {
        if(!isCoordinateInImage(x, y)) return null;
        return MEBiomesData.getBiomeById(indexes[x][y]);
    }

    /*
    private static void subDivide(boolean takeRandom) {
        int width = (pixels[0].length * 2) - 1; // TODO
        int height = (pixels.length * 2) - 1; // TODO
        int maxY = pixels.length - 1; // TODO
        int maxX = pixels[0].length - 1; // TODO

        int[][] newPixels = new int[height][width];
        Arrays.stream(newPixels).forEach(a -> Arrays.fill(a, pixels[0][0])); // TODO

        for (int y = 0; y < pixels.length; y++) { // TODO
            for (int x = 0; x < pixels[0].length; x++) { // TODO
                newPixels[y * 2][x * 2] = pixels[y][x]; // TODO
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

        pixels = newPixels; // TODO
    }
    */

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
        BufferedImage heightMap = new BufferedImage(indexes[0].length, indexes.length, BufferedImage.TYPE_BYTE_GRAY);
        for (int y = 0; y < indexes.length; y++) {
            for (int x = 0; x < indexes[0].length; x++) {
                int height = (int) ((getBiomeHeight(x, y) - MEBiomesData.MINIMAL_HEIGHT) * 4);
                try {
                    height = Math.max(0, Math.min(255, height));
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
