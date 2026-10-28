package net.sevenstars.middleearth.datageneration.content.tags;

import net.sevenstars.middleearth.block.registration.BlockRegistryME;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class Doors {
    public static List<Block> doors = new ArrayList<>() {
        {
            add(BlockRegistryME.AGED_WOOD_DOOR);
            add(BlockRegistryME.BRONZE_DOOR);
            add(BlockRegistryME.CRUDE_DOOR);
            add(BlockRegistryME.TREATED_STEEL_DOOR);
        }
    };
}
