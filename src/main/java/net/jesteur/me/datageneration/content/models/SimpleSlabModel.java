package net.jesteur.me.datageneration.content.models;

import net.jesteur.me.block.ModBlockSets;
import net.jesteur.me.block.ModBlocks;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class SimpleSlabModel {
    public record Slab(Block block, Block slab) {}
    public static List<Slab> blocks = new ArrayList<>() {
        {
        }
    };
}