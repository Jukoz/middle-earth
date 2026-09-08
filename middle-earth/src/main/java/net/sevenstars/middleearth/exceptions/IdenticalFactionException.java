package net.sevenstars.middleearth.exceptions;

import net.sevenstars.api.enums.LangCategory;
import net.sevenstars.middleearth.MiddleEarth;

public class IdenticalFactionException extends Exception{
    public static final String KEY_TARGET = MiddleEarth.rawTranslationKeyWithModId(LangCategory.EXCEPTION, "identical_faction.target");
    public static final String KEY_SOURCE = MiddleEarth.rawTranslationKeyWithModId(LangCategory.EXCEPTION, "identical_faction.source");

}
