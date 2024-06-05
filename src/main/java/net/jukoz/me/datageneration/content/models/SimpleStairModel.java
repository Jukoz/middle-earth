package net.jukoz.me.datageneration.content.models;

import net.jukoz.me.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import java.util.ArrayList;
import java.util.List;

public class SimpleStairModel {
    public record Stair(Block origin, Block stairs) {}
    public static List<Stair> stairs = new ArrayList<>() {
        {
            add(new Stair(ModBlocks.REED_BLOCK, ModBlocks.REED_STAIRS));
            add(new Stair(ModBlocks.GRASSY_DIRT, ModBlocks.GRASSY_DIRT_STAIRS));
            add(new Stair(ModBlocks.DRY_DIRT, ModBlocks.DRY_DIRT_STAIRS));
            add(new Stair(ModBlocks.ASHEN_DIRT, ModBlocks.ASHEN_DIRT_STAIRS));
            add(new Stair(ModBlocks.COBBLY_ASHEN_DIRT, ModBlocks.COBBLY_ASHEN_DIRT_STAIRS));
            add(new Stair(ModBlocks.COBBLY_DIRT, ModBlocks.COBBLY_DIRT_STAIRS));
            add(new Stair(ModBlocks.STRAW_BLOCK, ModBlocks.STRAW_STAIRS));
            add(new Stair(ModBlocks.CUT_LEAD, ModBlocks.CUT_LEAD_STAIRS));
            add(new Stair(ModBlocks.CUT_SILVER, ModBlocks.CUT_SILVER_STAIRS));
        }
    };

    public static List<Stair> strippedStairs = new ArrayList<>() {
        {
        }
    };

    public static List<Stair> woodStairs = new ArrayList<>() {
        {
        }
    };

    public static List<Stair> vanillaStairs = new ArrayList<>() {
        {
            add(new Stair(Blocks.DIRT, ModBlocks.DIRT_STAIRS));
            add(new Stair(Blocks.COARSE_DIRT, ModBlocks.COARSE_DIRT_STAIRS));
            add(new Stair(Blocks.MUD, ModBlocks.MUD_STAIRS));

            add(new Stair(Blocks.PACKED_MUD, ModBlocks.PACKED_MUD_STAIRS));
            add(new Stair(Blocks.TUFF, ModBlocks.TUFF_STAIRS));
            add(new Stair(Blocks.CALCITE, ModBlocks.CALCITE_STAIRS));
            add(new Stair(Blocks.SMOOTH_BASALT, ModBlocks.SMOOTH_BASALT_STAIRS));

            add(new Stair(Blocks.BLACK_WOOL, ModBlocks.BLACK_WOOL_STAIRS));
            add(new Stair(Blocks.BLUE_WOOL, ModBlocks.BLUE_WOOL_STAIRS));
            add(new Stair(Blocks.BROWN_WOOL, ModBlocks.BROWN_WOOL_STAIRS));
            add(new Stair(Blocks.CYAN_WOOL, ModBlocks.CYAN_WOOL_STAIRS));
            add(new Stair(Blocks.GRAY_WOOL, ModBlocks.GRAY_WOOL_STAIRS));
            add(new Stair(Blocks.GREEN_WOOL, ModBlocks.GREEN_WOOL_STAIRS));
            add(new Stair(Blocks.LIGHT_BLUE_WOOL, ModBlocks.LIGHT_BLUE_WOOL_STAIRS));
            add(new Stair(Blocks.LIGHT_GRAY_WOOL, ModBlocks.LIGHT_GRAY_WOOL_STAIRS));
            add(new Stair(Blocks.LIME_WOOL, ModBlocks.LIME_WOOL_STAIRS));
            add(new Stair(Blocks.MAGENTA_WOOL, ModBlocks.MAGENTA_WOOL_STAIRS));
            add(new Stair(Blocks.ORANGE_WOOL, ModBlocks.ORANGE_WOOL_STAIRS));
            add(new Stair(Blocks.PINK_WOOL, ModBlocks.PINK_WOOL_STAIRS));
            add(new Stair(Blocks.PURPLE_WOOL, ModBlocks.PURPLE_WOOL_STAIRS));
            add(new Stair(Blocks.RED_WOOL, ModBlocks.RED_WOOL_STAIRS));
            add(new Stair(Blocks.WHITE_WOOL, ModBlocks.WHITE_WOOL_STAIRS));
            add(new Stair(Blocks.YELLOW_WOOL, ModBlocks.YELLOW_WOOL_STAIRS));
        }
    };

    public static List<Stair> vanillaWoodStairs = new ArrayList<>() {
        {
            add(new SimpleStairModel.Stair(Blocks.OAK_WOOD, ModBlocks.OAK_WOOD_STAIRS));
            add(new SimpleStairModel.Stair(Blocks.SPRUCE_WOOD, ModBlocks.SPRUCE_WOOD_STAIRS));
            add(new SimpleStairModel.Stair(Blocks.BIRCH_WOOD, ModBlocks.BIRCH_WOOD_STAIRS));
            add(new SimpleStairModel.Stair(Blocks.JUNGLE_WOOD, ModBlocks.JUNGLE_WOOD_STAIRS));
            add(new SimpleStairModel.Stair(Blocks.ACACIA_WOOD, ModBlocks.ACACIA_WOOD_STAIRS));
            add(new SimpleStairModel.Stair(Blocks.DARK_OAK_WOOD, ModBlocks.DARK_OAK_WOOD_STAIRS));
            add(new SimpleStairModel.Stair(Blocks.MANGROVE_WOOD, ModBlocks.MANGROVE_WOOD_STAIRS));
            add(new SimpleStairModel.Stair(Blocks.CHERRY_WOOD, ModBlocks.CHERRY_WOOD_STAIRS));
        }
    };

    public static List<Stair> vanillaStrippedStairs = new ArrayList<>() {
        {
            add(new SimpleStairModel.Stair(Blocks.STRIPPED_OAK_WOOD, ModBlocks.STRIPPED_OAK_WOOD_STAIRS));
            add(new SimpleStairModel.Stair(Blocks.STRIPPED_SPRUCE_WOOD, ModBlocks.STRIPPED_SPRUCE_WOOD_STAIRS));
            add(new SimpleStairModel.Stair(Blocks.STRIPPED_BIRCH_WOOD, ModBlocks.STRIPPED_BIRCH_WOOD_STAIRS));
            add(new SimpleStairModel.Stair(Blocks.STRIPPED_JUNGLE_WOOD, ModBlocks.STRIPPED_JUNGLE_WOOD_STAIRS));
            add(new SimpleStairModel.Stair(Blocks.STRIPPED_ACACIA_WOOD, ModBlocks.STRIPPED_ACACIA_WOOD_STAIRS));
            add(new SimpleStairModel.Stair(Blocks.STRIPPED_DARK_OAK_WOOD, ModBlocks.STRIPPED_DARK_OAK_WOOD_STAIRS));
            add(new SimpleStairModel.Stair(Blocks.STRIPPED_MANGROVE_WOOD, ModBlocks.STRIPPED_MANGROVE_WOOD_STAIRS));
            add(new SimpleStairModel.Stair(Blocks.STRIPPED_CHERRY_WOOD, ModBlocks.STRIPPED_CHERRY_WOOD_STAIRS));
        }
    };
}
