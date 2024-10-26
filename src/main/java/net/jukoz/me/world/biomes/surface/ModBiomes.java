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

    public static void bootstrap(Registerable<Biome> context) {
        context.register(MEBiomeKeys.ANDUIN_VALES, createAnduinBiome(context, new BiomeColorsDTO(
                defaultSky, defaultFog, defaultWater, defaultWaterFog, 8703593, 6924625), false));
        context.register(MEBiomeKeys.ANDUIN_VALES_FOREST, createAnduinBiome(context, new BiomeColorsDTO(
                defaultSky, defaultFog, defaultWater, defaultWaterFog, 8703593, 6463563), true));
        context.register(MEBiomeKeys.ANORIEN, createAnorienBiome(context, new BiomeColorsDTO(
                defaultSky, defaultFog, defaultWater, defaultWaterFog, 6799458, 6662221)));
        context.register(MEBiomeKeys.ANORIEN_RIVERSIDE, createGondorRiverSideBiome(context, new BiomeColorsDTO(
                defaultSky, defaultFog, defaultWater, defaultWaterFog, 6799458, 6662221)));
        context.register(MEBiomeKeys.ANORIEN_FOOTHILLS, createAnorienBiome(context, new BiomeColorsDTO(
                defaultSky, defaultFog, defaultWater, defaultWaterFog, 6799458, 6662221)));
        context.register(MEBiomeKeys.BARROW_DOWNS, createBarrowDownsBiome(context, new BiomeColorsDTO(
                5993621, 7635851, 4812936, 3034721, 6721389, 6525545)));
        context.register(MEBiomeKeys.BELERIAND_ISLAND, createBeleriandIslandBiome(context, new BiomeColorsDTO(
                defaultSky, 12637429, defaultOceanWater, defaultOceanWaterFog, 6466659, 5740626)));
        context.register(MEBiomeKeys.BELFALAS, createBelfalasBiome(context, new BiomeColorsDTO(
                defaultSky, defaultFog, defaultWater, defaultWaterFog, 5951101, 5485154), false));
        context.register(MEBiomeKeys.BELFALAS_BEACH, createBelfalasShoresBiome(context, new BiomeColorsDTO(
                defaultSky, defaultFog, defaultShoreWater, defaultWaterFog, 7323271, 6665073)));
        context.register(MEBiomeKeys.BELFALAS_HILLS, createBelfalasBiome(context, new BiomeColorsDTO(
                defaultSky, defaultFog, defaultWater, defaultWaterFog, 6011255, 5614178), true));
        context.register(MEBiomeKeys.BLACKROOT_VALE, createBlackRootVale(context, new BiomeColorsDTO(
                8036582, 12176100, 5076939, defaultWaterFog, 6400098, 6069598), false));
        context.register(MEBiomeKeys.BLUE_MOUNTAINS_FOOTHILLS, createBlueMountainsBiome(context, new BiomeColorsDTO(
                7905261, 12241911, defaultWater, defaultWaterFog, 6652524, 6652524), 0));
        context.register(MEBiomeKeys.BLUE_MOUNTAINS_BASE, createBlueMountainsBiome(context, new BiomeColorsDTO(
                7905261, 12241911, defaultWater, defaultWaterFog, 5796446, 6652524), 1));
        context.register(MEBiomeKeys.BLUE_MOUNTAINS, createBlueMountainsBiome(context, new BiomeColorsDTO(
                7905261, 12241911, defaultWater, defaultWaterFog, 5270363, 5796446), 2));
        context.register(MEBiomeKeys.BLUE_MOUNTAINS_PEAKS, createBlueMountainsBiome(context, new BiomeColorsDTO(
                7905261, 12241911, defaultWater, defaultWaterFog, 4677202, 5270363), 3));
        context.register(MEBiomeKeys.BROWN_LANDS, createMordorWastesBiome(context, new BiomeColorsDTO(
                7570864, 8292235, 5075593, 1259598, 8032632, 7901046)));
        context.register(MEBiomeKeys.CARADHRAS_BASE, createMistyMountainsBiome(context, new BiomeColorsDTO(
                hillySky, defaultFog, defaultWater, defaultWaterFog, 7777673, 7316862), 0));
        context.register(MEBiomeKeys.CARADHRAS, createMistyMountainsBiome(context, new BiomeColorsDTO(
                hillySky, defaultFog, defaultWater, defaultWaterFog, 7777673, 7316862), 1));
        context.register(MEBiomeKeys.CARADHRAS_PEAKS, createMistyMountainsBiome(context, new BiomeColorsDTO(
                hillySky, defaultFog, defaultWater, defaultWaterFog, 7777673, 7316862), 2));
        context.register(MEBiomeKeys.CELEBDIL_BASE, createMistyMountainsBiome(context, new BiomeColorsDTO(
                hillySky, defaultFog, defaultWater, defaultWaterFog, 7777673, 7316862), 0));
        context.register(MEBiomeKeys.CELEBDIL, createMistyMountainsBiome(context, new BiomeColorsDTO(
                hillySky, defaultFog, defaultWater, defaultWaterFog, 7777673, 7316862), 1));
        context.register(MEBiomeKeys.CELEBDIL_PEAKS, createMistyMountainsBiome(context, new BiomeColorsDTO(
                hillySky, defaultFog, defaultWater, defaultWaterFog, 7777673, 7316862), 2));
        context.register(MEBiomeKeys.CORSAIR_COASTS, createCorsairCoastBiome(context, new BiomeColorsDTO(
                nearHaradSky, defaultFog, 5212644, 333363, 12107900, 10860366)));
        context.register(MEBiomeKeys.DAGORLAD, createMordorWastesBiome(context, new BiomeColorsDTO(
                7239328, 7632257, 4874882, 992318, 6971996, 8092011)));
        context.register(MEBiomeKeys.DALE, createDaleBiome(context, new BiomeColorsDTO(
                8230911, 12178175, defaultWater, defaultWaterFog, 6400105, 8703593), 0));
        context.register(MEBiomeKeys.DALE_FOREST, createDaleBiome(context, new BiomeColorsDTO(
                8230911, 12178175, defaultWater, defaultWaterFog, 6393176, 4820272), 1));
        context.register(MEBiomeKeys.DALE_HILL, createDaleBiome(context, new BiomeColorsDTO(
                8230911, 12178175, defaultWater, defaultWaterFog, 6400105, 8703593), 2));
        context.register(MEBiomeKeys.DALE_CITY, createDaleBiome(context, new BiomeColorsDTO(
                8230911, 12178175, defaultWater, defaultWaterFog, 6400105, 8703593), 0));
        context.register(MEBiomeKeys.DALE_RIVERSIDE, createDaleBiome(context, new BiomeColorsDTO(
                hillySky, 12637183, defaultWater, defaultWaterFog, 7253882, 7580788), 0));
        context.register(MEBiomeKeys.DARK_ANDUIN_VALES, createAnduinBiome(context, new BiomeColorsDTO(
                7246271, 8951443, 4354703, 1128527, 8032632, 7901046), false));
        context.register(MEBiomeKeys.DARK_MIRKWOOD, createMirkwoodBiome(context, new BiomeColorsDTO(
                5269900, 4806731, 4544130, 338483, 4151612, 3823415), true, true));
        context.register(MEBiomeKeys.DARK_MIRKWOOD_EDGE, createMirkwoodBiome(context, new BiomeColorsDTO(
                5861275, 8293250, 4551554, 467756, 4083260, 3690038), false, true));
        context.register(MEBiomeKeys.DEAD_MARSHES, createDeadMarshesBiome(context, new BiomeColorsDTO(
                11908531, 7108218, 3289373, 198924, 6115374, 5794902)));
        context.register(MEBiomeKeys.DEAD_MARSHES_WATER, createDeadMarshesWaterBiome(context, new BiomeColorsDTO(
                11908531, 7108218, 3289373, 198924, 6115374, 5794902)));
        context.register(MEBiomeKeys.DOL_GULDUR, createMirkwoodBiome(context, new BiomeColorsDTO(
                3684976, 5065045, 2503248, 462892, 3554356, 3424049), false, true));
        context.register(MEBiomeKeys.DOL_GULDUR_HILL, createMirkwoodBiome(context, new BiomeColorsDTO(
                3684976, 5065045, 2503248, 462892, 3554356, 3424049), false, true));
        context.register(MEBiomeKeys.DORWINION, createDorwinionBiome(context, new BiomeColorsDTO(
                defaultSky, defaultFog, defaultWater, defaultWaterFog, 4631895, 4431186)));
        context.register(MEBiomeKeys.DORWINION_HILLS, createDorwinionHillsBiome(context, new BiomeColorsDTO(
                hillySky, defaultFog, defaultWater, defaultWaterFog, 9084547, 8427113)));
        context.register(MEBiomeKeys.DUNLAND_FOOTHILLS, createDunlandFoothillsBiome(context, new BiomeColorsDTO(
                7508201, 10863086, defaultWater, defaultWaterFog, 6722387, 6198343)));
        context.register(MEBiomeKeys.EAST_BIGHT, createRhunBiome(context, new BiomeColorsDTO(
                7571933, defaultFog, 12440052, 591663, 10864741, 8894805)));
        context.register(MEBiomeKeys.EASTERN_NURN, createNurnBiome(context, new BiomeColorsDTO(
                8032225, defaultFog, 6720143, 1583408, 7435862, 7436627)));
        context.register(MEBiomeKeys.EASTERN_RHOVANION, createRhunBiome(context, new BiomeColorsDTO(
                7571933, defaultFog, 12440052, 591663, 8627523, 7052347)));
        context.register(MEBiomeKeys.EMYN_MUIL, createEmynMuilBiome(context, new BiomeColorsDTO(
                6980296, 10402016, 4618461, defaultWaterFog, 9666387, 9731143)));
        context.register(MEBiomeKeys.EMYN_MUIL_CLIFFS, createEmynMuilBiome(context, new BiomeColorsDTO(
                6980296, 10402016, 4618461, defaultWaterFog, 9666387, 9731143)));
        context.register(MEBiomeKeys.EMYN_MUIL_PEAKS, createEmynMuilBiome(context, new BiomeColorsDTO(
                6980296, 10402016, 4618461, defaultWaterFog, 9666387, 9731143)));
        context.register(MEBiomeKeys.EMYN_MUIL_POND, createWastePondBiome(context, new BiomeColorsDTO(
                6980296, 10402016, 3100271, 597791, 4412998, 3359013)));
        context.register(MEBiomeKeys.ENEDWAITH, createEnedwaithBiome(context, new BiomeColorsDTO(
                7181795, 10731509, 4618461, defaultWaterFog, 8761449, 7842644)));
        context.register(MEBiomeKeys.LONELY_MOUNTAIN, createLonelyMountainBiome(context, new BiomeColorsDTO(
                hillySky, 13031679, defaultWater, defaultWaterFog, 7188600, 6529388), 0));
        context.register(MEBiomeKeys.LONELY_MOUNTAIN_BASE, createLonelyMountainBiome(context, new BiomeColorsDTO(
                hillySky, 12637183, defaultWater, defaultWaterFog, 6927733, 6005862), 1));
        context.register(MEBiomeKeys.LONELY_MOUNTAIN_PEAKS, createLonelyMountainBiome(context, new BiomeColorsDTO(
                hillySky, 13031679, defaultWater, defaultWaterFog, 8106628, 6987890), 2));
        context.register(MEBiomeKeys.LONELY_MOUNTAIN_TAIGA, createLonelyMountainBiome(context, new BiomeColorsDTO(
                hillySky, 12637183, defaultWater, defaultWaterFog, 7253882, 7580788), 1));
        context.register(MEBiomeKeys.EREGION, createEregionBiome(context, new BiomeColorsDTO(
                7908853, 11393279, 5077445, 331050, 6858575, 6198343)));
        context.register(MEBiomeKeys.ETHIR_ANDUIN, createSwampAnduin(context, new BiomeColorsDTO(
                defaultSky, defaultFog, defaultWater, defaultWaterFog, 6400102, 6137428)));
        context.register(MEBiomeKeys.FANGORN, createFangornBiome(context, new BiomeColorsDTO(
                7972607, defaultFog, 4293787, 338483, 3559947, 1789719)));
        context.register(MEBiomeKeys.FANGORN_FOOTHILLS, createMistyMountainsBiome(context, new BiomeColorsDTO(
                7972607, defaultFog, 4293787, 338483, 5533992, 3567922), 0));
        context.register(MEBiomeKeys.FANUIDHOL_BASE, createMistyMountainsBiome(context, new BiomeColorsDTO(
                hillySky, defaultFog, defaultWater, defaultWaterFog, 7777673, 7316862), 0));
        context.register(MEBiomeKeys.FANUIDHOL, createMistyMountainsBiome(context, new BiomeColorsDTO(
                hillySky, defaultFog, defaultWater, defaultWaterFog, 7777673, 7316862), 1));
        context.register(MEBiomeKeys.FANUIDHOL_PEAKS, createMistyMountainsBiome(context, new BiomeColorsDTO(
                hillySky, defaultFog, defaultWater, defaultWaterFog, 7777673, 7316862), 2));
        context.register(MEBiomeKeys.FORODWAITH, createForodwaithBiome(context, new BiomeColorsDTO(
                8364543, 10335206, 3823818, 66852, 3494723, 4478280)));
        context.register(MEBiomeKeys.FROZEN_OCEAN, createOceanBiome(context, new BiomeColorsDTO(
                8628223, 10599910, 3750089, 263470, 3494723, 4478280)));
        context.register(MEBiomeKeys.FROZEN_POND, createFrozenPond(context, new BiomeColorsDTO(
                8628223, 10599910, 3750089, 263470, 3494723, 4478280)));
        context.register(MEBiomeKeys.GULF_OF_LHUN_CLIFFS, createLindonBiome(context, new BiomeColorsDTO(
                8827134, 12771327, defaultShoreWater, defaultWaterFog, 6523989, 5667402)));
        context.register(MEBiomeKeys.GULF_OF_LHUN_SHORES, createLindonBiome(context, new BiomeColorsDTO(
                8827134, 12771327, defaultShoreWater, defaultWaterFog, 6523989, 5667402)));
        context.register(MEBiomeKeys.GONDOR, createGondorBiome(context, new BiomeColorsDTO(
                defaultSky, defaultFog, defaultWater, defaultWaterFog, 7582562, 6592327)));
        context.register(MEBiomeKeys.GORGOROTH, createGorgorothBiome(context, new BiomeColorsDTO(
                2564128, 1709079, 4869697, 1513734, 3156775, 2169880)));
        context.register(MEBiomeKeys.GREY_MOUNTAINS_BASE, createGreyMountainsBiome(context, new BiomeColorsDTO(
                8098794, 12701936, defaultWater, defaultWaterFog, 8823414, 9022583), 0));
        context.register(MEBiomeKeys.GREY_MOUNTAINS, createGreyMountainsBiome(context, new BiomeColorsDTO(
                8098794, 12701936, defaultWater, defaultWaterFog, 8823414, 9022583), 0));
        context.register(MEBiomeKeys.GREY_MOUNTAINS_PEAKS, createGreyMountainsBiome(context, new BiomeColorsDTO(
                8098794, 12701936, defaultWater, defaultWaterFog, 8823414, 9022583), 1));
        context.register(MEBiomeKeys.GREY_PLAINS, createGreyPlainsBiome(context, new BiomeColorsDTO(
                hillySky, 12637179, defaultWater, defaultWaterFog, 5939024, 8566393)));
        context.register(MEBiomeKeys.GUNDABAD_PLAINS, createGreyPlainsTaiga(context, new BiomeColorsDTO(
                hillySky, 12637183, defaultWater, defaultWaterFog, 8036220, 7511410)));
        context.register(MEBiomeKeys.HARAD, createHaradBiome(context, new BiomeColorsDTO(
                nearHaradSky, nearHaradSkyFog, 5407446, 1120828, 12301405, 13356379)));
        context.register(MEBiomeKeys.HARAD_DESERT, createHaradDesertBiome(context, new BiomeColorsDTO(
                nearHaradSky, nearHaradSkyFog, 5407446, 1120828, 13419633, 9615182)));
        context.register(MEBiomeKeys.HARONDOR, createHarondorBiome(context, new BiomeColorsDTO(
                nearHaradSky, nearHaradSkyFog, 5406149, 1120828, 12305028, 10860366)));
        context.register(MEBiomeKeys.HILLS_OF_EVENDIM, createHillsOfElvendim(context, new BiomeColorsDTO(
                hillySky, defaultFog, defaultWater, defaultWaterFog, 9087338, 9218155)));
        context.register(MEBiomeKeys.IRON_HILLS, createIronHillsBiome(context, new BiomeColorsDTO(
                10140415, 13031679, defaultWater, defaultWaterFog, 6922099, 7119988), false));
        context.register(MEBiomeKeys.IRON_HILLS_BASE, createIronHillsBiome(context, new BiomeColorsDTO(
                8761343, defaultFog, defaultWater, defaultWaterFog, 7187321, 6793328), true));
        context.register(MEBiomeKeys.IRON_HILLS_PEAKS, createIronHillsBiome(context, new BiomeColorsDTO(
                8761343, defaultFog, defaultWater, defaultWaterFog, 7187321, 6793328), true));
        context.register(MEBiomeKeys.IRON_HILLS_PLAINS, createNorthernRhovanionTaiga(context, new BiomeColorsDTO(
                hillySky, 12637183, defaultWater, defaultWaterFog, 7713657, 7580788)));
        context.register(MEBiomeKeys.IRON_FOOTHILLS, createNorthernRhovanionTaiga(context, new BiomeColorsDTO(
                hillySky, 12637183, defaultWater, defaultWaterFog, 7253882, 7580788)));
        context.register(MEBiomeKeys.ISENGARD, createIsengardBiome(context, new BiomeColorsDTO(
                defaultSky, defaultFog, 5076423, defaultWaterFog, 5932619, 5867338), true));
        context.register(MEBiomeKeys.ISENGARD_HILL, createIsengardBiome(context, new BiomeColorsDTO(
                defaultSky, defaultFog, 5076423, defaultWaterFog, 5932619, 5867338), false));
        context.register(MEBiomeKeys.ITHILIEN, createIthilienBiome(context, new BiomeColorsDTO(
                defaultSky, defaultFog, defaultWater, defaultWaterFog, 5152072, 4889668), false));
        context.register(MEBiomeKeys.ITHILIEN_WASTES, createIthilienBiome(context, new BiomeColorsDTO(
                9085388, 11254223, 4944318, 723757, 5733716, 5537108), true));
        context.register(MEBiomeKeys.LAMEDON, createLamedonBiome(context, new BiomeColorsDTO(
                defaultSky, defaultFog, defaultWater, defaultWaterFog, 9162899, 8043898)));
        context.register(MEBiomeKeys.LEBENNIN, createLebennin(context, new BiomeColorsDTO(
                defaultSky, defaultFog, defaultWater, defaultWaterFog, 5883985, 7449969)));
        context.register(MEBiomeKeys.LEBENNIN_SHORES, createGondorRiverSideBiome(context, new BiomeColorsDTO(
                defaultSky, defaultFog, defaultShoreWater, defaultWaterFog, 5883985, 7449969)));
        context.register(MEBiomeKeys.LINDON, createLindonBiome(context, new BiomeColorsDTO(
                8827134, 12771327, defaultWater, defaultWaterFog, 6523989, 5667402)));
        context.register(MEBiomeKeys.LONG_LAKE, createLakeBiome(context, new BiomeColorsDTO(
                waterSky, defaultFog, 4352228, 525619, 7576434, 6588506)));
        context.register(MEBiomeKeys.LONG_MARSHES, createMirkwoodSwampBiome(context, new BiomeColorsDTO(
                8295660, 10993126, 6853265, 1919305, 5676922, 5282655)));
        context.register(MEBiomeKeys.LORIEN_EDGE, createLorienEdgeBiome(context, new BiomeColorsDTO(
                defaultSky, 11455105, defaultWater, defaultWaterFog, 10601262, 6202980)));
        context.register(MEBiomeKeys.LOSSARNACH, createLossarnach(context, new BiomeColorsDTO(
                defaultSky, defaultFog, defaultWater, defaultWaterFog, 8240485, 7909996), 0));
        context.register(MEBiomeKeys.LOSSARNACH_VALLEY, createLossarnach(context, new BiomeColorsDTO(
                defaultSky, defaultFog, defaultWater, defaultWaterFog, 8896601, 9355638), 1));
        context.register(MEBiomeKeys.LOTHLORIEN, createLothlorienBiome(context, new BiomeColorsDTO(
                defaultSky, 13748853, defaultWater, defaultWaterFog, 12961832, 6989412)));
        context.register(MEBiomeKeys.MINHIRIATH, createMinhiriathBiome(context, new BiomeColorsDTO(
                defaultSky, 12637429, 4615389, 658236, 8626526, 8231005)));
        context.register(MEBiomeKeys.MIRKWOOD, createMirkwoodBiome(context, new BiomeColorsDTO(
                6849446, 6780008, 7111535, 338483, 4151612, 3823415), true, false));
        context.register(MEBiomeKeys.MIRKWOOD_EDGE, createMirkwoodBiome(context, new BiomeColorsDTO(
                7578800, 7897724, 7111535, 338483, 4546876, 4284215), false, false));
        context.register(MEBiomeKeys.MIRKWOOD_FOOTHILLS, createMirkwoodBiome(context, new BiomeColorsDTO(
                6849446, 6780008, 7111535, 338483, 4151612, 3823415), true, false));
        context.register(MEBiomeKeys.MIRKWOOD_MOUNTAINS_BASE, createMirkwoodBiome(context, new BiomeColorsDTO(
                6849446, 6780008, 7111535, 338483, 4151612, 3823415), true, false));
        context.register(MEBiomeKeys.MIRKWOOD_MOUNTAINS, createMirkwoodBiome(context, new BiomeColorsDTO(
                6849446, 6780008, 7111535, 338483, 4151612, 3823415), false, false));
        context.register(MEBiomeKeys.MIRKWOOD_MOUNTAINS_PEAKS, createMirkwoodMountainsBiome(context, new BiomeColorsDTO(
                7972607, 9873053, 7111535, 338483, 4678724, 4482114), false));
        context.register(MEBiomeKeys.MIRKWOOD_SWAMP, createMirkwoodSwampBiome(context, new BiomeColorsDTO(
                6981536, 8821922, 7111535, 1458241, 4546876, 4284215)));
        context.register(MEBiomeKeys.MIRKWOOD_MARSHES, createMirkwoodSwampBiome(context, new BiomeColorsDTO(
                6981536, 8821922, 7111535, 1458241, 4546876, 4284215)));
        context.register(MEBiomeKeys.FOREST_RIVER, createMirkwoodSwampBiome(context, new BiomeColorsDTO(
                6981536, 8821922, 7111535, 1458241, 4546876, 4284215)));
        context.register(MEBiomeKeys.GREAT_RIVER, createRiverBiome(context, new BiomeColorsDTO(
                waterSky, 12638463, defaultWater, defaultWaterFog, 10995507, 7181907)));
        context.register(MEBiomeKeys.MISTY_MOUNTAINS_BASE, createMistyMountainsBiome(context, new BiomeColorsDTO(
                hillySky, defaultFog, defaultWater, defaultWaterFog, 7777673, 7316862), 0));
        context.register(MEBiomeKeys.MISTY_MOUNTAINS, createMistyMountainsBiome(context, new BiomeColorsDTO(
                hillySky, defaultFog, defaultWater, defaultWaterFog, 7777673, 7316862), 1));
        context.register(MEBiomeKeys.MISTY_MOUNTAINS_PEAKS, createMistyMountainsBiome(context, new BiomeColorsDTO(
                hillySky, defaultFog, defaultWater, defaultWaterFog, 7777673, 7316862), 2));
        context.register(MEBiomeKeys.MORDOR, createMordorBiome(context, new BiomeColorsDTO(
                2695203, 1905947, 6450777, 1513734, 3550502, 2695966)));
        context.register(MEBiomeKeys.MORDOR_MOUNTAINS, createMordorMountainsBiome(context, new BiomeColorsDTO(
                4142646, 3090215, 6450777, 1513734, 3550502, 2169880)));
        context.register(MEBiomeKeys.MORDOR_MOUNTAINS_FOOTHILLS, createMordorMountainsBiome(context, new BiomeColorsDTO(
                3747117, 2629407, 6450777, 1513734, 5129527, 3486247)));
        context.register(MEBiomeKeys.MORDOR_MOUNTAINS_PEAKS, createMordorMountainsBiome(context, new BiomeColorsDTO(
                4142646, 3090215, 6450777, 1513734, 3550502, 2169880)));
        context.register(MEBiomeKeys.MORDOR_WASTES, createMordorWastesBiome(context, new BiomeColorsDTO(
                5460048, 4999240, 5860962, 731161, 6252369, 4735297)));
        context.register(MEBiomeKeys.MORGUL_VALE, createMorgulVale(context, new BiomeColorsDTO(
                4479570, 3690813, 3897457, 595232, 4545602, 4348994)));
        context.register(MEBiomeKeys.MORGUL_RIVER, createNurnWaterBiome(context, new BiomeColorsDTO(
                4479570, 3690813, 3897457, 595232, 4545602, 4348994)));
        context.register(MEBiomeKeys.MOUNT_GUNDABAD, createGreyMountainsBiome(context, new BiomeColorsDTO(
                8098794, 12701936, defaultWater, defaultWaterFog, 8823414, 9022583), 1));
        context.register(MEBiomeKeys.MOUNT_DOOM, createMordorMountainsBiome(context, new BiomeColorsDTO(
                4142646, 3090215, 6450777, 1513734, 3550502, 2169880)));
        context.register(MEBiomeKeys.NAN_CURUNIR, createNanCurunirBiome(context, new BiomeColorsDTO(
                defaultSky, defaultFog, 5076423, defaultWaterFog, 5406786, 4554563)));
        context.register(MEBiomeKeys.NEN_HITHOEL, createLakeBiome(context, new BiomeColorsDTO(
                waterSky, 12638463, 6853316, 6853316, 10995507, 7181907)));
        context.register(MEBiomeKeys.NEN_HITHOEL_FOREST, createTrollshawsBiome(context, new BiomeColorsDTO(
                6980296, 10402016, 4618461, defaultWaterFog, 9666387, 9731143)));
        context.register(MEBiomeKeys.NEN_HITHOEL_SHORES, createTrollshawsBiome(context, new BiomeColorsDTO(
                6980296, 10402016, 4618461, defaultWaterFog, 9666387, 9731143)));
        context.register(MEBiomeKeys.NINDALF, createSwampAnduin(context, new BiomeColorsDTO(
                defaultSky, defaultFog, defaultWater, defaultWaterFog, 6263143, 5869915)));
        context.register(MEBiomeKeys.NORTH_DOWNS, createNorthDownsBiome(context, new BiomeColorsDTO(
                hillySky, defaultFog, defaultWater, defaultWaterFog, 9414008, 9018483)));
        context.register(MEBiomeKeys.NORTHERN_DUNLAND, createNorthDunlandBiome(context, new BiomeColorsDTO(
                7508201, 12964294, defaultWater, defaultWaterFog, 6722387, 6198343), true));
        context.register(MEBiomeKeys.NORTHERN_MIRKWOOD_SWAMP, createMirkwoodSwampBiome(context, new BiomeColorsDTO(
                6849692, 8427421, 7111535, 1458241, 4546876, 4284215)));
        context.register(MEBiomeKeys.NORTHERN_MIRKWOOD_MARSHES, createMirkwoodSwampBiome(context, new BiomeColorsDTO(
                6849692, 8427421, 7111535, 1458241, 4546876, 4284215)));
        context.register(MEBiomeKeys.NORTHERN_WASTELANDS, createNorthernWastelands(context, new BiomeColorsDTO(
                hillySky, defaultFog, defaultWater, defaultWaterFog, 5932658, 7973008)));
        context.register(MEBiomeKeys.NURN, createNurnBiome(context, new BiomeColorsDTO(
                8954077, 11781331, 5860970, 1321760, 7443043, 6982236)));
        context.register(MEBiomeKeys.NURN_EDGE, createNurnEdgeBiome(context, new BiomeColorsDTO(
                8821450, 11517913, 5202783, 1321247, 6511435, 7960147)));
        context.register(MEBiomeKeys.NURN_RIVER, createNurnWaterBiome(context, new BiomeColorsDTO(
                8952797, 11779800, 5860963, 863008, 5465422, 5663573)));
        context.register(MEBiomeKeys.NURN_SEA, createNurnWaterBiome(context, new BiomeColorsDTO(
                8952797, 11779800, 5860963, 863008, 5465422, 5663573)));
        context.register(MEBiomeKeys.OASIS, createOasisBiome(context, new BiomeColorsDTO(
                nearHaradSky, nearHaradSkyFog, 5407446, 1120828, 7253092, 6592350)));
        context.register(MEBiomeKeys.OCEAN, createOceanBiome(context, new BiomeColorsDTO(
                waterSky, defaultFog, defaultOceanWater, defaultOceanWaterFog, 7576434, 6588506)));
        context.register(MEBiomeKeys.OCEAN_COAST, createOceanCoastBiome(context, new BiomeColorsDTO(
                8104447, defaultFog, defaultCoastWater, defaultOceanWaterFog, 7971954, 6590810)));
        context.register(MEBiomeKeys.OLD_ANGMAR, createOldAngmarBiome(context, new BiomeColorsDTO(
                8954077, 11781343, 4814544, 460593, 7443043, 6982236), 1));
        context.register(MEBiomeKeys.OLD_ANGMAR_FOREST, createOldAngmarBiome(context, new BiomeColorsDTO(
                8954077, 11781343, 4814544, 460593, 7443043, 6982236), 0));
        context.register(MEBiomeKeys.OLD_ANGMAR_COLD_HILL, createOldAngmarBiome(context, new BiomeColorsDTO(
                8954077, 11781343, 4814544, 460593, 7443043, 6982236), 2));
        context.register(MEBiomeKeys.OLD_ANGMAR_FROZEN_HILL, createOldAngmarBiome(context, new BiomeColorsDTO(
                8954077, 11781343, 4814544, 460593, 7443043, 6982236), 3));
        context.register(MEBiomeKeys.OLD_ARTHEDAIN, createOldArthedainBiome(context, new BiomeColorsDTO(
                7907327, defaultFog, defaultWater, defaultWaterFog, 12508275, 11652468), 1));
        context.register(MEBiomeKeys.OLD_ARTHEDAIN_FOREST, createOldArthedainBiome(context, new BiomeColorsDTO(
                7907327, defaultFog, defaultWater, defaultWaterFog, 10862435, 11126380), 2));
        context.register(MEBiomeKeys.OLD_ARTHEDAIN_MEADOW, createOldArthedainBiome(context, new BiomeColorsDTO(
                7907327, defaultFog, defaultWater, defaultWaterFog, 12508275, 11652468), 0));
        context.register(MEBiomeKeys.OLD_ARTHEDAIN_FOOTHILL, createOldArthedainBiome(context, new BiomeColorsDTO(
                7907327, defaultFog, defaultWater, defaultWaterFog, 11190641, 11126380), 3));
        context.register(MEBiomeKeys.OLD_CARDOLAN, createOldCardolanBiome(context, new BiomeColorsDTO(
                defaultSky, 12637429, 4615389, 658236, 9154400, 9154400), 0));
        context.register(MEBiomeKeys.OLD_CARDOLAN_FOREST, createOldCardolanBiome(context, new BiomeColorsDTO(
                defaultSky, 12637429, 4615389, 658236, 8298580, 8627801), 1));
        context.register(MEBiomeKeys.OLD_CARDOLAN_HILL, createOldCardolanBiome(context, new BiomeColorsDTO(
                defaultSky, 12637429, 4615389, 658236, 9482090, 9154400), 2));
        context.register(MEBiomeKeys.OLD_RHUDAUR, createOldRhudaurBiome(context, new BiomeColorsDTO(
                7508201, 10863086, 4618461, defaultWaterFog, 6722387, 6198343), 0));
        context.register(MEBiomeKeys.OLD_RHUDAUR_FOREST, createOldRhudaurBiome(context, new BiomeColorsDTO(
                7507684, 10862569, 4618461, defaultWaterFog, 5932359, 6131783), 1));
        context.register(MEBiomeKeys.OLD_RHUDAUR_HILL, createOldRhudaurBiome(context, new BiomeColorsDTO(
                7507684, 10862569, 4618461, defaultWaterFog, 7049306, 6525264), 2));
        context.register(MEBiomeKeys.OSGILIATH, createGondorBiome(context, new BiomeColorsDTO(
                defaultSky, defaultFog, defaultWater, defaultWaterFog, 8104558, 7445848)));
        context.register(MEBiomeKeys.PELENNOR_FIELDS, createPelennorFields(context, new BiomeColorsDTO(
                defaultSky, defaultFog, defaultWater, defaultWaterFog, 8628846, 8169054)));
        context.register(MEBiomeKeys.POND, createPondBiome(context, new BiomeColorsDTO(
                waterSky, defaultFog, 4290786, defaultWaterFog, 7583083, 6592339)));
        context.register(MEBiomeKeys.RHUN, createRhunBiome(context, new BiomeColorsDTO(
                8041727, 12773631, 4618980, defaultWaterFog, 10995507, 7181907)));
        context.register(MEBiomeKeys.HIGH_MOOR, createRivendellBiome(context, new BiomeColorsDTO(
                9090047, 13426943, defaultWater, defaultWaterFog, 8630141, 8169079)));
        context.register(MEBiomeKeys.HIGH_MOOR_VALE, createRivendellBiome(context, new BiomeColorsDTO(
                8695029, 12637426, defaultWater, defaultWaterFog, 8630141, 8169079)));
        context.register(MEBiomeKeys.HIGH_MOOR_HILLS, createRivendellFoothillsBiome(context, new BiomeColorsDTO(
                8695029, 12637426, defaultWater, defaultWaterFog, 8630141, 8169079)));
        context.register(MEBiomeKeys.RIVER, createRiverBiome(context, new BiomeColorsDTO(
                waterSky, defaultFog, 4290790, defaultWaterFog, 7583083, 6592339)));
        context.register(MEBiomeKeys.RIVER_RUNNING, createRiverBiome(context, new BiomeColorsDTO(
                waterSky, defaultFog, 4290790, defaultWaterFog, 7583083, 6592339)));
        context.register(MEBiomeKeys.ROHAN, createRohanBiome(context, new BiomeColorsDTO(
                defaultSky, defaultFog, defaultWater, defaultWaterFog, 10342513, 6918471)));
        context.register(MEBiomeKeys.SEA_OF_RHUN, createLakeBiome(context, new BiomeColorsDTO(
                waterSky, 12638463, 4159204, defaultWaterFog, 10995507, 7181907)));
        context.register(MEBiomeKeys.SARN_GEBIR_SHORES, createIronHillsBiome(context, new BiomeColorsDTO(
                10140415, 13031679, defaultWater, defaultWaterFog, 6922099, 7119988), false));
        context.register(MEBiomeKeys.SARN_GEBIR_WILDLANDS, createIronHillsBiome(context, new BiomeColorsDTO(
                10140415, 13031679, defaultWater, defaultWaterFog, 6922099, 7119988), false));
        context.register(MEBiomeKeys.SHIRE, createShireBiome(context, new BiomeColorsDTO(
                8695029, 12637426, defaultWater, defaultWaterFog, 8704848, 6858783), 0));
        context.register(MEBiomeKeys.SHIRE_EDGE, createShireBiome(context, new BiomeColorsDTO(
                8695029, 12637426, defaultWater, defaultWaterFog, 7978571, 6132766), 1));
        context.register(MEBiomeKeys.SHIRE_HILLS, createShireBiome(context, new BiomeColorsDTO(
                8695029, 12637426, defaultWater, defaultWaterFog, 8175184, 6658600), 2));
        context.register(MEBiomeKeys.SHIRE_WOODS, createShireBiome(context, new BiomeColorsDTO(
                8695029, 12637426, defaultWater, defaultWaterFog, 6991676, 6460967), 3));
        context.register(MEBiomeKeys.SHIRE_FOREST, createShireBiome(context, new BiomeColorsDTO(
                8695029, 12637426, defaultWater, defaultWaterFog, 6662455, 5803292), 4));
        context.register(MEBiomeKeys.SOUTHERN_DUNLAND, createNorthDunlandBiome(context, new BiomeColorsDTO(
                7508201, 10863086, defaultWater, defaultWaterFog, 8302697, 7252827), false));
        context.register(MEBiomeKeys.SOUTHERN_EPHEL_DUATH, createMordorMountainsBiome(context, new BiomeColorsDTO(
                4142646, 3090215, 6450777, 1513734, 3550502, 2169880)));
        context.register(MEBiomeKeys.SOUTHERN_EPHEL_DUATH_BASE, createMordorMountainsBiome(context, new BiomeColorsDTO(
                3747117, 2629407, 6450777, 1513734, 5129527, 3486247)));
        context.register(MEBiomeKeys.SOUTHERN_EPHEL_DUATH_PEAKS, createMordorMountainsBiome(context, new BiomeColorsDTO(
                4142646, 3090215, 6450777, 1513734, 3550502, 2169880)));
        context.register(MEBiomeKeys.SOUTHEAST_RHOVANION, createRhunBiome(context, new BiomeColorsDTO(
                7905517, 11055575, defaultWater, defaultWaterFog, 8950352, 7836758)));
        context.register(MEBiomeKeys.SOUTHERN_FOROCHEL, createSouthernForochelBiome(context, new BiomeColorsDTO(
                hillySky, defaultFog, defaultWater, defaultWaterFog, 7177842, 7971216)));
        context.register(MEBiomeKeys.THE_ANGLE, createTheAngleBiome(context, new BiomeColorsDTO(
                defaultSky, defaultFog, 4615389, 658236, 9878641, 8431193)));
        context.register(MEBiomeKeys.THE_OLD_FOREST, createTheOldForestBiome(context, new BiomeColorsDTO(
                6785744, 10004675, 4421513, 402733, 2311707, 2050588)));
        context.register(MEBiomeKeys.THE_WHITE_DOWNS, createTheWhiteDownsBiome(context, new BiomeColorsDTO(
                9022444, 12898532, defaultWater, defaultWaterFog, 8431738, 7905395)));
        context.register(MEBiomeKeys.THE_WOLD, createRohanBiome(context, new BiomeColorsDTO(
                defaultSky, defaultFog, defaultWater, defaultWaterFog, 12309403, 12242068)));
        context.register(MEBiomeKeys.TOLFALAS, createTolfalasBiome(context, new BiomeColorsDTO(
                defaultSky, defaultFog, defaultWater, defaultWaterFog, 10927716, 9615182)));
        context.register(MEBiomeKeys.TOROGWAITH, createTorogwaithBiome(context, new BiomeColorsDTO(
                3289130, 2827810, 6255709, 1252359, 4142897, 3156775)));
        context.register(MEBiomeKeys.TROLLSHAWS, createTrollshawsBiome(context, new BiomeColorsDTO(
                6980296, 10402016, 4618461, defaultWaterFog, 9666387, 9731143)));
        context.register(MEBiomeKeys.UDUN, createMordorBiome(context, new BiomeColorsDTO(
                4273461, 2826529, 6450777, 1513734, 3549478, 2695966)));
        context.register(MEBiomeKeys.UMBAR, createUmbarBiome(context, new BiomeColorsDTO(
                7254527, 12638463, 5212644, 333363, 11059059, 9284946)));
        context.register(MEBiomeKeys.WASTE_POND, createWastePondBiome(context, new BiomeColorsDTO(
                8163746, 10926783, 5860963, 863008, 4020033, 2695710)));
        context.register(MEBiomeKeys.WEBBED_WOODS, createMirkwoodBiome(context, new BiomeColorsDTO(
                6385822, 5198943, 4544130, 338483, 4478786, 4017979), true, true));
        context.register(MEBiomeKeys.WITHERED_HEATH, createWitheredHeathBiome(context, new BiomeColorsDTO(
                9479110, 11780310, 5141697, 460593, 8881498, 10328434)));
        context.register(MEBiomeKeys.WHITE_MOUNTAINS_BASE, createWhiteMountainsBiome(context, new BiomeColorsDTO(
                hillySky, 12638463, defaultWater, defaultWaterFog, 7185769, 6857066)));
        context.register(MEBiomeKeys.WHITE_MOUNTAINS, createWhiteMountainsFaces(context, new BiomeColorsDTO(
                hillySky, 12638463, defaultWater, defaultWaterFog, 7183466, 7513204), false));
        context.register(MEBiomeKeys.WHITE_MOUNTAINS_PEAKS, createWhiteMountainsFaces(context, new BiomeColorsDTO(
                hillySky, 12638463, defaultWater, defaultWaterFog, 7185769, 6857066), true));
        context.register(MEBiomeKeys.WOODLAND_REALM, createWoodlandRealmBiome(context, new BiomeColorsDTO(
                8497918, 10666932, 4492967, 471355, 3960119, 3370029)));
        context.register(MEBiomeKeys.WOODLAND_FOOTHILLS, createMirkwoodMountainsBiome(context, new BiomeColorsDTO(
                6849446, 6780008, 7111535, 338483, 4420926, 3567152), true));
        context.register(MEBiomeKeys.WOODLAND_HILLS, createMirkwoodMountainsBiome(context, new BiomeColorsDTO(
                6849446, 6780008, 7111535, 338483, 4748611, 4093495), true));
    }

    public static Biome createAnduinBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors, boolean forest) {
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

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createAnorienBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
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

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createBarrowDownsBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        ModBiomeFeatures.addDisks(undergroundOres);
        ModBiomeFeatures.addGravelOre(vegetation);

        ModBiomeFeatures.addGrass(vegetation);

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createBeleriandIslandBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
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

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createBelfalasBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors, boolean hills) {
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

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createBelfalasShoresBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addGondorVegetation(generationSettings);
        ModBiomeFeatures.addDioriteBoulder(vegetation);
        ModBiomeFeatures.addHorokaka(vegetation);
        ModBiomeFeatures.addRareOakBushes(vegetation);

        vegetation.add(VegetationPlacedFeatures.TREES_PLAINS);

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createBlackRootVale(Registerable<Biome> context, BiomeColorsDTO biomeColors, boolean forest) {
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

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createBlueMountainsBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors, int step) {
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

        return createBiome(biomeColors, spawnSettings, generationSettings, temperature, true);
    }

    public static Biome createCorsairCoastBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
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

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createDaleBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors, int step) {
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

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createDeadMarshesBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
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

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createDeadMarshesWaterBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        undergroundOres.add(MiscPlacedFeatures.DISK_CLAY);
        ModBiomeFeatures.addAshenGravelOre(undergroundOres);
        ModBiomeFeatures.addAshenSandOre(undergroundOres);
        ModBiomeFeatures.addSoulSandOre(vegetation);
        vegetation.add(OceanPlacedFeatures.KELP_WARM);
        vegetation.add(OceanPlacedFeatures.SEAGRASS_NORMAL);
        ModBiomeFeatures.addGrass(vegetation);

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createDorwinionBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
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

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createDorwinionHillsBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
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

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createDunlandFoothillsBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
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

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createEmynMuilBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
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

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createEnedwaithBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
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

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createLonelyMountainBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors, int step) {
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

        return createBiome(biomeColors, spawnSettings, generationSettings, temperature, true);
    }

    public static Biome createEregionBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
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

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createSwampAnduin(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
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

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createFangornBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
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

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createForodwaithBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addForochelMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addForodwaithVegetation(generationSettings);

        return createBiome(biomeColors, spawnSettings, generationSettings, -0.8f, true);
    }

    public static Biome createGondorBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addPlainsMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addGondorVegetation(generationSettings);
        ModBiomeFeatures.addGravelOre(vegetation);
        ModBiomeFeatures.addDioriteBoulder(vegetation);

        vegetation.add(VegetationPlacedFeatures.TREES_PLAINS);
        ModBiomeFeatures.addRareBeechTrees(vegetation);
        ModBiomeFeatures.addRareLebethronTrees(vegetation);

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createGondorRiverSideBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addGondorVegetation(generationSettings);
        ModBiomeFeatures.addGravelOre(vegetation);
        ModBiomeFeatures.addDioriteBoulder(vegetation);
        ModBiomeFeatures.addDryGrass(vegetation);

        vegetation.add(VegetationPlacedFeatures.TREES_PLAINS);

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createGreyMountainsBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors, int step) {
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

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createGreyPlainsBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
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

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createGreyPlainsTaiga(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
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

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createHaradBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
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

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createHarondorBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
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

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createHaradDesertBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addHaradMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addHaradDesertVegetation(generationSettings);
        vegetation.add(VegetationPlacedFeatures.PATCH_DEAD_BUSH_2);

        return createBiome(biomeColors, spawnSettings, generationSettings, 0.8f, false);
    }

    public static Biome createHillsOfElvendim(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
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

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createIronHillsBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors, boolean foothills) {
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

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createIsengardBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors, boolean trees) {
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

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createNorthernRhovanionTaiga(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
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

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createIthilienBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors, boolean wastes) {
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

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createLamedonBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
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

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createLebennin(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
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

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createLindonBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
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

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createLorienEdgeBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addFarmAnimals(spawnSettings);
        ModSpawnSettingsBuilder.addSwan(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addLothlorienVegetation(generationSettings);
        ModBiomeFeatures.addMallornTrees(vegetation);

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createLothlorienBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addFarmAnimals(spawnSettings);
        ModSpawnSettingsBuilder.addSwan(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addLothlorienVegetation(generationSettings);
        ModBiomeFeatures.addSmallMallornTress(vegetation);
        ModBiomeFeatures.addOakTrees(vegetation);
        ModBiomeFeatures.addSparseBirchTrees(vegetation);
        ModBiomeFeatures.addMegaMallornTrees(vegetation);

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createLossarnach(Registerable<Biome> context, BiomeColorsDTO biomeColors, int step) {
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





        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createMinhiriathBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
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

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createMirkwoodBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors, boolean bigTrees, boolean dark) {
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

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createMirkwoodMountainsBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors, boolean foothill) {
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

        return createBiome(biomeColors, spawnSettings, generationSettings, temperature, true);
    }

    public static Biome createMirkwoodSwampBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
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

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createMistyMountainsBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors, int step) {
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

        return createBiome(biomeColors, spawnSettings, generationSettings,temperature, true);
    }

    public static Biome createMordorBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
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

        return createBiome(biomeColors, spawnSettings, generationSettings, 0.7f, false);
    }

    public static Biome createGorgorothBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
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

        return createBiome(biomeColors, spawnSettings, generationSettings, 0.7f, false);
    }

    public static Biome createMordorMountainsBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
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

        return createBiome(biomeColors, spawnSettings, generationSettings, 0.7f, false);
    }

    public static Biome createMordorWastesBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addMordorVegetation(generationSettings);
        ModBiomeFeatures.addDeadRushes(vegetation);
        ModBiomeFeatures.addMordorLichen(vegetation);
        ModBiomeFeatures.addGravelOre(vegetation);
        ModBiomeFeatures.addMireOre(vegetation);
        ModBiomeFeatures.addMudOre(vegetation);
        ModBiomeFeatures.addAshenDirtOre(vegetation);

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createMorgulVale(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
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


        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createNanCurunirBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
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

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createNorthDownsBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
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

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createNorthDunlandBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors, boolean trees) {
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
        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createNorthernWastelands(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
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

        return createBiome(biomeColors, spawnSettings, generationSettings, -0.1f, true);
    }

    public static Biome createNurnEdgeBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
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

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createNurnBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
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

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createNurnWaterBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addSwampMobs(spawnSettings);
        ModSpawnSettingsBuilder.addRiverAnimals(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addOceanVegetation(generationSettings);
        ModBiomeFeatures.addMudOre(vegetation);

        ModBiomeFeatures.addWillowTrees(vegetation);

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createOldAngmarBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors, int step) {
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

        return createBiome(biomeColors, spawnSettings, generationSettings, temperature, true);
    }

    public static Biome createOldArthedainBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors, int step) {
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

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createOldCardolanBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors, int step) {
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

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createOldRhudaurBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors, int step) {
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

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createRhunBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
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

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createRivendellBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addPlainsMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addRivendellVegetation(generationSettings);

        ModBiomeFeatures.addRareBeechTrees(vegetation);
        ModBiomeFeatures.addSparseBirchTrees(vegetation);
        ModBiomeFeatures.addVeryRareMegaOakTrees(vegetation);
        ModBiomeFeatures.addMegaBirchTrees(vegetation);

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createRivendellFoothillsBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
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

        return createBiome(biomeColors, spawnSettings, generationSettings, 0.4f, true);
    }

    public static Biome createRohanBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
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

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createShireBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors, int step) {
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
        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createSouthernForochelBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addNordicMobs(spawnSettings);
        ModSpawnSettingsBuilder.addWolves(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addNordicVegetation(generationSettings);
        ModBiomeFeatures.addGravelOre(vegetation);
        ModBiomeFeatures.addBlueTuffBoulder(vegetation);
        ModBiomeFeatures.addSparsePineTrees(vegetation);
        ModBiomeFeatures.addRareSpruceTrees(vegetation);

        return createBiome(biomeColors, spawnSettings, generationSettings, -0.4f, true);
    }

    public static Biome createTheAngleBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
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

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createTheOldForestBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
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

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createTheWhiteDownsBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
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

        return createBiome(biomeColors, spawnSettings, generationSettings, 0.4f, true);
    }

    public static Biome createTolfalasBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
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

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createTorogwaithBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
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

        return createBiome(biomeColors, spawnSettings, generationSettings, 0.7f, false);
    }

    public static Biome createTrollshawsBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
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

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createUmbarBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addHaradMobs(spawnSettings);
        ModSpawnSettingsBuilder.addRareWolves(spawnSettings);
        ModSpawnSettingsBuilder.addFarmAnimals(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addHaradVegetation(generationSettings);
        ModBiomeFeatures.addGraniteBoulder(vegetation);
        ModBiomeFeatures.addWildFlax(vegetation);

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createRiverBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
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

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createLakeBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addRiverAnimals(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addOceanVegetation(generationSettings);

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createOasisBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addRiverAnimals(spawnSettings);
        ModSpawnSettingsBuilder.addCamel(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addOasisVegetation(generationSettings);

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createWastePondBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addRiverAnimals(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        ModBiomeFeatures.addDisks(undergroundOres);
        vegetation.add(OceanPlacedFeatures.KELP_WARM);
        vegetation.add(OceanPlacedFeatures.SEAGRASS_NORMAL);
        ModBiomeFeatures.addGrass(vegetation);
        vegetation.add(VegetationPlacedFeatures.PATCH_SUGAR_CANE);
        ModBiomeFeatures.addReedsFoliage(vegetation);

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createWitheredHeathBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
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

        return createBiome(biomeColors, spawnSettings, generationSettings, 0.4f, true);
    }

    public static Biome createWhiteMountainsBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addMountainsMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        float temperature = 0.5f;
        addNordicVegetation(generationSettings);
        ModBiomeFeatures.addLarchTrees(vegetation);
        ModBiomeFeatures.addPineTrees(vegetation);
        ModBiomeFeatures.addScarceSpruceTrees(vegetation);
        ModBiomeFeatures.addSpruceBushes(vegetation);

        return createBiome(biomeColors, spawnSettings, generationSettings, temperature, true);
    }

    public static Biome createWhiteMountainsFaces(Registerable<Biome> context, BiomeColorsDTO biomeColors, boolean cold) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addMountainVegetation(generationSettings);
        float temperature = (cold) ? 0.5f : 0.5f;

        return createBiome(biomeColors, spawnSettings, generationSettings, temperature, true);
    }

    public static Biome createWoodlandRealmBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
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

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createOceanBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addOceanAnimals(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addOceanVegetation(generationSettings);

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createOceanCoastBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addOceanAnimals(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addOceanVegetation(generationSettings);
        ModBiomeFeatures.addCoastalFoliage(vegetation);

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createPelennorFields(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addPlainsMobs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addGondorVegetation(generationSettings);
        ModBiomeFeatures.addDioriteBoulder(vegetation);
        ModBiomeFeatures.addRareWilderGrass(vegetation);
        ModBiomeFeatures.addTuftGrass(vegetation);

        vegetation.add(VegetationPlacedFeatures.TREES_PLAINS);
        ModBiomeFeatures.addRareLebethronTrees(vegetation);

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createPondBiome(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addRiverAnimals(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addOceanVegetation(generationSettings);
        ModBiomeFeatures.addRiverSand(undergroundOres);
        ModBiomeFeatures.addWillowTrees(vegetation);

        return createBiome(biomeColors, spawnSettings, generationSettings);
    }

    public static Biome createFrozenPond(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModSpawnSettingsBuilder.addColdWaterAnimals(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));
        
        ModBiomeFeatures.addDisks(undergroundOres);

        vegetation.add(OceanPlacedFeatures.KELP_COLD);
        vegetation.add(OceanPlacedFeatures.SEAGRASS_NORMAL);
        ModBiomeFeatures.addGrass(vegetation);

        return createBiome(biomeColors, spawnSettings, generationSettings, -0.1f, true);
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
