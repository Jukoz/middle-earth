package net.jukoz.me.world;

import com.google.common.base.Stopwatch;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.utils.LoggerUtil;
import net.jukoz.me.utils.resources.ImageReader;

import java.awt.image.BufferedImage;
import java.util.logging.Logger;

public class MiddleEarthMapGeneration {
    private ImageReader imageReader;
    private LoggerUtil loggerUtil;
    public MiddleEarthMapGeneration() throws Exception {
        Stopwatch mainStopwatch = Stopwatch.createStarted();
        imageReader = ImageReader.getInstance();
        loggerUtil = LoggerUtil.getInstance();


        loggerUtil.logInfoMsg("Verify ORIGINAL image size;");
        BufferedImage initialImage = imageReader.getImage("assets/" + MiddleEarth.MOD_ID + "/textures/map.png");
        if(initialImage == null){
            loggerUtil.logError("Initial map image couldn't be found!");
            return;
        }
        loggerUtil.logError("Initial map image couldn't be found!");

        loggerUtil.logInfoMsg("Verify BIOME generation availability;");
        loggerUtil.logInfoMsg("Begin BIOME generation;");

        loggerUtil.logInfoMsg("Verify HEIGHT generation availability;");
        loggerUtil.logInfoMsg("Begin HEIGHT generation;");
    }
}

