package net.jukoz.me.world;

import net.jukoz.me.utils.LoggerUtil;
import net.jukoz.me.utils.resources.FileUtils;
import net.jukoz.me.world.biomes.MEBiome;
import net.jukoz.me.world.biomes.MEBiomesData;
import org.joml.Vector2i;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.UUID;

public class MiddleEarthMapRegion {
    public final Vector2i coordinate;
    private final BufferedImage biomeImage;
    private final BufferedImage heightImage;

    public MiddleEarthMapRegion(Vector2i coordinate){
        this.coordinate = coordinate;
        String biomePath = MiddleEarthMapConfigs.BIOME_PATH.formatted(MiddleEarthMapConfigs.MAP_ITERATION) + MiddleEarthMapConfigs.IMAGE_NAME.formatted(coordinate.x,coordinate.y);
        String heightPath = MiddleEarthMapConfigs.HEIGHT_PATH + MiddleEarthMapConfigs.IMAGE_NAME.formatted(coordinate.x, coordinate.y);
        biomeImage = FileUtils.getInstance().getRunImage(biomePath);
        heightImage = FileUtils.getInstance().getRunImage(heightPath);

        LoggerUtil.getInstance().sendChat(biomePath);
        LoggerUtil.getInstance().sendChat(heightPath);
    }

    public MEBiome getBiome(Vector2i imageCoordinates){
        if(biomeImage != null){
            return MEBiomesData.getBiomeByColor(biomeImage.getRGB(imageCoordinates.x, imageCoordinates.y));
        }
        return MEBiomesData.defaultBiome;
    }

    public Color getHeightColor(Vector2i imageCoordinates) {
        if(heightImage != null){
            return new Color(heightImage.getRGB(imageCoordinates.x, imageCoordinates.y));
        }
        return new Color(Math.abs(MEBiomesData.defaultBiome.height), 1, 0);
    }

}
