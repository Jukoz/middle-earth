package net.jukoz.me.datageneration.content.models;

import net.jukoz.me.block.ModBlocks;
import net.jukoz.me.block.ModDecorativeBlocks;
import net.jukoz.me.block.StoneBlockSets;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import java.util.ArrayList;
import java.util.List;

public class SimpleWoodStoolModel {

    public record VanillaStool(Block base, Block origin) {}

    public static List<Block> stools = new ArrayList<>() {
        {
        }
    };

    public static List<VanillaStool> vanillaStools = new ArrayList<>() {
        {
            add(new VanillaStool(ModDecorativeBlocks.OAK_STOOL, Blocks.STRIPPED_OAK_LOG));
            add(new VanillaStool(ModDecorativeBlocks.SPRUCE_STOOL, Blocks.STRIPPED_SPRUCE_LOG));
            add(new VanillaStool(ModDecorativeBlocks.BIRCH_STOOL, Blocks.STRIPPED_BIRCH_LOG));
            add(new VanillaStool(ModDecorativeBlocks.JUNGLE_STOOL, Blocks.STRIPPED_JUNGLE_LOG));
            add(new VanillaStool(ModDecorativeBlocks.ACACIA_STOOL, Blocks.STRIPPED_ACACIA_LOG));
            add(new VanillaStool(ModDecorativeBlocks.DARK_OAK_STOOL, Blocks.STRIPPED_DARK_OAK_LOG));
            add(new VanillaStool(ModDecorativeBlocks.MANGROVE_STOOL, Blocks.STRIPPED_MANGROVE_LOG));
            add(new VanillaStool(ModDecorativeBlocks.BAMBOO_STOOL, Blocks.STRIPPED_BAMBOO_BLOCK));
            add(new VanillaStool(ModDecorativeBlocks.CHERRY_STOOL, Blocks.STRIPPED_CHERRY_LOG));
            add(new VanillaStool(ModDecorativeBlocks.CRIMSON_STOOL, Blocks.STRIPPED_CRIMSON_STEM));
            add(new VanillaStool(ModDecorativeBlocks.WARPED_STOOL, Blocks.STRIPPED_WARPED_STEM));
        }
    };
}
