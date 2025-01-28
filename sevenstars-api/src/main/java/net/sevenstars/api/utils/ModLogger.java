package net.sevenstars.api.utils;

import net.sevenstars.api.SevenStarsApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModLogger {
    protected static Logger LOGGER = null;
    protected static ModLogger singleInstance = null;
    protected static boolean isDebug;

    public ModLogger(boolean debug, String prefix){
        LOGGER = LoggerFactory.getLogger(prefix);
        isDebug = debug;
    }

    protected static synchronized ModLogger updateInstance() {
        if (singleInstance == null) singleInstance = new ModLogger(SevenStarsApi.IS_DEBUG, SevenStarsApi.MOD_ID);
        return singleInstance;
    }


    public static void logDebugMsg(String msg) {
        if(SevenStarsApi.IS_DEBUG){
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

    public static void logTrace(String msg) {
        updateInstance();
        LOGGER.trace(msg);
    }

    public static void logWarn(String msg) {
        updateInstance();
        LOGGER.warn(msg);
    }
}
