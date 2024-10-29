package net.jukoz.me.item.utils;

import net.jukoz.me.MiddleEarth;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.registry.*;
import net.minecraft.util.Identifier;

public class ModBannerPatterns {
    public static final RegistryKey<BannerPattern> TREE = key("tree");
    public static final RegistryKey<BannerPattern> HORSE = key("horse");
    public static final RegistryKey<BannerPattern> HORSE_HEAD = key("horse_head");
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
    public static final RegistryKey<BannerPattern> CLOTH_GRADIENT = key("cloth_gradient");
    public static final RegistryKey<BannerPattern> CLOTH_GRADIENT_UP = key("cloth_gradient_up");
    public static final RegistryKey<BannerPattern> VEIL = key("veil");

    public static final RegistryKey<BannerPattern> ANVIL = key("anvil");
    public static final RegistryKey<BannerPattern> ANVIL_BOTTOM = key("anvil_bottom");
    public static final RegistryKey<BannerPattern> ANVIL_HAMMER = key("anvil_hammer");
    public static final RegistryKey<BannerPattern> ANVIL_HAMMER_BOTTOM = key("anvil_hammer_bottom");
    public static final RegistryKey<BannerPattern> ANVIL_HAMMER_TOP = key("anvil_hammer_top");
    public static final RegistryKey<BannerPattern> ANVIL_TOP = key("anvil_top");

    public static final RegistryKey<BannerPattern> BELL = key("bell");
    public static final RegistryKey<BannerPattern> BELL_BOTTOM = key("bell_bottom");
    public static final RegistryKey<BannerPattern> BELL_TOP = key("bell_top");

    public static final RegistryKey<BannerPattern> BOW = key("bow");
    public static final RegistryKey<BannerPattern> BOW_DOWN = key("bow_down");
    public static final RegistryKey<BannerPattern> BOW_FLAT_BOTTOM = key("bow_flat_bottom");
    public static final RegistryKey<BannerPattern> BOW_FLAT_CENTER = key("bow_flat_center");
    public static final RegistryKey<BannerPattern> BOW_FLAT_TOP = key("bow_flat_top");
    public static final RegistryKey<BannerPattern> BOW_LONG = key("bow_long");
    public static final RegistryKey<BannerPattern> BOW_UP = key("bow_up");

    public static final RegistryKey<BannerPattern> DWARF_CROWN = key("dwarf_crown");
    public static final RegistryKey<BannerPattern> DWARF_CROWN_BOTTOM = key("dwarf_crown_bottom");
    public static final RegistryKey<BannerPattern> DWARF_CROWN_TOP = key("dwarf_crown_top");

    public static final RegistryKey<BannerPattern> SMALL_CIRCLE = key("small_circle");
    public static final RegistryKey<BannerPattern> PIPE = key("pipe");

    private static RegistryKey<BannerPattern> key(String id) {
        return RegistryKey.of(RegistryKeys.BANNER_PATTERN, Identifier.of(MiddleEarth.MOD_ID, id));
    }

    public static void register(Registerable<BannerPattern> registry) {
        register(registry, TREE);
        register(registry, HORSE);
        register(registry, HORSE_HEAD);
        register(registry, GOLDENWOOD);
        register(registry, STAR_AND_LEAF);
        register(registry, MALLORN);
        register(registry, EYE_OF_SAURON);
        register(registry, GREAT_EYE);
        register(registry, PAINTED_EYE);
        register(registry, EVIL_EYE);
        register(registry, EVIL_PEAKS);
        register(registry, HAND);

        register(registry, DRAGON);
        register(registry, SNAIL);

        register(registry, CLOTH);
        register(registry, CLOTH_GRADIENT);
        register(registry, CLOTH_GRADIENT_UP);
        register(registry, VEIL);

        register(registry, ANVIL);
        register(registry, ANVIL_BOTTOM);
        register(registry, ANVIL_HAMMER);
        register(registry, ANVIL_HAMMER_BOTTOM);
        register(registry, ANVIL_HAMMER_TOP);
        register(registry, ANVIL_TOP);

        register(registry, BELL);
        register(registry, BELL_BOTTOM);
        register(registry, BELL_TOP);

        register(registry, BOW);
        register(registry, BOW_DOWN);
        register(registry, BOW_FLAT_BOTTOM);
        register(registry, BOW_FLAT_CENTER);
        register(registry, BOW_FLAT_TOP);
        register(registry, BOW_LONG);
        register(registry, BOW_UP);

        register(registry, DWARF_CROWN);
        register(registry, DWARF_CROWN_BOTTOM);
        register(registry, DWARF_CROWN_TOP);

        register(registry, SMALL_CIRCLE);
        register(registry, PIPE);
    }

    public static void register(Registerable<BannerPattern> registry, RegistryKey<BannerPattern> key) {
        registry.register(key, new BannerPattern(key.getValue(), "block.me.banner." + key.getValue().toShortTranslationKey()));
    }
}
