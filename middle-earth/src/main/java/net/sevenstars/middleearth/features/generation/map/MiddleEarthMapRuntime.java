package net.sevenstars.middleearth.features.generation.map;

import net.minecraft.server.network.ServerPlayerEntity;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.utils.resources.FileUtils;
import net.sevenstars.middleearth.features.generation.biomes.surface.MapBasedBiomePool;
import net.sevenstars.middleearth.features.generation.biomes.surface.MapBasedCustomBiome;
import net.sevenstars.middleearth.features.generation.chunkgen.map.ImageUtils;
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
    private float[] depths; // blocks, one per pixel of the depth map
    private int depthSize;
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
        BufferedImage depthImage = FileUtils.getInstance().getRunImage(MiddleEarthMapConfigs.BASE_HEIGHT_PATH + MiddleEarthMapConfigs.BASE_DEPTH_IMAGE_NAME);
        if(depthImage != null) {
            depthSize = depthImage.getWidth();
            depths = new float[depthSize * depthImage.getHeight()];
            for(int z = 0; z < depthImage.getHeight(); z++) {
                for(int x = 0; x < depthSize; x++) depths[z * depthSize + x] = (depthImage.getRGB(x, z) & 0xFF) / 255f * ImageUtils.MAX_DEPTH;
            }
        }
    }

    // Blocks the bottom of the world lies below y -64: bilinear between the pixels of the depth map, 0 outside the map
    public float getDepth(int posX, int posZ) {
        if(depths == null || !middleEarthMapUtils.isWorldCoordinateInBorder(posX, posZ)) return 0;
        float scale = middleEarthMapUtils.ratioX * MiddleEarthMapConfigs.DEPTH_STEP;
        float x = posX / scale - 0.5f;
        float z = posZ / scale - 0.5f;
        int x0 = (int) Math.floor(x);
        int z0 = (int) Math.floor(z);
        float fx = x - x0;
        float fz = z - z0;
        float top = depthAt(x0, z0) * (1 - fx) + depthAt(x0 + 1, z0) * fx;
        float bottom = depthAt(x0, z0 + 1) * (1 - fx) + depthAt(x0 + 1, z0 + 1) * fx;
        return top * (1 - fz) + bottom * fz;
    }

    private float depthAt(int x, int z) {
        int rows = depths.length / depthSize;
        return depths[Math.max(0, Math.min(rows - 1, z)) * depthSize + Math.max(0, Math.min(depthSize - 1, x))];
    }

    public MapBasedCustomBiome getBiome(int posX, int posZ) {
        if(!middleEarthMapUtils.isWorldCoordinateInBorder(posX, posZ)) return MapBasedBiomePool.defaultBiome;

        MiddleEarthMapRegion region = regionAt(posX, posZ);
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

        MiddleEarthMapRegion region = regionAt(posX, posZ);
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

    // The last region a thread used, so a lookup in the same region allocates no key. It is dropped whenever a purge removed
    // regions, so a purged region is not kept alive by a thread.
    private static final class LastRegion {
        int x = Integer.MIN_VALUE;
        int z;
        int epoch;
        MiddleEarthMapRegion region;
    }

    private static final ThreadLocal<LastRegion> LAST_REGION = ThreadLocal.withInitial(LastRegion::new);
    private volatile int purgeEpoch;

    private MiddleEarthMapRegion regionAt(int posX, int posZ) {
        // the same cells as getRegionByWorldCoordinate: truncating division, twice
        int regionX = posX / MiddleEarthMapConfigs.PIXEL_WEIGHT / MiddleEarthMapConfigs.REGION_SIZE;
        int regionZ = posZ / MiddleEarthMapConfigs.PIXEL_WEIGHT / MiddleEarthMapConfigs.REGION_SIZE;
        purgeRegions();
        LastRegion last = LAST_REGION.get();
        int epoch = purgeEpoch;
        if (last.region != null && last.x == regionX && last.z == regionZ && last.epoch == epoch) return last.region;
        MiddleEarthMapRegion region = regions.computeIfAbsent(new Vector2i(regionX, regionZ), MiddleEarthMapRegion::new);
        last.x = regionX;
        last.z = regionZ;
        last.epoch = epoch;
        last.region = region;
        return region;
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
            if (!toPurge.isEmpty()) purgeEpoch++;
        } catch(Exception exception){
            MiddleEarth.LOGGER.logError("%s : %s".formatted(toString(), exception.getMessage()));
        } finally {
            purging.set(false);
        }
    }
}
