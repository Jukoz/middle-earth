package net.jukoz.me.utils;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.jukoz.me.MiddleEarth;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerUtil {
    public static final Logger LOGGER = LoggerFactory.getLogger(MiddleEarth.MOD_ID);
    private static LoggerUtil singleInstance = null;

    public static synchronized LoggerUtil getInstance() {
        if (singleInstance == null) singleInstance = new LoggerUtil();
        return singleInstance;
    }

    public LoggerUtil() {
    }

    public void logDebugMsg(String msg) {
        if(MiddleEarth.IS_DEBUG) LOGGER.info(msg);
    }

    public void logInfoMsg(String msg) {
        LOGGER.info(msg);
    }

    public void logError(String msg) {
        LOGGER.error(msg);
    }
}
