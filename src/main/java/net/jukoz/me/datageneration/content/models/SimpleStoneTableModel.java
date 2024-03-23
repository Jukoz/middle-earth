package net.jukoz.me.datageneration.content.models;

import net.jukoz.me.block.ModDecorativeBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import java.util.ArrayList;
import java.util.List;

public class SimpleStoneTableModel {
    public record VanillaTable(Block base, Block origin) {}


    public static List<Block> tables = new ArrayList<>() {
        {
        }
    };

    public static List<VanillaTable> vanillaTables = new ArrayList<>() {
        {
            add(new SimpleStoneTableModel.VanillaTable(ModDecorativeBlocks.STONE_TABLE, Blocks.STONE));
            add(new SimpleStoneTableModel.VanillaTable(ModDecorativeBlocks.CALCITE_TABLE, Blocks.CALCITE));
            add(new SimpleStoneTableModel.VanillaTable(ModDecorativeBlocks.ANDESITE_TABLE, Blocks.ANDESITE));
            add(new SimpleStoneTableModel.VanillaTable(ModDecorativeBlocks.GRANITE_TABLE, Blocks.GRANITE));
            add(new SimpleStoneTableModel.VanillaTable(ModDecorativeBlocks.DIORITE_TABLE, Blocks.DIORITE));
            add(new SimpleStoneTableModel.VanillaTable(ModDecorativeBlocks.DEEPSLATE_TABLE, Blocks.DEEPSLATE));
            add(new SimpleStoneTableModel.VanillaTable(ModDecorativeBlocks.BLACKSTONE_TABLE, Blocks.BLACKSTONE));
            add(new SimpleStoneTableModel.VanillaTable(ModDecorativeBlocks.BASALT_TABLE, Blocks.BASALT));
            add(new SimpleStoneTableModel.VanillaTable(ModDecorativeBlocks.TUFF_TABLE, Blocks.TUFF));
        }
    };
}
