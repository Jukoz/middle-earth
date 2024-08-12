package net.jukoz.me.datageneration.content.tags;

import net.jukoz.me.block.ModBlocks;
import net.jukoz.me.block.ModDecorativeBlocks;
import net.jukoz.me.block.ModNatureBlocks;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class MineableAxe {
    public static List<Block> blocks = new ArrayList<>() {
        {
            add(ModDecorativeBlocks.BLUE_CUSHION);
            add(ModDecorativeBlocks.BROWN_CUSHION);
            add(ModDecorativeBlocks.DARK_BLUE_CUSHION);
            add(ModDecorativeBlocks.DARK_BROWN_CUSHION);
            add(ModDecorativeBlocks.DARK_GREEN_CUSHION);
            add(ModDecorativeBlocks.DARK_RED_CUSHION);
            add(ModDecorativeBlocks.GREEN_CUSHION);
            add(ModDecorativeBlocks.RED_CUSHION);

            add(ModNatureBlocks.PINE_BRANCHES);
        }
    };
}
