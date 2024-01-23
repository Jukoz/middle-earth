package net.jukoz.me.datageneration.content.models;

import net.jukoz.me.block.ModBlocks;
import net.jukoz.me.block.ModDecorativeBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import java.util.ArrayList;
import java.util.List;

public class SimpleWoodTableModel {
    public record VanillaTable(Block base, Block origin) {}


    public static List<Block> tables = new ArrayList<>() {
        {
        }
    };

    public static List<VanillaTable> vanillaTables = new ArrayList<>() {
        {
            add(new SimpleWoodTableModel.VanillaTable(ModDecorativeBlocks.OAK_TABLE, Blocks.OAK_SLAB));
            add(new SimpleWoodTableModel.VanillaTable(ModDecorativeBlocks.SPRUCE_TABLE, Blocks.SPRUCE_SLAB));
            add(new SimpleWoodTableModel.VanillaTable(ModDecorativeBlocks.BIRCH_TABLE, Blocks.BIRCH_SLAB));
            add(new SimpleWoodTableModel.VanillaTable(ModDecorativeBlocks.JUNGLE_TABLE, Blocks.JUNGLE_SLAB));
            add(new SimpleWoodTableModel.VanillaTable(ModDecorativeBlocks.ACACIA_TABLE, Blocks.ACACIA_SLAB));
            add(new SimpleWoodTableModel.VanillaTable(ModDecorativeBlocks.DARK_OAK_TABLE, Blocks.DARK_OAK_SLAB));
            add(new SimpleWoodTableModel.VanillaTable(ModDecorativeBlocks.MANGROVE_TABLE, Blocks.MANGROVE_SLAB));
            add(new SimpleWoodTableModel.VanillaTable(ModDecorativeBlocks.BAMBOO_TABLE, Blocks.BAMBOO_SLAB));
            add(new SimpleWoodTableModel.VanillaTable(ModDecorativeBlocks.CHERRY_TABLE, Blocks.CHERRY_SLAB));
            add(new SimpleWoodTableModel.VanillaTable(ModDecorativeBlocks.CRIMSON_TABLE, Blocks.CRIMSON_SLAB));
            add(new SimpleWoodTableModel.VanillaTable(ModDecorativeBlocks.WARPED_TABLE, Blocks.WARPED_SLAB));
        }
    };
}
