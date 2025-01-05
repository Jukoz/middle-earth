package net.jukoz.me.datageneration.content.tags;

import net.jukoz.me.block.ModBlocks;
import net.jukoz.me.block.ModNatureBlocks;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class MineableHoe {
    public static List<Block> blocks = new ArrayList<>() {
    {
        add(ModNatureBlocks.LEBETHRON_LEAVES);

        add(ModNatureBlocks.BERRY_HOLLY_LEAVES);

        add(ModNatureBlocks.DRY_LARCH_LEAVES);

        add(ModNatureBlocks.FLOWERING_MALLORN_LEAVES);

        add(ModNatureBlocks.MAPLE_LEAVES);
        add(ModNatureBlocks.ORANGE_MAPLE_LEAVES);
        add(ModNatureBlocks.RED_MAPLE_LEAVES);
        add(ModNatureBlocks.YELLOW_MAPLE_LEAVES);

        add(ModNatureBlocks.DRY_PINE_LEAVES);

        add(ModBlocks.REED_BLOCK);
        add(ModBlocks.REED_SLAB);
        add(ModBlocks.REED_STAIRS);
        add(ModBlocks.REED_WALL);

        add(ModBlocks.STRAW_BLOCK);
        add(ModBlocks.STRAW_SLAB);
        add(ModBlocks.STRAW_STAIRS);
        add(ModBlocks.STRAW_WALL);
    }};
}
