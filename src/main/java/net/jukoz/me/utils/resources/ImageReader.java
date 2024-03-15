package net.jukoz.me.utils.resources;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.world.MiddleEarthMapRuntime;
import net.jukoz.me.world.chunkgen.map.ImageUtils;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageReader {
    private static ImageReader single_instance = null;

    public static synchronized ImageReader getInstance()
    {
        if (single_instance == null)
            single_instance = new ImageReader(ClassLoader.getSystemClassLoader());

        return single_instance;
    }

    private ClassLoader classLoader;

    public ImageReader(ClassLoader classLoader){
        this.classLoader = classLoader;
    }

    public BufferedImage getImage(String path) {
        try{
            return ImageUtils.fetchResourceImage(classLoader, path);
        } catch (IOException e) {
            return null;
        }
    }
}
