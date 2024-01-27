package net.jukoz.me.datageneration.content.models;

import net.jukoz.me.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import java.util.ArrayList;
import java.util.List;

public class SimpleLayersModel {
    public record Layers(Block origin, Block layers) {}
    public static List<SimpleLayersModel.Layers> layers = new ArrayList<>() {
        {
        }
    };

    public static List<SimpleLayersModel.Layers> vanillaLayers = new ArrayList<>() {
        {
            add(new SimpleLayersModel.Layers(Blocks.GRAVEL, ModBlocks.GRAVEL_LAYER));
            add(new SimpleLayersModel.Layers(Blocks.SAND, ModBlocks.SAND_LAYER));
        }
    };
}
