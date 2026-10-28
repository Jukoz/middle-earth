package net.sevenstars.middleearth.registries;

import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;

public class CharacterClothesRegistryME {

    public record Base(){
        public final static Identifier PANTS_BEIGE                                      = MiddleEarth.id("pants_beige");
        public final static Identifier PANTS_BROWN                                      = MiddleEarth.id("pants_brown");
        public final static Identifier PANTS_DARK_BROWN                                 = MiddleEarth.id("pants_dark_brown");
        public final static Identifier PANTS_NIGHTSHADE                                 = MiddleEarth.id("pants_nightshade");
        public final static Identifier SHORT_PANTS_TAN                                  = MiddleEarth.id("short_pants_tan");
        public final static Identifier SHORT_PANTS_BROWN                                  = MiddleEarth.id("short_pants_brown");

        public final static Identifier THONG_BEIGE                                      = MiddleEarth.id("thong_beige");
        public final static Identifier THONG_BROWN                                      = MiddleEarth.id("thong_brown");
        public final static Identifier THONG_DARK_BROWN                                 = MiddleEarth.id("thong_dark_brown");
    }

    public record Over(){
        public final static Identifier SHIRT_BEIGE                                      = MiddleEarth.id("shirt_beige");
        public final static Identifier SHIRT_NIGHTSHADE                                 = MiddleEarth.id("shirt_nightshade");
        public final static Identifier SHIRT_BURGUNDY                                   = MiddleEarth.id("shirt_burgundy");
        public final static Identifier SHORT_CHEMISE_WHITE                              = MiddleEarth.id("short_chemise_white");
        public final static Identifier SHORT_CHEMISE_BEIGE                              = MiddleEarth.id("short_chemise_beige");
        public final static Identifier SHORT_CHEMISE_GRAY                               = MiddleEarth.id("short_chemise_gray");
        public final static Identifier SHORT_CHEMISE_RED                                = MiddleEarth.id("short_chemise_red");
        public final static Identifier SIMPLE_SHIRT                                     = MiddleEarth.id("simple_shirt");
        public final static Identifier BLUE_TUNIC                                       = MiddleEarth.id("blue_tunic");
        public final static Identifier PALE_BLUE_TUNIC                                  = MiddleEarth.id("pale_blue_tunic");
        public final static Identifier SILVER_TUNIC                                     = MiddleEarth.id("silver_tunic");
        public final static Identifier TAN_TUNIC                                        = MiddleEarth.id("tan_tunic");

        public final static Identifier DRESS_BURGUNDY_AND_WHITE                         = MiddleEarth.id("dress_burgundy_and_white");
        public final static Identifier BROWN_TOWNSMAN_SHIRT                             = MiddleEarth.id("brown_townsman_shirt");
    }

    public record Extra(){
        public final static Identifier EMPTY                                            = MiddleEarth.id("empty");
        public final static Identifier SCARF_BROWN                                      = MiddleEarth.id("scarf_brown");
        public final static Identifier SCARF_DARK_BROWN                                 = MiddleEarth.id("scarf_dark_brown");
        public final static Identifier SCARF_NIGHTSHADE                                 = MiddleEarth.id("scarf_nightshade");
        public final static Identifier SCARF_RED                                        = MiddleEarth.id("scarf_red");
        public final static Identifier SCARF_TEAL                                       = MiddleEarth.id("scarf_teal");
        public final static Identifier OVERALLS_BROWN                                   = MiddleEarth.id("overalls_brown");
        public final static Identifier OVERALLS_TAN                                     = MiddleEarth.id("overalls_tan");
        public final static Identifier BROWN_TOWNSMAN_SHIRT_SHORT                       = MiddleEarth.id("brown_townsman_shirt_short");
        public final static Identifier GREEN_TOWNSMAN_SHIRT_SHORT                       = MiddleEarth.id("green_townsman_shirt_short");
        public final static Identifier SHORT_RED_COTTE                                  = MiddleEarth.id("short_red_cotte");
        public final static Identifier YELLOW_TOWNSMAN_SHIRT_SHORT                      = MiddleEarth.id("yellow_townsman_shirt_short");
        public final static Identifier GREEN_COTTE                                      = MiddleEarth.id("green_cotte");
        public final static Identifier RED_HOUPPELANDE                                  = MiddleEarth.id("red_houppelande");
        public final static Identifier PURPLE_HOUPPELANDE                               = MiddleEarth.id("purple_houppelande");
        public final static Identifier RICH_HOUPPELANDE                                 = MiddleEarth.id("rich_houppelande");
        public final static Identifier GONDORIAN_TABBARD                                = MiddleEarth.id("gondorian_tabbard");
        public final static Identifier GONDORIAN_TABBARD_OPEN                           = MiddleEarth.id("gondorian_tabbard_open");
    }
}
