package net.jukoz.me.world.biomes;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.utils.LoggerUtil;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;

public class MEBiomeKeys extends BiomeKeys {
    // region SURFACE
    public static final RegistryKey<Biome> ANDUIN_VALES = MEBiomeKeys.register("anduin_vales");
    public static final RegistryKey<Biome> ANORIEN = MEBiomeKeys.register("anorien");
    public static final RegistryKey<Biome> ANORIEN_RIVERSIDE = MEBiomeKeys.register("anorien_riverside");
    public static final RegistryKey<Biome> ANORIEN_FOOTHILLS = MEBiomeKeys.register("anorien_foothills");
    public static final RegistryKey<Biome> BARROW_DOWNS = MEBiomeKeys.register("barrow_downs");
    public static final RegistryKey<Biome> BELFALAS = MEBiomeKeys.register("belfalas");
    public static final RegistryKey<Biome> BELFALAS_HILLS = MEBiomeKeys.register("belfalas_hills");
    public static final RegistryKey<Biome> BLUE_MOUNTAINS = MEBiomeKeys.register("blue_mountains");
    public static final RegistryKey<Biome> BLUE_MOUNTAINS_BASE = MEBiomeKeys.register("blue_mountains_base");
    public static final RegistryKey<Biome> BLUE_MOUNTAINS_PEAKS = MEBiomeKeys.register("blue_mountains_peaks");
    public static final RegistryKey<Biome> BROWN_LANDS = MEBiomeKeys.register("brown_lands");
    public static final RegistryKey<Biome> CORSAIR_COASTS = MEBiomeKeys.register("corsair_coasts");
    public static final RegistryKey<Biome> DALE = MEBiomeKeys.register("dale");
    public static final RegistryKey<Biome> DARK_MIRKWOOD = MEBiomeKeys.register("dark_mirkwood");
    public static final RegistryKey<Biome> DARK_MIRKWOOD_EDGE = MEBiomeKeys.register("dark_mirkwood_edge");
    public static final RegistryKey<Biome> DARK_ANDUIN_VALES = MEBiomeKeys.register("dark_anduin_vales");
    public static final RegistryKey<Biome> DOL_GULDUR = MEBiomeKeys.register("dol_guldur");
    public static final RegistryKey<Biome> DORWINION = MEBiomeKeys.register("dorwinion");
    public static final RegistryKey<Biome> DORWINION_HILLS = MEBiomeKeys.register("dorwinion_hills");
    public static final RegistryKey<Biome> DUNLAND_FOOTHILLS = MEBiomeKeys.register("dunland_foothills");
    public static final RegistryKey<Biome> EAST_BIGHT = MEBiomeKeys.register("east_bight");
    public static final RegistryKey<Biome> EASTERN_RHOVANION = MEBiomeKeys.register("eastern_rhovanion");
    public static final RegistryKey<Biome> EMYN_MUIL = MEBiomeKeys.register("emyn_muil");
    public static final RegistryKey<Biome> EMYN_MUIL_CLIFFS = MEBiomeKeys.register("emyn_muil_cliffs");
    public static final RegistryKey<Biome> EMYN_MUIL_PEAKS = MEBiomeKeys.register("emyn_muil_peaks");
    public static final RegistryKey<Biome> ENEDWAITH = MEBiomeKeys.register("enedwaith");
     public static final RegistryKey<Biome> EREGION = MEBiomeKeys.register("eregion");
    public static final RegistryKey<Biome> ERIADOR = MEBiomeKeys.register("eriador");
    public static final RegistryKey<Biome> ETHIR_ANDUIN = MEBiomeKeys.register("ethir_anduin");
    public static final RegistryKey<Biome> FANGORN = MEBiomeKeys.register("fangorn");
    public static final RegistryKey<Biome> FORODWAITH = MEBiomeKeys.register("forodwaith");
    public static final RegistryKey<Biome> FROZEN_OCEAN = MEBiomeKeys.register("frozen_ocean");
    public static final RegistryKey<Biome> FROZEN_POND = MEBiomeKeys.register("frozen_pond");
    public static final RegistryKey<Biome> GONDOR = MEBiomeKeys.register("gondor");
    public static final RegistryKey<Biome> GREY_MOUNTAINS = MEBiomeKeys.register("grey_mountains");
    public static final RegistryKey<Biome> GREY_PLAINS = MEBiomeKeys.register("grey_plains");
    public static final RegistryKey<Biome> HARAD = MEBiomeKeys.register("harad");
    public static final RegistryKey<Biome> HARAD_DESERT = MEBiomeKeys.register("harad_desert");
    public static final RegistryKey<Biome> HARONDOR = MEBiomeKeys.register("harondor");
    public static final RegistryKey<Biome> HIGH_MOOR_VALE = MEBiomeKeys.register("high_moor_vale");
    public static final RegistryKey<Biome> HIGH_MOOR = MEBiomeKeys.register("high_moor");
    public static final RegistryKey<Biome> HIGH_MOOR_HILLS = MEBiomeKeys.register("high_moor_hills");
    public static final RegistryKey<Biome> HILLS_OF_EVENDIM = MEBiomeKeys.register("hills_of_evendim");

