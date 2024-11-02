package net.jukoz.me.world.biomes.surface;

import net.jukoz.me.utils.LoggerUtil;
import net.jukoz.me.world.biomes.MEBiomeDataConfigs;
import net.jukoz.me.world.biomes.MEBiomeKeys;
import net.jukoz.me.world.biomes.caves.CaveType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Converts PNG pixel color to a BiomeKey reference.
 */
public class MEBiomesData {
    private static HashMap<Color, CustomBiomeHeightData> biomeHashMap;
    public static List<RegistryKey<Biome>> coastalBiomes = new ArrayList<>();
    public static List<RegistryKey<Biome>> waterBiomes = new ArrayList<>();
    public static List<RegistryKey<Biome>> riverbiomes = new ArrayList<>();
    public static List<RegistryKey<Biome>> frozenBiomes = new ArrayList<>();
    public static List<RegistryKey<Biome>> wastePondBiomes = new ArrayList<>();
    public static List<RegistryKey<Biome>> mirkwoodSwampBiomes = new ArrayList<>();
    public static List<RegistryKey<Biome>> oasisBiomes = new ArrayList<>();
    public static List<RegistryKey<Biome>> anduinWaterBiomes = new ArrayList<>();
    public static List<RegistryKey<Biome>> deadMarshesBiomes = new ArrayList<>();

    public static Color DEFAULT_COLOR = new Color(55, 90, 195);
    public static CustomBiomeHeightData defaultBiome;
    public static CustomBiomeHeightData oceanCoast;
    public static CustomBiomeHeightData frozenPond;
    public static CustomBiomeHeightData oasis;
    public static CustomBiomeHeightData pond;
    public static CustomBiomeHeightData greatRiver;
    public static CustomBiomeHeightData wastePond;
    public static CustomBiomeHeightData mirkwoodSwamp;
    public static CustomBiomeHeightData deadMarshes;
    public static CustomBiomeHeightData deadMarshesWater;

    public static void add(Color color, CustomBiomeHeightData biome) {
        biomeHashMap.put(color, biome);
    }

    public static CustomBiomeHeightData getBiomeByColor(Integer rgb){
        if(biomeHashMap.containsKey(new Color(rgb)))
            return biomeHashMap.get(new Color(rgb));
        throw new RuntimeException("MeBiomes::No registered biome has %s for color".formatted(rgb));
    }

