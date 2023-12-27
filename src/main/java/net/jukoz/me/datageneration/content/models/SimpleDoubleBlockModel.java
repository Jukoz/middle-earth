package net.jukoz.me.datageneration.content.models;

import net.jukoz.me.block.ModBlocks;
import net.jukoz.me.block.ModNatureBlocks;
import net.jukoz.me.item.ModRessourceItems;
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
        }
    };

    public static List<Block> doubleBlocksItems = new ArrayList<>() {
        {
            add(ModRessourceItems.REEDS);
        }
    };
}