    public static final RegistryKey<Biome> IRON_HILLS_PLAINS = MEBiomeKeys.register("iron_hills_frontier");
    public static final RegistryKey<Biome> DALE_RIVERSIDE = MEBiomeKeys.register("iron_hills_riverside");
    public static final RegistryKey<Biome> IRON_FOOTHILLS = MEBiomeKeys.register("iron_foothills");
    public static final RegistryKey<Biome> IRON_HILLS_BASE = MEBiomeKeys.register("iron_hills_base");
    public static final RegistryKey<Biome> IRON_HILLS = MEBiomeKeys.register("iron_hills");
    public static final RegistryKey<Biome> IRON_HILLS_PEAKS = MEBiomeKeys.register("iron_hills_peaks");
    // Ithilien
    public static final RegistryKey<Biome> ITHILIEN = MEBiomeKeys.register("ithilien");
    public static final RegistryKey<Biome> ITHILIEN_WASTES = MEBiomeKeys.register("ithilien_wastes");
    public static final RegistryKey<Biome> LAMEDON = MEBiomeKeys.register("lamedon");
    public static final RegistryKey<Biome> LEBENNIN = MEBiomeKeys.register("lebennin");
    public static final RegistryKey<Biome> LINDON = MEBiomeKeys.register("lindon");
    public static final RegistryKey<Biome> LONELY_MOUNTAIN = MEBiomeKeys.register("lonely_mountain");
    public static final RegistryKey<Biome> LONELY_MOUNTAIN_BASE = MEBiomeKeys.register("lonely_mountain_base");
    public static final RegistryKey<Biome> LONELY_MOUNTAIN_PEAKS = MEBiomeKeys.register("lonely_mountain_peaks");
    public static final RegistryKey<Biome> LONELY_MOUNTAIN_TAIGA = MEBiomeKeys.register("lonely_mountain_taiga");
    public static final RegistryKey<Biome> LONG_LAKE = MEBiomeKeys.register("long_lake");
    public static final RegistryKey<Biome> LONG_MARSHES = MEBiomeKeys.register("long_marshes");
    public static final RegistryKey<Biome> LORIEN_EDGE = MEBiomeKeys.register("lorien_edge");
    public static final RegistryKey<Biome> LOTHLORIEN = MEBiomeKeys.register("lothlorien");
    public static final RegistryKey<Biome> MINHIRIATH = MEBiomeKeys.register("minhiriath");
    public static final RegistryKey<Biome> MIRKWOOD = MEBiomeKeys.register("mirkwood");
    public static final RegistryKey<Biome> MIRKWOOD_EDGE = MEBiomeKeys.register("mirkwood_edge");
    public static final RegistryKey<Biome> MIRKWOOD_FOOTHILLS = MEBiomeKeys.register("mirkwood_foothills");
    public static final RegistryKey<Biome> MIRKWOOD_MOUNTAINS = MEBiomeKeys.register("mirkwood_mountains");
    public static final RegistryKey<Biome> MIRKWOOD_MOUNTAINS_BASE = MEBiomeKeys.register("mirkwood_mountains_base");
    public static final RegistryKey<Biome> MIRKWOOD_MOUNTAINS_PEAKS = MEBiomeKeys.register("mirkwood_mountains_peaks");
    public static final RegistryKey<Biome> MIRKWOOD_SWAMP = MEBiomeKeys.register("mirkwood_swamp");
    public static final RegistryKey<Biome> FOREST_RIVER = MEBiomeKeys.register("mirkwood_river");
    public static final RegistryKey<Biome> GREAT_RIVER = MEBiomeKeys.register("great_river");
    public static final RegistryKey<Biome> GUNDABAD_PLAINS = MEBiomeKeys.register("gundabad_plains");
    public static final RegistryKey<Biome> MIRKWOOD_MARSHES = MEBiomeKeys.register("mirkwood_marshes");
    public static final RegistryKey<Biome> MISTY_MOUNTAINS = MEBiomeKeys.register("misty_mountains");
    public static final RegistryKey<Biome> MISTY_MOUNTAINS_BASE = MEBiomeKeys.register("misty_mountains_base");
    public static final RegistryKey<Biome> MISTY_MOUNTAINS_PEAKS  = MEBiomeKeys.register("misty_mountains_peaks");
    public static final RegistryKey<Biome> MORDOR = MEBiomeKeys.register("mordor");
    public static final RegistryKey<Biome> MORDOR_MOUNTAINS = MEBiomeKeys.register("mordor_mountains");
    public static final RegistryKey<Biome> MORDOR_MOUNTAINS_FOOTHILLS = MEBiomeKeys.register("mordor_mountains_foothills");
    public static final RegistryKey<Biome> MORDOR_WASTES = MEBiomeKeys.register("mordor_wastes");
    public static final RegistryKey<Biome> NEN_HITHOEL = MEBiomeKeys.register("nen_hithoel");
    public static final RegistryKey<Biome> NEN_HITHOEL_SHORES = MEBiomeKeys.register("nen_hithoel_shores");
    public static final RegistryKey<Biome> NEN_HITHOEL_FOREST = MEBiomeKeys.register("nen_hithoel_forest");
    public static final RegistryKey<Biome> NINDALF = MEBiomeKeys.register("nindalf");
    public static final RegistryKey<Biome> NORTH_DOWNS = MEBiomeKeys.register("north_downs");
    public static final RegistryKey<Biome> NORTHERN_DUNLAND = MEBiomeKeys.register("northern_dunland");
    public static final RegistryKey<Biome> NORTHERN_MIRKWOOD_MARSHES = MEBiomeKeys.register("northern_mirkwood_marshes");
    public static final RegistryKey<Biome> NORTHERN_MIRKWOOD_SWAMP = MEBiomeKeys.register("northern_mirkwood_swamp");
    public static final RegistryKey<Biome> NORTHERN_WASTELANDS = MEBiomeKeys.register("northern_wastelands");
    public static final RegistryKey<Biome> NURN = MEBiomeKeys.register("nurn");
    public static final RegistryKey<Biome> NURN_RIVER = MEBiomeKeys.register("nurn_river");
    public static final RegistryKey<Biome> NURN_SEA = MEBiomeKeys.register("nurn_sea");
    public static final RegistryKey<Biome> OCEAN = MEBiomeKeys.register("ocean");
    public static final RegistryKey<Biome> OCEAN_COAST = MEBiomeKeys.register("ocean_coast");
    public static final RegistryKey<Biome> OLD_ANGMAR = MEBiomeKeys.register("old_angmar");
    public static final RegistryKey<Biome> OLD_ARTHEDAIN = MEBiomeKeys.register("old_arthedain");
    public static final RegistryKey<Biome> OLD_ARTHEDAIN_FOOTHILL = MEBiomeKeys.register("old_arthedain_foothill");
    public static final RegistryKey<Biome> OLD_CARDOLAN = MEBiomeKeys.register("old_cardolan");
    public static final RegistryKey<Biome> OLD_RHUDAUR = MEBiomeKeys.register("old_rhudaur");
    public static final RegistryKey<Biome> OASIS = MEBiomeKeys.register("oasis");
    public static final RegistryKey<Biome> POND = MEBiomeKeys.register("pond");
    public static final RegistryKey<Biome> RHUN = MEBiomeKeys.register("rhun");
    public static final RegistryKey<Biome> SEA_OF_RHUN = MEBiomeKeys.register("sea_of_rhun");
    public static final RegistryKey<Biome> RIVER = MEBiomeKeys.register("river");
    public static final RegistryKey<Biome> RIVER_RUNNING = MEBiomeKeys.register("river_running");
    public static final RegistryKey<Biome> ROHAN = MEBiomeKeys.register("rohan");
    public static final RegistryKey<Biome> SARN_GEBIR_SHORES = MEBiomeKeys.register("sarn_gebir_shores");
    public static final RegistryKey<Biome> SARN_GEBIR_WILDLANDS = MEBiomeKeys.register("sarn_gebir_wildlands");
    public static final RegistryKey<Biome> SHIRE = MEBiomeKeys.register("shire");
    public static final RegistryKey<Biome> SHIRE_EDGE = MEBiomeKeys.register("shire_edge");
    public static final RegistryKey<Biome> SHIRE_WOODS = MEBiomeKeys.register("shire_woods");
    public static final RegistryKey<Biome> SOUTHEAST_RHOVANION = MEBiomeKeys.register("southeast_rhovanion");
    public static final RegistryKey<Biome> SOUTHERN_DUNLAND = MEBiomeKeys.register("southern_dunland");
    public static final RegistryKey<Biome> SOUTHERN_FOROCHEL = MEBiomeKeys.register("southern_forochel");
    public static final RegistryKey<Biome> THE_ANGLE = MEBiomeKeys.register("the_angle");
    public static final RegistryKey<Biome> THE_OLD_FOREST = MEBiomeKeys.register("the_old_forest");
    public static final RegistryKey<Biome> THE_WOLD = MEBiomeKeys.register("the_wold");
    public static final RegistryKey<Biome> TOLFALAS = MEBiomeKeys.register("tolfalas");
    public static final RegistryKey<Biome> TROLLSHAWS = MEBiomeKeys.register("trollshaws");
    public static final RegistryKey<Biome> UMBAR = MEBiomeKeys.register("umbar");
    public static final RegistryKey<Biome> WEBBED_WOODS = MEBiomeKeys.register("webbed_woods");
    public static final RegistryKey<Biome> WASTE_POND = MEBiomeKeys.register("waste_pond");
    public static final RegistryKey<Biome> WHITE_MOUNTAINS_BASE = MEBiomeKeys.register("white_mountains_base");
    public static final RegistryKey<Biome> WHITE_MOUNTAINS = MEBiomeKeys.register("white_mountains");
    public static final RegistryKey<Biome> WHITE_MOUNTAINS_PEAKS = MEBiomeKeys.register("white_mountains_peaks");

