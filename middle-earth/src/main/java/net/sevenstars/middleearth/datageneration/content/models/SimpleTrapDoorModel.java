package net.sevenstars.middleearth.datageneration.content.models;

import net.sevenstars.middleearth.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import java.util.ArrayList;
import java.util.List;

public class SimpleTrapDoorModel {
    public record Trapdoor(Block block, Block trapdoor) {}

    public static List<Trapdoor> trapdoors = new ArrayList<>() {
        {
            add(new Trapdoor(ModBlocks.BLACK_WATTLE_AND_WHITE_DAUB, ModBlocks.BLACK_WATTLE_TRAPDOOR));
            add(new Trapdoor(ModBlocks.DARK_WATTLE_AND_DARK_DAUB, ModBlocks.DARK_WATTLE_TRAPDOOR));
            add(new Trapdoor(ModBlocks.GREEN_WATTLE_AND_WHITE_DAUB, ModBlocks.GREEN_WATTLE_TRAPDOOR));
            add(new Trapdoor(ModBlocks.RED_WATTLE_AND_WHITE_DAUB, ModBlocks.RED_WATTLE_TRAPDOOR));
            add(new Trapdoor(ModBlocks.WATTLE_AND_WHITE_DAUB, ModBlocks.WATTLE_TRAPDOOR));
            add(new Trapdoor(Blocks.IRON_BLOCK, ModBlocks.TREATED_STEEL_TRAPDOOR));
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
