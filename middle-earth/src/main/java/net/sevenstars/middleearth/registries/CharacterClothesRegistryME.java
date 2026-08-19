package net.sevenstars.middleearth.registries;

import net.minecraft.resources.ResourceLocation;
import net.sevenstars.middleearth.MiddleEarth;

public class CharacterClothesRegistryME {

    public record Base(){
        public final static ResourceLocation PANTS_BEIGE                                      = MiddleEarth.of("pants_beige");
        public final static ResourceLocation PANTS_BROWN                                      = MiddleEarth.of("pants_brown");
        public final static ResourceLocation PANTS_DARK_BROWN                                 = MiddleEarth.of("pants_dark_brown");
        public final static ResourceLocation PANTS_NIGHTSHADE                                 = MiddleEarth.of("pants_nightshade");
        public final static ResourceLocation SHORT_PANTS_TAN                                  = MiddleEarth.of("short_pants_tan");
        public final static ResourceLocation SHORT_PANTS_BROWN                                  = MiddleEarth.of("short_pants_brown");

        public final static ResourceLocation THONG_BEIGE                                      = MiddleEarth.of("thong_beige");
        public final static ResourceLocation THONG_BROWN                                      = MiddleEarth.of("thong_brown");
        public final static ResourceLocation THONG_DARK_BROWN                                 = MiddleEarth.of("thong_dark_brown");
    }

    public record Over(){
        public final static ResourceLocation SHIRT_BEIGE                                      = MiddleEarth.of("shirt_beige");
        public final static ResourceLocation SHIRT_NIGHTSHADE                                 = MiddleEarth.of("shirt_nightshade");
        public final static ResourceLocation SHIRT_BURGUNDY                                   = MiddleEarth.of("shirt_burgundy");
        public final static ResourceLocation SHORT_CHEMISE_WHITE                              = MiddleEarth.of("short_chemise_white");
        public final static ResourceLocation SHORT_CHEMISE_BEIGE                              = MiddleEarth.of("short_chemise_beige");
        public final static ResourceLocation SHORT_CHEMISE_GRAY                               = MiddleEarth.of("short_chemise_gray");
        public final static ResourceLocation SHORT_CHEMISE_RED                                = MiddleEarth.of("short_chemise_red");
        public final static ResourceLocation SIMPLE_SHIRT                                     = MiddleEarth.of("simple_shirt");
        public final static ResourceLocation BLUE_TUNIC                                       = MiddleEarth.of("blue_tunic");
        public final static ResourceLocation PALE_BLUE_TUNIC                                  = MiddleEarth.of("pale_blue_tunic");
        public final static ResourceLocation SILVER_TUNIC                                     = MiddleEarth.of("silver_tunic");
        public final static ResourceLocation TAN_TUNIC                                        = MiddleEarth.of("tan_tunic");

        public final static ResourceLocation DRESS_BURGUNDY_AND_WHITE                         = MiddleEarth.of("dress_burgundy_and_white");
        public final static ResourceLocation BROWN_TOWNSMAN_SHIRT                             = MiddleEarth.of("brown_townsman_shirt");
    }

    public record Extra(){
        public final static ResourceLocation EMPTY                                            = MiddleEarth.of("empty");
        public final static ResourceLocation SCARF_BROWN                                      = MiddleEarth.of("scarf_brown");
        public final static ResourceLocation SCARF_DARK_BROWN                                 = MiddleEarth.of("scarf_dark_brown");
        public final static ResourceLocation SCARF_NIGHTSHADE                                 = MiddleEarth.of("scarf_nightshade");
        public final static ResourceLocation SCARF_RED                                        = MiddleEarth.of("scarf_red");
        public final static ResourceLocation SCARF_TEAL                                       = MiddleEarth.of("scarf_teal");
        public final static ResourceLocation OVERALLS_BROWN                                   = MiddleEarth.of("overalls_brown");
        public final static ResourceLocation OVERALLS_TAN                                     = MiddleEarth.of("overalls_tan");
        public final static ResourceLocation BROWN_TOWNSMAN_SHIRT_SHORT                       = MiddleEarth.of("brown_townsman_shirt_short");
        public final static ResourceLocation GREEN_TOWNSMAN_SHIRT_SHORT                       = MiddleEarth.of("green_townsman_shirt_short");
        public final static ResourceLocation SHORT_RED_COTTE                                  = MiddleEarth.of("short_red_cotte");
        public final static ResourceLocation YELLOW_TOWNSMAN_SHIRT_SHORT                      = MiddleEarth.of("yellow_townsman_shirt_short");
        public final static ResourceLocation GREEN_COTTE                                      = MiddleEarth.of("green_cotte");
        public final static ResourceLocation RED_HOUPPELANDE                                  = MiddleEarth.of("red_houppelande");
        public final static ResourceLocation PURPLE_HOUPPELANDE                               = MiddleEarth.of("purple_houppelande");
        public final static ResourceLocation RICH_HOUPPELANDE                                 = MiddleEarth.of("rich_houppelande");
        public final static ResourceLocation GONDORIAN_TABBARD                                = MiddleEarth.of("gondorian_tabbard");
        public final static ResourceLocation GONDORIAN_TABBARD_OPEN                           = MiddleEarth.of("gondorian_tabbard_open");
    }
}
