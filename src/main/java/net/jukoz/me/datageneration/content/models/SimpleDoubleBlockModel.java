package net.jukoz.me.datageneration.content.models;

import net.jukoz.me.block.ModNatureBlocks;
import net.jukoz.me.item.ModResourceItems;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class SimpleDoubleBlockModel {
    public static List<Block> doubleBlocks = new ArrayList<>() {
        {
            add(ModNatureBlocks.WILD_PIPEWEED);
            add(ModNatureBlocks.WILD_TOMATO);

            add(ModNatureBlocks.TALL_TUBESHROOM);
            add(ModNatureBlocks.TALL_TRUMPET_SHROOM);

            add(ModNatureBlocks.TALL_CATTAILS);
            add(ModNatureBlocks.TALL_BULRUSH);
        }
    };

    public static List<Block> doubleBlocksItems = new ArrayList<>() {
        {
            add(ModResourceItems.REEDS);
        }
    };
}
