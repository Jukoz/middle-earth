package net.sevenstars.middleearth.registries;

import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;

public class CharacterClothesRegistryME {
    private final static String base = "character_clothes/base/";
    private final static String over = "character_clothes/over/";
    private final static String extra = "character_clothes/extra/";

    public record Base(){
        public final static Identifier PANTS_BEIGE                                      = MiddleEarth.of(base + "pants_beige");
        public final static Identifier PANTS_BROWN                                      = MiddleEarth.of(base + "pants_brown");
        public final static Identifier PANTS_DARK_BROWN                                 = MiddleEarth.of(base + "pants_dark_brown");

        public final static Identifier THONG_BEIGE                                      = MiddleEarth.of(base + "thong_beige");
        public final static Identifier THONG_BROWN                                      = MiddleEarth.of(base + "thong_brown");
        public final static Identifier THONG_DARK_BROWN                                 = MiddleEarth.of(base + "thong_dark_brown");
    }

    public record Over(){
        public final static Identifier SHIRT_BEIGE                                      = MiddleEarth.of(over + "shirt_beige");
        public final static Identifier SHIRT_BURGUNDY                                   = MiddleEarth.of(over + "shirt_burgundy");

        public final static Identifier DRESS_BURGUNDY_AND_WHITE                         = MiddleEarth.of(over + "dress_burgundy_and_white");
    }

    public record Extra(){
        public final static Identifier SCARF_BROWN                                      = MiddleEarth.of(extra + "scarf_brown");
        public final static Identifier SCARF_DARK_BROWN                                 = MiddleEarth.of(extra + "scarf_dark_brown");
    }

    public static Identifier RemovePath(Identifier identifier){
        String val = identifier.toString();

        val = val.replace(base, "");
        val = val.replace(over, "");
        val = val.replace(extra, "");

        return Identifier.of(val);
    }
}
