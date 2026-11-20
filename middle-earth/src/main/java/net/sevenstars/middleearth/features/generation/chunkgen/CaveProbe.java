package net.sevenstars.middleearth.features.generation.chunkgen;

import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.HeightLimitView;
import net.sevenstars.middleearth.features.generation.biomes.caves.DepthBands;
import net.sevenstars.middleearth.features.generation.map.MiddleEarthMapRuntime;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

// answers what buildSurface would leave solid, for structure code; lives as long as one structure start and holds
// bounded caches only: contexts of the most recent chunks, whose cave noise is evaluated one corner at a time
public final class CaveProbe {
    private static final int MAX_CHUNKS = 128;
    private static final int MAX_FLOORS = 1 << 16;

    private final long seed;
    private final int bottomY;
    private final int topY;
    private final Map<Long, CaveSampler> samplers = new LinkedHashMap<>(16, 0.75f, true) {
        @Override
        protected boolean removeEldestEntry(Map.Entry<Long, CaveSampler> eldest) {
            return size() > MAX_CHUNKS;
        }
    };
    private final Map<Long, Integer> floors = new HashMap<>();

    public CaveProbe(long seed, HeightLimitView world) {
        this.seed = seed;
        this.bottomY = world.getBottomY();
        this.topY = world.getTopYInclusive();
    }

    public boolean solid(int x, int y, int z) {
        if (y < bottomY) return true;
        if (y > topY) return false;
        if (y < DepthBands.SHALLOW_FLOOR && y <= deepFloor(x, z)) return true;
        long chunkKey = ChunkPos.toLong(x >> 4, z >> 4);
        CaveSampler sampler = samplers.get(chunkKey);
        if (sampler == null) {
            sampler = new CaveSampler(x & ~15, z & ~15, bottomY, topY, seed, true);
            samplers.put(chunkKey, sampler);
        }
        return sampler.sample(x, y, z) == CaveSampler.STONE;
    }

    private int deepFloor(int x, int z) {
        if (floors.size() >= MAX_FLOORS) floors.clear();
        long key = ChunkPos.toLong(x, z);
        Integer known = floors.get(key);
        if (known != null) return known;
        int floor = DepthBands.deepFloor(MiddleEarthMapRuntime.getInstance(), x, z, seed);
        floors.put(key, floor);
        return floor;
    }
}
