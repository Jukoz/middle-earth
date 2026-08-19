package net.sevenstars.middleearth.world.map;

import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.utils.resources.FileType;
import net.sevenstars.middleearth.utils.resources.FileUtils;
import net.sevenstars.middleearth.world.biomes.surface.MapBasedBiomePool;
import net.sevenstars.middleearth.world.biomes.surface.MapBasedCustomBiome;
import net.sevenstars.middleearth.world.chunkgen.map.ImageUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class MiddleEarthMapGeneration {
    public static int CURRENT_ITERATION = 0;

    private FileUtils fileUtils;
    private static final int WATER_BUFFER = 16;
    private static final float WATER_HEIGHT_MULTIPLIER = 1.0f;
    private static BufferedImage baseHeightImage;
    private static BufferedImage edgeHeightImage;

    private static BufferedImage initialMap;

    public MiddleEarthMapGeneration() throws Exception {
        fileUtils = FileUtils.getInstance();
        generate();
    }

    public void generate() throws Exception {
        MiddleEarth.LOGGER.logInfoMsg("");
        MiddleEarth.LOGGER.logInfoMsg("================ MiddleEarthMapGeneration ================");

        if (MiddleEarth.ENABLE_INSTANT_BOOTING) {
            MiddleEarth.LOGGER.logInfoMsg("Instant Booting - Enabled");
            if (installBundledMapData()) {
                return;
            }
            MiddleEarth.LOGGER.logError(
                    "Instant Booting - Failure; falling back to generated map data."
            );
        } else {
            MiddleEarth.LOGGER.logInfoMsg("Instant Booting - Disabled");
        }

        try {
            initialMap = getInitialImage();
            if (initialMap == null) {
                MiddleEarth.LOGGER.logError(
                        "The bundled map image could not be read; map generation will use runtime fallbacks."
                );
                return;
            }
        } catch (Exception e) {
            MiddleEarth.LOGGER.logError("MiddleEarthMapGeneration::generate() - Fetch Initial Map", e);
            return;
        }

        MiddleEarth.LOGGER.logInfoMsg("Validating initial map BIOME colors;");
        if(!validateBaseColors(initialMap)) return;

        MiddleEarth.LOGGER.logInfoMsg("Validating BIOME generation availability;");
        int iterationToGenerate = (MiddleEarthMapConfigs.FORCE_GENERATION)
                ? MiddleEarthMapConfigs.MAP_ITERATION + 1
                : findAmountOfIterationToGenerate(initialMap);

        if(iterationToGenerate > 0){
            MiddleEarth.LOGGER.logInfoMsg("Begin BIOME generation;");
            generateBiomes(initialMap, iterationToGenerate);
        }

        MiddleEarth.LOGGER.logInfoMsg("Validating initial map HEIGHT MODIFIER generation availability;");
        if(!validateBaseHeightDatas()){
            MiddleEarth.LOGGER.logInfoMsg("Begin initial map HEIGHT MODIFIER generation;");
            generateBaseHeightImage(initialMap);
            generateBaseEdgeImage(initialMap);
        }


        MiddleEarth.LOGGER.logInfoMsg("Validating HEIGHT generation availability;");
        if(!validateHeightDatas(initialMap)){
            MiddleEarth.LOGGER.logInfoMsg("Begin HEIGHT generation;");
            generateHeight(initialMap);
        }
    }

    private boolean installBundledMapData() {
        File runtimeDataRoot = new File(MiddleEarthMapConfigs.MOD_DATA_MOD_ROOT);
        File destination = new File(MiddleEarthMapConfigs.MOD_DATA);
        File completionMarker = new File(destination, ".complete");
        if (completionMarker.isFile()) {
            try {
                String installedVersion = Files.readString(completionMarker.toPath()).strip();
                if (MiddleEarthMapConfigs.MAP_DATA_VERSION.equals(installedVersion)
                        && hasRequiredBundledMapData()) {
                    MiddleEarth.LOGGER.logInfoMsg(
                            "Instant Booting - Skipped, verified files already present"
                    );
                    return true;
                }
                MiddleEarth.LOGGER.logWarn(
                        "Bundled map marker is stale or its files are incomplete; reinstalling map data."
                );
            } catch (IOException exception) {
                MiddleEarth.LOGGER.logWarn(
                        "Bundled map marker could not be read; reinstalling map data."
                );
            }
        }

        try {
            Files.createDirectories(runtimeDataRoot.toPath());
            String resourcePath = "/" + MiddleEarthMapConfigs.INITIAL_MAP_FOLDER + ".zip";
            String runtimeRoot = runtimeDataRoot.getCanonicalPath() + File.separator;
            InputStream resourceStream = getClass().getResourceAsStream(resourcePath);
            if (resourceStream == null) {
                throw new IOException("Missing bundled map archive " + resourcePath);
            }

            try (InputStream inputStream = resourceStream;
                 ZipInputStream zipInputStream = new ZipInputStream(inputStream)) {
                ZipEntry entry;
                byte[] buffer = new byte[8192];
                while ((entry = zipInputStream.getNextEntry()) != null) {
                    File entryDestination = new File(runtimeDataRoot, entry.getName());
                    String destinationPath = entryDestination.getCanonicalPath();
                    if (!destinationPath.startsWith(runtimeRoot)) {
                        throw new IOException(
                                "Invalid path in bundled map archive: " + entry.getName()
                        );
                    }
                    if (entry.isDirectory()) {
                        Files.createDirectories(entryDestination.toPath());
                    } else {
                        File parent = entryDestination.getParentFile();
                        if (parent != null) {
                            Files.createDirectories(parent.toPath());
                        }
                        try (FileOutputStream outputStream = new FileOutputStream(entryDestination)) {
                            int length;
                            while ((length = zipInputStream.read(buffer)) > 0) {
                                outputStream.write(buffer, 0, length);
                            }
                        }
                    }
                    zipInputStream.closeEntry();
                }
            }

            if (!hasRequiredBundledMapData()) {
                throw new IOException("Bundled map archive is incomplete after extraction");
            }
            Files.writeString(completionMarker.toPath(), MiddleEarthMapConfigs.MAP_DATA_VERSION);
            MiddleEarth.LOGGER.logInfoMsg("Instant Booting - Completed");
            return true;
        } catch (IOException exception) {
            MiddleEarth.LOGGER.logError(
                    "MiddleEarthMapGeneration::Couldn't install bundled map data",
                    exception
            );
            return false;
        }
    }

    private boolean hasRequiredBundledMapData() {
        File baseHeight = new File(
                MiddleEarthMapConfigs.BASE_HEIGHT_PATH
                        + MiddleEarthMapConfigs.BASE_HEIGHT_IMAGE_NAME
        );
        File baseEdge = new File(
                MiddleEarthMapConfigs.BASE_HEIGHT_PATH
                        + MiddleEarthMapConfigs.BASE_EDGE_IMAGE_NAME
        );
        if (!baseHeight.isFile() || !baseEdge.isFile()) {
            return false;
        }

        int regionCount = 1 << MiddleEarthMapConfigs.MAP_ITERATION;
        for (int x = 0; x < regionCount; x++) {
            for (int y = 0; y < regionCount; y++) {
                String imageName = MiddleEarthMapConfigs.IMAGE_NAME.formatted(x, y);
                File biome = new File(
                        MiddleEarthMapConfigs.BIOME_PATH.formatted(
                                MiddleEarthMapConfigs.MAP_ITERATION
                        ) + imageName
                );
                File height = new File(MiddleEarthMapConfigs.HEIGHT_PATH + imageName);
                if (!biome.isFile() || !height.isFile()) {
                    return false;
                }
            }
        }
        return true;
    }

    public static BufferedImage getEdgeHeightImage() {
        return edgeHeightImage;
    }

    private boolean validateBaseColors(BufferedImage initialMap) {
        if(initialMap == null)
            return false;
        for(int x = 0; x < initialMap.getWidth(); x++){
            for(int y = 0; y < initialMap.getHeight(); y++){
                try{
                    MapBasedBiomePool.getBiomeByColor(initialMap.getRGB(x,y));
                } catch (Exception e) {
                    MiddleEarth.LOGGER.logError("MiddleEarthMapGeneration::Cannot find color at [%s,%s] in the inital map".formatted(x,y));
                    return false;
                }
            }
        }
        return true;
    }

    private BufferedImage getInitialImage(){
        MiddleEarth.LOGGER.logInfoMsg("Validating ORIGINAL image existence;");
        BufferedImage initialImage = fileUtils.getResourceImage(MiddleEarthMapConfigs.INITIAL_IMAGE);
        if(initialImage == null){
            MiddleEarth.LOGGER.logError("Initial map image couldn't be found!");
            return null;
        }
        MiddleEarth.LOGGER.logInfoMsg("Validating ORIGINAL image size;");
        if(initialImage.getWidth() % MiddleEarthMapConfigs.REGION_SIZE != 0 || initialImage.getHeight() % MiddleEarthMapConfigs.REGION_SIZE != 0){
            MiddleEarth.LOGGER.logError("Initial map image has the wrong size!");
            return null;
        }

        return initialImage;
    }

    private int findAmountOfIterationToGenerate(BufferedImage initialMap) {
        if(initialMap == null)
            return 0;
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
                        MiddleEarth.LOGGER.logError("Need to regenerate biome files: Lacking biome file at : [%s]".formatted(path));
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

            CURRENT_ITERATION = i;

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
            executorService.shutdown();
            try {
                executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            } catch (Exception e) {
                MiddleEarth.LOGGER.logError("Error while generating biomes");
            }
        }
        return new BufferedImage[0][][];
    }

    private void generateInitialBiomes(BufferedImage initialImage){
        if(initialImage.getWidth() != MiddleEarthMapConfigs.REGION_SIZE
                || initialImage.getHeight() != MiddleEarthMapConfigs.REGION_SIZE){
            MiddleEarth.LOGGER.logError("Need to regenerate height files: Need splitting for the initial image!");
            for(int i = 0; i < initialImage.getWidth() / MiddleEarthMapConfigs.REGION_SIZE; i++){
                for(int j = 0; j < initialImage.getHeight() / MiddleEarthMapConfigs.REGION_SIZE; j++){
                    BufferedImage newImage = initialImage.getSubimage(MiddleEarthMapConfigs.REGION_SIZE * i, MiddleEarthMapConfigs.REGION_SIZE * j, MiddleEarthMapConfigs.REGION_SIZE, MiddleEarthMapConfigs.REGION_SIZE);
                    fileUtils.saveImage(newImage, MiddleEarthMapConfigs.BIOME_PATH.formatted(0), MiddleEarthMapConfigs.IMAGE_NAME.formatted(i,j), FileType.Png);
                }
            }
        } else {
            fileUtils.saveImage(initialImage, MiddleEarthMapConfigs.BIOME_PATH.formatted(0), MiddleEarthMapConfigs.IMAGE_NAME.formatted(0,0), FileType.Png);
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

    private final static int HEIGHT_BLUR_SIZE = 17;
    private void generateHeight(BufferedImage initialImage) {
        long start = System.currentTimeMillis();
        ExecutorService executorService = Executors.newFixedThreadPool(MiddleEarthMapConfigs.THREAD_POOL_SIZE);

        int regionAmountX = (int) (initialImage.getWidth() / MiddleEarthMapConfigs.REGION_SIZE * Math.pow(2, MiddleEarthMapConfigs.MAP_ITERATION));
        int regionAmountY = (int) (initialImage.getHeight() / MiddleEarthMapConfigs.REGION_SIZE * Math.pow(2, MiddleEarthMapConfigs.MAP_ITERATION));

        for(int x = 0; x < regionAmountX; x++) {
            for (int y = 0; y < regionAmountY; y++) {
                int finalX = x;
                int finalY = y;
                executorService.submit(() -> {
                    fileUtils.saveImage(
                            ImageUtils.blur(processHeightRegion(fileUtils.getRunImageWithBorders(finalX, finalY, HEIGHT_BLUR_SIZE),
                                            MiddleEarthMapConfigs.REGION_SIZE, true, finalX, finalY, HEIGHT_BLUR_SIZE),
                                    HEIGHT_BLUR_SIZE, true),
                            MiddleEarthMapConfigs.HEIGHT_PATH,
                            MiddleEarthMapConfigs.IMAGE_NAME.formatted(finalX, finalY),
                            FileType.Png
                    );
                });
            }
        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (Exception e) {
            MiddleEarth.LOGGER.logError("Error while generating heights");
        }

        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        MiddleEarth.LOGGER.logInfoMsg("TIME BLUR FOR HEIGHT: " + timeElapsed);
    }

    private boolean validateBaseHeightDatas() {
        String path = MiddleEarthMapConfigs.BASE_HEIGHT_PATH + MiddleEarthMapConfigs.BASE_HEIGHT_IMAGE_NAME;
        if(fileUtils.getRunImage(path) == null){
            return false;
        }
        baseHeightImage = fileUtils.getRunImage(path);
        return true;
    }

    private final static int BASE_HEIGHT_BLUR_SIZE = 25;
    private void generateBaseHeightImage(BufferedImage initialMap) {
        baseHeightImage = ImageUtils.blur(processHeightRegion(initialMap, MiddleEarthMapConfigs.REGION_SIZE, false, 0,0, 0), BASE_HEIGHT_BLUR_SIZE, false);
        fileUtils.saveImage(baseHeightImage,
                MiddleEarthMapConfigs.BASE_HEIGHT_PATH,
                MiddleEarthMapConfigs.BASE_HEIGHT_IMAGE_NAME,
                FileType.Png
        );
    }
    private void generateBaseEdgeImage(BufferedImage initialMap) {
        edgeHeightImage = ImageUtils.edge(initialMap);
        edgeHeightImage = ImageUtils.blur(edgeHeightImage, 15, false);
        fileUtils.saveImage(edgeHeightImage,
                MiddleEarthMapConfigs.BASE_HEIGHT_PATH,
                MiddleEarthMapConfigs.BASE_EDGE_IMAGE_NAME,
                FileType.Png
        );
    }

    private static BufferedImage processHeightRegion(BufferedImage biomeImage, int size, boolean hasBaseImage, int imageX, int imageZ, int brushSize) {
        BufferedImage newHeightRegion = new BufferedImage(size + brushSize*2, size + brushSize*2, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < size + brushSize*2; x++) {
            for (int z = 0; z < size + brushSize*2; z++) {
                try {
                    MapBasedCustomBiome biome = MapBasedBiomePool.getBiomeByColor(biomeImage.getRGB(x, z));
                    int height = biome.getHeight();
                    if(height > 255){
                        height = 255;
                    }

                    int waterHeightDifference = biome.getWaterHeight() - MapBasedCustomBiome.DEFAULT_WATER_HEIGHT;
                    int water = 0;
                    int waterHeight = height - waterHeightDifference;
                    if(waterHeight < 0) {
                        water = (int) Math.abs((waterHeight * WATER_HEIGHT_MULTIPLIER) - WATER_BUFFER);
                        height = Math.max(0, height);
                    }

                    short noiseModifier = (short) (biome.getBiomeData().noiseModifier * 127);

                    Color heightModifier = (hasBaseImage)
                            ? getBaseImageHeightModifier(x, z, imageX, imageZ, brushSize)
                            : new Color(Math.abs(height), noiseModifier, 0);

                    int red = (int)Math.round((biome.getBiomeData().heightModifier * ((double)Math.abs(height)) + (1 - biome.getBiomeData().heightModifier) * (double)heightModifier.getRed()));

                    int green = (int)((noiseModifier + heightModifier.getGreen()) / 2f);

                    Color newColor = new Color(red, green, water);

                    newHeightRegion.setRGB(x, z, newColor.getRGB());
                } catch (Exception e) {
                    throw new RuntimeException("MiddleEarthMapGeneration.processHeightRegion : Failed to create color for the height [%s]".formatted(e));
                }
            }
        }

        return newHeightRegion;
    }

    private static Color getBaseImageHeightModifier(int x, int z, int imageX, int imageZ, int brushSize) {
        double iterationDivider = Math.pow(2, MiddleEarthMapConfigs.MAP_ITERATION);

        float posX = (float) (((imageX * MiddleEarthMapConfigs.REGION_SIZE) + x - brushSize) / iterationDivider);
        float posZ = (float) (((imageZ * MiddleEarthMapConfigs.REGION_SIZE) + z - brushSize) / iterationDivider);

        int baseX = (int) (posX);
        int baseZ = (int) (posZ);

        Color initialColor = getColorFromBaseMap(baseX, baseZ);
        Color rightColor = getColorFromBaseMap(baseX + 1, baseZ);
        Color bottomColor = getColorFromBaseMap(baseX, baseZ + 1);
        Color botRightColor = getColorFromBaseMap(baseX + 1, baseZ + 1);

        float weightX = posX - baseX;
        float weightZ = posZ - baseZ;

        Color interpolatedTopColor = interpolateColor(initialColor, rightColor, weightX);
        Color interpolatedBottomColor = interpolateColor(bottomColor, botRightColor, weightX);

        return interpolateColor(interpolatedTopColor, interpolatedBottomColor, weightZ);
    }

    private static Color getColorFromBaseMap(int x, int z){
        if(x < 0 || x >= MiddleEarthMapConfigs.REGION_SIZE || z < 0 || z >= MiddleEarthMapConfigs.REGION_SIZE) {
            x = 0; // default biome corner
            z = 0;
        }
        return new Color(baseHeightImage.getRGB(x, z));
    }

    private static Color interpolateColor(Color color1, Color color2, float weight) {
        weight = Math.abs(weight);
        float newWeight = 1.0f - weight;

        int red = (int)(newWeight * color1.getRed() + weight * color2.getRed());
        int green = (int)(newWeight * color1.getGreen() + weight * color2.getGreen());
        int blue = (int)(newWeight * color1.getBlue() + weight * color2.getBlue());

        try{
            return new Color(red, green, blue);
        } catch (Exception e){
            MiddleEarth.LOGGER.logError(e.getMessage());
            return color1;
        }
    }
}

