package net.jukoz.me.datageneration.content.loot_tables;

import net.jukoz.me.block.ModBlocks;
import net.jukoz.me.block.ModDecorativeBlocks;
import net.jukoz.me.datageneration.content.models.SimpleVerticalSlabModel;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import java.util.ArrayList;
import java.util.List;

public class BlockDrops {
    public static List<Block> blocks = new ArrayList<>() {
        {
            add(ModDecorativeBlocks.SILVER_LANTERN);
            add(ModDecorativeBlocks.WALL_SILVER_LANTERN);
            add(ModDecorativeBlocks.DWARVEN_LANTERN);
            add(ModDecorativeBlocks.WALL_DWARVEN_LANTERN);
            add(ModDecorativeBlocks.WOOD_PILE);
        }
    };
}
