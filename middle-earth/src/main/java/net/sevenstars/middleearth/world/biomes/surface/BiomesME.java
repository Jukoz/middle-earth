package net.sevenstars.middleearth.world.biomes.surface;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.*;
import net.sevenstars.middleearth.particles.ParticleTypeRegistryME;
import net.sevenstars.middleearth.world.biomes.BiomeColorsDTO;
import net.sevenstars.middleearth.world.biomes.BiomeKeyRegistryME;
import net.sevenstars.middleearth.world.features.boulder.BoulderPlacedFeatureRegistryME;
import net.sevenstars.middleearth.world.features.misc.MiscPlacedFeatureRegistryME;
import net.sevenstars.middleearth.world.features.vegetation.VegetationPlacedFeatureRegistryME;
import net.sevenstars.middleearth.world.spawners.SpawnSettingsBuilderME;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BiomesME {
    private static List<RegistryKey<PlacedFeature>> surfaceStructures = new ArrayList<>();;
    private static List<RegistryKey<PlacedFeature>> vegetation = new ArrayList<>();;
    private static ArrayList<RegistryKey<PlacedFeature>> undergroundOres = new ArrayList<>();;
    
    public static void bootstrap(Registerable<Biome> context) {
        createAnduinBiome(context, BiomeKeyRegistryME.ANDUIN_VALES, false);
        createAnduinBiome(context, BiomeKeyRegistryME.ANDUIN_VALES_FOREST, true);
        createAnorienBiome(context, BiomeKeyRegistryME.ANORIEN);
        createGondorRiverSideBiome(context, BiomeKeyRegistryME.ANORIEN_RIVERSIDE);
        createAnorienBiome(context, BiomeKeyRegistryME.ANORIEN_FOOTHILLS);
        createBarrowDownsBiome(context, BiomeKeyRegistryME.BARROW_DOWNS);
        createBeleriandIslandBiome(context, BiomeKeyRegistryME.BELERIAND_ISLAND);
        createBelfalasBiome(context, BiomeKeyRegistryME.BELFALAS, 0);
        createBelfalasShoresBiome(context, BiomeKeyRegistryME.BELFALAS_BEACH);
        createBelfalasBiome(context, BiomeKeyRegistryME.BELFALAS_HILLS, 1);
        createBelfalasBiome(context, BiomeKeyRegistryME.BELFALAS_FOREST, 2);
        createBlackRootVale(context, BiomeKeyRegistryME.BLACKROOT_VALE, false);
        createBlackRootVale(context, BiomeKeyRegistryME.BLACKROOT_FOREST, true);
        createBlueMountainsBiome(context, BiomeKeyRegistryME.BLUE_MOUNTAINS_FOOTHILLS, 0);
        createBlueMountainsBiome(context, BiomeKeyRegistryME.BLUE_MOUNTAINS_BASE, 1);
        createBlueMountainsBiome(context, BiomeKeyRegistryME.BLUE_MOUNTAINS, 2);
        createBlueMountainsBiome(context, BiomeKeyRegistryME.BLUE_MOUNTAINS_HIGH_LANDS, 3);
        createBlueMountainsBiome(context, BiomeKeyRegistryME.BLUE_MOUNTAINS_PEAKS, 4);
        createBlueMountainsBiome(context, BiomeKeyRegistryME.BLUE_MOUNTAINS_WOODS, 5);
        createMordorWastesBiome(context, BiomeKeyRegistryME.BROWN_LANDS);
        createMistyMountainsBiome(context, BiomeKeyRegistryME.CARADHRAS_BASE, 0);
        createMistyMountainsBiome(context, BiomeKeyRegistryME.CARADHRAS, 1);
        createMistyMountainsBiome(context, BiomeKeyRegistryME.CARADHRAS_PEAKS, 2);
        createMistyMountainsBiome(context, BiomeKeyRegistryME.CELEBDIL_BASE, 0);
        createMistyMountainsBiome(context, BiomeKeyRegistryME.CELEBDIL, 1);
        createMistyMountainsBiome(context, BiomeKeyRegistryME.CELEBDIL_PEAKS, 2);
        createCorsairCoastBiome(context, BiomeKeyRegistryME.CORSAIR_COASTS);
        createMordorWastesBiome(context, BiomeKeyRegistryME.DAGORLAD);
        createDaleBiome(context, BiomeKeyRegistryME.DALE, 0);
        createDaleBiome(context, BiomeKeyRegistryME.DALE_FOREST, 1);
        createDaleBiome(context, BiomeKeyRegistryME.DALE_MEADOW, 2);
        createDaleBiome(context, BiomeKeyRegistryME.DALE_CITY, 0);
        createDaleBiome(context, BiomeKeyRegistryME.DALE_RIVERSIDE, 0);
        createAnduinBiome(context, BiomeKeyRegistryME.DARK_ANDUIN_VALES, false);
        createMirkwoodBiome(context, BiomeKeyRegistryME.DARK_MIRKWOOD, true, true, false);
        createMirkwoodBiome(context, BiomeKeyRegistryME.DARK_MIRKWOOD_EDGE, false, true, false);
        createDeadMarshesBiome(context, BiomeKeyRegistryME.DEAD_MARSHES);
        createDeadMarshesWaterBiome(context, BiomeKeyRegistryME.DEAD_MARSHES_WATER);
        createDesolatedLandsBiome(context, BiomeKeyRegistryME.DESOLATED_LANDS);
        createDolGuldurBiome(context, BiomeKeyRegistryME.DOL_GULDUR, 0);
        createDolGuldurBiome(context, BiomeKeyRegistryME.DOL_GULDUR_HILL, 1);
        createDorwinionBiome(context, BiomeKeyRegistryME.DORWINION, 0);
        createDorwinionBiome(context, BiomeKeyRegistryME.DORWINION_LAVENDER_FIELD, 1);
        createDorwinionHillsBiome(context, BiomeKeyRegistryME.DORWINION_HILLS);
        createDunlandFoothillsBiome(context, BiomeKeyRegistryME.DUNLAND_FOOTHILLS);
        createDunlandFoothillsBiome(context, BiomeKeyRegistryME.DUNLAND_HILLS);
        createEasternRhovanionBiome(context, BiomeKeyRegistryME.EAST_BIGHT, 0);
        createNurnBiome(context, BiomeKeyRegistryME.EASTERN_NURN, 0);
        createEasternRhovanionBiome(context, BiomeKeyRegistryME.EASTERN_RHOVANION, 0);
        createEasternRhovanionBiome(context, BiomeKeyRegistryME.EASTERN_RHOVANION_FOREST, 1);
        createEmynMuilBiome(context, BiomeKeyRegistryME.EMYN_MUIL);
        createEmynMuilBiome(context, BiomeKeyRegistryME.EMYN_MUIL_CLIFFS);
        createEmynMuilBiome(context, BiomeKeyRegistryME.EMYN_MUIL_PEAKS);
        createWastePondBiome(context, BiomeKeyRegistryME.EMYN_MUIL_POND);
        createEnedwaithBiome(context, BiomeKeyRegistryME.ENEDWAITH, 0);
        createEnedwaithBiome(context, BiomeKeyRegistryME.ENEDWAITH_FIELD, 1);
        createEnedwaithBiome(context, BiomeKeyRegistryME.ENEDWAITH_WHEAT_FIELD, 2);
        createMordorMountainsBiome(context, BiomeKeyRegistryME.EPHEL_DUATH_BASE, 0);
        createMordorMountainsBiome(context, BiomeKeyRegistryME.EPHEL_DUATH, 1);
        createMordorMountainsBiome(context, BiomeKeyRegistryME.EPHEL_DUATH_PEAKS, 2);
        createMordorMountainsBiome(context, BiomeKeyRegistryME.ERED_LITHUI_BASE, 0);
        createMordorMountainsBiome(context, BiomeKeyRegistryME.ERED_LITHUI, 1);
        createMordorMountainsBiome(context, BiomeKeyRegistryME.ERED_LITHUI_PEAKS, 2);
        createEregionBiome(context, BiomeKeyRegistryME.EREGION, 0);
        createEregionBiome(context, BiomeKeyRegistryME.EREGION_FOREST, 1);
        createEregionBiome(context, BiomeKeyRegistryME.EREGION_GLADE, 2);
        createEthirAnduin(context, BiomeKeyRegistryME.ETHIR_ANDUIN);
        createRiverBiome(context, BiomeKeyRegistryME.ETHIR_ANDUIN_RIVER_DELTA);
        createFangornBiome(context, BiomeKeyRegistryME.FANGORN);
        createMistyMountainsBiome(context, BiomeKeyRegistryME.FANGORN_FOOTHILLS, 0);
        createMistyMountainsBiome(context, BiomeKeyRegistryME.FANUIDHOL_BASE, 0);
        createMistyMountainsBiome(context, BiomeKeyRegistryME.FANUIDHOL, 1);
        createMistyMountainsBiome(context, BiomeKeyRegistryME.FANUIDHOL_PEAKS, 2);
        createForodwaithBiome(context, BiomeKeyRegistryME.FORODWAITH);
        createFrozenOceanBiome(context, BiomeKeyRegistryME.FROZEN_OCEAN);
        createFrozenPond(context, BiomeKeyRegistryME.FROZEN_POND);
        createGondorBiome(context, BiomeKeyRegistryME.GONDOR, 0);
        createGondorBiome(context, BiomeKeyRegistryME.GONDOR_FOREST, 1);
        createGondorBiome(context, BiomeKeyRegistryME.GONDOR_HILL, 2);
        createGorgorothBiome(context, BiomeKeyRegistryME.GORGOROTH, 0);
        createGorgorothBiome(context, BiomeKeyRegistryME.GORGOROTH_ASHEN_WOODS, 1);
        createGorgorothBiome(context, BiomeKeyRegistryME.GORGOROTH_DELTA, 2);
        createGreyMountainsBiome(context, BiomeKeyRegistryME.GREY_MOUNTAINS_BASE, 0);
        createGreyMountainsBiome(context, BiomeKeyRegistryME.GREY_MOUNTAINS, 1);
        createGreyMountainsBiome(context, BiomeKeyRegistryME.GREY_MOUNTAINS_PEAKS, 2);
        createGreyPlainsBiome(context, BiomeKeyRegistryME.GREY_PLAINS, 0);
        createGreyPlainsBiome(context, BiomeKeyRegistryME.GREY_ASHEN_WOODS, 1);
        createGreyPlainsBiome(context, BiomeKeyRegistryME.GREY_FOREST, 2);
        createGundabadPlains(context, BiomeKeyRegistryME.GUNDABAD_PLAINS, 0);
        createGundabadPlains(context, BiomeKeyRegistryME.GUNDABAD_WOODS, 1);
        createHaradBiome(context, BiomeKeyRegistryME.HARAD, 0);
        createHaradBiome(context, BiomeKeyRegistryME.HARAD_WOODS, 1);
        createHaradDesertBiome(context, BiomeKeyRegistryME.HARAD_DESERT);
        createHarondorBiome(context, BiomeKeyRegistryME.HARONDOR);
        createHillsOfEvendim(context, BiomeKeyRegistryME.HILLS_OF_EVENDIM);
        createIronHillsBiome(context, BiomeKeyRegistryME.IRON_HILLS, false);
        createIronHillsBiome(context, BiomeKeyRegistryME.IRON_HILLS_BASE, true);
        createIronHillsBiome(context, BiomeKeyRegistryME.IRON_HILLS_PEAKS, true);
        createRhovanionTaigaBiome(context, BiomeKeyRegistryME.IRON_HILLS_PLAINS, 0);
        createRhovanionTaigaBiome(context, BiomeKeyRegistryME.NORTHERN_RHOVANION_FOREST, 1);
        createRhovanionTaigaBiome(context, BiomeKeyRegistryME.NORTHERN_RHOVANION_HILLS, 2);
        createRhovanionTaigaBiome(context, BiomeKeyRegistryME.IRON_FOOTHILLS, 3);
        createIsengardBiome(context, BiomeKeyRegistryME.ISENGARD, true);
        createIsengardBiome(context, BiomeKeyRegistryME.ISENGARD_HILL, false);
        createIthilienBiome(context, BiomeKeyRegistryME.ITHILIEN, false, false);
        createIthilienBiome(context, BiomeKeyRegistryME.ITHILIEN_GLADE, false, true);
        createIthilienBiome(context, BiomeKeyRegistryME.ITHILIEN_WASTES, true, false);
        createIthilienBiome(context, BiomeKeyRegistryME.ITHILIEN_WASTES_GLADE, true, true);
        createLamedonBiome(context, BiomeKeyRegistryME.LAMEDON, 0);
        createLamedonBiome(context, BiomeKeyRegistryME.LAMEDON_HILLS, 1);
        createLebennin(context, BiomeKeyRegistryME.LEBENNIN, 0);
        createLebennin(context, BiomeKeyRegistryME.LEBENNIN_HILLS, 1);
        createGondorRiverSideBiome(context, BiomeKeyRegistryME.LEBENNIN_SHORES);
        createLindonBiome(context, BiomeKeyRegistryME.LINDON, 0);
        createLindonBiome(context, BiomeKeyRegistryME.LINDON_SHORES_CLIFFS, 1);
        createLindonBiome(context, BiomeKeyRegistryME.LINDON_SHORES, 2);
        createLindonBiome(context, BiomeKeyRegistryME.LINDON_FOREST, 3);
        createLindonBiome(context, BiomeKeyRegistryME.LINDON_HIDDEN_BLOSSOM, 4);
        createLindonBiome(context, BiomeKeyRegistryME.LINDON_MEADOW, 5);
        createLonelyMountainBiome(context, BiomeKeyRegistryME.LONELY_MOUNTAIN_FOOTHILLS, 0);
        createLonelyMountainBiome(context, BiomeKeyRegistryME.LONELY_MOUNTAIN, 0);
        createLonelyMountainBiome(context, BiomeKeyRegistryME.LONELY_MOUNTAIN_BASE, 1);
        createLonelyMountainBiome(context, BiomeKeyRegistryME.LONELY_MOUNTAIN_PEAKS, 2);
        createLonelyMountainBiome(context, BiomeKeyRegistryME.LONELY_MOUNTAIN_TAIGA, 3);
        createLakeBiome(context, BiomeKeyRegistryME.LONG_LAKE);
        createLakeBiome(context, BiomeKeyRegistryME.LONG_LAKE_SHORES);
        createMirkwoodSwampBiome(context, BiomeKeyRegistryME.LONG_MARSHES, 3);
        createLossarnach(context, BiomeKeyRegistryME.LOSSARNACH, 0);
        createLossarnachCherryBlossom(context, BiomeKeyRegistryME.LOSSARNACH_CHERRY_BLOSSOM);
        createLossarnach(context, BiomeKeyRegistryME.LOSSARNACH_VALLEY, 1);
        createLossarnach(context, BiomeKeyRegistryME.LOSSARNACH_VALLEY_GREEN, 2);
        createLossarnach(context, BiomeKeyRegistryME.LOSSARNACH_VALLEY_YELLOW, 3);
        createLossarnach(context, BiomeKeyRegistryME.LOSSARNACH_VALLEY_ORANGE, 4);
        createLossarnach(context, BiomeKeyRegistryME.LOSSARNACH_VALLEY_RED, 5);
        createLorienEdgeBiome(context, BiomeKeyRegistryME.LORIEN_EDGE);
        createLothlorienBiome(context, BiomeKeyRegistryME.LOTHLORIEN, 0);
        createLothlorienBiome(context, BiomeKeyRegistryME.LOTHLORIEN_GLADE, 1);
        createLothlorienBiome(context, BiomeKeyRegistryME.LOTHLORIEN_BLOSSOM, 2);
        createMinhiriathBiome(context, BiomeKeyRegistryME.MINHIRIATH, 0);
        createMinhiriathBiome(context, BiomeKeyRegistryME.MINHIRIATH_WHEAT_FIELD, 1);
        createMirkwoodBiome(context, BiomeKeyRegistryME.MIRKWOOD, true, false, false);
        createMirkwoodBiome(context, BiomeKeyRegistryME.MIRKWOOD_EDGE, false, false, false);
        createMirkwoodMountainsBiome(context, BiomeKeyRegistryME.MIRKWOOD_FOOTHILLS, -1);
        createMirkwoodMountainsBiome(context, BiomeKeyRegistryME.MIRKWOOD_MOUNTAINS_BASE, 0);
        createMirkwoodMountainsBiome(context, BiomeKeyRegistryME.MIRKWOOD_MOUNTAINS, 1);
        createMirkwoodMountainsBiome(context, BiomeKeyRegistryME.MIRKWOOD_MOUNTAINS_PEAKS, 2);
        createMirkwoodSwampBiome(context, BiomeKeyRegistryME.MIRKWOOD_SWAMP, 1);
        createMirkwoodSwampBiome(context, BiomeKeyRegistryME.MIRKWOOD_MARSHES, 0);
        createMirkwoodSwampBiome(context, BiomeKeyRegistryME.MIRKWOOD_RIVER, 2);
        createRiverBiome(context, BiomeKeyRegistryME.GREAT_RIVER);
        createMistyMountainsBiome(context, BiomeKeyRegistryME.MISTY_MOUNTAINS_BASE, 0);
        createMistyMountainsBiome(context, BiomeKeyRegistryME.MISTY_MOUNTAINS, 1);
        createMistyMountainsBiome(context, BiomeKeyRegistryME.MISTY_MOUNTAINS_PEAKS, 2);
        createMordorBiome(context, BiomeKeyRegistryME.MORDOR);
        createMordorAshenForestBiome(context, BiomeKeyRegistryME.MORDOR_ASHEN_FOREST);
        createMordorHillBiome(context, BiomeKeyRegistryME.MORDOR_HILL);
        createMordorWastesBiome(context, BiomeKeyRegistryME.MORDOR_WASTES);
        createMorgulVale(context, BiomeKeyRegistryME.MORGUL_VALE);
        createMorgulForest(context, BiomeKeyRegistryME.MORGUL_FOREST);
        createNurnWaterBiome(context, BiomeKeyRegistryME.MORGUL_RIVER);
        createMountGundabadBiomes(context, BiomeKeyRegistryME.MOUNT_GUNDABAD_BASE, 0);
        createMountGundabadBiomes(context, BiomeKeyRegistryME.MOUNT_GUNDABAD, 1);
        createMountGundabadBiomes(context, BiomeKeyRegistryME.MOUNT_GUNDABAD_PEAKS, 2);
        createMordorMountainsBiome(context, BiomeKeyRegistryME.MOUNT_DOOM, 2);
        createMordorMountainsBiome(context, BiomeKeyRegistryME.MOUNT_DOOM_PIT, 2);
        createNanCurunirBiome(context, BiomeKeyRegistryME.NAN_CURUNIR);
        createLakeBiome(context, BiomeKeyRegistryME.NEN_HITHOEL);
        createLakeBiome(context, BiomeKeyRegistryME.NEN_HITHOEL_RAPIDS);
        createNenHithoelBiome(context, BiomeKeyRegistryME.NEN_HITHOEL_FOREST, 0);
        createNenHithoelBiome(context, BiomeKeyRegistryME.NEN_HITHOEL_SHORES, 1);
        createNindalf(context, BiomeKeyRegistryME.NINDALF);
        createNorthDownsBiome(context, BiomeKeyRegistryME.NORTH_DOWNS);
        createDunlandBiome(context, BiomeKeyRegistryME.DUNLAND, 0);
        createDunlandBiome(context, BiomeKeyRegistryME.NORTHERN_DUNLAND_GLADE, 1);
        createMirkwoodSwampBiome(context, BiomeKeyRegistryME.NORTHERN_MIRKWOOD_SWAMP, 1);
        createMirkwoodSwampBiome(context, BiomeKeyRegistryME.NORTHERN_MIRKWOOD_MARSHES, 0);
        createNorthernWastelands(context, BiomeKeyRegistryME.NORTHERN_WASTELANDS);
        createNurnBiome(context, BiomeKeyRegistryME.NURN, 0);
        createNurnBiome(context, BiomeKeyRegistryME.NURN_FOREST, 1);
        createNurnBiome(context, BiomeKeyRegistryME.NURN_HILL, 2);
        createNurnEdgeBiome(context, BiomeKeyRegistryME.NURN_EDGE, 0);
        createNurnEdgeBiome(context, BiomeKeyRegistryME.NURN_EDGE_WOODS, 1);
        createNurnWaterBiome(context, BiomeKeyRegistryME.NURN_RIVER);
        createNurnWaterBiome(context, BiomeKeyRegistryME.NURN_SEA);
        createOasisBiome(context, BiomeKeyRegistryME.OASIS);
        createOceanBiome(context, BiomeKeyRegistryME.OCEAN);
        createOceanCoastBiome(context, BiomeKeyRegistryME.OCEAN_COAST);
        createOldAngmarBiome(context, BiomeKeyRegistryME.OLD_ANGMAR, 1);
        createOldAngmarBiome(context, BiomeKeyRegistryME.OLD_ANGMAR_FOREST, 0);
        createOldAngmarBiome(context, BiomeKeyRegistryME.OLD_ANGMAR_COLD_HILL, 2);
        createOldAngmarBiome(context, BiomeKeyRegistryME.OLD_ANGMAR_FROZEN_HILL, 3);
        createOldArthedainBiome(context, BiomeKeyRegistryME.OLD_ARTHEDAIN, 1);
        createOldArthedainBiome(context, BiomeKeyRegistryME.OLD_ARTHEDAIN_FOREST, 2);
        createOldArthedainBiome(context, BiomeKeyRegistryME.OLD_ARTHEDAIN_MEADOW, 0);
        createOldArthedainBiome(context, BiomeKeyRegistryME.OLD_ARTHEDAIN_FOOTHILL, 3);
        createOldCardolanBiome(context, BiomeKeyRegistryME.OLD_CARDOLAN, 0);
        createOldCardolanBiome(context, BiomeKeyRegistryME.OLD_CARDOLAN_FOREST, 1);
        createOldCardolanBiome(context, BiomeKeyRegistryME.OLD_CARDOLAN_HILL, 2);
        createOldRhudaurBiome(context, BiomeKeyRegistryME.OLD_RHUDAUR, 0);
        createOldRhudaurBiome(context, BiomeKeyRegistryME.OLD_RHUDAUR_FOREST, 1);
        createOldRhudaurBiome(context, BiomeKeyRegistryME.OLD_RHUDAUR_HILL, 2);
        createGondorBiome(context, BiomeKeyRegistryME.OSGILIATH, 0);
        createPelennorFields(context, BiomeKeyRegistryME.PELENNOR_FIELDS, 0);
        createPelennorFields(context, BiomeKeyRegistryME.PELENNOR_WHEAT_FIELD, 1);
        createPondBiome(context, BiomeKeyRegistryME.POND);
        createMangrovePondBiome(context, BiomeKeyRegistryME.MANGROVE_POND);
        createRhunBiome(context, BiomeKeyRegistryME.RHUN, 0);
        createRhunBiome(context, BiomeKeyRegistryME.RHUN_FIELD, 1);
        createRhunBiome(context, BiomeKeyRegistryME.RHUN_FOREST, 2);
        createRhunBiome(context, BiomeKeyRegistryME.RHUN_HIDDEN_BLOSSOM, 3);
        createRivendellBiome(context, BiomeKeyRegistryME.HIGH_MOOR_VALE, 0);
        createRivendellBiome(context, BiomeKeyRegistryME.HIGH_MOOR, 1);
        createRivendellFoothillsBiome(context, BiomeKeyRegistryME.HIGH_MOOR_HILLS);
        createRiverBiome(context, BiomeKeyRegistryME.RIVER);
        createRiverBiome(context, BiomeKeyRegistryME.RIVER_RUNNING);
        createRohanBiome(context, BiomeKeyRegistryME.ROHAN, 0);
        createRohanBiome(context, BiomeKeyRegistryME.ROHAN_FOREST, 1);
        createRohanBiome(context, BiomeKeyRegistryME.ROHAN_FIELD, 2);
        createLakeBiome(context, BiomeKeyRegistryME.SEA_OF_RHUN);
        createSarnGebir(context, BiomeKeyRegistryME.SARN_GEBIR_SHORES, 1);
        createSarnGebir(context, BiomeKeyRegistryME.SARN_GEBIR_WILDLANDS, 0);
        createShireBiome(context, BiomeKeyRegistryME.SHIRE, 0);
        createShireBiome(context, BiomeKeyRegistryME.SHIRE_EDGE, 1);
        createShireBiome(context, BiomeKeyRegistryME.SHIRE_HILLS, 2);
        createShireBiome(context, BiomeKeyRegistryME.SHIRE_WOODS, 3);
        createShireBiome(context, BiomeKeyRegistryME.SHIRE_FOREST, 4);
        createDunlandBiome(context, BiomeKeyRegistryME.DRUWAITH_IAUR, 2);
        createSoutheastRhovanionBiome(context, BiomeKeyRegistryME.SOUTHEAST_RHOVANION, 0);
        createSoutheastRhovanionBiome(context, BiomeKeyRegistryME.SOUTHEAST_RHOVANION_FIELD, 1);
        createSouthernForochelBiome(context, BiomeKeyRegistryME.SOUTHERN_FOROCHEL);
        createTheAngleBiome(context, BiomeKeyRegistryME.THE_ANGLE);
        createTheOldForestBiome(context, BiomeKeyRegistryME.THE_OLD_FOREST);
        createTheWhiteDownsBiome(context, BiomeKeyRegistryME.THE_WHITE_DOWNS);
        createRohanBiome(context, BiomeKeyRegistryME.THE_WOLD, 0);
        createRohanBiome(context, BiomeKeyRegistryME.THE_WOLD_WHEAT_FIELD, 2);
        createTolfalasBiome(context, BiomeKeyRegistryME.TOLFALAS);
        createTorogwaithBiome(context, BiomeKeyRegistryME.TOROGWAITH);
        createTrollshawsBiome(context, BiomeKeyRegistryME.TROLLSHAWS);
        createMordorBiome(context, BiomeKeyRegistryME.UDUN);
        createUmbarBiome(context, BiomeKeyRegistryME.UMBAR, 0);
        createUmbarBiome(context, BiomeKeyRegistryME.UMBAR_WOODS, 1);
        createWastePondBiome(context, BiomeKeyRegistryME.WASTE_POND);
        createWebbedMirkwoodBiome(context, BiomeKeyRegistryME.WEBBED_WOODS, false);
        createWebbedMirkwoodBiome(context, BiomeKeyRegistryME.WEBBED_DARK_WOODS, true);
        createWitheredHeathBiome(context, BiomeKeyRegistryME.WITHERED_HEATH);
        createWhiteMountainsBiome(context, BiomeKeyRegistryME.WHITE_MOUNTAINS_BASE, 0);
        createWhiteMountainsBiome(context, BiomeKeyRegistryME.WHITE_MOUNTAINS, 1);
        createWhiteMountainsBiome(context, BiomeKeyRegistryME.WHITE_MOUNTAINS_PEAKS, 2);
        createWoodlandRealmBiome(context, BiomeKeyRegistryME.WOODLAND_REALM, 0);
        createWoodlandRealmBiome(context, BiomeKeyRegistryME.WOODLAND_GLADE, 1);
        createWoodlandRealmBiome(context, BiomeKeyRegistryME.AUTUMN_WOODLAND, 2);
        createMirkwoodMountainsBiome(context, BiomeKeyRegistryME.WOODLAND_FOOTHILLS, true);
        createMirkwoodMountainsBiome(context, BiomeKeyRegistryME.WOODLAND_HILLS, true);
    }

    public static void createAnduinBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, boolean forest) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        SpawnSettingsBuilderME.addRareWarg(spawnSettings);
        SpawnSettingsBuilderME.addRabbits(spawnSettings);

        addDefaultVegetation(generationSettings);
        BiomeFeaturesME.addCoarseDirtOre(vegetation);
        BiomeFeaturesME.addFlowerGreenJewel(vegetation);
        BiomeFeaturesME.addGravelOre(vegetation);
        BiomeFeaturesME.addOakBushes(vegetation);
        BiomeFeaturesME.addWildGrass(vegetation);
        BiomeFeaturesME.addRareWilderGrass(vegetation);
        BiomeFeaturesME.addBeesOakTrees(vegetation);

        vegetation.add(VegetationPlacedFeatures.PATCH_TALL_GRASS_2);
        if(!forest) {
            SpawnSettingsBuilderME.addFarmAnimals(spawnSettings);
            BiomeFeaturesME.addThistle(vegetation);
            BiomeFeaturesME.addSparseGrass(vegetation);
            BiomeFeaturesME.addHogweeds(vegetation);
            BiomeFeaturesME.addCampion(vegetation);
            BiomeFeaturesME.addVeryRareBirchTrees(vegetation);
            BiomeFeaturesME.addScarceMapleTrees(vegetation);
            BiomeFeaturesME.addDolomiteBoulder(vegetation);
            BiomeFeaturesME.addVeryRareLavender(vegetation);
        } else {
            SpawnSettingsBuilderME.addDeer(spawnSettings);
            vegetation.add(VegetationPlacedFeatures.PATCH_LARGE_FERN);
            BiomeFeaturesME.addNettles(vegetation);
            BiomeFeaturesME.addBracken(vegetation);
            BiomeFeaturesME.addMossyBoulder(vegetation);
            BiomeFeaturesME.addGreenShrub(vegetation);
            BiomeFeaturesME.addBirchTrees(vegetation);
            BiomeFeaturesME.addBeechTrees(vegetation);
            BiomeFeaturesME.addMegaBirchTrees(vegetation);
            BiomeFeaturesME.addCommonDarkOakTrees(vegetation);
            BiomeFeaturesME.addLarchTrees(vegetation);
            BiomeFeaturesME.addMapleTrees(vegetation);
            BiomeFeaturesME.addCommonOakTrees(vegetation);
            BiomeFeaturesME.addPineTrees(vegetation);
            BiomeFeaturesME.addScarceBlackPineTrees(vegetation);
            BiomeFeaturesME.addScarceSpruceTrees(vegetation);
            BiomeFeaturesME.addRareMegaOakTrees(vegetation);
            BiomeFeaturesME.addPodzolOre(vegetation);
            BiomeFeaturesME.addForestMoss(vegetation);
            BiomeFeaturesME.addForestBlockMoss(vegetation);
            BiomeFeaturesME.addSpruceBushes(vegetation);
            BiomeFeaturesME.addRareMorsel(vegetation);
            BiomeFeaturesME.addRareWhiteMushroom(vegetation);
        }

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createAnorienBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        SpawnSettingsBuilderME.addPlainsMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addGondorVegetation(generationSettings);
        BiomeFeaturesME.addGravelOre(vegetation);
        BiomeFeaturesME.addWildLettuce(vegetation);
        BiomeFeaturesME.addOakBushes(vegetation);
        BiomeFeaturesME.addTuftGrass(vegetation);
        BiomeFeaturesME.addDioriteBoulder(vegetation);

        vegetation.add(VegetationPlacedFeatures.TREES_PLAINS);
        BiomeFeaturesME.addRareLebethronTrees(vegetation);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createBarrowDownsBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        BiomeFeaturesME.addDisks(undergroundOres);
        BiomeFeaturesME.addGravelOre(vegetation);
        BiomeFeaturesME.addGrassyDirtOre(vegetation);
        BiomeFeaturesME.addTurfOre(vegetation);

        BiomeFeaturesME.addGrass(vegetation);
        BiomeFeaturesME.addMistweed(vegetation);
        BiomeFeaturesME.addMeadowGrass(vegetation);
        BiomeFeaturesME.addTallGrass(vegetation);
        BiomeFeaturesME.addWildGrass(vegetation);
        BiomeFeaturesME.addSparseGrass(vegetation);
        BiomeFeaturesME.addShriveledShrubs(vegetation);
        BiomeFeaturesME.addBushes(vegetation);
        BiomeFeaturesME.addTuftGrass(vegetation);
        BiomeFeaturesME.addNettles(vegetation);
        BiomeFeaturesME.addThistle(vegetation);
        BiomeFeaturesME.addDyingGrass(vegetation);
        BiomeFeaturesME.addGreenShrub(vegetation);
        BiomeFeaturesME.addSmallDryShrub(vegetation);
        BiomeFeaturesME.addFireflyBushes(vegetation);
        BiomeFeaturesME.addBracken(vegetation);
        vegetation.add(VegetationPlacedFeatures.PATCH_BERRY_RARE);
        BiomeFeaturesME.addVeryRareDryGrass(vegetation);
        vegetation.add(VegetationPlacedFeatures.PALE_MOSS_PATCH);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings, new BiomeParticleConfig(ParticleTypeRegistryME.BIOME_FOG_PARTICLE, 0.003f), 0.5f, true);
    }

    public static void createBeleriandIslandBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        SpawnSettingsBuilderME.addFarmAnimals(spawnSettings);
        SpawnSettingsBuilderME.addMountainsMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addDefaultVegetation(generationSettings);
        BiomeFeaturesME.addCornflower(vegetation);
        BiomeFeaturesME.addGravelOre(vegetation);
        BiomeFeaturesME.addLimestoneBoulder(vegetation);
        BiomeFeaturesME.addGneissBoulder(vegetation);
        BiomeFeaturesME.addOakBushes(vegetation);
        BiomeFeaturesME.addRareBirchTrees(vegetation);
        BiomeFeaturesME.addMegaBirchTrees(vegetation);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createBelfalasBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, int step) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        SpawnSettingsBuilderME.addPlainsMobs(spawnSettings);
        SpawnSettingsBuilderME.addSwan(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        BiomeFeaturesME.addCornflower(vegetation);
        BiomeFeaturesME.addTuftGrass(vegetation);
        BiomeFeaturesME.addRareWilderGrass(vegetation);
        BiomeFeaturesME.addGravelOre(vegetation);
        BiomeFeaturesME.addCalciteBoulder(vegetation);

        if(step == 0) { // Plains
            addGondorVegetation(generationSettings);
            vegetation.add(VegetationPlacedFeatures.TREES_PLAINS);
            BiomeFeaturesME.addWildLettuce(vegetation);
            BiomeFeaturesME.addRareBirchTrees(vegetation);
            BiomeFeaturesME.addRareOakBushes(vegetation);
            BiomeFeaturesME.addLightBlueFlowers(vegetation);
        } else if(step == 1) { // Hills
            addNordicVegetation(generationSettings);
            BiomeFeaturesME.addCalciteOre(vegetation);
            BiomeFeaturesME.addDioriteOre(vegetation);
            BiomeFeaturesME.addTuffOre(vegetation);
            BiomeFeaturesME.addGrassToStoneOre(vegetation);
            BiomeFeaturesME.addLarchTrees(vegetation);
            BiomeFeaturesME.addPineTrees(vegetation);
            BiomeFeaturesME.addRareSpruceTrees(vegetation);
            BiomeFeaturesME.addSpruceBushes(vegetation);
        } else if (step == 2) { // Forest
            SpawnSettingsBuilderME.addDeer(spawnSettings);
            BiomeFeaturesME.addNettles(vegetation);
            BiomeFeaturesME.addOakBushes(vegetation);
            BiomeFeaturesME.addCoarseDirtOre(vegetation);
            BiomeFeaturesME.addLebethronTrees(vegetation);
            BiomeFeaturesME.addRareBirchTrees(vegetation);
            BiomeFeaturesME.addBirchTrees(vegetation);
            BiomeFeaturesME.addBeechTrees(vegetation);
            BiomeFeaturesME.addForestMoss(vegetation);
            BiomeFeaturesME.addFalseOatgrass(vegetation);
            BiomeFeaturesME.addPodzolOre(vegetation);
            BiomeFeaturesME.addHollyTrees(vegetation);
            BiomeFeaturesME.addWhiteMushroom(vegetation);
        }

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createBelfalasShoresBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addGondorVegetation(generationSettings);
        BiomeFeaturesME.addBeachGrass(vegetation);
        BiomeFeaturesME.addDioriteBoulder(vegetation);
        BiomeFeaturesME.addRareOakBushes(vegetation);
        BiomeFeaturesME.addWhitePalmTree(vegetation);
        BiomeFeaturesME.addWhiteFlowers(vegetation);

        vegetation.add(VegetationPlacedFeatures.TREES_PLAINS);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createBlackRootVale(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, boolean forest) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        BiomeFeaturesME.addFlowerGreenJewel(vegetation);
        BiomeFeaturesME.addTuftGrass(vegetation);
        BiomeFeaturesME.addRareWilderGrass(vegetation);
        BiomeFeaturesME.addGravelOre(vegetation);
        BiomeFeaturesME.addCalciteBoulder(vegetation);
        BiomeFeaturesME.addDioriteBoulder(vegetation);
        BiomeFeaturesME.addSpruceBushes(vegetation);

        if(forest) {
            SpawnSettingsBuilderME.addDeer(spawnSettings);
            addNordicVegetation(generationSettings);
            BiomeFeaturesME.addNettles(vegetation);
            BiomeFeaturesME.addDryPineBushes(vegetation);
            BiomeFeaturesME.addCoarseDirtOre(vegetation);
            BiomeFeaturesME.addLarchTrees(vegetation);
            BiomeFeaturesME.addCommonLebethronTrees(vegetation);
            BiomeFeaturesME.addVeryRareMegaOakTrees(vegetation);
            BiomeFeaturesME.addCommonDarkOakTrees(vegetation);
            BiomeFeaturesME.addPineTrees(vegetation);
            BiomeFeaturesME.addBlackPineTrees(vegetation);
            BiomeFeaturesME.addRareSpruceTrees(vegetation);
            BiomeFeaturesME.addPodzolOre(vegetation);
        } else {
            SpawnSettingsBuilderME.addPlainsMobs(spawnSettings);
            addGondorVegetation(generationSettings);
            vegetation.add(VegetationPlacedFeatures.TREES_PLAINS);
            BiomeFeaturesME.addSedum(vegetation);
            BiomeFeaturesME.addTallGrass(vegetation);
            BiomeFeaturesME.addWildGrass(vegetation);
            BiomeFeaturesME.addVeryRareLebethronTrees(vegetation);
        }

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createBlueMountainsBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, int step) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        SpawnSettingsBuilderME.addMountainsMobs(spawnSettings);
        SpawnSettingsBuilderME.addBroadhoofGoats(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));
        float temperature = 0.5f;

        BiomeFeaturesME.addGrassyPeatOre(vegetation);
        BiomeFeaturesME.addGrass(vegetation);
        BiomeFeaturesME.addDisks(undergroundOres);
        vegetation.add(VegetationPlacedFeatures.PATCH_LARGE_FERN);
        vegetation.add(VegetationPlacedFeatures.PATCH_GRASS_TAIGA);
        BiomeFeaturesME.addBracken(vegetation);
        BiomeFeaturesME.addOccasionalWildGrass(vegetation);
        BiomeFeaturesME.addFieldBlueFescue(vegetation);

        if(step != 4) {
            SpawnSettingsBuilderME.addFarmAnimals(spawnSettings);

            vegetation.add(VegetationPlacedFeatures.FLOWER_DEFAULT);
            vegetation.add(VegetationPlacedFeatures.BROWN_MUSHROOM_NORMAL);
            vegetation.add(VegetationPlacedFeatures.RED_MUSHROOM_NORMAL);
            vegetation.add(VegetationPlacedFeatures.PATCH_BERRY_RARE);
            BiomeFeaturesME.addBushes(vegetation);
            BiomeFeaturesME.addClovers(vegetation);
            BiomeFeaturesME.addVeryRareDryGrass(vegetation);
            BiomeFeaturesME.addSparseWheatGrass(vegetation);
            BiomeFeaturesME.addBlueGentianFlower(vegetation);
            BiomeFeaturesME.addBrownBolete(vegetation);
            BiomeFeaturesME.addMorsel(vegetation);
            BiomeFeaturesME.addWhiteMushroom(vegetation);
            BiomeFeaturesME.addWildBeetroot(vegetation);
            BiomeFeaturesME.addWildPotato(vegetation);
            BiomeFeaturesME.addSweetBerriesRare(vegetation);
            BiomeFeaturesME.addGravelOre(vegetation);
        }

        if(step == 0 || step == 1){
            BiomeFeaturesME.addCommonTurfOre(vegetation);
            BiomeFeaturesME.addFieldDeadNormalHeather(vegetation);
            BiomeFeaturesME.addCornflower(vegetation);
            BiomeFeaturesME.addBlueTuff(vegetation);
            BiomeFeaturesME.addRareSpruceTrees(vegetation);
            BiomeFeaturesME.addSpruceBushes(vegetation);
            BiomeFeaturesME.addBlueTuffBoulder(vegetation);
            if(step == 0){
                BiomeFeaturesME.addScarceBlackPineTrees(vegetation);
            }
        } else if(step == 2) { // Base
            BiomeFeaturesME.addSparsePineTrees(vegetation);
            BiomeFeaturesME.addBlueTuffBoulder(vegetation);
        } else if(step == 3) { // High Lands
            BiomeFeaturesME.addNoblewhite(vegetation);
        } else if(step == 4){ // Peaks
            temperature = -0.1f;
        } else if(step == 5) { // Woods
            SpawnSettingsBuilderME.addDeer(spawnSettings);

            BiomeFeaturesME.addFalseOatgrass(vegetation);
            BiomeFeaturesME.addNettles(vegetation);
            BiomeFeaturesME.addGiantButterbur(vegetation);
            BiomeFeaturesME.addCornflower(vegetation);
            BiomeFeaturesME.addBlueTuff(vegetation);
            BiomeFeaturesME.addLarchTrees(vegetation);
            BiomeFeaturesME.addScarceSpruceTrees(vegetation);
            BiomeFeaturesME.addWhiteSpruceTrees(vegetation);
            BiomeFeaturesME.addOakBushes(vegetation);
            BiomeFeaturesME.addSpruceBushes(vegetation);
            BiomeFeaturesME.addWhiteSpruceBushes(vegetation);
            BiomeFeaturesME.addCommonBlackPineTrees(vegetation);
            BiomeFeaturesME.addCommonFirTrees(vegetation);
            BiomeFeaturesME.addForestMoss(vegetation);
            BiomeFeaturesME.addForestBlockMoss(vegetation);
            BiomeFeaturesME.addBlueTuffBoulder(vegetation);
        }

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings, temperature, true);
    }

    public static void createCorsairCoastBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        SpawnSettingsBuilderME.addHaradMobs(spawnSettings);
        SpawnSettingsBuilderME.addWolves(spawnSettings);
        SpawnSettingsBuilderME.addLlama(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addHaradVegetation(generationSettings);
        BiomeFeaturesME.addCoastalFoliage(vegetation);
        vegetation.add(VegetationPlacedFeatures.PATCH_CACTUS_DESERT);
        BiomeFeaturesME.addGraniteBoulder(vegetation);
        BiomeFeaturesME.addDryGrowth(vegetation);
        BiomeFeaturesME.addSandStoneBoulder(vegetation);
        BiomeFeaturesME.addStoneBoulder(vegetation);
        BiomeFeaturesME.addSandOre(vegetation);
        BiomeFeaturesME.addSmallDryShrub(vegetation);
        BiomeFeaturesME.addGravelOre(vegetation);

        BiomeFeaturesME.addPalmTrees(vegetation);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createDaleBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, int step) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        if (step == 0){
            SpawnSettingsBuilderME.addCats(spawnSettings);
            SpawnSettingsBuilderME.addPlainsMobs(spawnSettings);
            addDefaultVegetation(generationSettings);
            BiomeFeaturesME.addThistle(vegetation);
            BiomeFeaturesME.addSparseGrass(vegetation);
            BiomeFeaturesME.addCornflower(vegetation);
            BiomeFeaturesME.addWildLettuce(vegetation);
            BiomeFeaturesME.addCoarseDirtOre(vegetation);
            BiomeFeaturesME.addGravelOre(vegetation);
            BiomeFeaturesME.addExtremelyRareSpruceTrees(vegetation);
            BiomeFeaturesME.addOakBushes(vegetation);
            BiomeFeaturesME.addTallGrass(vegetation);
        }else if(step == 1) { // Forest
            SpawnSettingsBuilderME.addFarmAnimals(spawnSettings);
            SpawnSettingsBuilderME.addWolves(spawnSettings);
            SpawnSettingsBuilderME.addDeer(spawnSettings);
            addDefaultVegetation(generationSettings);
            BiomeFeaturesME.addNettles(vegetation);
            BiomeFeaturesME.addCoarseDirtOre(vegetation);
            BiomeFeaturesME.addFalseOatgrass(vegetation);
            BiomeFeaturesME.addTallGrass(vegetation);
            BiomeFeaturesME.addPodzolOre(vegetation);
            BiomeFeaturesME.addGravelOre(vegetation);
            BiomeFeaturesME.addLarchTrees(vegetation);
            BiomeFeaturesME.addCommonSpruceTrees(vegetation);
            BiomeFeaturesME.addFirTrees(vegetation);
            BiomeFeaturesME.addCommonPineTrees(vegetation);
            BiomeFeaturesME.addMapleTrees(vegetation);
            BiomeFeaturesME.addBirchTrees(vegetation);
            BiomeFeaturesME.addScarceMapleTrees(vegetation);
            BiomeFeaturesME.addHollyTrees(vegetation);
            BiomeFeaturesME.addCommonOakTrees(vegetation);
            BiomeFeaturesME.addForestMoss(vegetation);
            BiomeFeaturesME.addForestBlockMoss(vegetation);
            BiomeFeaturesME.addSpruceBushes(vegetation);
        }else if(step == 2) { // Meadow
            SpawnSettingsBuilderME.addPlainsMobs(spawnSettings);
            addDefaultVegetation(generationSettings);
            BiomeFeaturesME.addUncommonLavender(vegetation);
            BiomeFeaturesME.addTuftGrass(vegetation);
            BiomeFeaturesME.addCornflowerCommon(vegetation);
            BiomeFeaturesME.addAlliumFlower(vegetation);
            BiomeFeaturesME.addLightBlueFlowers(vegetation);
            BiomeFeaturesME.addMagentaFlowers(vegetation);
            BiomeFeaturesME.addPurpleFlowers(vegetation);
        }

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createDeadMarshesBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        undergroundOres.add(MiscPlacedFeatures.DISK_CLAY);
        BiomeFeaturesME.addMistweed(vegetation);
        BiomeFeaturesME.addBasaltOre(vegetation);
        BiomeFeaturesME.addBlackSand(vegetation);
        BiomeFeaturesME.addBrownBolete(vegetation);
        BiomeFeaturesME.addCommonToughBerries(vegetation);
        BiomeFeaturesME.addDeadRushes(vegetation);
        BiomeFeaturesME.addMorsel(vegetation);
        BiomeFeaturesME.addGravelOre(vegetation);
        BiomeFeaturesME.addMireOre(vegetation);
        BiomeFeaturesME.addMudOre(vegetation);
        BiomeFeaturesME.addAshenDirtOre(vegetation);
        BiomeFeaturesME.addOakBushes(vegetation);
        BiomeFeaturesME.addRareSmallSwampOakTrees(vegetation);
        BiomeFeaturesME.addShriveledShrubs(vegetation);
        BiomeFeaturesME.addTallGrass(vegetation);
        BiomeFeaturesME.addWheatGrass(vegetation);
        BiomeFeaturesME.addWildGrass(vegetation);
        BiomeFeaturesME.addAbundantWaterDelta(vegetation);
        BiomeFeaturesME.addGrass(vegetation);
        vegetation.add(VegetationPlacedFeatures.PATCH_TALL_GRASS);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createDeadMarshesWaterBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        undergroundOres.add(MiscPlacedFeatures.DISK_CLAY);
        BiomeFeaturesME.addAshenGravelOre(undergroundOres);
        BiomeFeaturesME.addAshenSandOre(undergroundOres);
        BiomeFeaturesME.addDeadHeather(undergroundOres);
        BiomeFeaturesME.addSoulSandOre(vegetation);
        vegetation.add(OceanPlacedFeatures.KELP_WARM);
        vegetation.add(OceanPlacedFeatures.SEAGRASS_NORMAL);
        BiomeFeaturesME.addGrass(vegetation);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createDesolatedLandsBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        BiomeFeaturesME.addGrassyDirtOre(vegetation);
        BiomeFeaturesME.addAshenGravelOre(undergroundOres);
        BiomeFeaturesME.addAshenSandOre(undergroundOres);
        BiomeFeaturesME.addDyingGrass(undergroundOres);
        BiomeFeaturesME.addCommonToughBerries(undergroundOres);
        vegetation.add(VegetationPlacedFeatures.PATCH_BERRY_COMMON);
        BiomeFeaturesME.addBasaltBoulder(undergroundOres);
        BiomeFeaturesME.addBlackStonePile(undergroundOres);
        BiomeFeaturesME.addDeadHeather(undergroundOres);
        BiomeFeaturesME.addCommonScorchedShrub(undergroundOres);
        BiomeFeaturesME.addCommonScorchedGrass(undergroundOres);
        BiomeFeaturesME.addScorchedTrees(undergroundOres);
        BiomeFeaturesME.addSparsePineTrees(undergroundOres);
        BiomeFeaturesME.addRareFirTrees(undergroundOres);
        BiomeFeaturesME.addScarceBlackPineTrees(undergroundOres);
        BiomeFeaturesME.addRareSpruceTrees(undergroundOres);
        BiomeFeaturesME.addShriveledShrubs(vegetation);
        BiomeFeaturesME.addDeadRushes(vegetation);
        BiomeFeaturesME.addSmallDryShrub(vegetation);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createDorwinionBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, int step) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        SpawnSettingsBuilderME.addPlainsMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        BiomeFeaturesME.addDisks(undergroundOres);
        BiomeFeaturesME.addGrass(vegetation);
        vegetation.add(VegetationPlacedFeatures.BROWN_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.RED_MUSHROOM_NORMAL);
        BiomeFeaturesME.addRareHeather(vegetation);
        BiomeFeaturesME.addGravelOre(vegetation);
        BiomeFeaturesME.addGrassyDirtOre(vegetation);

        if(step == 0){            // Base biome
            vegetation.add(VegetationPlacedFeatures.PATCH_SUGAR_CANE);
            vegetation.add(VegetationPlacedFeatures.PATCH_PUMPKIN);
            BiomeFeaturesME.addFlowerDorwinion(vegetation);
            BiomeFeaturesME.addHeather(vegetation);
            BiomeFeaturesME.addPurpleFlowers(vegetation);
            BiomeFeaturesME.addTuftGrass(vegetation);
            BiomeFeaturesME.addWildGrass(vegetation);
            BiomeFeaturesME.addRareWilderGrass(vegetation);
            BiomeFeaturesME.addDryDirtOre(vegetation);

            BiomeFeaturesME.addWildBeetroot(vegetation);
            BiomeFeaturesME.addWildFlax(vegetation);
            BiomeFeaturesME.addWildLettuce(vegetation);
            BiomeFeaturesME.addWildOnion(vegetation);

            BiomeFeaturesME.addCalciteBoulder(vegetation);
            BiomeFeaturesME.addLimestoneBoulder(vegetation);
            BiomeFeaturesME.addOakBushes(vegetation);
            BiomeFeaturesME.addRareBeechTrees(vegetation);
            BiomeFeaturesME.addSparseBirchTrees(vegetation);
            BiomeFeaturesME.addVeryRareMegaOakTrees(vegetation);
            BiomeFeaturesME.addMegaBirchTrees(vegetation);
        } else if(step == 1) { // Flower Field
            BiomeFeaturesME.addLavenderField(vegetation);
            BiomeFeaturesME.addHeather(vegetation);
            BiomeFeaturesME.addPurpleFlowers(vegetation);
        }

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createDorwinionHillsBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        SpawnSettingsBuilderME.addMountainsMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addNordicVegetation(generationSettings);
        BiomeFeaturesME.addTuffOre(vegetation);
        BiomeFeaturesME.addCalciteOre(vegetation);
        BiomeFeaturesME.addDioriteOre(vegetation);
        BiomeFeaturesME.addLimestoneOre(vegetation);
        BiomeFeaturesME.addStoneGrassAbundantOre(vegetation);
        BiomeFeaturesME.addCommonOakBush(vegetation);
        BiomeFeaturesME.addBirchTrees(vegetation);
        BiomeFeaturesME.addLarchTrees(vegetation);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createDunlandFoothillsBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        SpawnSettingsBuilderME.addMountainsMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addNordicVegetation(generationSettings);
        BiomeFeaturesME.addGravelOre(vegetation);
        BiomeFeaturesME.addCoarseLoamOre(vegetation);
        BiomeFeaturesME.addGrassyLoamOre(vegetation);

        BiomeFeaturesME.addGabbroBoulder(vegetation);
        BiomeFeaturesME.addDolomiteBoulder(vegetation);

        BiomeFeaturesME.addBushes(vegetation);
        BiomeFeaturesME.addSmallDryShrub(vegetation);
        BiomeFeaturesME.addGreenShrub(vegetation);
        BiomeFeaturesME.addShriveledShrubs(vegetation);
        vegetation.add(VegetationPlacedFeatures.PATCH_DEAD_BUSH);

        BiomeFeaturesME.addOakBushes(vegetation);
        BiomeFeaturesME.addSpruceBushes(vegetation);
        BiomeFeaturesME.addGrassToStoneOre(vegetation);
        BiomeFeaturesME.addTuffOre(vegetation);

        BiomeFeaturesME.addLarchTrees(vegetation);
        BiomeFeaturesME.addPineTrees(vegetation);
        BiomeFeaturesME.addFirTrees(vegetation);
        BiomeFeaturesME.addSparsePineTrees(vegetation);
        BiomeFeaturesME.addBlackPineTrees(vegetation);
        BiomeFeaturesME.addScarceBlackPineTrees(vegetation);
        BiomeFeaturesME.addSpruceTrees(vegetation);
        BiomeFeaturesME.addWhiteSpruceTrees(vegetation);
        BiomeFeaturesME.addCommonSpruceBushes(vegetation);
        BiomeFeaturesME.addCommonWhiteSpruceBushes(vegetation);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createEasternRhovanionBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, int step) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        SpawnSettingsBuilderME.addPlainsMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addRhunVegetation(generationSettings);
        BiomeFeaturesME.addLimestoneBoulder(vegetation);
        BiomeFeaturesME.addGrimGrass(vegetation);
        BiomeFeaturesME.addDryDirtOre(vegetation);
        BiomeFeaturesME.addGravelOre(vegetation);
        BiomeFeaturesME.addOakBushes(vegetation);
        BiomeFeaturesME.addWildGrass(vegetation);
        BiomeFeaturesME.addRareWilderGrass(vegetation);
        BiomeFeaturesME.addSedumYellow(vegetation);

        if(step == 0) {
            BiomeFeaturesME.addRareBeechTrees(vegetation);
        } else if (step == 1) {
            BiomeFeaturesME.addFalseOatgrass(vegetation);
            BiomeFeaturesME.addCoarseDirtOre(vegetation);
            BiomeFeaturesME.addCommonBeechTrees(vegetation);
            BiomeFeaturesME.addCommonOakTrees(vegetation);
            BiomeFeaturesME.addBirchTrees(vegetation);
            BiomeFeaturesME.addPineTrees(vegetation);
            BiomeFeaturesME.addForestMoss(vegetation);
            BiomeFeaturesME.addForestBlockMoss(vegetation);
        }

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createEmynMuilBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        SpawnSettingsBuilderME.addRabbits(spawnSettings);

        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        BiomeFeaturesME.addGrass(vegetation);
        vegetation.add(VegetationPlacedFeatures.FLOWER_DEFAULT);
        vegetation.add(VegetationPlacedFeatures.BROWN_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.RED_MUSHROOM_NORMAL);
        BiomeFeaturesME.addStoneBoulder(vegetation);
        BiomeFeaturesME.addGravelOre(vegetation);
        //ModBiomeFeatures.addStoneGrassOre(vegetation);
        BiomeFeaturesME.addTerracottaOre(vegetation);
        BiomeFeaturesME.addTuffOre(vegetation);
        BiomeFeaturesME.addDryDirtOre(vegetation);
        BiomeFeaturesME.addTuftGrass(vegetation);

        BiomeFeaturesME.addCommonOakBush(vegetation);
        BiomeFeaturesME.addGraniteBoulder(vegetation);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createEnedwaithBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, int step) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        SpawnSettingsBuilderME.addPlainsMobs(spawnSettings);
        SpawnSettingsBuilderME.addRabbits(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addDefaultVegetation(generationSettings);
        BiomeFeaturesME.addGreenShrub(vegetation);
        BiomeFeaturesME.addDryDirtOre(vegetation);
        BiomeFeaturesME.addRareHeather(vegetation);
        BiomeFeaturesME.addWildGrass(vegetation);
        BiomeFeaturesME.addWilderGrass(vegetation);
        BiomeFeaturesME.addTuftGrass(vegetation);
        BiomeFeaturesME.addRareWilderGrass(vegetation);
        BiomeFeaturesME.addGrassyDirtOre(vegetation);

        if(step == 0) { // Plains
            BiomeFeaturesME.addGravelOre(vegetation);
            BiomeFeaturesME.addOakBushes(vegetation);
            BiomeFeaturesME.addTallGrass(vegetation);
        } else if(step ==1) { // Fields
            BiomeFeaturesME.addHeather(vegetation);
            BiomeFeaturesME.addHeatherField(vegetation);
            BiomeFeaturesME.addHeath(vegetation);
            BiomeFeaturesME.addUncommonLavender(vegetation);
            BiomeFeaturesME.addCommonHeath(vegetation);
            BiomeFeaturesME.addWheatGrass(vegetation);
        } else if(step == 2){ // Wheat Fields
            SpawnSettingsBuilderME.addRabbits(spawnSettings);
            BiomeFeaturesME.addWildWheatField(vegetation);
            BiomeFeaturesME.addMixedWildWheatPatch(vegetation);
            BiomeFeaturesME.addRareOakBushes(vegetation);
            BiomeFeaturesME.addVeryRareMegaOakTrees(vegetation);
        }

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createLonelyMountainBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, int step) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        SpawnSettingsBuilderME.addMountainsMobs(spawnSettings);
        SpawnSettingsBuilderME.addBroadhoofGoats(spawnSettings);
        SpawnSettingsBuilderME.addNordicMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));
        float temperature = 0.4f;

        if(step == 0) {
            //ModBiomeFeatures.addStoneGrassAbundantOre(vegetation);
            //ModBiomeFeatures.addCoarseDirtOre(vegetation);
            addMountainVegetation(generationSettings);
            BiomeFeaturesME.addFirTrees(vegetation);
            BiomeFeaturesME.addLarchTrees(vegetation);
            BiomeFeaturesME.addPineTrees(vegetation);
            BiomeFeaturesME.addScarceSpruceTrees(vegetation);
            BiomeFeaturesME.addCommonSpruceBushes(vegetation);
        } else if (step == 1) {
            //ModBiomeFeatures.addStoneGrassOre(vegetation);
            BiomeFeaturesME.addRareFirTrees(vegetation);
            BiomeFeaturesME.addSparseLarchTrees(vegetation);
            BiomeFeaturesME.addSparsePineTrees(vegetation);
            BiomeFeaturesME.addScarceSpruceTrees(vegetation);
            vegetation.add(VegetationPlacedFeatures.PATCH_LARGE_FERN);
            vegetation.add(VegetationPlacedFeatures.PATCH_GRASS_TAIGA);
            vegetation.add(VegetationPlacedFeatures.PATCH_BERRY_RARE);
        } else if (step == 2) {
            addNordicVegetation(generationSettings);
        } else if (step == 3) {
            addNordicVegetation(generationSettings);
            SpawnSettingsBuilderME.addFarmAnimals(spawnSettings);
            BiomeFeaturesME.addCoarseDirtOre(vegetation);
            BiomeFeaturesME.addSpruceBushes(vegetation);
            BiomeFeaturesME.addDolomiteBoulder(vegetation);
            BiomeFeaturesME.addVeryRareSpruceTrees(vegetation);
        }
        BiomeFeaturesME.addGravelOre(vegetation);
        BiomeFeaturesME.addAbundantTuffOre(vegetation);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings, temperature, true);
    }

    public static void createEregionBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, int step) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        SpawnSettingsBuilderME.addFarmAnimals(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addDefaultVegetation(generationSettings);
        BiomeFeaturesME.addGrassyDirtOre(vegetation);
        BiomeFeaturesME.addWildGrass(vegetation);
        BiomeFeaturesME.addWilderGrass(vegetation);
        BiomeFeaturesME.addFlowerGreenJewel(vegetation);
        BiomeFeaturesME.addOakBushes(vegetation);
        BiomeFeaturesME.addBushes(vegetation);

        if(step == 0) {
            BiomeFeaturesME.addTurfOre(vegetation);

            BiomeFeaturesME.addHeather(vegetation);
            BiomeFeaturesME.addThistle(vegetation);
            BiomeFeaturesME.addSparseGrass(vegetation);
            BiomeFeaturesME.addTuftGrass(vegetation);
            BiomeFeaturesME.addMeadowGrass(vegetation);
            BiomeFeaturesME.addGravelOre(vegetation);
            BiomeFeaturesME.addSpruceBushes(vegetation);
            BiomeFeaturesME.addGreenGrowth(vegetation);
        } else if (step == 1) {
            SpawnSettingsBuilderME.addGreatHorn(spawnSettings);
            SpawnSettingsBuilderME.addDeer(spawnSettings);
            SpawnSettingsBuilderME.addWolves(spawnSettings);
            BiomeFeaturesME.addNettles(vegetation);
            BiomeFeaturesME.addForestMoss(vegetation);
            BiomeFeaturesME.addForestBlockMoss(vegetation);
            BiomeFeaturesME.addGravelOre(vegetation);
            BiomeFeaturesME.addPodzolOre(vegetation);
            BiomeFeaturesME.addOldPodzolOre(vegetation);
            BiomeFeaturesME.addFallenLeaves(vegetation);
            BiomeFeaturesME.addBirchTrees(vegetation);
            BiomeFeaturesME.addSparseBirchTrees(vegetation);
            BiomeFeaturesME.addBeechTrees(vegetation);
            BiomeFeaturesME.addCommonHollyTrees(vegetation);
            BiomeFeaturesME.addLarchTrees(vegetation);
            BiomeFeaturesME.addCommonOakTrees(vegetation);
            BiomeFeaturesME.addRareMegaOakTrees(vegetation);
            BiomeFeaturesME.addCommonDarkOakTrees(vegetation);
            BiomeFeaturesME.addPineTrees(vegetation);
            BiomeFeaturesME.addSpruceTrees(vegetation);
            BiomeFeaturesME.addSpruceBushes(vegetation);
            BiomeFeaturesME.addGreenGrowth(vegetation);
            BiomeFeaturesME.addGreenShrub(vegetation);
        } else if (step == 2) {
            BiomeFeaturesME.addVeryRareBirchTrees(vegetation);
            BiomeFeaturesME.addCommonHeather(vegetation);
            BiomeFeaturesME.addHeath(vegetation);
            BiomeFeaturesME.addDeadHeather(vegetation);
            BiomeFeaturesME.addWheatGrass(vegetation);
            BiomeFeaturesME.addTuftGrass(vegetation);
            BiomeFeaturesME.addTallGrass(vegetation);
            BiomeFeaturesME.addThistle(vegetation);
            BiomeFeaturesME.addSparseGrass(vegetation);
            BiomeFeaturesME.addMeadowGrass(vegetation);
        }

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createEthirAnduin(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        SpawnSettingsBuilderME.addPlainsMobs(spawnSettings);
        SpawnSettingsBuilderME.addSwampMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        BiomeFeaturesME.addDisks(undergroundOres);
        BiomeFeaturesME.addGrass(vegetation);
        vegetation.add(VegetationPlacedFeatures.PATCH_PUMPKIN);
        vegetation.add(VegetationPlacedFeatures.FLOWER_SWAMP);
        vegetation.add(VegetationPlacedFeatures.PATCH_SUGAR_CANE_SWAMP);
        BiomeFeaturesME.addWaterDelta(vegetation);
        BiomeFeaturesME.addAbundantMudOre(vegetation);
        BiomeFeaturesME.addDeadRushes(vegetation);
        BiomeFeaturesME.addPackedMudOre(vegetation);
        BiomeFeaturesME.addGravelOre(vegetation);
        BiomeFeaturesME.addMudOre(vegetation);
        BiomeFeaturesME.addMireOre(vegetation);
        BiomeFeaturesME.addRareWilderGrass(vegetation);
        BiomeFeaturesME.addReedsFoliage(vegetation);
        BiomeFeaturesME.addWheatGrass(vegetation);
        BiomeFeaturesME.addOakVinesTrees(vegetation);

        BiomeFeaturesME.addCommonTallGrass(undergroundOres);
        BiomeFeaturesME.addBulrushAndCattail(undergroundOres);
        BiomeFeaturesME.addDuckweed(undergroundOres);
        BiomeFeaturesME.addLilyPads(undergroundOres);
        BiomeFeaturesME.addSmallLilyPads(undergroundOres);

        vegetation.add(VegetationPlacedFeatures.TREES_MANGROVE);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createNenHithoelBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, int step) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        BiomeFeaturesME.addDisks(undergroundOres);
        BiomeFeaturesME.addGrass(vegetation);
        vegetation.add(VegetationPlacedFeatures.FLOWER_DEFAULT);
        vegetation.add(VegetationPlacedFeatures.BROWN_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.RED_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.PATCH_SUGAR_CANE);
        vegetation.add(VegetationPlacedFeatures.PATCH_PUMPKIN);
        vegetation.add(VegetationPlacedFeatures.TREES_PLAINS);
        BiomeFeaturesME.addFlowerGreenJewel(vegetation);
        BiomeFeaturesME.addWildFlax(vegetation);
        BiomeFeaturesME.addWildGrass(vegetation);
        BiomeFeaturesME.addWildLeek(vegetation);
        BiomeFeaturesME.addWildLettuce(vegetation);
        BiomeFeaturesME.addWildOnion(vegetation);
        BiomeFeaturesME.addCoarseDirtOre(vegetation);
        BiomeFeaturesME.addGravelOre(vegetation);
        BiomeFeaturesME.addDeadHeather(vegetation);
        BiomeFeaturesME.addDyingGrass(vegetation);
        BiomeFeaturesME.addGraniteBoulder(vegetation);
        BiomeFeaturesME.addStoneBoulder(vegetation);
        BiomeFeaturesME.addDeadRushes(vegetation);
        BiomeFeaturesME.addOakBushes(vegetation);
        BiomeFeaturesME.addSpruceBushes(vegetation);
        BiomeFeaturesME.addCommonBeechTrees(vegetation);
        BiomeFeaturesME.addCommonOakTrees(vegetation);
        BiomeFeaturesME.addGreenMapleTree(vegetation);

        if(step == 0) { // Forest
            SpawnSettingsBuilderME.addDeer(spawnSettings);
            SpawnSettingsBuilderME.addRabbits(spawnSettings);
            SpawnSettingsBuilderME.addWolves(spawnSettings);
            vegetation.add(VegetationPlacedFeatures.PATCH_LARGE_FERN);
            vegetation.add(VegetationPlacedFeatures.PATCH_GRASS_TAIGA);
            BiomeFeaturesME.addNettles(vegetation);
            BiomeFeaturesME.addBracken(vegetation);
            BiomeFeaturesME.addFalseOatgrass(vegetation);
            BiomeFeaturesME.addOldPodzolOre(vegetation);
            BiomeFeaturesME.addPodzolOre(vegetation);
            BiomeFeaturesME.addRareMorsel(vegetation);
            BiomeFeaturesME.addCommonDarkOakTrees(vegetation);
            BiomeFeaturesME.addCommonPineTrees(vegetation);
            BiomeFeaturesME.addBlackPineTrees(vegetation);
            BiomeFeaturesME.addSpruceTrees(vegetation);
            BiomeFeaturesME.addForestMoss(vegetation);
        } else if(step == 1) { // Shores
            BiomeFeaturesME.addGrassToStoneOre(vegetation);
            BiomeFeaturesME.addTuftGrass(vegetation);
            BiomeFeaturesME.addDarkOakTrees(vegetation);
        }

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createNindalf(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        SpawnSettingsBuilderME.addPlainsMobs(spawnSettings);
        SpawnSettingsBuilderME.addSwampMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        BiomeFeaturesME.addDisks(undergroundOres);
        BiomeFeaturesME.addGrass(vegetation);
        vegetation.add(VegetationPlacedFeatures.PATCH_PUMPKIN);
        vegetation.add(VegetationPlacedFeatures.FLOWER_SWAMP);
        vegetation.add(VegetationPlacedFeatures.PATCH_SUGAR_CANE_SWAMP);
        BiomeFeaturesME.addWaterDelta(vegetation);
        BiomeFeaturesME.addAbundantMudOre(vegetation);
        BiomeFeaturesME.addDeadRushes(vegetation);
        BiomeFeaturesME.addSmallLilyPads(vegetation);
        BiomeFeaturesME.addPackedMudOre(vegetation);
        BiomeFeaturesME.addGravelOre(vegetation);
        BiomeFeaturesME.addMudOre(vegetation);
        BiomeFeaturesME.addMireOre(vegetation);
        BiomeFeaturesME.addRareWilderGrass(vegetation);
        BiomeFeaturesME.addReedsFoliage(vegetation);
        BiomeFeaturesME.addWheatGrass(vegetation);
        BiomeFeaturesME.addRareBeechTrees(vegetation);
        BiomeFeaturesME.addOakVinesTrees(vegetation);

        BiomeFeaturesME.addCommonTallGrass(undergroundOres);
        BiomeFeaturesME.addBulrushAndCattail(undergroundOres);
        BiomeFeaturesME.addDuckweed(undergroundOres);
        BiomeFeaturesME.addLilyPads(undergroundOres);
        BiomeFeaturesME.addSmallLilyPads(undergroundOres);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createFangornBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        SpawnSettingsBuilderME.addWolves(spawnSettings);
        SpawnSettingsBuilderME.addDeer(spawnSettings);
        SpawnSettingsBuilderME.addGreatHorn(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        BiomeFeaturesME.addDisks(undergroundOres);
        BiomeFeaturesME.addGrass(vegetation);
        vegetation.add(VegetationPlacedFeatures.FLOWER_DEFAULT);
        vegetation.add(VegetationPlacedFeatures.BROWN_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.RED_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.PATCH_SUGAR_CANE);
        vegetation.add(VegetationPlacedFeatures.PATCH_PUMPKIN);
        vegetation.add(VegetationPlacedFeatures.PATCH_TALL_GRASS_2);
        BiomeFeaturesME.addMistweed(vegetation);
        BiomeFeaturesME.addStoneBoulder(vegetation);
        BiomeFeaturesME.addNettles(vegetation);
        BiomeFeaturesME.addWildBeetroot(vegetation);
        BiomeFeaturesME.addWildFlax(vegetation);
        BiomeFeaturesME.addWildGarlic(vegetation);
        BiomeFeaturesME.addTallGrass(vegetation);
        BiomeFeaturesME.addFalseOatgrass(vegetation);
        BiomeFeaturesME.addWheatGrass(vegetation);
        BiomeFeaturesME.addFallenLeaves(vegetation);
        BiomeFeaturesME.addMossyBoulder(vegetation);
        BiomeFeaturesME.addForestMoss(vegetation);
        BiomeFeaturesME.addForestBlockMoss(vegetation);
        BiomeFeaturesME.addOldPodzolOre(vegetation);
        BiomeFeaturesME.addGravelOre(vegetation);
        BiomeFeaturesME.addWildGrass(vegetation);
        BiomeFeaturesME.addRareMorsel(vegetation);
        BiomeFeaturesME.addRareWhiteMushroom(vegetation);

        BiomeFeaturesME.addOakBushes(vegetation);
        BiomeFeaturesME.addHollyTrees(vegetation);
        BiomeFeaturesME.addMegaBirchCommonTrees(vegetation);
        BiomeFeaturesME.addMegaDarkOakCommonTrees(vegetation);
        BiomeFeaturesME.addMegaOakCommonTrees(vegetation);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createForodwaithBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        SpawnSettingsBuilderME.addForochelMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addForodwaithVegetation(generationSettings);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings, -0.8f, true);
    }

    public static void createGondorBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, int step) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        SpawnSettingsBuilderME.addPlainsMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addGondorVegetation(generationSettings);
        BiomeFeaturesME.addGravelOre(vegetation);
        BiomeFeaturesME.addDioriteBoulder(vegetation);

        vegetation.add(VegetationPlacedFeatures.TREES_PLAINS);

        if(step == 0) {
            BiomeFeaturesME.addTallGrass(vegetation);
            BiomeFeaturesME.addThistle(vegetation);
            BiomeFeaturesME.addSparseGrass(vegetation);
            BiomeFeaturesME.addTuftGrass(vegetation);
            BiomeFeaturesME.addWheatGrass(vegetation);
            BiomeFeaturesME.addVeryRareLebethronTrees(vegetation);
        } else if(step == 1) {
            SpawnSettingsBuilderME.addDeer(spawnSettings);
            BiomeFeaturesME.addRareBeechTrees(vegetation);
            BiomeFeaturesME.addBeechTrees(vegetation);
            BiomeFeaturesME.addBirchTrees(vegetation);
            BiomeFeaturesME.addRareLebethronTrees(vegetation);
            BiomeFeaturesME.addLebethronTrees(vegetation);
            BiomeFeaturesME.addChestnutTrees(vegetation);
            BiomeFeaturesME.addOakTrees(vegetation);
            BiomeFeaturesME.addDarkOakTrees(vegetation);
            BiomeFeaturesME.addRareMegaOakTrees(vegetation);
            BiomeFeaturesME.addGreenShrub(vegetation);
            BiomeFeaturesME.addWildGrass(vegetation);
            BiomeFeaturesME.addWilderGrass(vegetation);
            BiomeFeaturesME.addFalseOatgrass(vegetation);
            BiomeFeaturesME.addRareMorsel(vegetation);
            BiomeFeaturesME.addRareWhiteMushroom(vegetation);
            BiomeFeaturesME.addMossyBoulder(vegetation);
            BiomeFeaturesME.addCommonOakBush(vegetation);
            BiomeFeaturesME.addCoarseDirtOre(vegetation);
            BiomeFeaturesME.addForestMoss(vegetation);
            BiomeFeaturesME.addNettles(vegetation);
            BiomeFeaturesME.addPodzolOre(vegetation);
            BiomeFeaturesME.addForestBlockMoss(vegetation);
        } else if(step == 2) {
            vegetation.add(VegetationPlacedFeatures.PATCH_LARGE_FERN);
            BiomeFeaturesME.addBracken(vegetation);
            BiomeFeaturesME.addGrassToStoneOre(vegetation);
            BiomeFeaturesME.addPineTrees(vegetation);
            BiomeFeaturesME.addBlackPineTrees(vegetation);
            BiomeFeaturesME.addScarceSpruceTrees(vegetation);
            BiomeFeaturesME.addRareMorsel(vegetation);
            BiomeFeaturesME.addRareWhiteMushroom(vegetation);
            BiomeFeaturesME.addSpruceBushes(vegetation);
            BiomeFeaturesME.addOakBushes(vegetation);
        }


        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createGondorRiverSideBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addGondorVegetation(generationSettings);
        BiomeFeaturesME.addGravelOre(vegetation);
        BiomeFeaturesME.addDioriteBoulder(vegetation);
        BiomeFeaturesME.addBeachGrass(vegetation);
        BiomeFeaturesME.addDryGrass(vegetation);
        BiomeFeaturesME.addDeadHeather(vegetation);

        vegetation.add(VegetationPlacedFeatures.TREES_PLAINS);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createGreyMountainsBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, int step) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        SpawnSettingsBuilderME.addMountainsMobs(spawnSettings);
        SpawnSettingsBuilderME.addNordicMobs(spawnSettings);
        SpawnSettingsBuilderME.addBroadhoofGoats(spawnSettings);
        
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        BiomeFeaturesME.addAbundantTuffOre(vegetation);
        BiomeFeaturesME.addTuftGrass(vegetation);
        BiomeFeaturesME.addDyingGrass(vegetation);
        BiomeFeaturesME.addDeadHeather(vegetation);
        if(step == 0) {
            addNordicTrees(generationSettings);
            BiomeFeaturesME.addGravelToSiltOre(vegetation);
            addNordicVegetation(generationSettings);
            BiomeFeaturesME.addShriveledShrubs(vegetation);
            BiomeFeaturesME.addSnowyDirt(vegetation);
            BiomeFeaturesME.addFalseOatgrass(vegetation);
            SpawnSettingsBuilderME.addUncommonWarg(spawnSettings);
            SpawnSettingsBuilderME.addWolves(spawnSettings);
            SpawnSettingsBuilderME.addDeer(spawnSettings);
            SpawnSettingsBuilderME.addRabbits(spawnSettings);
        }
        if(step == 1) {
            BiomeFeaturesME.addGravelOre(vegetation);
            addNordicTrees(generationSettings);
            addNordicVegetation(generationSettings);
            BiomeFeaturesME.addSnowyGrass(vegetation);
            BiomeFeaturesME.addSnowyDirt(vegetation);
            BiomeFeaturesME.addFrozenGrass(vegetation);
            BiomeFeaturesME.addFrozenShrub(vegetation);
            BiomeFeaturesME.addShriveledShrubs(vegetation);
            SpawnSettingsBuilderME.addRareWarg(spawnSettings);
            SpawnSettingsBuilderME.addWolves(spawnSettings);
            SpawnSettingsBuilderME.addRabbits(spawnSettings);
        }
        if(step == 2) {
            BiomeFeaturesME.addGravelOre(vegetation);
            BiomeFeaturesME.addPowderSnowOre(vegetation);
            BiomeFeaturesME.addSnowyGrass(vegetation);
            BiomeFeaturesME.addSnowyDirt(vegetation);
            BiomeFeaturesME.addFrozenGrass(vegetation);
            BiomeFeaturesME.addFrozenShrub(vegetation);
            BiomeFeaturesME.addShriveledShrubs(vegetation);
        }
        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createMountGundabadBiomes(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, int step) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        SpawnSettingsBuilderME.addMountainsMobs(spawnSettings);
        SpawnSettingsBuilderME.addNordicMobs(spawnSettings);
        SpawnSettingsBuilderME.addBroadhoofGoats(spawnSettings);
        
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        BiomeFeaturesME.addGravelOre(vegetation);
        BiomeFeaturesME.addAbundantTuffOre(vegetation);

        if(step == 0) {
            addNordicTrees(generationSettings);
            addNordicVegetation(generationSettings);
            BiomeFeaturesME.addRareFirTrees(vegetation);
            BiomeFeaturesME.addShriveledShrubs(vegetation);
            BiomeFeaturesME.addSnowyDirt(vegetation);
            BiomeFeaturesME.addFalseOatgrass(vegetation);
        } else if(step == 1) {
            addNordicTrees(generationSettings);
            addNordicVegetation(generationSettings);
            BiomeFeaturesME.addSnowyGrass(vegetation);
            BiomeFeaturesME.addSnowyDirt(vegetation);
            BiomeFeaturesME.addFrozenGrass(vegetation);
            BiomeFeaturesME.addFrozenShrub(vegetation);
            BiomeFeaturesME.addShriveledShrubs(vegetation);
        } else if(step == 2) {
            BiomeFeaturesME.addPowderSnowOre(vegetation);
        }
        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createGreyPlainsBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, int step) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        SpawnSettingsBuilderME.addMountainsMobs(spawnSettings);
        SpawnSettingsBuilderME.addWolves(spawnSettings);
        SpawnSettingsBuilderME.addNordicMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        BiomeFeaturesME.addDisks(undergroundOres);
        vegetation.add(VegetationPlacedFeatures.PATCH_LARGE_FERN);
        vegetation.add(VegetationPlacedFeatures.PATCH_GRASS_TAIGA);
        vegetation.add(VegetationPlacedFeatures.BROWN_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.RED_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.PATCH_BERRY_RARE);
        BiomeFeaturesME.addBracken(vegetation);
        BiomeFeaturesME.addWildGrass(vegetation);
        BiomeFeaturesME.addGrass(vegetation);
        BiomeFeaturesME.addBrownBolete(vegetation);
        BiomeFeaturesME.addMorsel(vegetation);
        BiomeFeaturesME.addWhiteMushroom(vegetation);
        BiomeFeaturesME.addToughBerriesRare(vegetation);

        BiomeFeaturesME.addGravelToSiltOre(vegetation);
        BiomeFeaturesME.addFalseOatgrass(vegetation);
        BiomeFeaturesME.addSlateBoulder(vegetation);
        if(step == 0) {
            BiomeFeaturesME.addMixedWildWheatPatch(vegetation);
            SpawnSettingsBuilderME.addFarmAnimals(spawnSettings);
            vegetation.add(VegetationPlacedFeatures.PATCH_DRY_GRASS_DESERT);
            BiomeFeaturesME.addSedums(vegetation);
        } else if(step == 1) { // Scorched
            BiomeFeaturesME.addCommonScorchedGrass(vegetation);
            BiomeFeaturesME.addCommonScorchedShrub(vegetation);
            BiomeFeaturesME.addCommonScorchedTrees(vegetation);
            BiomeFeaturesME.addDryPineBushes(vegetation);
            BiomeFeaturesME.addDryPineTrees(vegetation);
            BiomeFeaturesME.addBlackPineTrees(vegetation);
            BiomeFeaturesME.addFirTrees(vegetation);
            BiomeFeaturesME.addCommonFirTrees(vegetation);
            BiomeFeaturesME.addDeadBlackPineTrees(vegetation);
            BiomeFeaturesME.addScarceBlackPineTrees(vegetation);
            BiomeFeaturesME.addShriveledShrubs(vegetation);
            BiomeFeaturesME.addDeadRushes(undergroundOres);
            BiomeFeaturesME.addAshBlockOre(vegetation);
        }else if(step == 2) { // Forest
            BiomeFeaturesME.addGravelOre(vegetation);
            BiomeFeaturesME.addForestMoss(vegetation);
            BiomeFeaturesME.addBushes(vegetation);
            BiomeFeaturesME.addDryPineBushes(vegetation);
            BiomeFeaturesME.addDryPineTrees(vegetation);
            BiomeFeaturesME.addDeadBlackPineTrees(vegetation);
            BiomeFeaturesME.addCommonFirTrees(vegetation);
            BiomeFeaturesME.addCommonPineTrees(vegetation);
            BiomeFeaturesME.addCommonBlackPineTrees(vegetation);
            BiomeFeaturesME.addRareFirTrees(vegetation);
            BiomeFeaturesME.addPineTrees(vegetation);
            BiomeFeaturesME.addScarceBlackPineTrees(vegetation);
        }

        BiomeFeaturesME.addSparsePineTrees(vegetation);
        BiomeFeaturesME.addSpruceBushes(vegetation);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createGundabadPlains(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, int step) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        SpawnSettingsBuilderME.addFarmAnimals(spawnSettings);
        SpawnSettingsBuilderME.addWolves(spawnSettings);
        SpawnSettingsBuilderME.addNordicMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));
        SpawnSettingsBuilderME.addUncommonWarg(spawnSettings);

        BiomeFeaturesME.addDisks(undergroundOres);
        vegetation.add(VegetationPlacedFeatures.PATCH_LARGE_FERN);
        vegetation.add(VegetationPlacedFeatures.PATCH_GRASS_TAIGA);
        vegetation.add(VegetationPlacedFeatures.BROWN_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.RED_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.PATCH_BERRY_RARE);
        BiomeFeaturesME.addBracken(vegetation);
        BiomeFeaturesME.addWildGrass(vegetation);
        BiomeFeaturesME.addGrass(vegetation);
        BiomeFeaturesME.addBrownBolete(vegetation);
        BiomeFeaturesME.addMorsel(vegetation);
        BiomeFeaturesME.addWhiteMushroom(vegetation);
        BiomeFeaturesME.addWildBeetroot(vegetation);
        BiomeFeaturesME.addWildPotato(vegetation);
        BiomeFeaturesME.addToughBerriesRare(vegetation);

        if(step == 0) {
            BiomeFeaturesME.addScarceBlackPineTrees(vegetation);
            BiomeFeaturesME.addVeryRareFirTrees(vegetation);
            BiomeFeaturesME.addDeadHeather(vegetation);
        } else {
            vegetation.add(VegetationPlacedFeatures.FLOWER_DEFAULT);
            BiomeFeaturesME.addForestMoss(vegetation);
            BiomeFeaturesME.addFalseOatgrass(vegetation);
            BiomeFeaturesME.addNettles(vegetation);
            BiomeFeaturesME.addOakTrees(vegetation);
            BiomeFeaturesME.addCommonDarkOakTrees(vegetation);
            BiomeFeaturesME.addLarchTrees(vegetation);
            BiomeFeaturesME.addCommonFirTrees(vegetation);
            BiomeFeaturesME.addCommonPineTrees(vegetation);
            BiomeFeaturesME.addCommonBlackPineTrees(vegetation);
            BiomeFeaturesME.addSpruceTrees(vegetation);
        }

        BiomeFeaturesME.addCoarseLoamToGrassy(vegetation);
        BiomeFeaturesME.addCoarseLoamToFoulDirt(vegetation);
        BiomeFeaturesME.addSpruceBushes(vegetation);
        BiomeFeaturesME.addShriveledShrubs(vegetation);
        BiomeFeaturesME.addSlateBoulder(vegetation);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createHaradBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, int step) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        SpawnSettingsBuilderME.addHaradMobs(spawnSettings);
        SpawnSettingsBuilderME.addFarmAnimals(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addHaradVegetation(generationSettings);
        BiomeFeaturesME.addSandOre(vegetation);
        BiomeFeaturesME.addGravelOre(vegetation);
        BiomeFeaturesME.addOakBushes(vegetation);
        BiomeFeaturesME.addDryHeather(vegetation);
        BiomeFeaturesME.addTallGrass(vegetation);
        vegetation.add(VegetationPlacedFeatures.PATCH_DEAD_BUSH);

        if(step == 0) { // Savannah
            SpawnSettingsBuilderME.addArmadillo(spawnSettings);
            BiomeFeaturesME.addRareAcaciaTrees(vegetation);
            BiomeFeaturesME.addWilderGrass(vegetation);
            BiomeFeaturesME.addDryGrass(vegetation);
            BiomeFeaturesME.addSmallDryShrub(vegetation);
        } else if(step == 1) { // Forest
            SpawnSettingsBuilderME.addRareWolves(spawnSettings);
            BiomeFeaturesME.addDryGrowth(vegetation);
            BiomeFeaturesME.addNettles(vegetation);
            BiomeFeaturesME.addCommonAcaciaTrees(vegetation);
            BiomeFeaturesME.addCoarseDirtOre(vegetation);
            BiomeFeaturesME.addAcaciaTrees(vegetation);
            BiomeFeaturesME.addBeechTrees(vegetation);
            BiomeFeaturesME.addOakTrees(vegetation);
            BiomeFeaturesME.addRareOakTrees(vegetation);
            BiomeFeaturesME.addFalseOatgrass(vegetation);
            vegetation.add(VegetationPlacedFeatures.TREES_PLAINS);
        }


        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createHarondorBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        SpawnSettingsBuilderME.addHaradMobs(spawnSettings);
        SpawnSettingsBuilderME.addFarmAnimals(spawnSettings);
        SpawnSettingsBuilderME.addCats(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addHaradVegetation(generationSettings);
        BiomeFeaturesME.addDryHeather(vegetation);
        BiomeFeaturesME.addOakBushes(vegetation);
        BiomeFeaturesME.addGravelOre(vegetation);
        BiomeFeaturesME.addSandOre(vegetation);
        BiomeFeaturesME.addSandStoneBoulder(vegetation);
        BiomeFeaturesME.addStoneBoulder(vegetation);
        BiomeFeaturesME.addWildFlax(vegetation);
        BiomeFeaturesME.addSmallDryShrub(vegetation);

        BiomeFeaturesME.addVeryRareBirchTrees(vegetation);
        BiomeFeaturesME.addVeryRareBeechTrees(vegetation);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createHaradDesertBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        SpawnSettingsBuilderME.addHaradMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addHaradDesertVegetation(generationSettings);
        vegetation.add(VegetationPlacedFeatures.PATCH_DEAD_BUSH_2);
        vegetation.add(VegetationPlacedFeatures.PATCH_CACTUS_DESERT);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings, 0.8f, false);
    }

    public static void createHillsOfEvendim(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        SpawnSettingsBuilderME.addMountainsMobs(spawnSettings);
        SpawnSettingsBuilderME.addNordicMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addArthedainVegetation(generationSettings);

        BiomeFeaturesME.addAbundantTuffOre(vegetation);
        BiomeFeaturesME.addGrassyDirtOre(vegetation);
        BiomeFeaturesME.addRootedDirtOre(vegetation);
        BiomeFeaturesME.addCoarseDirtOre(vegetation);

        BiomeFeaturesME.addCommonLarchTrees(vegetation);
        BiomeFeaturesME.addPineTrees(vegetation);
        BiomeFeaturesME.addSpruceTrees(vegetation);

        BiomeFeaturesME.addGiantButterbur(vegetation);
        BiomeFeaturesME.addBracken(vegetation);
        BiomeFeaturesME.addFalseOatgrass(vegetation);
        BiomeFeaturesME.addDryGrass(vegetation);
        BiomeFeaturesME.addNettles(vegetation);
        BiomeFeaturesME.addSweetBerriesRare(vegetation);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createIronHillsBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, boolean foothills) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        SpawnSettingsBuilderME.addMountainsMobs(spawnSettings);
        SpawnSettingsBuilderME.addBroadhoofGoats(spawnSettings);
        SpawnSettingsBuilderME.addNordicMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addNordicVegetation(generationSettings);
        if(foothills) {
            //ModBiomeFeatures.addGrassStoneOre(vegetation);
            BiomeFeaturesME.addCornflower(vegetation);
        }
        BiomeFeaturesME.addRareFirTrees(vegetation);
        BiomeFeaturesME.addLarchTrees(vegetation);
        BiomeFeaturesME.addSparsePineTrees(vegetation);
        BiomeFeaturesME.addRareSpruceTrees(vegetation);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createIsengardBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, boolean trees) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        SpawnSettingsBuilderME.addFarmAnimals(spawnSettings);
        SpawnSettingsBuilderME.addUncommonWarg(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        BiomeFeaturesME.addRareWilderGrass(vegetation);
        BiomeFeaturesME.addOakBushes(vegetation);
        addDefaultVegetation(generationSettings);

        if(trees) {
            BiomeFeaturesME.addOakTrees(vegetation);
            BiomeFeaturesME.addRareBeechTrees(vegetation);
            BiomeFeaturesME.addRareBirchTrees(vegetation);
            BiomeFeaturesME.addSparsePineTrees(vegetation);
            BiomeFeaturesME.addRareSpruceTrees(vegetation);
        }

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createRhovanionTaigaBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, int step) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        SpawnSettingsBuilderME.addFarmAnimals(spawnSettings);
        SpawnSettingsBuilderME.addNordicMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addNordicVegetation(generationSettings);
        BiomeFeaturesME.addSpruceBushes(vegetation);

        if(step == 0) { // Plains
            BiomeFeaturesME.addCornflower(vegetation);
            BiomeFeaturesME.addCoarseDirtOre(vegetation);
            BiomeFeaturesME.addHematiteBoulder(vegetation);
            BiomeFeaturesME.addIronStoneBoulder(vegetation);
            BiomeFeaturesME.addRareSpruceTrees(vegetation);
        } else if(step == 1) { // Forest
            BiomeFeaturesME.addFalseOatgrass(vegetation);
            BiomeFeaturesME.addNettles(vegetation);
            BiomeFeaturesME.addCoarseDirtOre(vegetation);
            BiomeFeaturesME.addGravelOre(vegetation);
            BiomeFeaturesME.addDolomiteBoulder(vegetation);
            BiomeFeaturesME.addOakTrees(vegetation);
            BiomeFeaturesME.addCommonDarkOakTrees(vegetation);
            BiomeFeaturesME.addLarchTrees(vegetation);
            BiomeFeaturesME.addCommonFirTrees(vegetation);
            BiomeFeaturesME.addCommonPineTrees(vegetation);
            BiomeFeaturesME.addCommonBlackPineTrees(vegetation);
            BiomeFeaturesME.addSpruceTrees(vegetation);
        } else if (step == 2) { // Hills
            BiomeFeaturesME.addGrassToStoneOre(vegetation);
            BiomeFeaturesME.addFalseOatgrass(vegetation);
            BiomeFeaturesME.addLarchTrees(vegetation);
            BiomeFeaturesME.addPineTrees(vegetation);
            BiomeFeaturesME.addFirTrees(vegetation);
            BiomeFeaturesME.addBlackPineTrees(vegetation);
            BiomeFeaturesME.addSpruceTrees(vegetation);
        } else if (step == 3) { // Foothills
            BiomeFeaturesME.addSparseLarchTrees(vegetation);
            BiomeFeaturesME.addPineTrees(vegetation);
            BiomeFeaturesME.addSpruceTrees(vegetation);
            BiomeFeaturesME.addFirTrees(vegetation);
        }

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createIthilienBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, boolean wastes, boolean glade) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        SpawnSettingsBuilderME.addFarmAnimals(spawnSettings);
        SpawnSettingsBuilderME.addDeer(spawnSettings);
        SpawnSettingsBuilderME.addRabbits(spawnSettings);
        SpawnSettingsBuilderME.addRareWarg(spawnSettings);
        SpawnSettingsBuilderME.addWolves(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        BiomeFeaturesME.addGravelOre(vegetation);
        BiomeFeaturesME.addWildGrass(vegetation);
        BiomeFeaturesME.addWildPotato(vegetation);
        BiomeFeaturesME.addFalseOatgrass(vegetation);
        BiomeFeaturesME.addRareMorsel(vegetation);
        BiomeFeaturesME.addRareWhiteMushroom(vegetation);
        BiomeFeaturesME.addMossyBoulder(vegetation);
        BiomeFeaturesME.addCommonOakBush(vegetation);
        BiomeFeaturesME.addCoarseDirtOre(vegetation);
        BiomeFeaturesME.addDioriteBoulder(vegetation);
        BiomeFeaturesME.addGreenShrub(vegetation);

        addGondorVegetation(generationSettings);

        if(!glade) {
            BiomeFeaturesME.addFallenLeaves(vegetation);
            BiomeFeaturesME.addNettles(vegetation);
            BiomeFeaturesME.addBracken(vegetation);
            BiomeFeaturesME.addWheatGrass(vegetation);
            BiomeFeaturesME.addForestMoss(vegetation);
            BiomeFeaturesME.addOakTrees(vegetation);
            BiomeFeaturesME.addPodzolOre(vegetation);
            BiomeFeaturesME.addSparsePineTrees(vegetation);
            BiomeFeaturesME.addBlackPineTrees(vegetation);
            BiomeFeaturesME.addForestBlockMoss(vegetation);
        } else {
            BiomeFeaturesME.addHeather(vegetation);
            BiomeFeaturesME.addTallGrass(vegetation);
            BiomeFeaturesME.addSparseWheatGrass(vegetation);
            BiomeFeaturesME.addTuftGrass(vegetation);
            BiomeFeaturesME.addVeryRareLebethronTrees(vegetation);
        }

        if(!wastes) {
            BiomeFeaturesME.addFlowerGreenJewel(vegetation);
            BiomeFeaturesME.addRareWilderGrass(vegetation);
            if(!glade) {
                BiomeFeaturesME.addBeechTrees(vegetation);
                BiomeFeaturesME.addBirchTrees(vegetation);
                BiomeFeaturesME.addCommonLebethronTrees(vegetation);
                BiomeFeaturesME.addChestnutTrees(vegetation);
                BiomeFeaturesME.addMegaOakTrees(vegetation);
            } else {
                BiomeFeaturesME.addRedFlowers(vegetation);
            }
        } else {
            BiomeFeaturesME.addCommonToughBerries(undergroundOres);
            BiomeFeaturesME.addAshenDirtOre(undergroundOres);
            BiomeFeaturesME.addThornyGrowth(vegetation);
            if(!glade) {
                BiomeFeaturesME.addRareBirchTrees(vegetation);
                BiomeFeaturesME.addLebethronTrees(vegetation);
                BiomeFeaturesME.addDeadPineTrees(vegetation);
                BiomeFeaturesME.addDeadBlackPineTrees(vegetation);
                BiomeFeaturesME.addRareBeechTrees(vegetation);
                BiomeFeaturesME.addRottenTrees(vegetation);
            }
        }

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createLamedonBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, int step) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        SpawnSettingsBuilderME.addPlainsMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addGondorVegetation(generationSettings);
        BiomeFeaturesME.addDioriteOre(vegetation);
        BiomeFeaturesME.addGravelOre(vegetation);
        BiomeFeaturesME.addCoarseDirtOre(vegetation);
        BiomeFeaturesME.addTurfOre(vegetation);
        BiomeFeaturesME.addCalciteBoulder(vegetation);
        BiomeFeaturesME.addDioriteBoulder(vegetation);

        if(step == 0) {
            BiomeFeaturesME.addWhiteFlowers(vegetation);
            BiomeFeaturesME.addTuftGrass(vegetation);
        } else if (step == 1) {
            BiomeFeaturesME.addCalciteOre(vegetation);
            BiomeFeaturesME.addPineTrees(vegetation);
            BiomeFeaturesME.addSparseBirchTrees(vegetation);
            BiomeFeaturesME.addRareBeechTrees(vegetation);
            vegetation.add(VegetationPlacedFeatures.PATCH_LARGE_FERN);
            BiomeFeaturesME.addBracken(vegetation);
            BiomeFeaturesME.addGrassToStoneOre(vegetation);
            BiomeFeaturesME.addScarceSpruceTrees(vegetation);
            BiomeFeaturesME.addRareMorsel(vegetation);
            BiomeFeaturesME.addRareWhiteMushroom(vegetation);
            BiomeFeaturesME.addSpruceBushes(vegetation);
        }

        vegetation.add(VegetationPlacedFeatures.TREES_PLAINS);
        BiomeFeaturesME.addOakBushes(vegetation);
        BiomeFeaturesME.addVeryRareBirchTrees(vegetation);
        BiomeFeaturesME.addVeryRareLebethronTrees(vegetation);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createLebennin(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, int step) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        SpawnSettingsBuilderME.addPlainsMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addGondorVegetation(generationSettings);
        BiomeFeaturesME.addLebenninFlowers(vegetation);
        BiomeFeaturesME.addMallos(vegetation);
        BiomeFeaturesME.addGravelOre(vegetation);
        BiomeFeaturesME.addCalciteBoulder(vegetation);
        BiomeFeaturesME.addDioriteBoulder(vegetation);

        vegetation.add(VegetationPlacedFeatures.TREES_PLAINS);
        BiomeFeaturesME.addOakBushes(vegetation);

        if(step == 0) { // Plains
            SpawnSettingsBuilderME.addCats(spawnSettings);
            BiomeFeaturesME.addLightBlueFlowers(vegetation);
            BiomeFeaturesME.addRareBeechTrees(vegetation);
            BiomeFeaturesME.addVeryRareLebethronTrees(vegetation);
            BiomeFeaturesME.addYellowTrolliusPatch(vegetation);
        } else if (step == 1) { // Hills
            BiomeFeaturesME.addBlackPineTrees(vegetation);
            BiomeFeaturesME.addPineTrees(vegetation);
            BiomeFeaturesME.addSpruceTrees(vegetation);
            BiomeFeaturesME.addSparseLarchTrees(vegetation);
            BiomeFeaturesME.addSpruceBushes(vegetation);
            BiomeFeaturesME.addStoneGrassOre(vegetation);
            BiomeFeaturesME.addFalseOatgrass(vegetation);
            BiomeFeaturesME.addBracken(vegetation);
        }

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createLindonBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, int step) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        SpawnSettingsBuilderME.addFarmAnimals(spawnSettings);
        SpawnSettingsBuilderME.addPlainsMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        BiomeFeaturesME.addDisks(undergroundOres);
        BiomeFeaturesME.addGrassyDirtOre(vegetation);
        BiomeFeaturesME.addGrass(vegetation);
        BiomeFeaturesME.addTuftGrass(vegetation);
        vegetation.add(VegetationPlacedFeatures.FLOWER_DEFAULT);
        vegetation.add(VegetationPlacedFeatures.BROWN_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.RED_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.PATCH_SUGAR_CANE);
        vegetation.add(VegetationPlacedFeatures.PATCH_PUMPKIN);
        BiomeFeaturesME.addGalonnBoulder(vegetation);
        BiomeFeaturesME.addWildCucumber(vegetation);
        BiomeFeaturesME.addWildFlax(vegetation);
        BiomeFeaturesME.addWildGarlic(vegetation);

        BiomeFeaturesME.addCornflower(vegetation);
        BiomeFeaturesME.addGravelOre(vegetation);
        BiomeFeaturesME.addOakBushes(vegetation);
        BiomeFeaturesME.addCommonTurfOre(vegetation);

        if(step == 0) { // Plains
            BiomeFeaturesME.addVeryRareBirchTrees(vegetation);
            BiomeFeaturesME.addFieldDeadNormalHeather(vegetation);
            BiomeFeaturesME.addTallGrass(vegetation);
            BiomeFeaturesME.addHogweeds(vegetation);
            BiomeFeaturesME.addSparseBlueLavender(vegetation);
            BiomeFeaturesME.addBigleafHydrangeas(vegetation);
            BiomeFeaturesME.addThistle(vegetation);
            BiomeFeaturesME.addSparseGrass(vegetation);
            BiomeFeaturesME.addHeath(vegetation);
            BiomeFeaturesME.addWildFlowers(vegetation);
            BiomeFeaturesME.addMeadowGrass(vegetation);
            BiomeFeaturesME.addBushes(vegetation);
            BiomeFeaturesME.addOccasionalWildGrass(vegetation);
            BiomeFeaturesME.addClovers(vegetation);
            BiomeFeaturesME.addBeesOakTrees(vegetation);
            vegetation.add(VegetationPlacedFeatures.TREES_PLAINS);
            BiomeFeaturesME.addLimestoneBoulder(vegetation);
        } else if(step == 1) { // Cliffs
            BiomeFeaturesME.addSandToGrass(vegetation);
            BiomeFeaturesME.addGrassyChalksoilOre(vegetation);
            BiomeFeaturesME.addTurfOre(vegetation);
            BiomeFeaturesME.addGreenShrub(vegetation);
            BiomeFeaturesME.addCommonBeachGrass(vegetation);
            BiomeFeaturesME.addCoastalFoliage(vegetation);
            BiomeFeaturesME.addDioriteBoulder(vegetation);
            BiomeFeaturesME.addBushes(vegetation);
            BiomeFeaturesME.addFieldDeadNormalHeather(vegetation);
            BiomeFeaturesME.addCommonOakBush(vegetation);
            BiomeFeaturesME.addWhiteFlowers(vegetation);
            BiomeFeaturesME.addCalciteBoulder(vegetation);
        } else if(step == 2) { // Shores
            BiomeFeaturesME.addBeachGrass(vegetation);
            BiomeFeaturesME.addDioriteBoulder(vegetation);
            BiomeFeaturesME.addRareOakBushes(vegetation);
            BiomeFeaturesME.addWhiteFlowers(vegetation);
        } else if(step == 3) { // Forest
            SpawnSettingsBuilderME.addDeer(spawnSettings);
            SpawnSettingsBuilderME.addPheasant(spawnSettings);

            vegetation.add(VegetationPlacedFeatures.PATCH_LARGE_FERN);
            vegetation.add(VegetationPlacedFeatures.PATCH_GRASS_TAIGA);
            vegetation.add(VegetationPlacedFeatures.TREES_PLAINS);
            BiomeFeaturesME.addCoarseDirtOre(vegetation);
            BiomeFeaturesME.addRootedDirtOre(vegetation);
            BiomeFeaturesME.addNettles(vegetation);
            BiomeFeaturesME.addWildPotato(vegetation);
            BiomeFeaturesME.addWildOnion(vegetation);
            BiomeFeaturesME.addWildGrass(vegetation);
            BiomeFeaturesME.addGiantButterbur(vegetation);
            BiomeFeaturesME.addBracken(vegetation);
            BiomeFeaturesME.addBushes(vegetation);
            BiomeFeaturesME.addGreenShrub(vegetation);
            BiomeFeaturesME.addStrawberries(vegetation);
            BiomeFeaturesME.addFalseOatgrass(vegetation);
            BiomeFeaturesME.addForestMoss(vegetation);
            BiomeFeaturesME.addForestBlockMoss(vegetation);
            BiomeFeaturesME.addFallenLeaves(vegetation);
            BiomeFeaturesME.addBirchTrees(vegetation);
            BiomeFeaturesME.addCommonAspenTrees(vegetation);
            BiomeFeaturesME.addSparseAspenTrees(vegetation);
            BiomeFeaturesME.addAspenTrees(vegetation);
            BiomeFeaturesME.addCommonOakTrees(vegetation);
            BiomeFeaturesME.addMegaBirchTrees(vegetation);
            BiomeFeaturesME.addRareMegaOakTrees(vegetation);
            vegetation.add(VegetationPlacedFeatures.PATCH_LEAF_LITTER);
        } else if(step == 4) { // Hidden Blossom
            BiomeFeaturesME.addWildFlowers(vegetation);
            BiomeFeaturesME.addWildGrass(vegetation);
            BiomeFeaturesME.addBeesOakTrees(vegetation);
            BiomeFeaturesME.addAspenTrees(vegetation);
            BiomeFeaturesME.addSparseBirchTrees(vegetation);
            BiomeFeaturesME.addCherryBlossomTrees(vegetation);
            BiomeFeaturesME.addPinkFlowers(vegetation);
            BiomeFeaturesME.addWhiteFlowers(vegetation);
            vegetation.add(VegetationPlacedFeatures.PATCH_LEAF_LITTER);
            vegetation.add(TreePlacedFeatures.FALLEN_BIRCH_TREE);
            vegetation.add(VegetationPlacedFeatures.FLOWER_CHERRY);
            vegetation.add(VegetationPlacedFeatures.TREES_CHERRY);
        } else if(step == 5) { // Meadow
            vegetation.add(VegetationPlacedFeatures.TREES_PLAINS);
            BiomeFeaturesME.addSparseFieldDeadNormalHeather(vegetation);
            BiomeFeaturesME.addBigleafHydrangeas(vegetation);
            BiomeFeaturesME.addYellowTrolliusPatch(vegetation);
            BiomeFeaturesME.addCornflowerCommon(vegetation);
            BiomeFeaturesME.addLightBlueFlowers(vegetation);
            BiomeFeaturesME.addAzureBluetFlower(vegetation);
            BiomeFeaturesME.addWhiteFlowers(vegetation);
        }

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createLorienEdgeBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        SpawnSettingsBuilderME.addFarmAnimals(spawnSettings);
        SpawnSettingsBuilderME.addSwan(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addLothlorienVegetation(generationSettings);
        BiomeFeaturesME.addMallornTrees(vegetation);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createLothlorienBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, int step) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        SpawnSettingsBuilderME.addFarmAnimals(spawnSettings);
        SpawnSettingsBuilderME.addDeer(spawnSettings);
        SpawnSettingsBuilderME.addGreatHorn(spawnSettings);
        SpawnSettingsBuilderME.addSwan(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addLothlorienVegetation(generationSettings);
        BiomeFeaturesME.addRareAzureBluetFlower(vegetation);

        if(step == 0) { // Forest
            BiomeFeaturesME.addSmallMallornTress(vegetation);
            BiomeFeaturesME.addOakTrees(vegetation);
            BiomeFeaturesME.addSparseBirchTrees(vegetation);
            BiomeFeaturesME.addMegaMallornTrees(vegetation);
            BiomeFeaturesME.addFallenMallornLeaves(vegetation);
            BiomeFeaturesME.addShriveledShrubs(vegetation);
        } else if(step == 1) { // Glade
            BiomeFeaturesME.addBeesOakTrees(vegetation);
            BiomeFeaturesME.addYellowFlowers(vegetation);
            BiomeFeaturesME.addSedumYellow(vegetation);
            BiomeFeaturesME.addMallornTrees(vegetation);
            BiomeFeaturesME.addMallornFloweringBushes(vegetation);
            BiomeFeaturesME.addYellowFlowerGrowth(vegetation);
            BiomeFeaturesME.addBigleafHydrangeas(vegetation);
        } else if(step == 2) { // Blossom
            BiomeFeaturesME.addBeesOakTrees(vegetation);
            BiomeFeaturesME.addSmallMallornTress(vegetation);
            BiomeFeaturesME.addMallornFloweringBushes(vegetation);
            BiomeFeaturesME.addOakTrees(vegetation);
            BiomeFeaturesME.addSparseBirchTrees(vegetation);
            BiomeFeaturesME.addCherryBlossomTrees(vegetation);
            BiomeFeaturesME.addPinkFlowers(vegetation);
            BiomeFeaturesME.addPinkFlowerGrowth(vegetation);
            BiomeFeaturesME.addYellowFlowerGrowth(vegetation);
            BiomeFeaturesME.addSedumYellow(vegetation);
            BiomeFeaturesME.addGreenShrub(vegetation);
            BiomeFeaturesME.addBigleafHydrangeas(vegetation);
            vegetation.add(VegetationPlacedFeatures.FLOWER_CHERRY);
        }

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createLossarnach(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, int step) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        SpawnSettingsBuilderME.addPlainsMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addGondorVegetation(generationSettings);
        BiomeFeaturesME.addWildBellPepper(vegetation);
        BiomeFeaturesME.addWheatGrass(vegetation);
        BiomeFeaturesME.addWildGrass(vegetation);
        BiomeFeaturesME.addTuftGrass(vegetation);
        BiomeFeaturesME.addBeesOakTrees(vegetation);
        if(step == 0) { // Plains
            SpawnSettingsBuilderME.addCats(spawnSettings);
            BiomeFeaturesME.addWildLettuce(vegetation);
            BiomeFeaturesME.addYellowFlowers(vegetation);
            BiomeFeaturesME.addOrangeFlowers(vegetation);
            BiomeFeaturesME.addRedFlowers(vegetation);
            BiomeFeaturesME.addWhiteFlowers(vegetation);
            BiomeFeaturesME.addSparseLavender(vegetation);
            BiomeFeaturesME.addLossarnachFlowers(vegetation);
            BiomeFeaturesME.addGravelOre(vegetation);
            BiomeFeaturesME.addDioriteBoulder(vegetation);
            vegetation.add(VegetationPlacedFeatures.TREES_PLAINS);
            BiomeFeaturesME.addOakBushes(vegetation);
            BiomeFeaturesME.addVeryRareBirchTrees(vegetation);
            BiomeFeaturesME.addRareBeechTrees(vegetation);
            BiomeFeaturesME.addRareOakTrees(vegetation);
            BiomeFeaturesME.addRareLebethronTrees(vegetation);
            BiomeFeaturesME.addWilderGrass(vegetation);
        } else if (step == 1) { // Flower valley
            BiomeFeaturesME.addWhiteFlowerGrowth(vegetation);
            BiomeFeaturesME.addYellowFlowers(vegetation);
            BiomeFeaturesME.addOrangeFlowers(vegetation);
            BiomeFeaturesME.addRedFlowers(vegetation);
            BiomeFeaturesME.addSparseLavender(vegetation);
            BiomeFeaturesME.addLossarnachFlowersCommon(vegetation);
            BiomeFeaturesME.addWildPotato(vegetation);
            BiomeFeaturesME.addRareBirchTrees(vegetation);
            BiomeFeaturesME.addRareOakTrees(vegetation);
            BiomeFeaturesME.addVeryRareLebethronTrees(vegetation);
            BiomeFeaturesME.addChestnutTrees(vegetation);
            BiomeFeaturesME.addWilderGrass(vegetation);
        } else if (step >= 2) { // Forest
            if (step == 2) {
                BiomeFeaturesME.addGreenShrub(vegetation);
                BiomeFeaturesME.addMapleTree(vegetation);
                BiomeFeaturesME.addWilderGrass(vegetation);
            } else if(step == 3) { // Yellow
                BiomeFeaturesME.addYellowMapleTree(vegetation);
                BiomeFeaturesME.addRareWilderGrass(vegetation);
            } else if (step == 4) { // Orange
                BiomeFeaturesME.addOrangeMapleTree(vegetation);
            } else if (step == 5) { // Red
                BiomeFeaturesME.addRedMapleTree(vegetation);
            }
            BiomeFeaturesME.addFallenLeaves(vegetation);
            BiomeFeaturesME.addSparseWheatGrass(vegetation);
            BiomeFeaturesME.addCoarseDirtOre(vegetation);
            BiomeFeaturesME.addFrequentOakTrees(vegetation);
            BiomeFeaturesME.addRareOakTrees(vegetation);
            BiomeFeaturesME.addOakTrees(vegetation);
            BiomeFeaturesME.addCommonOakTrees(vegetation);
            BiomeFeaturesME.addMegaOakTrees(vegetation);
            BiomeFeaturesME.addCommonOakBush(vegetation);
        }

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createLossarnachCherryBlossom(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        SpawnSettingsBuilderME.addFarmAnimals(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addGondorVegetation(generationSettings);
        BiomeFeaturesME.addOakBushes(vegetation);
        BiomeFeaturesME.addTallGrass(vegetation);
        BiomeFeaturesME.addWildGrass(vegetation);
        BiomeFeaturesME.addTuftGrass(vegetation);
        BiomeFeaturesME.addPinkFlowers(vegetation);
        BiomeFeaturesME.addPinkFlowerGrowth(vegetation);
        vegetation.add(VegetationPlacedFeatures.TREES_CHERRY);
        vegetation.add(VegetationPlacedFeatures.FLOWER_CHERRY);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createMinhiriathBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, int step) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        SpawnSettingsBuilderME.addEriadorMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        if(step == 0){
            addEriadorVegetation(generationSettings);
            BiomeFeaturesME.addGreenShrub(vegetation);
            BiomeFeaturesME.addAndesiteBoulder(vegetation);
            BiomeFeaturesME.addDryDirtOre(vegetation);
            BiomeFeaturesME.addGravelOre(vegetation);
            BiomeFeaturesME.addTuftGrass(vegetation);
            BiomeFeaturesME.addRareWilderGrass(vegetation);
            BiomeFeaturesME.addWheatGrass(vegetation);
            BiomeFeaturesME.addCommonOakBush(vegetation);
            BiomeFeaturesME.addWildCarrot(vegetation);
        } else if(step == 1){
            SpawnSettingsBuilderME.addRabbits(spawnSettings);

            BiomeFeaturesME.addWildWheatField(vegetation);
            BiomeFeaturesME.addMixedWildWheatPatch(vegetation);
            BiomeFeaturesME.addGrassyDirtOre(vegetation);
            BiomeFeaturesME.addDryDirtOre(vegetation);
            BiomeFeaturesME.addDryGrowth(vegetation);
        }

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createDolGuldurBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, int step) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        BiomeFeaturesME.addDisks(undergroundOres);
        BiomeFeaturesME.addGrass(vegetation);
        vegetation.add(VegetationPlacedFeatures.PATCH_TALL_GRASS_2);
        vegetation.add(VegetationPlacedFeatures.PATCH_LARGE_FERN);
        vegetation.add(VegetationPlacedFeatures.PATCH_GRASS_TAIGA);
        vegetation.add(VegetationPlacedFeatures.BROWN_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.RED_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.PATCH_BERRY_RARE);

        BiomeFeaturesME.addGabbroBoulder(vegetation);

        BiomeFeaturesME.addGrassyLoamOre(vegetation);
        BiomeFeaturesME.addCoarseLoamToGrassy(vegetation);
        BiomeFeaturesME.addCoarseLoamToFoulDirt(vegetation);

        BiomeFeaturesME.addMistweed(vegetation);
        BiomeFeaturesME.addCorruptedMoss(vegetation);
        BiomeFeaturesME.addBushes(vegetation);
        BiomeFeaturesME.addShriveledShrubs(vegetation);
        BiomeFeaturesME.addWildGrass(vegetation);
        BiomeFeaturesME.addSparseGrass(vegetation);
        BiomeFeaturesME.addTuftGrass(vegetation);
        BiomeFeaturesME.addWheatGrass(vegetation);

        SpawnSettingsBuilderME.addUncommonBats(spawnSettings);

        if(step == 0) {
            SpawnSettingsBuilderME.addDeer(spawnSettings);
            SpawnSettingsBuilderME.addUncommonWarg(spawnSettings);

            BiomeFeaturesME.addMirkwoodRoots(vegetation);
            BiomeFeaturesME.addRareMorsel(vegetation);
            BiomeFeaturesME.addRareWhiteMushroom(vegetation);
            BiomeFeaturesME.addNettles(vegetation);
            BiomeFeaturesME.addForestMoss(vegetation);
            BiomeFeaturesME.addForestBlockMoss(vegetation);
            BiomeFeaturesME.addBracken(vegetation);
            BiomeFeaturesME.addFalseOatgrass(vegetation);
            BiomeFeaturesME.addThornyGrowth(vegetation);

            BiomeFeaturesME.addWildFlax(vegetation);
            BiomeFeaturesME.addWildGarlic(vegetation);
            BiomeFeaturesME.addWildOnion(vegetation);

            BiomeFeaturesME.addRottenTrees(vegetation);
            BiomeFeaturesME.addDeadwoodTrees(vegetation);
            BiomeFeaturesME.addDeadMirkwoodTrees(vegetation);
            BiomeFeaturesME.addDeadMegaMirkwoodTrees(vegetation);
            BiomeFeaturesME.addScorchedTrees(vegetation);
            generationSettings.feature(GenerationStep.Feature.TOP_LAYER_MODIFICATION, VegetationPlacedFeatureRegistryME.MIRKWOOD_VINES);
            BiomeFeaturesME.addMirkwoodVines(vegetation);
            vegetation.add(VegetationPlacedFeatures.PATCH_LEAF_LITTER);
        }else if (step == 1){ //HILL
            BiomeFeaturesME.addThistle(vegetation);
            BiomeFeaturesME.addScorchedGrass(vegetation);
            BiomeFeaturesME.addScorchedShrub(vegetation);
        }

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createMirkwoodBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, boolean bigTrees, boolean dark, boolean hasWarg) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        SpawnSettingsBuilderME.addWolves(spawnSettings);

        if(dark) {
            SpawnSettingsBuilderME.addMirkwoodSpider(spawnSettings);
            SpawnSettingsBuilderME.addUncommonBats(spawnSettings);
        } else{
            SpawnSettingsBuilderME.addRareMirkwoodSpider(spawnSettings);
            SpawnSettingsBuilderME.addDeer(spawnSettings);
        }

        if(hasWarg){
            SpawnSettingsBuilderME.addUncommonWarg(spawnSettings);
        } else {
            SpawnSettingsBuilderME.addGreatHorn(spawnSettings);
        }

        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addMirkwoodVegetation(generationSettings);
        BiomeFeaturesME.addMirkwoodRoots(vegetation);
        BiomeFeaturesME.addDeadHeather(vegetation);
        BiomeFeaturesME.addMudOre(vegetation);
        BiomeFeaturesME.addRareMorsel(vegetation);
        BiomeFeaturesME.addRareWhiteMushroom(vegetation);
        BiomeFeaturesME.addThornyGrowth(vegetation);

        if(bigTrees) {
            BiomeFeaturesME.addMistweed(vegetation);
            BiomeFeaturesME.addFireflyBushes(vegetation);
            BiomeFeaturesME.addWaterDelta(vegetation);
            addMegaMirkwoodTrees(generationSettings);
            BiomeFeaturesME.addCommonBeechTrees(vegetation);
            BiomeFeaturesME.addSmallMirkwoodTrees(vegetation);
            BiomeFeaturesME.addRottenTrees(vegetation);
            BiomeFeaturesME.addCorruptedMoss(vegetation);
        } else {
            addMirkwoodTrees(generationSettings);
            BiomeFeaturesME.addBeechTrees(vegetation);
            BiomeFeaturesME.addRareMegaMirkwoodTrees(vegetation);
            if(!dark) {
                BiomeFeaturesME.addNettles(vegetation);
                BiomeFeaturesME.addForestMoss(vegetation);
                BiomeFeaturesME.addForestBlockMoss(vegetation);
            }
        }

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createMirkwoodMountainsBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, int step) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        float temperature = 0.3f;

        if(step == -1) {
            SpawnSettingsBuilderME.addWolves(spawnSettings);
            SpawnSettingsBuilderME.addDeer(spawnSettings);
            SpawnSettingsBuilderME.addGreatHorn(spawnSettings);
            addMirkwoodVegetation(generationSettings);
            BiomeFeaturesME.addMirkwoodRoots(vegetation);
            BiomeFeaturesME.addDeadHeather(vegetation);
            BiomeFeaturesME.addMudOre(vegetation);
            BiomeFeaturesME.addRareMorsel(vegetation);
            BiomeFeaturesME.addRareWhiteMushroom(vegetation);
            BiomeFeaturesME.addFireflyBushes(vegetation);
            BiomeFeaturesME.addRareMegaMirkwoodTrees(vegetation);
            BiomeFeaturesME.addAbundantFirTrees(vegetation);
            BiomeFeaturesME.addFirTrees(vegetation);
            BiomeFeaturesME.addBeechTrees(vegetation);
            BiomeFeaturesME.addSmallMirkwoodTrees(vegetation);
            BiomeFeaturesME.addForestMoss(vegetation);
        } else if(step == 0) {
            SpawnSettingsBuilderME.addWolves(spawnSettings);
            addMirkwoodVegetation(generationSettings);
            BiomeFeaturesME.addMirkwoodRoots(vegetation);
            BiomeFeaturesME.addCommonFirTrees(vegetation);
            BiomeFeaturesME.addFirTrees(vegetation);
            BiomeFeaturesME.addBeechTrees(vegetation);
            BiomeFeaturesME.addMudOre(vegetation);
            BiomeFeaturesME.addRareMorsel(vegetation);
            BiomeFeaturesME.addRareWhiteMushroom(vegetation);
            BiomeFeaturesME.addDeadHeather(vegetation);
            BiomeFeaturesME.addSmallMirkwoodTrees(vegetation);
            BiomeFeaturesME.addCorruptedMoss(vegetation);
        } else if (step == 1) {
            addMirkwoodVegetation(generationSettings);
            BiomeFeaturesME.addMirkwoodRoots(vegetation);
            BiomeFeaturesME.addMudOre(vegetation);
            BiomeFeaturesME.addBracken(vegetation);
            BiomeFeaturesME.addCommonOakBush(vegetation);
        } else if (step == 2) {
            BiomeFeaturesME.addSnowOre(vegetation);
            BiomeFeaturesME.addStickySnow(vegetation);
            BiomeFeaturesME.addFrozenGrass(vegetation);
            BiomeFeaturesME.addFrozenShrub(vegetation);
            temperature = 0.0f;
            BiomeFeaturesME.addGraniteOre(vegetation);
            BiomeFeaturesME.addDripstoneOre(vegetation);
            BiomeFeaturesME.addTuffOre(vegetation);
        }

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings, temperature, true);
    }

    public static void createMirkwoodMountainsBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, boolean foothill) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();

        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));
        BiomeFeaturesME.addGraniteBoulder(vegetation);
        float temperature = 0.3f;
        if(foothill) {
            addMirkwoodVegetation(generationSettings);
            BiomeFeaturesME.addSmallMirkwoodTrees(vegetation);
            //ModBiomeFeatures.addGrassStoneOre(vegetation);
            BiomeFeaturesME.addGravelOre(vegetation);
            BiomeFeaturesME.addForestMoss(vegetation);
            BiomeFeaturesME.addNettles(vegetation);
            BiomeFeaturesME.addMirkwoodRoots(vegetation);
            BiomeFeaturesME.addMudOre(vegetation);
            BiomeFeaturesME.addRareMorsel(vegetation);
            BiomeFeaturesME.addRareWhiteMushroom(vegetation);
        } else {
            temperature = 0.0f;
            BiomeFeaturesME.addGraniteOre(vegetation);
            BiomeFeaturesME.addDripstoneOre(vegetation);
            BiomeFeaturesME.addTuffOre(vegetation);
        }

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings, temperature, true);
    }

    public static void createWebbedMirkwoodBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, boolean dark) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        SpawnSettingsBuilderME.addWolves(spawnSettings);
        SpawnSettingsBuilderME.addMirkwoodSpider(spawnSettings);
        SpawnSettingsBuilderME.addSwampMobs(spawnSettings);

        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addMirkwoodVegetation(generationSettings);
        BiomeFeaturesME.addMirkwoodRoots(vegetation);
        BiomeFeaturesME.addDeadHeather(vegetation);
        BiomeFeaturesME.addMudOre(vegetation);
        BiomeFeaturesME.addRareMorsel(vegetation);
        BiomeFeaturesME.addRareWhiteMushroom(vegetation);
        BiomeFeaturesME.addCobwebs(vegetation);
        BiomeFeaturesME.addSpiderEggs(vegetation);

        addMegaMirkwoodTrees(generationSettings);
        BiomeFeaturesME.addSmallMirkwoodTrees(vegetation);
        BiomeFeaturesME.addRottenTrees(vegetation);
        BiomeFeaturesME.addCorruptedMoss(vegetation);
        BiomeFeaturesME.addDeadMegaMirkwoodTrees(vegetation);
        BiomeFeaturesME.addDeadwoodTrees(vegetation);
        if(!dark) {
            BiomeFeaturesME.addForestMoss(vegetation);
            BiomeFeaturesME.addForestBlockMoss(vegetation);
        }

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createMirkwoodSwampBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, int step) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        SpawnSettingsBuilderME.addSwampMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addMirkwoodVegetation(generationSettings);
        BiomeFeaturesME.addWaterDelta(vegetation);
        BiomeFeaturesME.addMireOre(vegetation);
        BiomeFeaturesME.addMudOre(vegetation);
        BiomeFeaturesME.addAbundantMudOre(vegetation);

        if(step == 0 || step == 1) { // Marshes & Swamp
            BiomeFeaturesME.addMistweed(vegetation);
            BiomeFeaturesME.addMossyBoulder(vegetation);
            BiomeFeaturesME.addDeadRushes(vegetation);
            BiomeFeaturesME.addForestMoss(vegetation);
            BiomeFeaturesME.addReedsFoliage(vegetation);
            BiomeFeaturesME.addWheatGrass(vegetation);
            BiomeFeaturesME.addBlueOrchidFlower(vegetation);
            BiomeFeaturesME.addCommonTallGrass(vegetation);
            BiomeFeaturesME.addBulrushAndCattail(vegetation);
            BiomeFeaturesME.addDuckweed(vegetation);
            BiomeFeaturesME.addLilyPads(vegetation);
            BiomeFeaturesME.addSmallLilyPads(vegetation);
            vegetation.add(VegetationPlacedFeatures.PATCH_WATERLILY);
            BiomeFeaturesME.addWillowTrees(vegetation);
            addMirkwoodTrees(generationSettings);
            vegetation.add(OceanPlacedFeatures.SEAGRASS_NORMAL);
        } else if(step == 2) { // River
            vegetation.add(OceanPlacedFeatures.SEAGRASS_NORMAL);
            vegetation.add(OceanPlacedFeatures.KELP_COLD);
            SpawnSettingsBuilderME.addRiverAnimals(spawnSettings);

        } else if(step == 3) { // Long marshes
            vegetation.add(BoulderPlacedFeatureRegistryME.SMALL_BOULDER_MOSSY_STONE);
            vegetation.add(BoulderPlacedFeatureRegistryME.MEDIUM_BOULDER_MOSSY_STONE);
            BiomeFeaturesME.addMistweed(vegetation);
            BiomeFeaturesME.addDeadRushes(vegetation);
            BiomeFeaturesME.addReedsFoliage(vegetation);
            BiomeFeaturesME.addWheatGrass(vegetation);
            BiomeFeaturesME.addBlueOrchidFlower(vegetation);
            BiomeFeaturesME.addCommonTallGrass(vegetation);
            BiomeFeaturesME.addBulrushAndCattail(vegetation);
            BiomeFeaturesME.addCommonOakBush(vegetation);
        }

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createMistyMountainsBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, int step) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        float temperature = 0.35f;

        BiomeFeaturesME.addDisks(undergroundOres);
        vegetation.add(VegetationPlacedFeatures.PATCH_LARGE_FERN);
        vegetation.add(VegetationPlacedFeatures.PATCH_GRASS_TAIGA);
        BiomeFeaturesME.addBrownBolete(vegetation);
        BiomeFeaturesME.addMorsel(vegetation);
        BiomeFeaturesME.addWhiteMushroom(vegetation);

        if(step == 0) {
            vegetation.add(VegetationPlacedFeatures.FLOWER_DEFAULT);
            vegetation.add(VegetationPlacedFeatures.BROWN_MUSHROOM_NORMAL);
            vegetation.add(VegetationPlacedFeatures.RED_MUSHROOM_NORMAL);
            vegetation.add(VegetationPlacedFeatures.PATCH_BERRY_RARE);
            SpawnSettingsBuilderME.addRareWarg(spawnSettings);
            SpawnSettingsBuilderME.addMountainsMobs(spawnSettings);
            BiomeFeaturesME.addBracken(vegetation);
            BiomeFeaturesME.addCampion(vegetation);
            BiomeFeaturesME.addWildGrass(vegetation);
            BiomeFeaturesME.addGrass(vegetation);
            BiomeFeaturesME.addWildBeetroot(vegetation);
            BiomeFeaturesME.addWildPotato(vegetation);
            BiomeFeaturesME.addGravelOre(vegetation);
            BiomeFeaturesME.addLarchTrees(vegetation);
            BiomeFeaturesME.addRareFirTrees(vegetation);
            BiomeFeaturesME.addPineTrees(vegetation);
            BiomeFeaturesME.addSpruceTrees(vegetation);
            BiomeFeaturesME.addCommonSpruceBushes(vegetation);
        } else if (step == 1){
            BiomeFeaturesME.addPowderSnowOre(vegetation);
            BiomeFeaturesME.addStickySnow(vegetation);
            BiomeFeaturesME.addFrozenGrass(vegetation);
            BiomeFeaturesME.addFrozenShrub(vegetation);
            temperature = -0.3f;
        } else if (step == 2){
            SpawnSettingsBuilderME.addRareWolves(spawnSettings);
            BiomeFeaturesME.addPowderSnowOre(vegetation);
            BiomeFeaturesME.addFrozenGrass(vegetation);
            temperature = -0.6f;
        }

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings,temperature, true);
    }

    public static void createMordorBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));
        SpawnSettingsBuilderME.addUncommonWarg(spawnSettings);
        SpawnSettingsBuilderME.addCommonWolves(spawnSettings);
        SpawnSettingsBuilderME.addUncommonBats(spawnSettings);

        addMordorVegetation(generationSettings);
        BiomeFeaturesME.addPumicePileRare(vegetation);
        BiomeFeaturesME.addAshenStoneBoulder(vegetation);
        BiomeFeaturesME.addPumiceColumnRare(vegetation);
        BiomeFeaturesME.addAshenStoneDirtOre(vegetation);
        BiomeFeaturesME.addAshenStoneGravelOre(vegetation);
        BiomeFeaturesME.addAshenStoneSandOre(vegetation);
        BiomeFeaturesME.addLavaMagmaLake(generationSettings);
        BiomeFeaturesME.addBasaltPile(vegetation);
        BiomeFeaturesME.addBlackStonePile(vegetation);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings, 0.7f, false, true);
    }

    public static void createMordorAshenForestBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));
        SpawnSettingsBuilderME.addRareWarg(spawnSettings);
        SpawnSettingsBuilderME.addRareWolves(spawnSettings);
        SpawnSettingsBuilderME.addUncommonBats(spawnSettings);

        addMordorVegetation(generationSettings);
        BiomeFeaturesME.addTuftGrass(vegetation);
        BiomeFeaturesME.addAshenStoneBoulder(vegetation);
        BiomeFeaturesME.addBasaltPile(vegetation);
        BiomeFeaturesME.addAshenStoneDirtOre(vegetation);
        BiomeFeaturesME.addAshenStoneDirtCommonOre(vegetation);
        BiomeFeaturesME.addAshenStoneGravelOre(vegetation);
        BiomeFeaturesME.addAshenStoneSandOre(vegetation);
        BiomeFeaturesME.addDeadRushes(vegetation);
        BiomeFeaturesME.addStoneOldPodzolOre(vegetation);
        BiomeFeaturesME.addPumiceColumnRare(vegetation);
        BiomeFeaturesME.addShriveledShrubs(vegetation);
        BiomeFeaturesME.addBlackStonePile(vegetation);
        BiomeFeaturesME.addAbundantScorchedTrees(vegetation);
        BiomeFeaturesME.addAbundantDeadBlackPineTrees(vegetation);
        BiomeFeaturesME.addRottenTrees(vegetation);
        BiomeFeaturesME.addDryPineBushes(vegetation);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings, 0.7f, false, true);
    }

    public static void createMordorHillBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));
        SpawnSettingsBuilderME.addRareWarg(spawnSettings);
        SpawnSettingsBuilderME.addWolves(spawnSettings);
        SpawnSettingsBuilderME.addUncommonBats(spawnSettings);

        addMordorVegetation(generationSettings);
        BiomeFeaturesME.addPumicePileSparse(vegetation);
        BiomeFeaturesME.addAshenStoneBoulder(vegetation);
        BiomeFeaturesME.addPumiceColumn(vegetation);
        BiomeFeaturesME.addPumiceColumnLarge(vegetation);
        BiomeFeaturesME.addAshenStoneDirtOre(vegetation);
        BiomeFeaturesME.addAshenStoneGravelOre(vegetation);
        BiomeFeaturesME.addAshenStoneSandOre(vegetation);
        BiomeFeaturesME.addLavaMagmaLake(generationSettings);
        BiomeFeaturesME.addBasaltPile(vegetation);
        BiomeFeaturesME.addBlackStonePile(vegetation);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings, 0.7f, false, true);
    }

    public static void createGorgorothBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, int step) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));
        SpawnSettingsBuilderME.addRareWarg(spawnSettings);
        SpawnSettingsBuilderME.addWolves(spawnSettings);
        SpawnSettingsBuilderME.addUncommonBats(spawnSettings);

        BiomeFeaturesME.addAshBlockOre(vegetation);
        BiomeFeaturesME.addPumicePileSparse(vegetation);
        BiomeFeaturesME.addPumiceColumn(vegetation);
        BiomeFeaturesME.addPumiceColumnLarge(vegetation);
        BiomeFeaturesME.addAshenStoneBoulder(vegetation);
        BiomeFeaturesME.addAshenStoneDirtOre(vegetation);
        BiomeFeaturesME.addAshenStoneGravelOre(vegetation);
        BiomeFeaturesME.addAshenStoneSandOre(vegetation);
        BiomeFeaturesME.addBlackSand(vegetation);
        BiomeFeaturesME.addBasaltPile(vegetation);
        BiomeFeaturesME.addBlackStonePile(vegetation);
        BiomeFeaturesME.addMordorBrambles(vegetation);

        if(step == 0) { // Plateau
            BiomeFeaturesME.addGrimGrass(vegetation);
            BiomeFeaturesME.addCommonScorchedGrass(vegetation);
            BiomeFeaturesME.addCommonToughBerries(vegetation);
            BiomeFeaturesME.addLavaMagmaLake(generationSettings);
        } else if(step == 1) { // Ashen Forest
            BiomeFeaturesME.addGrimGrass(vegetation);
            BiomeFeaturesME.addCommonScorchedGrass(vegetation);
            BiomeFeaturesME.addCommonScorchedShrub(vegetation);
            BiomeFeaturesME.addToughBerries(vegetation);
            BiomeFeaturesME.addCommonToughBerries(vegetation);
            BiomeFeaturesME.addAshenStoneDirtCommonOre(vegetation);
            BiomeFeaturesME.addDeadRushes(vegetation);
            BiomeFeaturesME.addStoneOldPodzolOre(vegetation);
            BiomeFeaturesME.addPumiceColumnRare(vegetation);
            BiomeFeaturesME.addShriveledShrubs(vegetation);
            BiomeFeaturesME.addAbundantScorchedTrees(vegetation);
            BiomeFeaturesME.addAbundantDeadBlackPineTrees(vegetation);
            BiomeFeaturesME.addDryPineBushes(vegetation);
        } else if(step == 2) { // Delta
            vegetation.add(NetherPlacedFeatures.DELTA);
            surfaceStructures.add(MiscPlacedFeatureRegistryME.SMALL_BASALT_COLUMNS);
            surfaceStructures.add(MiscPlacedFeatureRegistryME.SMALL_PUMICE_COLUMNS);
            surfaceStructures.add(MiscPlacedFeatureRegistryME.LARGE_PUMICE_COLUMNS);
            BiomeFeaturesME.addLavaMagmaLake(generationSettings);
            BiomeFeaturesME.addBasaltBoulder(vegetation);
            BiomeFeaturesME.addSmoothBasaltOre(vegetation);
            BiomeFeaturesME.addBasaltPileRare(vegetation);
            BiomeFeaturesME.addPumicePileRare(vegetation);
        }

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings, 0.7f, false, true);
    }

    public static void createMordorMountainsBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, int step) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        if (step == 0) {
            BiomeFeaturesME.addShriveledShrubs(vegetation);
            BiomeFeaturesME.addCommonToughBerries(vegetation);
            BiomeFeaturesME.addScorchedGrass(vegetation);
            BiomeFeaturesME.addScorchedShrub(vegetation);
            SpawnSettingsBuilderME.addUncommonWarg(spawnSettings);
            SpawnSettingsBuilderME.addUncommonBats(spawnSettings);
        } else if(step == 1) {
            SpawnSettingsBuilderME.addRareWarg(spawnSettings);
            SpawnSettingsBuilderME.addUncommonBats(spawnSettings);

            BiomeFeaturesME.addScorchedGrass(vegetation);
            BiomeFeaturesME.addShriveledShrubs(vegetation);
            BiomeFeaturesME.addToughBerries(vegetation);
        } else if(step == 2) {
            BiomeFeaturesME.addBasaltPileRare(vegetation);
        } else if(step == 3) {
            vegetation.add(NetherPlacedFeatures.DELTA);
        }

        BiomeFeaturesME.addAshBlockOre(vegetation);
        BiomeFeaturesME.addPumiceColumn(vegetation);
        BiomeFeaturesME.addPumicePileSparse(vegetation);
        BiomeFeaturesME.addAshenGravelDirtOre(vegetation);
        BiomeFeaturesME.addAshenGravelSandOre(vegetation);
        BiomeFeaturesME.addAshenStoneGravelOre(vegetation);
        BiomeFeaturesME.addAshenStoneSandOre(vegetation);
        BiomeFeaturesME.addAshenStoneDirtOre(vegetation);
        BiomeFeaturesME.addBasaltPile(vegetation);
        BiomeFeaturesME.addBlackStonePile(vegetation);


        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings, 0.7f, false, true);
    }

    public static void createMordorWastesBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));
        SpawnSettingsBuilderME.addSwampMobs(spawnSettings);

        addMordorVegetation(generationSettings);
        BiomeFeaturesME.addWaterDelta(vegetation);
        BiomeFeaturesME.addDeadRushes(vegetation);
        BiomeFeaturesME.addGravelOre(vegetation);
        BiomeFeaturesME.addMireOre(vegetation);
        BiomeFeaturesME.addMudOre(vegetation);
        BiomeFeaturesME.addAshenDirtOre(vegetation);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createMorgulVale(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        SpawnSettingsBuilderME.addRareWolves(spawnSettings);
        
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));
        SpawnSettingsBuilderME.addRareWarg(spawnSettings);
        SpawnSettingsBuilderME.addRareWolves(spawnSettings);

        BiomeFeaturesME.addGrass(vegetation);
        BiomeFeaturesME.addTuftGrass(vegetation);
        BiomeFeaturesME.addWheatGrass(vegetation);
        BiomeFeaturesME.addAshenGravelOre(vegetation);
        BiomeFeaturesME.addAshenSandOre(vegetation);
        BiomeFeaturesME.addAshBlockOre(vegetation);
        BiomeFeaturesME.addDeadRushes(vegetation);
        BiomeFeaturesME.addTurfOre(vegetation);
        BiomeFeaturesME.addWildGrass(vegetation);
        BiomeFeaturesME.addThornyGrowth(vegetation);

        BiomeFeaturesME.addPumicePileRare(vegetation);
        BiomeFeaturesME.addAshenStoneBoulder(vegetation);
        BiomeFeaturesME.addRareMorsel(vegetation);
        BiomeFeaturesME.addRareWhiteMushroom(vegetation);
        BiomeFeaturesME.addBasaltPile(vegetation);
        BiomeFeaturesME.addCoarseLoamOre(vegetation);
        BiomeFeaturesME.addCoarseLoamToFoulDirt(vegetation);
        BiomeFeaturesME.addDeadBlackPineTrees(vegetation);

        BiomeFeaturesME.addCommonToughBerries(undergroundOres);
        BiomeFeaturesME.addAshenDirtOre(undergroundOres);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }
    public static void createMorgulForest(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        SpawnSettingsBuilderME.addWolves(spawnSettings);
        
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        BiomeFeaturesME.addGrass(vegetation);
        BiomeFeaturesME.addTuftGrass(vegetation);
        BiomeFeaturesME.addWheatGrass(vegetation);
        BiomeFeaturesME.addNettles(vegetation);
        BiomeFeaturesME.addAshenGravelOre(vegetation);
        BiomeFeaturesME.addAshBlockOre(vegetation);
        BiomeFeaturesME.addShriveledShrubs(vegetation);
        BiomeFeaturesME.addDeadRushes(vegetation);
        BiomeFeaturesME.addTurfOre(vegetation);
        BiomeFeaturesME.addWildGrass(vegetation);
        BiomeFeaturesME.addThornyGrowth(vegetation);

        BiomeFeaturesME.addPumicePileRare(vegetation);
        BiomeFeaturesME.addAshenStoneBoulder(vegetation);
        BiomeFeaturesME.addRareMorsel(vegetation);
        BiomeFeaturesME.addRareWhiteMushroom(vegetation);
        BiomeFeaturesME.addBasaltPile(vegetation);
        BiomeFeaturesME.addCoarseLoamOre(vegetation);
        BiomeFeaturesME.addOldPodzolOre(vegetation);
        BiomeFeaturesME.addOakTrees(vegetation);
        BiomeFeaturesME.addSpruceBushes(vegetation);
        BiomeFeaturesME.addDarkOakTrees(vegetation);
        BiomeFeaturesME.addSpruceTrees(vegetation);
        BiomeFeaturesME.addSparsePineTrees(vegetation);
        BiomeFeaturesME.addCommonBlackPineTrees(vegetation);
        BiomeFeaturesME.addDeadBlackPineTrees(vegetation);
        BiomeFeaturesME.addRareLebethronTrees(vegetation);
        BiomeFeaturesME.addRottenTrees(vegetation);
        BiomeFeaturesME.addDeadwoodTrees(vegetation);

        BiomeFeaturesME.addCommonToughBerries(undergroundOres);
        BiomeFeaturesME.addAshenDirtOre(undergroundOres);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createNanCurunirBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        SpawnSettingsBuilderME.addFarmAnimals(spawnSettings);
        SpawnSettingsBuilderME.addRareWarg(spawnSettings);

        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        BiomeFeaturesME.addRareWilderGrass(vegetation);
        BiomeFeaturesME.addOakBushes(vegetation);
        addDefaultVegetation(generationSettings);

        BiomeFeaturesME.addOakTrees(vegetation);
        BiomeFeaturesME.addRareBeechTrees(vegetation);
        BiomeFeaturesME.addBirchTrees(vegetation);
        BiomeFeaturesME.addPineTrees(vegetation);
        BiomeFeaturesME.addSpruceTrees(vegetation);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createNorthDownsBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        SpawnSettingsBuilderME.addMountainsMobs(spawnSettings);
        SpawnSettingsBuilderME.addNordicMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        BiomeFeaturesME.addRareWilderGrass(vegetation);
        BiomeFeaturesME.addAndesiteBoulder(vegetation);
        BiomeFeaturesME.addGrassToStoneOre(vegetation);
        BiomeFeaturesME.addAbundantTuffOre(vegetation);
        addEriadorVegetation(generationSettings);

        BiomeFeaturesME.addLarchTrees(vegetation);
        BiomeFeaturesME.addPineTrees(vegetation);
        BiomeFeaturesME.addSpruceTrees(vegetation);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createDunlandBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, int step) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        SpawnSettingsBuilderME.addNordicMobs(spawnSettings);
        SpawnSettingsBuilderME.addWolves(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addNordicVegetation(generationSettings);
        BiomeFeaturesME.addGravelOre(vegetation);
        BiomeFeaturesME.addCoarseLoamOre(vegetation);
        BiomeFeaturesME.addGrassyLoamOre(vegetation);
        BiomeFeaturesME.addTurfOre(vegetation);

        BiomeFeaturesME.addGabbroBoulder(vegetation);
        BiomeFeaturesME.addDolomiteBoulder(vegetation);

        BiomeFeaturesME.addBushes(vegetation);
        BiomeFeaturesME.addSmallDryShrub(vegetation);
        BiomeFeaturesME.addGreenShrub(vegetation);
        BiomeFeaturesME.addShriveledShrubs(vegetation);
        vegetation.add(VegetationPlacedFeatures.PATCH_DEAD_BUSH);

        BiomeFeaturesME.addOakBushes(vegetation);
        BiomeFeaturesME.addSpruceBushes(vegetation);

        if(step == 0) { //Forest
            SpawnSettingsBuilderME.addFarmAnimals(spawnSettings);
            SpawnSettingsBuilderME.addDeer(spawnSettings);
            BiomeFeaturesME.addNettles(vegetation);
            BiomeFeaturesME.addRootedDirtOre(vegetation);
            BiomeFeaturesME.addFalseOatgrass(vegetation);
            BiomeFeaturesME.addGiantButterbur(vegetation);
            BiomeFeaturesME.addForestMoss(vegetation);
            BiomeFeaturesME.addCommonBeechTrees(vegetation);
            BiomeFeaturesME.addLarchTrees(vegetation);
            BiomeFeaturesME.addRareMegaOakTrees(vegetation);
            BiomeFeaturesME.addCommonOakTrees(vegetation);
            BiomeFeaturesME.addCommonPineTrees(vegetation);
            BiomeFeaturesME.addCommonFirTrees(vegetation);
            BiomeFeaturesME.addBlackPineTrees(vegetation);
            BiomeFeaturesME.addSpruceTrees(vegetation);
            BiomeFeaturesME.addRareSpruceTrees(vegetation);
            vegetation.add(VegetationPlacedFeatures.PATCH_LEAF_LITTER);
        } else if (step == 1) { // Glade
            SpawnSettingsBuilderME.addFarmAnimals(spawnSettings);
            SpawnSettingsBuilderME.addDeer(spawnSettings);
            BiomeFeaturesME.addFalseOatgrass(vegetation);
            BiomeFeaturesME.addIvyGrowth(vegetation);
            BiomeFeaturesME.addTuftGrass(vegetation);
            BiomeFeaturesME.addWheatGrass(vegetation);
            BiomeFeaturesME.addSedumYellow(vegetation);
            BiomeFeaturesME.addYellowFlowers(vegetation);
            BiomeFeaturesME.addLilacFlower(vegetation);
            BiomeFeaturesME.addFlowerGreenJewel(vegetation);
            BiomeFeaturesME.addSparsePineTrees(vegetation);
            vegetation.add(VegetationPlacedFeatures.TREES_PLAINS);
        } else if (step == 2){ //Druwaith Iaur :gnuh:
            BiomeFeaturesME.addLarchTrees(vegetation);
            BiomeFeaturesME.addSparsePineTrees(vegetation);
            BiomeFeaturesME.addRareSpruceTrees(vegetation);
            BiomeFeaturesME.addFlowerGreenJewel(vegetation);
            BiomeFeaturesME.addWheatGrass(vegetation);
        }
        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createNorthernWastelands(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        SpawnSettingsBuilderME.addMountainsMobs(spawnSettings);
        SpawnSettingsBuilderME.addWolves(spawnSettings);
        SpawnSettingsBuilderME.addNordicMobs(spawnSettings);
        SpawnSettingsBuilderME.addGreatHorn(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        vegetation.add(VegetationPlacedFeatures.PATCH_GRASS_TAIGA);
        vegetation.add(VegetationPlacedFeatures.BROWN_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.RED_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.PATCH_BERRY_RARE);
        BiomeFeaturesME.addCoarseDirtOre(vegetation);
        BiomeFeaturesME.addGravelOre(vegetation);
        BiomeFeaturesME.addAndesiteBoulder(vegetation);
        BiomeFeaturesME.addBlueTuffBoulder(vegetation);

        BiomeFeaturesME.addFrozenGrass(vegetation);
        BiomeFeaturesME.addFrozenShrub(vegetation);
        BiomeFeaturesME.addFrozenGrowth(vegetation);

        BiomeFeaturesME.addSparseLarchTrees(vegetation);
        BiomeFeaturesME.addSparsePineTrees(vegetation);
        BiomeFeaturesME.addRareSpruceTrees(vegetation);
        BiomeFeaturesME.addSpruceBushes(vegetation);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings, -0.1f, true);
    }

    public static void createNurnEdgeBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, int step) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        SpawnSettingsBuilderME.addPlainsMobs(spawnSettings);
        SpawnSettingsBuilderME.addRareWarg(spawnSettings);
        SpawnSettingsBuilderME.addRabbits(spawnSettings);
        SpawnSettingsBuilderME.addWolves(spawnSettings);

        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        BiomeFeaturesME.addDisks(undergroundOres);
        BiomeFeaturesME.addGrass(vegetation);
        BiomeFeaturesME.addGrassyDirtOre(vegetation);
        vegetation.add(VegetationPlacedFeatures.FLOWER_DEFAULT);
        vegetation.add(VegetationPlacedFeatures.BROWN_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.RED_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.PATCH_SUGAR_CANE);
        vegetation.add(VegetationPlacedFeatures.PATCH_PUMPKIN);
        BiomeFeaturesME.addAshenStoneBoulder(vegetation);
        BiomeFeaturesME.addBasaltBoulder(vegetation);
        BiomeFeaturesME.addDeadRushes(vegetation);
        BiomeFeaturesME.addDeadHeather(vegetation);
        BiomeFeaturesME.addWildLeek(vegetation);
        BiomeFeaturesME.addWildFlax(vegetation);
        BiomeFeaturesME.addToughBerries(vegetation);
        BiomeFeaturesME.addAshenDirtOre(vegetation);
        BiomeFeaturesME.addAshenGravelOre(vegetation);
        BiomeFeaturesME.addDyingGrass(vegetation);
        BiomeFeaturesME.addShriveledShrubs(vegetation);
        vegetation.add(VegetationPlacedFeatures.TREES_PLAINS);

        if(step == 0) { // Plains
            BiomeFeaturesME.addVeryRareBeechTrees(vegetation);
            BiomeFeaturesME.addCommonWheatGrass(vegetation);
            BiomeFeaturesME.addWilderGrass(vegetation);
        } else if (step == 1) { // Woods
            BiomeFeaturesME.addFalseOatgrass(vegetation);
            BiomeFeaturesME.addNettles(vegetation);
            BiomeFeaturesME.addForestMoss(vegetation);
            BiomeFeaturesME.addForestBlockMoss(vegetation);
            BiomeFeaturesME.addOldPodzolOre(vegetation);
            BiomeFeaturesME.addRareBirchTrees(vegetation);
            BiomeFeaturesME.addScarceBlackPineTrees(vegetation);
            BiomeFeaturesME.addScorchedTrees(vegetation);
            BiomeFeaturesME.addDryPineTrees(vegetation);
            BiomeFeaturesME.addDarkOakTrees(vegetation);
            BiomeFeaturesME.addRareBeechTrees(vegetation);
            BiomeFeaturesME.addRottenTrees(vegetation);
        }

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createNurnBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, int step) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        SpawnSettingsBuilderME.addPlainsMobs(spawnSettings);
        SpawnSettingsBuilderME.addRareWarg(spawnSettings);
        SpawnSettingsBuilderME.addRabbits(spawnSettings);
        SpawnSettingsBuilderME.addWolves(spawnSettings);

        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addDefaultVegetation(generationSettings);
        BiomeFeaturesME.addToughBerries(vegetation);
        BiomeFeaturesME.addThistle(vegetation);
        BiomeFeaturesME.addSparseGrass(vegetation);
        BiomeFeaturesME.addGravelOre(vegetation);
        BiomeFeaturesME.addAshenDirtOre(vegetation);
        BiomeFeaturesME.addDryDirtOre(vegetation);
        BiomeFeaturesME.addDyingGrass(vegetation);
        BiomeFeaturesME.addRareWilderGrass(vegetation);

        vegetation.add(VegetationPlacedFeatures.TREES_PLAINS);
        BiomeFeaturesME.addVeryRareBeechTrees(vegetation);

        if(step == 0) { // Plains
            BiomeFeaturesME.addCommonWheatGrass(vegetation);
            BiomeFeaturesME.addWilderGrass(vegetation);
            BiomeFeaturesME.addTallGrass(vegetation);
        } else if(step == 1) { // Forest
            BiomeFeaturesME.addDeadRushes(vegetation);
            BiomeFeaturesME.addNettles(vegetation);
            BiomeFeaturesME.addFalseOatgrass(vegetation);
            BiomeFeaturesME.addForestMoss(vegetation);
            BiomeFeaturesME.addForestBlockMoss(vegetation);
            BiomeFeaturesME.addOldPodzolOre(vegetation);
            BiomeFeaturesME.addRareBirchTrees(vegetation);
            BiomeFeaturesME.addBlackPineTrees(vegetation);
            BiomeFeaturesME.addDarkOakTrees(vegetation);
            BiomeFeaturesME.addVeryRareMegaOakTrees(vegetation);
            BiomeFeaturesME.addRareBeechTrees(vegetation);
            BiomeFeaturesME.addRottenTrees(vegetation);
            BiomeFeaturesME.addWildOnion(vegetation);
        } else if(step == 2) {
            BiomeFeaturesME.addDeadRushes(vegetation);
            BiomeFeaturesME.addDeadPineTrees(vegetation);
            BiomeFeaturesME.addPineTrees(vegetation);
            BiomeFeaturesME.addBlackPineTrees(vegetation);
            BiomeFeaturesME.addScarceSpruceTrees(vegetation);
            BiomeFeaturesME.addSparseLarchTrees(vegetation);
            BiomeFeaturesME.addBasaltBoulder(vegetation);
            BiomeFeaturesME.addAshBlockOre(vegetation);
        }

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createNurnWaterBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        SpawnSettingsBuilderME.addSwampMobs(spawnSettings);
        SpawnSettingsBuilderME.addRiverAnimals(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addOceanVegetation(generationSettings);
        BiomeFeaturesME.addMudOre(vegetation);

        BiomeFeaturesME.addWillowTrees(vegetation);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createOldAngmarBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, int step) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        SpawnSettingsBuilderME.addRareWarg(spawnSettings);
        SpawnSettingsBuilderME.addWolves(spawnSettings);
        SpawnSettingsBuilderME.addDeer(spawnSettings);
        SpawnSettingsBuilderME.addRabbits(spawnSettings);

        BiomeFeaturesME.addDisks(undergroundOres);
        vegetation.add(VegetationPlacedFeatures.PATCH_LARGE_FERN);
        vegetation.add(VegetationPlacedFeatures.PATCH_GRASS_TAIGA);
        vegetation.add(VegetationPlacedFeatures.FLOWER_DEFAULT);
        vegetation.add(VegetationPlacedFeatures.BROWN_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.RED_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.PATCH_BERRY_RARE);

        BiomeFeaturesME.addBasaltBoulder(vegetation);
        BiomeFeaturesME.addWildGrass(vegetation);
        BiomeFeaturesME.addWhiteMushroom(vegetation);
        BiomeFeaturesME.addToughBerriesRare(vegetation);
        BiomeFeaturesME.addCoarseDirtOre(vegetation);
        BiomeFeaturesME.addAshenGravelOre(vegetation);
        BiomeFeaturesME.addDyingGrass(vegetation);

        float temperature = 0.35f;
        if(step == 0) { // Forest
            BiomeFeaturesME.addAshenDirtOre(vegetation);
            BiomeFeaturesME.addNettles(vegetation);
            BiomeFeaturesME.addPodzolOre(vegetation);
            BiomeFeaturesME.addBrownBolete(vegetation);
            BiomeFeaturesME.addMorsel(vegetation);
            BiomeFeaturesME.addRareForestMoss(vegetation);
            BiomeFeaturesME.addCommonSpruceBushes(vegetation);
            BiomeFeaturesME.addDeadPineTrees(vegetation);
            BiomeFeaturesME.addDeadBlackPineTrees(vegetation);
            BiomeFeaturesME.addCommonFirTrees(vegetation);
            BiomeFeaturesME.addCommonPineTrees(vegetation);
            BiomeFeaturesME.addCommonBlackPineTrees(vegetation);
            BiomeFeaturesME.addFrequentSpruceTrees(vegetation);
            BiomeFeaturesME.addScorchedTrees(vegetation);
            BiomeFeaturesME.addScorchedGrass(vegetation);
            BiomeFeaturesME.addScorchedShrub(vegetation);
            BiomeFeaturesME.addThornyGrowth(vegetation);
            BiomeFeaturesME.addRottenTrees(vegetation);
            BiomeFeaturesME.addDeadwoodTrees(vegetation);
        } else if(step == 1) { // Plains
            SpawnSettingsBuilderME.addFarmAnimals(spawnSettings);
            BiomeFeaturesME.addShriveledShrubs(vegetation);
            BiomeFeaturesME.addSpruceBushes(vegetation);
            BiomeFeaturesME.addScarceBlackPineTrees(vegetation);
            BiomeFeaturesME.addVeryRareSpruceTrees(vegetation);
        } else if(step == 2) { // Cold Hill
            BiomeFeaturesME.addSparsePineTrees(vegetation);
            BiomeFeaturesME.addScarceBlackPineTrees(vegetation);
            BiomeFeaturesME.addScarceSpruceTrees(vegetation);
            BiomeFeaturesME.addSpruceBushes(vegetation);
            BiomeFeaturesME.addScorchedTrees(vegetation);
            BiomeFeaturesME.addStickySnow(vegetation);
            BiomeFeaturesME.addFrozenGrass(vegetation);
            BiomeFeaturesME.addFrozenGrowth(vegetation);
            temperature = 0.5f;
        } else if(step == 3) { // Frozen Hill
            BiomeFeaturesME.addShriveledShrubs(vegetation);
            BiomeFeaturesME.addSnowOre(vegetation);
            BiomeFeaturesME.addScorchedTrees(vegetation);
            BiomeFeaturesME.addDeadBlackPineTrees(vegetation);
            BiomeFeaturesME.addSparsePineTrees(vegetation);
            BiomeFeaturesME.addScarceBlackPineTrees(vegetation);
            BiomeFeaturesME.addSpruceTrees(vegetation);
            BiomeFeaturesME.addFrozenGrass(vegetation);
            BiomeFeaturesME.addFrozenShrub(vegetation);
            BiomeFeaturesME.addFrozenGrowth(vegetation);
            temperature = -0.2f;
        }

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings, temperature, true);
    }

    public static void createOldArthedainBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, int step) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        SpawnSettingsBuilderME.addEriadorMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addArthedainVegetation(generationSettings);
        BiomeFeaturesME.addHeather(vegetation);

        if(step == 0) { // Meadow
            BiomeFeaturesME.addBeesOakTrees(vegetation);
            BiomeFeaturesME.addCommonHeath(vegetation);
            BiomeFeaturesME.addCommonHeather(vegetation);
            BiomeFeaturesME.addUncommonLavender(vegetation);
            BiomeFeaturesME.addRareHeather(vegetation);
            BiomeFeaturesME.addSparseFieldDeadNormalHeather(vegetation);
        } else if(step == 1) { // Plains
            SpawnSettingsBuilderME.addRabbits(spawnSettings);

            BiomeFeaturesME.addThistle(vegetation);
            BiomeFeaturesME.addSparseGrass(vegetation);
            BiomeFeaturesME.addHogweeds(vegetation);
            BiomeFeaturesME.addBeesOakTrees(vegetation);
            BiomeFeaturesME.addSmoothDolomiteBoulder(vegetation);
            BiomeFeaturesME.addHeath(vegetation);
            BiomeFeaturesME.addVeryRareBirchTrees(vegetation);
            vegetation.add(VegetationPlacedFeatures.TREES_PLAINS);
            BiomeFeaturesME.addOakBushes(vegetation);
            BiomeFeaturesME.addMixedWildWheatPatch(vegetation);
            BiomeFeaturesME.addCoarseSiltOre(vegetation);
        } else if(step == 2) { // Forest
            SpawnSettingsBuilderME.addDeer(spawnSettings);

            vegetation.add(VegetationPlacedFeatures.PATCH_LARGE_FERN);
            BiomeFeaturesME.addBeesOakTrees(vegetation);
            BiomeFeaturesME.addBracken(vegetation);
            BiomeFeaturesME.addNettles(vegetation);
            BiomeFeaturesME.addGiantButterbur(vegetation);
            BiomeFeaturesME.addAlliumFlower(vegetation);
            BiomeFeaturesME.addSweetBerriesRare(vegetation);
            BiomeFeaturesME.addGreenShrub(vegetation);
            BiomeFeaturesME.addFalseOatgrass(vegetation);
            BiomeFeaturesME.addMossyBoulder(vegetation);
            BiomeFeaturesME.addBirchTrees(vegetation);
            BiomeFeaturesME.addSparseBirchTrees(vegetation);
            BiomeFeaturesME.addMegaBirchTrees(vegetation);
            BiomeFeaturesME.addCommonBeechTrees(vegetation);
            BiomeFeaturesME.addLarchTrees(vegetation);
            BiomeFeaturesME.addFirTrees(vegetation);
            BiomeFeaturesME.addCommonOakTrees(vegetation);
            BiomeFeaturesME.addCommonPineTrees(vegetation);
            BiomeFeaturesME.addBlackPineTrees(vegetation);
            BiomeFeaturesME.addScarceSpruceTrees(vegetation);
            BiomeFeaturesME.addRareMegaOakTrees(vegetation);
            BiomeFeaturesME.addPodzolOre(vegetation);
            BiomeFeaturesME.addForestMoss(vegetation);
            BiomeFeaturesME.addForestBlockMoss(vegetation);
            BiomeFeaturesME.addSpruceBushes(vegetation);
            BiomeFeaturesME.addRareMorsel(vegetation);
            BiomeFeaturesME.addRareWhiteMushroom(vegetation);
            BiomeFeaturesME.addWildFlax(vegetation);
        } else if(step == 3) { // Foothill
            BiomeFeaturesME.addSmoothDolomiteBoulder(vegetation);
            BiomeFeaturesME.addSpruceBushes(vegetation);
            BiomeFeaturesME.addSparsePineTrees(vegetation);
            BiomeFeaturesME.addScarceBlackPineTrees(vegetation);
            BiomeFeaturesME.addScarceSpruceTrees(vegetation);
        }

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createOldCardolanBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, int step) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        SpawnSettingsBuilderME.addEriadorMobs(spawnSettings);
        SpawnSettingsBuilderME.addRareWolves(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addEriadorVegetation(generationSettings);
        vegetation.add(VegetationPlacedFeatures.PATCH_BERRY_RARE);

        BiomeFeaturesME.addRareHeather(vegetation);
        BiomeFeaturesME.addRedHeather(vegetation);
        BiomeFeaturesME.addRareWilderGrass(vegetation);

        if(step == 0) { // Plains
            BiomeFeaturesME.addSmoothDolomiteBoulder(vegetation);
            BiomeFeaturesME.addThistle(vegetation);
            BiomeFeaturesME.addSparseGrass(vegetation);
            BiomeFeaturesME.addHogweeds(vegetation);
            BiomeFeaturesME.addDryDirtOre(vegetation);
            BiomeFeaturesME.addGravelOre(vegetation);
            BiomeFeaturesME.addHeath(vegetation);
            BiomeFeaturesME.addVeryRareBeechTrees(vegetation);
            BiomeFeaturesME.addTuftGrass(vegetation);
            BiomeFeaturesME.addOakBushes(vegetation);
        } else if(step == 1) { // Forest
            SpawnSettingsBuilderME.addDeer(spawnSettings);
            vegetation.add(VegetationPlacedFeatures.PATCH_LARGE_FERN);
            BiomeFeaturesME.addFalseOatgrass(vegetation);
            BiomeFeaturesME.addNettles(vegetation);
            BiomeFeaturesME.addMossyBoulder(vegetation);
            BiomeFeaturesME.addGreenShrub(vegetation);
            BiomeFeaturesME.addCommonBeechTrees(vegetation);
            BiomeFeaturesME.addBirchTrees(vegetation);
            BiomeFeaturesME.addSparseBirchTrees(vegetation);
            BiomeFeaturesME.addMegaBirchTrees(vegetation);
            BiomeFeaturesME.addChestnutTrees(vegetation);
            BiomeFeaturesME.addMapleTrees(vegetation);
            BiomeFeaturesME.addOakTrees(vegetation);
            BiomeFeaturesME.addCommonOakTrees(vegetation);
            BiomeFeaturesME.addRareMegaOakTrees(vegetation);
            BiomeFeaturesME.addSparsePineTrees(vegetation);
            BiomeFeaturesME.addPodzolOre(vegetation);
            BiomeFeaturesME.addForestMoss(vegetation);
            BiomeFeaturesME.addForestBlockMoss(vegetation);
            BiomeFeaturesME.addSpruceBushes(vegetation);
            BiomeFeaturesME.addRareMorsel(vegetation);
            BiomeFeaturesME.addRareWhiteMushroom(vegetation);
        } else if(step == 2) { // Hill
            vegetation.add(VegetationPlacedFeatures.PATCH_LARGE_FERN);
            BiomeFeaturesME.addBracken(vegetation);
            BiomeFeaturesME.addPodzolOre(vegetation);
            BiomeFeaturesME.addCoarseDirtOre(vegetation);
            BiomeFeaturesME.addGravelOre(vegetation);
            BiomeFeaturesME.addGrassToGraniteOre(vegetation);
            BiomeFeaturesME.addSmoothDolomiteBoulder(vegetation);
            BiomeFeaturesME.addCommonSpruceBushes(vegetation);
            BiomeFeaturesME.addCommonBlackPineTrees(vegetation);
            BiomeFeaturesME.addLarchTrees(vegetation);
            BiomeFeaturesME.addCommonPineTrees(vegetation);
            BiomeFeaturesME.addSpruceTrees(vegetation);
        }

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createOldRhudaurBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, int step) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        SpawnSettingsBuilderME.addRareWarg(spawnSettings);
        SpawnSettingsBuilderME.addWolves(spawnSettings);
        SpawnSettingsBuilderME.addDeer(spawnSettings);
        SpawnSettingsBuilderME.addRabbits(spawnSettings);

        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addNordicVegetation(generationSettings);
        BiomeFeaturesME.addFalseOatgrass(vegetation);
        BiomeFeaturesME.addCoarseDirtOre(vegetation);
        BiomeFeaturesME.addDyingGrass(vegetation);

        if(step == 0) {
            SpawnSettingsBuilderME.addPlainsMobs(spawnSettings);
            BiomeFeaturesME.addThistle(vegetation);
            BiomeFeaturesME.addSparseGrass(vegetation);
            BiomeFeaturesME.addHogweeds(vegetation);
            BiomeFeaturesME.addStoneBoulder(vegetation);
            BiomeFeaturesME.addOakBushes(vegetation);
            BiomeFeaturesME.addWheatGrass(vegetation);
            BiomeFeaturesME.addShriveledShrubs(vegetation);
            vegetation.add(VegetationPlacedFeatures.TREES_PLAINS);
        } else if(step == 1) { // Forest
            BiomeFeaturesME.addMossyBoulder(vegetation);
            SpawnSettingsBuilderME.addDeer(spawnSettings);
            BiomeFeaturesME.addForestMoss(vegetation);
            BiomeFeaturesME.addNettles(vegetation);
            BiomeFeaturesME.addBeechTrees(vegetation);
            BiomeFeaturesME.addSparseLarchTrees(vegetation);
            BiomeFeaturesME.addOakTrees(vegetation);
            BiomeFeaturesME.addOakBushes(vegetation);
            BiomeFeaturesME.addDeadPineTrees(vegetation);
            BiomeFeaturesME.addSparsePineTrees(vegetation);
            BiomeFeaturesME.addBlackPineTrees(vegetation);
            BiomeFeaturesME.addSpruceTrees(vegetation);
            BiomeFeaturesME.addCommonFirTrees(vegetation);
            BiomeFeaturesME.addCommonSpruceTrees(vegetation);
            BiomeFeaturesME.addSpruceBushes(vegetation);
            BiomeFeaturesME.addPodzolOre(vegetation);
        } else if(step == 2) { // Hill
            BiomeFeaturesME.addAndesiteBoulder(vegetation);
            BiomeFeaturesME.addDolomiteBoulder(vegetation);
            BiomeFeaturesME.addGravelOre(vegetation);
            BiomeFeaturesME.addGrassToStoneOre(vegetation);
            BiomeFeaturesME.addForestMoss(vegetation);
            BiomeFeaturesME.addSparseLarchTrees(vegetation);
            BiomeFeaturesME.addDeadPineTrees(vegetation);
            BiomeFeaturesME.addFirTrees(vegetation);
            BiomeFeaturesME.addPineTrees(vegetation);
            BiomeFeaturesME.addBlackPineTrees(vegetation);
            BiomeFeaturesME.addCommonSpruceTrees(vegetation);
            BiomeFeaturesME.addSpruceBushes(vegetation);
            BiomeFeaturesME.addPodzolOre(vegetation);
        }

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createRhunBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, int step) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        SpawnSettingsBuilderME.addPlainsMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        BiomeFeaturesME.addDryDirtOre(vegetation);

        if(step != 1){
            addRhunVegetation(generationSettings);
            BiomeFeaturesME.addThistle(vegetation);
            BiomeFeaturesME.addSparseGrass(vegetation);
            BiomeFeaturesME.addGravelOre(vegetation);
            BiomeFeaturesME.addOakBushes(vegetation);
            BiomeFeaturesME.addWildGrass(vegetation);
            BiomeFeaturesME.addSparseWheatGrass(vegetation);
            BiomeFeaturesME.addRareWilderGrass(vegetation);
            BiomeFeaturesME.addLimestoneBoulder(vegetation);
        } else { // Fields
            SpawnSettingsBuilderME.addRabbits(spawnSettings);

            BiomeFeaturesME.addWildWheatField(vegetation);
            BiomeFeaturesME.addMixedWildWheatPatch(vegetation);
            BiomeFeaturesME.addGrassyDirtOre(vegetation);
        }

        if(step == 0) { // Plains
            BiomeFeaturesME.addRareBeechTrees(vegetation);
            BiomeFeaturesME.addRareYellowFlower(vegetation);
            BiomeFeaturesME.addTallGrass(vegetation);
            BiomeFeaturesME.addYellowTrolliusPatch(vegetation);
        } else if(step == 2) { // Forest
            SpawnSettingsBuilderME.addDeer(spawnSettings);
            BiomeFeaturesME.addCommonBeechTrees(vegetation);
            BiomeFeaturesME.addCommonOakTrees(vegetation);
            BiomeFeaturesME.addCommonBirchTrees(vegetation);
            BiomeFeaturesME.addCommonPineTrees(vegetation);
            BiomeFeaturesME.addCommonBlackPineTrees(vegetation);
            BiomeFeaturesME.addFalseOatgrass(vegetation);
            BiomeFeaturesME.addNettles(vegetation);
            BiomeFeaturesME.addCoarseDirtOre(vegetation);
            BiomeFeaturesME.addMoss(vegetation);
            BiomeFeaturesME.addMossCarpet(vegetation);
            vegetation.add(VegetationPlacedFeatures.FLOWER_FOREST_FLOWERS);
        } else if(step == 3) { // Blossom
            SpawnSettingsBuilderME.addWolves(spawnSettings);
            SpawnSettingsBuilderME.addRareWolves(spawnSettings);
            BiomeFeaturesME.addBeesOakTrees(vegetation);
            BiomeFeaturesME.addOakTrees(vegetation);
            BiomeFeaturesME.addSparseBirchTrees(vegetation);
            BiomeFeaturesME.addCherryBlossomTrees(vegetation);
            BiomeFeaturesME.addPinkFlowers(vegetation);
            BiomeFeaturesME.addRedFlowers(vegetation);
            BiomeFeaturesME.addYellowFlower(vegetation);
            BiomeFeaturesME.addHeath(vegetation);
            BiomeFeaturesME.addYellowFlowerGrowth(vegetation);
            vegetation.add(VegetationPlacedFeatures.FLOWER_CHERRY);
            vegetation.add(VegetationPlacedFeatures.TREES_CHERRY);
        }

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createRivendellBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, int step) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        SpawnSettingsBuilderME.addPlainsMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addRivendellVegetation(generationSettings);

        BiomeFeaturesME.addTurfOre(vegetation);

        BiomeFeaturesME.addSparseGrass(vegetation);
        BiomeFeaturesME.addWildGrass(vegetation);
        BiomeFeaturesME.addWhiteFlowers(vegetation);
        BiomeFeaturesME.addWildFlowers(vegetation);

        if(step == 0) {
            BiomeFeaturesME.addCoarseChalksoilOre(vegetation);
            BiomeFeaturesME.addPodzolOre(vegetation);

            BiomeFeaturesME.addFalseOatgrass(vegetation);
            BiomeFeaturesME.addBracken(vegetation);
            BiomeFeaturesME.addGiantButterbur(vegetation);
            BiomeFeaturesME.addNettles(vegetation);
            BiomeFeaturesME.addForestMoss(vegetation);

            BiomeFeaturesME.addLarchTrees(vegetation);
            BiomeFeaturesME.addFirTrees(vegetation);
            BiomeFeaturesME.addMapleTrees(vegetation);
            BiomeFeaturesME.addCommonPineTrees(vegetation);
            BiomeFeaturesME.addCommonSpruceTrees(vegetation);
        } else if (step == 1) {
            BiomeFeaturesME.addGrassyChalksoilOre(vegetation);

            BiomeFeaturesME.addOakBushes(vegetation);

            BiomeFeaturesME.addRareLightBlueFlowers(vegetation);
            BiomeFeaturesME.addHeatherField(vegetation);
            BiomeFeaturesME.addShriveledShrubs(vegetation);
            BiomeFeaturesME.addRareLavender(vegetation);
            BiomeFeaturesME.addCornflower(vegetation);
        }

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createRivendellFoothillsBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        SpawnSettingsBuilderME.addMountainsMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        BiomeFeaturesME.addTurfOre(vegetation);
        BiomeFeaturesME.addGrassyChalksoilOre(vegetation);

        BiomeFeaturesME.addDolomiteOre(vegetation);
        BiomeFeaturesME.addGravelOre(vegetation);
        BiomeFeaturesME.addLimestoneOre(vegetation);
        BiomeFeaturesME.addTuffOre(vegetation);
        BiomeFeaturesME.addCornflower(vegetation);

        BiomeFeaturesME.addRareLightBlueFlowers(vegetation);
        BiomeFeaturesME.addHeatherField(vegetation);
        BiomeFeaturesME.addShriveledShrubs(vegetation);
        BiomeFeaturesME.addRareLavender(vegetation);
        BiomeFeaturesME.addOakBushes(vegetation);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings, 0.4f, true);
    }

    public static void createRohanBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, int step) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        SpawnSettingsBuilderME.addPlainsMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));
        addDefaultVegetation(generationSettings);

        BiomeFeaturesME.addGrassyDirtOre(vegetation);
        BiomeFeaturesME.addDryDirtOre(vegetation);
        BiomeFeaturesME.addGravelOre(vegetation);
        BiomeFeaturesME.addOakBushes(vegetation);
        vegetation.add(VegetationPlacedFeatures.TREES_PLAINS);

        if(step != 2){
            BiomeFeaturesME.addGraniteBoulder(vegetation);
            BiomeFeaturesME.addFlowerGreenJewel(vegetation);
            BiomeFeaturesME.addWildGrass(vegetation);
            BiomeFeaturesME.addRareWilderGrass(vegetation);
            BiomeFeaturesME.addVeryRareBeechTrees(vegetation);
            BiomeFeaturesME.addWheatGrass(vegetation);
            BiomeFeaturesME.addSedum(vegetation);
        }

        if (step == 0) { //  Plains
            BiomeFeaturesME.addThistle(vegetation);
            BiomeFeaturesME.addSparseGrass(vegetation);
            BiomeFeaturesME.addHogweeds(vegetation);
            BiomeFeaturesME.addCommonWheatGrass(vegetation);
            BiomeFeaturesME.addSedumYellow(vegetation);
            BiomeFeaturesME.addSimbelmyne(vegetation);
            BiomeFeaturesME.addWildLettuce(vegetation);
            BiomeFeaturesME.addDryGrass(vegetation);
            BiomeFeaturesME.addTallGrass(vegetation);
            BiomeFeaturesME.addDolomiteBoulder(vegetation);
        } else if (step == 1) { // Forest
            SpawnSettingsBuilderME.addDeer(spawnSettings);
            BiomeFeaturesME.addNettles(vegetation);
            BiomeFeaturesME.addGreenShrub(vegetation);
            BiomeFeaturesME.addFalseOatgrass(vegetation);
            BiomeFeaturesME.addCoarseDirtOre(vegetation);
            BiomeFeaturesME.addPodzolOre(vegetation);
            BiomeFeaturesME.addRareMorsel(vegetation);
            BiomeFeaturesME.addCommonBeechTrees(vegetation);
            BiomeFeaturesME.addCommonOakTrees(vegetation);
            BiomeFeaturesME.addMapleTrees(vegetation);
            BiomeFeaturesME.addDarkOakTrees(vegetation);
            BiomeFeaturesME.addChestnutTrees(vegetation);
            BiomeFeaturesME.addSparsePineTrees(vegetation);
            BiomeFeaturesME.addVeryRareSpruceTrees(vegetation);
            BiomeFeaturesME.addMoss(vegetation);
        } else if (step == 2) { // Fields
            SpawnSettingsBuilderME.addRabbits(spawnSettings);
            BiomeFeaturesME.addWildWheatField(vegetation);
            BiomeFeaturesME.addMixedWildWheatPatch(vegetation);
            BiomeFeaturesME.addDryGrowth(vegetation);
            BiomeFeaturesME.addVeryRareMegaOakTrees(vegetation);
        }

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createSarnGebir(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, int step) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        BiomeFeaturesME.addDisks(undergroundOres);
        BiomeFeaturesME.addGrass(vegetation);
        vegetation.add(VegetationPlacedFeatures.FLOWER_DEFAULT);
        vegetation.add(VegetationPlacedFeatures.BROWN_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.RED_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.PATCH_SUGAR_CANE);
        vegetation.add(VegetationPlacedFeatures.PATCH_PUMPKIN);
        vegetation.add(VegetationPlacedFeatures.TREES_PLAINS);
        BiomeFeaturesME.addFlowerGreenJewel(vegetation);
        BiomeFeaturesME.addWildFlax(vegetation);
        BiomeFeaturesME.addWildGrass(vegetation);
        BiomeFeaturesME.addWildLeek(vegetation);
        BiomeFeaturesME.addWildLettuce(vegetation);
        BiomeFeaturesME.addWildOnion(vegetation);
        BiomeFeaturesME.addCoarseDirtOre(vegetation);
        BiomeFeaturesME.addGravelOre(vegetation);
        BiomeFeaturesME.addDeadHeather(vegetation);
        BiomeFeaturesME.addDyingGrass(vegetation);
        BiomeFeaturesME.addGraniteBoulder(vegetation);
        BiomeFeaturesME.addStoneBoulder(vegetation);
        BiomeFeaturesME.addDeadRushes(vegetation);
        BiomeFeaturesME.addCommonOakBush(vegetation);
        BiomeFeaturesME.addLarchTrees(vegetation);
        BiomeFeaturesME.addCommonBeechTrees(vegetation);
        BiomeFeaturesME.addCommonOakTrees(vegetation);
        BiomeFeaturesME.addGreenMapleTree(vegetation);

        if(step == 0) { // Forest
            SpawnSettingsBuilderME.addDeer(spawnSettings);
            SpawnSettingsBuilderME.addRabbits(spawnSettings);
            SpawnSettingsBuilderME.addRareWolves(spawnSettings);
            vegetation.add(VegetationPlacedFeatures.PATCH_LARGE_FERN);
            vegetation.add(VegetationPlacedFeatures.PATCH_GRASS_TAIGA);
            BiomeFeaturesME.addBracken(vegetation);
            BiomeFeaturesME.addNettles(vegetation);
            BiomeFeaturesME.addFalseOatgrass(vegetation);
            BiomeFeaturesME.addOldPodzolOre(vegetation);
            BiomeFeaturesME.addPodzolOre(vegetation);
            BiomeFeaturesME.addRareMorsel(vegetation);
            BiomeFeaturesME.addCommonDarkOakTrees(vegetation);
            BiomeFeaturesME.addCommonPineTrees(vegetation);
            BiomeFeaturesME.addBlackPineTrees(vegetation);
            BiomeFeaturesME.addSpruceTrees(vegetation);
            BiomeFeaturesME.addForestMoss(vegetation);
        } else if(step == 1) { // Shores
            BiomeFeaturesME.addGrassToStoneOre(vegetation);
            BiomeFeaturesME.addTuftGrass(vegetation);
            BiomeFeaturesME.addDarkOakTrees(vegetation);
        }

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createShireBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, int step) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addShireVegetation(generationSettings);

        if(step < 3) {
            SpawnSettingsBuilderME.addEriadorMobs(spawnSettings);
            SpawnSettingsBuilderME.addRareSnails(spawnSettings);
            BiomeFeaturesME.addAzureBluetFlower(vegetation);
            BiomeFeaturesME.addFlowerGreenJewel(vegetation);
            BiomeFeaturesME.addCommonTurfOre(vegetation);
            BiomeFeaturesME.addRareOakTrees(vegetation);
            BiomeFeaturesME.addClovers(vegetation);
            BiomeFeaturesME.addWildFlowers(vegetation);
            BiomeFeaturesME.addSparseGrass(vegetation);
            BiomeFeaturesME.addWildGrass(vegetation);
            vegetation.add(VegetationPlacedFeatures.TREES_PLAINS);
            if(step == 1) {
                SpawnSettingsBuilderME.addCats(spawnSettings);
                BiomeFeaturesME.addYellowTrolliusPatch(vegetation);
            } else if(step == 0) {
                SpawnSettingsBuilderME.addCats(spawnSettings);
                BiomeFeaturesME.addThistle(vegetation);
                BiomeFeaturesME.addHobbitSunflowers(vegetation);
                BiomeFeaturesME.addBigleafHydrangeas(vegetation);
                BiomeFeaturesME.addHogweeds(vegetation);
                BiomeFeaturesME.addStrawberries(vegetation);
                BiomeFeaturesME.addRareOakBushes(vegetation);
                BiomeFeaturesME.addYellowTrolliusPatch(vegetation);
            } else {
                BiomeFeaturesME.addBracken(vegetation);
                BiomeFeaturesME.addFalseOatgrass(vegetation);
                BiomeFeaturesME.addTurfOre(vegetation);
                BiomeFeaturesME.addPackedMudOre(vegetation);
                BiomeFeaturesME.addGraniteBoulder(vegetation);
                BiomeFeaturesME.addCommonOakBush(vegetation);
                BiomeFeaturesME.addWildFlax(vegetation);
            }
        } else {
            SpawnSettingsBuilderME.addDeer(spawnSettings);
            SpawnSettingsBuilderME.addWolves(spawnSettings);
            BiomeFeaturesME.addFalseOatgrass(vegetation);
            BiomeFeaturesME.addMossyBoulder(vegetation);
            BiomeFeaturesME.addCoarseDirtOre(vegetation);
            BiomeFeaturesME.addPodzolOre(vegetation);
            BiomeFeaturesME.addRareMorsel(vegetation);
            BiomeFeaturesME.addWildFlax(vegetation);
            vegetation.add(VegetationPlacedFeatures.TREES_PLAINS);
            BiomeFeaturesME.addBeechTrees(vegetation);
            BiomeFeaturesME.addBirchTrees(vegetation);
            BiomeFeaturesME.addChestnutTrees(vegetation);
            BiomeFeaturesME.addOakBushes(vegetation);
            BiomeFeaturesME.addPineTrees(vegetation);
            BiomeFeaturesME.addVeryRareSpruceTrees(vegetation);
            BiomeFeaturesME.addMoss(vegetation);
            if(step == 4) {
                BiomeFeaturesME.addMossCarpet(vegetation);
                BiomeFeaturesME.addOakTrees(vegetation);
                BiomeFeaturesME.addMegaBirchTrees(vegetation);
                BiomeFeaturesME.addMegaDarkOakTrees(vegetation);
                BiomeFeaturesME.addMegaOakCommonTrees(vegetation);
            } else {
                BiomeFeaturesME.addRareBeechTrees(vegetation);
                BiomeFeaturesME.addSparseBirchTrees(vegetation);
                BiomeFeaturesME.addRareBirchTrees(vegetation);
                BiomeFeaturesME.addBirchAndOakTrees(vegetation);
                BiomeFeaturesME.addCommonOakTrees(vegetation);
                BiomeFeaturesME.addRareOakTrees(vegetation);
                BiomeFeaturesME.addCommonDarkOakTrees(vegetation);
                BiomeFeaturesME.addCherryBlossomTrees(vegetation);
                BiomeFeaturesME.addRareMegaOakTrees(vegetation);
                BiomeFeaturesME.addRareMegaDarkOakTrees(vegetation);
            }
        }
        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createSoutheastRhovanionBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, int step) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        SpawnSettingsBuilderME.addPlainsMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addRhunVegetation(generationSettings);
        BiomeFeaturesME.addBasaltBoulder(vegetation);
        BiomeFeaturesME.addShriveledShrubs(vegetation);
        BiomeFeaturesME.addGrimGrass(vegetation);
        BiomeFeaturesME.addDryDirtOre(vegetation);
        BiomeFeaturesME.addGravelOre(vegetation);
        BiomeFeaturesME.addOakBushes(vegetation);
        BiomeFeaturesME.addWildGrass(vegetation);
        BiomeFeaturesME.addRareWilderGrass(vegetation);
        BiomeFeaturesME.addSedumYellow(vegetation);

        if(step == 0) {
            BiomeFeaturesME.addRareBeechTrees(vegetation);
        } else if (step == 1) { // Fields
            BiomeFeaturesME.addRedFlowers(vegetation);
            BiomeFeaturesME.addRedFlowerGrowth(vegetation);
            BiomeFeaturesME.addPoppyFlower(vegetation);
            BiomeFeaturesME.addRoseBush(vegetation);
            BiomeFeaturesME.addTuftGrass(vegetation);
            BiomeFeaturesME.addRedHeather(vegetation);
        }

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createSouthernForochelBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        SpawnSettingsBuilderME.addNordicMobs(spawnSettings);
        SpawnSettingsBuilderME.addWolves(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        BiomeFeaturesME.addDisks(undergroundOres);
        vegetation.add(VegetationPlacedFeatures.PATCH_LARGE_FERN);
        vegetation.add(VegetationPlacedFeatures.PATCH_GRASS_TAIGA);
        vegetation.add(VegetationPlacedFeatures.FLOWER_DEFAULT);
        vegetation.add(VegetationPlacedFeatures.BROWN_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.RED_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.PATCH_BERRY_RARE);
        BiomeFeaturesME.addBracken(vegetation);
        BiomeFeaturesME.addBrownBolete(vegetation);
        BiomeFeaturesME.addMorsel(vegetation);
        BiomeFeaturesME.addWhiteMushroom(vegetation);
        BiomeFeaturesME.addWildBeetroot(vegetation);
        BiomeFeaturesME.addWildPotato(vegetation);

        BiomeFeaturesME.addSmallDryShrub(vegetation);
        BiomeFeaturesME.addGravelOre(vegetation);
        BiomeFeaturesME.addBlueTuffBoulder(vegetation);
        BiomeFeaturesME.addSparsePineTrees(vegetation);
        BiomeFeaturesME.addRareSpruceTrees(vegetation);
        BiomeFeaturesME.addVeryRareFirTrees(vegetation);
        BiomeFeaturesME.addFrozenGrass(vegetation);
        BiomeFeaturesME.addFrozenShrub(vegetation);
        BiomeFeaturesME.addFrozenGrowth(vegetation);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings, -0.4f, true);
    }

    public static void createTheAngleBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        SpawnSettingsBuilderME.addEriadorMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addEriadorVegetation(generationSettings);
        BiomeFeaturesME.addRareHeather(vegetation);
        BiomeFeaturesME.addRareForestMoss(vegetation);
        BiomeFeaturesME.addSparseBlueLavender(vegetation);
        BiomeFeaturesME.addGravelOre(vegetation);
        BiomeFeaturesME.addCoarseDirtOre(vegetation);
        BiomeFeaturesME.addRareWilderGrass(vegetation);

        BiomeFeaturesME.addOakBushes(vegetation);
        BiomeFeaturesME.addRareBeechTrees(vegetation);
        BiomeFeaturesME.addVeryRareBirchTrees(vegetation);
        BiomeFeaturesME.addSparseLarchTrees(vegetation);
        BiomeFeaturesME.addVeryRareSpruceTrees(vegetation);
        BiomeFeaturesME.addMapleTrees(vegetation);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createTheOldForestBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        SpawnSettingsBuilderME.addDeer(spawnSettings);
        SpawnSettingsBuilderME.addWolves(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addEriadorVegetation(generationSettings);
        vegetation.add(VegetationPlacedFeatures.PATCH_TALL_GRASS_2);
        BiomeFeaturesME.addMistweed(vegetation);
        BiomeFeaturesME.addMossyBoulder(vegetation);
        BiomeFeaturesME.addNettles(vegetation);
        BiomeFeaturesME.addForestMoss(vegetation);
        BiomeFeaturesME.addForestBlockMoss(vegetation);
        BiomeFeaturesME.addOldPodzolOre(vegetation);
        BiomeFeaturesME.addGravelOre(vegetation);
        BiomeFeaturesME.addRareMorsel(vegetation);
        BiomeFeaturesME.addRareWhiteMushroom(vegetation);

        BiomeFeaturesME.addOakBushes(vegetation);
        BiomeFeaturesME.addMegaBirchCommonTrees(vegetation);
        BiomeFeaturesME.addMegaDarkOakCommonTrees(vegetation);
        BiomeFeaturesME.addMegaOakCommonTrees(vegetation);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createTheWhiteDownsBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        SpawnSettingsBuilderME.addFarmAnimals(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addNordicVegetation(generationSettings);
        BiomeFeaturesME.addDryHeatherField(vegetation);
        BiomeFeaturesME.addBushes(vegetation);
        BiomeFeaturesME.addRareOakBushes(vegetation);
        BiomeFeaturesME.addWilderGrass(vegetation);
        BiomeFeaturesME.addWheatGrass(vegetation);
        BiomeFeaturesME.addGrassyChalksoilOre(vegetation);
        BiomeFeaturesME.addTurfOre(vegetation);
        BiomeFeaturesME.addGravelOre(vegetation);
        BiomeFeaturesME.addDioriteBoulder(vegetation);
        BiomeFeaturesME.addCalciteBoulder(vegetation);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings, 0.4f, true);
    }

    public static void createTolfalasBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        SpawnSettingsBuilderME.addFarmAnimals(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addDefaultVegetation(generationSettings);
        BiomeFeaturesME.addCoarseDirtOre(vegetation);
        BiomeFeaturesME.addGravelOre(vegetation);
        BiomeFeaturesME.addGrassToStoneOre(vegetation);
        BiomeFeaturesME.addRareWilderGrass(vegetation);
        BiomeFeaturesME.addOakBushes(vegetation);

        BiomeFeaturesME.addSparseLarchTrees(vegetation);
        BiomeFeaturesME.addRareLebethronTrees(vegetation);
        BiomeFeaturesME.addSparsePineTrees(vegetation);
        BiomeFeaturesME.addRareSpruceTrees(vegetation);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createTorogwaithBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));
        SpawnSettingsBuilderME.addRareWarg(spawnSettings);
        SpawnSettingsBuilderME.addRareWolves(spawnSettings);
        SpawnSettingsBuilderME.addRareCaveTroll(spawnSettings);
        SpawnSettingsBuilderME.addUncommonBats(spawnSettings);

        addMordorVegetation(generationSettings);
        BiomeFeaturesME.addGrass(vegetation);
        BiomeFeaturesME.addAshenDirtOre(vegetation);
        BiomeFeaturesME.addDirtToGrassOre(vegetation);
        BiomeFeaturesME.addShriveledShrubs(vegetation);
        BiomeFeaturesME.addAshenStoneDirtOre(vegetation);
        BiomeFeaturesME.addBasaltPile(vegetation);
        BiomeFeaturesME.addBlackStonePile(vegetation);
        BiomeFeaturesME.addPumicePileRare(vegetation);
        BiomeFeaturesME.addDeadBlackPineTrees(vegetation);
        BiomeFeaturesME.addScorchedTrees(vegetation);
        BiomeFeaturesME.addDryPineTrees(vegetation);
        BiomeFeaturesME.addDryPineBushes(vegetation);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings, 0.7f, false);
    }

    public static void createTrollshawsBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        SpawnSettingsBuilderME.addDeer(spawnSettings);
        SpawnSettingsBuilderME.addGreatHorn(spawnSettings);
        SpawnSettingsBuilderME.addWolves(spawnSettings);
        SpawnSettingsBuilderME.addRareStoneTroll(spawnSettings);

        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addEriadorVegetation(generationSettings);

        BiomeFeaturesME.addGrassyPeatOre(vegetation);
        BiomeFeaturesME.addCoarsePeatOre(vegetation);
        BiomeFeaturesME.addRootedDirtOre(vegetation);
        BiomeFeaturesME.addPodzolOre(vegetation);
        BiomeFeaturesME.addGravelOre(vegetation);

        BiomeFeaturesME.addLimestoneBoulder(vegetation);

        vegetation.add(VegetationPlacedFeatures.PATCH_LARGE_FERN);
        vegetation.add(VegetationPlacedFeatures.PATCH_GRASS_TAIGA);
        vegetation.add(VegetationPlacedFeatures.PATCH_LEAF_LITTER);
        BiomeFeaturesME.addBracken(vegetation);
        BiomeFeaturesME.addNettles(vegetation);
        BiomeFeaturesME.addGiantButterbur(vegetation);
        BiomeFeaturesME.addHogweeds(vegetation);
        BiomeFeaturesME.addYellowFlowers(vegetation);
        BiomeFeaturesME.addBushes(vegetation);
        BiomeFeaturesME.addDeadRushes(vegetation);
        BiomeFeaturesME.addSedumOrange(vegetation);
        BiomeFeaturesME.addSedumRed(vegetation);
        BiomeFeaturesME.addFalseOatgrass(vegetation);
        BiomeFeaturesME.addDyingGrass(vegetation);
        BiomeFeaturesME.addForestMoss(vegetation);
        BiomeFeaturesME.addRareMorsel(vegetation);

        BiomeFeaturesME.addCommonOakBush(vegetation);
        BiomeFeaturesME.addCommonBeechTrees(vegetation);
        BiomeFeaturesME.addCommonOakTrees(vegetation);
        BiomeFeaturesME.addRareMegaOakTrees(vegetation);
        BiomeFeaturesME.addCommonPineTrees(vegetation);
        BiomeFeaturesME.addSpruceTrees(vegetation);
        BiomeFeaturesME.addMapleTrees(vegetation);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createUmbarBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, int step) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        SpawnSettingsBuilderME.addHaradMobs(spawnSettings);
        SpawnSettingsBuilderME.addRareWolves(spawnSettings);
        SpawnSettingsBuilderME.addFarmAnimals(spawnSettings);
        SpawnSettingsBuilderME.addCats(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addHaradVegetation(generationSettings);
        BiomeFeaturesME.addGraniteBoulder(vegetation);
        BiomeFeaturesME.addRareOakBushes(vegetation);
        BiomeFeaturesME.addDryGrass(vegetation);

        if(step == 0) {
            BiomeFeaturesME.addTallGrass(vegetation);
            BiomeFeaturesME.addSmallDryShrub(vegetation);
            vegetation.add(VegetationPlacedFeatures.PATCH_MELON_SPARSE);
        } else if(step == 1) {
            BiomeFeaturesME.addWildFlax(vegetation);
            BiomeFeaturesME.addOakBushes(vegetation);
            BiomeFeaturesME.addPalmTrees(vegetation);
            BiomeFeaturesME.addCommonAcaciaTrees(vegetation);
            BiomeFeaturesME.addRareBirchTrees(vegetation);
            BiomeFeaturesME.addOakTrees(vegetation);
            BiomeFeaturesME.addCommonOakTrees(vegetation);
            vegetation.add(VegetationPlacedFeatures.TREES_SPARSE_JUNGLE);
            BiomeFeaturesME.addBamboo(vegetation);
        }

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createRiverBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        SpawnSettingsBuilderME.addRiverAnimals(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        BiomeFeaturesME.addRiverDisks(undergroundOres);
        vegetation.add(OceanPlacedFeatures.KELP_COLD);
        vegetation.add(OceanPlacedFeatures.SEAGRASS_NORMAL);
        BiomeFeaturesME.addGrass(vegetation);
        vegetation.add(VegetationPlacedFeatures.FLOWER_DEFAULT);
        vegetation.add(VegetationPlacedFeatures.PATCH_SUGAR_CANE);
        vegetation.add(VegetationPlacedFeatures.PATCH_PUMPKIN);
        BiomeFeaturesME.addReedsFoliage(vegetation);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createLakeBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        SpawnSettingsBuilderME.addRiverAnimals(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addOceanVegetation(generationSettings);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createOasisBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        SpawnSettingsBuilderME.addRiverAnimals(spawnSettings);
        SpawnSettingsBuilderME.addCamel(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addOasisVegetation(generationSettings);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createWastePondBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        SpawnSettingsBuilderME.addRiverAnimals(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        BiomeFeaturesME.addDisks(undergroundOres);
        vegetation.add(OceanPlacedFeatures.KELP_WARM);
        vegetation.add(OceanPlacedFeatures.SEAGRASS_NORMAL);
        BiomeFeaturesME.addGrass(vegetation);
        vegetation.add(VegetationPlacedFeatures.PATCH_SUGAR_CANE);
        BiomeFeaturesME.addReedsFoliage(vegetation);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createWitheredHeathBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        vegetation.add(VegetationPlacedFeatures.PATCH_GRASS_TAIGA);
        vegetation.add(VegetationPlacedFeatures.BROWN_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.RED_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.PATCH_BERRY_RARE);
        BiomeFeaturesME.addCoarseDirtOre(vegetation);
        BiomeFeaturesME.addGravelOre(vegetation);
        BiomeFeaturesME.addBasaltBoulder(vegetation);
        BiomeFeaturesME.addBlackStonePile(vegetation);
        BiomeFeaturesME.addAshenGravelOre(undergroundOres);
        BiomeFeaturesME.addAshenSandOre(undergroundOres);

        BiomeFeaturesME.addDeadRushes(undergroundOres);
        BiomeFeaturesME.addDeadBlackPineTrees(vegetation);
        BiomeFeaturesME.addScorchedTrees(vegetation);
        BiomeFeaturesME.addCommonScorchedGrass(vegetation);
        BiomeFeaturesME.addCommonScorchedShrub(vegetation);
        BiomeFeaturesME.addShriveledShrubs(vegetation);
        BiomeFeaturesME.addDryPineTrees(vegetation);
        BiomeFeaturesME.addDryPineBushes(vegetation);
        BiomeFeaturesME.addToughBerries(vegetation);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings, 0.4f, true);
    }

    public static void createWhiteMountainsBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, int step) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        SpawnSettingsBuilderME.addMountainsMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        float temperature = 0.5f;

        BiomeFeaturesME.addDisks(undergroundOres);
        vegetation.add(VegetationPlacedFeatures.PATCH_LARGE_FERN);
        vegetation.add(VegetationPlacedFeatures.PATCH_GRASS_TAIGA);
        BiomeFeaturesME.addBracken(vegetation);
        BiomeFeaturesME.addWildGrass(vegetation);
        BiomeFeaturesME.addGrass(vegetation);
        BiomeFeaturesME.addSpruceBushes(vegetation);

        if(step != 2) {
            BiomeFeaturesME.addBrownBolete(vegetation);
            BiomeFeaturesME.addMorsel(vegetation);
            BiomeFeaturesME.addWhiteMushroom(vegetation);
            BiomeFeaturesME.addWildBeetroot(vegetation);
            BiomeFeaturesME.addWildPotato(vegetation);
            vegetation.add(VegetationPlacedFeatures.FLOWER_DEFAULT);
            vegetation.add(VegetationPlacedFeatures.BROWN_MUSHROOM_NORMAL);
            vegetation.add(VegetationPlacedFeatures.RED_MUSHROOM_NORMAL);
            vegetation.add(VegetationPlacedFeatures.PATCH_BERRY_RARE);
        }

        if(step == 0) {
            BiomeFeaturesME.addLarchTrees(vegetation);
            BiomeFeaturesME.addPineTrees(vegetation);
            BiomeFeaturesME.addScarceSpruceTrees(vegetation);
            BiomeFeaturesME.addRareLebethronTrees(vegetation);
        } else if(step == 1) {
            BiomeFeaturesME.addVeryRareLebethronTrees(vegetation);
            BiomeFeaturesME.addRareLarchTrees(vegetation);
            BiomeFeaturesME.addSparsePineTrees(vegetation);
            BiomeFeaturesME.addRareSpruceTrees(vegetation);
        } else if(step == 2) {
            BiomeFeaturesME.addVeryRareSpruceTrees(vegetation);
            temperature = 0f;
        }

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings, temperature, true);
    }

    public static void createWoodlandRealmBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, int step) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        SpawnSettingsBuilderME.addWolves(spawnSettings);
        SpawnSettingsBuilderME.addDeer(spawnSettings);

        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addMirkwoodVegetation(generationSettings);
        BiomeFeaturesME.addGreenShrub(vegetation);
        BiomeFeaturesME.addIvyGrowth(vegetation);
        BiomeFeaturesME.addBracken(vegetation);
        BiomeFeaturesME.addNettles(vegetation);
        BiomeFeaturesME.addForestMoss(vegetation);
        BiomeFeaturesME.addFlowerGreenJewel(vegetation);
        BiomeFeaturesME.addGravelOre(vegetation);
        BiomeFeaturesME.addLimestoneBoulder(vegetation);
        vegetation.add(VegetationPlacedFeatures.FLOWER_FOREST_FLOWERS);
        BiomeFeaturesME.addRareMorsel(vegetation);
        BiomeFeaturesME.addWheatGrass(vegetation);
        if(step == 0) { // Forest
            BiomeFeaturesME.addFireflyBushes(vegetation);
            BiomeFeaturesME.addBeechTrees(vegetation);
            BiomeFeaturesME.addRareBeechTrees(vegetation);
            BiomeFeaturesME.addSmallMirkwoodTrees(vegetation);
            BiomeFeaturesME.addUncommonMirkwoodTrees(vegetation);
            BiomeFeaturesME.addAspenTrees(vegetation);
            BiomeFeaturesME.addMapleTree(vegetation);
            BiomeFeaturesME.addYellowMapleTree(vegetation);
            BiomeFeaturesME.addOrangeMapleTree(vegetation);
            BiomeFeaturesME.addVeryRareMegaMirkwoodTrees(vegetation);
        } else if(step == 1) { // Glade
            SpawnSettingsBuilderME.addFarmAnimals(spawnSettings);
            vegetation.add(VegetationPlacedFeatures.TREES_PLAINS);
            vegetation.add(VegetationPlacedFeatures.FLOWER_DEFAULT);
            BiomeFeaturesME.addRedHeather(vegetation);
            BiomeFeaturesME.addRedFlowers(vegetation);
            BiomeFeaturesME.addPoppyFlower(vegetation);
            BiomeFeaturesME.addTuftGrass(vegetation);
            BiomeFeaturesME.addMixedWildWheatPatch(vegetation);
        } else if(step == 2) {
            SpawnSettingsBuilderME.addRabbits(spawnSettings);
            BiomeFeaturesME.addFireflyBushes(vegetation);
            BiomeFeaturesME.addRedFlowers(vegetation);
            BiomeFeaturesME.addRedHeather(vegetation);
            BiomeFeaturesME.addDryHeather(vegetation);
            BiomeFeaturesME.addSedums(vegetation);
            BiomeFeaturesME.addCommonMapleTrees(vegetation);
            BiomeFeaturesME.addCommonAspenTrees(vegetation);
            BiomeFeaturesME.addBeechTrees(vegetation);
            BiomeFeaturesME.addRareBeechTrees(vegetation);
            BiomeFeaturesME.addSmallMirkwoodTrees(vegetation);
            BiomeFeaturesME.addSparseMirkwoodTrees(vegetation);
        }

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createOceanBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        SpawnSettingsBuilderME.addOceanAnimals(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addOceanVegetation(generationSettings);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createOceanCoastBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        SpawnSettingsBuilderME.addOceanAnimals(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addOceanVegetation(generationSettings);
        BiomeFeaturesME.addCoastalFoliage(vegetation);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createPelennorFields(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, int step) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        SpawnSettingsBuilderME.addPlainsMobs(spawnSettings);
        SpawnSettingsBuilderME.addCats(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        vegetation.add(VegetationPlacedFeatures.TREES_PLAINS);

        if(step == 0){
            addGondorVegetation(generationSettings);
            BiomeFeaturesME.addDioriteBoulder(vegetation);
            BiomeFeaturesME.addRareWilderGrass(vegetation);
            BiomeFeaturesME.addWildLettuce(vegetation);
            BiomeFeaturesME.addTuftGrass(vegetation);
            BiomeFeaturesME.addSedum(vegetation);
            BiomeFeaturesME.addVeryRareLebethronTrees(vegetation);

        } else if(step == 1){
            SpawnSettingsBuilderME.addRabbits(spawnSettings);

            BiomeFeaturesME.addWildWheatField(vegetation);
            BiomeFeaturesME.addMixedWildWheatPatch(vegetation);
            BiomeFeaturesME.addRareMegaOakTrees(vegetation);
            BiomeFeaturesME.addGrassyDirtOre(vegetation);
            BiomeFeaturesME.addDryDirtOre(vegetation);
            BiomeFeaturesME.addDryGrowth(vegetation);
        }

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createPondBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        SpawnSettingsBuilderME.addSwan(spawnSettings);
        SpawnSettingsBuilderME.addSwampMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addPondVegetation(generationSettings);
        BiomeFeaturesME.addAbundantWaterDelta(undergroundOres);
        BiomeFeaturesME.addRiverSand(undergroundOres);
        BiomeFeaturesME.addAbundantMudOre(undergroundOres);
        BiomeFeaturesME.addBlueOrchidFlower(undergroundOres);
        BiomeFeaturesME.addBulrushAndCattail(undergroundOres);
        BiomeFeaturesME.addDuckweed(undergroundOres);
        BiomeFeaturesME.addLargeLilyPad(undergroundOres);
        BiomeFeaturesME.addLargeFloweringLilyPad(undergroundOres);
        BiomeFeaturesME.addLilyPads(undergroundOres);
        BiomeFeaturesME.addFloweringLilyPads(undergroundOres);
        BiomeFeaturesME.addSmallLilyPads(undergroundOres);
        BiomeFeaturesME.addSmallFloweringLilyPads(undergroundOres);
        BiomeFeaturesME.addWheatGrass(undergroundOres);
        vegetation.add(VegetationPlacedFeatures.TREES_PLAINS);
        BiomeFeaturesME.addCommonWillowTrees(vegetation);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createMangrovePondBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        SpawnSettingsBuilderME.addRiverAnimals(spawnSettings);
        SpawnSettingsBuilderME.addSwan(spawnSettings);
        SpawnSettingsBuilderME.addSwampMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addPondVegetation(generationSettings);
        BiomeFeaturesME.addAbundantWaterDelta(undergroundOres);
        BiomeFeaturesME.addRiverSand(undergroundOres);
        BiomeFeaturesME.addAbundantMudOre(undergroundOres);
        BiomeFeaturesME.addBlueOrchidFlower(undergroundOres);
        BiomeFeaturesME.addCommonTallGrass(undergroundOres);
        BiomeFeaturesME.addBulrushAndCattail(undergroundOres);
        BiomeFeaturesME.addDuckweed(undergroundOres);
        BiomeFeaturesME.addWheatGrass(undergroundOres);
        vegetation.add(VegetationPlacedFeatures.TREES_MANGROVE);
        vegetation.add(VegetationPlacedFeatures.PATCH_SUGAR_CANE_SWAMP);
        BiomeFeaturesME.addCommonWillowTrees(vegetation);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createFrozenOceanBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        SpawnSettingsBuilderME.addOceanAnimals(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addOceanVegetation(generationSettings);
        BiomeFeaturesME.addFloatingIce(vegetation);
        BiomeFeaturesME.addFrozenGrass(vegetation);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createFrozenPond(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        SpawnSettingsBuilderME.addColdWaterAnimals(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));
        
        BiomeFeaturesME.addDisks(undergroundOres);

        vegetation.add(OceanPlacedFeatures.KELP_COLD);
        vegetation.add(OceanPlacedFeatures.SEAGRASS_NORMAL);
        BiomeFeaturesME.addFloatingIce(vegetation);
        BiomeFeaturesME.addFrozenGrass(vegetation);
        BiomeFeaturesME.addFrozenShrub(vegetation);
        BiomeFeaturesME.addFrozenGrowth(vegetation);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings, -0.1f, true);
    }

    public static void addNordicTrees(GenerationSettings.LookupBackedBuilder generationSettings) {
        BiomeFeaturesME.addLarchTrees(vegetation);
        BiomeFeaturesME.addSparsePineTrees(vegetation);
        BiomeFeaturesME.addRareSpruceTrees(vegetation);
    }

    public static void addMirkwoodTrees(GenerationSettings.LookupBackedBuilder generationSettings) {
        BiomeFeaturesME.addMirkwoodTrees(vegetation);
    }

    public static void addMegaMirkwoodTrees(GenerationSettings.LookupBackedBuilder generationSettings) {
        BiomeFeaturesME.addMegaMirkwoodTrees(vegetation);
    }

    public static void addDefaultVegetation(GenerationSettings.LookupBackedBuilder generationSettings) {
        BiomeFeaturesME.addDisks(undergroundOres);
        BiomeFeaturesME.addGrass(vegetation);
        vegetation.add(VegetationPlacedFeatures.FLOWER_DEFAULT);
        vegetation.add(VegetationPlacedFeatures.BROWN_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.RED_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.PATCH_SUGAR_CANE);
        vegetation.add(VegetationPlacedFeatures.PATCH_PUMPKIN);
        BiomeFeaturesME.addStoneBoulder(vegetation);
        BiomeFeaturesME.addWildBeetroot(vegetation);
        BiomeFeaturesME.addWildCucumber(vegetation);
        BiomeFeaturesME.addWildFlax(vegetation);
        BiomeFeaturesME.addWildGarlic(vegetation);
    }

    public static void addArthedainVegetation(GenerationSettings.LookupBackedBuilder generationSettings) {
        BiomeFeaturesME.addDisks(undergroundOres);
        BiomeFeaturesME.addGrass(vegetation);
        BiomeFeaturesME.addWildGrass(vegetation);
        BiomeFeaturesME.addTurfOre(vegetation);
        BiomeFeaturesME.addGrassySiltOre(vegetation);
        vegetation.add(VegetationPlacedFeatures.PATCH_TALL_GRASS_2);
        vegetation.add(VegetationPlacedFeatures.FLOWER_DEFAULT);
        vegetation.add(VegetationPlacedFeatures.BROWN_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.RED_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.PATCH_SUGAR_CANE);
        vegetation.add(VegetationPlacedFeatures.PATCH_PUMPKIN);
        BiomeFeaturesME.addAthelas(vegetation);
        BiomeFeaturesME.addVeryRareDryGrass(vegetation);
        BiomeFeaturesME.addBushes(vegetation);
        BiomeFeaturesME.addCommonWheatGrass(vegetation);
        BiomeFeaturesME.addRareWilderGrass(vegetation);
        BiomeFeaturesME.addWildGarlic(vegetation);
        BiomeFeaturesME.addWildLeek(vegetation);
        BiomeFeaturesME.addWildLettuce(vegetation);
        BiomeFeaturesME.addWildPotato(vegetation);
        BiomeFeaturesME.addGabbroBoulder(vegetation);
        BiomeFeaturesME.addGravelOre(vegetation);
    }

    public static void addEriadorVegetation(GenerationSettings.LookupBackedBuilder generationSettings) {
        BiomeFeaturesME.addDisks(undergroundOres);
        BiomeFeaturesME.addGrass(vegetation);
        vegetation.add(VegetationPlacedFeatures.FLOWER_DEFAULT);
        vegetation.add(VegetationPlacedFeatures.BROWN_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.RED_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.PATCH_SUGAR_CANE);
        vegetation.add(VegetationPlacedFeatures.PATCH_PUMPKIN);
        vegetation.add(VegetationPlacedFeatures.TREES_PLAINS);
        BiomeFeaturesME.addFlowerGreenJewel(vegetation);
        BiomeFeaturesME.addWildFlax(vegetation);
        BiomeFeaturesME.addWildGrass(vegetation);
        BiomeFeaturesME.addWildLeek(vegetation);
        BiomeFeaturesME.addWildLettuce(vegetation);
        BiomeFeaturesME.addWildOnion(vegetation);
        BiomeFeaturesME.addGraniteBoulder(vegetation);
    }

    public static void addForodwaithVegetation(GenerationSettings.LookupBackedBuilder generationSettings) {
        BiomeFeaturesME.addDisks(undergroundOres);
        BiomeFeaturesME.addToughBerriesRare(vegetation);
    }

    public static void addGondorVegetation(GenerationSettings.LookupBackedBuilder generationSettings) {
        BiomeFeaturesME.addDisks(undergroundOres);
        BiomeFeaturesME.addGrass(vegetation);
        vegetation.add(VegetationPlacedFeatures.FLOWER_DEFAULT);
        vegetation.add(VegetationPlacedFeatures.BROWN_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.RED_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.PATCH_SUGAR_CANE);
        vegetation.add(VegetationPlacedFeatures.PATCH_PUMPKIN);
        BiomeFeaturesME.addHogweeds(vegetation);
        BiomeFeaturesME.addSparseWhiteLavender(vegetation);
        BiomeFeaturesME.addWildCarrot(vegetation);
        BiomeFeaturesME.addWildOnion(vegetation);
        BiomeFeaturesME.addWildGarlic(vegetation);
        BiomeFeaturesME.addWildFlax(vegetation);
    }

    public static void addHaradVegetation(GenerationSettings.LookupBackedBuilder generationSettings) {
        BiomeFeaturesME.addDisks(undergroundOres);
        BiomeFeaturesME.addGrass(vegetation);
        BiomeFeaturesME.addTemperateGrass(vegetation);
        vegetation.add(VegetationPlacedFeatures.PATCH_TALL_GRASS);
        vegetation.add(VegetationPlacedFeatures.PATCH_SUGAR_CANE);
        BiomeFeaturesME.addHaradFoliage(vegetation);
        BiomeFeaturesME.addDryDirtOre(vegetation);
        BiomeFeaturesME.addWildBellPepper(vegetation);
        BiomeFeaturesME.addWildTomato(vegetation);
    }

    public static void addHaradDesertVegetation(GenerationSettings.LookupBackedBuilder generationSettings) {
        BiomeFeaturesME.addDisks(undergroundOres);
        vegetation.add(VegetationPlacedFeatures.PATCH_SUGAR_CANE);
        BiomeFeaturesME.addGrass(vegetation);
        BiomeFeaturesME.addDryGrass(vegetation);
        BiomeFeaturesME.addSmallDryShrub(vegetation);
        BiomeFeaturesME.addSandStoneBoulder(vegetation);
    }

    public static void addLothlorienVegetation(GenerationSettings.LookupBackedBuilder generationSettings) {
        BiomeFeaturesME.addDisks(undergroundOres);
        BiomeFeaturesME.addGrass(vegetation);
        vegetation.add(VegetationPlacedFeatures.FLOWER_MEADOW);
        vegetation.add(VegetationPlacedFeatures.BROWN_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.RED_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.PATCH_SUGAR_CANE);
        vegetation.add(VegetationPlacedFeatures.PATCH_PUMPKIN);
        vegetation.add(VegetationPlacedFeatures.TREES_PLAINS);
        BiomeFeaturesME.addGravelOre(vegetation);
        BiomeFeaturesME.addRootedDirtOre(vegetation);
        BiomeFeaturesME.addDryDirtOre(vegetation);
        BiomeFeaturesME.addLorienPodzolOre(vegetation);
        BiomeFeaturesME.addAbundantPodzolOre(vegetation);
        BiomeFeaturesME.addDirtyRootsOre(vegetation);
        BiomeFeaturesME.addGalonnBoulder(vegetation);
        BiomeFeaturesME.addTuftGrass(vegetation);
        BiomeFeaturesME.addHogweeds(vegetation);
        BiomeFeaturesME.addRareWilderGrass(vegetation);
        BiomeFeaturesME.addMallornBushes(vegetation);
        BiomeFeaturesME.addOakBushes(vegetation);
        BiomeFeaturesME.addBushes(vegetation);
        BiomeFeaturesME.addElanor(vegetation);
        BiomeFeaturesME.addNiphredil(vegetation);
        BiomeFeaturesME.addYellowFlower(vegetation);
        BiomeFeaturesME.addWildCarrot(vegetation);
        BiomeFeaturesME.addWildFlax(vegetation);
        BiomeFeaturesME.addWildLettuce(vegetation);
        BiomeFeaturesME.addWildOnion(vegetation);
    }

    public static void addMirkwoodVegetation(GenerationSettings.LookupBackedBuilder generationSettings) {
        BiomeFeaturesME.addDisks(undergroundOres);
        BiomeFeaturesME.addGrass(vegetation);
        vegetation.add(VegetationPlacedFeatures.PATCH_TALL_GRASS_2);
        vegetation.add(VegetationPlacedFeatures.PATCH_LARGE_FERN);
        vegetation.add(VegetationPlacedFeatures.PATCH_GRASS_TAIGA);
        vegetation.add(VegetationPlacedFeatures.BROWN_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.RED_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.PATCH_BERRY_RARE);
        generationSettings.feature(GenerationStep.Feature.TOP_LAYER_MODIFICATION, VegetationPlacedFeatureRegistryME.MIRKWOOD_VINES);
        BiomeFeaturesME.addMirkwoodVines(vegetation);
        BiomeFeaturesME.addShriveledShrubs(vegetation);
        BiomeFeaturesME.addFallenMirkwoodLeaves(vegetation);
        BiomeFeaturesME.addCoarseDirtOre(vegetation);
        BiomeFeaturesME.addFalseOatgrass(vegetation);
        BiomeFeaturesME.addOldPodzolOre(vegetation);
        BiomeFeaturesME.addWildGrass(vegetation);
        BiomeFeaturesME.addOakBushes(vegetation);
        BiomeFeaturesME.addWildFlax(vegetation);
        BiomeFeaturesME.addWildGarlic(vegetation);
        BiomeFeaturesME.addWildOnion(vegetation);
    }

    public static void addMordorVegetation(GenerationSettings.LookupBackedBuilder generationSettings) {
        BiomeFeaturesME.addGrimGrass(vegetation);
        BiomeFeaturesME.addCommonScorchedGrass(vegetation);
        BiomeFeaturesME.addCommonScorchedShrub(vegetation);
        BiomeFeaturesME.addAshBlockOre(vegetation);
        BiomeFeaturesME.addCommonToughBerries(vegetation);
        BiomeFeaturesME.addMordorBrambles(vegetation);
    }

    public static void addNordicVegetation(GenerationSettings.LookupBackedBuilder generationSettings) {
        BiomeFeaturesME.addDisks(undergroundOres);
        vegetation.add(VegetationPlacedFeatures.PATCH_LARGE_FERN);
        vegetation.add(VegetationPlacedFeatures.PATCH_GRASS_TAIGA);
        vegetation.add(VegetationPlacedFeatures.FLOWER_DEFAULT);
        vegetation.add(VegetationPlacedFeatures.BROWN_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.RED_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.PATCH_BERRY_RARE);
        BiomeFeaturesME.addBracken(vegetation);
        BiomeFeaturesME.addWildGrass(vegetation);
        BiomeFeaturesME.addGrass(vegetation);
        BiomeFeaturesME.addBrownBolete(vegetation);
        BiomeFeaturesME.addMorsel(vegetation);
        BiomeFeaturesME.addWhiteMushroom(vegetation);
        BiomeFeaturesME.addWildBeetroot(vegetation);
        BiomeFeaturesME.addWildPotato(vegetation);
    }

    public static void addMountainVegetation(GenerationSettings.LookupBackedBuilder generationSettings) {
        vegetation.add(VegetationPlacedFeatures.PATCH_LARGE_FERN);
        vegetation.add(VegetationPlacedFeatures.PATCH_GRASS_TAIGA);
        vegetation.add(VegetationPlacedFeatures.FLOWER_DEFAULT);
        vegetation.add(VegetationPlacedFeatures.BROWN_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.RED_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.PATCH_BERRY_RARE);
        BiomeFeaturesME.addWildGrass(vegetation);
        BiomeFeaturesME.addBrownBolete(vegetation);
        BiomeFeaturesME.addMorsel(vegetation);
        BiomeFeaturesME.addWhiteMushroom(vegetation);
        BiomeFeaturesME.addWildBeetroot(vegetation);
        BiomeFeaturesME.addWildPotato(vegetation);
    }

    public static void addOasisVegetation(GenerationSettings.LookupBackedBuilder generationSettings) {
        BiomeFeaturesME.addDisks(undergroundOres);
        vegetation.add(OceanPlacedFeatures.KELP_WARM);
        vegetation.add(OceanPlacedFeatures.SEAGRASS_WARM);
        BiomeFeaturesME.addGrass(vegetation);
        BiomeFeaturesME.addSandOre(vegetation);
        BiomeFeaturesME.addBeachGrass(vegetation);
        BiomeFeaturesME.addTemperateGrass(vegetation);
        vegetation.add(VegetationPlacedFeatures.FLOWER_DEFAULT);
        vegetation.add(VegetationPlacedFeatures.PATCH_SUGAR_CANE);
        vegetation.add(VegetationPlacedFeatures.PATCH_PUMPKIN);
        BiomeFeaturesME.addHaradFoliage(vegetation);
        BiomeFeaturesME.addWildBellPepper(vegetation);
        BiomeFeaturesME.addWildTomato(vegetation);
        BiomeFeaturesME.addPalmTrees(vegetation);
        BiomeFeaturesME.addWhitePalmTree(vegetation);
    }

    public static void addOceanVegetation(GenerationSettings.LookupBackedBuilder generationSettings) {
        BiomeFeaturesME.addDisks(undergroundOres);
        vegetation.add(OceanPlacedFeatures.KELP_COLD);
        vegetation.add(OceanPlacedFeatures.SEAGRASS_NORMAL);
        BiomeFeaturesME.addGrass(vegetation);
        vegetation.add(VegetationPlacedFeatures.FLOWER_DEFAULT);
        vegetation.add(VegetationPlacedFeatures.PATCH_SUGAR_CANE);
        vegetation.add(VegetationPlacedFeatures.PATCH_PUMPKIN);
        BiomeFeaturesME.addReedsFoliage(vegetation);
    }

    public static void addPondVegetation(GenerationSettings.LookupBackedBuilder generationSettings) {
        BiomeFeaturesME.addDisks(undergroundOres);
        BiomeFeaturesME.addRushes(vegetation);
        generationSettings.feature(GenerationStep.Feature.TOP_LAYER_MODIFICATION, VegetationPlacedFeatureRegistryME.WILLOW_VINES);
        BiomeFeaturesME.addWillowVines(vegetation);
        vegetation.add(VegetationPlacedFeatures.FLOWER_DEFAULT);
        vegetation.add(VegetationPlacedFeatures.PATCH_SUGAR_CANE);
        vegetation.add(VegetationPlacedFeatures.PATCH_PUMPKIN);
        BiomeFeaturesME.addReedsFoliage(vegetation);
        BiomeFeaturesME.addFireflyBushes(vegetation);
    }

    public static void addRivendellVegetation(GenerationSettings.LookupBackedBuilder generationSettings) {
        BiomeFeaturesME.addDisks(undergroundOres);
        BiomeFeaturesME.addGrass(vegetation);
        vegetation.add(VegetationPlacedFeatures.BROWN_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.RED_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.PATCH_SUGAR_CANE);
        vegetation.add(VegetationPlacedFeatures.PATCH_PUMPKIN);
        BiomeFeaturesME.addFlowerMeadow(vegetation);
        BiomeFeaturesME.addRareWilderGrass(vegetation);
        BiomeFeaturesME.addWheatGrass(vegetation);
        BiomeFeaturesME.addTuftGrass(vegetation);
        BiomeFeaturesME.addGravelOre(vegetation);
        BiomeFeaturesME.addWildLeek(vegetation);
        BiomeFeaturesME.addWildLettuce(vegetation);
        BiomeFeaturesME.addWildOnion(vegetation);
        BiomeFeaturesME.addWildFlax(vegetation);
        BiomeFeaturesME.addLimestoneBoulder(vegetation);
    }

    public static void addRhunVegetation(GenerationSettings.LookupBackedBuilder generationSettings) {
        BiomeFeaturesME.addDisks(undergroundOres);
        BiomeFeaturesME.addGrass(vegetation);
        vegetation.add(VegetationPlacedFeatures.TREES_PLAINS);
        vegetation.add(VegetationPlacedFeatures.FLOWER_DEFAULT);
        vegetation.add(VegetationPlacedFeatures.BROWN_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.RED_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.PATCH_SUGAR_CANE);
        vegetation.add(VegetationPlacedFeatures.PATCH_PUMPKIN);
        BiomeFeaturesME.addWildCarrot(vegetation);
        BiomeFeaturesME.addWildBellPepper(vegetation);
        BiomeFeaturesME.addWildFlax(vegetation);
        BiomeFeaturesME.addWildLettuce(vegetation);
        BiomeFeaturesME.addWildOnion(vegetation);
    }

    public static void addShireVegetation(GenerationSettings.LookupBackedBuilder generationSettings) {
        BiomeFeaturesME.addDisks(undergroundOres);
        BiomeFeaturesME.addGrass(vegetation);
        BiomeFeaturesME.addWheatGrass(vegetation);
        BiomeFeaturesME.addTuftGrass(vegetation);
        vegetation.add(VegetationPlacedFeatures.FLOWER_DEFAULT);
        vegetation.add(VegetationPlacedFeatures.BROWN_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.RED_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.PATCH_SUGAR_CANE);
        vegetation.add(VegetationPlacedFeatures.PATCH_PUMPKIN);
        BiomeFeaturesME.addWilderGrass(vegetation);
        BiomeFeaturesME.addGravelOre(vegetation);
        BiomeFeaturesME.addWildBeetroot(vegetation);
        BiomeFeaturesME.addWildCarrot(vegetation);
        BiomeFeaturesME.addWildCucumber(vegetation);
        BiomeFeaturesME.addWildGarlic(vegetation);
        BiomeFeaturesME.addWildLeek(vegetation);
        BiomeFeaturesME.addWildLettuce(vegetation);
        BiomeFeaturesME.addWildOnion(vegetation);
        BiomeFeaturesME.addWildPipeweed(vegetation);
        BiomeFeaturesME.addWildPotato(vegetation);
        BiomeFeaturesME.addStoneBoulder(vegetation);
    }

    public static void registerBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, SpawnSettings.Builder spawnSettings, GenerationSettings.LookupBackedBuilder generationSettings) {
        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings, 0.5f, true);
    }

    public static void registerBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, SpawnSettings.Builder spawnSettings, GenerationSettings.LookupBackedBuilder generationSettings, float temperature, boolean precipitation, boolean... removeDefaultOres) {
        if(removeDefaultOres.length == 0) {
            undergroundOres.add(OrePlacedFeatures.ORE_DIRT);
            undergroundOres.add(OrePlacedFeatures.ORE_GRAVEL);
            undergroundOres.add(OrePlacedFeatures.ORE_GRANITE_UPPER);
            undergroundOres.add(OrePlacedFeatures.ORE_GRANITE_LOWER);
            undergroundOres.add(OrePlacedFeatures.ORE_DIORITE_UPPER);
            undergroundOres.add(OrePlacedFeatures.ORE_DIORITE_LOWER);
            undergroundOres.add(OrePlacedFeatures.ORE_ANDESITE_UPPER);
            undergroundOres.add(OrePlacedFeatures.ORE_ANDESITE_LOWER);
            undergroundOres.add(OrePlacedFeatures.ORE_TUFF);
        }

        undergroundOres.add(OrePlacedFeatures.ORE_COAL_UPPER);
        vegetation.add(UndergroundPlacedFeatures.GLOW_LICHEN);

        DefaultBiomeFeatures.addFrozenTopLayer(generationSettings);

        surfaceStructures = surfaceStructures.stream().sorted(Comparator.comparing(a -> a.getValue().toString())).toList();
        vegetation = vegetation.stream().sorted(Comparator.comparing(a -> a.getValue().toString())).toList();
        for(int i = 0; i < vegetation.size() - 1; i++) {
            if(vegetation.get(i).getValue().toString().equals(vegetation.get(i + 1).getValue().toString())) {
                throw new IllegalStateException("Duplicate value in list for: " + vegetation.get(i).getValue().toString());
            }
        }
        for (RegistryKey<PlacedFeature> feature: surfaceStructures) {
            generationSettings.feature(GenerationStep.Feature.SURFACE_STRUCTURES, feature);
        }
        for (RegistryKey<PlacedFeature> feature: vegetation) {
            generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, feature);
        }
        for (RegistryKey<PlacedFeature> feature: undergroundOres.stream().sorted(Comparator.comparing(a -> a.getValue().toString())).toList()) {
            generationSettings.feature(GenerationStep.Feature.UNDERGROUND_ORES, feature);
        }

        BiomeColorsDTO biomeColorsDTO = MapBiomeData.getBiome(biomeRegistryKey).getBiomeColors();


        SpawnSettingsBuilderME.addNpcs(spawnSettings);

        Biome biome = (new Biome.Builder())
                .precipitation(precipitation)
                .temperature(temperature)
                .downfall(0.5F)
                .effects((new BiomeEffects.Builder())
                        .skyColor(biomeColorsDTO.skyColor)
                        .fogColor(biomeColorsDTO.fogColor)
                        .waterColor(biomeColorsDTO.waterColor)
                        .waterFogColor(biomeColorsDTO.waterFogColor)
                        .grassColor(biomeColorsDTO.grassColor)
                        .foliageColor(biomeColorsDTO.foliageColor)
                        .build())
                .spawnSettings(spawnSettings.build())
                .generationSettings(generationSettings.build())
                .build();
        context.register(biomeRegistryKey, biome);

        surfaceStructures = new ArrayList<>();
        vegetation = new ArrayList<>();
        undergroundOres = new ArrayList<>();
    }

    public static void registerBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, SpawnSettings.Builder spawnSettings, GenerationSettings.LookupBackedBuilder generationSettings, BiomeParticleConfig particleConfig, float temperature, boolean precipitation, boolean... removeDefaultOres) {
        if(removeDefaultOres.length == 0) {
            undergroundOres.add(OrePlacedFeatures.ORE_DIRT);
            undergroundOres.add(OrePlacedFeatures.ORE_GRAVEL);
            undergroundOres.add(OrePlacedFeatures.ORE_GRANITE_UPPER);
            undergroundOres.add(OrePlacedFeatures.ORE_GRANITE_LOWER);
            undergroundOres.add(OrePlacedFeatures.ORE_DIORITE_UPPER);
            undergroundOres.add(OrePlacedFeatures.ORE_DIORITE_LOWER);
            undergroundOres.add(OrePlacedFeatures.ORE_ANDESITE_UPPER);
            undergroundOres.add(OrePlacedFeatures.ORE_ANDESITE_LOWER);
            undergroundOres.add(OrePlacedFeatures.ORE_TUFF);
        }

        undergroundOres.add(OrePlacedFeatures.ORE_COAL_UPPER);
        vegetation.add(UndergroundPlacedFeatures.GLOW_LICHEN);

        DefaultBiomeFeatures.addFrozenTopLayer(generationSettings);

        surfaceStructures = surfaceStructures.stream().sorted(Comparator.comparing(a -> a.getValue().toString())).toList();
        vegetation = vegetation.stream().sorted(Comparator.comparing(a -> a.getValue().toString())).toList();
        for(int i = 0; i < vegetation.size() - 1; i++) {
            if(vegetation.get(i).getValue().toString().equals(vegetation.get(i + 1).getValue().toString())) {
                throw new IllegalStateException("Duplicate value in list for: " + vegetation.get(i).getValue().toString());
            }
        }
        for (RegistryKey<PlacedFeature> feature: surfaceStructures) {
            generationSettings.feature(GenerationStep.Feature.SURFACE_STRUCTURES, feature);
        }
        for (RegistryKey<PlacedFeature> feature: vegetation) {
            generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, feature);
        }
        for (RegistryKey<PlacedFeature> feature: undergroundOres.stream().sorted(Comparator.comparing(a -> a.getValue().toString())).toList()) {
            generationSettings.feature(GenerationStep.Feature.UNDERGROUND_ORES, feature);
        }

        BiomeColorsDTO biomeColorsDTO = MapBiomeData.getBiome(biomeRegistryKey).getBiomeColors();

        Biome biome = (new Biome.Builder())
                .precipitation(precipitation)
                .temperature(temperature)
                .downfall(0.5F)
                .effects((new BiomeEffects.Builder())
                        .skyColor(biomeColorsDTO.skyColor)
                        .fogColor(biomeColorsDTO.fogColor)
                        .waterColor(biomeColorsDTO.waterColor)
                        .waterFogColor(biomeColorsDTO.waterFogColor)
                        .grassColor(biomeColorsDTO.grassColor)
                        .foliageColor(biomeColorsDTO.foliageColor)
                        .particleConfig(particleConfig)
                        .build())
                .spawnSettings(spawnSettings.build())
                .generationSettings(generationSettings.build())
                .build();
        context.register(biomeRegistryKey, biome);

        surfaceStructures = new ArrayList<>();
        vegetation = new ArrayList<>();
        undergroundOres = new ArrayList<>();
    }
}
