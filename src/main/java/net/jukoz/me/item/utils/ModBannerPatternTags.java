package net.jukoz.me.item.utils;

import net.jukoz.me.MiddleEarth;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModBannerPatternTags {
    public static final TagKey<BannerPattern> GONDOR_PATTERN_ITEM = of("gondor");
    public static final TagKey<BannerPattern> ROHAN_PATTERN_ITEM = of("rohan");
    public static final TagKey<BannerPattern> LONGBEARD_PATTERN_ITEM = of("longbeard");
    public static final TagKey<BannerPattern> LOTHLORIEN_PATTERN_ITEM = of("lothlorien");
    public static final TagKey<BannerPattern> MORDOR_PATTERN_ITEM = of("mordor");
    public static final TagKey<BannerPattern> MISTY_ORCS_PATTERN_ITEM = of("misty_orcs");
    public static final TagKey<BannerPattern> ISENGARD_PATTERN_ITEM = of("isengard");

    public static final TagKey<BannerPattern> DRAGON_PATTERN_ITEM = of("dragon");

    private static TagKey<BannerPattern> of(String name) {
        return TagKey.of(RegistryKeys.BANNER_PATTERN, new Identifier(MiddleEarth.MOD_ID, "pattern_item/" + name));
    }
}
