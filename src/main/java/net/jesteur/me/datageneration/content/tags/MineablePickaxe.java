package net.jesteur.me.datageneration.content.tags;

import net.jesteur.me.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

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
            add(ModBlocks.MITHRIL_BLOCK);
            add(ModBlocks.MITHRIL_ORE);
            add(ModBlocks.REEDS_BLOCK);
            add(ModBlocks.STRAW_BLOCK);
        }
    };
}
