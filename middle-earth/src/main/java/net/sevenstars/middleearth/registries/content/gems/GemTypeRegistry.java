package net.sevenstars.middleearth.registries.content.gems;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.text.TextColor;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.resources.datas.gems.GemAspect;
import net.sevenstars.middleearth.resources.datas.gems.GemType;

import java.util.List;
import java.util.Optional;

/**
 * The built-in gem types: one gem per general aspect plus one per people (team roster, Gem Cutting thread).
 * Inscription words follow the Inscription Table's existing pools for now: attack = the old ruby words,
 * defense = sapphire, speed = adamant, utility = vanilla emerald.
 */
public class GemTypeRegistry {
    private static final RegistryKey<Registry<GemType>> GEM_TYPE_KEY = DynamicRegistriesME.GEM_TYPE;

    public static final RegistryKey<GemType> ADAMANT = of("adamant");
    public static final RegistryKey<GemType> RUBY = of("ruby");
    public static final RegistryKey<GemType> SAPPHIRE = of("sapphire");
    public static final RegistryKey<GemType> BERYL = of("beryl");
    public static final RegistryKey<GemType> HADORITE = of("hadorite");
    public static final RegistryKey<GemType> OPAL = of("opal");
    public static final RegistryKey<GemType> ONYX = of("onyx");
    public static final RegistryKey<GemType> MOONSTONE = of("moonstone");

    /** Built-in gem types, in creative tab order. */
    public static final List<RegistryKey<GemType>> ALL =
            List.of(ADAMANT, RUBY, SAPPHIRE, BERYL, HADORITE, OPAL, ONYX, MOONSTONE);

    private static final List<String> ATTACK_WORDS = List.of("fierce", "forceful", "noiseless", "bane");
    private static final List<String> DEFENSE_WORDS = List.of("pierce", "flame", "sturdy");
    private static final List<String> SPEED_WORDS = List.of("swift");
    private static final List<String> UTILITY_WORDS = List.of("broad", "careful", "long", "gifted");

    public static void bootstrap(Registerable<GemType> context) {
        RegistryEntryLookup<GemType> lookup = context.getRegistryLookup(GEM_TYPE_KEY);
        register(context, lookup, ADAMANT, gem(GemAspect.SPEED, 0xD9E5F1, SPEED_WORDS, 3));
        register(context, lookup, RUBY, gem(GemAspect.ATTACK, 0xEA1C56, ATTACK_WORDS, 1));
        register(context, lookup, SAPPHIRE, gem(GemAspect.DEFENSE, 0x328ECD, DEFENSE_WORDS, 1));
        register(context, lookup, BERYL, gem(GemAspect.UTILITY, 0x1FAE62, UTILITY_WORDS, 1));
        register(context, lookup, HADORITE, gem(GemAspect.MEN, 0x9A74F2, UTILITY_WORDS, 2));
        // Opal's play of colour: its ore sparks lilac, blue and white (from its palette).
        register(context, lookup, OPAL, gem(GemAspect.DWARVES, 0x8FD3F0, DEFENSE_WORDS, 2, 0xE58FC4, 0x8FD3F0, 0xFFFAF0));
        register(context, lookup, ONYX, gem(GemAspect.MORGUL, 0x6E6E78, ATTACK_WORDS, 2));
        register(context, lookup, MOONSTONE, gem(GemAspect.ELVES, 0xA6CDF8, SPEED_WORDS, 2));
    }

    private static GemType gem(GemAspect aspect, int color, List<String> words, int studyBand, int... sparkColors) {
        return new GemType(aspect, TextColor.fromRgb(color), words, studyBand,
            java.util.Arrays.stream(sparkColors).mapToObj(TextColor::fromRgb).toList());
    }

    private static void register(Registerable<GemType> context, RegistryEntryLookup<GemType> lookup,
                                 RegistryKey<GemType> key, GemType element) {
        DynamicRegistriesME.register(context, lookup, key, element);
    }

    private static RegistryKey<GemType> of(String name) {
        return DynamicRegistriesME.of(GEM_TYPE_KEY, MiddleEarth.id(name));
    }

    /** Looks a gem type up in the synced registry; empty if the lookup or entry is missing. */
    public static Optional<GemType> get(RegistryWrapper.WrapperLookup lookup, RegistryKey<GemType> key) {
        if (lookup == null || key == null) {
            return Optional.empty();
        }
        return lookup.getOptional(GEM_TYPE_KEY).flatMap(registry -> registry.getOptional(key)).map(entry -> entry.value());
    }

    public static String translationKey(RegistryKey<GemType> key) {
        return key.getValue().toTranslationKey("gem_type");
    }

    /** The gem's short name, e.g. "ruby". */
    public static String name(RegistryKey<GemType> key) {
        return key.getValue().getPath();
    }
}
