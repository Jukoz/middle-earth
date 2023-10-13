package net.jesteur.me.world.biomes;

import com.mojang.serialization.Codec;
import net.jesteur.me.world.chunkgen.MiddleEarthChunkGenerator;
import net.jesteur.me.world.chunkgen.map.MapImageLoader;
import net.jesteur.me.world.chunkgen.map.MiddleEarthHeightMap;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeCoords;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.stream.Stream;

public class ModBiomeSource extends BiomeSource {
    private final ArrayList<RegistryEntry<Biome>> biomes;

    public ModBiomeSource(ArrayList<RegistryEntry<Biome>> biomes) {
        this.biomes = biomes;
    }

    @Override
    protected Codec<? extends BiomeSource> getCodec() {
        return CODEC;
    }

    @Override
    protected Stream<RegistryEntry<Biome>> biomeStream() {
        return biomes.stream();
    }

    @Override
    public RegistryEntry<Biome> getBiome(int x, int y, int z, MultiNoiseUtil.MultiNoiseSampler noise) {
        int i = BiomeCoords.toBlock(x);
        int k = BiomeCoords.toBlock(z);

        if(!MiddleEarthHeightMap.isCoordinateInBounds(i, k)) return biomes.get(0);
        MEBiome meBiome = MEBiomesData.biomeMap.get(MapImageLoader.getBiomeColor(i, k));
        if(meBiome == null) {
            return biomes.get(0);
        }

        RegistryKey<Biome> biome = meBiome.biome;
        RegistryKey<Biome> processedBiome;

        if(!MEBiomesData.waterBiomes.contains(biome)) {
            float height = MiddleEarthChunkGenerator.DIRT_HEIGHT + MiddleEarthHeightMap.getHeight(i, k);
            if(height <= MiddleEarthChunkGenerator.WATER_HEIGHT + 1.25f) {
                if(MEBiomesData.wastePondBiomes.contains(biome)) {
                    processedBiome = MEBiomesData.wastePond.biome;
                } else if(MEBiomesData.mirkwoodSwampBiomes.contains(biome)) {
                    processedBiome = MEBiomesData.mirkwoodSwamp.biome;
                } else {
                    processedBiome = MEBiomesData.millPond.biome;
                }
            } else processedBiome = biome;
        } else processedBiome = biome;

        return biomes.stream().filter(
                        b -> b.getKey().get().toString().equalsIgnoreCase(processedBiome.toString()))
                .findFirst().get();
    }
}
