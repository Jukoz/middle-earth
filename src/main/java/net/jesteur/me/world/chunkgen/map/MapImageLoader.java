package net.jesteur.me.world.chunkgen.map;

import net.jesteur.me.MiddleEarth;
import net.jesteur.me.world.biomes.MEBiomesData;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class MapImageLoader {
    private static int[][] pixels;
    private static Random random = new Random(1379);

    public static void loadImage(ClassLoader classLoader) throws IOException, URISyntaxException {
        URL resource = classLoader.getResource("assets/" + MiddleEarth.MOD_ID + "/textures/map.png");
        BufferedImage img;
        try {
            img = ImageIO.read(resource);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            ex.printStackTrace();
            throw ex;
        }
        pixels = convertTo2D(img);

        for (int i = 0; i < 2; i++) {
            subDivide(false);
        }
    }

    public static int[] getImageSize() {
        return new int[] { pixels[0].length, pixels.length };
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
        int[][] newPixels = new int[height][width];
        Arrays.stream(newPixels).forEach(a -> Arrays.fill(a, pixels[0][0]));

        for (int y = 0; y < pixels.length; y++) {
            for (int x = 0; x < pixels[0].length; x++) {
                newPixels[y * 2][x * 2] = pixels[y][x];
            }
        }

        for (int y = 0; y < pixels.length - 1; y++) {
            for (int x = 0; x < pixels[0].length - 1; x++) {
                newPixels[(y * 2) + 1][x * 2] = pickRandom(newPixels[y * 2][x * 2], newPixels[(y * 2) + 2][x * 2]); // Horizontal
                newPixels[y * 2][(x * 2) + 1] = pickRandom(newPixels[y * 2][x * 2], newPixels[y * 2][(x * 2) + 2]); // Vertical
            }
        }
        if(takeRandom) {
            for (int y = 0; y < pixels.length - 1; y++) {
                for (int x = 0; x < pixels[0].length - 1; x++) {
                    int centerAverage = pickRandom(
                            pickRandom(newPixels[(y * 2) + 1][x * 2], newPixels[(y * 2) + 1][(x * 2) + 2]), // Top and down
                            pickRandom(newPixels[y * 2][(x * 2) + 1], newPixels[(y * 2) + 2][(x * 2) + 1])); // Left and right
                    newPixels[(y * 2) + 1][(x * 2) + 1] = centerAverage;
                }
            }
        } else {
            for (int y = 0; y < pixels.length - 1; y++) {
                for (int x = 0; x < pixels[0].length - 1; x++) {
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
        if(random.nextFloat() < 0.5f) return a;
        else return b;
    }

    private static int pickRandom(int[] heights) {
        return heights[random.nextInt(heights.length)];
    }

    private static int pickPopular(int[] heights) {
        HashMap<Integer, Integer> popularHeights = new HashMap<>();
        for (int height: heights) {
            popularHeights.merge(height, 1, Integer::sum);
        }

        int popular = heights[0];
        for (int height: heights) {
            if(popularHeights.get(height) > popularHeights.get(popular)) popular = height;
        }
        return popular;
    }

    private static int[][] convertTo2D(BufferedImage image) {

        final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        final int width = image.getWidth();
        final int height = image.getHeight();
        final boolean hasAlphaChannel = image.getAlphaRaster() != null;

        int[][] result = new int[height][width];
        if (hasAlphaChannel) {
            final int pixelLength = 4;
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
            final int pixelLength = 3;
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
        return result;
    }
}
