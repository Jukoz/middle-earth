package net.sevenstars.middleearth.world.map;

import net.sevenstars.middleearth.utils.resources.FileUtils;
import net.sevenstars.middleearth.MiddleEarth;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import org.joml.Vector2d;
import org.joml.Vector2i;

import java.awt.image.BufferedImage;
import java.util.List;

public class MiddleEarthMapUtils {
    private static MiddleEarthMapUtils single_instance = null;

    public final float ratioX;
    public final float ratioZ;
    private final int maxImageCoordinateX;
    private final int maxImageCoordinateZ;
    public static synchronized MiddleEarthMapUtils getInstance()
    {
        if (single_instance == null)
            single_instance = new MiddleEarthMapUtils();

        return single_instance;
    }
    public MiddleEarthMapUtils(){
        BufferedImage initial = FileUtils.getInstance().getResourceImage(MiddleEarthMapConfigs.INITIAL_IMAGE);
        int imageWidth = initial != null ? initial.getWidth() : MiddleEarthMapConfigs.REGION_SIZE;
        int imageHeight = initial != null ? initial.getHeight() : MiddleEarthMapConfigs.REGION_SIZE;
        if (initial == null) {
            MiddleEarth.LOGGER.logError(
                    "Missing map resource " + MiddleEarthMapConfigs.INITIAL_IMAGE
                            + "; using the configured map bounds as a safe fallback."
            );
        }
        double iterationScale = Math.pow(2, MiddleEarthMapConfigs.MAP_ITERATION)
                * MiddleEarthMapConfigs.PIXEL_WEIGHT;
        ratioX = (float) ((double) MiddleEarthMapConfigs.REGION_SIZE / imageWidth * iterationScale);
        ratioZ = (float) ((double) MiddleEarthMapConfigs.REGION_SIZE / imageHeight * iterationScale);
        maxImageCoordinateX = (int) (imageWidth * ratioX);
        maxImageCoordinateZ = (int) (imageHeight * ratioZ);
    }

    public List<ServerPlayer> getPlayers() {
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        if (server == null) {
            return List.of();
        }
        return server.getPlayerList().getPlayers();
    }
    public int getTick() {
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        if(server == null) return 1;
        return server.getTickCount();
    }

    public Vector2d getWorldCoordinateFromInitialMap(double x, double z){
        return new Vector2d( (x * ratioX),  (z * ratioZ));
    }

    public Vector2i getRegionByWorldCoordinate(int x, int z){
        Vector2i region = new Vector2i();
        x = Math.floorDiv(x, MiddleEarthMapConfigs.PIXEL_WEIGHT);
        z = Math.floorDiv(z, MiddleEarthMapConfigs.PIXEL_WEIGHT);
        region.x = Math.floorDiv(x, MiddleEarthMapConfigs.REGION_SIZE);
        region.y = Math.floorDiv(z, MiddleEarthMapConfigs.REGION_SIZE);
        return region;
    }

    public boolean isWorldCoordinateInBorder(int x, int z) {
        if(x < 0 || z < 0) return false;
        return (x < maxImageCoordinateX && z < maxImageCoordinateZ);
    }
}
