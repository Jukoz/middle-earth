package net.sevenstars.middleearth.datageneration.content.models;

import net.sevenstars.middleearth.block.ModNatureBlocks;
import net.sevenstars.middleearth.item.ModResourceItems;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class SimpleDoubleBlockModel {
    public static List<Block> doubleBlocks = new ArrayList<>() {
        {
            add(ModNatureBlocks.WILD_PIPEWEED);
            add(ModNatureBlocks.TALL_WILD_WHEAT);
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
