package net.jukoz.me.world.biomes.surface;

import net.minecraft.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

import java.util.ArrayList;

public class SubBiome {
    public ArrayList<SubBiomeData> subBiomesData;
    private float additionalHeight;

    public SubBiome() {
        this(56);
    }

    public SubBiome(float additionalHeight) {
        this.subBiomesData = new ArrayList<>();
        this.additionalHeight = additionalHeight;
    }

    public SubBiome addSubBiomeData(float noiseMin, float noiseMax, RegistryKey<Biome> biome) {
        return this.addSubBiomeData(noiseMin, noiseMax, biome, false);
    }

    public SubBiome addSubBiomeData(float noiseMin, float noiseMax, RegistryKey<Biome> biome, boolean additionalHeight) {
        SubBiomeData newSubBiomeData = new SubBiomeData(noiseMin, noiseMax, biome, additionalHeight);
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
            if(value >= subBiomeData.noiseMin && value < subBiomeData.noiseMax) {
                return subBiomeData;
            }
        }
        return null;
    }

    public boolean containsSubBiome(RegistryKey<Biome> biomeRegistryKey) {
        for(SubBiomeData subBiomeData : subBiomesData) {
            if(biomeRegistryKey == subBiomeData.biome) return true;
        }
        return false;
    }

    public float getAdditionalHeight(float value) {
        SubBiomeData biomeData = getBiomeAtNoise(value);
        if(biomeData == null) {
            return 0;
        } else {
            if(!biomeData.additionalHeight) return 0;
            float distanceLeft = recursiveFindClosestVoid(value, -1);
            float distanceRight = recursiveFindClosestVoid(value, 1);
            return Math.min(value - distanceLeft, distanceRight - value) * 56f;
        }
    }

    private float recursiveFindClosestVoid(float value, int direction) {
        SubBiomeData biomeData = getBiomeAtNoise(value);
        if(biomeData == null) {
            return value;
        } else {
            if(!biomeData.additionalHeight) return value;
            else if(direction == -1) {
                return recursiveFindClosestVoid(biomeData.noiseMin - 0.001f, direction);
            } else {
                return recursiveFindClosestVoid(biomeData.noiseMax, direction);
            }
        }
    }

    public class SubBiomeData {
        public float noiseMin;
        public float noiseMax;
        public RegistryKey<Biome> biome;
        public boolean additionalHeight;

        public SubBiomeData(float noiseMin, float noiseMax, RegistryKey<Biome> biome, boolean additionalHeight) {
            this.noiseMin = noiseMin;
            this.noiseMax = noiseMax;
            this.biome = biome;
            this.additionalHeight = additionalHeight;
        }
    }
}
