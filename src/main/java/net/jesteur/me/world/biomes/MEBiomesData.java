package net.jesteur.me.world.biomes;

import net.jesteur.me.block.ModBlockSets;
import net.jesteur.me.block.ModBlocks;
import net.minecraft.block.Blocks;

import java.awt.*;
import java.util.HashMap;

/**
 * Converts PNG pixel color to a BiomeKey reference.
 */
public class MEBiomesData {
    public static HashMap<Color, MEBiome> biomeHeights = new HashMap<>();
    public static MEBiome defaultBiome;

    /// Only supports height value from -22 to 41
    public static final int MINIMAL_HEIGHT = -22;

    public static void loadBiomes() {
        defaultBiome = new MEBiome(-21, MEBiomeKeys.OCEAN, Blocks.SAND, Blocks.STONE, Blocks.STONE, Blocks.STONE);
        biomeHeights.put(new Color(55, 90, 195), defaultBiome);
        biomeHeights.put(new Color(157, 208, 113), new MEBiome(4, MEBiomeKeys.ANDUIN, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE, Blocks.STONE));
        biomeHeights.put(new Color(200, 209, 255), new MEBiome(39, MEBiomeKeys.BLUE_MOUNTAINS, ModBlockSets.BLUE_ROCK.base(), ModBlockSets.BLUE_ROCK.base(), Blocks.STONE, Blocks.STONE));
        biomeHeights.put(new Color(132,164,78), new MEBiome(4, MEBiomeKeys.DALE, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE, Blocks.STONE));
        biomeHeights.put(new Color(171,193,128), new MEBiome(4, MEBiomeKeys.ERIADOR, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE, Blocks.STONE));
        biomeHeights.put(new Color(54, 75, 11), new MEBiome(6, MEBiomeKeys.FANGORN, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE, Blocks.STONE));
        biomeHeights.put(new Color(236, 236, 236), new MEBiome(8, MEBiomeKeys.FORODWAITH, Blocks.SNOW, Blocks.SNOW_BLOCK, Blocks.STONE, Blocks.STONE));
        biomeHeights.put(new Color(91, 189, 85), new MEBiome(4, MEBiomeKeys.GONDOR, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.CALCITE, Blocks.STONE));
        biomeHeights.put(new Color(90,159,90), new MEBiome(7, MEBiomeKeys.GREY_PLAINS, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE, Blocks.STONE));
        biomeHeights.put(new Color(204, 196, 113), new MEBiome(4, MEBiomeKeys.HARAD, Blocks.GRASS_BLOCK, Blocks.SANDSTONE, Blocks.STONE, Blocks.STONE));
        biomeHeights.put(new Color(148, 148, 148), new MEBiome(37, MEBiomeKeys.IRON_HILLS, Blocks.STONE, Blocks.STONE, Blocks.STONE, Blocks.DEEPSLATE));
        biomeHeights.put(new Color(67,193,125), new MEBiome(4, MEBiomeKeys.LINDON, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE, Blocks.STONE));
        biomeHeights.put(new Color(221, 216, 28), new MEBiome(4, MEBiomeKeys.LOTHLORIEN, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE, Blocks.STONE));
        biomeHeights.put(new Color(10, 54, 15), new MEBiome(6, MEBiomeKeys.MIRKWOOD, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE, Blocks.STONE));
        biomeHeights.put(new Color(129, 129, 129), new MEBiome(41, MEBiomeKeys.MISTY_MOUNTAINS, Blocks.STONE, Blocks.STONE, Blocks.STONE, Blocks.STONE));
        biomeHeights.put(new Color(60, 42, 42), new MEBiome(5, MEBiomeKeys.MORDOR, ModBlockSets.ASHEN_ROCK.base(), ModBlockSets.ASHEN_ROCK.base(), ModBlockSets.ASHEN_ROCK.base(), ModBlockSets.ASHEN_ROCK.base()));
        biomeHeights.put(new Color(36, 31, 31), new MEBiome(37, MEBiomeKeys.MORDOR_MOUNTAINS, ModBlockSets.ASHEN_ROCK.base(), ModBlockSets.ASHEN_ROCK.base(), ModBlockSets.ASHEN_ROCK.base(), ModBlockSets.ASHEN_ROCK.base()));
        biomeHeights.put(new Color(88, 82, 71), new MEBiome(3, MEBiomeKeys.MORDOR_WASTES, Blocks.GRASS_BLOCK, ModBlocks.MORDOR_DIRT, Blocks.STONE, Blocks.STONE));
        biomeHeights.put(new Color(160, 165, 149), new MEBiome(6, MEBiomeKeys.NORTHERN_WASTELANDS, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE, Blocks.STONE));
        biomeHeights.put(new Color(79,91,161), new MEBiome(-16, MEBiomeKeys.NURN_RIVER, ModBlocks.MORDOR_DIRT, ModBlocks.MORDOR_DIRT, Blocks.STONE, Blocks.STONE));
        biomeHeights.put(new Color(88,94,130), new MEBiome(-17, MEBiomeKeys.NURN_SEA, ModBlocks.MORDOR_DIRT, ModBlocks.MORDOR_DIRT, Blocks.STONE, Blocks.STONE));
        biomeHeights.put(new Color(174, 178, 113), new MEBiome(4, MEBiomeKeys.RHUN, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE, Blocks.STONE));
        biomeHeights.put(new Color(183,229,102), new MEBiome(4, MEBiomeKeys.ROHAN, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE, Blocks.STONE));
        biomeHeights.put(new Color(95,140,195), new MEBiome(-18, MEBiomeKeys.RIVER, Blocks.SAND, Blocks.STONE, Blocks.STONE, Blocks.STONE));
        biomeHeights.put(new Color(84, 217, 70), new MEBiome(4, MEBiomeKeys.SHIRE, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE, Blocks.STONE));
        biomeHeights.put(new Color(206, 179, 156), new MEBiome(4, MEBiomeKeys.SOUTHERN_FOROCHEL, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE, Blocks.STONE));
        biomeHeights.put(new Color(219, 245, 215), new MEBiome(36, MEBiomeKeys.WHITE_MOUNTAINS, Blocks.CALCITE, Blocks.CALCITE, Blocks.STONE, Blocks.STONE));
    }
}
