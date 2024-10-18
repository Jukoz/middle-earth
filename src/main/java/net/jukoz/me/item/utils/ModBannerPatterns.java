package net.jukoz.me.item.utils;

import net.jukoz.me.MiddleEarth;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.registry.*;
import net.minecraft.util.Identifier;

public class ModBannerPatterns {
    public static final RegistryKey<BannerPattern> GONDOR_BANNER_PATTERN = key("gondor");
    public static final RegistryKey<BannerPattern> ROHAN_BANNER_PATTERN = key("rohan");
    public static final RegistryKey<BannerPattern> HORSE_BANNER_PATTERN = key("horse");
    public static final RegistryKey<BannerPattern> LONGBEARD_BANNER_PATTERN = key("longbeard");
    public static final RegistryKey<BannerPattern> LOTHLORIEN_BANNER_PATTERN = key("lothlorien");
    public static final RegistryKey<BannerPattern> MALLORN_LEAF_BANNER_PATTERN = key("mallorn_leaf");
    public static final RegistryKey<BannerPattern> MORDOR_BANNER_PATTERN = key("mordor");
    public static final RegistryKey<BannerPattern> MISTY_MOUNTAINS_ORCS_BANNER_PATTERN = key("misty_mountains_orcs");
    public static final RegistryKey<BannerPattern> ISENGARD_BANNER_PATTERN = key("isengard");

    // Variants
    public static final RegistryKey<BannerPattern> MORDOR_EYE_BANNER_PATTERN = key("mordor_eye");
    public static final RegistryKey<BannerPattern> MORDOR_GREAT_EYE_BANNER_PATTERN = key("mordor_great_eye");
    public static final RegistryKey<BannerPattern> SAURON_EYE_BANNER_PATTERN = key("sauron_eye");
    public static final RegistryKey<BannerPattern> MISTY_MOUNTAINS_ORCS_EYE_BANNER_PATTERN = key("misty_mountains_orcs_eye");
    public static final RegistryKey<BannerPattern> MISTY_MOUNTAINS_ORCS_PEAKS_BANNER_PATTERN = key("misty_mountains_orcs_peaks");

    public static final RegistryKey<BannerPattern> DRAGON_BANNER_PATTERN = key("dragon");
    public static final RegistryKey<BannerPattern> SNAIL_BANNER_PATTERN = key("snail");
    public static final RegistryKey<BannerPattern> CLOTH_BANNER_PATTERN = key("cloth");
    public static final RegistryKey<BannerPattern> FELLOWSHIP_BANNER_PATTERN = key("fellowship");
    public static final RegistryKey<BannerPattern> SMALL_CIRCLE_BANNER_PATTERN = key("small_circle");

    private static RegistryKey<BannerPattern> key(String id) {
        return RegistryKey.of(RegistryKeys.BANNER_PATTERN, Identifier.of(MiddleEarth.MOD_ID, id));
    }

    public static void register(Registerable<BannerPattern> registry) {
        register(registry, GONDOR_BANNER_PATTERN);
        register(registry, ROHAN_BANNER_PATTERN);
        register(registry, HORSE_BANNER_PATTERN);
        register(registry, LONGBEARD_BANNER_PATTERN);
        register(registry, LOTHLORIEN_BANNER_PATTERN);
        register(registry, MALLORN_LEAF_BANNER_PATTERN);
        register(registry, MORDOR_BANNER_PATTERN);
        register(registry, SAURON_EYE_BANNER_PATTERN);
        register(registry, MISTY_MOUNTAINS_ORCS_BANNER_PATTERN);
        register(registry, ISENGARD_BANNER_PATTERN);

        register(registry, DRAGON_BANNER_PATTERN);
        register(registry, SNAIL_BANNER_PATTERN);
        register(registry, CLOTH_BANNER_PATTERN);
        register(registry, FELLOWSHIP_BANNER_PATTERN);
        register(registry, SMALL_CIRCLE_BANNER_PATTERN);
    }

    public static void register(Registerable<BannerPattern> registry, RegistryKey<BannerPattern> key) {
        registry.register(key, new BannerPattern(key.getValue(), "block.me.banner." + key.getValue().toShortTranslationKey()));
    }
}
