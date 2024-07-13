package net.jukoz.me.utils;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.jukoz.me.MiddleEarth;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(MiddleEarth.MOD_ID);
    private static LoggerUtil singleInstance = null;

    private static synchronized LoggerUtil updateInstance() {
        if (singleInstance == null) singleInstance = new LoggerUtil();
        return singleInstance;
    }

    public LoggerUtil() {
    }

    public static void logDebugMsg(String msg) {
        if(MiddleEarth.IS_DEBUG){
            updateInstance();
            LOGGER.info(msg);
        }
    }

    public static void logInfoMsg(String msg) {
        updateInstance();
        LOGGER.info(msg);
    }

    public static void logError(String msg) {
        updateInstance();
        LOGGER.error(msg);
    }
    public static void logError(String msg, Exception e) {
        updateInstance();
        LOGGER.error(msg, e);
    }
}
