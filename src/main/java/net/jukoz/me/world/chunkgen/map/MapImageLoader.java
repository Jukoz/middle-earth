package net.jukoz.me.world.chunkgen.map;

import com.google.common.base.Stopwatch;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.world.biomes.MEBiome;
import net.jukoz.me.world.biomes.MEBiomesData;
import net.jukoz.me.world.datas.WorldMapDatas;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static net.jukoz.me.world.chunkgen.map.ImageUtils.blur;
import static net.jukoz.me.world.datas.WorldMapDatas.getBiomeRegionByRegionCoordinates;

public class MapImageLoader {

    public static void loadImage(ClassLoader classLoader) throws IOException, URISyntaxException {
        Stopwatch mainStopwatch = Stopwatch.createStarted();
        Stopwatch stopwatch = Stopwatch.createStarted();

        URL resource = classLoader.getResource("assets/" + MiddleEarth.MOD_ID + "/textures/map.png");

        BufferedImage img;

        try {
            img = ImageIO.read(resource);

            if(WorldMapDatas.needWorldGeneration(img)){
                String msg = "MapImageLoader::WorldMapData Guess : %s".formatted(WorldMapDatas.toStr());
                System.out.println(msg);
                ImageUtils.createBiomeMap(img);

                System.out.println("It took %s ms to convert the image to a 2D array (new)".formatted(stopwatch.elapsed(TimeUnit.MILLISECONDS)));
                stopwatch.reset();
                stopwatch.start();


            }
            if(WorldMapDatas.needHeightGeneration()) {
                System.out.println("Start the height map generation");
                createHeightMap();
                System.out.println("It took %s ms to generate height map".formatted(stopwatch.elapsed(TimeUnit.MILLISECONDS)));
                stopwatch.reset();
                stopwatch.start();
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            ex.printStackTrace();
            throw ex;
        }
        /*
          BufferedImage blurredImage = ImageUtils.blur(heightMap);
        System.out.println("It took %s ms to blur the height map".formatted(stopwatch.elapsed(TimeUnit.MILLISECONDS)));
        stopwatch.reset();
        stopwatch.start();

        MiddleEarthHeightMap.applyHeightMapImage(blurredImage);
        System.out.println("It took %s ms to apply the height map".formatted(stopwatch.elapsed(TimeUnit.MILLISECONDS)));
         */
        System.out.println("It took %s ms to load the image".formatted(mainStopwatch.elapsed(TimeUnit.MILLISECONDS)));

    }


    private static void createHeightMap() {
        final int threadPoolSize = 12;
        ExecutorService executorService = Executors.newFixedThreadPool(threadPoolSize);

        for (int i = 0; i < WorldMapDatas.AMOUNT_REGION_X; i++) {
            for (int j = 0; j < WorldMapDatas.AMOUNT_REGION_Y; j++) {
                BufferedImage buffImage = WorldMapDatas.getBiomeRegionByRegionCoordinates(i, j);
                if(buffImage != null){
                    int finalI = i;
                    int finalJ = j;
                    executorService.submit(() -> {
                        BufferedImage newHeightRegion = processRegion(buffImage, WorldMapDatas.REGION_SIZE);
                        WorldMapDatas.saveHeightRegion(finalI, finalJ, blur(newHeightRegion));
                    });
                }
            }
        }

        // Shutdown the executor and wait for all threads to finish
        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static BufferedImage processRegion(BufferedImage buffImage, int size) {
        BufferedImage newHeightRegion = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        for (int rI = 0; rI < 2; rI++) {
            for (int rJ = 0; rJ < 2; rJ++) {
                for (int x = 0; x < size; x++) {
                    for (int y = 0; y < size; y++) {
                        try {
                            int height = MEBiomesData.getBiomeByColor(buffImage.getRGB(x, y)).height;
                            if(height > 255 - 25){
                                height = 230;
                            }
                            int isNegative = (height < 0 ? 200 : 0);
                            byte decimal = 0;

                            if(height > 0){
                                height += 25;
                            }

                            newHeightRegion.setRGB(x, y, new Color(Math.abs(height), decimal, isNegative).getRGB());
                        } catch (Exception e) {
                            throw new RuntimeException("MapImageLoader.processRegion:: Failed to create color for the height (" + e.toString() + ")");
                        }
                    }
                }
            }
        }

        return newHeightRegion;
    }

}
