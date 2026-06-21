package net.sevenstars.middleearth.registries;

import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;

public class CharacterClothesRegistryME {

    public record Base(){
        public final static Identifier PANTS_BEIGE                                      = MiddleEarth.of("pants_beige");
        public final static Identifier PANTS_BROWN                                      = MiddleEarth.of("pants_brown");
        public final static Identifier PANTS_DARK_BROWN                                 = MiddleEarth.of("pants_dark_brown");
        public final static Identifier PANTS_NIGHTSHADE                                 = MiddleEarth.of("pants_nightshade");
        public final static Identifier SHORT_PANTS_TAN                                  = MiddleEarth.of("short_pants_tan");
        public final static Identifier SHORT_PANTS_BROWN                                  = MiddleEarth.of("short_pants_brown");

        public final static Identifier THONG_BEIGE                                      = MiddleEarth.of("thong_beige");
        public final static Identifier THONG_BROWN                                      = MiddleEarth.of("thong_brown");
        public final static Identifier THONG_DARK_BROWN                                 = MiddleEarth.of("thong_dark_brown");
    }

    public record Over(){
        public final static Identifier SHIRT_BEIGE                                      = MiddleEarth.of("shirt_beige");
        public final static Identifier SHIRT_NIGHTSHADE                                 = MiddleEarth.of("shirt_nightshade");
        public final static Identifier SHIRT_BURGUNDY                                   = MiddleEarth.of("shirt_burgundy");
        public final static Identifier SHORT_CHEMISE_WHITE                              = MiddleEarth.of("short_chemise_white");
        public final static Identifier SHORT_CHEMISE_BEIGE                              = MiddleEarth.of("short_chemise_beige");

        public final static Identifier DRESS_BURGUNDY_AND_WHITE                         = MiddleEarth.of("dress_burgundy_and_white");
        public final static Identifier BROWN_TOWNSMAN_SHIRT                             = MiddleEarth.of("brown_townsman_shirt");
    }

    public record Extra(){
        public final static Identifier EMPTY                                            = MiddleEarth.of("empty");
        public final static Identifier SCARF_BROWN                                      = MiddleEarth.of("scarf_brown");
        public final static Identifier SCARF_DARK_BROWN                                 = MiddleEarth.of("scarf_dark_brown");
        public final static Identifier SCARF_NIGHTSHADE                                 = MiddleEarth.of("scarf_nightshade");
        public final static Identifier SCARF_RED                                        = MiddleEarth.of("scarf_red");
        public final static Identifier SCARF_TEAL                                       = MiddleEarth.of("scarf_teal");
        public final static Identifier OVERALLS_BROWN                                   = MiddleEarth.of("overalls_brown");
        public final static Identifier OVERALLS_TAN                                     = MiddleEarth.of("overalls_tan");
        public final static Identifier BROWN_TOWNSMAN_SHIRT_SHORT                       = MiddleEarth.of("brown_townsman_shirt_short");
        public final static Identifier GREEN_TOWNSMAN_SHIRT_SHORT                       = MiddleEarth.of("green_townsman_shirt_short");
    }
}
