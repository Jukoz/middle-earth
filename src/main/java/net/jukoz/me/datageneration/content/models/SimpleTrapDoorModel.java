package net.jukoz.me.datageneration.content.models;

import net.jukoz.me.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import java.util.ArrayList;
import java.util.List;

public class SimpleTrapDoorModel {
    public record Trapdoor(Block block, Block trapdoor) {}

    public static List<Trapdoor> blocks = new ArrayList<>() {
        {
            add(new Trapdoor(Blocks.STONE, ModBlocks.STONE_TRAPDOOR));
            add(new Trapdoor(Blocks.BLACKSTONE, ModBlocks.BLACKSTONE_TRAPDOOR));
        }
    };
}
