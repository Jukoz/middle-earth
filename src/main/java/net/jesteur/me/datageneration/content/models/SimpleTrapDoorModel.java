package net.jesteur.me.datageneration.content.models;

import net.jesteur.me.block.ModBlocks;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class SimpleTrapDoorModel {
    public static List<Block> blocks = new ArrayList<>() {
        {
            add(ModBlocks.STONE_TRAPDOOR);
            add(ModBlocks.BLACKSTONE_TRAPDOOR);
        }
    };
}
