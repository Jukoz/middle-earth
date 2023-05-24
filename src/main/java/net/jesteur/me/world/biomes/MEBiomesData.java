package net.jesteur.me.world.biomes;

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

    public static void loadBiomes() {
        defaultBiome = new MEBiome(-21, MEBiomeKeys.OCEAN, Blocks.SAND, Blocks.STONE, Blocks.STONE, Blocks.STONE);
        biomeHeights.put(new Color(55, 90, 195), defaultBiome);
        biomeHeights.put(new Color(157, 208, 113), new MEBiome(4, MEBiomeKeys.ANDUIN, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE, Blocks.STONE));
        biomeHeights.put(new Color(200, 209, 255), new MEBiome(48, MEBiomeKeys.BLUE_MOUNTAINS, ModBlocks.BLUE_ROCK, ModBlocks.BLUE_ROCK, Blocks.STONE, Blocks.STONE));
        biomeHeights.put(new Color(171,193,128), new MEBiome(4, MEBiomeKeys.ERIADOR, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE, Blocks.STONE));
        biomeHeights.put(new Color(54, 75, 11), new MEBiome(6, MEBiomeKeys.FANGORN, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE, Blocks.STONE));
        biomeHeights.put(new Color(236, 236, 236), new MEBiome(8, MEBiomeKeys.FORODWAITH, Blocks.SNOW, Blocks.SNOW_BLOCK, Blocks.STONE, Blocks.STONE));
        biomeHeights.put(new Color(91, 189, 85), new MEBiome(4, MEBiomeKeys.GONDOR, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.CALCITE, Blocks.STONE));
        biomeHeights.put(new Color(204, 196, 113), new MEBiome(4, MEBiomeKeys.HARAD, Blocks.GRASS_BLOCK, Blocks.SANDSTONE, Blocks.STONE, Blocks.STONE));
        biomeHeights.put(new Color(148, 148, 148), new MEBiome(37, MEBiomeKeys.IRON_HILLS, Blocks.STONE, Blocks.STONE, Blocks.STONE, Blocks.DEEPSLATE));
        biomeHeights.put(new Color(221, 216, 28), new MEBiome(4, MEBiomeKeys.LOTHLORIEN, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE, Blocks.STONE));
        biomeHeights.put(new Color(10, 54, 15), new MEBiome(6, MEBiomeKeys.MIRKWOOD, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE, Blocks.STONE));
        biomeHeights.put(new Color(129, 129, 129), new MEBiome(41, MEBiomeKeys.MISTY_MOUNTAINS, Blocks.STONE, Blocks.STONE, Blocks.STONE, Blocks.STONE));
        biomeHeights.put(new Color(60, 42, 42), new MEBiome(5, MEBiomeKeys.MORDOR, ModBlocks.ASHEN_ROCK, ModBlocks.ASHEN_ROCK, ModBlocks.ASHEN_ROCK, ModBlocks.ASHEN_ROCK));
        biomeHeights.put(new Color(36, 31, 31), new MEBiome(37, MEBiomeKeys.MORDOR_MOUNTAINS, ModBlocks.ASHEN_ROCK, ModBlocks.ASHEN_ROCK, ModBlocks.ASHEN_ROCK, ModBlocks.ASHEN_ROCK));
        biomeHeights.put(new Color(88, 82, 71), new MEBiome(3, MEBiomeKeys.MORDOR_WASTES, Blocks.GRASS_BLOCK, ModBlocks.MORDOR_DIRT, Blocks.STONE, Blocks.STONE));
        biomeHeights.put(new Color(160, 165, 149), new MEBiome(6, MEBiomeKeys.NORTHEN_WASTELANDS, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE, Blocks.STONE));
        biomeHeights.put(new Color(174, 178, 113), new MEBiome(4, MEBiomeKeys.RHUN, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE, Blocks.STONE));
        biomeHeights.put(new Color(95,140,195), new MEBiome(-20, MEBiomeKeys.RIVER, Blocks.SAND, Blocks.STONE, Blocks.STONE, Blocks.STONE));
        biomeHeights.put(new Color(84, 217, 70), new MEBiome(4, MEBiomeKeys.SHIRE, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE, Blocks.STONE));
        biomeHeights.put(new Color(219, 245, 215), new MEBiome(36, MEBiomeKeys.WHITE_MOUNTAINS, Blocks.CALCITE, Blocks.CALCITE, Blocks.STONE, Blocks.STONE));
    }
}
