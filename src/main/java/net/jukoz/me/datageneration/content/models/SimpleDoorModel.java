package net.jukoz.me.datageneration.content.models;

import net.jukoz.me.block.ModBlocks;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class SimpleDoorModel {
    public record Door(Block block, Block door) {}

    public static List<Door> blocks = new ArrayList<>() {
        {
        }
    };
}
