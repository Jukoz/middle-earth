package net.sevenstars.middleearth.datageneration.content.models;

import net.sevenstars.middleearth.block.registration.DecorativeBlockRegistryME;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class SimpleWoodChairModel {
    public record VanillaChair(Block base, Block planks) {}

    public static List<Block> chairs = new ArrayList<>() {
        {
            add(DecorativeBlockRegistryME.TREATED_WOOD_CHAIR);
        }
    };


    public static List<VanillaChair> vanillaChairs = new ArrayList<>() {
        {
        }
    };
}
