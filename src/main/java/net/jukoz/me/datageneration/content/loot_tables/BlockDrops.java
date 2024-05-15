package net.jukoz.me.datageneration.content.loot_tables;

import net.jukoz.me.block.ModBlocks;
import net.jukoz.me.block.ModDecorativeBlocks;
import net.jukoz.me.block.ModNatureBlocks;
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

            add(ModDecorativeBlocks.ROPE);

            add(ModNatureBlocks.BROWN_BOLETE);
            add(ModNatureBlocks.CAVE_AMANITA);
            add(ModNatureBlocks.DEEP_FIRECAP);
            add(ModNatureBlocks.GHOSTSHROOM);
            add(ModNatureBlocks.MORSEL);
            add(ModNatureBlocks.SKY_FIRECAP);
            add(ModNatureBlocks.TRUMPET_SHROOM);
            add(ModNatureBlocks.TUBESHRROM);
            add(ModNatureBlocks.VIOLET_CAPS);
            add(ModNatureBlocks.WHITE_MUSHROOM);
            add(ModNatureBlocks.YELLOW_AMANITA);

            add(ModNatureBlocks.WHITE_LEBETHRON_SAPLING);
            add(ModNatureBlocks.LEBETHRON_SAPLING);
            add(ModNatureBlocks.WHITE_PALM_SAPLING);

            add(ModDecorativeBlocks.ALLOY_FURNACE);

            add(ModNatureBlocks.MALLOS);
            add(ModNatureBlocks.YELLOW_FLOWER);
            add(ModNatureBlocks.HOROKAKA);
            add(ModNatureBlocks.GIANT_HOROKAKA);
        }
    };
}
