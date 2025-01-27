package net.sevenstars.middleearth.datageneration.content.models;

import net.sevenstars.middleearth.block.ModNatureBlocks;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class SimpleFanModel {
    public static List<Block> grassLikeFans = new ArrayList<>()
    {
        {
            add(ModNatureBlocks.HOROKAKA);
            add(ModNatureBlocks.GIANT_HOROKAKA);
        }
    };
}
