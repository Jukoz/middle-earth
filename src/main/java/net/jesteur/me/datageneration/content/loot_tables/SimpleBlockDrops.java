package net.jesteur.me.datageneration.content.loot_tables;

import net.jesteur.me.block.ModBlocks;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class SimpleBlockDrops {
    public static List<Block> blocks = new ArrayList<>() {
        {
            add(ModBlocks.ASHEN_ROCK);
        }
    };
}
