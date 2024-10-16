package net.jukoz.me.datageneration.content.tags;

import net.jukoz.me.block.ModBlocks;
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
