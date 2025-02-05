package net.sevenstars.middleearth.datageneration.content.models;

import net.sevenstars.middleearth.block.ModDecorativeBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import java.util.ArrayList;
import java.util.List;

public class SimpleWoodTableModel {
    public record VanillaTable(Block base, Block planks, Block origin) {}


    public static List<Block> tables = new ArrayList<>() {
        {
            add(ModDecorativeBlocks.TREATED_WOOD_TABLE);
        }
    };

    public static List<VanillaTable> vanillaTables = new ArrayList<>() {
        {
            add(new VanillaTable(ModDecorativeBlocks.OAK_TABLE, Blocks.OAK_SLAB, Blocks.STRIPPED_OAK_LOG));
            add(new VanillaTable(ModDecorativeBlocks.SPRUCE_TABLE, Blocks.SPRUCE_SLAB, Blocks.STRIPPED_SPRUCE_LOG));
            add(new VanillaTable(ModDecorativeBlocks.BIRCH_TABLE, Blocks.BIRCH_SLAB, Blocks.STRIPPED_BIRCH_LOG));
            add(new VanillaTable(ModDecorativeBlocks.JUNGLE_TABLE, Blocks.JUNGLE_SLAB, Blocks.STRIPPED_JUNGLE_LOG));
            add(new VanillaTable(ModDecorativeBlocks.ACACIA_TABLE, Blocks.ACACIA_SLAB, Blocks.STRIPPED_ACACIA_LOG));
            add(new VanillaTable(ModDecorativeBlocks.DARK_OAK_TABLE, Blocks.DARK_OAK_SLAB, Blocks.STRIPPED_DARK_OAK_LOG));
            add(new VanillaTable(ModDecorativeBlocks.MANGROVE_TABLE, Blocks.MANGROVE_SLAB, Blocks.STRIPPED_MANGROVE_LOG));
            add(new VanillaTable(ModDecorativeBlocks.BAMBOO_TABLE, Blocks.BAMBOO_BLOCK, Blocks.STRIPPED_BAMBOO_BLOCK));
            add(new VanillaTable(ModDecorativeBlocks.CHERRY_TABLE, Blocks.CHERRY_SLAB, Blocks.STRIPPED_CHERRY_LOG));
            add(new VanillaTable(ModDecorativeBlocks.CRIMSON_TABLE, Blocks.CRIMSON_SLAB, Blocks.STRIPPED_CRIMSON_STEM));
            add(new VanillaTable(ModDecorativeBlocks.WARPED_TABLE, Blocks.WARPED_SLAB, Blocks.STRIPPED_WARPED_STEM));
        }
    };
}
