package net.jukoz.me.datageneration.content.models;

import net.jukoz.me.block.ModDecorativeBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import java.util.ArrayList;
import java.util.List;

public class SimpleWoodBenchModel {

    public record VanillaBench(Block base, Block planks) {}

    public static List<Block> benchs = new ArrayList<>() {};

    public static List<VanillaBench> vanillaBenchs = new ArrayList<>() {
        {
            add(new VanillaBench(ModDecorativeBlocks.OAK_BENCH, Blocks.OAK_PLANKS));
            add(new VanillaBench(ModDecorativeBlocks.SPRUCE_BENCH, Blocks.SPRUCE_PLANKS));
            add(new VanillaBench(ModDecorativeBlocks.BIRCH_BENCH, Blocks.BIRCH_PLANKS));
            add(new VanillaBench(ModDecorativeBlocks.JUNGLE_BENCH, Blocks.JUNGLE_PLANKS));
            add(new VanillaBench(ModDecorativeBlocks.ACACIA_BENCH, Blocks.ACACIA_PLANKS));
            add(new VanillaBench(ModDecorativeBlocks.DARK_OAK_BENCH, Blocks.DARK_OAK_PLANKS));
            add(new VanillaBench(ModDecorativeBlocks.MANGROVE_BENCH, Blocks.MANGROVE_PLANKS));
            add(new VanillaBench(ModDecorativeBlocks.BAMBOO_BENCH, Blocks.BAMBOO_BLOCK));
            add(new VanillaBench(ModDecorativeBlocks.CHERRY_BENCH, Blocks.CHERRY_PLANKS));
            add(new VanillaBench(ModDecorativeBlocks.CRIMSON_BENCH, Blocks.CRIMSON_PLANKS));
            add(new VanillaBench(ModDecorativeBlocks.WARPED_BENCH, Blocks.WARPED_PLANKS));
        }
    };
}
