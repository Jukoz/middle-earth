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

            add(ModDecorativeBlocks.BLUE_HOBBIT_DOOR);
            add(ModDecorativeBlocks.GREEN_HOBBIT_DOOR);
            add(ModDecorativeBlocks.RED_HOBBIT_DOOR);
            add(ModDecorativeBlocks.YELLOW_HOBBIT_DOOR);
            add(ModDecorativeBlocks.LARCH_HOBBIT_DOOR);
            add(ModDecorativeBlocks.SPRUCE_HOBBIT_DOOR);
            add(ModDecorativeBlocks.REINFORCED_SPRUCE_DOOR);
            add(ModDecorativeBlocks.VARNISHED_DWARVEN_DOOR);
            add(ModDecorativeBlocks.GREAT_ELVEN_GATE);

            add(ModBlocks.BLACK_WATTLE_TRAPDOOR);
            add(ModBlocks.DARK_WATTLE_TRAPDOOR);
            add(ModBlocks.GREEN_WATTLE_TRAPDOOR);
            add(ModBlocks.RED_WATTLE_TRAPDOOR);
            add(ModBlocks.WATTLE_TRAPDOOR);
        }
    };
}
