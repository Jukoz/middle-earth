package net.sevenstars.middleearth.item.utils;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.datageneration.content.TranslationEntries;

public class BannerPatternsME {
    public static final ResourceKey<BannerPattern> TREE = key("tree");
    public static final ResourceKey<BannerPattern> HORSE = key("horse");
    public static final ResourceKey<BannerPattern> HORSE_HEAD = key("horse_head");
    public static final ResourceKey<BannerPattern> GOLDENWOOD = key("goldenwood");
    public static final ResourceKey<BannerPattern> STAR_AND_LEAF = key("star_and_leaf");
    public static final ResourceKey<BannerPattern> MALLORN = key("mallorn");
    public static final ResourceKey<BannerPattern> EYE_OF_SAURON = key("eye_of_sauron");
    public static final ResourceKey<BannerPattern> GREAT_EYE = key("great_eye");
    public static final ResourceKey<BannerPattern> PAINTED_EYE = key("painted_eye");
    public static final ResourceKey<BannerPattern> EVIL_EYE = key("evil_eye");
    public static final ResourceKey<BannerPattern> EVIL_PEAKS = key("evil_peaks");
    public static final ResourceKey<BannerPattern> HAND = key("hand");
    public static final ResourceKey<BannerPattern> SCREECHING_SKULL = key("screeching_skull");
    public static final ResourceKey<BannerPattern> GOBLIN_SKULL = key("goblin_skull");
    public static final ResourceKey<BannerPattern> ANTLERS = key("antlers");
    public static final ResourceKey<BannerPattern> ANTLERS_TOP = key("antlers_top");
    public static final ResourceKey<BannerPattern> ELK = key("elk");
    public static final ResourceKey<BannerPattern> ELK_ANTLERS = key("elk_antlers");
    public static final ResourceKey<BannerPattern> ELK_SHED = key("elk_shed");
    public static final ResourceKey<BannerPattern> STAG = key("stag");
    public static final ResourceKey<BannerPattern> STAG_ANTLERS = key("stag_antlers");
    public static final ResourceKey<BannerPattern> STAG_SHED = key("stag_shed");
    public static final ResourceKey<BannerPattern> OAK_LEAF = key("oak_leaf");

    public static final ResourceKey<BannerPattern> DRAGON = key("dragon");
    public static final ResourceKey<BannerPattern> SNAIL = key("snail");

    public static final ResourceKey<BannerPattern> CLOTH = key("cloth");
    public static final ResourceKey<BannerPattern> CLOTH_GRADIENT = key("cloth_gradient");
    public static final ResourceKey<BannerPattern> CLOTH_GRADIENT_UP = key("cloth_gradient_up");
    public static final ResourceKey<BannerPattern> VEIL = key("veil");

    public static final ResourceKey<BannerPattern> ANVIL = key("anvil");
    public static final ResourceKey<BannerPattern> ANVIL_BOTTOM = key("anvil_bottom");
    public static final ResourceKey<BannerPattern> ANVIL_HAMMER = key("anvil_hammer");
    public static final ResourceKey<BannerPattern> ANVIL_HAMMER_BOTTOM = key("anvil_hammer_bottom");
    public static final ResourceKey<BannerPattern> ANVIL_HAMMER_TOP = key("anvil_hammer_top");
    public static final ResourceKey<BannerPattern> ANVIL_TOP = key("anvil_top");

    public static final ResourceKey<BannerPattern> BELL = key("bell");
    public static final ResourceKey<BannerPattern> BELL_BOTTOM = key("bell_bottom");
    public static final ResourceKey<BannerPattern> BELL_TOP = key("bell_top");

    public static final ResourceKey<BannerPattern> BOW = key("bow");
    public static final ResourceKey<BannerPattern> BOW_DOWN = key("bow_down");
    public static final ResourceKey<BannerPattern> BOW_FLAT_BOTTOM = key("bow_flat_bottom");
    public static final ResourceKey<BannerPattern> BOW_FLAT_CENTER = key("bow_flat_center");
    public static final ResourceKey<BannerPattern> BOW_FLAT_TOP = key("bow_flat_top");
    public static final ResourceKey<BannerPattern> BOW_LONG = key("bow_long");
    public static final ResourceKey<BannerPattern> BOW_UP = key("bow_up");

    public static final ResourceKey<BannerPattern> DWARF_CROWN = key("dwarf_crown");
    public static final ResourceKey<BannerPattern> DWARF_CROWN_BOTTOM = key("dwarf_crown_bottom");
    public static final ResourceKey<BannerPattern> DWARF_CROWN_TOP = key("dwarf_crown_top");

    public static final ResourceKey<BannerPattern> SMALL_CIRCLE = key("small_circle");
    public static final ResourceKey<BannerPattern> PIPE = key("pipe");

    private static ResourceKey<BannerPattern> key(String id) {
        TranslationEntries.bannerPatternEntries.add(id);
        return ResourceKey.create(Registries.BANNER_PATTERN, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, id));
    }

    public static void register(BootstrapContext<BannerPattern> registry) {
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
        register(registry, SCREECHING_SKULL);
        register(registry, GOBLIN_SKULL);
        register(registry, ANTLERS);
        register(registry, ELK);
        register(registry, ELK_ANTLERS);
        register(registry, ELK_SHED);
        register(registry, STAG);
        register(registry, STAG_ANTLERS);
        register(registry, STAG_SHED);
        register(registry, OAK_LEAF);

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

    public static void register(BootstrapContext<BannerPattern> registry, ResourceKey<BannerPattern> key) {
        registry.register(key, new BannerPattern(key.location(), "block.%s.banner.".formatted(MiddleEarth.MOD_ID) + key.location().toShortLanguageKey()));
    }
}
