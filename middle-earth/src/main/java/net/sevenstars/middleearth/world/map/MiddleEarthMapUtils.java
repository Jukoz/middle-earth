package net.sevenstars.middleearth.world.map;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.sevenstars.middleearth.utils.resources.FileUtils;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
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
    private MinecraftServer server;
    public static synchronized MiddleEarthMapUtils getInstance()
    {
        if (single_instance == null)
            single_instance = new MiddleEarthMapUtils();

        return single_instance;
    }
    public MiddleEarthMapUtils(){
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            this.server = server;
        });

        BufferedImage initial = FileUtils.getInstance().getResourceImage(MiddleEarthMapConfigs.INITIAL_IMAGE);
        ratioX = (float) (MiddleEarthMapConfigs.REGION_SIZE / initial.getWidth() * Math.pow(2, MiddleEarthMapConfigs.MAP_ITERATION) * MiddleEarthMapConfigs.PIXEL_WEIGHT);
        ratioZ = (float) (MiddleEarthMapConfigs.REGION_SIZE / initial.getHeight() * Math.pow(2, MiddleEarthMapConfigs.MAP_ITERATION) * MiddleEarthMapConfigs.PIXEL_WEIGHT);
        maxImageCoordinateX = (int) (initial.getWidth() * ratioX);
        maxImageCoordinateZ = (int) (initial.getHeight() * ratioZ);
    }

    public List<ServerPlayerEntity> getPlayers() {
        return server.getPlayerManager().getPlayerList();
    }
    public int getTick() {
        if(server == null) return 1;
        return server.getTicks();
    }

    public Vector2d getWorldCoordinateFromInitialMap(double x, double z){
        return new Vector2d( (x * ratioX),  (z * ratioZ));
    }

    public Vector2i getRegionByWorldCoordinate(int x, int z){
        Vector2i region = new Vector2i();
        x /= MiddleEarthMapConfigs.PIXEL_WEIGHT;
        z /= MiddleEarthMapConfigs.PIXEL_WEIGHT;
        region.x = ((x - (x % MiddleEarthMapConfigs.REGION_SIZE)) / MiddleEarthMapConfigs.REGION_SIZE);
        region.y = ((z - (z % MiddleEarthMapConfigs.REGION_SIZE)) / MiddleEarthMapConfigs.REGION_SIZE);
        return region;
    }

    public boolean isWorldCoordinateInBorder(int x, int z) {
        if(x < 0 || z < 0) return false;
        return (x < maxImageCoordinateX && z < maxImageCoordinateZ);
    }
}
