package net.jukoz.me.utils;

import net.jukoz.me.MiddleEarth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerUtil {
    public static final Logger LOGGER = LoggerFactory.getLogger(MiddleEarth.MOD_ID);
    private static LoggerUtil single_instance = null;

    public static synchronized LoggerUtil getInstance()
    {
        if (single_instance == null)
            single_instance = new LoggerUtil();

        return single_instance;
    }

    public void logDebugMsg(String msg){
        if(MiddleEarth.IS_DEBUG)
            LOGGER.info(msg);
    }

    public void logInfoMsg(String msg){
        LOGGER.info(msg);
    }

    public void logError(String msg){
        LOGGER.error(msg);
    }
    public void logWarn(String msg){
        LOGGER.warn(msg);
    }

}
