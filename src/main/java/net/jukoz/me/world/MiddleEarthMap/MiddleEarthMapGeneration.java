package net.jukoz.me.world.MiddleEarthMap;

import net.jukoz.me.utils.LoggerUtil;
import net.jukoz.me.utils.resources.FileType;
import net.jukoz.me.utils.resources.FileUtils;
import net.jukoz.me.world.MiddleEarthMap.MiddleEarthMapConfigs;
import net.jukoz.me.world.biomes.surface.MEBiomesData;
import net.jukoz.me.world.chunkgen.map.ImageUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static java.lang.String.format;

public class MiddleEarthMapGeneration {
    private FileUtils fileUtils;
    private LoggerUtil loggerUtil;

    public MiddleEarthMapGeneration() throws Exception {
        fileUtils = FileUtils.getInstance();
        loggerUtil = LoggerUtil.getInstance();
        generate();
    }

    public void generate() throws Exception {
        loggerUtil.logInfoMsg("");
        loggerUtil.logInfoMsg("================ MiddleEarthMapGeneration ================");

        BufferedImage initialMap = getInitialImage();

        if(initialMap == null){
            throw new Exception(this + " : The image of the map in resource has created an error and operation cannot continue.");
        }

        loggerUtil.logInfoMsg("Validating BIOME generation availability;");

        int iterationToGenerate = (MiddleEarthMapConfigs.FORCE_GENERATION)
                ? MiddleEarthMapConfigs.MAP_ITERATION + 1
                : findAmountOfIterationToGenerate(initialMap);

        if(iterationToGenerate > 0){
            loggerUtil.logInfoMsg("Begin BIOME generation;");
            generateBiomes(initialMap, iterationToGenerate);
        }

        loggerUtil.logInfoMsg("Validating HEIGHT generation availability;");
        if(!validateHeightDatas(initialMap)){
            loggerUtil.logInfoMsg("Begin HEIGHT generation;");
            generateHeight(initialMap);
        }
    }

    private BufferedImage getInitialImage(){
        loggerUtil.logInfoMsg("Validating ORIGINAL image existence;");
        BufferedImage initialImage = fileUtils.getResourceImage(MiddleEarthMapConfigs.INITIAL_IMAGE);
        if(initialImage == null){
            loggerUtil.logError("Initial map image couldn't be found!");
            return null;
        }
        loggerUtil.logInfoMsg("Validating ORIGINAL image size;");
        if(initialImage.getWidth() % MiddleEarthMapConfigs.REGION_SIZE != 0 || initialImage.getHeight() % MiddleEarthMapConfigs.REGION_SIZE != 0){
            loggerUtil.logError("Initial map image has the wrong size!");
            return null;
        }

        return initialImage;
    }

    private int findAmountOfIterationToGenerate(BufferedImage initialMap) {
        int currentRegionAmountX = initialMap.getWidth() / MiddleEarthMapConfigs.REGION_SIZE;
        int currentRegionAmountY = initialMap.getHeight() / MiddleEarthMapConfigs.REGION_SIZE;
        int absoluteMapIteration = MiddleEarthMapConfigs.MAP_ITERATION + 1;

        for(int i = 0; i < absoluteMapIteration; i++){
            if(i > 0){
                currentRegionAmountX *= 2;
                currentRegionAmountY *= 2;
            }

            for(int x = 0; x < currentRegionAmountX; x ++){
                for(int y = 0; y < currentRegionAmountY; y ++) {
                    String path = MiddleEarthMapConfigs.BIOME_PATH.formatted(i) + MiddleEarthMapConfigs.IMAGE_NAME.formatted(x,y);
                    if(fileUtils.getRunImage(path) == null){
                        loggerUtil.logError("TO REMOVE - Lacking biome file at : [%s]".formatted(path));
                        return absoluteMapIteration - i;
                    }
                }
            }
        }
        return 0;
    }

    private BufferedImage[][][] generateBiomes(BufferedImage initialImage, int missingIterationAmount) {
        int startingIteration = MiddleEarthMapConfigs.MAP_ITERATION + 1 - missingIterationAmount;
        if(startingIteration == 0){
            generateInitialBiomes(initialImage);
            startingIteration ++;
        }

        for(int i = startingIteration; i < MiddleEarthMapConfigs.MAP_ITERATION + 1; i ++){
            ExecutorService executorService = Executors.newFixedThreadPool(MiddleEarthMapConfigs.THREAD_POOL_SIZE);

            int regionAmountX = (int) (initialImage.getWidth() / MiddleEarthMapConfigs.REGION_SIZE * Math.pow(2, i - 1));
            int regionAmountY = (int) (initialImage.getHeight() / MiddleEarthMapConfigs.REGION_SIZE * Math.pow(2, i - 1));

            for(int x = 0; x < regionAmountX; x++){
                for(int y = 0; y < regionAmountY; y++){
                    int finalI = i;
                    int finalX = x;
                    int finalY = y;
                    executorService.submit(() -> {
                        String path = MiddleEarthMapConfigs.BIOME_PATH.formatted(finalI - 1) + MiddleEarthMapConfigs.IMAGE_NAME.formatted(finalX, finalY);
                        BufferedImage[][] subidivedRegions = ImageUtils.subdivide(fileUtils.getRunImage(path));

                        for(int j = 0; j < 2; j ++){
                            for(int k = 0; k < 2; k ++){
                                fileUtils.saveImage(
                                        subidivedRegions[j][k],
                                        MiddleEarthMapConfigs.BIOME_PATH.formatted(finalI),
                                        MiddleEarthMapConfigs.IMAGE_NAME.formatted((finalX * 2) + j, (finalY * 2) + k),
                                        FileType.Png
                                );
                            }
                        }
                    });

                }
            }
            // Shutdown the executor and wait for all threads to finish
            executorService.shutdown();
            try {
                executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            } catch (Exception e) {
                loggerUtil.logError("Error while generating biomes");
            }
        }

        // Generate iterations with subdivisions

        return new BufferedImage[0][][];
    }

