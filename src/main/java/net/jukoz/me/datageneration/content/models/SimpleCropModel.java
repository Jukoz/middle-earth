package net.jukoz.me.datageneration.content.models;

import net.jukoz.me.block.ModNatureBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.CropBlock;

import java.util.ArrayList;
import java.util.List;

public class SimpleCropModel {
    public static List<Block> blocks = new ArrayList<>() {
        {
            add(ModNatureBlocks.TOMATO_CROP);
        }
    };
}
