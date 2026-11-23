package net.sevenstars.middleearth.features.generation.caves;

import java.util.ArrayList;
import java.util.List;

// a shape's carving worked out once over a box, so the many neighbour checks of a dressing pass are lookups
final class CarveMask implements Walk.Carving {
    static final int[][] SIDES = {{1, 0, 0}, {-1, 0, 0}, {0, 1, 0}, {0, -1, 0}, {0, 0, 1}, {0, 0, -1}};

    private final Walk.Carving shape;
    private final int x0;
    private final int y0;
    private final int z0;
    private final int sizeX;
    private final int sizeY;
    private final int sizeZ;
    private final boolean[] air;

    // the box is inclusive, points outside it are asked of the shape
    CarveMask(Walk.Carving shape, int minX, int maxX, int minY, int maxY, int minZ, int maxZ) {
        this.shape = shape;
        this.x0 = minX;
        this.y0 = minY;
        this.z0 = minZ;
        this.sizeX = Math.max(0, maxX - minX + 1);
        this.sizeY = Math.max(0, maxY - minY + 1);
        this.sizeZ = Math.max(0, maxZ - minZ + 1);
        this.air = new boolean[sizeX * sizeY * sizeZ];
        for (int x = 0; x < sizeX; x++) {
            for (int z = 0; z < sizeZ; z++) {
                for (int y = 0; y < sizeY; y++) air[(x * sizeZ + z) * sizeY + y] = shape.at(x0 + x, y0 + y, z0 + z);
            }
        }
    }

    // lumps of rock of at most `limit` blocks with carving all round are counted as carved, they depend only on
    // the shape, so neighbouring chunks agree
    void clearIslands(int limit) {
        boolean[] loose = new boolean[air.length];
        int[][] lump = new int[limit + 1][];
        for (int x = 0; x < sizeX; x++) {
            for (int z = 0; z < sizeZ; z++) {
                for (int y = 0; y < sizeY; y++) {
                    int i = (x * sizeZ + z) * sizeY + y;
                    if (air[i] || loose[i] || !touchesAir(x0 + x, y0 + y, z0 + z)) continue;
                    int size = lump(x0 + x, y0 + y, z0 + z, limit, lump);
                    if (size > limit) continue;
                    for (int k = 0; k < size; k++) {
                        int dx = lump[k][0] - x0, dy = lump[k][1] - y0, dz = lump[k][2] - z0;
                        if (dx >= 0 && dy >= 0 && dz >= 0 && dx < sizeX && dy < sizeY && dz < sizeZ) loose[(dx * sizeZ + dz) * sizeY + dy] = true;
                    }
                }
            }
        }
        for (int i = 0; i < air.length; i++) air[i] |= loose[i];
    }

    private boolean touchesAir(int x, int y, int z) {
        for (int[] side : SIDES) {
            if (at(x + side[0], y + side[1], z + side[2])) return true;
        }
        return false;
    }

    // the rock joined to a block, given up once it passes the limit
    private int lump(int x, int y, int z, int limit, int[][] found) {
        int size = 0;
        found[size++] = new int[]{x, y, z};
        for (int head = 0; head < size; head++) {
            int[] p = found[head];
            for (int[] side : SIDES) {
                int nx = p[0] + side[0], ny = p[1] + side[1], nz = p[2] + side[2];
                if (at(nx, ny, nz) || contains(found, size, nx, ny, nz)) continue;
                if (size > limit) return size;
                found[size++] = new int[]{nx, ny, nz};
            }
        }
        return size;
    }

    private static boolean contains(int[][] found, int size, int x, int y, int z) {
        for (int k = 0; k < size; k++) {
            if (found[k][0] == x && found[k][1] == y && found[k][2] == z) return true;
        }
        return false;
    }

    @Override
    public boolean at(int x, int y, int z) {
        int dx = x - x0;
        int dy = y - y0;
        int dz = z - z0;
        if (dx < 0 || dy < 0 || dz < 0 || dx >= sizeX || dy >= sizeY || dz >= sizeZ) return shape.at(x, y, z);
        return air[(dx * sizeZ + dz) * sizeY + dy];
    }

    // carving within 2 blocks, counting each step along an axis
    boolean nearAir(int x, int y, int z) {
        for (int[] o : NEAR) {
            if (at(x + o[0], y + o[1], z + o[2])) return true;
        }
        return false;
    }

    private static final int[][] NEAR = near();

    private static int[][] near() {
        List<int[]> out = new ArrayList<>();
        for (int dx = -2; dx <= 2; dx++) {
            for (int dy = -2; dy <= 2; dy++) {
                for (int dz = -2; dz <= 2; dz++) {
                    if (Math.abs(dx) + Math.abs(dy) + Math.abs(dz) <= 2) out.add(new int[]{dx, dy, dz});
                }
            }
        }
        return out.toArray(new int[0][]);
    }
}
