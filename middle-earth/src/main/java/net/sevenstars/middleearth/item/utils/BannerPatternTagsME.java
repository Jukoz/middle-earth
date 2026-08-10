package net.sevenstars.middleearth.item.utils;

import net.sevenstars.middleearth.MiddleEarth;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class BannerPatternTagsME {
    public static final TagKey<BannerPattern> GONDOR_PATTERN_ITEM = of("gondor");
    public static final TagKey<BannerPattern> ROHAN_PATTERN_ITEM = of("rohan");
    public static final TagKey<BannerPattern> DALE_PATTERN_ITEM = of("dale");
    public static final TagKey<BannerPattern> LONGBEARD_PATTERN_ITEM = of("longbeard");
    public static final TagKey<BannerPattern> LOTHLORIEN_PATTERN_ITEM = of("lothlorien");
    public static final TagKey<BannerPattern> MORDOR_PATTERN_ITEM = of("mordor");
    public static final TagKey<BannerPattern> MISTY_MOUNTAINS_ORCS_PATTERN_ITEM = of("misty_mountains_orcs");
    public static final TagKey<BannerPattern> ISENGARD_PATTERN_ITEM = of("isengard");
    public static final TagKey<BannerPattern> SCREECHING_SKULL_PATTERN_ITEM = of("screeching_skull");
    public static final TagKey<BannerPattern> GOBLIN_SKULL_PATTERN_ITEM = of("goblin_skull");
    public static final TagKey<BannerPattern> GREAT_HORN_PATTERN_ITEM = of("great_horn");

    public static final TagKey<BannerPattern> DRAGON_PATTERN_ITEM = of("dragon");
    public static final TagKey<BannerPattern> PIPEWEED_PATTERN_ITEM = of("pipeweed");
    public static final TagKey<BannerPattern> FELLOWSHIP_PATTERN_ITEM = of("fellowship");
    public static final TagKey<BannerPattern> SNAIL_PATTERN_ITEM = of("snail");
    public static final TagKey<BannerPattern> ANVIL_PATTERN_ITEM = of("anvil");
    public static final TagKey<BannerPattern> BELL_PATTERN_ITEM = of("bell");
    public static final TagKey<BannerPattern> DWARF_CROWN_PATTERN_ITEM = of("dwarf_crown");
    public static final TagKey<BannerPattern> SPIDER_PATTERN_ITEM = of("spider");
    public static final TagKey<BannerPattern> BOW_PATTERN_ITEM = of("bow");
    public static final TagKey<BannerPattern> ANTLERS_PATTERN_ITEM = of("antlers");
    public static final TagKey<BannerPattern> OAK_LEAF_PATTERN_ITEM = of("oak_leaf");

    private static TagKey<BannerPattern> of(String name) {
        return TagKey.of(RegistryKeys.BANNER_PATTERN, Identifier.of(MiddleEarth.MOD_ID, "pattern_item/" + name));
    }
}
