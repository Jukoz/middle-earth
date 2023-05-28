package net.jesteur.me.datageneration.content.tags;

import net.jesteur.me.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;

public class MineablePickaxe {
    public static List<Block> blocks = new ArrayList<>() {
        {
            add(ModBlocks.MITHRIL_ORE);
            add(ModBlocks.GONDOR_BRICKS);
            add(ModBlocks.GONDOR_BRICKS_SLAB);
            add(ModBlocks.GONDOR_BRICKS_STAIRS);
            add(ModBlocks.GONDOR_BRICKS_WALL);
            add(ModBlocks.ASHEN_BRICKS);
            add(ModBlocks.ASHEN_BRICKS_SLAB);
            add(ModBlocks.ASHEN_BRICKS_STAIRS);
            add(ModBlocks.ASHEN_BRICKS_WALL);
            add(ModBlocks.MITHRIL_BLOCK);
        }
    };
}
