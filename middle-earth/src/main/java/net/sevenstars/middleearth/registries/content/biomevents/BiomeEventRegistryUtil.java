package net.sevenstars.middleearth.registries.content.biomevents;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.registries.content.biomevents.pools.GenericHostilesBiomeEventPool;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventData;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public final class BiomeEventRegistryUtil {
    private static final ResourceKey<Registry<BiomeEventData>> BIOME_EVENT_KEY = DynamicRegistriesME.BIOME_EVENT;
    private static final Set<ResourceKey<Biome>> BIOME_ENTRIES = new LinkedHashSet<>();
    private static final ThreadLocal<Set<ResourceLocation>> CURRENT_REGISTRATIONS = new ThreadLocal<>();

    private BiomeEventRegistryUtil() {
    }

    public static synchronized void addBiomeEntry(ResourceKey<Biome> biome) {
        BIOME_ENTRIES.add(biome);
    }

    public static void beginRegistration() {
        CURRENT_REGISTRATIONS.set(new HashSet<>());
    }

    public static void register(
            BootstrapContext<BiomeEventData> context,
            HolderGetter<BiomeEventData> registryLookup,
            ResourceKey<BiomeEventData> key,
            BiomeEventData data) {
        DynamicRegistriesME.register(context, registryLookup, key, data);
        Set<ResourceLocation> registrations = CURRENT_REGISTRATIONS.get();
        if (registrations != null) {
            registrations.add(key.location());
        }
    }

    public static void registerDefaults(
            BootstrapContext<BiomeEventData> context,
            HolderGetter<BiomeEventData> registryLookup) {
        Set<ResourceLocation> registrations = CURRENT_REGISTRATIONS.get();
        List<ResourceKey<Biome>> biomeEntries;
        synchronized (BiomeEventRegistryUtil.class) {
            biomeEntries = List.copyOf(BIOME_ENTRIES);
        }

        try {
            for (ResourceKey<Biome> biome : biomeEntries) {
                ResourceKey<BiomeEventData> eventKey = of(biome);
                if (registrations == null || !registrations.contains(eventKey.location())) {
                    DynamicRegistriesME.register(
                            context,
                            registryLookup,
                            eventKey,
                            GenericHostilesBiomeEventPool.EMPTY
                    );
                }
            }
        } finally {
            CURRENT_REGISTRATIONS.remove();
        }
    }

    public static ResourceKey<BiomeEventData> of(ResourceKey<Biome> key) {
        return DynamicRegistriesME.of(BIOME_EVENT_KEY, key.location());
    }

    public static ResourceKey<Structure> structureKey(String name) {
        return ResourceKey.create(Registries.STRUCTURE, MiddleEarth.of(name));
    }
}
