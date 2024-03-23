package net.jukoz.me.datageneration.content.tags;

import net.jukoz.me.block.ModNatureBlocks;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class Crops {
    public static List<Block> crops = new ArrayList<>() {
        {
            add(ModNatureBlocks.TOMATO_CROP);
            add(ModNatureBlocks.BELL_PEPPER_CROP);
            add(ModNatureBlocks.CUCUMBER_CROP);
            add(ModNatureBlocks.FLAX_CROP);
            add(ModNatureBlocks.GARLIC_CROP);
            add(ModNatureBlocks.LEEK_CROP);
            add(ModNatureBlocks.LETTUCE_CROP);
            add(ModNatureBlocks.ONION_CROP);
            add(ModNatureBlocks.PIPEWEED_CROP);
        }
    };
}
