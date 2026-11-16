package net.sevenstars.middleearth.registries.content.rings;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.random.Random;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.resources.datas.rings.RingCurse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/** The built-in ring curses (10 minor, 10 major) and the curse roll for a finished ring. */
public class RingCurseRegistry {
    private static final RegistryKey<Registry<RingCurse>> RING_CURSE_KEY = DynamicRegistriesME.RING_CURSE;
    private static final int POTENCY_PER_CURSE = 25;
    private static final int MAX_CURSES = 4;

    public static final RegistryKey<RingCurse> SLEEPLESS = of("sleepless");
    public static final RegistryKey<RingCurse> GNAWING_HUNGER = of("gnawing_hunger");
    public static final RegistryKey<RingCurse> HEAVY_TREAD = of("heavy_tread");
    public static final RegistryKey<RingCurse> TARNISHED_LUCK = of("tarnished_luck");
    public static final RegistryKey<RingCurse> BRITTLE_TOUCH = of("brittle_touch");
    public static final RegistryKey<RingCurse> MURMURING = of("murmuring");
    public static final RegistryKey<RingCurse> COLD_BLOOD = of("cold_blood");
    public static final RegistryKey<RingCurse> BEAST_SHUNNED = of("beast_shunned");
    public static final RegistryKey<RingCurse> UNSTEADY_HAND = of("unsteady_hand");
    public static final RegistryKey<RingCurse> MARKED = of("marked");

    public static final RegistryKey<RingCurse> BINDING = of("binding");
    public static final RegistryKey<RingCurse> WITHERING = of("withering");
    public static final RegistryKey<RingCurse> FADING = of("fading");
    public static final RegistryKey<RingCurse> SUNBANE = of("sunbane");
    public static final RegistryKey<RingCurse> FEA_LEAK = of("fea_leak");
    public static final RegistryKey<RingCurse> BEACON = of("beacon");
    public static final RegistryKey<RingCurse> FAITHLESS = of("faithless");
    public static final RegistryKey<RingCurse> TREACHEROUS = of("treacherous");
    public static final RegistryKey<RingCurse> GRASPING_SHADOW = of("grasping_shadow");
    public static final RegistryKey<RingCurse> STRETCHED_THIN = of("stretched_thin");

    public static final List<RegistryKey<RingCurse>> MINOR = List.of(SLEEPLESS, GNAWING_HUNGER, HEAVY_TREAD,
            TARNISHED_LUCK, BRITTLE_TOUCH, MURMURING, COLD_BLOOD, BEAST_SHUNNED, UNSTEADY_HAND, MARKED);
    public static final List<RegistryKey<RingCurse>> MAJOR = List.of(BINDING, WITHERING, FADING, SUNBANE, FEA_LEAK,
            BEACON, FAITHLESS, TREACHEROUS, GRASPING_SHADOW, STRETCHED_THIN);

    public static void bootstrap(Registerable<RingCurse> context) {
        RegistryEntryLookup<RingCurse> lookup = context.getRegistryLookup(RING_CURSE_KEY);
        MINOR.forEach(key -> DynamicRegistriesME.register(context, lookup, key, new RingCurse(RingCurse.Severity.MINOR, 1)));
        MAJOR.forEach(key -> DynamicRegistriesME.register(context, lookup, key, new RingCurse(RingCurse.Severity.MAJOR, 1)));
    }

    /**
     * One curse per started 25% of curse potency (at most 4), each major with a chance equal to the potency,
     * drawn by weight without repeats; falls back to the other severity when one runs out.
     */
    public static List<RegistryKey<RingCurse>> roll(RegistryWrapper.WrapperLookup lookup, Random random, int cursePotency) {
        List<RegistryKey<RingCurse>> rolled = new ArrayList<>();
        if (cursePotency <= 0) {
            return rolled;
        }
        List<RegistryEntry.Reference<RingCurse>> pool = new ArrayList<>(lookup.getOrThrow(RING_CURSE_KEY).streamEntries().toList());
        int count = Math.min(MAX_CURSES, (cursePotency + POTENCY_PER_CURSE - 1) / POTENCY_PER_CURSE);
        for (int i = 0; i < count && !pool.isEmpty(); i++) {
            RingCurse.Severity severity = random.nextInt(100) < cursePotency ? RingCurse.Severity.MAJOR : RingCurse.Severity.MINOR;
            List<RegistryEntry.Reference<RingCurse>> candidates = pool.stream().filter(entry -> entry.value().severity() == severity).toList();
            RegistryEntry.Reference<RingCurse> picked = weighted(random, candidates.isEmpty() ? pool : candidates);
            pool.remove(picked);
            rolled.add(picked.registryKey());
        }
        return rolled;
    }

    private static RegistryEntry.Reference<RingCurse> weighted(Random random, List<RegistryEntry.Reference<RingCurse>> entries) {
        int total = entries.stream().mapToInt(entry -> entry.value().weight()).sum();
        int pick = random.nextInt(total);
        for (RegistryEntry.Reference<RingCurse> entry : entries) {
            pick -= entry.value().weight();
            if (pick < 0) {
                return entry;
            }
        }
        return entries.getLast();
    }

    private static RegistryKey<RingCurse> of(String name) {
        return DynamicRegistriesME.of(RING_CURSE_KEY, MiddleEarth.id(name));
    }

    public static Optional<RingCurse> get(RegistryWrapper.WrapperLookup lookup, RegistryKey<RingCurse> key) {
        if (lookup == null || key == null) {
            return Optional.empty();
        }
        return lookup.getOptional(RING_CURSE_KEY).flatMap(registry -> registry.getOptional(key)).map(entry -> entry.value());
    }

    public static String translationKey(RegistryKey<RingCurse> key) {
        return key.getValue().toTranslationKey("ring_curse");
    }
}
