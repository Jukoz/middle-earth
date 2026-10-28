package net.sevenstars.middleearth.datageneration.content.models;

import net.sevenstars.middleearth.block.registration.BlockRegistryME;
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
            add(new VerticalSlab(Blocks.BRICKS, Blocks.BRICK_SLAB, BlockRegistryME.BRICK_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.MUD_BRICKS, Blocks.MUD_BRICK_SLAB, BlockRegistryME.MUD_BRICK_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.SANDSTONE, Blocks.SANDSTONE_SLAB, BlockRegistryME.SANDSTONE_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.SMOOTH_SANDSTONE, Blocks.SMOOTH_SANDSTONE_SLAB, BlockRegistryME.SMOOTH_SANDSTONE_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.CUT_SANDSTONE, Blocks.CUT_SANDSTONE_SLAB, BlockRegistryME.CUT_SANDSTONE_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.RED_SANDSTONE, Blocks.RED_SANDSTONE_SLAB, BlockRegistryME.RED_SANDSTONE_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.SMOOTH_RED_SANDSTONE, Blocks.SMOOTH_RED_SANDSTONE_SLAB, BlockRegistryME.SMOOTH_RED_SANDSTONE_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.CUT_RED_SANDSTONE, Blocks.CUT_RED_SANDSTONE_SLAB, BlockRegistryME.CUT_RED_SANDSTONE_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.PRISMARINE, Blocks.PRISMARINE_SLAB, BlockRegistryME.PRISMARINE_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.PRISMARINE_BRICKS, Blocks.PRISMARINE_BRICK_SLAB, BlockRegistryME.PRISMARINE_BRICK_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.DARK_PRISMARINE, Blocks.DARK_PRISMARINE_SLAB, BlockRegistryME.DARK_PRISMARINE_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.NETHER_BRICKS, Blocks.NETHER_BRICK_SLAB, BlockRegistryME.NETHER_BRICK_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.RED_NETHER_BRICKS, Blocks.RED_NETHER_BRICK_SLAB, BlockRegistryME.RED_NETHER_BRICK_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.END_STONE_BRICKS, Blocks.END_STONE_BRICK_SLAB, BlockRegistryME.END_STONE_BRICK_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.PURPUR_BLOCK, Blocks.PURPUR_SLAB, BlockRegistryME.PURPUR_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.QUARTZ_BLOCK, Blocks.QUARTZ_SLAB, BlockRegistryME.QUARTZ_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.SMOOTH_QUARTZ, Blocks.SMOOTH_QUARTZ_SLAB, BlockRegistryME.SMOOTH_QUARTZ_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.CUT_COPPER, Blocks.CUT_COPPER_SLAB, BlockRegistryME.CUT_COPPER_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.EXPOSED_CUT_COPPER, Blocks.EXPOSED_CUT_COPPER_SLAB, BlockRegistryME.EXPOSED_CUT_COPPER_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.WEATHERED_CUT_COPPER, Blocks.WEATHERED_CUT_COPPER_SLAB, BlockRegistryME.WEATHERED_CUT_COPPER_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.OXIDIZED_CUT_COPPER, Blocks.OXIDIZED_CUT_COPPER_SLAB, BlockRegistryME.OXIDIZED_CUT_COPPER_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.WAXED_CUT_COPPER, Blocks.WAXED_CUT_COPPER_SLAB, BlockRegistryME.WAXED_CUT_COPPER_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.WAXED_EXPOSED_CUT_COPPER, Blocks.WAXED_EXPOSED_CUT_COPPER_SLAB, BlockRegistryME.WAXED_EXPOSED_CUT_COPPER_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.WAXED_WEATHERED_CUT_COPPER, Blocks.WAXED_WEATHERED_CUT_COPPER_SLAB, BlockRegistryME.WAXED_WEATHERED_CUT_COPPER_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.WAXED_OXIDIZED_CUT_COPPER, Blocks.WAXED_OXIDIZED_CUT_COPPER_SLAB, BlockRegistryME.WAXED_OXIDIZED_CUT_COPPER_VERTICAL_SLAB));

            add(new VerticalSlab(Blocks.PACKED_MUD, BlockRegistryME.PACKED_MUD_SLAB, BlockRegistryME.PACKED_MUD_VERTICAL_SLAB));

            add(new VerticalSlab(Blocks.BLACK_WOOL, BlockRegistryME.BLACK_WOOL_SLAB, BlockRegistryME.BLACK_WOOL_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.BLUE_WOOL, BlockRegistryME.BLUE_WOOL_SLAB, BlockRegistryME.BLUE_WOOL_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.BROWN_WOOL, BlockRegistryME.BROWN_WOOL_SLAB, BlockRegistryME.BROWN_WOOL_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.CYAN_WOOL, BlockRegistryME.CYAN_WOOL_SLAB, BlockRegistryME.CYAN_WOOL_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.GRAY_WOOL, BlockRegistryME.GRAY_WOOL_SLAB, BlockRegistryME.GRAY_WOOL_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.GREEN_WOOL, BlockRegistryME.GREEN_WOOL_SLAB, BlockRegistryME.GREEN_WOOL_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.LIGHT_BLUE_WOOL, BlockRegistryME.LIGHT_BLUE_WOOL_SLAB, BlockRegistryME.LIGHT_BLUE_WOOL_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.LIGHT_GRAY_WOOL, BlockRegistryME.LIGHT_GRAY_WOOL_SLAB, BlockRegistryME.LIGHT_GRAY_WOOL_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.LIME_WOOL, BlockRegistryME.LIME_WOOL_SLAB, BlockRegistryME.LIME_WOOL_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.MAGENTA_WOOL, BlockRegistryME.MAGENTA_WOOL_SLAB, BlockRegistryME.MAGENTA_WOOL_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.ORANGE_WOOL, BlockRegistryME.ORANGE_WOOL_SLAB, BlockRegistryME.ORANGE_WOOL_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.PINK_WOOL, BlockRegistryME.PINK_WOOL_SLAB, BlockRegistryME.PINK_WOOL_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.PURPLE_WOOL, BlockRegistryME.PURPLE_WOOL_SLAB, BlockRegistryME.PURPLE_WOOL_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.RED_WOOL, BlockRegistryME.RED_WOOL_SLAB, BlockRegistryME.RED_WOOL_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.WHITE_WOOL, BlockRegistryME.WHITE_WOOL_SLAB, BlockRegistryME.WHITE_WOOL_VERTICAL_SLAB));
            add(new VerticalSlab(Blocks.YELLOW_WOOL, BlockRegistryME.YELLOW_WOOL_SLAB, BlockRegistryME.YELLOW_WOOL_VERTICAL_SLAB));
        }
    };
}