    private void generateInitialBiomes(BufferedImage initialImage){
        if(initialImage.getWidth() != MiddleEarthMapConfigs.REGION_SIZE || initialImage.getWidth() !=  MiddleEarthMapConfigs.REGION_SIZE){
            loggerUtil.logError("TO REMOVE - Need splitting for the initial image!");
            for(int i = 0; i < initialImage.getWidth() / MiddleEarthMapConfigs.REGION_SIZE; i++){
                for(int j = 0; j < initialImage.getHeight() / MiddleEarthMapConfigs.REGION_SIZE; j++){
                    BufferedImage newImage = initialImage.getSubimage(MiddleEarthMapConfigs.REGION_SIZE * i, MiddleEarthMapConfigs.REGION_SIZE * j, MiddleEarthMapConfigs.REGION_SIZE, MiddleEarthMapConfigs.REGION_SIZE);
                    fileUtils.saveImage(newImage,MiddleEarthMapConfigs.BIOME_PATH.formatted(0), MiddleEarthMapConfigs.IMAGE_NAME.formatted(i,j), FileType.Png);
                }
            }
        } else {
            fileUtils.saveImage(initialImage,MiddleEarthMapConfigs.BIOME_PATH.formatted(0), MiddleEarthMapConfigs.IMAGE_NAME.formatted(0,0), FileType.Png);
        }
    }

    private boolean validateHeightDatas(BufferedImage initialImage) {
        int regionAmountX = (int) (initialImage.getWidth() / MiddleEarthMapConfigs.REGION_SIZE * Math.pow(2, MiddleEarthMapConfigs.MAP_ITERATION));
        int regionAmountY = (int) (initialImage.getHeight() / MiddleEarthMapConfigs.REGION_SIZE * Math.pow(2, MiddleEarthMapConfigs.MAP_ITERATION));

        for(int x = 0; x < regionAmountX; x ++){
            for(int y = 0; y < regionAmountY; y ++) {
                String path = MiddleEarthMapConfigs.HEIGHT_PATH + MiddleEarthMapConfigs.IMAGE_NAME.formatted(x,y);
                if(fileUtils.getRunImage(path) == null){
                    return false;
                }
            }
        }
        return true;
    }
    private void generateHeight(BufferedImage initialImage) {
        ExecutorService executorService = Executors.newFixedThreadPool(MiddleEarthMapConfigs.THREAD_POOL_SIZE);

        int regionAmountX = (int) (initialImage.getWidth() / MiddleEarthMapConfigs.REGION_SIZE * Math.pow(2, MiddleEarthMapConfigs.MAP_ITERATION));
        int regionAmountY = (int) (initialImage.getHeight() / MiddleEarthMapConfigs.REGION_SIZE * Math.pow(2, MiddleEarthMapConfigs.MAP_ITERATION));

        for(int x = 0; x < regionAmountX; x++){
            for(int y = 0; y < regionAmountY; y++){
                int finalX = x;
                int finalY = y;
                executorService.submit(() -> {
                    String path = MiddleEarthMapConfigs.BIOME_PATH.formatted(MiddleEarthMapConfigs.MAP_ITERATION) + MiddleEarthMapConfigs.IMAGE_NAME.formatted(finalX, finalY);
                    fileUtils.saveImage(
                            FileUtils.blur(processHeightRegion(fileUtils.getRunImage(path), MiddleEarthMapConfigs.REGION_SIZE), 16, 1.0f / (16 * 16)),
                            MiddleEarthMapConfigs.HEIGHT_PATH,
                            MiddleEarthMapConfigs.IMAGE_NAME.formatted(finalX, finalY),
                            FileType.Png
                    );
                });
            }
        }
        // Shutdown the executor and wait for all threads to finish
        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (Exception e) {
            loggerUtil.logError("Error while generating biomes");
        }
    }

    private static BufferedImage processHeightRegion(BufferedImage biomeImage, int size) {
        BufferedImage newHeightRegion = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);

            for (int x = 0; x < size; x++) {
                for (int y = 0; y < size; y++) {
                    try {
                        int height = MEBiomesData.getBiomeByColor(biomeImage.getRGB(x, y)).height;
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
                        throw new RuntimeException("MiddleEarthMapGeneration.processHeightRegion : Failed to create color for the height [%s]".formatted(e));
                    }
                }
        }

        return newHeightRegion;
    }
}

