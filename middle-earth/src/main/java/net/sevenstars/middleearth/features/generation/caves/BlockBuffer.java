package net.sevenstars.middleearth.features.generation.caves;

import net.minecraft.block.BlockState;

// reads and writes the blocks of the chunk being generated, by world position
public interface BlockBuffer {
    BlockState get(int x, int y, int z);

    void put(int x, int y, int z, BlockState state);
}
