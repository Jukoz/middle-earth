package net.jukoz.me.utils.resources;

import net.jukoz.me.utils.LoggerUtil;
import net.jukoz.me.world.biomes.surface.MEBiomesData;
import net.jukoz.me.world.chunkgen.map.ImageUtils;
import net.jukoz.me.world.map.MiddleEarthMapConfigs;
import org.joml.Vector2i;
import org.joml.sampling.Convolution;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

public class FileUtils {

    private static FileUtils single_instance = null;

    public static synchronized FileUtils getInstance()
    {
        if (single_instance == null)
            single_instance = new FileUtils(ClassLoader.getSystemClassLoader());

        return single_instance;
    }

    private ClassLoader classLoader;

    public FileUtils(ClassLoader classLoader){
        this.classLoader = classLoader;
    }

    public BufferedImage getResourceImage(String path) {
        try{
            return ImageUtils.fetchResourceImage(getClass().getClassLoader(), path);
        } catch (IOException e) {
            return null;
        }
    }

    public BufferedImage getRunImage(String path) {
        try{
            File f = new File(path);
            if(!f.exists())
                return null;
            return ImageIO.read(f);
        } catch (IOException e) {
            return null;
        }
    }

    private static final Vector2i[] directions = {new Vector2i(-1, 1), new Vector2i(0, 1), new Vector2i(1, 1),
                                            new Vector2i(-1, 0), new Vector2i(1, 0),
                                            new Vector2i(-1, -1), new Vector2i(0, -1), new Vector2i(1, -1)};
    public BufferedImage getRunImageWithBorders(int x, int y, int padding) {
        String basePath = MiddleEarthMapConfigs.BIOME_PATH.formatted(MiddleEarthMapConfigs.MAP_ITERATION);
        String centerPath = basePath + MiddleEarthMapConfigs.IMAGE_NAME.formatted(x, y);
        BufferedImage centerImage = getRunImage(centerPath);
        if(centerImage == null) return null;

        int width = centerImage.getWidth();
        int height = centerImage.getHeight();

        BufferedImage imageWithBorders = new BufferedImage(width + 2*padding, height + 2*padding, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = imageWithBorders.createGraphics();

        graphics.setColor(MEBiomesData.defaultBiome.color);
        graphics.fillRect(0, 0, width + 2*padding, height + 2*padding);
        graphics.drawImage(centerImage, padding, padding, null);

        for(Vector2i direction : directions) {
            String edgePath = basePath + MiddleEarthMapConfigs.IMAGE_NAME.formatted(x + direction.x, y + direction.y);
            BufferedImage edgeImage = getRunImage(edgePath);
            if(edgeImage != null) {
                graphics.drawImage(edgeImage, padding + (width * direction.x), padding + (height * direction.y), null);
            }
        }
        graphics.dispose();

        return imageWithBorders;
    }

    public void saveImage(BufferedImage bufferedImage, String path, String fileName, FileType fileType) {
        try{
            new File(path).mkdirs();
            File f = new File(path + fileName);
            ImageIO.write(bufferedImage, fileType.extension, f);
        } catch(Exception e){
            LoggerUtil.logError("Image Utils couldn't save image for {0}.".formatted(path + fileName));
        }
    }
}
