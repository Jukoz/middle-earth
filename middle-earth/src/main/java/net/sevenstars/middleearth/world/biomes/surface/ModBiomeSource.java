package net.sevenstars.middleearth.world.biomes.surface;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
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
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.Vec2f;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeCoords;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;

import java.util.List;
import java.util.stream.Stream;

public class ModBiomeSource extends BiomeSource {

    public static final MapCodec<ModBiomeSource> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
            Codec.list(Biome.REGISTRY_CODEC).fieldOf("biomes").forGetter((biomeSource) -> biomeSource.biomes)).apply(instance, ModBiomeSource::new));

    private final List<RegistryEntry<Biome>> biomes;
    private static final int CAVE_NOISE = 360;
    private static final int CAVE_OFFSET = 7220;
    public static final int SUB_BIOME_NOISE = 256;
    public static final int SUB_BIOME_OFFSET = 8240;
    private MiddleEarthMapRuntime middleEarthMapRuntime;
    public ModBiomeSource(List<RegistryEntry<Biome>> biomes) {
        this.biomes = biomes;
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

    private RegistryKey<Biome> getCaveBiome(int x, int z, BiomeData surfaceBiome) {
        x += MiddleEarthHeightMap.getSeed();
        z += MiddleEarthHeightMap.getSeed();
        float temperature = (float) SimplexNoise.noise((double) x / CAVE_NOISE,  (double) z / CAVE_NOISE);
        float humidity = (float) SimplexNoise.noise((double) (x + CAVE_OFFSET) / CAVE_NOISE, (double)(z + CAVE_OFFSET) / CAVE_NOISE);
        return ModCaveBiomes.getBiome(new Vec2f(temperature, humidity), surfaceBiome);
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

        MapBasedCustomBiome biomeHeightData = middleEarthMapRuntime.getBiome(i, k);
        
        if (biomeHeightData == null) {
            return biomes.get(0);
        }

        BiomeData biome = biomeHeightData.getBiome();
        RegistryKey<Biome> processedBiome;

        float height = MiddleEarthChunkGenerator.DIRT_HEIGHT + MiddleEarthHeightMap.getHeight(i, k);
        if(j <= CavesPlacedFeatures.MAX_MITHRIL_HEIGHT && biome.getCaveType() == CaveType.MISTIES) {
            processedBiome = MEBiomeKeys.MITHRIL_CAVE;
        } else if(j < (height - 16)) {
            processedBiome = getCaveBiome(i, k, biome);
        }
        else if(!MapBasedBiomePool.waterBiomes.contains(biome.getBiomeRegistryKey())) {
            SubBiome subBiome = SubBiomes.getSubBiome(biomeHeightData.getBiomeKey());
            if(subBiome != null) {
                double perlin = ModBiomeSource.getSubBiomeNoise(i, k, subBiome.getFrequency());
                double additionalHeight = subBiome.getAdditionalHeight((float) perlin);
                additionalHeight *= MiddleEarthMapRuntime.getInstance().getEdge(i, k);
                height += (float) additionalHeight;
            }
            RegistryKey<Biome> biomeRegistryKey = biome.getBiomeRegistryKey();
            if(j <= CavesPlacedFeatures.MAX_MITHRIL_HEIGHT && biome.getCaveType() == CaveType.MISTIES) {
                processedBiome = MEBiomeKeys.MITHRIL_CAVE;
            } else if(biomeRegistryKey == MapBasedBiomePool.deadMarshes.getBiomeKey() || biomeRegistryKey == MapBasedBiomePool.deadMarshesWater.getBiomeKey()) {
                height = MiddleEarthChunkGenerator.DIRT_HEIGHT + MiddleEarthChunkGenerator.getMarshesHeight(i, k, height);
                if(j < (height - 20)) processedBiome = getCaveBiome(i, k, biome);
                else if(height < MiddleEarthChunkGenerator.WATER_HEIGHT) processedBiome = MapBasedBiomePool.deadMarshesWater.getBiomeKey();
                else processedBiome = MapBasedBiomePool.deadMarshes.getBiomeKey();
            } else if(height <= biomeHeightData.getWaterHeight() + 1.25f) { // TODO : This is really rough, need to be more dynamic
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
            } else if(biome.getBiomeRegistryKey().isOf(MEBiomeKeys.NAN_CURUNIR.getRegistryRef()) && ProceduralStructures.isInsideIsengard(i, k)) {
                processedBiome = MEBiomeKeys.ISENGARD;
            } else {
                processedBiome = getSubBiome(i, k, biome);
            }
        } else processedBiome = biome.getBiomeRegistryKey();

        return biomes.stream().filter(
                        b -> b.getKey().get().toString().equalsIgnoreCase(processedBiome.toString()))
                .findFirst().get();
    }
}
