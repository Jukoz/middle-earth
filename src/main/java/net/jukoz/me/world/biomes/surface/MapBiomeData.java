package net.jukoz.me.world.biomes.surface;

import net.jukoz.me.world.biomes.BiomeColorsDTO;
import net.jukoz.me.world.biomes.MEBiomeDataConfigs;
import net.jukoz.me.world.biomes.MEBiomeKeys;
import net.jukoz.me.world.biomes.caves.CaveType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

import java.util.HashMap;

public class MapBiomeData {
    private static HashMap<RegistryKey<Biome>, BiomeData> biomes;

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

    private static void addBiome(BiomeData biome){
        biomes.put(biome.getBiomeRegistryKey(), biome);
    }

    public static BiomeData getBiome(RegistryKey<Biome> biomeRegistryKey){
        if(!biomes.containsKey(biomeRegistryKey))
            throw new RuntimeException("Cannot find %s in the custom biome data pool".formatted(biomeRegistryKey.getValue()));
        return biomes.get(biomeRegistryKey);
    }

    public static void loadBiomes() {
        biomes = new HashMap<>();
        SubBiomes.loadSubBiomes();
        loadGenericPonds();
        loadGenericRivers();
        loadGenericOceans();

        // ANDUIN_VALES
        addBiome(new BiomeData(MEBiomeKeys.ANDUIN_VALES, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, defaultWater, defaultWaterFog, 8703593, 6924625)));
        addBiome(new BiomeData(MEBiomeKeys.ANDUIN_VALES_FOREST, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, defaultWater, defaultWaterFog, 8703593, 6463563)));
        addBiome(new BiomeData(MEBiomeKeys.DARK_ANDUIN_VALES, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(7246271, 8951443, 4354703, 1128527, 8032632, 7901046)));
        // ANORIEN
        addBiome(new BiomeData(MEBiomeKeys.ANORIEN, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.gondorLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, defaultWater, defaultWaterFog, 6799458, 6662221)));
        addBiome(new BiomeData(MEBiomeKeys.ANORIEN_RIVERSIDE, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.gondorLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, defaultWater, defaultWaterFog, 6799458, 6662221)));
        addBiome(new BiomeData(MEBiomeKeys.ANORIEN_FOOTHILLS, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.gondorLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, defaultWater, defaultWaterFog, 6799458, 6662221)));
        // BARROW_DOWNS
        addBiome(new BiomeData(MEBiomeKeys.BARROW_DOWNS, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(5993621, 7635851, 4812936, 3034721, 6721389, 6525545)));
        // BELERIAND
        addBiome(new BiomeData(MEBiomeKeys.BELERIAND_ISLAND, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(defaultSky, 12637429, defaultOceanWater, defaultOceanWaterFog, 6466659, 5740626)));
        // BELFALAS
        addBiome(new BiomeData(MEBiomeKeys.BELFALAS, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.gondorLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, defaultWater, defaultWaterFog, 5951101, 5485154)));
        addBiome(new BiomeData(MEBiomeKeys.BELFALAS_BEACH, MEBiomeDataConfigs.whiteSandShores, MEBiomeDataConfigs.gondorLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, defaultShoreWater, defaultWaterFog, 7323271, 6665073)));
        addBiome(new BiomeData(MEBiomeKeys.BELFALAS_HILLS, MEBiomeDataConfigs.whiteMountains, MEBiomeDataConfigs.gondorLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, defaultWater, defaultWaterFog, 6011255, 5614178)));
        // BLACKROOT
        addBiome(new BiomeData(MEBiomeKeys.BLACKROOT_VALE, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.gondorLayers,
                new BiomeColorsDTO(8036582, 12176100, 5076939, defaultWaterFog, 6400098, 6069598)));
        // BLUE MOUNTAINS
        addBiome(new BiomeData(MEBiomeKeys.BLUE_MOUNTAINS, MEBiomeDataConfigs.blueMountains, MEBiomeDataConfigs.gonluinLayers,
                new BiomeColorsDTO(7905261, 12241911, defaultWater, defaultWaterFog, 6652524, 6652524)));
        addBiome(new BiomeData(MEBiomeKeys.BLUE_MOUNTAINS_FOOTHILLS, MEBiomeDataConfigs.blueMountainsBase, MEBiomeDataConfigs.gonluinLayers,
                new BiomeColorsDTO(7905261, 12241911, defaultWater, defaultWaterFog, 6652524, 6652524)));
        addBiome(new BiomeData(MEBiomeKeys.BLUE_MOUNTAINS_BASE, MEBiomeDataConfigs.blueMountainsBase, MEBiomeDataConfigs.gonluinLayers,
                new BiomeColorsDTO(7905261, 12241911, defaultWater, defaultWaterFog, 5796446, 6652524)));
        addBiome(new BiomeData(MEBiomeKeys.BLUE_MOUNTAINS_PEAKS, MEBiomeDataConfigs.blueMountainsPeaks, MEBiomeDataConfigs.gonluinLayers,
                new BiomeColorsDTO(7905261, 12241911, defaultWater, defaultWaterFog, 4677202, 5270363)));
        // BROWN LANDS
        addBiome(new BiomeData(MEBiomeKeys.BROWN_LANDS, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(7570864, 8292235, 5075593, 1259598, 8032632, 7901046)));
        // CARADHRAS
        addBiome(new BiomeData(MEBiomeKeys.CARADHRAS_BASE, MEBiomeDataConfigs.caradhras, MEBiomeDataConfigs.mistyMountainsLayers,
                new BiomeColorsDTO(hillySky, defaultFog, defaultWater, defaultWaterFog, 7777673, 7316862), CaveType.MISTIES));
        addBiome(new BiomeData(MEBiomeKeys.CARADHRAS, MEBiomeDataConfigs.caradhras, MEBiomeDataConfigs.mistyMountainsLayers,
                new BiomeColorsDTO(hillySky, defaultFog, defaultWater, defaultWaterFog, 7777673, 7316862), CaveType.MISTIES));
        addBiome(new BiomeData(MEBiomeKeys.CARADHRAS_PEAKS, MEBiomeDataConfigs.caradhrasPeaks, MEBiomeDataConfigs.mistyMountainsLayers,
                new BiomeColorsDTO(hillySky, defaultFog, defaultWater, defaultWaterFog, 7777673, 7316862), CaveType.MISTIES));
        // CELEBDIL
        addBiome(new BiomeData(MEBiomeKeys.CELEBDIL_BASE, MEBiomeDataConfigs.celebdil, MEBiomeDataConfigs.mistyMountainsLayers,
                new BiomeColorsDTO(hillySky, defaultFog, defaultWater, defaultWaterFog, 7777673, 7316862), CaveType.MISTIES));
        addBiome(new BiomeData(MEBiomeKeys.CELEBDIL, MEBiomeDataConfigs.celebdil, MEBiomeDataConfigs.mistyMountainsLayers,
                new BiomeColorsDTO(hillySky, defaultFog, defaultWater, defaultWaterFog, 7777673, 7316862), CaveType.MISTIES));
        addBiome(new BiomeData(MEBiomeKeys.CELEBDIL_PEAKS, MEBiomeDataConfigs.celebdilPeaks, MEBiomeDataConfigs.mistyMountainsLayers,
                new BiomeColorsDTO(hillySky, defaultFog, defaultWater, defaultWaterFog, 7777673, 7316862), CaveType.MISTIES));
        // CORSAIR COASTS
        addBiome(new BiomeData(MEBiomeKeys.CORSAIR_COASTS, MEBiomeDataConfigs.nearHarad, MEBiomeDataConfigs.sandstoneLayers,
                new BiomeColorsDTO(nearHaradSky, defaultFog, 5212644, 333363, 12107900, 10860366), CaveType.HARAD));
        // DAGORLAD
        addBiome(new BiomeData(MEBiomeKeys.DAGORLAD, MEBiomeDataConfigs.mordorGrass, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(7239328, 7632257, 4874882, 992318, 6971996, 8092011)));
        // DALE
        addBiome(new BiomeData(MEBiomeKeys.DALE, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(8230911, 12178175, defaultWater, defaultWaterFog, 6400105, 8703593)));
        addBiome(new BiomeData(MEBiomeKeys.DALE_FOREST, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(8230911, 12178175, defaultWater, defaultWaterFog, 6393176, 4820272)));
        addBiome(new BiomeData(MEBiomeKeys.DALE_HILL, MEBiomeDataConfigs.stoneHills, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(8230911, 12178175, defaultWater, defaultWaterFog, 6400105, 8703593)));
        addBiome(new BiomeData(MEBiomeKeys.DALE_CITY, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(8230911, 12178175, defaultWater, defaultWaterFog, 6400105, 8703593)));
        addBiome(new BiomeData(MEBiomeKeys.DALE_RIVERSIDE, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(hillySky, 12637183, defaultWater, defaultWaterFog, 7253882, 7580788)));
        // DEAD MARSHES
        addBiome(new BiomeData(MEBiomeKeys.DEAD_MARSHES, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(11908531, 7108218, 3289373, 198924, 6115374, 5794902)));
        addBiome(new BiomeData(MEBiomeKeys.DEAD_MARSHES_WATER, MEBiomeDataConfigs.ashenDirt, MEBiomeDataConfigs.ashenStoneLayers,
                new BiomeColorsDTO(11908531, 7108218, 3289373, 198924, 6115374, 5794902)));
        // DOL GOLDUR
        addBiome(new BiomeData(MEBiomeKeys.DOL_GULDUR, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.ashenStoneLayers,
                new BiomeColorsDTO(3684976, 5065045, 2503248, 462892, 3554356, 3424049)));
        addBiome(new BiomeData(MEBiomeKeys.DOL_GULDUR_HILL, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.ashenStoneLayers,
                new BiomeColorsDTO(3684976, 5065045, 2503248, 462892, 3554356, 3424049)));
        // DORWINION
        addBiome(new BiomeData(MEBiomeKeys.DORWINION, MEBiomeDataConfigs.limeStoneHills, MEBiomeDataConfigs.limeStoneLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, defaultWater, defaultWaterFog, 4631895, 4431186)));
        addBiome(new BiomeData(MEBiomeKeys.DORWINION_HILLS, MEBiomeDataConfigs.limeStoneHills, MEBiomeDataConfigs.limeStoneLayers,
                new BiomeColorsDTO(hillySky, defaultFog, defaultWater, defaultWaterFog, 9084547, 8427113)));
        // DUNLAND
        addBiome(new BiomeData(MEBiomeKeys.DUNLAND_FOOTHILLS, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(7508201, 10863086, defaultWater, defaultWaterFog, 6722387, 6198343)));
        addBiome(new BiomeData(MEBiomeKeys.NORTHERN_DUNLAND, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(7508201, 12964294, defaultWater, defaultWaterFog, 6722387, 6198343)));
        addBiome(new BiomeData(MEBiomeKeys.SOUTHERN_DUNLAND, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(7508201, 10863086, defaultWater, defaultWaterFog, 8302697, 7252827)));
        // EAST BIGHT
        addBiome(new BiomeData(MEBiomeKeys.EAST_BIGHT, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(7571933, defaultFog, 12440052, 591663, 10864741, 8894805)));
        // EMYN MUIL
        addBiome(new BiomeData(MEBiomeKeys.EMYN_MUIL_POND, MEBiomeDataConfigs.emynMuil, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(6980296, 10402016, 3100271, 597791, 4412998, 3359013)));
        addBiome(new BiomeData(MEBiomeKeys.EMYN_MUIL, MEBiomeDataConfigs.emynMuil, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(6980296, 10402016, 4618461, defaultWaterFog, 9666387, 9731143)));
        addBiome(new BiomeData(MEBiomeKeys.EMYN_MUIL_CLIFFS, MEBiomeDataConfigs.emynMuil, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(6980296, 10402016, 4618461, defaultWaterFog, 9666387, 9731143)));
        addBiome(new BiomeData(MEBiomeKeys.EMYN_MUIL_PEAKS, MEBiomeDataConfigs.emynMuil, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(6980296, 10402016, 4618461, defaultWaterFog, 9666387, 9731143)));
        // ENEDWAITH
        addBiome(new BiomeData(MEBiomeKeys.ENEDWAITH, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(7181795, 10731509, 4618461, defaultWaterFog, 8761449, 7842644)));
        // EREGION
        addBiome(new BiomeData(MEBiomeKeys.EREGION, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.limeStoneLayers,
                new BiomeColorsDTO(7908853, 11393279, 5077445, 331050, 6858575, 6198343)));
        // ETHIR ANDUIN
        addBiome(new BiomeData(MEBiomeKeys.ETHIR_ANDUIN, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, defaultWater, defaultWaterFog, 6400102, 6137428)));
        // FANGORN
        addBiome(new BiomeData(MEBiomeKeys.FANGORN, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(7972607, defaultFog, 4293787, 338483, 3559947, 1789719)));
        addBiome(new BiomeData(MEBiomeKeys.FANGORN_FOOTHILLS, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(7972607, defaultFog, 4293787, 338483, 5533992, 3567922)));
        // FANUIDHOL
        addBiome(new BiomeData(MEBiomeKeys.FANUIDHOL_BASE, MEBiomeDataConfigs.fanuidhol, MEBiomeDataConfigs.mistyMountainsLayers,
                new BiomeColorsDTO(hillySky, defaultFog, defaultWater, defaultWaterFog, 7777673, 7316862)));
        addBiome(new BiomeData(MEBiomeKeys.FANUIDHOL, MEBiomeDataConfigs.fanuidhol, MEBiomeDataConfigs.mistyMountainsLayers,
                new BiomeColorsDTO(hillySky, defaultFog, defaultWater, defaultWaterFog, 7777673, 7316862)));
        addBiome(new BiomeData(MEBiomeKeys.FANUIDHOL_PEAKS, MEBiomeDataConfigs.fanuidholPeaks, MEBiomeDataConfigs.mistyMountainsLayers,
                new BiomeColorsDTO(hillySky, defaultFog, defaultWater, defaultWaterFog, 7777673, 7316862)));
        // FORODWAITH
        addBiome(new BiomeData(MEBiomeKeys.FORODWAITH, MEBiomeDataConfigs.forodwaith, MEBiomeDataConfigs.frozenLayers,
                new BiomeColorsDTO(8364543, 10335206, 3823818, 66852, 3494723, 4478280), CaveType.FOROD));
        // GULF OF LHUN
        addBiome(new BiomeData(MEBiomeKeys.GULF_OF_LHUN_SHORES, MEBiomeDataConfigs.gulfOfLhunShoresLayers, MEBiomeDataConfigs.limeStoneLayers,
                new BiomeColorsDTO(8827134, 12771327, defaultShoreWater, defaultWaterFog, 6523989, 5667402)));
        addBiome(new BiomeData(MEBiomeKeys.GULF_OF_LHUN_CLIFFS, MEBiomeDataConfigs.gulfOfLhunShoresLayers, MEBiomeDataConfigs.limeStoneLayers,
                new BiomeColorsDTO(8827134, 12771327, defaultShoreWater, defaultWaterFog, 6523989, 5667402)));
        // GONDOR
        addBiome(new BiomeData(MEBiomeKeys.GONDOR, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.gondorLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, defaultWater, defaultWaterFog, 7582562, 6592327)));
        // GORGOROTH
        addBiome(new BiomeData(MEBiomeKeys.GORGOROTH, MEBiomeDataConfigs.mordor, MEBiomeDataConfigs.ashenStoneLayers,
                new BiomeColorsDTO(2564128, 1709079, 4869697, 1513734, 3156775, 2169880), CaveType.ASHEN));
        // GREY MOUNTAINS
        addBiome(new BiomeData(MEBiomeKeys.GREY_PLAINS, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(hillySky, 12637179, defaultWater, defaultWaterFog, 5939024, 8566393)));
        addBiome(new BiomeData(MEBiomeKeys.GREY_MOUNTAINS_BASE, MEBiomeDataConfigs.greyMountains, MEBiomeDataConfigs.greyMountainsLayers,
                new BiomeColorsDTO(8098794, 12701936, defaultWater, defaultWaterFog, 8823414, 9022583)));
        addBiome(new BiomeData(MEBiomeKeys.GREY_MOUNTAINS, MEBiomeDataConfigs.greyMountains, MEBiomeDataConfigs.greyMountainsLayers,
                new BiomeColorsDTO(8098794, 12701936, defaultWater, defaultWaterFog, 8823414, 9022583)));
        addBiome(new BiomeData(MEBiomeKeys.GREY_MOUNTAINS_PEAKS, MEBiomeDataConfigs.greyMountains, MEBiomeDataConfigs.greyMountainsLayers,
                new BiomeColorsDTO(8098794, 12701936, defaultWater, defaultWaterFog, 8823414, 9022583)));
        // GUNDABAD
        addBiome(new BiomeData(MEBiomeKeys.GUNDABAD_PLAINS, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(hillySky, 12637183, defaultWater, defaultWaterFog, 8036220, 7511410)));
        // HARAD
        addBiome(new BiomeData(MEBiomeKeys.HARAD, MEBiomeDataConfigs.nearHarad, MEBiomeDataConfigs.sandstoneLayers,
                new BiomeColorsDTO(nearHaradSky, nearHaradSkyFog, 5407446, 1120828, 12301405, 13356379), CaveType.HARAD));
        addBiome(new BiomeData(MEBiomeKeys.HARAD_DESERT, MEBiomeDataConfigs.harad, MEBiomeDataConfigs.sandstoneLayers,
                new BiomeColorsDTO(nearHaradSky, nearHaradSkyFog, 5407446, 1120828, 13419633, 9615182), CaveType.HARAD));
        addBiome(new BiomeData(MEBiomeKeys.HARONDOR, MEBiomeDataConfigs.nearHarad, MEBiomeDataConfigs.sandstoneLayers,
                new BiomeColorsDTO(nearHaradSky, nearHaradSkyFog, 5406149, 1120828, 12305028, 10860366), CaveType.HARAD));
        // HIGH MOOR
        addBiome(new BiomeData(MEBiomeKeys.HIGH_MOOR, MEBiomeDataConfigs.limeStoneHills, MEBiomeDataConfigs.limeStoneLayers,
                new BiomeColorsDTO(9090047, 13426943, defaultWater, defaultWaterFog, 8630141, 8169079)));
        addBiome(new BiomeData(MEBiomeKeys.HIGH_MOOR_VALE, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.limeStoneLayers,
                new BiomeColorsDTO(8695029, 12637426, defaultWater, defaultWaterFog, 8630141, 8169079)));
        addBiome(new BiomeData(MEBiomeKeys.HIGH_MOOR_HILLS, MEBiomeDataConfigs.limeStoneMountains, MEBiomeDataConfigs.limeStoneLayers,
                new BiomeColorsDTO(8695029, 12637426, defaultWater, defaultWaterFog, 8630141, 8169079)));
        // HILLS OF EVENDIM
        addBiome(new BiomeData(MEBiomeKeys.HILLS_OF_EVENDIM, MEBiomeDataConfigs.stoneHills, MEBiomeDataConfigs.sandstoneLayers,
                new BiomeColorsDTO(hillySky, defaultFog, defaultWater, defaultWaterFog, 9087338, 9218155)));
        // IRON HILLS
        addBiome(new BiomeData(MEBiomeKeys.IRON_HILLS, MEBiomeDataConfigs.ironHills, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(10140415, 13031679, defaultWater, defaultWaterFog, 6922099, 7119988)));
        addBiome(new BiomeData(MEBiomeKeys.IRON_HILLS_BASE, MEBiomeDataConfigs.ironHills, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(8761343, defaultFog, defaultWater, defaultWaterFog, 7187321, 6793328)));
        addBiome(new BiomeData(MEBiomeKeys.IRON_HILLS_PEAKS, MEBiomeDataConfigs.ironHills, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(8761343, defaultFog, defaultWater, defaultWaterFog, 7187321, 6793328)));
        addBiome(new BiomeData(MEBiomeKeys.IRON_HILLS_PLAINS, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(hillySky, 12637183, defaultWater, defaultWaterFog, 7713657, 7580788)));
        addBiome(new BiomeData(MEBiomeKeys.IRON_FOOTHILLS, MEBiomeDataConfigs.ironHills, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(hillySky, 12637183, defaultWater, defaultWaterFog, 7253882, 7580788)));
        // ISENGARD
        addBiome(new BiomeData(MEBiomeKeys.ISENGARD, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, 5076423, defaultWaterFog, 5932619, 5867338)));
        addBiome(new BiomeData(MEBiomeKeys.ISENGARD_HILL, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, 5076423, defaultWaterFog, 5932619, 5867338)));
        // ITHILIEN
        addBiome(new BiomeData(MEBiomeKeys.ITHILIEN, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.gondorLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, defaultWater, defaultWaterFog, 5152072, 4889668)));
        addBiome(new BiomeData(MEBiomeKeys.ITHILIEN_WASTES, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.gondorLayers,
                new BiomeColorsDTO(9085388, 11254223, 4944318, 723757, 5733716, 5537108)));
        // LAMEDON
        addBiome(new BiomeData(MEBiomeKeys.LAMEDON, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.gondorLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, defaultWater, defaultWaterFog, 9162899, 8043898)));
        // LEBENNIN
        addBiome(new BiomeData(MEBiomeKeys.LEBENNIN, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.gondorLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, defaultWater, defaultWaterFog, 5883985, 744996)));
        addBiome(new BiomeData(MEBiomeKeys.LEBENNIN_SHORES, MEBiomeDataConfigs.sandShores, MEBiomeDataConfigs.gondorLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, defaultShoreWater, defaultWaterFog, 5883985, 7449969)));
        // LINDON
        addBiome(new BiomeData(MEBiomeKeys.LINDON, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.limeStoneLayers,
                new BiomeColorsDTO(8827134, 12771327, defaultWater, defaultWaterFog, 6523989, 5667402)));
        // LONELY MOUNTAINS
        addBiome(new BiomeData(MEBiomeKeys.LONELY_MOUNTAIN, MEBiomeDataConfigs.lonelyMountain, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(hillySky, 13031679, defaultWater, defaultWaterFog, 7188600, 6529388)));
        addBiome(new BiomeData(MEBiomeKeys.LONELY_MOUNTAIN_BASE, MEBiomeDataConfigs.lonelyMountain, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(hillySky, 12637183, defaultWater, defaultWaterFog, 6927733, 6005862)));
        addBiome(new BiomeData(MEBiomeKeys.LONELY_MOUNTAIN_PEAKS, MEBiomeDataConfigs.lonelyMountainPeak, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(hillySky, 13031679, defaultWater, defaultWaterFog, 8106628, 6987890)));
        addBiome(new BiomeData(MEBiomeKeys.LONELY_MOUNTAIN_TAIGA, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(hillySky, 12637183, defaultWater, defaultWaterFog, 7253882, 7580788)));
        // LONG MARSHES
        addBiome(new BiomeData(MEBiomeKeys.LONG_MARSHES, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(8295660, 10993126, 6853265, 1919305, 5676922, 5282655)));
        // LOSSARNACH
        addBiome(new BiomeData(MEBiomeKeys.LOSSARNACH, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.gondorLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, defaultWater, defaultWaterFog, 8240485, 7909996)));
        addBiome(new BiomeData(MEBiomeKeys.LOSSARNACH_VALLEY, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.gondorLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, defaultWater, defaultWaterFog, 8896601, 9355638)));
        // LOTHLORIEN
        addBiome(new BiomeData(MEBiomeKeys.LORIEN_EDGE, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.limeStoneLayers,
                new BiomeColorsDTO(defaultSky, 11455105, defaultWater, defaultWaterFog, 10601262, 6202980)));
        addBiome(new BiomeData(MEBiomeKeys.LOTHLORIEN, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.limeStoneLayers,
                new BiomeColorsDTO(defaultSky, 13748853, defaultWater, defaultWaterFog, 12961832, 6989412)));
        // MINHIRIATH
        addBiome(new BiomeData(MEBiomeKeys.MINHIRIATH, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.limeStoneLayers,
                new BiomeColorsDTO(defaultSky, 12637429, 4615389, 658236, 8626526, 8231005)));
        // MIRKWOOD
        addBiome(new BiomeData(MEBiomeKeys.MIRKWOOD, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(6849446, 6780008, 7111535, 338483, 4151612, 3823415)));
        addBiome(new BiomeData(MEBiomeKeys.MIRKWOOD_EDGE, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(7578800, 7897724, 7111535, 338483, 4546876, 4284215)));
        addBiome(new BiomeData(MEBiomeKeys.MIRKWOOD_FOOTHILLS, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(6849446, 6780008, 7111535, 338483, 4151612, 3823415)));
        addBiome(new BiomeData(MEBiomeKeys.MIRKWOOD_MOUNTAINS_BASE, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(6849446, 6780008, 7111535, 338483, 4151612, 3823415)));
        addBiome(new BiomeData(MEBiomeKeys.MIRKWOOD_MOUNTAINS, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(6849446, 6780008, 7111535, 338483, 4151612, 3823415)));
        addBiome(new BiomeData(MEBiomeKeys.MIRKWOOD_MOUNTAINS_PEAKS, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(7972607, 9873053, 7111535, 338483, 4678724, 4482114)));
        addBiome(new BiomeData(MEBiomeKeys.DARK_MIRKWOOD, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(5269900, 4806731, 4544130, 338483, 4151612, 3823415)));
        addBiome(new BiomeData(MEBiomeKeys.DARK_MIRKWOOD_EDGE, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(5861275, 8293250, 4551554, 467756, 4083260, 3690038)));
        addBiome(new BiomeData(MEBiomeKeys.MIRKWOOD_SWAMP, MEBiomeDataConfigs.mud, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(6981536, 8821922, 7111535, 1458241, 4546876, 4284215)));
        addBiome(new BiomeData(MEBiomeKeys.NORTHERN_MIRKWOOD_SWAMP, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(6849692, 8427421, 7111535, 1458241, 4546876, 4284215)));
        addBiome(new BiomeData(MEBiomeKeys.MIRKWOOD_MARSHES, MEBiomeDataConfigs.mud, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(6981536, 8821922, 7111535, 1458241, 4546876, 4284215)));
        addBiome(new BiomeData(MEBiomeKeys.NORTHERN_MIRKWOOD_MARSHES, MEBiomeDataConfigs.mud, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(6849692, 8427421, 7111535, 1458241, 4546876, 4284215)));
        // MISTY MOUNTAINS
        addBiome(new BiomeData(MEBiomeKeys.MISTY_MOUNTAINS_BASE, MEBiomeDataConfigs.mistyMountains, MEBiomeDataConfigs.mistyMountainsLayers,
                new BiomeColorsDTO(hillySky, defaultFog, defaultWater, defaultWaterFog, 7777673, 7316862), CaveType.MISTIES));
        addBiome(new BiomeData(MEBiomeKeys.MISTY_MOUNTAINS, MEBiomeDataConfigs.mistyMountains, MEBiomeDataConfigs.mistyMountainsLayers,
                new BiomeColorsDTO(hillySky, defaultFog, defaultWater, defaultWaterFog, 7777673, 7316862), CaveType.MISTIES));
        addBiome(new BiomeData(MEBiomeKeys.MISTY_MOUNTAINS_PEAKS, MEBiomeDataConfigs.mistiesPeaks, MEBiomeDataConfigs.mistyMountainsLayers,
                new BiomeColorsDTO(hillySky, defaultFog, defaultWater, defaultWaterFog, 7777673, 7316862), CaveType.MISTIES));
        // MORDOR
        addBiome(new BiomeData(MEBiomeKeys.MORDOR, MEBiomeDataConfigs.mordor, MEBiomeDataConfigs.ashenStoneLayers,
                new BiomeColorsDTO(2695203, 1905947, 6450777, 1513734, 3550502, 2695966), CaveType.ASHEN));
        addBiome(new BiomeData(MEBiomeKeys.MORDOR_MOUNTAINS_FOOTHILLS, MEBiomeDataConfigs.mordorMountainsPeaks, MEBiomeDataConfigs.ashenStoneLayers,
                new BiomeColorsDTO(3747117, 2629407, 6450777, 1513734, 5129527, 3486247), CaveType.ASHEN));
        addBiome(new BiomeData(MEBiomeKeys.MORDOR_MOUNTAINS, MEBiomeDataConfigs.mordorMountainsPeaks, MEBiomeDataConfigs.ashenStoneLayers,
                new BiomeColorsDTO(4142646, 3090215, 6450777, 1513734, 3550502, 2169880), CaveType.ASHEN));
        addBiome(new BiomeData(MEBiomeKeys.MORDOR_MOUNTAINS_PEAKS, MEBiomeDataConfigs.mordorMountainsPeaks, MEBiomeDataConfigs.ashenStoneLayers,
                new BiomeColorsDTO(4142646, 3090215, 6450777, 1513734, 3550502, 2169880), CaveType.ASHEN));
        addBiome(new BiomeData(MEBiomeKeys.MORDOR_WASTES, MEBiomeDataConfigs.ashenDirt, MEBiomeDataConfigs.ashenStoneLayers,
                new BiomeColorsDTO(5460048, 4999240, 5860962, 731161, 6252369, 4735297), CaveType.ASHEN));
        // MORGUL VALE
        addBiome(new BiomeData(MEBiomeKeys.MORGUL_VALE, MEBiomeDataConfigs.mordorGrass, MEBiomeDataConfigs.ashenStoneLayers,
                new BiomeColorsDTO(4479570, 3690813, 3897457, 595232, 4545602, 4348994), CaveType.ASHEN));
        addBiome(new BiomeData(MEBiomeKeys.MORGUL_RIVER, MEBiomeDataConfigs.ashenDirt, MEBiomeDataConfigs.ashenStoneLayers,
                new BiomeColorsDTO(4479570, 3690813, 3897457, 595232, 4545602, 4348994)));
        // MOUNT DOOM
        addBiome(new BiomeData(MEBiomeKeys.MOUNT_DOOM, MEBiomeDataConfigs.mordorMountainsPeaks, MEBiomeDataConfigs.ashenStoneLayers,
                new BiomeColorsDTO(4142646, 3090215, 6450777, 1513734, 3550502, 2169880), CaveType.ASHEN));
        // MOUNT GUNDABAD
        addBiome(new BiomeData(MEBiomeKeys.MOUNT_GUNDABAD_BASE, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(8098794, 12701936, defaultWater, defaultWaterFog, 8823414, 9022583)));
        addBiome(new BiomeData(MEBiomeKeys.MOUNT_GUNDABAD, MEBiomeDataConfigs.greyMountains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(8098794, 12701936, defaultWater, defaultWaterFog, 8823414, 9022583)));
        addBiome(new BiomeData(MEBiomeKeys.MOUNT_GUNDABAD_PEAKS, MEBiomeDataConfigs.greyMountains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(8098794, 12701936, defaultWater, defaultWaterFog, 8823414, 9022583)));
        // NAN CURUNIR
        addBiome(new BiomeData(MEBiomeKeys.NAN_CURUNIR, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, 5076423, defaultWaterFog, 5406786, 4554563)));
        // NEN HITHOEL
        addBiome(new BiomeData(MEBiomeKeys.NEN_HITHOEL, MEBiomeDataConfigs.ocean, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(waterSky, 12638463, 6853316, 6853316, 10995507, 7181907)));
        addBiome(new BiomeData(MEBiomeKeys.NEN_HITHOEL_FOREST, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(6980296, 10402016, 4618461, defaultWaterFog, 9666387, 9731143)));
        addBiome(new BiomeData(MEBiomeKeys.NEN_HITHOEL_SHORES, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.gondorLayers,
                new BiomeColorsDTO(6980296, 10402016, 4618461, defaultWaterFog, 9666387, 9731143)));
        // NINDALF
        addBiome(new BiomeData(MEBiomeKeys.NINDALF, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, defaultWater, defaultWaterFog, 6263143, 5869915)));
        // NORTH DOWNS
        addBiome(new BiomeData(MEBiomeKeys.NORTH_DOWNS, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(hillySky, defaultFog, defaultWater, defaultWaterFog, 9414008, 9018483)));
        // NORTHERN WASTELANDS
        addBiome(new BiomeData(MEBiomeKeys.NORTHERN_WASTELANDS, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.frozenLayers,
                new BiomeColorsDTO(hillySky, defaultFog, defaultWater, defaultWaterFog, 5932658, 7973008), CaveType.FOROD));
        // NURN
        addBiome(new BiomeData(MEBiomeKeys.NURN, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(8954077, 11781331, 5860970, 1321760, 7443043, 6982236)));
        addBiome(new BiomeData(MEBiomeKeys.NURN_EDGE, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(8821450, 11517913, 5202783, 1321247, 6511435, 7960147)));
        addBiome(new BiomeData(MEBiomeKeys.NURN_SEA, MEBiomeDataConfigs.ocean, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(8952797, 11779800, 5860963, 863008, 5465422, 5663573)));
        addBiome(new BiomeData(MEBiomeKeys.NURN_RIVER, MEBiomeDataConfigs.river, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(8952797, 11779800, 5860963, 863008, 5465422, 5663573)));
        addBiome(new BiomeData(MEBiomeKeys.EASTERN_NURN, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(8032225, defaultFog, 6720143, 1583408, 7435862, 7436627)));
        // OLD ANGMAR
        addBiome(new BiomeData(MEBiomeKeys.OLD_ANGMAR, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(8954077, 11781343, 4814544, 460593, 7443043, 6982236)));
        addBiome(new BiomeData(MEBiomeKeys.OLD_ANGMAR_FOREST, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(8954077, 11781343, 4814544, 460593, 7443043, 6982236)));
        addBiome(new BiomeData(MEBiomeKeys.OLD_ANGMAR_COLD_HILL, MEBiomeDataConfigs.greyMountains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(8954077, 11781343, 4814544, 460593, 7443043, 6982236)));
        addBiome(new BiomeData(MEBiomeKeys.OLD_ANGMAR_FROZEN_HILL, MEBiomeDataConfigs.greyMountains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(8954077, 11781343, 4814544, 460593, 7443043, 6982236)));
        // OLD ARTHEDAIN
        addBiome(new BiomeData(MEBiomeKeys.OLD_ARTHEDAIN, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(7907327, defaultFog, defaultWater, defaultWaterFog, 12508275, 11652468)));
        addBiome(new BiomeData(MEBiomeKeys.OLD_ARTHEDAIN_FOREST, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(7907327, defaultFog, defaultWater, defaultWaterFog, 10862435, 11126380)));
        addBiome(new BiomeData(MEBiomeKeys.OLD_ARTHEDAIN_MEADOW, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(7907327, defaultFog, defaultWater, defaultWaterFog, 12508275, 11652468)));
        addBiome(new BiomeData(MEBiomeKeys.OLD_ARTHEDAIN_FOOTHILL, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(7907327, defaultFog, defaultWater, defaultWaterFog, 11190641, 11126380)));
        // OLD CARDOLAN
        addBiome(new BiomeData(MEBiomeKeys.OLD_CARDOLAN, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(defaultSky, 12637429, 4615389, 658236, 9154400, 9154400)));
        addBiome(new BiomeData(MEBiomeKeys.OLD_CARDOLAN_FOREST, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(defaultSky, 12637429, 4615389, 658236, 8298580, 8627801)));
        addBiome(new BiomeData(MEBiomeKeys.OLD_CARDOLAN_HILL, MEBiomeDataConfigs.stoneHills, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(defaultSky, 12637429, 4615389, 658236, 9482090, 9154400)));
        // OLD RHUDAUR
        addBiome(new BiomeData(MEBiomeKeys.OLD_RHUDAUR, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(7508201, 10863086, 4618461, defaultWaterFog, 6722387, 6198343)));
        addBiome(new BiomeData(MEBiomeKeys.OLD_RHUDAUR_FOREST, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(7507684, 10862569, 4618461, defaultWaterFog, 5932359, 6131783)));
        addBiome(new BiomeData(MEBiomeKeys.OLD_RHUDAUR_HILL, MEBiomeDataConfigs.stoneHills, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(7507684, 10862569, 4618461, defaultWaterFog, 7049306, 6525264)));
        // OSGILIATH
        addBiome(new BiomeData(MEBiomeKeys.OSGILIATH, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.gondorLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, defaultWater, defaultWaterFog, 8104558, 7445848)));
        // PELENNOR FIELDS
        addBiome(new BiomeData(MEBiomeKeys.PELENNOR_FIELDS, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.gondorLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, defaultWater, defaultWaterFog, 8628846, 8169054)));
        // RHUN
        addBiome(new BiomeData(MEBiomeKeys.RHUN, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(8041727, 12773631, 4618980, defaultWaterFog, 10995507, 7181907)));
        // RHOVANION
        addBiome(new BiomeData(MEBiomeKeys.EASTERN_RHOVANION, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(7571933, defaultFog, 12440052, 591663, 8627523, 7052347)));
        addBiome(new BiomeData(MEBiomeKeys.SOUTHEAST_RHOVANION, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(8954077, 11781343, 4814544, 460593, 7443043, 6982236)));
        // ROHAN
        addBiome(new BiomeData(MEBiomeKeys.ROHAN, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, defaultWater, defaultWaterFog, 10342513, 6918471)));
        // SARN GEBIR SHORES
        addBiome(new BiomeData(MEBiomeKeys.SARN_GEBIR_SHORES, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.gondorLayers,
                new BiomeColorsDTO(10140415, 13031679, defaultWater, defaultWaterFog, 6922099, 7119988)));
        addBiome(new BiomeData(MEBiomeKeys.SARN_GEBIR_WILDLANDS, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(10140415, 13031679, defaultWater, defaultWaterFog, 6922099, 7119988)));
        // SHIRE
        addBiome(new BiomeData(MEBiomeKeys.SHIRE, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(8695029, 12637426, defaultWater, defaultWaterFog, 8704848, 6858783)));
        addBiome(new BiomeData(MEBiomeKeys.SHIRE_EDGE, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(8695029, 12637426, defaultWater, defaultWaterFog, 7978571, 6132766)));
        addBiome(new BiomeData(MEBiomeKeys.SHIRE_HILLS, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(8695029, 12637426, defaultWater, defaultWaterFog, 8175184, 6658600)));
        addBiome(new BiomeData(MEBiomeKeys.SHIRE_WOODS, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(8695029, 12637426, defaultWater, defaultWaterFog, 6991676, 6460967)));
        addBiome(new BiomeData(MEBiomeKeys.SHIRE_FOREST, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(8695029, 12637426, defaultWater, defaultWaterFog, 6662455, 5803292)));
        // SOUTHERN EPHEL DUATH
        addBiome(new BiomeData(MEBiomeKeys.SOUTHERN_EPHEL_DUATH_BASE, MEBiomeDataConfigs.southernMordorMountains, MEBiomeDataConfigs.ashenStoneLayers,
                new BiomeColorsDTO(3747117, 2629407, 6450777, 1513734, 5129527, 3486247), CaveType.ASHEN));
        addBiome(new BiomeData(MEBiomeKeys.SOUTHERN_EPHEL_DUATH, MEBiomeDataConfigs.southernMordorMountainsPeaks, MEBiomeDataConfigs.ashenStoneLayers,
                new BiomeColorsDTO(4142646, 3090215, 6450777, 1513734, 3550502, 2169880), CaveType.ASHEN));
        addBiome(new BiomeData(MEBiomeKeys.SOUTHERN_EPHEL_DUATH_PEAKS, MEBiomeDataConfigs.southernMordorMountainsPeaks, MEBiomeDataConfigs.ashenStoneLayers,
                new BiomeColorsDTO(4142646, 3090215, 6450777, 1513734, 3550502, 2169880), CaveType.ASHEN));
        // SOUTHERN FOROCHEL
        addBiome(new BiomeData(MEBiomeKeys.SOUTHERN_FOROCHEL, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.frozenLayers,
                new BiomeColorsDTO(hillySky, defaultFog, defaultWater, defaultWaterFog, 7177842, 7971216), CaveType.FOROD));
        // THE ANGLE
        addBiome(new BiomeData(MEBiomeKeys.THE_ANGLE, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, 4615389, 658236, 9878641, 8431193)));
        // THE OLD FOREST
        addBiome(new BiomeData(MEBiomeKeys.THE_OLD_FOREST, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(6785744, 10004675, 4421513, 402733, 2311707, 2050588)));
        // THE WHITE DOWNS
        addBiome(new BiomeData(MEBiomeKeys.THE_WHITE_DOWNS, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(9022444, 12898532, defaultWater, defaultWaterFog, 8431738, 7905395)));
        // THE WOLD
        addBiome(new BiomeData(MEBiomeKeys.THE_WOLD, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, defaultWater, defaultWaterFog, 12309403, 12242068)));
        // TOLFALAS
        addBiome(new BiomeData(MEBiomeKeys.TOLFALAS, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, defaultWater, defaultWaterFog, 10927716, 9615182)));
        // TOROGWAITH
        addBiome(new BiomeData(MEBiomeKeys.TOROGWAITH, MEBiomeDataConfigs.mordor, MEBiomeDataConfigs.ashenStoneLayers,
                new BiomeColorsDTO(3289130, 2827810, 6255709, 1252359, 4142897, 3156775), CaveType.ASHEN));
        // TROLLSHAWS
        addBiome(new BiomeData(MEBiomeKeys.TROLLSHAWS, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(6980296, 10402016, 4618461, defaultWaterFog, 9666387, 9731143), CaveType.ASHEN));
        // UDUN
        addBiome(new BiomeData(MEBiomeKeys.UDUN, MEBiomeDataConfigs.mordor, MEBiomeDataConfigs.ashenStoneLayers,
                new BiomeColorsDTO(4273461, 2826529, 6450777, 1513734, 3549478, 2695966), CaveType.ASHEN));
        // UMBAR
        addBiome(new BiomeData(MEBiomeKeys.UMBAR, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(7254527, 12638463, 5212644, 333363, 11059059, 9284946), CaveType.HARAD));
        // WEBBED WOODS
        addBiome(new BiomeData(MEBiomeKeys.WEBBED_WOODS, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(6385822, 5198943, 4544130, 338483, 4478786, 4017979)));
        // WITHERED HEATH
        addBiome(new BiomeData(MEBiomeKeys.WITHERED_HEATH, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(9479110, 11780310, 5141697, 460593, 8881498, 10328434)));
        // WHITE MOUNTAINS
        addBiome(new BiomeData(MEBiomeKeys.WHITE_MOUNTAINS_BASE, MEBiomeDataConfigs.whiteMountains, MEBiomeDataConfigs.gondorLayers,
                new BiomeColorsDTO(hillySky, 12638463, defaultWater, defaultWaterFog, 7185769, 6857066)));
        addBiome(new BiomeData(MEBiomeKeys.WHITE_MOUNTAINS, MEBiomeDataConfigs.whiteMountains, MEBiomeDataConfigs.gondorLayers,
                new BiomeColorsDTO(hillySky, 12638463, defaultWater, defaultWaterFog, 7183466, 7513204)));
        addBiome(new BiomeData(MEBiomeKeys.WHITE_MOUNTAINS_PEAKS, MEBiomeDataConfigs.whitePeaks, MEBiomeDataConfigs.gondorLayers,
                new BiomeColorsDTO(hillySky, 12638463, defaultWater, defaultWaterFog, 7185769, 6857066)));
        // WOODLANDS
        addBiome(new BiomeData(MEBiomeKeys.WOODLAND_REALM, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.limeStoneLayers,
                new BiomeColorsDTO(8497918, 10666932, 4492967, 471355, 3960119, 3370029)));
        addBiome(new BiomeData(MEBiomeKeys.WOODLAND_FOOTHILLS, MEBiomeDataConfigs.limeStoneHills, MEBiomeDataConfigs.limeStoneLayers,
                new BiomeColorsDTO(6849446, 6780008, 7111535, 338483, 4420926, 3567152)));
        addBiome(new BiomeData(MEBiomeKeys.WOODLAND_HILLS, MEBiomeDataConfigs.limeStoneHills, MEBiomeDataConfigs.limeStoneLayers,
                new BiomeColorsDTO(6849446, 6780008, 7111535, 338483, 4748611, 4093495)));
    }

   private static void loadGenericPonds(){
       addBiome(new BiomeData(MEBiomeKeys.POND,MEBiomeDataConfigs.pond, MEBiomeDataConfigs.stoneLayers,
               new BiomeColorsDTO(waterSky, defaultFog, 5141154, 331315, 7583083, 6592339)));
       addBiome(new BiomeData(MEBiomeKeys.FROZEN_POND, MEBiomeDataConfigs.ocean, MEBiomeDataConfigs.stoneLayers,
               new BiomeColorsDTO(8628223, 10599910, 3750089, 263470, 3494723, 4478280), CaveType.FOROD));
       addBiome(new BiomeData(MEBiomeKeys.OASIS, MEBiomeDataConfigs.beach, MEBiomeDataConfigs.sandstoneLayers,
               new BiomeColorsDTO(nearHaradSky, nearHaradSkyFog, 5407446, 1120828, 7253092, 6592350), CaveType.HARAD));
       addBiome(new BiomeData(MEBiomeKeys.WASTE_POND, MEBiomeDataConfigs.ashenDirt, MEBiomeDataConfigs.ashenStoneLayers,
               new BiomeColorsDTO(8163746, 10926783, 5860963, 863008, 4020033, 2695710)));

   }

    private static void loadGenericRivers(){
        addBiome(new BiomeData(MEBiomeKeys.FOREST_RIVER, MEBiomeDataConfigs.river, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(6981536, 8821922, 7111535, 1458241, 4546876, 4284215)));
        addBiome(new BiomeData(MEBiomeKeys.GREAT_RIVER, MEBiomeDataConfigs.river, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(waterSky, 12638463, defaultWater, defaultWaterFog, 10995507, 7181907)));
        addBiome(new BiomeData(MEBiomeKeys.RIVER, MEBiomeDataConfigs.river, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(waterSky, defaultFog, 4290790, defaultWaterFog, 7583083, 6592339)));
        addBiome(new BiomeData(MEBiomeKeys.RIVER_RUNNING, MEBiomeDataConfigs.river, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(waterSky, defaultFog, 4290790, defaultWaterFog, 7583083, 6592339)));
    }

    private static void loadGenericOceans(){
       addBiome(new BiomeData(MEBiomeKeys.OCEAN, MEBiomeDataConfigs.ocean, MEBiomeDataConfigs.stoneLayers,
               new BiomeColorsDTO(waterSky, defaultFog, defaultOceanWater, defaultOceanWaterFog, 7576434, 6588506)));
       addBiome(new BiomeData(MEBiomeKeys.OCEAN_COAST, MEBiomeDataConfigs.ocean, MEBiomeDataConfigs.stoneLayers,
               new BiomeColorsDTO(8104447, defaultFog, defaultCoastWater, defaultOceanWaterFog, 7971954, 6590810)));
       addBiome(new BiomeData(MEBiomeKeys.FROZEN_OCEAN, MEBiomeDataConfigs.ocean, MEBiomeDataConfigs.stoneLayers,
               new BiomeColorsDTO(8628223, 10599910, 3750089, 263470, 3494723, 4478280), CaveType.FOROD));
        addBiome(new BiomeData(MEBiomeKeys.LONG_LAKE, MEBiomeDataConfigs.ocean, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(waterSky, defaultFog, 4352228, 525619, 7576434, 6588506)));
        addBiome(new BiomeData(MEBiomeKeys.SEA_OF_RHUN, MEBiomeDataConfigs.ocean, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(waterSky, 12638463, 4159204, defaultWaterFog, 10995507, 7181907)));
   }
}
