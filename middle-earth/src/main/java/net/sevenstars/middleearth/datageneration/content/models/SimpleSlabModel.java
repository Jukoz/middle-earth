package net.sevenstars.middleearth.datageneration.content.models;

import net.sevenstars.middleearth.block.registration.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import java.util.ArrayList;
import java.util.List;

public class SimpleSlabModel {
    public record Slab(Block origin, Block slab) {}
    public static List<Slab> slabs = new ArrayList<>() {
        {
            add(new Slab(ModBlocks.MIRE, ModBlocks.MIRE_SLAB));
            add(new Slab(ModBlocks.DRY_DIRT, ModBlocks.DRY_DIRT_SLAB));
            add(new Slab(ModBlocks.LOAM, ModBlocks.LOAM_SLAB));
            add(new Slab(ModBlocks.SILT, ModBlocks.SILT_SLAB));
            add(new Slab(ModBlocks.FOUL_DIRT, ModBlocks.FOUL_DIRT_SLAB));
            add(new Slab(ModBlocks.DIRTY_ROOTS, ModBlocks.DIRTY_ROOTS_SLAB));
            add(new Slab(ModBlocks.ASHEN_DIRT, ModBlocks.ASHEN_DIRT_SLAB));
            add(new Slab(ModBlocks.COBBLY_ASHEN_DIRT, ModBlocks.COBBLY_ASHEN_DIRT_SLAB));
            add(new Slab(ModBlocks.COBBLY_DIRT, ModBlocks.COBBLY_DIRT_SLAB));
            add(new Slab(ModBlocks.SNOWY_DIRT, ModBlocks.SNOWY_DIRT_SLAB));
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
        }
    };

    public static List<Slab> vanillaStrippedSlab = new ArrayList<>() {
        {
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
