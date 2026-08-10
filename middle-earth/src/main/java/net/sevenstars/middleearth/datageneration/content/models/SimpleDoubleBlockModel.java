package net.sevenstars.middleearth.datageneration.content.models;

import net.sevenstars.middleearth.block.registration.ModNatureBlocks;
import net.sevenstars.middleearth.item.ResourceItemsME;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class SimpleDoubleBlockModel {
    public static List<Block> doubleBlocks = new ArrayList<>() {
        {
            add(ModNatureBlocks.WILD_PIPEWEED);
            add(ModNatureBlocks.TALL_WILD_WHEAT);

            add(ModNatureBlocks.TALL_TUBESHROOM);
            add(ModNatureBlocks.TALL_TRUMPET_SHROOM);

            add(ModNatureBlocks.HOGWEED);

            add(ModNatureBlocks.TALL_CATTAILS);
            add(ModNatureBlocks.TALL_BULRUSH);
        }
    };

    public static List<Block> doubleBlocksItems = new ArrayList<>() {
        {
            add(ResourceItemsME.REEDS);
        }
    };
}
