package net.sevenstars.middleearth.registries.content.rings;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.resources.datas.gems.GemAspect;
import net.sevenstars.middleearth.resources.datas.rings.RingWord;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/** The built-in ring inscription words, the per-theme word tags and which themes allow each word. */
public class RingWordRegistry {
    private static final RegistryKey<Registry<RingWord>> RING_WORD_KEY = DynamicRegistriesME.RING_WORD;

    public static final RegistryKey<RingWord> NOURISHMENT = of("nourishment");
    public static final RegistryKey<RingWord> MORTALITY = of("mortality");
    public static final RegistryKey<RingWord> SPIRIT = of("spirit");
    public static final RegistryKey<RingWord> SIGHT = of("sight");
    public static final RegistryKey<RingWord> COMMAND = of("command");
    public static final RegistryKey<RingWord> COURAGE = of("courage");
    public static final RegistryKey<RingWord> PRESENCE = of("presence");
    public static final RegistryKey<RingWord> MIGHT = of("might");
    public static final RegistryKey<RingWord> WISDOM = of("wisdom");
    public static final RegistryKey<RingWord> LUCK = of("luck");
    public static final RegistryKey<RingWord> FLAME = of("flame");
    public static final RegistryKey<RingWord> WATER = of("water");
    public static final RegistryKey<RingWord> SKY = of("sky");
    public static final RegistryKey<RingWord> SUCCOR = of("succor");
    public static final RegistryKey<RingWord> EARTH = of("earth");
    public static final RegistryKey<RingWord> ENDURANCE = of("endurance");
    public static final RegistryKey<RingWord> SWIFTNESS = of("swiftness");
    public static final RegistryKey<RingWord> KINSHIP = of("kinship");
    public static final RegistryKey<RingWord> PRESERVATION = of("preservation");
    public static final RegistryKey<RingWord> NATURE = of("nature");

    public static final List<RegistryKey<RingWord>> ALL = List.of(NOURISHMENT, MORTALITY, SPIRIT, SIGHT, COMMAND,
            COURAGE, PRESENCE, MIGHT, WISDOM, LUCK, FLAME, WATER, SKY, SUCCOR, EARTH, ENDURANCE, SWIFTNESS, KINSHIP,
            PRESERVATION, NATURE);

    public static final TagKey<RingWord> SINDARIN_WORDS = tag("sindarin");
    public static final TagKey<RingWord> KHUZDUL_WORDS = tag("khuzdul");
    public static final TagKey<RingWord> ADUNAIC_WORDS = tag("adunaic");
    public static final TagKey<RingWord> BLACK_SPEECH_WORDS = tag("black_speech");

    public static final List<TagKey<RingWord>> THEME_TAGS = List.of(SINDARIN_WORDS, KHUZDUL_WORDS, ADUNAIC_WORDS,
            BLACK_SPEECH_WORDS);

    public static final Map<RegistryKey<RingWord>, List<TagKey<RingWord>>> THEMES = Map.ofEntries(
            Map.entry(NOURISHMENT, THEME_TAGS),
            Map.entry(MORTALITY, List.of(ADUNAIC_WORDS, BLACK_SPEECH_WORDS)),
            Map.entry(SPIRIT, List.of(SINDARIN_WORDS, BLACK_SPEECH_WORDS)),
            Map.entry(SIGHT, THEME_TAGS),
            Map.entry(COMMAND, THEME_TAGS),
            Map.entry(COURAGE, List.of(SINDARIN_WORDS, ADUNAIC_WORDS)),
            Map.entry(PRESENCE, THEME_TAGS),
            Map.entry(MIGHT, THEME_TAGS),
            Map.entry(WISDOM, THEME_TAGS),
            Map.entry(LUCK, List.of(KHUZDUL_WORDS)),
            Map.entry(FLAME, THEME_TAGS),
            Map.entry(WATER, List.of(SINDARIN_WORDS, ADUNAIC_WORDS, BLACK_SPEECH_WORDS)),
            Map.entry(SKY, List.of(SINDARIN_WORDS, ADUNAIC_WORDS)),
            Map.entry(SUCCOR, List.of(SINDARIN_WORDS, ADUNAIC_WORDS)),
            Map.entry(EARTH, List.of(KHUZDUL_WORDS, BLACK_SPEECH_WORDS)),
            Map.entry(ENDURANCE, THEME_TAGS),
            Map.entry(SWIFTNESS, THEME_TAGS),
            Map.entry(KINSHIP, List.of(SINDARIN_WORDS, KHUZDUL_WORDS, ADUNAIC_WORDS)),
            Map.entry(PRESERVATION, THEME_TAGS),
            Map.entry(NATURE, List.of(SINDARIN_WORDS, ADUNAIC_WORDS)));

