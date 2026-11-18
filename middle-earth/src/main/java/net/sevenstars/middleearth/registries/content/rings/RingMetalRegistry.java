package net.sevenstars.middleearth.registries.content.rings;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.text.TextColor;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.forge.MetalTypes;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.resources.datas.rings.RingMetal;

import java.util.List;
import java.util.Optional;

/** The built-in ring metals and their slot counts. */
public class RingMetalRegistry {
    private static final RegistryKey<Registry<RingMetal>> RING_METAL_KEY = DynamicRegistriesME.RING_METAL;

    public static final RegistryKey<RingMetal> COPPER = of("copper");
    public static final RegistryKey<RingMetal> IRON = of("iron");
    public static final RegistryKey<RingMetal> SILVER = of("silver");
    public static final RegistryKey<RingMetal> GOLD = of("gold");
    public static final RegistryKey<RingMetal> MITHRIL = of("mithril");

    public static final List<RegistryKey<RingMetal>> ALL = List.of(COPPER, IRON, SILVER, GOLD, MITHRIL);

    public static void bootstrap(Registerable<RingMetal> context) {
        RegistryEntryLookup<RingMetal> lookup = context.getRegistryLookup(RING_METAL_KEY);
        register(context, lookup, COPPER, metal(MetalTypes.COPPER, 0xE77C56, 1));
        register(context, lookup, IRON, metal(MetalTypes.IRON, 0xA6A4A0, 2));
        register(context, lookup, SILVER, metal(MetalTypes.SILVER, 0xC1D5D5, 2));
        register(context, lookup, GOLD, metal(MetalTypes.GOLD, 0xF4E063, 3));
        register(context, lookup, MITHRIL, metal(MetalTypes.MITHRIL, 0xD9DFE7, 4));
    }

    private static RingMetal metal(MetalTypes type, int color, int slots) {
        return new RingMetal(type, TextColor.fromRgb(color), slots);
    }

    private static void register(Registerable<RingMetal> context, RegistryEntryLookup<RingMetal> lookup,
                                 RegistryKey<RingMetal> key, RingMetal element) {
        DynamicRegistriesME.register(context, lookup, key, element);
    }

    private static RegistryKey<RingMetal> of(String name) {
        return DynamicRegistriesME.of(RING_METAL_KEY, MiddleEarth.id(name));
    }

    public static Optional<RingMetal> get(RegistryWrapper.WrapperLookup lookup, RegistryKey<RingMetal> key) {
        if (lookup == null || key == null) {
            return Optional.empty();
        }
        return lookup.getOptional(RING_METAL_KEY).flatMap(registry -> registry.getOptional(key)).map(entry -> entry.value());
    }

    public static String translationKey(RegistryKey<RingMetal> key) {
        return key.getValue().toTranslationKey("ring_metal");
    }

    public static String name(RegistryKey<RingMetal> key) {
        return key.getValue().getPath();
    }
}
