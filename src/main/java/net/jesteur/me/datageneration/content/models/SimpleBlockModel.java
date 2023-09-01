package net.jesteur.me.datageneration.content.models;

import net.jesteur.me.block.ModBlocks;
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
            add(ModBlocks.MITHRIL_ORE);
        }
    };
}
