package net.jesteur.me.datageneration.content.models;

import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class SimplePillarModel {
    public record Pillar(Block block) {}

    public static List<Pillar> blocks = new ArrayList<>();
}
