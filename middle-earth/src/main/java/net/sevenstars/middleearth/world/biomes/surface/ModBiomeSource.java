package net.sevenstars.middleearth.world.biomes.surface;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.QuartPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.phys.Vec2;
import net.sevenstars.middleearth.utils.noises.BlendedNoise;
import net.sevenstars.middleearth.utils.noises.SimplexNoise;
import net.sevenstars.middleearth.world.biomes.MEBiomeKeys;
import net.sevenstars.middleearth.world.biomes.caves.CaveType;
import net.sevenstars.middleearth.world.biomes.caves.ModCaveBiomes;
import net.sevenstars.middleearth.world.chunkgen.MiddleEarthChunkGenerator;
import net.sevenstars.middleearth.world.chunkgen.ProceduralStructures;
import net.sevenstars.middleearth.world.chunkgen.map.MiddleEarthHeightMap;
import net.sevenstars.middleearth.world.features.underground.CavesPlacedFeatures;
import net.sevenstars.middleearth.world.map.MiddleEarthMapRuntime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class ModBiomeSource extends BiomeSource {

    public static final MapCodec<ModBiomeSource> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
            RegistryOps.retrieveGetter(Registries.BIOME)).apply(instance, ModBiomeSource::new));

    private final List<Holder<Biome>> biomes;
    private final Map<ResourceKey<Biome>, Holder<Biome>> biomesByKey;
    private final Holder<Biome> fallbackBiome;
    private static final int CAVE_NOISE = 360;
    private static final int CAVE_OFFSET = 7220;
    public static final int SUB_BIOME_NOISE = 256;
    public static final int SUB_BIOME_OFFSET = 8240;
    private final MiddleEarthMapRuntime middleEarthMapRuntime;
    private volatile SeedBinding seedBinding;

    public ModBiomeSource(HolderGetter<Biome> biomeRegistry) {
        this(MiddleEarthChunkGenerator.createBiomeList(biomeRegistry));
    }

    public ModBiomeSource(List<Holder<Biome>> biomes) {
        if (biomes.isEmpty()) {
            throw new IllegalArgumentException("Middle-earth biome source requires at least one biome");
        }
        this.biomes = biomes;
        this.biomesByKey = new HashMap<>();
        for (Holder<Biome> biome : biomes) {
            biome.unwrapKey().ifPresent(key -> biomesByKey.put(key, biome));
        }
        this.fallbackBiome = biomesByKey.getOrDefault(MEBiomeKeys.OCEAN, biomes.get(0));
        this.middleEarthMapRuntime = MiddleEarthMapRuntime.getInstance();
    }

    @Override
    protected MapCodec<? extends BiomeSource> codec() {
        return CODEC;
    }

    @Override
    protected Stream<Holder<Biome>> collectPossibleBiomes() {
        return biomes.stream();
    }

    public synchronized void bindWorldSeed(long worldSeed) {
        if (seedBinding != null && seedBinding.value() != worldSeed) {
            throw new IllegalStateException("Middle-earth biome source was rebound to a different world seed");
        }
        seedBinding = new SeedBinding(worldSeed);
    }

    public long getWorldSeed() {
        SeedBinding binding = seedBinding;
        if (binding == null) {
            throw new IllegalStateException("Middle-earth biome source world seed is not bound");
        }
        return binding.value();
    }

    private ResourceKey<Biome> getCaveBiome(int x, int z, BiomeData surfaceBiome, long worldSeed) {
        x += worldSeed;
        z += worldSeed;
        float temperature = (float) SimplexNoise.noise((double) x / CAVE_NOISE,  (double) z / CAVE_NOISE);
        float humidity = (float) SimplexNoise.noise((double) (x + CAVE_OFFSET) / CAVE_NOISE, (double)(z + CAVE_OFFSET) / CAVE_NOISE);
        return ModCaveBiomes.getBiome(new Vec2(temperature, humidity), surfaceBiome);
    }

    public static double getSubBiomeNoise(int x, int z, float frequency, long worldSeed) {
        x += worldSeed;
        z += worldSeed;
        float noiseFrequency = (SUB_BIOME_NOISE * frequency);
        double perlin = 1 * BlendedNoise.noise((double) x / noiseFrequency, (double) z / noiseFrequency);
        perlin += 0.5f * BlendedNoise.noise((double) x * 2 / noiseFrequency, (double) z * 2 / noiseFrequency);
        perlin = perlin / (1 + 0.5f); // 2 octaves
        return perlin;
    }

    @Override
    public Holder<Biome> getNoiseBiome(int x, int y, int z, Climate.Sampler noise) {
        int i = QuartPos.toBlock(x);
        int j = QuartPos.toBlock(y);
        int k = QuartPos.toBlock(z);
        long worldSeed = getWorldSeed();

        MapBasedCustomBiome biomeHeightData = middleEarthMapRuntime.getBiome(i, k);
        
        if (biomeHeightData == null) {
            return fallbackBiome;
        }

        BiomeData biome = biomeHeightData.getBiome();
        ResourceKey<Biome> processedBiome;

        float height = MiddleEarthChunkGenerator.DIRT_HEIGHT + MiddleEarthHeightMap.getHeight(i, k, worldSeed);
        if(j <= CavesPlacedFeatures.MAX_MITHRIL_HEIGHT && biome.getCaveType() == CaveType.MISTIES) {
            processedBiome = MEBiomeKeys.MITHRIL_CAVE;
        } else if(j < (height - 16)) {
            processedBiome = getCaveBiome(i, k, biome, worldSeed);
        }
        else {
            ResourceKey<Biome> biomeRegistryKey = biome.getBiomeRegistryKey();
            if (MapBasedBiomePool.waterBiomes.contains(biomeRegistryKey)) {
                processedBiome = biomeRegistryKey;
            } else if (isDeadMarshesBiome(biomeRegistryKey)) {
                float surfaceHeight = getDeadMarshesSurfaceHeight(
                        i, k, biomeHeightData, worldSeed, height);
                if (j < surfaceHeight - 20) {
                    processedBiome = getCaveBiome(i, k, biome, worldSeed);
                } else {
                    processedBiome = getDeadMarshesBiomeKey(surfaceHeight);
                }
            } else {
                processedBiome = resolveSurfaceBiomeKey(
                        i, k, biomeHeightData, worldSeed, height);
            }
        }

        return biomesByKey.getOrDefault(processedBiome, fallbackBiome);
    }

    private ResourceKey<Biome> resolveSurfaceBiomeKey(
            int x,
            int z,
            MapBasedCustomBiome biomeHeightData,
            long worldSeed,
            float absoluteSurfaceHeight
    ) {
        BiomeData biome = biomeHeightData.getBiome();
        ResourceKey<Biome> biomeRegistryKey = biome.getBiomeRegistryKey();
        if (MapBasedBiomePool.waterBiomes.contains(biomeRegistryKey)) {
            return biomeRegistryKey;
        }

        if (isDeadMarshesBiome(biomeRegistryKey)) {
            return getDeadMarshesBiomeKey(
                    getDeadMarshesSurfaceHeight(
                            x, z, biomeHeightData, worldSeed, absoluteSurfaceHeight)
            );
        }

        float height = absoluteSurfaceHeight;
        SubBiome subBiome = SubBiomes.getSubBiome(biomeHeightData.getBiomeKey());
        float subBiomeNoise = 0.0f;
        SubBiome.SubBiomeData selectedSubBiome = null;
        if(subBiome != null) {
            subBiomeNoise = (float) getSubBiomeNoise(x, z, subBiome.getFrequency(), worldSeed);
            selectedSubBiome = subBiome.getBiomeAtNoise(subBiomeNoise);
        }

        ResourceKey<Biome> processedBiome;
        float waterThreshold = biomeHeightData.getWaterHeight() + 1.25f;
        if (height <= waterThreshold) {
            if (subBiome != null) {
                double additionalHeight = subBiome.getAdditionalHeight(subBiomeNoise);
                height += (float) (additionalHeight * middleEarthMapRuntime.getEdge(x, z));
            }
        }
        if(height <= waterThreshold) {
            if(MapBasedBiomePool.coastalBiomes.contains(biomeRegistryKey)){
                processedBiome = MapBasedBiomePool.oceanCoast.getBiomeKey();
            } else if(MapBasedBiomePool.wastePondBiomes.contains(biomeRegistryKey)) {
                processedBiome = MapBasedBiomePool.wastePond.getBiomeKey();
            } else if(MapBasedBiomePool.mirkwoodSwampBiomes.contains(biomeRegistryKey)) {
                processedBiome = MapBasedBiomePool.mirkwoodSwamp.getBiomeKey();
            } else if(MapBasedBiomePool.oasisBiomes.contains(biomeRegistryKey)) {
                processedBiome = MapBasedBiomePool.oasis.getBiomeKey();
            } else if(MapBasedBiomePool.frozenBiomes.contains(biomeRegistryKey)) {
                processedBiome = MapBasedBiomePool.frozenPond.getBiomeKey();
            } else if(MapBasedBiomePool.anduinWaterBiomes.contains(biomeRegistryKey)){
                processedBiome = MapBasedBiomePool.greatRiver.getBiomeKey();
            } else if(MapBasedBiomePool.mangrovePondBiomes.contains(biomeRegistryKey)){
                processedBiome = MapBasedBiomePool.mangrovePond.getBiomeKey();
            } else {
                processedBiome = MapBasedBiomePool.pond.getBiomeKey();
            }
        } else if(biomeRegistryKey.isFor(MEBiomeKeys.NAN_CURUNIR.registryKey())
                && ProceduralStructures.isInsideIsengard(x, z)) {
            processedBiome = MEBiomeKeys.ISENGARD;
        } else {
            processedBiome = selectedSubBiome == null
                    ? biomeRegistryKey
                    : selectedSubBiome.biome;
        }
        return processedBiome;
    }

    private float getDeadMarshesSurfaceHeight(
            int x,
            int z,
            MapBasedCustomBiome biomeHeightData,
            long worldSeed,
            float absoluteSurfaceHeight
    ) {
        float height = absoluteSurfaceHeight;
        if (MapBasedBiomePool.waterBiomes.contains(biomeHeightData.getBiomeKey())) {
            return height;
        }

        SubBiome subBiome = SubBiomes.getSubBiome(biomeHeightData.getBiomeKey());
        if (subBiome != null) {
            float noise = (float) getSubBiomeNoise(x, z, subBiome.getFrequency(), worldSeed);
            double additionalHeight = subBiome.getAdditionalHeight(noise);
            height += (float) (additionalHeight * middleEarthMapRuntime.getEdge(x, z));
        }
        return MiddleEarthChunkGenerator.DIRT_HEIGHT
                + MiddleEarthChunkGenerator.getMarshesHeight(x, z, height);
    }

    private static boolean isDeadMarshesBiome(ResourceKey<Biome> biomeKey) {
        return biomeKey == MapBasedBiomePool.deadMarshes.getBiomeKey()
                || biomeKey == MapBasedBiomePool.deadMarshesWater.getBiomeKey();
    }

    private static ResourceKey<Biome> getDeadMarshesBiomeKey(float surfaceHeight) {
        return surfaceHeight < MiddleEarthChunkGenerator.WATER_HEIGHT
                ? MapBasedBiomePool.deadMarshesWater.getBiomeKey()
                : MapBasedBiomePool.deadMarshes.getBiomeKey();
    }

    private record SeedBinding(long value) {
    }
}
