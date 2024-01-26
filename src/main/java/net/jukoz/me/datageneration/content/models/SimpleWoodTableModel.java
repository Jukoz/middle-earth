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
            add(new SimpleWoodTableModel.VanillaTable(ModDecorativeBlocks.OAK_TABLE, Blocks.OAK_LOG));
            add(new SimpleWoodTableModel.VanillaTable(ModDecorativeBlocks.SPRUCE_TABLE, Blocks.SPRUCE_LOG));
            add(new SimpleWoodTableModel.VanillaTable(ModDecorativeBlocks.BIRCH_TABLE, Blocks.BIRCH_LOG));
            add(new SimpleWoodTableModel.VanillaTable(ModDecorativeBlocks.JUNGLE_TABLE, Blocks.JUNGLE_LOG));
            add(new SimpleWoodTableModel.VanillaTable(ModDecorativeBlocks.ACACIA_TABLE, Blocks.ACACIA_LOG));
            add(new SimpleWoodTableModel.VanillaTable(ModDecorativeBlocks.DARK_OAK_TABLE, Blocks.DARK_OAK_LOG));
            add(new SimpleWoodTableModel.VanillaTable(ModDecorativeBlocks.MANGROVE_TABLE, Blocks.MANGROVE_LOG));
            add(new SimpleWoodTableModel.VanillaTable(ModDecorativeBlocks.BAMBOO_TABLE, Blocks.BAMBOO_BLOCK));
            add(new SimpleWoodTableModel.VanillaTable(ModDecorativeBlocks.CHERRY_TABLE, Blocks.CHERRY_LOG));
            add(new SimpleWoodTableModel.VanillaTable(ModDecorativeBlocks.CRIMSON_TABLE, Blocks.CRIMSON_STEM));
            add(new SimpleWoodTableModel.VanillaTable(ModDecorativeBlocks.WARPED_TABLE, Blocks.WARPED_STEM));
        }
    };
}
