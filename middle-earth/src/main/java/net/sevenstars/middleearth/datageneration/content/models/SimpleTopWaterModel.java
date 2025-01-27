package net.sevenstars.middleearth.datageneration.content.models;

import net.sevenstars.middleearth.block.ModNatureBlocks;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class SimpleTopWaterModel {
    public static List<Block> topWaterBlocks = new ArrayList<>() {
        {
            add(ModNatureBlocks.SMALL_LILY_PADS);
            add(ModNatureBlocks.SMALL_FLOWERING_LILY_PADS);
            add(ModNatureBlocks.LILY_PADS);
            add(ModNatureBlocks.DUCKWEED);

            add(ModNatureBlocks.FLOATING_ICE);
        }
    };
}
