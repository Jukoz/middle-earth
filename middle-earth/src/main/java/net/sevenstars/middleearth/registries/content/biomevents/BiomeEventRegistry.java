package net.sevenstars.middleearth.registries.content.biomevents;

import net.minecraft.registry.*;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.structure.Structure;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.datageneration.content.TranslationEntries;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.registries.content.biomevents.pools.*;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventData;
import net.sevenstars.middleearth.world.biomes.MEBiomeKeys;

public class BiomeEventRegistry {
    private static final RegistryKey<Registry<BiomeEventData>> BIOME_EVENT_KEY = DynamicRegistriesME.BIOME_EVENT;
    private static final RegistryKey<Registry<BiomeEventData>> STRUCTURE_EVENT_KEY = DynamicRegistriesME.STRUCTURE_EVENT;

    public final static RegistryKey<BiomeEventData> DEFAULT = DynamicRegistriesME.of(BIOME_EVENT_KEY, MiddleEarth.of("default"));

    public final static RegistryKey<BiomeEventData> CAVE = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.BASIC_CAVE.getValue());
    public final static RegistryKey<BiomeEventData> FUNGUS_CAVE = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.FUNGUS_CAVE.getValue());
    public final static RegistryKey<BiomeEventData> DRIPSTONE_CAVE = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.DRIPSTONE_CAVE.getValue());
    public final static RegistryKey<BiomeEventData> DOLOMITE_CAVE = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.DOLOMITE_CAVE.getValue());
    public final static RegistryKey<BiomeEventData> MOUNTAIN_CAVE = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.MOUNTAIN_CAVE.getValue());
    public final static RegistryKey<BiomeEventData> MUD_CAVE = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.MUD_CAVE.getValue());

    // region STRUCTURES
    //public final static RegistryKey<BiomeEventData> WOODLAND_REALM_HALL = DynamicRegistriesME.of(STRUCTURE_EVENT_KEY, MiddleEarth.of("woodland_realm_hall"));
    public final static RegistryKey<Structure> WLR_HALL_STRUCTURE = register("woodland_realm_hall");
    public final static RegistryKey<BiomeEventData> WOODLAND_REALM_HALL = DynamicRegistriesME.of(STRUCTURE_EVENT_KEY, WLR_HALL_STRUCTURE.getValue());
    // endregion

    // region MORDOR
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
    public final static RegistryKey<BiomeEventData> MORGUL_VALE         = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.MORGUL_VALE.getValue());
    public final static RegistryKey<BiomeEventData> MORGUL_FOREST       = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.MORGUL_FOREST.getValue());
    public final static RegistryKey<BiomeEventData> ITHILIEN_WASTES     = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.ITHILIEN_WASTES.getValue());
    public final static RegistryKey<BiomeEventData> ITHILIEN_WASTES_GLADE = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.ITHILIEN_WASTES_GLADE.getValue());
    public final static RegistryKey<BiomeEventData> ITHILIEN            = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.ITHILIEN.getValue());
    public final static RegistryKey<BiomeEventData> NURN                = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.NURN.getValue());
    public final static RegistryKey<BiomeEventData> NURN_FOREST         = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.NURN_FOREST.getValue());
    public final static RegistryKey<BiomeEventData> NURN_HILL           = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.NURN_HILL.getValue());
    public final static RegistryKey<BiomeEventData> NURN_EDGE           = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.NURN_EDGE.getValue());
    public final static RegistryKey<BiomeEventData> NURN_EDGE_WOODS     = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.NURN_EDGE_WOODS.getValue());
    public final static RegistryKey<BiomeEventData> EASTERN_NURN        = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.EASTERN_NURN.getValue());
    public final static RegistryKey<BiomeEventData> TOROGWAITH          = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.TOROGWAITH.getValue());
    public final static RegistryKey<BiomeEventData> EPHEL_DUATH_BASE    = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.EPHEL_DUATH_BASE.getValue());
    public final static RegistryKey<BiomeEventData> EPHEL_DUATH         = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.EPHEL_DUATH.getValue());
    public final static RegistryKey<BiomeEventData> ERED_LITHUI_BASE    = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.ERED_LITHUI_BASE.getValue());
    public final static RegistryKey<BiomeEventData> ERED_LITHUI         = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.ERED_LITHUI.getValue());
    public final static RegistryKey<BiomeEventData> BASALT_CAVE         = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.BASALT_CAVE.getValue());
    public final static RegistryKey<BiomeEventData> MAGMA_CAVE          = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.MAGMA_CAVE.getValue());
    // endregion

    // region GONDOR
    public final static RegistryKey<BiomeEventData> GONDOR              = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.GONDOR.getValue());
    public final static RegistryKey<BiomeEventData> OSGILIATH           = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.OSGILIATH.getValue());
    public final static RegistryKey<BiomeEventData> ANORIEN             = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.ANORIEN.getValue());
    public final static RegistryKey<BiomeEventData> BELFALAS            = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.BELFALAS.getValue());
    public final static RegistryKey<BiomeEventData> ITHILIEN_GLADE      = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.ITHILIEN_GLADE.getValue());
    public final static RegistryKey<BiomeEventData> PELENNOR_FIELDS     = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.PELENNOR_FIELDS.getValue());
    public final static RegistryKey<BiomeEventData> LAMEDON             = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.LAMEDON.getValue());
    public final static RegistryKey<BiomeEventData> LEBENNIN            = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.LEBENNIN.getValue());
    public final static RegistryKey<BiomeEventData> LOSSARNACH          = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.LOSSARNACH.getValue());
    public final static RegistryKey<BiomeEventData> LOSSARNACH_VALLEY   = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.LOSSARNACH_VALLEY.getValue());
    public final static RegistryKey<BiomeEventData> BLACKROOT_VALE      = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.BLACKROOT_VALE.getValue());
    // endregion


    // region ROHAN
    public final static RegistryKey<BiomeEventData> ROHAN              = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.ROHAN.getValue());
    public final static RegistryKey<BiomeEventData> ROHAN_FIELD        = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.ROHAN_FIELD.getValue());
    public final static RegistryKey<BiomeEventData> THE_WOLD           = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.THE_WOLD.getValue());
    public final static RegistryKey<BiomeEventData> THE_WOLD_WHEAT_FIELD = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.THE_WOLD_WHEAT_FIELD.getValue());
    // endregion

    public static void bootstrapStructureEvents(Registerable<BiomeEventData> context) {
        RegistryEntryLookup<BiomeEventData> registryEntryLookup = context.getRegistryLookup(STRUCTURE_EVENT_KEY);

        register(context, registryEntryLookup, WOODLAND_REALM_HALL, WoodlandRealmBiomeEventPool.HALL);
    }

    public static void bootstrap(Registerable<BiomeEventData> context) {
        RegistryEntryLookup<BiomeEventData> registryEntryLookup = context.getRegistryLookup(BIOME_EVENT_KEY);

        register(context, registryEntryLookup, DEFAULT, GenericHostilesBiomeEventPool.EMPTY);
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
        register(context, registryEntryLookup, MORGUL_VALE      , MordorBiomeEventPool.DEFAULT);
        register(context, registryEntryLookup, MORGUL_FOREST    , MordorBiomeEventPool.SCOUTS);
        register(context, registryEntryLookup, ITHILIEN_WASTES  , MordorBiomeEventPool.SCOUTS);
        register(context, registryEntryLookup, ITHILIEN_WASTES_GLADE, MordorBiomeEventPool.SCOUTS);
        register(context, registryEntryLookup, ITHILIEN         , MordorBiomeEventPool.ITHILIEN);
        register(context, registryEntryLookup, NURN             , MordorBiomeEventPool.NURN);
        register(context, registryEntryLookup, NURN_FOREST      , MordorBiomeEventPool.SCOUTS);
        register(context, registryEntryLookup, NURN_HILL        , MordorBiomeEventPool.NURN);
        register(context, registryEntryLookup, NURN_EDGE        , MordorBiomeEventPool.NURN);
        register(context, registryEntryLookup, NURN_EDGE_WOODS  , MordorBiomeEventPool.SCOUTS);
        register(context, registryEntryLookup, EASTERN_NURN     , MordorBiomeEventPool.SCOUTS);
        register(context, registryEntryLookup, EPHEL_DUATH_BASE , MordorBiomeEventPool.SCOUTS);
        register(context, registryEntryLookup, TOROGWAITH       , MordorBiomeEventPool.DEFAULT);
        register(context, registryEntryLookup, EPHEL_DUATH      , MordorBiomeEventPool.SCOUTS);
        register(context, registryEntryLookup, ERED_LITHUI_BASE , MordorBiomeEventPool.SCOUTS);
        register(context, registryEntryLookup, ERED_LITHUI      , MordorBiomeEventPool.SCOUTS);
        register(context, registryEntryLookup, BASALT_CAVE      , MordorBiomeEventPool.CAVE);
        register(context, registryEntryLookup, MAGMA_CAVE       , MordorBiomeEventPool.CAVE);

        register(context, registryEntryLookup, GONDOR               , GondorBiomeEventPool.PEASANT_FIEF);
        register(context, registryEntryLookup, OSGILIATH            , GondorBiomeEventPool.OSGILIATH);
        register(context, registryEntryLookup, ANORIEN              , GondorBiomeEventPool.DEFAULT);
        register(context, registryEntryLookup, BELFALAS             , GondorBiomeEventPool.DEFAULT);
        register(context, registryEntryLookup, ITHILIEN_GLADE       , GondorBiomeEventPool.DEFAULT);
        register(context, registryEntryLookup, PELENNOR_FIELDS      , GondorBiomeEventPool.DEFAULT);
        register(context, registryEntryLookup, LAMEDON              , GondorBiomeEventPool.PEASANT_FIEF);
        register(context, registryEntryLookup, LEBENNIN             , GondorBiomeEventPool.DEFAULT);
        register(context, registryEntryLookup, LOSSARNACH           , GondorBiomeEventPool.DEFAULT);
        register(context, registryEntryLookup, LOSSARNACH_VALLEY    , GondorBiomeEventPool.PEASANT_FIEF);
        register(context, registryEntryLookup, BLACKROOT_VALE       , GondorBiomeEventPool.PEASANT_FIEF);

        register(context, registryEntryLookup, ROHAN                , RohanBiomeEventPool.DEFAULT);
        register(context, registryEntryLookup, ROHAN_FIELD          , RohanBiomeEventPool.DEFAULT);
        register(context, registryEntryLookup, THE_WOLD             , RohanBiomeEventPool.DEFAULT);
        register(context, registryEntryLookup, THE_WOLD_WHEAT_FIELD , RohanBiomeEventPool.DEFAULT);
    }

    private static void register(Registerable<BiomeEventData> context, RegistryEntryLookup<BiomeEventData> registryEntryLookup, RegistryKey<BiomeEventData> registryKey, BiomeEventData element){
        DynamicRegistriesME.register(context, registryEntryLookup, registryKey, element);
        // [LANG datagen]
        // None
    }

    private static RegistryKey<Structure> register(String name) {
        return RegistryKey.of(RegistryKeys.STRUCTURE, Identifier.of(MiddleEarth.MOD_ID, name));
    }
}
