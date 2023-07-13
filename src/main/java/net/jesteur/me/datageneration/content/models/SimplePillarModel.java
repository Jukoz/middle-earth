package net.jesteur.me.datageneration.content.models;

import net.jesteur.me.block.ModBlocks;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class SimplePillarModel {
    public record Pillar(Block base) {}

    public static List<Pillar> blocks = new ArrayList<>() {
        {
            add(new Pillar(ModBlocks.CALCITE_PILLAR));
        }
    };
}
