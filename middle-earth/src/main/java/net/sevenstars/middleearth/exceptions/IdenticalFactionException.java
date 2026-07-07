package net.sevenstars.middleearth.exceptions;

import net.sevenstars.middleearth.MiddleEarth;

public class IdenticalFactionException extends Exception{
    public static final String KEY_TARGET = "exception.%s.identical_faction.target".formatted(MiddleEarth.MOD_ID);
    public static final String KEY_SOURCE = "exception.%s.identical_faction.source".formatted(MiddleEarth.MOD_ID);

}
