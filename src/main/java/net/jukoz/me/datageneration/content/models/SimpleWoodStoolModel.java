package net.jukoz.me.datageneration.content.models;

import net.jukoz.me.block.ModBlocks;
import net.jukoz.me.block.ModDecorativeBlocks;
import net.jukoz.me.block.StoneBlockSets;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import java.util.ArrayList;
import java.util.List;

public class SimpleWoodStoolModel {
    public static List<Block> stools = new ArrayList<>() {
        {
        }
    };

    public static List<Block> vanillaStools = new ArrayList<>() {
        {
            add(ModDecorativeBlocks.OAK_WOOD_STOOL);
            add(ModDecorativeBlocks.SPRUCE_WOOD_STOOL);
            add(ModDecorativeBlocks.BIRCH_WOOD_STOOL);
            add(ModDecorativeBlocks.JUNGLE_WOOD_STOOL);
            add(ModDecorativeBlocks.ACACIA_WOOD_STOOL);
            add(ModDecorativeBlocks.DARK_OAK_WOOD_STOOL);
            add(ModDecorativeBlocks.MANGROVE_WOOD_STOOL);
            add(ModDecorativeBlocks.BAMBOO_WOOD_STOOL);
            add(ModDecorativeBlocks.CHERRY_WOOD_STOOL);
            add(ModDecorativeBlocks.CRIMSON_WOOD_STOOL);
            add(ModDecorativeBlocks.WARPED_WOOD_STOOL);
        }
    };
}
