package net.sevenstars.middleearth.datageneration.content.tags;

import net.minecraft.block.Block;
import net.sevenstars.middleearth.block.registration.BlockRegistryME;

import java.util.ArrayList;
import java.util.List;

public class Fences {
    public static List<Block> fences = new ArrayList<>() {
        {
            add(BlockRegistryME.TREATED_WOOD_ROPE_FENCE);
        }
    };
}
