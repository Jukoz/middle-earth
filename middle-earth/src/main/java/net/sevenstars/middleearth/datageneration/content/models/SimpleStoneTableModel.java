package net.sevenstars.middleearth.datageneration.content.models;

import net.sevenstars.middleearth.block.ModDecorativeBlocks;
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
            add(new VanillaTable(ModDecorativeBlocks.STONE_TABLE, Blocks.STONE));
            add(new VanillaTable(ModDecorativeBlocks.CALCITE_TABLE, Blocks.CALCITE));
            add(new VanillaTable(ModDecorativeBlocks.ANDESITE_TABLE, Blocks.ANDESITE));
            add(new VanillaTable(ModDecorativeBlocks.GRANITE_TABLE, Blocks.GRANITE));
            add(new VanillaTable(ModDecorativeBlocks.DIORITE_TABLE, Blocks.DIORITE));
            add(new VanillaTable(ModDecorativeBlocks.DEEPSLATE_TABLE, Blocks.DEEPSLATE));
            add(new VanillaTable(ModDecorativeBlocks.BLACKSTONE_TABLE, Blocks.BLACKSTONE));
            add(new VanillaTable(ModDecorativeBlocks.BASALT_TABLE, Blocks.BASALT));
            add(new VanillaTable(ModDecorativeBlocks.TUFF_TABLE, Blocks.TUFF));
        }
    };
}
