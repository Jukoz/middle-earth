package net.jukoz.me.datageneration.content.tags;

import net.jukoz.me.block.ModBlocks;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class MineablePickaxe {
    public static List<Block> blocks = new ArrayList<>() {
        {
            add(ModBlocks.CALCITE_PILLAR);
            add(ModBlocks.LIMESTONE_PILLAR);
            add(ModBlocks.CHISELED_BLUE_ROCK_BRICKS);
            add(ModBlocks.CHISELED_CALCITE);
            add(ModBlocks.CHISELED_LIMESTONE_BRICKS);
            add(ModBlocks.MITHRIL_ORE);
            add(ModBlocks.RAW_MITHRIL_BLOCK);
            add(ModBlocks.MITHRIL_BLOCK);
            add(ModBlocks.RAW_TIN_BLOCK);
            add(ModBlocks.TIN_BLOCK);
            add(ModBlocks.RAW_LEAD_BLOCK);
            add(ModBlocks.LEAD_BLOCK);
            add(ModBlocks.RAW_SILVER_BLOCK);
            add(ModBlocks.SILVER_BLOCK);
        }
    };
}
