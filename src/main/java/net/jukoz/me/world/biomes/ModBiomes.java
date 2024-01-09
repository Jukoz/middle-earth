package net.jukoz.me.world.biomes;

import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.registry.Registerable;

public class ModBiomes {
    public static final int defaultSky = 7907327;
    public static final int defaultFog = 12638463;
    public static final int defaultWater = 4159204;
    public static final int defaultWaterFog = 329011;
    public static final int hillySky = 8233727;
    public static final int waterSky = 8103167;
    public static final int nearHaradSky = 7254527;
    public static final int nearHaradSkyFog = 12902399;

    public static void bootstrap(Registerable<Biome> context) {
        context.register(MEBiomeKeys.ANDUIN_VALES, createAnduinBiome(context, new BiomeColorsDTO(
                defaultSky, defaultFog, defaultWater, defaultWaterFog, 8703593, 8703593)));
        context.register(MEBiomeKeys.BARROW_DOWNS, createBarrowDownsBiome(context, new BiomeColorsDTO(
                5993621, 7635851, 4812936, 3034721, 6721389, 6525545)));
        context.register(MEBiomeKeys.BLUE_MOUNTAINS, createAnduinBiome(context, new BiomeColorsDTO(
                7903952, 12241898, defaultWater, defaultWaterFog, 6791282, 7773545)));
        context.register(MEBiomeKeys.BLUE_MOUNTAINS_FOOTHILLS, createDefaultBiome(context, new BiomeColorsDTO(
                7905261, 12241911, defaultWater, defaultWaterFog, 7125373, 8703593)));
        context.register(MEBiomeKeys.CORSAIR_COASTS, createDefaultBiome(context, new BiomeColorsDTO(
                nearHaradSky, defaultFog, 5212644, 333363, 12107900, 10860366)));
        context.register(MEBiomeKeys.DALE, createDefaultBiome(context, new BiomeColorsDTO(
                8230911, 12178175, defaultWater, defaultWaterFog, 6400105, 8703593)));
        context.register(MEBiomeKeys.DARK_ANDUIN_VALES, createDefaultBiome(context, new BiomeColorsDTO(
                7246271, 8951443, 4354703, 1128527, 8032632, 7901046)));
        context.register(MEBiomeKeys.DARK_MIRKWOOD, createDefaultBiome(context, new BiomeColorsDTO(
                5269900, 4806731, 4544130, 338483, 4151612, 3823415)));
        context.register(MEBiomeKeys.DARK_MIRKWOOD_EDGE, createDefaultBiome(context, new BiomeColorsDTO(
                5861275, 8293250, 4551554, 467756, 4083260, 3690038)));
        context.register(MEBiomeKeys.DOL_GULDUR, createDefaultBiome(context, new BiomeColorsDTO(
                3684976, 5065045, 2503248, 462892, 3554356, 3424049)));
        context.register(MEBiomeKeys.DORWINION_HILLS, createDefaultBiome(context, new BiomeColorsDTO(
                hillySky, defaultFog, defaultWater, defaultWaterFog, 9084547, 8427113)));
        context.register(MEBiomeKeys.DUNLAND_FOOTHILLS, createDefaultBiome(context, new BiomeColorsDTO(
                7508201, 10863086, defaultWater, defaultWaterFog, 6722387, 6198343)));
        context.register(MEBiomeKeys.EASTERN_RHOVANION, createDefaultBiome(context, new BiomeColorsDTO(
                7905517, defaultFog, 12440052, 591663, 8950352, 7836758)));
        context.register(MEBiomeKeys.ENEDWAITH, createDefaultBiome(context, new BiomeColorsDTO(
                7181795, 10731509, 4618461, defaultWaterFog, 8761449, 7842644)));
        context.register(MEBiomeKeys.EREGION, createDefaultBiome(context, new BiomeColorsDTO(
                7908853, 11393279, 5077445, 331050, 6858575, 6198343)));
        context.register(MEBiomeKeys.ERIADOR, createDefaultBiome(context, new BiomeColorsDTO(
                defaultSky, 12637429, 4615389, 658236, 7582562, 6592327)));
        context.register(MEBiomeKeys.FANGORN, createDefaultBiome(context, new BiomeColorsDTO(
                7972607, defaultFog, 4293787, 338483, 3559947, 1789719)));
        context.register(MEBiomeKeys.FORODWAITH, createDefaultBiome(context, new BiomeColorsDTO(
                8364543, 10335206, 3823818, 66852, 3494723, 4478280)));
        context.register(MEBiomeKeys.FROZEN_OCEAN, createDefaultBiome(context, new BiomeColorsDTO(
                8628223, 10599910, 3750089, 263470, 3494723, 4478280)));
        context.register(MEBiomeKeys.GONDOR, createDefaultBiome(context, new BiomeColorsDTO(
                defaultSky, defaultFog, defaultWater, defaultWaterFog, 7582562, 6592327)));
        context.register(MEBiomeKeys.GREY_PLAINS, createDefaultBiome(context, new BiomeColorsDTO(
                hillySky, 12637179, defaultWater, defaultWaterFog, 5939024, 8566393)));
        context.register(MEBiomeKeys.HARAD, createDefaultBiome(context, new BiomeColorsDTO(
                nearHaradSky, nearHaradSkyFog, 5407446, 1120828, 12755803, 12963163)));
        context.register(MEBiomeKeys.HARAD_DESERT, createDefaultBiome(context, new BiomeColorsDTO(
                nearHaradSky, nearHaradSkyFog, 5407446, 1120828, 13419633, 9615182)));
        context.register(MEBiomeKeys.HARONDOR, createDefaultBiome(context, new BiomeColorsDTO(
                nearHaradSky, nearHaradSkyFog, 5406149, 1120828, 12755803, 12963163)));
        context.register(MEBiomeKeys.IRON_HILLS, createDefaultBiome(context, new BiomeColorsDTO(
                10140415, 13031679, defaultWater, defaultWaterFog, 6922099, 7119988)));
        context.register(MEBiomeKeys.IRON_HILLS_FRONTIER, createDefaultBiome(context, new BiomeColorsDTO(
                hillySky, 12637183, defaultWater, defaultWaterFog, 7253882, 7580788)));
        context.register(MEBiomeKeys.LINDON, createDefaultBiome(context, new BiomeColorsDTO(
                8827134, 12771327, defaultWater, defaultWaterFog, 6341501, 6202980)));
        context.register(MEBiomeKeys.LONG_LAKE, createDefaultBiome(context, new BiomeColorsDTO(
                waterSky, defaultFog, 4352228, 525619, 7576434, 6588506)));
        context.register(MEBiomeKeys.LORIEN_EDGE, createDefaultBiome(context, new BiomeColorsDTO(
                defaultSky, 11455105, defaultWater, defaultWaterFog, 10601262, 6202980)));
        context.register(MEBiomeKeys.LOTHLORIEN, createDefaultBiome(context, new BiomeColorsDTO(
                defaultSky, 13748853, defaultWater, defaultWaterFog, 12961832, 6989412)));
        context.register(MEBiomeKeys.MIRKWOOD, createDefaultBiome(context, new BiomeColorsDTO(
                6849446, 6780008, 4949147, 338483, 4151612, 3823415)));
        context.register(MEBiomeKeys.MIRKWOOD_EDGE, createDefaultBiome(context, new BiomeColorsDTO(
                7578800, 7897724, 4293787, 338483, 4546876, 4284215)));
        context.register(MEBiomeKeys.MIRKWOOD_FOOTHILLS, createDefaultBiome(context, new BiomeColorsDTO(
                7575984, 7897724, 4293787, 338483, 4546876, 4284215)));
        context.register(MEBiomeKeys.MIRKWOOD_MOUNTAINS, createDefaultBiome(context, new BiomeColorsDTO(
                7972607, 9873053, 4293787, 338483, 4678724, 4482114)));
        context.register(MEBiomeKeys.MIRKWOOD_SWAMP, createDefaultBiome(context, new BiomeColorsDTO(
                6981536, 8821922, 5080729, 1458241, 4546876, 4284215)));
        context.register(MEBiomeKeys.MISTY_MOUNTAINS, createDefaultBiome(context, new BiomeColorsDTO(
                hillySky, defaultFog, defaultWater, defaultWaterFog, 7643011, 7709826)));
        context.register(MEBiomeKeys.MORDOR, createDefaultBiome(context, new BiomeColorsDTO(
                2695203, 1905947, 6450777, 1513734, 3550502, 2169880)));
        context.register(MEBiomeKeys.MORDOR_MOUNTAINS, createDefaultBiome(context, new BiomeColorsDTO(
                2695203, 1905947, 6450777, 1513734, 3550502, 2169880)));
        context.register(MEBiomeKeys.MORDOR_WASTES, createDefaultBiome(context, new BiomeColorsDTO(
                5460048, 4999240, 5860962, 731161, 6252369, 4735297)));
        context.register(MEBiomeKeys.NORTHERN_DUNLAND, createDefaultBiome(context, new BiomeColorsDTO(
                7508201, 12964294, defaultWater, defaultWaterFog, 6722387, 6198343)));
        context.register(MEBiomeKeys.NORTHERN_WASTELANDS, createDefaultBiome(context, new BiomeColorsDTO(
                hillySky, defaultFog, defaultWater, defaultWaterFog, 5932658, 7973008)));
        context.register(MEBiomeKeys.NURN, createDefaultBiome(context, new BiomeColorsDTO(
                8954077, 11781343, 5860970, 1321760, 7443043, 6982236)));
        context.register(MEBiomeKeys.NURN_RIVER, createDefaultBiome(context, new BiomeColorsDTO(
                8952797, 11779800, 5860963, 863008, 5465422, 5663573)));
        context.register(MEBiomeKeys.NURN_SEA, createDefaultBiome(context, new BiomeColorsDTO(
                8952797, 11779800, 5860963, 863008, 5465422, 5663573)));
        context.register(MEBiomeKeys.OCEAN, createDefaultBiome(context, new BiomeColorsDTO(
                waterSky, defaultFog, 4157145, defaultWaterFog, 7576434, 6588506)));
        context.register(MEBiomeKeys.OCEAN_COAST, createDefaultBiome(context, new BiomeColorsDTO(
                8104447, defaultFog, 5145060, 330291, 7971954, 6590810)));
        context.register(MEBiomeKeys.OLD_ANGMAR, createDefaultBiome(context, new BiomeColorsDTO(
                8954077, 11781343, 4814544, 460593, 7443043, 6982236)));
        context.register(MEBiomeKeys.OLD_ARTHEDAIN, createDefaultBiome(context, new BiomeColorsDTO(
                7907327, defaultFog, defaultWater, defaultWaterFog, 13301380, 12047734)));
        context.register(MEBiomeKeys.OLD_RHUDAUR, createDefaultBiome(context, new BiomeColorsDTO(
                7508201, 10863086, 4618461, defaultWaterFog, 6722387, 6198343)));
        context.register(MEBiomeKeys.POND, createDefaultBiome(context, new BiomeColorsDTO(
                waterSky, defaultFog, 4290786, defaultWaterFog, 7583083, 6592339)));
        context.register(MEBiomeKeys.RHUN, createDefaultBiome(context, new BiomeColorsDTO(
                8041727, 12773631, 4618980, defaultWaterFog, 10995507, 7181907)));
        context.register(MEBiomeKeys.RIVENDELL, createDefaultBiome(context, new BiomeColorsDTO(
                9090047, 13426943, defaultWater, defaultWaterFog, 7915639, 6858340)));
        context.register(MEBiomeKeys.RIVENDELL_FOOTHILLS, createDefaultBiome(context, new BiomeColorsDTO(
                8695029, 12637426, defaultWater, defaultWaterFog, 8569732, 7644782)));
        context.register(MEBiomeKeys.RIVER, createDefaultBiome(context, new BiomeColorsDTO(
                waterSky, defaultFog, 4290790, defaultWaterFog, 7583083, 6592339)));
        context.register(MEBiomeKeys.ROHAN, createDefaultBiome(context, new BiomeColorsDTO(
                defaultSky, defaultFog, defaultWater, defaultWaterFog, 10342513, 6918471)));
        context.register(MEBiomeKeys.SEA_OF_RHUN, createDefaultBiome(context, new BiomeColorsDTO(
                waterSky, 12638463, 4159204, defaultWaterFog, 10995507, 7181907)));
        context.register(MEBiomeKeys.SHIRE, createDefaultBiome(context, new BiomeColorsDTO(
                8695029, 12637426, defaultWater, defaultWaterFog, 8576835, 5874947)));
        context.register(MEBiomeKeys.SOUTHEAST_RHOVANION, createDefaultBiome(context, new BiomeColorsDTO(
                7571933, 11055575, defaultWater, defaultWaterFog, 8627523, 7052347)));
        context.register(MEBiomeKeys.SOUTHERN_DUNLAND, createDefaultBiome(context, new BiomeColorsDTO(
                7508201, 10863086, defaultWater, defaultWaterFog, 8302697, 7252827)));
        context.register(MEBiomeKeys.SOUTHERN_FOROCHEL, createDefaultBiome(context, new BiomeColorsDTO(
                hillySky, defaultFog, defaultWater, defaultWaterFog, 7177842, 7971216)));
        context.register(MEBiomeKeys.THE_ANGLE, createDefaultBiome(context, new BiomeColorsDTO(
                defaultSky, defaultFog, 4615389, 658236, 9749859, 7378759)));
        context.register(MEBiomeKeys.THE_OLD_FOREST, createDefaultBiome(context, new BiomeColorsDTO(
                6785744, 10004675, 4421513, 402733, 2051603, 1789719)));
        context.register(MEBiomeKeys.THE_WOLD, createDefaultBiome(context, new BiomeColorsDTO(
                defaultSky, defaultFog, defaultWater, defaultWaterFog, 12309403, 12242068)));
        context.register(MEBiomeKeys.TOLFALAS, createDefaultBiome(context, new BiomeColorsDTO(
                defaultSky, defaultFog, defaultWater, defaultWaterFog, 13419633, 9615182)));
        context.register(MEBiomeKeys.TROLLSHAWS, createDefaultBiome(context, new BiomeColorsDTO(
                6980296, 10402016, 4618461, defaultWaterFog, 9666387, 9731143)));
        context.register(MEBiomeKeys.UMBAR, createDefaultBiome(context, new BiomeColorsDTO(
                7254527, 12638463, 5212644, 333363, 11059059, 9284946)));
        context.register(MEBiomeKeys.WASTE_POND, createDefaultBiome(context, new BiomeColorsDTO(
                8163746, 10926783, 5860963, 863008, 4020033, 2371608)));
        context.register(MEBiomeKeys.WHITE_MOUNTAINS, createDefaultBiome(context, new BiomeColorsDTO(
                hillySky, 12638463, defaultWater, defaultWaterFog, 7183466, 7513204)));
        context.register(MEBiomeKeys.WOODLAND_REALM, createDefaultBiome(context, new BiomeColorsDTO(
                8497918, 10666932, 4492967, 471355, 2780195, 2713634)));
    }

