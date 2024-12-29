package net.jukoz.me.world.biomes.surface;

import net.jukoz.me.world.biomes.BiomeColorsDTO;
import net.jukoz.me.world.biomes.MEBiomeKeys;
import net.jukoz.me.world.features.misc.ModMiscPlacedFeatures;
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
    private static List<RegistryKey<PlacedFeature>> surfaceStructures = new ArrayList<>();;
    private static List<RegistryKey<PlacedFeature>> vegetation = new ArrayList<>();;
    private static ArrayList<RegistryKey<PlacedFeature>> undergroundOres = new ArrayList<>();;
    
    public static void bootstrap(Registerable<Biome> context) {
        createAnduinBiome(context, MEBiomeKeys.ANDUIN_VALES, false);
        createAnduinBiome(context, MEBiomeKeys.ANDUIN_VALES_FOREST, true);
        createAnorienBiome(context, MEBiomeKeys.ANORIEN);
        createGondorRiverSideBiome(context, MEBiomeKeys.ANORIEN_RIVERSIDE);
        createAnorienBiome(context, MEBiomeKeys.ANORIEN_FOOTHILLS);
        createBarrowDownsBiome(context, MEBiomeKeys.BARROW_DOWNS);
        createBeleriandIslandBiome(context, MEBiomeKeys.BELERIAND_ISLAND);
        createBelfalasBiome(context, MEBiomeKeys.BELFALAS, 0);
        createBelfalasShoresBiome(context, MEBiomeKeys.BELFALAS_BEACH);
        createBelfalasBiome(context, MEBiomeKeys.BELFALAS_HILLS, 1);
        createBelfalasBiome(context, MEBiomeKeys.BELFALAS_FOREST, 2);
        createBlackRootVale(context, MEBiomeKeys.BLACKROOT_VALE, false);
        createBlueMountainsBiome(context, MEBiomeKeys.BLUE_MOUNTAINS_FOOTHILLS, 0);
        createBlueMountainsBiome(context, MEBiomeKeys.BLUE_MOUNTAINS_BASE, 1);
        createBlueMountainsBiome(context, MEBiomeKeys.BLUE_MOUNTAINS, 2);
        createBlueMountainsBiome(context, MEBiomeKeys.BLUE_MOUNTAINS_PEAKS, 3);
        createMordorWastesBiome(context, MEBiomeKeys.BROWN_LANDS);
        createMistyMountainsBiome(context, MEBiomeKeys.CARADHRAS_BASE, 0);
        createMistyMountainsBiome(context, MEBiomeKeys.CARADHRAS, 1);
        createMistyMountainsBiome(context, MEBiomeKeys.CARADHRAS_PEAKS, 2);
        createMistyMountainsBiome(context, MEBiomeKeys.CELEBDIL_BASE, 0);
        createMistyMountainsBiome(context, MEBiomeKeys.CELEBDIL, 1);
        createMistyMountainsBiome(context, MEBiomeKeys.CELEBDIL_PEAKS, 2);
        createCorsairCoastBiome(context, MEBiomeKeys.CORSAIR_COASTS);
        createMordorWastesBiome(context, MEBiomeKeys.DAGORLAD);
        createDaleBiome(context, MEBiomeKeys.DALE, 0);
        createDaleBiome(context, MEBiomeKeys.DALE_FOREST, 1);
        createDaleBiome(context, MEBiomeKeys.DALE_HILL, 2);
        createDaleBiome(context, MEBiomeKeys.DALE_CITY, 0);
        createDaleBiome(context, MEBiomeKeys.DALE_RIVERSIDE, 0);
        createAnduinBiome(context, MEBiomeKeys.DARK_ANDUIN_VALES, false);
        createMirkwoodBiome(context, MEBiomeKeys.DARK_MIRKWOOD, true, true);
        createMirkwoodBiome(context, MEBiomeKeys.DARK_MIRKWOOD_EDGE, false, true);
        createDeadMarshesBiome(context, MEBiomeKeys.DEAD_MARSHES);
        createDeadMarshesWaterBiome(context, MEBiomeKeys.DEAD_MARSHES_WATER);
        createMirkwoodBiome(context, MEBiomeKeys.DOL_GULDUR, false, true);
        createMirkwoodBiome(context, MEBiomeKeys.DOL_GULDUR_HILL, false, true);
        createDorwinionBiome(context, MEBiomeKeys.DORWINION);
        createDorwinionHillsBiome(context, MEBiomeKeys.DORWINION_HILLS);
        createDunlandFoothillsBiome(context, MEBiomeKeys.DUNLAND_FOOTHILLS);
        createRhunBiome(context, MEBiomeKeys.EAST_BIGHT);
        createNurnBiome(context, MEBiomeKeys.EASTERN_NURN, 0);
        createRhunBiome(context, MEBiomeKeys.EASTERN_RHOVANION);
        createEmynMuilBiome(context, MEBiomeKeys.EMYN_MUIL);
        createEmynMuilBiome(context, MEBiomeKeys.EMYN_MUIL_CLIFFS);
        createEmynMuilBiome(context, MEBiomeKeys.EMYN_MUIL_PEAKS);
        createWastePondBiome(context, MEBiomeKeys.EMYN_MUIL_POND);
        createEnedwaithBiome(context, MEBiomeKeys.ENEDWAITH);
        createLonelyMountainBiome(context, MEBiomeKeys.LONELY_MOUNTAIN, 0);
        createLonelyMountainBiome(context, MEBiomeKeys.LONELY_MOUNTAIN_BASE, 1);
        createLonelyMountainBiome(context, MEBiomeKeys.LONELY_MOUNTAIN_PEAKS, 2);
        createLonelyMountainBiome(context, MEBiomeKeys.LONELY_MOUNTAIN_TAIGA, 1);
        createEregionBiome(context, MEBiomeKeys.EREGION);
        createSwampAnduin(context,MEBiomeKeys.ETHIR_ANDUIN);
        createFangornBiome(context, MEBiomeKeys.FANGORN);
        createMistyMountainsBiome(context, MEBiomeKeys.FANGORN_FOOTHILLS, 0);
        createMistyMountainsBiome(context, MEBiomeKeys.FANUIDHOL_BASE, 0);
        createMistyMountainsBiome(context, MEBiomeKeys.FANUIDHOL, 1);
        createMistyMountainsBiome(context, MEBiomeKeys.FANUIDHOL_PEAKS, 2);
        createForodwaithBiome(context, MEBiomeKeys.FORODWAITH);
        createOceanBiome(context, MEBiomeKeys.FROZEN_OCEAN);
        createFrozenPond(context, MEBiomeKeys.FROZEN_POND);
        createLindonBiome(context, MEBiomeKeys.GULF_OF_LHUN_CLIFFS);
        createLindonBiome(context, MEBiomeKeys.GULF_OF_LHUN_SHORES);
        createGondorBiome(context, MEBiomeKeys.GONDOR, 0);
        createGondorBiome(context, MEBiomeKeys.GONDOR_FOREST, 1);
        createGondorBiome(context, MEBiomeKeys.GONDOR_HILL, 2);
        createGorgorothBiome(context, MEBiomeKeys.GORGOROTH, 0);
        createGorgorothBiome(context, MEBiomeKeys.GORGOROTH_ASHEN_WOODS, 1);
        createGorgorothBiome(context, MEBiomeKeys.GORGOROTH_DELTA, 2);
        createGreyMountainsBiome(context, MEBiomeKeys.GREY_MOUNTAINS_BASE, 0);
        createGreyMountainsBiome(context, MEBiomeKeys.GREY_MOUNTAINS, 0);
        createGreyMountainsBiome(context, MEBiomeKeys.GREY_MOUNTAINS_PEAKS, 1);
        createGreyPlainsBiome(context, MEBiomeKeys.GREY_PLAINS);
        createGreyPlainsTaiga(context, MEBiomeKeys.GUNDABAD_PLAINS);
        createHaradBiome(context, MEBiomeKeys.HARAD);
        createHaradDesertBiome(context, MEBiomeKeys.HARAD_DESERT);
        createHarondorBiome(context, MEBiomeKeys.HARONDOR);
        createHillsOfEvendim(context, MEBiomeKeys.HILLS_OF_EVENDIM);
        createIronHillsBiome(context, MEBiomeKeys.IRON_HILLS, false);
        createIronHillsBiome(context, MEBiomeKeys.IRON_HILLS_BASE, true);
        createIronHillsBiome(context, MEBiomeKeys.IRON_HILLS_PEAKS, true);
        createNorthernRhovanionTaiga(context, MEBiomeKeys.IRON_HILLS_PLAINS);
        createNorthernRhovanionTaiga(context, MEBiomeKeys.IRON_FOOTHILLS);
        createIsengardBiome(context, MEBiomeKeys.ISENGARD, true);
        createIsengardBiome(context, MEBiomeKeys.ISENGARD_HILL, false);
        createIthilienBiome(context, MEBiomeKeys.ITHILIEN, false, false);
        createIthilienBiome(context, MEBiomeKeys.ITHILIEN_GLADE, false, true);
        createIthilienBiome(context, MEBiomeKeys.ITHILIEN_WASTES, true, false);
        createIthilienBiome(context, MEBiomeKeys.ITHILIEN_WASTES_GLADE, true, true);
        createLamedonBiome(context, MEBiomeKeys.LAMEDON, 0);
        createLamedonBiome(context, MEBiomeKeys.LAMEDON_HILLS, 1);
        createLebennin(context, MEBiomeKeys.LEBENNIN, 0);
        createLebennin(context, MEBiomeKeys.LEBENNIN_HILLS, 1);
        createGondorRiverSideBiome(context, MEBiomeKeys.LEBENNIN_SHORES);
        createLindonBiome(context, MEBiomeKeys.LINDON);
        createLakeBiome(context, MEBiomeKeys.LONG_LAKE);
        createMirkwoodSwampBiome(context, MEBiomeKeys.LONG_MARSHES);
        createLossarnach(context, MEBiomeKeys.LOSSARNACH, 0);
        createLossarnachCherryBlossom(context, MEBiomeKeys.LOSSARNACH_CHERRY_BLOSSOM);
        createLossarnach(context, MEBiomeKeys.LOSSARNACH_VALLEY, 1);
        createLossarnach(context, MEBiomeKeys.LOSSARNACH_VALLEY_GREEN, 2);
        createLossarnach(context, MEBiomeKeys.LOSSARNACH_VALLEY_YELLOW, 3);
        createLossarnach(context, MEBiomeKeys.LOSSARNACH_VALLEY_ORANGE, 4);
        createLossarnach(context, MEBiomeKeys.LOSSARNACH_VALLEY_RED, 5);
        createLorienEdgeBiome(context, MEBiomeKeys.LORIEN_EDGE);
        createLothlorienBiome(context, MEBiomeKeys.LOTHLORIEN);
        createMinhiriathBiome(context, MEBiomeKeys.MINHIRIATH);
        createMirkwoodBiome(context, MEBiomeKeys.MIRKWOOD, true, false);
        createMirkwoodBiome(context, MEBiomeKeys.MIRKWOOD_EDGE, false, false);
        createMirkwoodBiome(context, MEBiomeKeys.MIRKWOOD_FOOTHILLS, true, false);
        createMirkwoodBiome(context, MEBiomeKeys.MIRKWOOD_MOUNTAINS_BASE, true, false);
        createMirkwoodBiome(context, MEBiomeKeys.MIRKWOOD_MOUNTAINS, false, false);
        createMirkwoodMountainsBiome(context, MEBiomeKeys.MIRKWOOD_MOUNTAINS_PEAKS, false);
        createMirkwoodSwampBiome(context, MEBiomeKeys.MIRKWOOD_SWAMP);
        createMirkwoodSwampBiome(context, MEBiomeKeys.MIRKWOOD_MARSHES);
        createMirkwoodSwampBiome(context, MEBiomeKeys.FOREST_RIVER);
        createRiverBiome(context, MEBiomeKeys.GREAT_RIVER);
        createMistyMountainsBiome(context, MEBiomeKeys.MISTY_MOUNTAINS_BASE, 0);
        createMistyMountainsBiome(context, MEBiomeKeys.MISTY_MOUNTAINS, 1);
        createMistyMountainsBiome(context, MEBiomeKeys.MISTY_MOUNTAINS_PEAKS, 2);
        createMordorBiome(context, MEBiomeKeys.MORDOR);
        createMordorAshenForestBiome(context, MEBiomeKeys.MORDOR_ASHEN_FOREST);
        createMordorHillBiome(context, MEBiomeKeys.MORDOR_HILL);
        createMordorMountainsBiome(context, MEBiomeKeys.MORDOR_MOUNTAINS_FOOTHILLS);
        createMordorMountainsBiome(context, MEBiomeKeys.MORDOR_MOUNTAINS);
        createMordorMountainsBiome(context, MEBiomeKeys.MORDOR_MOUNTAINS_PEAKS);
        createMordorWastesBiome(context, MEBiomeKeys.MORDOR_WASTES);
        createMorgulVale(context, MEBiomeKeys.MORGUL_VALE);
        createMorgulForest(context, MEBiomeKeys.MORGUL_FOREST);
        createNurnWaterBiome(context, MEBiomeKeys.MORGUL_RIVER);
        createGreyMountainsBiome(context, MEBiomeKeys.MOUNT_GUNDABAD_BASE, 0);
        createGreyMountainsBiome(context, MEBiomeKeys.MOUNT_GUNDABAD, 1);
        createGreyMountainsBiome(context, MEBiomeKeys.MOUNT_GUNDABAD_PEAKS, 1);
        createMordorMountainsBiome(context, MEBiomeKeys.MOUNT_DOOM);
        createNanCurunirBiome(context, MEBiomeKeys.NAN_CURUNIR);
        createLakeBiome(context, MEBiomeKeys.NEN_HITHOEL);
        createTrollshawsBiome(context, MEBiomeKeys.NEN_HITHOEL_FOREST);
        createTrollshawsBiome(context, MEBiomeKeys.NEN_HITHOEL_SHORES);
        createSwampAnduin(context, MEBiomeKeys.NINDALF);
        createNorthDownsBiome(context, MEBiomeKeys.NORTH_DOWNS);
        createNorthDunlandBiome(context, MEBiomeKeys.NORTHERN_DUNLAND, true);
        createMirkwoodSwampBiome(context, MEBiomeKeys.NORTHERN_MIRKWOOD_SWAMP);
        createMirkwoodSwampBiome(context, MEBiomeKeys.NORTHERN_MIRKWOOD_MARSHES);
        createNorthernWastelands(context, MEBiomeKeys.NORTHERN_WASTELANDS);
        createNurnBiome(context, MEBiomeKeys.NURN, 0);
        createNurnBiome(context, MEBiomeKeys.NURN_FOREST, 1);
        createNurnBiome(context, MEBiomeKeys.NURN_HILL, 2);
        createNurnEdgeBiome(context, MEBiomeKeys.NURN_EDGE, 0);
        createNurnEdgeBiome(context, MEBiomeKeys.NURN_EDGE_WOODS, 1);
        createNurnWaterBiome(context, MEBiomeKeys.NURN_RIVER);
        createNurnWaterBiome(context, MEBiomeKeys.NURN_SEA);
        createOasisBiome(context, MEBiomeKeys.OASIS);
        createOceanBiome(context, MEBiomeKeys.OCEAN);
        createOceanCoastBiome(context, MEBiomeKeys.OCEAN_COAST);
        createOldAngmarBiome(context, MEBiomeKeys.OLD_ANGMAR, 1);
        createOldAngmarBiome(context, MEBiomeKeys.OLD_ANGMAR_FOREST, 0);
        createOldAngmarBiome(context, MEBiomeKeys.OLD_ANGMAR_COLD_HILL, 2);
        createOldAngmarBiome(context, MEBiomeKeys.OLD_ANGMAR_FROZEN_HILL, 3);
        createOldArthedainBiome(context, MEBiomeKeys.OLD_ARTHEDAIN, 1);
        createOldArthedainBiome(context, MEBiomeKeys.OLD_ARTHEDAIN_FOREST, 2);
        createOldArthedainBiome(context, MEBiomeKeys.OLD_ARTHEDAIN_MEADOW, 0);
        createOldArthedainBiome(context, MEBiomeKeys.OLD_ARTHEDAIN_FOOTHILL, 3);
        createOldCardolanBiome(context, MEBiomeKeys.OLD_CARDOLAN, 0);
        createOldCardolanBiome(context, MEBiomeKeys.OLD_CARDOLAN_FOREST, 1);
        createOldCardolanBiome(context, MEBiomeKeys.OLD_CARDOLAN_HILL, 2);
        createOldRhudaurBiome(context, MEBiomeKeys.OLD_RHUDAUR, 0);
        createOldRhudaurBiome(context, MEBiomeKeys.OLD_RHUDAUR_FOREST, 1);
        createOldRhudaurBiome(context, MEBiomeKeys.OLD_RHUDAUR_HILL, 2);
        createGondorBiome(context, MEBiomeKeys.OSGILIATH, 0);
        createPelennorFields(context, MEBiomeKeys.PELENNOR_FIELDS);
        createPondBiome(context, MEBiomeKeys.POND);
        createRhunBiome(context, MEBiomeKeys.RHUN);
        createRivendellBiome(context, MEBiomeKeys.HIGH_MOOR);
        createRivendellBiome(context, MEBiomeKeys.HIGH_MOOR_VALE);
        createRivendellFoothillsBiome(context, MEBiomeKeys.HIGH_MOOR_HILLS);
        createRiverBiome(context, MEBiomeKeys.RIVER);
        createRiverBiome(context, MEBiomeKeys.RIVER_RUNNING);
        createRohanBiome(context, MEBiomeKeys.ROHAN);
        createLakeBiome(context, MEBiomeKeys.SEA_OF_RHUN);
        createIronHillsBiome(context, MEBiomeKeys.SARN_GEBIR_SHORES, false);
        createIronHillsBiome(context, MEBiomeKeys.SARN_GEBIR_WILDLANDS, false);
        createShireBiome(context, MEBiomeKeys.SHIRE, 0);
        createShireBiome(context, MEBiomeKeys.SHIRE_EDGE, 1);
        createShireBiome(context, MEBiomeKeys.SHIRE_HILLS, 2);
        createShireBiome(context, MEBiomeKeys.SHIRE_WOODS, 3);
        createShireBiome(context, MEBiomeKeys.SHIRE_FOREST, 4);
        createNorthDunlandBiome(context, MEBiomeKeys.SOUTHERN_DUNLAND, false);
        createMordorMountainsBiome(context, MEBiomeKeys.SOUTHERN_EPHEL_DUATH);
        createMordorMountainsBiome(context, MEBiomeKeys.SOUTHERN_EPHEL_DUATH_BASE);
        createMordorMountainsBiome(context, MEBiomeKeys.SOUTHERN_EPHEL_DUATH_PEAKS);
        createRhunBiome(context, MEBiomeKeys.SOUTHEAST_RHOVANION);
        createSouthernForochelBiome(context, MEBiomeKeys.SOUTHERN_FOROCHEL);
        createTheAngleBiome(context, MEBiomeKeys.THE_ANGLE);
        createTheOldForestBiome(context, MEBiomeKeys.THE_OLD_FOREST);
        createTheWhiteDownsBiome(context, MEBiomeKeys.THE_WHITE_DOWNS);
        createRohanBiome(context, MEBiomeKeys.THE_WOLD);
        createTolfalasBiome(context, MEBiomeKeys.TOLFALAS);
        createTorogwaithBiome(context, MEBiomeKeys.TOROGWAITH);
        createTrollshawsBiome(context, MEBiomeKeys.TROLLSHAWS);
        createMordorBiome(context, MEBiomeKeys.UDUN);
        createUmbarBiome(context, MEBiomeKeys.UMBAR);
        createWastePondBiome(context, MEBiomeKeys.WASTE_POND);
        createMirkwoodBiome(context, MEBiomeKeys.WEBBED_WOODS, true, true);
        createWitheredHeathBiome(context, MEBiomeKeys.WITHERED_HEATH);
        createWhiteMountainsBiome(context, MEBiomeKeys.WHITE_MOUNTAINS_BASE);
        createWhiteMountainsFaces(context, MEBiomeKeys.WHITE_MOUNTAINS, false);
        createWhiteMountainsFaces(context, MEBiomeKeys.WHITE_MOUNTAINS_PEAKS, true);
        createWoodlandRealmBiome(context, MEBiomeKeys.WOODLAND_REALM);
        createMirkwoodMountainsBiome(context, MEBiomeKeys.WOODLAND_FOOTHILLS, true);
        createMirkwoodMountainsBiome(context, MEBiomeKeys.WOODLAND_HILLS, true);
    }

    public static void createAnduinBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, boolean forest) {
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

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createAnorienBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
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

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createBarrowDownsBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        ModBiomeFeatures.addDisks(undergroundOres);
        ModBiomeFeatures.addGravelOre(vegetation);

        ModBiomeFeatures.addGrass(vegetation);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createBeleriandIslandBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
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

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createBelfalasBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, int step) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addPlainsMobs(spawnSettings);
        ModSpawnSettingsBuilder.addSwan(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        ModBiomeFeatures.addCornflower(vegetation);
        ModBiomeFeatures.addTuftGrass(vegetation);
        ModBiomeFeatures.addRareWilderGrass(vegetation);
        ModBiomeFeatures.addGravelOre(vegetation);
        ModBiomeFeatures.addCalciteBoulder(vegetation);

        if(step == 0) { // Plains
            addGondorVegetation(generationSettings);
            vegetation.add(VegetationPlacedFeatures.TREES_PLAINS);
            ModBiomeFeatures.addRareBirchTrees(vegetation);
            ModBiomeFeatures.addRareOakBushes(vegetation);
            ModBiomeFeatures.addLightBlueFlowers(vegetation);
        } else if(step == 1) { // Hills
            addNordicVegetation(generationSettings);
            ModBiomeFeatures.addCalciteOre(vegetation);
            ModBiomeFeatures.addDioriteOre(vegetation);
            ModBiomeFeatures.addTuffOre(vegetation);
            ModBiomeFeatures.addGrassToStoneOre(vegetation);
            ModBiomeFeatures.addLarchTrees(vegetation);
            ModBiomeFeatures.addPineTrees(vegetation);
            ModBiomeFeatures.addRareSpruceTrees(vegetation);
            ModBiomeFeatures.addSpruceBushes(vegetation);
        } else if (step == 2) { // Forest
            ModSpawnSettingsBuilder.addDeer(spawnSettings);
            ModBiomeFeatures.addOakBushes(vegetation);
            ModBiomeFeatures.addGravelOre(vegetation);
            ModBiomeFeatures.addLebethronTrees(vegetation);
            ModBiomeFeatures.addRareBirchTrees(vegetation);
            ModBiomeFeatures.addBirchTrees(vegetation);
            ModBiomeFeatures.addBeechTrees(vegetation);
            ModBiomeFeatures.addForestMoss(vegetation);
            ModBiomeFeatures.addFalseOatgrass(vegetation);
            ModBiomeFeatures.addPodzolOre(vegetation);
            ModBiomeFeatures.addHollyTrees(vegetation);
            ModBiomeFeatures.addWhiteMushroom(vegetation);
        }

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createBelfalasShoresBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addGondorVegetation(generationSettings);
        ModBiomeFeatures.addBeachGrass(vegetation);
        ModBiomeFeatures.addDioriteBoulder(vegetation);
        ModBiomeFeatures.addHorokaka(vegetation);
        ModBiomeFeatures.addRareOakBushes(vegetation);
        ModBiomeFeatures.addWhitePalmTree(vegetation);
        ModBiomeFeatures.addWhiteFlowers(vegetation);

        vegetation.add(VegetationPlacedFeatures.TREES_PLAINS);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createBlackRootVale(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, boolean forest) {
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

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createBlueMountainsBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, int step) {
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

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings, temperature, true);
    }

    public static void createCorsairCoastBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
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

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createDaleBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, int step) {
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

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createDeadMarshesBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        undergroundOres.add(MiscPlacedFeatures.DISK_CLAY);
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

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createDeadMarshesWaterBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        undergroundOres.add(MiscPlacedFeatures.DISK_CLAY);
        ModBiomeFeatures.addAshenGravelOre(undergroundOres);
        ModBiomeFeatures.addAshenSandOre(undergroundOres);
        ModBiomeFeatures.addSoulSandOre(vegetation);
        vegetation.add(OceanPlacedFeatures.KELP_WARM);
        vegetation.add(OceanPlacedFeatures.SEAGRASS_NORMAL);
        ModBiomeFeatures.addGrass(vegetation);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createDorwinionBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
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

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createDorwinionHillsBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
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

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createDunlandFoothillsBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
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

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createEmynMuilBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
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

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createEnedwaithBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
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

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createLonelyMountainBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, int step) {
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

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings, temperature, true);
    }

    public static void createEregionBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
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

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createSwampAnduin(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
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

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createFangornBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
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

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createForodwaithBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addForochelMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addForodwaithVegetation(generationSettings);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings, -0.8f, true);
    }

    public static void createGondorBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, int step) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addPlainsMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addGondorVegetation(generationSettings);
        ModBiomeFeatures.addGravelOre(vegetation);
        ModBiomeFeatures.addDioriteBoulder(vegetation);

        vegetation.add(VegetationPlacedFeatures.TREES_PLAINS);

        if(step == 0) {
            ModBiomeFeatures.addTallGrass(vegetation);
            ModBiomeFeatures.addTuftGrass(vegetation);
            ModBiomeFeatures.addWheatGrass(vegetation);
            ModBiomeFeatures.addVeryRareLebethronTrees(vegetation);
        } else if(step == 1) {
            ModSpawnSettingsBuilder.addDeer(spawnSettings);
            ModBiomeFeatures.addRareBeechTrees(vegetation);
            ModBiomeFeatures.addBeechTrees(vegetation);
            ModBiomeFeatures.addBirchTrees(vegetation);
            ModBiomeFeatures.addRareLebethronTrees(vegetation);
            ModBiomeFeatures.addLebethronTrees(vegetation);
            ModBiomeFeatures.addChestnutTrees(vegetation);
            ModBiomeFeatures.addOakTrees(vegetation);
            ModBiomeFeatures.addDarkOakTrees(vegetation);
            ModBiomeFeatures.addRareMegaOakTrees(vegetation);

            ModBiomeFeatures.addWildGrass(vegetation);
            ModBiomeFeatures.addWilderGrass(vegetation);
            ModBiomeFeatures.addFalseOatgrass(vegetation);
            ModBiomeFeatures.addRareMorsel(vegetation);
            ModBiomeFeatures.addRareWhiteMushroom(vegetation);
            ModBiomeFeatures.addMossyBoulder(vegetation);
            ModBiomeFeatures.addCommonOakBush(vegetation);
            ModBiomeFeatures.addCoarseDirtOre(vegetation);
            ModBiomeFeatures.addForestMoss(vegetation);
            ModBiomeFeatures.addPodzolOre(vegetation);
            ModBiomeFeatures.addForestBlockMoss(vegetation);
        } else if(step == 2) {
            vegetation.add(VegetationPlacedFeatures.PATCH_LARGE_FERN);
            ModBiomeFeatures.addBracken(vegetation);
            ModBiomeFeatures.addGrassToStoneOre(vegetation);
            ModBiomeFeatures.addPineTrees(vegetation);
            ModBiomeFeatures.addBlackPineTrees(vegetation);
            ModBiomeFeatures.addScarceSpruceTrees(vegetation);
            ModBiomeFeatures.addRareMorsel(vegetation);
            ModBiomeFeatures.addRareWhiteMushroom(vegetation);
            ModBiomeFeatures.addSpruceBushes(vegetation);
            ModBiomeFeatures.addOakBushes(vegetation);
        }


        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createGondorRiverSideBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addGondorVegetation(generationSettings);
        ModBiomeFeatures.addGravelOre(vegetation);
        ModBiomeFeatures.addDioriteBoulder(vegetation);
        ModBiomeFeatures.addBeachGrass(vegetation);
        ModBiomeFeatures.addDryGrass(vegetation);

        vegetation.add(VegetationPlacedFeatures.TREES_PLAINS);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createGreyMountainsBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, int step) {
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

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createGreyPlainsBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
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

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createGreyPlainsTaiga(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
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

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createHaradBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
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

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createHarondorBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
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

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createHaradDesertBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addHaradMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addHaradDesertVegetation(generationSettings);
        vegetation.add(VegetationPlacedFeatures.PATCH_DEAD_BUSH_2);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings, 0.8f, false);
    }

    public static void createHillsOfEvendim(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
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

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createIronHillsBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, boolean foothills) {
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

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createIsengardBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, boolean trees) {
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

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createNorthernRhovanionTaiga(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
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

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createIthilienBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, boolean wastes, boolean glade) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addFarmAnimals(spawnSettings);
        ModSpawnSettingsBuilder.addDeer(spawnSettings);
        ModSpawnSettingsBuilder.addRabbits(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        ModBiomeFeatures.addGravelOre(vegetation);
        ModBiomeFeatures.addWildGrass(vegetation);
        ModBiomeFeatures.addWildPotato(vegetation);
        ModBiomeFeatures.addFalseOatgrass(vegetation);
        ModBiomeFeatures.addRareMorsel(vegetation);
        ModBiomeFeatures.addRareWhiteMushroom(vegetation);
        ModBiomeFeatures.addMossyBoulder(vegetation);
        ModBiomeFeatures.addCommonOakBush(vegetation);
        ModBiomeFeatures.addCoarseDirtOre(vegetation);
        ModBiomeFeatures.addDioriteBoulder(vegetation);

        addGondorVegetation(generationSettings);

        if(!glade) {
            ModBiomeFeatures.addBracken(vegetation);
            ModBiomeFeatures.addWheatGrass(vegetation);
            ModBiomeFeatures.addForestMoss(vegetation);
            ModBiomeFeatures.addOakTrees(vegetation);
            ModBiomeFeatures.addPodzolOre(vegetation);
            ModBiomeFeatures.addSparsePineTrees(vegetation);
            ModBiomeFeatures.addBlackPineTrees(vegetation);
            ModBiomeFeatures.addForestBlockMoss(vegetation);
        } else {
            ModBiomeFeatures.addHeather(vegetation);
            ModBiomeFeatures.addTallGrass(vegetation);
            ModBiomeFeatures.addSparseWheatGrass(vegetation);
            ModBiomeFeatures.addTuftGrass(vegetation);
            ModBiomeFeatures.addRareLebethronTrees(vegetation);
        }

        if(!wastes) {
            ModBiomeFeatures.addFlowerGreenJewel(vegetation);
            ModBiomeFeatures.addRareWilderGrass(vegetation);
            if(!glade) {
                ModBiomeFeatures.addBeechTrees(vegetation);
                ModBiomeFeatures.addBirchTrees(vegetation);
                ModBiomeFeatures.addCommonLebethronTrees(vegetation);
                ModBiomeFeatures.addChestnutTrees(vegetation);
                ModBiomeFeatures.addMegaOakTrees(vegetation);
            } else {
                ModBiomeFeatures.addRedFlowers(vegetation);
            }
        } else {
            ModBiomeFeatures.addCommonToughBerries(undergroundOres);
            ModBiomeFeatures.addAshenDirtOre(undergroundOres);
            if(!glade) {
                ModBiomeFeatures.addRareBirchTrees(vegetation);
                ModBiomeFeatures.addLebethronTrees(vegetation);
                ModBiomeFeatures.addDeadPineTrees(vegetation);
                ModBiomeFeatures.addDeadBlackPineTrees(vegetation);
                ModBiomeFeatures.addRareBeechTrees(vegetation);
            }
        }

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createLamedonBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, int step) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addPlainsMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addGondorVegetation(generationSettings);
        ModBiomeFeatures.addDioriteOre(vegetation);
        ModBiomeFeatures.addGravelOre(vegetation);
        ModBiomeFeatures.addCoarseDirtOre(vegetation);
        ModBiomeFeatures.addTurfOre(vegetation);
        ModBiomeFeatures.addCalciteBoulder(vegetation);
        ModBiomeFeatures.addDioriteBoulder(vegetation);

        if(step == 0) {
            ModBiomeFeatures.addWhiteFlowers(vegetation);
            ModBiomeFeatures.addTuftGrass(vegetation);
        } else if (step == 1) {
            ModBiomeFeatures.addCalciteOre(vegetation);
            ModBiomeFeatures.addPineTrees(vegetation);
            ModBiomeFeatures.addSparseBirchTrees(vegetation);
            ModBiomeFeatures.addRareBeechTrees(vegetation);
            vegetation.add(VegetationPlacedFeatures.PATCH_LARGE_FERN);
            ModBiomeFeatures.addBracken(vegetation);
            ModBiomeFeatures.addGrassToStoneOre(vegetation);
            ModBiomeFeatures.addScarceSpruceTrees(vegetation);
            ModBiomeFeatures.addRareMorsel(vegetation);
            ModBiomeFeatures.addRareWhiteMushroom(vegetation);
            ModBiomeFeatures.addSpruceBushes(vegetation);
        }

        vegetation.add(VegetationPlacedFeatures.TREES_PLAINS);
        ModBiomeFeatures.addOakBushes(vegetation);
        ModBiomeFeatures.addVeryRareBirchTrees(vegetation);
        ModBiomeFeatures.addRareLebethronTrees(vegetation);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createLebennin(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, int step) {
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

        if(step == 0) { // Plains
            ModBiomeFeatures.addLightBlueFlowers(vegetation);
            ModBiomeFeatures.addRareBeechTrees(vegetation);
            ModBiomeFeatures.addRareLebethronTrees(vegetation);
        } else if (step == 1) { // Hills
            ModBiomeFeatures.addBlackPineTrees(vegetation);
            ModBiomeFeatures.addPineTrees(vegetation);
            ModBiomeFeatures.addSpruceTrees(vegetation);
            ModBiomeFeatures.addSparseLarchTrees(vegetation);
            ModBiomeFeatures.addSpruceBushes(vegetation);
            ModBiomeFeatures.addStoneGrassOre(vegetation);
            ModBiomeFeatures.addFalseOatgrass(vegetation);
            ModBiomeFeatures.addBracken(vegetation);
        }

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createLindonBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
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

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createLorienEdgeBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addFarmAnimals(spawnSettings);
        ModSpawnSettingsBuilder.addSwan(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addLothlorienVegetation(generationSettings);
        ModBiomeFeatures.addMallornTrees(vegetation);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createLothlorienBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addFarmAnimals(spawnSettings);
        ModSpawnSettingsBuilder.addSwan(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addLothlorienVegetation(generationSettings);
        ModBiomeFeatures.addSmallMallornTress(vegetation);
        ModBiomeFeatures.addOakTrees(vegetation);
        ModBiomeFeatures.addSparseBirchTrees(vegetation);
        ModBiomeFeatures.addMegaMallornTrees(vegetation);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createLossarnach(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, int step) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addPlainsMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addGondorVegetation(generationSettings);
        ModBiomeFeatures.addWildBellPepper(vegetation);
        ModBiomeFeatures.addWheatGrass(vegetation);
        ModBiomeFeatures.addWildGrass(vegetation);
        ModBiomeFeatures.addTuftGrass(vegetation);

        if(step == 0) { // Plains
            ModBiomeFeatures.addYellowFlowers(vegetation);
            ModBiomeFeatures.addOrangeFlowers(vegetation);
            ModBiomeFeatures.addRedFlowers(vegetation);
            ModBiomeFeatures.addWhiteFlowers(vegetation);
            ModBiomeFeatures.addLossarnachFlowers(vegetation);
            ModBiomeFeatures.addGravelOre(vegetation);
            ModBiomeFeatures.addDioriteBoulder(vegetation);
            vegetation.add(VegetationPlacedFeatures.TREES_PLAINS);
            ModBiomeFeatures.addOakBushes(vegetation);
            ModBiomeFeatures.addVeryRareBirchTrees(vegetation);
            ModBiomeFeatures.addRareBeechTrees(vegetation);
            ModBiomeFeatures.addRareOakTrees(vegetation);
            ModBiomeFeatures.addRareLebethronTrees(vegetation);
            ModBiomeFeatures.addWilderGrass(vegetation);
        } else if (step == 1) { // Flower valley
            ModBiomeFeatures.addYellowFlowers(vegetation);
            ModBiomeFeatures.addOrangeFlowers(vegetation);
            ModBiomeFeatures.addRedFlowers(vegetation);
            ModBiomeFeatures.addLossarnachFlowersCommon(vegetation);
            ModBiomeFeatures.addWildPotato(vegetation);
            ModBiomeFeatures.addSparsePineTrees(vegetation);
            ModBiomeFeatures.addRareBirchTrees(vegetation);
            ModBiomeFeatures.addRareOakTrees(vegetation);
            ModBiomeFeatures.addRareLebethronTrees(vegetation);
            ModBiomeFeatures.addChestnutTrees(vegetation);
            ModBiomeFeatures.addWilderGrass(vegetation);
        } else if (step >= 2) { // Forest
            if (step == 2) {
                ModBiomeFeatures.addMapleTree(vegetation);
                ModBiomeFeatures.addWilderGrass(vegetation);
            } else if(step == 3) { // Yellow
                ModBiomeFeatures.addYellowMapleTree(vegetation);
                ModBiomeFeatures.addRareWilderGrass(vegetation);
            } else if (step == 4) { // Orange
                ModBiomeFeatures.addOrangeMapleTree(vegetation);
            } else if (step == 5) { // Red
                ModBiomeFeatures.addRedMapleTree(vegetation);
            }
            ModBiomeFeatures.addSparseWheatGrass(vegetation);
            ModBiomeFeatures.addCoarseDirtOre(vegetation);
            ModBiomeFeatures.addFrequentOakTrees(vegetation);
            ModBiomeFeatures.addRareOakTrees(vegetation);
            ModBiomeFeatures.addOakTrees(vegetation);
            ModBiomeFeatures.addCommonOakTrees(vegetation);
            ModBiomeFeatures.addMegaOakTrees(vegetation);
            ModBiomeFeatures.addCommonOakBush(vegetation);
        }

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createLossarnachCherryBlossom(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addFarmAnimals(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addGondorVegetation(generationSettings);
        ModBiomeFeatures.addOakBushes(vegetation);
        ModBiomeFeatures.addTallGrass(vegetation);
        ModBiomeFeatures.addWildGrass(vegetation);
        ModBiomeFeatures.addTuftGrass(vegetation);
        ModBiomeFeatures.addPinkFlowers(vegetation);
        ModBiomeFeatures.addPinkFlowerGrowth(vegetation);
        vegetation.add(VegetationPlacedFeatures.TREES_CHERRY);
        vegetation.add(VegetationPlacedFeatures.FLOWER_CHERRY);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createMinhiriathBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
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

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createMirkwoodBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, boolean bigTrees, boolean dark) {
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

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createMirkwoodMountainsBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, boolean foothill) {
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

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings, temperature, true);
    }

    public static void createMirkwoodSwampBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
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

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createMistyMountainsBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, int step) {
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

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings,temperature, true);
    }

    public static void createMordorBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addMordorVegetation(generationSettings);
        ModBiomeFeatures.addPumicePileRare(vegetation);
        ModBiomeFeatures.addAshenStoneBoulder(vegetation);
        ModBiomeFeatures.addPumiceColumnRare(vegetation);
        ModBiomeFeatures.addAshenStoneDirtOre(vegetation);
        ModBiomeFeatures.addAshenStoneGravelOre(vegetation);
        ModBiomeFeatures.addAshenStoneSandOre(vegetation);
        ModBiomeFeatures.addLavaMagmaLake(generationSettings);
        ModBiomeFeatures.addBasaltPile(vegetation);
        ModBiomeFeatures.addBlackStonePile(vegetation);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings, 0.7f, false, true);
    }

    public static void createMordorAshenForestBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addMordorVegetation(generationSettings);
        ModBiomeFeatures.addTuftGrass(vegetation);
        ModBiomeFeatures.addAshenStoneBoulder(vegetation);
        ModBiomeFeatures.addBasaltPile(vegetation);
        ModBiomeFeatures.addAshenStoneDirtOre(vegetation);
        ModBiomeFeatures.addAshenStoneDirtCommonOre(vegetation);
        ModBiomeFeatures.addAshenStoneGravelOre(vegetation);
        ModBiomeFeatures.addAshenStoneSandOre(vegetation);
        ModBiomeFeatures.addDeadRushes(vegetation);
        ModBiomeFeatures.addStoneOldPodzolOre(vegetation);
        ModBiomeFeatures.addPumiceColumnRare(vegetation);
        ModBiomeFeatures.addShriveledShrubs(vegetation);
        ModBiomeFeatures.addBlackStonePile(vegetation);
        ModBiomeFeatures.addAbundantDeadBlackPineTrees(vegetation);
        ModBiomeFeatures.addDryPineBushes(vegetation);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings, 0.7f, false, true);
    }

    public static void createMordorHillBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addMordorVegetation(generationSettings);
        ModBiomeFeatures.addPumicePileSparse(vegetation);
        ModBiomeFeatures.addAshenStoneBoulder(vegetation);
        ModBiomeFeatures.addPumiceColumn(vegetation);
        ModBiomeFeatures.addPumiceColumnLarge(vegetation);
        ModBiomeFeatures.addAshenStoneDirtOre(vegetation);
        ModBiomeFeatures.addAshenStoneGravelOre(vegetation);
        ModBiomeFeatures.addAshenStoneSandOre(vegetation);
        ModBiomeFeatures.addLavaMagmaLake(generationSettings);
        ModBiomeFeatures.addBasaltPile(vegetation);
        ModBiomeFeatures.addBlackStonePile(vegetation);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings, 0.7f, false, true);
    }

    public static void createGorgorothBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, int step) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        ModBiomeFeatures.addAshBlockOre(vegetation);
        ModBiomeFeatures.addPumicePileSparse(vegetation);
        ModBiomeFeatures.addPumiceColumn(vegetation);
        ModBiomeFeatures.addPumiceColumnLarge(vegetation);
        ModBiomeFeatures.addAshenStoneBoulder(vegetation);
        ModBiomeFeatures.addAshenStoneDirtOre(vegetation);
        ModBiomeFeatures.addAshenStoneGravelOre(vegetation);
        ModBiomeFeatures.addAshenStoneSandOre(vegetation);
        ModBiomeFeatures.addBlackSand(vegetation);
        ModBiomeFeatures.addBasaltPile(vegetation);
        ModBiomeFeatures.addBlackStonePile(vegetation);

        if(step == 0) { // Plateau
            ModBiomeFeatures.addGrimGrass(vegetation);
            ModBiomeFeatures.addCommonToughBerries(vegetation);
            ModBiomeFeatures.addLavaMagmaLake(generationSettings);
        } else if(step == 1) { // Ashen Forest
            ModBiomeFeatures.addGrimGrass(vegetation);
            ModBiomeFeatures.addToughBerries(vegetation);
            ModBiomeFeatures.addCommonToughBerries(vegetation);
            ModBiomeFeatures.addAshenStoneDirtCommonOre(vegetation);
            ModBiomeFeatures.addDeadRushes(vegetation);
            ModBiomeFeatures.addStoneOldPodzolOre(vegetation);
            ModBiomeFeatures.addPumiceColumnRare(vegetation);
            ModBiomeFeatures.addShriveledShrubs(vegetation);
            ModBiomeFeatures.addAbundantDeadBlackPineTrees(vegetation);
            ModBiomeFeatures.addDryPineBushes(vegetation);
        } else if(step == 2) { // Delta
            vegetation.add(NetherPlacedFeatures.DELTA);
            surfaceStructures.add(ModMiscPlacedFeatures.SMALL_BASALT_COLUMNS);
            surfaceStructures.add(ModMiscPlacedFeatures.SMALL_PUMICE_COLUMNS);
            surfaceStructures.add(ModMiscPlacedFeatures.LARGE_PUMICE_COLUMNS);
            ModBiomeFeatures.addLavaMagmaLake(generationSettings);
            ModBiomeFeatures.addBasaltBoulder(vegetation);
            ModBiomeFeatures.addSmoothBasaltOre(vegetation);
            ModBiomeFeatures.addBasaltPileRare(vegetation);
            ModBiomeFeatures.addPumicePileRare(vegetation);
        }

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings, 0.7f, false, true);
    }

    public static void createMordorMountainsBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        ModBiomeFeatures.addAshBlockOre(vegetation);
        ModBiomeFeatures.addPumiceColumn(vegetation);
        ModBiomeFeatures.addPumicePileSparse(vegetation);
        ModBiomeFeatures.addAshenGravelDirtOre(vegetation);
        ModBiomeFeatures.addAshenGravelSandOre(vegetation);
        ModBiomeFeatures.addAshenStoneGravelOre(vegetation);
        ModBiomeFeatures.addAshenStoneSandOre(vegetation);
        ModBiomeFeatures.addAshenStoneDirtOre(vegetation);
        ModBiomeFeatures.addBasaltPile(vegetation);
        ModBiomeFeatures.addBlackStonePile(vegetation);
        ModBiomeFeatures.addShriveledShrubs(vegetation);
        ModBiomeFeatures.addCommonToughBerries(vegetation);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings, 0.7f, false, true);
    }

    public static void createMordorWastesBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addMordorVegetation(generationSettings);
        ModBiomeFeatures.addDeadRushes(vegetation);
        ModBiomeFeatures.addGravelOre(vegetation);
        ModBiomeFeatures.addMireOre(vegetation);
        ModBiomeFeatures.addMudOre(vegetation);
        ModBiomeFeatures.addAshenDirtOre(vegetation);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createMorgulVale(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addRareWolves(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        ModBiomeFeatures.addGrass(vegetation);
        ModBiomeFeatures.addTuftGrass(vegetation);
        ModBiomeFeatures.addWheatGrass(vegetation);
        ModBiomeFeatures.addAshenGravelOre(vegetation);
        ModBiomeFeatures.addAshenSandOre(vegetation);
        ModBiomeFeatures.addAshBlockOre(vegetation);
        ModBiomeFeatures.addDeadRushes(vegetation);
        ModBiomeFeatures.addTurfOre(vegetation);
        ModBiomeFeatures.addWildGrass(vegetation);

        ModBiomeFeatures.addPumicePileRare(vegetation);
        ModBiomeFeatures.addAshenStoneBoulder(vegetation);
        ModBiomeFeatures.addRareMorsel(vegetation);
        ModBiomeFeatures.addRareWhiteMushroom(vegetation);
        ModBiomeFeatures.addBasaltPile(vegetation);
        ModBiomeFeatures.addCoarseDirtOre(vegetation);
        ModBiomeFeatures.addDeadBlackPineTrees(vegetation);

        ModBiomeFeatures.addCommonToughBerries(undergroundOres);
        ModBiomeFeatures.addAshenDirtOre(undergroundOres);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }
    public static void createMorgulForest(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addWolves(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        ModBiomeFeatures.addGrass(vegetation);
        ModBiomeFeatures.addTuftGrass(vegetation);
        ModBiomeFeatures.addWheatGrass(vegetation);
        ModBiomeFeatures.addAshenGravelOre(vegetation);
        ModBiomeFeatures.addAshBlockOre(vegetation);
        ModBiomeFeatures.addShriveledShrubs(vegetation);
        ModBiomeFeatures.addDeadRushes(vegetation);
        ModBiomeFeatures.addTurfOre(vegetation);
        ModBiomeFeatures.addWildGrass(vegetation);

        ModBiomeFeatures.addPumicePileRare(vegetation);
        ModBiomeFeatures.addAshenStoneBoulder(vegetation);
        ModBiomeFeatures.addRareMorsel(vegetation);
        ModBiomeFeatures.addRareWhiteMushroom(vegetation);
        ModBiomeFeatures.addBasaltPile(vegetation);
        ModBiomeFeatures.addCoarseDirtOre(vegetation);
        ModBiomeFeatures.addOldPodzolOre(vegetation);
        ModBiomeFeatures.addOakTrees(vegetation);
        ModBiomeFeatures.addSpruceBushes(vegetation);
        ModBiomeFeatures.addDarkOakTrees(vegetation);
        ModBiomeFeatures.addSpruceTrees(vegetation);
        ModBiomeFeatures.addSparsePineTrees(vegetation);
        ModBiomeFeatures.addCommonBlackPineTrees(vegetation);
        ModBiomeFeatures.addDeadBlackPineTrees(vegetation);
        ModBiomeFeatures.addRareLebethronTrees(vegetation);

        ModBiomeFeatures.addCommonToughBerries(undergroundOres);
        ModBiomeFeatures.addAshenDirtOre(undergroundOres);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createNanCurunirBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
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

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createNorthDownsBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
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

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createNorthDunlandBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, boolean trees) {
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
        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createNorthernWastelands(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
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

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings, -0.1f, true);
    }

    public static void createNurnEdgeBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, int step) {
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
        ModBiomeFeatures.addShriveledShrubs(vegetation);
        vegetation.add(VegetationPlacedFeatures.TREES_PLAINS);

        if(step == 0) { // Plains
            ModBiomeFeatures.addVeryRareBeechTrees(vegetation);
            ModBiomeFeatures.addCommonWheatGrass(vegetation);
            ModBiomeFeatures.addWilderGrass(vegetation);
        } else if (step == 1) { // Woods
            ModBiomeFeatures.addFalseOatgrass(vegetation);
            ModBiomeFeatures.addForestMoss(vegetation);
            ModBiomeFeatures.addForestBlockMoss(vegetation);
            ModBiomeFeatures.addOldPodzolOre(vegetation);
            ModBiomeFeatures.addRareBirchTrees(vegetation);
            ModBiomeFeatures.addScarceBlackPineTrees(vegetation);
            ModBiomeFeatures.addDryPineTrees(vegetation);
            ModBiomeFeatures.addDarkOakTrees(vegetation);
            ModBiomeFeatures.addRareBeechTrees(vegetation);
        }

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createNurnBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, int step) {
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

        if(step == 0) { // Plains
            ModBiomeFeatures.addCommonWheatGrass(vegetation);
            ModBiomeFeatures.addWilderGrass(vegetation);
            ModBiomeFeatures.addTallGrass(vegetation);
        } else if(step == 1) { // Forest
            ModBiomeFeatures.addDeadRushes(vegetation);
            ModBiomeFeatures.addFalseOatgrass(vegetation);
            ModBiomeFeatures.addForestMoss(vegetation);
            ModBiomeFeatures.addForestBlockMoss(vegetation);
            ModBiomeFeatures.addOldPodzolOre(vegetation);
            ModBiomeFeatures.addRareBirchTrees(vegetation);
            ModBiomeFeatures.addBlackPineTrees(vegetation);
            ModBiomeFeatures.addDarkOakTrees(vegetation);
            ModBiomeFeatures.addVeryRareMegaOakTrees(vegetation);
            ModBiomeFeatures.addRareBeechTrees(vegetation);
            ModBiomeFeatures.addWildOnion(vegetation);
        } else if(step == 2) {
            ModBiomeFeatures.addDeadRushes(vegetation);
            ModBiomeFeatures.addDeadPineTrees(vegetation);
            ModBiomeFeatures.addPineTrees(vegetation);
            ModBiomeFeatures.addBlackPineTrees(vegetation);
            ModBiomeFeatures.addScarceSpruceTrees(vegetation);
            ModBiomeFeatures.addSparseLarchTrees(vegetation);
            ModBiomeFeatures.addBasaltBoulder(vegetation);
            ModBiomeFeatures.addAshBlockOre(vegetation);
        }

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createNurnWaterBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addSwampMobs(spawnSettings);
        ModSpawnSettingsBuilder.addRiverAnimals(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addOceanVegetation(generationSettings);
        ModBiomeFeatures.addMudOre(vegetation);

        ModBiomeFeatures.addWillowTrees(vegetation);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createOldAngmarBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, int step) {
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

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings, temperature, true);
    }

    public static void createOldArthedainBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, int step) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addEriadorMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addArthedainVegetation(generationSettings);

        if(step == 0) { // Meadow
            ModBiomeFeatures.addCommonHeath(vegetation);
            ModBiomeFeatures.addCommonHeather(vegetation);
            ModBiomeFeatures.addRareHeather(vegetation);
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

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createOldCardolanBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, int step) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addEriadorMobs(spawnSettings);
        ModSpawnSettingsBuilder.addRareWolves(spawnSettings);
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

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createOldRhudaurBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, int step) {
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

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createRhunBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
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

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createRivendellBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addPlainsMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addRivendellVegetation(generationSettings);

        ModBiomeFeatures.addRareBeechTrees(vegetation);
        ModBiomeFeatures.addSparseBirchTrees(vegetation);
        ModBiomeFeatures.addVeryRareMegaOakTrees(vegetation);
        ModBiomeFeatures.addMegaBirchTrees(vegetation);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createRivendellFoothillsBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
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

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings, 0.4f, true);
    }

    public static void createRohanBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
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

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createShireBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, int step) {
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
            ModBiomeFeatures.addMoss(vegetation);
            if(step == 4) {
                ModBiomeFeatures.addMossCarpet(vegetation);
                ModBiomeFeatures.addOakTrees(vegetation);
                ModBiomeFeatures.addMegaBirchTrees(vegetation);
                ModBiomeFeatures.addMegaDarkOakTrees(vegetation);
                ModBiomeFeatures.addMegaOakCommonTrees(vegetation);
            } else {
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
        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createSouthernForochelBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addNordicMobs(spawnSettings);
        ModSpawnSettingsBuilder.addWolves(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addNordicVegetation(generationSettings);
        ModBiomeFeatures.addGravelOre(vegetation);
        ModBiomeFeatures.addBlueTuffBoulder(vegetation);
        ModBiomeFeatures.addSparsePineTrees(vegetation);
        ModBiomeFeatures.addRareSpruceTrees(vegetation);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings, -0.4f, true);
    }

    public static void createTheAngleBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
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

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createTheOldForestBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
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

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createTheWhiteDownsBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
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

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings, 0.4f, true);
    }

    public static void createTolfalasBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
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

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createTorogwaithBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addMordorVegetation(generationSettings);
        ModBiomeFeatures.addGrass(vegetation);
        ModBiomeFeatures.addAshenDirtOre(vegetation);
        ModBiomeFeatures.addDirtToGrassOre(vegetation);
        ModBiomeFeatures.addShriveledShrubs(vegetation);
        ModBiomeFeatures.addAshenStoneDirtOre(vegetation);
        ModBiomeFeatures.addBasaltPile(vegetation);
        ModBiomeFeatures.addBlackStonePile(vegetation);
        ModBiomeFeatures.addPumicePileRare(vegetation);
        ModBiomeFeatures.addDeadBlackPineTrees(vegetation);
        ModBiomeFeatures.addDryPineTrees(vegetation);
        ModBiomeFeatures.addDryPineBushes(vegetation);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings, 0.7f, false);
    }

    public static void createTrollshawsBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
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

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createUmbarBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addHaradMobs(spawnSettings);
        ModSpawnSettingsBuilder.addRareWolves(spawnSettings);
        ModSpawnSettingsBuilder.addFarmAnimals(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addHaradVegetation(generationSettings);
        ModBiomeFeatures.addGraniteBoulder(vegetation);
        ModBiomeFeatures.addWildFlax(vegetation);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createRiverBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
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

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createLakeBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addRiverAnimals(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addOceanVegetation(generationSettings);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createOasisBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addRiverAnimals(spawnSettings);
        ModSpawnSettingsBuilder.addCamel(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addOasisVegetation(generationSettings);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createWastePondBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addRiverAnimals(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        ModBiomeFeatures.addDisks(undergroundOres);
        vegetation.add(OceanPlacedFeatures.KELP_WARM);
        vegetation.add(OceanPlacedFeatures.SEAGRASS_NORMAL);
        ModBiomeFeatures.addGrass(vegetation);
        vegetation.add(VegetationPlacedFeatures.PATCH_SUGAR_CANE);
        ModBiomeFeatures.addReedsFoliage(vegetation);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createWitheredHeathBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
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

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings, 0.4f, true);
    }

    public static void createWhiteMountainsBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addMountainsMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        float temperature = 0.5f;
        addNordicVegetation(generationSettings);
        ModBiomeFeatures.addLarchTrees(vegetation);
        ModBiomeFeatures.addPineTrees(vegetation);
        ModBiomeFeatures.addScarceSpruceTrees(vegetation);
        ModBiomeFeatures.addSpruceBushes(vegetation);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings, temperature, true);
    }

    public static void createWhiteMountainsFaces(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey, boolean cold) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addMountainVegetation(generationSettings);
        float temperature = (cold) ? 0.5f : 0.5f;

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings, temperature, true);
    }

    public static void createWoodlandRealmBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
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

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createOceanBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addOceanAnimals(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addOceanVegetation(generationSettings);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createOceanCoastBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addOceanAnimals(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addOceanVegetation(generationSettings);
        ModBiomeFeatures.addCoastalFoliage(vegetation);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createPelennorFields(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addPlainsMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addGondorVegetation(generationSettings);
        ModBiomeFeatures.addDioriteBoulder(vegetation);
        ModBiomeFeatures.addRareWilderGrass(vegetation);
        ModBiomeFeatures.addTuftGrass(vegetation);

        vegetation.add(VegetationPlacedFeatures.TREES_PLAINS);
        ModBiomeFeatures.addRareLebethronTrees(vegetation);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createPondBiome(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
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

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings);
    }

    public static void createFrozenPond(Registerable<Biome> context, RegistryKey<Biome> biomeRegistryKey) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addColdWaterAnimals(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));
        
        ModBiomeFeatures.addDisks(undergroundOres);

        vegetation.add(OceanPlacedFeatures.KELP_COLD);
        vegetation.add(OceanPlacedFeatures.SEAGRASS_NORMAL);
        ModBiomeFeatures.addGrass(vegetation);

        registerBiome(context, biomeRegistryKey, spawnSettings, generationSettings, -0.1f, true);
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
        ModBiomeFeatures.addGrimGrass(vegetation);
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
}
