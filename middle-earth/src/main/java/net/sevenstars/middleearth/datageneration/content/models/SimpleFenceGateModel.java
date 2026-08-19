package net.sevenstars.middleearth.datageneration.content.models;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.level.block.Block;

public class SimpleFenceGateModel {
    public record FenceGate(Block block, Block fenceGate) {}
    public static List<FenceGate> blocks = new ArrayList<>() {
        {
        }
    };
}
