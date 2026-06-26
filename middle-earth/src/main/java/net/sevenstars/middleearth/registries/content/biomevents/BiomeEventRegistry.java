package net.sevenstars.middleearth.registries.content.biomevents;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.registries.content.biomevents.pools.GenericHostilesBiomeEventPool;
import net.sevenstars.middleearth.registries.content.biomevents.pools.MordorBiomeEventPool;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventData;
import net.sevenstars.middleearth.world.biomes.MEBiomeKeys;

public class BiomeEventRegistry {
    private static final RegistryKey<Registry<BiomeEventData>> BIOME_EVENT_KEY = DynamicRegistriesME.BIOME_EVENT;

    public final static RegistryKey<BiomeEventData> DEFAULT = DynamicRegistriesME.of(BIOME_EVENT_KEY, MiddleEarth.of("default"));

    public final static RegistryKey<BiomeEventData> CAVE = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.BASIC_CAVE.getValue());
    public final static RegistryKey<BiomeEventData> FUNGUS_CAVE = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.FUNGUS_CAVE.getValue());
    public final static RegistryKey<BiomeEventData> DRIPSTONE_CAVE = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.DRIPSTONE_CAVE.getValue());
    public final static RegistryKey<BiomeEventData> DOLOMITE_CAVE = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.DOLOMITE_CAVE.getValue());
    public final static RegistryKey<BiomeEventData> MOUNTAIN_CAVE = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.MOUNTAIN_CAVE.getValue());
    public final static RegistryKey<BiomeEventData> MUD_CAVE = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.MUD_CAVE.getValue());

    public final static RegistryKey<BiomeEventData> MORDOR = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.MORDOR.getValue());
    public final static RegistryKey<BiomeEventData> MORDOR_ASHEN_FOREST = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.MORDOR_ASHEN_FOREST.getValue());
    public final static RegistryKey<BiomeEventData> MORDOR_HILL         = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.MORDOR_HILL.getValue());
    public final static RegistryKey<BiomeEventData> MORDOR_WASTES       = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.MORDOR_WASTES.getValue());
    public final static RegistryKey<BiomeEventData> GORGOROTH           = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.GORGOROTH.getValue());
    public final static RegistryKey<BiomeEventData> GORGOROTH_ASHEN_WOODS= DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.GORGOROTH_ASHEN_WOODS.getValue());
    public final static RegistryKey<BiomeEventData> GORGOROTH_DELTA     = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.GORGOROTH_DELTA.getValue());
    public final static RegistryKey<BiomeEventData> UDUN                = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.UDUN.getValue());
    public final static RegistryKey<BiomeEventData> BROWN_LANDS         = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.BROWN_LANDS.getValue());
    public final static RegistryKey<BiomeEventData> DAGORLAD            = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.DAGORLAD.getValue());
    public final static RegistryKey<BiomeEventData> DEAD_MARSHES        = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.DEAD_MARSHES.getValue());
    public final static RegistryKey<BiomeEventData> MORGUL_VALE         = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.MORGUL_VALE.getValue());
    public final static RegistryKey<BiomeEventData> MORGUL_FOREST       = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.MORGUL_FOREST.getValue());
    public final static RegistryKey<BiomeEventData> ITHILIEN_WASTES     = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.ITHILIEN_WASTES.getValue());
    public final static RegistryKey<BiomeEventData> ITHILIEN_WASTES_GLADE = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.ITHILIEN_WASTES_GLADE.getValue());
    public final static RegistryKey<BiomeEventData> NURN                = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.NURN.getValue());
    public final static RegistryKey<BiomeEventData> NURN_FOREST         = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.NURN_FOREST.getValue());
    public final static RegistryKey<BiomeEventData> NURN_HILL           = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.NURN_HILL.getValue());
    public final static RegistryKey<BiomeEventData> NURN_EDGE           = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.NURN_EDGE.getValue());
    public final static RegistryKey<BiomeEventData> NURN_EDGE_WOODS     = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.NURN_EDGE_WOODS.getValue());
    public final static RegistryKey<BiomeEventData> EASTERN_NURN        = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.EASTERN_NURN.getValue());
    public final static RegistryKey<BiomeEventData> TOROGWAITH          = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.TOROGWAITH.getValue());
    public final static RegistryKey<BiomeEventData> EPHEL_DUATH_BASE    = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.EPHEL_DUATH_BASE.getValue());
    public final static RegistryKey<BiomeEventData> EPHEL_DUATH         = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.EPHEL_DUATH.getValue());
    public final static RegistryKey<BiomeEventData> EPHEL_DUATH_PEAKS   = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.EPHEL_DUATH_PEAKS.getValue());
    public final static RegistryKey<BiomeEventData> ERED_LITHUI_BASE    = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.ERED_LITHUI_BASE.getValue());
    public final static RegistryKey<BiomeEventData> ERED_LITHUI         = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.ERED_LITHUI.getValue());
    public final static RegistryKey<BiomeEventData> ERED_LITHUI_PEAKS   = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.ERED_LITHUI_PEAKS.getValue());
    public final static RegistryKey<BiomeEventData> WASTE_POND          = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.WASTE_POND.getValue());
    public final static RegistryKey<BiomeEventData> BASALT_CAVE         = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.BASALT_CAVE.getValue());
    public final static RegistryKey<BiomeEventData> MAGMA_CAVE          = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.MAGMA_CAVE.getValue());


    public static void bootstrap(Registerable<BiomeEventData> context) {
        RegistryEntryLookup<BiomeEventData> registryEntryLookup = context.getRegistryLookup(BIOME_EVENT_KEY);

        register(context, registryEntryLookup, DEFAULT, GenericHostilesBiomeEventPool.DEFAULT);
        register(context, registryEntryLookup, CAVE, GenericHostilesBiomeEventPool.CAVE);
        register(context, registryEntryLookup, FUNGUS_CAVE, GenericHostilesBiomeEventPool.CAVE);
        register(context, registryEntryLookup, DRIPSTONE_CAVE, GenericHostilesBiomeEventPool.CAVE);
        register(context, registryEntryLookup, DOLOMITE_CAVE, GenericHostilesBiomeEventPool.CAVE);
        register(context, registryEntryLookup, MOUNTAIN_CAVE, GenericHostilesBiomeEventPool.CAVE);
        register(context, registryEntryLookup, MUD_CAVE, GenericHostilesBiomeEventPool.CAVE);

        register(context, registryEntryLookup, MORDOR, MordorBiomeEventPool.DEFAULT);
        register(context, registryEntryLookup, MORDOR_ASHEN_FOREST, MordorBiomeEventPool.SCOUTS);
        register(context, registryEntryLookup, MORDOR_HILL      , MordorBiomeEventPool.DEFAULT);
        register(context, registryEntryLookup, MORDOR_WASTES    , MordorBiomeEventPool.DEFAULT);
        register(context, registryEntryLookup, GORGOROTH        , MordorBiomeEventPool.DEFAULT);
        register(context, registryEntryLookup, GORGOROTH_ASHEN_WOODS, MordorBiomeEventPool.SCOUTS);
        register(context, registryEntryLookup, GORGOROTH_DELTA  , MordorBiomeEventPool.DEFAULT);
        register(context, registryEntryLookup, UDUN             , MordorBiomeEventPool.DEFAULT);
        register(context, registryEntryLookup, BROWN_LANDS      , MordorBiomeEventPool.SCOUTS);
        register(context, registryEntryLookup, DAGORLAD         , MordorBiomeEventPool.SCOUTS);
        register(context, registryEntryLookup, DEAD_MARSHES     , GenericHostilesBiomeEventPool.EMPTY);
        register(context, registryEntryLookup, MORGUL_VALE      , MordorBiomeEventPool.DEFAULT);
        register(context, registryEntryLookup, MORGUL_FOREST    , MordorBiomeEventPool.SCOUTS);
        register(context, registryEntryLookup, ITHILIEN_WASTES  , MordorBiomeEventPool.SCOUTS);
        register(context, registryEntryLookup, ITHILIEN_WASTES_GLADE, MordorBiomeEventPool.SCOUTS);
        register(context, registryEntryLookup, NURN             , MordorBiomeEventPool.NURN);
        register(context, registryEntryLookup, NURN_FOREST      , MordorBiomeEventPool.SCOUTS);
        register(context, registryEntryLookup, NURN_HILL        , MordorBiomeEventPool.NURN);
        register(context, registryEntryLookup, NURN_EDGE        , MordorBiomeEventPool.NURN);
        register(context, registryEntryLookup, NURN_EDGE_WOODS  , MordorBiomeEventPool.SCOUTS);
        register(context, registryEntryLookup, EASTERN_NURN     , MordorBiomeEventPool.SCOUTS);
        register(context, registryEntryLookup, EPHEL_DUATH_BASE , MordorBiomeEventPool.SCOUTS);
        register(context, registryEntryLookup, TOROGWAITH       , MordorBiomeEventPool.DEFAULT);
        register(context, registryEntryLookup, EPHEL_DUATH      , MordorBiomeEventPool.SCOUTS);
        register(context, registryEntryLookup, EPHEL_DUATH_PEAKS, GenericHostilesBiomeEventPool.EMPTY);
        register(context, registryEntryLookup, ERED_LITHUI_BASE , MordorBiomeEventPool.SCOUTS);
        register(context, registryEntryLookup, ERED_LITHUI      , MordorBiomeEventPool.SCOUTS);
        register(context, registryEntryLookup, ERED_LITHUI_PEAKS, GenericHostilesBiomeEventPool.EMPTY);
        register(context, registryEntryLookup, WASTE_POND       , GenericHostilesBiomeEventPool.EMPTY);
        register(context, registryEntryLookup, BASALT_CAVE      , MordorBiomeEventPool.CAVE);
        register(context, registryEntryLookup, MAGMA_CAVE       , MordorBiomeEventPool.CAVE);
    }

    private static void register(Registerable<BiomeEventData> context, RegistryEntryLookup<BiomeEventData> registryEntryLookup, RegistryKey<BiomeEventData> registryKey, BiomeEventData element){
        DynamicRegistriesME.register(context, registryEntryLookup, registryKey, element);
        // [LANG datagen]
        // None
    }
}
