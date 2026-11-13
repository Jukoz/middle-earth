package net.sevenstars.middleearth.datageneration.content.models;

import net.sevenstars.middleearth.block.registration.DecorativeBlockRegistryME;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class SimpleWoodStoolModel {

    public record VanillaStool(Block base, Block planks) {}

    public static List<Block> stools = new ArrayList<>() {
        {
            add(DecorativeBlockRegistryME.TREATED_WOOD_STOOL);
        }
    };

    public static List<VanillaStool> vanillaStools = new ArrayList<>() {
        {
        }
    };
}
