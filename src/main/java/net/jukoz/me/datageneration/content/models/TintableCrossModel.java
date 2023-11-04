package net.jukoz.me.datageneration.content.models;

import com.mojang.datafixers.util.Pair;
import net.jukoz.me.block.ModNatureBlocks;
import net.minecraft.block.Block;
import net.minecraft.data.client.BlockStateModelGenerator;

import java.util.ArrayList;
import java.util.List;

public class TintableCrossModel {
    public static List<Block> notTintedBlocks = new ArrayList<>() {
        {
            add(ModNatureBlocks.WILD_PIPEWEED);
            add(ModNatureBlocks.WILD_FLAX);
            add(ModNatureBlocks.WILD_TOMATO);
            add(ModNatureBlocks.WILD_BELL_PEPPER);
            add(ModNatureBlocks.WILD_CUCUMBER);
            add(ModNatureBlocks.WILD_LEEK);
            add(ModNatureBlocks.WILD_LETTUCE);
            add(ModNatureBlocks.WILD_GARLIC);
            add(ModNatureBlocks.WILD_ONION);
        }
    };
    public static List<Block> tintedBlocks = new ArrayList<>() {

    };
}
