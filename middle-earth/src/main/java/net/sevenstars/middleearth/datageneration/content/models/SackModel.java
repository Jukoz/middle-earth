package net.sevenstars.middleearth.datageneration.content.models;

import net.minecraft.block.Block;
import net.sevenstars.middleearth.features.storage.StorageBlocks;

import java.util.ArrayList;
import java.util.List;

public class SackModel {
    public static List<Block> sacks = new ArrayList<>(){
        {
            add(StorageBlocks.BLACK_SACK);
            add(StorageBlocks.BLUE_SACK);
            add(StorageBlocks.BROWN_SACK);
            add(StorageBlocks.CYAN_SACK);
            add(StorageBlocks.GREEN_SACK);
            add(StorageBlocks.GRAY_SACK);
            add(StorageBlocks.LIGHT_BLUE_SACK);
            add(StorageBlocks.LIGHT_GRAY_SACK);
            add(StorageBlocks.LIME_SACK);
            add(StorageBlocks.MAGENTA_SACK);
            add(StorageBlocks.ORANGE_SACK);
            add(StorageBlocks.PINK_SACK);
            add(StorageBlocks.PURPLE_SACK);
            add(StorageBlocks.RED_SACK);
            add(StorageBlocks.YELLOW_SACK);
            add(StorageBlocks.WHITE_SACK);
        }
    };
}
