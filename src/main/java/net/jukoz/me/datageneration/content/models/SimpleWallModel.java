package net.jukoz.me.datageneration.content.models;

import net.jukoz.me.block.ModBlocks;
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
            add(new SimpleWallModel.Wall(Blocks.PACKED_MUD, ModBlocks.PACKED_MUD_WALL));
        }
    };

    public static List<Wall> vanillaStrippedWalls = new ArrayList<>() {
        {
            add(new SimpleWallModel.Wall(Blocks.STRIPPED_OAK_WOOD, ModBlocks.STRIPPED_OAK_WOOD_WALL));
            add(new SimpleWallModel.Wall(Blocks.STRIPPED_SPRUCE_WOOD, ModBlocks.STRIPPED_SPRUCE_WOOD_WALL));
            add(new SimpleWallModel.Wall(Blocks.STRIPPED_BIRCH_WOOD, ModBlocks.STRIPPED_BIRCH_WOOD_WALL));
            add(new SimpleWallModel.Wall(Blocks.STRIPPED_JUNGLE_WOOD, ModBlocks.STRIPPED_JUNGLE_WOOD_WALL));
            add(new SimpleWallModel.Wall(Blocks.STRIPPED_ACACIA_WOOD, ModBlocks.STRIPPED_ACACIA_WOOD_WALL));
            add(new SimpleWallModel.Wall(Blocks.STRIPPED_DARK_OAK_WOOD, ModBlocks.STRIPPED_DARK_OAK_WOOD_WALL));
            add(new SimpleWallModel.Wall(Blocks.STRIPPED_MANGROVE_WOOD, ModBlocks.STRIPPED_MANGROVE_WOOD_WALL));
            add(new SimpleWallModel.Wall(Blocks.STRIPPED_CHERRY_WOOD, ModBlocks.STRIPPED_CHERRY_WOOD_WALL));
        }
    };

    public static List<Wall> vanillaWoodWalls = new ArrayList<>() {
        {
            add(new SimpleWallModel.Wall(Blocks.OAK_WOOD, ModBlocks.OAK_WOOD_WALL));
            add(new SimpleWallModel.Wall(Blocks.SPRUCE_WOOD, ModBlocks.SPRUCE_WOOD_WALL));
            add(new SimpleWallModel.Wall(Blocks.BIRCH_WOOD, ModBlocks.BIRCH_WOOD_WALL));
            add(new SimpleWallModel.Wall(Blocks.JUNGLE_WOOD, ModBlocks.JUNGLE_WOOD_WALL));
            add(new SimpleWallModel.Wall(Blocks.ACACIA_WOOD, ModBlocks.ACACIA_WOOD_WALL));
            add(new SimpleWallModel.Wall(Blocks.DARK_OAK_WOOD, ModBlocks.DARK_OAK_WOOD_WALL));
            add(new SimpleWallModel.Wall(Blocks.MANGROVE_WOOD, ModBlocks.MANGROVE_WOOD_WALL));
            add(new SimpleWallModel.Wall(Blocks.CHERRY_WOOD, ModBlocks.CHERRY_WOOD_WALL));
        }
    };
}
