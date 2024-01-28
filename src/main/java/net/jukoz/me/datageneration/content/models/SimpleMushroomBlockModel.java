package net.jukoz.me.datageneration.content.models;

import net.jukoz.me.block.ModNatureBlocks;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class SimpleMushroomBlockModel {
    public static List<Block> mushroomBlocks = new ArrayList<>() {
        {
            add(ModNatureBlocks.BROWN_BOLETE_BLOCK);
            add(ModNatureBlocks.CAVE_AMANITA_BLOCK);
            add(ModNatureBlocks.DEEP_FIRECAP_BLOCK);
            add(ModNatureBlocks.SKY_FIRECAP_BLOCK);
            add(ModNatureBlocks.YELLOW_AMANITA_BLOCK);
        }
    };
}
