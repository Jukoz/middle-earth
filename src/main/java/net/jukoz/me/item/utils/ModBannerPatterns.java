package net.jukoz.me.item.utils;

import net.jukoz.me.MiddleEarth;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.registry.*;
import net.minecraft.util.Identifier;

public class ModBannerPatterns {
    public static final RegistryKey<BannerPattern> GONDOR_BANNER_PATTERN = key("gondor");
    public static final RegistryKey<BannerPattern> ROHAN_BANNER_PATTERN = key("rohan");
    public static final RegistryKey<BannerPattern> LONGBEARD_BANNER_PATTERN = key("longbeard");
    public static final RegistryKey<BannerPattern> LOTHLORIEN_BANNER_PATTERN = key("lothlorien");
    public static final RegistryKey<BannerPattern> MORDOR_BANNER_PATTERN = key("mordor");
    public static final RegistryKey<BannerPattern> MISTY_ORCS_BANNER_PATTERN = key("misty_orcs");
    public static final RegistryKey<BannerPattern> ISENGARD_BANNER_PATTERN = key("isengard");

    public static final RegistryKey<BannerPattern> DRAGON_BANNER_PATTERN = key("dragon");

    private static RegistryKey<BannerPattern> key(String id) {
        return RegistryKey.of(RegistryKeys.BANNER_PATTERN, new Identifier(MiddleEarth.MOD_ID, id));
    }

    public static void register(Registerable<BannerPattern> registry) { // TODO fixme
        register(registry, GONDOR_BANNER_PATTERN);
        register(registry, ROHAN_BANNER_PATTERN);
        register(registry, LONGBEARD_BANNER_PATTERN);
        register(registry, LOTHLORIEN_BANNER_PATTERN);
        register(registry, MORDOR_BANNER_PATTERN);
        register(registry, MISTY_ORCS_BANNER_PATTERN);
        register(registry, ISENGARD_BANNER_PATTERN);

        register(registry, DRAGON_BANNER_PATTERN);
    }

    public static void register(Registerable<BannerPattern> registry, RegistryKey<BannerPattern> key) {
        registry.register(key, new BannerPattern(key.getValue(), "block.me.banner." + key.getValue().toShortTranslationKey()));
    }
}
