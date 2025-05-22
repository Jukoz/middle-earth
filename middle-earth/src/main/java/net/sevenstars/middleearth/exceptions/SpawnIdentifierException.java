package net.sevenstars.middleearth.exceptions;

import net.sevenstars.middleearth.MiddleEarth;

public class SpawnIdentifierException extends Exception{
    public static final String KEY = "exception.%s.spawn_identifier".formatted(MiddleEarth.MOD_ID);

}
