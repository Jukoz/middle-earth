package net.jukoz.me.datageneration.content.models;

import net.jukoz.me.block.ModBlocks;
import net.jukoz.me.block.ModDecorativeBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import java.util.ArrayList;
import java.util.List;

public class SimpleWoodTableModel {
    public record VanillaTable(Block base, Block planks, Block legs, Block origin) {}


    public static List<Block> tables = new ArrayList<>() {
        {
        }
    };

    public static List<VanillaTable> vanillaTables = new ArrayList<>() {
        {
            add(new SimpleWoodTableModel.VanillaTable(ModDecorativeBlocks.OAK_TABLE, Blocks.OAK_SLAB, ModBlocks.STRIPPED_OAK_WOOD_FENCE, Blocks.STRIPPED_OAK_LOG));
            add(new SimpleWoodTableModel.VanillaTable(ModDecorativeBlocks.SPRUCE_TABLE, Blocks.SPRUCE_SLAB, ModBlocks.STRIPPED_SPRUCE_WOOD_FENCE, Blocks.STRIPPED_SPRUCE_LOG));
            add(new SimpleWoodTableModel.VanillaTable(ModDecorativeBlocks.BIRCH_TABLE, Blocks.BIRCH_SLAB, ModBlocks.STRIPPED_BIRCH_WOOD_FENCE, Blocks.STRIPPED_BIRCH_LOG));
            add(new SimpleWoodTableModel.VanillaTable(ModDecorativeBlocks.JUNGLE_TABLE, Blocks.JUNGLE_SLAB, ModBlocks.STRIPPED_JUNGLE_WOOD_FENCE, Blocks.STRIPPED_JUNGLE_LOG));
            add(new SimpleWoodTableModel.VanillaTable(ModDecorativeBlocks.ACACIA_TABLE, Blocks.ACACIA_SLAB, ModBlocks.STRIPPED_ACACIA_WOOD_FENCE, Blocks.STRIPPED_ACACIA_LOG));
            add(new SimpleWoodTableModel.VanillaTable(ModDecorativeBlocks.DARK_OAK_TABLE, Blocks.DARK_OAK_SLAB, ModBlocks.STRIPPED_DARK_OAK_WOOD_FENCE, Blocks.STRIPPED_DARK_OAK_LOG));
            add(new SimpleWoodTableModel.VanillaTable(ModDecorativeBlocks.MANGROVE_TABLE, Blocks.MANGROVE_SLAB, ModBlocks.STRIPPED_MANGROVE_WOOD_FENCE, Blocks.STRIPPED_MANGROVE_LOG));
            add(new SimpleWoodTableModel.VanillaTable(ModDecorativeBlocks.BAMBOO_TABLE, Blocks.BAMBOO_BLOCK, Blocks.BAMBOO_FENCE, Blocks.STRIPPED_BAMBOO_BLOCK));
            add(new SimpleWoodTableModel.VanillaTable(ModDecorativeBlocks.CHERRY_TABLE, Blocks.CHERRY_SLAB, ModBlocks.STRIPPED_CHERRY_WOOD_FENCE, Blocks.STRIPPED_CHERRY_LOG));
            add(new SimpleWoodTableModel.VanillaTable(ModDecorativeBlocks.CRIMSON_TABLE, Blocks.CRIMSON_STEM, Blocks.CRIMSON_FENCE, Blocks.STRIPPED_CRIMSON_STEM));
            add(new SimpleWoodTableModel.VanillaTable(ModDecorativeBlocks.WARPED_TABLE, Blocks.WARPED_STEM, Blocks.WARPED_FENCE, Blocks.STRIPPED_WARPED_STEM));
        }
    };
}
