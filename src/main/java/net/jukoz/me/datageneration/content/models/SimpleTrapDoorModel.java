package net.jukoz.me.datageneration.content.models;

import net.jukoz.me.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import java.util.ArrayList;
import java.util.List;

public class SimpleTrapDoorModel {
    public record Trapdoor(Block block, Block trapdoor) {}

    public static List<Trapdoor> trapdoors = new ArrayList<>() {
        {
        }
    };

    public static List<Trapdoor> stoneTrapdoors = new ArrayList<>() {
        {
        }
    };

    public static List<Trapdoor> vanillaStoneTrapdoors = new ArrayList<>() {
        {
            add(new Trapdoor(Blocks.STONE, ModBlocks.STONE_TRAPDOOR));
            add(new Trapdoor(Blocks.GRANITE, ModBlocks.GRANITE_TRAPDOOR));
            add(new Trapdoor(Blocks.DIORITE, ModBlocks.DIORITE_TRAPDOOR));
            add(new Trapdoor(Blocks.ANDESITE, ModBlocks.ANDESITE_TRAPDOOR));
            add(new Trapdoor(Blocks.CALCITE, ModBlocks.CALCITE_TRAPDOOR));
            add(new Trapdoor(Blocks.DEEPSLATE, ModBlocks.DEEPSLATE_TRAPDOOR));
            add(new Trapdoor(Blocks.TUFF, ModBlocks.TUFF_TRAPDOOR));
            add(new Trapdoor(Blocks.BASALT, ModBlocks.BASALT_TRAPDOOR));
            add(new Trapdoor(Blocks.BLACKSTONE, ModBlocks.BLACKSTONE_TRAPDOOR));
        }
    };
}
