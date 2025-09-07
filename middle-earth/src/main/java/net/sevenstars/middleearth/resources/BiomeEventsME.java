package net.sevenstars.middleearth.resources;

import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventData;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventDataLookup;
import net.sevenstars.middleearth.resources.datas.npcs.pools.GondorianNpcDataPool;
import net.sevenstars.middleearth.world.biomes.MEBiomeKeys;

import java.util.Optional;

/**
 * Middle-earth mod npc wild spawn condition registry<br>
 * To fetch a race during runtime, use : {@link BiomeEventDataLookup#findNpcDataForBiome(World, Biome)}<br>
 * <b><u>Datadriven content, do not use during runtime!</u></b>
 * <hr>
 */
public class BiomeEventsME {
    public static final RegistryKey<Registry<BiomeEventData>> KEY = RegistryKey.ofRegistry(Identifier.of(MiddleEarth.MOD_ID, "biome_events"));

    public final static RegistryKey<BiomeEventData> ANORIEN = of("anorien_events");
    public final static RegistryKey<BiomeEventData> PELENNOR_FIELDS = of("pelennor_fields_events");

    private static RegistryKey<BiomeEventData> of(String name) {
        return RegistryKey.of(KEY, Identifier.of(MiddleEarth.MOD_ID, name));
    }

    public static void register(){
        MiddleEarth.LOGGER.logDebugMsg("Registering Dynamic Biome Events for " + MiddleEarth.MOD_ID);
        DynamicRegistries.registerSynced(KEY, BiomeEventData.CODEC);
    }

    public static void bootstrap(Registerable<BiomeEventData> context) {
        RegistryEntryLookup<BiomeEventData> registryEntryLookup = context.getRegistryLookup(KEY);
        register(context, registryEntryLookup, ANORIEN, new BiomeEventData(MEBiomeKeys.ANORIEN, GondorianNpcDataPool.GONDOR_SOLDIER));
        register(context, registryEntryLookup, PELENNOR_FIELDS, new BiomeEventData(MEBiomeKeys.PELENNOR_FIELDS, GondorianNpcDataPool.GONDOR_FOUNTAIN_GUARD));
    }

    private static BiomeEventData register(Registerable<BiomeEventData> context, RegistryEntryLookup<BiomeEventData> registryEntryLookup, RegistryKey<BiomeEventData> key, BiomeEventData biomeEventData) {
        Optional<RegistryEntry.Reference<BiomeEventData>> optionalBiomeEvent = registryEntryLookup.getOptional(key);
        optionalBiomeEvent.ifPresent(biomeReference -> context.register(key, biomeEventData));
        return biomeEventData;
    }
}
