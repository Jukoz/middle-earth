package net.jukoz.me.datageneration.content.models;

import net.jukoz.me.block.ModBlocks;
import net.jukoz.me.block.ModDecorativeBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import java.util.ArrayList;
import java.util.List;

public class SimpleWoodChairModel {
    public record VanillaChair(Block base, Block planks) {}

    public static List<Block> chairs = new ArrayList<>() {};

    public static List<VanillaChair> vanillaChairs = new ArrayList<>() {
        {
            add(new SimpleWoodChairModel.VanillaChair(ModDecorativeBlocks.OAK_CHAIR, Blocks.OAK_PLANKS));
            add(new SimpleWoodChairModel.VanillaChair(ModDecorativeBlocks.SPRUCE_CHAIR, Blocks.SPRUCE_PLANKS));
            add(new SimpleWoodChairModel.VanillaChair(ModDecorativeBlocks.BIRCH_CHAIR, Blocks.BIRCH_PLANKS));
            add(new SimpleWoodChairModel.VanillaChair(ModDecorativeBlocks.JUNGLE_CHAIR, Blocks.JUNGLE_PLANKS));
            add(new SimpleWoodChairModel.VanillaChair(ModDecorativeBlocks.ACACIA_CHAIR, Blocks.ACACIA_PLANKS));
            add(new SimpleWoodChairModel.VanillaChair(ModDecorativeBlocks.DARK_OAK_CHAIR, Blocks.DARK_OAK_PLANKS));
            add(new SimpleWoodChairModel.VanillaChair(ModDecorativeBlocks.MANGROVE_CHAIR, Blocks.MANGROVE_PLANKS));
            add(new SimpleWoodChairModel.VanillaChair(ModDecorativeBlocks.BAMBOO_CHAIR, Blocks.BAMBOO_BLOCK));
            add(new SimpleWoodChairModel.VanillaChair(ModDecorativeBlocks.CHERRY_CHAIR, Blocks.CHERRY_PLANKS));
            add(new SimpleWoodChairModel.VanillaChair(ModDecorativeBlocks.CRIMSON_CHAIR, Blocks.CRIMSON_PLANKS));
            add(new SimpleWoodChairModel.VanillaChair(ModDecorativeBlocks.WARPED_CHAIR, Blocks.WARPED_PLANKS));
        }
    };
}
