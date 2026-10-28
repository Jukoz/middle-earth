package net.sevenstars.middleearth.datageneration.content.models;

import net.sevenstars.middleearth.block.registration.BlockRegistryME;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import java.util.ArrayList;
import java.util.List;

public class SimpleLayersModel {
    public record Layers(Block origin, Block layers) {}
    public static List<Layers> layers = new ArrayList<>() {
        {
            add(new Layers(BlockRegistryME.BLACK_SAND, BlockRegistryME.BLACK_SAND_LAYER));
            add(new Layers(BlockRegistryME.WHITE_SAND, BlockRegistryME.WHITE_SAND_LAYER));
            add(new Layers(BlockRegistryME.ASHEN_SAND, BlockRegistryME.ASHEN_SAND_LAYER));
            add(new Layers(BlockRegistryME.ASHEN_GRAVEL, BlockRegistryME.ASHEN_GRAVEL_LAYER));
            add(new Layers(BlockRegistryME.SKELETAL_PILE, BlockRegistryME.SKELETAL_PILE_LAYER));
            add(new Layers(BlockRegistryME.WASTE_PILE, BlockRegistryME.WASTE_PILE_LAYER));
        }
    };

    public static List<Layers> vanillaLayers = new ArrayList<>() {
        {
            add(new Layers(Blocks.GRAVEL, BlockRegistryME.GRAVEL_LAYER));
            add(new Layers(Blocks.SAND, BlockRegistryME.SAND_LAYER));
        }
    };
}
