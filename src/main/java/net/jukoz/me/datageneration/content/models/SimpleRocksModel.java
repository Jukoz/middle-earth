package net.jukoz.me.datageneration.content.models;

import net.jukoz.me.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import java.util.ArrayList;
import java.util.List;

public class SimpleRocksModel {
    public record Rocks(Block block, Block rocks) {}
    public static List<Rocks> rocks = new ArrayList<>() {
        {
        }
    };

    public static List<Rocks> vanillaRocks = new ArrayList<>() {
        {
            add(new Rocks(Blocks.STONE, ModBlocks.STONE_ROCKS));
            add(new Rocks(Blocks.GRANITE, ModBlocks.GRANITE_ROCKS));
            add(new Rocks(Blocks.DIORITE, ModBlocks.DIORITE_ROCKS));
            add(new Rocks(Blocks.ANDESITE, ModBlocks.ANDESITE_ROCKS));
            add(new Rocks(Blocks.CALCITE, ModBlocks.CALCITE_ROCKS));
            add(new Rocks(Blocks.DEEPSLATE, ModBlocks.DEEPSLATE_ROCKS));
            add(new Rocks(Blocks.TUFF, ModBlocks.TUFF_ROCKS));
            add(new Rocks(Blocks.BASALT, ModBlocks.BASALT_ROCKS));
            add(new Rocks(Blocks.BLACKSTONE, ModBlocks.BLACKSTONE_ROCKS));
        }
    };
}
