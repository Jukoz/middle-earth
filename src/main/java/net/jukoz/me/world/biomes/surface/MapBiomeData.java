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
                new BiomeColorsDTO(defaultSky, defaultFog, defaultWater, defaultWaterFog, 7714397, 6463563)));
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
        addBiome(new BiomeData(MEBiomeKeys.BELFALAS_FOREST, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.gondorLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, defaultWater, defaultWaterFog, 5291633, 5221213)));
        addBiome(new BiomeData(MEBiomeKeys.BELFALAS_BEACH, MEBiomeDataConfigs.whiteSandShores, MEBiomeDataConfigs.gondorLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, defaultShoreWater, defaultWaterFog, 7323271, 6665073)));
        addBiome(new BiomeData(MEBiomeKeys.BELFALAS_HILLS, MEBiomeDataConfigs.whiteMountains, MEBiomeDataConfigs.gondorLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, defaultWater, defaultWaterFog, 6011255, 5614178)));
        // BLACKROOT
        addBiome(new BiomeData(MEBiomeKeys.BLACKROOT_VALE, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.gondorLayers,
                new BiomeColorsDTO(8036582, 12176100, 5076939, defaultWaterFog, 6400098, 6069598)));
        addBiome(new BiomeData(MEBiomeKeys.BLACKROOT_FOREST, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.gondorLayers,
                new BiomeColorsDTO(7575257, 11255256, 5076939, defaultWaterFog, 5610326, 5543511)));
        // BLUE MOUNTAINS
        addBiome(new BiomeData(MEBiomeKeys.BLUE_MOUNTAINS, MEBiomeDataConfigs.blueMountains, MEBiomeDataConfigs.gonluinLayers,
                new BiomeColorsDTO(7905261, 12241911, defaultWater, defaultWaterFog, 7903103, 6652524), CaveType.MOUNTAINS));
        addBiome(new BiomeData(MEBiomeKeys.BLUE_MOUNTAINS_FOOTHILLS, MEBiomeDataConfigs.blueMountainsBase, MEBiomeDataConfigs.gonluinLayers,
                new BiomeColorsDTO(7905261, 12241911, defaultWater, defaultWaterFog, 7376759, 6652524), CaveType.MOUNTAINS));
        addBiome(new BiomeData(MEBiomeKeys.BLUE_MOUNTAINS_BASE, MEBiomeDataConfigs.blueMountainsBase, MEBiomeDataConfigs.gonluinLayers,
                new BiomeColorsDTO(7905261, 12241911, defaultWater, defaultWaterFog, 7376759, 6652524), CaveType.MOUNTAINS));
        addBiome(new BiomeData(MEBiomeKeys.BLUE_MOUNTAINS_HIGH_LANDS, MEBiomeDataConfigs.blueMountainHighLands, MEBiomeDataConfigs.gonluinLayers,
                new BiomeColorsDTO(7905261, 12241911, defaultWater, defaultWaterFog, 7903103, 6652524), CaveType.MOUNTAINS));
        addBiome(new BiomeData(MEBiomeKeys.BLUE_MOUNTAINS_PEAKS, MEBiomeDataConfigs.blueMountainsPeaks, MEBiomeDataConfigs.gonluinLayers,
                new BiomeColorsDTO(7905261, 12241911, defaultWater, defaultWaterFog, 8495753, 5270363), CaveType.MOUNTAINS));
        addBiome(new BiomeData(MEBiomeKeys.BLUE_MOUNTAINS_WOODS, MEBiomeDataConfigs.blueMountainsBase, MEBiomeDataConfigs.gonluinLayers,
                new BiomeColorsDTO(7509727, 11715307, defaultWater, defaultWaterFog, 6521706, 5996644), CaveType.MOUNTAINS));
        // BROWN LANDS
        addBiome(new BiomeData(MEBiomeKeys.BROWN_LANDS, MEBiomeDataConfigs.ashenDirt, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(5460048, 4999240, 5860962, 731161, 6252369, 4735297)));
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
        addBiome(new BiomeData(MEBiomeKeys.DALE_MEADOW, MEBiomeDataConfigs.stoneHills, MEBiomeDataConfigs.stoneLayers,
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
        // DESOLATED LANDS
        addBiome(new BiomeData(MEBiomeKeys.DESOLATED_LANDS, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.ashenStoneLayers,
                new BiomeColorsDTO(8097212, 10661315, 5206453, 460588, 7572570, 7111523)));
        // DOL GOLDUR
        addBiome(new BiomeData(MEBiomeKeys.DOL_GULDUR, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.ashenStoneLayers,
                new BiomeColorsDTO(3684976, 5065045, 2503248, 462892, 3554356, 3424049)));
        addBiome(new BiomeData(MEBiomeKeys.DOL_GULDUR_HILL, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.ashenStoneLayers,
                new BiomeColorsDTO(3684976, 5065045, 2503248, 462892, 3554356, 3424049)));
        // DORWINION
        addBiome(new BiomeData(MEBiomeKeys.DORWINION, MEBiomeDataConfigs.limeStoneHills, MEBiomeDataConfigs.limeStoneLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, defaultWater, defaultWaterFog, 4631895, 4431186), CaveType.ELVEN));
        addBiome(new BiomeData(MEBiomeKeys.DORWINION_LAVENDER_FIELD, MEBiomeDataConfigs.limeStoneHills, MEBiomeDataConfigs.limeStoneLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, defaultWater, defaultWaterFog, 4631895, 4431186), CaveType.ELVEN));
        addBiome(new BiomeData(MEBiomeKeys.DORWINION_HILLS, MEBiomeDataConfigs.limeStoneHills, MEBiomeDataConfigs.limeStoneLayers,
                new BiomeColorsDTO(hillySky, defaultFog, defaultWater, defaultWaterFog, 9084547, 8427113), CaveType.ELVEN));
        // DUNLAND
        addBiome(new BiomeData(MEBiomeKeys.DUNLAND_FOOTHILLS, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(7508201, 10863086, defaultWater, defaultWaterFog, 6722387, 6198343)));
        addBiome(new BiomeData(MEBiomeKeys.DUNLAND_HILLS, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(7508201, 12964294, defaultWater, defaultWaterFog, 7116123, 6657105)));
        addBiome(new BiomeData(MEBiomeKeys.NORTHERN_DUNLAND, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(7508201, 12964294, defaultWater, defaultWaterFog, 6722387, 6198343)));
        addBiome(new BiomeData(MEBiomeKeys.NORTHERN_DUNLAND_GLADE, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(7903218, 13753556, defaultWater, defaultWaterFog, 7250265, 6659405)));
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
        addBiome(new BiomeData(MEBiomeKeys.ENEDWAITH_FIELD, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(7181795, 10731509, 4618461, defaultWaterFog, 9025898, 8106838)));
        addBiome(new BiomeData(MEBiomeKeys.ENEDWAITH_WHEAT_FIELD, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(7181795, 10731509, 4618461, defaultWaterFog, 0x92a865, 0xaac476)));

        // EREGION
        addBiome(new BiomeData(MEBiomeKeys.EREGION, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.limeStoneLayers,
                new BiomeColorsDTO(7908853, 11393279, 5077445, 331050, 6858575, 6198343), CaveType.ELVEN));
        addBiome(new BiomeData(MEBiomeKeys.EREGION_FOREST, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.limeStoneLayers,
                new BiomeColorsDTO(7709409, 11062768, 5077445, 331050, 6331720, 6066757), CaveType.ELVEN));
        addBiome(new BiomeData(MEBiomeKeys.EREGION_GLADE, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.limeStoneLayers,
                new BiomeColorsDTO(8500733, 11787007, 5077445, 331050, 7648091, 6659660), CaveType.ELVEN));
        // ETHIR ANDUIN
        addBiome(new BiomeData(MEBiomeKeys.ETHIR_ANDUIN, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, defaultWater, defaultWaterFog, 6400102, 6137428)));
        addBiome(new BiomeData(MEBiomeKeys.ETHIR_ANDUIN_RIVER_DELTA, MEBiomeDataConfigs.river, MEBiomeDataConfigs.stoneLayers,
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
        // GONDOR
        addBiome(new BiomeData(MEBiomeKeys.GONDOR, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.gondorLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, defaultWater, defaultWaterFog, 7582562, 6592327)));
        addBiome(new BiomeData(MEBiomeKeys.GONDOR_FOREST, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.gondorLayers,
                new BiomeColorsDTO(7510756, 12308204, defaultWater, defaultWaterFog, 7186525, 6658376)));
        addBiome(new BiomeData(MEBiomeKeys.GONDOR_HILL, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.gondorLayers,
                new BiomeColorsDTO(8432895, 12900082, defaultWater, defaultWaterFog, 8502640, 7381842)));
        // GORGOROTH
        addBiome(new BiomeData(MEBiomeKeys.GORGOROTH, MEBiomeDataConfigs.mordor, MEBiomeDataConfigs.ashenStoneLayers,
                new BiomeColorsDTO(2564128, 1709079, 4869697, 1513734, 3156775, 2169880), CaveType.ASHEN));
        addBiome(new BiomeData(MEBiomeKeys.GORGOROTH_ASHEN_WOODS, MEBiomeDataConfigs.mordor, MEBiomeDataConfigs.ashenStoneLayers,
                new BiomeColorsDTO(2958629, 2432545, 4869697, 1513734, 3156775, 2169880), CaveType.ASHEN));
        addBiome(new BiomeData(MEBiomeKeys.GORGOROTH_DELTA, MEBiomeDataConfigs.mordor, MEBiomeDataConfigs.ashenStoneLayers,
                new BiomeColorsDTO(2762018, 2695711, 4869697, 1513734, 3156775, 2169880), CaveType.ASHEN));
        // GREY MOUNTAINS
        addBiome(new BiomeData(MEBiomeKeys.GREY_PLAINS, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(hillySky, 12637179, defaultWater, defaultWaterFog, 6791518, 8566393)));
        addBiome(new BiomeData(MEBiomeKeys.GREY_ASHEN_WOODS, MEBiomeDataConfigs.greyMountains, MEBiomeDataConfigs.greyMountainsLayers,
                new BiomeColorsDTO(7640812, 12372710, defaultWater, defaultWaterFog, 7707497, 7642221)));
        addBiome(new BiomeData(MEBiomeKeys.GREY_MOUNTAINS_BASE, MEBiomeDataConfigs.greyMountains, MEBiomeDataConfigs.greyMountainsLayers,
                new BiomeColorsDTO(8098794, 12701936, defaultWater, defaultWaterFog, 8823414, 9022583), CaveType.MOUNTAINS));
        addBiome(new BiomeData(MEBiomeKeys.GREY_MOUNTAINS, MEBiomeDataConfigs.greyMountains, MEBiomeDataConfigs.greyMountainsLayers,
                new BiomeColorsDTO(8098794, 12701936, defaultWater, defaultWaterFog, 8823414, 9022583), CaveType.MOUNTAINS));
        addBiome(new BiomeData(MEBiomeKeys.GREY_MOUNTAINS_PEAKS, MEBiomeDataConfigs.greyMountainPeaks, MEBiomeDataConfigs.greyMountainsLayers,
                new BiomeColorsDTO(8098794, 12701936, defaultWater, defaultWaterFog, 8823414, 9022583), CaveType.MOUNTAINS));
        // GUNDABAD
        addBiome(new BiomeData(MEBiomeKeys.GUNDABAD_PLAINS, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(hillySky, 12637183, defaultWater, defaultWaterFog, 8036220, 7511410)));
        // HARAD
        addBiome(new BiomeData(MEBiomeKeys.HARAD, MEBiomeDataConfigs.nearHarad, MEBiomeDataConfigs.sandstoneLayers,
                new BiomeColorsDTO(nearHaradSky, nearHaradSkyFog, 5407446, 1120828, 12301405, 13356379), CaveType.HARAD));
        addBiome(new BiomeData(MEBiomeKeys.HARAD_DESERT, MEBiomeDataConfigs.harad, MEBiomeDataConfigs.sandstoneLayers,
                new BiomeColorsDTO(nearHaradSky, nearHaradSkyFog, 5407446, 1120828, 13419633, 9615182), CaveType.HARAD));
        addBiome(new BiomeData(MEBiomeKeys.HARAD_WOODS, MEBiomeDataConfigs.nearHarad, MEBiomeDataConfigs.sandstoneLayers,
                new BiomeColorsDTO(nearHaradSky, nearHaradSkyFog, 5407446, 1120828, 11515226, 12240727), CaveType.HARAD));
        addBiome(new BiomeData(MEBiomeKeys.HARONDOR, MEBiomeDataConfigs.nearHarad, MEBiomeDataConfigs.sandstoneLayers,
                new BiomeColorsDTO(nearHaradSky, nearHaradSkyFog, 5406149, 1120828, 12305028, 10860366), CaveType.HARAD));
        // HIGH MOOR
        addBiome(new BiomeData(MEBiomeKeys.HIGH_MOOR, MEBiomeDataConfigs.limeStoneHills, MEBiomeDataConfigs.limeStoneLayers,
                new BiomeColorsDTO(9090047, 13426943, defaultWater, defaultWaterFog, 8630141, 8169079), CaveType.ELVEN));
        addBiome(new BiomeData(MEBiomeKeys.HIGH_MOOR_VALE, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.limeStoneLayers,
                new BiomeColorsDTO(8695029, 12637426, defaultWater, defaultWaterFog, 8630141, 8169079), CaveType.ELVEN));
        addBiome(new BiomeData(MEBiomeKeys.HIGH_MOOR_HILLS, MEBiomeDataConfigs.limeStoneMountains, MEBiomeDataConfigs.limeStoneLayers,
                new BiomeColorsDTO(8695029, 12637426, defaultWater, defaultWaterFog, 8630141, 8169079), CaveType.ELVEN));
        // HILLS OF EVENDIM
        addBiome(new BiomeData(MEBiomeKeys.HILLS_OF_EVENDIM, MEBiomeDataConfigs.stoneHills, MEBiomeDataConfigs.sandstoneLayers,
                new BiomeColorsDTO(hillySky, defaultFog, defaultWater, defaultWaterFog, 9087338, 9218155)));
        // IRON HILLS
        addBiome(new BiomeData(MEBiomeKeys.IRON_HILLS, MEBiomeDataConfigs.ironHills, MEBiomeDataConfigs.ironhills,
                new BiomeColorsDTO(10140415, 13031679, defaultWater, defaultWaterFog, 6922099, 7119988), CaveType.MOUNTAINS));
        addBiome(new BiomeData(MEBiomeKeys.IRON_HILLS_BASE, MEBiomeDataConfigs.ironHills, MEBiomeDataConfigs.ironhills,
                new BiomeColorsDTO(8761343, defaultFog, defaultWater, defaultWaterFog, 7187321, 6793328), CaveType.MOUNTAINS));
        addBiome(new BiomeData(MEBiomeKeys.IRON_HILLS_PEAKS, MEBiomeDataConfigs.ironHills, MEBiomeDataConfigs.ironhills,
                new BiomeColorsDTO(8761343, defaultFog, defaultWater, defaultWaterFog, 7187321, 6793328), CaveType.MOUNTAINS));
        addBiome(new BiomeData(MEBiomeKeys.IRON_HILLS_PLAINS, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.ironhills,
                new BiomeColorsDTO(hillySky, 12637183, defaultWater, defaultWaterFog, 7713657, 7580788)));
        addBiome(new BiomeData(MEBiomeKeys.NORTHERN_RHOVANION_FOREST, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.ironhills,
                new BiomeColorsDTO(9218275, 11978476, defaultWater, defaultWaterFog, 7120752, 6988907)));
        addBiome(new BiomeData(MEBiomeKeys.NORTHERN_RHOVANION_HILLS, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.ironhills,
                new BiomeColorsDTO(9218275, 11978476, defaultWater, defaultWaterFog, 6462822, 6987883)));
        addBiome(new BiomeData(MEBiomeKeys.IRON_FOOTHILLS, MEBiomeDataConfigs.ironHills, MEBiomeDataConfigs.ironhills,
                new BiomeColorsDTO(hillySky, 12637183, defaultWater, defaultWaterFog, 7253882, 7580788)));
        // ISENGARD
        addBiome(new BiomeData(MEBiomeKeys.ISENGARD, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, 5076423, defaultWaterFog, 5932619, 5867338)));
        addBiome(new BiomeData(MEBiomeKeys.ISENGARD_HILL, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, 5076423, defaultWaterFog, 5932619, 5867338)));
        // ITHILIEN
        addBiome(new BiomeData(MEBiomeKeys.ITHILIEN, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.gondorLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, defaultWater, defaultWaterFog, 5152072, 4889668)));
        addBiome(new BiomeData(MEBiomeKeys.ITHILIEN_GLADE, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.gondorLayers,
                new BiomeColorsDTO(7246036, 11518431, defaultWater, defaultWaterFog, 6401627, 5086791)));
        addBiome(new BiomeData(MEBiomeKeys.ITHILIEN_WASTES, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.gondorLayers,
                new BiomeColorsDTO(9085388, 10661827, 4944318, 723757, 5733716, 5537108)));
        addBiome(new BiomeData(MEBiomeKeys.ITHILIEN_WASTES_GLADE, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.gondorLayers,
                new BiomeColorsDTO(9611731, 11254223, 4944318, 723757, 6326621, 5537108)));
        // LAMEDON
        addBiome(new BiomeData(MEBiomeKeys.LAMEDON, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.gondorLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, defaultWater, defaultWaterFog, 9162899, 8043898)));
        addBiome(new BiomeData(MEBiomeKeys.LAMEDON_HILLS, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.gondorLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, defaultWater, defaultWaterFog, 9751707, 8370303)));
        // LEBENNIN
        addBiome(new BiomeData(MEBiomeKeys.LEBENNIN, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.gondorLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, defaultWater, defaultWaterFog, 5883985, 3248206)));
        addBiome(new BiomeData(MEBiomeKeys.LEBENNIN_HILLS, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.gondorLayers,
                new BiomeColorsDTO(hillySky, defaultFog, defaultWater, defaultWaterFog, 7194470, 4693601)));
        addBiome(new BiomeData(MEBiomeKeys.LEBENNIN_SHORES, MEBiomeDataConfigs.sandShores, MEBiomeDataConfigs.gondorLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, defaultShoreWater, defaultWaterFog, 5883985, 4239457)));
        // LINDON
        addBiome(new BiomeData(MEBiomeKeys.LINDON, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.limeStoneLayers,
                new BiomeColorsDTO(8827134, 12771327, defaultWater, defaultWaterFog, 7385448, 6593880), CaveType.ELVEN));
        addBiome(new BiomeData(MEBiomeKeys.LINDON_FOREST, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.limeStoneLayers,
                new BiomeColorsDTO(8827134, 12771327, defaultWater, defaultWaterFog, 6331736, 6067536), CaveType.ELVEN));
        addBiome(new BiomeData(MEBiomeKeys.LINDON_HIDDEN_BLOSSOM, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.limeStoneLayers,
                new BiomeColorsDTO(8827134, 12771327, defaultWater, defaultWaterFog, 7584106, 6593880), CaveType.ELVEN));
        addBiome(new BiomeData(MEBiomeKeys.LINDON_MEADOW, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.limeStoneLayers,
                new BiomeColorsDTO(8827134, 12771327, defaultWater, defaultWaterFog, 7584106, 6725722), CaveType.ELVEN));
        addBiome(new BiomeData(MEBiomeKeys.LINDON_SHORES, MEBiomeDataConfigs.gulfOfLhunShoresLayers, MEBiomeDataConfigs.limeStoneLayers,
                new BiomeColorsDTO(8827134, 12771327, defaultShoreWater, defaultWaterFog, 9090684, 8168815), CaveType.ELVEN));
        addBiome(new BiomeData(MEBiomeKeys.LINDON_SHORES_CLIFFS, MEBiomeDataConfigs.gulfOfLhunShoreCliffsLayers, MEBiomeDataConfigs.limeStoneLayers,
                new BiomeColorsDTO(8827134, 12771327, defaultShoreWater, defaultWaterFog, 9090684, 8168815), CaveType.ELVEN));
        // LONELY MOUNTAINS
        addBiome(new BiomeData(MEBiomeKeys.LONELY_MOUNTAIN_FOOTHILLS, MEBiomeDataConfigs.lonelyMountainBase, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(hillySky, 13031679, defaultWater, defaultWaterFog, 7321722, 6858353), CaveType.LONELY_MOUNTAIN));
        addBiome(new BiomeData(MEBiomeKeys.LONELY_MOUNTAIN, MEBiomeDataConfigs.lonelyMountainBase, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(hillySky, 13031679, defaultWater, defaultWaterFog, 7188600, 6529388), CaveType.LONELY_MOUNTAIN));
        addBiome(new BiomeData(MEBiomeKeys.LONELY_MOUNTAIN_BASE, MEBiomeDataConfigs.lonelyMountain, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(hillySky, 12637183, defaultWater, defaultWaterFog, 6927733, 6005862), CaveType.LONELY_MOUNTAIN));
        addBiome(new BiomeData(MEBiomeKeys.LONELY_MOUNTAIN_PEAKS, MEBiomeDataConfigs.lonelyMountainPeak, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(hillySky, 13031679, defaultWater, defaultWaterFog, 8106628, 6987890), CaveType.LONELY_MOUNTAIN));
        addBiome(new BiomeData(MEBiomeKeys.LONELY_MOUNTAIN_TAIGA, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(hillySky, 12637183, defaultWater, defaultWaterFog, 7253882, 7580788)));
        // LONG MARSHES
        addBiome(new BiomeData(MEBiomeKeys.LONG_MARSHES, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(8295660, 10993126, 6853265, 1919305, 5676922, 5282655)));
        // LOSSARNACH
        addBiome(new BiomeData(MEBiomeKeys.LOSSARNACH, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.gondorLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, defaultWater, defaultWaterFog, 8240485, 7909996)));
        addBiome(new BiomeData(MEBiomeKeys.LOSSARNACH_CHERRY_BLOSSOM, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.gondorLayers,
                new BiomeColorsDTO(8103167, 12638463, defaultWater, defaultWaterFog, 11983713, 11983713)));
        addBiome(new BiomeData(MEBiomeKeys.LOSSARNACH_VALLEY, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.gondorLayers,
                new BiomeColorsDTO(8103167, 12638463, defaultWater, defaultWaterFog, 9551961, 9355638)));
        addBiome(new BiomeData(MEBiomeKeys.LOSSARNACH_VALLEY_RED, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.gondorLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, defaultWater, defaultWaterFog, 12082206, 13977913)));
        addBiome(new BiomeData(MEBiomeKeys.LOSSARNACH_VALLEY_ORANGE, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.gondorLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, defaultWater, defaultWaterFog, 14647328, 15761188)));
        addBiome(new BiomeData(MEBiomeKeys.LOSSARNACH_VALLEY_YELLOW, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.gondorLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, defaultWater, defaultWaterFog, 15649303, 16768256)));
        addBiome(new BiomeData(MEBiomeKeys.LOSSARNACH_VALLEY_GREEN, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.gondorLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, defaultWater, defaultWaterFog, 11126589, 9289041)));
        // LOTHLORIEN
        addBiome(new BiomeData(MEBiomeKeys.LORIEN_EDGE, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.limeStoneLayers,
                new BiomeColorsDTO(defaultSky, 11455105, defaultWater, defaultWaterFog, 10601262, 6202980), CaveType.ELVEN));
        addBiome(new BiomeData(MEBiomeKeys.LOTHLORIEN, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.limeStoneLayers,
                new BiomeColorsDTO(defaultSky, 13748853, defaultWater, defaultWaterFog, 12961832, 6989412), CaveType.ELVEN));
        addBiome(new BiomeData(MEBiomeKeys.LOTHLORIEN_GLADE, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.limeStoneLayers,
                new BiomeColorsDTO(defaultSky, 13748853, defaultWater, defaultWaterFog, 14146100, 6989412), CaveType.ELVEN));
        addBiome(new BiomeData(MEBiomeKeys.LOTHLORIEN_BLOSSOM, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.limeStoneLayers,
                new BiomeColorsDTO(defaultSky, 13748853, defaultWater, defaultWaterFog, 10864680, 7387753), CaveType.ELVEN));
        // MINHIRIATH
        addBiome(new BiomeData(MEBiomeKeys.MINHIRIATH, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.limeStoneLayers,
                new BiomeColorsDTO(defaultSky, 12637429, 4615389, 658236, 8626526, 8231005)));
        addBiome(new BiomeData(MEBiomeKeys.MINHIRIATH_WHEAT_FIELD, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.limeStoneLayers,
                new BiomeColorsDTO(defaultSky, 12637429, 4615389, 658236, 0x96a15e, 0x9da862)));
        // MIRKWOOD
        addBiome(new BiomeData(MEBiomeKeys.MIRKWOOD, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.limeStoneLayers,
                new BiomeColorsDTO(6849446, 6780008, 7111535, 338483, 4151612, 3823415), CaveType.ELVEN));
        addBiome(new BiomeData(MEBiomeKeys.WEBBED_DARK_WOODS, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.limeStoneLayers,
                new BiomeColorsDTO(6124690, 6319201, 7111535, 338483, 4216382, 3823671), CaveType.ELVEN));
        addBiome(new BiomeData(MEBiomeKeys.MIRKWOOD_EDGE, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.limeStoneLayers,
                new BiomeColorsDTO(7578800, 7897724, 7111535, 338483, 4546876, 4284215), CaveType.ELVEN));
        addBiome(new BiomeData(MEBiomeKeys.MIRKWOOD_FOOTHILLS, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.limeStoneLayers,
                new BiomeColorsDTO(6849446, 6780008, 7111535, 338483, 4151612, 3823415), CaveType.MOUNTAINS));
        addBiome(new BiomeData(MEBiomeKeys.MIRKWOOD_MOUNTAINS_BASE, MEBiomeDataConfigs.mirkwoodMountains, MEBiomeDataConfigs.limeStoneLayers,
                new BiomeColorsDTO(6849446, 6780008, 7111535, 338483, 4151612, 3823415), CaveType.MOUNTAINS));
        addBiome(new BiomeData(MEBiomeKeys.MIRKWOOD_MOUNTAINS, MEBiomeDataConfigs.mirkwoodMountains, MEBiomeDataConfigs.limeStoneLayers,
                new BiomeColorsDTO(6849446, 6780008, 7111535, 338483, 4151612, 3823415), CaveType.MOUNTAINS));
        addBiome(new BiomeData(MEBiomeKeys.MIRKWOOD_MOUNTAINS_PEAKS, MEBiomeDataConfigs.mirkwoodMountainPeaks, MEBiomeDataConfigs.limeStoneLayers,
                new BiomeColorsDTO(7972607, 9873053, 7111535, 338483, 4678724, 4482114), CaveType.MOUNTAINS));
        addBiome(new BiomeData(MEBiomeKeys.DARK_MIRKWOOD, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.limeStoneLayers,
                new BiomeColorsDTO(5269900, 4806731, 4544130, 338483, 4151612, 3823415), CaveType.ELVEN));
        addBiome(new BiomeData(MEBiomeKeys.DARK_MIRKWOOD_EDGE, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.limeStoneLayers,
                new BiomeColorsDTO(5861275, 8293250, 4551554, 467756, 4083260, 3690038), CaveType.ELVEN));
        addBiome(new BiomeData(MEBiomeKeys.MIRKWOOD_SWAMP, MEBiomeDataConfigs.mud, MEBiomeDataConfigs.limeStoneLayers,
                new BiomeColorsDTO(6981536, 8821922, 7111535, 1458241, 4546876, 4284215), CaveType.ELVEN));
        addBiome(new BiomeData(MEBiomeKeys.NORTHERN_MIRKWOOD_SWAMP, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.limeStoneLayers,
                new BiomeColorsDTO(6849692, 8427421, 7111535, 1458241, 4546876, 4284215), CaveType.ELVEN));
        addBiome(new BiomeData(MEBiomeKeys.MIRKWOOD_MARSHES, MEBiomeDataConfigs.mud, MEBiomeDataConfigs.limeStoneLayers,
                new BiomeColorsDTO(6981536, 8821922, 7111535, 1458241, 4546876, 4284215), CaveType.ELVEN));
        addBiome(new BiomeData(MEBiomeKeys.NORTHERN_MIRKWOOD_MARSHES, MEBiomeDataConfigs.mud, MEBiomeDataConfigs.limeStoneLayers,
                new BiomeColorsDTO(6849692, 8427421, 7111535, 1458241, 4546876, 4284215), CaveType.ELVEN));
        // MISTY MOUNTAINS
        addBiome(new BiomeData(MEBiomeKeys.MISTY_MOUNTAINS_BASE, MEBiomeDataConfigs.mistyMountainsBase, MEBiomeDataConfigs.mistyMountainsLayers,
                new BiomeColorsDTO(hillySky, defaultFog, defaultWater, defaultWaterFog, 7777673, 7316862), CaveType.MISTIES));
        addBiome(new BiomeData(MEBiomeKeys.MISTY_MOUNTAINS, MEBiomeDataConfigs.mistyMountains, MEBiomeDataConfigs.mistyMountainsLayers,
                new BiomeColorsDTO(hillySky, defaultFog, defaultWater, defaultWaterFog, 7777673, 7316862), CaveType.MISTIES));
        addBiome(new BiomeData(MEBiomeKeys.MISTY_MOUNTAINS_PEAKS, MEBiomeDataConfigs.mistiesPeaks, MEBiomeDataConfigs.mistyMountainsLayers,
                new BiomeColorsDTO(hillySky, defaultFog, defaultWater, defaultWaterFog, 7777673, 7316862), CaveType.MISTIES));
        // MORDOR
        addBiome(new BiomeData(MEBiomeKeys.MORDOR, MEBiomeDataConfigs.mordor, MEBiomeDataConfigs.ashenStoneLayers,
                new BiomeColorsDTO(2695203, 1905947, 6450777, 1513734, 3550502, 2695966), CaveType.ASHEN));
        addBiome(new BiomeData(MEBiomeKeys.MORDOR_ASHEN_FOREST, MEBiomeDataConfigs.mordor, MEBiomeDataConfigs.ashenStoneLayers,
                new BiomeColorsDTO(2695203, 1905947, 6450777, 1513734, 3550502, 2695966), CaveType.ASHEN));
        addBiome(new BiomeData(MEBiomeKeys.MORDOR_HILL, MEBiomeDataConfigs.mordor, MEBiomeDataConfigs.ashenStoneLayers,
                new BiomeColorsDTO(2695203, 1905947, 6450777, 1513734, 3550502, 2695966), CaveType.ASHEN));
        addBiome(new BiomeData(MEBiomeKeys.ERED_LITHUI_BASE, MEBiomeDataConfigs.mordorMountains, MEBiomeDataConfigs.ashenStoneLayers,
                new BiomeColorsDTO(3747117, 2629407, 6450777, 1513734, 5129527, 3486247), CaveType.ASHEN));
        addBiome(new BiomeData(MEBiomeKeys.ERED_LITHUI, MEBiomeDataConfigs.mordorMountainsPeaks, MEBiomeDataConfigs.ashenStoneLayers,
                new BiomeColorsDTO(4142646, 3090215, 6450777, 1513734, 3550502, 2169880), CaveType.ASHEN));
        addBiome(new BiomeData(MEBiomeKeys.ERED_LITHUI_PEAKS, MEBiomeDataConfigs.mordorMountainsPeaks, MEBiomeDataConfigs.ashenStoneLayers,
                new BiomeColorsDTO(4142646, 3090215, 6450777, 1513734, 3550502, 2169880), CaveType.ASHEN));
        addBiome(new BiomeData(MEBiomeKeys.MORDOR_WASTES, MEBiomeDataConfigs.ashenDirt, MEBiomeDataConfigs.ashenStoneLayers,
                new BiomeColorsDTO(5460048, 4999240, 5860962, 731161, 6252369, 4735297), CaveType.ASHEN));
        // MORGUL VALE
        addBiome(new BiomeData(MEBiomeKeys.MORGUL_VALE, MEBiomeDataConfigs.mordorGrass, MEBiomeDataConfigs.ashenStoneLayers,
                new BiomeColorsDTO(4479570, 3690813, 3897457, 595232, 4545602, 4876362), CaveType.ASHEN));
        addBiome(new BiomeData(MEBiomeKeys.MORGUL_FOREST, MEBiomeDataConfigs.mordorGrass, MEBiomeDataConfigs.ashenStoneLayers,
                new BiomeColorsDTO(4479570, 3690813, 3897457, 595232, 4545602, 4876362), CaveType.ASHEN));
        addBiome(new BiomeData(MEBiomeKeys.MORGUL_RIVER, MEBiomeDataConfigs.ashenDirt, MEBiomeDataConfigs.ashenStoneLayers,
                new BiomeColorsDTO(4479570, 3690813, 3897457, 595232, 4545602, 4348994)));
        // MOUNT DOOM
        addBiome(new BiomeData(MEBiomeKeys.MOUNT_DOOM, MEBiomeDataConfigs.mordorMountainsPeaks, MEBiomeDataConfigs.ashenStoneLayers,
                new BiomeColorsDTO(4142646, 3090215, 6450777, 1513734, 3550502, 2169880), CaveType.ASHEN));
        addBiome(new BiomeData(MEBiomeKeys.MOUNT_DOOM_PIT, MEBiomeDataConfigs.mordorMountainsPeaks, MEBiomeDataConfigs.ashenStoneLayers,
                new BiomeColorsDTO(4142646, 3090215, 6450777, 1513734, 3550502, 2169880), CaveType.ASHEN));
        // MOUNT GUNDABAD
        addBiome(new BiomeData(MEBiomeKeys.MOUNT_GUNDABAD_BASE, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(8098794, 12701936, defaultWater, defaultWaterFog, 8823414, 9022583), CaveType.MISTIES));
        addBiome(new BiomeData(MEBiomeKeys.MOUNT_GUNDABAD, MEBiomeDataConfigs.mountGundabad, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(8098794, 12701936, defaultWater, defaultWaterFog, 8823414, 9022583), CaveType.MISTIES));
        addBiome(new BiomeData(MEBiomeKeys.MOUNT_GUNDABAD_PEAKS, MEBiomeDataConfigs.mountGundabadPeaks, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(8098794, 12701936, defaultWater, defaultWaterFog, 8823414, 9022583), CaveType.MISTIES));
        // NAN CURUNIR
        addBiome(new BiomeData(MEBiomeKeys.NAN_CURUNIR, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, 5076423, defaultWaterFog, 5406786, 4554563)));
        // NEN HITHOEL
        addBiome(new BiomeData(MEBiomeKeys.NEN_HITHOEL, MEBiomeDataConfigs.ocean, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(waterSky, 12638463, 6853316, 6853316, 10995507, 7181907)));
        addBiome(new BiomeData(MEBiomeKeys.NEN_HITHOEL_RAPIDS, MEBiomeDataConfigs.ocean, MEBiomeDataConfigs.stoneLayers,
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
        addBiome(new BiomeData(MEBiomeKeys.NORTHERN_WASTELANDS, MEBiomeDataConfigs.snowyPlains, MEBiomeDataConfigs.frozenLayers,
                new BiomeColorsDTO(hillySky, defaultFog, defaultWater, defaultWaterFog, 5932658, 7973008), CaveType.FOROD));
        // NURN
        addBiome(new BiomeData(MEBiomeKeys.NURN, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(8954077, 11781331, 5860970, 1321760, 7443043, 6982236)));
        addBiome(new BiomeData(MEBiomeKeys.NURN_FOREST, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(8822986, 11057090, 5860970, 1321760, 6916445, 6784858)));
        addBiome(new BiomeData(MEBiomeKeys.NURN_HILL, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(9283042, 11978194, 5860970, 1321760, 7968366, 7310690)));
        addBiome(new BiomeData(MEBiomeKeys.NURN_EDGE, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(8821450, 11517913, 5202783, 1321247, 6511435, 7960147)));
        addBiome(new BiomeData(MEBiomeKeys.NURN_EDGE_WOODS, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(8360637, 10793931, 5202783, 1321247, 6513227, 7829843)));
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
        addBiome(new BiomeData(MEBiomeKeys.OLD_ANGMAR_FROZEN_HILL, MEBiomeDataConfigs.snowyPlains, MEBiomeDataConfigs.stoneLayers,
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
                new BiomeColorsDTO(defaultSky, defaultFog, defaultWater, defaultWaterFog, 0x7ea150, 8169054)));
        addBiome(new BiomeData(MEBiomeKeys.PELENNOR_WHEAT_FIELD, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.gondorLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, defaultWater, defaultWaterFog, 0x94a150, 8169054)));
        // RHUN
        addBiome(new BiomeData(MEBiomeKeys.RHUN, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(8041727, 12773631, 4618980, defaultWaterFog, 10995507, 8165203)));
        addBiome(new BiomeData(MEBiomeKeys.RHUN_FIELD, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(8041727, 12773631, 4618980, defaultWaterFog, 12439605, 8427347)));
        addBiome(new BiomeData(MEBiomeKeys.RHUN_FOREST, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(7316455, 12049136, 4618980, defaultWaterFog, 10007856, 7901520)));
        addBiome(new BiomeData(MEBiomeKeys.RHUN_HIDDEN_BLOSSOM, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(8041727, 12773631, 4618980, defaultWaterFog, 11456565, 8427347)));
        // RHOVANION
        addBiome(new BiomeData(MEBiomeKeys.EASTERN_RHOVANION, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(7571933, defaultFog, 12440052, 591663, 8627523, 7052347)));
        addBiome(new BiomeData(MEBiomeKeys.EASTERN_RHOVANION_FOREST, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(7176133, 10926035, 12440052, 591663, 8627523, 6721596)));
        addBiome(new BiomeData(MEBiomeKeys.SOUTHEAST_RHOVANION, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(8954077, 11781343, 4814544, 460593, 7378251, 6982236)));
        addBiome(new BiomeData(MEBiomeKeys.SOUTHEAST_RHOVANION_FIELD, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(8954077, 11781343, 4814544, 460593, 7708747, 7839335)));
        // ROHAN
        addBiome(new BiomeData(MEBiomeKeys.ROHAN, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, defaultWater, defaultWaterFog, 12569978, 9677397)));
        addBiome(new BiomeData(MEBiomeKeys.ROHAN_FOREST, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, defaultWater, defaultWaterFog, 11321968, 8887375)));
        addBiome(new BiomeData(MEBiomeKeys.ROHAN_FIELD, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(hillySky, defaultFog, defaultWater, defaultWaterFog, 11913598, 9873760)));
        // SARN GEBIR SHORES
        addBiome(new BiomeData(MEBiomeKeys.SARN_GEBIR_SHORES, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.gondorLayers,
                new BiomeColorsDTO(6980296, 10402016, defaultWater, defaultWaterFog, 9666387, 9731143)));
        addBiome(new BiomeData(MEBiomeKeys.SARN_GEBIR_WILDLANDS, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(6980296, 10402016, defaultWater, defaultWaterFog, 9666387, 9731143)));
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
        addBiome(new BiomeData(MEBiomeKeys.EPHEL_DUATH_BASE, MEBiomeDataConfigs.mordorMountains, MEBiomeDataConfigs.ashenStoneLayers,
                new BiomeColorsDTO(3747117, 2629407, 6450777, 1513734, 5129527, 3486247), CaveType.ASHEN));
        addBiome(new BiomeData(MEBiomeKeys.EPHEL_DUATH, MEBiomeDataConfigs.mordorMountainsPeaks, MEBiomeDataConfigs.ashenStoneLayers,
                new BiomeColorsDTO(4142646, 3090215, 6450777, 1513734, 3550502, 2169880), CaveType.ASHEN));
        addBiome(new BiomeData(MEBiomeKeys.EPHEL_DUATH_PEAKS, MEBiomeDataConfigs.mordorMountainsPeaks, MEBiomeDataConfigs.ashenStoneLayers,
                new BiomeColorsDTO(4142646, 3090215, 6450777, 1513734, 3550502, 2169880), CaveType.ASHEN));
        // SOUTHERN FOROCHEL
        addBiome(new BiomeData(MEBiomeKeys.SOUTHERN_FOROCHEL, MEBiomeDataConfigs.snowyPlains, MEBiomeDataConfigs.frozenLayers,
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
        addBiome(new BiomeData(MEBiomeKeys.THE_WOLD_WHEAT_FIELD, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, defaultWater, defaultWaterFog, 0xaab777, 0xc5d48a)));
        // TOLFALAS
        addBiome(new BiomeData(MEBiomeKeys.TOLFALAS, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, defaultWater, defaultWaterFog, 10927716, 9615182)));
        // TOROGWAITH
        addBiome(new BiomeData(MEBiomeKeys.TOROGWAITH, MEBiomeDataConfigs.mordor, MEBiomeDataConfigs.ashenStoneLayers,
                new BiomeColorsDTO(3289130, 2827810, 6255709, 1252359, 4142897, 3156775), CaveType.ASHEN));
        // TROLLSHAWS
        addBiome(new BiomeData(MEBiomeKeys.TROLLSHAWS, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(6980296, 10402016, 4618461, defaultWaterFog, 9666387, 9731143)));
        // UDUN
        addBiome(new BiomeData(MEBiomeKeys.UDUN, MEBiomeDataConfigs.mordor, MEBiomeDataConfigs.ashenStoneLayers,
                new BiomeColorsDTO(4273461, 2826529, 6450777, 1513734, 3549478, 2695966), CaveType.ASHEN));
        // UMBAR
        addBiome(new BiomeData(MEBiomeKeys.UMBAR, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(7254527, 12638463, 5212644, 333363, 11059059, 9284946), CaveType.HARAD));
        addBiome(new BiomeData(MEBiomeKeys.UMBAR_WOODS, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(7317996, 12308721, 5212644, 333363, 9876841, 8955470), CaveType.HARAD));
        // WEBBED WOODS
        addBiome(new BiomeData(MEBiomeKeys.WEBBED_WOODS, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(6385822, 5198943, 4544130, 338483, 4478786, 4017979)));
        // WITHERED HEATH
        addBiome(new BiomeData(MEBiomeKeys.WITHERED_HEATH, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(9479110, 11780310, 5141697, 460593, 8881498, 10328434)));
        // WHITE MOUNTAINS
        addBiome(new BiomeData(MEBiomeKeys.WHITE_MOUNTAINS_BASE, MEBiomeDataConfigs.whiteMountains, MEBiomeDataConfigs.gondorLayers,
                new BiomeColorsDTO(hillySky, 12638463, defaultWater, defaultWaterFog, 7185769, 6857066), CaveType.MOUNTAINS));
        addBiome(new BiomeData(MEBiomeKeys.WHITE_MOUNTAINS, MEBiomeDataConfigs.whiteMountains, MEBiomeDataConfigs.gondorLayers,
                new BiomeColorsDTO(hillySky, 12638463, defaultWater, defaultWaterFog, 7183466, 7513204), CaveType.MOUNTAINS));
        addBiome(new BiomeData(MEBiomeKeys.WHITE_MOUNTAINS_PEAKS, MEBiomeDataConfigs.whitePeaks, MEBiomeDataConfigs.gondorLayers,
                new BiomeColorsDTO(hillySky, 12638463, defaultWater, defaultWaterFog, 7185769, 6857066), CaveType.MOUNTAINS));
        // WOODLANDS
        addBiome(new BiomeData(MEBiomeKeys.WOODLAND_REALM, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.limeStoneLayers,
                new BiomeColorsDTO(8497918, 10666932, 4492967, 471355, 3960119, 3370029), CaveType.ELVEN));
        addBiome(new BiomeData(MEBiomeKeys.WOODLAND_FOOTHILLS, MEBiomeDataConfigs.limeStoneHills, MEBiomeDataConfigs.limeStoneLayers,
                new BiomeColorsDTO(6849446, 6780008, 7111535, 338483, 4420926, 3567152), CaveType.ELVEN));
        addBiome(new BiomeData(MEBiomeKeys.WOODLAND_GLADE, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.limeStoneLayers,
                new BiomeColorsDTO(8826622, 8293248, 4492967, 471355, 5408076, 4950339), CaveType.ELVEN));
        addBiome(new BiomeData(MEBiomeKeys.WOODLAND_HILLS, MEBiomeDataConfigs.limeStoneHills, MEBiomeDataConfigs.limeStoneLayers,
                new BiomeColorsDTO(6849446, 6780008, 7111535, 338483, 4748611, 4093495), CaveType.ELVEN));
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
       addBiome(new BiomeData(MEBiomeKeys.MANGROVE_POND,MEBiomeDataConfigs.pond, MEBiomeDataConfigs.stoneLayers,
               new BiomeColorsDTO(waterSky, defaultFog, 5141154, 331315, 7583083, 6592339)));
   }

    private static void loadGenericRivers(){
        addBiome(new BiomeData(MEBiomeKeys.MIRKWOOD_RIVER, MEBiomeDataConfigs.river, MEBiomeDataConfigs.stoneLayers,
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
        addBiome(new BiomeData(MEBiomeKeys.LONG_LAKE_SHORES, MEBiomeDataConfigs.ocean, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(waterSky, defaultFog, 4352228, 525619, 7576434, 6588506)));
        addBiome(new BiomeData(MEBiomeKeys.LONG_LAKE, MEBiomeDataConfigs.ocean, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(waterSky, defaultFog, 4352228, 525619, 7576434, 6588506)));
        addBiome(new BiomeData(MEBiomeKeys.SEA_OF_RHUN, MEBiomeDataConfigs.ocean, MEBiomeDataConfigs.stoneLayers,
                new BiomeColorsDTO(waterSky, 12638463, 4159204, defaultWaterFog, 10995507, 7181907)));
   }
}
