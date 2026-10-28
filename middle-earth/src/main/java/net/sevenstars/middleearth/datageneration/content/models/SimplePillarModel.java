package net.sevenstars.middleearth.datageneration.content.models;

import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class SimplePillarModel {
    public record Pillar(Block base) {}

    public record StonePillar(Block base, Block origin) {}

    public static List<Pillar> blocks = new ArrayList<>() {
        {
        }
    };

    public static List<StonePillar> stonePillars = new ArrayList<>() {
        {

        }
    };

    public static List<StonePillar> carvedWindows = new ArrayList<>() {
        {

        }
    };
}
