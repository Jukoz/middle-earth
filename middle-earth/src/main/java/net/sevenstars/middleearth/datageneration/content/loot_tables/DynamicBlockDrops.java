package net.sevenstars.middleearth.datageneration.content.loot_tables;

import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;

public final class DynamicBlockDrops {
    public static final List<Block> BLOCKS = new ArrayList<>();

    private DynamicBlockDrops() {
    }

    public static void add(Block block) {
        if (block != null) {
            BLOCKS.add(block);
        }
    }
}
