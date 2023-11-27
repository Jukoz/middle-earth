package net.jukoz.me.datageneration.content.models;

import net.jukoz.me.block.ModBlocks;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class SimpleBlockModel {
    public static List<Block> blocks = new ArrayList<>() {
        {

            add(ModBlocks.DRY_DIRT);
            add(ModBlocks.MORDOR_DIRT);

            add(ModBlocks.CHISELED_BLUE_ROCK_BRICKS);

            add(ModBlocks.CHISELED_CALCITE_BRICKS);

            add(ModBlocks.CHISELED_LIMESTONE_BRICKS);

            add(ModBlocks.CHISELED_ANDESITE_BRICKS);
            add(ModBlocks.CUT_POLISHED_ANDESITE);

            add(ModBlocks.CHISELED_GRANITE_BRICKS);
            add(ModBlocks.CUT_POLISHED_GRANITE);

            add(ModBlocks.CHISELED_DIORITE_BRICKS);
            add(ModBlocks.CUT_POLISHED_DIORITE);

            //METALS
            add(ModBlocks.MITHRIL_ORE);
            add(ModBlocks.RAW_MITHRIL_BLOCK);
            add(ModBlocks.MITHRIL_BLOCK);
            add(ModBlocks.RAW_TIN_BLOCK);
            add(ModBlocks.TIN_BLOCK);
            add(ModBlocks.RAW_LEAD_BLOCK);
            add(ModBlocks.LEAD_BLOCK);
            add(ModBlocks.RAW_SILVER_BLOCK);
            add(ModBlocks.SILVER_BLOCK);
            add(ModBlocks.CUT_LEAD);

            add(ModBlocks.RIVER_SAND);
        }
    };
}
