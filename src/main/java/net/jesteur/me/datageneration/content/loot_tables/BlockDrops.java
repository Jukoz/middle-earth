package net.jesteur.me.datageneration.content.loot_tables;

import net.jesteur.me.block.ModBlocks;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class BlockDrops {
    public static List<Block> blocks = new ArrayList<>() {
        {
            add(ModBlocks.CALCITE_PILLAR);
            add(ModBlocks.LIMESTONE_PILLAR);
            add(ModBlocks.CHISELED_BLUE_ROCK_BRICKS);
            add(ModBlocks.CHISELED_CALCITE);
            add(ModBlocks.CHISELED_LIMESTONE_BRICKS);
            add(ModBlocks.DRY_DIRT);
            add(ModBlocks.MORDOR_DIRT);
            add(ModBlocks.REEDS_BLOCK);
            add(ModBlocks.STRAW_BLOCK);
            add(ModBlocks.STONE_TRAPDOOR);
            add(ModBlocks.BLACKSTONE_TRAPDOOR);
        }
    };
}
