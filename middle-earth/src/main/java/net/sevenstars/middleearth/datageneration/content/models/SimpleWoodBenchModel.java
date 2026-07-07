package net.sevenstars.middleearth.datageneration.content.models;

import net.sevenstars.middleearth.block.registration.ModDecorativeBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import java.util.ArrayList;
import java.util.List;

public class SimpleWoodBenchModel {

    public record VanillaBench(Block base, Block planks) {}

    public static List<Block> benchs = new ArrayList<>() {
        {
            add(ModDecorativeBlocks.TREATED_WOOD_BENCH);
        }
    };

    public static List<VanillaBench> vanillaBenchs = new ArrayList<>() {
        {
        }
    };
}
