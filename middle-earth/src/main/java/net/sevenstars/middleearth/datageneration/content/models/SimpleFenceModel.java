package net.sevenstars.middleearth.datageneration.content.models;

import net.sevenstars.middleearth.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import java.util.ArrayList;
import java.util.List;

public class SimpleFenceModel {
    public record Fence(Block block, Block fence) {}
    public static List<Fence> blocks = new ArrayList<>() {
        {
        }
    };

    public static List<Fence> strippedFences = new ArrayList<>() {
        {
        }
    };

    public static List<Fence> vanillaStrippedFences = new ArrayList<>() {
        {
            add(new Fence(Blocks.STRIPPED_OAK_WOOD, ModBlocks.STRIPPED_OAK_WOOD_FENCE));
            add(new Fence(Blocks.STRIPPED_SPRUCE_WOOD, ModBlocks.STRIPPED_SPRUCE_WOOD_FENCE));
            add(new Fence(Blocks.STRIPPED_BIRCH_WOOD, ModBlocks.STRIPPED_BIRCH_WOOD_FENCE));
            add(new Fence(Blocks.STRIPPED_JUNGLE_WOOD, ModBlocks.STRIPPED_JUNGLE_WOOD_FENCE));
            add(new Fence(Blocks.STRIPPED_ACACIA_WOOD, ModBlocks.STRIPPED_ACACIA_WOOD_FENCE));
            add(new Fence(Blocks.STRIPPED_DARK_OAK_WOOD, ModBlocks.STRIPPED_DARK_OAK_WOOD_FENCE));
            add(new Fence(Blocks.STRIPPED_MANGROVE_WOOD, ModBlocks.STRIPPED_MANGROVE_WOOD_FENCE));
            add(new Fence(Blocks.STRIPPED_CHERRY_WOOD, ModBlocks.STRIPPED_CHERRY_WOOD_FENCE));
        }
    };

    public static List<Fence> vanillaWoodFences = new ArrayList<>() {
        {
            add(new Fence(Blocks.OAK_WOOD, ModBlocks.OAK_WOOD_FENCE));
            add(new Fence(Blocks.SPRUCE_WOOD, ModBlocks.SPRUCE_WOOD_FENCE));
            add(new Fence(Blocks.BIRCH_WOOD, ModBlocks.BIRCH_WOOD_FENCE));
            add(new Fence(Blocks.JUNGLE_WOOD, ModBlocks.JUNGLE_WOOD_FENCE));
            add(new Fence(Blocks.ACACIA_WOOD, ModBlocks.ACACIA_WOOD_FENCE));
            add(new Fence(Blocks.DARK_OAK_WOOD, ModBlocks.DARK_OAK_WOOD_FENCE));
            add(new Fence(Blocks.MANGROVE_WOOD, ModBlocks.MANGROVE_WOOD_FENCE));
            add(new Fence(Blocks.CHERRY_WOOD, ModBlocks.CHERRY_WOOD_FENCE));
        }
    };
}
