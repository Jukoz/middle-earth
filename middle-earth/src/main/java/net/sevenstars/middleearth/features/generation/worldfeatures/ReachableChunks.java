package net.sevenstars.middleearth.features.generation.worldfeatures;

import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkStatus;

public final class ReachableChunks {
    private ReachableChunks() {
    }

    // null when the generation region does not reach that chunk at that status; it throws instead of answering
    public static Chunk get(StructureWorldAccess world, int chunkX, int chunkZ, ChunkStatus status) {
        if (!world.isChunkLoaded(chunkX, chunkZ)) return null;
        try {
            return world.getChunk(chunkX, chunkZ, status, false);
        } catch (RuntimeException exception) {
            return null;
        }
    }
}
