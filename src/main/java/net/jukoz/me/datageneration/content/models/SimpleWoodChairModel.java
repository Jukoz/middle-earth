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
            add(new SimpleWoodChairModel.VanillaChair(ModDecorativeBlocks.OAK_CHAIR, Blocks.OAK_LOG));
            add(new SimpleWoodChairModel.VanillaChair(ModDecorativeBlocks.SPRUCE_CHAIR, Blocks.SPRUCE_LOG));
            add(new SimpleWoodChairModel.VanillaChair(ModDecorativeBlocks.BIRCH_CHAIR, Blocks.BIRCH_LOG));
            add(new SimpleWoodChairModel.VanillaChair(ModDecorativeBlocks.JUNGLE_CHAIR, Blocks.JUNGLE_LOG));
            add(new SimpleWoodChairModel.VanillaChair(ModDecorativeBlocks.ACACIA_CHAIR, Blocks.ACACIA_LOG));
            add(new SimpleWoodChairModel.VanillaChair(ModDecorativeBlocks.DARK_OAK_CHAIR, Blocks.DARK_OAK_LOG));
            add(new SimpleWoodChairModel.VanillaChair(ModDecorativeBlocks.MANGROVE_CHAIR, Blocks.MANGROVE_LOG));
            add(new SimpleWoodChairModel.VanillaChair(ModDecorativeBlocks.BAMBOO_CHAIR, Blocks.BAMBOO_BLOCK));
            add(new SimpleWoodChairModel.VanillaChair(ModDecorativeBlocks.CHERRY_CHAIR, Blocks.CHERRY_LOG));
            add(new SimpleWoodChairModel.VanillaChair(ModDecorativeBlocks.CRIMSON_CHAIR, Blocks.CRIMSON_STEM));
            add(new SimpleWoodChairModel.VanillaChair(ModDecorativeBlocks.WARPED_CHAIR, Blocks.WARPED_STEM));
        }
    };
}