    public static void loadBiomes() {
        biomeHashMap = new HashMap<>();

        SubBiomes.loadSubBiomes();
        defaultBiome = new CustomBiomeHeightData(MEBiomeKeys.OCEAN, -17, MEBiomeDataConfigs.oceanModifier);
        add(DEFAULT_COLOR, defaultBiome);

        oceanCoast = new CustomBiomeHeightData(MEBiomeKeys.OCEAN_COAST, -9, MEBiomeDataConfigs.oceanModifier);
        add(new Color(75, 106, 199), oceanCoast);

        // Ponds
        frozenPond = new CustomBiomeHeightData(MEBiomeKeys.FROZEN_POND, -10, MEBiomeDataConfigs.riverModifier);
        add(new Color(104, 168, 222), frozenPond);
        oasis = new CustomBiomeHeightData(MEBiomeKeys.OASIS, -10, MEBiomeDataConfigs.riverModifier);
        add(new Color(104, 168, 222), oasis);
        pond = new CustomBiomeHeightData(MEBiomeKeys.POND, -10, MEBiomeDataConfigs.riverModifier);
        add(new Color(110, 154, 218), pond);
        mirkwoodSwamp = new CustomBiomeHeightData(MEBiomeKeys.MIRKWOOD_SWAMP, -10, MEBiomeDataConfigs.riverModifier);
        add(new Color(28, 107, 86), mirkwoodSwamp);
        greatRiver = new CustomBiomeHeightData(MEBiomeKeys.GREAT_RIVER, -10, MEBiomeDataConfigs.riverModifier);
        add(new Color(99, 138, 186), greatRiver);
        wastePond = new CustomBiomeHeightData(MEBiomeKeys.WASTE_POND, -10, MEBiomeDataConfigs.riverModifier);
        add(new Color(75, 108, 143), wastePond);
        deadMarshes = new CustomBiomeHeightData(MEBiomeKeys.DEAD_MARSHES, 0, MEBiomeDataConfigs.riverModifier.noiseModifier(0f));
        add(new Color(48, 94, 66), deadMarshes);
        deadMarshesWater = new CustomBiomeHeightData(MEBiomeKeys.DEAD_MARSHES_WATER, -10, MEBiomeDataConfigs.riverModifier);
        add(new Color(90, 124, 161), deadMarshesWater);

        add(new Color(78, 99, 122), new CustomBiomeHeightData(MEBiomeKeys.EMYN_MUIL_POND,-4, MEBiomeDataConfigs.emynMuilModifier));
        add(new Color(101, 123, 243), new CustomBiomeHeightData(MEBiomeKeys.FROZEN_OCEAN, -12, MEBiomeDataConfigs.oceanModifier));
        add(new Color(69, 92, 228), new CustomBiomeHeightData(MEBiomeKeys.LONG_LAKE, -10, MEBiomeDataConfigs.oceanModifier));
        add(new Color(72, 109, 114), new CustomBiomeHeightData(MEBiomeKeys.MORGUL_RIVER, -7, MEBiomeDataConfigs.riverModifier));
        add(new Color(79, 91, 161), new CustomBiomeHeightData(MEBiomeKeys.NURN_RIVER, -7, MEBiomeDataConfigs.riverModifier));
        add(new Color(88, 94, 130), new CustomBiomeHeightData(MEBiomeKeys.NURN_SEA, -12, MEBiomeDataConfigs.oceanModifier));
        add(new Color(75, 106, 199), new CustomBiomeHeightData(MEBiomeKeys.OCEAN_COAST, -11, MEBiomeDataConfigs.oceanModifier));
        add(new Color(83, 129, 186), new CustomBiomeHeightData(MEBiomeKeys.RIVER, -6, MEBiomeDataConfigs.smallRiverModifier));
        add(new Color(77, 119, 137), new CustomBiomeHeightData(MEBiomeKeys.FOREST_RIVER, -6, MEBiomeDataConfigs.smallRiverModifier));
        add(new Color(66, 97, 157), new CustomBiomeHeightData(MEBiomeKeys.SEA_OF_RHUN, -8, MEBiomeDataConfigs.oceanModifier));
        add(new Color(20, 79, 50), new CustomBiomeHeightData(MEBiomeKeys.MIRKWOOD_MARSHES, 3, MEBiomeDataConfigs.landModifier));

        // Land Biomes :
        add(new Color(156, 207, 113), new CustomBiomeHeightData(MEBiomeKeys.ANDUIN_VALES, 5, MEBiomeDataConfigs.landModifier));
        add(new Color(133, 178, 95), new CustomBiomeHeightData(MEBiomeKeys.ANDUIN_VALES_FOREST, 6, MEBiomeDataConfigs.landModifier));
        add(new Color(111, 191, 101), new CustomBiomeHeightData(MEBiomeKeys.ANORIEN_RIVERSIDE, 0, MEBiomeDataConfigs.plainsModifier.heightModifier(0.98f).noiseModifier(0.1f)));
        add(new Color(79, 187, 73), new CustomBiomeHeightData(MEBiomeKeys.ANORIEN, 10, MEBiomeDataConfigs.plainsModifier));
        add(new Color(107, 205, 102), new CustomBiomeHeightData(MEBiomeKeys.ANORIEN_FOOTHILLS, 19, MEBiomeDataConfigs.foothillModifier.heightModifier(0.28f)));
        add(new Color(96, 171, 89), new CustomBiomeHeightData(MEBiomeKeys.BARROW_DOWNS, 6, MEBiomeDataConfigs.landModifier));
        add(new Color(108, 214, 157), new CustomBiomeHeightData(MEBiomeKeys.BELERIAND_ISLAND, 5, MEBiomeDataConfigs.landModifier));
        add(new Color(38, 207, 94), new CustomBiomeHeightData(MEBiomeKeys.BELFALAS, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(35, 173, 81), new CustomBiomeHeightData(MEBiomeKeys.BELFALAS_HILLS, 37, MEBiomeDataConfigs.mountainModifier));
        add(new Color(77, 219, 119), new CustomBiomeHeightData(MEBiomeKeys.BELFALAS_BEACH, 0, MEBiomeDataConfigs.plainsModifier.heightModifier(0.98f).noiseModifier(0.1f)));
        add(new Color(65, 148, 60), new CustomBiomeHeightData(MEBiomeKeys.BLACKROOT_VALE, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(76, 175, 117), new CustomBiomeHeightData(MEBiomeKeys.BLUE_MOUNTAINS_FOOTHILLS, 35, MEBiomeDataConfigs.bmModifier.heightModifier(0.23f)));
        add(new Color(178, 183, 210), new CustomBiomeHeightData(MEBiomeKeys.BLUE_MOUNTAINS_BASE, 53, MEBiomeDataConfigs.bmModifier));
        add(new Color(200, 209, 255), new CustomBiomeHeightData(MEBiomeKeys.BLUE_MOUNTAINS, 82, MEBiomeDataConfigs.bmModifier));
        add(new Color(217, 224, 255), new CustomBiomeHeightData(MEBiomeKeys.BLUE_MOUNTAINS_PEAKS, 97, MEBiomeDataConfigs.bmPeaksModifier));
        add(new Color(88, 82, 71), new CustomBiomeHeightData(MEBiomeKeys.BROWN_LANDS, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(180, 136, 119), new CustomBiomeHeightData(MEBiomeKeys.CARADHRAS_BASE, 56, MEBiomeDataConfigs.mountainModifier.heightModifier(0.24f).noiseModifier(1.21f)));
        add(new Color(206, 156, 138), new CustomBiomeHeightData(MEBiomeKeys.CARADHRAS, 79, MEBiomeDataConfigs.mountainModifier.heightModifier(0.34f)));
        add(new Color(237, 179, 158), new CustomBiomeHeightData(MEBiomeKeys.CARADHRAS_PEAKS, 106, MEBiomeDataConfigs.mountainModifier.heightModifier(0.39f)));
        add(new Color(180, 173, 178), new CustomBiomeHeightData(MEBiomeKeys.CELEBDIL_BASE, 54, MEBiomeDataConfigs.mountainModifier.heightModifier(0.24f).noiseModifier(1.21f)));
        add(new Color(189, 185, 189), new CustomBiomeHeightData(MEBiomeKeys.CELEBDIL, 78, MEBiomeDataConfigs.mountainModifier.heightModifier(0.34f)));
        add(new Color(205, 205, 205), new CustomBiomeHeightData(MEBiomeKeys.CELEBDIL_PEAKS, 104, MEBiomeDataConfigs.mountainModifier.heightModifier(0.39f)));
        add(new Color(193, 188, 131), new CustomBiomeHeightData(MEBiomeKeys.CORSAIR_COASTS, 6, MEBiomeDataConfigs.landModifier));
        add(new Color(77, 69, 65), new CustomBiomeHeightData(MEBiomeKeys.DAGORLAD, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(132, 164, 78), new CustomBiomeHeightData(MEBiomeKeys.DALE, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(132, 164, 85), new CustomBiomeHeightData(MEBiomeKeys.DALE_FOREST, 5, MEBiomeDataConfigs.landModifier));
        add(new Color(132, 164, 80), new CustomBiomeHeightData(MEBiomeKeys.DALE_HILL, 27, MEBiomeDataConfigs.landModifier));
        add(new Color(132, 180, 75), new CustomBiomeHeightData(MEBiomeKeys.DALE_CITY, 4, MEBiomeDataConfigs.plainsModifier));
        add(new Color(125, 176, 106), new CustomBiomeHeightData(MEBiomeKeys.DALE_RIVERSIDE, 0, MEBiomeDataConfigs.landModifier.heightModifier(0.98f).noiseModifier(0.1f)));
        add(new Color(18, 26, 19), new CustomBiomeHeightData(MEBiomeKeys.DARK_MIRKWOOD, 6, MEBiomeDataConfigs.mirkwoodModifier));
        add(new Color(26, 45, 28), new CustomBiomeHeightData(MEBiomeKeys.DARK_MIRKWOOD_EDGE, 5, MEBiomeDataConfigs.landModifier));
        add(new Color(166, 191, 114), new CustomBiomeHeightData(MEBiomeKeys.DARK_ANDUIN_VALES, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(44, 39, 51), new CustomBiomeHeightData(MEBiomeKeys.DOL_GULDUR, 11, MEBiomeDataConfigs.foothillModifier));
        add(new Color(53, 45, 66), new CustomBiomeHeightData(MEBiomeKeys.DOL_GULDUR_HILL, 31, MEBiomeDataConfigs.mountainModifier));
        add(new Color(82, 146, 80), new CustomBiomeHeightData(MEBiomeKeys.DORWINION, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(93, 113, 92), new CustomBiomeHeightData(MEBiomeKeys.DORWINION_HILLS, 34, MEBiomeDataConfigs.foothillModifier));
        add(new Color(132, 137, 124), new CustomBiomeHeightData(MEBiomeKeys.DUNLAND_FOOTHILLS, 34, MEBiomeDataConfigs.foothillModifier));
        add(new Color(162, 197, 133), new CustomBiomeHeightData(MEBiomeKeys.EAST_BIGHT, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(101, 89, 80), new CustomBiomeHeightData(MEBiomeKeys.EASTERN_NURN, 5, MEBiomeDataConfigs.landModifier));
        add(new Color(140, 150, 84), new CustomBiomeHeightData(MEBiomeKeys.EASTERN_RHOVANION, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(120, 107, 84), new CustomBiomeHeightData(MEBiomeKeys.EMYN_MUIL_CLIFFS, 57, MEBiomeDataConfigs.emynMuilModifier));
        add(new Color(133, 122, 103), new CustomBiomeHeightData(MEBiomeKeys.EMYN_MUIL, 74, MEBiomeDataConfigs.emynMuilModifier.heightModifier(0.32f)));
        add(new Color(148, 137, 118), new CustomBiomeHeightData(MEBiomeKeys.EMYN_MUIL_PEAKS, 94, MEBiomeDataConfigs.emynMuilModifier.heightModifier(0.53f)));
        add(new Color(117, 164, 109), new CustomBiomeHeightData(MEBiomeKeys.ENEDWAITH, 6, MEBiomeDataConfigs.landModifier));
        add(new Color(185, 185, 187), new CustomBiomeHeightData(MEBiomeKeys.LONELY_MOUNTAIN_BASE, 60, MEBiomeDataConfigs.mountainModifier.heightModifier(0.55f)));
        add(new Color(168, 168, 170), new CustomBiomeHeightData(MEBiomeKeys.LONELY_MOUNTAIN, 73, MEBiomeDataConfigs.mountainModifier.heightModifier(0.65f)));
        add(new Color(151, 151, 153), new CustomBiomeHeightData(MEBiomeKeys.LONELY_MOUNTAIN_PEAKS, 94, MEBiomeDataConfigs.mountainModifier.heightModifier(0.76f)));
        add(new Color(90, 159, 90), new CustomBiomeHeightData(MEBiomeKeys.LONELY_MOUNTAIN_TAIGA, 7, MEBiomeDataConfigs.landModifier));
        add(new Color(106, 155, 104), new CustomBiomeHeightData(MEBiomeKeys.EREGION, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(16, 156, 109), new CustomBiomeHeightData(MEBiomeKeys.ETHIR_ANDUIN, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(54, 75, 12), new CustomBiomeHeightData(MEBiomeKeys.FANGORN, 6, MEBiomeDataConfigs.landModifier));
        add(new Color(121, 131, 112), new CustomBiomeHeightData(MEBiomeKeys.FANGORN_FOOTHILLS, 24, MEBiomeDataConfigs.foothillModifier));
        add(new Color(166, 151, 130), new CustomBiomeHeightData(MEBiomeKeys.FANUIDHOL_BASE, 51, MEBiomeDataConfigs.mountainModifier.heightModifier(0.24f).noiseModifier(1.21f)));
        add(new Color(176, 162, 136), new CustomBiomeHeightData(MEBiomeKeys.FANUIDHOL, 78, MEBiomeDataConfigs.mountainModifier.heightModifier(0.34f)));
        add(new Color(196, 180, 153), new CustomBiomeHeightData(MEBiomeKeys.FANUIDHOL_PEAKS, 102, MEBiomeDataConfigs.mountainModifier.heightModifier(0.39f)));
        add(new Color(236, 236, 236), new CustomBiomeHeightData(MEBiomeKeys.FORODWAITH, 14, MEBiomeDataConfigs.landModifier));
        add(new Color(74, 213, 138), new CustomBiomeHeightData(MEBiomeKeys.GULF_OF_LHUN_CLIFFS, 30, MEBiomeDataConfigs.landModifier.heightModifier(0.76f)));
        add(new Color(222, 227, 191), new CustomBiomeHeightData(MEBiomeKeys.GULF_OF_LHUN_SHORES, 0, MEBiomeDataConfigs.landModifier.heightModifier(0.97f).heightModifier(0.1f).noiseModifier(0.05f)));
        add(new Color(91, 189, 85), new CustomBiomeHeightData(MEBiomeKeys.GONDOR, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(56, 36, 36), new CustomBiomeHeightData(MEBiomeKeys.GORGOROTH, 6, MEBiomeDataConfigs.plainsModifier));
        add(new Color(92, 147, 92), new CustomBiomeHeightData(MEBiomeKeys.GREY_PLAINS, 6, MEBiomeDataConfigs.landModifier));
        add(new Color(125, 113, 113), new CustomBiomeHeightData(MEBiomeKeys.GREY_MOUNTAINS_BASE, 30, MEBiomeDataConfigs.mountainModifier.heightModifier(0.24f)));
        add(new Color(110, 98, 98), new CustomBiomeHeightData(MEBiomeKeys.GREY_MOUNTAINS, 61, MEBiomeDataConfigs.mountainModifier.heightModifier(0.355f)));
        add(new Color(99, 90, 90), new CustomBiomeHeightData(MEBiomeKeys.GREY_MOUNTAINS_PEAKS, 88, MEBiomeDataConfigs.mountainModifier.heightModifier(0.59f).noiseModifier(1.3f)));
        add(new Color(93, 139, 93), new CustomBiomeHeightData(MEBiomeKeys.GUNDABAD_PLAINS, 9, MEBiomeDataConfigs.landModifier));
        add(new Color(204, 196, 113), new CustomBiomeHeightData(MEBiomeKeys.HARAD, 7, MEBiomeDataConfigs.landModifier));
        add(new Color(237, 229, 145), new CustomBiomeHeightData(MEBiomeKeys.HARAD_DESERT, 9, MEBiomeDataConfigs.landModifier));
        add(new Color(180, 214, 121), new CustomBiomeHeightData(MEBiomeKeys.HARONDOR, 6, MEBiomeDataConfigs.landModifier));
        add(new Color(156, 187, 94), new CustomBiomeHeightData(MEBiomeKeys.HILLS_OF_EVENDIM, 35, MEBiomeDataConfigs.foothillModifier));
        add(new Color(110, 161, 99), new CustomBiomeHeightData(MEBiomeKeys.IRON_HILLS_PLAINS, 8, MEBiomeDataConfigs.landModifier));
        add(new Color(109, 175, 109), new CustomBiomeHeightData(MEBiomeKeys.IRON_FOOTHILLS, 25, MEBiomeDataConfigs.foothillModifier));
        add(new Color(189, 170, 168), new CustomBiomeHeightData(MEBiomeKeys.IRON_HILLS_BASE, 32, MEBiomeDataConfigs.mountainModifier.heightModifier(0.48f)));
        add(new Color(174, 144, 141), new CustomBiomeHeightData(MEBiomeKeys.IRON_HILLS, 51, MEBiomeDataConfigs.mountainModifier.heightModifier(0.57f)));
        add(new Color(173, 130, 127), new CustomBiomeHeightData(MEBiomeKeys.IRON_HILLS_PEAKS, 72, MEBiomeDataConfigs.mountainModifier.heightModifier(0.68f)));
        add(new Color(97, 157, 89), new CustomBiomeHeightData(MEBiomeKeys.ISENGARD, 5, MEBiomeDataConfigs.landModifier));
        add(new Color(106, 162, 98), new CustomBiomeHeightData(MEBiomeKeys.ISENGARD_HILL, 32, MEBiomeDataConfigs.landModifier.heightModifier(0.51f)));
        add(new Color(4, 117, 42), new CustomBiomeHeightData(MEBiomeKeys.ITHILIEN, 5, MEBiomeDataConfigs.landModifier));
        add(new Color(51, 100, 67), new CustomBiomeHeightData(MEBiomeKeys.ITHILIEN_WASTES, 5, MEBiomeDataConfigs.landModifier));
        add(new Color(164, 255, 164), new CustomBiomeHeightData(MEBiomeKeys.LAMEDON, 7, MEBiomeDataConfigs.landModifier));
        add(new Color(66, 220, 56), new CustomBiomeHeightData(MEBiomeKeys.LEBENNIN, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(127, 214, 101), new CustomBiomeHeightData(MEBiomeKeys.LEBENNIN_SHORES, 0, MEBiomeDataConfigs.plainsModifier.heightModifier(0.98f).noiseModifier(0.1f)));
        add(new Color(67, 193, 125), new CustomBiomeHeightData(MEBiomeKeys.LINDON, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(67, 133, 100), new CustomBiomeHeightData(MEBiomeKeys.LONG_MARSHES, 3, MEBiomeDataConfigs.landModifier));
        add(new Color(72, 196, 64), new CustomBiomeHeightData(MEBiomeKeys.LOSSARNACH, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(107, 195, 92), new CustomBiomeHeightData(MEBiomeKeys.LOSSARNACH_VALLEY, 7, MEBiomeDataConfigs.landModifier));
        add(new Color(205, 206, 96), new CustomBiomeHeightData(MEBiomeKeys.LORIEN_EDGE, 5, MEBiomeDataConfigs.landModifier));
        add(new Color(221, 216, 28), new CustomBiomeHeightData(MEBiomeKeys.LOTHLORIEN, 6, MEBiomeDataConfigs.landModifier));
        add(new Color(145, 164, 109), new CustomBiomeHeightData(MEBiomeKeys.MINHIRIATH, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(10, 54, 15), new CustomBiomeHeightData(MEBiomeKeys.MIRKWOOD, 8, MEBiomeDataConfigs.mirkwoodModifier));
        add(new Color(18, 73, 24), new CustomBiomeHeightData(MEBiomeKeys.MIRKWOOD_EDGE, 5, MEBiomeDataConfigs.landModifier));
        add(new Color(46, 83, 50), new CustomBiomeHeightData(MEBiomeKeys.MIRKWOOD_FOOTHILLS, 32, MEBiomeDataConfigs.foothillModifier.heightModifier(0.28f)));
        add(new Color(76, 85, 77), new CustomBiomeHeightData(MEBiomeKeys.MIRKWOOD_MOUNTAINS_BASE, 56, MEBiomeDataConfigs.mountainModifier));
        add(new Color(90, 101, 91), new CustomBiomeHeightData(MEBiomeKeys.MIRKWOOD_MOUNTAINS,80,  MEBiomeDataConfigs.mountainModifier));
        add(new Color(108, 122, 110), new CustomBiomeHeightData(MEBiomeKeys.MIRKWOOD_MOUNTAINS_PEAKS, 120, MEBiomeDataConfigs.mountainModifier));
        add(new Color(129, 131, 125), new CustomBiomeHeightData(MEBiomeKeys.MISTY_MOUNTAINS_BASE, 37, MEBiomeDataConfigs.mountainModifier.heightModifier(0.24f)));
        add(new Color(121, 121, 121), new CustomBiomeHeightData(MEBiomeKeys.MISTY_MOUNTAINS, 70, MEBiomeDataConfigs.mountainModifier.heightModifier(0.34f)));
        add(new Color(110, 110, 110), new CustomBiomeHeightData(MEBiomeKeys.MISTY_MOUNTAINS_PEAKS, 90, MEBiomeDataConfigs.mountainModifier.heightModifier(0.39f)));
        add(new Color(60, 42, 42), new CustomBiomeHeightData(MEBiomeKeys.MORDOR, 7, MEBiomeDataConfigs.landModifier));
        add(new Color(45, 42, 42), new CustomBiomeHeightData(MEBiomeKeys.MORDOR_MOUNTAINS_FOOTHILLS, 36, MEBiomeDataConfigs.mountainModifier.heightModifier(0.33f)));
        add(new Color(36, 31, 31), new CustomBiomeHeightData(MEBiomeKeys.MORDOR_MOUNTAINS, 73, MEBiomeDataConfigs.mountainModifier));
        add(new Color(26, 23, 23), new CustomBiomeHeightData(MEBiomeKeys.MORDOR_MOUNTAINS_PEAKS, 93, MEBiomeDataConfigs.mountainModifier.heightModifier(0.38f)));
        add(new Color(88, 82, 71), new CustomBiomeHeightData(MEBiomeKeys.MORDOR_WASTES, 3, MEBiomeDataConfigs.landModifier));
        add(new Color(45, 58, 44), new CustomBiomeHeightData(MEBiomeKeys.MORGUL_VALE, 3, MEBiomeDataConfigs.landModifier));
        add(new Color(99, 94, 94), new CustomBiomeHeightData(MEBiomeKeys.MOUNT_GUNDABAD, 87, MEBiomeDataConfigs.mountainModifier.heightModifier(0.59f)));
        add(new Color(96, 39, 13), new CustomBiomeHeightData(MEBiomeKeys.MOUNT_DOOM, 90, MEBiomeDataConfigs.mountainModifier.heightModifier(0.36f).noiseModifier(1.0f).expansionWeight(new byte[]{2, 3})));
        add(new Color(97, 155, 89), new CustomBiomeHeightData(MEBiomeKeys.NAN_CURUNIR, 5, MEBiomeDataConfigs.landModifier));
        add(new Color(104, 146, 196), new CustomBiomeHeightData(MEBiomeKeys.NEN_HITHOEL, -4, MEBiomeDataConfigs.riverModifier));
        add(new Color(126, 158, 57), new CustomBiomeHeightData(MEBiomeKeys.NEN_HITHOEL_FOREST, 4, MEBiomeDataConfigs.landModifier.heightModifier(0.13f)));
        add(new Color(142, 179, 64), new CustomBiomeHeightData(MEBiomeKeys.NEN_HITHOEL_SHORES, 1, MEBiomeDataConfigs.landModifier.heightModifier(0.98f).noiseModifier(0.1f)));
        add(new Color(43, 158, 120), new CustomBiomeHeightData(MEBiomeKeys.NINDALF, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(177, 188, 154), new CustomBiomeHeightData(MEBiomeKeys.NORTH_DOWNS, 41, MEBiomeDataConfigs.foothillModifier.noiseModifier(1.8f)));
        add(new Color(106, 127, 62), new CustomBiomeHeightData(MEBiomeKeys.NORTHERN_DUNLAND, 5, MEBiomeDataConfigs.landModifier));
        add(new Color(26, 71, 49), new CustomBiomeHeightData(MEBiomeKeys.NORTHERN_MIRKWOOD_MARSHES, 3, MEBiomeDataConfigs.landModifier));
        add(new Color(16, 60, 37), new CustomBiomeHeightData(MEBiomeKeys.NORTHERN_MIRKWOOD_SWAMP, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(160, 165, 149), new CustomBiomeHeightData(MEBiomeKeys.NORTHERN_WASTELANDS, 6, MEBiomeDataConfigs.landModifier));
        add(new Color(74, 77, 51), new CustomBiomeHeightData(MEBiomeKeys.NURN, 5, MEBiomeDataConfigs.landModifier));
        add(new Color(69, 59, 50), new CustomBiomeHeightData(MEBiomeKeys.NURN_EDGE, 6, MEBiomeDataConfigs.landModifier));
        add(new Color(73, 82, 53), new CustomBiomeHeightData(MEBiomeKeys.OLD_ANGMAR, 6, MEBiomeDataConfigs.landModifier.noiseModifier(0.6f)));
        add(new Color(77, 87, 54), new CustomBiomeHeightData(MEBiomeKeys.OLD_ANGMAR_FOREST, 7, MEBiomeDataConfigs.landModifier));
        add(new Color(83, 93, 64), new CustomBiomeHeightData(MEBiomeKeys.OLD_ANGMAR_COLD_HILL, 17, MEBiomeDataConfigs.landModifier.noiseModifier(0.5f)));
        add(new Color(106, 114, 89), new CustomBiomeHeightData(MEBiomeKeys.OLD_ANGMAR_FROZEN_HILL, 25, MEBiomeDataConfigs.landModifier));
        add(new Color(200, 238, 128), new CustomBiomeHeightData(MEBiomeKeys.OLD_ARTHEDAIN, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(164, 196, 102), new CustomBiomeHeightData(MEBiomeKeys.OLD_ARTHEDAIN_FOREST, 5, MEBiomeDataConfigs.landModifier));
        add(new Color(200, 224, 127), new CustomBiomeHeightData(MEBiomeKeys.OLD_ARTHEDAIN_MEADOW, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(193, 222, 140), new CustomBiomeHeightData(MEBiomeKeys.OLD_ARTHEDAIN_FOOTHILL, 23, MEBiomeDataConfigs.foothillModifier.heightModifier(0.2f)));
        add(new Color(156, 185, 97), new CustomBiomeHeightData(MEBiomeKeys.OLD_CARDOLAN, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(142, 169, 86), new CustomBiomeHeightData(MEBiomeKeys.OLD_CARDOLAN_FOREST, 5, MEBiomeDataConfigs.landModifier));
        add(new Color(156, 182, 104), new CustomBiomeHeightData(MEBiomeKeys.OLD_CARDOLAN_HILL, 27, MEBiomeDataConfigs.landModifier));
        add(new Color(115, 135, 74), new CustomBiomeHeightData(MEBiomeKeys.OLD_RHUDAUR, 5, MEBiomeDataConfigs.landModifier));
        add(new Color(97, 115, 60), new CustomBiomeHeightData(MEBiomeKeys.OLD_RHUDAUR_FOREST, 6, MEBiomeDataConfigs.landModifier));
        add(new Color(113, 131, 77), new CustomBiomeHeightData(MEBiomeKeys.OLD_RHUDAUR_HILL, 28, MEBiomeDataConfigs.landModifier));
        add(new Color(65, 160, 59), new CustomBiomeHeightData(MEBiomeKeys.OSGILIATH, 4, MEBiomeDataConfigs.plainsModifier));
        add(new Color(88, 204, 81), new CustomBiomeHeightData(MEBiomeKeys.PELENNOR_FIELDS, 4, MEBiomeDataConfigs.plainsModifier));
        add(new Color(172, 176, 113), new CustomBiomeHeightData(MEBiomeKeys.RHUN, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(155, 193, 160), new CustomBiomeHeightData(MEBiomeKeys.HIGH_MOOR_VALE, 3, MEBiomeDataConfigs.landModifier.noiseModifier(0.1f).heightModifier(0.99f)));
        add(new Color(137, 171, 142), new CustomBiomeHeightData(MEBiomeKeys.HIGH_MOOR, 36, MEBiomeDataConfigs.landModifier.noiseModifier(0.3f).heightModifier(0.66f)));
        add(new Color(126, 157, 130), new CustomBiomeHeightData(MEBiomeKeys.HIGH_MOOR_HILLS, 43, MEBiomeDataConfigs.landModifier.noiseModifier(0.4f).heightModifier(0.34f)));
        add(new Color(183, 229, 102), new CustomBiomeHeightData(MEBiomeKeys.ROHAN, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(149, 173, 95), new CustomBiomeHeightData(MEBiomeKeys.SARN_GEBIR_SHORES, 5, MEBiomeDataConfigs.landModifier.heightModifier(0.98f).noiseModifier(0.1f)));
        add(new Color(136, 158, 87), new CustomBiomeHeightData(MEBiomeKeys.SARN_GEBIR_WILDLANDS, 11, MEBiomeDataConfigs.landModifier.heightModifier(0.67f)));
        add(new Color(84, 217, 70), new CustomBiomeHeightData(MEBiomeKeys.SHIRE, 6, MEBiomeDataConfigs.shireModifier));
        add(new Color(75, 184, 64), new CustomBiomeHeightData(MEBiomeKeys.SHIRE_EDGE, 6, MEBiomeDataConfigs.shireModifier));
        add(new Color(61, 152, 52), new CustomBiomeHeightData(MEBiomeKeys.SHIRE_FOREST, 7, MEBiomeDataConfigs.shireModifier));
        add(new Color(83, 161, 76), new CustomBiomeHeightData(MEBiomeKeys.SHIRE_HILLS, 15, MEBiomeDataConfigs.foothillModifier));
        add(new Color(67, 168, 57), new CustomBiomeHeightData(MEBiomeKeys.SHIRE_WOODS, 7, MEBiomeDataConfigs.shireModifier));
        add(new Color(154, 147, 57), new CustomBiomeHeightData(MEBiomeKeys.SOUTHEAST_RHOVANION, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(121, 186, 111), new CustomBiomeHeightData(MEBiomeKeys.SOUTHERN_DUNLAND, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(206, 179, 156), new CustomBiomeHeightData(MEBiomeKeys.SOUTHERN_FOROCHEL, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(39, 37, 37), new CustomBiomeHeightData(MEBiomeKeys.SOUTHERN_EPHEL_DUATH, 71, MEBiomeDataConfigs.mountainModifier));
        add(new Color(49, 46, 46), new CustomBiomeHeightData(MEBiomeKeys.SOUTHERN_EPHEL_DUATH_BASE, 36, MEBiomeDataConfigs.mountainModifier.heightModifier(0.33f)));
        add(new Color(28, 26, 26), new CustomBiomeHeightData(MEBiomeKeys.SOUTHERN_EPHEL_DUATH_PEAKS, 91, MEBiomeDataConfigs.mountainModifier.heightModifier(0.38f)));
        add(new Color(126, 149, 79), new CustomBiomeHeightData(MEBiomeKeys.THE_ANGLE, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(48, 109, 42), new CustomBiomeHeightData(MEBiomeKeys.THE_OLD_FOREST, 7, MEBiomeDataConfigs.landModifier));
        add(new Color(152, 174, 113), new CustomBiomeHeightData(MEBiomeKeys.THE_WOLD, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(168, 215, 163), new CustomBiomeHeightData(MEBiomeKeys.THE_WHITE_DOWNS, 33, MEBiomeDataConfigs.foothillModifier.noiseModifier(1.3f)));
        add(new Color(156, 150, 150), new CustomBiomeHeightData(MEBiomeKeys.TOLFALAS, 13, MEBiomeDataConfigs.landModifier));
        add(new Color(58, 54, 46), new CustomBiomeHeightData(MEBiomeKeys.TOROGWAITH, 8, MEBiomeDataConfigs.landModifier));
        add(new Color(40, 66, 42), new CustomBiomeHeightData(MEBiomeKeys.TROLLSHAWS, 8, MEBiomeDataConfigs.landModifier));
        add(new Color(73, 46, 46), new CustomBiomeHeightData(MEBiomeKeys.UDUN, 5, MEBiomeDataConfigs.landModifier));
        add(new Color(225, 192, 133), new CustomBiomeHeightData(MEBiomeKeys.UMBAR, 7, MEBiomeDataConfigs.landModifier));
        add(new Color(24, 60, 28), new CustomBiomeHeightData(MEBiomeKeys.WEBBED_WOODS, 8, MEBiomeDataConfigs.mirkwoodModifier));
        add(new Color(138, 134, 112), new CustomBiomeHeightData(MEBiomeKeys.WITHERED_HEATH, 8, MEBiomeDataConfigs.landModifier));
        add(new Color(198, 237, 192), new CustomBiomeHeightData(MEBiomeKeys.WHITE_MOUNTAINS_BASE, 47, MEBiomeDataConfigs.mountainModifier.noiseModifier(0.9f).heightModifier(0.27f)));
        add(new Color(219, 245, 215), new CustomBiomeHeightData(MEBiomeKeys.WHITE_MOUNTAINS, 68, MEBiomeDataConfigs.mountainModifier.noiseModifier(1.05f).heightModifier(0.46f)));
        add(new Color(242, 255, 240), new CustomBiomeHeightData(MEBiomeKeys.WHITE_MOUNTAINS_PEAKS, 89, MEBiomeDataConfigs.mountainModifier.heightModifier(0.57f)));
        add(new Color(22, 102, 31), new CustomBiomeHeightData(MEBiomeKeys.WOODLAND_REALM, 5, MEBiomeDataConfigs.mountainModifier));
        add(new Color(49, 107, 46), new CustomBiomeHeightData(MEBiomeKeys.WOODLAND_FOOTHILLS, 37, MEBiomeDataConfigs.mountainModifier.noiseModifier(0.47f).heightModifier(0.47f)));
        add(new Color(79, 124, 76), new CustomBiomeHeightData(MEBiomeKeys.WOODLAND_HILLS, 63, MEBiomeDataConfigs.mountainModifier.noiseModifier(0.62f).heightModifier(0.76f)));

        coastalBiomes.add(MEBiomeKeys.GULF_OF_LHUN_CLIFFS);
        coastalBiomes.add(MEBiomeKeys.GULF_OF_LHUN_SHORES);

        riverbiomes.add(MEBiomeKeys.RIVER);
        riverbiomes.add(MEBiomeKeys.NURN_RIVER);
        riverbiomes.add(MEBiomeKeys.FROZEN_RIVER);

        waterBiomes.add(MEBiomeKeys.BELFALAS_BEACH);
        waterBiomes.add(MEBiomeKeys.DEAD_MARSHES_WATER);
        waterBiomes.add(MEBiomeKeys.EMYN_MUIL_POND);
        waterBiomes.add(MEBiomeKeys.FROZEN_POND);
        waterBiomes.add(MEBiomeKeys.FROZEN_OCEAN);
        waterBiomes.add(MEBiomeKeys.LONG_LAKE);
        waterBiomes.add(MEBiomeKeys.NURN_RIVER);
        waterBiomes.add(MEBiomeKeys.NURN_SEA);
        waterBiomes.add(MEBiomeKeys.OCEAN);
        waterBiomes.add(MEBiomeKeys.OCEAN_COAST);
        waterBiomes.add(MEBiomeKeys.RIVER);
        waterBiomes.add(MEBiomeKeys.SEA_OF_RHUN);

        anduinWaterBiomes.add(MEBiomeKeys.GREAT_RIVER);
        anduinWaterBiomes.add(MEBiomeKeys.NEN_HITHOEL);
        anduinWaterBiomes.add(MEBiomeKeys.NEN_HITHOEL_SHORES);
        anduinWaterBiomes.add(MEBiomeKeys.NEN_HITHOEL_FOREST);
        anduinWaterBiomes.add(MEBiomeKeys.EMYN_MUIL_CLIFFS);

        frozenBiomes.add(MEBiomeKeys.NORTHERN_WASTELANDS);
        frozenBiomes.add(MEBiomeKeys.SOUTHERN_FOROCHEL);
        frozenBiomes.add(MEBiomeKeys.FORODWAITH);

        mirkwoodSwampBiomes.add(MEBiomeKeys.DARK_MIRKWOOD);
        mirkwoodSwampBiomes.add(MEBiomeKeys.DARK_MIRKWOOD_EDGE);
        mirkwoodSwampBiomes.add(MEBiomeKeys.DARK_ANDUIN_VALES);
        mirkwoodSwampBiomes.add(MEBiomeKeys.DOL_GULDUR);
        mirkwoodSwampBiomes.add(MEBiomeKeys.MIRKWOOD);
        mirkwoodSwampBiomes.add(MEBiomeKeys.MIRKWOOD_EDGE);

        oasisBiomes.add(MEBiomeKeys.CORSAIR_COASTS);
        oasisBiomes.add(MEBiomeKeys.HARONDOR);
        oasisBiomes.add(MEBiomeKeys.HARAD);
        oasisBiomes.add(MEBiomeKeys.HARAD_DESERT);
        oasisBiomes.add(MEBiomeKeys.UMBAR);

        wastePondBiomes.add(MEBiomeKeys.DAGORLAD);
        wastePondBiomes.add(MEBiomeKeys.GORGOROTH);
        wastePondBiomes.add(MEBiomeKeys.MORDOR);
        wastePondBiomes.add(MEBiomeKeys.MORDOR_MOUNTAINS_FOOTHILLS);
        wastePondBiomes.add(MEBiomeKeys.SOUTHERN_EPHEL_DUATH_BASE);
        wastePondBiomes.add(MEBiomeKeys.MORDOR_WASTES);
        wastePondBiomes.add(MEBiomeKeys.MORGUL_RIVER);
        wastePondBiomes.add(MEBiomeKeys.MORGUL_VALE);
        wastePondBiomes.add(MEBiomeKeys.EASTERN_NURN);
        wastePondBiomes.add(MEBiomeKeys.NURN);
        wastePondBiomes.add(MEBiomeKeys.NURN_EDGE);
        wastePondBiomes.add(MEBiomeKeys.TOROGWAITH);
        wastePondBiomes.add(MEBiomeKeys.UDUN);

        deadMarshesBiomes.add(MEBiomeKeys.DEAD_MARSHES);
    }

    public static CustomBiomeHeightData getBiomeByKey(RegistryEntry<Biome> biome) {
        if(biome.getKey().isPresent()){
            Identifier biomeId = biome.getKey().get().getValue();
            return biomeHashMap.values().stream().filter(
                    b-> b.getBiomeKey().getValue().equals(biomeId)
            ).findFirst().orElse(defaultBiome);
        }
        return defaultBiome;
    }
}
