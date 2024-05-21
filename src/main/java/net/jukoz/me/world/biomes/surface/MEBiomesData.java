package net.jukoz.me.world.biomes.surface;

import net.jukoz.me.block.StoneBlockSets;
import net.jukoz.me.block.ModBlocks;
import net.jukoz.me.utils.LoggerUtil;
import net.jukoz.me.world.biomes.MEBiomeKeys;
import net.jukoz.me.world.biomes.caves.CaveType;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.biome.Biome;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Converts PNG pixel color to a BiomeKey reference.
 */
public class MEBiomesData {
    private static List<MEBiome> biomes = new ArrayList<>();
    public static List<RegistryKey<Biome>> waterBiomes = new ArrayList<>();
    public static List<RegistryKey<Biome>> riverbiomes = new ArrayList<>();
    public static List<RegistryKey<Biome>> frozenBiomes = new ArrayList<>();
    public static List<RegistryKey<Biome>> wastePondBiomes = new ArrayList<>();
    public static List<RegistryKey<Biome>> mirkwoodSwampBiomes = new ArrayList<>();
    public static List<RegistryKey<Biome>> oasisBiomes = new ArrayList<>();

    public static MEBiome defaultBiome;
    public static MEBiome frozenPond;
    public static MEBiome oasis;
    public static MEBiome pond;
    public static MEBiome wastePond;
    public static MEBiome mirkwoodSwamp;

    // Expansion weights
    private static byte[] RIVER_WEIGHT = {2, 4};
    private static byte[] OCEAN_WEIGHT = {2, 5};
    private static byte[] MOUNTAIN_WEIGHT = {1, 4};
    private static byte[] EDGE_WEIGHT = {1, 4};
    private static byte[] LAND_WEIGHT = {1, 5};

    // Noise Modifiers
    private static double WATER_NOISE_MODIFIER = 0.4f;
    private static double LAND_NOISE_MODIFIER = 0.35f;
    private static double FOOTHILL_NOISE_MODIFIER = 0.6f;
    private static double MOUNTAIN_NOISE_MODIFIER = 0.8f;
    private static double MOUNTAIN_PEAKS_NOISE_MODIFIER = 1.5f;

    // Height Base Modifiers
    private static double WATER_HEIGHT_MODIFIER = 0.2f;
    private static double LAND_HEIGHT_MODIFIER = 0.3f;
    private static double FOOTHILL_HEIGHT_MODIFIER = 0.38f;
    private static double MOUNTAIN_HEIGHT_MODIFIER = 0.46f;
    private static double MOUNTAIN_PEAKS_HEIGHT_MODIFIER = 0.55f;

    public static void addBiome(Color color, MEBiome biome) {
        biome.color = color;
        biomes.add(biome);
    }

    public static MEBiome getBiomeByColor(Integer rgb) throws Exception{
        try{
            return biomes.stream().filter(x -> x.color.getRGB() == rgb).findFirst().get();
        } catch (Exception e){
            Color color = new Color(rgb);
            LoggerUtil.getInstance().logError("MeBiomes::No registered biome has %s for color".formatted(color.toString()));
            throw new Exception();
        }
    }

    public static MEBiome getBiomeById(Short id){
        try{
            return biomes.get(id);
        } catch (Exception e){
            LoggerUtil.getInstance().logError("MeBiomes::No registered biome has %s for id".formatted(id));
            return null;
        }
    }

    public static Integer getColorByBiomeId(Short id){
        try{
            return biomes.get(id).color.getRGB();
        } catch (Exception e){
            LoggerUtil.getInstance().logError("MeBiomes::No registered biome has %s for id".formatted(id));
        }
        return null;
    }

