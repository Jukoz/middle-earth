package net.jesteur.me.datageneration.content.models;

import net.jesteur.me.block.ModBlocks;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class SimplePillarModel {
    public record Pillar(Block base) {}

    public static List<Pillar> blocks = new ArrayList<>() {
        {
            add(new Pillar(ModBlocks.LIMESTONE_PILLAR));
            add(new Pillar(ModBlocks.CALCITE_PILLAR));
            add(new Pillar(ModBlocks.REEDS_BLOCK));
            add(new Pillar(ModBlocks.STRAW_BLOCK));
        }
    };
}
