package net.jukoz.me.world.datas;

import com.google.common.base.Stopwatch;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.world.biomes.surface.MEBiome;
import net.jukoz.me.world.biomes.surface.MEBiomesData;
import net.jukoz.me.world.chunkgen.map.ImageUtils;
import org.joml.Vector2i;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MiddleEarthMapDatas {
    public static final String BIOME_PATH = "data/me/biomes/i_%s";
    public static final String HEIGHT_PATH = "data/me/heights";
    public static final String IMAGE_NAME = "/%s_%s.png";
    public static final int REGION_SIZE = 3000;
    public static final int PIXEL_WEIGHT = 4;
    public int iteration;
    public int xRegionAmount;
    public int zRegionAmount;

    public int xMaxImageCoordinate;
    public int zMaxImageCoordinate;

    private BufferedImage[][][] biomeRegions;
    private BufferedImage[][] heightRegions;
    private ClassLoader classLoader;
    private final int threadPoolSize = 12;
    public MiddleEarthMapDatas(ClassLoader classLoader_) throws Exception {
        Stopwatch mainStopwatch = Stopwatch.createStarted();
        Stopwatch stopwatch = Stopwatch.createStarted();

        iteration = 0;
        this.classLoader = classLoader_;

        BufferedImage initialMapImage = ImageUtils.fetchResourceImage(classLoader,"assets/" + MiddleEarth.MOD_ID + "/textures/map.png");
        if(initialMapImage.getWidth() % REGION_SIZE != 0 || initialMapImage.getHeight() % REGION_SIZE != 0){
            throw new Exception("WorldMapDatas::Map image has an incorrect size [%s, %s]".formatted(initialMapImage.getWidth(), initialMapImage.getHeight()));
        }
        xRegionAmount = initialMapImage.getWidth() / REGION_SIZE;
        zRegionAmount = initialMapImage.getHeight() / REGION_SIZE;

        xMaxImageCoordinate = REGION_SIZE * xRegionAmount;
        zMaxImageCoordinate = REGION_SIZE * zRegionAmount;

        biomeRegions = new BufferedImage[MiddleEarth.MAP_ITERATION + 1][xRegionAmount][zRegionAmount];
        heightRegions = new BufferedImage[xRegionAmount][zRegionAmount];

        if(verifyIfBiomeGenerationNeeded() || MiddleEarth.FORCE_GENERATION){
            System.out.println("MiddleEarthMapDatas::Need biome generation");
            stopwatch.reset().start();
            createInitialData(initialMapImage);
            createBiomeDatas();
            System.out.println("It took %s ms to create the middle-earth height datas".formatted(stopwatch.elapsed(TimeUnit.MILLISECONDS)));
        }
        if(verifyIfHeightGenerationNeeded() || MiddleEarth.FORCE_GENERATION){
            System.out.println("MiddleEarthMapDatas::Need height generation");
            stopwatch.reset().start();
            createHeightDatas();
            System.out.println("It took %s ms to create the middle-earth height datas".formatted(stopwatch.elapsed(TimeUnit.MILLISECONDS)));
        }

        System.out.println("It took %s ms to create the middle-earth map datas".formatted(mainStopwatch.elapsed(TimeUnit.MILLISECONDS)));
        System.out.println(this);
    }

    private void createInitialData(BufferedImage initialMapImage) throws Exception {
        System.out.println("MiddleEarthMapDatas::createInitialData [%s][%s]".formatted(xRegionAmount, zRegionAmount));
        if(xRegionAmount > 1 || zRegionAmount > 1){
            // Todo : Unimplemented
            System.out.println("MiddleEarthMapDatas::Not implemented - createInitialData");
        } else {
            biomeRegions[iteration][0][0] = initialMapImage;
            saveRegions(biomeRegions[iteration], BIOME_PATH.formatted(iteration));
        }
    }

    private void createBiomeDatas() throws Exception {
        while(iteration < MiddleEarth.MAP_ITERATION){
            ExecutorService executorService = Executors.newFixedThreadPool(threadPoolSize);
            applyNext();
            for(int xOld = 0; xOld < biomeRegions[iteration-1].length; xOld++){
                for(int zOld = 0; zOld < biomeRegions[iteration-1][0].length; zOld++){
                    int finalX = xOld;
                    int finalZ = zOld;

                    executorService.submit(() -> {
                        BufferedImage[][] subidivedRegions = ImageUtils.subdivide(biomeRegions[iteration-1][finalX][finalZ]);
                        for(int i = 0; i < subidivedRegions.length; i++){
                            for(int j = 0; j < subidivedRegions[0].length; j ++){
                                biomeRegions[iteration][(finalX * 2) + i][(finalZ * 2) + j] = subidivedRegions[i][j];
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
                System.out.println("MiddleEarthMapDatas::Didn't complete generating biomes");
            }
            saveRegions(biomeRegions[iteration], BIOME_PATH.formatted(iteration));
        }
    }

    private void createHeightDatas() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(threadPoolSize);

        for (int x = 0; x < xRegionAmount; x++) {
            for (int z = 0; z < zRegionAmount; z++) {
                final BufferedImage buffImage = getBiomeImageByRegion(iteration, x, z);
                if(buffImage != null){
                    int finalX = x;
                    int finalZ = z;
                    executorService.submit(() -> {
                        heightRegions[finalX][finalZ] = ImageUtils.blur(processHeightRegion(buffImage, MiddleEarthMapDatas.REGION_SIZE));
                    });
                }
            }
        }
        // Shutdown the executor and wait for all threads to finish
        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (Exception e) {
            System.out.println("MiddleEarthMapDatas::Didn't complete generating heights");
        }
        saveRegions(heightRegions, HEIGHT_PATH);

    }

    private static BufferedImage processHeightRegion(BufferedImage buffImage, int size) {
        BufferedImage newHeightRegion = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        for (int rI = 0; rI < 2; rI++) {
            for (int rJ = 0; rJ < 2; rJ++) {
                for (int x = 0; x < size; x++) {
                    for (int y = 0; y < size; y++) {
                        int color = buffImage.getRGB(x, y);
                        try {
                            int height = MEBiomesData.getBiomeByColor(color).height;
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

    private void saveRegions(BufferedImage[][] regions, String path) throws Exception {
        for(int x = 0; x < regions.length; x ++){
            for(int y = 0; y < regions[0].length; y ++){
                ImageUtils.saveImage(regions[x][y], path, IMAGE_NAME.formatted(x, y));
            }
        }
    }

    public int getIteration(){
        return iteration;
    }

    public Vector2i getRegionByWorldCoordinate(int iter, int xWorld, int zWorld){
        Vector2i region = new Vector2i();
        region.x = (xWorld - (xWorld % REGION_SIZE)) / REGION_SIZE;
        region.y = (zWorld - (zWorld % REGION_SIZE)) / REGION_SIZE;
        return region;
    }

    private BufferedImage getBiomeImageByCoordinate(int iter, int xWorld, int zWorld){
        if(!isWorldCoordinateInBound(xWorld, zWorld)) return null;

        Vector2i region = getRegionByWorldCoordinate(iter, xWorld, zWorld);
        return getBiomeImageByRegion(iter, region.x, region.y);
    }

    private BufferedImage getHeightImageByCoordinate(int xWorld, int zWorld){
        if(!isWorldCoordinateInBound(xWorld, zWorld)) return null;

        Vector2i region = getRegionByWorldCoordinate(iteration, xWorld, zWorld);
        return getHeightImageByRegion(region.x, region.y);
    }

    private BufferedImage getBiomeImageByRegion(int iter, int xRegion, int zRegion){
        if(xRegion >= biomeRegions[iter].length || zRegion >= biomeRegions[iter][0].length) return null;
        return biomeRegions[iter][xRegion][zRegion];
    }

    private BufferedImage getHeightImageByRegion(int xRegion, int zRegion){
        if(xRegion >= heightRegions.length || zRegion >= heightRegions[0].length) return null;
        return heightRegions[xRegion][zRegion];
    }

    public boolean verifyIfBiomeGenerationNeeded() {
        int xNewRegionAmount = xRegionAmount;
        int zNewRegionAmount = zRegionAmount;
        int xNewMaxImageCoordinates = xMaxImageCoordinate;
        int zNewMaxImageCoordinates = zMaxImageCoordinate;
        try{
            for(int i = 0; i < biomeRegions.length; i ++){
                biomeRegions[i] = new BufferedImage[xNewRegionAmount][zNewRegionAmount];

                for(int x = 0; x < xNewRegionAmount; x ++){
                    for(int y = 0; y < zNewRegionAmount; y ++){
                        String path = BIOME_PATH.formatted(i) + IMAGE_NAME.formatted(x,y);
                        BufferedImage img = ImageUtils.fetchRunImage(path);
                        if(img == null){
                            System.out.println("MiddleEarthMapDatas::Unknown biome at <%s>".formatted(path));
                            return true;
                        } else {
                            biomeRegions[i][x][y] = img;
                        }
                    }
                }
                if(i < biomeRegions.length - 1){
                    // Simulate next
                    xNewRegionAmount *= 2;
                    zNewRegionAmount *= 2;
                    xNewMaxImageCoordinates *= 2;
                    zNewMaxImageCoordinates *= 2;
                }
            }
        } catch(Exception e){
            System.out.println("MiddleEarthMapDatas");
            System.out.println(e.toString());
            return true;
        }

        iteration = biomeRegions.length - 1;
        xRegionAmount = xNewRegionAmount;
        zRegionAmount = zNewRegionAmount;
        xMaxImageCoordinate = xNewMaxImageCoordinates;
        zMaxImageCoordinate = zNewMaxImageCoordinates;

        return false;
    }

    public boolean verifyIfHeightGenerationNeeded(){
        heightRegions = new BufferedImage[xRegionAmount][zRegionAmount];
        try{
            for(int x = 0; x < xRegionAmount; x ++){
                for(int y = 0; y < xRegionAmount; y ++){
                    BufferedImage img = ImageUtils.fetchRunImage(HEIGHT_PATH + IMAGE_NAME.formatted(x,y));
                    if(img == null){
                        return true;
                    } else {
                        heightRegions[x][y] = img;
                    }
                }
            }
        } catch(Exception e){
            return true;
        }
        return false;
    }

    private void applyNext(){
        iteration ++;
        xRegionAmount *= 2;
        zRegionAmount *= 2;
        xMaxImageCoordinate *= 2;
        zMaxImageCoordinate *= 2;

        biomeRegions[iteration] = new BufferedImage[xRegionAmount][zRegionAmount];
        heightRegions = new BufferedImage[xRegionAmount][zRegionAmount];
    }

    private boolean isCoordinateInBound(int x, int z) {
        if(x < 0 || z < 0) return false;
        return (x < xMaxImageCoordinate && z < zMaxImageCoordinate);
    }

    public boolean isWorldCoordinateInBound(int x, int z) {
        if(x < 0 || z < 0) return false;
        return (x < xMaxImageCoordinate * PIXEL_WEIGHT && z < zMaxImageCoordinate * PIXEL_WEIGHT);
    }

    @Override
    public String toString(){
        return "Iteration: [%s]  AMOUNT_REGION_X: [%s]  AMOUNT_REGION_Y: [%s]  MAX_X: [%s]  MAX_Z: [%s]".formatted(iteration, xRegionAmount, zRegionAmount, xMaxImageCoordinate, zMaxImageCoordinate);
    }

    public Vector2i getWorldCoordinateFromImage(int xImage, int zImage) {
        if(!isCoordinateInBound(xImage, zImage)) return null;
        Vector2i coordinates = new Vector2i(0,0);

        int initialWidth = biomeRegions[0].length * REGION_SIZE;
        int initialHeight = biomeRegions[0][0].length * REGION_SIZE;

        float xK = (1.f / initialWidth * xImage);
        float zK = (1.f / initialHeight * zImage);

        coordinates.x = (int)(xMaxImageCoordinate * xK) * PIXEL_WEIGHT;
        coordinates.y = (int)(zMaxImageCoordinate * zK) * PIXEL_WEIGHT;

        return coordinates;
    }

    public MEBiome getBiomeFromWorldCoordinate(int i, int xWorld, int zWorld) {
        if(!isWorldCoordinateInBound(xWorld, zWorld)) return null;

        int xMapCoordinate = Math.round((float) xWorld / PIXEL_WEIGHT);
        int zMapCoordinate = Math.round((float) zWorld / PIXEL_WEIGHT);

        BufferedImage biomeRegion = getBiomeImageByCoordinate(i, xMapCoordinate, zMapCoordinate);
        if(biomeRegion == null){
            return MEBiomesData.getBiomeById((short)0);
        }
        Vector2i coord = getRegionCoordinates(xMapCoordinate, zMapCoordinate);
        return MEBiomesData.getBiomeByColor(biomeRegion.getRGB(coord.x, coord.y));
    }

    public Color getHeightFromWorldCoordinates(int xWorld, int zWorld) {
        if(!isWorldCoordinateInBound(xWorld, zWorld)) return null;

        int xMapCoordinate = (int)Math.floor((double) xWorld / PIXEL_WEIGHT);
        int zMapCoordinate = (int)Math.floor((double) zWorld / PIXEL_WEIGHT);

        BufferedImage heightRegion = getHeightImageByCoordinate(xMapCoordinate, zMapCoordinate);

        if(heightRegion == null){
            return new Color(Math.abs(MEBiomesData.getBiomeById((short)0).height), 1, 0);
        }

        Vector2i coord = getRegionCoordinates(xMapCoordinate, zMapCoordinate);
        return new Color(heightRegion.getRGB(coord.x, coord.y));
    }

    public static Vector2i getRegionCoordinates(int worldCoordinateX, int worldCoordinateY){
        return  new Vector2i((worldCoordinateX % REGION_SIZE), (worldCoordinateY % REGION_SIZE));
    }
}
