package net.sevenstars.middleearth.datageneration.content.models;

import net.sevenstars.middleearth.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import java.util.ArrayList;
import java.util.List;

public class SimpleLayersModel {
    public record Layers(Block origin, Block layers) {}
    public static List<Layers> layers = new ArrayList<>() {
        {
            add(new Layers(ModBlocks.BLACK_SAND, ModBlocks.BLACK_SAND_LAYER));
            add(new Layers(ModBlocks.WHITE_SAND, ModBlocks.WHITE_SAND_LAYER));
            add(new Layers(ModBlocks.ASHEN_SAND, ModBlocks.ASHEN_SAND_LAYER));
            add(new Layers(ModBlocks.ASHEN_GRAVEL, ModBlocks.ASHEN_GRAVEL_LAYER));
        }
    };

    public static List<Layers> vanillaLayers = new ArrayList<>() {
        {
            add(new Layers(Blocks.GRAVEL, ModBlocks.GRAVEL_LAYER));
            add(new Layers(Blocks.SAND, ModBlocks.SAND_LAYER));
        }
    };
}