    public static void loadBiomes() {
        defaultBiome = new MEBiome(-13, MEBiomeKeys.OCEAN, OCEAN_WEIGHT, WATER_NOISE_MODIFIER, WATER_HEIGHT_MODIFIER, Blocks.SAND, Blocks.STONE, Blocks.STONE);
        // Ponds
        frozenPond = new MEBiome(-10, MEBiomeKeys.FROZEN_POND, RIVER_WEIGHT, WATER_NOISE_MODIFIER, WATER_HEIGHT_MODIFIER,  Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE, CaveType.FOROD);
        oasis = new MEBiome(-10, MEBiomeKeys.OASIS, RIVER_WEIGHT, WATER_NOISE_MODIFIER, WATER_HEIGHT_MODIFIER,  Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE, CaveType.HARAD);
        pond = new MEBiome(-10, MEBiomeKeys.POND, RIVER_WEIGHT, WATER_NOISE_MODIFIER, WATER_HEIGHT_MODIFIER, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE);
        mirkwoodSwamp = new MEBiome(-10, MEBiomeKeys.MIRKWOOD_SWAMP, RIVER_WEIGHT, WATER_NOISE_MODIFIER, WATER_HEIGHT_MODIFIER, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE);

        wastePond = new MEBiome(-10, MEBiomeKeys.WASTE_POND, RIVER_WEIGHT, WATER_NOISE_MODIFIER, WATER_HEIGHT_MODIFIER, ModBlocks.ASHEN_DIRT, ModBlocks.ASHEN_DIRT, Blocks.STONE);

        // Water Biomes :
        addBiome(new Color(55, 90, 195), defaultBiome);
        addBiome(new Color(104, 168, 222), oasis);
        addBiome(new Color(104, 168, 222), frozenPond);
        addBiome(new Color(110, 154, 218), pond);
        addBiome(new Color(75, 108, 143), wastePond);

        addBiome(new Color(101, 123, 243), new MEBiome(-8, MEBiomeKeys.FROZEN_OCEAN, OCEAN_WEIGHT, WATER_NOISE_MODIFIER, WATER_HEIGHT_MODIFIER, Blocks.GRAVEL, StoneBlockSets.FROZEN_STONE.base(), Blocks.STONE, CaveType.FOROD));
        addBiome(new Color(69, 92, 228), new MEBiome(-7, MEBiomeKeys.LONG_LAKE, OCEAN_WEIGHT, WATER_NOISE_MODIFIER, WATER_HEIGHT_MODIFIER, Blocks.SAND, Blocks.STONE, Blocks.STONE));
        addBiome(new Color(79, 91, 161), new MEBiome(-3, MEBiomeKeys.NURN_RIVER, RIVER_WEIGHT, WATER_NOISE_MODIFIER, WATER_HEIGHT_MODIFIER, ModBlocks.ASHEN_DIRT, ModBlocks.ASHEN_DIRT, Blocks.STONE));
        addBiome(new Color(88, 94, 130), new MEBiome(-6, MEBiomeKeys.NURN_SEA, OCEAN_WEIGHT, WATER_NOISE_MODIFIER, WATER_HEIGHT_MODIFIER, ModBlocks.ASHEN_DIRT, ModBlocks.ASHEN_DIRT, Blocks.STONE));
        addBiome(new Color(75, 106, 199), new MEBiome(-4, MEBiomeKeys.OCEAN_COAST, OCEAN_WEIGHT, WATER_NOISE_MODIFIER, WATER_HEIGHT_MODIFIER, Blocks.SAND, Blocks.STONE, Blocks.STONE));
        addBiome(new Color(83, 129, 186), new MEBiome(-1, MEBiomeKeys.RIVER, RIVER_WEIGHT, 0.05, 0.05, ModBlocks.RIVER_SAND, Blocks.STONE, Blocks.STONE));
        addBiome(new Color(77, 119, 137), new MEBiome(-1, MEBiomeKeys.FOREST_RIVER, RIVER_WEIGHT, 0.05, 0.05, Blocks.MUD, Blocks.CLAY, Blocks.STONE));
        addBiome(new Color(66, 97, 157), new MEBiome(-8, MEBiomeKeys.SEA_OF_RHUN, OCEAN_WEIGHT, WATER_NOISE_MODIFIER, WATER_HEIGHT_MODIFIER, Blocks.SAND, Blocks.STONE, Blocks.STONE));
        addBiome(new Color(28, 107, 86), mirkwoodSwamp);
        addBiome(new Color(20, 79, 50), new MEBiome(3, MEBiomeKeys.MIRKWOOD_MARSHES, RIVER_WEIGHT, 0.05, 0.05, Blocks.MUD, Blocks.CLAY, Blocks.STONE));


        // Land Biomes :
        addBiome(new Color(156, 207, 113), new MEBiome(4, MEBiomeKeys.ANDUIN_VALES, LAND_WEIGHT, LAND_NOISE_MODIFIER, LAND_HEIGHT_MODIFIER, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(79, 187, 73), new MEBiome(10, MEBiomeKeys.ANORIEN, LAND_WEIGHT, LAND_NOISE_MODIFIER, LAND_HEIGHT_MODIFIER, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.CALCITE, Blocks.STONE));
        addBiome(new Color(107, 205, 102), new MEBiome(17, MEBiomeKeys.ANORIEN_FOOTHILLS, LAND_WEIGHT, FOOTHILL_NOISE_MODIFIER, FOOTHILL_HEIGHT_MODIFIER, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.CALCITE, Blocks.STONE));
        addBiome(new Color(96, 171, 89), new MEBiome(6, MEBiomeKeys.BARROW_DOWNS, LAND_WEIGHT, LAND_NOISE_MODIFIER, LAND_HEIGHT_MODIFIER, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(38, 207, 94), new MEBiome(4, MEBiomeKeys.BELFALAS, LAND_WEIGHT, LAND_NOISE_MODIFIER, LAND_HEIGHT_MODIFIER, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.CALCITE, Blocks.STONE));
        addBiome(new Color(35, 173, 81), new MEBiome(37, MEBiomeKeys.BELFALAS_HILLS, LAND_WEIGHT, FOOTHILL_NOISE_MODIFIER, FOOTHILL_HEIGHT_MODIFIER, Blocks.GRASS_BLOCK, Blocks.CALCITE, Blocks.CALCITE, Blocks.STONE));
        addBiome(new Color(178, 183, 210), new MEBiome(55, MEBiomeKeys.BLUE_MOUNTAINS_BASE, LAND_WEIGHT, MOUNTAIN_NOISE_MODIFIER, 0.28f, StoneBlockSets.GONLUIN.base(), StoneBlockSets.GONLUIN.base(), StoneBlockSets.GONLUIN.base(), Blocks.STONE));
        addBiome(new Color(200, 209, 255), new MEBiome(74, MEBiomeKeys.BLUE_MOUNTAINS, EDGE_WEIGHT, MOUNTAIN_NOISE_MODIFIER, 0.33f, StoneBlockSets.GONLUIN.base(), StoneBlockSets.GONLUIN.base(), StoneBlockSets.GONLUIN.base(), Blocks.STONE));
        addBiome(new Color(217, 224, 255), new MEBiome(95, MEBiomeKeys.BLUE_MOUNTAINS_PEAKS, MOUNTAIN_WEIGHT, MOUNTAIN_NOISE_MODIFIER, 0.39f, StoneBlockSets.GONLUIN.base(), StoneBlockSets.GONLUIN.base(), StoneBlockSets.GONLUIN.base(), Blocks.STONE));
        addBiome(new Color(193, 188, 131), new MEBiome(6, MEBiomeKeys.CORSAIR_COASTS, LAND_WEIGHT, LAND_NOISE_MODIFIER, LAND_HEIGHT_MODIFIER, Blocks.GRASS_BLOCK, Blocks.SANDSTONE, Blocks.STONE, CaveType.HARAD));
        addBiome(new Color(132, 164, 78), new MEBiome(4, MEBiomeKeys.DALE, LAND_WEIGHT, LAND_NOISE_MODIFIER, LAND_HEIGHT_MODIFIER, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(18, 26, 19), new MEBiome(6, MEBiomeKeys.DARK_MIRKWOOD, LAND_WEIGHT, LAND_NOISE_MODIFIER, LAND_HEIGHT_MODIFIER, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(26, 45, 28), new MEBiome(5, MEBiomeKeys.DARK_MIRKWOOD_EDGE, EDGE_WEIGHT, LAND_NOISE_MODIFIER, LAND_HEIGHT_MODIFIER, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(166, 191, 114), new MEBiome(4, MEBiomeKeys.DARK_ANDUIN_VALES, LAND_WEIGHT, LAND_NOISE_MODIFIER, LAND_HEIGHT_MODIFIER, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(44, 39, 51), new MEBiome(4, MEBiomeKeys.DOL_GULDUR, LAND_WEIGHT, LAND_NOISE_MODIFIER, LAND_HEIGHT_MODIFIER, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(82, 146, 80), new MEBiome(4, MEBiomeKeys.DORWINION, LAND_WEIGHT, LAND_NOISE_MODIFIER, LAND_HEIGHT_MODIFIER, Blocks.GRASS_BLOCK, Blocks.DIRT, StoneBlockSets.LIMESTONE.base()));
        addBiome(new Color(93, 113, 92), new MEBiome(34, MEBiomeKeys.DORWINION_HILLS, EDGE_WEIGHT, FOOTHILL_NOISE_MODIFIER, FOOTHILL_HEIGHT_MODIFIER, Blocks.STONE, Blocks.STONE, StoneBlockSets.LIMESTONE.base()));
        addBiome(new Color(132, 137, 124), new MEBiome(24, MEBiomeKeys.DUNLAND_FOOTHILLS, EDGE_WEIGHT, FOOTHILL_NOISE_MODIFIER, FOOTHILL_HEIGHT_MODIFIER, Blocks.STONE, Blocks.STONE, Blocks.STONE));
        addBiome(new Color(162, 197, 133), new MEBiome(4, MEBiomeKeys.EAST_BIGHT, LAND_WEIGHT, LAND_NOISE_MODIFIER, LAND_HEIGHT_MODIFIER, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(140, 150, 84), new MEBiome(4, MEBiomeKeys.EASTERN_RHOVANION, LAND_WEIGHT, LAND_NOISE_MODIFIER, LAND_HEIGHT_MODIFIER, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(120, 107, 84), new MEBiome(37, MEBiomeKeys.EMYN_MUIL, LAND_WEIGHT, LAND_NOISE_MODIFIER, LAND_HEIGHT_MODIFIER, Blocks.STONE, Blocks.STONE, Blocks.STONE));
        addBiome(new Color(117, 164, 109), new MEBiome(6, MEBiomeKeys.ENEDWAITH, LAND_WEIGHT, LAND_NOISE_MODIFIER, LAND_HEIGHT_MODIFIER, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(185, 185, 187), new MEBiome(60, MEBiomeKeys.LONELY_MOUNTAIN_BASE, EDGE_WEIGHT, MOUNTAIN_NOISE_MODIFIER, 0.55f, Blocks.STONE, Blocks.STONE, Blocks.STONE));
        addBiome(new Color(168, 168, 170), new MEBiome(73, MEBiomeKeys.LONELY_MOUNTAIN, EDGE_WEIGHT, MOUNTAIN_NOISE_MODIFIER, 0.65f, Blocks.STONE, Blocks.STONE, Blocks.STONE));
        addBiome(new Color(151, 151, 153), new MEBiome(100, MEBiomeKeys.LONELY_MOUNTAIN_PEAKS, MOUNTAIN_WEIGHT, MOUNTAIN_PEAKS_NOISE_MODIFIER, 0.76f, Blocks.STONE, Blocks.STONE, Blocks.STONE));
        addBiome(new Color(106, 155, 104), new MEBiome(4, MEBiomeKeys.EREGION, LAND_WEIGHT, LAND_NOISE_MODIFIER, LAND_HEIGHT_MODIFIER, Blocks.GRASS_BLOCK, Blocks.DIRT, StoneBlockSets.LIMESTONE.base(), Blocks.STONE));
        addBiome(new Color(171, 193, 128), new MEBiome(4, MEBiomeKeys.ERIADOR, LAND_WEIGHT, LAND_NOISE_MODIFIER, LAND_HEIGHT_MODIFIER, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(16, 156, 109), new MEBiome(4, MEBiomeKeys.ETHIR_ANDUIN, LAND_WEIGHT, LAND_NOISE_MODIFIER, LAND_HEIGHT_MODIFIER, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(54, 75, 12), new MEBiome(6, MEBiomeKeys.FANGORN, LAND_WEIGHT, LAND_NOISE_MODIFIER, LAND_HEIGHT_MODIFIER, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(236, 236, 236), new MEBiome(14, MEBiomeKeys.FORODWAITH, LAND_WEIGHT, LAND_NOISE_MODIFIER, LAND_HEIGHT_MODIFIER, Blocks.SNOW_BLOCK, Blocks.SNOW_BLOCK, StoneBlockSets.FROZEN_STONE.base(), CaveType.FOROD));
        addBiome(new Color(91, 189, 85), new MEBiome(4, MEBiomeKeys.GONDOR, LAND_WEIGHT, LAND_NOISE_MODIFIER, LAND_HEIGHT_MODIFIER, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.CALCITE, Blocks.STONE));
        addBiome(new Color(92, 147, 92), new MEBiome(6, MEBiomeKeys.GREY_PLAINS, LAND_WEIGHT, LAND_NOISE_MODIFIER, LAND_HEIGHT_MODIFIER, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(92, 78, 78), new MEBiome(42, MEBiomeKeys.GREY_MOUNTAINS, LAND_WEIGHT, MOUNTAIN_NOISE_MODIFIER, MOUNTAIN_HEIGHT_MODIFIER, Blocks.STONE, Blocks.STONE, Blocks.STONE));
        addBiome(new Color(204, 196, 113), new MEBiome(7, MEBiomeKeys.HARAD, LAND_WEIGHT, LAND_NOISE_MODIFIER, LAND_HEIGHT_MODIFIER, Blocks.GRASS_BLOCK, Blocks.SANDSTONE, Blocks.STONE, CaveType.HARAD));
        addBiome(new Color(237, 229, 145), new MEBiome(9, MEBiomeKeys.HARAD_DESERT, LAND_WEIGHT, LAND_NOISE_MODIFIER, LAND_HEIGHT_MODIFIER, Blocks.SAND, Blocks.SAND, Blocks.SANDSTONE, CaveType.HARAD));
        addBiome(new Color(180, 214, 121), new MEBiome(6, MEBiomeKeys.HARONDOR, LAND_WEIGHT, LAND_NOISE_MODIFIER, LAND_HEIGHT_MODIFIER, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(156, 187, 94), new MEBiome(35, MEBiomeKeys.HILLS_OF_EVENDIM, LAND_WEIGHT,FOOTHILL_NOISE_MODIFIER, FOOTHILL_HEIGHT_MODIFIER, Blocks.GRASS_BLOCK, Blocks.STONE, Blocks.STONE));
        addBiome(new Color(90, 159, 90), new MEBiome(8, MEBiomeKeys.IRON_HILLS_FRONTIER, LAND_WEIGHT, FOOTHILL_NOISE_MODIFIER, FOOTHILL_HEIGHT_MODIFIER, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(109, 175, 109), new MEBiome(25, MEBiomeKeys.IRON_FOOTHILLS, LAND_WEIGHT, MOUNTAIN_NOISE_MODIFIER, FOOTHILL_HEIGHT_MODIFIER, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(189, 170, 168), new MEBiome(32, MEBiomeKeys.IRON_HILLS_BASE, EDGE_WEIGHT, MOUNTAIN_NOISE_MODIFIER, 0.48f, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(174, 144, 141), new MEBiome(51, MEBiomeKeys.IRON_HILLS, MOUNTAIN_WEIGHT, MOUNTAIN_NOISE_MODIFIER, 0.57f, Blocks.STONE, Blocks.STONE, Blocks.STONE));
        addBiome(new Color(173, 130, 127), new MEBiome(72, MEBiomeKeys.IRON_HILLS_PEAKS, MOUNTAIN_WEIGHT, MOUNTAIN_NOISE_MODIFIER, 0.68f, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(4, 117, 42), new MEBiome(5, MEBiomeKeys.ITHILIEN, LAND_WEIGHT, LAND_NOISE_MODIFIER, LAND_HEIGHT_MODIFIER, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.CALCITE, Blocks.STONE));
        addBiome(new Color(51, 100, 67), new MEBiome(5, MEBiomeKeys.ITHILIEN_WASTES, LAND_WEIGHT, LAND_NOISE_MODIFIER, LAND_HEIGHT_MODIFIER, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.CALCITE, Blocks.STONE));
        addBiome(new Color(164, 255, 164), new MEBiome(7, MEBiomeKeys.LAMEDON, LAND_WEIGHT, LAND_NOISE_MODIFIER, LAND_HEIGHT_MODIFIER, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.CALCITE, Blocks.STONE));
        addBiome(new Color(66, 220, 56), new MEBiome(4, MEBiomeKeys.LEBENNIN, LAND_WEIGHT, LAND_NOISE_MODIFIER, LAND_HEIGHT_MODIFIER, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.CALCITE, Blocks.STONE));
        addBiome(new Color(67, 193, 125), new MEBiome(4, MEBiomeKeys.LINDON, LAND_WEIGHT, LAND_NOISE_MODIFIER, LAND_HEIGHT_MODIFIER, Blocks.GRASS_BLOCK, Blocks.DIRT, StoneBlockSets.LIMESTONE.base(), Blocks.STONE));
        addBiome(new Color(67, 133, 100), new MEBiome(3, MEBiomeKeys.LONG_MARSHES, LAND_WEIGHT, LAND_NOISE_MODIFIER, LAND_HEIGHT_MODIFIER, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(205, 206, 96), new MEBiome(4, MEBiomeKeys.LORIEN_EDGE, LAND_WEIGHT, LAND_NOISE_MODIFIER, LAND_HEIGHT_MODIFIER, Blocks.GRASS_BLOCK, Blocks.DIRT, StoneBlockSets.LIMESTONE.base(), Blocks.STONE));
        addBiome(new Color(221, 216, 28), new MEBiome(5, MEBiomeKeys.LOTHLORIEN, LAND_WEIGHT, LAND_NOISE_MODIFIER, LAND_HEIGHT_MODIFIER, Blocks.GRASS_BLOCK, Blocks.DIRT, StoneBlockSets.LIMESTONE.base(), Blocks.STONE));
        addBiome(new Color(145, 164, 109), new MEBiome(4, MEBiomeKeys.MINHIRIATH, LAND_WEIGHT, LAND_NOISE_MODIFIER, LAND_HEIGHT_MODIFIER, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(10, 54, 15), new MEBiome(8, MEBiomeKeys.MIRKWOOD, LAND_WEIGHT, FOOTHILL_NOISE_MODIFIER, FOOTHILL_HEIGHT_MODIFIER, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(18, 73, 24), new MEBiome(5, MEBiomeKeys.MIRKWOOD_EDGE, EDGE_WEIGHT, LAND_NOISE_MODIFIER, LAND_HEIGHT_MODIFIER, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(46, 83, 50), new MEBiome(32, MEBiomeKeys.MIRKWOOD_FOOTHILLS, EDGE_WEIGHT, FOOTHILL_NOISE_MODIFIER, FOOTHILL_HEIGHT_MODIFIER, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(76, 85, 77), new MEBiome(56, MEBiomeKeys.MIRKWOOD_MOUNTAINS_BASE, EDGE_WEIGHT, FOOTHILL_NOISE_MODIFIER, FOOTHILL_HEIGHT_MODIFIER, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(90, 101, 91), new MEBiome(80, MEBiomeKeys.MIRKWOOD_MOUNTAINS, EDGE_WEIGHT, MOUNTAIN_NOISE_MODIFIER, FOOTHILL_HEIGHT_MODIFIER, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(108, 122, 110), new MEBiome(120, MEBiomeKeys.MIRKWOOD_MOUNTAINS_PEAKS, MOUNTAIN_WEIGHT, MOUNTAIN_PEAKS_NOISE_MODIFIER, MOUNTAIN_HEIGHT_MODIFIER, Blocks.STONE, Blocks.STONE, Blocks.STONE));
        addBiome(new Color(129, 131, 125), new MEBiome(43, MEBiomeKeys.MISTY_MOUNTAINS_BASE, EDGE_WEIGHT, MOUNTAIN_NOISE_MODIFIER, 0.16f, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE, CaveType.MISTIES));
        addBiome(new Color(121, 121, 121), new MEBiome(73, MEBiomeKeys.MISTY_MOUNTAINS, MOUNTAIN_WEIGHT, MOUNTAIN_NOISE_MODIFIER, 0.43f, Blocks.STONE, Blocks.STONE, Blocks.STONE, CaveType.MISTIES));
        addBiome(new Color(110, 110, 110), new MEBiome(87, MEBiomeKeys.MISTY_MOUNTAINS_PEAKS, EDGE_WEIGHT, MOUNTAIN_NOISE_MODIFIER, 0.62f, Blocks.SNOW_BLOCK, Blocks.STONE, Blocks.STONE, CaveType.MISTIES));
        addBiome(new Color(60, 42, 42), new MEBiome(6, MEBiomeKeys.MORDOR, LAND_WEIGHT, LAND_NOISE_MODIFIER, LAND_HEIGHT_MODIFIER, StoneBlockSets.ASHEN_STONE.base(), StoneBlockSets.ASHEN_STONE.base(), StoneBlockSets.ASHEN_STONE.base(), CaveType.ASHEN));
        addBiome(new Color(36, 31, 31), new MEBiome(47, MEBiomeKeys.MORDOR_MOUNTAINS, LAND_WEIGHT, MOUNTAIN_NOISE_MODIFIER, MOUNTAIN_HEIGHT_MODIFIER, StoneBlockSets.ASHEN_STONE.base(), StoneBlockSets.ASHEN_STONE.base(), StoneBlockSets.ASHEN_STONE.base(), CaveType.ASHEN));
        addBiome(new Color(47, 42, 42), new MEBiome(30, MEBiomeKeys.MORDOR_MOUNTAINS_FOOTHILLS, MOUNTAIN_WEIGHT, FOOTHILL_NOISE_MODIFIER, FOOTHILL_HEIGHT_MODIFIER, StoneBlockSets.ASHEN_STONE.base(), StoneBlockSets.ASHEN_STONE.base(), StoneBlockSets.ASHEN_STONE.base(), CaveType.ASHEN));
        addBiome(new Color(88, 82, 71), new MEBiome(3, MEBiomeKeys.MORDOR_WASTES, LAND_WEIGHT, LAND_NOISE_MODIFIER, LAND_HEIGHT_MODIFIER, Blocks.GRASS_BLOCK, ModBlocks.ASHEN_DIRT, Blocks.STONE, CaveType.ASHEN));
        addBiome(new Color(43, 158, 120), new MEBiome(4, MEBiomeKeys.NINDALF, LAND_WEIGHT, LAND_NOISE_MODIFIER, LAND_HEIGHT_MODIFIER, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(177, 188, 154), new MEBiome(34, MEBiomeKeys.NORTH_DOWNS, LAND_WEIGHT, LAND_NOISE_MODIFIER, LAND_HEIGHT_MODIFIER, Blocks.GRASS_BLOCK, Blocks.STONE, Blocks.STONE, CaveType.MISTIES));
        addBiome(new Color(26, 71, 49), new MEBiome(3, MEBiomeKeys.NORTHERN_MIRKWOOD_MARSHES, LAND_WEIGHT, LAND_NOISE_MODIFIER, LAND_HEIGHT_MODIFIER, Blocks.MUD, Blocks.DIRT, Blocks.STONE, CaveType.MISTIES));
        addBiome(new Color(16, 60, 37), new MEBiome(4, MEBiomeKeys.NORTHERN_MIRKWOOD_SWAMP, LAND_WEIGHT, LAND_NOISE_MODIFIER, LAND_HEIGHT_MODIFIER, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE, CaveType.MISTIES));
        addBiome(new Color(106, 127, 62), new MEBiome(5, MEBiomeKeys.NORTHERN_DUNLAND, LAND_WEIGHT, LAND_NOISE_MODIFIER, LAND_HEIGHT_MODIFIER, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(160, 165, 149), new MEBiome(6, MEBiomeKeys.NORTHERN_WASTELANDS, LAND_WEIGHT, LAND_NOISE_MODIFIER, LAND_HEIGHT_MODIFIER, Blocks.GRASS_BLOCK, Blocks.DIRT, StoneBlockSets.FROZEN_STONE.base(), Blocks.STONE, CaveType.FOROD));
        addBiome(new Color(74, 77, 51), new MEBiome(5, MEBiomeKeys.NURN, LAND_WEIGHT, LAND_NOISE_MODIFIER, LAND_HEIGHT_MODIFIER, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(73, 82, 53), new MEBiome(6, MEBiomeKeys.OLD_ANGMAR, LAND_WEIGHT, LAND_NOISE_MODIFIER, LAND_HEIGHT_MODIFIER, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(200, 238, 128), new MEBiome(4, MEBiomeKeys.OLD_ARTHEDAIN, LAND_WEIGHT, LAND_NOISE_MODIFIER, LAND_HEIGHT_MODIFIER, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(193, 222, 140), new MEBiome(23, MEBiomeKeys.OLD_ARTHEDAIN_FOOTHILL, LAND_WEIGHT, FOOTHILL_NOISE_MODIFIER, 0.2f, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(156, 185, 97), new MEBiome(4, MEBiomeKeys.OLD_CARDOLAN, LAND_WEIGHT, LAND_NOISE_MODIFIER, LAND_HEIGHT_MODIFIER, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(115, 135, 74), new MEBiome(5, MEBiomeKeys.OLD_RHUDAUR, LAND_WEIGHT, LAND_NOISE_MODIFIER, LAND_HEIGHT_MODIFIER, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(172, 176, 113), new MEBiome(4, MEBiomeKeys.RHUN, LAND_WEIGHT, LAND_NOISE_MODIFIER, LAND_HEIGHT_MODIFIER, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(155, 193, 160), new MEBiome(3, MEBiomeKeys.HIGH_MOOR_VALE, LAND_WEIGHT, 0.1f, 0.99f, Blocks.GRASS_BLOCK, Blocks.DIRT, StoneBlockSets.LIMESTONE.base(), Blocks.STONE));
        addBiome(new Color(137, 171, 142), new MEBiome(38, MEBiomeKeys.HIGH_MOOR, LAND_WEIGHT, 0.3f, 0.99f, Blocks.GRASS_BLOCK, Blocks.DIRT, StoneBlockSets.LIMESTONE.base(), Blocks.STONE));
        addBiome(new Color(126, 157, 130), new MEBiome(51, MEBiomeKeys.HIGH_MOOR_HILLS, LAND_WEIGHT, 0.5f, 0.77f, Blocks.STONE, Blocks.STONE, Blocks.STONE));
        addBiome(new Color(183, 229, 102), new MEBiome(4, MEBiomeKeys.ROHAN, LAND_WEIGHT, LAND_NOISE_MODIFIER, LAND_HEIGHT_MODIFIER, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(84, 217, 70), new MEBiome(6, MEBiomeKeys.SHIRE, LAND_WEIGHT, FOOTHILL_NOISE_MODIFIER, FOOTHILL_HEIGHT_MODIFIER, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(75, 184, 64), new MEBiome(6, MEBiomeKeys.SHIRE_EDGE, EDGE_WEIGHT, LAND_NOISE_MODIFIER, LAND_HEIGHT_MODIFIER, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(61, 152, 52), new MEBiome(7, MEBiomeKeys.SHIRE_WOODS, LAND_WEIGHT, LAND_NOISE_MODIFIER, LAND_HEIGHT_MODIFIER, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(154, 147, 57), new MEBiome(4, MEBiomeKeys.SOUTHEAST_RHOVANION, LAND_WEIGHT, LAND_NOISE_MODIFIER, LAND_HEIGHT_MODIFIER, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(121, 186, 111), new MEBiome(4, MEBiomeKeys.SOUTHERN_DUNLAND, LAND_WEIGHT, LAND_NOISE_MODIFIER, LAND_HEIGHT_MODIFIER, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(206, 179, 156), new MEBiome(4, MEBiomeKeys.SOUTHERN_FOROCHEL, LAND_WEIGHT, LAND_NOISE_MODIFIER, LAND_HEIGHT_MODIFIER, Blocks.GRASS_BLOCK, Blocks.DIRT, StoneBlockSets.FROZEN_STONE.base(), Blocks.STONE, CaveType.FOROD));
        addBiome(new Color(126, 149, 79), new MEBiome(4, MEBiomeKeys.THE_ANGLE, LAND_WEIGHT, LAND_NOISE_MODIFIER, LAND_HEIGHT_MODIFIER, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(48, 109, 42), new MEBiome(7, MEBiomeKeys.THE_OLD_FOREST, LAND_WEIGHT, LAND_NOISE_MODIFIER, LAND_HEIGHT_MODIFIER, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(152, 174, 113), new MEBiome(4, MEBiomeKeys.THE_WOLD, LAND_WEIGHT, LAND_NOISE_MODIFIER, LAND_HEIGHT_MODIFIER, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(156, 150, 150), new MEBiome(13, MEBiomeKeys.TOLFALAS, LAND_WEIGHT, LAND_NOISE_MODIFIER, LAND_HEIGHT_MODIFIER, Blocks.GRASS_BLOCK, Blocks.STONE, Blocks.STONE));
        addBiome(new Color(40, 66, 42), new MEBiome(8, MEBiomeKeys.TROLLSHAWS, LAND_WEIGHT, LAND_NOISE_MODIFIER, LAND_HEIGHT_MODIFIER, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(225, 192, 133), new MEBiome(7, MEBiomeKeys.UMBAR, LAND_WEIGHT, LAND_NOISE_MODIFIER, LAND_HEIGHT_MODIFIER, Blocks.GRASS_BLOCK, Blocks.SANDSTONE, Blocks.STONE, CaveType.HARAD));
        addBiome(new Color(24, 60, 28), new MEBiome(8, MEBiomeKeys.WEBBED_WOODS, LAND_WEIGHT, FOOTHILL_NOISE_MODIFIER, FOOTHILL_HEIGHT_MODIFIER, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(198, 237, 192), new MEBiome(50, MEBiomeKeys.WHITE_MOUNTAINS_BASE, LAND_WEIGHT, FOOTHILL_NOISE_MODIFIER, MOUNTAIN_HEIGHT_MODIFIER, Blocks.GRASS_BLOCK, Blocks.CALCITE, Blocks.CALCITE, Blocks.STONE));
        addBiome(new Color(219, 245, 215), new MEBiome(70, MEBiomeKeys.WHITE_MOUNTAINS, LAND_WEIGHT, MOUNTAIN_NOISE_MODIFIER, MOUNTAIN_HEIGHT_MODIFIER, Blocks.GRASS_BLOCK, Blocks.CALCITE, Blocks.CALCITE, Blocks.STONE));
        addBiome(new Color(242, 255, 240), new MEBiome(86, MEBiomeKeys.WHITE_MOUNTAINS_PEAKS, LAND_WEIGHT, MOUNTAIN_PEAKS_NOISE_MODIFIER, MOUNTAIN_PEAKS_HEIGHT_MODIFIER,Blocks.CALCITE, Blocks.CALCITE, Blocks.CALCITE, Blocks.STONE));
        addBiome(new Color(22, 102, 31), new MEBiome(5, MEBiomeKeys.WOODLAND_REALM, LAND_WEIGHT, LAND_NOISE_MODIFIER, LAND_HEIGHT_MODIFIER, Blocks.GRASS_BLOCK, Blocks.DIRT, StoneBlockSets.LIMESTONE.base(), Blocks.STONE));
        addBiome(new Color(49, 107, 46), new MEBiome(28, MEBiomeKeys.WOODLAND_FOOTHILLS, EDGE_WEIGHT, FOOTHILL_NOISE_MODIFIER, FOOTHILL_HEIGHT_MODIFIER, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(79, 124, 76), new MEBiome(47, MEBiomeKeys.WOODLAND_HILLS, EDGE_WEIGHT, FOOTHILL_NOISE_MODIFIER, FOOTHILL_HEIGHT_MODIFIER, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));

        riverbiomes.add(MEBiomeKeys.RIVER);
        riverbiomes.add(MEBiomeKeys.NURN_RIVER);
        riverbiomes.add(MEBiomeKeys.FROZEN_RIVER);

        waterBiomes.add(MEBiomeKeys.FROZEN_POND);
        waterBiomes.add(MEBiomeKeys.FROZEN_OCEAN);
        waterBiomes.add(MEBiomeKeys.LONG_LAKE);
        waterBiomes.add(MEBiomeKeys.NURN_RIVER);
        waterBiomes.add(MEBiomeKeys.NURN_SEA);
        waterBiomes.add(MEBiomeKeys.OCEAN);
        waterBiomes.add(MEBiomeKeys.OCEAN_COAST);
        waterBiomes.add(MEBiomeKeys.RIVER);
        waterBiomes.add(MEBiomeKeys.SEA_OF_RHUN);

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

        wastePondBiomes.add(MEBiomeKeys.MORDOR);
        wastePondBiomes.add(MEBiomeKeys.MORDOR_MOUNTAINS);
        wastePondBiomes.add(MEBiomeKeys.MORDOR_WASTES);
        wastePondBiomes.add(MEBiomeKeys.NURN);
    }

    public static MEBiome getBiomeByKey(RegistryEntry<Biome> biome) {
        return biomes.stream().filter(
                b -> b.biome.getValue().toString().equalsIgnoreCase(biome.getKey().get().getValue().toString()))
                .findFirst().orElse(defaultBiome);
    }
}
