package net.sevenstars.middleearth.datageneration.content.tags;

import net.sevenstars.middleearth.features.farming.FarmingBlocks;

import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class Crops {
    public static List<Block> crops = new ArrayList<>() {
        {
            add(FarmingBlocks.TOMATO_CROP);
            add(FarmingBlocks.BELL_PEPPER_CROP);
            add(FarmingBlocks.CUCUMBER_CROP);
            add(FarmingBlocks.FLAX_CROP);
            add(FarmingBlocks.GARLIC_CROP);
            add(FarmingBlocks.LEEK_CROP);
            add(FarmingBlocks.LETTUCE_CROP);
            add(FarmingBlocks.ONION_CROP);
            add(FarmingBlocks.PIPEWEED_CROP);
        }
    };
}
