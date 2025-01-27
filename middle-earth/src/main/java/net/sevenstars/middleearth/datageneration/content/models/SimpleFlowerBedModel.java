package net.sevenstars.middleearth.datageneration.content.models;

import net.sevenstars.middleearth.block.ModNatureBlocks;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class SimpleFlowerBedModel {
    public static List<Block> flowerBeds = new ArrayList<>() {
        {
            add(ModNatureBlocks.BROWN_BOLETE_TILLER);
            add(ModNatureBlocks.CAVE_AMANITA_TILLER);
            add(ModNatureBlocks.DEEP_FIRECAP_TILLER);
            add(ModNatureBlocks.GHOSTSHROOM_TILLER);
            add(ModNatureBlocks.MORSEL_TILLER);
            add(ModNatureBlocks.SKY_FIRECAP_TILLER);
            add(ModNatureBlocks.VIOLET_CAPS_TILLER);
            add(ModNatureBlocks.WHITE_MUSHROOM_TILLER);
            add(ModNatureBlocks.YELLOW_AMANITA_TILLER);
        }
    };
}
