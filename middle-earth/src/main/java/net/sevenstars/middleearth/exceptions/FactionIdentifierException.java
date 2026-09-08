package net.sevenstars.middleearth.exceptions;

import net.sevenstars.api.enums.LangCategory;
import net.sevenstars.middleearth.MiddleEarth;

public class FactionIdentifierException extends Exception{
    public static final String KEY = MiddleEarth.rawTranslationKeyWithModId(LangCategory.EXCEPTION, "faction_identifier");

}
