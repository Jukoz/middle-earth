package net.sevenstars.middleearth.registries.content.rings;

import net.minecraft.block.Block;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.TextColor;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.registration.DecorativeBlockRegistryME;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.resources.datas.rings.RingTheme;
import net.sevenstars.middleearth.resources.datas.rings.RingWord;

import java.util.List;
import java.util.Optional;

/** The built-in ring themes, one per treated anvil; the stone anvil has none and cannot shape rings. */
public class RingThemeRegistry {
    private static final RegistryKey<Registry<RingTheme>> RING_THEME_KEY = DynamicRegistriesME.RING_THEME;

    public static final RegistryKey<RingTheme> SINDARIN = of("sindarin");
    public static final RegistryKey<RingTheme> KHUZDUL = of("khuzdul");
    public static final RegistryKey<RingTheme> ADUNAIC = of("adunaic");
    public static final RegistryKey<RingTheme> BLACK_SPEECH = of("black_speech");

    public static final List<RegistryKey<RingTheme>> ALL = List.of(SINDARIN, KHUZDUL, ADUNAIC, BLACK_SPEECH);

    public static void bootstrap(Registerable<RingTheme> context) {
        RegistryEntryLookup<RingTheme> lookup = context.getRegistryLookup(RING_THEME_KEY);
        register(context, lookup, SINDARIN, theme(DecorativeBlockRegistryME.ELVEN_TREATED_ANVIL, 40, 0xB4D7E3, RingWordRegistry.SINDARIN_WORDS));
        register(context, lookup, KHUZDUL, theme(DecorativeBlockRegistryME.DWARVEN_TREATED_ANVIL, 15, 0xD9A55B, RingWordRegistry.KHUZDUL_WORDS));
        register(context, lookup, ADUNAIC, theme(DecorativeBlockRegistryME.TREATED_ANVIL, 0, 0x7FA6C9, RingWordRegistry.ADUNAIC_WORDS));
        register(context, lookup, BLACK_SPEECH, theme(DecorativeBlockRegistryME.ORCISH_TREATED_ANVIL, -40, 0xA0282C, RingWordRegistry.BLACK_SPEECH_WORDS));
    }

    private static RingTheme theme(Block anvil, int polarity, int color, TagKey<RingWord> words) {
        return new RingTheme(List.of(anvil), polarity, TextColor.fromRgb(color), words);
    }

    private static void register(Registerable<RingTheme> context, RegistryEntryLookup<RingTheme> lookup,
                                 RegistryKey<RingTheme> key, RingTheme element) {
        DynamicRegistriesME.register(context, lookup, key, element);
    }

    private static RegistryKey<RingTheme> of(String name) {
        return DynamicRegistriesME.of(RING_THEME_KEY, MiddleEarth.id(name));
    }

    public static Optional<RegistryEntry.Reference<RingTheme>> get(RegistryWrapper.WrapperLookup lookup, RegistryKey<RingTheme> key) {
        if (lookup == null || key == null) {
            return Optional.empty();
        }
        return lookup.getOptional(RING_THEME_KEY).flatMap(registry -> registry.getOptional(key));
    }

    public static Optional<RegistryEntry.Reference<RingTheme>> forAnvil(RegistryWrapper.WrapperLookup lookup, Block anvil) {
        return lookup.getOptional(RING_THEME_KEY).flatMap(registry -> registry.streamEntries()
                .filter(entry -> entry.value().anvils().contains(anvil)).findFirst());
    }

    public static String translationKey(RegistryKey<RingTheme> key) {
        return key.getValue().toTranslationKey("ring_theme");
    }
}
