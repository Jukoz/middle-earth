package net.jukoz.me.datageneration.content.models;

import net.jukoz.me.block.ModBlocks;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class SimpleStairModel {
    public record Stair(Block block, Block stairs) {}
    public static List<Stair> blocks = new ArrayList<>() {
        {
            add(new Stair(ModBlocks.REED_BLOCK, ModBlocks.REED_STAIRS));
            add(new Stair(ModBlocks.STRAW_BLOCK, ModBlocks.STRAW_STAIRS));
        }
    };
}
