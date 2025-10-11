package net.sevenstars.middleearth.resources;

import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.ModEntities;
import net.sevenstars.middleearth.item.EquipmentItemsME;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventData;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventDataLookup;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeNpcSpawningData;
import net.sevenstars.middleearth.resources.datas.npcs.pools.BrigandNpcDataPool;
import net.sevenstars.middleearth.resources.datas.npcs.pools.EreborNpcDataPool;
import net.sevenstars.middleearth.resources.datas.npcs.pools.GondorianNpcDataPool;
import net.sevenstars.middleearth.utils.IdentifierUtil;
import net.sevenstars.middleearth.utils.ItemUtil;
import net.sevenstars.middleearth.world.biomes.MEBiomeKeys;

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

    public final static RegistryKey<BiomeEventData> DEFAULT = of(IdentifierUtil.create("default"));
    public final static RegistryKey<BiomeEventData> ANORIEN = of(MEBiomeKeys.ANORIEN_FOOTHILLS.getValue());
    public final static RegistryKey<BiomeEventData> PELENNOR_FIELDS = of(MEBiomeKeys.PELENNOR_FIELDS.getValue());
    public final static RegistryKey<BiomeEventData> LONELY_MOUNTAINS_TAIGA = of(MEBiomeKeys.LONELY_MOUNTAIN_TAIGA.getValue());

    private static RegistryKey<BiomeEventData> of(Identifier id) {
        return RegistryKey.of(KEY, id);
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
                new BiomeNpcSpawningData(GondorianNpcDataPool.GONDOR_FOUNTAIN_GUARD).withSkylightRequired().withMount(EntityType.HORSE, ItemUtil.getIdentifier(EquipmentItemsME.GONDORIAN_HORSE_ARMOR)),
                new BiomeNpcSpawningData(GondorianNpcDataPool.GONDOR_KNIGHT).withSkylightRequired().withWeight(3).withMount(EntityType.HORSE, ItemUtil.getIdentifier(EquipmentItemsME.GONDORIAN_HORSE_ARMOR)),
                new BiomeNpcSpawningData(GondorianNpcDataPool.GONDOR_KNIGHT).withSkylightRequired().withWeight(3),
                new BiomeNpcSpawningData(GondorianNpcDataPool.GONDOR_SOLDIER).withSkylightRequired().withWeight(8)
        )));

        register(context, registryEntryLookup, LONELY_MOUNTAINS_TAIGA, new BiomeEventData(List.of(
            new BiomeNpcSpawningData(EreborNpcDataPool.EREBOR_ELITE).withSkylightRequired().withMount(ModEntities.BROADHOOF_GOAT, ItemUtil.getIdentifier(EquipmentItemsME.BROADHOOF_GOAT_PLATE_ARMOR)),
            new BiomeNpcSpawningData(EreborNpcDataPool.EREBOR_SOLDIER).withSkylightRequired().withMount(ModEntities.BROADHOOF_GOAT, ItemUtil.getIdentifier(EquipmentItemsME.BROADHOOF_GOAT_PADDED_ARMOR), 0x50647d),
            new BiomeNpcSpawningData(EreborNpcDataPool.EREBOR_SOLDIER).withSkylightRequired().withMount(ModEntities.BROADHOOF_GOAT, ItemUtil.getIdentifier(EquipmentItemsME.BROADHOOF_GOAT_PADDED_ARMOR), 0x50647d),
            new BiomeNpcSpawningData(EreborNpcDataPool.EREBOR_MILITIA).withSkylightRequired().withWeight(3),
            new BiomeNpcSpawningData(EreborNpcDataPool.EREBOR_MILITIA).withSkylightRequired().withWeight(8)
        )));


    }

    private static BiomeEventData register(Registerable<BiomeEventData> context, RegistryEntryLookup<BiomeEventData> registryEntryLookup, RegistryKey<BiomeEventData> key, BiomeEventData biomeEventData) {
        Optional<RegistryEntry.Reference<BiomeEventData>> optionalBiomeEvent = registryEntryLookup.getOptional(key);
        optionalBiomeEvent.ifPresent(biomeReference -> context.register(key, biomeEventData));
        return biomeEventData;
    }
}
