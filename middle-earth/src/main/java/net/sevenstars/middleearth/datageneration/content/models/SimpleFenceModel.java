package net.sevenstars.middleearth.datageneration.content.models;

import net.minecraft.world.level.block.Block;
import net.sevenstars.middleearth.block.registration.ModBlocks;
import java.util.ArrayList;
import java.util.List;

public class SimpleFenceModel {
    public record Fence(Block block, Block fence) {}
    public static List<Fence> blocks = new ArrayList<>() {
        {
        }
    };

    public static List<Fence> strippedFences = new ArrayList<>() {
        {
        }
    };

    public static List<Fence> vanillaStrippedFences = new ArrayList<>() {
        {
        }
    };

    public static List<Fence> vanillaWoodFences = new ArrayList<>() {
        {
        }
    };
}
