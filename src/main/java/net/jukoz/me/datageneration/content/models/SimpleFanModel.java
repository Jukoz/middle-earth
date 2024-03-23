package net.jukoz.me.datageneration.content.models;

import net.jukoz.me.block.ModNatureBlocks;
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
