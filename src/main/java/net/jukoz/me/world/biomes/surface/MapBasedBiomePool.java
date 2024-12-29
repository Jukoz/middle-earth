package net.jukoz.me.world.biomes.surface;

import net.jukoz.me.world.biomes.MEBiomeDataConfigs;
import net.jukoz.me.world.biomes.MEBiomeKeys;
import net.jukoz.me.world.chunkgen.map.MiddleEarthHeightMap;
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
        defaultBiome = new MapBasedCustomBiome(MEBiomeKeys.OCEAN, -17, MEBiomeDataConfigs.oceanModifier);
        add(DEFAULT_COLOR, defaultBiome);

        oceanCoast = new MapBasedCustomBiome(MEBiomeKeys.OCEAN_COAST, -9, MEBiomeDataConfigs.oceanModifier);
        add(new Color(0x4b6ac7), oceanCoast);

        // Ponds
        frozenPond = new MapBasedCustomBiome(MEBiomeKeys.FROZEN_POND, -10, MEBiomeDataConfigs.riverModifier);
        add(new Color(0x68a8de), frozenPond);
        oasis = new MapBasedCustomBiome(MEBiomeKeys.OASIS, -10, MEBiomeDataConfigs.riverModifier);
        add(new Color(0x68a8de), oasis);
        pond = new MapBasedCustomBiome(MEBiomeKeys.POND, -10, MEBiomeDataConfigs.riverModifier);
        add(new Color(0x6e9ada), pond);
        mirkwoodSwamp = new MapBasedCustomBiome(MEBiomeKeys.MIRKWOOD_SWAMP, -10, MEBiomeDataConfigs.riverModifier);
        add(new Color(0x1c6b56), mirkwoodSwamp);
        greatRiver = new MapBasedCustomBiome(MEBiomeKeys.GREAT_RIVER, -10, MEBiomeDataConfigs.riverModifier);
        add(new Color(0x638aba), greatRiver);
        wastePond = new MapBasedCustomBiome(MEBiomeKeys.WASTE_POND, -10, MEBiomeDataConfigs.riverModifier);
        add(new Color(0x4b6c8f), wastePond);
        deadMarshes = new MapBasedCustomBiome(MEBiomeKeys.DEAD_MARSHES, 0, MEBiomeDataConfigs.riverModifier.noiseModifier(0f));
        add(new Color(0x305e42), deadMarshes);
        deadMarshesWater = new MapBasedCustomBiome(MEBiomeKeys.DEAD_MARSHES_WATER, -10, MEBiomeDataConfigs.riverModifier);
        add(new Color(0x5a7ca1), deadMarshesWater);

        add(new Color(0x4e637a), new MapBasedCustomBiome(MEBiomeKeys.EMYN_MUIL_POND,-4, MEBiomeDataConfigs.emynMuilModifier));
        add(new Color(0x657bf3), new MapBasedCustomBiome(MEBiomeKeys.FROZEN_OCEAN, -12, MEBiomeDataConfigs.oceanModifier));
        add(new Color(0x455ce4), new MapBasedCustomBiome(MEBiomeKeys.LONG_LAKE, -10, MEBiomeDataConfigs.oceanModifier));
        add(new Color(0x486d72), new MapBasedCustomBiome(MEBiomeKeys.MORGUL_RIVER, -7, MEBiomeDataConfigs.riverModifier));
        add(new Color(0x4f5ba1), new MapBasedCustomBiome(MEBiomeKeys.NURN_RIVER, -7, MEBiomeDataConfigs.riverModifier));
        add(new Color(0x585e82), new MapBasedCustomBiome(MEBiomeKeys.NURN_SEA, -12, MEBiomeDataConfigs.oceanModifier));
        add(new Color(0x4b6ac7), new MapBasedCustomBiome(MEBiomeKeys.OCEAN_COAST, -11, MEBiomeDataConfigs.oceanModifier));
        add(new Color(0x5381ba), new MapBasedCustomBiome(MEBiomeKeys.RIVER, -6, MEBiomeDataConfigs.smallRiverModifier));
        add(new Color(0x4d7789), new MapBasedCustomBiome(MEBiomeKeys.FOREST_RIVER, -6, MEBiomeDataConfigs.smallRiverModifier));
        add(new Color(0x42619d), new MapBasedCustomBiome(MEBiomeKeys.SEA_OF_RHUN, -8, MEBiomeDataConfigs.oceanModifier));
        add(new Color(0x144f32), new MapBasedCustomBiome(MEBiomeKeys.MIRKWOOD_MARSHES, 3, MEBiomeDataConfigs.landModifier));

        // Land Biomes :
        add(new Color(0x9ccf71), new MapBasedCustomBiome(MEBiomeKeys.ANDUIN_VALES, 5, MEBiomeDataConfigs.landModifier));
        add(new Color(0x85b25f), new MapBasedCustomBiome(MEBiomeKeys.ANDUIN_VALES_FOREST, 6, MEBiomeDataConfigs.landModifier));
        add(new Color(0x6fbf65), new MapBasedCustomBiome(MEBiomeKeys.ANORIEN_RIVERSIDE, 0, MEBiomeDataConfigs.plainsModifier.heightModifier(0.98f).noiseModifier(0.1f)));
        add(new Color(0x4fbb49), new MapBasedCustomBiome(MEBiomeKeys.ANORIEN, 10, MEBiomeDataConfigs.plainsModifier));
        add(new Color(0x6bcd66), new MapBasedCustomBiome(MEBiomeKeys.ANORIEN_FOOTHILLS, 19, MEBiomeDataConfigs.foothillModifier.heightModifier(0.28f)));
        add(new Color(0x60ab59), new MapBasedCustomBiome(MEBiomeKeys.BARROW_DOWNS, 6, MEBiomeDataConfigs.landModifier));
        add(new Color(0x6cd69d), new MapBasedCustomBiome(MEBiomeKeys.BELERIAND_ISLAND, 5, MEBiomeDataConfigs.landModifier));
        add(new Color(0x26cf5e), new MapBasedCustomBiome(MEBiomeKeys.BELFALAS, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(0x22C457), new MapBasedCustomBiome(MEBiomeKeys.BELFALAS_FOREST, 6, MEBiomeDataConfigs.landModifier));
        add(new Color(0x23ad51), new MapBasedCustomBiome(MEBiomeKeys.BELFALAS_HILLS, 37, MEBiomeDataConfigs.mountainModifier));
        add(new Color(0x4ddb77), new MapBasedCustomBiome(MEBiomeKeys.BELFALAS_BEACH, 0, MEBiomeDataConfigs.plainsModifier.heightModifier(0.98f).noiseModifier(0.1f)));
        add(new Color(0x41943c), new MapBasedCustomBiome(MEBiomeKeys.BLACKROOT_VALE, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(0x3C8C37), new MapBasedCustomBiome(MEBiomeKeys.BLACKROOT_FOREST, 7, MEBiomeDataConfigs.landModifier));
        add(new Color(0x4caf75), new MapBasedCustomBiome(MEBiomeKeys.BLUE_MOUNTAINS_FOOTHILLS, 35, MEBiomeDataConfigs.bmModifier.heightModifier(0.23f)));
        add(new Color(0xb2b7d2), new MapBasedCustomBiome(MEBiomeKeys.BLUE_MOUNTAINS_BASE, 53, MEBiomeDataConfigs.bmModifier));
        add(new Color(0xc8d1ff), new MapBasedCustomBiome(MEBiomeKeys.BLUE_MOUNTAINS, 82, MEBiomeDataConfigs.bmModifier));
        add(new Color(0xd9e0ff), new MapBasedCustomBiome(MEBiomeKeys.BLUE_MOUNTAINS_PEAKS, 97, MEBiomeDataConfigs.bmPeaksModifier));
        add(new Color(0x585247), new MapBasedCustomBiome(MEBiomeKeys.BROWN_LANDS, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(0xb48877), new MapBasedCustomBiome(MEBiomeKeys.CARADHRAS_BASE, 56, MEBiomeDataConfigs.mountainModifier.heightModifier(0.24f).noiseModifier(1.21f)));
        add(new Color(0xce9c8a), new MapBasedCustomBiome(MEBiomeKeys.CARADHRAS, 79, MEBiomeDataConfigs.mountainModifier.heightModifier(0.34f)));
        add(new Color(0xedb39e), new MapBasedCustomBiome(MEBiomeKeys.CARADHRAS_PEAKS, 106, MEBiomeDataConfigs.mountainModifier.heightModifier(0.39f)));
        add(new Color(0xb4adb2), new MapBasedCustomBiome(MEBiomeKeys.CELEBDIL_BASE, 54, MEBiomeDataConfigs.mountainModifier.heightModifier(0.24f).noiseModifier(1.21f)));
        add(new Color(0xbdb9bd), new MapBasedCustomBiome(MEBiomeKeys.CELEBDIL, 78, MEBiomeDataConfigs.mountainModifier.heightModifier(0.34f)));
        add(new Color(0xcdcdcd), new MapBasedCustomBiome(MEBiomeKeys.CELEBDIL_PEAKS, 104, MEBiomeDataConfigs.mountainModifier.heightModifier(0.39f)));
        add(new Color(0xc1bc83), new MapBasedCustomBiome(MEBiomeKeys.CORSAIR_COASTS, 6, MEBiomeDataConfigs.landModifier));
        add(new Color(0x4d4541), new MapBasedCustomBiome(MEBiomeKeys.DAGORLAD, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(0x84a44e), new MapBasedCustomBiome(MEBiomeKeys.DALE, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(0x84a455), new MapBasedCustomBiome(MEBiomeKeys.DALE_FOREST, 5, MEBiomeDataConfigs.landModifier));
        add(new Color(0x84a450), new MapBasedCustomBiome(MEBiomeKeys.DALE_HILL, 27, MEBiomeDataConfigs.landModifier));
        add(new Color(0x84b44b), new MapBasedCustomBiome(MEBiomeKeys.DALE_CITY, 4, MEBiomeDataConfigs.plainsModifier));
        add(new Color(0x7db06a), new MapBasedCustomBiome(MEBiomeKeys.DALE_RIVERSIDE, 0, MEBiomeDataConfigs.landModifier.heightModifier(0.98f).noiseModifier(0.1f)));
        add(new Color(0x121a13), new MapBasedCustomBiome(MEBiomeKeys.DARK_MIRKWOOD, 6, MEBiomeDataConfigs.mirkwoodModifier));
        add(new Color(0x1a2d1c), new MapBasedCustomBiome(MEBiomeKeys.DARK_MIRKWOOD_EDGE, 5, MEBiomeDataConfigs.landModifier));
        add(new Color(0xa6bf72), new MapBasedCustomBiome(MEBiomeKeys.DARK_ANDUIN_VALES, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(0x2c2733), new MapBasedCustomBiome(MEBiomeKeys.DOL_GULDUR, 11, MEBiomeDataConfigs.foothillModifier));
        add(new Color(0x352d42), new MapBasedCustomBiome(MEBiomeKeys.DOL_GULDUR_HILL, 31, MEBiomeDataConfigs.mountainModifier));
        add(new Color(0x529250), new MapBasedCustomBiome(MEBiomeKeys.DORWINION, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(0x5d715c), new MapBasedCustomBiome(MEBiomeKeys.DORWINION_HILLS, 34, MEBiomeDataConfigs.foothillModifier));
        add(new Color(0x84897c), new MapBasedCustomBiome(MEBiomeKeys.DUNLAND_FOOTHILLS, 34, MEBiomeDataConfigs.foothillModifier));
        add(new Color(0xa2c585), new MapBasedCustomBiome(MEBiomeKeys.EAST_BIGHT, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(0x655950), new MapBasedCustomBiome(MEBiomeKeys.EASTERN_NURN, 5, MEBiomeDataConfigs.landModifier));
        add(new Color(0x8c9654), new MapBasedCustomBiome(MEBiomeKeys.EASTERN_RHOVANION, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(0x786b54), new MapBasedCustomBiome(MEBiomeKeys.EMYN_MUIL_CLIFFS, 57, MEBiomeDataConfigs.emynMuilModifier));
        add(new Color(0x857a67), new MapBasedCustomBiome(MEBiomeKeys.EMYN_MUIL, 74, MEBiomeDataConfigs.emynMuilModifier.heightModifier(0.32f)));
        add(new Color(0x948976), new MapBasedCustomBiome(MEBiomeKeys.EMYN_MUIL_PEAKS, 94, MEBiomeDataConfigs.emynMuilModifier.heightModifier(0.53f)));
        add(new Color(0x75a46d), new MapBasedCustomBiome(MEBiomeKeys.ENEDWAITH, 6, MEBiomeDataConfigs.landModifier));
        add(new Color(0xc9c9cb), new MapBasedCustomBiome(MEBiomeKeys.LONELY_MOUNTAIN_BASE, 51, MEBiomeDataConfigs.mountainModifier.heightModifier(0.24f)));
        add(new Color(0xb9b9bb), new MapBasedCustomBiome(MEBiomeKeys.LONELY_MOUNTAIN_BASE, 60, MEBiomeDataConfigs.mountainModifier.heightModifier(0.37f)));
        add(new Color(0xa8a8aa), new MapBasedCustomBiome(MEBiomeKeys.LONELY_MOUNTAIN, 68, MEBiomeDataConfigs.mountainModifier.heightModifier(0.43f)));
        add(new Color(0x979799), new MapBasedCustomBiome(MEBiomeKeys.LONELY_MOUNTAIN, 81, MEBiomeDataConfigs.mountainModifier.heightModifier(0.57f)));
        add(new Color(0x7f7f81), new MapBasedCustomBiome(MEBiomeKeys.LONELY_MOUNTAIN_PEAKS, 94, MEBiomeDataConfigs.mountainModifier.heightModifier(0.81f)));
        add(new Color(0x5a9f5a), new MapBasedCustomBiome(MEBiomeKeys.LONELY_MOUNTAIN_TAIGA, 7, MEBiomeDataConfigs.landModifier));
        add(new Color(0x6a9b68), new MapBasedCustomBiome(MEBiomeKeys.EREGION, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(0x109c6d), new MapBasedCustomBiome(MEBiomeKeys.ETHIR_ANDUIN, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(0x364b0c), new MapBasedCustomBiome(MEBiomeKeys.FANGORN, 6, MEBiomeDataConfigs.landModifier));
        add(new Color(0x798370), new MapBasedCustomBiome(MEBiomeKeys.FANGORN_FOOTHILLS, 24, MEBiomeDataConfigs.foothillModifier));
        add(new Color(0xa69782), new MapBasedCustomBiome(MEBiomeKeys.FANUIDHOL_BASE, 51, MEBiomeDataConfigs.mountainModifier.heightModifier(0.24f).noiseModifier(1.21f)));
        add(new Color(0xb0a288), new MapBasedCustomBiome(MEBiomeKeys.FANUIDHOL, 78, MEBiomeDataConfigs.mountainModifier.heightModifier(0.34f)));
        add(new Color(0xc4b499), new MapBasedCustomBiome(MEBiomeKeys.FANUIDHOL_PEAKS, 102, MEBiomeDataConfigs.mountainModifier.heightModifier(0.39f)));
        add(new Color(0xececec), new MapBasedCustomBiome(MEBiomeKeys.FORODWAITH, 14, MEBiomeDataConfigs.landModifier));
        add(new Color(0x4ad58a), new MapBasedCustomBiome(MEBiomeKeys.GULF_OF_LHUN_CLIFFS, 30, MEBiomeDataConfigs.landModifier.heightModifier(0.76f)));
        add(new Color(0xdee3bf), new MapBasedCustomBiome(MEBiomeKeys.GULF_OF_LHUN_SHORES, 0, MEBiomeDataConfigs.landModifier.heightModifier(0.97f).heightModifier(0.1f).noiseModifier(0.05f)));
        add(new Color(0x5bbd55), new MapBasedCustomBiome(MEBiomeKeys.GONDOR, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(0x50A94B), new MapBasedCustomBiome(MEBiomeKeys.GONDOR_FOREST, 6, MEBiomeDataConfigs.landModifier));
        add(new Color(0x5BAB57), new MapBasedCustomBiome(MEBiomeKeys.GONDOR_HILL, 12, MEBiomeDataConfigs.foothillModifier));
        add(new Color(0x382424), new MapBasedCustomBiome(MEBiomeKeys.GORGOROTH, 6, MEBiomeDataConfigs.plainsModifier));
        add(new Color(0x3B2826), new MapBasedCustomBiome(MEBiomeKeys.GORGOROTH_ASHEN_WOODS, 6, MEBiomeDataConfigs.plainsModifier));
        add(new Color(0x593B38), new MapBasedCustomBiome(MEBiomeKeys.GORGOROTH_DELTA, 6, MEBiomeDataConfigs.plainsModifier));
        add(new Color(0x5c935c), new MapBasedCustomBiome(MEBiomeKeys.GREY_PLAINS, 6, MEBiomeDataConfigs.landModifier));
        add(new Color(0x7d7171), new MapBasedCustomBiome(MEBiomeKeys.GREY_MOUNTAINS_BASE, 30, MEBiomeDataConfigs.mountainModifier.heightModifier(0.24f)));
        add(new Color(0x6e6262), new MapBasedCustomBiome(MEBiomeKeys.GREY_MOUNTAINS, 61, MEBiomeDataConfigs.mountainModifier.heightModifier(0.355f)));
        add(new Color(0x635a5a), new MapBasedCustomBiome(MEBiomeKeys.GREY_MOUNTAINS_PEAKS, 88, MEBiomeDataConfigs.mountainModifier.heightModifier(0.59f).noiseModifier(1.3f)));
        add(new Color(0x5d8b5d), new MapBasedCustomBiome(MEBiomeKeys.GUNDABAD_PLAINS, 9, MEBiomeDataConfigs.landModifier));
        add(new Color(0xccc471), new MapBasedCustomBiome(MEBiomeKeys.HARAD, 7, MEBiomeDataConfigs.landModifier));
        add(new Color(0xede591), new MapBasedCustomBiome(MEBiomeKeys.HARAD_DESERT, 9, MEBiomeDataConfigs.landModifier));
        add(new Color(0xb4d679), new MapBasedCustomBiome(MEBiomeKeys.HARONDOR, 6, MEBiomeDataConfigs.landModifier));
        add(new Color(0x9cbb5e), new MapBasedCustomBiome(MEBiomeKeys.HILLS_OF_EVENDIM, 35, MEBiomeDataConfigs.foothillModifier));
        add(new Color(0x6ea163), new MapBasedCustomBiome(MEBiomeKeys.IRON_HILLS_PLAINS, 8, MEBiomeDataConfigs.landModifier));
        add(new Color(0x6daf6d), new MapBasedCustomBiome(MEBiomeKeys.IRON_FOOTHILLS, 25, MEBiomeDataConfigs.foothillModifier));
        add(new Color(0xbdaaa8), new MapBasedCustomBiome(MEBiomeKeys.IRON_HILLS_BASE, 32, MEBiomeDataConfigs.mountainModifier.heightModifier(0.48f)));
        add(new Color(0xae908d), new MapBasedCustomBiome(MEBiomeKeys.IRON_HILLS, 51, MEBiomeDataConfigs.mountainModifier.heightModifier(0.57f)));
        add(new Color(0xad827f), new MapBasedCustomBiome(MEBiomeKeys.IRON_HILLS_PEAKS, 72, MEBiomeDataConfigs.mountainModifier.heightModifier(0.68f)));
        add(new Color(0x619d59), new MapBasedCustomBiome(MEBiomeKeys.ISENGARD, 5, MEBiomeDataConfigs.landModifier));
        add(new Color(0x6aa262), new MapBasedCustomBiome(MEBiomeKeys.ISENGARD_HILL, 32, MEBiomeDataConfigs.landModifier.heightModifier(0.51f)));
        add(new Color(0x04752a), new MapBasedCustomBiome(MEBiomeKeys.ITHILIEN, 5, MEBiomeDataConfigs.landModifier));
        add(new Color(0x078030), new MapBasedCustomBiome(MEBiomeKeys.ITHILIEN_GLADE, 5, MEBiomeDataConfigs.landModifier));
        add(new Color(0x336443), new MapBasedCustomBiome(MEBiomeKeys.ITHILIEN_WASTES, 5, MEBiomeDataConfigs.landModifier));
        add(new Color(0x3B724D), new MapBasedCustomBiome(MEBiomeKeys.ITHILIEN_WASTES_GLADE, 5, MEBiomeDataConfigs.landModifier));
        add(new Color(0xa4ffa4), new MapBasedCustomBiome(MEBiomeKeys.LAMEDON, 7, MEBiomeDataConfigs.landModifier));
        add(new Color(0xABF1AB), new MapBasedCustomBiome(MEBiomeKeys.LAMEDON_HILLS, 23, MEBiomeDataConfigs.foothillModifier));
        add(new Color(0x42dc38), new MapBasedCustomBiome(MEBiomeKeys.LEBENNIN, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(0x5AD952), new MapBasedCustomBiome(MEBiomeKeys.LEBENNIN_HILLS, 17, MEBiomeDataConfigs.foothillModifier));
        add(new Color(0x7fd665), new MapBasedCustomBiome(MEBiomeKeys.LEBENNIN_SHORES, 0, MEBiomeDataConfigs.plainsModifier.heightModifier(0.98f).noiseModifier(0.1f)));
        add(new Color(0x43c17d), new MapBasedCustomBiome(MEBiomeKeys.LINDON, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(0x438564), new MapBasedCustomBiome(MEBiomeKeys.LONG_MARSHES, 3, MEBiomeDataConfigs.landModifier));
        add(new Color(0x48c440), new MapBasedCustomBiome(MEBiomeKeys.LOSSARNACH, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(0xE5A695), new MapBasedCustomBiome(MEBiomeKeys.LOSSARNACH_CHERRY_BLOSSOM, 23, MEBiomeDataConfigs.foothillModifier));
        add(new Color(0x6bc35c), new MapBasedCustomBiome(MEBiomeKeys.LOSSARNACH_VALLEY, 7, MEBiomeDataConfigs.landModifier));
        add(new Color(0xA9C35C), new MapBasedCustomBiome(MEBiomeKeys.LOSSARNACH_VALLEY_RED, 7, MEBiomeDataConfigs.landModifier));
        add(new Color(0x9AC35C), new MapBasedCustomBiome(MEBiomeKeys.LOSSARNACH_VALLEY_ORANGE, 7, MEBiomeDataConfigs.landModifier));
        add(new Color(0x8AC35C), new MapBasedCustomBiome(MEBiomeKeys.LOSSARNACH_VALLEY_YELLOW, 7, MEBiomeDataConfigs.landModifier));
        add(new Color(0x72C35C), new MapBasedCustomBiome(MEBiomeKeys.LOSSARNACH_VALLEY_GREEN, 7, MEBiomeDataConfigs.landModifier));
        add(new Color(0xcdce60), new MapBasedCustomBiome(MEBiomeKeys.LORIEN_EDGE, 5, MEBiomeDataConfigs.landModifier));
        add(new Color(0xddd81c), new MapBasedCustomBiome(MEBiomeKeys.LOTHLORIEN, 6, MEBiomeDataConfigs.landModifier));
        add(new Color(0x91a46d), new MapBasedCustomBiome(MEBiomeKeys.MINHIRIATH, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(0x0a360f), new MapBasedCustomBiome(MEBiomeKeys.MIRKWOOD, 8, MEBiomeDataConfigs.mirkwoodModifier));
        add(new Color(0x124918), new MapBasedCustomBiome(MEBiomeKeys.MIRKWOOD_EDGE, 5, MEBiomeDataConfigs.landModifier));
        add(new Color(0x2e5332), new MapBasedCustomBiome(MEBiomeKeys.MIRKWOOD_FOOTHILLS, 32, MEBiomeDataConfigs.foothillModifier.heightModifier(0.28f)));
        add(new Color(0x4c554d), new MapBasedCustomBiome(MEBiomeKeys.MIRKWOOD_MOUNTAINS_BASE, 56, MEBiomeDataConfigs.mountainModifier));
        add(new Color(0x5a655b), new MapBasedCustomBiome(MEBiomeKeys.MIRKWOOD_MOUNTAINS,80,  MEBiomeDataConfigs.mountainModifier));
        add(new Color(0x6c7a6e), new MapBasedCustomBiome(MEBiomeKeys.MIRKWOOD_MOUNTAINS_PEAKS, 120, MEBiomeDataConfigs.mountainModifier));
        add(new Color(0x81837d), new MapBasedCustomBiome(MEBiomeKeys.MISTY_MOUNTAINS_BASE, 37, MEBiomeDataConfigs.mountainModifier.heightModifier(0.24f)));
        add(new Color(0x797979), new MapBasedCustomBiome(MEBiomeKeys.MISTY_MOUNTAINS, 70, MEBiomeDataConfigs.mountainModifier.heightModifier(0.34f)));
        add(new Color(0x6e6e6e), new MapBasedCustomBiome(MEBiomeKeys.MISTY_MOUNTAINS_PEAKS, 90, MEBiomeDataConfigs.mountainModifier.heightModifier(0.39f)));
        add(new Color(0x3c2a2a), new MapBasedCustomBiome(MEBiomeKeys.MORDOR, 7, MEBiomeDataConfigs.landModifier));
        add(new Color(0x423430), new MapBasedCustomBiome(MEBiomeKeys.MORDOR_ASHEN_FOREST, 7, MEBiomeDataConfigs.landModifier));
        add(new Color(0x3D3432), new MapBasedCustomBiome(MEBiomeKeys.MORDOR_HILL, 13, MEBiomeDataConfigs.landModifier));
        add(new Color(0x2d2a2a), new MapBasedCustomBiome(MEBiomeKeys.MORDOR_MOUNTAINS_FOOTHILLS, 36, MEBiomeDataConfigs.mountainModifier.heightModifier(0.33f)));
        add(new Color(0x241f1f), new MapBasedCustomBiome(MEBiomeKeys.MORDOR_MOUNTAINS, 73, MEBiomeDataConfigs.mountainModifier));
        add(new Color(0x1a1717), new MapBasedCustomBiome(MEBiomeKeys.MORDOR_MOUNTAINS_PEAKS, 93, MEBiomeDataConfigs.mountainModifier.heightModifier(0.38f).noiseModifier(0.97f)));
        add(new Color(0x585247), new MapBasedCustomBiome(MEBiomeKeys.MORDOR_WASTES, 3, MEBiomeDataConfigs.landModifier));
        add(new Color(0x2d3a2c), new MapBasedCustomBiome(MEBiomeKeys.MORGUL_VALE, 3, MEBiomeDataConfigs.landModifier));
        add(new Color(0x2B3B2A), new MapBasedCustomBiome(MEBiomeKeys.MORGUL_FOREST, 3, MEBiomeDataConfigs.landModifier));
        add(new Color(0x635e5e), new MapBasedCustomBiome(MEBiomeKeys.MOUNT_GUNDABAD_BASE, 39, MEBiomeDataConfigs.mountainModifier.heightModifier(0.24f)));
        add(new Color(0x4e4a4a), new MapBasedCustomBiome(MEBiomeKeys.MOUNT_GUNDABAD, 73, MEBiomeDataConfigs.mountainModifier.heightModifier(0.33f)));
        add(new Color(0x3e3b3b), new MapBasedCustomBiome(MEBiomeKeys.MOUNT_GUNDABAD_PEAKS, 87, MEBiomeDataConfigs.mountainModifier.heightModifier(0.41f)));
        add(new Color(0x2a2828), new MapBasedCustomBiome(MEBiomeKeys.MOUNT_GUNDABAD_PEAKS, 98, MEBiomeDataConfigs.mountainModifier.heightModifier(0.69f)));
        add(new Color(0x60270d), new MapBasedCustomBiome(MEBiomeKeys.MOUNT_DOOM, 90, MEBiomeDataConfigs.mountainModifier.heightModifier(0.36f).noiseModifier(1.0f).expansionWeight(new byte[]{2, 3})));
        add(new Color(0x619b59), new MapBasedCustomBiome(MEBiomeKeys.NAN_CURUNIR, 5, MEBiomeDataConfigs.landModifier));
        add(new Color(0x6892c4), new MapBasedCustomBiome(MEBiomeKeys.NEN_HITHOEL, -4, MEBiomeDataConfigs.riverModifier));
        add(new Color(0x7e9e39), new MapBasedCustomBiome(MEBiomeKeys.NEN_HITHOEL_FOREST, 4, MEBiomeDataConfigs.landModifier.heightModifier(0.13f)));
        add(new Color(0x8eb340), new MapBasedCustomBiome(MEBiomeKeys.NEN_HITHOEL_SHORES, 1, MEBiomeDataConfigs.landModifier.heightModifier(0.98f).noiseModifier(0.1f)));
        add(new Color(0x2b9e78), new MapBasedCustomBiome(MEBiomeKeys.NINDALF, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(0xb1bc9a), new MapBasedCustomBiome(MEBiomeKeys.NORTH_DOWNS, 41, MEBiomeDataConfigs.foothillModifier.noiseModifier(1.8f)));
        add(new Color(0x6a7f3e), new MapBasedCustomBiome(MEBiomeKeys.NORTHERN_DUNLAND, 5, MEBiomeDataConfigs.landModifier));
        add(new Color(0x1a4731), new MapBasedCustomBiome(MEBiomeKeys.NORTHERN_MIRKWOOD_MARSHES, 3, MEBiomeDataConfigs.landModifier));
        add(new Color(0x103c25), new MapBasedCustomBiome(MEBiomeKeys.NORTHERN_MIRKWOOD_SWAMP, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(0xa0a595), new MapBasedCustomBiome(MEBiomeKeys.NORTHERN_WASTELANDS, 6, MEBiomeDataConfigs.landModifier));
        add(new Color(0x4a4d33), new MapBasedCustomBiome(MEBiomeKeys.NURN, 5, MEBiomeDataConfigs.landModifier));
        add(new Color(0x4C5437), new MapBasedCustomBiome(MEBiomeKeys.NURN_FOREST, 6, MEBiomeDataConfigs.landModifier));
        add(new Color(0x515741), new MapBasedCustomBiome(MEBiomeKeys.NURN_HILL, 11, MEBiomeDataConfigs.foothillModifier));
        add(new Color(0x453b32), new MapBasedCustomBiome(MEBiomeKeys.NURN_EDGE, 6, MEBiomeDataConfigs.landModifier));
        add(new Color(0x4B4135), new MapBasedCustomBiome(MEBiomeKeys.NURN_EDGE_WOODS, 7, MEBiomeDataConfigs.landModifier));
        add(new Color(0x495235), new MapBasedCustomBiome(MEBiomeKeys.OLD_ANGMAR, 6, MEBiomeDataConfigs.landModifier.noiseModifier(0.6f)));
        add(new Color(0x4d5736), new MapBasedCustomBiome(MEBiomeKeys.OLD_ANGMAR_FOREST, 7, MEBiomeDataConfigs.landModifier));
        add(new Color(0x535d40), new MapBasedCustomBiome(MEBiomeKeys.OLD_ANGMAR_COLD_HILL, 17, MEBiomeDataConfigs.landModifier.noiseModifier(0.5f)));
        add(new Color(0x6a7259), new MapBasedCustomBiome(MEBiomeKeys.OLD_ANGMAR_FROZEN_HILL, 25, MEBiomeDataConfigs.landModifier));
        add(new Color(0xc8ee80), new MapBasedCustomBiome(MEBiomeKeys.OLD_ARTHEDAIN, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(0xa4c466), new MapBasedCustomBiome(MEBiomeKeys.OLD_ARTHEDAIN_FOREST, 5, MEBiomeDataConfigs.landModifier));
        add(new Color(0xc8e07f), new MapBasedCustomBiome(MEBiomeKeys.OLD_ARTHEDAIN_MEADOW, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(0xc1de8c), new MapBasedCustomBiome(MEBiomeKeys.OLD_ARTHEDAIN_FOOTHILL, 23, MEBiomeDataConfigs.foothillModifier.heightModifier(0.2f)));
        add(new Color(0x9cb961), new MapBasedCustomBiome(MEBiomeKeys.OLD_CARDOLAN, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(0x8ea908), new MapBasedCustomBiome(MEBiomeKeys.OLD_CARDOLAN_FOREST, 5, MEBiomeDataConfigs.landModifier));
        add(new Color(0x9cb668), new MapBasedCustomBiome(MEBiomeKeys.OLD_CARDOLAN_HILL, 27, MEBiomeDataConfigs.landModifier));
        add(new Color(0x73874a), new MapBasedCustomBiome(MEBiomeKeys.OLD_RHUDAUR, 5, MEBiomeDataConfigs.landModifier));
        add(new Color(0x61733c), new MapBasedCustomBiome(MEBiomeKeys.OLD_RHUDAUR_FOREST, 6, MEBiomeDataConfigs.landModifier));
        add(new Color(0x71834d), new MapBasedCustomBiome(MEBiomeKeys.OLD_RHUDAUR_HILL, 28, MEBiomeDataConfigs.landModifier));
        add(new Color(0x41a03b), new MapBasedCustomBiome(MEBiomeKeys.OSGILIATH, 4, MEBiomeDataConfigs.plainsModifier));
        add(new Color(0x58cc51), new MapBasedCustomBiome(MEBiomeKeys.PELENNOR_FIELDS, 4, MEBiomeDataConfigs.plainsModifier));
        add(new Color(0xacb071), new MapBasedCustomBiome(MEBiomeKeys.RHUN, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(0x9bc1a0), new MapBasedCustomBiome(MEBiomeKeys.HIGH_MOOR_VALE, 3, MEBiomeDataConfigs.landModifier.noiseModifier(0.1f).heightModifier(0.99f)));
        add(new Color(0x89ab8e), new MapBasedCustomBiome(MEBiomeKeys.HIGH_MOOR, 36, MEBiomeDataConfigs.landModifier.noiseModifier(0.3f).heightModifier(0.66f)));
        add(new Color(0x7e9d82), new MapBasedCustomBiome(MEBiomeKeys.HIGH_MOOR_HILLS, 43, MEBiomeDataConfigs.landModifier.noiseModifier(0.4f).heightModifier(0.34f)));
        add(new Color(0xb7e566), new MapBasedCustomBiome(MEBiomeKeys.ROHAN, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(0xA5D75A), new MapBasedCustomBiome(MEBiomeKeys.ROHAN_FOREST, 5, MEBiomeDataConfigs.landModifier));
        add(new Color(0xB6DE71), new MapBasedCustomBiome(MEBiomeKeys.ROHAN_HILLS, 17, MEBiomeDataConfigs.foothillModifier));
        add(new Color(0x95ad5f), new MapBasedCustomBiome(MEBiomeKeys.SARN_GEBIR_SHORES, 5, MEBiomeDataConfigs.landModifier.heightModifier(0.98f).noiseModifier(0.1f)));
        add(new Color(0x889e57), new MapBasedCustomBiome(MEBiomeKeys.SARN_GEBIR_WILDLANDS, 11, MEBiomeDataConfigs.landModifier.heightModifier(0.67f)));
        add(new Color(0x54d946), new MapBasedCustomBiome(MEBiomeKeys.SHIRE, 6, MEBiomeDataConfigs.shireModifier));
        add(new Color(0x4bb840), new MapBasedCustomBiome(MEBiomeKeys.SHIRE_EDGE, 6, MEBiomeDataConfigs.shireModifier));
        add(new Color(0x3d9834), new MapBasedCustomBiome(MEBiomeKeys.SHIRE_FOREST, 7, MEBiomeDataConfigs.shireModifier));
        add(new Color(0x53a14c), new MapBasedCustomBiome(MEBiomeKeys.SHIRE_HILLS, 15, MEBiomeDataConfigs.foothillModifier));
        add(new Color(0x43a839), new MapBasedCustomBiome(MEBiomeKeys.SHIRE_WOODS, 7, MEBiomeDataConfigs.shireModifier));
        add(new Color(0x9a9339), new MapBasedCustomBiome(MEBiomeKeys.SOUTHEAST_RHOVANION, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(0x79ba6f), new MapBasedCustomBiome(MEBiomeKeys.SOUTHERN_DUNLAND, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(0xceb39c), new MapBasedCustomBiome(MEBiomeKeys.SOUTHERN_FOROCHEL, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(0x272525), new MapBasedCustomBiome(MEBiomeKeys.SOUTHERN_EPHEL_DUATH, 71, MEBiomeDataConfigs.mountainModifier));
        add(new Color(0x312e2e), new MapBasedCustomBiome(MEBiomeKeys.SOUTHERN_EPHEL_DUATH_BASE, 36, MEBiomeDataConfigs.mountainModifier.heightModifier(0.33f)));
        add(new Color(0x1c1a1a), new MapBasedCustomBiome(MEBiomeKeys.SOUTHERN_EPHEL_DUATH_PEAKS, 91, MEBiomeDataConfigs.mountainModifier.heightModifier(0.38f)));
        add(new Color(0x7e954f), new MapBasedCustomBiome(MEBiomeKeys.THE_ANGLE, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(0x306d2a), new MapBasedCustomBiome(MEBiomeKeys.THE_OLD_FOREST, 7, MEBiomeDataConfigs.landModifier));
        add(new Color(0x98ae71), new MapBasedCustomBiome(MEBiomeKeys.THE_WOLD, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(0xa8d7a3), new MapBasedCustomBiome(MEBiomeKeys.THE_WHITE_DOWNS, 33, MEBiomeDataConfigs.foothillModifier.noiseModifier(1.3f)));
        add(new Color(0x9c9696), new MapBasedCustomBiome(MEBiomeKeys.TOLFALAS, 13, MEBiomeDataConfigs.landModifier));
        add(new Color(0x3a362e), new MapBasedCustomBiome(MEBiomeKeys.TOROGWAITH, 8, MEBiomeDataConfigs.landModifier));
        add(new Color(0x28422a), new MapBasedCustomBiome(MEBiomeKeys.TROLLSHAWS, 8, MEBiomeDataConfigs.landModifier));
        add(new Color(0x492e2e), new MapBasedCustomBiome(MEBiomeKeys.UDUN, 5, MEBiomeDataConfigs.landModifier));
        add(new Color(0xe1c085), new MapBasedCustomBiome(MEBiomeKeys.UMBAR, 7, MEBiomeDataConfigs.landModifier));
        add(new Color(0x183c1c), new MapBasedCustomBiome(MEBiomeKeys.WEBBED_WOODS, 8, MEBiomeDataConfigs.mirkwoodModifier));
        add(new Color(0x8a8670), new MapBasedCustomBiome(MEBiomeKeys.WITHERED_HEATH, 8, MEBiomeDataConfigs.landModifier));
        add(new Color(0xc6edc0), new MapBasedCustomBiome(MEBiomeKeys.WHITE_MOUNTAINS_BASE, 47, MEBiomeDataConfigs.mountainModifier.noiseModifier(0.9f).heightModifier(0.27f)));
        add(new Color(0xdbf5d7), new MapBasedCustomBiome(MEBiomeKeys.WHITE_MOUNTAINS, 68, MEBiomeDataConfigs.mountainModifier.noiseModifier(1.05f).heightModifier(0.46f)));
        add(new Color(0xf2fff0), new MapBasedCustomBiome(MEBiomeKeys.WHITE_MOUNTAINS_PEAKS, 89, MEBiomeDataConfigs.mountainModifier.heightModifier(0.57f)));
        add(new Color(0x16661f), new MapBasedCustomBiome(MEBiomeKeys.WOODLAND_REALM, 5, MEBiomeDataConfigs.mountainModifier));
        add(new Color(0x316b2e), new MapBasedCustomBiome(MEBiomeKeys.WOODLAND_FOOTHILLS, 37, MEBiomeDataConfigs.mountainModifier.noiseModifier(0.47f).heightModifier(0.47f)));
        add(new Color(0x4f7c4c), new MapBasedCustomBiome(MEBiomeKeys.WOODLAND_HILLS, 63, MEBiomeDataConfigs.mountainModifier.noiseModifier(0.62f).heightModifier(0.76f)));

        coastalBiomes.add(MEBiomeKeys.GULF_OF_LHUN_CLIFFS);
        coastalBiomes.add(MEBiomeKeys.GULF_OF_LHUN_SHORES);

        riverbiomes.add(MEBiomeKeys.RIVER);
        riverbiomes.add(MEBiomeKeys.NURN_RIVER);
        riverbiomes.add(MEBiomeKeys.FROZEN_RIVER);

        waterBiomes.add(MEBiomeKeys.BELFALAS_BEACH);
        waterBiomes.add(MEBiomeKeys.LEBENNIN_SHORES);
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
        waterBiomes.add(MEBiomeKeys.FOREST_RIVER);
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
        wastePondBiomes.add(MEBiomeKeys.GORGOROTH_ASHEN_WOODS);
        wastePondBiomes.add(MEBiomeKeys.GORGOROTH_DELTA);
        wastePondBiomes.add(MEBiomeKeys.MORDOR);
        wastePondBiomes.add(MEBiomeKeys.MORDOR_ASHEN_FOREST);
        wastePondBiomes.add(MEBiomeKeys.MORDOR_HILL);
        wastePondBiomes.add(MEBiomeKeys.MORDOR_MOUNTAINS_FOOTHILLS);
        wastePondBiomes.add(MEBiomeKeys.SOUTHERN_EPHEL_DUATH_BASE);
        wastePondBiomes.add(MEBiomeKeys.MORDOR_WASTES);
        wastePondBiomes.add(MEBiomeKeys.MORGUL_RIVER);
        wastePondBiomes.add(MEBiomeKeys.MORGUL_VALE);
        wastePondBiomes.add(MEBiomeKeys.MORGUL_FOREST);
        wastePondBiomes.add(MEBiomeKeys.EASTERN_NURN);
        wastePondBiomes.add(MEBiomeKeys.NURN);
        wastePondBiomes.add(MEBiomeKeys.NURN_FOREST);
        wastePondBiomes.add(MEBiomeKeys.NURN_HILL);
        wastePondBiomes.add(MEBiomeKeys.NURN_EDGE);
        wastePondBiomes.add(MEBiomeKeys.NURN_EDGE_WOODS);
        wastePondBiomes.add(MEBiomeKeys.TOROGWAITH);
        wastePondBiomes.add(MEBiomeKeys.UDUN);

        deadMarshesBiomes.add(MEBiomeKeys.DEAD_MARSHES);
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
