package net.jesteur.me.datageneration.content.models;

import net.jesteur.me.block.ModBlockSets;
import net.jesteur.me.block.ModBlocks;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class SimpleStairModel {
    public record Stair(Block block, Block stairs) {}
    public static List<Stair> blocks = new ArrayList<>() {
        {
        }
    };
}
