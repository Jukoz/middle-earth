package net.jesteur.me.datageneration.content;

import net.jesteur.me.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;

public class MineablePickaxe {
    public static List<Block> blocks = new ArrayList<>() {
        {
            add(ModBlocks.MITHRIL_ORE);
        }
    };
}
