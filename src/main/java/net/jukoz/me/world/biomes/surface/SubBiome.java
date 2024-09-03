package net.jukoz.me.world.biomes.surface;

import net.minecraft.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

import java.util.ArrayList;

public class SubBiome {
    public ArrayList<SubBiomeData> subBiomesData;

    public SubBiome(SubBiome subBiome) {
        this.subBiomesData = new ArrayList<>(subBiome.subBiomesData);
    }

    public SubBiome() {
        this.subBiomesData = new ArrayList<>();
    }

    public SubBiome addSubBiomeData(float noiseMin, float noiseMax, RegistryKey<Biome> biome) {
        SubBiomeData newSubBiomeData = new SubBiomeData(noiseMin, noiseMax, biome);
        if(!subBiomesData.isEmpty()) {
            for(SubBiomeData subBiomeData : subBiomesData) {
                if(newSubBiomeData.noiseMax > subBiomeData.noiseMin && newSubBiomeData.noiseMin < subBiomeData.noiseMax) {
                    throw new ArithmeticException("Sub biome conflicts in noise range");
                }
            }
        }
        subBiomesData.add(newSubBiomeData);
        return this;
    }

    public SubBiomeData getBiomeAtNoise(float value) {
        for(SubBiomeData subBiomeData : subBiomesData) {
            if(value > subBiomeData.noiseMin && value < subBiomeData.noiseMax) {
                return subBiomeData;
            }
        }
        return null;
    }

    public class SubBiomeData {
        public float noiseMin;
        public float noiseMax;
        public RegistryKey<Biome> biome;

        public SubBiomeData(float noiseMin, float noiseMax, RegistryKey<Biome> biome) {
            this.noiseMin = noiseMin;
            this.noiseMax = noiseMax;
            this.biome = biome;
        }
    }
}
