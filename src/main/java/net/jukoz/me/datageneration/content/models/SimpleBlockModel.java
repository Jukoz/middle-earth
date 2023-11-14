package net.jukoz.me.datageneration.content.models;

import net.jukoz.me.block.ModBlocks;
import net.jukoz.me.block.OreRockSets;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class SimpleBlockModel {
    public static List<Block> blocks = new ArrayList<>() {
        {
            add(ModBlocks.CHISELED_BLUE_ROCK_BRICKS);
            add(ModBlocks.CHISELED_CALCITE);
            add(ModBlocks.CHISELED_LIMESTONE_BRICKS);
            add(ModBlocks.DRY_DIRT);
            add(ModBlocks.MORDOR_DIRT);
            add(ModBlocks.MITHRIL_BLOCK);
            add(ModBlocks.TIN_BLOCK);
            add(ModBlocks.MITHRIL_ORE);
            add(ModBlocks.LEAD_BLOCK);
            add(ModBlocks.SILVER_BLOCK);
        }
    };
}
