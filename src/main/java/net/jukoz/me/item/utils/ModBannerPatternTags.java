package net.jukoz.me.item.utils;

import net.jukoz.me.MiddleEarth;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModBannerPatternTags {
    public static final TagKey<BannerPattern> GONDOR_PATTERN_ITEM = of("pattern_item/gondor");
    public static final TagKey<BannerPattern> ROHAN_PATTERN_ITEM = of("pattern_item/rohan");
    public static final TagKey<BannerPattern> LONGBEARD_PATTERN_ITEM = of("pattern_item/longbeard");
    public static final TagKey<BannerPattern> LOTHLORIEN_PATTERN_ITEM = of("pattern_item/lothlorien");
    public static final TagKey<BannerPattern> MORDOR_GREAT_EYE_PATTERN_ITEM = of("pattern_item/mordor_great_eye");
    public static final TagKey<BannerPattern> MORDOR_EYE_PATTERN_ITEM = of("pattern_item/mordor_eye");
    public static final TagKey<BannerPattern> MISTY_ORCS_EYE_PATTERN_ITEM = of("pattern_item/misty_orcs_eye");
    public static final TagKey<BannerPattern> MISTY_ORCS_PEAKS_PATTERN_ITEM = of("pattern_item/misty_orcs_peaks");

    private static TagKey<BannerPattern> of(String name) {
        return TagKey.of(RegistryKeys.BANNER_PATTERN, new Identifier(name));
    }
}
