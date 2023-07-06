package net.jesteur.me.datageneration.content.multi;

import net.jesteur.me.block.ModBlocks;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class WoodMulti {
    public record WoodRecord(Block wood, Block log) {

    }

    public static List<WoodRecord> woods = new ArrayList<>() {
        {
            add(new WoodRecord(ModBlocks.MALLORN_WOOD, ModBlocks.MALLORN_LOG));
        }
    };

}
