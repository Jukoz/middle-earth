package net.jukoz.me.datageneration.content.models;

import net.jukoz.me.block.ModDecorativeBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import java.util.ArrayList;
import java.util.List;

public class SimpleWoodChairModel {
    public record VanillaChair(Block base, Block origin) {}


    public static List<Block> chairs = new ArrayList<>() {
        {
        }
    };

    public static List<VanillaChair> vanillaChairs = new ArrayList<>() {
        {
            add(new SimpleWoodChairModel.VanillaChair(ModDecorativeBlocks.OAK_CHAIR, Blocks.STRIPPED_OAK_LOG));
            add(new SimpleWoodChairModel.VanillaChair(ModDecorativeBlocks.SPRUCE_CHAIR, Blocks.STRIPPED_SPRUCE_LOG));
            add(new SimpleWoodChairModel.VanillaChair(ModDecorativeBlocks.BIRCH_CHAIR, Blocks.STRIPPED_BIRCH_LOG));
            add(new SimpleWoodChairModel.VanillaChair(ModDecorativeBlocks.JUNGLE_CHAIR, Blocks.STRIPPED_JUNGLE_LOG));
            add(new SimpleWoodChairModel.VanillaChair(ModDecorativeBlocks.ACACIA_CHAIR, Blocks.STRIPPED_ACACIA_LOG));
            add(new SimpleWoodChairModel.VanillaChair(ModDecorativeBlocks.DARK_OAK_CHAIR, Blocks.STRIPPED_DARK_OAK_LOG));
            add(new SimpleWoodChairModel.VanillaChair(ModDecorativeBlocks.MANGROVE_CHAIR, Blocks.STRIPPED_MANGROVE_LOG));
            add(new SimpleWoodChairModel.VanillaChair(ModDecorativeBlocks.BAMBOO_CHAIR, Blocks.STRIPPED_BAMBOO_BLOCK));
            add(new SimpleWoodChairModel.VanillaChair(ModDecorativeBlocks.CHERRY_CHAIR, Blocks.STRIPPED_CHERRY_LOG));
            add(new SimpleWoodChairModel.VanillaChair(ModDecorativeBlocks.CRIMSON_CHAIR, Blocks.STRIPPED_CRIMSON_STEM));
            add(new SimpleWoodChairModel.VanillaChair(ModDecorativeBlocks.WARPED_CHAIR, Blocks.STRIPPED_WARPED_STEM));
        }
    };
}
