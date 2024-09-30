package net.jukoz.me.datageneration.content.models;

import net.jukoz.me.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import java.util.ArrayList;
import java.util.List;

public class SimpleSlabModel {
    public record Slab(Block origin, Block slab) {}
    public static List<Slab> slabs = new ArrayList<>() {
        {
            add(new Slab(ModBlocks.REED_BLOCK, ModBlocks.REED_SLAB));
            add(new Slab(ModBlocks.MIRE, ModBlocks.MIRE_SLAB));
            add(new Slab(ModBlocks.DRY_DIRT, ModBlocks.DRY_DIRT_SLAB));
            add(new Slab(ModBlocks.DIRTY_ROOTS, ModBlocks.DIRTY_ROOTS_SLAB));
            add(new Slab(ModBlocks.ASHEN_DIRT, ModBlocks.ASHEN_DIRT_SLAB));
            add(new Slab(ModBlocks.COBBLY_ASHEN_DIRT, ModBlocks.COBBLY_ASHEN_DIRT_SLAB));
            add(new Slab(ModBlocks.COBBLY_DIRT, ModBlocks.COBBLY_DIRT_SLAB));
            add(new Slab(ModBlocks.STRAW_BLOCK, ModBlocks.STRAW_SLAB));
            add(new Slab(ModBlocks.CUT_LEAD, ModBlocks.CUT_LEAD_SLAB));
            add(new Slab(ModBlocks.CUT_SILVER, ModBlocks.CUT_SILVER_SLAB));
        }
    };

    public static List<Slab> woodSlabs = new ArrayList<>() {
        {

        }
    };

    public static List<Slab> strippedSlabs = new ArrayList<>() {
        {

        }
    };

    public static List<Slab> vanillaWoodSlabs = new ArrayList<>() {
        {
            add(new SimpleSlabModel.Slab(Blocks.OAK_WOOD, ModBlocks.OAK_WOOD_SLAB));
            add(new SimpleSlabModel.Slab(Blocks.SPRUCE_WOOD, ModBlocks.SPRUCE_WOOD_SLAB));
            add(new SimpleSlabModel.Slab(Blocks.BIRCH_WOOD, ModBlocks.BIRCH_WOOD_SLAB));
            add(new SimpleSlabModel.Slab(Blocks.JUNGLE_WOOD, ModBlocks.JUNGLE_WOOD_SLAB));
            add(new SimpleSlabModel.Slab(Blocks.ACACIA_WOOD, ModBlocks.ACACIA_WOOD_SLAB));
            add(new SimpleSlabModel.Slab(Blocks.DARK_OAK_WOOD, ModBlocks.DARK_OAK_WOOD_SLAB));
            add(new SimpleSlabModel.Slab(Blocks.MANGROVE_WOOD, ModBlocks.MANGROVE_WOOD_SLAB));
            add(new SimpleSlabModel.Slab(Blocks.CHERRY_WOOD, ModBlocks.CHERRY_WOOD_SLAB));
        }
    };

    public static List<Slab> vanillaStrippedSlab = new ArrayList<>() {
        {
            add(new SimpleSlabModel.Slab(Blocks.STRIPPED_OAK_WOOD, ModBlocks.STRIPPED_OAK_WOOD_SLAB));
            add(new SimpleSlabModel.Slab(Blocks.STRIPPED_SPRUCE_WOOD, ModBlocks.STRIPPED_SPRUCE_WOOD_SLAB));
            add(new SimpleSlabModel.Slab(Blocks.STRIPPED_BIRCH_WOOD, ModBlocks.STRIPPED_BIRCH_WOOD_SLAB));
            add(new SimpleSlabModel.Slab(Blocks.STRIPPED_JUNGLE_WOOD, ModBlocks.STRIPPED_JUNGLE_WOOD_SLAB));
            add(new SimpleSlabModel.Slab(Blocks.STRIPPED_ACACIA_WOOD, ModBlocks.STRIPPED_ACACIA_WOOD_SLAB));
            add(new SimpleSlabModel.Slab(Blocks.STRIPPED_DARK_OAK_WOOD, ModBlocks.STRIPPED_DARK_OAK_WOOD_SLAB));
            add(new SimpleSlabModel.Slab(Blocks.STRIPPED_MANGROVE_WOOD, ModBlocks.STRIPPED_MANGROVE_WOOD_SLAB));
            add(new SimpleSlabModel.Slab(Blocks.STRIPPED_CHERRY_WOOD, ModBlocks.STRIPPED_CHERRY_WOOD_SLAB));
        }
    };

    public static List<Slab> vanillaSlabs = new ArrayList<>() {
        {
            add(new Slab(Blocks.DIRT, ModBlocks.DIRT_SLAB));
            add(new Slab(Blocks.COARSE_DIRT, ModBlocks.COARSE_DIRT_SLAB));
            add(new Slab(Blocks.ROOTED_DIRT, ModBlocks.ROOTED_DIRT_SLAB));
            add(new Slab(Blocks.MUD, ModBlocks.MUD_SLAB));
            add(new Slab(Blocks.MOSS_BLOCK, ModBlocks.MOSS_SLAB));

            add(new Slab(Blocks.PACKED_MUD, ModBlocks.PACKED_MUD_SLAB));
            add(new Slab(Blocks.CALCITE, ModBlocks.CALCITE_SLAB));
            add(new Slab(Blocks.SMOOTH_BASALT, ModBlocks.SMOOTH_BASALT_SLAB));

            add(new Slab(Blocks.BLACK_WOOL, ModBlocks.BLACK_WOOL_SLAB));
            add(new Slab(Blocks.BLUE_WOOL, ModBlocks.BLUE_WOOL_SLAB));
            add(new Slab(Blocks.BROWN_WOOL, ModBlocks.BROWN_WOOL_SLAB));
            add(new Slab(Blocks.CYAN_WOOL, ModBlocks.CYAN_WOOL_SLAB));
            add(new Slab(Blocks.GRAY_WOOL, ModBlocks.GRAY_WOOL_SLAB));
            add(new Slab(Blocks.GREEN_WOOL, ModBlocks.GREEN_WOOL_SLAB));
            add(new Slab(Blocks.LIGHT_BLUE_WOOL, ModBlocks.LIGHT_BLUE_WOOL_SLAB));
            add(new Slab(Blocks.LIGHT_GRAY_WOOL, ModBlocks.LIGHT_GRAY_WOOL_SLAB));
            add(new Slab(Blocks.LIME_WOOL, ModBlocks.LIME_WOOL_SLAB));
            add(new Slab(Blocks.MAGENTA_WOOL, ModBlocks.MAGENTA_WOOL_SLAB));
            add(new Slab(Blocks.ORANGE_WOOL, ModBlocks.ORANGE_WOOL_SLAB));
            add(new Slab(Blocks.PINK_WOOL, ModBlocks.PINK_WOOL_SLAB));
            add(new Slab(Blocks.PURPLE_WOOL, ModBlocks.PURPLE_WOOL_SLAB));
            add(new Slab(Blocks.RED_WOOL, ModBlocks.RED_WOOL_SLAB));
            add(new Slab(Blocks.WHITE_WOOL, ModBlocks.WHITE_WOOL_SLAB));
            add(new Slab(Blocks.YELLOW_WOOL, ModBlocks.YELLOW_WOOL_SLAB));
        }
    };
}
