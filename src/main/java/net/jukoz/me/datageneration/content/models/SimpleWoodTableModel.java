package net.jukoz.me.datageneration.content.models;

import net.jukoz.me.block.ModBlocks;
import net.jukoz.me.block.ModDecorativeBlocks;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class SimpleWoodTableModel {
    public static List<Block> tables = new ArrayList<>() {
        {
        }
    };

    public static List<Block> vanillaTables = new ArrayList<>() {
        {
            add(ModDecorativeBlocks.OAK_WOOD_TABLE);
            add(ModDecorativeBlocks.SPRUCE_WOOD_TABLE);
            add(ModDecorativeBlocks.BIRCH_WOOD_TABLE);
            add(ModDecorativeBlocks.JUNGLE_WOOD_TABLE);
            add(ModDecorativeBlocks.ACACIA_WOOD_TABLE);
            add(ModDecorativeBlocks.DARK_OAK_WOOD_TABLE);
            add(ModDecorativeBlocks.MANGROVE_WOOD_TABLE);
            add(ModDecorativeBlocks.BAMBOO_WOOD_TABLE);
            add(ModDecorativeBlocks.CHERRY_WOOD_TABLE);
            add(ModDecorativeBlocks.CRIMSON_WOOD_TABLE);
            add(ModDecorativeBlocks.WARPED_WOOD_TABLE);
        }
    };
}
