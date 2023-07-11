package net.jesteur.me.datageneration.content.loot_tables;

import net.jesteur.me.block.ModBlocks;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class BlockDrops {
    public static List<Block> blocks = new ArrayList<>() {
        {
            add(ModBlocks.CALCITE_PILLAR);
            add(ModBlocks.CHISELED_BLUE_ROCK_BRICKS);
            add(ModBlocks.CHISELED_CALCITE);
            add(ModBlocks.MORDOR_DIRT);
        }
    };
}
