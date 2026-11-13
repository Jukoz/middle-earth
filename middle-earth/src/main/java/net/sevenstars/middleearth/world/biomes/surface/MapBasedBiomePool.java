package net.sevenstars.middleearth.world.biomes.surface;

import net.sevenstars.middleearth.world.biomes.BiomeDataConfigsME;
import net.sevenstars.middleearth.world.biomes.BiomeKeyRegistryME;
import net.sevenstars.middleearth.world.chunkgen.map.MiddleEarthHeightMap;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Converts PNG pixel color to a BiomeKey reference.
 */
public class MapBasedBiomePool {
    private static HashMap<Color, MapBasedCustomBiome> biomeHashMap;
    public static List<RegistryKey<Biome>> coastalBiomes = new ArrayList<>();
    public static List<RegistryKey<Biome>> waterBiomes = new ArrayList<>();
    public static List<RegistryKey<Biome>> riverbiomes = new ArrayList<>();
    public static List<RegistryKey<Biome>> frozenBiomes = new ArrayList<>();
    public static List<RegistryKey<Biome>> wastePondBiomes = new ArrayList<>();
    public static List<RegistryKey<Biome>> mirkwoodSwampBiomes = new ArrayList<>();
    public static List<RegistryKey<Biome>> oasisBiomes = new ArrayList<>();
    public static List<RegistryKey<Biome>> anduinWaterBiomes = new ArrayList<>();
    public static List<RegistryKey<Biome>> deadMarshesBiomes = new ArrayList<>();
    public static List<RegistryKey<Biome>> mangrovePondBiomes = new ArrayList<>();

    public static Color DEFAULT_COLOR = new Color(0x375ac3);
    public static MapBasedCustomBiome defaultBiome;
    public static MapBasedCustomBiome oceanCoast;
    public static MapBasedCustomBiome frozenPond;
    public static MapBasedCustomBiome oasis;
    public static MapBasedCustomBiome pond;
    public static MapBasedCustomBiome greatRiver;
    public static MapBasedCustomBiome wastePond;
    public static MapBasedCustomBiome mirkwoodSwamp;
    public static MapBasedCustomBiome deadMarshes;
    public static MapBasedCustomBiome deadMarshesWater;
    public static MapBasedCustomBiome mangrovePond;

    public static void add(Color color, MapBasedCustomBiome biome) {
        biomeHashMap.put(color, biome);
    }

    public static MapBasedCustomBiome getBiomeByColor(Integer rgb){
        if(biomeHashMap.containsKey(new Color(rgb)))
            return biomeHashMap.get(new Color(rgb));
        throw new RuntimeException("MeBiomes::No registered biome has %s for color".formatted(rgb));
    }

