package net.jesteur.me.datageneration.content.models;

import net.jesteur.me.block.ModBlocks;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class SimpleWallModel {
    public record Wall(Block block, Block wall) {}
    public static List<Wall> blocks = new ArrayList<>() {
        {
            add(new Wall(ModBlocks.REEDS_BLOCK, ModBlocks.REEDS_WALL));
            add(new Wall(ModBlocks.STRAW_BLOCK, ModBlocks.STRAW_WALL));
        }
    };
}
