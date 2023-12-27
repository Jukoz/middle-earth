package net.jukoz.me.datageneration.content.models;

import net.jukoz.me.block.ModBlocks;
import net.jukoz.me.block.ModNatureBlocks;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class TintableCrossModel {
    public static List<Block> notTintedBlocks = new ArrayList<>() {
        {
            add(ModNatureBlocks.WILD_FLAX);
            add(ModNatureBlocks.WILD_BELL_PEPPER);
            add(ModNatureBlocks.WILD_CUCUMBER);
            add(ModNatureBlocks.WILD_LEEK);
            add(ModNatureBlocks.WILD_LETTUCE);
            add(ModNatureBlocks.WILD_GARLIC);
            add(ModNatureBlocks.WILD_ONION);

            add(ModNatureBlocks.BROWN_BOLETE);
            add(ModNatureBlocks.CAVE_AMANITA);
            add(ModNatureBlocks.DEEP_FIRECAP);
            add(ModNatureBlocks.GHOSTSHROOM);
            add(ModNatureBlocks.MORSEL);
            add(ModNatureBlocks.SKY_FIRECAP);
            add(ModNatureBlocks.TRUMPET_SHROOM);
            add(ModNatureBlocks.TUBESHRROM);
            add(ModNatureBlocks.VIOLET_CAPS);
            add(ModNatureBlocks.WHITE_MUSHROOM);
            add(ModNatureBlocks.YELLOW_AMANITA);

            add(ModNatureBlocks.GLOWWORM_MAIN);
            add(ModNatureBlocks.GLOWWORM_WEBBING);
        }
    };
    public static List<Block> tintedBlocks = new ArrayList<>() {

    };
}
