package net.jesteur.me.datageneration.content.models;

import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class SimpleWallModel {
    public record Wall(Block block, Block wall) {}
    public static List<Wall> blocks = new ArrayList<>() {
        {
        }
    };
}
