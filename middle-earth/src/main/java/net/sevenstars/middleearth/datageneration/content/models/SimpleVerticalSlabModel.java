package net.sevenstars.middleearth.datageneration.content.models;

import net.sevenstars.middleearth.block.registration.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import java.util.ArrayList;
import java.util.List;

public class SimpleVerticalSlabModel {
    public record VerticalSlab(Block block, Block slab, Block verticalSlab) {}
    public static List<VerticalSlab> verticalSlabs = new ArrayList<>() {
        {
        }
    };

    public static List<VerticalSlab> columnVerticalSlabs = new ArrayList<>() {
        {
        }
    };

    public static List<VerticalSlab> woodVerticalSlabs = new ArrayList<>() {
        {
        }
    };

    public static List<VerticalSlab> strippedVerticalSlabs = new ArrayList<>() {
        {
        }
    };

    public static List<VerticalSlab> plansVerticalSlabs = new ArrayList<>() {
        {
        }
    };

    public static List<VerticalSlab> vanillaWoodVerticalSlabs = new ArrayList<>() {
        {
        }
    };

    public static List<VerticalSlab> vanillaStrippedVerticalSlabs = new ArrayList<>() {
        {
        }
    };

    public static List<VerticalSlab> vanillaVerticalSlabs = new ArrayList<>() {
        {
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

            add(new VerticalSlab(Blocks.PACKED_MUD, ModBlocks.PACKED_MUD_SLAB, ModBlocks.PACKED_MUD_VERTICAL_SLAB));

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
