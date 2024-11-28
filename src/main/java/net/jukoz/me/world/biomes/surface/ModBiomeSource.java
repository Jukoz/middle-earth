package net.jukoz.me.world.biomes.surface;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.jukoz.me.utils.noises.BlendedNoise;
import net.jukoz.me.world.chunkgen.ProceduralStructures;
import net.jukoz.me.world.map.MiddleEarthMapRuntime;
import net.jukoz.me.world.chunkgen.MiddleEarthChunkGenerator;
import net.jukoz.me.world.chunkgen.map.MiddleEarthHeightMap;
import net.jukoz.me.utils.noises.SimplexNoise;
import net.jukoz.me.world.biomes.MEBiomeKeys;
import net.jukoz.me.world.biomes.caves.CaveType;
import net.jukoz.me.world.biomes.caves.ModCaveBiomes;
import net.jukoz.me.world.features.underground.CavesPlacedFeatures;
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
    private static final int CAVE_NOISE = 128;
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
        float temperature = (float) SimplexNoise.noise((double) x / CAVE_NOISE,  (double) z / CAVE_NOISE);
        float humidity = (float) SimplexNoise.noise((double) (x + CAVE_OFFSET) / CAVE_NOISE, (double)(z + CAVE_OFFSET) / CAVE_NOISE);
        return ModCaveBiomes.getBiome(new Vec2f(temperature, humidity), surfaceBiome);
    }

    public static double getSubBiomeNoise(int x, int z) {
        double perlin = 1 * BlendedNoise.noise((double) x / SUB_BIOME_NOISE, (double) z / SUB_BIOME_NOISE);
        perlin += 0.5f * BlendedNoise.noise((double) x * 2 / SUB_BIOME_NOISE, (double) z * 2 / SUB_BIOME_NOISE);
        perlin = perlin / (1 + 0.5f); // 2 octaves
        return perlin;
    }

    private RegistryKey<Biome> getSubBiome(int x, int z, BiomeData surfaceBiome) {
        SubBiome subBiome = SubBiomes.getSubBiome(surfaceBiome.getBiomeRegistryKey());
        if(subBiome != null) {
            double perlin = getSubBiomeNoise(x, z);
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

        if(!MapBasedBiomePool.waterBiomes.contains(biome.getBiomeRegistryKey())) {
            float height = MiddleEarthChunkGenerator.DIRT_HEIGHT + MiddleEarthHeightMap.getHeight(i, k);
            SubBiome subBiome = SubBiomes.getSubBiome(biomeHeightData.getBiomeKey());
            if(subBiome != null) {
                double perlin = ModBiomeSource.getSubBiomeNoise(i, k);
                double additionalHeight = subBiome.getAdditionalHeight((float) perlin);
                additionalHeight *= MiddleEarthMapRuntime.getInstance().getEdge(i, k);
                height += (float) additionalHeight;
            }

            if(j <= CavesPlacedFeatures.MAX_MITHRIL_HEIGHT && biome.getCaveType() == CaveType.MISTIES) {
                processedBiome = MEBiomeKeys.MITHRIL_CAVE;
            } else if(biome.getBiomeRegistryKey() == MapBasedBiomePool.deadMarshes.getBiomeKey() || biome.getBiomeRegistryKey() == MapBasedBiomePool.deadMarshesWater.getBiomeKey()) {
                height = MiddleEarthChunkGenerator.DIRT_HEIGHT + MiddleEarthChunkGenerator.getMarshesHeight(i, k, height);
                if(j < (height - 16)) processedBiome = getCaveBiome(i, k, biome);
                else if(height < MiddleEarthChunkGenerator.WATER_HEIGHT) processedBiome = MapBasedBiomePool.deadMarshesWater.getBiomeKey();
                else processedBiome = MapBasedBiomePool.deadMarshes.getBiomeKey();
            } else if(j < (height - 16)) {
                processedBiome = getCaveBiome(i, k, biome);
            } else if(height <= biomeHeightData.getWaterHeight() + 1.25f) { // TODO : This is really rough, need to be more dynamic
                if(MapBasedBiomePool.coastalBiomes.contains(biome.getBiomeRegistryKey())){
                    processedBiome = MapBasedBiomePool.oceanCoast.getBiomeKey();
                } else if(MapBasedBiomePool.wastePondBiomes.contains(biome.getBiomeRegistryKey())) {
                    processedBiome = MapBasedBiomePool.wastePond.getBiomeKey();
                } else if(MapBasedBiomePool.mirkwoodSwampBiomes.contains(biome.getBiomeRegistryKey())) {
                    processedBiome = MapBasedBiomePool.mirkwoodSwamp.getBiomeKey();
                } else if(MapBasedBiomePool.oasisBiomes.contains(biome.getBiomeRegistryKey())) {
                    processedBiome = MapBasedBiomePool.oasis.getBiomeKey();
                } else if(MapBasedBiomePool.frozenBiomes.contains(biome.getBiomeRegistryKey())) {
                    processedBiome = MapBasedBiomePool.frozenPond.getBiomeKey();
                } else if(MapBasedBiomePool.anduinWaterBiomes.contains(biome.getBiomeRegistryKey())){
                    processedBiome = MapBasedBiomePool.greatRiver.getBiomeKey();
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
