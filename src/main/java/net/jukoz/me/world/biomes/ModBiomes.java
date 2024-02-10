package net.jukoz.me.world.biomes;

import net.jukoz.me.world.features.boulder.BoulderPlacedFeatures;
import net.jukoz.me.world.features.vegetation.ModVegetationPlacedFeatures;
import net.jukoz.me.world.spawners.ModSpawnSettingsBuilder;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.registry.Registerable;
import net.minecraft.world.gen.feature.OceanPlacedFeatures;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ModBiomes {
    public static final int defaultSky = 7907327;
    public static final int defaultFog = 12638463;
    public static final int defaultWater = 4159204;
    public static final int defaultWaterFog = 329011;
    public static final int hillySky = 8233727;
    public static final int waterSky = 8103167;
    public static final int nearHaradSky = 7254527;
    public static final int nearHaradSkyFog = 12902399;

    private static List<RegistryKey<PlacedFeature>> vegetation = new ArrayList<>();;

    public static void bootstrap(Registerable<Biome> context) {
        context.register(MEBiomeKeys.ANDUIN_VALES, createAnduinBiome(context, new BiomeColorsDTO(
                defaultSky, defaultFog, defaultWater, defaultWaterFog, 8703593, 8703593)));
        context.register(MEBiomeKeys.BARROW_DOWNS, createBarrowDownsBiome(context, new BiomeColorsDTO(
                5993621, 7635851, 4812936, 3034721, 6721389, 6525545)));
        context.register(MEBiomeKeys.BLUE_MOUNTAINS, createBlueMountainsBiome(context, new BiomeColorsDTO(
                7903952, 12241898, defaultWater, defaultWaterFog, 6791282, 7773545)));
        context.register(MEBiomeKeys.BLUE_MOUNTAINS_FOOTHILLS, createBlueMountainsBiome(context, new BiomeColorsDTO(
                7905261, 12241911, defaultWater, defaultWaterFog, 7125373, 8703593)));
        context.register(MEBiomeKeys.CORSAIR_COASTS, createCorsairCoastBiome(context, new BiomeColorsDTO(
                nearHaradSky, defaultFog, 5212644, 333363, 12107900, 10860366)));
        context.register(MEBiomeKeys.DALE, createDaleBiome(context, new BiomeColorsDTO(
                8230911, 12178175, defaultWater, defaultWaterFog, 6400105, 8703593)));
        context.register(MEBiomeKeys.DARK_ANDUIN_VALES, createAnduinBiome(context, new BiomeColorsDTO(
                7246271, 8951443, 4354703, 1128527, 8032632, 7901046)));
        context.register(MEBiomeKeys.DARK_MIRKWOOD, createMirkwoodBiome(context, new BiomeColorsDTO(
                5269900, 4806731, 4544130, 338483, 4151612, 3823415), true));
        context.register(MEBiomeKeys.DARK_MIRKWOOD_EDGE, createMirkwoodBiome(context, new BiomeColorsDTO(
                5861275, 8293250, 4551554, 467756, 4083260, 3690038), false));
        context.register(MEBiomeKeys.DOL_GULDUR, createMirkwoodBiome(context, new BiomeColorsDTO(
                3684976, 5065045, 2503248, 462892, 3554356, 3424049), false));
        context.register(MEBiomeKeys.DORWINION_HILLS, createDorwinionHillsBiome(context, new BiomeColorsDTO(
                hillySky, defaultFog, defaultWater, defaultWaterFog, 9084547, 8427113)));
        context.register(MEBiomeKeys.DUNLAND_FOOTHILLS, createDunlandFoothillsBiome(context, new BiomeColorsDTO(
                7508201, 10863086, defaultWater, defaultWaterFog, 6722387, 6198343)));
        context.register(MEBiomeKeys.EASTERN_RHOVANION, createRhunBiome(context, new BiomeColorsDTO(
                7905517, defaultFog, 12440052, 591663, 8950352, 7836758)));
        context.register(MEBiomeKeys.ENEDWAITH, createEnedwaithBiome(context, new BiomeColorsDTO(
                7181795, 10731509, 4618461, defaultWaterFog, 8761449, 7842644)));
        context.register(MEBiomeKeys.EREGION, createEregionBiome(context, new BiomeColorsDTO(
                7908853, 11393279, 5077445, 331050, 6858575, 6198343)));
        context.register(MEBiomeKeys.ERIADOR, createEriadorBiome(context, new BiomeColorsDTO(
                defaultSky, 12637429, 4615389, 658236, 7582562, 6592327)));
        context.register(MEBiomeKeys.FANGORN, createFangornBiome(context, new BiomeColorsDTO(
                7972607, defaultFog, 4293787, 338483, 3559947, 1789719)));
        context.register(MEBiomeKeys.FORODWAITH, createForodwaithBiome(context, new BiomeColorsDTO(
                8364543, 10335206, 3823818, 66852, 3494723, 4478280)));
        context.register(MEBiomeKeys.FROZEN_OCEAN, createOceanBiome(context, new BiomeColorsDTO(
                8628223, 10599910, 3750089, 263470, 3494723, 4478280)));
        context.register(MEBiomeKeys.GONDOR, createGondorBiome(context, new BiomeColorsDTO(
                defaultSky, defaultFog, defaultWater, defaultWaterFog, 7582562, 6592327)));
        context.register(MEBiomeKeys.GREY_PLAINS, createGreyPlainsBiome(context, new BiomeColorsDTO(
                hillySky, 12637179, defaultWater, defaultWaterFog, 5939024, 8566393)));
        context.register(MEBiomeKeys.HARAD, createHaradBiome(context, new BiomeColorsDTO(
                nearHaradSky, nearHaradSkyFog, 5407446, 1120828, 12755803, 12963163)));
        context.register(MEBiomeKeys.HARAD_DESERT, createHaradDesertBiome(context, new BiomeColorsDTO(
                nearHaradSky, nearHaradSkyFog, 5407446, 1120828, 13419633, 9615182)));
        context.register(MEBiomeKeys.HARONDOR, createHaradBiome(context, new BiomeColorsDTO(
                nearHaradSky, nearHaradSkyFog, 5406149, 1120828, 12755803, 12963163)));
        context.register(MEBiomeKeys.IRON_HILLS, createIronHillsBiome(context, new BiomeColorsDTO(
                10140415, 13031679, defaultWater, defaultWaterFog, 6922099, 7119988)));
        context.register(MEBiomeKeys.IRON_HILLS_FRONTIER, createIronHillsFrontierBiome(context, new BiomeColorsDTO(
                hillySky, 12637183, defaultWater, defaultWaterFog, 7253882, 7580788)));
        context.register(MEBiomeKeys.LINDON, createLindonBiome(context, new BiomeColorsDTO(
                8827134, 12771327, defaultWater, defaultWaterFog, 6341501, 6202980)));
        context.register(MEBiomeKeys.LONG_LAKE, createLakeBiome(context, new BiomeColorsDTO(
                waterSky, defaultFog, 4352228, 525619, 7576434, 6588506)));
        context.register(MEBiomeKeys.LORIEN_EDGE, createLorienEdgeBiome(context, new BiomeColorsDTO(
                defaultSky, 11455105, defaultWater, defaultWaterFog, 10601262, 6202980)));
        context.register(MEBiomeKeys.LOTHLORIEN, createLothlorienBiome(context, new BiomeColorsDTO(
                defaultSky, 13748853, defaultWater, defaultWaterFog, 12961832, 6989412)));
        context.register(MEBiomeKeys.MIRKWOOD, createMirkwoodBiome(context, new BiomeColorsDTO(
                6849446, 6780008, 4949147, 338483, 4151612, 3823415), true));
        context.register(MEBiomeKeys.MIRKWOOD_EDGE, createMirkwoodBiome(context, new BiomeColorsDTO(
                7578800, 7897724, 4293787, 338483, 4546876, 4284215), false));
        context.register(MEBiomeKeys.MIRKWOOD_FOOTHILLS, createMirkwoodBiome(context, new BiomeColorsDTO(
                7575984, 7897724, 4293787, 338483, 4546876, 4284215), false));
        context.register(MEBiomeKeys.MIRKWOOD_MOUNTAINS, createMirkwoodBiome(context, new BiomeColorsDTO(
                7972607, 9873053, 4293787, 338483, 4678724, 4482114), false));
        context.register(MEBiomeKeys.MIRKWOOD_SWAMP, createMirkwoodSwampBiome(context, new BiomeColorsDTO(
                6981536, 8821922, 5080729, 1458241, 4546876, 4284215)));
        context.register(MEBiomeKeys.MISTY_MOUNTAINS, createMistyMountainsBiome(context, new BiomeColorsDTO(
                hillySky, defaultFog, defaultWater, defaultWaterFog, 7643011, 7709826)));
        context.register(MEBiomeKeys.MORDOR, createMordorBiome(context, new BiomeColorsDTO(
                2695203, 1905947, 6450777, 1513734, 3550502, 2169880)));
        context.register(MEBiomeKeys.MORDOR_MOUNTAINS, createMordorBiome(context, new BiomeColorsDTO(
                2695203, 1905947, 6450777, 1513734, 3550502, 2169880)));
        context.register(MEBiomeKeys.MORDOR_WASTES, createMordorBiome(context, new BiomeColorsDTO(
                5460048, 4999240, 5860962, 731161, 6252369, 4735297)));
        context.register(MEBiomeKeys.NORTHERN_DUNLAND, createNorthDunlandBiome(context, new BiomeColorsDTO(
                7508201, 12964294, defaultWater, defaultWaterFog, 6722387, 6198343), true));
        context.register(MEBiomeKeys.NORTHERN_WASTELANDS, createGreyPlainsBiome(context, new BiomeColorsDTO(
                hillySky, defaultFog, defaultWater, defaultWaterFog, 5932658, 7973008)));
        context.register(MEBiomeKeys.NURN, createNurnBiome(context, new BiomeColorsDTO(
                8954077, 11781331, 5860970, 1321760, 7443043, 6982236)));
        context.register(MEBiomeKeys.NURN_RIVER, createNurnWaterBiome(context, new BiomeColorsDTO(
                8952797, 11779800, 5860963, 863008, 5465422, 5663573)));
        context.register(MEBiomeKeys.NURN_SEA, createNurnWaterBiome(context, new BiomeColorsDTO(
                8952797, 11779800, 5860963, 863008, 5465422, 5663573)));
        context.register(MEBiomeKeys.OCEAN, createOceanBiome(context, new BiomeColorsDTO(
                waterSky, defaultFog, 4157145, defaultWaterFog, 7576434, 6588506)));
        context.register(MEBiomeKeys.OCEAN_COAST, createOceanBiome(context, new BiomeColorsDTO(
                8104447, defaultFog, 5145060, 330291, 7971954, 6590810)));
        context.register(MEBiomeKeys.OLD_ANGMAR, createOldAngmarBiome(context, new BiomeColorsDTO(
                8954077, 11781343, 4814544, 460593, 7443043, 6982236)));
        context.register(MEBiomeKeys.OLD_ARTHEDAIN, createOldArthedainBiome(context, new BiomeColorsDTO(
                7907327, defaultFog, defaultWater, defaultWaterFog, 13301380, 12047734)));
        context.register(MEBiomeKeys.OLD_RHUDAUR, createOldRhudaurBiome(context, new BiomeColorsDTO(
                7508201, 10863086, 4618461, defaultWaterFog, 6722387, 6198343)));
        context.register(MEBiomeKeys.OASIS, createLakeBiome(context, new BiomeColorsDTO(
                nearHaradSky, nearHaradSkyFog, 5407446, 1120828, 7253092, 6592350)));
        context.register(MEBiomeKeys.POND, createPondBiome(context, new BiomeColorsDTO(
                waterSky, defaultFog, 4290786, defaultWaterFog, 7583083, 6592339)));
        context.register(MEBiomeKeys.RHUN, createRhunBiome(context, new BiomeColorsDTO(
                8041727, 12773631, 4618980, defaultWaterFog, 10995507, 7181907)));
        context.register(MEBiomeKeys.RIVENDELL, createRivendellBiome(context, new BiomeColorsDTO(
                9090047, 13426943, defaultWater, defaultWaterFog, 7915639, 6858340)));
        context.register(MEBiomeKeys.RIVENDELL_FOOTHILLS, createRivendellFoothillsBiome(context, new BiomeColorsDTO(
                8695029, 12637426, defaultWater, defaultWaterFog, 8569732, 7644782)));
        context.register(MEBiomeKeys.RIVER, createLakeBiome(context, new BiomeColorsDTO(
                waterSky, defaultFog, 4290790, defaultWaterFog, 7583083, 6592339)));
        context.register(MEBiomeKeys.ROHAN, createRohanBiome(context, new BiomeColorsDTO(
                defaultSky, defaultFog, defaultWater, defaultWaterFog, 10342513, 6918471)));
        context.register(MEBiomeKeys.SEA_OF_RHUN, createLakeBiome(context, new BiomeColorsDTO(
                waterSky, 12638463, 4159204, defaultWaterFog, 10995507, 7181907)));
        context.register(MEBiomeKeys.SHIRE, createShireBiome(context, new BiomeColorsDTO(
                8695029, 12637426, defaultWater, defaultWaterFog, 8576835, 5874947)));
        context.register(MEBiomeKeys.SOUTHEAST_RHOVANION, createRhunBiome(context, new BiomeColorsDTO(
                7571933, 11055575, defaultWater, defaultWaterFog, 8627523, 7052347)));
        context.register(MEBiomeKeys.SOUTHERN_DUNLAND, createNorthDunlandBiome(context, new BiomeColorsDTO(
                7508201, 10863086, defaultWater, defaultWaterFog, 8302697, 7252827), false));
        context.register(MEBiomeKeys.SOUTHERN_FOROCHEL, createSouthernForochelBiome(context, new BiomeColorsDTO(
                hillySky, defaultFog, defaultWater, defaultWaterFog, 7177842, 7971216)));
        context.register(MEBiomeKeys.THE_ANGLE, createTheAngleBiome(context, new BiomeColorsDTO(
                defaultSky, defaultFog, 4615389, 658236, 9749859, 7378759)));
        context.register(MEBiomeKeys.THE_OLD_FOREST, createTheOldForestBiome(context, new BiomeColorsDTO(
                6785744, 10004675, 4421513, 402733, 2051603, 1789719)));
        context.register(MEBiomeKeys.THE_WOLD, createRohanBiome(context, new BiomeColorsDTO(
                defaultSky, defaultFog, defaultWater, defaultWaterFog, 12309403, 12242068)));
        context.register(MEBiomeKeys.TOLFALAS, createTolfalasBiome(context, new BiomeColorsDTO(
                defaultSky, defaultFog, defaultWater, defaultWaterFog, 13419633, 9615182)));
        context.register(MEBiomeKeys.TROLLSHAWS, createTrollshawsBiome(context, new BiomeColorsDTO(
                6980296, 10402016, 4618461, defaultWaterFog, 9666387, 9731143)));
        context.register(MEBiomeKeys.UMBAR, createUmbarBiome(context, new BiomeColorsDTO(
                7254527, 12638463, 5212644, 333363, 11059059, 9284946)));
        context.register(MEBiomeKeys.WASTE_POND, createLakeBiome(context, new BiomeColorsDTO(
                8163746, 10926783, 5860963, 863008, 4020033, 2371608)));
        context.register(MEBiomeKeys.WHITE_MOUNTAINS, createWhiteMountainsBiome(context, new BiomeColorsDTO(
                hillySky, 12638463, defaultWater, defaultWaterFog, 7183466, 7513204)));
        context.register(MEBiomeKeys.WOODLAND_REALM, createWoodlandRealmBiome(context, new BiomeColorsDTO(
                8497918, 10666932, 4492967, 471355, 2780195, 2713634)));
    }

    public static Biome createAnduinBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addFarmAnimals(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addDefaultVegetation(generationSettings);
        ModBiomeFeatures.addBirchTrees(vegetation);
        ModBiomeFeatures.addLarchTrees(vegetation);
        ModBiomeFeatures.addMapleTrees(vegetation);

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createBarrowDownsBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        DefaultBiomeFeatures.addDefaultDisks(generationSettings);
        vegetation.add(VegetationPlacedFeatures.PATCH_GRASS_FOREST);

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createBlueMountainsBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addMountainsMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addNordicVegetation(generationSettings);
        ModBiomeFeatures.addLarchTrees(vegetation);
        ModBiomeFeatures.addPineTrees(vegetation);
        ModBiomeFeatures.addSpruceTrees(vegetation);

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createCorsairCoastBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addHaradMobs(spawnSettings);
        ModSpawnSettingsBuilder.addLlama(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addHaradVegetation(generationSettings);
        ModBiomeFeatures.addGraniteBoulder(vegetation);

        ModBiomeFeatures.addPalmTrees(vegetation);

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createDaleBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addPlainsMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addDefaultVegetation(generationSettings);
        addNordicTrees(generationSettings);

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createDorwinionHillsBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addMountainsMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addNordicVegetation(generationSettings);
        ModBiomeFeatures.addBirchTrees(vegetation);
        ModBiomeFeatures.addLarchTrees(vegetation);

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createDunlandFoothillsBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addMountainsMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addNordicVegetation(generationSettings);
        ModBiomeFeatures.addBirchTrees(vegetation);
        ModBiomeFeatures.addLarchTrees(vegetation);
        ModBiomeFeatures.addPineTrees(vegetation);
        ModBiomeFeatures.addSpruceTrees(vegetation);

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createEnedwaithBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addPlainsMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addDefaultVegetation(generationSettings);

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createEregionBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addFarmAnimals(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addDefaultVegetation(generationSettings);
        ModBiomeFeatures.addBirchTrees(vegetation);
        ModBiomeFeatures.addLarchTrees(vegetation);
        ModBiomeFeatures.addMegaOakTrees(vegetation);
        ModBiomeFeatures.addPineTrees(vegetation);
        ModBiomeFeatures.addSpruceTrees(vegetation);

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createEriadorBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addPlainsMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addEriadorVegetation(generationSettings);
        ModBiomeFeatures.addRareBeechTrees(vegetation);
        ModBiomeFeatures.addRareBirchTrees(vegetation);

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createFangornBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addDefaultVegetation(generationSettings);

        ModBiomeFeatures.addMegaBirchCommonTrees(vegetation);
        ModBiomeFeatures.addMegaDarkOakCommonTrees(vegetation);
        ModBiomeFeatures.addMegaOakCommonTrees(vegetation);

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createForodwaithBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addForochelMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addForodwaithVegetation(generationSettings);

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createGondorBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addPlainsMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addDefaultVegetation(generationSettings);
        ModBiomeFeatures.addDioriteBoulder(vegetation);

        vegetation.add(VegetationPlacedFeatures.TREES_PLAINS);
        ModBiomeFeatures.addBeechTrees(vegetation);
        ModBiomeFeatures.addLebethronTrees(vegetation);

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createGreyPlainsBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addMountainsMobs(spawnSettings);
        ModSpawnSettingsBuilder.addNordicMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addNordicVegetation(generationSettings);
        addNordicTrees(generationSettings);

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createHaradBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addHaradMobs(spawnSettings);
        ModSpawnSettingsBuilder.addFarmAnimals(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addHaradVegetation(generationSettings);
        ModBiomeFeatures.addAcaciaTrees(vegetation);

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createHaradDesertBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addHaradMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addHaradDesertVegetation(generationSettings);

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createIronHillsBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addMountainsMobs(spawnSettings);
        ModSpawnSettingsBuilder.addNordicMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addNordicVegetation(generationSettings);
        addNordicTrees(generationSettings);

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createIronHillsFrontierBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addFarmAnimals(spawnSettings);
        ModSpawnSettingsBuilder.addNordicMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addNordicVegetation(generationSettings);
        addNordicTrees(generationSettings);

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createLindonBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addFarmAnimals(spawnSettings);
        ModSpawnSettingsBuilder.addPlainsMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addDefaultVegetation(generationSettings);
        ModBiomeFeatures.addBirchTrees(vegetation);
        ModBiomeFeatures.addMegaBirchTrees(vegetation);
        ModBiomeFeatures.addMegaOakTrees(vegetation);

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createLorienEdgeBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addFarmAnimals(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addLothlorienVegetation(generationSettings);
        ModBiomeFeatures.addMallornTrees(vegetation);

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createLothlorienBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addFarmAnimals(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addLothlorienVegetation(generationSettings);
        ModBiomeFeatures.addMegaMallornTrees(vegetation);

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createMirkwoodBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors, boolean bigTrees) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addWolves(spawnSettings);

        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addMirkwoodVegetation(generationSettings);

        if(bigTrees) addMegaMirkwoodTrees(generationSettings);
        else addMirkwoodTrees(generationSettings);

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createMirkwoodSwampBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addSwampMobs(spawnSettings);

        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addMirkwoodVegetation(generationSettings);

        addMirkwoodTrees(generationSettings);
        ModBiomeFeatures.addWillowTrees(vegetation);

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createMistyMountainsBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addNordicVegetation(generationSettings);

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createMordorBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addMordorVegetation(generationSettings);

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createNorthDunlandBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors, boolean trees) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addFarmAnimals(spawnSettings);
        ModSpawnSettingsBuilder.addNordicMobs(spawnSettings);
        ModSpawnSettingsBuilder.addWolves(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addNordicVegetation(generationSettings);

        if(trees) {
            ModBiomeFeatures.addLarchTrees(vegetation);
            ModBiomeFeatures.addPineTrees(vegetation);
            ModBiomeFeatures.addSpruceTrees(vegetation);
        } else {
            ModBiomeFeatures.addLarchTrees(vegetation);
            ModBiomeFeatures.addSparsePineTrees(vegetation);
            ModBiomeFeatures.addSparseSpruceTrees(vegetation);
        }
        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createNurnBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addDefaultVegetation(generationSettings);
        ModBiomeFeatures.addToughBerriesRare(vegetation);

        vegetation.add(VegetationPlacedFeatures.TREES_PLAINS);
        ModBiomeFeatures.addMegaOakTrees(vegetation);

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createNurnWaterBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addSwampMobs(spawnSettings);
        ModSpawnSettingsBuilder.addRiverAnimals(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addOceanVegetation(generationSettings);

        ModBiomeFeatures.addWillowTrees(vegetation);

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createOldAngmarBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addNordicVegetation(generationSettings);

        ModBiomeFeatures.addDeadPineTrees(vegetation);
        ModBiomeFeatures.addSparsePineTrees(vegetation);
        ModBiomeFeatures.addScarceSpruceTrees(vegetation);

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createOldArthedainBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addPlainsMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addArthedainVegetation(generationSettings);

        ModBiomeFeatures.addBeechTrees(vegetation);
        ModBiomeFeatures.addSparseBirchTrees(vegetation);
        ModBiomeFeatures.addMegaOakTrees(vegetation);

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createOldRhudaurBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addNordicVegetation(generationSettings);

        ModBiomeFeatures.addDeadPineTrees(vegetation);
        ModBiomeFeatures.addSparsePineTrees(vegetation);
        ModBiomeFeatures.addSpruceTrees(vegetation);

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createRhunBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addPlainsMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addRhunVegetation(generationSettings);

        ModBiomeFeatures.addBeechTrees(vegetation);
        ModBiomeFeatures.addSparseBirchTrees(vegetation);
        ModBiomeFeatures.addMegaOakTrees(vegetation);

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createRivendellBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addPlainsMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addRivendellVegetation(generationSettings);

        ModBiomeFeatures.addBeechTrees(vegetation);
        ModBiomeFeatures.addSparseBirchTrees(vegetation);
        ModBiomeFeatures.addMegaOakTrees(vegetation);

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createRivendellFoothillsBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addMountainsMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addNordicVegetation(generationSettings);

        ModBiomeFeatures.addSparseBirchTrees(vegetation);
        ModBiomeFeatures.addSparsePineTrees(vegetation);
        ModBiomeFeatures.addSparseSpruceTrees(vegetation);

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createRohanBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addPlainsMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addDefaultVegetation(generationSettings);
        ModBiomeFeatures.addGraniteBoulder(vegetation);

        vegetation.add(VegetationPlacedFeatures.TREES_PLAINS);
        ModBiomeFeatures.addBeechTrees(vegetation);

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createShireBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addPlainsMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addShireVegetation(generationSettings);

        vegetation.add(VegetationPlacedFeatures.TREES_PLAINS);
        ModBiomeFeatures.addRareBeechTrees(vegetation);
        ModBiomeFeatures.addRareBirchTrees(vegetation);
        ModBiomeFeatures.addRareMegaOakTrees(vegetation);

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createSouthernForochelBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addNordicMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addNordicVegetation(generationSettings);
        ModBiomeFeatures.addSparsePineTrees(vegetation);
        ModBiomeFeatures.addSparseSpruceTrees(vegetation);

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createTheAngleBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addPlainsMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addEriadorVegetation(generationSettings);

        ModBiomeFeatures.addBeechTrees(vegetation);
        ModBiomeFeatures.addLarchTrees(vegetation);

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createTheOldForestBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addEriadorVegetation(generationSettings);

        ModBiomeFeatures.addMegaBirchCommonTrees(vegetation);
        ModBiomeFeatures.addMegaDarkOakCommonTrees(vegetation);
        ModBiomeFeatures.addMegaOakCommonTrees(vegetation);

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createTolfalasBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addFarmAnimals(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addDefaultVegetation(generationSettings);

        ModBiomeFeatures.addLarchTrees(vegetation);
        ModBiomeFeatures.addLebethronTrees(vegetation);
        ModBiomeFeatures.addSparsePineTrees(vegetation);
        ModBiomeFeatures.addSparseSpruceTrees(vegetation);

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createTrollshawsBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addFarmAnimals(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addEriadorVegetation(generationSettings);

        ModBiomeFeatures.addBeechTrees(vegetation);
        ModBiomeFeatures.addPineTrees(vegetation);
        ModBiomeFeatures.addSpruceTrees(vegetation);

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createUmbarBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addHaradMobs(spawnSettings);
        ModSpawnSettingsBuilder.addFarmAnimals(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addHaradVegetation(generationSettings);
        ModBiomeFeatures.addGraniteBoulder(vegetation);

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createLakeBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addRiverAnimals(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addOceanVegetation(generationSettings);

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createWhiteMountainsBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addMountainsMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addNordicVegetation(generationSettings);
        ModBiomeFeatures.addLarchTrees(vegetation);
        ModBiomeFeatures.addPineTrees(vegetation);
        ModBiomeFeatures.addSpruceTrees(vegetation);

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createWoodlandRealmBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addWolves(spawnSettings);

        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addMirkwoodVegetation(generationSettings);
        addMirkwoodTrees(generationSettings);

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createOceanBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addOceanAnimals(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addOceanVegetation(generationSettings);

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createPondBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addRiverAnimals(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addOceanVegetation(generationSettings);
        ModBiomeFeatures.addWillowTrees(vegetation);

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static void addColdTrees(GenerationSettings.LookupBackedBuilder generationSettings) {
        ModBiomeFeatures.addLarchTrees(vegetation);
        ModBiomeFeatures.addMapleTrees(vegetation);
    }

    public static void addNordicTrees(GenerationSettings.LookupBackedBuilder generationSettings) {
        ModBiomeFeatures.addLarchTrees(vegetation);
        ModBiomeFeatures.addSparsePineTrees(vegetation);
        ModBiomeFeatures.addSparseSpruceTrees(vegetation);
    }

    public static void addMirkwoodTrees(GenerationSettings.LookupBackedBuilder generationSettings) {
        ModBiomeFeatures.addMirkwoodTrees(vegetation);
    }

    public static void addMegaMirkwoodTrees(GenerationSettings.LookupBackedBuilder generationSettings) {
        ModBiomeFeatures.addMegaMirkwoodTrees(vegetation);
    }

    public static void addDefaultVegetation(GenerationSettings.LookupBackedBuilder generationSettings) {
        DefaultBiomeFeatures.addDefaultDisks(generationSettings);
        vegetation.add(VegetationPlacedFeatures.PATCH_GRASS_FOREST);
        vegetation.add(VegetationPlacedFeatures.FLOWER_DEFAULT);
        vegetation.add(VegetationPlacedFeatures.BROWN_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.RED_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.PATCH_SUGAR_CANE);
        vegetation.add(VegetationPlacedFeatures.PATCH_PUMPKIN);
        vegetation.add(BoulderPlacedFeatures.STONE_BOULDER);
    }

    public static void addArthedainVegetation(GenerationSettings.LookupBackedBuilder generationSettings) {
        DefaultBiomeFeatures.addDefaultDisks(generationSettings);
        vegetation.add(VegetationPlacedFeatures.PATCH_GRASS_FOREST);
        vegetation.add(VegetationPlacedFeatures.FLOWER_DEFAULT);
        vegetation.add(VegetationPlacedFeatures.BROWN_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.RED_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.PATCH_SUGAR_CANE);
        vegetation.add(VegetationPlacedFeatures.PATCH_PUMPKIN);
        ModBiomeFeatures.addWildGarlic(vegetation);
        ModBiomeFeatures.addWildLeek(vegetation);
        ModBiomeFeatures.addWildLettuce(vegetation);
        ModBiomeFeatures.addWildPotato(vegetation);
        ModBiomeFeatures.addStoneBoulder(vegetation);
    }

    public static void addEriadorVegetation(GenerationSettings.LookupBackedBuilder generationSettings) {
        DefaultBiomeFeatures.addDefaultDisks(generationSettings);
        vegetation.add(VegetationPlacedFeatures.PATCH_GRASS_FOREST);
        vegetation.add(VegetationPlacedFeatures.FLOWER_DEFAULT);
        vegetation.add(VegetationPlacedFeatures.BROWN_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.RED_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.PATCH_SUGAR_CANE);
        vegetation.add(VegetationPlacedFeatures.PATCH_PUMPKIN);
        vegetation.add(VegetationPlacedFeatures.TREES_PLAINS);
        ModBiomeFeatures.addWildLeek(vegetation);
        ModBiomeFeatures.addWildLettuce(vegetation);
        ModBiomeFeatures.addWildOnion(vegetation);
        ModBiomeFeatures.addStoneBoulder(vegetation);
    }

    public static void addForodwaithVegetation(GenerationSettings.LookupBackedBuilder generationSettings) {
        DefaultBiomeFeatures.addDefaultDisks(generationSettings);
        ModBiomeFeatures.addToughBerriesRare(vegetation);
    }

    public static void addHaradVegetation(GenerationSettings.LookupBackedBuilder generationSettings) {
        DefaultBiomeFeatures.addDefaultDisks(generationSettings);
        vegetation.add(VegetationPlacedFeatures.PATCH_GRASS_SAVANNA);
        vegetation.add(VegetationPlacedFeatures.PATCH_TALL_GRASS);
        vegetation.add(VegetationPlacedFeatures.PATCH_SUGAR_CANE);
        ModBiomeFeatures.addWildBellPepper(vegetation);
        ModBiomeFeatures.addWildTomato(vegetation);
    }

    public static void addHaradDesertVegetation(GenerationSettings.LookupBackedBuilder generationSettings) {
        DefaultBiomeFeatures.addDefaultDisks(generationSettings);
        vegetation.add(VegetationPlacedFeatures.PATCH_SUGAR_CANE);
        vegetation.add(VegetationPlacedFeatures.PATCH_GRASS_SAVANNA);
    }

    public static void addLothlorienVegetation(GenerationSettings.LookupBackedBuilder generationSettings) {
        DefaultBiomeFeatures.addDefaultDisks(generationSettings);
        vegetation.add(VegetationPlacedFeatures.PATCH_GRASS_FOREST);
        vegetation.add(VegetationPlacedFeatures.FLOWER_MEADOW);
        vegetation.add(VegetationPlacedFeatures.BROWN_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.RED_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.PATCH_SUGAR_CANE);
        vegetation.add(VegetationPlacedFeatures.PATCH_PUMPKIN);
        ModBiomeFeatures.addMallos(vegetation);
        ModBiomeFeatures.addWildCarrot(vegetation);
        ModBiomeFeatures.addWildLettuce(vegetation);
        ModBiomeFeatures.addWildOnion(vegetation);
    }

    public static void addMirkwoodVegetation(GenerationSettings.LookupBackedBuilder generationSettings) {
        DefaultBiomeFeatures.addDefaultDisks(generationSettings);
        vegetation.add(VegetationPlacedFeatures.PATCH_LARGE_FERN);
        vegetation.add(VegetationPlacedFeatures.PATCH_GRASS_FOREST);
        vegetation.add(VegetationPlacedFeatures.FLOWER_FOREST_FLOWERS);
        vegetation.add(VegetationPlacedFeatures.BROWN_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.RED_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.PATCH_BERRY_RARE);
        ModBiomeFeatures.addWildFlax(vegetation);
        ModBiomeFeatures.addWildGarlic(vegetation);
        ModBiomeFeatures.addWildOnion(vegetation);
        ModBiomeFeatures.addMossyBoulder(vegetation);
        ModBiomeFeatures.addMirkwoodFoliage(vegetation);
    }

    public static void addMordorVegetation(GenerationSettings.LookupBackedBuilder generationSettings) {
        DefaultBiomeFeatures.addDefaultDisks(generationSettings);
        ModBiomeFeatures.addToughBerries(vegetation);
    }

    public static void addNordicVegetation(GenerationSettings.LookupBackedBuilder generationSettings) {
        DefaultBiomeFeatures.addDefaultDisks(generationSettings);
        vegetation.add(VegetationPlacedFeatures.PATCH_LARGE_FERN);
        vegetation.add(VegetationPlacedFeatures.PATCH_GRASS_TAIGA);
        vegetation.add(VegetationPlacedFeatures.FLOWER_DEFAULT);
        vegetation.add(VegetationPlacedFeatures.BROWN_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.RED_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.PATCH_BERRY_RARE);
        ModBiomeFeatures.addAndesiteBoulder(vegetation);
    }

    public static void addOceanVegetation(GenerationSettings.LookupBackedBuilder generationSettings) {
        DefaultBiomeFeatures.addDefaultDisks(generationSettings);
        vegetation.add(OceanPlacedFeatures.KELP_COLD);
        vegetation.add(OceanPlacedFeatures.SEAGRASS_SIMPLE);
        vegetation.add(VegetationPlacedFeatures.PATCH_GRASS_FOREST);
        vegetation.add(VegetationPlacedFeatures.FLOWER_DEFAULT);
        vegetation.add(VegetationPlacedFeatures.PATCH_SUGAR_CANE);
        vegetation.add(VegetationPlacedFeatures.PATCH_PUMPKIN);
        ModBiomeFeatures.addReedsFoliage(vegetation);
    }

    public static void addRivendellVegetation(GenerationSettings.LookupBackedBuilder generationSettings) {
        DefaultBiomeFeatures.addDefaultDisks(generationSettings);
        vegetation.add(VegetationPlacedFeatures.PATCH_GRASS_FOREST);
        vegetation.add(VegetationPlacedFeatures.FLOWER_MEADOW);
        vegetation.add(VegetationPlacedFeatures.BROWN_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.RED_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.PATCH_SUGAR_CANE);
        vegetation.add(VegetationPlacedFeatures.PATCH_PUMPKIN);
        ModBiomeFeatures.addWildLeek(vegetation);
        ModBiomeFeatures.addWildLettuce(vegetation);
        ModBiomeFeatures.addWildOnion(vegetation);
        ModBiomeFeatures.addStoneBoulder(vegetation);
    }

    public static void addRhunVegetation(GenerationSettings.LookupBackedBuilder generationSettings) {
        DefaultBiomeFeatures.addDefaultDisks(generationSettings);
        vegetation.add(VegetationPlacedFeatures.PATCH_GRASS_FOREST);
        vegetation.add(VegetationPlacedFeatures.FLOWER_DEFAULT);
        vegetation.add(VegetationPlacedFeatures.BROWN_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.RED_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.PATCH_SUGAR_CANE);
        vegetation.add(VegetationPlacedFeatures.PATCH_PUMPKIN);
        ModBiomeFeatures.addWildCarrot(vegetation);
        ModBiomeFeatures.addWildBellPepper(vegetation);
        ModBiomeFeatures.addWildLettuce(vegetation);
        ModBiomeFeatures.addWildOnion(vegetation);
        ModBiomeFeatures.addStoneBoulder(vegetation);
    }

    public static void addShireVegetation(GenerationSettings.LookupBackedBuilder generationSettings) {
        DefaultBiomeFeatures.addDefaultDisks(generationSettings);
        vegetation.add(VegetationPlacedFeatures.PATCH_GRASS_FOREST);
        vegetation.add(VegetationPlacedFeatures.FLOWER_DEFAULT);
        vegetation.add(VegetationPlacedFeatures.BROWN_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.RED_MUSHROOM_NORMAL);
        vegetation.add(VegetationPlacedFeatures.PATCH_SUGAR_CANE);
        vegetation.add(VegetationPlacedFeatures.PATCH_PUMPKIN);
        ModBiomeFeatures.addStrawberries(vegetation);
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
        DefaultBiomeFeatures.addAmethystGeodes(generationSettings);
        DefaultBiomeFeatures.addMineables(generationSettings);
        DefaultBiomeFeatures.addFrozenTopLayer(generationSettings);

        vegetation = vegetation.stream().sorted(Comparator.comparing(a -> a.getValue().toString())).toList();
        for (RegistryKey<PlacedFeature> feature: vegetation) {
            generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, feature);
        }

        Biome biome = (new Biome.Builder())
                .precipitation(true)
                .temperature(0.5F)
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
        return biome;
    }
}
