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
import net.sevenstars.middleearth.registries.content.biomevents.pools.*;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventData;
import net.sevenstars.middleearth.world.biomes.MEBiomeKeys;

public class BiomeEventRegistry {
    private static final ResourceKey<Registry<BiomeEventData>> BIOME_EVENT_KEY = DynamicRegistriesME.BIOME_EVENT;
    private static final ResourceKey<Registry<BiomeEventData>> STRUCTURE_EVENT_KEY = DynamicRegistriesME.STRUCTURE_EVENT;

    public final static ResourceKey<BiomeEventData> DEFAULT = DynamicRegistriesME.of(BIOME_EVENT_KEY, MiddleEarth.of("default"));

    // region CAVES
    public final static ResourceKey<BiomeEventData> BASIC_CAVE = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.BASIC_CAVE.location());
    public final static ResourceKey<BiomeEventData> LUSH_CAVE = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.LUSH_CAVE.location());
    public final static ResourceKey<BiomeEventData> DRIPSTONE_CAVE = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.DRIPSTONE_CAVE.location());
    public final static ResourceKey<BiomeEventData> DOLOMITE_CAVE = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.DOLOMITE_CAVE.location());
    public final static ResourceKey<BiomeEventData> GALONN_CAVE = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.GALONN_CAVE.location());
    public final static ResourceKey<BiomeEventData> GILDED_CAVE = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.GILDED_CAVE.location());
    public final static ResourceKey<BiomeEventData> IZHERABAN_CAVE = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.IZHERABAN_CAVE.location());
    public final static ResourceKey<BiomeEventData> LIMESTONE_CAVE = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.LIMESTONE_CAVE.location());
    public final static ResourceKey<BiomeEventData> MOUNTAIN_CAVE = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.MOUNTAIN_CAVE.location());
    public final static ResourceKey<BiomeEventData> MUD_CAVE = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.MUD_CAVE.location());
    public final static ResourceKey<BiomeEventData> FUNGUS_CAVE = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.FUNGUS_CAVE.location());
    public final static ResourceKey<BiomeEventData> BASALT_CAVE = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.BASALT_CAVE.location());
    public final static ResourceKey<BiomeEventData> MAGMA_CAVE = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.MAGMA_CAVE.location());
    public final static ResourceKey<BiomeEventData> MITHRIL_CAVE = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.MITHRIL_CAVE.location());
    public final static ResourceKey<BiomeEventData> DRY_CAVE = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.DRY_CAVE.location());
    public final static ResourceKey<BiomeEventData> ICE_CAVE = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.ICE_CAVE.location());
    // endregion

    // region STRUCTURES
    public final static ResourceKey<Structure> WLR_HALL_STRUCTURE = register("woodland_realm_hall");
    public final static ResourceKey<BiomeEventData> WOODLAND_REALM_HALL = DynamicRegistriesME.of(STRUCTURE_EVENT_KEY, WLR_HALL_STRUCTURE.location());
    public final static ResourceKey<Structure> LONGBEARDS_HALL_STRUCTURE = register("longbeards_hall");
    public final static ResourceKey<BiomeEventData> LONGBEARDS_HALL = DynamicRegistriesME.of(STRUCTURE_EVENT_KEY, LONGBEARDS_HALL_STRUCTURE.location());
    // endregion

    // region BRIGDANDS //
    public final static ResourceKey<BiomeEventData> ANDUIN_VALES        = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.ANDUIN_VALES.location());
    public final static ResourceKey<BiomeEventData> ENEDWAITH           = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.ENEDWAITH.location());
    public final static ResourceKey<BiomeEventData> MINHIRIATH          = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.MINHIRIATH.location());
    public final static ResourceKey<BiomeEventData> OLD_RHUDAUR         = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.OLD_RHUDAUR.location());
    // endregion

    // region TROLLSHAWS //
    public final static ResourceKey<BiomeEventData> TROLLSHAWS        = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.TROLLSHAWS.location());

    // endregion

    // region MORDOR
    public final static ResourceKey<BiomeEventData> MORDOR = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.MORDOR.location());
    public final static ResourceKey<BiomeEventData> MORDOR_ASHEN_FOREST = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.MORDOR_ASHEN_FOREST.location());
    public final static ResourceKey<BiomeEventData> MORDOR_HILL         = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.MORDOR_HILL.location());
    public final static ResourceKey<BiomeEventData> MORDOR_WASTES       = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.MORDOR_WASTES.location());
    public final static ResourceKey<BiomeEventData> GORGOROTH           = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.GORGOROTH.location());
    public final static ResourceKey<BiomeEventData> GORGOROTH_ASHEN_WOODS= DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.GORGOROTH_ASHEN_WOODS.location());
    public final static ResourceKey<BiomeEventData> GORGOROTH_DELTA     = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.GORGOROTH_DELTA.location());
    public final static ResourceKey<BiomeEventData> UDUN                = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.UDUN.location());
    public final static ResourceKey<BiomeEventData> BROWN_LANDS         = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.BROWN_LANDS.location());
    public final static ResourceKey<BiomeEventData> DAGORLAD            = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.DAGORLAD.location());
    public final static ResourceKey<BiomeEventData> MORGUL_VALE         = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.MORGUL_VALE.location());
    public final static ResourceKey<BiomeEventData> MORGUL_FOREST       = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.MORGUL_FOREST.location());
    public final static ResourceKey<BiomeEventData> ITHILIEN_WASTES     = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.ITHILIEN_WASTES.location());
    public final static ResourceKey<BiomeEventData> ITHILIEN_WASTES_GLADE = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.ITHILIEN_WASTES_GLADE.location());
    public final static ResourceKey<BiomeEventData> ITHILIEN            = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.ITHILIEN.location());
    public final static ResourceKey<BiomeEventData> NURN                = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.NURN.location());
    public final static ResourceKey<BiomeEventData> NURN_FOREST         = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.NURN_FOREST.location());
    public final static ResourceKey<BiomeEventData> NURN_HILL           = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.NURN_HILL.location());
    public final static ResourceKey<BiomeEventData> NURN_EDGE           = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.NURN_EDGE.location());
    public final static ResourceKey<BiomeEventData> NURN_EDGE_WOODS     = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.NURN_EDGE_WOODS.location());
    public final static ResourceKey<BiomeEventData> EASTERN_NURN        = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.EASTERN_NURN.location());
    public final static ResourceKey<BiomeEventData> TOROGWAITH          = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.TOROGWAITH.location());
    public final static ResourceKey<BiomeEventData> EPHEL_DUATH_BASE    = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.EPHEL_DUATH_BASE.location());
    public final static ResourceKey<BiomeEventData> EPHEL_DUATH         = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.EPHEL_DUATH.location());
    public final static ResourceKey<BiomeEventData> ERED_LITHUI_BASE    = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.ERED_LITHUI_BASE.location());
    public final static ResourceKey<BiomeEventData> ERED_LITHUI         = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.ERED_LITHUI.location());
    // endregion

    // region DOL GULDUR //
    public final static ResourceKey<BiomeEventData> DOL_GULDUR          = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.DOL_GULDUR.location());
    public final static ResourceKey<BiomeEventData> DOL_GULDUR_HILL     = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.DOL_GULDUR_HILL.location());
    public final static ResourceKey<BiomeEventData> DARK_MIRKWOOD       = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.DARK_MIRKWOOD.location());
    public final static ResourceKey<BiomeEventData> DARK_MIRKWOOD_EDGE  = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.DARK_MIRKWOOD_EDGE.location());
    // endregion

    // region ISENGARD //
    public final static ResourceKey<BiomeEventData> ISENGARD            = of(MEBiomeKeys.ISENGARD);
    public final static ResourceKey<BiomeEventData> NAN_CURUNIR         = of(MEBiomeKeys.NAN_CURUNIR);
    // endregion

    // region GUNDABAD
    public final static ResourceKey<BiomeEventData> GUNDABAD_PLAINS     = of(MEBiomeKeys.GUNDABAD_PLAINS);
    public final static ResourceKey<BiomeEventData> GUNDABAD_WOODS      = of(MEBiomeKeys.GUNDABAD_WOODS);
    public final static ResourceKey<BiomeEventData> MOUNT_GUNDABAD_BASE = of(MEBiomeKeys.MOUNT_GUNDABAD_BASE);
    public final static ResourceKey<BiomeEventData> MOUNT_GUNDABAD      = of(MEBiomeKeys.MOUNT_GUNDABAD);
    public final static ResourceKey<BiomeEventData> MISTY_MOUNTAINS     = of(MEBiomeKeys.MISTY_MOUNTAINS);
    // endregion

    // MORIA
    public final static ResourceKey<BiomeEventData> CELEBDIL_BASE       = of(MEBiomeKeys.CELEBDIL_BASE);
    public final static ResourceKey<BiomeEventData> EREGION             = of(MEBiomeKeys.EREGION);

    // region GONDOR
    public final static ResourceKey<BiomeEventData> GONDOR              = of(MEBiomeKeys.GONDOR);
    public final static ResourceKey<BiomeEventData> OSGILIATH           = of(MEBiomeKeys.OSGILIATH);
    public final static ResourceKey<BiomeEventData> ANORIEN             = of(MEBiomeKeys.ANORIEN);
    public final static ResourceKey<BiomeEventData> BELFALAS            = of(MEBiomeKeys.BELFALAS);
    public final static ResourceKey<BiomeEventData> ITHILIEN_GLADE      = of(MEBiomeKeys.ITHILIEN_GLADE);
    public final static ResourceKey<BiomeEventData> PELENNOR_FIELDS     = of(MEBiomeKeys.PELENNOR_FIELDS);
    public final static ResourceKey<BiomeEventData> LAMEDON             = of(MEBiomeKeys.LAMEDON);
    public final static ResourceKey<BiomeEventData> LEBENNIN            = of(MEBiomeKeys.LEBENNIN);
    public final static ResourceKey<BiomeEventData> LOSSARNACH          = of(MEBiomeKeys.LOSSARNACH);
    public final static ResourceKey<BiomeEventData> LOSSARNACH_VALLEY   = of(MEBiomeKeys.LOSSARNACH_VALLEY);
    public final static ResourceKey<BiomeEventData> BLACKROOT_VALE      = of(MEBiomeKeys.BLACKROOT_VALE);
    // endregion

    // region ROHAN
    public final static ResourceKey<BiomeEventData> ROHAN              = of(MEBiomeKeys.ROHAN);
    public final static ResourceKey<BiomeEventData> ROHAN_FIELD        = of(MEBiomeKeys.ROHAN_FIELD);
    public final static ResourceKey<BiomeEventData> THE_WOLD           = of(MEBiomeKeys.THE_WOLD);
    public final static ResourceKey<BiomeEventData> THE_WOLD_WHEAT_FIELD = of(MEBiomeKeys.THE_WOLD_WHEAT_FIELD);
    // endregion

    // region DALE
    public final static ResourceKey<BiomeEventData> DALE                = of(MEBiomeKeys.DALE);
    public final static ResourceKey<BiomeEventData> DALE_CITY           = of(MEBiomeKeys.DALE_CITY);
    public final static ResourceKey<BiomeEventData> DALE_MEADOW         = of(MEBiomeKeys.DALE_MEADOW);
    // endregion

    // region LONGBEARDS
    public final static ResourceKey<BiomeEventData> LONELY_MOUNTAIN_TAIGA       = of(MEBiomeKeys.LONELY_MOUNTAIN_TAIGA);
    public final static ResourceKey<BiomeEventData> LONELY_MOUNTAIN_FOOTHILLS   = of(MEBiomeKeys.LONELY_MOUNTAIN_FOOTHILLS);
    public final static ResourceKey<BiomeEventData> IRON_HILLS_PLAINS           = of(MEBiomeKeys.IRON_HILLS_PLAINS);
    public final static ResourceKey<BiomeEventData> IRON_HILLS_BASE             = of(MEBiomeKeys.IRON_HILLS_BASE);
    public final static ResourceKey<BiomeEventData> GREY_PLAINS                 = of(MEBiomeKeys.GREY_PLAINS);
    // endregion

    // region LOTHLORIEN
    public final static ResourceKey<BiomeEventData> LOTHLORIEN          = of(MEBiomeKeys.LOTHLORIEN);
    public final static ResourceKey<BiomeEventData> LOTHLORIEN_GLADE    = of(MEBiomeKeys.LOTHLORIEN_GLADE);
    public final static ResourceKey<BiomeEventData> LOTHLORIEN_BLOSSOM  = of(MEBiomeKeys.LOTHLORIEN_BLOSSOM);
    public final static ResourceKey<BiomeEventData> LORIEN_EDGE         = of(MEBiomeKeys.LORIEN_EDGE);
    // endregion

    // region WOODLAND_REALM
    public final static ResourceKey<BiomeEventData> WOODLAND_REALM      = of(MEBiomeKeys.WOODLAND_REALM);
    public final static ResourceKey<BiomeEventData> WOODLAND_GLADE      = of(MEBiomeKeys.WOODLAND_GLADE);
    public final static ResourceKey<BiomeEventData> AUTUMN_WOODLAND     = of(MEBiomeKeys.AUTUMN_WOODLAND);
    public final static ResourceKey<BiomeEventData> WOODLAND_FOOTHILLS  = of(MEBiomeKeys.WOODLAND_FOOTHILLS);
    public final static ResourceKey<BiomeEventData> MIRKWOOD            = of(MEBiomeKeys.MIRKWOOD);
    // endregion

    public final static ResourceKey<BiomeEventData> SHIRE               = of(MEBiomeKeys.SHIRE);
    public final static ResourceKey<BiomeEventData> SHIRE_EDGE          = of(MEBiomeKeys.SHIRE_EDGE);

    private static ResourceKey<BiomeEventData> of(ResourceKey<Biome> key){
        return BiomeEventRegistryUtil.of(key);
    }

    public static void bootstrapStructureEvents(BootstrapContext<BiomeEventData> context) {
        HolderGetter<BiomeEventData> registryEntryLookup = context.lookup(STRUCTURE_EVENT_KEY);

        register(context, registryEntryLookup, WOODLAND_REALM_HALL, WoodlandRealmBiomeEventPool.HALL);
        register(context, registryEntryLookup, LONGBEARDS_HALL, LongbeardsBiomeEventPool.DEFAULT);
    }

    public static void bootstrap(BootstrapContext<BiomeEventData> context) {
        HolderGetter<BiomeEventData> registryEntryLookup = context.lookup(BIOME_EVENT_KEY);
        BiomeEventRegistryUtil.beginRegistration();

        register(context, registryEntryLookup, DEFAULT, GenericHostilesBiomeEventPool.EMPTY);


        register(context, registryEntryLookup, BASIC_CAVE, GenericHostilesBiomeEventPool.CAVE);
        register(context, registryEntryLookup, LUSH_CAVE , GenericHostilesBiomeEventPool.CAVE);
        register(context, registryEntryLookup, DRIPSTONE_CAVE, GenericHostilesBiomeEventPool.CAVE);
        register(context, registryEntryLookup, DOLOMITE_CAVE, GenericHostilesBiomeEventPool.CAVE);
        register(context, registryEntryLookup, GALONN_CAVE, GenericHostilesBiomeEventPool.CAVE);
        register(context, registryEntryLookup, GILDED_CAVE, GenericHostilesBiomeEventPool.CAVE);
        register(context, registryEntryLookup, IZHERABAN_CAVE, GenericHostilesBiomeEventPool.CAVE);
        register(context, registryEntryLookup, LIMESTONE_CAVE, GenericHostilesBiomeEventPool.CAVE);
        register(context, registryEntryLookup, MOUNTAIN_CAVE, GenericHostilesBiomeEventPool.CAVE);
        register(context, registryEntryLookup, MUD_CAVE, GenericHostilesBiomeEventPool.CAVE);
        register(context, registryEntryLookup, FUNGUS_CAVE, GenericHostilesBiomeEventPool.CAVE);
        register(context, registryEntryLookup, BASALT_CAVE, GenericHostilesBiomeEventPool.CAVE);
        register(context, registryEntryLookup, MAGMA_CAVE, GenericHostilesBiomeEventPool.CAVE);
        register(context, registryEntryLookup, MITHRIL_CAVE, GenericHostilesBiomeEventPool.CAVE);
        register(context, registryEntryLookup, DRY_CAVE, GenericHostilesBiomeEventPool.CAVE);
        register(context, registryEntryLookup, ICE_CAVE, GenericHostilesBiomeEventPool.CAVE);


        register(context, registryEntryLookup, ANDUIN_VALES     , GenericHostilesBiomeEventPool.ANDUIN);
        register(context, registryEntryLookup, ENEDWAITH        , GenericHostilesBiomeEventPool.BRIGANDS);
        register(context, registryEntryLookup, MINHIRIATH       , GenericHostilesBiomeEventPool.BRIGANDS);
        register(context, registryEntryLookup, OLD_RHUDAUR      , GenericHostilesBiomeEventPool.BRIGANDS);

        register(context, registryEntryLookup, TROLLSHAWS      , TrollShawBiomeEventPool.TROLLSHAWS);


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
        BiomeEventRegistryUtil.registerDefaults(context, registryEntryLookup);
    }

    private static void register(BootstrapContext<BiomeEventData> context, HolderGetter<BiomeEventData> registryEntryLookup, ResourceKey<BiomeEventData> registryKey, BiomeEventData element){
        BiomeEventRegistryUtil.register(context, registryEntryLookup, registryKey, element);
        // [LANG datagen]
        // None
    }

    private static ResourceKey<Structure> register(String name) {
        return BiomeEventRegistryUtil.structureKey(name);
    }
}
