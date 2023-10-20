package net.jukoz.me.datageneration.content.models;

import net.jukoz.me.block.ModBlocks;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class SimpleWallModel {
    public record Wall(Block block, Block wall) {}
    public static List<Wall> blocks = new ArrayList<>() {
        {
            add(new Wall(ModBlocks.REED_BLOCK, ModBlocks.REED_WALL));
            add(new Wall(ModBlocks.STRAW_BLOCK, ModBlocks.STRAW_WALL));
        }
    };
}