    public static void bootstrap(Registerable<RingWord> context) {
        RegistryEntryLookup<RingWord> lookup = context.getRegistryLookup(RING_WORD_KEY);
        register(context, lookup, NOURISHMENT, GemAspect.UTILITY, 1, GemAspect.DWARVES, GemAspect.ELVES);
        register(context, lookup, MORTALITY, GemAspect.DEFENSE, 2, GemAspect.MORGUL);
        register(context, lookup, SPIRIT, GemAspect.UTILITY, 3, GemAspect.ELVES, GemAspect.MORGUL);
        register(context, lookup, SIGHT, GemAspect.UTILITY, 1, GemAspect.DWARVES);
        register(context, lookup, COMMAND, GemAspect.UTILITY, 3, GemAspect.MEN, GemAspect.MORGUL);
        register(context, lookup, COURAGE, GemAspect.DEFENSE, 1, GemAspect.MEN, GemAspect.ELVES);
        register(context, lookup, PRESENCE, GemAspect.SPEED, 2, GemAspect.ELVES, GemAspect.MORGUL);
        register(context, lookup, MIGHT, GemAspect.ATTACK, 1, GemAspect.DWARVES, GemAspect.MORGUL);
        register(context, lookup, WISDOM, GemAspect.UTILITY, 3, GemAspect.ELVES);
        register(context, lookup, LUCK, GemAspect.UTILITY, 1, GemAspect.DWARVES);
        register(context, lookup, FLAME, GemAspect.ATTACK, 2, GemAspect.ELVES, GemAspect.MORGUL);
        register(context, lookup, WATER, GemAspect.ATTACK, 1, GemAspect.MEN, GemAspect.ELVES);
        register(context, lookup, SKY, GemAspect.SPEED, 2, GemAspect.MEN, GemAspect.ELVES);
        register(context, lookup, SUCCOR, GemAspect.DEFENSE, 2, GemAspect.MEN, GemAspect.ELVES);
        register(context, lookup, EARTH, GemAspect.UTILITY, 2, GemAspect.DWARVES);
        register(context, lookup, ENDURANCE, GemAspect.DEFENSE, 2, GemAspect.DWARVES);
        register(context, lookup, SWIFTNESS, GemAspect.SPEED, 1, GemAspect.ELVES);
        register(context, lookup, KINSHIP, GemAspect.ATTACK, 2, GemAspect.MEN);
        register(context, lookup, PRESERVATION, GemAspect.DEFENSE, 1, GemAspect.ELVES);
        register(context, lookup, NATURE, GemAspect.UTILITY, 1, GemAspect.ELVES);
    }

    private static void register(Registerable<RingWord> context, RegistryEntryLookup<RingWord> lookup,
                                 RegistryKey<RingWord> key, GemAspect aspect, int minSlots, GemAspect... races) {
        DynamicRegistriesME.register(context, lookup, key, new RingWord(aspect, List.of(races), minSlots));
    }

    private static RegistryKey<RingWord> of(String name) {
        return DynamicRegistriesME.of(RING_WORD_KEY, MiddleEarth.id(name));
    }

    private static TagKey<RingWord> tag(String name) {
        return TagKey.of(RING_WORD_KEY, MiddleEarth.id(name));
    }

    public static Optional<RingWord> get(RegistryWrapper.WrapperLookup lookup, RegistryKey<RingWord> key) {
        if (lookup == null || key == null) {
            return Optional.empty();
        }
        return lookup.getOptional(RING_WORD_KEY).flatMap(registry -> registry.getOptional(key)).map(entry -> entry.value());
    }

    public static String translationKey(RegistryKey<RingWord> key) {
        return key.getValue().toTranslationKey("ring_word");
    }
}
