package net.sevenstars.middleearth.features.generation.caves;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.LongFunction;

// results per grid cell, absent ones included, cleared when it fills or the world seed changes
final class CellCache<T> {
    private static final Object NONE = new Object();

    private final Map<Long, Object> cells = new ConcurrentHashMap<>();
    private final int limit;
    private volatile long seed = Long.MIN_VALUE;

    CellCache(int limit) {
        this.limit = limit;
    }

    static long key(int x, int z) {
        return (long) x << 32 | (z & 0xFFFFFFFFL);
    }

    void forSeed(long seed) {
        if (this.seed != seed) {
            cells.clear();
            this.seed = seed;
        }
    }

    // not computeIfAbsent: making a value may look up other cells of the same cache
    @SuppressWarnings("unchecked")
    T get(long key, LongFunction<T> make) {
        Object cached = cells.get(key);
        if (cached == null) {
            T made = make.apply(key);
            cached = made == null ? NONE : made;
            if (cells.size() >= limit) cells.clear();
            cells.put(key, cached);
        }
        return cached == NONE ? null : (T) cached;
    }
}
