package net.sevenstars.middleearth.datageneration.content.models;

import net.sevenstars.middleearth.block.ModDecorativeBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import java.util.ArrayList;
import java.util.List;

public class SimpleStoneStoolModel {

    public record VanillaStool(Block base, Block origin) {}

    public static List<Block> stools = new ArrayList<>() {
        {
        }
    };

    public static List<VanillaStool> vanillaStools = new ArrayList<>() {
        {
            add(new VanillaStool(ModDecorativeBlocks.STONE_STOOL, Blocks.STONE));
            add(new VanillaStool(ModDecorativeBlocks.CALCITE_STOOL, Blocks.CALCITE));
            add(new VanillaStool(ModDecorativeBlocks.ANDESITE_STOOL, Blocks.ANDESITE));
            add(new VanillaStool(ModDecorativeBlocks.GRANITE_STOOL, Blocks.GRANITE));
            add(new VanillaStool(ModDecorativeBlocks.DIORITE_STOOL, Blocks.DIORITE));
            add(new VanillaStool(ModDecorativeBlocks.DEEPSLATE_STOOL, Blocks.DEEPSLATE));
            add(new VanillaStool(ModDecorativeBlocks.BLACKSTONE_STOOL, Blocks.BLACKSTONE));
            add(new VanillaStool(ModDecorativeBlocks.BASALT_STOOL, Blocks.BASALT));
            add(new VanillaStool(ModDecorativeBlocks.TUFF_STOOL, Blocks.TUFF));
        }
    };
}
