package net.jukoz.me.datageneration.content.models;

import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class SimpleRocksModel {
    public record Rocks(Block block, Block rocks) {}
    public static List<Rocks> rocks = new ArrayList<>() {
        {
        }
    };
}
