package net.sevenstars.middleearth.features.generation.caves;

// inclusive block bounds
record Box(int minX, int maxX, int minY, int maxY, int minZ, int maxZ) {
    static Box around(float x, float z, float reach, int minY, int maxY) {
        return new Box((int) Math.floor(x - reach), (int) Math.ceil(x + reach), minY, maxY, (int) Math.floor(z - reach), (int) Math.ceil(z + reach));
    }

    boolean contains(int x, int y, int z) {
        return y >= minY && y <= maxY && containsColumn(x, z);
    }

    boolean containsColumn(int x, int z) {
        return x >= minX && x <= maxX && z >= minZ && z <= maxZ;
    }

    boolean touchesChunk(int startX, int startZ) {
        return maxX >= startX && minX <= startX + 15 && maxZ >= startZ && minZ <= startZ + 15;
    }
}
