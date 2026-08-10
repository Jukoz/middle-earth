package net.sevenstars.middleearth.datageneration.content.models;

import net.sevenstars.middleearth.block.registration.ModDecorativeBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import java.util.ArrayList;
import java.util.List;

public class SimpleWoodChairModel {
    public record VanillaChair(Block base, Block planks) {}

    public static List<Block> chairs = new ArrayList<>() {
        {
            add(ModDecorativeBlocks.TREATED_WOOD_CHAIR);
        }
    };


    public static List<VanillaChair> vanillaChairs = new ArrayList<>() {
        {
        }
    };
}
