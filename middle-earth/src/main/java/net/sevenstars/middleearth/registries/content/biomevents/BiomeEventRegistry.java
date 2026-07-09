package net.sevenstars.middleearth.registries.content.biomevents;

import net.minecraft.registry.*;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.structure.Structure;
import net.sevenstars.middleearth.MiddleEarth;
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
    public final static RegistryKey<Structure> WLR_HALL_STRUCTURE = register("woodland_realm_hall");
    public final static RegistryKey<BiomeEventData> WOODLAND_REALM_HALL = DynamicRegistriesME.of(STRUCTURE_EVENT_KEY, WLR_HALL_STRUCTURE.getValue());
    public final static RegistryKey<Structure> LONGBEARDS_HALL_STRUCTURE = register("longbeards_hall");
    public final static RegistryKey<BiomeEventData> LONGBEARDS_HALL = DynamicRegistriesME.of(STRUCTURE_EVENT_KEY, LONGBEARDS_HALL_STRUCTURE.getValue());
    // endregion

    // region BRIGDANDS //
    public final static RegistryKey<BiomeEventData> ANDUIN_VALES        = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.ANDUIN_VALES.getValue());
    public final static RegistryKey<BiomeEventData> ENEDWAITH           = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.ENEDWAITH.getValue());
    public final static RegistryKey<BiomeEventData> MINHIRIATH          = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.MINHIRIATH.getValue());
    public final static RegistryKey<BiomeEventData> OLD_RHUDAUR         = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.OLD_RHUDAUR.getValue());
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

    // region DOL GULDUR //
    public final static RegistryKey<BiomeEventData> DOL_GULDUR          = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.DOL_GULDUR.getValue());
    public final static RegistryKey<BiomeEventData> DOL_GULDUR_HILL     = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.DOL_GULDUR_HILL.getValue());
    public final static RegistryKey<BiomeEventData> DARK_MIRKWOOD       = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.DARK_MIRKWOOD.getValue());
    public final static RegistryKey<BiomeEventData> DARK_MIRKWOOD_EDGE  = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.DARK_MIRKWOOD_EDGE.getValue());
    // endregion

    // region ISENGARD //
    public final static RegistryKey<BiomeEventData> ISENGARD            = of(MEBiomeKeys.ISENGARD);
    public final static RegistryKey<BiomeEventData> NAN_CURUNIR         = of(MEBiomeKeys.NAN_CURUNIR);
    // endregion

    // region GUNDABAD
    public final static RegistryKey<BiomeEventData> GUNDABAD_PLAINS     = of(MEBiomeKeys.GUNDABAD_PLAINS);
    public final static RegistryKey<BiomeEventData> GUNDABAD_WOODS      = of(MEBiomeKeys.GUNDABAD_WOODS);
    public final static RegistryKey<BiomeEventData> MOUNT_GUNDABAD_BASE = of(MEBiomeKeys.MOUNT_GUNDABAD_BASE);
    public final static RegistryKey<BiomeEventData> MOUNT_GUNDABAD      = of(MEBiomeKeys.MOUNT_GUNDABAD);
    public final static RegistryKey<BiomeEventData> MISTY_MOUNTAINS     = of(MEBiomeKeys.MISTY_MOUNTAINS);
    // endregion

    // MORIA
    public final static RegistryKey<BiomeEventData> CELEBDIL_BASE       = of(MEBiomeKeys.CELEBDIL_BASE);
    public final static RegistryKey<BiomeEventData> EREGION             = of(MEBiomeKeys.EREGION);

    // region GONDOR
    public final static RegistryKey<BiomeEventData> GONDOR              = of(MEBiomeKeys.GONDOR);
    public final static RegistryKey<BiomeEventData> OSGILIATH           = of(MEBiomeKeys.OSGILIATH);
    public final static RegistryKey<BiomeEventData> ANORIEN             = of(MEBiomeKeys.ANORIEN);
    public final static RegistryKey<BiomeEventData> BELFALAS            = of(MEBiomeKeys.BELFALAS);
    public final static RegistryKey<BiomeEventData> ITHILIEN_GLADE      = of(MEBiomeKeys.ITHILIEN_GLADE);
    public final static RegistryKey<BiomeEventData> PELENNOR_FIELDS     = of(MEBiomeKeys.PELENNOR_FIELDS);
    public final static RegistryKey<BiomeEventData> LAMEDON             = of(MEBiomeKeys.LAMEDON);
    public final static RegistryKey<BiomeEventData> LEBENNIN            = of(MEBiomeKeys.LEBENNIN);
    public final static RegistryKey<BiomeEventData> LOSSARNACH          = of(MEBiomeKeys.LOSSARNACH);
    public final static RegistryKey<BiomeEventData> LOSSARNACH_VALLEY   = of(MEBiomeKeys.LOSSARNACH_VALLEY);
    public final static RegistryKey<BiomeEventData> BLACKROOT_VALE      = of(MEBiomeKeys.BLACKROOT_VALE);
    // endregion

    // region ROHAN
    public final static RegistryKey<BiomeEventData> ROHAN              = of(MEBiomeKeys.ROHAN);
    public final static RegistryKey<BiomeEventData> ROHAN_FIELD        = of(MEBiomeKeys.ROHAN_FIELD);
    public final static RegistryKey<BiomeEventData> THE_WOLD           = of(MEBiomeKeys.THE_WOLD);
    public final static RegistryKey<BiomeEventData> THE_WOLD_WHEAT_FIELD = of(MEBiomeKeys.THE_WOLD_WHEAT_FIELD);
    // endregion

    // region DALE
    public final static RegistryKey<BiomeEventData> DALE                = of(MEBiomeKeys.DALE);
    public final static RegistryKey<BiomeEventData> DALE_CITY           = of(MEBiomeKeys.DALE_CITY);
    public final static RegistryKey<BiomeEventData> DALE_MEADOW         = of(MEBiomeKeys.DALE_MEADOW);
    // endregion

    // region LONGBEARDS
    public final static RegistryKey<BiomeEventData> LONELY_MOUNTAIN_TAIGA       = of(MEBiomeKeys.LONELY_MOUNTAIN_TAIGA);
    public final static RegistryKey<BiomeEventData> LONELY_MOUNTAIN_FOOTHILLS   = of(MEBiomeKeys.LONELY_MOUNTAIN_FOOTHILLS);
    public final static RegistryKey<BiomeEventData> IRON_HILLS_PLAINS           = of(MEBiomeKeys.IRON_HILLS_PLAINS);
    public final static RegistryKey<BiomeEventData> IRON_HILLS_BASE             = of(MEBiomeKeys.IRON_HILLS_BASE);
    public final static RegistryKey<BiomeEventData> GREY_PLAINS                 = of(MEBiomeKeys.GREY_PLAINS);
    // endregion

    // region LOTHLORIEN
    public final static RegistryKey<BiomeEventData> LOTHLORIEN          = of(MEBiomeKeys.LOTHLORIEN);
    public final static RegistryKey<BiomeEventData> LOTHLORIEN_GLADE    = of(MEBiomeKeys.LOTHLORIEN_GLADE);
    public final static RegistryKey<BiomeEventData> LOTHLORIEN_BLOSSOM  = of(MEBiomeKeys.LOTHLORIEN_BLOSSOM);
    public final static RegistryKey<BiomeEventData> LORIEN_EDGE         = of(MEBiomeKeys.LORIEN_EDGE);
    // endregion

    // region WOODLAND_REALM
    public final static RegistryKey<BiomeEventData> WOODLAND_REALM      = of(MEBiomeKeys.WOODLAND_REALM);
    public final static RegistryKey<BiomeEventData> WOODLAND_GLADE      = of(MEBiomeKeys.WOODLAND_GLADE);
    public final static RegistryKey<BiomeEventData> AUTUMN_WOODLAND     = of(MEBiomeKeys.AUTUMN_WOODLAND);
    public final static RegistryKey<BiomeEventData> WOODLAND_FOOTHILLS  = of(MEBiomeKeys.WOODLAND_FOOTHILLS);
    public final static RegistryKey<BiomeEventData> MIRKWOOD            = of(MEBiomeKeys.MIRKWOOD);
    // endregion

    public final static RegistryKey<BiomeEventData> SHIRE               = of(MEBiomeKeys.SHIRE);
    public final static RegistryKey<BiomeEventData> SHIRE_EDGE          = of(MEBiomeKeys.SHIRE_EDGE);

    private static RegistryKey<BiomeEventData> of(RegistryKey<Biome> key){
        return DynamicRegistriesME.of(BIOME_EVENT_KEY, key.getValue());
    }

    public static void bootstrapStructureEvents(Registerable<BiomeEventData> context) {
        RegistryEntryLookup<BiomeEventData> registryEntryLookup = context.getRegistryLookup(STRUCTURE_EVENT_KEY);

        register(context, registryEntryLookup, WOODLAND_REALM_HALL, WoodlandRealmBiomeEventPool.HALL);
        register(context, registryEntryLookup, LONGBEARDS_HALL, LongbeardsBiomeEventPool.DEFAULT);
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

        register(context, registryEntryLookup, ANDUIN_VALES     , GenericHostilesBiomeEventPool.BRIGANDS);
        register(context, registryEntryLookup, ENEDWAITH        , GenericHostilesBiomeEventPool.BRIGANDS);
        register(context, registryEntryLookup, MINHIRIATH       , GenericHostilesBiomeEventPool.BRIGANDS);
        register(context, registryEntryLookup, OLD_RHUDAUR      , GenericHostilesBiomeEventPool.BRIGANDS);

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

        register(context, registryEntryLookup, DOL_GULDUR       , MordorBiomeEventPool.DOL_GULDUR);
        register(context, registryEntryLookup, DOL_GULDUR_HILL  , MordorBiomeEventPool.DOL_GULDUR);
        register(context, registryEntryLookup, DARK_MIRKWOOD    , MordorBiomeEventPool.DOL_GULDUR);
        register(context, registryEntryLookup, DARK_MIRKWOOD_EDGE, MordorBiomeEventPool.DOL_GULDUR);

        register(context, registryEntryLookup, ISENGARD     , IsengardBiomeEventPool.DEFAULT);
        register(context, registryEntryLookup, NAN_CURUNIR  , IsengardBiomeEventPool.SCOUTS);

        register(context, registryEntryLookup, GUNDABAD_PLAINS      , GundabadBiomeEventPool.DEFAULT);
        register(context, registryEntryLookup, GUNDABAD_WOODS       , GundabadBiomeEventPool.SCOUTS);
        register(context, registryEntryLookup, MOUNT_GUNDABAD_BASE  , GundabadBiomeEventPool.DEFAULT);
        register(context, registryEntryLookup, MOUNT_GUNDABAD       , GundabadBiomeEventPool.DEFAULT);
        register(context, registryEntryLookup, MISTY_MOUNTAINS      , GundabadBiomeEventPool.SCOUTS);

        register(context, registryEntryLookup, CELEBDIL_BASE        , MoriaBiomeEventPool.DEFAULT);
        register(context, registryEntryLookup, EREGION              , MoriaBiomeEventPool.EREGION);

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

        register(context, registryEntryLookup, DALE                 , DaleBiomeEventPool.DEFAULT);
        register(context, registryEntryLookup, DALE_CITY            , DaleBiomeEventPool.DEFAULT);
        register(context, registryEntryLookup, DALE_MEADOW          , DaleBiomeEventPool.DEFAULT);

        register(context, registryEntryLookup, LONELY_MOUNTAIN_TAIGA        , LongbeardsBiomeEventPool.DEFAULT);
        register(context, registryEntryLookup, LONELY_MOUNTAIN_FOOTHILLS    , LongbeardsBiomeEventPool.DEFAULT);
        register(context, registryEntryLookup, IRON_HILLS_PLAINS            , LongbeardsBiomeEventPool.DEFAULT);
        register(context, registryEntryLookup, IRON_HILLS_BASE              , LongbeardsBiomeEventPool.DEFAULT);
        register(context, registryEntryLookup, GREY_PLAINS                  , LongbeardsBiomeEventPool.GREY_PLAINS);

        register(context, registryEntryLookup, LOTHLORIEN           , LothlorienBiomeEventPool.DEFAULT);
        register(context, registryEntryLookup, LOTHLORIEN_GLADE     , LothlorienBiomeEventPool.DEFAULT);
        register(context, registryEntryLookup, LOTHLORIEN_BLOSSOM   , LothlorienBiomeEventPool.DEFAULT);
        register(context, registryEntryLookup, LORIEN_EDGE          , LothlorienBiomeEventPool.SCOUTS);

        register(context, registryEntryLookup, WOODLAND_REALM       , WoodlandRealmBiomeEventPool.DEFAULT);
        register(context, registryEntryLookup, WOODLAND_GLADE       , WoodlandRealmBiomeEventPool.DEFAULT);
        register(context, registryEntryLookup, AUTUMN_WOODLAND      , WoodlandRealmBiomeEventPool.DEFAULT);
        register(context, registryEntryLookup, WOODLAND_FOOTHILLS   , WoodlandRealmBiomeEventPool.SCOUTS);
        register(context, registryEntryLookup, MIRKWOOD             , WoodlandRealmBiomeEventPool.SCOUTS);

        register(context, registryEntryLookup, SHIRE        , ShireBiomeEventPool.DEFAULT);
        register(context, registryEntryLookup, SHIRE_EDGE   , ShireBiomeEventPool.DEFAULT);
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
