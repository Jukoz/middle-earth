package net.jukoz.me.datageneration.content.models;

import net.jukoz.me.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import java.util.ArrayList;
import java.util.List;

public class SimpleVerticalSlabModel {
    public record VerticalSlab(Block block, Block slab, Block verticalSlab) {}
    public static List<SimpleVerticalSlabModel.VerticalSlab> verticalSlabs = new ArrayList<>() {
        {
            add(new VerticalSlab(ModBlocks.REED_BLOCK, ModBlocks.REED_SLAB, ModBlocks.REED_VERTICAL_SLAB));
            add(new VerticalSlab(ModBlocks.STRAW_BLOCK, ModBlocks.STRAW_SLAB, ModBlocks.STRAW_VERTICAL_SLAB));
            add(new VerticalSlab(ModBlocks.CUT_LEAD, ModBlocks.CUT_LEAD_SLAB, ModBlocks.CUT_LEAD_VERTICAL_SLAB));
        }
    };

    public static List<SimpleVerticalSlabModel.VerticalSlab> vanillaVerticalSlabs = new ArrayList<>() {
        {
            add(new VerticalSlab(Blocks.STONE, Blocks.STONE_SLAB, ModBlocks.STONE_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.COBBLESTONE, Blocks.COBBLESTONE_SLAB, ModBlocks.COBBLESTONE_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.MOSSY_COBBLESTONE, Blocks.MOSSY_COBBLESTONE_SLAB, ModBlocks.MOSSY_COBBLESTONE_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.SMOOTH_STONE, Blocks.SMOOTH_STONE_SLAB, ModBlocks.SMOOTH_STONE_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.STONE_BRICKS, Blocks.STONE_BRICK_SLAB, ModBlocks.STONE_BRICK_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.MOSSY_STONE_BRICKS, Blocks.MOSSY_STONE_BRICK_SLAB, ModBlocks.MOSSY_STONE_BRICK_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.GRANITE, Blocks.GRANITE_SLAB, ModBlocks.GRANITE_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.POLISHED_GRANITE, Blocks.POLISHED_GRANITE_SLAB, ModBlocks.POLISHED_GRANITE_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.DIORITE, Blocks.DIORITE_SLAB, ModBlocks.DIORITE_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.POLISHED_DIORITE, Blocks.POLISHED_DIORITE_SLAB, ModBlocks.POLISHED_DIORITE_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.ANDESITE, Blocks.ANDESITE_SLAB, ModBlocks.ANDESITE_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.POLISHED_ANDESITE, Blocks.POLISHED_ANDESITE_SLAB, ModBlocks.POLISHED_ANDESITE_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.COBBLED_DEEPSLATE, Blocks.COBBLED_DEEPSLATE_SLAB, ModBlocks.COBBLED_DEEPSLATE_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.POLISHED_DEEPSLATE, Blocks.POLISHED_DEEPSLATE_SLAB, ModBlocks.POLISHED_DEEPSLATE_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.DEEPSLATE_BRICKS, Blocks.DEEPSLATE_BRICKS, ModBlocks.DEEPSLATE_BRICK_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.DEEPSLATE_TILES, Blocks.DEEPSLATE_TILE_SLAB, ModBlocks.DEEPSLATE_TILE_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.BRICKS, Blocks.BRICK_SLAB, ModBlocks.BRICK_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.MUD_BRICKS, Blocks.MUD_BRICK_SLAB, ModBlocks.MUD_BRICK_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.SANDSTONE, Blocks.SANDSTONE_SLAB, ModBlocks.SANDSTONE_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.SMOOTH_SANDSTONE, Blocks.SMOOTH_SANDSTONE_SLAB, ModBlocks.SMOOTH_SANDSTONE_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.CUT_SANDSTONE, Blocks.CUT_SANDSTONE_SLAB, ModBlocks.CUT_SANDSTONE_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.RED_SANDSTONE, Blocks.RED_SANDSTONE_SLAB, ModBlocks.RED_SANDSTONE_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.SMOOTH_RED_SANDSTONE, Blocks.SMOOTH_RED_SANDSTONE_SLAB, ModBlocks.SMOOTH_RED_SANDSTONE_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.CUT_RED_SANDSTONE, Blocks.CUT_RED_SANDSTONE_SLAB, ModBlocks.CUT_RED_SANDSTONE_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.PRISMARINE, Blocks.PRISMARINE_SLAB, ModBlocks.PRISMARINE_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.PRISMARINE_BRICKS, Blocks.PRISMARINE_BRICK_SLAB, ModBlocks.PRISMARINE_BRICK_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.DARK_PRISMARINE, Blocks.DARK_PRISMARINE_SLAB, ModBlocks.DARK_PRISMARINE_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.NETHER_BRICKS, Blocks.NETHER_BRICK_SLAB, ModBlocks.NETHER_BRICK_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.RED_NETHER_BRICKS, Blocks.RED_NETHER_BRICK_SLAB, ModBlocks.RED_NETHER_BRICK_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.BLACKSTONE, Blocks.BLACKSTONE_SLAB, ModBlocks.BLACKSTONE_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.POLISHED_BLACKSTONE, Blocks.POLISHED_BLACKSTONE_SLAB, ModBlocks.POLISHED_BLACKSTONE_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.POLISHED_BLACKSTONE_BRICKS, Blocks.POLISHED_BLACKSTONE_BRICK_SLAB, ModBlocks.POLISHED_BLACKSTONE_BRICK_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.END_STONE_BRICKS, Blocks.END_STONE_BRICK_SLAB, ModBlocks.END_STONE_BRICK_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.PURPUR_BLOCK, Blocks.PURPUR_SLAB, ModBlocks.PURPUR_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.QUARTZ_BLOCK, Blocks.QUARTZ_SLAB, ModBlocks.QUARTZ_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.SMOOTH_QUARTZ, Blocks.SMOOTH_QUARTZ_SLAB, ModBlocks.SMOOTH_QUARTZ_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.CUT_COPPER, Blocks.CUT_COPPER_SLAB, ModBlocks.CUT_COPPER_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.EXPOSED_CUT_COPPER, Blocks.EXPOSED_CUT_COPPER_SLAB, ModBlocks.EXPOSED_CUT_COPPER_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.WEATHERED_CUT_COPPER, Blocks.WEATHERED_CUT_COPPER_SLAB, ModBlocks.WEATHERED_CUT_COPPER_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.OXIDIZED_CUT_COPPER, Blocks.OXIDIZED_CUT_COPPER_SLAB, ModBlocks.OXIDIZED_CUT_COPPER_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.WAXED_CUT_COPPER, Blocks.WAXED_CUT_COPPER_SLAB, ModBlocks.WAXED_CUT_COPPER_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.WAXED_EXPOSED_CUT_COPPER, Blocks.WAXED_EXPOSED_CUT_COPPER_SLAB, ModBlocks.WAXED_EXPOSED_CUT_COPPER_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.WAXED_WEATHERED_CUT_COPPER, Blocks.WAXED_WEATHERED_CUT_COPPER_SLAB, ModBlocks.WAXED_WEATHERED_CUT_COPPER_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.WAXED_OXIDIZED_CUT_COPPER, Blocks.WAXED_OXIDIZED_CUT_COPPER_SLAB, ModBlocks.WAXED_OXIDIZED_CUT_COPPER_VERTICAL_SLAB));

            add(new VerticalSlab(Blocks.OAK_PLANKS, Blocks.OAK_SLAB, ModBlocks.OAK_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.SPRUCE_PLANKS, Blocks.SPRUCE_SLAB, ModBlocks.SPRUCE_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.BIRCH_PLANKS, Blocks.BIRCH_SLAB, ModBlocks.BIRCH_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.JUNGLE_PLANKS, Blocks.JUNGLE_SLAB, ModBlocks.JUNGLE_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.ACACIA_PLANKS, Blocks.ACACIA_SLAB, ModBlocks.ACACIA_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.DARK_OAK_PLANKS, Blocks.DARK_OAK_SLAB, ModBlocks.DARK_OAK_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.MANGROVE_PLANKS, Blocks.MANGROVE_SLAB, ModBlocks.MANGROVE_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.CHERRY_PLANKS, Blocks.CHERRY_SLAB, ModBlocks.CHERRY_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.BAMBOO_PLANKS, Blocks.BAMBOO_SLAB, ModBlocks.BAMBOO_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.CRIMSON_PLANKS, Blocks.CRIMSON_SLAB, ModBlocks.CRIMSON_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.WARPED_PLANKS, Blocks.WARPED_SLAB, ModBlocks.WARPED_VERTICAL_SLAB));

            add(new VerticalSlab(Blocks.BLACK_WOOL, ModBlocks.BLACK_WOOL_SLAB, ModBlocks.BLACK_WOOL_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.BLUE_WOOL, ModBlocks.BLUE_WOOL_SLAB, ModBlocks.BLUE_WOOL_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.BROWN_WOOL, ModBlocks.BROWN_WOOL_SLAB, ModBlocks.BROWN_WOOL_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.CYAN_WOOL, ModBlocks.CYAN_WOOL_SLAB, ModBlocks.CYAN_WOOL_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.GRAY_WOOL, ModBlocks.GRAY_WOOL_SLAB, ModBlocks.GRAY_WOOL_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.GREEN_WOOL, ModBlocks.GREEN_WOOL_SLAB, ModBlocks.GREEN_WOOL_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.LIGHT_BLUE_WOOL, ModBlocks.LIGHT_BLUE_WOOL_SLAB, ModBlocks.LIGHT_BLUE_WOOL_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.LIGHT_GRAY_WOOL, ModBlocks.LIGHT_GRAY_WOOL_SLAB, ModBlocks.LIGHT_GRAY_WOOL_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.LIME_WOOL, ModBlocks.LIME_WOOL_SLAB, ModBlocks.LIME_WOOL_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.MAGENTA_WOOL, ModBlocks.MAGENTA_WOOL_SLAB, ModBlocks.MAGENTA_WOOL_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.ORANGE_WOOL, ModBlocks.ORANGE_WOOL_SLAB, ModBlocks.ORANGE_WOOL_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.PINK_WOOL, ModBlocks.PINK_WOOL_SLAB, ModBlocks.PINK_WOOL_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.PURPLE_WOOL, ModBlocks.PURPLE_WOOL_SLAB, ModBlocks.PURPLE_WOOL_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.RED_WOOL, ModBlocks.RED_WOOL_SLAB, ModBlocks.RED_WOOL_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.WHITE_WOOL, ModBlocks.WHITE_WOOL_SLAB, ModBlocks.WHITE_WOOL_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.YELLOW_WOOL, ModBlocks.YELLOW_WOOL_SLAB, ModBlocks.YELLOW_WOOL_VERTICAL_SLAB));
        }
    };
}
