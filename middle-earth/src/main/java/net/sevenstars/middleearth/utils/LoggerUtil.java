package net.sevenstars.middleearth.utils;

import net.sevenstars.api.utils.ModLogger;
import net.sevenstars.middleearth.MiddleEarth;
public class LoggerUtil extends ModLogger {
    public LoggerUtil(){
        super(MiddleEarth.IS_DEBUG, MiddleEarth.MOD_ID);
    }
}
