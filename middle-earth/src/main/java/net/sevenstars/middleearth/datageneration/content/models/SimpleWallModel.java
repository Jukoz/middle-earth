package net.sevenstars.middleearth.datageneration.content.models;

import net.sevenstars.middleearth.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import java.util.ArrayList;
import java.util.List;

public class SimpleWallModel {
    public record Wall(Block block, Block wall) {}
    public static List<Wall> blocks = new ArrayList<>() {
        {
            add(new Wall(ModBlocks.REED_BLOCK, ModBlocks.REED_WALL));
            add(new Wall(ModBlocks.STRAW_BLOCK, ModBlocks.STRAW_WALL));
        }
    };

    public static List<Wall> strippedWalls = new ArrayList<>() {
        {
        }
    };

    public static List<Wall> vanillaWalls = new ArrayList<>() {
        {
            add(new Wall(Blocks.PACKED_MUD, ModBlocks.PACKED_MUD_WALL));
            add(new Wall(Blocks.CALCITE, ModBlocks.CALCITE_WALL));
            add(new Wall(Blocks.SMOOTH_BASALT, ModBlocks.SMOOTH_BASALT_WALL));

            add(new Wall(Blocks.CUT_COPPER, ModBlocks.CUT_COPPER_WALL));
            add(new Wall(Blocks.EXPOSED_CUT_COPPER, ModBlocks.EXPOSED_CUT_COPPER_WALL));
            add(new Wall(Blocks.WEATHERED_CUT_COPPER, ModBlocks.WEATHERED_CUT_COPPER_WALL));
            add(new Wall(Blocks.OXIDIZED_CUT_COPPER, ModBlocks.OXIDIZED_CUT_COPPER_WALL));
            add(new Wall(Blocks.WAXED_CUT_COPPER, ModBlocks.WAXED_CUT_COPPER_WALL));
            add(new Wall(Blocks.WAXED_EXPOSED_CUT_COPPER, ModBlocks.WAXED_EXPOSED_CUT_COPPER_WALL));
            add(new Wall(Blocks.WAXED_WEATHERED_CUT_COPPER, ModBlocks.WAXED_WEATHERED_CUT_COPPER_WALL));
            add(new Wall(Blocks.WAXED_OXIDIZED_CUT_COPPER, ModBlocks.WAXED_OXIDIZED_CUT_COPPER_WALL));

        }
    };

    public static List<Wall> vanillaStrippedWalls = new ArrayList<>() {
        {
            add(new Wall(Blocks.STRIPPED_OAK_WOOD, ModBlocks.STRIPPED_OAK_WOOD_WALL));
            add(new Wall(Blocks.STRIPPED_SPRUCE_WOOD, ModBlocks.STRIPPED_SPRUCE_WOOD_WALL));
            add(new Wall(Blocks.STRIPPED_BIRCH_WOOD, ModBlocks.STRIPPED_BIRCH_WOOD_WALL));
            add(new Wall(Blocks.STRIPPED_JUNGLE_WOOD, ModBlocks.STRIPPED_JUNGLE_WOOD_WALL));
            add(new Wall(Blocks.STRIPPED_ACACIA_WOOD, ModBlocks.STRIPPED_ACACIA_WOOD_WALL));
            add(new Wall(Blocks.STRIPPED_DARK_OAK_WOOD, ModBlocks.STRIPPED_DARK_OAK_WOOD_WALL));
            add(new Wall(Blocks.STRIPPED_MANGROVE_WOOD, ModBlocks.STRIPPED_MANGROVE_WOOD_WALL));
            add(new Wall(Blocks.STRIPPED_CHERRY_WOOD, ModBlocks.STRIPPED_CHERRY_WOOD_WALL));
        }
    };

    public static List<Wall> vanillaWoodWalls = new ArrayList<>() {
        {
            add(new Wall(Blocks.OAK_WOOD, ModBlocks.OAK_WOOD_WALL));
            add(new Wall(Blocks.SPRUCE_WOOD, ModBlocks.SPRUCE_WOOD_WALL));
            add(new Wall(Blocks.BIRCH_WOOD, ModBlocks.BIRCH_WOOD_WALL));
            add(new Wall(Blocks.JUNGLE_WOOD, ModBlocks.JUNGLE_WOOD_WALL));
            add(new Wall(Blocks.ACACIA_WOOD, ModBlocks.ACACIA_WOOD_WALL));
            add(new Wall(Blocks.DARK_OAK_WOOD, ModBlocks.DARK_OAK_WOOD_WALL));
            add(new Wall(Blocks.MANGROVE_WOOD, ModBlocks.MANGROVE_WOOD_WALL));
            add(new Wall(Blocks.CHERRY_WOOD, ModBlocks.CHERRY_WOOD_WALL));
        }
    };
}
