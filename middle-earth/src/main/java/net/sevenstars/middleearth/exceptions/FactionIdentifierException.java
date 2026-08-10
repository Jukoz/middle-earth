package net.sevenstars.middleearth.exceptions;

import net.sevenstars.middleearth.MiddleEarth;

public class FactionIdentifierException extends Exception{
    public static final String KEY = "exception.%s.faction_identifier".formatted(MiddleEarth.MOD_ID);

}