    public static void loadBiomes() {
        biomeHashMap = new HashMap<>();

        SubBiomes.loadSubBiomes();
        defaultBiome = new MapBasedCustomBiome(BiomeKeyRegistryME.OCEAN, -35, BiomeDataConfigsME.oceanModifier);
        add(DEFAULT_COLOR, defaultBiome);

        oceanCoast = new MapBasedCustomBiome(BiomeKeyRegistryME.OCEAN_COAST, -15, BiomeDataConfigsME.oceanModifier);
        add(new Color(0x4b6ac7), oceanCoast);

        // Ponds
        frozenPond = new MapBasedCustomBiome(BiomeKeyRegistryME.FROZEN_POND, -10, BiomeDataConfigsME.riverModifier);
        add(new Color(0x68a8de), frozenPond);
        oasis = new MapBasedCustomBiome(BiomeKeyRegistryME.OASIS, -10, BiomeDataConfigsME.riverModifier);
        add(new Color(0x68a8de), oasis);
        pond = new MapBasedCustomBiome(BiomeKeyRegistryME.POND, -10, BiomeDataConfigsME.riverModifier);
        add(new Color(0x6e9ada), pond);
        mirkwoodSwamp = new MapBasedCustomBiome(BiomeKeyRegistryME.MIRKWOOD_SWAMP, -2, BiomeDataConfigsME.landModifier.heightModifier(0.1));
        add(new Color(0x1c6b56), mirkwoodSwamp);
        greatRiver = new MapBasedCustomBiome(BiomeKeyRegistryME.GREAT_RIVER, -10, BiomeDataConfigsME.riverModifier);
        add(new Color(0x638aba), greatRiver);
        wastePond = new MapBasedCustomBiome(BiomeKeyRegistryME.WASTE_POND, -10, BiomeDataConfigsME.riverModifier);
        add(new Color(0x4b6c8f), wastePond);
        deadMarshes = new MapBasedCustomBiome(BiomeKeyRegistryME.DEAD_MARSHES, 0, BiomeDataConfigsME.riverModifier.noiseModifier(0f));
        add(new Color(0x305e42), deadMarshes);
        deadMarshesWater = new MapBasedCustomBiome(BiomeKeyRegistryME.DEAD_MARSHES_WATER, -10, BiomeDataConfigsME.riverModifier);
        add(new Color(0x5a7ca1), deadMarshesWater);
        mangrovePond = new MapBasedCustomBiome(BiomeKeyRegistryME.MANGROVE_POND, -3, BiomeDataConfigsME.riverModifier);
        add(new Color(0x5ba67d), mangrovePond);


        add(new Color(0x4e637a), new MapBasedCustomBiome(BiomeKeyRegistryME.EMYN_MUIL_POND,-4, BiomeDataConfigsME.emynMuilModifier));
        add(new Color(0x657bf3), new MapBasedCustomBiome(BiomeKeyRegistryME.FROZEN_OCEAN, -26, BiomeDataConfigsME.oceanModifier));
        add(new Color(0x4250db), new MapBasedCustomBiome(BiomeKeyRegistryME.LONG_LAKE, -26, BiomeDataConfigsME.longLakeModifier));
        add(new Color(0x455ce4), new MapBasedCustomBiome(BiomeKeyRegistryME.LONG_LAKE_SHORES, -14, BiomeDataConfigsME.longLakeModifier));
        add(new Color(0x486d72), new MapBasedCustomBiome(BiomeKeyRegistryME.MORGUL_RIVER, -13, BiomeDataConfigsME.riverModifier));
        add(new Color(0x4f5ba1), new MapBasedCustomBiome(BiomeKeyRegistryME.NURN_RIVER, -13, BiomeDataConfigsME.riverModifier));
        add(new Color(0x585e82), new MapBasedCustomBiome(BiomeKeyRegistryME.NURN_SEA, -12, BiomeDataConfigsME.oceanModifier));
        add(new Color(0x4b6ac7), new MapBasedCustomBiome(BiomeKeyRegistryME.OCEAN_COAST, -11, BiomeDataConfigsME.oceanModifier));
        add(new Color(0x5381ba), new MapBasedCustomBiome(BiomeKeyRegistryME.RIVER, -13, BiomeDataConfigsME.smallRiverModifier));
        add(new Color(0x4d7789), new MapBasedCustomBiome(BiomeKeyRegistryME.MIRKWOOD_RIVER, -9, BiomeDataConfigsME.smallRiverModifier));
        add(new Color(0x42619d), new MapBasedCustomBiome(BiomeKeyRegistryME.SEA_OF_RHUN, -8, BiomeDataConfigsME.oceanModifier));
        add(new Color(0x144f32), new MapBasedCustomBiome(BiomeKeyRegistryME.MIRKWOOD_MARSHES, 3, BiomeDataConfigsME.landModifier));

        // Land Biomes :
        add(new Color(0x9ccf71), new MapBasedCustomBiome(BiomeKeyRegistryME.ANDUIN_VALES, 5, BiomeDataConfigsME.landModifier));
        add(new Color(0x85b25f), new MapBasedCustomBiome(BiomeKeyRegistryME.ANDUIN_VALES_FOREST, 6, BiomeDataConfigsME.landModifier));
        add(new Color(0x6fbf65), new MapBasedCustomBiome(BiomeKeyRegistryME.ANORIEN_RIVERSIDE, 0, BiomeDataConfigsME.plainsModifier.heightModifier(0.98f).noiseModifier(0.1f)));
        add(new Color(0x4fbb49), new MapBasedCustomBiome(BiomeKeyRegistryME.ANORIEN, 10, BiomeDataConfigsME.plainsModifier));
        add(new Color(0x6bcd66), new MapBasedCustomBiome(BiomeKeyRegistryME.ANORIEN_FOOTHILLS, 19, BiomeDataConfigsME.foothillModifier.heightModifier(0.28f)));
        add(new Color(0x60ab59), new MapBasedCustomBiome(BiomeKeyRegistryME.BARROW_DOWNS, 6, BiomeDataConfigsME.landModifier));
        add(new Color(0x6cd69d), new MapBasedCustomBiome(BiomeKeyRegistryME.BELERIAND_ISLAND, 5, BiomeDataConfigsME.landModifier));
        add(new Color(0x26cf5e), new MapBasedCustomBiome(BiomeKeyRegistryME.BELFALAS, 4, BiomeDataConfigsME.landModifier));
        add(new Color(0x22C457), new MapBasedCustomBiome(BiomeKeyRegistryME.BELFALAS_FOREST, 6, BiomeDataConfigsME.landModifier));
        add(new Color(0x23ad51), new MapBasedCustomBiome(BiomeKeyRegistryME.BELFALAS_HILLS, 37, BiomeDataConfigsME.mountainModifier));
        add(new Color(0x4ddb77), new MapBasedCustomBiome(BiomeKeyRegistryME.BELFALAS_BEACH, 0, BiomeDataConfigsME.plainsModifier.heightModifier(0.98f).noiseModifier(0.1f)));
        add(new Color(0x41943c), new MapBasedCustomBiome(BiomeKeyRegistryME.BLACKROOT_VALE, 4, BiomeDataConfigsME.landModifier));
        add(new Color(0x3C8C37), new MapBasedCustomBiome(BiomeKeyRegistryME.BLACKROOT_FOREST, 7, BiomeDataConfigsME.landModifier));
        add(new Color(0x4caf75), new MapBasedCustomBiome(BiomeKeyRegistryME.BLUE_MOUNTAINS_FOOTHILLS, 35, BiomeDataConfigsME.bmModifier.heightModifier(0.23f)));
        add(new Color(0xb2b7d2), new MapBasedCustomBiome(BiomeKeyRegistryME.BLUE_MOUNTAINS_BASE, 53, BiomeDataConfigsME.bmModifier));
        add(new Color(0xc8d1ff), new MapBasedCustomBiome(BiomeKeyRegistryME.BLUE_MOUNTAINS, 67,  BiomeDataConfigsME.bmModifier));
        add(new Color(0xd9e0ff), new MapBasedCustomBiome(BiomeKeyRegistryME.BLUE_MOUNTAINS_HIGH_LANDS, 83, BiomeDataConfigsME.bmModifier.heightModifier(0.63f)));
        add(new Color(0xedf0ff), new MapBasedCustomBiome(BiomeKeyRegistryME.BLUE_MOUNTAINS_PEAKS, 89, BiomeDataConfigsME.bmPeaksModifier.heightModifier(0.83).noiseModifier(0.05)));
        add(new Color(0xA2C7B9), new MapBasedCustomBiome(BiomeKeyRegistryME.BLUE_MOUNTAINS_WOODS, 41, BiomeDataConfigsME.bmModifier));
        add(new Color(0x585247), new MapBasedCustomBiome(BiomeKeyRegistryME.BROWN_LANDS, 4, BiomeDataConfigsME.landModifier));
        add(new Color(0xb48877), new MapBasedCustomBiome(BiomeKeyRegistryME.CARADHRAS_BASE, 56, BiomeDataConfigsME.mountainModifier.heightModifier(0.24f).noiseModifier(1.21f)));
        add(new Color(0xce9c8a), new MapBasedCustomBiome(BiomeKeyRegistryME.CARADHRAS, 79, BiomeDataConfigsME.mountainModifier.heightModifier(0.34f)));
        add(new Color(0xedb39e), new MapBasedCustomBiome(BiomeKeyRegistryME.CARADHRAS_PEAKS, 106, BiomeDataConfigsME.mountainModifier.heightModifier(0.39f)));
        add(new Color(0xb4adb2), new MapBasedCustomBiome(BiomeKeyRegistryME.CELEBDIL_BASE, 54, BiomeDataConfigsME.mountainModifier.heightModifier(0.24f).noiseModifier(1.21f)));
        add(new Color(0xbdb9bd), new MapBasedCustomBiome(BiomeKeyRegistryME.CELEBDIL, 78, BiomeDataConfigsME.mountainModifier.heightModifier(0.34f)));
        add(new Color(0xcdcdcd), new MapBasedCustomBiome(BiomeKeyRegistryME.CELEBDIL_PEAKS, 104, BiomeDataConfigsME.mountainModifier.heightModifier(0.39f)));
        add(new Color(0xc1bc83), new MapBasedCustomBiome(BiomeKeyRegistryME.CORSAIR_COASTS, 6, BiomeDataConfigsME.landModifier));
        add(new Color(0x4d4541), new MapBasedCustomBiome(BiomeKeyRegistryME.DAGORLAD, 4, BiomeDataConfigsME.landModifier));
        add(new Color(0x84a44e), new MapBasedCustomBiome(BiomeKeyRegistryME.DALE, 4, BiomeDataConfigsME.landModifier));
        add(new Color(0x84a455), new MapBasedCustomBiome(BiomeKeyRegistryME.DALE_FOREST, 5, BiomeDataConfigsME.landModifier));
        add(new Color(0x84a450), new MapBasedCustomBiome(BiomeKeyRegistryME.DALE_MEADOW, 27, BiomeDataConfigsME.landModifier));
        add(new Color(0x84b44b), new MapBasedCustomBiome(BiomeKeyRegistryME.DALE_CITY, 4, BiomeDataConfigsME.plainsModifier));
        add(new Color(0x7db06a), new MapBasedCustomBiome(BiomeKeyRegistryME.DALE_RIVERSIDE, 0, BiomeDataConfigsME.landModifier.heightModifier(0.98f).noiseModifier(0.1f)));
        add(new Color(0x121a13), new MapBasedCustomBiome(BiomeKeyRegistryME.DARK_MIRKWOOD, 6, BiomeDataConfigsME.mirkwoodModifier));
        add(new Color(0x1a2d1c), new MapBasedCustomBiome(BiomeKeyRegistryME.DARK_MIRKWOOD_EDGE, 5, BiomeDataConfigsME.landModifier));
        add(new Color(0xa6bf72), new MapBasedCustomBiome(BiomeKeyRegistryME.DARK_ANDUIN_VALES, 4, BiomeDataConfigsME.landModifier));
        add(new Color(0x759675), new MapBasedCustomBiome(BiomeKeyRegistryME.DESOLATED_LANDS, 9, BiomeDataConfigsME.landModifier));
        add(new Color(0x2c2733), new MapBasedCustomBiome(BiomeKeyRegistryME.DOL_GULDUR, 11, BiomeDataConfigsME.foothillModifier));
        add(new Color(0x352d42), new MapBasedCustomBiome(BiomeKeyRegistryME.DOL_GULDUR_HILL, 31, BiomeDataConfigsME.mountainModifier));
        add(new Color(0x529250), new MapBasedCustomBiome(BiomeKeyRegistryME.DORWINION, 4, BiomeDataConfigsME.foothillModifier.noiseModifier(0.71f)));
        add(new Color(0x467c44), new MapBasedCustomBiome(BiomeKeyRegistryME.DORWINION_LAVENDER_FIELD, 4, BiomeDataConfigsME.landModifier));
        add(new Color(0x5d715c), new MapBasedCustomBiome(BiomeKeyRegistryME.DORWINION_HILLS, 34, BiomeDataConfigsME.foothillModifier.noiseModifier(1.32f)));
        add(new Color(0x84897c), new MapBasedCustomBiome(BiomeKeyRegistryME.DUNLAND_FOOTHILLS, 34, BiomeDataConfigsME.foothillModifier));
        add(new Color(0x899478), new MapBasedCustomBiome(BiomeKeyRegistryME.DUNLAND_HILLS, 18, BiomeDataConfigsME.foothillModifier));
        add(new Color(0xa2c585), new MapBasedCustomBiome(BiomeKeyRegistryME.EAST_BIGHT, 4, BiomeDataConfigsME.landModifier));
        add(new Color(0x655950), new MapBasedCustomBiome(BiomeKeyRegistryME.EASTERN_NURN, 5, BiomeDataConfigsME.landModifier));
        add(new Color(0x8c9654), new MapBasedCustomBiome(BiomeKeyRegistryME.EASTERN_RHOVANION, 4, BiomeDataConfigsME.landModifier));
        add(new Color(0x81914E), new MapBasedCustomBiome(BiomeKeyRegistryME.EASTERN_RHOVANION_FOREST, 5, BiomeDataConfigsME.landModifier));
        add(new Color(0x786b54), new MapBasedCustomBiome(BiomeKeyRegistryME.EMYN_MUIL_CLIFFS, 57, BiomeDataConfigsME.emynMuilModifier));
        add(new Color(0x857a67), new MapBasedCustomBiome(BiomeKeyRegistryME.EMYN_MUIL, 74, BiomeDataConfigsME.emynMuilModifier.heightModifier(0.32f)));
        add(new Color(0x948976), new MapBasedCustomBiome(BiomeKeyRegistryME.EMYN_MUIL_PEAKS, 90, BiomeDataConfigsME.emynMuilModifier.heightModifier(0.53f).noiseModifier(0.84f)));
        add(new Color(0x75a46d), new MapBasedCustomBiome(BiomeKeyRegistryME.ENEDWAITH, 6, BiomeDataConfigsME.landModifier));
        add(new Color(0x7AB270), new MapBasedCustomBiome(BiomeKeyRegistryME.ENEDWAITH_FIELD, 6, BiomeDataConfigsME.landModifier));
        add(new Color(0x94b270), new MapBasedCustomBiome(BiomeKeyRegistryME.ENEDWAITH_WHEAT_FIELD, 6, BiomeDataConfigsME.landModifier));
        add(new Color(0x6ea76e), new MapBasedCustomBiome(BiomeKeyRegistryME.LONELY_MOUNTAIN_FOOTHILLS, 25, BiomeDataConfigsME.foothillModifier));
        add(new Color(0xc9c9cb), new MapBasedCustomBiome(BiomeKeyRegistryME.LONELY_MOUNTAIN_BASE, 46, BiomeDataConfigsME.mountainModifier.heightModifier(0.24f)));
        add(new Color(0xb9b9bb), new MapBasedCustomBiome(BiomeKeyRegistryME.LONELY_MOUNTAIN_BASE, 53, BiomeDataConfigsME.mountainModifier.heightModifier(0.37f)));
        add(new Color(0xa8a8aa), new MapBasedCustomBiome(BiomeKeyRegistryME.LONELY_MOUNTAIN, 64, BiomeDataConfigsME.mountainModifier.heightModifier(0.43f)));
        add(new Color(0x979799), new MapBasedCustomBiome(BiomeKeyRegistryME.LONELY_MOUNTAIN, 76, BiomeDataConfigsME.mountainModifier.heightModifier(0.57f)));
        add(new Color(0x88888a), new MapBasedCustomBiome(BiomeKeyRegistryME.LONELY_MOUNTAIN_PEAKS, 81, BiomeDataConfigsME.mountainModifier.heightModifier(0.81f).noiseModifier(0.62f)));
        add(new Color(0x7f7f81), new MapBasedCustomBiome(BiomeKeyRegistryME.LONELY_MOUNTAIN_PEAKS, 93, BiomeDataConfigsME.mountainModifier.heightModifier(0.81f).noiseModifier(0.4f)));
        add(new Color(0x5a9f5a), new MapBasedCustomBiome(BiomeKeyRegistryME.LONELY_MOUNTAIN_TAIGA, 7, BiomeDataConfigsME.landModifier));
        add(new Color(0x272525), new MapBasedCustomBiome(BiomeKeyRegistryME.EPHEL_DUATH, 71, BiomeDataConfigsME.mountainModifier));
        add(new Color(0x312e2e), new MapBasedCustomBiome(BiomeKeyRegistryME.EPHEL_DUATH_BASE, 36, BiomeDataConfigsME.mountainModifier.heightModifier(0.33f)));
        add(new Color(0x1c1a1a), new MapBasedCustomBiome(BiomeKeyRegistryME.EPHEL_DUATH_PEAKS, 91, BiomeDataConfigsME.mountainModifier.heightModifier(0.38f)));
        add(new Color(0x2d2a2a), new MapBasedCustomBiome(BiomeKeyRegistryME.ERED_LITHUI_BASE, 36, BiomeDataConfigsME.mountainModifier.heightModifier(0.33f)));
        add(new Color(0x241f1f), new MapBasedCustomBiome(BiomeKeyRegistryME.ERED_LITHUI, 73, BiomeDataConfigsME.mountainModifier));
        add(new Color(0x1a1717), new MapBasedCustomBiome(BiomeKeyRegistryME.ERED_LITHUI_PEAKS, 93, BiomeDataConfigsME.mountainModifier.heightModifier(0.38f).noiseModifier(0.97f)));
        add(new Color(0x6a9b68), new MapBasedCustomBiome(BiomeKeyRegistryME.EREGION, 4, BiomeDataConfigsME.landModifier));
        add(new Color(0x5B8A59), new MapBasedCustomBiome(BiomeKeyRegistryME.EREGION_FOREST, 4, BiomeDataConfigsME.landModifier));
        add(new Color(0x75AD73), new MapBasedCustomBiome(BiomeKeyRegistryME.EREGION_GLADE, 4, BiomeDataConfigsME.landModifier));
        add(new Color(0x109c6d), new MapBasedCustomBiome(BiomeKeyRegistryME.ETHIR_ANDUIN, 4, BiomeDataConfigsME.landModifier));
        add(new Color(0x66b3ba), new MapBasedCustomBiome(BiomeKeyRegistryME.ETHIR_ANDUIN_RIVER_DELTA, -3, BiomeDataConfigsME.riverModifier));
        add(new Color(0x364b0c), new MapBasedCustomBiome(BiomeKeyRegistryME.FANGORN, 6, BiomeDataConfigsME.landModifier));
        add(new Color(0x798370), new MapBasedCustomBiome(BiomeKeyRegistryME.FANGORN_FOOTHILLS, 24, BiomeDataConfigsME.foothillModifier));
        add(new Color(0xa69782), new MapBasedCustomBiome(BiomeKeyRegistryME.FANUIDHOL_BASE, 51, BiomeDataConfigsME.mountainModifier.heightModifier(0.24f).noiseModifier(1.21f)));
        add(new Color(0xb0a288), new MapBasedCustomBiome(BiomeKeyRegistryME.FANUIDHOL, 78, BiomeDataConfigsME.mountainModifier.heightModifier(0.34f)));
        add(new Color(0xc4b499), new MapBasedCustomBiome(BiomeKeyRegistryME.FANUIDHOL_PEAKS, 102, BiomeDataConfigsME.mountainModifier.heightModifier(0.39f)));
        add(new Color(0xececec), new MapBasedCustomBiome(BiomeKeyRegistryME.FORODWAITH, 14, BiomeDataConfigsME.landModifier));
        add(new Color(0x4ad58a), new MapBasedCustomBiome(BiomeKeyRegistryME.LINDON_SHORES_CLIFFS, 30, BiomeDataConfigsME.landModifier.heightModifier(0.76f)));
        add(new Color(0xdee3bf), new MapBasedCustomBiome(BiomeKeyRegistryME.LINDON_SHORES, 0, BiomeDataConfigsME.landModifier.heightModifier(0.97f).heightModifier(0.1f).noiseModifier(0.05f)));
        add(new Color(0x5BB456), new MapBasedCustomBiome(BiomeKeyRegistryME.GONDOR, 4, BiomeDataConfigsME.landModifier));
        add(new Color(0x50A94B), new MapBasedCustomBiome(BiomeKeyRegistryME.GONDOR_FOREST, 6, BiomeDataConfigsME.landModifier));
        add(new Color(0x5BAB57), new MapBasedCustomBiome(BiomeKeyRegistryME.GONDOR_HILL, 12, BiomeDataConfigsME.foothillModifier));
        add(new Color(0x382424), new MapBasedCustomBiome(BiomeKeyRegistryME.GORGOROTH, 6, BiomeDataConfigsME.plainsModifier));
        add(new Color(0x3B2826), new MapBasedCustomBiome(BiomeKeyRegistryME.GORGOROTH_ASHEN_WOODS, 6, BiomeDataConfigsME.plainsModifier));
        add(new Color(0x593B38), new MapBasedCustomBiome(BiomeKeyRegistryME.GORGOROTH_DELTA, 6, BiomeDataConfigsME.plainsModifier));
        add(new Color(0x5c935c), new MapBasedCustomBiome(BiomeKeyRegistryME.GREY_PLAINS, 6, BiomeDataConfigsME.landModifier));
        add(new Color(0x557A55), new MapBasedCustomBiome(BiomeKeyRegistryME.GREY_ASHEN_WOODS, 7, BiomeDataConfigsME.landModifier));
        add(new Color(0x7d7171), new MapBasedCustomBiome(BiomeKeyRegistryME.GREY_MOUNTAINS_BASE, 30, BiomeDataConfigsME.mountainModifier.heightModifier(0.24f)));
        add(new Color(0x6e6262), new MapBasedCustomBiome(BiomeKeyRegistryME.GREY_MOUNTAINS, 61, BiomeDataConfigsME.mountainModifier.heightModifier(0.355f)));
        add(new Color(0x635a5a), new MapBasedCustomBiome(BiomeKeyRegistryME.GREY_MOUNTAINS_PEAKS, 88, BiomeDataConfigsME.mountainModifier.heightModifier(0.59f).noiseModifier(1.3f)));
        add(new Color(0x5d8b5d), new MapBasedCustomBiome(BiomeKeyRegistryME.GUNDABAD_PLAINS, 9, BiomeDataConfigsME.landModifier));
        add(new Color(0x538053), new MapBasedCustomBiome(BiomeKeyRegistryME.GUNDABAD_WOODS, 11, BiomeDataConfigsME.landModifier));
        add(new Color(0xccc471), new MapBasedCustomBiome(BiomeKeyRegistryME.HARAD, 7, BiomeDataConfigsME.landModifier));
        add(new Color(0xede591), new MapBasedCustomBiome(BiomeKeyRegistryME.HARAD_DESERT, 9, BiomeDataConfigsME.landModifier));
        add(new Color(0xC0C56B), new MapBasedCustomBiome(BiomeKeyRegistryME.HARAD_WOODS, 7, BiomeDataConfigsME.landModifier));
        add(new Color(0xb4d679), new MapBasedCustomBiome(BiomeKeyRegistryME.HARONDOR, 6, BiomeDataConfigsME.landModifier));
        add(new Color(0x9cbb5e), new MapBasedCustomBiome(BiomeKeyRegistryME.HILLS_OF_EVENDIM, 35, BiomeDataConfigsME.foothillModifier));
        add(new Color(0x6ea163), new MapBasedCustomBiome(BiomeKeyRegistryME.IRON_HILLS_PLAINS, 8, BiomeDataConfigsME.landModifier));
        add(new Color(0x639458), new MapBasedCustomBiome(BiomeKeyRegistryME.NORTHERN_RHOVANION_FOREST, 9, BiomeDataConfigsME.landModifier));
        add(new Color(0x72A268), new MapBasedCustomBiome(BiomeKeyRegistryME.NORTHERN_RHOVANION_HILLS, 23, BiomeDataConfigsME.landModifier));
        add(new Color(0x6daf6d), new MapBasedCustomBiome(BiomeKeyRegistryME.IRON_FOOTHILLS, 25, BiomeDataConfigsME.foothillModifier));
        add(new Color(0xbdaaa8), new MapBasedCustomBiome(BiomeKeyRegistryME.IRON_HILLS_BASE, 32, BiomeDataConfigsME.mountainModifier.heightModifier(0.48f)));
        add(new Color(0xae908d), new MapBasedCustomBiome(BiomeKeyRegistryME.IRON_HILLS, 51, BiomeDataConfigsME.mountainModifier.heightModifier(0.57f)));
        add(new Color(0xad827f), new MapBasedCustomBiome(BiomeKeyRegistryME.IRON_HILLS_PEAKS, 72, BiomeDataConfigsME.mountainModifier.heightModifier(0.68f)));
        add(new Color(0x619d59), new MapBasedCustomBiome(BiomeKeyRegistryME.ISENGARD, 5, BiomeDataConfigsME.landModifier));
        add(new Color(0x6aa262), new MapBasedCustomBiome(BiomeKeyRegistryME.ISENGARD_HILL, 32, BiomeDataConfigsME.landModifier.heightModifier(0.51f)));
        add(new Color(0x04752a), new MapBasedCustomBiome(BiomeKeyRegistryME.ITHILIEN, 5, BiomeDataConfigsME.landModifier));
        add(new Color(0x078030), new MapBasedCustomBiome(BiomeKeyRegistryME.ITHILIEN_GLADE, 5, BiomeDataConfigsME.landModifier));
        add(new Color(0x336443), new MapBasedCustomBiome(BiomeKeyRegistryME.ITHILIEN_WASTES, 5, BiomeDataConfigsME.landModifier));
        add(new Color(0x3B724D), new MapBasedCustomBiome(BiomeKeyRegistryME.ITHILIEN_WASTES_GLADE, 5, BiomeDataConfigsME.landModifier));
        add(new Color(0xa4ffa4), new MapBasedCustomBiome(BiomeKeyRegistryME.LAMEDON, 7, BiomeDataConfigsME.landModifier));
        add(new Color(0xABF1AB), new MapBasedCustomBiome(BiomeKeyRegistryME.LAMEDON_HILLS, 23, BiomeDataConfigsME.foothillModifier));
        add(new Color(0x42dc38), new MapBasedCustomBiome(BiomeKeyRegistryME.LEBENNIN, 4, BiomeDataConfigsME.landModifier));
        add(new Color(0x5AD952), new MapBasedCustomBiome(BiomeKeyRegistryME.LEBENNIN_HILLS, 17, BiomeDataConfigsME.foothillModifier));
        add(new Color(0x7fd665), new MapBasedCustomBiome(BiomeKeyRegistryME.LEBENNIN_SHORES, 0, BiomeDataConfigsME.plainsModifier.heightModifier(0.98f).noiseModifier(0.1f)));
        add(new Color(0x43c17d), new MapBasedCustomBiome(BiomeKeyRegistryME.LINDON, 4, BiomeDataConfigsME.landModifier));
        add(new Color(0x3CB072), new MapBasedCustomBiome(BiomeKeyRegistryME.LINDON_FOREST, 6, BiomeDataConfigsME.landModifier));
        add(new Color(0x48C761), new MapBasedCustomBiome(BiomeKeyRegistryME.LINDON_HIDDEN_BLOSSOM, 8, BiomeDataConfigsME.landModifier));
        add(new Color(0x4AD375), new MapBasedCustomBiome(BiomeKeyRegistryME.LINDON_MEADOW, 4, BiomeDataConfigsME.landModifier));
        add(new Color(0x438564), new MapBasedCustomBiome(BiomeKeyRegistryME.LONG_MARSHES, 2, BiomeDataConfigsME.landModifier.noiseModifier(0.17f)));
        add(new Color(0x48c440), new MapBasedCustomBiome(BiomeKeyRegistryME.LOSSARNACH, 4, BiomeDataConfigsME.landModifier));
        add(new Color(0xE5A695), new MapBasedCustomBiome(BiomeKeyRegistryME.LOSSARNACH_CHERRY_BLOSSOM, 23, BiomeDataConfigsME.foothillModifier));
        add(new Color(0x6bc35c), new MapBasedCustomBiome(BiomeKeyRegistryME.LOSSARNACH_VALLEY, 7, BiomeDataConfigsME.landModifier));
        add(new Color(0xA9C35C), new MapBasedCustomBiome(BiomeKeyRegistryME.LOSSARNACH_VALLEY_RED, 7, BiomeDataConfigsME.landModifier));
        add(new Color(0x9AC35C), new MapBasedCustomBiome(BiomeKeyRegistryME.LOSSARNACH_VALLEY_ORANGE, 7, BiomeDataConfigsME.landModifier));
        add(new Color(0x8AC35C), new MapBasedCustomBiome(BiomeKeyRegistryME.LOSSARNACH_VALLEY_YELLOW, 7, BiomeDataConfigsME.landModifier));
        add(new Color(0x72C35C), new MapBasedCustomBiome(BiomeKeyRegistryME.LOSSARNACH_VALLEY_GREEN, 7, BiomeDataConfigsME.landModifier));
        add(new Color(0xcdce60), new MapBasedCustomBiome(BiomeKeyRegistryME.LORIEN_EDGE, 5, BiomeDataConfigsME.landModifier));
        add(new Color(0xddd81c), new MapBasedCustomBiome(BiomeKeyRegistryME.LOTHLORIEN, 6, BiomeDataConfigsME.landModifier));
        add(new Color(0xDDDA1C), new MapBasedCustomBiome(BiomeKeyRegistryME.LOTHLORIEN_GLADE, 6, BiomeDataConfigsME.landModifier));
        add(new Color(0xE1C822), new MapBasedCustomBiome(BiomeKeyRegistryME.LOTHLORIEN_BLOSSOM, 6, BiomeDataConfigsME.landModifier));
        add(new Color(0x91a46d), new MapBasedCustomBiome(BiomeKeyRegistryME.MINHIRIATH, 4, BiomeDataConfigsME.landModifier));
        add(new Color(0x9fa46d), new MapBasedCustomBiome(BiomeKeyRegistryME.MINHIRIATH_WHEAT_FIELD, 4, BiomeDataConfigsME.landModifier));
        add(new Color(0x0a360f), new MapBasedCustomBiome(BiomeKeyRegistryME.MIRKWOOD, 8, BiomeDataConfigsME.mirkwoodModifier));
        add(new Color(0x123B16), new MapBasedCustomBiome(BiomeKeyRegistryME.WEBBED_DARK_WOODS, 8, BiomeDataConfigsME.mirkwoodModifier));
        add(new Color(0x124918), new MapBasedCustomBiome(BiomeKeyRegistryME.MIRKWOOD_EDGE, 5, BiomeDataConfigsME.landModifier));
        add(new Color(0x2e5332), new MapBasedCustomBiome(BiomeKeyRegistryME.MIRKWOOD_FOOTHILLS, 25, BiomeDataConfigsME.foothillModifier.noiseModifier(1.13f).heightModifier(0.28f)));
        add(new Color(0x4c554d), new MapBasedCustomBiome(BiomeKeyRegistryME.MIRKWOOD_MOUNTAINS_BASE, 48, BiomeDataConfigsME.mountainModifier.noiseModifier(0.97f).heightModifier(0.41f)));
        add(new Color(0x5a655b), new MapBasedCustomBiome(BiomeKeyRegistryME.MIRKWOOD_MOUNTAINS,77,  BiomeDataConfigsME.mountainModifier.heightModifier(0.52f)));
        add(new Color(0x6c7a6e), new MapBasedCustomBiome(BiomeKeyRegistryME.MIRKWOOD_MOUNTAINS_PEAKS, 89, BiomeDataConfigsME.mountainModifier.heightModifier(0.69f)));
        add(new Color(0x81837d), new MapBasedCustomBiome(BiomeKeyRegistryME.MISTY_MOUNTAINS_BASE, 37, BiomeDataConfigsME.mountainModifier.heightModifier(0.24f)));
        add(new Color(0x797979), new MapBasedCustomBiome(BiomeKeyRegistryME.MISTY_MOUNTAINS, 70, BiomeDataConfigsME.mountainModifier.heightModifier(0.34f)));
        add(new Color(0x6e6e6e), new MapBasedCustomBiome(BiomeKeyRegistryME.MISTY_MOUNTAINS_PEAKS, 90, BiomeDataConfigsME.mountainModifier.heightModifier(0.39f)));
        add(new Color(0x3c2a2a), new MapBasedCustomBiome(BiomeKeyRegistryME.MORDOR, 7, BiomeDataConfigsME.landModifier));
        add(new Color(0x423430), new MapBasedCustomBiome(BiomeKeyRegistryME.MORDOR_ASHEN_FOREST, 7, BiomeDataConfigsME.landModifier));
        add(new Color(0x3D3432), new MapBasedCustomBiome(BiomeKeyRegistryME.MORDOR_HILL, 13, BiomeDataConfigsME.landModifier));
        add(new Color(0x4F483D), new MapBasedCustomBiome(BiomeKeyRegistryME.MORDOR_WASTES, 3, BiomeDataConfigsME.landModifier));
        add(new Color(0x2d3a2c), new MapBasedCustomBiome(BiomeKeyRegistryME.MORGUL_VALE, 3, BiomeDataConfigsME.landModifier));
        add(new Color(0x2B3B2A), new MapBasedCustomBiome(BiomeKeyRegistryME.MORGUL_FOREST, 3, BiomeDataConfigsME.landModifier));
        add(new Color(0x635e5e), new MapBasedCustomBiome(BiomeKeyRegistryME.MOUNT_GUNDABAD_BASE, 39, BiomeDataConfigsME.mountainModifier.heightModifier(0.24f)));
        add(new Color(0x4e4a4a), new MapBasedCustomBiome(BiomeKeyRegistryME.MOUNT_GUNDABAD, 73, BiomeDataConfigsME.mountainModifier.heightModifier(0.33f)));
        add(new Color(0x3e3b3b), new MapBasedCustomBiome(BiomeKeyRegistryME.MOUNT_GUNDABAD_PEAKS, 85, BiomeDataConfigsME.mountainModifier.heightModifier(0.41f)));
        add(new Color(0x2a2828), new MapBasedCustomBiome(BiomeKeyRegistryME.MOUNT_GUNDABAD_PEAKS, 94, BiomeDataConfigsME.mountainModifier.heightModifier(0.69f)));
        add(new Color(0x1A1515), new MapBasedCustomBiome(BiomeKeyRegistryME.MOUNT_DOOM, 92, BiomeDataConfigsME.mountainModifier.heightModifier(0.38f).noiseModifier(0.97f)));
        add(new Color(0x60270d), new MapBasedCustomBiome(BiomeKeyRegistryME.MOUNT_DOOM_PIT, 90, BiomeDataConfigsME.mountainModifier.heightModifier(0.36f).noiseModifier(1.0f).expansionWeight(new byte[]{2, 3})));
        add(new Color(0x619b59), new MapBasedCustomBiome(BiomeKeyRegistryME.NAN_CURUNIR, 5, BiomeDataConfigsME.landModifier));
        add(new Color(0x597ca7), new MapBasedCustomBiome(BiomeKeyRegistryME.NEN_HITHOEL_RAPIDS, -20, BiomeDataConfigsME.riverModifier));
        add(new Color(0x6892c4), new MapBasedCustomBiome(BiomeKeyRegistryME.NEN_HITHOEL, -14, BiomeDataConfigsME.riverModifier));
        add(new Color(0x7e9e39), new MapBasedCustomBiome(BiomeKeyRegistryME.NEN_HITHOEL_FOREST, 4, BiomeDataConfigsME.landModifier.heightModifier(0.13f)));
        add(new Color(0x8eb340), new MapBasedCustomBiome(BiomeKeyRegistryME.NEN_HITHOEL_SHORES, 1, BiomeDataConfigsME.landModifier.heightModifier(0.98f).noiseModifier(0.1f)));
        add(new Color(0x2b9e78), new MapBasedCustomBiome(BiomeKeyRegistryME.NINDALF, 4, BiomeDataConfigsME.landModifier));
        add(new Color(0xb1bc9a), new MapBasedCustomBiome(BiomeKeyRegistryME.NORTH_DOWNS, 41, BiomeDataConfigsME.foothillModifier.noiseModifier(1.8f)));
        add(new Color(0x6a7f3e), new MapBasedCustomBiome(BiomeKeyRegistryME.DUNLAND, 5, BiomeDataConfigsME.landModifier));
        add(new Color(0x7A9345), new MapBasedCustomBiome(BiomeKeyRegistryME.NORTHERN_DUNLAND_GLADE, 5, BiomeDataConfigsME.landModifier));
        add(new Color(0x1a4731), new MapBasedCustomBiome(BiomeKeyRegistryME.NORTHERN_MIRKWOOD_MARSHES, 3, BiomeDataConfigsME.landModifier));
        add(new Color(0x103c25), new MapBasedCustomBiome(BiomeKeyRegistryME.NORTHERN_MIRKWOOD_SWAMP, 4, BiomeDataConfigsME.landModifier));
        add(new Color(0xa0a595), new MapBasedCustomBiome(BiomeKeyRegistryME.NORTHERN_WASTELANDS, 6, BiomeDataConfigsME.landModifier));
        add(new Color(0x4a4d33), new MapBasedCustomBiome(BiomeKeyRegistryME.NURN, 5, BiomeDataConfigsME.landModifier));
        add(new Color(0x4C5437), new MapBasedCustomBiome(BiomeKeyRegistryME.NURN_FOREST, 6, BiomeDataConfigsME.landModifier));
        add(new Color(0x515741), new MapBasedCustomBiome(BiomeKeyRegistryME.NURN_HILL, 11, BiomeDataConfigsME.foothillModifier));
        add(new Color(0x453b32), new MapBasedCustomBiome(BiomeKeyRegistryME.NURN_EDGE, 6, BiomeDataConfigsME.landModifier));
        add(new Color(0x4B4135), new MapBasedCustomBiome(BiomeKeyRegistryME.NURN_EDGE_WOODS, 7, BiomeDataConfigsME.landModifier));
        add(new Color(0x495235), new MapBasedCustomBiome(BiomeKeyRegistryME.OLD_ANGMAR, 6, BiomeDataConfigsME.landModifier.noiseModifier(0.6f)));
        add(new Color(0x4d5736), new MapBasedCustomBiome(BiomeKeyRegistryME.OLD_ANGMAR_FOREST, 7, BiomeDataConfigsME.landModifier));
        add(new Color(0x535d40), new MapBasedCustomBiome(BiomeKeyRegistryME.OLD_ANGMAR_COLD_HILL, 17, BiomeDataConfigsME.landModifier.noiseModifier(0.5f)));
        add(new Color(0x6a7259), new MapBasedCustomBiome(BiomeKeyRegistryME.OLD_ANGMAR_FROZEN_HILL, 25, BiomeDataConfigsME.landModifier));
        add(new Color(0xc8ee80), new MapBasedCustomBiome(BiomeKeyRegistryME.OLD_ARTHEDAIN, 4, BiomeDataConfigsME.landModifier));
        add(new Color(0xa4c466), new MapBasedCustomBiome(BiomeKeyRegistryME.OLD_ARTHEDAIN_FOREST, 5, BiomeDataConfigsME.landModifier));
        add(new Color(0xc8e07f), new MapBasedCustomBiome(BiomeKeyRegistryME.OLD_ARTHEDAIN_MEADOW, 4, BiomeDataConfigsME.landModifier));
        add(new Color(0xc1de8c), new MapBasedCustomBiome(BiomeKeyRegistryME.OLD_ARTHEDAIN_FOOTHILL, 23, BiomeDataConfigsME.foothillModifier.heightModifier(0.2f)));
        add(new Color(0x9cb961), new MapBasedCustomBiome(BiomeKeyRegistryME.OLD_CARDOLAN, 4, BiomeDataConfigsME.landModifier));
        add(new Color(0x8ea908), new MapBasedCustomBiome(BiomeKeyRegistryME.OLD_CARDOLAN_FOREST, 5, BiomeDataConfigsME.landModifier));
        add(new Color(0x9cb668), new MapBasedCustomBiome(BiomeKeyRegistryME.OLD_CARDOLAN_HILL, 27, BiomeDataConfigsME.landModifier));
        add(new Color(0x73874a), new MapBasedCustomBiome(BiomeKeyRegistryME.OLD_RHUDAUR, 5, BiomeDataConfigsME.landModifier));
        add(new Color(0x61733c), new MapBasedCustomBiome(BiomeKeyRegistryME.OLD_RHUDAUR_FOREST, 6, BiomeDataConfigsME.landModifier));
        add(new Color(0x61733c), new MapBasedCustomBiome(BiomeKeyRegistryME.OLD_RHUDAUR_FOREST, 6, BiomeDataConfigsME.landModifier));
        add(new Color(0x71834d), new MapBasedCustomBiome(BiomeKeyRegistryME.OLD_RHUDAUR_HILL, 28, BiomeDataConfigsME.landModifier));
        add(new Color(0x41a03b), new MapBasedCustomBiome(BiomeKeyRegistryME.OSGILIATH, 4, BiomeDataConfigsME.plainsModifier));
        add(new Color(0x58cc51), new MapBasedCustomBiome(BiomeKeyRegistryME.PELENNOR_FIELDS, 4, BiomeDataConfigsME.plainsModifier));
        add(new Color(0x7fcc51), new MapBasedCustomBiome(BiomeKeyRegistryME.PELENNOR_WHEAT_FIELD, 4, BiomeDataConfigsME.plainsModifier));
        add(new Color(0xacb071), new MapBasedCustomBiome(BiomeKeyRegistryME.RHUN, 5, BiomeDataConfigsME.landModifier));
        add(new Color(0xBBC075), new MapBasedCustomBiome(BiomeKeyRegistryME.RHUN_FIELD, 5, BiomeDataConfigsME.landModifier));
        add(new Color(0x95A166), new MapBasedCustomBiome(BiomeKeyRegistryME.RHUN_FOREST, 6, BiomeDataConfigsME.landModifier));
        add(new Color(0xC0BF80), new MapBasedCustomBiome(BiomeKeyRegistryME.RHUN_HIDDEN_BLOSSOM, 9, BiomeDataConfigsME.landModifier));
        add(new Color(0x9bc1a0), new MapBasedCustomBiome(BiomeKeyRegistryME.HIGH_MOOR_VALE, 3, BiomeDataConfigsME.landModifier.noiseModifier(0.17f).heightModifier(0.99f)));
        add(new Color(0x89ab8e), new MapBasedCustomBiome(BiomeKeyRegistryME.HIGH_MOOR, 36, BiomeDataConfigsME.landModifier.noiseModifier(0.36f).heightModifier(0.66f)));
        add(new Color(0x7e9d82), new MapBasedCustomBiome(BiomeKeyRegistryME.HIGH_MOOR_HILLS, 43, BiomeDataConfigsME.landModifier.noiseModifier(0.48f).heightModifier(0.34f)));
        add(new Color(0xb7e566), new MapBasedCustomBiome(BiomeKeyRegistryME.ROHAN, 4, BiomeDataConfigsME.landModifier));
        add(new Color(0xA5D75A), new MapBasedCustomBiome(BiomeKeyRegistryME.ROHAN_FOREST, 5, BiomeDataConfigsME.landModifier));
        add(new Color(0xB6DE71), new MapBasedCustomBiome(BiomeKeyRegistryME.ROHAN_FIELD, 17, BiomeDataConfigsME.foothillModifier));
        add(new Color(0x95ad5f), new MapBasedCustomBiome(BiomeKeyRegistryME.SARN_GEBIR_SHORES, 5, BiomeDataConfigsME.landModifier.heightModifier(0.98f).noiseModifier(0.1f)));
        add(new Color(0x889e57), new MapBasedCustomBiome(BiomeKeyRegistryME.SARN_GEBIR_WILDLANDS, 11, BiomeDataConfigsME.landModifier.heightModifier(0.67f)));
        add(new Color(0x54d946), new MapBasedCustomBiome(BiomeKeyRegistryME.SHIRE, 6, BiomeDataConfigsME.shireModifier));
        add(new Color(0x4bb840), new MapBasedCustomBiome(BiomeKeyRegistryME.SHIRE_EDGE, 6, BiomeDataConfigsME.shireModifier));
        add(new Color(0x3d9834), new MapBasedCustomBiome(BiomeKeyRegistryME.SHIRE_FOREST, 7, BiomeDataConfigsME.shireModifier));
        add(new Color(0x53a14c), new MapBasedCustomBiome(BiomeKeyRegistryME.SHIRE_HILLS, 15, BiomeDataConfigsME.foothillModifier));
        add(new Color(0x43a839), new MapBasedCustomBiome(BiomeKeyRegistryME.SHIRE_WOODS, 7, BiomeDataConfigsME.shireModifier));
        add(new Color(0x9a9339), new MapBasedCustomBiome(BiomeKeyRegistryME.SOUTHEAST_RHOVANION, 4, BiomeDataConfigsME.landModifier));
        add(new Color(0xB0A83A), new MapBasedCustomBiome(BiomeKeyRegistryME.SOUTHEAST_RHOVANION_FIELD, 4, BiomeDataConfigsME.landModifier));
        add(new Color(0x79ba6f), new MapBasedCustomBiome(BiomeKeyRegistryME.DRUWAITH_IAUR, 4, BiomeDataConfigsME.landModifier));
        add(new Color(0xceb39c), new MapBasedCustomBiome(BiomeKeyRegistryME.SOUTHERN_FOROCHEL, 4, BiomeDataConfigsME.landModifier));
        add(new Color(0x7e954f), new MapBasedCustomBiome(BiomeKeyRegistryME.THE_ANGLE, 4, BiomeDataConfigsME.landModifier));
        add(new Color(0x306d2a), new MapBasedCustomBiome(BiomeKeyRegistryME.THE_OLD_FOREST, 7, BiomeDataConfigsME.landModifier));
        add(new Color(0x98ae71), new MapBasedCustomBiome(BiomeKeyRegistryME.THE_WOLD, 4, BiomeDataConfigsME.landModifier));
        add(new Color(0xa9ae69), new MapBasedCustomBiome(BiomeKeyRegistryME.THE_WOLD_WHEAT_FIELD, 4, BiomeDataConfigsME.landModifier));
        add(new Color(0xa8d7a3), new MapBasedCustomBiome(BiomeKeyRegistryME.THE_WHITE_DOWNS, 33, BiomeDataConfigsME.foothillModifier.noiseModifier(1.3f)));
        add(new Color(0x9c9696), new MapBasedCustomBiome(BiomeKeyRegistryME.TOLFALAS, 13, BiomeDataConfigsME.landModifier));
        add(new Color(0x3a362e), new MapBasedCustomBiome(BiomeKeyRegistryME.TOROGWAITH, 8, BiomeDataConfigsME.landModifier));
        add(new Color(0x28422a), new MapBasedCustomBiome(BiomeKeyRegistryME.TROLLSHAWS, 8, BiomeDataConfigsME.landModifier));
        add(new Color(0x492e2e), new MapBasedCustomBiome(BiomeKeyRegistryME.UDUN, 5, BiomeDataConfigsME.landModifier));
        add(new Color(0xe1c085), new MapBasedCustomBiome(BiomeKeyRegistryME.UMBAR, 7, BiomeDataConfigsME.landModifier));
        add(new Color(0xC9DA7D), new MapBasedCustomBiome(BiomeKeyRegistryME.UMBAR_WOODS, 7, BiomeDataConfigsME.landModifier));
        add(new Color(0x183c1c), new MapBasedCustomBiome(BiomeKeyRegistryME.WEBBED_WOODS, 8, BiomeDataConfigsME.mirkwoodModifier));
        add(new Color(0x8a8670), new MapBasedCustomBiome(BiomeKeyRegistryME.WITHERED_HEATH, 8, BiomeDataConfigsME.landModifier));
        add(new Color(0xc6edc0), new MapBasedCustomBiome(BiomeKeyRegistryME.WHITE_MOUNTAINS_BASE, 49, BiomeDataConfigsME.mountainModifier.noiseModifier(0.95f).heightModifier(0.27f)));
        add(new Color(0xdbf5d7), new MapBasedCustomBiome(BiomeKeyRegistryME.WHITE_MOUNTAINS, 68, BiomeDataConfigsME.mountainModifier.noiseModifier(1.07f).heightModifier(0.4f)));
        add(new Color(0xf2fff0), new MapBasedCustomBiome(BiomeKeyRegistryME.WHITE_MOUNTAINS_PEAKS, 89, BiomeDataConfigsME.mountainModifier.heightModifier(0.56f)));
        add(new Color(0x16661f), new MapBasedCustomBiome(BiomeKeyRegistryME.WOODLAND_REALM, 5, BiomeDataConfigsME.woodlandModifier));
        add(new Color(0x316b2e), new MapBasedCustomBiome(BiomeKeyRegistryME.WOODLAND_FOOTHILLS, 45, BiomeDataConfigsME.mountainModifier.noiseModifier(0.47f).heightModifier(0.45f)));
        add(new Color(0x367E33), new MapBasedCustomBiome(BiomeKeyRegistryME.WOODLAND_GLADE, 5, BiomeDataConfigsME.woodlandModifier));
        add(new Color(0x5C7E33), new MapBasedCustomBiome(BiomeKeyRegistryME.AUTUMN_WOODLAND, 5, BiomeDataConfigsME.woodlandModifier));
        add(new Color(0x4f7c4c), new MapBasedCustomBiome(BiomeKeyRegistryME.WOODLAND_HILLS, 74, BiomeDataConfigsME.mountainModifier.noiseModifier(0.56f).heightModifier(0.56f)));

        coastalBiomes.add(BiomeKeyRegistryME.LINDON_SHORES_CLIFFS);
        coastalBiomes.add(BiomeKeyRegistryME.LINDON_SHORES);

        riverbiomes.add(BiomeKeyRegistryME.RIVER);
        riverbiomes.add(BiomeKeyRegistryME.NURN_RIVER);
        riverbiomes.add(BiomeKeyRegistryME.FROZEN_RIVER);
        riverbiomes.add(BiomeKeyRegistryME.ETHIR_ANDUIN_RIVER_DELTA);

        waterBiomes.add(BiomeKeyRegistryME.BELFALAS_BEACH);
        waterBiomes.add(BiomeKeyRegistryME.LEBENNIN_SHORES);
        waterBiomes.add(BiomeKeyRegistryME.DEAD_MARSHES_WATER);
        waterBiomes.add(BiomeKeyRegistryME.EMYN_MUIL_POND);
        waterBiomes.add(BiomeKeyRegistryME.FROZEN_POND);
        waterBiomes.add(BiomeKeyRegistryME.FROZEN_OCEAN);
        waterBiomes.add(BiomeKeyRegistryME.LONG_LAKE);
        waterBiomes.add(BiomeKeyRegistryME.LONG_LAKE_SHORES);
        waterBiomes.add(BiomeKeyRegistryME.NURN_RIVER);
        waterBiomes.add(BiomeKeyRegistryME.NURN_SEA);
        waterBiomes.add(BiomeKeyRegistryME.OCEAN);
        waterBiomes.add(BiomeKeyRegistryME.OCEAN_COAST);
        waterBiomes.add(BiomeKeyRegistryME.RIVER);
        waterBiomes.add(BiomeKeyRegistryME.MIRKWOOD_RIVER);
        waterBiomes.add(BiomeKeyRegistryME.SEA_OF_RHUN);
        waterBiomes.add(BiomeKeyRegistryME.ETHIR_ANDUIN_RIVER_DELTA);
        waterBiomes.add(BiomeKeyRegistryME.MIRKWOOD_SWAMP);

        anduinWaterBiomes.add(BiomeKeyRegistryME.GREAT_RIVER);
        anduinWaterBiomes.add(BiomeKeyRegistryME.NEN_HITHOEL);
        anduinWaterBiomes.add(BiomeKeyRegistryME.NEN_HITHOEL_RAPIDS);
        anduinWaterBiomes.add(BiomeKeyRegistryME.NEN_HITHOEL_SHORES);
        anduinWaterBiomes.add(BiomeKeyRegistryME.NEN_HITHOEL_FOREST);
        anduinWaterBiomes.add(BiomeKeyRegistryME.EMYN_MUIL_CLIFFS);

        frozenBiomes.add(BiomeKeyRegistryME.NORTHERN_WASTELANDS);
        frozenBiomes.add(BiomeKeyRegistryME.SOUTHERN_FOROCHEL);
        frozenBiomes.add(BiomeKeyRegistryME.FORODWAITH);

        mirkwoodSwampBiomes.add(BiomeKeyRegistryME.DARK_MIRKWOOD);
        mirkwoodSwampBiomes.add(BiomeKeyRegistryME.DARK_MIRKWOOD_EDGE);
        mirkwoodSwampBiomes.add(BiomeKeyRegistryME.DARK_ANDUIN_VALES);
        mirkwoodSwampBiomes.add(BiomeKeyRegistryME.DOL_GULDUR);
        mirkwoodSwampBiomes.add(BiomeKeyRegistryME.MIRKWOOD);
        mirkwoodSwampBiomes.add(BiomeKeyRegistryME.MIRKWOOD_EDGE);
        mirkwoodSwampBiomes.add(BiomeKeyRegistryME.MIRKWOOD_MARSHES);
        mirkwoodSwampBiomes.add(BiomeKeyRegistryME.NORTHERN_MIRKWOOD_MARSHES);
        mirkwoodSwampBiomes.add(BiomeKeyRegistryME.WEBBED_DARK_WOODS);
        mirkwoodSwampBiomes.add(BiomeKeyRegistryME.WOODLAND_REALM);
        mirkwoodSwampBiomes.add(BiomeKeyRegistryME.WOODLAND_HILLS);
        mirkwoodSwampBiomes.add(BiomeKeyRegistryME.WOODLAND_GLADE);
        mirkwoodSwampBiomes.add(BiomeKeyRegistryME.AUTUMN_WOODLAND);
        mirkwoodSwampBiomes.add(BiomeKeyRegistryME.WOODLAND_FOOTHILLS);

        oasisBiomes.add(BiomeKeyRegistryME.HARAD);
        oasisBiomes.add(BiomeKeyRegistryME.HARAD_DESERT);
        oasisBiomes.add(BiomeKeyRegistryME.HARAD_WOODS);

        wastePondBiomes.add(BiomeKeyRegistryME.BROWN_LANDS);
        wastePondBiomes.add(BiomeKeyRegistryME.DAGORLAD);
        wastePondBiomes.add(BiomeKeyRegistryME.GORGOROTH);
        wastePondBiomes.add(BiomeKeyRegistryME.GORGOROTH_ASHEN_WOODS);
        wastePondBiomes.add(BiomeKeyRegistryME.GORGOROTH_DELTA);
        wastePondBiomes.add(BiomeKeyRegistryME.MORDOR);
        wastePondBiomes.add(BiomeKeyRegistryME.MORDOR_ASHEN_FOREST);
        wastePondBiomes.add(BiomeKeyRegistryME.MORDOR_HILL);
        wastePondBiomes.add(BiomeKeyRegistryME.ERED_LITHUI_BASE);
        wastePondBiomes.add(BiomeKeyRegistryME.EPHEL_DUATH_BASE);
        wastePondBiomes.add(BiomeKeyRegistryME.MORDOR_WASTES);
        wastePondBiomes.add(BiomeKeyRegistryME.MORGUL_RIVER);
        wastePondBiomes.add(BiomeKeyRegistryME.MORGUL_VALE);
        wastePondBiomes.add(BiomeKeyRegistryME.MORGUL_FOREST);
        wastePondBiomes.add(BiomeKeyRegistryME.EASTERN_NURN);
        wastePondBiomes.add(BiomeKeyRegistryME.NURN);
        wastePondBiomes.add(BiomeKeyRegistryME.NURN_FOREST);
        wastePondBiomes.add(BiomeKeyRegistryME.NURN_HILL);
        wastePondBiomes.add(BiomeKeyRegistryME.NURN_EDGE);
        wastePondBiomes.add(BiomeKeyRegistryME.NURN_EDGE_WOODS);
        wastePondBiomes.add(BiomeKeyRegistryME.TOROGWAITH);
        wastePondBiomes.add(BiomeKeyRegistryME.UDUN);

        deadMarshesBiomes.add(BiomeKeyRegistryME.DEAD_MARSHES);

        mangrovePondBiomes.add(BiomeKeyRegistryME.ETHIR_ANDUIN);
    }
    public static MapBasedCustomBiome getBiome(RegistryEntry<Biome> biome, int posX, int posZ) {
        MapBasedCustomBiome foundBiome = null;
        if(biome.getKey().isPresent()){
            Identifier biomeId = biome.getKey().get().getValue();
            foundBiome = biomeHashMap.values().stream().filter(
                    b-> b.getBiomeKey().getValue().equals(biomeId)
            ).findFirst().orElse(defaultBiome);
        }

        if(foundBiome != null){
            MapBasedCustomBiome colorBasedBiome = MiddleEarthHeightMap.getBiomeFromMap(posX, posZ);
            if(colorBasedBiome.getBiomeKey() == foundBiome.getBiomeKey()){
                return colorBasedBiome;
            }
        }

        return foundBiome;
    }
}
