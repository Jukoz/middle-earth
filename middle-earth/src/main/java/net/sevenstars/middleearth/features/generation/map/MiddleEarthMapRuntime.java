package net.sevenstars.middleearth.features.generation.map;

import net.minecraft.server.network.ServerPlayerEntity;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.utils.resources.FileUtils;
import net.sevenstars.middleearth.features.generation.biomes.surface.MapBasedBiomePool;
import net.sevenstars.middleearth.features.generation.biomes.surface.MapBasedCustomBiome;
import org.joml.Vector2i;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class MiddleEarthMapRuntime {
    Map<Vector2i, MiddleEarthMapRegion> regions;
    HashMap<UUID, Vector2i> regionByUuids;


    private static MiddleEarthMapRuntime single_instance = null;

    private BufferedImage edgeImage;
    private MiddleEarthMapUtils middleEarthMapUtils;

    private volatile int latestValidationTick = 0;
    private final AtomicInteger currentValidationBlockCount = new AtomicInteger();
    private final AtomicBoolean purging = new AtomicBoolean();

    public static synchronized MiddleEarthMapRuntime getInstance()
    {
        if (single_instance == null)
            single_instance = new MiddleEarthMapRuntime();

        return single_instance;
    }

    public MiddleEarthMapRuntime() {
        // filled from the world generation threads
        regions = new ConcurrentHashMap<>();
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

    public MapBasedCustomBiome getBiome(int posX, int posZ) {
        if(!middleEarthMapUtils.isWorldCoordinateInBorder(posX, posZ)) return MapBasedBiomePool.defaultBiome;

        MiddleEarthMapRegion region = getRegionToUse(middleEarthMapUtils.getRegionByWorldCoordinate(posX, posZ));
        if(region == null) return MapBasedBiomePool.defaultBiome;

        return region.getBiome(imageCoordinate(posX), imageCoordinate(posZ));
    }

    private static final int OUT_OF_BORDER_RGB = new Color(35, 48, 55).getRGB();
    public Color getHeight(int posX, int posZ) {
        return new Color(getHeightRgb(posX, posZ));
    }

    public int getHeightRgb(int posX, int posZ) {
        if(!middleEarthMapUtils.isWorldCoordinateInBorder(posX, posZ)){
            return OUT_OF_BORDER_RGB;
        }

        MiddleEarthMapRegion region = getRegionToUse(middleEarthMapUtils.getRegionByWorldCoordinate(posX, posZ));
        if(region == null) return OUT_OF_BORDER_RGB;

        return region.getHeightRgb(imageCoordinate(posX), imageCoordinate(posZ));
    }

    public float getEdge(int posX, int posZ) {
        posX /= middleEarthMapUtils.ratioX;
        posZ /= middleEarthMapUtils.ratioZ;
        if(!middleEarthMapUtils.isWorldCoordinateInBorder(posX, posZ)) return -0.67f;

        if(edgeImage == null) return 1.001f;

        int edgeRgb = edgeImage.getRGB(posX, posZ);

        float average = (float)(((edgeRgb >> 16) & 0xFF) + ((edgeRgb >> 8) & 0xFF) + (edgeRgb & 0xFF)) / 3;

        if(average > 0.01f) {
            average *= 12;
        }

        return Math.max(0, 1 - (average / 255));
    }

    private static int imageCoordinate(int pos){
        return (int)((float)pos / MiddleEarthMapConfigs.PIXEL_WEIGHT % MiddleEarthMapConfigs.REGION_SIZE);
    }

    private MiddleEarthMapRegion getRegionToUse(Vector2i regionCoordinate){
        purgeRegions();

        return regions.computeIfAbsent(regionCoordinate, MiddleEarthMapRegion::new);
    }

    private void purgeRegions() {
        // Block delay
        if(currentValidationBlockCount.incrementAndGet() < MiddleEarthMapConfigs.BIOME_VALIDATION_BLOCK_DELAY) return;
        currentValidationBlockCount.set(0);

        // Tick delay
        int serverTick = middleEarthMapUtils.getTick();
        if(serverTick - latestValidationTick < MiddleEarthMapConfigs.BIOME_VALIDATION_TICK_DELAY) return;
        // several world generation threads can get here at once, only one of them purges
        if(!purging.compareAndSet(false, true)) return;

        try{
            latestValidationTick = serverTick;

            // Create purge array
            List<Vector2i> toPurge = new ArrayList<>();
            List<Vector2i> playerCoordinates = new ArrayList<>();
            // the player list belongs to the server thread, so copy it before reading
            for(ServerPlayerEntity player : new ArrayList<>(middleEarthMapUtils.getPlayers())){
                if(player == null) continue;
                playerCoordinates.add(new Vector2i(player.getBlockX(), player.getBlockZ()));
            }

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
            //MiddleEarth.LOGGER.logDebugMsg("Purging [%s/%s] regions (tick : %s)".formatted(toPurge.size(), regions.size(), serverTick));
            for (Vector2i region : toPurge){
                regions.remove(region);
            }
        } catch(Exception exception){
            MiddleEarth.LOGGER.logError("%s : %s".formatted(toString(), exception.getMessage()));
        } finally {
            purging.set(false);
        }
    }
}
