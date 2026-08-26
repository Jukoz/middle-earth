package net.sevenstars.middleearth.world.biomes.surface;

import net.sevenstars.middleearth.world.biomes.BiomeColorsDTO;
import net.sevenstars.middleearth.world.biomes.BiomeDataConfigsME;
import net.sevenstars.middleearth.world.biomes.BiomeKeyRegistryME;
import net.sevenstars.middleearth.world.biomes.caves.CaveType;
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
        addBiome(new BiomeData(BiomeKeyRegistryME.ANDUIN_VALES, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, defaultWater, defaultWaterFog, 8703593, 6924625)));
        addBiome(new BiomeData(BiomeKeyRegistryME.ANDUIN_VALES_FOREST, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, defaultWater, defaultWaterFog, 7714397, 6463563)));
        addBiome(new BiomeData(BiomeKeyRegistryME.DARK_ANDUIN_VALES, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(7246271, 8951443, 4354703, 1128527, 8032632, 7901046)));
        // ANORIEN
        addBiome(new BiomeData(BiomeKeyRegistryME.ANORIEN, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.gondorLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, defaultWater, defaultWaterFog, 8242025, 6662221)));
        addBiome(new BiomeData(BiomeKeyRegistryME.ANORIEN_RIVERSIDE, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.gondorLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, defaultWater, defaultWaterFog, 8636272, 6662221)));
        addBiome(new BiomeData(BiomeKeyRegistryME.ANORIEN_FOOTHILLS, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.gondorLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, defaultWater, defaultWaterFog, 8636272, 6662221)));
        // BARROW_DOWNS
        addBiome(new BiomeData(BiomeKeyRegistryME.BARROW_DOWNS, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, defaultWater, defaultWaterFog, 8628873, 7772283)));
        // BELERIAND
        addBiome(new BiomeData(BiomeKeyRegistryME.BELERIAND_ISLAND, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(defaultSky, 12637429, defaultOceanWater, defaultOceanWaterFog, 6466659, 5740626)));
        // BELFALAS
        addBiome(new BiomeData(BiomeKeyRegistryME.BELFALAS, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.gondorLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, defaultWater, defaultWaterFog, 5951101, 5485154)));
        addBiome(new BiomeData(BiomeKeyRegistryME.BELFALAS_FOREST, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.gondorLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, defaultWater, defaultWaterFog, 5291633, 5221213)));
        addBiome(new BiomeData(BiomeKeyRegistryME.BELFALAS_BEACH, BiomeDataConfigsME.whiteSandShores, BiomeDataConfigsME.gondorLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, defaultShoreWater, defaultWaterFog, 7323271, 6665073)));
        addBiome(new BiomeData(BiomeKeyRegistryME.BELFALAS_HILLS, BiomeDataConfigsME.whiteMountains, BiomeDataConfigsME.gondorLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, defaultWater, defaultWaterFog, 6011255, 5614178)));
        // BLACKROOT
        addBiome(new BiomeData(BiomeKeyRegistryME.BLACKROOT_VALE, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.gondorLayers,
                new BiomeColorsDTO(8036582, 12176100, 5076939, defaultWaterFog, 6400098, 6069598)));
        addBiome(new BiomeData(BiomeKeyRegistryME.BLACKROOT_FOREST, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.gondorLayers,
                new BiomeColorsDTO(7575257, 11255256, 5076939, defaultWaterFog, 5610326, 5543511)));
        // BLUE MOUNTAINS
        addBiome(new BiomeData(BiomeKeyRegistryME.BLUE_MOUNTAINS, BiomeDataConfigsME.blueMountains, BiomeDataConfigsME.gonluinLayers,
                new BiomeColorsDTO(7905261, 10803198, defaultWater, defaultWaterFog, 0x6EAD84, 6652524), CaveType.MOUNTAINS));
        addBiome(new BiomeData(BiomeKeyRegistryME.BLUE_MOUNTAINS_FOOTHILLS, BiomeDataConfigsME.blueMountainsBase, BiomeDataConfigsME.gonluinLayers,
                new BiomeColorsDTO(7905261, 10803198, defaultWater, defaultWaterFog, 0x6EAD84, 6652524), CaveType.MOUNTAINS));
        addBiome(new BiomeData(BiomeKeyRegistryME.BLUE_MOUNTAINS_BASE, BiomeDataConfigsME.blueMountainsBase, BiomeDataConfigsME.gonluinLayers,
                new BiomeColorsDTO(7905261, 10803198, defaultWater, defaultWaterFog, 0x6EAD84, 6652524), CaveType.MOUNTAINS));
        addBiome(new BiomeData(BiomeKeyRegistryME.BLUE_MOUNTAINS_HIGH_LANDS, BiomeDataConfigsME.blueMountainHighLands, BiomeDataConfigsME.gonluinLayers,
                new BiomeColorsDTO(7905261, 10803198, defaultWater, defaultWaterFog, 0x6EAD84, 6652524), CaveType.MOUNTAINS));
        addBiome(new BiomeData(BiomeKeyRegistryME.BLUE_MOUNTAINS_PEAKS, BiomeDataConfigsME.blueMountainsPeaks, BiomeDataConfigsME.gonluinLayers,
                new BiomeColorsDTO(7905261, 10803198, defaultWater, defaultWaterFog, 0x6EAD84, 6652524), CaveType.MOUNTAINS));
        addBiome(new BiomeData(BiomeKeyRegistryME.BLUE_MOUNTAINS_WOODS, BiomeDataConfigsME.blueMountainsBase, BiomeDataConfigsME.gonluinLayers,
                new BiomeColorsDTO(7905261, 10803198, defaultWater, defaultWaterFog, 0x6EAD84, 6652524), CaveType.MOUNTAINS));
        // BROWN LANDS
        addBiome(new BiomeData(BiomeKeyRegistryME.BROWN_LANDS, BiomeDataConfigsME.ashenDirt, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(5460048, 4999240, 5860962, 731161, 6252369, 4735297)));
        // CARADHRAS
        addBiome(new BiomeData(BiomeKeyRegistryME.CARADHRAS_BASE, BiomeDataConfigsME.caradhras, BiomeDataConfigsME.mistyMountainsLayers,
                new BiomeColorsDTO(hillySky, defaultFog, defaultWater, defaultWaterFog, 7777673, 7316862), CaveType.MISTIES));
        addBiome(new BiomeData(BiomeKeyRegistryME.CARADHRAS, BiomeDataConfigsME.caradhras, BiomeDataConfigsME.mistyMountainsLayers,
                new BiomeColorsDTO(hillySky, defaultFog, defaultWater, defaultWaterFog, 7777673, 7316862), CaveType.MISTIES));
        addBiome(new BiomeData(BiomeKeyRegistryME.CARADHRAS_PEAKS, BiomeDataConfigsME.caradhrasPeaks, BiomeDataConfigsME.mistyMountainsLayers,
                new BiomeColorsDTO(hillySky, defaultFog, defaultWater, defaultWaterFog, 7777673, 7316862), CaveType.MISTIES));
        // CELEBDIL
        addBiome(new BiomeData(BiomeKeyRegistryME.CELEBDIL_BASE, BiomeDataConfigsME.celebdil, BiomeDataConfigsME.mistyMountainsLayers,
                new BiomeColorsDTO(hillySky, defaultFog, defaultWater, defaultWaterFog, 7777673, 7316862), CaveType.MISTIES));
        addBiome(new BiomeData(BiomeKeyRegistryME.CELEBDIL, BiomeDataConfigsME.celebdil, BiomeDataConfigsME.mistyMountainsLayers,
                new BiomeColorsDTO(hillySky, defaultFog, defaultWater, defaultWaterFog, 7777673, 7316862), CaveType.MISTIES));
        addBiome(new BiomeData(BiomeKeyRegistryME.CELEBDIL_PEAKS, BiomeDataConfigsME.celebdilPeaks, BiomeDataConfigsME.mistyMountainsLayers,
                new BiomeColorsDTO(hillySky, defaultFog, defaultWater, defaultWaterFog, 7777673, 7316862), CaveType.MISTIES));
        // CORSAIR COASTS
        addBiome(new BiomeData(BiomeKeyRegistryME.CORSAIR_COASTS, BiomeDataConfigsME.corsairCoasts, BiomeDataConfigsME.sandstoneTravertineLayers,
                new BiomeColorsDTO(nearHaradSky, defaultFog, 5212644, 333363, 12107900, 10860366), CaveType.HARAD));
        // DAGORLAD
        addBiome(new BiomeData(BiomeKeyRegistryME.DAGORLAD, BiomeDataConfigsME.mordorGrass, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(7239328, 7632257, 4874882, 992318, 6971996, 8092011)));
        // DALE
        addBiome(new BiomeData(BiomeKeyRegistryME.DALE, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(8230911, 12178175, defaultWater, defaultWaterFog, 6400105, 8703593)));
        addBiome(new BiomeData(BiomeKeyRegistryME.DALE_FOREST, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(8230911, 12178175, defaultWater, defaultWaterFog, 6393176, 4820272)));
        addBiome(new BiomeData(BiomeKeyRegistryME.DALE_MEADOW, BiomeDataConfigsME.stoneHills, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(8230911, 12178175, defaultWater, defaultWaterFog, 6400105, 8703593)));
        addBiome(new BiomeData(BiomeKeyRegistryME.DALE_CITY, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(8230911, 12178175, defaultWater, defaultWaterFog, 6400105, 8703593)));
        addBiome(new BiomeData(BiomeKeyRegistryME.DALE_RIVERSIDE, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(hillySky, 12637183, defaultWater, defaultWaterFog, 7253882, 7580788)));
        // DEAD MARSHES
        addBiome(new BiomeData(BiomeKeyRegistryME.DEAD_MARSHES, BiomeDataConfigsME.peatPlains, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(11908531, 7108218, 3289373, 198924, 6115374, 5794902)));
        addBiome(new BiomeData(BiomeKeyRegistryME.DEAD_MARSHES_WATER, BiomeDataConfigsME.ashenDirt, BiomeDataConfigsME.ashenStoneLayers,
                new BiomeColorsDTO(11908531, 7108218, 3289373, 198924, 6115374, 5794902)));
        // DESOLATED LANDS
        addBiome(new BiomeData(BiomeKeyRegistryME.DESOLATED_LANDS, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.ashenStoneLayers,
                new BiomeColorsDTO(8097212, 10661315, 5206453, 460588, 7572570, 7111523)));
        // DOL GOLDUR
        addBiome(new BiomeData(BiomeKeyRegistryME.DOL_GULDUR, BiomeDataConfigsME.dolGuldur, BiomeDataConfigsME.stoneGabbroLayers,
                new BiomeColorsDTO(3684976, 5065045, 2503248, 462892, 3554356, 3424049)));
        addBiome(new BiomeData(BiomeKeyRegistryME.DOL_GULDUR_HILL, BiomeDataConfigsME.dolGuldur, BiomeDataConfigsME.stoneGabbroLayers,
                new BiomeColorsDTO(3684976, 5065045, 2503248, 462892, 3554356, 3424049)));
        // DORWINION
        addBiome(new BiomeData(BiomeKeyRegistryME.DORWINION, BiomeDataConfigsME.chalkPlains, BiomeDataConfigsME.chalkLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, defaultWater, defaultWaterFog, 10538040, 8298071), CaveType.ELVEN));
        addBiome(new BiomeData(BiomeKeyRegistryME.DORWINION_LAVENDER_FIELD, BiomeDataConfigsME.chalkPlains, BiomeDataConfigsME.limeStoneLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, defaultWater, defaultWaterFog, 9357367, 8298071), CaveType.ELVEN));
        addBiome(new BiomeData(BiomeKeyRegistryME.DORWINION_HILLS, BiomeDataConfigsME.chalkPlains, BiomeDataConfigsME.chalkLayers,
                new BiomeColorsDTO(hillySky, defaultFog, defaultWater, defaultWaterFog, 10538040, 8298071), CaveType.ELVEN));
        // DUNLAND
        addBiome(new BiomeData(BiomeKeyRegistryME.DUNLAND_FOOTHILLS, BiomeDataConfigsME.dunland, BiomeDataConfigsME.stoneGabbroLayers,
                new BiomeColorsDTO(7508201, 10863086, defaultWater, defaultWaterFog, 6722387, 6198343)));
        addBiome(new BiomeData(BiomeKeyRegistryME.DUNLAND_HILLS, BiomeDataConfigsME.dunland, BiomeDataConfigsME.stoneGabbroLayers,
                new BiomeColorsDTO(7508201, 12964294, defaultWater, defaultWaterFog, 7116123, 6657105)));
        addBiome(new BiomeData(BiomeKeyRegistryME.DUNLAND, BiomeDataConfigsME.dunland, BiomeDataConfigsME.stoneGabbroLayers,
                new BiomeColorsDTO(7508201, 12964294, defaultWater, defaultWaterFog, 6722387, 6198343)));
        addBiome(new BiomeData(BiomeKeyRegistryME.NORTHERN_DUNLAND_GLADE, BiomeDataConfigsME.dunland, BiomeDataConfigsME.stoneGabbroLayers,
                new BiomeColorsDTO(7903218, 13753556, defaultWater, defaultWaterFog, 7250265, 6659405)));

        addBiome(new BiomeData(BiomeKeyRegistryME.DRUWAITH_IAUR, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(7508201, 10863086, defaultWater, defaultWaterFog, 8302697, 7252827)));
        // EAST BIGHT
        addBiome(new BiomeData(BiomeKeyRegistryME.EAST_BIGHT, BiomeDataConfigsME.loamPlains, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(7571933, defaultFog, 12440052, 591663, 10864741, 8894805)));
        // EMYN MUIL
        addBiome(new BiomeData(BiomeKeyRegistryME.EMYN_MUIL_POND, BiomeDataConfigsME.emynMuil, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(6980296, 10402016, 3100271, 597791, 4412998, 3359013)));
        addBiome(new BiomeData(BiomeKeyRegistryME.EMYN_MUIL, BiomeDataConfigsME.emynMuil, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(6980296, 10402016, 4618461, defaultWaterFog, 9666387, 9731143)));
        addBiome(new BiomeData(BiomeKeyRegistryME.EMYN_MUIL_CLIFFS, BiomeDataConfigsME.emynMuil, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(6980296, 10402016, 4618461, defaultWaterFog, 9666387, 9731143)));
        addBiome(new BiomeData(BiomeKeyRegistryME.EMYN_MUIL_PEAKS, BiomeDataConfigsME.emynMuil, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(6980296, 10402016, 4618461, defaultWaterFog, 9666387, 9731143)));
        // ENEDWAITH
        addBiome(new BiomeData(BiomeKeyRegistryME.ENEDWAITH, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(7181795, 10731509, 4618461, defaultWaterFog, 8761449, 7842644)));
        addBiome(new BiomeData(BiomeKeyRegistryME.ENEDWAITH_FIELD, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(7181795, 10731509, 4618461, defaultWaterFog, 9025898, 8106838)));
        addBiome(new BiomeData(BiomeKeyRegistryME.ENEDWAITH_WHEAT_FIELD, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(7181795, 10731509, 4618461, defaultWaterFog, 0x92a865, 0xaac476)));

        // EREGION
        addBiome(new BiomeData(BiomeKeyRegistryME.EREGION, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.limeStoneLayers,
                new BiomeColorsDTO(7908853, 11393279, 5077445, 331050, 6858575, 6198343), CaveType.ELVEN));
        addBiome(new BiomeData(BiomeKeyRegistryME.EREGION_FOREST, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.limeStoneLayers,
                new BiomeColorsDTO(7709409, 11062768, 5077445, 331050, 6331720, 6066757), CaveType.ELVEN));
        addBiome(new BiomeData(BiomeKeyRegistryME.EREGION_GLADE, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.limeStoneLayers,
                new BiomeColorsDTO(8500733, 11787007, 5077445, 331050, 7648091, 6659660), CaveType.ELVEN));
        // ETHIR ANDUIN
        addBiome(new BiomeData(BiomeKeyRegistryME.ETHIR_ANDUIN, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, defaultWater, defaultWaterFog, 6400102, 6137428)));
        addBiome(new BiomeData(BiomeKeyRegistryME.ETHIR_ANDUIN_RIVER_DELTA, BiomeDataConfigsME.river, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, defaultWater, defaultWaterFog, 6400102, 6137428)));

        // FANGORN
        addBiome(new BiomeData(BiomeKeyRegistryME.FANGORN, BiomeDataConfigsME.loamPlains, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(7972607, defaultFog, 4293787, 338483, 3559947, 1789719)));
        addBiome(new BiomeData(BiomeKeyRegistryME.FANGORN_FOOTHILLS, BiomeDataConfigsME.loamPlains, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(7972607, defaultFog, 4293787, 338483, 5533992, 3567922)));
        // FANUIDHOL
        addBiome(new BiomeData(BiomeKeyRegistryME.FANUIDHOL_BASE, BiomeDataConfigsME.fanuidhol, BiomeDataConfigsME.mistyMountainsLayers,
                new BiomeColorsDTO(hillySky, defaultFog, defaultWater, defaultWaterFog, 7777673, 7316862)));
        addBiome(new BiomeData(BiomeKeyRegistryME.FANUIDHOL, BiomeDataConfigsME.fanuidhol, BiomeDataConfigsME.mistyMountainsLayers,
                new BiomeColorsDTO(hillySky, defaultFog, defaultWater, defaultWaterFog, 7777673, 7316862)));
        addBiome(new BiomeData(BiomeKeyRegistryME.FANUIDHOL_PEAKS, BiomeDataConfigsME.fanuidholPeaks, BiomeDataConfigsME.mistyMountainsLayers,
                new BiomeColorsDTO(hillySky, defaultFog, defaultWater, defaultWaterFog, 7777673, 7316862)));
        // FORODWAITH
        addBiome(new BiomeData(BiomeKeyRegistryME.FORODWAITH, BiomeDataConfigsME.forodwaith, BiomeDataConfigsME.frozenLayers,
                new BiomeColorsDTO(8364543, 10335206, 3823818, 66852, 3494723, 4478280), CaveType.FOROD));
        // GONDOR
        addBiome(new BiomeData(BiomeKeyRegistryME.GONDOR, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.gondorLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, defaultWater, defaultWaterFog, 7582562, 6592327)));
        addBiome(new BiomeData(BiomeKeyRegistryME.GONDOR_FOREST, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.gondorLayers,
                new BiomeColorsDTO(7510756, 12308204, defaultWater, defaultWaterFog, 7186525, 6658376)));
        addBiome(new BiomeData(BiomeKeyRegistryME.GONDOR_HILL, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.gondorLayers,
                new BiomeColorsDTO(8432895, 12900082, defaultWater, defaultWaterFog, 8502640, 7381842)));
        // GORGOROTH
        addBiome(new BiomeData(BiomeKeyRegistryME.GORGOROTH, BiomeDataConfigsME.mordor, BiomeDataConfigsME.ashenStoneLayers,
                new BiomeColorsDTO(2564128, 1709079, 4869697, 1513734, 3156775, 2169880), CaveType.ASHEN));
        addBiome(new BiomeData(BiomeKeyRegistryME.GORGOROTH_ASHEN_WOODS, BiomeDataConfigsME.mordor, BiomeDataConfigsME.ashenStoneLayers,
                new BiomeColorsDTO(2958629, 2432545, 4869697, 1513734, 3156775, 2169880), CaveType.ASHEN));
        addBiome(new BiomeData(BiomeKeyRegistryME.GORGOROTH_DELTA, BiomeDataConfigsME.mordor, BiomeDataConfigsME.ashenStoneLayers,
                new BiomeColorsDTO(2762018, 2695711, 4869697, 1513734, 3156775, 2169880), CaveType.ASHEN));
        // GREY MOUNTAINS
        addBiome(new BiomeData(BiomeKeyRegistryME.GREY_PLAINS, BiomeDataConfigsME.gravelPlains, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(12171705, 8484720, 7768221, 5597568, 7832178, 8883574)));
        addBiome(new BiomeData(BiomeKeyRegistryME.GREY_ASHEN_WOODS, BiomeDataConfigsME.gravelPlains, BiomeDataConfigsME.greyMountainsLayers,
                new BiomeColorsDTO(11973812, 8484720, 7768221, 5597568, 7438443, 7642221)));
        addBiome(new BiomeData(BiomeKeyRegistryME.GREY_FOREST, BiomeDataConfigsME.siltPlains, BiomeDataConfigsME.greyMountainsLayers,
                new BiomeColorsDTO(11973812, 8484720, 7768221, 5597568, 7438443, 7642221)));
        addBiome(new BiomeData(BiomeKeyRegistryME.GREY_MOUNTAINS_BASE, BiomeDataConfigsME.greyMountains, BiomeDataConfigsME.greyMountainsLayers,
                new BiomeColorsDTO(12171705, 8484720, 7768221, 5597568, 7832178, 8883574), CaveType.MOUNTAINS));
        addBiome(new BiomeData(BiomeKeyRegistryME.GREY_MOUNTAINS, BiomeDataConfigsME.greyMountains, BiomeDataConfigsME.greyMountainsLayers,
                new BiomeColorsDTO(12171705, 8484720, 7768221, 5597568, 7832178, 8883574), CaveType.MOUNTAINS));
        addBiome(new BiomeData(BiomeKeyRegistryME.GREY_MOUNTAINS_PEAKS, BiomeDataConfigsME.greyMountainPeaks, BiomeDataConfigsME.greyMountainsLayers,
                new BiomeColorsDTO(12171705, 8484720, 7768221, 5597568, 7832178, 8883574), CaveType.MOUNTAINS));
        // GUNDABAD
        addBiome(new BiomeData(BiomeKeyRegistryME.GUNDABAD_PLAINS, BiomeDataConfigsME.coarseLoam, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(11315633, 10855857, 7768221, 5597568, 8036220, 7511410)));
        addBiome(new BiomeData(BiomeKeyRegistryME.GUNDABAD_WOODS, BiomeDataConfigsME.coarseLoam, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(11315633, 10855857, 7768221, 5597568, 8036220, 7511410)));
        // HARAD
        addBiome(new BiomeData(BiomeKeyRegistryME.HARAD, BiomeDataConfigsME.nearHarad, BiomeDataConfigsME.sandstoneTravertineLayers,
                new BiomeColorsDTO(nearHaradSky, nearHaradSkyFog, 5407446, 1120828, 12301405, 13356379), CaveType.HARAD));
        addBiome(new BiomeData(BiomeKeyRegistryME.HARAD_DESERT, BiomeDataConfigsME.harad, BiomeDataConfigsME.sandstoneLayers,
                new BiomeColorsDTO(nearHaradSky, nearHaradSkyFog, 5407446, 1120828, 13419633, 9615182), CaveType.HARAD));
        addBiome(new BiomeData(BiomeKeyRegistryME.HARAD_WOODS, BiomeDataConfigsME.nearHarad, BiomeDataConfigsME.sandstoneTravertineLayers,
                new BiomeColorsDTO(nearHaradSky, nearHaradSkyFog, 5407446, 1120828, 11515226, 12240727), CaveType.HARAD));
        addBiome(new BiomeData(BiomeKeyRegistryME.HARONDOR, BiomeDataConfigsME.nearHarad, BiomeDataConfigsME.sandstoneTravertineLayers,
                new BiomeColorsDTO(nearHaradSky, nearHaradSkyFog, 5406149, 1120828, 12305028, 10860366), CaveType.HARAD));
        // HIGH MOOR
        addBiome(new BiomeData(BiomeKeyRegistryME.HIGH_MOOR, BiomeDataConfigsME.chalkPlains, BiomeDataConfigsME.chalkLayers,
                new BiomeColorsDTO(9090047, 13426943, defaultWater, defaultWaterFog, 8630141, 8169079), CaveType.ELVEN));
        addBiome(new BiomeData(BiomeKeyRegistryME.HIGH_MOOR_VALE, BiomeDataConfigsME.chalkPlains, BiomeDataConfigsME.chalkLayers,
                new BiomeColorsDTO(8695029, 12637426, defaultWater, defaultWaterFog, 8630141, 8169079), CaveType.ELVEN));
        addBiome(new BiomeData(BiomeKeyRegistryME.HIGH_MOOR_HILLS, BiomeDataConfigsME.chalkPlains, BiomeDataConfigsME.chalkLayers,
                new BiomeColorsDTO(8695029, 12637426, defaultWater, defaultWaterFog, 8630141, 8169079), CaveType.ELVEN));
        // HILLS OF EVENDIM
        addBiome(new BiomeData(BiomeKeyRegistryME.HILLS_OF_EVENDIM, BiomeDataConfigsME.stoneHills, BiomeDataConfigsME.sandstoneLayers,
                new BiomeColorsDTO(hillySky, defaultFog, defaultWater, defaultWaterFog, 9087338, 9218155)));
        // IRON HILLS
        addBiome(new BiomeData(BiomeKeyRegistryME.IRON_HILLS, BiomeDataConfigsME.ironHills, BiomeDataConfigsME.ironhills,
                new BiomeColorsDTO(10140415, 13031679, defaultWater, defaultWaterFog, 6922099, 7119988), CaveType.MOUNTAINS));
        addBiome(new BiomeData(BiomeKeyRegistryME.IRON_HILLS_BASE, BiomeDataConfigsME.ironHills, BiomeDataConfigsME.ironhills,
                new BiomeColorsDTO(8761343, defaultFog, defaultWater, defaultWaterFog, 7187321, 6793328), CaveType.MOUNTAINS));
        addBiome(new BiomeData(BiomeKeyRegistryME.IRON_HILLS_PEAKS, BiomeDataConfigsME.ironHills, BiomeDataConfigsME.ironhills,
                new BiomeColorsDTO(8761343, defaultFog, defaultWater, defaultWaterFog, 7187321, 6793328), CaveType.MOUNTAINS));
        addBiome(new BiomeData(BiomeKeyRegistryME.IRON_HILLS_PLAINS, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.ironhills,
                new BiomeColorsDTO(hillySky, 12637183, defaultWater, defaultWaterFog, 7713657, 7580788)));
        addBiome(new BiomeData(BiomeKeyRegistryME.NORTHERN_RHOVANION_FOREST, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.ironhills,
                new BiomeColorsDTO(9218275, 11978476, defaultWater, defaultWaterFog, 7120752, 6988907)));
        addBiome(new BiomeData(BiomeKeyRegistryME.NORTHERN_RHOVANION_HILLS, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.ironhills,
                new BiomeColorsDTO(9218275, 11978476, defaultWater, defaultWaterFog, 6462822, 6987883)));
        addBiome(new BiomeData(BiomeKeyRegistryME.IRON_FOOTHILLS, BiomeDataConfigsME.ironHills, BiomeDataConfigsME.ironhills,
                new BiomeColorsDTO(hillySky, 12637183, defaultWater, defaultWaterFog, 7253882, 7580788)));
        // ISENGARD
        addBiome(new BiomeData(BiomeKeyRegistryME.ISENGARD, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, 5076423, defaultWaterFog, 5932619, 5867338)));
        addBiome(new BiomeData(BiomeKeyRegistryME.ISENGARD_HILL, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, 5076423, defaultWaterFog, 5932619, 5867338)));
        // ITHILIEN
        addBiome(new BiomeData(BiomeKeyRegistryME.ITHILIEN, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.gondorLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, defaultWater, defaultWaterFog, 5152072, 4889668)));
        addBiome(new BiomeData(BiomeKeyRegistryME.ITHILIEN_GLADE, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.gondorLayers,
                new BiomeColorsDTO(7246036, 11518431, defaultWater, defaultWaterFog, 6401627, 5086791)));
        addBiome(new BiomeData(BiomeKeyRegistryME.ITHILIEN_WASTES, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.gondorLayers,
                new BiomeColorsDTO(9085388, 10661827, 4944318, 723757, 5733716, 5537108)));
        addBiome(new BiomeData(BiomeKeyRegistryME.ITHILIEN_WASTES_GLADE, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.gondorLayers,
                new BiomeColorsDTO(9611731, 11254223, 4944318, 723757, 6326621, 5537108)));
        // LAMEDON
        addBiome(new BiomeData(BiomeKeyRegistryME.LAMEDON, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.gondorLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, defaultWater, defaultWaterFog, 9162899, 8043898)));
        addBiome(new BiomeData(BiomeKeyRegistryME.LAMEDON_HILLS, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.gondorLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, defaultWater, defaultWaterFog, 9751707, 8370303)));
        // LEBENNIN
        addBiome(new BiomeData(BiomeKeyRegistryME.LEBENNIN, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.gondorLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, defaultWater, defaultWaterFog, 5883985, 3248206)));
        addBiome(new BiomeData(BiomeKeyRegistryME.LEBENNIN_HILLS, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.gondorLayers,
                new BiomeColorsDTO(hillySky, defaultFog, defaultWater, defaultWaterFog, 7194470, 4693601)));
        addBiome(new BiomeData(BiomeKeyRegistryME.LEBENNIN_SHORES, BiomeDataConfigsME.sandShores, BiomeDataConfigsME.gondorLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, defaultShoreWater, defaultWaterFog, 5883985, 4239457)));
        // LINDON
        addBiome(new BiomeData(BiomeKeyRegistryME.LINDON, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.limeStoneLayers,
                new BiomeColorsDTO(8827134, 12771327, defaultWater, defaultWaterFog, 7385448, 6593880), CaveType.ELVEN));
        addBiome(new BiomeData(BiomeKeyRegistryME.LINDON_FOREST, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.limeStoneLayers,
                new BiomeColorsDTO(8827134, 12771327, defaultWater, defaultWaterFog, 6331736, 6067536), CaveType.ELVEN));
        addBiome(new BiomeData(BiomeKeyRegistryME.LINDON_HIDDEN_BLOSSOM, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.limeStoneLayers,
                new BiomeColorsDTO(8827134, 12771327, defaultWater, defaultWaterFog, 7584106, 6593880), CaveType.ELVEN));
        addBiome(new BiomeData(BiomeKeyRegistryME.LINDON_MEADOW, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.limeStoneLayers,
                new BiomeColorsDTO(8827134, 12771327, defaultWater, defaultWaterFog, 7584106, 6725722), CaveType.ELVEN));
        addBiome(new BiomeData(BiomeKeyRegistryME.LINDON_SHORES, BiomeDataConfigsME.gulfOfLhunShoresLayers, BiomeDataConfigsME.limeStoneLayers,
                new BiomeColorsDTO(8827134, 12771327, defaultShoreWater, defaultWaterFog, 9090684, 8168815), CaveType.ELVEN));
        addBiome(new BiomeData(BiomeKeyRegistryME.LINDON_SHORES_CLIFFS, BiomeDataConfigsME.gulfOfLhunShoreCliffsLayers, BiomeDataConfigsME.chalkLayers,
                new BiomeColorsDTO(8827134, 12771327, defaultShoreWater, defaultWaterFog, 9090684, 8168815), CaveType.ELVEN));
        // LONELY MOUNTAINS
        addBiome(new BiomeData(BiomeKeyRegistryME.LONELY_MOUNTAIN_FOOTHILLS, BiomeDataConfigsME.lonelyMountainBase, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(hillySky, 13031679, defaultWater, defaultWaterFog, 7321722, 6858353), CaveType.LONELY_MOUNTAIN));
        addBiome(new BiomeData(BiomeKeyRegistryME.LONELY_MOUNTAIN, BiomeDataConfigsME.lonelyMountainBase, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(hillySky, 13031679, defaultWater, defaultWaterFog, 7188600, 6529388), CaveType.LONELY_MOUNTAIN));
        addBiome(new BiomeData(BiomeKeyRegistryME.LONELY_MOUNTAIN_BASE, BiomeDataConfigsME.lonelyMountain, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(hillySky, 12637183, defaultWater, defaultWaterFog, 6927733, 6005862), CaveType.LONELY_MOUNTAIN));
        addBiome(new BiomeData(BiomeKeyRegistryME.LONELY_MOUNTAIN_PEAKS, BiomeDataConfigsME.lonelyMountainPeak, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(hillySky, 13031679, defaultWater, defaultWaterFog, 8106628, 6987890), CaveType.LONELY_MOUNTAIN));
        addBiome(new BiomeData(BiomeKeyRegistryME.LONELY_MOUNTAIN_TAIGA, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(hillySky, 12637183, defaultWater, defaultWaterFog, 7253882, 7580788)));
        // LONG MARSHES
        addBiome(new BiomeData(BiomeKeyRegistryME.LONG_MARSHES, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(8295660, 10993126, 6853265, 1919305, 5676922, 5282655)));
        // LOSSARNACH
        addBiome(new BiomeData(BiomeKeyRegistryME.LOSSARNACH, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.gondorLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, defaultWater, defaultWaterFog, 8240485, 7909996)));
        addBiome(new BiomeData(BiomeKeyRegistryME.LOSSARNACH_CHERRY_BLOSSOM, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.gondorLayers,
                new BiomeColorsDTO(8103167, 12638463, defaultWater, defaultWaterFog, 11983713, 11983713)));
        addBiome(new BiomeData(BiomeKeyRegistryME.LOSSARNACH_VALLEY, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.gondorLayers,
                new BiomeColorsDTO(8103167, 12638463, defaultWater, defaultWaterFog, 9551961, 9355638)));
        addBiome(new BiomeData(BiomeKeyRegistryME.LOSSARNACH_VALLEY_RED, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.gondorLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, defaultWater, defaultWaterFog, 12082206, 13977913)));
        addBiome(new BiomeData(BiomeKeyRegistryME.LOSSARNACH_VALLEY_ORANGE, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.gondorLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, defaultWater, defaultWaterFog, 14647328, 15761188)));
        addBiome(new BiomeData(BiomeKeyRegistryME.LOSSARNACH_VALLEY_YELLOW, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.gondorLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, defaultWater, defaultWaterFog, 15649303, 16768256)));
        addBiome(new BiomeData(BiomeKeyRegistryME.LOSSARNACH_VALLEY_GREEN, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.gondorLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, defaultWater, defaultWaterFog, 11126589, 9289041)));
        // LOTHLORIEN
        addBiome(new BiomeData(BiomeKeyRegistryME.LORIEN_EDGE, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.limeStoneLayers,
                new BiomeColorsDTO(defaultSky, 11455105, defaultWater, defaultWaterFog, 10601262, 6202980), CaveType.ELVEN));
        addBiome(new BiomeData(BiomeKeyRegistryME.LOTHLORIEN, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.limeStoneLayers,
                new BiomeColorsDTO(defaultSky, 13748853, defaultWater, defaultWaterFog, 12961832, 6989412), CaveType.ELVEN));
        addBiome(new BiomeData(BiomeKeyRegistryME.LOTHLORIEN_GLADE, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.limeStoneLayers,
                new BiomeColorsDTO(defaultSky, 13748853, defaultWater, defaultWaterFog, 14146100, 6989412), CaveType.ELVEN));
        addBiome(new BiomeData(BiomeKeyRegistryME.LOTHLORIEN_BLOSSOM, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.limeStoneLayers,
                new BiomeColorsDTO(defaultSky, 13748853, defaultWater, defaultWaterFog, 10864680, 7387753), CaveType.ELVEN));
        // MINHIRIATH
        addBiome(new BiomeData(BiomeKeyRegistryME.MINHIRIATH, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.limeStoneLayers,
                new BiomeColorsDTO(defaultSky, 12637429, 4615389, 658236, 8626526, 8231005)));
        addBiome(new BiomeData(BiomeKeyRegistryME.MINHIRIATH_WHEAT_FIELD, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.limeStoneLayers,
                new BiomeColorsDTO(defaultSky, 12637429, 4615389, 658236, 0x96a15e, 0x9da862)));
        // MIRKWOOD
        addBiome(new BiomeData(BiomeKeyRegistryME.MIRKWOOD, BiomeDataConfigsME.loamPlains, BiomeDataConfigsME.limeStoneLayers,
                new BiomeColorsDTO(6849446, 6780008, 7111535, 338483, 4151612, 3823415), CaveType.ELVEN));
        addBiome(new BiomeData(BiomeKeyRegistryME.WEBBED_DARK_WOODS, BiomeDataConfigsME.loamPlains, BiomeDataConfigsME.limeStoneLayers,
                new BiomeColorsDTO(6124690, 6319201, 7111535, 338483, 4216382, 3823671), CaveType.ELVEN));
        addBiome(new BiomeData(BiomeKeyRegistryME.MIRKWOOD_EDGE, BiomeDataConfigsME.loamPlains, BiomeDataConfigsME.limeStoneLayers,
                new BiomeColorsDTO(7578800, 7897724, 7111535, 338483, 4546876, 4284215), CaveType.ELVEN));
        addBiome(new BiomeData(BiomeKeyRegistryME.MIRKWOOD_FOOTHILLS, BiomeDataConfigsME.loamPlains, BiomeDataConfigsME.limeStoneLayers,
                new BiomeColorsDTO(6849446, 6780008, 7111535, 338483, 4151612, 3823415), CaveType.MOUNTAINS));
        addBiome(new BiomeData(BiomeKeyRegistryME.MIRKWOOD_MOUNTAINS_BASE, BiomeDataConfigsME.mirkwoodMountains, BiomeDataConfigsME.limeStoneLayers,
                new BiomeColorsDTO(6849446, 6780008, 7111535, 338483, 4151612, 3823415), CaveType.MOUNTAINS));
        addBiome(new BiomeData(BiomeKeyRegistryME.MIRKWOOD_MOUNTAINS, BiomeDataConfigsME.mirkwoodMountains, BiomeDataConfigsME.limeStoneLayers,
                new BiomeColorsDTO(6849446, 6780008, 7111535, 338483, 4151612, 3823415), CaveType.MOUNTAINS));
        addBiome(new BiomeData(BiomeKeyRegistryME.MIRKWOOD_MOUNTAINS_PEAKS, BiomeDataConfigsME.mirkwoodMountainPeaks, BiomeDataConfigsME.limeStoneLayers,
                new BiomeColorsDTO(7972607, 9873053, 7111535, 338483, 4678724, 4482114), CaveType.MOUNTAINS));
        addBiome(new BiomeData(BiomeKeyRegistryME.DARK_MIRKWOOD, BiomeDataConfigsME.loamPlains, BiomeDataConfigsME.limeStoneLayers,
                new BiomeColorsDTO(5269900, 4806731, 4544130, 338483, 4151612, 3823415), CaveType.ELVEN));
        addBiome(new BiomeData(BiomeKeyRegistryME.DARK_MIRKWOOD_EDGE, BiomeDataConfigsME.loamPlains, BiomeDataConfigsME.limeStoneLayers,
                new BiomeColorsDTO(5861275, 8293250, 4551554, 467756, 4083260, 3690038), CaveType.ELVEN));
        addBiome(new BiomeData(BiomeKeyRegistryME.MIRKWOOD_SWAMP, BiomeDataConfigsME.mud, BiomeDataConfigsME.limeStoneLayers,
                new BiomeColorsDTO(6981536, 8821922, 7111535, 1458241, 4546876, 4284215), CaveType.ELVEN));
        addBiome(new BiomeData(BiomeKeyRegistryME.NORTHERN_MIRKWOOD_SWAMP, BiomeDataConfigsME.peatPlains, BiomeDataConfigsME.limeStoneLayers,
                new BiomeColorsDTO(6849692, 8427421, 7111535, 1458241, 4546876, 4284215), CaveType.ELVEN));
        addBiome(new BiomeData(BiomeKeyRegistryME.MIRKWOOD_MARSHES, BiomeDataConfigsME.mud, BiomeDataConfigsME.limeStoneLayers,
                new BiomeColorsDTO(6981536, 8821922, 7111535, 1458241, 4546876, 4284215), CaveType.ELVEN));
        addBiome(new BiomeData(BiomeKeyRegistryME.NORTHERN_MIRKWOOD_MARSHES, BiomeDataConfigsME.mud, BiomeDataConfigsME.limeStoneLayers,
                new BiomeColorsDTO(6849692, 8427421, 7111535, 1458241, 4546876, 4284215), CaveType.ELVEN));
        // MISTY MOUNTAINS
        addBiome(new BiomeData(BiomeKeyRegistryME.MISTY_MOUNTAINS_BASE, BiomeDataConfigsME.mistyMountainsBase, BiomeDataConfigsME.mistyMountainsLayers,
                new BiomeColorsDTO(hillySky, defaultFog, defaultWater, defaultWaterFog, 7777673, 7316862), CaveType.MISTIES));
        addBiome(new BiomeData(BiomeKeyRegistryME.MISTY_MOUNTAINS, BiomeDataConfigsME.mistyMountains, BiomeDataConfigsME.mistyMountainsLayers,
                new BiomeColorsDTO(hillySky, defaultFog, defaultWater, defaultWaterFog, 7777673, 7316862), CaveType.MISTIES));
        addBiome(new BiomeData(BiomeKeyRegistryME.MISTY_MOUNTAINS_PEAKS, BiomeDataConfigsME.mistiesPeaks, BiomeDataConfigsME.mistyMountainsLayers,
                new BiomeColorsDTO(hillySky, defaultFog, defaultWater, defaultWaterFog, 7777673, 7316862), CaveType.MISTIES));
        // MORDOR
        addBiome(new BiomeData(BiomeKeyRegistryME.MORDOR, BiomeDataConfigsME.mordor, BiomeDataConfigsME.ashenStoneLayers,
                new BiomeColorsDTO(2695203, 1905947, 6450777, 1513734, 3550502, 2695966), CaveType.ASHEN));
        addBiome(new BiomeData(BiomeKeyRegistryME.MORDOR_ASHEN_FOREST, BiomeDataConfigsME.mordor, BiomeDataConfigsME.ashenStoneLayers,
                new BiomeColorsDTO(2695203, 1905947, 6450777, 1513734, 3550502, 2695966), CaveType.ASHEN));
        addBiome(new BiomeData(BiomeKeyRegistryME.MORDOR_HILL, BiomeDataConfigsME.mordor, BiomeDataConfigsME.ashenStoneLayers,
                new BiomeColorsDTO(2695203, 1905947, 6450777, 1513734, 3550502, 2695966), CaveType.ASHEN));
        addBiome(new BiomeData(BiomeKeyRegistryME.ERED_LITHUI_BASE, BiomeDataConfigsME.mordorMountains, BiomeDataConfigsME.ashenStoneLayers,
                new BiomeColorsDTO(3747117, 2629407, 6450777, 1513734, 5129527, 3486247), CaveType.ASHEN));
        addBiome(new BiomeData(BiomeKeyRegistryME.ERED_LITHUI, BiomeDataConfigsME.mordorMountainsPeaks, BiomeDataConfigsME.ashenStoneLayers,
                new BiomeColorsDTO(4142646, 3090215, 6450777, 1513734, 3550502, 2169880), CaveType.ASHEN));
        addBiome(new BiomeData(BiomeKeyRegistryME.ERED_LITHUI_PEAKS, BiomeDataConfigsME.mordorMountainsPeaks, BiomeDataConfigsME.ashenStoneLayers,
                new BiomeColorsDTO(4142646, 3090215, 6450777, 1513734, 3550502, 2169880), CaveType.ASHEN));
        addBiome(new BiomeData(BiomeKeyRegistryME.MORDOR_WASTES, BiomeDataConfigsME.ashenDirt, BiomeDataConfigsME.ashenStoneLayers,
                new BiomeColorsDTO(5460048, 4999240, 5860962, 731161, 6252369, 4735297), CaveType.ASHEN));
        // MORGUL VALE
        addBiome(new BiomeData(BiomeKeyRegistryME.MORGUL_VALE, BiomeDataConfigsME.mordorGrass, BiomeDataConfigsME.ashenStoneLayers,
                new BiomeColorsDTO(4479570, 3690813, 3897457, 595232, 4545602, 4876362), CaveType.ASHEN));
        addBiome(new BiomeData(BiomeKeyRegistryME.MORGUL_FOREST, BiomeDataConfigsME.mordorGrass, BiomeDataConfigsME.ashenStoneLayers,
                new BiomeColorsDTO(4479570, 3690813, 3897457, 595232, 4545602, 4876362), CaveType.ASHEN));
        addBiome(new BiomeData(BiomeKeyRegistryME.MORGUL_RIVER, BiomeDataConfigsME.ashenDirt, BiomeDataConfigsME.ashenStoneLayers,
                new BiomeColorsDTO(4479570, 3690813, 3897457, 595232, 4545602, 4348994)));
        // MOUNT DOOM
        addBiome(new BiomeData(BiomeKeyRegistryME.MOUNT_DOOM, BiomeDataConfigsME.mordorMountainsPeaks, BiomeDataConfigsME.ashenStoneLayers,
                new BiomeColorsDTO(4142646, 3090215, 6450777, 1513734, 3550502, 2169880), CaveType.ASHEN));
        addBiome(new BiomeData(BiomeKeyRegistryME.MOUNT_DOOM_PIT, BiomeDataConfigsME.mordorMountainsPeaks, BiomeDataConfigsME.ashenStoneLayers,
                new BiomeColorsDTO(4142646, 3090215, 6450777, 1513734, 3550502, 2169880), CaveType.ASHEN));
        // MOUNT GUNDABAD
        addBiome(new BiomeData(BiomeKeyRegistryME.MOUNT_GUNDABAD_BASE, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.greyMountainsLayers,
                new BiomeColorsDTO(8098794, 12701936, defaultWater, defaultWaterFog, 8823414, 9022583), CaveType.MISTIES));
        addBiome(new BiomeData(BiomeKeyRegistryME.MOUNT_GUNDABAD, BiomeDataConfigsME.mountGundabad, BiomeDataConfigsME.greyMountainsLayers,
                new BiomeColorsDTO(8098794, 12701936, defaultWater, defaultWaterFog, 8823414, 9022583), CaveType.MISTIES));
        addBiome(new BiomeData(BiomeKeyRegistryME.MOUNT_GUNDABAD_PEAKS, BiomeDataConfigsME.mountGundabadPeaks, BiomeDataConfigsME.greyMountainsLayers,
                new BiomeColorsDTO(8098794, 12701936, defaultWater, defaultWaterFog, 8823414, 9022583), CaveType.MISTIES));
        // NAN CURUNIR
        addBiome(new BiomeData(BiomeKeyRegistryME.NAN_CURUNIR, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, 5076423, defaultWaterFog, 5406786, 4554563)));
        // NEN HITHOEL
        addBiome(new BiomeData(BiomeKeyRegistryME.NEN_HITHOEL, BiomeDataConfigsME.ocean, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(waterSky, 12638463, 6853316, 6853316, 10995507, 7181907)));
        addBiome(new BiomeData(BiomeKeyRegistryME.NEN_HITHOEL_RAPIDS, BiomeDataConfigsME.ocean, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(waterSky, 12638463, 6853316, 6853316, 10995507, 7181907)));
        addBiome(new BiomeData(BiomeKeyRegistryME.NEN_HITHOEL_FOREST, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(6980296, 10402016, 4618461, defaultWaterFog, 9666387, 9731143)));
        addBiome(new BiomeData(BiomeKeyRegistryME.NEN_HITHOEL_SHORES, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.gondorLayers,
                new BiomeColorsDTO(6980296, 10402016, 4618461, defaultWaterFog, 9666387, 9731143)));
        // NINDALF
        addBiome(new BiomeData(BiomeKeyRegistryME.NINDALF, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, defaultWater, defaultWaterFog, 6263143, 5869915)));
        // NORTH DOWNS
        addBiome(new BiomeData(BiomeKeyRegistryME.NORTH_DOWNS, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(hillySky, defaultFog, defaultWater, defaultWaterFog, 9414008, 9018483)));
        // NORTHERN WASTELANDS
        addBiome(new BiomeData(BiomeKeyRegistryME.NORTHERN_WASTELANDS, BiomeDataConfigsME.snowyPlains, BiomeDataConfigsME.frozenLayers,
                new BiomeColorsDTO(hillySky, defaultFog, defaultWater, defaultWaterFog, 5932658, 7973008), CaveType.FOROD));
        // NURN
        addBiome(new BiomeData(BiomeKeyRegistryME.NURN, BiomeDataConfigsME.loamPlains, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(8954077, 11781331, 5860970, 1321760, 7443043, 6982236)));
        addBiome(new BiomeData(BiomeKeyRegistryME.NURN_FOREST, BiomeDataConfigsME.loamPlains, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(8822986, 11057090, 5860970, 1321760, 6916445, 6784858)));
        addBiome(new BiomeData(BiomeKeyRegistryME.NURN_HILL, BiomeDataConfigsME.loamPlains, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(9283042, 11978194, 5860970, 1321760, 7968366, 7310690)));
        addBiome(new BiomeData(BiomeKeyRegistryME.NURN_EDGE, BiomeDataConfigsME.loamPlains, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(8821450, 11517913, 5202783, 1321247, 6511435, 7960147)));
        addBiome(new BiomeData(BiomeKeyRegistryME.NURN_EDGE_WOODS, BiomeDataConfigsME.loamPlains, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(8360637, 10793931, 5202783, 1321247, 6513227, 7829843)));
        addBiome(new BiomeData(BiomeKeyRegistryME.NURN_SEA, BiomeDataConfigsME.ocean, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(8952797, 11779800, 5860963, 863008, 5465422, 5663573)));
        addBiome(new BiomeData(BiomeKeyRegistryME.NURN_RIVER, BiomeDataConfigsME.river, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(8952797, 11779800, 5860963, 863008, 5465422, 5663573)));
        addBiome(new BiomeData(BiomeKeyRegistryME.EASTERN_NURN, BiomeDataConfigsME.loamPlains, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(8032225, defaultFog, 6720143, 1583408, 7435862, 7436627)));
        // OLD ANGMAR
        addBiome(new BiomeData(BiomeKeyRegistryME.OLD_ANGMAR, BiomeDataConfigsME.peatPlains, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(8954077, 11781343, 4814544, 460593, 7443043, 6982236)));
        addBiome(new BiomeData(BiomeKeyRegistryME.OLD_ANGMAR_FOREST, BiomeDataConfigsME.peatPlains, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(8954077, 11781343, 4814544, 460593, 7443043, 6982236)));
        addBiome(new BiomeData(BiomeKeyRegistryME.OLD_ANGMAR_COLD_HILL, BiomeDataConfigsME.peatPlains, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(8954077, 11781343, 4814544, 460593, 7443043, 6982236)));
        addBiome(new BiomeData(BiomeKeyRegistryME.OLD_ANGMAR_FROZEN_HILL, BiomeDataConfigsME.peatPlains, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(8954077, 11781343, 4814544, 460593, 7443043, 6982236)));
        // OLD ARTHEDAIN
        addBiome(new BiomeData(BiomeKeyRegistryME.OLD_ARTHEDAIN, BiomeDataConfigsME.siltPlains, BiomeDataConfigsME.stoneGabbroLayers,
                new BiomeColorsDTO(7907327, defaultFog, defaultWater, defaultWaterFog, 12508275, 11652468)));
        addBiome(new BiomeData(BiomeKeyRegistryME.OLD_ARTHEDAIN_FOREST, BiomeDataConfigsME.siltPlains, BiomeDataConfigsME.stoneGabbroLayers,
                new BiomeColorsDTO(7907327, defaultFog, defaultWater, defaultWaterFog, 10862435, 11126380)));
        addBiome(new BiomeData(BiomeKeyRegistryME.OLD_ARTHEDAIN_MEADOW, BiomeDataConfigsME.siltPlains, BiomeDataConfigsME.stoneGabbroLayers,
                new BiomeColorsDTO(7907327, defaultFog, defaultWater, defaultWaterFog, 12508275, 11652468)));
        addBiome(new BiomeData(BiomeKeyRegistryME.OLD_ARTHEDAIN_FOOTHILL, BiomeDataConfigsME.siltPlains, BiomeDataConfigsME.stoneGabbroLayers,
                new BiomeColorsDTO(7907327, defaultFog, defaultWater, defaultWaterFog, 11190641, 11126380)));
        // OLD CARDOLAN
        addBiome(new BiomeData(BiomeKeyRegistryME.OLD_CARDOLAN, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(defaultSky, 12637429, 4615389, 658236, 9154400, 9154400)));
        addBiome(new BiomeData(BiomeKeyRegistryME.OLD_CARDOLAN_FOREST, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(defaultSky, 12637429, 4615389, 658236, 8298580, 8627801)));
        addBiome(new BiomeData(BiomeKeyRegistryME.OLD_CARDOLAN_HILL, BiomeDataConfigsME.stoneHills, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(defaultSky, 12637429, 4615389, 658236, 9482090, 9154400)));
        // OLD RHUDAUR
        addBiome(new BiomeData(BiomeKeyRegistryME.OLD_RHUDAUR, BiomeDataConfigsME.loamPlains, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(7508201, 10863086, 4618461, defaultWaterFog, 6722387, 6198343)));
        addBiome(new BiomeData(BiomeKeyRegistryME.OLD_RHUDAUR_FOREST, BiomeDataConfigsME.loamPlains, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(7507684, 10862569, 4618461, defaultWaterFog, 5932359, 6131783)));
        addBiome(new BiomeData(BiomeKeyRegistryME.OLD_RHUDAUR_HILL, BiomeDataConfigsME.stoneHills, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(7507684, 10862569, 4618461, defaultWaterFog, 7049306, 6525264)));
        // OSGILIATH
        addBiome(new BiomeData(BiomeKeyRegistryME.OSGILIATH, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.gondorLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, defaultWater, defaultWaterFog, 8104558, 7445848)));
        // PELENNOR FIELDS
        addBiome(new BiomeData(BiomeKeyRegistryME.PELENNOR_FIELDS, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.gondorLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, defaultWater, defaultWaterFog, 0x7ea150, 8169054)));
        addBiome(new BiomeData(BiomeKeyRegistryME.PELENNOR_WHEAT_FIELD, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.gondorLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, defaultWater, defaultWaterFog, 0x94a150, 8169054)));
        // RHUN
        addBiome(new BiomeData(BiomeKeyRegistryME.RHUN, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(8041727, 12773631, 4618980, defaultWaterFog, 10995507, 8165203)));
        addBiome(new BiomeData(BiomeKeyRegistryME.RHUN_FIELD, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(8041727, 12773631, 4618980, defaultWaterFog, 12439605, 8427347)));
        addBiome(new BiomeData(BiomeKeyRegistryME.RHUN_FOREST, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(7316455, 12049136, 4618980, defaultWaterFog, 10007856, 7901520)));
        addBiome(new BiomeData(BiomeKeyRegistryME.RHUN_HIDDEN_BLOSSOM, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(8041727, 12773631, 4618980, defaultWaterFog, 11456565, 8427347)));
        // RHOVANION
        addBiome(new BiomeData(BiomeKeyRegistryME.EASTERN_RHOVANION, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(7571933, defaultFog, 12440052, 591663, 8627523, 7052347)));
        addBiome(new BiomeData(BiomeKeyRegistryME.EASTERN_RHOVANION_FOREST, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(7176133, 10926035, 12440052, 591663, 8627523, 6721596)));
        addBiome(new BiomeData(BiomeKeyRegistryME.SOUTHEAST_RHOVANION, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(8954077, 11781343, 4814544, 460593, 7378251, 6982236)));
        addBiome(new BiomeData(BiomeKeyRegistryME.SOUTHEAST_RHOVANION_FIELD, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(8954077, 11781343, 4814544, 460593, 7708747, 7839335)));
        // ROHAN
        addBiome(new BiomeData(BiomeKeyRegistryME.ROHAN, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, defaultWater, defaultWaterFog, 12569978, 9677397)));
        addBiome(new BiomeData(BiomeKeyRegistryME.ROHAN_FOREST, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, defaultWater, defaultWaterFog, 11321968, 8887375)));
        addBiome(new BiomeData(BiomeKeyRegistryME.ROHAN_FIELD, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(hillySky, defaultFog, defaultWater, defaultWaterFog, 11913598, 9873760)));
        // SARN GEBIR SHORES
        addBiome(new BiomeData(BiomeKeyRegistryME.SARN_GEBIR_SHORES, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.gondorLayers,
                new BiomeColorsDTO(6980296, 10402016, defaultWater, defaultWaterFog, 9666387, 9731143)));
        addBiome(new BiomeData(BiomeKeyRegistryME.SARN_GEBIR_WILDLANDS, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(6980296, 10402016, defaultWater, defaultWaterFog, 9666387, 9731143)));
        // SHIRE
        addBiome(new BiomeData(BiomeKeyRegistryME.SHIRE, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(8695029, 12637426, defaultWater, defaultWaterFog, 8704848, 6858783)));
        addBiome(new BiomeData(BiomeKeyRegistryME.SHIRE_EDGE, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(8695029, 12637426, defaultWater, defaultWaterFog, 7978571, 6132766)));
        addBiome(new BiomeData(BiomeKeyRegistryME.SHIRE_HILLS, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(8695029, 12637426, defaultWater, defaultWaterFog, 8175184, 6658600)));
        addBiome(new BiomeData(BiomeKeyRegistryME.SHIRE_WOODS, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(8695029, 12637426, defaultWater, defaultWaterFog, 6991676, 6460967)));
        addBiome(new BiomeData(BiomeKeyRegistryME.SHIRE_FOREST, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(8695029, 12637426, defaultWater, defaultWaterFog, 6662455, 5803292)));
        // SOUTHERN EPHEL DUATH
        addBiome(new BiomeData(BiomeKeyRegistryME.EPHEL_DUATH_BASE, BiomeDataConfigsME.mordorMountains, BiomeDataConfigsME.ashenStoneLayers,
                new BiomeColorsDTO(3747117, 2629407, 6450777, 1513734, 5129527, 3486247), CaveType.ASHEN));
        addBiome(new BiomeData(BiomeKeyRegistryME.EPHEL_DUATH, BiomeDataConfigsME.mordorMountainsPeaks, BiomeDataConfigsME.ashenStoneLayers,
                new BiomeColorsDTO(4142646, 3090215, 6450777, 1513734, 3550502, 2169880), CaveType.ASHEN));
        addBiome(new BiomeData(BiomeKeyRegistryME.EPHEL_DUATH_PEAKS, BiomeDataConfigsME.mordorMountainsPeaks, BiomeDataConfigsME.ashenStoneLayers,
                new BiomeColorsDTO(4142646, 3090215, 6450777, 1513734, 3550502, 2169880), CaveType.ASHEN));
        // SOUTHERN FOROCHEL
        addBiome(new BiomeData(BiomeKeyRegistryME.SOUTHERN_FOROCHEL, BiomeDataConfigsME.snowyPlains, BiomeDataConfigsME.frozenLayers,
                new BiomeColorsDTO(hillySky, defaultFog, defaultWater, defaultWaterFog, 7177842, 7971216), CaveType.FOROD));
        // THE ANGLE
        addBiome(new BiomeData(BiomeKeyRegistryME.THE_ANGLE, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, 4615389, 658236, 9878641, 8431193)));
        // THE OLD FOREST
        addBiome(new BiomeData(BiomeKeyRegistryME.THE_OLD_FOREST, BiomeDataConfigsME.loamPlains, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(6785744, 10004675, 4421513, 402733, 2311707, 2050588)));
        // THE WHITE DOWNS
        addBiome(new BiomeData(BiomeKeyRegistryME.THE_WHITE_DOWNS, BiomeDataConfigsME.chalkPlains, BiomeDataConfigsME.chalkLayers,
                new BiomeColorsDTO(9022444, 12898532, defaultWater, defaultWaterFog, 11983713, 7905395)));
        // THE WOLD
        addBiome(new BiomeData(BiomeKeyRegistryME.THE_WOLD, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, defaultWater, defaultWaterFog, 12309403, 12242068)));
        addBiome(new BiomeData(BiomeKeyRegistryME.THE_WOLD_WHEAT_FIELD, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, defaultWater, defaultWaterFog, 0xaab777, 0xc5d48a)));
        // TOLFALAS
        addBiome(new BiomeData(BiomeKeyRegistryME.TOLFALAS, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(defaultSky, defaultFog, defaultWater, defaultWaterFog, 10927716, 9615182)));
        // TOROGWAITH
        addBiome(new BiomeData(BiomeKeyRegistryME.TOROGWAITH, BiomeDataConfigsME.mordor, BiomeDataConfigsME.ashenStoneLayers,
                new BiomeColorsDTO(3289130, 2827810, 6255709, 1252359, 4142897, 3156775), CaveType.ASHEN));
        // TROLLSHAWS
        addBiome(new BiomeData(BiomeKeyRegistryME.TROLLSHAWS, BiomeDataConfigsME.peatPlains, BiomeDataConfigsME.limeStoneLayers,
                new BiomeColorsDTO(6980296, 10402016, 4618461, defaultWaterFog, 0x7D9943, 0x5B5717)));
        // UDUN
        addBiome(new BiomeData(BiomeKeyRegistryME.UDUN, BiomeDataConfigsME.mordor, BiomeDataConfigsME.ashenStoneLayers,
                new BiomeColorsDTO(4273461, 2826529, 6450777, 1513734, 3549478, 2695966), CaveType.ASHEN));
        // UMBAR
        addBiome(new BiomeData(BiomeKeyRegistryME.UMBAR, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.sandstoneTravertineLayers,
                new BiomeColorsDTO(7254527, 12638463, 5212644, 333363, 11059059, 9284946), CaveType.HARAD));
        addBiome(new BiomeData(BiomeKeyRegistryME.UMBAR_WOODS, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.sandstoneTravertineLayers,
                new BiomeColorsDTO(7317996, 12308721, 5212644, 333363, 9876841, 8955470), CaveType.HARAD));
        // WEBBED WOODS
        addBiome(new BiomeData(BiomeKeyRegistryME.WEBBED_WOODS, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(6385822, 5198943, 4544130, 338483, 4478786, 4017979)));
        // WITHERED HEATH
        addBiome(new BiomeData(BiomeKeyRegistryME.WITHERED_HEATH, BiomeDataConfigsME.grassPlains, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(9479110, 11780310, 5141697, 460593, 8881498, 10328434)));
        // WHITE MOUNTAINS
        addBiome(new BiomeData(BiomeKeyRegistryME.WHITE_MOUNTAINS_BASE, BiomeDataConfigsME.whiteMountains, BiomeDataConfigsME.whiteMountainsLayers,
                new BiomeColorsDTO(hillySky, 12638463, defaultWater, defaultWaterFog, 7185769, 6857066), CaveType.MOUNTAINS));
        addBiome(new BiomeData(BiomeKeyRegistryME.WHITE_MOUNTAINS, BiomeDataConfigsME.whiteMountains, BiomeDataConfigsME.whiteMountainsLayers,
                new BiomeColorsDTO(hillySky, 12638463, defaultWater, defaultWaterFog, 7183466, 7513204), CaveType.MOUNTAINS));
        addBiome(new BiomeData(BiomeKeyRegistryME.WHITE_MOUNTAINS_PEAKS, BiomeDataConfigsME.whitePeaks, BiomeDataConfigsME.whiteMountainsLayers,
                new BiomeColorsDTO(hillySky, 12638463, defaultWater, defaultWaterFog, 7185769, 6857066), CaveType.MOUNTAINS));
        // WOODLANDS
        addBiome(new BiomeData(BiomeKeyRegistryME.WOODLAND_REALM, BiomeDataConfigsME.loamPlains, BiomeDataConfigsME.limeStoneTravertineLayers,
                new BiomeColorsDTO(8497918, 10666932, 4492967, 471355, 3960119, 3370029), CaveType.ELVEN));
        addBiome(new BiomeData(BiomeKeyRegistryME.WOODLAND_FOOTHILLS, BiomeDataConfigsME.limeStoneHills, BiomeDataConfigsME.limeStoneTravertineLayers,
                new BiomeColorsDTO(9614583, 12242629, 4492967, 471355, 4420926, 3567152), CaveType.ELVEN));
        addBiome(new BiomeData(BiomeKeyRegistryME.WOODLAND_GLADE, BiomeDataConfigsME.loamPlains, BiomeDataConfigsME.limeStoneTravertineLayers,
                new BiomeColorsDTO(8826622, 12242629, 4492967, 471355, 5408076, 4950339), CaveType.ELVEN));
        addBiome(new BiomeData(BiomeKeyRegistryME.AUTUMN_WOODLAND, BiomeDataConfigsME.loamPlains, BiomeDataConfigsME.limeStoneTravertineLayers,
                new BiomeColorsDTO(8826622, 8293248, 4492967, 471355, 6981960, 6261059), CaveType.ELVEN));
        addBiome(new BiomeData(BiomeKeyRegistryME.WOODLAND_HILLS, BiomeDataConfigsME.limeStoneHills, BiomeDataConfigsME.limeStoneLayers,
                new BiomeColorsDTO(9614583, 12242629, 4492967, 471355, 4748611, 4093495), CaveType.ELVEN));
    }

   private static void loadGenericPonds(){
       addBiome(new BiomeData(BiomeKeyRegistryME.POND, BiomeDataConfigsME.pond, BiomeDataConfigsME.stoneLayers,
               new BiomeColorsDTO(waterSky, defaultFog, 6388580, 2302743, 5011004, 6975545)));
       addBiome(new BiomeData(BiomeKeyRegistryME.FROZEN_POND, BiomeDataConfigsME.ocean, BiomeDataConfigsME.stoneLayers,
               new BiomeColorsDTO(8628223, 10599910, 3750089, 263470, 3494723, 4478280), CaveType.FOROD));
       addBiome(new BiomeData(BiomeKeyRegistryME.OASIS, BiomeDataConfigsME.beach, BiomeDataConfigsME.sandstoneLayers,
               new BiomeColorsDTO(nearHaradSky, nearHaradSkyFog, 5407446, 1120828, 7253092, 6592350), CaveType.HARAD));
       addBiome(new BiomeData(BiomeKeyRegistryME.WASTE_POND, BiomeDataConfigsME.ashenDirt, BiomeDataConfigsME.ashenStoneLayers,
               new BiomeColorsDTO(8163746, 10926783, 5860963, 863008, 4020033, 2695710)));
       addBiome(new BiomeData(BiomeKeyRegistryME.MANGROVE_POND, BiomeDataConfigsME.pond, BiomeDataConfigsME.stoneLayers,
               new BiomeColorsDTO(waterSky, defaultFog, 5141154, 331315, 7583083, 6592339)));
   }

    private static void loadGenericRivers(){
        addBiome(new BiomeData(BiomeKeyRegistryME.MIRKWOOD_RIVER, BiomeDataConfigsME.river, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(6981536, 8821922, 7111535, 1458241, 4546876, 4284215)));
        addBiome(new BiomeData(BiomeKeyRegistryME.GREAT_RIVER, BiomeDataConfigsME.river, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(waterSky, 12638463, defaultWater, defaultWaterFog, 10995507, 7181907)));
        addBiome(new BiomeData(BiomeKeyRegistryME.RIVER, BiomeDataConfigsME.river, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(waterSky, defaultFog, 4290790, defaultWaterFog, 7583083, 6592339)));
        addBiome(new BiomeData(BiomeKeyRegistryME.RIVER_RUNNING, BiomeDataConfigsME.river, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(waterSky, defaultFog, 4290790, defaultWaterFog, 7583083, 6592339)));
    }

    private static void loadGenericOceans(){
       addBiome(new BiomeData(BiomeKeyRegistryME.OCEAN, BiomeDataConfigsME.ocean, BiomeDataConfigsME.stoneLayers,
               new BiomeColorsDTO(waterSky, defaultFog, defaultOceanWater, defaultOceanWaterFog, 7576434, 6588506)));
       addBiome(new BiomeData(BiomeKeyRegistryME.OCEAN_COAST, BiomeDataConfigsME.ocean, BiomeDataConfigsME.stoneLayers,
               new BiomeColorsDTO(8104447, defaultFog, defaultCoastWater, defaultOceanWaterFog, 7971954, 6590810)));
       addBiome(new BiomeData(BiomeKeyRegistryME.FROZEN_OCEAN, BiomeDataConfigsME.ocean, BiomeDataConfigsME.stoneLayers,
               new BiomeColorsDTO(8628223, 10599910, 3750089, 263470, 3494723, 4478280), CaveType.FOROD));
        addBiome(new BiomeData(BiomeKeyRegistryME.LONG_LAKE_SHORES, BiomeDataConfigsME.ocean, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(waterSky, defaultFog, 4352228, 525619, 7576434, 6588506)));
        addBiome(new BiomeData(BiomeKeyRegistryME.LONG_LAKE, BiomeDataConfigsME.ocean, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(waterSky, defaultFog, 4352228, 525619, 7576434, 6588506)));
        addBiome(new BiomeData(BiomeKeyRegistryME.SEA_OF_RHUN, BiomeDataConfigsME.ocean, BiomeDataConfigsME.stoneLayers,
                new BiomeColorsDTO(waterSky, 12638463, 4159204, defaultWaterFog, 10995507, 7181907)));
   }
}
