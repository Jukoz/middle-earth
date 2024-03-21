package net.jukoz.me.world.MiddleEarthMap;

import net.jukoz.me.utils.resources.FileUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.server.network.ServerPlayerEntity;
import org.joml.Vector2i;

import java.awt.image.BufferedImage;
import java.util.List;

public class MiddleEarthMapUtils {
    private static MiddleEarthMapUtils single_instance = null;
    private MinecraftClient mc;

    private final float ratioX;
    private final float ratioZ;
    private final int maxImageCoordinateX;
    private final int maxImageCoordinateZ;
    public static synchronized MiddleEarthMapUtils getInstance()
    {
        if (single_instance == null)
            single_instance = new MiddleEarthMapUtils();

        return single_instance;
    }
    public MiddleEarthMapUtils(){
        this.mc = MinecraftClient.getInstance();
        BufferedImage initial = FileUtils.getInstance().getResourceImage(MiddleEarthMapConfigs.INITIAL_IMAGE);
        ratioX = (float) (MiddleEarthMapConfigs.REGION_SIZE / initial.getWidth() * Math.pow(2, MiddleEarthMapConfigs.MAP_ITERATION) * MiddleEarthMapConfigs.PIXEL_WEIGHT);
        ratioZ = (float) (MiddleEarthMapConfigs.REGION_SIZE / initial.getHeight() * Math.pow(2, MiddleEarthMapConfigs.MAP_ITERATION) * MiddleEarthMapConfigs.PIXEL_WEIGHT);
        maxImageCoordinateX = (int) (initial.getWidth() * ratioX);
        maxImageCoordinateZ = (int) (initial.getHeight() * ratioZ);
        //LoggerUtil.getInstance().logError("%s, %s".formatted(maxImageCoordinateX, maxImageCoordinateZ));
    }

    public List<ServerPlayerEntity> getPlayers() {
        return mc.getServer().getPlayerManager().getPlayerList();
    }
    public int getTick() {
        if(mc == null || mc.getServer() == null) return 1;
        return mc.getServer().getTicks();
    }

    public Vector2i getWorldCoordinateFromInitialMap(int x, int z){
        return new Vector2i((int) (x * ratioX), (int) (z * ratioZ));
    }

    public Vector2i getRegionByWorldCoordinate(int x, int z){
        Vector2i region = new Vector2i();
        x /= MiddleEarthMapConfigs.PIXEL_WEIGHT;
        z /= MiddleEarthMapConfigs.PIXEL_WEIGHT;
        region.x = (int)((x - (x % MiddleEarthMapConfigs.REGION_SIZE)) / MiddleEarthMapConfigs.REGION_SIZE);
        region.y = (int)((z - (z % MiddleEarthMapConfigs.REGION_SIZE)) / MiddleEarthMapConfigs.REGION_SIZE);
        return region;
    }

    public boolean isWorldCoordinateInBorder(int x, int z) {
        if(x < 0 || z < 0) return false;
        return (x < maxImageCoordinateX && z < maxImageCoordinateZ);
    }
}
