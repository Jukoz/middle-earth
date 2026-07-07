package net.sevenstars.middleearth.datageneration.content.models;

import net.sevenstars.middleearth.block.registration.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import java.util.ArrayList;
import java.util.List;

public class SimpleRocksModel {
    public record Rocks(Block block, Block rocks) {}
    public static List<Rocks> rocks = new ArrayList<>() {
        {
        }
    };

    public static List<Rocks> vanillaRocks = new ArrayList<>() {
        {

        }
    };
}
