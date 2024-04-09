package net.jukoz.me.datageneration.content.models;

import net.jukoz.me.block.ModBlocks;
import net.jukoz.me.block.ModDecorativeBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import java.util.ArrayList;
import java.util.List;

public class SimpleWoodChairModel {
    public record VanillaChair(Block base, Block planks, Block legs, Block origin) {}


    public static List<Block> chairs = new ArrayList<>() {
        {
        }
    };

    public static List<VanillaChair> vanillaChairs = new ArrayList<>() {
        {
            add(new SimpleWoodChairModel.VanillaChair(ModDecorativeBlocks.OAK_CHAIR, Blocks.OAK_SLAB, ModBlocks.STRIPPED_OAK_WOOD_FENCE, Blocks.STRIPPED_OAK_LOG));
            add(new SimpleWoodChairModel.VanillaChair(ModDecorativeBlocks.SPRUCE_CHAIR, Blocks.SPRUCE_SLAB, ModBlocks.STRIPPED_SPRUCE_WOOD_FENCE, Blocks.STRIPPED_SPRUCE_LOG));
            add(new SimpleWoodChairModel.VanillaChair(ModDecorativeBlocks.BIRCH_CHAIR, Blocks.BIRCH_SLAB, ModBlocks.STRIPPED_BIRCH_WOOD_FENCE, Blocks.STRIPPED_BIRCH_LOG));
            add(new SimpleWoodChairModel.VanillaChair(ModDecorativeBlocks.JUNGLE_CHAIR, Blocks.JUNGLE_SLAB, ModBlocks.STRIPPED_JUNGLE_WOOD_FENCE, Blocks.STRIPPED_JUNGLE_LOG));
            add(new SimpleWoodChairModel.VanillaChair(ModDecorativeBlocks.ACACIA_CHAIR, Blocks.ACACIA_SLAB, ModBlocks.STRIPPED_ACACIA_WOOD_FENCE, Blocks.STRIPPED_ACACIA_LOG));
            add(new SimpleWoodChairModel.VanillaChair(ModDecorativeBlocks.DARK_OAK_CHAIR, Blocks.DARK_OAK_SLAB, ModBlocks.STRIPPED_DARK_OAK_WOOD_FENCE, Blocks.STRIPPED_DARK_OAK_LOG));
            add(new SimpleWoodChairModel.VanillaChair(ModDecorativeBlocks.MANGROVE_CHAIR, Blocks.MANGROVE_SLAB, ModBlocks.STRIPPED_MANGROVE_WOOD_FENCE, Blocks.STRIPPED_MANGROVE_LOG));
            add(new SimpleWoodChairModel.VanillaChair(ModDecorativeBlocks.BAMBOO_CHAIR, Blocks.BAMBOO_BLOCK, Blocks.BAMBOO_FENCE, Blocks.STRIPPED_BAMBOO_BLOCK));
            add(new SimpleWoodChairModel.VanillaChair(ModDecorativeBlocks.CHERRY_CHAIR, Blocks.CHERRY_SLAB, ModBlocks.STRIPPED_CHERRY_WOOD_FENCE, Blocks.STRIPPED_CHERRY_LOG));
            add(new SimpleWoodChairModel.VanillaChair(ModDecorativeBlocks.CRIMSON_CHAIR, Blocks.CRIMSON_STEM, Blocks.CRIMSON_FENCE, Blocks.STRIPPED_CRIMSON_STEM));
            add(new SimpleWoodChairModel.VanillaChair(ModDecorativeBlocks.WARPED_CHAIR, Blocks.WARPED_STEM, Blocks.WARPED_FENCE, Blocks.STRIPPED_WARPED_STEM));
        }
    };
}
