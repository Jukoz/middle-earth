package net.jukoz.me.world.biomes.surface;

import net.jukoz.me.world.biomes.BiomeColorsDTO;
import net.jukoz.me.world.biomes.MEBiomeKeys;
import net.jukoz.me.world.spawners.ModSpawnSettingsBuilder;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.*;
import net.minecraft.registry.Registerable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ModBiomes {
    public static final int defaultSky = 7907327;
    public static final int defaultFog = 12638463;
    public static final int defaultWater = 4159204;
    public static final int defaultWaterFog = 329011;
    public static final int defaultShoreWater = 4157124;
    public static final int defaultCoastWater = 4155044;
    public static final int defaultOceanWater = 3956102;
    public static final int defaultOceanWaterFog = 2309971;
    public static final int hillySky = 8233727;
    public static final int waterSky = 8103167;
    public static final int nearHaradSky = 7254527;
    public static final int nearHaradSkyFog = 12902399;

    private static List<RegistryKey<PlacedFeature>> vegetation = new ArrayList<>();;
    private static ArrayList<RegistryKey<PlacedFeature>> undergroundOres = new ArrayList<>();;

    private static BiomeColorsDTO getBiomeColors(RegistryKey<Biome> biomeRegistryKey){
        return CustomBiomesData.getBiome(biomeRegistryKey).getBiomeColors();
    }
    public static void bootstrap(Registerable<Biome> context) {
        context.register(MEBiomeKeys.ANDUIN_VALES, createAnduinBiome(context, MEBiomeKeys.ANDUIN_VALES, false));
        context.register(MEBiomeKeys.ANDUIN_VALES_FOREST, createAnduinBiome(context, MEBiomeKeys.ANDUIN_VALES_FOREST, true));
        context.register(MEBiomeKeys.ANORIEN, createAnorienBiome(context, MEBiomeKeys.ANORIEN));
        context.register(MEBiomeKeys.ANORIEN_RIVERSIDE, createGondorRiverSideBiome(context, MEBiomeKeys.ANORIEN_RIVERSIDE));
        context.register(MEBiomeKeys.ANORIEN_FOOTHILLS, createAnorienBiome(context, MEBiomeKeys.ANORIEN_FOOTHILLS));
        context.register(MEBiomeKeys.BARROW_DOWNS, createBarrowDownsBiome(context, MEBiomeKeys.BARROW_DOWNS));
        context.register(MEBiomeKeys.BELERIAND_ISLAND, createBeleriandIslandBiome(context, MEBiomeKeys.BELERIAND_ISLAND));
        context.register(MEBiomeKeys.BELFALAS, createBelfalasBiome(context, MEBiomeKeys.BELFALAS, false));
        context.register(MEBiomeKeys.BELFALAS_BEACH, createBelfalasShoresBiome(context, MEBiomeKeys.BELFALAS_BEACH));
        context.register(MEBiomeKeys.BELFALAS_HILLS, createBelfalasBiome(context, MEBiomeKeys.BELFALAS_HILLS, true));
        context.register(MEBiomeKeys.BLACKROOT_VALE, createBlackRootVale(context, MEBiomeKeys.BLACKROOT_VALE, false));
        context.register(MEBiomeKeys.BLUE_MOUNTAINS_FOOTHILLS, createBlueMountainsBiome(context, MEBiomeKeys.BLUE_MOUNTAINS_FOOTHILLS, 0));
        context.register(MEBiomeKeys.BLUE_MOUNTAINS_BASE, createBlueMountainsBiome(context, MEBiomeKeys.BLUE_MOUNTAINS_BASE, 1));
        context.register(MEBiomeKeys.BLUE_MOUNTAINS, createBlueMountainsBiome(context, MEBiomeKeys.BLUE_MOUNTAINS, 2));
        context.register(MEBiomeKeys.BLUE_MOUNTAINS_PEAKS, createBlueMountainsBiome(context, MEBiomeKeys.BLUE_MOUNTAINS_PEAKS, 3));
        context.register(MEBiomeKeys.BROWN_LANDS, createMordorWastesBiome(context, MEBiomeKeys.BROWN_LANDS));
        context.register(MEBiomeKeys.CARADHRAS_BASE, createMistyMountainsBiome(context, MEBiomeKeys.CARADHRAS_BASE, 0));
        context.register(MEBiomeKeys.CARADHRAS, createMistyMountainsBiome(context, MEBiomeKeys.CARADHRAS, 1));
        context.register(MEBiomeKeys.CARADHRAS_PEAKS, createMistyMountainsBiome(context, MEBiomeKeys.CARADHRAS_PEAKS, 2));
        context.register(MEBiomeKeys.CELEBDIL_BASE, createMistyMountainsBiome(context, MEBiomeKeys.CELEBDIL_BASE, 0));
        context.register(MEBiomeKeys.CELEBDIL, createMistyMountainsBiome(context, MEBiomeKeys.CELEBDIL, 1));
        context.register(MEBiomeKeys.CELEBDIL_PEAKS, createMistyMountainsBiome(context, MEBiomeKeys.CELEBDIL_PEAKS, 2));
        context.register(MEBiomeKeys.CORSAIR_COASTS, createCorsairCoastBiome(context, MEBiomeKeys.CORSAIR_COASTS));
        context.register(MEBiomeKeys.DAGORLAD, createMordorWastesBiome(context, MEBiomeKeys.DAGORLAD));
        context.register(MEBiomeKeys.DALE, createDaleBiome(context, MEBiomeKeys.DALE, 0));
        context.register(MEBiomeKeys.DALE_FOREST, createDaleBiome(context, MEBiomeKeys.DALE_FOREST, 1));
        context.register(MEBiomeKeys.DALE_HILL, createDaleBiome(context, MEBiomeKeys.DALE_HILL, 2));
        context.register(MEBiomeKeys.DALE_CITY, createDaleBiome(context, MEBiomeKeys.DALE_CITY, 0));
        context.register(MEBiomeKeys.DALE_RIVERSIDE, createDaleBiome(context, MEBiomeKeys.DALE_RIVERSIDE, 0));
        context.register(MEBiomeKeys.DARK_ANDUIN_VALES, createAnduinBiome(context, MEBiomeKeys.DARK_ANDUIN_VALES, false));
        context.register(MEBiomeKeys.DARK_MIRKWOOD, createMirkwoodBiome(context, MEBiomeKeys.DARK_MIRKWOOD, true, true));
        context.register(MEBiomeKeys.DARK_MIRKWOOD_EDGE, createMirkwoodBiome(context, MEBiomeKeys.DARK_MIRKWOOD_EDGE, false, true));
        context.register(MEBiomeKeys.DEAD_MARSHES, createDeadMarshesBiome(context, MEBiomeKeys.DEAD_MARSHES));
        context.register(MEBiomeKeys.DEAD_MARSHES_WATER, createDeadMarshesWaterBiome(context, MEBiomeKeys.DEAD_MARSHES_WATER));
        context.register(MEBiomeKeys.DOL_GULDUR, createMirkwoodBiome(context, MEBiomeKeys.DOL_GULDUR, false, true));
        context.register(MEBiomeKeys.DOL_GULDUR_HILL, createMirkwoodBiome(context, MEBiomeKeys.DOL_GULDUR_HILL, false, true));
        context.register(MEBiomeKeys.DORWINION, createDorwinionBiome(context, MEBiomeKeys.DORWINION));
        context.register(MEBiomeKeys.DORWINION_HILLS, createDorwinionHillsBiome(context, MEBiomeKeys.DORWINION_HILLS));
        context.register(MEBiomeKeys.DUNLAND_FOOTHILLS, createDunlandFoothillsBiome(context, MEBiomeKeys.DUNLAND_FOOTHILLS));
        context.register(MEBiomeKeys.EAST_BIGHT, createRhunBiome(context, MEBiomeKeys.EAST_BIGHT));
        context.register(MEBiomeKeys.EASTERN_NURN, createNurnBiome(context, MEBiomeKeys.EASTERN_NURN));
        context.register(MEBiomeKeys.EASTERN_RHOVANION, createRhunBiome(context, MEBiomeKeys.EASTERN_RHOVANION));
        context.register(MEBiomeKeys.EMYN_MUIL, createEmynMuilBiome(context, MEBiomeKeys.EMYN_MUIL));
        context.register(MEBiomeKeys.EMYN_MUIL_CLIFFS, createEmynMuilBiome(context, MEBiomeKeys.EMYN_MUIL_CLIFFS));
        context.register(MEBiomeKeys.EMYN_MUIL_PEAKS, createEmynMuilBiome(context, MEBiomeKeys.EMYN_MUIL_PEAKS));
        context.register(MEBiomeKeys.EMYN_MUIL_POND, createWastePondBiome(context, MEBiomeKeys.EMYN_MUIL_POND));
        context.register(MEBiomeKeys.ENEDWAITH, createEnedwaithBiome(context, MEBiomeKeys.ENEDWAITH));
        context.register(MEBiomeKeys.LONELY_MOUNTAIN, createLonelyMountainBiome(context, MEBiomeKeys.LONELY_MOUNTAIN, 0));
        context.register(MEBiomeKeys.LONELY_MOUNTAIN_BASE, createLonelyMountainBiome(context, MEBiomeKeys.LONELY_MOUNTAIN_BASE, 1));
        context.register(MEBiomeKeys.LONELY_MOUNTAIN_PEAKS, createLonelyMountainBiome(context, MEBiomeKeys.LONELY_MOUNTAIN_PEAKS, 2));
        context.register(MEBiomeKeys.LONELY_MOUNTAIN_TAIGA, createLonelyMountainBiome(context, MEBiomeKeys.LONELY_MOUNTAIN_TAIGA, 1));
        context.register(MEBiomeKeys.EREGION, createEregionBiome(context, MEBiomeKeys.EREGION));
        context.register(MEBiomeKeys.ETHIR_ANDUIN, createSwampAnduin(context,MEBiomeKeys.ETHIR_ANDUIN));
        context.register(MEBiomeKeys.FANGORN, createFangornBiome(context, MEBiomeKeys.FANGORN));
        context.register(MEBiomeKeys.FANGORN_FOOTHILLS, createMistyMountainsBiome(context, MEBiomeKeys.FANGORN_FOOTHILLS, 0));
        context.register(MEBiomeKeys.FANUIDHOL_BASE, createMistyMountainsBiome(context, MEBiomeKeys.FANUIDHOL_BASE, 0));
        context.register(MEBiomeKeys.FANUIDHOL, createMistyMountainsBiome(context, MEBiomeKeys.FANUIDHOL, 1));
        context.register(MEBiomeKeys.FANUIDHOL_PEAKS, createMistyMountainsBiome(context, MEBiomeKeys.FANUIDHOL_PEAKS, 2));
        context.register(MEBiomeKeys.FORODWAITH, createForodwaithBiome(context, MEBiomeKeys.FORODWAITH));
        context.register(MEBiomeKeys.FROZEN_OCEAN, createOceanBiome(context, MEBiomeKeys.FROZEN_OCEAN));
        context.register(MEBiomeKeys.FROZEN_POND, createFrozenPond(context, MEBiomeKeys.FROZEN_POND));
        context.register(MEBiomeKeys.GULF_OF_LHUN_CLIFFS, createLindonBiome(context, MEBiomeKeys.GULF_OF_LHUN_CLIFFS));
        context.register(MEBiomeKeys.GULF_OF_LHUN_SHORES, createLindonBiome(context, MEBiomeKeys.GULF_OF_LHUN_SHORES));
        context.register(MEBiomeKeys.GONDOR, createGondorBiome(context, MEBiomeKeys.GONDOR));
        context.register(MEBiomeKeys.GORGOROTH, createGorgorothBiome(context, MEBiomeKeys.GORGOROTH));
        context.register(MEBiomeKeys.GREY_MOUNTAINS_BASE, createGreyMountainsBiome(context, MEBiomeKeys.GREY_MOUNTAINS_BASE, 0));
        context.register(MEBiomeKeys.GREY_MOUNTAINS, createGreyMountainsBiome(context, MEBiomeKeys.GREY_MOUNTAINS, 0));
        context.register(MEBiomeKeys.GREY_MOUNTAINS_PEAKS, createGreyMountainsBiome(context, MEBiomeKeys.GREY_MOUNTAINS_PEAKS, 1));
        context.register(MEBiomeKeys.GREY_PLAINS, createGreyPlainsBiome(context, MEBiomeKeys.GREY_PLAINS));
        context.register(MEBiomeKeys.GUNDABAD_PLAINS, createGreyPlainsTaiga(context, MEBiomeKeys.GUNDABAD_PLAINS));
        context.register(MEBiomeKeys.HARAD, createHaradBiome(context, MEBiomeKeys.HARAD));
        context.register(MEBiomeKeys.HARAD_DESERT, createHaradBiome(context, MEBiomeKeys.HARAD_DESERT));
        context.register(MEBiomeKeys.HARONDOR, createHaradBiome(context, MEBiomeKeys.HARONDOR));
        context.register(MEBiomeKeys.HILLS_OF_EVENDIM, createHaradBiome(context, MEBiomeKeys.HILLS_OF_EVENDIM));
        context.register(MEBiomeKeys.IRON_HILLS, createIronHillsBiome(context, MEBiomeKeys.IRON_HILLS, false));
        context.register(MEBiomeKeys.IRON_HILLS_BASE, createIronHillsBiome(context, MEBiomeKeys.IRON_HILLS_BASE, true));
        context.register(MEBiomeKeys.IRON_HILLS_PEAKS, createIronHillsBiome(context, MEBiomeKeys.IRON_HILLS_PEAKS, true));
        context.register(MEBiomeKeys.IRON_HILLS_PLAINS, createNorthernRhovanionTaiga(context, MEBiomeKeys.IRON_HILLS_PLAINS));
        context.register(MEBiomeKeys.IRON_FOOTHILLS, createNorthernRhovanionTaiga(context, MEBiomeKeys.IRON_FOOTHILLS));
        context.register(MEBiomeKeys.ISENGARD, createIsengardBiome(context, MEBiomeKeys.ISENGARD, true));
        context.register(MEBiomeKeys.ISENGARD_HILL, createIsengardBiome(context, MEBiomeKeys.ISENGARD_HILL, false));
        context.register(MEBiomeKeys.ITHILIEN, createIthilienBiome(context, MEBiomeKeys.ITHILIEN, false));
        context.register(MEBiomeKeys.ITHILIEN_WASTES, createIthilienBiome(context, MEBiomeKeys.ITHILIEN_WASTES, true));
        context.register(MEBiomeKeys.LAMEDON, createLamedonBiome(context, MEBiomeKeys.LAMEDON));
        context.register(MEBiomeKeys.LEBENNIN, createLebennin(context, MEBiomeKeys.LEBENNIN));
        context.register(MEBiomeKeys.LEBENNIN_SHORES, createGondorRiverSideBiome(context, MEBiomeKeys.LEBENNIN_SHORES));
        context.register(MEBiomeKeys.LINDON, createLindonBiome(context, MEBiomeKeys.LINDON));
        context.register(MEBiomeKeys.LONG_LAKE, createLakeBiome(context, MEBiomeKeys.LONG_LAKE));
        context.register(MEBiomeKeys.LONG_MARSHES, createMirkwoodSwampBiome(context, MEBiomeKeys.LONG_MARSHES));
        context.register(MEBiomeKeys.LOSSARNACH, createLossarnach(context, MEBiomeKeys.LOSSARNACH, 0));
        context.register(MEBiomeKeys.LOSSARNACH_VALLEY, createLossarnach(context, MEBiomeKeys.LOSSARNACH_VALLEY, 1));
        context.register(MEBiomeKeys.LORIEN_EDGE, createLorienEdgeBiome(context, MEBiomeKeys.LORIEN_EDGE));
        context.register(MEBiomeKeys.LOTHLORIEN, createLothlorienBiome(context, MEBiomeKeys.LOTHLORIEN));
        context.register(MEBiomeKeys.MINHIRIATH, createMinhiriathBiome(context, MEBiomeKeys.MINHIRIATH));
        context.register(MEBiomeKeys.MIRKWOOD, createMirkwoodBiome(context, MEBiomeKeys.MIRKWOOD, true, false));
        context.register(MEBiomeKeys.MIRKWOOD_EDGE, createMirkwoodBiome(context, MEBiomeKeys.MIRKWOOD_EDGE, false, false));
        context.register(MEBiomeKeys.MIRKWOOD_FOOTHILLS, createMirkwoodBiome(context, MEBiomeKeys.MIRKWOOD_FOOTHILLS, true, false));
        context.register(MEBiomeKeys.MIRKWOOD_MOUNTAINS_BASE, createMirkwoodBiome(context, MEBiomeKeys.MIRKWOOD_MOUNTAINS_BASE, true, false));
        context.register(MEBiomeKeys.MIRKWOOD_MOUNTAINS, createMirkwoodBiome(context, MEBiomeKeys.MIRKWOOD_MOUNTAINS, false, false));
        context.register(MEBiomeKeys.MIRKWOOD_MOUNTAINS_PEAKS, createMirkwoodMountainsBiome(context, MEBiomeKeys.MIRKWOOD_MOUNTAINS_PEAKS, false));
        context.register(MEBiomeKeys.MIRKWOOD_SWAMP, createMirkwoodSwampBiome(context, MEBiomeKeys.MIRKWOOD_SWAMP));
        context.register(MEBiomeKeys.MIRKWOOD_MARSHES, createMirkwoodSwampBiome(context, MEBiomeKeys.MIRKWOOD_MARSHES));
        context.register(MEBiomeKeys.FOREST_RIVER, createMirkwoodSwampBiome(context, MEBiomeKeys.FOREST_RIVER));
        context.register(MEBiomeKeys.GREAT_RIVER, createRiverBiome(context, MEBiomeKeys.GREAT_RIVER));
        context.register(MEBiomeKeys.MISTY_MOUNTAINS_BASE, createMistyMountainsBiome(context, MEBiomeKeys.MISTY_MOUNTAINS_BASE, 0));
        context.register(MEBiomeKeys.MISTY_MOUNTAINS, createMistyMountainsBiome(context, MEBiomeKeys.MISTY_MOUNTAINS, 1));
        context.register(MEBiomeKeys.MISTY_MOUNTAINS_PEAKS, createMistyMountainsBiome(context, MEBiomeKeys.MISTY_MOUNTAINS_PEAKS, 2));
        context.register(MEBiomeKeys.MORDOR, createMordorBiome(context, MEBiomeKeys.MORDOR));
        context.register(MEBiomeKeys.MORDOR_MOUNTAINS_FOOTHILLS, createMordorMountainsBiome(context, MEBiomeKeys.MORDOR_MOUNTAINS_FOOTHILLS));
        context.register(MEBiomeKeys.MORDOR_MOUNTAINS, createMordorMountainsBiome(context, MEBiomeKeys.MORDOR_MOUNTAINS));
        context.register(MEBiomeKeys.MORDOR_MOUNTAINS_PEAKS, createMordorMountainsBiome(context, MEBiomeKeys.MORDOR_MOUNTAINS_PEAKS));
        context.register(MEBiomeKeys.MORDOR_WASTES, createMordorWastesBiome(context, MEBiomeKeys.MORDOR_WASTES));
        context.register(MEBiomeKeys.MORGUL_VALE, createMorgulVale(context, MEBiomeKeys.MORGUL_VALE));
        context.register(MEBiomeKeys.MORGUL_RIVER, createNurnWaterBiome(context, MEBiomeKeys.MORGUL_RIVER));
        context.register(MEBiomeKeys.MOUNT_GUNDABAD, createGreyMountainsBiome(context, MEBiomeKeys.MOUNT_GUNDABAD, 1));
        context.register(MEBiomeKeys.MOUNT_DOOM, createMordorMountainsBiome(context, MEBiomeKeys.MOUNT_DOOM));
        context.register(MEBiomeKeys.NAN_CURUNIR, createNanCurunirBiome(context, MEBiomeKeys.NAN_CURUNIR));
        context.register(MEBiomeKeys.NEN_HITHOEL, createLakeBiome(context, MEBiomeKeys.NEN_HITHOEL));
        context.register(MEBiomeKeys.NEN_HITHOEL_FOREST, createTrollshawsBiome(context, MEBiomeKeys.NEN_HITHOEL_FOREST));
        context.register(MEBiomeKeys.NEN_HITHOEL_SHORES, createTrollshawsBiome(context, MEBiomeKeys.NEN_HITHOEL_SHORES));
        context.register(MEBiomeKeys.NINDALF, createSwampAnduin(context, MEBiomeKeys.NINDALF));
        context.register(MEBiomeKeys.NORTH_DOWNS, createNorthDownsBiome(context, MEBiomeKeys.NORTH_DOWNS));
        context.register(MEBiomeKeys.NORTHERN_DUNLAND, createNorthDunlandBiome(context, MEBiomeKeys.NORTHERN_DUNLAND, true));
        context.register(MEBiomeKeys.NORTHERN_MIRKWOOD_SWAMP, createMirkwoodSwampBiome(context, MEBiomeKeys.NORTHERN_MIRKWOOD_SWAMP));
        context.register(MEBiomeKeys.NORTHERN_MIRKWOOD_MARSHES, createMirkwoodSwampBiome(context, MEBiomeKeys.NORTHERN_MIRKWOOD_MARSHES));
        context.register(MEBiomeKeys.NORTHERN_WASTELANDS, createNorthernWastelands(context, MEBiomeKeys.NORTHERN_WASTELANDS));
        context.register(MEBiomeKeys.NURN, createNurnBiome(context, MEBiomeKeys.NURN));
        context.register(MEBiomeKeys.NURN_EDGE, createNurnEdgeBiome(context, MEBiomeKeys.NURN_EDGE));
        context.register(MEBiomeKeys.NURN_RIVER, createNurnWaterBiome(context, MEBiomeKeys.NURN_RIVER));
        context.register(MEBiomeKeys.NURN_SEA, createNurnWaterBiome(context, MEBiomeKeys.NURN_SEA));
        context.register(MEBiomeKeys.OASIS, createOasisBiome(context, MEBiomeKeys.OASIS));
        context.register(MEBiomeKeys.OCEAN, createOceanBiome(context, MEBiomeKeys.OCEAN));
        context.register(MEBiomeKeys.OCEAN_COAST, createOceanCoastBiome(context, MEBiomeKeys.OCEAN_COAST));
        context.register(MEBiomeKeys.OLD_ANGMAR, createOldAngmarBiome(context, MEBiomeKeys.OLD_ANGMAR, 1));
        context.register(MEBiomeKeys.OLD_ANGMAR_FOREST, createOldAngmarBiome(context, MEBiomeKeys.OLD_ANGMAR_FOREST, 0));
        context.register(MEBiomeKeys.OLD_ANGMAR_COLD_HILL, createOldAngmarBiome(context, MEBiomeKeys.OLD_ANGMAR_COLD_HILL, 2));
        context.register(MEBiomeKeys.OLD_ANGMAR_FROZEN_HILL, createOldAngmarBiome(context, MEBiomeKeys.OLD_ANGMAR_FROZEN_HILL, 3));
        context.register(MEBiomeKeys.OLD_ARTHEDAIN, createOldArthedainBiome(context, MEBiomeKeys.OLD_ARTHEDAIN, 1));
        context.register(MEBiomeKeys.OLD_ARTHEDAIN_FOREST, createOldArthedainBiome(context, MEBiomeKeys.OLD_ARTHEDAIN_FOREST, 2));
        context.register(MEBiomeKeys.OLD_ARTHEDAIN_MEADOW, createOldArthedainBiome(context, MEBiomeKeys.OLD_ARTHEDAIN_MEADOW, 0));
        context.register(MEBiomeKeys.OLD_ARTHEDAIN_FOOTHILL, createOldArthedainBiome(context, MEBiomeKeys.OLD_ARTHEDAIN_FOOTHILL, 3));
        context.register(MEBiomeKeys.OLD_CARDOLAN, createOldCardolanBiome(context, MEBiomeKeys.OLD_CARDOLAN, 0));
        context.register(MEBiomeKeys.OLD_CARDOLAN_FOREST, createOldCardolanBiome(context, MEBiomeKeys.OLD_CARDOLAN_FOREST, 1));
        context.register(MEBiomeKeys.OLD_CARDOLAN_HILL, createOldCardolanBiome(context, MEBiomeKeys.OLD_CARDOLAN_HILL, 2));
        context.register(MEBiomeKeys.OLD_RHUDAUR, createOldRhudaurBiome(context, MEBiomeKeys.OLD_RHUDAUR, 0));
        context.register(MEBiomeKeys.OLD_RHUDAUR_FOREST, createOldRhudaurBiome(context, MEBiomeKeys.OLD_RHUDAUR_FOREST, 1));
        context.register(MEBiomeKeys.OLD_RHUDAUR_HILL, createOldRhudaurBiome(context, MEBiomeKeys.OLD_RHUDAUR_HILL, 2));
        context.register(MEBiomeKeys.OSGILIATH, createGondorBiome(context, MEBiomeKeys.OSGILIATH));
        context.register(MEBiomeKeys.PELENNOR_FIELDS, createPelennorFields(context, MEBiomeKeys.PELENNOR_FIELDS));
        context.register(MEBiomeKeys.POND, createPondBiome(context, MEBiomeKeys.POND));
        context.register(MEBiomeKeys.RHUN, createRhunBiome(context, MEBiomeKeys.POND));
        context.register(MEBiomeKeys.HIGH_MOOR, createRivendellBiome(context, MEBiomeKeys.HIGH_MOOR));
        context.register(MEBiomeKeys.HIGH_MOOR_VALE, createRivendellBiome(context, MEBiomeKeys.HIGH_MOOR_VALE));
        context.register(MEBiomeKeys.HIGH_MOOR_HILLS, createRivendellFoothillsBiome(context, MEBiomeKeys.HIGH_MOOR_HILLS));
        context.register(MEBiomeKeys.RIVER, createRiverBiome(context, MEBiomeKeys.RIVER));
        context.register(MEBiomeKeys.RIVER_RUNNING, createRiverBiome(context, MEBiomeKeys.RIVER_RUNNING));
        context.register(MEBiomeKeys.ROHAN, createRohanBiome(context, MEBiomeKeys.ROHAN));
        context.register(MEBiomeKeys.SEA_OF_RHUN, createLakeBiome(context, MEBiomeKeys.SEA_OF_RHUN));
        context.register(MEBiomeKeys.SARN_GEBIR_SHORES, createIronHillsBiome(context, MEBiomeKeys.SARN_GEBIR_SHORES, false));
        context.register(MEBiomeKeys.SARN_GEBIR_WILDLANDS, createIronHillsBiome(context, MEBiomeKeys.SARN_GEBIR_WILDLANDS, false));
        context.register(MEBiomeKeys.SHIRE, createShireBiome(context, MEBiomeKeys.SHIRE, 0));
        context.register(MEBiomeKeys.SHIRE_EDGE, createShireBiome(context, MEBiomeKeys.SHIRE_EDGE, 1));
        context.register(MEBiomeKeys.SHIRE_HILLS, createShireBiome(context, MEBiomeKeys.SHIRE_HILLS, 2));
        context.register(MEBiomeKeys.SHIRE_WOODS, createShireBiome(context, MEBiomeKeys.SHIRE_WOODS, 3));
        context.register(MEBiomeKeys.SHIRE_FOREST, createShireBiome(context, MEBiomeKeys.SHIRE_FOREST, 4));
        context.register(MEBiomeKeys.SOUTHERN_DUNLAND, createNorthDunlandBiome(context, MEBiomeKeys.SOUTHERN_DUNLAND, false));
        context.register(MEBiomeKeys.SOUTHERN_EPHEL_DUATH, createMordorMountainsBiome(context, MEBiomeKeys.SOUTHERN_EPHEL_DUATH));
        context.register(MEBiomeKeys.SOUTHERN_EPHEL_DUATH_BASE, createMordorMountainsBiome(context, MEBiomeKeys.SOUTHERN_EPHEL_DUATH_BASE));
        context.register(MEBiomeKeys.SOUTHERN_EPHEL_DUATH_PEAKS, createMordorMountainsBiome(context, MEBiomeKeys.SOUTHERN_EPHEL_DUATH_PEAKS));
        context.register(MEBiomeKeys.SOUTHEAST_RHOVANION, createRhunBiome(context, MEBiomeKeys.SOUTHEAST_RHOVANION));
        context.register(MEBiomeKeys.SOUTHERN_FOROCHEL, createSouthernForochelBiome(context, MEBiomeKeys.SOUTHERN_FOROCHEL));
        context.register(MEBiomeKeys.THE_ANGLE, createTheAngleBiome(context, MEBiomeKeys.THE_ANGLE));
        context.register(MEBiomeKeys.THE_OLD_FOREST, createTheOldForestBiome(context, MEBiomeKeys.THE_OLD_FOREST));
        context.register(MEBiomeKeys.THE_WHITE_DOWNS, createTheWhiteDownsBiome(context, MEBiomeKeys.THE_WHITE_DOWNS));
        context.register(MEBiomeKeys.THE_WOLD, createRohanBiome(context, MEBiomeKeys.THE_WOLD));
        context.register(MEBiomeKeys.TOLFALAS, createTolfalasBiome(context, MEBiomeKeys.TOLFALAS));
        context.register(MEBiomeKeys.TOROGWAITH, createTorogwaithBiome(context, MEBiomeKeys.TOROGWAITH));
        context.register(MEBiomeKeys.TROLLSHAWS, createTrollshawsBiome(context, MEBiomeKeys.TROLLSHAWS));
        context.register(MEBiomeKeys.UDUN, createMordorBiome(context, MEBiomeKeys.UDUN));
        context.register(MEBiomeKeys.UMBAR, createUmbarBiome(context, MEBiomeKeys.UMBAR));
        context.register(MEBiomeKeys.WASTE_POND, createWastePondBiome(context, MEBiomeKeys.WASTE_POND));
        context.register(MEBiomeKeys.WEBBED_WOODS, createMirkwoodBiome(context, MEBiomeKeys.WEBBED_WOODS, true, true));
        context.register(MEBiomeKeys.WITHERED_HEATH, createWitheredHeathBiome(context, MEBiomeKeys.WITHERED_HEATH));
        context.register(MEBiomeKeys.WHITE_MOUNTAINS_BASE, createWhiteMountainsBiome(context, MEBiomeKeys.WHITE_MOUNTAINS_BASE));
        context.register(MEBiomeKeys.WHITE_MOUNTAINS, createWhiteMountainsFaces(context, MEBiomeKeys.WHITE_MOUNTAINS, false));
        context.register(MEBiomeKeys.WHITE_MOUNTAINS_PEAKS, createWhiteMountainsFaces(context, MEBiomeKeys.WHITE_MOUNTAINS_PEAKS, true));
        context.register(MEBiomeKeys.WOODLAND_REALM, createWoodlandRealmBiome(context, MEBiomeKeys.WOODLAND_REALM));
        context.register(MEBiomeKeys.WOODLAND_FOOTHILLS, createMirkwoodMountainsBiome(context, MEBiomeKeys.WOODLAND_FOOTHILLS, true));
        context.register(MEBiomeKeys.WOODLAND_HILLS, createMirkwoodMountainsBiome(context, MEBiomeKeys.WOODLAND_HILLS, true));
    }

    public static Biome createAnduinBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, boolean forest) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addDefaultVegetation(generationSettings);
        ModBiomeFeatures.addCoarseDirtOre(vegetation);
        ModBiomeFeatures.addFlowerGreenJewel(vegetation);
        ModBiomeFeatures.addGravelOre(vegetation);
        ModBiomeFeatures.addOakBushes(vegetation);
        ModBiomeFeatures.addWildGrass(vegetation);
        ModBiomeFeatures.addRareWilderGrass(vegetation);
        vegetation.add(VegetationPlacedFeatures.PATCH_TALL_GRASS_2);
        vegetation.add(VegetationPlacedFeatures.TREES_PLAINS);

        if(!forest) {
            ModSpawnSettingsBuilder.addFarmAnimals(spawnSettings);
            ModBiomeFeatures.addVeryRareBirchTrees(vegetation);
            ModBiomeFeatures.addRareLarchTrees(vegetation);
            ModBiomeFeatures.addScarceMapleTrees(vegetation);
            ModBiomeFeatures.addDolomiteBoulder(vegetation);
        } else {
            ModSpawnSettingsBuilder.addDeer(spawnSettings);
            vegetation.add(VegetationPlacedFeatures.PATCH_LARGE_FERN);
            ModBiomeFeatures.addBracken(vegetation);
            ModBiomeFeatures.addMossyBoulder(vegetation);
            ModBiomeFeatures.addBirchTrees(vegetation);
            ModBiomeFeatures.addMegaBirchTrees(vegetation);
            ModBiomeFeatures.addDarkOakTrees(vegetation);
            ModBiomeFeatures.addLarchTrees(vegetation);
            ModBiomeFeatures.addMapleTrees(vegetation);
            ModBiomeFeatures.addCommonOakTrees(vegetation);
            ModBiomeFeatures.addPineTrees(vegetation);
            ModBiomeFeatures.addScarceBlackPineTrees(vegetation);
            ModBiomeFeatures.addScarceSpruceTrees(vegetation);
            ModBiomeFeatures.addRareMegaOakTrees(vegetation);
            ModBiomeFeatures.addPodzolOre(vegetation);
            ModBiomeFeatures.addForestMoss(vegetation);
            ModBiomeFeatures.addForestBlockMoss(vegetation);
            ModBiomeFeatures.addSpruceBushes(vegetation);
            ModBiomeFeatures.addRareMorsel(vegetation);
            ModBiomeFeatures.addRareWhiteMushroom(vegetation);
        }

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings);
    }

    public static Biome createAnorienBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addPlainsMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addGondorVegetation(generationSettings);
        ModBiomeFeatures.addGravelOre(vegetation);
        ModBiomeFeatures.addOakBushes(vegetation);
        ModBiomeFeatures.addTuftGrass(vegetation);
        ModBiomeFeatures.addDioriteBoulder(vegetation);

        vegetation.add(VegetationPlacedFeatures.TREES_PLAINS);
        ModBiomeFeatures.addRareLebethronTrees(vegetation);

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings);
    }

    public static Biome createBarrowDownsBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        ModBiomeFeatures.addDisks(undergroundOres);
        ModBiomeFeatures.addGravelOre(vegetation);

        ModBiomeFeatures.addGrass(vegetation);

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings);
    }

    public static Biome createBeleriandIslandBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addFarmAnimals(spawnSettings);
        ModSpawnSettingsBuilder.addMountainsMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addDefaultVegetation(generationSettings);
        ModBiomeFeatures.addCornflower(vegetation);
        ModBiomeFeatures.addGravelOre(vegetation);
        ModBiomeFeatures.addLimestoneBoulder(vegetation);
        ModBiomeFeatures.addGneissBoulder(vegetation);
        ModBiomeFeatures.addOakBushes(vegetation);
        ModBiomeFeatures.addRareBirchTrees(vegetation);
        ModBiomeFeatures.addMegaBirchTrees(vegetation);

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings);
    }

    public static Biome createBelfalasBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, boolean hills) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addPlainsMobs(spawnSettings);
        ModSpawnSettingsBuilder.addSwan(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        ModBiomeFeatures.addCornflower(vegetation);
        ModBiomeFeatures.addTuftGrass(vegetation);
        ModBiomeFeatures.addRareWilderGrass(vegetation);
        ModBiomeFeatures.addGravelOre(vegetation);
        ModBiomeFeatures.addCalciteBoulder(vegetation);

        if(hills) {
            addNordicVegetation(generationSettings);
            ModBiomeFeatures.addCalciteOre(vegetation);
            ModBiomeFeatures.addDioriteOre(vegetation);
            ModBiomeFeatures.addTuffOre(vegetation);
            ModBiomeFeatures.addGrassToStoneOre(vegetation);
            ModBiomeFeatures.addLarchTrees(vegetation);
            ModBiomeFeatures.addPineTrees(vegetation);
            ModBiomeFeatures.addRareSpruceTrees(vegetation);
            ModBiomeFeatures.addSpruceBushes(vegetation);
        } else {
            addGondorVegetation(generationSettings);
            vegetation.add(VegetationPlacedFeatures.TREES_PLAINS);
            ModBiomeFeatures.addRareBirchTrees(vegetation);
            ModBiomeFeatures.addRareLebethronTrees(vegetation);
            ModBiomeFeatures.addRareOakBushes(vegetation);
        }

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings);
    }

    public static Biome createBelfalasShoresBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addGondorVegetation(generationSettings);
        ModBiomeFeatures.addDioriteBoulder(vegetation);
        ModBiomeFeatures.addHorokaka(vegetation);
        ModBiomeFeatures.addRareOakBushes(vegetation);

        vegetation.add(VegetationPlacedFeatures.TREES_PLAINS);

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings);
    }

    public static Biome createBlackRootVale(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, boolean forest) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        ModBiomeFeatures.addFlowerGreenJewel(vegetation);
        ModBiomeFeatures.addTuftGrass(vegetation);
        ModBiomeFeatures.addRareWilderGrass(vegetation);
        ModBiomeFeatures.addGravelOre(vegetation);
        ModBiomeFeatures.addCalciteBoulder(vegetation);
        ModBiomeFeatures.addDioriteBoulder(vegetation);
        ModBiomeFeatures.addSpruceBushes(vegetation);
        ModBiomeFeatures.addDryPineBushes(vegetation);

        if(forest) {
            ModSpawnSettingsBuilder.addDeer(spawnSettings);
            addNordicVegetation(generationSettings);
            ModBiomeFeatures.addLarchTrees(vegetation);
            ModBiomeFeatures.addCommonLebethronTrees(vegetation);
            ModBiomeFeatures.addPineTrees(vegetation);
            ModBiomeFeatures.addBlackPineTrees(vegetation);
            ModBiomeFeatures.addRareSpruceTrees(vegetation);
            ModBiomeFeatures.addPodzolOre(vegetation);
        } else {
            ModSpawnSettingsBuilder.addPlainsMobs(spawnSettings);
            addGondorVegetation(generationSettings);
            vegetation.add(VegetationPlacedFeatures.TREES_PLAINS);
            ModBiomeFeatures.addWildGrass(vegetation);
            ModBiomeFeatures.addRareLebethronTrees(vegetation);
            ModBiomeFeatures.addScarceBlackPineTrees(vegetation);
        }

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings);
    }

    public static Biome createBlueMountainsBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, int step) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addMountainsMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));
        float temperature = 0.5f;
        if(step == 0 || step == 1){
            ModBiomeFeatures.addCornflower(vegetation);
            ModBiomeFeatures.addBlueTuff(vegetation);
            addNordicVegetation(generationSettings);
            ModBiomeFeatures.addSparseLarchTrees(vegetation);
            ModBiomeFeatures.addSparsePineTrees(vegetation);
            ModBiomeFeatures.addScarceSpruceTrees(vegetation);
            ModBiomeFeatures.addSpruceBushes(vegetation);
            if(step == 0){
                ModBiomeFeatures.addScarceBlackPineTrees(vegetation);
                ModBiomeFeatures.addBlueTuffBoulder(vegetation);
            }
        } else if (step == 2) {
            ModBiomeFeatures.addSparsePineTrees(vegetation);
        }else if(step == 3){
            temperature = -0.1f;
        }

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings, temperature, true);
    }

    public static Biome createCorsairCoastBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addHaradMobs(spawnSettings);
        ModSpawnSettingsBuilder.addWolves(spawnSettings);
        ModSpawnSettingsBuilder.addLlama(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addHaradVegetation(generationSettings);
        ModBiomeFeatures.addCoastalFoliage(vegetation);
        vegetation.add(VegetationPlacedFeatures.PATCH_CACTUS_DESERT);
        ModBiomeFeatures.addGraniteBoulder(vegetation);
        ModBiomeFeatures.addSandStoneBoulder(vegetation);
        ModBiomeFeatures.addStoneBoulder(vegetation);
        ModBiomeFeatures.addSandOre(vegetation);
        ModBiomeFeatures.addGravelOre(vegetation);

        ModBiomeFeatures.addPalmTrees(vegetation);

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings);
    }

    public static Biome createDaleBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, int step) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addPlainsMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        if (step == 0){
            addDefaultVegetation(generationSettings);
            ModBiomeFeatures.addCornflower(vegetation);
            ModBiomeFeatures.addGravelOre(vegetation);
            ModBiomeFeatures.addVeryRareSpruceTrees(vegetation);
            ModBiomeFeatures.addSpruceBushes(vegetation);
        }else if(step == 1) {
            addDefaultVegetation(generationSettings);
            ModBiomeFeatures.addTallGrass(vegetation);
            ModBiomeFeatures.addPodzolOre(vegetation);
            ModBiomeFeatures.addCornflower(vegetation);
            ModBiomeFeatures.addGravelOre(vegetation);
            ModBiomeFeatures.addCommonLarchTrees(vegetation);
            ModBiomeFeatures.addCommonPineTrees(vegetation);
            ModBiomeFeatures.addFrequentSpruceTrees(vegetation);
            ModBiomeFeatures.addBlackPineTrees(vegetation);
            ModBiomeFeatures.addForestMoss(vegetation);
            ModBiomeFeatures.addForestBlockMoss(vegetation);
            ModBiomeFeatures.addSpruceBushes(vegetation);
            ModBiomeFeatures.addOakTrees(vegetation);
        }else if(step == 2) {
            addDefaultVegetation(generationSettings);
            ModBiomeFeatures.addCornflower(vegetation);
            ModBiomeFeatures.addBracken(vegetation);
            ModBiomeFeatures.addCoarseDirtOre(vegetation);
            ModBiomeFeatures.addGravelOre(vegetation);
            ModBiomeFeatures.addSmoothDolomiteBoulder(vegetation);
            ModBiomeFeatures.addSpruceBushes(vegetation);
            ModBiomeFeatures.addVeryRareSpruceTrees(vegetation);
        }

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings);
    }

    public static Biome createDeadMarshesBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        undergroundOres.add(MiscPlacedFeatures.DISK_CLAY);
        ModBiomeFeatures.addMordorLichen(vegetation);
        ModBiomeFeatures.addBasaltOre(vegetation);
        ModBiomeFeatures.addBlackSand(vegetation);
        ModBiomeFeatures.addBrownBolete(vegetation);
        ModBiomeFeatures.addCommonToughBerries(vegetation);
        ModBiomeFeatures.addDeadRushes(vegetation);
        ModBiomeFeatures.addMorsel(vegetation);
        ModBiomeFeatures.addGravelOre(vegetation);
        ModBiomeFeatures.addMireOre(vegetation);
        ModBiomeFeatures.addMudOre(vegetation);
        ModBiomeFeatures.addAshenDirtOre(vegetation);
        ModBiomeFeatures.addOakBushes(vegetation);
        ModBiomeFeatures.addRareSmallSwampOakTrees(vegetation);
        ModBiomeFeatures.addShriveledShrubs(vegetation);
        ModBiomeFeatures.addTallGrass(vegetation);
        ModBiomeFeatures.addWheatGrass(vegetation);
        ModBiomeFeatures.addWildGrass(vegetation);
        ModBiomeFeatures.addAbundantWaterDelta(vegetation);
        ModBiomeFeatures.addGrass(vegetation);
        vegetation.add(VegetationPlacedFeatures.PATCH_TALL_GRASS);

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings);
    }

    public static Biome createDeadMarshesWaterBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        undergroundOres.add(MiscPlacedFeatures.DISK_CLAY);
        ModBiomeFeatures.addAshenGravelOre(undergroundOres);
        ModBiomeFeatures.addAshenSandOre(undergroundOres);
        ModBiomeFeatures.addSoulSandOre(vegetation);
        vegetation.add(OceanPlacedFeatures.KELP_WARM);
        vegetation.add(OceanPlacedFeatures.SEAGRASS_NORMAL);
        ModBiomeFeatures.addGrass(vegetation);

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings);
    }

    public static Biome createDorwinionBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addPlainsMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        ModBiomeFeatures.addDisks(undergroundOres);
        ModBiomeFeatures.addGrass(vegetation);
        vegetation.add(VegetationPlacedFeatures.BROWN_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.RED_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.PATCH_SUGAR_CANE);
        vegetation.add(VegetationPlacedFeatures.PATCH_PUMPKIN);
        ModBiomeFeatures.addFlowerDorwinion(vegetation);
        ModBiomeFeatures.addCornflower(vegetation);
        ModBiomeFeatures.addTuftGrass(vegetation);
        ModBiomeFeatures.addWildGrass(vegetation);
        ModBiomeFeatures.addRareWilderGrass(vegetation);
        ModBiomeFeatures.addRareHeather(vegetation);
        ModBiomeFeatures.addDryDirtOre(vegetation);
        ModBiomeFeatures.addGravelOre(vegetation);

        ModBiomeFeatures.addWildBeetroot(vegetation);
        ModBiomeFeatures.addWildFlax(vegetation);
        ModBiomeFeatures.addWildLettuce(vegetation);
        ModBiomeFeatures.addWildOnion(vegetation);

        ModBiomeFeatures.addCalciteBoulder(vegetation);
        ModBiomeFeatures.addLimestoneBoulder(vegetation);
        ModBiomeFeatures.addOakBushes(vegetation);
        ModBiomeFeatures.addRareBeechTrees(vegetation);
        ModBiomeFeatures.addSparseBirchTrees(vegetation);
        ModBiomeFeatures.addVeryRareMegaOakTrees(vegetation);
        ModBiomeFeatures.addMegaBirchTrees(vegetation);

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings);
    }

    public static Biome createDorwinionHillsBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addMountainsMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addNordicVegetation(generationSettings);
        ModBiomeFeatures.addTuffOre(vegetation);
        ModBiomeFeatures.addCalciteOre(vegetation);
        ModBiomeFeatures.addDioriteOre(vegetation);
        ModBiomeFeatures.addLimestoneOre(vegetation);
        ModBiomeFeatures.addStoneGrassAbundantOre(vegetation);
        ModBiomeFeatures.addCommonOakBush(vegetation);
        ModBiomeFeatures.addBirchTrees(vegetation);
        ModBiomeFeatures.addLarchTrees(vegetation);

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings);
    }

    public static Biome createDunlandFoothillsBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addMountainsMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addNordicVegetation(generationSettings);
        ModBiomeFeatures.addCoarseDirtOre(vegetation);
        ModBiomeFeatures.addDolomiteOre(vegetation);
        ModBiomeFeatures.addGravelOre(vegetation);
        ModBiomeFeatures.addPodzolOre(vegetation);
        ModBiomeFeatures.addStoneGrassOre(vegetation);
        ModBiomeFeatures.addTuffOre(vegetation);
        ModBiomeFeatures.addCommonLarchTrees(vegetation);
        ModBiomeFeatures.addAbundantPineTrees(vegetation);
        ModBiomeFeatures.addAbundantSpruceTrees(vegetation);
        ModBiomeFeatures.addCommonSpruceBushes(vegetation);

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings);
    }

    public static Biome createEmynMuilBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addRabbits(spawnSettings);

        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        ModBiomeFeatures.addGrass(vegetation);
        vegetation.add(VegetationPlacedFeatures.FLOWER_DEFAULT);
        vegetation.add(VegetationPlacedFeatures.BROWN_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.RED_MUSHROOM_NORMAL);
        ModBiomeFeatures.addStoneBoulder(vegetation);
        ModBiomeFeatures.addGravelOre(vegetation);
        //ModBiomeFeatures.addStoneGrassOre(vegetation);
        ModBiomeFeatures.addTerracottaOre(vegetation);
        ModBiomeFeatures.addTuffOre(vegetation);
        ModBiomeFeatures.addDryDirtOre(vegetation);
        ModBiomeFeatures.addTuftGrass(vegetation);

        ModBiomeFeatures.addCommonOakBush(vegetation);
        ModBiomeFeatures.addGraniteBoulder(vegetation);

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings);
    }

    public static Biome createEnedwaithBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addEriadorMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addDefaultVegetation(generationSettings);
        ModBiomeFeatures.addGravelOre(vegetation);
        ModBiomeFeatures.addDryDirtOre(vegetation);
        ModBiomeFeatures.addRareHeather(vegetation);
        ModBiomeFeatures.addWildGrass(vegetation);
        ModBiomeFeatures.addRareWilderGrass(vegetation);
        ModBiomeFeatures.addOakBushes(vegetation);

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings);
    }

    public static Biome createLonelyMountainBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, int step) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addMountainsMobs(spawnSettings);
        ModSpawnSettingsBuilder.addNordicMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));
        float temperature = 0.4f;

        if(step == 0) {
            //ModBiomeFeatures.addStoneGrassAbundantOre(vegetation);
            //ModBiomeFeatures.addCoarseDirtOre(vegetation);
            addMountainVegetation(generationSettings);
            ModBiomeFeatures.addLarchTrees(vegetation);
            ModBiomeFeatures.addPineTrees(vegetation);
            ModBiomeFeatures.addSpruceTrees(vegetation);
            ModBiomeFeatures.addCommonSpruceBushes(vegetation);
        } else if (step == 1) {
            //ModBiomeFeatures.addStoneGrassOre(vegetation);
            //ModBiomeFeatures.addDolomiteOre(vegetation);
            ModBiomeFeatures.addSparseLarchTrees(vegetation);
            ModBiomeFeatures.addSparsePineTrees(vegetation);
            ModBiomeFeatures.addScarceSpruceTrees(vegetation);
            vegetation.add(VegetationPlacedFeatures.PATCH_LARGE_FERN);
            vegetation.add(VegetationPlacedFeatures.PATCH_GRASS_TAIGA);
            vegetation.add(VegetationPlacedFeatures.PATCH_BERRY_RARE);
        } else {
            //ModBiomeFeatures.addDolomiteOre(vegetation);
            //ModBiomeFeatures.addFrozenStone(vegetation);
            //temperature = 0.0f;
        }
        ModBiomeFeatures.addGravelOre(vegetation);
        ModBiomeFeatures.addAbundantTuffOre(vegetation);

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings, temperature, true);
    }

    public static Biome createEregionBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addFarmAnimals(spawnSettings);
        ModSpawnSettingsBuilder.addWolves(spawnSettings);
        ModSpawnSettingsBuilder.addDeer(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addDefaultVegetation(generationSettings);
        ModBiomeFeatures.addForestMoss(vegetation);
        ModBiomeFeatures.addGravelOre(vegetation);
        ModBiomeFeatures.addPodzolOre(vegetation);
        ModBiomeFeatures.addBirchTrees(vegetation);
        ModBiomeFeatures.addBeechTrees(vegetation);
        ModBiomeFeatures.addHollyTrees(vegetation);
        ModBiomeFeatures.addLarchTrees(vegetation);
        ModBiomeFeatures.addOakTrees(vegetation);
        ModBiomeFeatures.addRareMegaOakTrees(vegetation);
        ModBiomeFeatures.addPineTrees(vegetation);
        ModBiomeFeatures.addSpruceTrees(vegetation);

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings);
    }

    public static Biome createSwampAnduin(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addPlainsMobs(spawnSettings);
        ModSpawnSettingsBuilder.addSwampMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        ModBiomeFeatures.addDisks(undergroundOres);
        ModBiomeFeatures.addGrass(vegetation);
        vegetation.add(VegetationPlacedFeatures.PATCH_PUMPKIN);
        vegetation.add(VegetationPlacedFeatures.FLOWER_SWAMP);
        vegetation.add(VegetationPlacedFeatures.PATCH_SUGAR_CANE_SWAMP);
        ModBiomeFeatures.addWaterDelta(vegetation);
        ModBiomeFeatures.addAbundantMudOre(vegetation);
        ModBiomeFeatures.addDeadRushes(vegetation);
        ModBiomeFeatures.addPackedMudOre(vegetation);
        ModBiomeFeatures.addGravelOre(vegetation);
        ModBiomeFeatures.addMudOre(vegetation);
        ModBiomeFeatures.addMireOre(vegetation);
        ModBiomeFeatures.addRareWilderGrass(vegetation);
        ModBiomeFeatures.addReedsFoliage(vegetation);
        ModBiomeFeatures.addWheatGrass(vegetation);
        ModBiomeFeatures.addRareBeechTrees(vegetation);
        ModBiomeFeatures.addOakVinesTrees(vegetation);

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings);
    }

    public static Biome createFangornBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addWolves(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addDefaultVegetation(generationSettings);
        vegetation.add(VegetationPlacedFeatures.PATCH_TALL_GRASS_2);
        ModBiomeFeatures.addMossyBoulder(vegetation);
        ModBiomeFeatures.addForestMoss(vegetation);
        ModBiomeFeatures.addForestBlockMoss(vegetation);
        ModBiomeFeatures.addOldPodzolOre(vegetation);
        ModBiomeFeatures.addGravelOre(vegetation);
        ModBiomeFeatures.addWildGrass(vegetation);
        ModBiomeFeatures.addRareMorsel(vegetation);
        ModBiomeFeatures.addRareWhiteMushroom(vegetation);

        ModBiomeFeatures.addOakBushes(vegetation);
        ModBiomeFeatures.addHollyTrees(vegetation);
        ModBiomeFeatures.addMegaBirchCommonTrees(vegetation);
        ModBiomeFeatures.addMegaDarkOakCommonTrees(vegetation);
        ModBiomeFeatures.addMegaOakCommonTrees(vegetation);

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings);
    }

    public static Biome createForodwaithBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addForochelMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addForodwaithVegetation(generationSettings);

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings, -0.8f, true);
    }

    public static Biome createGondorBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addPlainsMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addGondorVegetation(generationSettings);
        ModBiomeFeatures.addGravelOre(vegetation);
        ModBiomeFeatures.addDioriteBoulder(vegetation);

        vegetation.add(VegetationPlacedFeatures.TREES_PLAINS);
        ModBiomeFeatures.addRareBeechTrees(vegetation);
        ModBiomeFeatures.addRareLebethronTrees(vegetation);

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings);
    }

    public static Biome createGondorRiverSideBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addGondorVegetation(generationSettings);
        ModBiomeFeatures.addGravelOre(vegetation);
        ModBiomeFeatures.addDioriteBoulder(vegetation);
        ModBiomeFeatures.addDryGrass(vegetation);

        vegetation.add(VegetationPlacedFeatures.TREES_PLAINS);

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings);
    }

    public static Biome createGreyMountainsBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, int step) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addMountainsMobs(spawnSettings);
        ModSpawnSettingsBuilder.addNordicMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        ModBiomeFeatures.addAshBlockOre(vegetation);
        ModBiomeFeatures.addGravelOre(vegetation);
        ModBiomeFeatures.addAbundantTuffOre(vegetation);
        if(step == 0) {
            addNordicVegetation(generationSettings);
            addNordicTrees(generationSettings);
        }

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings);
    }

    public static Biome createGreyPlainsBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addMountainsMobs(spawnSettings);
        ModSpawnSettingsBuilder.addWolves(spawnSettings);
        ModSpawnSettingsBuilder.addNordicMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addNordicVegetation(generationSettings);
        ModBiomeFeatures.addCoarseDirtOre(vegetation);
        ModBiomeFeatures.addGravelOre(vegetation);
        ModBiomeFeatures.addRareForestMoss(vegetation);

        ModBiomeFeatures.addSparseLarchTrees(vegetation);
        ModBiomeFeatures.addSparsePineTrees(vegetation);
        ModBiomeFeatures.addRareSpruceTrees(vegetation);
        ModBiomeFeatures.addScarceBlackPineTrees(vegetation);
        ModBiomeFeatures.addSpruceBushes(vegetation);

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings);
    }

    public static Biome createGreyPlainsTaiga(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addFarmAnimals(spawnSettings);
        ModSpawnSettingsBuilder.addWolves(spawnSettings);
        ModSpawnSettingsBuilder.addNordicMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        ModBiomeFeatures.addCornflower(vegetation);
        addNordicVegetation(generationSettings);
        addNordicTrees(generationSettings);
        ModBiomeFeatures.addScarceBlackPineTrees(vegetation);
        ModBiomeFeatures.addSpruceBushes(vegetation);

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings);
    }

    public static Biome createHaradBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addHaradMobs(spawnSettings);
        ModSpawnSettingsBuilder.addFarmAnimals(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addHaradVegetation(generationSettings);
        ModBiomeFeatures.addSandOre(vegetation);
        ModBiomeFeatures.addGravelOre(vegetation);
        ModBiomeFeatures.addOakBushes(vegetation);
        ModBiomeFeatures.addAcaciaTrees(vegetation);
        vegetation.add(VegetationPlacedFeatures.PATCH_DEAD_BUSH);

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings);
    }

    public static Biome createHarondorBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addHaradMobs(spawnSettings);
        ModSpawnSettingsBuilder.addFarmAnimals(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addHaradVegetation(generationSettings);
        ModBiomeFeatures.addOakBushes(vegetation);
        ModBiomeFeatures.addGravelOre(vegetation);
        ModBiomeFeatures.addSandOre(vegetation);
        ModBiomeFeatures.addSandStoneBoulder(vegetation);
        ModBiomeFeatures.addStoneBoulder(vegetation);
        ModBiomeFeatures.addWildFlax(vegetation);

        ModBiomeFeatures.addVeryRareBirchTrees(vegetation);
        ModBiomeFeatures.addVeryRareBeechTrees(vegetation);

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings);
    }

    public static Biome createHaradDesertBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addHaradMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addHaradDesertVegetation(generationSettings);
        vegetation.add(VegetationPlacedFeatures.PATCH_DEAD_BUSH_2);

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings, 0.8f, false);
    }

    public static Biome createHillsOfElvendim(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addMountainsMobs(spawnSettings);
        ModSpawnSettingsBuilder.addNordicMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        //ModBiomeFeatures.addGrassStoneOre(vegetation);
        ModBiomeFeatures.addAbundantTuffOre(vegetation);
        addArthedainVegetation(generationSettings);

        ModBiomeFeatures.addCommonLarchTrees(vegetation);
        ModBiomeFeatures.addPineTrees(vegetation);
        ModBiomeFeatures.addSpruceTrees(vegetation);

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings);
    }

    public static Biome createIronHillsBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, boolean foothills) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addMountainsMobs(spawnSettings);
        ModSpawnSettingsBuilder.addNordicMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        if(foothills) {
            //ModBiomeFeatures.addGrassStoneOre(vegetation);
            ModBiomeFeatures.addCornflower(vegetation);
        }
        addNordicVegetation(generationSettings);
        addNordicTrees(generationSettings);

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings);
    }

    public static Biome createIsengardBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, boolean trees) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addFarmAnimals(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        ModBiomeFeatures.addRareWilderGrass(vegetation);
        ModBiomeFeatures.addOakBushes(vegetation);
        addDefaultVegetation(generationSettings);

        if(trees) {
            ModBiomeFeatures.addOakTrees(vegetation);
            ModBiomeFeatures.addRareBeechTrees(vegetation);
            ModBiomeFeatures.addRareBirchTrees(vegetation);
            ModBiomeFeatures.addSparsePineTrees(vegetation);
            ModBiomeFeatures.addRareSpruceTrees(vegetation);
        }

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings);
    }

    public static Biome createNorthernRhovanionTaiga(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addFarmAnimals(spawnSettings);
        ModSpawnSettingsBuilder.addNordicMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        ModBiomeFeatures.addCornflower(vegetation);
        ModBiomeFeatures.addHematiteBoulder(vegetation);
        ModBiomeFeatures.addIronStoneBoulder(vegetation);
        addNordicVegetation(generationSettings);
        addNordicTrees(generationSettings);
        ModBiomeFeatures.addSpruceBushes(vegetation);

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings);
    }

    public static Biome createIthilienBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, boolean wastes) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addPlainsMobs(spawnSettings);
        ModSpawnSettingsBuilder.addDeer(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        ModBiomeFeatures.addGravelOre(vegetation);
        ModBiomeFeatures.addWildGrass(vegetation);
        ModBiomeFeatures.addFalseOatgrass(vegetation);
        ModBiomeFeatures.addForestMoss(vegetation);
        ModBiomeFeatures.addForestBlockMoss(vegetation);
        ModBiomeFeatures.addRareMorsel(vegetation);
        ModBiomeFeatures.addRareWhiteMushroom(vegetation);
        ModBiomeFeatures.addMossyBoulder(vegetation);
        ModBiomeFeatures.addCommonOakBush(vegetation);
        ModBiomeFeatures.addCoarseDirtOre(vegetation);
        ModBiomeFeatures.addDioriteBoulder(vegetation);
        ModBiomeFeatures.addOakTrees(vegetation);
        ModBiomeFeatures.addSparsePineTrees(vegetation);
        ModBiomeFeatures.addBlackPineTrees(vegetation);
        addGondorVegetation(generationSettings);
        if(!wastes) {
            ModBiomeFeatures.addFlowerGreenJewel(vegetation);
            ModBiomeFeatures.addRareWilderGrass(vegetation);
            ModBiomeFeatures.addBeechTrees(vegetation);
            ModBiomeFeatures.addBirchTrees(vegetation);
            ModBiomeFeatures.addCommonLebethronTrees(vegetation);
            ModBiomeFeatures.addChestnutTrees(vegetation);
            ModBiomeFeatures.addMegaOakTrees(vegetation);
        } else {
            ModBiomeFeatures.addCommonToughBerries(undergroundOres);
            ModBiomeFeatures.addAshenDirtOre(undergroundOres);
            ModBiomeFeatures.addRareBeechTrees(vegetation);
            ModBiomeFeatures.addRareBirchTrees(vegetation);
            ModBiomeFeatures.addLebethronTrees(vegetation);
            ModBiomeFeatures.addDeadPineTrees(vegetation);
            ModBiomeFeatures.addDeadBlackPineTrees(vegetation);
        }

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings);
    }

    public static Biome createLamedonBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addPlainsMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addGondorVegetation(generationSettings);
        ModBiomeFeatures.addGravelOre(vegetation);
        ModBiomeFeatures.addCoarseDirtOre(vegetation);
        ModBiomeFeatures.addTurfOre(vegetation);
        ModBiomeFeatures.addCalciteBoulder(vegetation);
        ModBiomeFeatures.addDioriteBoulder(vegetation);

        vegetation.add(VegetationPlacedFeatures.TREES_PLAINS);
        ModBiomeFeatures.addOakBushes(vegetation);
        ModBiomeFeatures.addVeryRareBirchTrees(vegetation);
        ModBiomeFeatures.addRareBeechTrees(vegetation);
        ModBiomeFeatures.addRareLebethronTrees(vegetation);

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings);
    }

    public static Biome createLebennin(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addPlainsMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addGondorVegetation(generationSettings);
        ModBiomeFeatures.addLebenninFlowers(vegetation);
        ModBiomeFeatures.addMallos(vegetation);
        ModBiomeFeatures.addGravelOre(vegetation);
        ModBiomeFeatures.addCalciteBoulder(vegetation);
        ModBiomeFeatures.addDioriteBoulder(vegetation);

        vegetation.add(VegetationPlacedFeatures.TREES_PLAINS);
        ModBiomeFeatures.addOakBushes(vegetation);
        ModBiomeFeatures.addVeryRareBirchTrees(vegetation);
        ModBiomeFeatures.addRareBeechTrees(vegetation);
        ModBiomeFeatures.addRareOakTrees(vegetation);
        ModBiomeFeatures.addRareLebethronTrees(vegetation);

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings);
    }

    public static Biome createLindonBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addFarmAnimals(spawnSettings);
        ModSpawnSettingsBuilder.addPlainsMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        // Boenndal :
        // Add heather fields, mix with heath, dry heather and dying grass
        // Need to be thick, probably better to do it via sub biome

        addDefaultVegetation(generationSettings);
        ModBiomeFeatures.addCornflower(vegetation);
        ModBiomeFeatures.addRareForestMoss(vegetation);
        ModBiomeFeatures.addGravelOre(vegetation);
        ModBiomeFeatures.addLimestoneBoulder(vegetation);
        ModBiomeFeatures.addOakBushes(vegetation);
        ModBiomeFeatures.addRareBirchTrees(vegetation);
        ModBiomeFeatures.addMegaBirchTrees(vegetation);
        ModBiomeFeatures.addRareMegaOakTrees(vegetation);

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings);
    }

    public static Biome createLorienEdgeBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addFarmAnimals(spawnSettings);
        ModSpawnSettingsBuilder.addSwan(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addLothlorienVegetation(generationSettings);
        ModBiomeFeatures.addMallornTrees(vegetation);

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings);
    }

    public static Biome createLothlorienBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addFarmAnimals(spawnSettings);
        ModSpawnSettingsBuilder.addSwan(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addLothlorienVegetation(generationSettings);
        ModBiomeFeatures.addSmallMallornTress(vegetation);
        ModBiomeFeatures.addOakTrees(vegetation);
        ModBiomeFeatures.addSparseBirchTrees(vegetation);
        ModBiomeFeatures.addMegaMallornTrees(vegetation);

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings);
    }

    public static Biome createLossarnach(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, int step) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addPlainsMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addGondorVegetation(generationSettings);
        ModBiomeFeatures.addWildBellPepper(vegetation);
        ModBiomeFeatures.addWheatGrass(vegetation);
        ModBiomeFeatures.addWildGrass(vegetation);
        ModBiomeFeatures.addRareWilderGrass(vegetation);

        if(step == 0) { // Plains
            ModBiomeFeatures.addLossarnachFlowers(vegetation);
            ModBiomeFeatures.addGravelOre(vegetation);
            ModBiomeFeatures.addDioriteBoulder(vegetation);
            vegetation.add(VegetationPlacedFeatures.TREES_PLAINS);
            ModBiomeFeatures.addOakBushes(vegetation);
            ModBiomeFeatures.addVeryRareBirchTrees(vegetation);
            ModBiomeFeatures.addRareBeechTrees(vegetation);
            ModBiomeFeatures.addRareOakTrees(vegetation);
            ModBiomeFeatures.addRareLebethronTrees(vegetation);
        } else if (step == 1) { // Flower valley
            ModBiomeFeatures.addLossarnachFlowersCommon(vegetation);
            ModBiomeFeatures.addWildPotato(vegetation);
            ModBiomeFeatures.addSparsePineTrees(vegetation);
            ModBiomeFeatures.addRareBirchTrees(vegetation);
            ModBiomeFeatures.addRareOakTrees(vegetation);
            ModBiomeFeatures.addRareLebethronTrees(vegetation);
            ModBiomeFeatures.addChestnutTrees(vegetation);
        } else { // Forest

        }





        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings);
    }

    public static Biome createMinhiriathBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addEriadorMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addEriadorVegetation(generationSettings);
        ModBiomeFeatures.addAndesiteBoulder(vegetation);
        ModBiomeFeatures.addDryDirtOre(vegetation);
        ModBiomeFeatures.addGravelOre(vegetation);
        ModBiomeFeatures.addTuftGrass(vegetation);
        ModBiomeFeatures.addRareWilderGrass(vegetation);
        ModBiomeFeatures.addWheatGrass(vegetation);
        ModBiomeFeatures.addCommonOakBush(vegetation);
        ModBiomeFeatures.addWildCarrot(vegetation);

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings);
    }

    public static Biome createMirkwoodBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, boolean bigTrees, boolean dark) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addWolves(spawnSettings);
        if(!dark) ModSpawnSettingsBuilder.addDeer(spawnSettings);

        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addMirkwoodVegetation(generationSettings);
        ModBiomeFeatures.addMirkwoodRoots(vegetation);
        ModBiomeFeatures.addMudOre(vegetation);
        ModBiomeFeatures.addRareMorsel(vegetation);
        ModBiomeFeatures.addRareWhiteMushroom(vegetation);

        if(bigTrees) {
            addMegaMirkwoodTrees(generationSettings);
            ModBiomeFeatures.addSmallMirkwoodTrees(vegetation);
            ModBiomeFeatures.addCorruptedMoss(vegetation);
        } else {
            addMirkwoodTrees(generationSettings);
            if(!dark) {
                ModBiomeFeatures.addForestMoss(vegetation);
                ModBiomeFeatures.addForestBlockMoss(vegetation);
            }
        }

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings);
    }

    public static Biome createMirkwoodMountainsBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, boolean foothill) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();

        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));
        ModBiomeFeatures.addGraniteBoulder(vegetation);
        float temperature = 0.3f;
        if(foothill) {
            addMirkwoodVegetation(generationSettings);
            ModBiomeFeatures.addSmallMirkwoodTrees(vegetation);
            //ModBiomeFeatures.addGrassStoneOre(vegetation);
            ModBiomeFeatures.addGravelOre(vegetation);
            ModBiomeFeatures.addForestMoss(vegetation);
            ModBiomeFeatures.addMirkwoodRoots(vegetation);
            ModBiomeFeatures.addMudOre(vegetation);
            ModBiomeFeatures.addRareMorsel(vegetation);
            ModBiomeFeatures.addRareWhiteMushroom(vegetation);
        } else {
            temperature = 0.0f;
            ModBiomeFeatures.addGraniteOre(vegetation);
            ModBiomeFeatures.addDripstoneOre(vegetation);
            ModBiomeFeatures.addTuffOre(vegetation);
        }

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings, temperature, true);
    }

    public static Biome createMirkwoodSwampBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addSwampMobs(spawnSettings);

        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addMirkwoodVegetation(generationSettings);
        ModBiomeFeatures.addDeadRushes(vegetation);
        ModBiomeFeatures.addWaterDelta(vegetation);
        ModBiomeFeatures.addForestMoss(vegetation);
        ModBiomeFeatures.addReedsFoliage(vegetation);
        ModBiomeFeatures.addWheatGrass(vegetation);
        ModBiomeFeatures.addMireOre(vegetation);
        ModBiomeFeatures.addMudOre(vegetation);

        addMirkwoodTrees(generationSettings);
        ModBiomeFeatures.addWillowTrees(vegetation);

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings);
    }

    public static Biome createMistyMountainsBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, int step) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        float temperature = 0.35f;
        addNordicVegetation(generationSettings);
        if(step == 0) {
            ModBiomeFeatures.addGravelOre(vegetation);
            ModBiomeFeatures.addLarchTrees(vegetation);
            ModBiomeFeatures.addPineTrees(vegetation);
            ModBiomeFeatures.addSpruceTrees(vegetation);
            ModBiomeFeatures.addCommonSpruceBushes(vegetation);
        } else if (step == 1){
            ModBiomeFeatures.addPowderSnowOre(vegetation);
            temperature = -0.3f;
        } else if (step == 2){
            ModSpawnSettingsBuilder.addRareWolves(spawnSettings);
            ModBiomeFeatures.addPowderSnowOre(vegetation);
            temperature = -0.6f;
        }

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings,temperature, true);
    }

    public static Biome createMordorBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addMordorVegetation(generationSettings);
        ModBiomeFeatures.addMordorLichen(vegetation);
        ModBiomeFeatures.addPumicePileRare(vegetation);
        ModBiomeFeatures.addAshenStoneBoulder(vegetation);
        ModBiomeFeatures.addPumiceColumnRare(vegetation);
        ModBiomeFeatures.addAshenDirtStoneOre(vegetation);
        ModBiomeFeatures.addAshenStoneGravelOre(vegetation);
        ModBiomeFeatures.addAshenStoneSandOre(vegetation);
        ModBiomeFeatures.addLavaMagmaLake(generationSettings);
        ModBiomeFeatures.addBasaltPile(vegetation);
        ModBiomeFeatures.addBlackStonePile(vegetation);

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings, 0.7f, false);
    }

    public static Biome createGorgorothBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addMordorVegetation(generationSettings);
        ModBiomeFeatures.addPumicePileSparse(vegetation);
        ModBiomeFeatures.addPumiceColumn(vegetation);
        ModBiomeFeatures.addPumiceColumnLarge(vegetation);
        ModBiomeFeatures.addAshenStoneBoulder(vegetation);
        ModBiomeFeatures.addAshenDirtStoneOre(vegetation);
        ModBiomeFeatures.addAshenStoneGravelOre(vegetation);
        ModBiomeFeatures.addAshenStoneSandOre(vegetation);
        ModBiomeFeatures.addLavaMagmaLake(generationSettings);
        ModBiomeFeatures.addBlackSand(vegetation);
        ModBiomeFeatures.addBasaltPile(vegetation);
        ModBiomeFeatures.addBlackStonePile(vegetation);

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings, 0.7f, false);
    }

    public static Biome createMordorMountainsBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        ModBiomeFeatures.addMordorLichen(vegetation);
        ModBiomeFeatures.addAshBlockOre(vegetation);
        ModBiomeFeatures.addPumicePileRare(vegetation);
        ModBiomeFeatures.addAshenStoneGravelOre(vegetation);
        ModBiomeFeatures.addAshenStoneSandOre(vegetation);
        ModBiomeFeatures.addAshenDirtStoneOre(vegetation);
        ModBiomeFeatures.addBasaltPile(vegetation);
        ModBiomeFeatures.addBlackStonePile(vegetation);
        ModBiomeFeatures.addShriveledShrubs(vegetation);
        ModBiomeFeatures.addCommonToughBerries(vegetation);

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings, 0.7f, false);
    }

    public static Biome createMordorWastesBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addMordorVegetation(generationSettings);
        ModBiomeFeatures.addDeadRushes(vegetation);
        ModBiomeFeatures.addMordorLichen(vegetation);
        ModBiomeFeatures.addGravelOre(vegetation);
        ModBiomeFeatures.addMireOre(vegetation);
        ModBiomeFeatures.addMudOre(vegetation);
        ModBiomeFeatures.addAshenDirtOre(vegetation);

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings);
    }

    public static Biome createMorgulVale(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        ModBiomeFeatures.addAshenGravelOre(vegetation);
        ModBiomeFeatures.addAshenSandOre(vegetation);
        ModBiomeFeatures.addAshBlockOre(vegetation);
        ModBiomeFeatures.addDeadRushes(vegetation);
        ModBiomeFeatures.addWildGrass(vegetation);

        ModBiomeFeatures.addPumicePileRare(vegetation);
        ModBiomeFeatures.addAshenStoneBoulder(vegetation);
        ModBiomeFeatures.addRareMorsel(vegetation);
        ModBiomeFeatures.addRareWhiteMushroom(vegetation);
        ModBiomeFeatures.addBasaltPile(vegetation);
        ModBiomeFeatures.addCoarseDirtOre(vegetation);
        ModBiomeFeatures.addOakTrees(vegetation);
        ModBiomeFeatures.addDeadPineTrees(vegetation);
        ModBiomeFeatures.addScarceBlackPineTrees(vegetation);
        ModBiomeFeatures.addRareLebethronTrees(vegetation);

        ModBiomeFeatures.addCommonToughBerries(undergroundOres);
        ModBiomeFeatures.addAshenDirtOre(undergroundOres);


        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings);
    }

    public static Biome createNanCurunirBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addFarmAnimals(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        ModBiomeFeatures.addRareWilderGrass(vegetation);
        ModBiomeFeatures.addOakBushes(vegetation);
        addDefaultVegetation(generationSettings);

        ModBiomeFeatures.addOakTrees(vegetation);
        ModBiomeFeatures.addRareBeechTrees(vegetation);
        ModBiomeFeatures.addBirchTrees(vegetation);
        ModBiomeFeatures.addPineTrees(vegetation);
        ModBiomeFeatures.addSpruceTrees(vegetation);

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings);
    }

    public static Biome createNorthDownsBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addMountainsMobs(spawnSettings);
        ModSpawnSettingsBuilder.addNordicMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        ModBiomeFeatures.addRareWilderGrass(vegetation);
        ModBiomeFeatures.addAndesiteBoulder(vegetation);
        ModBiomeFeatures.addGrassToStoneOre(vegetation);
        ModBiomeFeatures.addAbundantTuffOre(vegetation);
        addEriadorVegetation(generationSettings);

        ModBiomeFeatures.addLarchTrees(vegetation);
        ModBiomeFeatures.addPineTrees(vegetation);
        ModBiomeFeatures.addSpruceTrees(vegetation);

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings);
    }

    public static Biome createNorthDunlandBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, boolean trees) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addFarmAnimals(spawnSettings);
        ModSpawnSettingsBuilder.addNordicMobs(spawnSettings);
        ModSpawnSettingsBuilder.addWolves(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addNordicVegetation(generationSettings);
        ModBiomeFeatures.addGravelOre(vegetation);
        ModBiomeFeatures.addAndesiteBoulder(vegetation);
        ModBiomeFeatures.addDolomiteBoulder(vegetation);
        ModBiomeFeatures.addCoarseDirtOre(vegetation);
        ModBiomeFeatures.addOakBushes(vegetation);
        ModBiomeFeatures.addSpruceBushes(vegetation);

        if(trees) {
            ModBiomeFeatures.addForestMoss(vegetation);
            ModBiomeFeatures.addBirchTrees(vegetation);
            ModBiomeFeatures.addLarchTrees(vegetation);
            ModBiomeFeatures.addOakTrees(vegetation);
            ModBiomeFeatures.addCommonPineTrees(vegetation);
            ModBiomeFeatures.addSpruceTrees(vegetation);
        } else {
            ModBiomeFeatures.addLarchTrees(vegetation);
            ModBiomeFeatures.addSparsePineTrees(vegetation);
            ModBiomeFeatures.addRareSpruceTrees(vegetation);
        }
        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings);
    }

    public static Biome createNorthernWastelands(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addMountainsMobs(spawnSettings);
        ModSpawnSettingsBuilder.addWolves(spawnSettings);
        ModSpawnSettingsBuilder.addNordicMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        vegetation.add(VegetationPlacedFeatures.PATCH_GRASS_TAIGA);
        vegetation.add(VegetationPlacedFeatures.BROWN_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.RED_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.PATCH_BERRY_RARE);
        ModBiomeFeatures.addCoarseDirtOre(vegetation);
        ModBiomeFeatures.addGravelOre(vegetation);
        ModBiomeFeatures.addAndesiteBoulder(vegetation);
        ModBiomeFeatures.addBlueTuffBoulder(vegetation);

        ModBiomeFeatures.addSparseLarchTrees(vegetation);
        ModBiomeFeatures.addSparsePineTrees(vegetation);
        ModBiomeFeatures.addRareSpruceTrees(vegetation);
        ModBiomeFeatures.addSpruceBushes(vegetation);

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings, -0.1f, true);
    }

    public static Biome createNurnEdgeBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addPlainsMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        ModBiomeFeatures.addDisks(undergroundOres);
        ModBiomeFeatures.addGrass(vegetation);
        vegetation.add(VegetationPlacedFeatures.FLOWER_DEFAULT);
        vegetation.add(VegetationPlacedFeatures.BROWN_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.RED_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.PATCH_SUGAR_CANE);
        vegetation.add(VegetationPlacedFeatures.PATCH_PUMPKIN);
        ModBiomeFeatures.addAshenStoneBoulder(vegetation);
        ModBiomeFeatures.addBasaltBoulder(vegetation);
        ModBiomeFeatures.addDeadRushes(vegetation);
        ModBiomeFeatures.addWildLeek(vegetation);
        ModBiomeFeatures.addWildFlax(vegetation);
        ModBiomeFeatures.addToughBerries(vegetation);
        ModBiomeFeatures.addAshenDirtOre(vegetation);
        ModBiomeFeatures.addAshenGravelOre(vegetation);
        ModBiomeFeatures.addDyingGrass(vegetation);
        ModBiomeFeatures.addMordorLichen(vegetation);
        ModBiomeFeatures.addShriveledShrubs(vegetation);

        vegetation.add(VegetationPlacedFeatures.TREES_PLAINS);
        ModBiomeFeatures.addVeryRareBeechTrees(vegetation);
        ModBiomeFeatures.addScarceBlackPineTrees(vegetation);
        ModBiomeFeatures.addDryPineTrees(vegetation);

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings);
    }

    public static Biome createNurnBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addPlainsMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addDefaultVegetation(generationSettings);
        ModBiomeFeatures.addToughBerries(vegetation);
        ModBiomeFeatures.addGravelOre(vegetation);
        ModBiomeFeatures.addAshenDirtOre(vegetation);
        ModBiomeFeatures.addDryDirtOre(vegetation);
        ModBiomeFeatures.addDyingGrass(vegetation);
        ModBiomeFeatures.addRareWilderGrass(vegetation);

        vegetation.add(VegetationPlacedFeatures.TREES_PLAINS);
        ModBiomeFeatures.addVeryRareBeechTrees(vegetation);
        ModBiomeFeatures.addRareBirchTrees(vegetation);
        ModBiomeFeatures.addScarceBlackPineTrees(vegetation);
        ModBiomeFeatures.addVeryRareMegaOakTrees(vegetation);

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings);
    }

    public static Biome createNurnWaterBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addSwampMobs(spawnSettings);
        ModSpawnSettingsBuilder.addRiverAnimals(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addOceanVegetation(generationSettings);
        ModBiomeFeatures.addMudOre(vegetation);

        ModBiomeFeatures.addWillowTrees(vegetation);

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings);
    }

    public static Biome createOldAngmarBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, int step) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        ModBiomeFeatures.addDisks(undergroundOres);
        vegetation.add(VegetationPlacedFeatures.PATCH_LARGE_FERN);
        vegetation.add(VegetationPlacedFeatures.PATCH_GRASS_TAIGA);
        vegetation.add(VegetationPlacedFeatures.FLOWER_DEFAULT);
        vegetation.add(VegetationPlacedFeatures.BROWN_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.RED_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.PATCH_BERRY_RARE);

        ModBiomeFeatures.addBasaltBoulder(vegetation);
        ModBiomeFeatures.addWildGrass(vegetation);
        ModBiomeFeatures.addWhiteMushroom(vegetation);
        ModBiomeFeatures.addToughBerriesRare(vegetation);
        ModBiomeFeatures.addCoarseDirtOre(vegetation);
        ModBiomeFeatures.addAshenGravelOre(vegetation);
        ModBiomeFeatures.addDyingGrass(vegetation);

        float temperature = 0.35f;
        if(step == 0) { // Forest
            ModBiomeFeatures.addAshenDirtOre(vegetation);
            ModBiomeFeatures.addPodzolOre(vegetation);
            ModBiomeFeatures.addBrownBolete(vegetation);
            ModBiomeFeatures.addMorsel(vegetation);
            ModBiomeFeatures.addRareForestMoss(vegetation);
            ModBiomeFeatures.addCommonSpruceBushes(vegetation);
            ModBiomeFeatures.addDeadPineTrees(vegetation);
            ModBiomeFeatures.addDeadBlackPineTrees(vegetation);
            ModBiomeFeatures.addCommonPineTrees(vegetation);
            ModBiomeFeatures.addCommonBlackPineTrees(vegetation);
            ModBiomeFeatures.addFrequentSpruceTrees(vegetation);
        } else if(step == 1) { // Plains
            ModBiomeFeatures.addShriveledShrubs(vegetation);
            ModBiomeFeatures.addSpruceBushes(vegetation);
            ModBiomeFeatures.addScarceBlackPineTrees(vegetation);
            ModBiomeFeatures.addVeryRareSpruceTrees(vegetation);
        } else if(step == 2) { // Cold Hill
            ModBiomeFeatures.addSparsePineTrees(vegetation);
            ModBiomeFeatures.addScarceBlackPineTrees(vegetation);
            ModBiomeFeatures.addScarceSpruceTrees(vegetation);
            ModBiomeFeatures.addSpruceBushes(vegetation);
            ModBiomeFeatures.addStickySnow(vegetation);
            temperature = 0.5f;
        } else if(step == 3) { // Frozen Hill
            ModBiomeFeatures.addShriveledShrubs(vegetation);
            ModBiomeFeatures.addSnowOre(vegetation);
            ModBiomeFeatures.addDeadBlackPineTrees(vegetation);
            ModBiomeFeatures.addSparsePineTrees(vegetation);
            ModBiomeFeatures.addScarceBlackPineTrees(vegetation);
            ModBiomeFeatures.addSpruceTrees(vegetation);
            temperature = -0.2f;
        }

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings, temperature, true);
    }

    public static Biome createOldArthedainBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, int step) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addEriadorMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addArthedainVegetation(generationSettings);

        if(step == 0) { // Meadow
            ModBiomeFeatures.addCommonHeath(vegetation);
            ModBiomeFeatures.addCommonHeather(vegetation);
            ModBiomeFeatures.addRareHeather(vegetation);
            ModBiomeFeatures.addCommonHeatherBush(vegetation);
            ModBiomeFeatures.addAlliumFlower(vegetation);
            ModBiomeFeatures.addLilacFlowerGrowth(vegetation);
            ModBiomeFeatures.addLilacFlower(vegetation);
        } else if(step == 1) { // Plains
            ModBiomeFeatures.addSmoothDolomiteBoulder(vegetation);
            ModBiomeFeatures.addHeath(vegetation);
            ModBiomeFeatures.addVeryRareBirchTrees(vegetation);
            vegetation.add(VegetationPlacedFeatures.TREES_PLAINS);
            ModBiomeFeatures.addOakBushes(vegetation);
        } else if(step == 2) { // Forest
            ModSpawnSettingsBuilder.addDeer(spawnSettings);
            vegetation.add(VegetationPlacedFeatures.PATCH_LARGE_FERN);
            ModBiomeFeatures.addBracken(vegetation);
            ModBiomeFeatures.addFalseOatgrass(vegetation);
            ModBiomeFeatures.addMossyBoulder(vegetation);
            ModBiomeFeatures.addBirchTrees(vegetation);
            ModBiomeFeatures.addSparseBirchTrees(vegetation);
            ModBiomeFeatures.addMegaBirchTrees(vegetation);
            ModBiomeFeatures.addCommonBeechTrees(vegetation);
            ModBiomeFeatures.addLarchTrees(vegetation);
            ModBiomeFeatures.addCommonOakTrees(vegetation);
            ModBiomeFeatures.addCommonPineTrees(vegetation);
            ModBiomeFeatures.addBlackPineTrees(vegetation);
            ModBiomeFeatures.addScarceSpruceTrees(vegetation);
            ModBiomeFeatures.addRareMegaOakTrees(vegetation);
            ModBiomeFeatures.addPodzolOre(vegetation);
            ModBiomeFeatures.addForestMoss(vegetation);
            ModBiomeFeatures.addForestBlockMoss(vegetation);
            ModBiomeFeatures.addSpruceBushes(vegetation);
            ModBiomeFeatures.addRareMorsel(vegetation);
            ModBiomeFeatures.addRareWhiteMushroom(vegetation);
        } else if(step == 3) { // Foothill
            ModBiomeFeatures.addSmoothDolomiteBoulder(vegetation);
            ModBiomeFeatures.addSpruceBushes(vegetation);
            ModBiomeFeatures.addSparsePineTrees(vegetation);
            ModBiomeFeatures.addScarceBlackPineTrees(vegetation);
            ModBiomeFeatures.addScarceSpruceTrees(vegetation);
        }

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings);
    }

    public static Biome createOldCardolanBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, int step) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addEriadorMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addEriadorVegetation(generationSettings);
        vegetation.add(VegetationPlacedFeatures.PATCH_BERRY_RARE);

        ModBiomeFeatures.addRareHeather(vegetation);
        ModBiomeFeatures.addRedHeather(vegetation);
        ModBiomeFeatures.addRareWilderGrass(vegetation);

        if(step == 0) { // Plains
            ModBiomeFeatures.addSmoothDolomiteBoulder(vegetation);
            ModBiomeFeatures.addDryDirtOre(vegetation);
            ModBiomeFeatures.addGravelOre(vegetation);
            ModBiomeFeatures.addHeath(vegetation);
            ModBiomeFeatures.addVeryRareBeechTrees(vegetation);
            ModBiomeFeatures.addTuftGrass(vegetation);
            ModBiomeFeatures.addOakBushes(vegetation);
        } else if(step == 1) { // Forest
            ModSpawnSettingsBuilder.addDeer(spawnSettings);
            vegetation.add(VegetationPlacedFeatures.PATCH_LARGE_FERN);
            ModBiomeFeatures.addFalseOatgrass(vegetation);
            ModBiomeFeatures.addMossyBoulder(vegetation);

            ModBiomeFeatures.addCommonBeechTrees(vegetation);
            ModBiomeFeatures.addBirchTrees(vegetation);
            ModBiomeFeatures.addSparseBirchTrees(vegetation);
            ModBiomeFeatures.addMegaBirchTrees(vegetation);
            ModBiomeFeatures.addChestnutTrees(vegetation);
            ModBiomeFeatures.addMapleTrees(vegetation);
            ModBiomeFeatures.addOakTrees(vegetation);
            ModBiomeFeatures.addCommonOakTrees(vegetation);
            ModBiomeFeatures.addRareMegaOakTrees(vegetation);
            ModBiomeFeatures.addSparsePineTrees(vegetation);
            ModBiomeFeatures.addPodzolOre(vegetation);
            ModBiomeFeatures.addForestMoss(vegetation);
            ModBiomeFeatures.addForestBlockMoss(vegetation);
            ModBiomeFeatures.addSpruceBushes(vegetation);
            ModBiomeFeatures.addRareMorsel(vegetation);
            ModBiomeFeatures.addRareWhiteMushroom(vegetation);
        } else if(step == 2) { // Hill
            vegetation.add(VegetationPlacedFeatures.PATCH_LARGE_FERN);
            ModBiomeFeatures.addBracken(vegetation);
            ModBiomeFeatures.addPodzolOre(vegetation);
            ModBiomeFeatures.addCoarseDirtOre(vegetation);
            ModBiomeFeatures.addGravelOre(vegetation);
            ModBiomeFeatures.addGrassToGraniteOre(vegetation);
            ModBiomeFeatures.addSmoothDolomiteBoulder(vegetation);
            ModBiomeFeatures.addCommonSpruceBushes(vegetation);
            ModBiomeFeatures.addCommonBlackPineTrees(vegetation);
            ModBiomeFeatures.addLarchTrees(vegetation);
            ModBiomeFeatures.addCommonPineTrees(vegetation);
            ModBiomeFeatures.addSpruceTrees(vegetation);
        }

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings);
    }

    public static Biome createOldRhudaurBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, int step) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addWolves(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addNordicVegetation(generationSettings);
        ModBiomeFeatures.addFalseOatgrass(vegetation);
        ModBiomeFeatures.addCoarseDirtOre(vegetation);
        ModBiomeFeatures.addDyingGrass(vegetation);

        if(step == 0) {
            ModSpawnSettingsBuilder.addPlainsMobs(spawnSettings);
            ModBiomeFeatures.addStoneBoulder(vegetation);
            ModBiomeFeatures.addOakBushes(vegetation);
            ModBiomeFeatures.addWheatGrass(vegetation);
            ModBiomeFeatures.addShriveledShrubs(vegetation);
            vegetation.add(VegetationPlacedFeatures.TREES_PLAINS);
        } else if(step == 1) { // Forest
            ModBiomeFeatures.addMossyBoulder(vegetation);
            ModSpawnSettingsBuilder.addDeer(spawnSettings);
            ModBiomeFeatures.addForestMoss(vegetation);
            ModBiomeFeatures.addBeechTrees(vegetation);
            ModBiomeFeatures.addSparseLarchTrees(vegetation);
            ModBiomeFeatures.addOakTrees(vegetation);
            ModBiomeFeatures.addOakBushes(vegetation);
            ModBiomeFeatures.addDeadPineTrees(vegetation);
            ModBiomeFeatures.addSparsePineTrees(vegetation);
            ModBiomeFeatures.addBlackPineTrees(vegetation);
            ModBiomeFeatures.addSpruceTrees(vegetation);
            ModBiomeFeatures.addCommonSpruceTrees(vegetation);
            ModBiomeFeatures.addSpruceBushes(vegetation);
            ModBiomeFeatures.addPodzolOre(vegetation);
        } else if(step == 2) { // Hill
            ModBiomeFeatures.addAndesiteBoulder(vegetation);
            ModBiomeFeatures.addDolomiteBoulder(vegetation);
            ModBiomeFeatures.addGravelOre(vegetation);
            ModBiomeFeatures.addGrassToStoneOre(vegetation);
            ModBiomeFeatures.addForestMoss(vegetation);
            ModBiomeFeatures.addSparseLarchTrees(vegetation);
            ModBiomeFeatures.addDeadPineTrees(vegetation);
            ModBiomeFeatures.addPineTrees(vegetation);
            ModBiomeFeatures.addBlackPineTrees(vegetation);
            ModBiomeFeatures.addCommonSpruceTrees(vegetation);
            ModBiomeFeatures.addSpruceBushes(vegetation);
            ModBiomeFeatures.addPodzolOre(vegetation);
        }

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings);
    }

    public static Biome createRhunBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addPlainsMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addRhunVegetation(generationSettings);
        ModBiomeFeatures.addDryDirtOre(vegetation);
        ModBiomeFeatures.addGravelOre(vegetation);
        ModBiomeFeatures.addOakBushes(vegetation);
        ModBiomeFeatures.addWildGrass(vegetation);
        ModBiomeFeatures.addRareWilderGrass(vegetation);

        ModBiomeFeatures.addRareBeechTrees(vegetation);
        ModBiomeFeatures.addRareBirchTrees(vegetation);
        ModBiomeFeatures.addOakTrees(vegetation);

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings);
    }

    public static Biome createRivendellBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addPlainsMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addRivendellVegetation(generationSettings);

        ModBiomeFeatures.addRareBeechTrees(vegetation);
        ModBiomeFeatures.addSparseBirchTrees(vegetation);
        ModBiomeFeatures.addVeryRareMegaOakTrees(vegetation);
        ModBiomeFeatures.addMegaBirchTrees(vegetation);

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings);
    }

    public static Biome createRivendellFoothillsBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addMountainsMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addNordicVegetation(generationSettings);
        ModBiomeFeatures.addDolomiteOre(vegetation);
        ModBiomeFeatures.addGravelOre(vegetation);
        ModBiomeFeatures.addLimestoneOre(vegetation);
        //ModBiomeFeatures.addStoneGrassAbundantOre(vegetation);
        ModBiomeFeatures.addTuffOre(vegetation);

        ModBiomeFeatures.addSparseBirchTrees(vegetation);
        ModBiomeFeatures.addLarchTrees(vegetation);
        ModBiomeFeatures.addCommonPineTrees(vegetation);
        ModBiomeFeatures.addFrequentSpruceTrees(vegetation);

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings, 0.4f, true);
    }

    public static Biome createRohanBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addPlainsMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addDefaultVegetation(generationSettings);
        ModBiomeFeatures.addDryDirtOre(vegetation);
        ModBiomeFeatures.addGravelOre(vegetation);
        ModBiomeFeatures.addGraniteBoulder(vegetation);
        ModBiomeFeatures.addFlowerGreenJewel(vegetation);
        ModBiomeFeatures.addOakBushes(vegetation);
        ModBiomeFeatures.addWildGrass(vegetation);
        ModBiomeFeatures.addRareWilderGrass(vegetation);

        vegetation.add(VegetationPlacedFeatures.TREES_PLAINS);
        ModBiomeFeatures.addRareBeechTrees(vegetation);

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings);
    }

    public static Biome createShireBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, int step) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addShireVegetation(generationSettings);

        if(step < 3) {
            ModSpawnSettingsBuilder.addEriadorMobs(spawnSettings);
            ModSpawnSettingsBuilder.addRareSnails(spawnSettings);
            ModBiomeFeatures.addAzureBluetFlower(vegetation);
            ModBiomeFeatures.addFlowerGreenJewel(vegetation);
            ModBiomeFeatures.addRareBeechTrees(vegetation);
            ModBiomeFeatures.addRareBirchTrees(vegetation);
            ModBiomeFeatures.addCommonTurfOre(vegetation);
            vegetation.add(VegetationPlacedFeatures.TREES_PLAINS);
            if(step == 1) {
                ModBiomeFeatures.addOakTrees(vegetation);
            } else if(step == 0) {
                ModBiomeFeatures.addStrawberries(vegetation);
                ModBiomeFeatures.addRareOakBushes(vegetation);
            } else {
                ModBiomeFeatures.addBracken(vegetation);
                ModBiomeFeatures.addFalseOatgrass(vegetation);
                ModBiomeFeatures.addTurfOre(vegetation);
                ModBiomeFeatures.addPackedMudOre(vegetation);
                ModBiomeFeatures.addGraniteBoulder(vegetation);
                ModBiomeFeatures.addSparsePineTrees(vegetation);
                ModBiomeFeatures.addSparseLarchTrees(vegetation);
                ModBiomeFeatures.addOakTrees(vegetation);
                ModBiomeFeatures.addCommonOakBush(vegetation);
            }
        } else {
            ModSpawnSettingsBuilder.addDeer(spawnSettings);
            ModSpawnSettingsBuilder.addWolves(spawnSettings);
            ModBiomeFeatures.addFalseOatgrass(vegetation);
            ModBiomeFeatures.addMossyBoulder(vegetation);
            ModBiomeFeatures.addCoarseDirtOre(vegetation);
            ModBiomeFeatures.addPodzolOre(vegetation);
            ModBiomeFeatures.addRareMorsel(vegetation);

            vegetation.add(VegetationPlacedFeatures.TREES_PLAINS);
            ModBiomeFeatures.addBeechTrees(vegetation);
            ModBiomeFeatures.addBirchTrees(vegetation);
            ModBiomeFeatures.addChestnutTrees(vegetation);
            ModBiomeFeatures.addOakBushes(vegetation);
            ModBiomeFeatures.addPineTrees(vegetation);
            ModBiomeFeatures.addVeryRareSpruceTrees(vegetation);
            ModBiomeFeatures.addForestMoss(vegetation);
            if(step == 4) {
                ModBiomeFeatures.addForestBlockMoss(vegetation);
                ModBiomeFeatures.addOakTrees(vegetation);
                ModBiomeFeatures.addMegaBirchTrees(vegetation);
                ModBiomeFeatures.addMegaDarkOakTrees(vegetation);
                ModBiomeFeatures.addMegaOakCommonTrees(vegetation);
            } else {
                ModBiomeFeatures.addMoss(vegetation);
                ModBiomeFeatures.addRareBeechTrees(vegetation);
                ModBiomeFeatures.addSparseBirchTrees(vegetation);
                ModBiomeFeatures.addRareBirchTrees(vegetation);
                ModBiomeFeatures.addBirchAndOakTrees(vegetation);
                ModBiomeFeatures.addCommonOakTrees(vegetation);
                ModBiomeFeatures.addRareOakTrees(vegetation);
                ModBiomeFeatures.addCommonDarkOakTrees(vegetation);
                ModBiomeFeatures.addCherryBlossomTrees(vegetation);
                ModBiomeFeatures.addRareMegaOakTrees(vegetation);
                ModBiomeFeatures.addRareMegaDarkOakTrees(vegetation);
            }
        }
        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings);
    }

    public static Biome createSouthernForochelBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addNordicMobs(spawnSettings);
        ModSpawnSettingsBuilder.addWolves(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addNordicVegetation(generationSettings);
        ModBiomeFeatures.addGravelOre(vegetation);
        ModBiomeFeatures.addBlueTuffBoulder(vegetation);
        ModBiomeFeatures.addSparsePineTrees(vegetation);
        ModBiomeFeatures.addRareSpruceTrees(vegetation);

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings, -0.4f, true);
    }

    public static Biome createTheAngleBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addEriadorMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addEriadorVegetation(generationSettings);
        ModBiomeFeatures.addRareHeather(vegetation);
        ModBiomeFeatures.addRareForestMoss(vegetation);
        ModBiomeFeatures.addGravelOre(vegetation);
        ModBiomeFeatures.addCoarseDirtOre(vegetation);
        ModBiomeFeatures.addRareWilderGrass(vegetation);

        ModBiomeFeatures.addOakBushes(vegetation);
        ModBiomeFeatures.addRareBeechTrees(vegetation);
        ModBiomeFeatures.addVeryRareBirchTrees(vegetation);
        ModBiomeFeatures.addSparseLarchTrees(vegetation);
        ModBiomeFeatures.addVeryRareSpruceTrees(vegetation);
        ModBiomeFeatures.addMapleTrees(vegetation);

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings);
    }

    public static Biome createTheOldForestBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addDeer(spawnSettings);
        ModSpawnSettingsBuilder.addWolves(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addEriadorVegetation(generationSettings);
        vegetation.add(VegetationPlacedFeatures.PATCH_TALL_GRASS_2);
        ModBiomeFeatures.addMossyBoulder(vegetation);
        ModBiomeFeatures.addForestMoss(vegetation);
        ModBiomeFeatures.addForestBlockMoss(vegetation);
        ModBiomeFeatures.addOldPodzolOre(vegetation);
        ModBiomeFeatures.addGravelOre(vegetation);
        ModBiomeFeatures.addRareMorsel(vegetation);
        ModBiomeFeatures.addRareWhiteMushroom(vegetation);

        ModBiomeFeatures.addOakBushes(vegetation);
        ModBiomeFeatures.addMegaBirchCommonTrees(vegetation);
        ModBiomeFeatures.addMegaDarkOakCommonTrees(vegetation);
        ModBiomeFeatures.addMegaOakCommonTrees(vegetation);

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings);
    }

    public static Biome createTheWhiteDownsBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addMountainsMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addNordicVegetation(generationSettings);
        ModBiomeFeatures.addDioriteOre(vegetation);
        ModBiomeFeatures.addGravelOre(vegetation);
        ModBiomeFeatures.addDioriteBoulder(vegetation);
        ModBiomeFeatures.addCalciteBoulder(vegetation);
        ModBiomeFeatures.addStoneGrassAbundantOre(vegetation);
        ModBiomeFeatures.addTuffOre(vegetation);

        ModBiomeFeatures.addSparseBirchTrees(vegetation);
        ModBiomeFeatures.addLarchTrees(vegetation);
        ModBiomeFeatures.addSparsePineTrees(vegetation);
        ModBiomeFeatures.addHollyTrees(vegetation);

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings, 0.4f, true);
    }

    public static Biome createTolfalasBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addFarmAnimals(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addDefaultVegetation(generationSettings);
        ModBiomeFeatures.addCoarseDirtOre(vegetation);
        ModBiomeFeatures.addGravelOre(vegetation);
        ModBiomeFeatures.addGrassToStoneOre(vegetation);
        ModBiomeFeatures.addRareWilderGrass(vegetation);
        ModBiomeFeatures.addOakBushes(vegetation);

        ModBiomeFeatures.addSparseLarchTrees(vegetation);
        ModBiomeFeatures.addRareLebethronTrees(vegetation);
        ModBiomeFeatures.addSparsePineTrees(vegetation);
        ModBiomeFeatures.addRareSpruceTrees(vegetation);

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings);
    }

    public static Biome createTorogwaithBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addMordorVegetation(generationSettings);
        ModBiomeFeatures.addGrass(vegetation);
        ModBiomeFeatures.addMordorLichen(vegetation);
        ModBiomeFeatures.addAshenDirtOre(vegetation);
        ModBiomeFeatures.addDirtToGrassOre(vegetation);
        ModBiomeFeatures.addShriveledShrubs(vegetation);
        ModBiomeFeatures.addAshenDirtStoneOre(vegetation);
        ModBiomeFeatures.addBasaltPile(vegetation);
        ModBiomeFeatures.addBlackStonePile(vegetation);
        ModBiomeFeatures.addPumicePileRare(vegetation);
        ModBiomeFeatures.addDeadBlackPineTrees(vegetation);
        ModBiomeFeatures.addDryPineTrees(vegetation);
        ModBiomeFeatures.addDryPineBushes(vegetation);

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings, 0.7f, false);
    }

    public static Biome createTrollshawsBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addFarmAnimals(spawnSettings);
        ModSpawnSettingsBuilder.addDeer(spawnSettings);
        ModSpawnSettingsBuilder.addWolves(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addEriadorVegetation(generationSettings);
        vegetation.add(VegetationPlacedFeatures.PATCH_LARGE_FERN);
        vegetation.add(VegetationPlacedFeatures.PATCH_GRASS_TAIGA);
        ModBiomeFeatures.addBracken(vegetation);
        ModBiomeFeatures.addDeadRushes(vegetation);
        ModBiomeFeatures.addFalseOatgrass(vegetation);
        ModBiomeFeatures.addDyingGrass(vegetation);
        ModBiomeFeatures.addForestMoss(vegetation);
        ModBiomeFeatures.addDryDirtOre(vegetation);
        ModBiomeFeatures.addCoarseDirtOre(vegetation);
        ModBiomeFeatures.addGravelOre(vegetation);
        ModBiomeFeatures.addPodzolOre(vegetation);
        ModBiomeFeatures.addRareMorsel(vegetation);

        ModBiomeFeatures.addCommonOakBush(vegetation);
        ModBiomeFeatures.addCommonBeechTrees(vegetation);
        ModBiomeFeatures.addCommonOakTrees(vegetation);
        ModBiomeFeatures.addRareMegaOakTrees(vegetation);
        ModBiomeFeatures.addPineTrees(vegetation);
        ModBiomeFeatures.addSpruceTrees(vegetation);

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings);
    }

    public static Biome createUmbarBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addHaradMobs(spawnSettings);
        ModSpawnSettingsBuilder.addRareWolves(spawnSettings);
        ModSpawnSettingsBuilder.addFarmAnimals(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addHaradVegetation(generationSettings);
        ModBiomeFeatures.addGraniteBoulder(vegetation);
        ModBiomeFeatures.addWildFlax(vegetation);

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings);
    }

    public static Biome createRiverBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addRiverAnimals(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        ModBiomeFeatures.addRiverDisks(undergroundOres);
        vegetation.add(OceanPlacedFeatures.KELP_COLD);
        vegetation.add(OceanPlacedFeatures.SEAGRASS_NORMAL);
        ModBiomeFeatures.addGrass(vegetation);
        vegetation.add(VegetationPlacedFeatures.FLOWER_DEFAULT);
        vegetation.add(VegetationPlacedFeatures.PATCH_SUGAR_CANE);
        vegetation.add(VegetationPlacedFeatures.PATCH_PUMPKIN);
        ModBiomeFeatures.addReedsFoliage(vegetation);

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings);
    }

    public static Biome createLakeBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addRiverAnimals(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addOceanVegetation(generationSettings);

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings);
    }

    public static Biome createOasisBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addRiverAnimals(spawnSettings);
        ModSpawnSettingsBuilder.addCamel(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addOasisVegetation(generationSettings);

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings);
    }

    public static Biome createWastePondBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addRiverAnimals(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        ModBiomeFeatures.addDisks(undergroundOres);
        vegetation.add(OceanPlacedFeatures.KELP_WARM);
        vegetation.add(OceanPlacedFeatures.SEAGRASS_NORMAL);
        ModBiomeFeatures.addGrass(vegetation);
        vegetation.add(VegetationPlacedFeatures.PATCH_SUGAR_CANE);
        ModBiomeFeatures.addReedsFoliage(vegetation);

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings);
    }

    public static Biome createWitheredHeathBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        vegetation.add(VegetationPlacedFeatures.PATCH_GRASS_TAIGA);
        vegetation.add(VegetationPlacedFeatures.BROWN_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.RED_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.PATCH_BERRY_RARE);
        ModBiomeFeatures.addCoarseDirtOre(vegetation);
        ModBiomeFeatures.addGravelOre(vegetation);
        ModBiomeFeatures.addBasaltBoulder(vegetation);

        ModBiomeFeatures.addDeadBlackPineTrees(vegetation);
        ModBiomeFeatures.addDryPineTrees(vegetation);
        ModBiomeFeatures.addDryPineBushes(vegetation);
        ModBiomeFeatures.addToughBerries(vegetation);

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings, 0.4f, true);
    }

    public static Biome createWhiteMountainsBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addMountainsMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        float temperature = 0.5f;
        addNordicVegetation(generationSettings);
        ModBiomeFeatures.addLarchTrees(vegetation);
        ModBiomeFeatures.addPineTrees(vegetation);
        ModBiomeFeatures.addScarceSpruceTrees(vegetation);
        ModBiomeFeatures.addSpruceBushes(vegetation);

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings, temperature, true);
    }

    public static Biome createWhiteMountainsFaces(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, boolean cold) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addMountainVegetation(generationSettings);
        float temperature = (cold) ? 0.5f : 0.5f;

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings, temperature, true);
    }

    public static Biome createWoodlandRealmBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addWolves(spawnSettings);
        ModSpawnSettingsBuilder.addDeer(spawnSettings);

        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addMirkwoodVegetation(generationSettings);
        ModBiomeFeatures.addBracken(vegetation);
        ModBiomeFeatures.addForestMoss(vegetation);
        ModBiomeFeatures.addFlowerGreenJewel(vegetation);
        ModBiomeFeatures.addGravelOre(vegetation);
        ModBiomeFeatures.addLimestoneBoulder(vegetation);
        vegetation.add(VegetationPlacedFeatures.FLOWER_FOREST_FLOWERS);
        ModBiomeFeatures.addRareMorsel(vegetation);
        addMirkwoodTrees(generationSettings);

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings);
    }

    public static Biome createOceanBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addOceanAnimals(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addOceanVegetation(generationSettings);

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings);
    }

    public static Biome createOceanCoastBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addOceanAnimals(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addOceanVegetation(generationSettings);
        ModBiomeFeatures.addCoastalFoliage(vegetation);

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings);
    }

    public static Biome createPelennorFields(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addPlainsMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addGondorVegetation(generationSettings);
        ModBiomeFeatures.addDioriteBoulder(vegetation);
        ModBiomeFeatures.addRareWilderGrass(vegetation);
        ModBiomeFeatures.addTuftGrass(vegetation);

        vegetation.add(VegetationPlacedFeatures.TREES_PLAINS);
        ModBiomeFeatures.addRareLebethronTrees(vegetation);

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings);
    }

    public static Biome createPondBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addRiverAnimals(spawnSettings);
        ModSpawnSettingsBuilder.addSwan(spawnSettings);
        ModSpawnSettingsBuilder.addSwampMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addPondVegetation(generationSettings);
        ModBiomeFeatures.addWaterDelta(undergroundOres);
        ModBiomeFeatures.addRiverSand(undergroundOres);
        ModBiomeFeatures.addAbundantMudOre(undergroundOres);
        ModBiomeFeatures.addBlueOrchidFlower(undergroundOres);
        ModBiomeFeatures.addCommonTallGrass(undergroundOres);
        ModBiomeFeatures.addBulrushAndCattail(undergroundOres);
        ModBiomeFeatures.addDuckweed(undergroundOres);
        ModBiomeFeatures.addLilyPads(undergroundOres);
        ModBiomeFeatures.addSmallLilyPads(undergroundOres);
        ModBiomeFeatures.addSmallFloweringLilyPads(undergroundOres);
        ModBiomeFeatures.addWheatGrass(undergroundOres);
        vegetation.add(VegetationPlacedFeatures.TREES_PLAINS);
        ModBiomeFeatures.addCommonWillowTrees(vegetation);

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings);
    }

    public static Biome createFrozenPond(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addColdWaterAnimals(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));
        
        ModBiomeFeatures.addDisks(undergroundOres);

        vegetation.add(OceanPlacedFeatures.KELP_COLD);
        vegetation.add(OceanPlacedFeatures.SEAGRASS_NORMAL);
        ModBiomeFeatures.addGrass(vegetation);

        return createBiome(getBiomeColors(biomeRegistryKey), spawnSettings, generationSettings, -0.1f, true);
    }

    public static void addNordicTrees(GenerationSettings.LookupBackedBuilder generationSettings) {
        ModBiomeFeatures.addLarchTrees(vegetation);
        ModBiomeFeatures.addSparsePineTrees(vegetation);
        ModBiomeFeatures.addRareSpruceTrees(vegetation);
    }

    public static void addMirkwoodTrees(GenerationSettings.LookupBackedBuilder generationSettings) {
        ModBiomeFeatures.addMirkwoodTrees(vegetation);
    }

    public static void addMegaMirkwoodTrees(GenerationSettings.LookupBackedBuilder generationSettings) {
        ModBiomeFeatures.addMegaMirkwoodTrees(vegetation);
    }

    public static void addDefaultVegetation(GenerationSettings.LookupBackedBuilder generationSettings) {
        ModBiomeFeatures.addDisks(undergroundOres);
        ModBiomeFeatures.addGrass(vegetation);
        vegetation.add(VegetationPlacedFeatures.FLOWER_DEFAULT);
        vegetation.add(VegetationPlacedFeatures.BROWN_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.RED_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.PATCH_SUGAR_CANE);
        vegetation.add(VegetationPlacedFeatures.PATCH_PUMPKIN);
        ModBiomeFeatures.addStoneBoulder(vegetation);
        ModBiomeFeatures.addWildBeetroot(vegetation);
        ModBiomeFeatures.addWildCucumber(vegetation);
        ModBiomeFeatures.addWildFlax(vegetation);
        ModBiomeFeatures.addWildGarlic(vegetation);
    }

    public static void addArthedainVegetation(GenerationSettings.LookupBackedBuilder generationSettings) {
        ModBiomeFeatures.addDisks(undergroundOres);
        ModBiomeFeatures.addGrass(vegetation);
        vegetation.add(VegetationPlacedFeatures.PATCH_TALL_GRASS_2);
        vegetation.add(VegetationPlacedFeatures.FLOWER_DEFAULT);
        vegetation.add(VegetationPlacedFeatures.BROWN_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.RED_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.PATCH_SUGAR_CANE);
        vegetation.add(VegetationPlacedFeatures.PATCH_PUMPKIN);
        ModBiomeFeatures.addHeather(vegetation);
        ModBiomeFeatures.addHeatherBush(vegetation);
        ModBiomeFeatures.addCommonWheatGrass(vegetation);
        ModBiomeFeatures.addRareWilderGrass(vegetation);
        ModBiomeFeatures.addWildGarlic(vegetation);
        ModBiomeFeatures.addWildLeek(vegetation);
        ModBiomeFeatures.addWildLettuce(vegetation);
        ModBiomeFeatures.addWildPotato(vegetation);
        ModBiomeFeatures.addStoneBoulder(vegetation);
        ModBiomeFeatures.addDryDirtOre(vegetation);
        ModBiomeFeatures.addGravelOre(vegetation);
    }

    public static void addEriadorVegetation(GenerationSettings.LookupBackedBuilder generationSettings) {
        ModBiomeFeatures.addDisks(undergroundOres);
        ModBiomeFeatures.addGrass(vegetation);
        vegetation.add(VegetationPlacedFeatures.FLOWER_DEFAULT);
        vegetation.add(VegetationPlacedFeatures.BROWN_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.RED_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.PATCH_SUGAR_CANE);
        vegetation.add(VegetationPlacedFeatures.PATCH_PUMPKIN);
        vegetation.add(VegetationPlacedFeatures.TREES_PLAINS);
        ModBiomeFeatures.addFlowerGreenJewel(vegetation);
        ModBiomeFeatures.addWildFlax(vegetation);
        ModBiomeFeatures.addWildGrass(vegetation);
        ModBiomeFeatures.addWildLeek(vegetation);
        ModBiomeFeatures.addWildLettuce(vegetation);
        ModBiomeFeatures.addWildOnion(vegetation);
        ModBiomeFeatures.addGraniteBoulder(vegetation);
    }

    public static void addForodwaithVegetation(GenerationSettings.LookupBackedBuilder generationSettings) {
        ModBiomeFeatures.addDisks(undergroundOres);
        ModBiomeFeatures.addToughBerriesRare(vegetation);
    }

    public static void addGondorVegetation(GenerationSettings.LookupBackedBuilder generationSettings) {
        ModBiomeFeatures.addDisks(undergroundOres);
        ModBiomeFeatures.addGrass(vegetation);
        vegetation.add(VegetationPlacedFeatures.FLOWER_DEFAULT);
        vegetation.add(VegetationPlacedFeatures.BROWN_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.RED_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.PATCH_SUGAR_CANE);
        vegetation.add(VegetationPlacedFeatures.PATCH_PUMPKIN);
        ModBiomeFeatures.addWildCarrot(vegetation);
        ModBiomeFeatures.addWildOnion(vegetation);
        ModBiomeFeatures.addWildGarlic(vegetation);
        ModBiomeFeatures.addWildFlax(vegetation);
    }

    public static void addHaradVegetation(GenerationSettings.LookupBackedBuilder generationSettings) {
        ModBiomeFeatures.addDisks(undergroundOres);
        ModBiomeFeatures.addGrass(vegetation);
        vegetation.add(VegetationPlacedFeatures.PATCH_TALL_GRASS);
        vegetation.add(VegetationPlacedFeatures.PATCH_SUGAR_CANE);
        ModBiomeFeatures.addHaradFoliage(vegetation);
        ModBiomeFeatures.addDryDirtOre(vegetation);
        ModBiomeFeatures.addWildBellPepper(vegetation);
        ModBiomeFeatures.addWildTomato(vegetation);
    }

    public static void addHaradDesertVegetation(GenerationSettings.LookupBackedBuilder generationSettings) {
        ModBiomeFeatures.addDisks(undergroundOres);
        vegetation.add(VegetationPlacedFeatures.PATCH_SUGAR_CANE);
        ModBiomeFeatures.addGrass(vegetation);
        ModBiomeFeatures.addDryGrass(vegetation);
        ModBiomeFeatures.addSandStoneBoulder(vegetation);
    }

    public static void addLothlorienVegetation(GenerationSettings.LookupBackedBuilder generationSettings) {
        ModBiomeFeatures.addDisks(undergroundOres);
        ModBiomeFeatures.addGrass(vegetation);
        vegetation.add(VegetationPlacedFeatures.FLOWER_MEADOW);
        vegetation.add(VegetationPlacedFeatures.BROWN_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.RED_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.PATCH_SUGAR_CANE);
        vegetation.add(VegetationPlacedFeatures.PATCH_PUMPKIN);
        vegetation.add(VegetationPlacedFeatures.TREES_PLAINS);
        ModBiomeFeatures.addGravelOre(vegetation);
        ModBiomeFeatures.addDryDirtOre(vegetation);
        ModBiomeFeatures.addLimestoneBoulder(vegetation);
        ModBiomeFeatures.addTuftGrass(vegetation);
        ModBiomeFeatures.addRareWilderGrass(vegetation);
        ModBiomeFeatures.addMallornBushes(vegetation);
        ModBiomeFeatures.addOakBushes(vegetation);
        ModBiomeFeatures.addElanor(vegetation);
        ModBiomeFeatures.addYellowFlowerGrowth(vegetation);
        ModBiomeFeatures.addWildCarrot(vegetation);
        ModBiomeFeatures.addWildFlax(vegetation);
        ModBiomeFeatures.addWildLettuce(vegetation);
        ModBiomeFeatures.addWildOnion(vegetation);
    }

    public static void addMirkwoodVegetation(GenerationSettings.LookupBackedBuilder generationSettings) {
        ModBiomeFeatures.addDisks(undergroundOres);
        ModBiomeFeatures.addGrass(vegetation);
        vegetation.add(VegetationPlacedFeatures.PATCH_TALL_GRASS_2);
        vegetation.add(VegetationPlacedFeatures.PATCH_LARGE_FERN);
        vegetation.add(VegetationPlacedFeatures.PATCH_GRASS_TAIGA);
        vegetation.add(VegetationPlacedFeatures.BROWN_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.RED_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.PATCH_BERRY_RARE);
        ModBiomeFeatures.addCoarseDirtOre(vegetation);
        ModBiomeFeatures.addFalseOatgrass(vegetation);
        ModBiomeFeatures.addOldPodzolOre(vegetation);
        ModBiomeFeatures.addWildGrass(vegetation);
        ModBiomeFeatures.addOakBushes(vegetation);
        ModBiomeFeatures.addWildFlax(vegetation);
        ModBiomeFeatures.addWildGarlic(vegetation);
        ModBiomeFeatures.addWildOnion(vegetation);
        ModBiomeFeatures.addMossyBoulder(vegetation);
    }

    public static void addMordorVegetation(GenerationSettings.LookupBackedBuilder generationSettings) {
        ModBiomeFeatures.addAshBlockOre(vegetation);
        ModBiomeFeatures.addCommonToughBerries(vegetation);
    }

    public static void addNordicVegetation(GenerationSettings.LookupBackedBuilder generationSettings) {
        ModBiomeFeatures.addDisks(undergroundOres);
        vegetation.add(VegetationPlacedFeatures.PATCH_LARGE_FERN);
        vegetation.add(VegetationPlacedFeatures.PATCH_GRASS_TAIGA);
        vegetation.add(VegetationPlacedFeatures.FLOWER_DEFAULT);
        vegetation.add(VegetationPlacedFeatures.BROWN_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.RED_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.PATCH_BERRY_RARE);
        ModBiomeFeatures.addBracken(vegetation);
        ModBiomeFeatures.addWildGrass(vegetation);
        ModBiomeFeatures.addBrownBolete(vegetation);
        ModBiomeFeatures.addMorsel(vegetation);
        ModBiomeFeatures.addWhiteMushroom(vegetation);
        ModBiomeFeatures.addWildBeetroot(vegetation);
        ModBiomeFeatures.addWildPotato(vegetation);
    }

    public static void addMountainVegetation(GenerationSettings.LookupBackedBuilder generationSettings) {
        vegetation.add(VegetationPlacedFeatures.PATCH_LARGE_FERN);
        vegetation.add(VegetationPlacedFeatures.PATCH_GRASS_TAIGA);
        vegetation.add(VegetationPlacedFeatures.FLOWER_DEFAULT);
        vegetation.add(VegetationPlacedFeatures.BROWN_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.RED_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.PATCH_BERRY_RARE);
        ModBiomeFeatures.addWildGrass(vegetation);
        ModBiomeFeatures.addBrownBolete(vegetation);
        ModBiomeFeatures.addMorsel(vegetation);
        ModBiomeFeatures.addWhiteMushroom(vegetation);
        ModBiomeFeatures.addWildBeetroot(vegetation);
        ModBiomeFeatures.addWildPotato(vegetation);
    }

    public static void addOasisVegetation(GenerationSettings.LookupBackedBuilder generationSettings) {
        ModBiomeFeatures.addDisks(undergroundOres);
        vegetation.add(OceanPlacedFeatures.KELP_WARM);
        vegetation.add(OceanPlacedFeatures.SEAGRASS_WARM);
        ModBiomeFeatures.addGrass(vegetation);
        vegetation.add(VegetationPlacedFeatures.FLOWER_DEFAULT);
        vegetation.add(VegetationPlacedFeatures.PATCH_SUGAR_CANE);
        vegetation.add(VegetationPlacedFeatures.PATCH_PUMPKIN);
        ModBiomeFeatures.addHaradFoliage(vegetation);
        ModBiomeFeatures.addWildBellPepper(vegetation);
        ModBiomeFeatures.addWildTomato(vegetation);
    }

    public static void addOceanVegetation(GenerationSettings.LookupBackedBuilder generationSettings) {
        ModBiomeFeatures.addDisks(undergroundOres);
        vegetation.add(OceanPlacedFeatures.KELP_COLD);
        vegetation.add(OceanPlacedFeatures.SEAGRASS_NORMAL);
        ModBiomeFeatures.addGrass(vegetation);
        vegetation.add(VegetationPlacedFeatures.FLOWER_DEFAULT);
        vegetation.add(VegetationPlacedFeatures.PATCH_SUGAR_CANE);
        vegetation.add(VegetationPlacedFeatures.PATCH_PUMPKIN);
        ModBiomeFeatures.addReedsFoliage(vegetation);
    }

    public static void addPondVegetation(GenerationSettings.LookupBackedBuilder generationSettings) {
        ModBiomeFeatures.addDisks(undergroundOres);
        ModBiomeFeatures.addGrass(vegetation);
        vegetation.add(VegetationPlacedFeatures.FLOWER_DEFAULT);
        vegetation.add(VegetationPlacedFeatures.PATCH_SUGAR_CANE);
        vegetation.add(VegetationPlacedFeatures.PATCH_PUMPKIN);
        ModBiomeFeatures.addReedsFoliage(vegetation);
    }

    public static void addRivendellVegetation(GenerationSettings.LookupBackedBuilder generationSettings) {
        ModBiomeFeatures.addDisks(undergroundOres);
        ModBiomeFeatures.addGrass(vegetation);
        vegetation.add(VegetationPlacedFeatures.BROWN_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.RED_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.PATCH_SUGAR_CANE);
        vegetation.add(VegetationPlacedFeatures.PATCH_PUMPKIN);
        ModBiomeFeatures.addFlowerMeadow(vegetation);
        ModBiomeFeatures.addRareWilderGrass(vegetation);
        ModBiomeFeatures.addGravelOre(vegetation);
        ModBiomeFeatures.addWhiteSand(vegetation);

        ModBiomeFeatures.addWildLeek(vegetation);
        ModBiomeFeatures.addWildLettuce(vegetation);
        ModBiomeFeatures.addWildOnion(vegetation);
        ModBiomeFeatures.addLimestoneBoulder(vegetation);
    }

    public static void addRhunVegetation(GenerationSettings.LookupBackedBuilder generationSettings) {
        ModBiomeFeatures.addDisks(undergroundOres);
        ModBiomeFeatures.addGrass(vegetation);
        vegetation.add(VegetationPlacedFeatures.TREES_PLAINS);
        vegetation.add(VegetationPlacedFeatures.FLOWER_DEFAULT);
        vegetation.add(VegetationPlacedFeatures.BROWN_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.RED_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.PATCH_SUGAR_CANE);
        vegetation.add(VegetationPlacedFeatures.PATCH_PUMPKIN);
        ModBiomeFeatures.addWildCarrot(vegetation);
        ModBiomeFeatures.addWildBellPepper(vegetation);
        ModBiomeFeatures.addWildFlax(vegetation);
        ModBiomeFeatures.addWildLettuce(vegetation);
        ModBiomeFeatures.addWildOnion(vegetation);
        ModBiomeFeatures.addStoneBoulder(vegetation);
    }

    public static void addShireVegetation(GenerationSettings.LookupBackedBuilder generationSettings) {
        ModBiomeFeatures.addDisks(undergroundOres);
        ModBiomeFeatures.addGrass(vegetation);
        ModBiomeFeatures.addWheatGrass(vegetation);
        ModBiomeFeatures.addTuftGrass(vegetation);
        vegetation.add(VegetationPlacedFeatures.FLOWER_DEFAULT);
        vegetation.add(VegetationPlacedFeatures.BROWN_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.RED_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.PATCH_SUGAR_CANE);
        vegetation.add(VegetationPlacedFeatures.PATCH_PUMPKIN);
        ModBiomeFeatures.addWilderGrass(vegetation);
        ModBiomeFeatures.addGravelOre(vegetation);
        ModBiomeFeatures.addWildBeetroot(vegetation);
        ModBiomeFeatures.addWildCarrot(vegetation);
        ModBiomeFeatures.addWildCucumber(vegetation);
        ModBiomeFeatures.addWildGarlic(vegetation);
        ModBiomeFeatures.addWildLeek(vegetation);
        ModBiomeFeatures.addWildLettuce(vegetation);
        ModBiomeFeatures.addWildOnion(vegetation);
        ModBiomeFeatures.addWildPipeweed(vegetation);
        ModBiomeFeatures.addWildPotato(vegetation);
        ModBiomeFeatures.addStoneBoulder(vegetation);
    }

    public static Biome createBiome(BiomeColorsDTO biomeColors, SpawnSettings.Builder spawnSettings, GenerationSettings.LookupBackedBuilder generationSettings) {
        return createBiome(biomeColors, spawnSettings, generationSettings, 0.5f, true);
    }

    public static Biome createBiome(BiomeColorsDTO biomeColors, SpawnSettings.Builder spawnSettings, GenerationSettings.LookupBackedBuilder generationSettings, float temperature, boolean precipitation) {
        undergroundOres.add(OrePlacedFeatures.ORE_DIRT);
        undergroundOres.add(OrePlacedFeatures.ORE_GRAVEL);
        undergroundOres.add(OrePlacedFeatures.ORE_GRANITE_UPPER);
        undergroundOres.add(OrePlacedFeatures.ORE_GRANITE_LOWER);
        undergroundOres.add(OrePlacedFeatures.ORE_DIORITE_UPPER);
        undergroundOres.add(OrePlacedFeatures.ORE_DIORITE_LOWER);
        undergroundOres.add(OrePlacedFeatures.ORE_ANDESITE_UPPER);
        undergroundOres.add(OrePlacedFeatures.ORE_ANDESITE_LOWER);
        undergroundOres.add(OrePlacedFeatures.ORE_TUFF);
        undergroundOres.add(OrePlacedFeatures.ORE_COAL_UPPER);
        vegetation.add(UndergroundPlacedFeatures.GLOW_LICHEN);

        DefaultBiomeFeatures.addFrozenTopLayer(generationSettings);

        vegetation = vegetation.stream().sorted(Comparator.comparing(a -> a.getValue().toString())).toList();
        for (RegistryKey<PlacedFeature> feature: vegetation) {
            generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, feature);
        }
        for (RegistryKey<PlacedFeature> feature: undergroundOres.stream().sorted(Comparator.comparing(a -> a.getValue().toString())).toList()) {
            generationSettings.feature(GenerationStep.Feature.UNDERGROUND_ORES, feature);
        }

        Biome biome = (new Biome.Builder())
                .precipitation(precipitation)
                .temperature(temperature)
                .downfall(0.5F)
                .effects((new BiomeEffects.Builder())
                        .skyColor(biomeColors.skyColor)
                        .fogColor(biomeColors.fogColor)
                        .waterColor(biomeColors.waterColor)
                        .waterFogColor(biomeColors.waterFogColor)
                        .grassColor(biomeColors.grassColor)
                        .foliageColor(biomeColors.foliageColor)
                        .build())
                .spawnSettings(spawnSettings.build())
                .generationSettings(generationSettings.build())
                .build();
        vegetation = new ArrayList<>();
        undergroundOres = new ArrayList<>();
        return biome;
    }
}