    public static Biome createAnduinBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        DefaultBiomeFeatures.addFarmAnimals(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        ModBiomeFeatures.addBirchTrees(generationSettings);
        ModBiomeFeatures.addLarchTrees(generationSettings);
        ModBiomeFeatures.addMapleTrees(generationSettings);
        addDefaultVegetation(generationSettings);

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createBarrowDownsBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        DefaultBiomeFeatures.addForestGrass(generationSettings);
        DefaultBiomeFeatures.addDefaultDisks(generationSettings);

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createDefaultBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addDefaultVegetation(generationSettings);

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static void addDefaultVegetation(GenerationSettings.LookupBackedBuilder generationSettings) {
        DefaultBiomeFeatures.addDefaultMushrooms(generationSettings);
        DefaultBiomeFeatures.addDefaultVegetation(generationSettings);
        DefaultBiomeFeatures.addForestGrass(generationSettings);
        DefaultBiomeFeatures.addDefaultDisks(generationSettings);
        DefaultBiomeFeatures.addDefaultFlowers(generationSettings);
    }

    public static void addNordicVegetation(GenerationSettings.LookupBackedBuilder generationSettings) {
        DefaultBiomeFeatures.addTaigaGrass(generationSettings);
        DefaultBiomeFeatures.addLargeFerns(generationSettings);
        DefaultBiomeFeatures.addDefaultMushrooms(generationSettings);
        DefaultBiomeFeatures.addSweetBerryBushes(generationSettings);
        DefaultBiomeFeatures.addDefaultDisks(generationSettings);
        DefaultBiomeFeatures.addDefaultFlowers(generationSettings);
    }

    public static Biome createBiome(BiomeColorsDTO biomeColors, SpawnSettings.Builder spawnSettings, GenerationSettings.LookupBackedBuilder generationSettings) {
        DefaultBiomeFeatures.addAmethystGeodes(generationSettings);
        DefaultBiomeFeatures.addLandCarvers(generationSettings);
        DefaultBiomeFeatures.addMineables(generationSettings);
        DefaultBiomeFeatures.addSprings(generationSettings);
        DefaultBiomeFeatures.addFrozenTopLayer(generationSettings);

        return (new Biome.Builder())
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
    }
}
