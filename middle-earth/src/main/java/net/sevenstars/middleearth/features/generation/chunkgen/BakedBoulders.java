package net.sevenstars.middleearth.features.generation.chunkgen;

import net.minecraft.block.BlockState;
import net.sevenstars.middleearth.MiddleEarth;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.structure.StructurePiece;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeCoords;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.placementmodifier.CountPlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import net.sevenstars.middleearth.features.generation.chunkgen.map.MiddleEarthHeightMap;
import net.sevenstars.middleearth.features.generation.map.MiddleEarthMapRuntime;
import net.sevenstars.middleearth.features.generation.worldfeatures.boulder.BigBoulderFeatureConfig;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// the boulders of the biomes' big_boulder features, written with the terrain instead of placed after it: every boulder
// comes from a hash of its origin chunk, so neighbouring chunks build the same boulder without reading each other,
// and the blocks of structures are kept clear without asking which structures a feature could still see
final class BakedBoulders {
    private static final int MAX_SLOTS = 16; // boulder features a biome may list, each rolled in turn like a placed feature; the most now is 9
    private static final int SURFACE_BIOME_Y = 320;
    private static final int STRUCTURE_CLEARANCE = 2;
    private static final int RATE_SAMPLES = 4096;

    private record Kind(BigBoulderFeatureConfig config, float rate) {
    }

    private record Boulder(BigBoulderFeatureConfig config, int x, int y, int z, float length, float width, float height,
                           float cos, float sin, long hash) {
    }

    private static final Map<RegistryKey<Biome>, Kind[]> KINDS = new ConcurrentHashMap<>();
    private static final Kind[] NONE = new Kind[0];

    private final List<Boulder> boulders = new ArrayList<>();
    private final List<BlockBox> clear = new ArrayList<>();
    private final long seed;

    // the boulders reaching into a chunk, from origins in it and its neighbours
    BakedBoulders(BiomeSource biomes, int chunkX, int chunkZ, long seed, List<StructureStart> structureStarts) {
        this.seed = seed;
        for (int originX = chunkX - 1; originX <= chunkX + 1; originX++) {
            for (int originZ = chunkZ - 1; originZ <= chunkZ + 1; originZ++) {
                collect(biomes, originX, originZ, chunkX, chunkZ);
            }
        }
        if (boulders.isEmpty()) return;
        for (StructureStart start : structureStarts) {
            for (StructurePiece piece : start.getChildren()) clear.add(piece.getBoundingBox().expand(STRUCTURE_CLEARANCE));
        }
    }

    boolean isEmpty() {
        return boulders.isEmpty();
    }

    private void collect(BiomeSource biomes, int originX, int originZ, int chunkX, int chunkZ) {
        for (int slot = 0; slot < MAX_SLOTS; slot++) {
            long hash = hash(seed, originX, originZ, slot);
            int x = (originX << 4) + (int) (hash & 15);
            int z = (originZ << 4) + (int) ((hash >>> 4) & 15);
            // the surface biome, which every height above the ground gives; the height itself only once a boulder is rolled
            Kind[] kinds = kinds(biomes.getBiome(BiomeCoords.fromBlock(x), BiomeCoords.fromBlock(SURFACE_BIOME_Y), BiomeCoords.fromBlock(z), null));
            if (slot >= kinds.length) continue;
            Kind kind = kinds[slot];
            if (unit(hash, 1) >= kind.rate) continue;

            BigBoulderFeatureConfig config = kind.config;
            float length = (float) Math.ceil(config.length) + unit(hash, 2) * config.randomSize;
            float width = (float) Math.ceil(config.width) + unit(hash, 3) * config.randomSize;
            float height = config.height + unit(hash, 4) * config.randomSize;
            float reach = Math.max(length, width) + config.randomness;
            if (x + reach < chunkX << 4 || x - reach > (chunkX << 4) + 15 || z + reach < chunkZ << 4 || z - reach > (chunkZ << 4) + 15) continue;
            int surfaceY = (int) (MiddleEarthChunkGenerator.DIRT_HEIGHT + MiddleEarthHeightMap.getHeight(x, z));
            // a feature on water found nothing to stand on
            if (surfaceY < MiddleEarthMapRuntime.getInstance().getBiome(x, z).getWaterHeight()) continue;
            double angle = Math.toRadians(unit(hash, 5) * 180);
            boulders.add(new Boulder(config, x, surfaceY + 1, z, length, width, height, (float) Math.cos(angle), (float) Math.sin(angle), hash));
        }
    }

