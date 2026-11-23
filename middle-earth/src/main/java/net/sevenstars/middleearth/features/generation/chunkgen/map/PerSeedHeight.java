package net.sevenstars.middleearth.features.generation.chunkgen.map;

// A height worked out once per world seed, again when the seed changes
final class PerSeedHeight {
    interface Compute {
        float height();
    }

    private record Known(long seed, float height) {
    }

    private final Compute compute;
    private volatile Known known;

    PerSeedHeight(Compute compute) {
        this.compute = compute;
    }

    float get() {
        long seed = MiddleEarthHeightMap.getSeed();
        Known current = known;
        if (current == null || current.seed != seed) {
            current = new Known(seed, compute.height());
            known = current;
        }
        return current.height;
    }
}
