package net.sevenstars.middleearth.exceptions;

import net.sevenstars.api.enums.LangCategory;
import net.sevenstars.middleearth.MiddleEarth;

public class NoFactionException extends Exception{
    public static final String KEY_TARGET = LangCategory.EXCEPTION.createKey(MiddleEarth.id("no_faction.target"));
    public static final String KEY_SOURCE = LangCategory.EXCEPTION.createKey(MiddleEarth.id("no_faction.source"));

}
