package net.jukoz.me.datageneration.content.models;

import net.jukoz.me.block.ModDecorativeBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import java.util.ArrayList;
import java.util.List;

public class SimpleStoneChairModel {

    public record VanillaChair(Block base, Block origin) {}

    public static List<Block> chairs = new ArrayList<>() {
        {
        }
    };

    public static List<VanillaChair> vanillaChairs = new ArrayList<>() {
        {
            add(new VanillaChair(ModDecorativeBlocks.STONE_CHAIR, Blocks.STONE));
            add(new VanillaChair(ModDecorativeBlocks.CALCITE_CHAIR, Blocks.CALCITE));
            add(new VanillaChair(ModDecorativeBlocks.ANDESITE_CHAIR, Blocks.ANDESITE));
            add(new VanillaChair(ModDecorativeBlocks.GRANITE_CHAIR, Blocks.GRANITE));
            add(new VanillaChair(ModDecorativeBlocks.DIORITE_CHAIR, Blocks.DIORITE));
            add(new VanillaChair(ModDecorativeBlocks.DEEPSLATE_CHAIR, Blocks.DEEPSLATE));
            add(new VanillaChair(ModDecorativeBlocks.BLACKSTONE_CHAIR, Blocks.BLACKSTONE));
            add(new VanillaChair(ModDecorativeBlocks.BASALT_CHAIR, Blocks.BASALT));
            add(new VanillaChair(ModDecorativeBlocks.TUFF_CHAIR, Blocks.TUFF));
        }
    };
}
