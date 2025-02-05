package net.sevenstars.middleearth.datageneration.content.tags;

import net.sevenstars.middleearth.block.ModBlocks;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class Doors {
    public static List<Block> doors = new ArrayList<>() {
        {
            add(ModBlocks.TREATED_STEEL_DOOR);
        }
    };
}
