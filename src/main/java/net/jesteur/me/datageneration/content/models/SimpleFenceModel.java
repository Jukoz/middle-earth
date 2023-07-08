package net.jesteur.me.datageneration.content.models;

import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class SimpleFenceModel {
    public record Fence(Block block, Block fence) {}
    public static List<Fence> blocks = new ArrayList<>() {
        {
        }
    };
}
