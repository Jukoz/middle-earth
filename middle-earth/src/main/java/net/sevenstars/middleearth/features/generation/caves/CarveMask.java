package net.sevenstars.middleearth.features.generation.caves;

// a shape's carving worked out once over a box, so the many neighbour checks of a dressing pass are lookups
final class CarveMask implements Walk.Carving {
    private final Walk.Carving shape;
    private final int x0;
    private final int y0;
    private final int z0;
    private final int sizeX;
    private final int sizeY;
    private final int sizeZ;
    private final boolean[] air;

    // the box is inclusive; points outside it are asked of the shape
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

    @Override
    public boolean at(int x, int y, int z) {
        int dx = x - x0;
        int dy = y - y0;
        int dz = z - z0;
        if (dx < 0 || dy < 0 || dz < 0 || dx >= sizeX || dy >= sizeY || dz >= sizeZ) return shape.at(x, y, z);
        return air[(dx * sizeZ + dz) * sizeY + dy];
    }
}
