package net.sevenstars.middleearth.datageneration.content.models;

import net.sevenstars.middleearth.block.registration.ModDecorativeBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import java.util.ArrayList;
import java.util.List;

public class SimpleWoodStoolModel {

    public record VanillaStool(Block base, Block planks) {}

    public static List<Block> stools = new ArrayList<>() {
        {
            add(ModDecorativeBlocks.TREATED_WOOD_STOOL);
        }
    };

    public static List<VanillaStool> vanillaStools = new ArrayList<>() {
        {
        }
    };
}
