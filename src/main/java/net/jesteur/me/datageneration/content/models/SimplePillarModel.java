package net.jesteur.me.datageneration.content.models;

import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class SimplePillarModel {
    public record Pillar(Block base, Block pillar) {}

    public static List<Pillar> blocks = new ArrayList<>();
}
