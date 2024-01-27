package net.jukoz.me.datageneration.content.tags;

import net.jukoz.me.block.ModBlocks;
import net.jukoz.me.block.ModDecorativeBlocks;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class MineableAxe {
    public static List<Block> blocks = new ArrayList<>() {
        {
            add(ModDecorativeBlocks.WOOD_PILE);
        }
    };
}
