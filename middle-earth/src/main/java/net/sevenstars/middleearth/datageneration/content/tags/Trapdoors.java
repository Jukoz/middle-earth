package net.sevenstars.middleearth.datageneration.content.tags;

import net.sevenstars.middleearth.block.registration.BlockRegistryME;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class Trapdoors {
    public static List<Block> trapdoors = new ArrayList<>() {
        {
            add(BlockRegistryME.AGED_WOOD_TRAPDOOR);
            add(BlockRegistryME.BLACK_WATTLE_TRAPDOOR);
            add(BlockRegistryME.DARK_WATTLE_TRAPDOOR);
            add(BlockRegistryME.GREEN_WATTLE_TRAPDOOR);
            add(BlockRegistryME.RED_WATTLE_TRAPDOOR);
            add(BlockRegistryME.WATTLE_TRAPDOOR);
            add(BlockRegistryME.BRONZE_TRAPDOOR);
            add(BlockRegistryME.CRUDE_TRAPDOOR);
            add(BlockRegistryME.TREATED_STEEL_TRAPDOOR);
        }
    };
}
