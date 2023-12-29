package net.jukoz.me.world.datas;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.world.biomes.MEBiome;
import net.jukoz.me.world.biomes.MEBiomesData;
import org.joml.Vector2i;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WorldMapDatas {
    public static final String BIOME_PATH = "./mods/me_data/biomes/i_%s";
    public static final String HEIGHT_PATH = "./mods/me_data/heights";
    public static final String IMAGE_NAME = "/%s_%s.png";

    public static final int REGION_SIZE = 3000;
    public static int ITERATION;
    public static int AMOUNT_REGION_X;
    public static int AMOUNT_REGION_Y;
    public static int MAX_X;
    public static int MAX_Y;
    static int latestRegionX;
    static int latestRegionY;
    private static BufferedImage[][] biomeRegions;
    private static BufferedImage[][] heightRegions;

    public static void initialize(int initialSizeX, int initialSizeY){
        ITERATION = 0;
        AMOUNT_REGION_X = initialSizeX;
        AMOUNT_REGION_Y = initialSizeY;

        MAX_X = REGION_SIZE * AMOUNT_REGION_X;
        MAX_Y = REGION_SIZE * AMOUNT_REGION_Y;


        biomeRegions = new BufferedImage[AMOUNT_REGION_X][AMOUNT_REGION_Y];
        heightRegions = new BufferedImage[AMOUNT_REGION_X][AMOUNT_REGION_Y];
    }

    public static boolean needWorldGeneration(BufferedImage initialImage) throws IOException {
        if(initialImage.getWidth() % REGION_SIZE != 0 || initialImage.getHeight() % REGION_SIZE != 0)
            throw new IOException("Initial image is in the wrong format.");


        if(MiddleEarth.FORCE_GENERATION){
            return true;
        }
        WorldMapDatas.initialize(initialImage.getWidth() / REGION_SIZE, initialImage.getHeight() / REGION_SIZE);
        while(ITERATION < MiddleEarth.MAP_ITERATION){
            System.out.println(ITERATION);
            WorldMapDatas.next();
            System.out.println("WorldMapDatas::needWorldGeneration > checking %s".formatted(ITERATION));
            for(int x = 0; x < AMOUNT_REGION_X; x++){
                for(int y = 0; y < AMOUNT_REGION_Y; y++){
                    if(!new File(BIOME_PATH.formatted(ITERATION) + IMAGE_NAME.formatted(x,y)).exists()){
                        System.out.println("WorldMapDatas::needWorldGeneration > Missing biome map image for %s-[%s, %s]".formatted(ITERATION,x,y));
                        return true;
                    }
                }
            }
        }
        System.out.println("WorldMapData::needWorldGeneration Guessed the size of the map to : %s".formatted(toStr()));


        return false;
    }

    public static boolean needHeightGeneration() throws IOException {
        if(MiddleEarth.FORCE_GENERATION){
            return true;
        }
        for(int x = 0; x < AMOUNT_REGION_X; x++){
            for(int y = 0; y < AMOUNT_REGION_Y; y++){
                if(!new File(HEIGHT_PATH + IMAGE_NAME.formatted(x,y)).exists()){
                    System.out.println("WorldMapDatas::needHeightGeneration > Missing height map image for [%s, %s]".formatted(x,y));
                    return true;
                }
            }
        }

        return false;
    }

    public static void next(){
        ITERATION++;
        AMOUNT_REGION_X *= 2;
        AMOUNT_REGION_Y *= 2;
        MAX_X = REGION_SIZE * AMOUNT_REGION_X;
        MAX_Y = REGION_SIZE * AMOUNT_REGION_Y;
        biomeRegions = new BufferedImage[AMOUNT_REGION_X][AMOUNT_REGION_Y];
        heightRegions = new BufferedImage[AMOUNT_REGION_X][AMOUNT_REGION_Y];
    }

    private static boolean isCoordinateInBound(int x, int y) {
        if(x < 0 || y < 0) return false;
        return (x < MAX_X && y < MAX_Y);
    }

    public static boolean isWorldCoordinateInBound(int x, int y) {
        if(x < 0 || y < 0) return false;
        return (x < MAX_X * 4 && y < MAX_Y * 4);
    }

    public static String toStr(){
        return "Iteration: [%s]  AMOUNT_REGION_X: [%s]  AMOUNT_REGION_Y: [%s]  MAX_X: [%s]  MAX_Y: [%s]".formatted(ITERATION, AMOUNT_REGION_X, AMOUNT_REGION_Y, MAX_X, MAX_Y);
    }

    public static BufferedImage getBiomeRegionByRegionCoordinates(int xRegion, int yRegion){
        if(!isCoordinateInBound(xRegion * REGION_SIZE, yRegion * REGION_SIZE)) return null;
        latestRegionX = xRegion;
        latestRegionY = yRegion;
        updateRegionFromRegionIndexes();
        return biomeRegions[latestRegionX][latestRegionY];
    }

    public static MEBiome getBiome(int xWorld, int zWorld) {
        int xMapCoordinate = Math.round((float) xWorld / 4);
        int zMapCoordinate = Math.round((float) zWorld / 4);

        if(!isCoordinateInBound(xMapCoordinate, zMapCoordinate)) return null;
        updateRegionFromWorldCoordinates(xMapCoordinate, zMapCoordinate);
        Vector2i coordinates = getRegionCoordinates(xMapCoordinate, zMapCoordinate);
        if(biomeRegions != null && biomeRegions[latestRegionX][latestRegionY] != null) {
            MEBiome biome = MEBiomesData.getBiomeByColor((biomeRegions[latestRegionX][latestRegionY].getRGB(coordinates.x, coordinates.y)));
            if(biome == null)
                System.out.println("WorldMapDatas::getBiome > Couldn't find biome.");
            return biome;
        }
        return null;
    }

    public static Color getHeight(int xWorld, int yWorld) {
        if(!isCoordinateInBound(xWorld, yWorld)) return null;
        updateRegionFromWorldCoordinates(xWorld, yWorld);
        Vector2i coordinates = getRegionCoordinates(xWorld, yWorld);
        if(heightRegions != null && heightRegions[latestRegionX][latestRegionY] != null) {
            try {
                return new Color(heightRegions[latestRegionX][latestRegionY].getRGB(coordinates.x, coordinates.y));
            }
            catch (Exception e) {
                System.out.println(latestRegionX);
                System.out.println(latestRegionY);
                System.out.println(heightRegions[latestRegionX][latestRegionY].getRGB(coordinates.x, coordinates.y));
                System.out.println(heightRegions[latestRegionX][latestRegionY]);
                return new Color(heightRegions[latestRegionX][latestRegionY].getRGB(coordinates.x, coordinates.y));
            }
        }
        return null;
    }

    public static void saveBiomeRegion(int regionX, int regionY, int iteration, BufferedImage img){
        saveRegion(BIOME_PATH.formatted(iteration), IMAGE_NAME.formatted(regionX, regionY), img);
    }
    public static void saveHeightRegion(int regionX, int regionY, BufferedImage img){
        saveRegion(HEIGHT_PATH, IMAGE_NAME.formatted(regionX, regionY), img);
    }
    private static BufferedImage saveRegion(String path, String fileName, BufferedImage img){
        try
        {
            new File(path).mkdirs();
            File f = new File(path + fileName);
            ImageIO.write(img, "png", f);
            return ImageIO.read(new File(path));
        }
        catch(Exception e)
        {
            System.out.println("Error: " + e);
            return null;
        }
    }

    public static Vector2i getRegionCoordinates(int worldCoordinateX, int worldCoordinateY){
        return  new Vector2i((worldCoordinateX % REGION_SIZE), (worldCoordinateY % REGION_SIZE));
    }

    private static boolean isUnknownRegion(){
        return biomeRegions[latestRegionX][latestRegionY] == null || heightRegions[latestRegionX][latestRegionY] == null;
    }
    // Update the latest regions from the world  coordinates
    public static void updateRegionFromWorldCoordinates(int worldCoordinateX, int worldCoordinateY) {
        Vector2i coordinates = getRegionCoordinates(worldCoordinateX, worldCoordinateY);

        final int newLatestRegionX = (int)(((double)(worldCoordinateX - coordinates.x)) / REGION_SIZE);
        final int newLatestRegionY = (int)(((double)(worldCoordinateY - coordinates.y)) / REGION_SIZE);

        if(newLatestRegionX != latestRegionX || newLatestRegionY != latestRegionY || isUnknownRegion()){
            latestRegionX = newLatestRegionX;
            latestRegionY = newLatestRegionY;

            System.out.println("[%s] - [%s]".formatted(biomeRegions[latestRegionX][latestRegionY], heightRegions[latestRegionX][latestRegionY]));
            if(isUnknownRegion()){
                updateRegionFromRegionIndexes();
                System.out.println("[%s] - [%s]".formatted(biomeRegions[latestRegionX][latestRegionY], heightRegions[latestRegionX][latestRegionY]));
            }
        }
    }

    private static void updateRegionFromRegionIndexes(){
        try{
            for(int x = 0; x < AMOUNT_REGION_X; x ++){
                for(int y = 0; y < AMOUNT_REGION_Y; y ++){
                    String path = BIOME_PATH.formatted(ITERATION) + IMAGE_NAME.formatted(x, y);
                    File biomeImage = new File(path);
                    if(biomeImage.exists() && biomeRegions[x][y] == null) {
                        biomeRegions[x][y] = ImageIO.read(biomeImage);
                        System.out.printf("WorldMapDatas.updateRegionFromRegionIndexes : Added region at [%s,%s] for <%s>\n", x, y, path);
                    }

                    path = HEIGHT_PATH + IMAGE_NAME.formatted(x, y);
                    File heightImage = new File(path);
                    if(heightImage.exists() && heightRegions[x][y] == null) {
                        heightRegions[x][y] = ImageIO.read(heightImage);
                        System.out.printf("WorldMapDatas.updateRegionFromRegionIndexes : Added region at [%s,%s] for <%s>\n", x, y, path);
                    }
                }
            }
        } catch (Exception e){
            throw new RuntimeException("IterationData.updateRegion : Catched an exception > %s".formatted(e.toString()));
        }
    }
}
