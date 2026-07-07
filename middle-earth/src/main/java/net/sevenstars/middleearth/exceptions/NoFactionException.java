package net.sevenstars.middleearth.exceptions;

import net.sevenstars.middleearth.MiddleEarth;

public class NoFactionException extends Exception{
    public static final String KEY_TARGET = "exception.%s.no_faction.target".formatted(MiddleEarth.MOD_ID);
    public static final String KEY_SOURCE = "exception.%s.no_faction.source".formatted(MiddleEarth.MOD_ID);

}
