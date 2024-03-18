package net.jukoz.me.world;

import net.jukoz.me.utils.LoggerUtil;
import net.jukoz.me.world.biomes.MEBiome;
import net.jukoz.me.world.biomes.MEBiomesData;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import org.joml.Vector2i;

import java.awt.*;
import java.util.*;
import java.util.List;

public class MiddleEarthMapRuntime {
    private static MiddleEarthMapRuntime single_instance = null;
    Dictionary<Vector2i, MiddleEarthMapRegion> regions;
    Dictionary<Vector2i, MiddleEarthMapRegion> temporaryRegions;

    private LoggerUtil loggerUtil;
    private MiddleEarthMapUtils middleEarthMapUtils;
    public static synchronized MiddleEarthMapRuntime getInstance()
    {
        if (single_instance == null)
            single_instance = new MiddleEarthMapRuntime();

        return single_instance;
    }

    public MiddleEarthMapRuntime() {
        regions = new Hashtable<>();
        temporaryRegions = new Hashtable<>();
        loggerUtil = LoggerUtil.getInstance();
        middleEarthMapUtils = MiddleEarthMapUtils.getInstance();
    }

    public MEBiome getBiome(int posX, int posZ) {
        if(!middleEarthMapUtils.isWorldCoordinateInBorder(posX, posZ)) return MEBiomesData.defaultBiome;

        MiddleEarthMapRegion region = getRegionToUse(middleEarthMapUtils.getRegionByWorldCoordinate(posX, posZ));
        return region.getBiome(getImageCoordinates(posX, posZ));
    }

    public Color getHeight(int posX, int posZ) {
        if(!middleEarthMapUtils.isWorldCoordinateInBorder(posX, posZ)) return null;

        MiddleEarthMapRegion region = getRegionToUse(middleEarthMapUtils.getRegionByWorldCoordinate(posX, posZ));
        return region.getHeightColor(getImageCoordinates(posX, posZ));
    }

    private Vector2i getImageCoordinates(int posX, int posZ){
        return new Vector2i(
            (int)((float)posX / MiddleEarthMapConfigs.PIXEL_WEIGHT % MiddleEarthMapConfigs.REGION_SIZE),
            (int)((float)posZ / MiddleEarthMapConfigs.PIXEL_WEIGHT % MiddleEarthMapConfigs.REGION_SIZE)
        );
    }

    private MiddleEarthMapRegion getRegionToUse(Vector2i regionCoordinate){
        refreshRegions();

        if(regions.get(regionCoordinate) != null) {
            return regions.get(regionCoordinate);
        } else if(temporaryRegions.get(regionCoordinate) != null){
            return temporaryRegions.get(regionCoordinate);
        }
        return temporaryRegions.put(regionCoordinate, new MiddleEarthMapRegion(regionCoordinate));
    }


    private void refreshRegions() {
        List<ServerPlayerEntity> players = middleEarthMapUtils.getPlayers();

        List<BlockPos> playerPosList = new ArrayList<>();
        for(ServerPlayerEntity playerEntity : players){
            playerPosList.add(playerEntity.getBlockPos());
        }


        for(BlockPos playerPos : playerPosList){
            Vector2i regionCoord = middleEarthMapUtils.getRegionByWorldCoordinate(playerPos.getX(), playerPos.getZ());
            if(regions.get(regionCoord) == null){
                regions.put(regionCoord, new MiddleEarthMapRegion(regionCoord));
                loggerUtil.sendChat("PLayer region : [%s, %s]".formatted(regionCoord.x, regionCoord.y));
            }
        }
    }



}
