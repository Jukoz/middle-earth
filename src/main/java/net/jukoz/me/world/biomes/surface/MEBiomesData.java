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

    public static Color DEFAULT_COLOR = new Color(0x375ac3);
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
        add(new Color(0x4b6ac7), oceanCoast);

        // Ponds
        frozenPond = new CustomBiomeHeightData(MEBiomeKeys.FROZEN_POND, -10, MEBiomeDataConfigs.riverModifier);
        add(new Color(0x68a8de), frozenPond);
        oasis = new CustomBiomeHeightData(MEBiomeKeys.OASIS, -10, MEBiomeDataConfigs.riverModifier);
        add(new Color(0x68a8de), oasis);
        pond = new CustomBiomeHeightData(MEBiomeKeys.POND, -10, MEBiomeDataConfigs.riverModifier);
        add(new Color(0x6e9ada), pond);
        mirkwoodSwamp = new CustomBiomeHeightData(MEBiomeKeys.MIRKWOOD_SWAMP, -10, MEBiomeDataConfigs.riverModifier);
        add(new Color(0x1c6b56), mirkwoodSwamp);
        greatRiver = new CustomBiomeHeightData(MEBiomeKeys.GREAT_RIVER, -10, MEBiomeDataConfigs.riverModifier);
        add(new Color(0x638aba), greatRiver);
        wastePond = new CustomBiomeHeightData(MEBiomeKeys.WASTE_POND, -10, MEBiomeDataConfigs.riverModifier);
        add(new Color(0x4b6c8f), wastePond);
        deadMarshes = new CustomBiomeHeightData(MEBiomeKeys.DEAD_MARSHES, 0, MEBiomeDataConfigs.riverModifier.noiseModifier(0f));
        add(new Color(0x305e42), deadMarshes);
        deadMarshesWater = new CustomBiomeHeightData(MEBiomeKeys.DEAD_MARSHES_WATER, -10, MEBiomeDataConfigs.riverModifier);
        add(new Color(0x5a7ca1), deadMarshesWater);

        add(new Color(0x4e637a), new CustomBiomeHeightData(MEBiomeKeys.EMYN_MUIL_POND,-4, MEBiomeDataConfigs.emynMuilModifier));
        add(new Color(0x657bf3), new CustomBiomeHeightData(MEBiomeKeys.FROZEN_OCEAN, -12, MEBiomeDataConfigs.oceanModifier));
        add(new Color(0x455ce4), new CustomBiomeHeightData(MEBiomeKeys.LONG_LAKE, -10, MEBiomeDataConfigs.oceanModifier));
        add(new Color(0x486d72), new CustomBiomeHeightData(MEBiomeKeys.MORGUL_RIVER, -7, MEBiomeDataConfigs.riverModifier));
        add(new Color(0x4f5ba1), new CustomBiomeHeightData(MEBiomeKeys.NURN_RIVER, -7, MEBiomeDataConfigs.riverModifier));
        add(new Color(0x585e82), new CustomBiomeHeightData(MEBiomeKeys.NURN_SEA, -12, MEBiomeDataConfigs.oceanModifier));
        add(new Color(0x4b6ac7), new CustomBiomeHeightData(MEBiomeKeys.OCEAN_COAST, -11, MEBiomeDataConfigs.oceanModifier));
        add(new Color(0x5381ba), new CustomBiomeHeightData(MEBiomeKeys.RIVER, -6, MEBiomeDataConfigs.smallRiverModifier));
        add(new Color(0x4d7789), new CustomBiomeHeightData(MEBiomeKeys.FOREST_RIVER, -6, MEBiomeDataConfigs.smallRiverModifier));
        add(new Color(0x42619d), new CustomBiomeHeightData(MEBiomeKeys.SEA_OF_RHUN, -8, MEBiomeDataConfigs.oceanModifier));
        add(new Color(0x144f32), new CustomBiomeHeightData(MEBiomeKeys.MIRKWOOD_MARSHES, 3, MEBiomeDataConfigs.landModifier));

        // Land Biomes :
        add(new Color(0x9ccf71), new CustomBiomeHeightData(MEBiomeKeys.ANDUIN_VALES, 5, MEBiomeDataConfigs.landModifier));
        add(new Color(0x85b25f), new CustomBiomeHeightData(MEBiomeKeys.ANDUIN_VALES_FOREST, 6, MEBiomeDataConfigs.landModifier));
        add(new Color(0x6fbf65), new CustomBiomeHeightData(MEBiomeKeys.ANORIEN_RIVERSIDE, 0, MEBiomeDataConfigs.plainsModifier.heightModifier(0.98f).noiseModifier(0.1f)));
        add(new Color(0x4fbb49), new CustomBiomeHeightData(MEBiomeKeys.ANORIEN, 10, MEBiomeDataConfigs.plainsModifier));
        add(new Color(0x6bcd66), new CustomBiomeHeightData(MEBiomeKeys.ANORIEN_FOOTHILLS, 19, MEBiomeDataConfigs.foothillModifier.heightModifier(0.28f)));
        add(new Color(0x60ab59), new CustomBiomeHeightData(MEBiomeKeys.BARROW_DOWNS, 6, MEBiomeDataConfigs.landModifier));
        add(new Color(0x6cd69d), new CustomBiomeHeightData(MEBiomeKeys.BELERIAND_ISLAND, 5, MEBiomeDataConfigs.landModifier));
        add(new Color(0x26cf5e), new CustomBiomeHeightData(MEBiomeKeys.BELFALAS, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(0x23ad51), new CustomBiomeHeightData(MEBiomeKeys.BELFALAS_HILLS, 37, MEBiomeDataConfigs.mountainModifier));
        add(new Color(0x4ddb77), new CustomBiomeHeightData(MEBiomeKeys.BELFALAS_BEACH, 0, MEBiomeDataConfigs.plainsModifier.heightModifier(0.98f).noiseModifier(0.1f)));
        add(new Color(0x41943c), new CustomBiomeHeightData(MEBiomeKeys.BLACKROOT_VALE, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(0x4caf75), new CustomBiomeHeightData(MEBiomeKeys.BLUE_MOUNTAINS_FOOTHILLS, 35, MEBiomeDataConfigs.bmModifier.heightModifier(0.23f)));
        add(new Color(0xb2b7d2), new CustomBiomeHeightData(MEBiomeKeys.BLUE_MOUNTAINS_BASE, 53, MEBiomeDataConfigs.bmModifier));
        add(new Color(0xc8d1ff), new CustomBiomeHeightData(MEBiomeKeys.BLUE_MOUNTAINS, 82, MEBiomeDataConfigs.bmModifier));
        add(new Color(0xd9e0ff), new CustomBiomeHeightData(MEBiomeKeys.BLUE_MOUNTAINS_PEAKS, 97, MEBiomeDataConfigs.bmPeaksModifier));
        add(new Color(0x585247), new CustomBiomeHeightData(MEBiomeKeys.BROWN_LANDS, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(0xb48877), new CustomBiomeHeightData(MEBiomeKeys.CARADHRAS_BASE, 56, MEBiomeDataConfigs.mountainModifier.heightModifier(0.24f).noiseModifier(1.21f)));
        add(new Color(0xce9c8a), new CustomBiomeHeightData(MEBiomeKeys.CARADHRAS, 79, MEBiomeDataConfigs.mountainModifier.heightModifier(0.34f)));
        add(new Color(0xedb39e), new CustomBiomeHeightData(MEBiomeKeys.CARADHRAS_PEAKS, 106, MEBiomeDataConfigs.mountainModifier.heightModifier(0.39f)));
        add(new Color(0xb4adb2), new CustomBiomeHeightData(MEBiomeKeys.CELEBDIL_BASE, 54, MEBiomeDataConfigs.mountainModifier.heightModifier(0.24f).noiseModifier(1.21f)));
        add(new Color(0xbdb9bd), new CustomBiomeHeightData(MEBiomeKeys.CELEBDIL, 78, MEBiomeDataConfigs.mountainModifier.heightModifier(0.34f)));
        add(new Color(0xcdcdcd), new CustomBiomeHeightData(MEBiomeKeys.CELEBDIL_PEAKS, 104, MEBiomeDataConfigs.mountainModifier.heightModifier(0.39f)));
        add(new Color(0xc1bc83), new CustomBiomeHeightData(MEBiomeKeys.CORSAIR_COASTS, 6, MEBiomeDataConfigs.landModifier));
        add(new Color(0x4d4541), new CustomBiomeHeightData(MEBiomeKeys.DAGORLAD, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(0x84a44e), new CustomBiomeHeightData(MEBiomeKeys.DALE, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(0x84a455), new CustomBiomeHeightData(MEBiomeKeys.DALE_FOREST, 5, MEBiomeDataConfigs.landModifier));
        add(new Color(0x84a450), new CustomBiomeHeightData(MEBiomeKeys.DALE_HILL, 27, MEBiomeDataConfigs.landModifier));
        add(new Color(0x84b44b), new CustomBiomeHeightData(MEBiomeKeys.DALE_CITY, 4, MEBiomeDataConfigs.plainsModifier));
        add(new Color(0x7db06a), new CustomBiomeHeightData(MEBiomeKeys.DALE_RIVERSIDE, 0, MEBiomeDataConfigs.landModifier.heightModifier(0.98f).noiseModifier(0.1f)));
        add(new Color(0x121a13), new CustomBiomeHeightData(MEBiomeKeys.DARK_MIRKWOOD, 6, MEBiomeDataConfigs.mirkwoodModifier));
        add(new Color(0x1a2d1c), new CustomBiomeHeightData(MEBiomeKeys.DARK_MIRKWOOD_EDGE, 5, MEBiomeDataConfigs.landModifier));
        add(new Color(0xa6bf72), new CustomBiomeHeightData(MEBiomeKeys.DARK_ANDUIN_VALES, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(0x2c2733), new CustomBiomeHeightData(MEBiomeKeys.DOL_GULDUR, 11, MEBiomeDataConfigs.foothillModifier));
        add(new Color(0x352d42), new CustomBiomeHeightData(MEBiomeKeys.DOL_GULDUR_HILL, 31, MEBiomeDataConfigs.mountainModifier));
        add(new Color(0x529250), new CustomBiomeHeightData(MEBiomeKeys.DORWINION, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(0x5d715c), new CustomBiomeHeightData(MEBiomeKeys.DORWINION_HILLS, 34, MEBiomeDataConfigs.foothillModifier));
        add(new Color(0x84897c), new CustomBiomeHeightData(MEBiomeKeys.DUNLAND_FOOTHILLS, 34, MEBiomeDataConfigs.foothillModifier));
        add(new Color(0xa2c585), new CustomBiomeHeightData(MEBiomeKeys.EAST_BIGHT, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(0x655950), new CustomBiomeHeightData(MEBiomeKeys.EASTERN_NURN, 5, MEBiomeDataConfigs.landModifier));
        add(new Color(0x8c9654), new CustomBiomeHeightData(MEBiomeKeys.EASTERN_RHOVANION, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(0x786b54), new CustomBiomeHeightData(MEBiomeKeys.EMYN_MUIL_CLIFFS, 57, MEBiomeDataConfigs.emynMuilModifier));
        add(new Color(0x857a67), new CustomBiomeHeightData(MEBiomeKeys.EMYN_MUIL, 74, MEBiomeDataConfigs.emynMuilModifier.heightModifier(0.32f)));
        add(new Color(0x948976), new CustomBiomeHeightData(MEBiomeKeys.EMYN_MUIL_PEAKS, 94, MEBiomeDataConfigs.emynMuilModifier.heightModifier(0.53f)));
        add(new Color(0x75a46d), new CustomBiomeHeightData(MEBiomeKeys.ENEDWAITH, 6, MEBiomeDataConfigs.landModifier));
        add(new Color(0xb9b9bb), new CustomBiomeHeightData(MEBiomeKeys.LONELY_MOUNTAIN_BASE, 60, MEBiomeDataConfigs.mountainModifier.heightModifier(0.55f)));
        add(new Color(0xa8a8aa), new CustomBiomeHeightData(MEBiomeKeys.LONELY_MOUNTAIN, 73, MEBiomeDataConfigs.mountainModifier.heightModifier(0.65f)));
        add(new Color(0x979799), new CustomBiomeHeightData(MEBiomeKeys.LONELY_MOUNTAIN_PEAKS, 94, MEBiomeDataConfigs.mountainModifier.heightModifier(0.76f)));
        add(new Color(0x5a9f5a), new CustomBiomeHeightData(MEBiomeKeys.LONELY_MOUNTAIN_TAIGA, 7, MEBiomeDataConfigs.landModifier));
        add(new Color(0x6a9b68), new CustomBiomeHeightData(MEBiomeKeys.EREGION, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(0x109c6d), new CustomBiomeHeightData(MEBiomeKeys.ETHIR_ANDUIN, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(0x364b0c), new CustomBiomeHeightData(MEBiomeKeys.FANGORN, 6, MEBiomeDataConfigs.landModifier));
        add(new Color(0x798370), new CustomBiomeHeightData(MEBiomeKeys.FANGORN_FOOTHILLS, 24, MEBiomeDataConfigs.foothillModifier));
        add(new Color(0xa69782), new CustomBiomeHeightData(MEBiomeKeys.FANUIDHOL_BASE, 51, MEBiomeDataConfigs.mountainModifier.heightModifier(0.24f).noiseModifier(1.21f)));
        add(new Color(0xb0a288), new CustomBiomeHeightData(MEBiomeKeys.FANUIDHOL, 78, MEBiomeDataConfigs.mountainModifier.heightModifier(0.34f)));
        add(new Color(0xc4b499), new CustomBiomeHeightData(MEBiomeKeys.FANUIDHOL_PEAKS, 102, MEBiomeDataConfigs.mountainModifier.heightModifier(0.39f)));
        add(new Color(0xececec), new CustomBiomeHeightData(MEBiomeKeys.FORODWAITH, 14, MEBiomeDataConfigs.landModifier));
        add(new Color(0x4ad58a), new CustomBiomeHeightData(MEBiomeKeys.GULF_OF_LHUN_CLIFFS, 30, MEBiomeDataConfigs.landModifier.heightModifier(0.76f)));
        add(new Color(0xdee3bf), new CustomBiomeHeightData(MEBiomeKeys.GULF_OF_LHUN_SHORES, 0, MEBiomeDataConfigs.landModifier.heightModifier(0.97f).heightModifier(0.1f).noiseModifier(0.05f)));
        add(new Color(0x5bbd55), new CustomBiomeHeightData(MEBiomeKeys.GONDOR, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(0x382424), new CustomBiomeHeightData(MEBiomeKeys.GORGOROTH, 6, MEBiomeDataConfigs.plainsModifier));
        add(new Color(0x5c935c), new CustomBiomeHeightData(MEBiomeKeys.GREY_PLAINS, 6, MEBiomeDataConfigs.landModifier));
        add(new Color(0x7d7171), new CustomBiomeHeightData(MEBiomeKeys.GREY_MOUNTAINS_BASE, 30, MEBiomeDataConfigs.mountainModifier.heightModifier(0.24f)));
        add(new Color(0x6e6262), new CustomBiomeHeightData(MEBiomeKeys.GREY_MOUNTAINS, 61, MEBiomeDataConfigs.mountainModifier.heightModifier(0.355f)));
        add(new Color(0x635a5a), new CustomBiomeHeightData(MEBiomeKeys.GREY_MOUNTAINS_PEAKS, 88, MEBiomeDataConfigs.mountainModifier.heightModifier(0.59f).noiseModifier(1.3f)));
        add(new Color(0x5d8b5d), new CustomBiomeHeightData(MEBiomeKeys.GUNDABAD_PLAINS, 9, MEBiomeDataConfigs.landModifier));
        add(new Color(0xccc471), new CustomBiomeHeightData(MEBiomeKeys.HARAD, 7, MEBiomeDataConfigs.landModifier));
        add(new Color(0xede591), new CustomBiomeHeightData(MEBiomeKeys.HARAD_DESERT, 9, MEBiomeDataConfigs.landModifier));
        add(new Color(0xb4d679), new CustomBiomeHeightData(MEBiomeKeys.HARONDOR, 6, MEBiomeDataConfigs.landModifier));
        add(new Color(0x9cbb5e), new CustomBiomeHeightData(MEBiomeKeys.HILLS_OF_EVENDIM, 35, MEBiomeDataConfigs.foothillModifier));
        add(new Color(0x6ea163), new CustomBiomeHeightData(MEBiomeKeys.IRON_HILLS_PLAINS, 8, MEBiomeDataConfigs.landModifier));
        add(new Color(0x6daf6d), new CustomBiomeHeightData(MEBiomeKeys.IRON_FOOTHILLS, 25, MEBiomeDataConfigs.foothillModifier));
        add(new Color(0xbdaaa8), new CustomBiomeHeightData(MEBiomeKeys.IRON_HILLS_BASE, 32, MEBiomeDataConfigs.mountainModifier.heightModifier(0.48f)));
        add(new Color(0xae908d), new CustomBiomeHeightData(MEBiomeKeys.IRON_HILLS, 51, MEBiomeDataConfigs.mountainModifier.heightModifier(0.57f)));
        add(new Color(0xad827f), new CustomBiomeHeightData(MEBiomeKeys.IRON_HILLS_PEAKS, 72, MEBiomeDataConfigs.mountainModifier.heightModifier(0.68f)));
        add(new Color(0x619d59), new CustomBiomeHeightData(MEBiomeKeys.ISENGARD, 5, MEBiomeDataConfigs.landModifier));
        add(new Color(0x6aa262), new CustomBiomeHeightData(MEBiomeKeys.ISENGARD_HILL, 32, MEBiomeDataConfigs.landModifier.heightModifier(0.51f)));
        add(new Color(0x04752a), new CustomBiomeHeightData(MEBiomeKeys.ITHILIEN, 5, MEBiomeDataConfigs.landModifier));
        add(new Color(0x336443), new CustomBiomeHeightData(MEBiomeKeys.ITHILIEN_WASTES, 5, MEBiomeDataConfigs.landModifier));
        add(new Color(0xa4ffa4), new CustomBiomeHeightData(MEBiomeKeys.LAMEDON, 7, MEBiomeDataConfigs.landModifier));
        add(new Color(0x42dc38), new CustomBiomeHeightData(MEBiomeKeys.LEBENNIN, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(0x7fd665), new CustomBiomeHeightData(MEBiomeKeys.LEBENNIN_SHORES, 0, MEBiomeDataConfigs.plainsModifier.heightModifier(0.98f).noiseModifier(0.1f)));
        add(new Color(0x43c17d), new CustomBiomeHeightData(MEBiomeKeys.LINDON, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(0x438564), new CustomBiomeHeightData(MEBiomeKeys.LONG_MARSHES, 3, MEBiomeDataConfigs.landModifier));
        add(new Color(0x48c440), new CustomBiomeHeightData(MEBiomeKeys.LOSSARNACH, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(0x6bc35c), new CustomBiomeHeightData(MEBiomeKeys.LOSSARNACH_VALLEY, 7, MEBiomeDataConfigs.landModifier));
        add(new Color(0xcdce60), new CustomBiomeHeightData(MEBiomeKeys.LORIEN_EDGE, 5, MEBiomeDataConfigs.landModifier));
        add(new Color(0xddd81c), new CustomBiomeHeightData(MEBiomeKeys.LOTHLORIEN, 6, MEBiomeDataConfigs.landModifier));
        add(new Color(0x91a46d), new CustomBiomeHeightData(MEBiomeKeys.MINHIRIATH, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(0x0a360f), new CustomBiomeHeightData(MEBiomeKeys.MIRKWOOD, 8, MEBiomeDataConfigs.mirkwoodModifier));
        add(new Color(0x124918), new CustomBiomeHeightData(MEBiomeKeys.MIRKWOOD_EDGE, 5, MEBiomeDataConfigs.landModifier));
        add(new Color(0x2e5332), new CustomBiomeHeightData(MEBiomeKeys.MIRKWOOD_FOOTHILLS, 32, MEBiomeDataConfigs.foothillModifier.heightModifier(0.28f)));
        add(new Color(0x4c554d), new CustomBiomeHeightData(MEBiomeKeys.MIRKWOOD_MOUNTAINS_BASE, 56, MEBiomeDataConfigs.mountainModifier));
        add(new Color(0x5a655b), new CustomBiomeHeightData(MEBiomeKeys.MIRKWOOD_MOUNTAINS,80,  MEBiomeDataConfigs.mountainModifier));
        add(new Color(0x6c7a6e), new CustomBiomeHeightData(MEBiomeKeys.MIRKWOOD_MOUNTAINS_PEAKS, 120, MEBiomeDataConfigs.mountainModifier));
        add(new Color(0x81837d), new CustomBiomeHeightData(MEBiomeKeys.MISTY_MOUNTAINS_BASE, 37, MEBiomeDataConfigs.mountainModifier.heightModifier(0.24f)));
        add(new Color(0x797979), new CustomBiomeHeightData(MEBiomeKeys.MISTY_MOUNTAINS, 70, MEBiomeDataConfigs.mountainModifier.heightModifier(0.34f)));
        add(new Color(0x6e6e6e), new CustomBiomeHeightData(MEBiomeKeys.MISTY_MOUNTAINS_PEAKS, 90, MEBiomeDataConfigs.mountainModifier.heightModifier(0.39f)));
        add(new Color(0x3c2a2a), new CustomBiomeHeightData(MEBiomeKeys.MORDOR, 7, MEBiomeDataConfigs.landModifier));
        add(new Color(0x2d2a2a), new CustomBiomeHeightData(MEBiomeKeys.MORDOR_MOUNTAINS_FOOTHILLS, 36, MEBiomeDataConfigs.mountainModifier.heightModifier(0.33f)));
        add(new Color(0x241f1f), new CustomBiomeHeightData(MEBiomeKeys.MORDOR_MOUNTAINS, 73, MEBiomeDataConfigs.mountainModifier));
        add(new Color(0x1a1717), new CustomBiomeHeightData(MEBiomeKeys.MORDOR_MOUNTAINS_PEAKS, 93, MEBiomeDataConfigs.mountainModifier.heightModifier(0.38f)));
        add(new Color(0x585247), new CustomBiomeHeightData(MEBiomeKeys.MORDOR_WASTES, 3, MEBiomeDataConfigs.landModifier));
        add(new Color(0x2d3a2c), new CustomBiomeHeightData(MEBiomeKeys.MORGUL_VALE, 3, MEBiomeDataConfigs.landModifier));
        add(new Color(0x635e5e), new CustomBiomeHeightData(MEBiomeKeys.MOUNT_GUNDABAD, 87, MEBiomeDataConfigs.mountainModifier.heightModifier(0.59f)));
        add(new Color(0x60270d), new CustomBiomeHeightData(MEBiomeKeys.MOUNT_DOOM, 90, MEBiomeDataConfigs.mountainModifier.heightModifier(0.36f).noiseModifier(1.0f).expansionWeight(new byte[]{2, 3})));
        add(new Color(0x619b59), new CustomBiomeHeightData(MEBiomeKeys.NAN_CURUNIR, 5, MEBiomeDataConfigs.landModifier));
        add(new Color(0x6892c4), new CustomBiomeHeightData(MEBiomeKeys.NEN_HITHOEL, -4, MEBiomeDataConfigs.riverModifier));
        add(new Color(0x7e9e39), new CustomBiomeHeightData(MEBiomeKeys.NEN_HITHOEL_FOREST, 4, MEBiomeDataConfigs.landModifier.heightModifier(0.13f)));
        add(new Color(0x8eb340), new CustomBiomeHeightData(MEBiomeKeys.NEN_HITHOEL_SHORES, 1, MEBiomeDataConfigs.landModifier.heightModifier(0.98f).noiseModifier(0.1f)));
        add(new Color(0x2b9e78), new CustomBiomeHeightData(MEBiomeKeys.NINDALF, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(0xb1bc9a), new CustomBiomeHeightData(MEBiomeKeys.NORTH_DOWNS, 41, MEBiomeDataConfigs.foothillModifier.noiseModifier(1.8f)));
        add(new Color(0x6a7f3e), new CustomBiomeHeightData(MEBiomeKeys.NORTHERN_DUNLAND, 5, MEBiomeDataConfigs.landModifier));
        add(new Color(0x1a4731), new CustomBiomeHeightData(MEBiomeKeys.NORTHERN_MIRKWOOD_MARSHES, 3, MEBiomeDataConfigs.landModifier));
        add(new Color(0x103c25), new CustomBiomeHeightData(MEBiomeKeys.NORTHERN_MIRKWOOD_SWAMP, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(0xa0a595), new CustomBiomeHeightData(MEBiomeKeys.NORTHERN_WASTELANDS, 6, MEBiomeDataConfigs.landModifier));
        add(new Color(0x4a4d33), new CustomBiomeHeightData(MEBiomeKeys.NURN, 5, MEBiomeDataConfigs.landModifier));
        add(new Color(0x453b32), new CustomBiomeHeightData(MEBiomeKeys.NURN_EDGE, 6, MEBiomeDataConfigs.landModifier));
        add(new Color(0x495235), new CustomBiomeHeightData(MEBiomeKeys.OLD_ANGMAR, 6, MEBiomeDataConfigs.landModifier.noiseModifier(0.6f)));
        add(new Color(0x4d5736), new CustomBiomeHeightData(MEBiomeKeys.OLD_ANGMAR_FOREST, 7, MEBiomeDataConfigs.landModifier));
        add(new Color(0x535d40), new CustomBiomeHeightData(MEBiomeKeys.OLD_ANGMAR_COLD_HILL, 17, MEBiomeDataConfigs.landModifier.noiseModifier(0.5f)));
        add(new Color(0x6a7259), new CustomBiomeHeightData(MEBiomeKeys.OLD_ANGMAR_FROZEN_HILL, 25, MEBiomeDataConfigs.landModifier));
        add(new Color(0xc8ee80), new CustomBiomeHeightData(MEBiomeKeys.OLD_ARTHEDAIN, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(0xa4c466), new CustomBiomeHeightData(MEBiomeKeys.OLD_ARTHEDAIN_FOREST, 5, MEBiomeDataConfigs.landModifier));
        add(new Color(0xc8e07f), new CustomBiomeHeightData(MEBiomeKeys.OLD_ARTHEDAIN_MEADOW, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(0xc1de8c), new CustomBiomeHeightData(MEBiomeKeys.OLD_ARTHEDAIN_FOOTHILL, 23, MEBiomeDataConfigs.foothillModifier.heightModifier(0.2f)));
        add(new Color(0x9cb961), new CustomBiomeHeightData(MEBiomeKeys.OLD_CARDOLAN, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(0x8ea908), new CustomBiomeHeightData(MEBiomeKeys.OLD_CARDOLAN_FOREST, 5, MEBiomeDataConfigs.landModifier));
        add(new Color(0x9cb668), new CustomBiomeHeightData(MEBiomeKeys.OLD_CARDOLAN_HILL, 27, MEBiomeDataConfigs.landModifier));
        add(new Color(0x73874a), new CustomBiomeHeightData(MEBiomeKeys.OLD_RHUDAUR, 5, MEBiomeDataConfigs.landModifier));
        add(new Color(0x61733c), new CustomBiomeHeightData(MEBiomeKeys.OLD_RHUDAUR_FOREST, 6, MEBiomeDataConfigs.landModifier));
        add(new Color(0x71834d), new CustomBiomeHeightData(MEBiomeKeys.OLD_RHUDAUR_HILL, 28, MEBiomeDataConfigs.landModifier));
        add(new Color(0x41a03b), new CustomBiomeHeightData(MEBiomeKeys.OSGILIATH, 4, MEBiomeDataConfigs.plainsModifier));
        add(new Color(0x58cc51), new CustomBiomeHeightData(MEBiomeKeys.PELENNOR_FIELDS, 4, MEBiomeDataConfigs.plainsModifier));
        add(new Color(0xacb071), new CustomBiomeHeightData(MEBiomeKeys.RHUN, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(0x9bc1a0), new CustomBiomeHeightData(MEBiomeKeys.HIGH_MOOR_VALE, 3, MEBiomeDataConfigs.landModifier.noiseModifier(0.1f).heightModifier(0.99f)));
        add(new Color(0x89ab8e), new CustomBiomeHeightData(MEBiomeKeys.HIGH_MOOR, 36, MEBiomeDataConfigs.landModifier.noiseModifier(0.3f).heightModifier(0.66f)));
        add(new Color(0x7e9d82), new CustomBiomeHeightData(MEBiomeKeys.HIGH_MOOR_HILLS, 43, MEBiomeDataConfigs.landModifier.noiseModifier(0.4f).heightModifier(0.34f)));
        add(new Color(0xb7e566), new CustomBiomeHeightData(MEBiomeKeys.ROHAN, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(0x95ad5f), new CustomBiomeHeightData(MEBiomeKeys.SARN_GEBIR_SHORES, 5, MEBiomeDataConfigs.landModifier.heightModifier(0.98f).noiseModifier(0.1f)));
        add(new Color(0x889e57), new CustomBiomeHeightData(MEBiomeKeys.SARN_GEBIR_WILDLANDS, 11, MEBiomeDataConfigs.landModifier.heightModifier(0.67f)));
        add(new Color(0x54d946), new CustomBiomeHeightData(MEBiomeKeys.SHIRE, 6, MEBiomeDataConfigs.shireModifier));
        add(new Color(0x4bb840), new CustomBiomeHeightData(MEBiomeKeys.SHIRE_EDGE, 6, MEBiomeDataConfigs.shireModifier));
        add(new Color(0x3d9834), new CustomBiomeHeightData(MEBiomeKeys.SHIRE_FOREST, 7, MEBiomeDataConfigs.shireModifier));
        add(new Color(0x53a14c), new CustomBiomeHeightData(MEBiomeKeys.SHIRE_HILLS, 15, MEBiomeDataConfigs.foothillModifier));
        add(new Color(0x43a839), new CustomBiomeHeightData(MEBiomeKeys.SHIRE_WOODS, 7, MEBiomeDataConfigs.shireModifier));
        add(new Color(0x9a9339), new CustomBiomeHeightData(MEBiomeKeys.SOUTHEAST_RHOVANION, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(0x79ba6f), new CustomBiomeHeightData(MEBiomeKeys.SOUTHERN_DUNLAND, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(0xceb39c), new CustomBiomeHeightData(MEBiomeKeys.SOUTHERN_FOROCHEL, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(0x272525), new CustomBiomeHeightData(MEBiomeKeys.SOUTHERN_EPHEL_DUATH, 71, MEBiomeDataConfigs.mountainModifier));
        add(new Color(0x312e2e), new CustomBiomeHeightData(MEBiomeKeys.SOUTHERN_EPHEL_DUATH_BASE, 36, MEBiomeDataConfigs.mountainModifier.heightModifier(0.33f)));
        add(new Color(0x1c1a1a), new CustomBiomeHeightData(MEBiomeKeys.SOUTHERN_EPHEL_DUATH_PEAKS, 91, MEBiomeDataConfigs.mountainModifier.heightModifier(0.38f)));
        add(new Color(0x7e954f), new CustomBiomeHeightData(MEBiomeKeys.THE_ANGLE, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(0x306d2a), new CustomBiomeHeightData(MEBiomeKeys.THE_OLD_FOREST, 7, MEBiomeDataConfigs.landModifier));
        add(new Color(0x98ae71), new CustomBiomeHeightData(MEBiomeKeys.THE_WOLD, 4, MEBiomeDataConfigs.landModifier));
        add(new Color(0xa8d7a3), new CustomBiomeHeightData(MEBiomeKeys.THE_WHITE_DOWNS, 33, MEBiomeDataConfigs.foothillModifier.noiseModifier(1.3f)));
        add(new Color(0x9c9696), new CustomBiomeHeightData(MEBiomeKeys.TOLFALAS, 13, MEBiomeDataConfigs.landModifier));
        add(new Color(0x3a362e), new CustomBiomeHeightData(MEBiomeKeys.TOROGWAITH, 8, MEBiomeDataConfigs.landModifier));
        add(new Color(0x28422a), new CustomBiomeHeightData(MEBiomeKeys.TROLLSHAWS, 8, MEBiomeDataConfigs.landModifier));
        add(new Color(0x492e2e), new CustomBiomeHeightData(MEBiomeKeys.UDUN, 5, MEBiomeDataConfigs.landModifier));
        add(new Color(0xe1c085), new CustomBiomeHeightData(MEBiomeKeys.UMBAR, 7, MEBiomeDataConfigs.landModifier));
        add(new Color(0x183c1c), new CustomBiomeHeightData(MEBiomeKeys.WEBBED_WOODS, 8, MEBiomeDataConfigs.mirkwoodModifier));
        add(new Color(0x8a8670), new CustomBiomeHeightData(MEBiomeKeys.WITHERED_HEATH, 8, MEBiomeDataConfigs.landModifier));
        add(new Color(0xc6edc0), new CustomBiomeHeightData(MEBiomeKeys.WHITE_MOUNTAINS_BASE, 47, MEBiomeDataConfigs.mountainModifier.noiseModifier(0.9f).heightModifier(0.27f)));
        add(new Color(0xdbf5d7), new CustomBiomeHeightData(MEBiomeKeys.WHITE_MOUNTAINS, 68, MEBiomeDataConfigs.mountainModifier.noiseModifier(1.05f).heightModifier(0.46f)));
        add(new Color(0xf2fff0), new CustomBiomeHeightData(MEBiomeKeys.WHITE_MOUNTAINS_PEAKS, 89, MEBiomeDataConfigs.mountainModifier.heightModifier(0.57f)));
        add(new Color(0x16661f), new CustomBiomeHeightData(MEBiomeKeys.WOODLAND_REALM, 5, MEBiomeDataConfigs.mountainModifier));
        add(new Color(0x316b2e), new CustomBiomeHeightData(MEBiomeKeys.WOODLAND_FOOTHILLS, 37, MEBiomeDataConfigs.mountainModifier.noiseModifier(0.47f).heightModifier(0.47f)));
        add(new Color(0x4f7c4c), new CustomBiomeHeightData(MEBiomeKeys.WOODLAND_HILLS, 63, MEBiomeDataConfigs.mountainModifier.noiseModifier(0.62f).heightModifier(0.76f)));

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
