package net.sevenstars.middleearth.datageneration.content.models;

import net.sevenstars.middleearth.block.registration.ModBlocks;
import net.sevenstars.middleearth.block.registration.StoneBlockSets;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import java.util.ArrayList;
import java.util.List;

public class SimplePillarModel {
    public record Pillar(Block base) {}

    public record StonePillar(Block base, Block origin) {}

    public static List<Pillar> blocks = new ArrayList<>() {
        {
            add(new Pillar(ModBlocks.REED_BLOCK));
            add(new Pillar(ModBlocks.STRAW_BLOCK));
        }
    };

    public static List<StonePillar> stonePillars = new ArrayList<>() {
        {

        }
    };
}
