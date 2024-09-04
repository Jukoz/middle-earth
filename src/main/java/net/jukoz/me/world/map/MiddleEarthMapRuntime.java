package net.jukoz.me.world.map;

import net.jukoz.me.utils.LoggerUtil;
import net.jukoz.me.utils.resources.FileUtils;
import net.jukoz.me.world.biomes.surface.MEBiome;
import net.jukoz.me.world.biomes.surface.MEBiomesData;
import net.minecraft.server.network.ServerPlayerEntity;
import org.joml.Vector2i;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

public class MiddleEarthMapRuntime {
    private static MiddleEarthMapRuntime single_instance = null;
    HashMap<Vector2i, MiddleEarthMapRegion> regions;
    HashMap<UUID, Vector2i> regionByUuids;
    private BufferedImage edgeImage;
    private LoggerUtil loggerUtil;
    private MiddleEarthMapUtils middleEarthMapUtils;

    private int latestValidationTick = 0;
    private int currentValidationBlockCount = 0;

    public static synchronized MiddleEarthMapRuntime getInstance()
    {
        if (single_instance == null)
            single_instance = new MiddleEarthMapRuntime();

        return single_instance;
    }

    public MiddleEarthMapRuntime() {
        regions = new HashMap<>();
        regionByUuids = new HashMap<>();
        edgeImage = MiddleEarthMapGeneration.getEdgeHeightImage();
        if(edgeImage == null) {
            String path = MiddleEarthMapConfigs.BASE_HEIGHT_PATH + MiddleEarthMapConfigs.BASE_EDGE_IMAGE_NAME;
            BufferedImage image = FileUtils.getInstance().getRunImage(path);
            if(image != null){
                edgeImage = image;
            }
        }
        middleEarthMapUtils = MiddleEarthMapUtils.getInstance();
    }

    public MEBiome getBiome(int posX, int posZ) {
        if(!middleEarthMapUtils.isWorldCoordinateInBorder(posX, posZ)) return MEBiomesData.defaultBiome;

        MiddleEarthMapRegion region = getRegionToUse(middleEarthMapUtils.getRegionByWorldCoordinate(posX, posZ));
        if(region == null) return MEBiomesData.defaultBiome;

        return region.getBiome(getImageCoordinates(posX, posZ));
    }

    public Color getHeight(int posX, int posZ) {
        if(!middleEarthMapUtils.isWorldCoordinateInBorder(posX, posZ)) return null;

        MiddleEarthMapRegion region = getRegionToUse(middleEarthMapUtils.getRegionByWorldCoordinate(posX, posZ));
        if(region == null) return new Color(0);

        Vector2i coords = getImageCoordinates(posX, posZ);
        return region.getHeightColor(coords);
    }

    public float getEdge(int posX, int posZ) {
        posX /= middleEarthMapUtils.ratioX;
        posZ /= middleEarthMapUtils.ratioZ;
        if(!middleEarthMapUtils.isWorldCoordinateInBorder(posX, posZ)) return -0.67f;

        if(edgeImage == null) return 1.001f;

        Color edgeColor = new Color(edgeImage.getRGB(posX, posZ));

        float average = (float)(edgeColor.getRed() + edgeColor.getGreen() +  edgeColor.getBlue()) / 3;

        if(average > 0.01f) {
            average *= 12;
        }

        return Math.max(0, 1 - (average / 255));
    }

    private Vector2i getImageCoordinates(int posX, int posZ){
        return new Vector2i(
            (int)((float)posX / MiddleEarthMapConfigs.PIXEL_WEIGHT % MiddleEarthMapConfigs.REGION_SIZE),
            (int)((float)posZ / MiddleEarthMapConfigs.PIXEL_WEIGHT % MiddleEarthMapConfigs.REGION_SIZE)
        );
    }

    private MiddleEarthMapRegion getRegionToUse(Vector2i regionCoordinate){
        purgeRegions();

        if(regions.get(regionCoordinate) != null) {
            return regions.get(regionCoordinate);
        }
        return regions.put(regionCoordinate, new MiddleEarthMapRegion(regionCoordinate));
    }

    private void purgeRegions() {
        // Block delay
        currentValidationBlockCount ++;
        if(currentValidationBlockCount < MiddleEarthMapConfigs.BIOME_VALIDATION_BLOCK_DELAY) return;
        currentValidationBlockCount = 0;

        // Tick delay
        int serverTick = middleEarthMapUtils.getTick();
        if(serverTick - latestValidationTick < MiddleEarthMapConfigs.BIOME_VALIDATION_TICK_DELAY) return;
        latestValidationTick = serverTick;

        // Create purge array
        List<Vector2i> toPurge = new ArrayList<>();
        List<Vector2i> playerCoordinates = new ArrayList<>();
        for(ServerPlayerEntity player : middleEarthMapUtils.getPlayers()){
            playerCoordinates.add(new Vector2i(player.getBlockX(), player.getBlockZ()));
        }

        try{
            middleEarthMapUtils.getPlayers();
            regions.forEach((key, value) -> {
                boolean hasPlayerInRange = false;
                for(Vector2i coordinate : playerCoordinates){
                    if(value.isInRange(coordinate)){
                        hasPlayerInRange = true;
                        break;
                    }
                }
                if(!hasPlayerInRange)
                    toPurge.add(key);
            });

            // Purging
            //loggerUtil.logDebugMsg("Purging [%s] regions (tick : %s)".formatted(toPurge.size(), serverTick));
            for (Vector2i region : toPurge){
                regions.remove(region);
            }
        } catch(Exception exception){
            loggerUtil.logError("%s : %s".formatted(toString(), exception.getMessage()));
        }
    }
}
