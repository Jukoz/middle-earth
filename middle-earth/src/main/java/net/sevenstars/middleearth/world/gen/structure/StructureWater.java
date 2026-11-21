package net.sevenstars.middleearth.world.gen.structure;

import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.structure.StructurePiece;
import net.minecraft.structure.StructurePiecesCollector;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.structure.JigsawStructure;
import net.minecraft.world.gen.structure.Structure;
import net.sevenstars.middleearth.MiddleEarth;

// Keeps the mod's structures off lakes, checked against the bare terrain before anything is placed
public final class StructureWater {
    // In blocks. A stream is fine to cross, a lake isn't
    private static final int MAXIMUM_WATER_DEPTH = 2;
    private static final int SAMPLE_STEP = 8;
    // This far below ground is a cellar or a hall, which water above doesn't reach
    private static final int BURIED_DEPTH = 4;

    private StructureWater() {}

    public static boolean applies(JigsawStructure structure) {
        return structure.getStartPool().getKey().map(key -> key.getValue().getNamespace().equals(MiddleEarth.MOD_ID)).orElse(false);
    }

    public static boolean isFlooded(Structure.Context context, StructurePiecesCollector pieces) {
        LongSet sampled = new LongOpenHashSet();
        for(StructurePiece piece : pieces.toList().pieces()) {
            if(isFlooded(context, piece.getBoundingBox(), sampled))
                return true;
        }
        return false;
    }

    private static boolean isFlooded(Structure.Context context, BlockBox box, LongSet sampled) {
        for(int x = box.getMinX(); x <= box.getMaxX(); x = nextSample(x, box.getMaxX())) {
            for(int z = box.getMinZ(); z <= box.getMaxZ(); z = nextSample(z, box.getMaxZ())) {
                if(!sampled.add(ChunkPos.toLong(x, z)))
                    continue;
                int ground = heightAt(context, x, z, Heightmap.Type.OCEAN_FLOOR_WG);
                if(box.getMaxY() < ground - BURIED_DEPTH)
                    continue;
                if(heightAt(context, x, z, Heightmap.Type.WORLD_SURFACE_WG) - ground > MAXIMUM_WATER_DEPTH)
                    return true;
            }
        }
        return false;
    }

    // Always ends on the box's far edge
    private static int nextSample(int at, int last) {
        if(at == last)
            return last + 1;
        return Math.min(at + SAMPLE_STEP, last);
    }

    private static int heightAt(Structure.Context context, int x, int z, Heightmap.Type type) {
        return context.chunkGenerator().getHeight(x, z, type, context.world(), context.noiseConfig());
    }
}
