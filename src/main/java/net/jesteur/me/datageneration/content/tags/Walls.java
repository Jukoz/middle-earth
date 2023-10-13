package net.jesteur.me.datageneration.content.tags;

import net.jesteur.me.block.ModBlocks;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class Walls {
    public static List<Block> walls = new ArrayList<>() {
        {
            add(ModBlocks.REED_WALL);
            add(ModBlocks.STRAW_WALL);
        }
    };
}