    public static final RegistryKey<Biome> WOODLAND_REALM = MEBiomeKeys.register("woodland_realm");
    public static final RegistryKey<Biome> WOODLAND_FOOTHILLS= MEBiomeKeys.register("woodland_foothills");
    public static final RegistryKey<Biome> WOODLAND_HILLS = MEBiomeKeys.register("woodland_hills");
    // endregion

    // region CAVES
    public static final RegistryKey<Biome> BASIC_CAVE = MEBiomeKeys.register("basic_cave");
    public static final RegistryKey<Biome> LUSH_CAVE = MEBiomeKeys.register("lush_cave");
    public static final RegistryKey<Biome> DRIPSTONE_CAVE = MEBiomeKeys.register("dripstone_cave");
    public static final RegistryKey<Biome> MUD_CAVE = MEBiomeKeys.register("mud_cave");
    public static final RegistryKey<Biome> FUNGUS_CAVE = MEBiomeKeys.register("fungus_cave");
    public static final RegistryKey<Biome> BASALT_CAVE = MEBiomeKeys.register("basalt_cave");
    public static final RegistryKey<Biome> MAGMA_CAVE = MEBiomeKeys.register("magma_cave");
    public static final RegistryKey<Biome> MITHRIL_CAVE = MEBiomeKeys.register("mithril_cave");
    public static final RegistryKey<Biome> DRY_CAVE = MEBiomeKeys.register("dry_cave");
    public static final RegistryKey<Biome> ICE_CAVE = MEBiomeKeys.register("ice_cave");

    // endregion

    private static RegistryKey<Biome> register(String name) {
        return RegistryKey.of(RegistryKeys.BIOME, new Identifier(MiddleEarth.MOD_ID, name));
    }

    public static void registerModBiomes() {
        LoggerUtil.getInstance().logDebugMsg("Registering ModBiomes for " + MiddleEarth.MOD_ID);
    }
}
