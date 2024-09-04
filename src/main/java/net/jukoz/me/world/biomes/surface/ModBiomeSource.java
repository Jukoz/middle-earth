package net.jukoz.me.world.biomes.surface;

import com.mojang.datafixers.kinds.Applicative;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.jukoz.me.utils.noises.BlendedNoise;
import net.jukoz.me.world.biomes.MEBiomeDataConfigs;
import net.jukoz.me.world.chunkgen.ProceduralStructures;
import net.jukoz.me.world.features.tree.trunks.CanopyTrunkPlacer;
import net.jukoz.me.world.map.MiddleEarthMapRuntime;
import net.jukoz.me.world.chunkgen.MiddleEarthChunkGenerator;
import net.jukoz.me.world.chunkgen.map.MiddleEarthHeightMap;
import net.jukoz.me.utils.noises.SimplexNoise;
import net.jukoz.me.world.biomes.MEBiomeKeys;
import net.jukoz.me.world.biomes.caves.CaveType;
import net.jukoz.me.world.biomes.caves.ModCaveBiomes;
import net.jukoz.me.world.features.underground.CavesPlacedFeatures;
import net.minecraft.block.AbstractBlock;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.util.math.Vec2f;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeCoords;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.biome.source.CheckerboardBiomeSource;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ModBiomeSource extends BiomeSource {

    public static final MapCodec<ModBiomeSource> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
            Codec.list(Biome.REGISTRY_CODEC).fieldOf("biomes").forGetter((biomeSource) -> biomeSource.biomes)).apply(instance, ModBiomeSource::new));

    private final List<RegistryEntry<Biome>> biomes;
    private static final int CAVE_NOISE = 128;
    private static final int CAVE_OFFSET = 7220;
    public static final int SUB_BIOME_NOISE = 196;
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

    private RegistryKey<Biome> getCaveBiome(int x, int z, MEBiome surfaceBiome) {
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

    private RegistryKey<Biome> getSubBiome(int x, int z, MEBiome surfaceBiome) {
        SubBiome subBiome = SubBiomes.getSubBiome(surfaceBiome.biome);
        if(subBiome != null) {
            double perlin = getSubBiomeNoise(x, z);
            SubBiome.SubBiomeData biomeData = SubBiomes.subBiomesMap.get(surfaceBiome.biome).getBiomeAtNoise((float) perlin);
            if (biomeData != null) return biomeData.biome;
        }
        return surfaceBiome.biome;
    }

    @Override
    public RegistryEntry<Biome> getBiome(int x, int y, int z, MultiNoiseUtil.MultiNoiseSampler noise) {
        int i = BiomeCoords.toBlock(x);
        int j = BiomeCoords.toBlock(y);
        int k = BiomeCoords.toBlock(z);

        MEBiome meBiome = middleEarthMapRuntime.getBiome(i, k);
        
        if (meBiome == null) {
            return biomes.get(0);
        }

        RegistryKey<Biome> biome = meBiome.biome;
        RegistryKey<Biome> processedBiome;

        if(!MEBiomesData.waterBiomes.contains(biome)) {
            float height = MiddleEarthChunkGenerator.DIRT_HEIGHT + MiddleEarthHeightMap.getHeight(i, k);
            SubBiome subBiome = SubBiomes.getSubBiome(meBiome.biome);
            if(subBiome != null) {
                double perlin = ModBiomeSource.getSubBiomeNoise(i, k);
                double additionalHeight = subBiome.getAdditionalHeight((float) perlin);
                additionalHeight *= MiddleEarthMapRuntime.getInstance().getEdge(i, k);
                height += additionalHeight;
            }

            if(j <= CavesPlacedFeatures.MAX_MITHRIL_HEIGHT && meBiome.caveType == CaveType.MISTIES) {
                processedBiome = MEBiomeKeys.MITHRIL_CAVE;
            } else if(biome == MEBiomesData.deadMarshes.biome || biome == MEBiomesData.deadMarshesWater.biome) {
                height = MiddleEarthChunkGenerator.DIRT_HEIGHT + MiddleEarthChunkGenerator.getMarshesHeight(i, k, height);
                if(j < (height - 16)) processedBiome = getCaveBiome(i, k, meBiome);
                else if(height < MiddleEarthChunkGenerator.WATER_HEIGHT) processedBiome = MEBiomesData.deadMarshesWater.biome;
                else processedBiome = MEBiomesData.deadMarshes.biome;
            } else if(j < (height - 16)) {
                processedBiome = getCaveBiome(i, k, meBiome);
            } else if(height <= meBiome.waterHeight + 1.25f) {
                if(MEBiomesData.coastalBiomes.contains(biome)){
                    processedBiome = MEBiomesData.oceanCoast.biome;
                } else if(MEBiomesData.wastePondBiomes.contains(biome)) {
                    processedBiome = MEBiomesData.wastePond.biome;
                } else if(MEBiomesData.mirkwoodSwampBiomes.contains(biome)) {
                    processedBiome = MEBiomesData.mirkwoodSwamp.biome;
                } else if(MEBiomesData.oasisBiomes.contains(biome)) {
                    processedBiome = MEBiomesData.oasis.biome;
                } else if(MEBiomesData.frozenBiomes.contains(biome)) {
                    processedBiome = MEBiomesData.frozenPond.biome;
                } else if(MEBiomesData.anduinWaterBiomes.contains(biome)){
                    processedBiome = MEBiomesData.greatRiver.biome;
                } else {
                    processedBiome = MEBiomesData.pond.biome;
                }
            } else if(biome.isOf(MEBiomeKeys.NAN_CURUNIR.getRegistryRef()) && ProceduralStructures.isInsideIsengard(i, k)) {
                processedBiome = MEBiomeKeys.ISENGARD;
            } else {
                processedBiome = getSubBiome(i, k, meBiome);
            }
        } else processedBiome = biome;

        return biomes.stream().filter(
                        b -> b.getKey().get().toString().equalsIgnoreCase(processedBiome.toString()))
                .findFirst().get();
    }
}