    // the blocks of the boulders in one column, over whatever the column holds
    void write(ColumnWriter out, int localX, int localZ, int x, int z) {
        for (Boulder boulder : boulders) {
            float dx = x - boulder.x;
            float dz = z - boulder.z;
            float alongMax = boulder.length + boulder.config.randomness;
            float acrossMax = boulder.width + boulder.config.randomness;
            float along = dx * boulder.cos - dz * boulder.sin;
            float across = dx * boulder.sin + dz * boulder.cos;
            if (along * along / (alongMax * alongMax) + across * across / (acrossMax * acrossMax) > 1) continue;
            int top = (int) Math.ceil(boulder.height + boulder.config.randomness);
            for (int dy = -top; dy <= top; dy++) {
                int y = boulder.y + dy;
                if (out.outOfHeightLimit(y)) continue;
                long blockHash = hash(boulder.hash, x, y, z);
                // the rough surface of the feature: each block sees the shape a little bigger or smaller
                float rough = (unit(blockHash, 0) * 2 - 1) * boulder.config.randomness;
                float l = boulder.length + rough;
                float w = boulder.width + rough;
                float h = boulder.height + rough;
                if (along * along / (l * l) + dy * dy / (h * h) + across * across / (w * w) > 1) continue;
                if (cleared(x, y, z)) continue;
                List<BlockState> states = boulder.config.blockStates;
                out.set(localX, y, localZ, states.get((int) (unit(blockHash, 1) * states.size())));
            }
        }
    }

    private boolean cleared(int x, int y, int z) {
        for (BlockBox box : clear) {
            if (box.contains(x, y, z)) return true;
        }
        return false;
    }

    // the big_boulder features a biome lists, each with how many it places per chunk on average
    private static Kind[] kinds(RegistryEntry<Biome> biome) {
        RegistryKey<Biome> key = biome.getKey().orElse(null);
        if (key == null) return NONE;
        return KINDS.computeIfAbsent(key, k -> read(biome.value()));
    }

    private static Kind[] read(Biome biome) {
        List<Kind> kinds = new ArrayList<>();
        for (RegistryEntryList<PlacedFeature> step : biome.getGenerationSettings().getFeatures()) {
            for (RegistryEntry<PlacedFeature> entry : step) {
                PlacedFeature placed = entry.value();
                ConfiguredFeature<?, ?> configured = placed.feature().value();
                if (!(configured.config() instanceof BigBoulderFeatureConfig config)) continue;
                kinds.add(new Kind(config, rate(placed)));
            }
        }
        if (kinds.size() > MAX_SLOTS) MiddleEarth.LOGGER.logError("A biome lists %s boulder features, only the first %s are placed".formatted(kinds.size(), MAX_SLOTS));
        return kinds.isEmpty() ? NONE : kinds.toArray(NONE);
    }

    private static float rate(PlacedFeature placed) {
        float rate = 1;
        for (PlacementModifier modifier : placed.placementModifiers()) {
            if (modifier instanceof CountPlacementModifier count) rate *= mean(countOf(count));
        }
        return rate;
    }

    private static float mean(IntProvider provider) {
        Random random = Random.create(0);
        long total = 0;
        for (int i = 0; i < RATE_SAMPLES; i++) total += provider.get(random);
        return (float) total / RATE_SAMPLES;
    }

    // the count is private and its name differs between mappings, so it is found by its type
    private static IntProvider countOf(CountPlacementModifier modifier) {
        for (Field field : CountPlacementModifier.class.getDeclaredFields()) {
            if (!IntProvider.class.isAssignableFrom(field.getType())) continue;
            try {
                field.setAccessible(true);
                return (IntProvider) field.get(modifier);
            } catch (IllegalAccessException exception) {
                throw new IllegalStateException(exception);
            }
        }
        throw new IllegalStateException("no count in " + modifier);
    }

    private static long hash(long seed, long a, long b, long c) {
        long h = seed ^ 0x9e3779b97f4a7c15L;
        h = mix(h + a * 0xbf58476d1ce4e5b9L);
        h = mix(h + b * 0x94d049bb133111ebL);
        return mix(h + c * 0x2545f4914f6cdd1dL);
    }

    private static long mix(long h) {
        h ^= h >>> 30;
        h *= 0xbf58476d1ce4e5b9L;
        h ^= h >>> 27;
        h *= 0x94d049bb133111ebL;
        return h ^ (h >>> 31);
    }

    // a value in [0, 1) from one of several draws of a hash
    private static float unit(long hash, int draw) {
        return (mix(hash + draw * 0x632be59bd9b4e019L) >>> 40) / (float) (1L << 24);
    }
}
