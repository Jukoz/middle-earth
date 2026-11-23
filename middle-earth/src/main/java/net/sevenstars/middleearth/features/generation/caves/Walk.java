package net.sevenstars.middleearth.features.generation.caves;

import it.unimi.dsi.fastutil.longs.LongArrayFIFOQueue;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;

// whether a player can walk through carved air from one spot to another, stepping at most one block up or down
final class Walk {
    interface Carving {
        boolean at(int x, int y, int z);
    }

    private static final int[][] STEPS = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    private static final int BITS = 21;
    private static final long MASK = (1L << BITS) - 1;
    private static final int OFFSET = 1 << (BITS - 1);

    private Walk() {
    }

    // reaches within 2 blocks of the goal, without straying further than range sideways from the start
    static boolean reaches(Carving air, int sx, int sy, int sz, int gx, int gy, int gz, int range) {
        LongArrayFIFOQueue queue = new LongArrayFIFOQueue();
        LongOpenHashSet seen = new LongOpenHashSet();
        long start = pack(sx, sy, sz);
        queue.enqueue(start);
        seen.add(start);
        while (!queue.isEmpty()) {
            long p = queue.dequeueLong();
            int x = unpackX(p);
            int y = unpackY(p);
            int z = unpackZ(p);
            if (Math.abs(x - gx) <= 2 && Math.abs(z - gz) <= 2 && Math.abs(y - gy) <= 2) return true;
            for (int[] step : STEPS) {
                int nx = x + step[0];
                int nz = z + step[1];
                if (Math.abs(nx - sx) > range || Math.abs(nz - sz) > range) continue;
                for (int ny = y - 1; ny <= y + 1; ny++) {
                    if (!air.at(nx, ny, nz) || !air.at(nx, ny + 1, nz) || air.at(nx, ny - 1, nz)) continue;
                    if (ny > y && !air.at(x, y + 2, z)) continue;
                    if (ny < y && !air.at(nx, y + 1, nz)) continue;
                    long next = pack(nx, ny, nz);
                    if (seen.add(next)) queue.enqueue(next);
                }
            }
        }
        return false;
    }

    private static long pack(int x, int y, int z) {
        return ((x + OFFSET) & MASK) << (2 * BITS) | ((y + OFFSET) & MASK) << BITS | ((z + OFFSET) & MASK);
    }

    private static int unpackX(long p) {
        return (int) ((p >>> (2 * BITS)) & MASK) - OFFSET;
    }

    private static int unpackY(long p) {
        return (int) ((p >>> BITS) & MASK) - OFFSET;
    }

    private static int unpackZ(long p) {
        return (int) (p & MASK) - OFFSET;
    }
}
