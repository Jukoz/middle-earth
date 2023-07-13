package net.jesteur.me.datageneration.content.models;

import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class SimpleFenceGateModel {
    public record FenceGate(Block block, Block fenceGate) {}
    public static List<FenceGate> blocks = new ArrayList<>() {
        {
        }
    };
}
