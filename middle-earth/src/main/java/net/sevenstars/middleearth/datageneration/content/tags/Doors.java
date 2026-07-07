package net.sevenstars.middleearth.datageneration.content.tags;

import net.sevenstars.middleearth.block.registration.ModBlocks;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class Doors {
    public static List<Block> doors = new ArrayList<>() {
        {
            add(ModBlocks.AGED_WOOD_DOOR);
            add(ModBlocks.BRONZE_DOOR);
            add(ModBlocks.CRUDE_DOOR);
            add(ModBlocks.TREATED_STEEL_DOOR);
        }
    };
}
