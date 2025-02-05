package net.sevenstars.api.utils;

import net.sevenstars.api.SevenStarsApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModLogger {
    protected Logger LOGGER;
    protected ModLogger singleInstance = null;
    protected boolean isDebug;
    protected String messagePrefix;

    public ModLogger(String prefix, boolean debug){
        messagePrefix = prefix;
        LOGGER = LoggerFactory.getLogger(prefix);
        isDebug = debug;
    }


    public void logDebugMsg(String msg) {
        if(SevenStarsApi.IS_DEBUG){
            LOGGER.info(buildMessage(msg));
        }
    }

    private String buildMessage(String msg) {
        return "[%s] - %s".formatted(messagePrefix, msg);
    }

    public void logInfoMsg(String msg) {
        LOGGER.info(buildMessage(msg));
    }

    public void logError(String msg) {
        LOGGER.error(buildMessage(msg));
    }

    public void logError(String msg, Exception e) {
        LOGGER.error(buildMessage(msg), e);
    }

    public void logTrace(String msg) {
        LOGGER.trace(buildMessage(msg));
    }

    public void logWarn(String msg) {
        LOGGER.warn(buildMessage(msg));
    }
}
