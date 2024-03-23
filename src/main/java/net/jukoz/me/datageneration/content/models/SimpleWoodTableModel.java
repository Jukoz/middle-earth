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
            add(new SimpleWoodTableModel.VanillaTable(ModDecorativeBlocks.OAK_TABLE, Blocks.STRIPPED_OAK_LOG));
            add(new SimpleWoodTableModel.VanillaTable(ModDecorativeBlocks.SPRUCE_TABLE, Blocks.STRIPPED_SPRUCE_LOG));
            add(new SimpleWoodTableModel.VanillaTable(ModDecorativeBlocks.BIRCH_TABLE, Blocks.STRIPPED_BIRCH_LOG));
            add(new SimpleWoodTableModel.VanillaTable(ModDecorativeBlocks.JUNGLE_TABLE, Blocks.STRIPPED_JUNGLE_LOG));
            add(new SimpleWoodTableModel.VanillaTable(ModDecorativeBlocks.ACACIA_TABLE, Blocks.STRIPPED_ACACIA_LOG));
            add(new SimpleWoodTableModel.VanillaTable(ModDecorativeBlocks.DARK_OAK_TABLE, Blocks.STRIPPED_DARK_OAK_LOG));
            add(new SimpleWoodTableModel.VanillaTable(ModDecorativeBlocks.MANGROVE_TABLE, Blocks.STRIPPED_MANGROVE_LOG));
            add(new SimpleWoodTableModel.VanillaTable(ModDecorativeBlocks.BAMBOO_TABLE, Blocks.STRIPPED_BAMBOO_BLOCK));
            add(new SimpleWoodTableModel.VanillaTable(ModDecorativeBlocks.CHERRY_TABLE, Blocks.STRIPPED_CHERRY_LOG));
            add(new SimpleWoodTableModel.VanillaTable(ModDecorativeBlocks.CRIMSON_TABLE, Blocks.STRIPPED_CRIMSON_STEM));
            add(new SimpleWoodTableModel.VanillaTable(ModDecorativeBlocks.WARPED_TABLE, Blocks.STRIPPED_WARPED_STEM));
        }
    };
}
