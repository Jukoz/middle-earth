package net.jukoz.me.item.utils;

import net.jukoz.me.MiddleEarth;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModBannerPatterns {
    public static final RegistryKey<BannerPattern> GONDOR_BANNER_PATTERN = key("gondor");
    public static final RegistryKey<BannerPattern> ROHAN_BANNER_PATTERN = key("rohan");
    public static final RegistryKey<BannerPattern> LONGBEARD_BANNER_PATTERN = key("longbeard");
    public static final RegistryKey<BannerPattern> LOTHLORIEN_BANNER_PATTERN = key("lothlorien");
    public static final RegistryKey<BannerPattern> MORDOR_GREAT_EYE_BANNER_PATTERN = key("mordor_great_eye");
    public static final RegistryKey<BannerPattern> MORDOR_EYE_BANNER_PATTERN = key("mordor_eye");
    public static final RegistryKey<BannerPattern> MISTY_ORCS_EYE_BANNER_PATTERN = key("misty_orcs_eye");
    public static final RegistryKey<BannerPattern> MISTY_ORCS_PEAKS_BANNER_PATTERN = key("misty_orcs_peaks");

    private static RegistryKey<BannerPattern> key(String id) {
        return RegistryKey.of(RegistryKeys.BANNER_PATTERN, new Identifier(id));
    }

    public static void register(Registry<BannerPattern> registry) {
        Registry.register(registry, GONDOR_BANNER_PATTERN, new BannerPattern("gon"));
        Registry.register(registry, ROHAN_BANNER_PATTERN, new BannerPattern("roh"));
        Registry.register(registry, LONGBEARD_BANNER_PATTERN, new BannerPattern("longb"));
        Registry.register(registry, LOTHLORIEN_BANNER_PATTERN, new BannerPattern("loth"));
        Registry.register(registry, MORDOR_GREAT_EYE_BANNER_PATTERN, new BannerPattern("moreye"));
        Registry.register(registry, MORDOR_EYE_BANNER_PATTERN, new BannerPattern("morgreye"));
        Registry.register(registry, MISTY_ORCS_EYE_BANNER_PATTERN, new BannerPattern("misteye"));
        Registry.register(registry, MISTY_ORCS_PEAKS_BANNER_PATTERN, new BannerPattern("mistpeaks"));
    }
}
