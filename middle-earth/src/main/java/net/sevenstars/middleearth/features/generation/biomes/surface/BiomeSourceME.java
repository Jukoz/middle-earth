package net.sevenstars.middleearth.features.generation.biomes.surface;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.sevenstars.middleearth.features.generation.noise.BlendedNoise;
import net.sevenstars.middleearth.features.generation.noise.SimplexNoise;
import net.sevenstars.middleearth.features.generation.biomes.BiomeKeyRegistryME;
import net.sevenstars.middleearth.features.generation.biomes.caves.CaveBiomesME;
import net.sevenstars.middleearth.features.generation.biomes.caves.DepthBands;
import net.sevenstars.middleearth.features.generation.chunkgen.MiddleEarthChunkGenerator;
import net.sevenstars.middleearth.features.generation.chunkgen.ProceduralStructures;
import net.sevenstars.middleearth.features.generation.chunkgen.map.MiddleEarthHeightMap;
import net.sevenstars.middleearth.features.generation.map.MiddleEarthMapRuntime;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.Vec2f;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeCoords;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class BiomeSourceME extends BiomeSource {

    public static final MapCodec<BiomeSourceME> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
            Codec.list(Biome.REGISTRY_CODEC).fieldOf("biomes").forGetter((biomeSource) -> biomeSource.biomes)).apply(instance, BiomeSourceME::new));

    private final List<RegistryEntry<Biome>> biomes;
    private static final int CAVE_NOISE = 360;
    private static final int CAVE_OFFSET = 7220;
    public static final int SUB_BIOME_NOISE = 256;
    public static final int SUB_BIOME_OFFSET = 8240;
    private MiddleEarthMapRuntime middleEarthMapRuntime;
    private final Map<RegistryKey<Biome>, RegistryEntry<Biome>> entriesByKey = new HashMap<>();
    public BiomeSourceME(List<RegistryEntry<Biome>> biomes) {
        this.biomes = biomes;
        for (RegistryEntry<Biome> entry : biomes) {
            entry.getKey().ifPresent(key -> entriesByKey.putIfAbsent(key, entry));
        }
        middleEarthMapRuntime = MiddleEarthMapRuntime.getInstance();
    }

    @Override
    protected MapCodec<? extends BiomeSource> getCodec() {
        return CODEC;
    }

    @Override
    protected Stream<RegistryEntry<Biome>> biomeStream() {
        return biomes.stream();
    }

    private RegistryKey<Biome> getCaveBiome(int x, int z, BiomeData surfaceBiome, DepthBands.Band band) {
        x += MiddleEarthHeightMap.getSeed();
        z += MiddleEarthHeightMap.getSeed();
        float temperature = (float) SimplexNoise.noise((double) x / CAVE_NOISE,  (double) z / CAVE_NOISE);
        float humidity = (float) SimplexNoise.noise((double) (x + CAVE_OFFSET) / CAVE_NOISE, (double)(z + CAVE_OFFSET) / CAVE_NOISE);
        return CaveBiomesME.getBiome(new Vec2f(temperature, humidity), surfaceBiome, band);
    }

    public static double getSubBiomeNoise(int x, int z, float frequency) {
        x += MiddleEarthHeightMap.getSeed();
        z += MiddleEarthHeightMap.getSeed();
        float noiseFrequency = (SUB_BIOME_NOISE * frequency);
        double perlin = 1 * BlendedNoise.noise((double) x / noiseFrequency, (double) z / noiseFrequency);
        perlin += 0.5f * BlendedNoise.noise((double) x * 2 / noiseFrequency, (double) z * 2 / noiseFrequency);
        perlin = perlin / (1 + 0.5f); // 2 octaves
        return perlin;
    }

    private RegistryKey<Biome> getSubBiome(int x, int z, BiomeData surfaceBiome) {
        SubBiome subBiome = SubBiomes.getSubBiome(surfaceBiome.getBiomeRegistryKey());
        if(subBiome != null) {
            double perlin = getSubBiomeNoise(x, z, subBiome.getFrequency());
            SubBiome.SubBiomeData biomeData = SubBiomes.subBiomesMap.get(surfaceBiome.getBiomeRegistryKey()).getBiomeAtNoise((float) perlin);
            if (biomeData != null) return biomeData.biome;
        }
        return surfaceBiome.getBiomeRegistryKey();
    }

    @Override
    public RegistryEntry<Biome> getBiome(int x, int y, int z, MultiNoiseUtil.MultiNoiseSampler noise) {
        int i = BiomeCoords.toBlock(x);
        int j = BiomeCoords.toBlock(y);
        int k = BiomeCoords.toBlock(z);

        Column column = getColumn(i, k);
        if (column.mapBiome == null) {
            return biomes.get(0);
        }

        RegistryKey<Biome> processedBiome;
        if (j < column.caveBelow || j < column.marshCaveBelow) {
            DepthBands.Span span = DepthBands.at(j, column.edges);
            DepthBands.Band band = span == null ? DepthBands.Band.UPPER : span.band();
            RegistryKey<Biome> caveBiome = column.caveBiomes[band.ordinal()];
            // the deeps belong to every column with a floor below the usual one, whatever its cave type
            if (band == DepthBands.Band.DEEPS && column.edges.floor() < DepthBands.SHALLOW_FLOOR) caveBiome = column.caveBiomes[band.ordinal()] = BiomeKeyRegistryME.NAMELESS_DEEPS;
            if (caveBiome == null) caveBiome = column.caveBiomes[band.ordinal()] = getCaveBiome(i, k, column.mapBiome.getBiome(), band);
            processedBiome = caveBiome;
        } else {
            processedBiome = column.surfaceBiome;
        }
        return toEntry(processedBiome);
    }

    // Everything here only depends on x and z, and the game asks for every 4 blocks of height in a column
    private static final class Column {
        long seed;
        int x;
        int z;
        MapBasedCustomBiome mapBiome;
        DepthBands.Edges edges;
        float caveBelow;
        float marshCaveBelow = Float.NEGATIVE_INFINITY;
        RegistryKey<Biome> surfaceBiome;
        final RegistryKey<Biome>[] caveBiomes = newBandArray();
    }

    @SuppressWarnings("unchecked")
    private static RegistryKey<Biome>[] newBandArray() {
        return new RegistryKey[DepthBands.Band.values().length];
    }

    private static final int COLUMN_CACHE_SIZE = 256;
    private static final ThreadLocal<Column[]> columnCache = ThreadLocal.withInitial(() -> new Column[COLUMN_CACHE_SIZE]);

    private Column getColumn(int i, int k) {
        long seed = MiddleEarthHeightMap.getSeed();
        int slot = (((i >> 2) & 15) << 4) | ((k >> 2) & 15);
        Column[] cache = columnCache.get();
        Column column = cache[slot];
        if (column != null && column.x == i && column.z == k && column.seed == seed) return column;

        column = new Column();
        column.seed = seed;
        column.x = i;
        column.z = k;
        fillColumn(column, i, k);
        cache[slot] = column;
        return column;
    }

    private void fillColumn(Column column, int i, int k) {
        MapBasedCustomBiome biomeHeightData = middleEarthMapRuntime.getBiome(i, k);
        column.mapBiome = biomeHeightData;
        if (biomeHeightData == null) return;

        BiomeData biome = biomeHeightData.getBiome();

        float height = MiddleEarthChunkGenerator.DIRT_HEIGHT + MiddleEarthHeightMap.getCachedHeight(i, k);
        column.caveBelow = height - 16;
        long seed = MiddleEarthHeightMap.getSeed();
        column.edges = DepthBands.Edges.of((int) height, DepthBands.deepFloor(middleEarthMapRuntime, i, k, seed), MiddleEarthChunkGenerator.caveBlendNoise(i, k, seed));

        if(!MapBasedBiomePool.waterBiomes.contains(biome.getBiomeRegistryKey())) {
            RegistryKey<Biome> biomeRegistryKey = biome.getBiomeRegistryKey();
            if(biomeRegistryKey == MapBasedBiomePool.deadMarshes.getBiomeKey() || biomeRegistryKey == MapBasedBiomePool.deadMarshesWater.getBiomeKey()) {
                column.marshCaveBelow = height - 20;
                if(height < MiddleEarthChunkGenerator.WATER_HEIGHT) column.surfaceBiome = MapBasedBiomePool.deadMarshesWater.getBiomeKey();
                else column.surfaceBiome = MapBasedBiomePool.deadMarshes.getBiomeKey();
            } else if(height <= biomeHeightData.getWaterHeight() + 1.25f) { // TODO : This is really rough, need to be more dynamic
                if(MapBasedBiomePool.coastalBiomes.contains(biomeRegistryKey)){
                    column.surfaceBiome = MapBasedBiomePool.oceanCoast.getBiomeKey();
                } else if(MapBasedBiomePool.wastePondBiomes.contains(biomeRegistryKey)) {
                    column.surfaceBiome = MapBasedBiomePool.wastePond.getBiomeKey();
                } else if(MapBasedBiomePool.mirkwoodSwampBiomes.contains(biomeRegistryKey)) {
                    column.surfaceBiome = MapBasedBiomePool.mirkwoodSwamp.getBiomeKey();
                } else if(MapBasedBiomePool.oasisBiomes.contains(biomeRegistryKey)) {
                    column.surfaceBiome = MapBasedBiomePool.oasis.getBiomeKey();
                } else if(MapBasedBiomePool.frozenBiomes.contains(biomeRegistryKey)) {
                    column.surfaceBiome = MapBasedBiomePool.frozenPond.getBiomeKey();
                } else if(MapBasedBiomePool.anduinWaterBiomes.contains(biomeRegistryKey)){
                    column.surfaceBiome = MapBasedBiomePool.greatRiver.getBiomeKey();
                } else if(MapBasedBiomePool.mangrovePondBiomes.contains(biomeRegistryKey)){
                    column.surfaceBiome = MapBasedBiomePool.mangrovePond.getBiomeKey();
                } else {
                    column.surfaceBiome = MapBasedBiomePool.pond.getBiomeKey();
                }
            } else if(biome.getBiomeRegistryKey().isOf(BiomeKeyRegistryME.NAN_CURUNIR.getRegistryRef()) && ProceduralStructures.isInsideIsengard(i, k)) {
                column.surfaceBiome = BiomeKeyRegistryME.ISENGARD;
            } else {
                column.surfaceBiome = getSubBiome(i, k, biome);
            }
        } else column.surfaceBiome = biome.getBiomeRegistryKey();
    }

    private RegistryEntry<Biome> toEntry(RegistryKey<Biome> key) {
        RegistryEntry<Biome> entry = entriesByKey.get(key);
        if (entry != null) return entry;
        return biomes.stream().filter(
                        b -> b.getKey().get().toString().equalsIgnoreCase(key.toString()))
                .findFirst().get();
    }
}
