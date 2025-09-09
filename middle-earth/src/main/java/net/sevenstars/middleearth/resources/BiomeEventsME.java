package net.sevenstars.middleearth.resources;

import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventData;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventDataLookup;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeNpcSpawningData;
import net.sevenstars.middleearth.resources.datas.npcs.pools.BrigandNpcDataPool;
import net.sevenstars.middleearth.resources.datas.npcs.pools.GondorianNpcDataPool;

import java.util.List;
import java.util.Optional;

/**
 * Middle-earth mod npc wild spawn condition registry<br>
 * To fetch a race during runtime, use : {@link BiomeEventDataLookup#findNpcDataForBiome(World, RegistryEntry)}<br>
 * <b><u>Datadriven content, do not use during runtime!</u></b>
 * <hr>
 */
public class BiomeEventsME {
    public static final RegistryKey<Registry<BiomeEventData>> KEY = RegistryKey.ofRegistry(Identifier.of(MiddleEarth.MOD_ID, "biome_events"));

    public final static RegistryKey<BiomeEventData> DEFAULT = of("default");
    public final static RegistryKey<BiomeEventData> ANORIEN = of("anorien");
    public final static RegistryKey<BiomeEventData> PELENNOR_FIELDS = of("pelennor_fields");

    private static RegistryKey<BiomeEventData> of(String name) {
        return RegistryKey.of(KEY, Identifier.of(MiddleEarth.MOD_ID, name));
    }

    public static void register(){
        MiddleEarth.LOGGER.logDebugMsg("Registering Dynamic Biome Events for " + MiddleEarth.MOD_ID);
        DynamicRegistries.registerSynced(KEY, BiomeEventData.CODEC);
    }

    public static void bootstrap(Registerable<BiomeEventData> context) {
        RegistryEntryLookup<BiomeEventData> registryEntryLookup = context.getRegistryLookup(KEY);

        register(context, registryEntryLookup, DEFAULT, new BiomeEventData(List.of(
            new BiomeNpcSpawningData(BrigandNpcDataPool.BRIGAND_CHIEFTAIN).withMount(EntityType.HORSE),
            new BiomeNpcSpawningData(BrigandNpcDataPool.BRIGAND_CHIEFTAIN).withWeight(2),
            new BiomeNpcSpawningData(BrigandNpcDataPool.BRIGAND_MERCENARY).withWeight(3),
            new BiomeNpcSpawningData(BrigandNpcDataPool.BRIGAND_THIEF).withWeight(5),
            new BiomeNpcSpawningData(BrigandNpcDataPool.BRIGAND_THUG).withWeight(5)
        )));

        register(context, registryEntryLookup, ANORIEN, new BiomeEventData(List.of(
                new BiomeNpcSpawningData(GondorianNpcDataPool.GONDOR_SOLDIER).withWeight(2),
                new BiomeNpcSpawningData(GondorianNpcDataPool.GONDOR_MILITIA),
                new BiomeNpcSpawningData(GondorianNpcDataPool.GONDOR_MILITIA)
        )));

        register(context, registryEntryLookup, PELENNOR_FIELDS, new BiomeEventData(List.of(
                new BiomeNpcSpawningData(GondorianNpcDataPool.GONDOR_FOUNTAIN_GUARD).withSkylightRequired(),
                new BiomeNpcSpawningData(GondorianNpcDataPool.GONDOR_FOUNTAIN_GUARD).withSkylightRequired().withMount(EntityType.HORSE, Identifier.of("leather_horse_armor")),
                new BiomeNpcSpawningData(GondorianNpcDataPool.GONDOR_KNIGHT).withSkylightRequired().withWeight(3),
                new BiomeNpcSpawningData(GondorianNpcDataPool.GONDOR_SOLDIER).withSkylightRequired().withWeight(8)
        )));
    }

    private static BiomeEventData register(Registerable<BiomeEventData> context, RegistryEntryLookup<BiomeEventData> registryEntryLookup, RegistryKey<BiomeEventData> key, BiomeEventData biomeEventData) {
        Optional<RegistryEntry.Reference<BiomeEventData>> optionalBiomeEvent = registryEntryLookup.getOptional(key);
        optionalBiomeEvent.ifPresent(biomeReference -> context.register(key, biomeEventData));
        return biomeEventData;
    }
}
