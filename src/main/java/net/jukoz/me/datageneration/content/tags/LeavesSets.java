package net.jukoz.me.datageneration.content.tags;

import net.jukoz.me.block.ModNatureBlocks;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class LeavesSets {
    public static List<Block> blocks = new ArrayList<>() {
        {
            add(ModNatureBlocks.LEBETHRON_LEAVES);
            add(ModNatureBlocks.MAPLE_LEAVES);
        }
    };
}
