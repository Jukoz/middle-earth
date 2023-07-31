package net.jesteur.me.world.biomes;

import net.jesteur.me.block.SimpleBlockSets;
import net.jesteur.me.block.ModBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Converts PNG pixel color to a BiomeKey reference.
 */
public class MEBiomesData {
    public static HashMap<Integer, MEBiome> biomeMap = new HashMap<>();
    public static List<RegistryKey<Biome>> waterBiomes = new ArrayList<>();
    public static List<RegistryKey<Biome>> wastePondBiomes = new ArrayList<>();
    public static MEBiome defaultBiome;
    public static MEBiome millPond;
    public static MEBiome wastePond;

    /// Only supports height value from -22 to 41
    public static final int MINIMAL_HEIGHT = -22;

    public static void addBiome(Color color, MEBiome biome) {
        biomeMap.put(color.getRGB(), biome);
    }

    public static void loadBiomes() {
        defaultBiome = new MEBiome(-21, MEBiomeKeys.OCEAN, Blocks.SAND, Blocks.STONE, Blocks.STONE, Blocks.STONE);
        millPond = new MEBiome(-10, MEBiomeKeys.MILLPOND,  Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE, Blocks.STONE);
        wastePond = new MEBiome(-15, MEBiomeKeys.WASTE_POND, ModBlocks.MORDOR_DIRT, ModBlocks.MORDOR_DIRT, Blocks.STONE, Blocks.STONE);
        addBiome(new Color(55, 90, 195), defaultBiome);
        addBiome(new Color(110, 154, 218), millPond);
        addBiome(new Color(75, 108, 143), wastePond);

        addBiome(new Color(156, 207, 113), new MEBiome(4, MEBiomeKeys.ANDUIN, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE, Blocks.STONE));
        addBiome(new Color(200, 209, 255), new MEBiome(39, MEBiomeKeys.BLUE_MOUNTAINS, SimpleBlockSets.BLUE_ROCK.base(), SimpleBlockSets.BLUE_ROCK.base(), Blocks.STONE, Blocks.STONE));
        addBiome(new Color(178, 183, 210), new MEBiome(18, MEBiomeKeys.BLUE_MOUNTAINS_FOOTHILLS, SimpleBlockSets.BLUE_ROCK.base(), SimpleBlockSets.BLUE_ROCK.base(), Blocks.STONE, Blocks.STONE));
        addBiome(new Color(193, 188, 131), new MEBiome(6, MEBiomeKeys.CORSAIR_COASTS, Blocks.GRASS_BLOCK, Blocks.SANDSTONE, Blocks.STONE, Blocks.STONE));
        addBiome(new Color(132, 164, 78), new MEBiome(4, MEBiomeKeys.DALE, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE, Blocks.STONE));
        addBiome(new Color(93, 113, 92), new MEBiome(31, MEBiomeKeys.DORWINION_HILLS, Blocks.STONE, Blocks.STONE, Blocks.STONE, Blocks.STONE));
        addBiome(new Color(132, 137, 124), new MEBiome(19, MEBiomeKeys.DUNLAND_FOOTHILLS, Blocks.STONE, Blocks.STONE, Blocks.STONE, Blocks.STONE));
        addBiome(new Color(136, 146, 80), new MEBiome(4, MEBiomeKeys.EASTERN_RHOVANION, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE, Blocks.STONE));
        addBiome(new Color(117, 164, 109), new MEBiome(4, MEBiomeKeys.ENEDWAITH, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE, Blocks.STONE));
        addBiome(new Color(106, 155, 104), new MEBiome(4, MEBiomeKeys.EREGION, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE, Blocks.STONE));
        addBiome(new Color(171, 193, 128), new MEBiome(4, MEBiomeKeys.ERIADOR, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE, Blocks.STONE));
        addBiome(new Color(54, 75, 12), new MEBiome(6, MEBiomeKeys.FANGORN, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE, Blocks.STONE));
        addBiome(new Color(236, 236, 236), new MEBiome(8, MEBiomeKeys.FORODWAITH, Blocks.SNOW, Blocks.SNOW_BLOCK, Blocks.STONE, Blocks.STONE));
        addBiome(new Color(101, 123, 243), new MEBiome(-18, MEBiomeKeys.FROZEN_OCEAN, Blocks.GRAVEL, Blocks.STONE, Blocks.STONE, Blocks.STONE));
        addBiome(new Color(91, 189, 85), new MEBiome(4, MEBiomeKeys.GONDOR, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.CALCITE, Blocks.STONE));
        addBiome(new Color(90, 159, 90), new MEBiome(7, MEBiomeKeys.GREY_PLAINS, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE, Blocks.STONE));
        addBiome(new Color(204, 196, 113), new MEBiome(4, MEBiomeKeys.HARAD, Blocks.GRASS_BLOCK, Blocks.SANDSTONE, Blocks.STONE, Blocks.STONE));
        addBiome(new Color(237, 229, 145), new MEBiome(5, MEBiomeKeys.HARAD_DESERT, Blocks.SAND, Blocks.SAND, Blocks.SANDSTONE, Blocks.STONE));
        addBiome(new Color(206, 214, 140), new MEBiome(6, MEBiomeKeys.HARONDOR, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE, Blocks.STONE));
        addBiome(new Color(148, 148, 148), new MEBiome(37, MEBiomeKeys.IRON_HILLS, Blocks.STONE, Blocks.STONE, Blocks.STONE, Blocks.DEEPSLATE));
        addBiome(new Color(67, 193, 125), new MEBiome(4, MEBiomeKeys.LINDON, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE, Blocks.STONE));
        addBiome(new Color(69, 92, 228), new MEBiome(-17, MEBiomeKeys.LONG_LAKE, Blocks.SAND, Blocks.STONE, Blocks.STONE, Blocks.STONE));
        addBiome(new Color(205, 206, 96), new MEBiome(4, MEBiomeKeys.LORIEN_EDGE, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE, Blocks.STONE));
        addBiome(new Color(221, 216, 28), new MEBiome(4, MEBiomeKeys.LOTHLORIEN, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE, Blocks.STONE));
        addBiome(new Color(10, 54, 15), new MEBiome(6, MEBiomeKeys.MIRKWOOD, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE, Blocks.STONE));
        addBiome(new Color(129, 129, 129), new MEBiome(41, MEBiomeKeys.MISTY_MOUNTAINS, Blocks.STONE, Blocks.STONE, Blocks.STONE, Blocks.STONE));
        addBiome(new Color(60, 42, 42), new MEBiome(5, MEBiomeKeys.MORDOR, SimpleBlockSets.ASHEN_ROCK.base(), SimpleBlockSets.ASHEN_ROCK.base(), SimpleBlockSets.ASHEN_ROCK.base(), SimpleBlockSets.ASHEN_ROCK.base()));
        addBiome(new Color(36, 31, 31), new MEBiome(37, MEBiomeKeys.MORDOR_MOUNTAINS, SimpleBlockSets.ASHEN_ROCK.base(), SimpleBlockSets.ASHEN_ROCK.base(), SimpleBlockSets.ASHEN_ROCK.base(), SimpleBlockSets.ASHEN_ROCK.base()));
        addBiome(new Color(88, 82, 71), new MEBiome(3, MEBiomeKeys.MORDOR_WASTES, Blocks.GRASS_BLOCK, ModBlocks.MORDOR_DIRT, Blocks.STONE, Blocks.STONE));
        addBiome(new Color(106, 127, 62), new MEBiome(5, MEBiomeKeys.NORTHERN_DUNLAND, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE, Blocks.STONE));
        addBiome(new Color(160, 165, 149), new MEBiome(6, MEBiomeKeys.NORTHERN_WASTELANDS, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE, Blocks.STONE));
        addBiome(new Color(74, 77, 51), new MEBiome(5, MEBiomeKeys.NURN, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE, Blocks.STONE));
        addBiome(new Color(79, 91, 161), new MEBiome(-21, MEBiomeKeys.NURN_RIVER, ModBlocks.MORDOR_DIRT, ModBlocks.MORDOR_DIRT, Blocks.STONE, Blocks.STONE));
        addBiome(new Color(88, 94, 130), new MEBiome(-17, MEBiomeKeys.NURN_SEA, ModBlocks.MORDOR_DIRT, ModBlocks.MORDOR_DIRT, Blocks.STONE, Blocks.STONE));
        addBiome(new Color(75, 106, 199), new MEBiome(-12, MEBiomeKeys.OCEAN_COAST, Blocks.SAND, Blocks.STONE, Blocks.STONE, Blocks.STONE));
        addBiome(new Color(73, 82, 53), new MEBiome(6, MEBiomeKeys.OLD_ANGMAR, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE, Blocks.STONE));
        addBiome(new Color(213, 255, 132), new MEBiome(4, MEBiomeKeys.OLD_ARTHEDAIN, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE, Blocks.STONE));
        addBiome(new Color(115, 135, 74), new MEBiome(5, MEBiomeKeys.OLD_RHUDAUR, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE, Blocks.STONE));
        addBiome(new Color(172, 176, 113), new MEBiome(4, MEBiomeKeys.RHUN, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE, Blocks.STONE));
        addBiome(new Color(76, 202, 95), new MEBiome(3, MEBiomeKeys.RIVENDELL, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE, Blocks.STONE));
        addBiome(new Color(137, 171, 142), new MEBiome(5, MEBiomeKeys.RIVENDELL_FOOTHILLS, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE, Blocks.STONE));
        addBiome(new Color(95, 140, 195), new MEBiome(-19, MEBiomeKeys.RIVER, Blocks.SAND, Blocks.STONE, Blocks.STONE, Blocks.STONE));
        addBiome(new Color(183, 229, 102), new MEBiome(4, MEBiomeKeys.ROHAN, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE, Blocks.STONE));
        addBiome(new Color(61, 104, 187), new MEBiome(-19, MEBiomeKeys.SEA_OF_RHUN, Blocks.SAND, Blocks.STONE, Blocks.STONE, Blocks.STONE));
        addBiome(new Color(84, 217, 70), new MEBiome(4, MEBiomeKeys.SHIRE, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE, Blocks.STONE));
        addBiome(new Color(155, 139, 29), new MEBiome(4, MEBiomeKeys.SOUTHEAST_RHOVANION, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE, Blocks.STONE));
        addBiome(new Color(121, 186, 111), new MEBiome(4, MEBiomeKeys.SOUTHERN_DUNLAND, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE, Blocks.STONE));
        addBiome(new Color(206, 179, 156), new MEBiome(4, MEBiomeKeys.SOUTHERN_FOROCHEL, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE, Blocks.STONE));
        addBiome(new Color(126, 149, 79), new MEBiome(4, MEBiomeKeys.THE_ANGLE, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE, Blocks.STONE));
        addBiome(new Color(156, 150, 150), new MEBiome(13, MEBiomeKeys.TOLFALAS, Blocks.GRASS_BLOCK, Blocks.STONE, Blocks.STONE, Blocks.STONE));
        addBiome(new Color(40, 66, 42), new MEBiome(8, MEBiomeKeys.TROLLSHAWS, Blocks.GRASS_BLOCK, Blocks.STONE, Blocks.STONE, Blocks.STONE));
        addBiome(new Color(225, 192, 133), new MEBiome(7, MEBiomeKeys.UMBAR, Blocks.GRASS_BLOCK, Blocks.SANDSTONE, Blocks.STONE, Blocks.STONE));
        addBiome(new Color(219, 245, 215), new MEBiome(36, MEBiomeKeys.WHITE_MOUNTAINS, Blocks.CALCITE, Blocks.CALCITE, Blocks.STONE, Blocks.STONE));

        waterBiomes.add(MEBiomeKeys.FROZEN_OCEAN);
        waterBiomes.add(MEBiomeKeys.LONG_LAKE);
        waterBiomes.add(MEBiomeKeys.NURN_RIVER);
        waterBiomes.add(MEBiomeKeys.NURN_SEA);
        waterBiomes.add(MEBiomeKeys.OCEAN);
        waterBiomes.add(MEBiomeKeys.OCEAN_COAST);
        waterBiomes.add(MEBiomeKeys.RIVER);
        waterBiomes.add(MEBiomeKeys.SEA_OF_RHUN);

        wastePondBiomes.add(MEBiomeKeys.MORDOR);
        wastePondBiomes.add(MEBiomeKeys.MORDOR_MOUNTAINS);
        wastePondBiomes.add(MEBiomeKeys.MORDOR_WASTES);
        wastePondBiomes.add(MEBiomeKeys.NURN);
    }
}
