package net.jukoz.me.item.utils;

import net.jukoz.me.MiddleEarth;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.registry.*;
import net.minecraft.util.Identifier;

public class ModBannerPatterns {
    public static final RegistryKey<BannerPattern> TREE = key("tree");
    public static final RegistryKey<BannerPattern> HORSE = key("horse");
    public static final RegistryKey<BannerPattern> HORSE_HEAD = key("horse_head");
    public static final RegistryKey<BannerPattern> BELL_AND_BOW = key("bell_and_bow");
    public static final RegistryKey<BannerPattern> CROWN_AND_ANVIL = key("crown_and_anvil");
    public static final RegistryKey<BannerPattern> GOLDENWOOD = key("goldenwood");
    public static final RegistryKey<BannerPattern> STAR_AND_LEAF = key("star_and_leaf");
    public static final RegistryKey<BannerPattern> MALLORN = key("mallorn");
    public static final RegistryKey<BannerPattern> EYE_OF_SAURON = key("eye_of_sauron");
    public static final RegistryKey<BannerPattern> GREAT_EYE = key("great_eye");
    public static final RegistryKey<BannerPattern> PAINTED_EYE = key("painted_eye");
    public static final RegistryKey<BannerPattern> EVIL_EYE = key("evil_eye");
    public static final RegistryKey<BannerPattern> EVIL_PEAKS = key("evil_peaks");
    public static final RegistryKey<BannerPattern> HAND = key("hand");

    public static final RegistryKey<BannerPattern> DRAGON = key("dragon");
    public static final RegistryKey<BannerPattern> SNAIL = key("snail");
    public static final RegistryKey<BannerPattern> CLOTH = key("cloth");
    public static final RegistryKey<BannerPattern> SMALL_CIRCLE = key("small_circle");
    public static final RegistryKey<BannerPattern> PIPE = key("pipe");

    private static RegistryKey<BannerPattern> key(String id) {
        return RegistryKey.of(RegistryKeys.BANNER_PATTERN, Identifier.of(MiddleEarth.MOD_ID, id));
    }

    public static void register(Registerable<BannerPattern> registry) {
        register(registry, TREE);
        register(registry, HORSE);
        register(registry, HORSE_HEAD);
        register(registry, CROWN_AND_ANVIL);
        register(registry, GOLDENWOOD);
        register(registry, STAR_AND_LEAF);
        register(registry, MALLORN);
        register(registry, EYE_OF_SAURON);
        register(registry, GREAT_EYE);
        register(registry, PAINTED_EYE);
        register(registry, EVIL_EYE);
        register(registry, EVIL_PEAKS);
        register(registry, HAND);
        register(registry, BELL_AND_BOW);

        register(registry, DRAGON);
        register(registry, SNAIL);
        register(registry, CLOTH);
        register(registry, SMALL_CIRCLE);
        register(registry, PIPE);
    }

    public static void register(Registerable<BannerPattern> registry, RegistryKey<BannerPattern> key) {
        registry.register(key, new BannerPattern(key.getValue(), "block.me.banner." + key.getValue().toShortTranslationKey()));
    }
}
